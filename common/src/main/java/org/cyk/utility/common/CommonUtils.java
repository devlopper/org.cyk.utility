package org.cyk.utility.common;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.enterprise.inject.spi.CDI;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.cyk.utility.common.ClassRepository.ClassField;
import org.cyk.utility.common.annotation.FieldOverride;
import org.cyk.utility.common.annotation.FieldOverrides;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;
import lombok.Setter;


public class CommonUtils implements Serializable  {

	private static final long serialVersionUID = -6146661020703974108L;
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtils.class);
	private static final Map<String,Class<?>> CLASSES_MAP = new HashMap<String, Class<?>>();

	public <T> T inject(Class<T> aClass){
		if(aClass==null)
			return null;
		return CDI.current().select(aClass).get();
	}
	
	public Class<?> classFormName(String name){
		try {
			Class<?> clazz = CLASSES_MAP.get(name);
			if(clazz==null){
				clazz = Class.forName(name);
				if(clazz!=null)
					CLASSES_MAP.put(name, clazz);
			}
			return clazz;
		} catch (ClassNotFoundException e) {
			LOGGER.error(String.format("Class named %s cannot be found",name),e);
			return null;
		}
	}
	
	public Field getField(Object source,Object value){
		for(Field field : getAllFields(source.getClass()))
			try {
				if(value==FieldUtils.readField(field, source, true))
					return field;
			} catch (Exception e) {
				LOGGER.error(String.format("Field named %s cannot be read from object %s",field.getName(),source),e);
				return null;
			}
		return null;
	}
	
	private Collection<Field> __getAllFields__(Collection<Field> fields,Class<?> type) {
		//super class fields first
		if (type.getSuperclass() != null) {
			fields = __getAllFields__(fields, type.getSuperclass());
		}
		//declared class fields second
		for (Field field : type.getDeclaredFields()) {
			fields.add(field);
		}
		
		return fields;
	}
	
	public Collection<Field> getAllFields(Class<?> type) {
		Collection<Field> fields = new ArrayList<>();
		if(Boolean.TRUE.equals(ClassRepository.ENABLED)){
			fields.addAll(ClassRepository.getInstance().get(type).getFields());
		}else{
			__getAllFields__(fields, type);
		}
		return fields;
	}
	
	public Field getFieldFromClass(Class<?> type,String name) {
		for(Field field : getAllFields(type))
			if(field.getName().equals(name))
				return field;
		return null;
	}
	
	public Collection<Field> getAllFields(Class<?> type,Collection<Class<? extends Annotation>> annotationClasses) {
		Collection<Field> fields = new ArrayList<>();
		for(Field field : getAllFields(type))
			if(annotationClasses==null || annotationClasses.isEmpty())
				fields.add(field);
			else
				for(Class<? extends Annotation> annotationClass : annotationClasses)
					if(field.isAnnotationPresent(annotationClass)){
						fields.add(field);
						break;
					}
		return fields;
	}
	
	public Collection<Field> getAllFields(Class<?> type,Class<? extends Annotation> annotationClass) {
		Collection<Class<? extends Annotation>> collection = new ArrayList<>();
		collection.add(annotationClass);
		return getAllFields(type,collection);
	}
	
	public Boolean isNumberClass(Class<?> aClass){
		return Number.class.isAssignableFrom(ClassUtils.primitiveToWrapper(aClass));
	}
	
	public Object readField(Object object,Field field,Boolean recursive,Boolean createIfNull,Boolean autoSet,Collection<Class<? extends Annotation>> annotationClasses){
		Object r = __readField__(object,field,recursive,annotationClasses);
		try {
			if(r==null && Boolean.TRUE.equals(createIfNull)){
				if(field.getType().equals(Collection.class) || field.getType().equals(List.class))
					r = new ArrayList<>();
				else if(field.getType().equals(Set.class))
					r = new LinkedHashSet<>();
				else
					r = field.getType().newInstance();
				if(Boolean.TRUE.equals(autoSet))
					writeField(field, object, r);
			}
		} catch (Exception e) {
			LOGGER.error(e.toString(),e);
		}	
		return r;
	}
	
	public Object readField(Object object,Field field,Boolean recursive,Boolean createIfNull,Collection<Class<? extends Annotation>> annotationClasses){
		return readField(object, field,recursive, createIfNull, Boolean.FALSE,annotationClasses);
	}
	
	public Object readField(Object object,Field field,Boolean createIfNull,Boolean autoSet){
		return readField(object, field, Boolean.FALSE, createIfNull,autoSet,null);
	}
	
	public Object readField(Object object,Field field,Boolean createIfNull){
		return readField(object, field, Boolean.FALSE, createIfNull,null);
	}
	
	private Object __readField__(Object object,Field field,Boolean recursive,Collection<Class<? extends Annotation>> annotationClasses){
		Object value = null;
		
		if(object==null)
			;
		else
			try {
				if(Boolean.TRUE.equals(recursive)){
					Collection<Field> fields = getAllFields(object.getClass(),annotationClasses);
					if(fields.contains(field))
						value = FieldUtils.readField(field, object,Boolean.TRUE);
					else{
						for(Field f : fields)
							//if(!f.getType().isPrimitive() && !f.getType().getName().startsWith("java.")){
							if(f.getType().getName().startsWith("org.cyk.")){
								value = __readField__(FieldUtils.readField(f, object,Boolean.TRUE),field,recursive,annotationClasses);
								if(value!=null)
									break;
							}
						/*if(value==null){
							System.out.println(field.getName()+" not in "+object.getClass().getSimpleName());
						}else
							System.out.println(field.getName()+"  in "+object.getClass().getSimpleName());*/
					}
				}else{
					value = FieldUtils.readField(field, object, Boolean.TRUE);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				return null;
			}	
		
		return value;
	}
	
	public void writeField(Field field, Object target, Object value){
		try {
			FieldUtils.writeField(field, target, value,Boolean.TRUE);
		} catch (IllegalAccessException e) {
			LOGGER.error(e.toString(),e);
		}
	}
	
	public <T extends Annotation> T getAnnotation(Class<?> aClass,Class<T> anAnnotationClass){
		T annotation = aClass.getAnnotation(anAnnotationClass);
		if(annotation==null && aClass.getSuperclass()!=null)
			return getAnnotation(aClass.getSuperclass(), anAnnotationClass);
		return annotation;
	}
	
	public <T extends Annotation> T getFieldAnnotation(Field field,Class<?> clazz,Class<T> anAnnotationClass,Boolean includeGetter){
		T annotation = null;
		if(Boolean.TRUE.equals(includeGetter) && clazz!=null){
			try {
				annotation = MethodUtils.getAccessibleMethod(clazz, "get"+StringUtils.capitalize(field.getName())).getAnnotation(anAnnotationClass);
			} catch (Exception e) {}
			//System.out.println( "get"+StringUtils.capitalize(field.getName())+" : "+MethodUtils.getAccessibleMethod(field.getDeclaringClass(), "get"+StringUtils.capitalize(field.getName())) );
		}
		if(annotation==null)
			annotation = field.getAnnotation(anAnnotationClass);
			
		return annotation;
	}
	
	public <T extends Annotation> T getFieldAnnotation(Field field,Class<?> clazz,Class<T> anAnnotationClass){
		return getFieldAnnotation(field,clazz, anAnnotationClass,Boolean.FALSE);
	}
	
	public <T extends Annotation> T getFieldAnnotation(Field field,Class<T> anAnnotationClass){
		return getFieldAnnotation(field,field.getDeclaringClass(), anAnnotationClass,Boolean.FALSE);
	}
	
	public Class<?> getFieldType(Class<?> declaringClass, Field field) {
		FieldOverride fieldOverride = getFieldOverride(declaringClass,field.getName());
		Class<?> clazz;
		if(fieldOverride==null)
			clazz = field.getType();
		else
			clazz = fieldOverride.type();
		return clazz;
	}
	
	public FieldOverride getFieldOverride(Class<?> aClass, String fieldName) {
		FieldOverride fieldOverride = null;
		FieldOverrides fieldOverrides = aClass.getAnnotation(FieldOverrides.class);
		if(fieldOverrides==null){
			
		}else{
			for(FieldOverride index : fieldOverrides.value())
				if( fieldName.equals(index.name()) ){
					fieldOverride = index;
					break;
				}
		}
		
		if(fieldOverride==null)
			for(Annotation annotation : aClass.getAnnotations())
				if(annotation instanceof FieldOverride && ((FieldOverride)annotation).name().equals(fieldName)){
					fieldOverride = (FieldOverride) annotation;
					break;
				}

		return fieldOverride;
	}
	
	public Object getFieldValueContainer(Object object,Field field){
		if(object==null)
			return null;
		LOGGER.trace("Class={} , Field={}",object.getClass(),field);
		/*System.out.println("Declaring Class -> "+field.getDeclaringClass());
		System.out.println("Object Class -> "+object.getClass());
		System.out.println("Class -> "+field.getDeclaringClass().equals(object.getClass()));
		System.out.println("Fields -> "+getAllFields(object.getClass()));
		System.out.println("Field -> "+field);
		System.out.println("Contains : "+ArrayUtils.contains(new ArrayList<>(getAllFields(object.getClass())).toArray(), field));
		System.out.println("Object : "+object);
		*/
		if(/*field.getDeclaringClass().equals(object.getClass()) &&*/ ArrayUtils.contains(new ArrayList<>(getAllFields(object.getClass())).toArray(), field)){
			return object;
		}
		for(Field f : object.getClass().getDeclaredFields()){
			try {
				Object value = FieldUtils.readField(f, object, Boolean.TRUE);
				LOGGER.trace("Field={} , Value={}",f,value);
				if(f.equals(field)){
					LOGGER.trace("Field found in {}",object.getClass());
					return object;
				}
				if(value!=null && !value.getClass().getName().startsWith("java."))
					return getFieldValueContainer(value, field);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public Throwable getThrowableInstanceOf(Throwable throwable,Class<?> aClass){
		Throwable index = throwable;
		while(index!=null){
			//System.out.println(aClass.getSimpleName()+" - "+index.getClass().getSimpleName()+" - "+aClass.isAssignableFrom(index.getClass()));
			if(aClass.isAssignableFrom(index.getClass())){
				return index;
			}else
				index = index.getCause();
		}
		return null;
	}
	
	public Throwable getThrowableFirstCause(Throwable throwable){
		Throwable cause=throwable,index = throwable;
		while(index!=null){
			cause = index;
			index = index.getCause();
		}
		return cause;
	}
	
	public <T> Collection<Class<? extends T>> getPackageClasses(String aPackage,Class<T> aRootClass){
		List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
	    classLoadersList.add(ClasspathHelper.contextClassLoader());
	    classLoadersList.add(ClasspathHelper.staticClassLoader());

	    Reflections reflections = new Reflections(new ConfigurationBuilder()
	        .setScanners(new SubTypesScanner(false /* don't exclude Object.class */), new ResourcesScanner())
	        .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
	        .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(aPackage))));
	    
	    return reflections.getSubTypesOf(aRootClass);
	}

	public Boolean isFieldNameIn(Field field,String...names){
		return ArrayUtils.contains(names, field.getName());
	}
	
	public Boolean isFieldNameNotIn(Field field,String...names){
		return Boolean.FALSE.equals(isFieldNameIn(field, names));
	}
	
	/**/
	
	public Date getUniversalTimeCoordinated(){
		return new DateTime(DateTimeZone.UTC).toDate();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> stringLines(String fileName,Class<?> aClass){
		try {
			return (List<String>) IOUtils.readLines(aClass.getResourceAsStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String mime(String extension) {
        try {
            return Files.probeContentType(Paths.get("file."+extension));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    } 
	
	public Boolean canWriteSourceToDestination(Object sourceObject,Field sourceField,Object destinationObject,Field destinationField){
		Class<?> sourceClass=sourceField.getType(),destinationClass=destinationField.getType();
		if(Collection.class.equals(destinationClass))
			if(Collection.class.equals(sourceClass))
				return Boolean.TRUE;
			else
				return Collection.class.isAssignableFrom(sourceClass);
		else if(List.class.equals(destinationClass))
			if(Collection.class.equals(sourceClass)){
				//Use value type
				Object value = readField(sourceObject, sourceField, Boolean.FALSE);
				return value!=null && List.class.isAssignableFrom(value.getClass());
			}else
				return List.class.isAssignableFrom(sourceClass);
		else
			return destinationClass.equals(sourceClass);
	}
	
	public Integer numberOfDaysIn(Date date1,Date date2,Boolean partial){
		return new DateTime(date2).getDayOfYear() - new DateTime(date1).getDayOfYear();
	}
	
	@SuppressWarnings("unchecked")
	public <T> Constructor<T> getConstructor(Class<T> aClass,Class<?>[] parameterTypes){
		if(Object.class.equals(aClass) || parameterTypes==null || parameterTypes.length==0)
			return null;
		
		if(parameterTypes.length > 1)
			throw new RuntimeException("Too much parameters found. One and only one parameter expected.");
		LOGGER.trace("Find constructor in {} with parameter {}",aClass,StringUtils.join(parameterTypes));
		for(Constructor<?> constructor : aClass.getDeclaredConstructors()){
			if(/*constructor==null || */parameterTypes==null || parameterTypes.length==0)
				return null;
			if(parameterTypes.length > 1)
				throw new RuntimeException("Only one parameter is supported");
			
			Class<?>[] constructorParameterTypes = constructor.getParameterTypes();
			if(constructorParameterTypes==null || constructorParameterTypes.length!=parameterTypes.length)
				continue;
			Boolean valid = Boolean.TRUE;
			for(int i=0;i<parameterTypes.length;i++)
				if(!constructorParameterTypes[i].isAssignableFrom(parameterTypes[i])){
					valid = Boolean.FALSE;
					break;
				}
			if(Boolean.TRUE.equals(valid)){
				LOGGER.trace("Found constructor is {}",constructor);
				return (Constructor<T>) constructor;
			}else{
				//keep going : check next one
			}
		}
		
		return null;
		
		/*
		try {
			LOGGER.trace("Find constructor in {} with parameter {}",aClass,parameterType);
			return aClass.getConstructor(parameterType);
		} catch (NoSuchMethodException | SecurityException e) {
			if(e instanceof NoSuchMethodException){
				if(Boolean.TRUE.equals(allowSubType))
					return getConstructor(aClass, parameterType.getSuperclass(), allowSubType);
				else
					return null;
			}else {
				LOGGER.error(e.toString(), e);
				return null;
			}
		}
		*/
	}
	
	public <T> T instanciate(Class<T> aClass,Class<?>[] parameterTypes,Object[] parameterValues){
		try {
			Constructor<T> constructor = getConstructor(aClass, parameterTypes);
			if(constructor==null)
				throw new IllegalArgumentException("No constructor found for "+aClass+" with parameters "+StringUtils.join(parameterTypes,Constant.CHARACTER_COMA));
			return constructor.newInstance(parameterValues);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/*
	private <T> Constructor<T> __getConstructor__(Constructor<T> constructor,Class<?>[] parameterTypes,Boolean allowSubType){
		if(constructor==null || parameterTypes==null || parameterTypes.length==0)
			return null;
		if(parameterTypes.length > 1)
			throw new RuntimeException("Only one parameter is supported");

		Class<?>[] constructorParameterTypes = constructor.getParameterTypes();
		if(constructorParameterTypes==null || constructorParameterTypes.length!=parameterTypes.length)
			return null;
		for(int i=0;i<parameterTypes.length;i++)
			if(!constructorParameterTypes[i].isAssignableFrom(parameterTypes[i]))
				return null;
		return constructor;
		
	}*/
	
	public String attributePath(String variable,String...properties){
		List<String> l = new ArrayList<>();
		l.addAll(Arrays.asList(properties));
		l.add(0, variable);
		return StringUtils.join(l,Constant.CHARACTER_DOT);
	}
	
	public <T> T sum(Class<T> aClass,Collection<T> collection,Set<String> fieldNames){
		T result;
		try {
			result = aClass.newInstance();
		} catch (Exception e) {
			LOGGER.error(e.toString(),e);
			return null;
		}
		List<Field> fields = new ArrayList<Field>(getAllFields(aClass));
		//filter
		if(fieldNames!=null && !fieldNames.isEmpty()){
			for(int i=0;i<fields.size();)
				if(fieldNames.contains(fields.get(i).getName()))
					i++;
				else
					fields.remove(i);
		}
		//initialize result
		for(int i=0;i<fields.size();){
			Field field = fields.get(i);
			if(BigDecimal.class.equals(field.getType()))	
				writeField(field, result, BigDecimal.ZERO);
			else if(isNumberClass(field.getType()))	
				writeField(field, result, 0);
			else{
				LOGGER.error("Sum of {} not supported yet",field.getType());
				fields.remove(i);
				continue;
			}
			i++;
		}
		
		for(T item : collection){
			for(Field field : fields){
				if(BigDecimal.class.equals(field.getType())){	
					BigDecimal value = (BigDecimal)readField(item, field, Boolean.FALSE);
					if(value==null)
						value = BigDecimal.ZERO;
					writeField(field, result,((BigDecimal)readField(result, field, Boolean.FALSE)).add(value));
				}else if(Byte.class.equals(field.getType())){
					Byte value = (Byte)readField(item, field, Boolean.FALSE);
					if(value==null)
						value = 0;
					writeField(field, result, value+(Byte)readField(result, field, Boolean.FALSE));
				}else if(Short.class.equals(field.getType())){
					Short value = (Short)readField(item, field, Boolean.FALSE);
					if(value==null)
						value = 0;
					writeField(field, result, value+(Short)readField(result, field, Boolean.FALSE));
				}else if(Integer.class.equals(field.getType())){
					Integer value = (Integer)readField(item, field, Boolean.FALSE);
					if(value==null)
						value = 0;
					writeField(field, result, value+(Integer)readField(result, field, Boolean.FALSE));
				}else if(Long.class.equals(field.getType())){
					Long value = (Long)readField(item, field, Boolean.FALSE);
					if(value==null)
						value = 0l;
					writeField(field, result, value+(Long)readField(result, field, Boolean.FALSE));
				}
			}
			
		}
		
		return result;
	}
	
	public <T> String getFieldsValues(T object,Class<? super T> upToCass){
		String value = ReflectionToStringBuilder.reflectionToString(object, ToStringStyle.SHORT_PREFIX_STYLE,Boolean.FALSE,upToCass);
		value = StringUtils.remove(value, getClass().getSimpleName());
		//value = StringUtils.replace(value, "<null>","<>");
		return value;
	}
	
	public String concatenate(String string,String[] values,String separator){
		if(StringUtils.isNotEmpty(string) && !StringUtils.endsWith(string, separator))
			string += separator;
		string=string+StringUtils.join(values,separator);
		return string;
	}
	
	public <T extends Number> void increment(Class<T> valueType,Object object,String fieldName,T increment){
		try {
			@SuppressWarnings("unchecked")
			T value = (T) FieldUtils.readField(object, fieldName, Boolean.TRUE);
			if(BigDecimal.class.equals(value.getClass())){
				FieldUtils.writeField(object, fieldName, ((BigDecimal)value).add((BigDecimal) increment), Boolean.TRUE);
			}else if(Long.class.equals(value.getClass())){
				FieldUtils.writeField(object, fieldName, ((Long)value) + (Long) increment, Boolean.TRUE);
			}else if(Integer.class.equals(value.getClass())){
				FieldUtils.writeField(object, fieldName, ((Integer)value) + (Integer) increment, Boolean.TRUE);
			}  
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	/**/
	
	public String executeCommand(String command) throws IOException, InterruptedException{
		LOGGER.trace("Executing command : {}",command);
		Process process = Runtime.getRuntime().exec(String.format(CMD_C_FORMAT, command));
		/*process.getOutputStream().write(new byte[]{'\r','\n'});
		process.getOutputStream().write(new byte[]{'\r','\n'});
		
		BufferedWriter printOut = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
		printOut.write("\r\n");printOut.write("\r\n");printOut.write("\r\n");
		*/
		process.waitFor();
		BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = input.readLine()) != null) {
        	sb.append(line+Constant.LINE_DELIMITER);
        }
        return sb.toString();
	}
	
	public BigDecimal getBigDecimal(String value){
		return value ==null ? null : new BigDecimal(value);
	}
	
	public Object readProperty(Object object,String name){
		try {
			return PropertyUtils.getProperty(object, name);
		} catch (Exception e) {
			return null;
		}
	}
	
	public void setProperty(Object object,String name,Object value){
		instanciateProperty(object, name);
		try {
			PropertyUtils.setProperty(object, name, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void instanciateProperty(Object object,String name){
		String[] n = StringUtils.split(name, Constant.CHARACTER_DOT);
		for(String p : n){
			Object pValue = readProperty(object, p);
			if(pValue==null){
				Field field = ClassRepository.getInstance().getField(object.getClass(), p);
				try {
					FieldUtils.writeField(object, p, pValue = field.getType().newInstance(), Boolean.TRUE);
				} catch (Exception e) {
					new RuntimeException(e);
				}
				LOGGER.trace("Field {} of object {} instanciated",field,object);
			}
			object = pValue;
		}
	}
	
	public <T> T instanciateOne(Class<T> aClass,T instance,ObjectFieldValues objectFieldValues){
		try {
			for(Entry<ClassField, Object> entry : objectFieldValues.getValuesMap().entrySet()){
				LOGGER.trace("Set property to be called. classfield {} , value {}",entry.getKey(),entry.getValue());
				setProperty(instance, entry.getKey().getName(), entry.getValue());
			}
			return instance;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public <T> T instanciateOne(Class<T> aClass,ObjectFieldValues objectFieldValues){
		try {
			return instanciateOne(aClass, aClass.newInstance(), objectFieldValues);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public <T> List<T> instanciateMany(Class<T> aClass,List<ObjectFieldValues> objectFieldValues){
		List<T> list = new ArrayList<>();
		for(ObjectFieldValues ov : objectFieldValues)
			list.add(instanciateOne(aClass, ov));
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T convertString(String value,Class<T> type){
		Object convertedValue = null;
		if(String.class.equals(type))
			convertedValue = value;
		else if(BigDecimal.class.equals(type))
			convertedValue = new BigDecimal(value);
		else if(Long.class.equals(type))
			convertedValue = new Long(value);
		else if(Integer.class.equals(type))
			convertedValue = new Integer(value);
		else if(Byte.class.equals(type))
			convertedValue = new Byte(value);
		else if(Date.class.equals(type))
			try {
				convertedValue = Constant.DATE_TIME_FORMATTER.parse(value);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		else
			LOGGER.error("Convert string to {} not yet implemented",type);
		return (T) convertedValue;
	}
	
	public void swapColumns(Object[][] array,Integer columnIndex1,Integer columnIndex2){
		for(Object[] row : array){
			Object value = row[columnIndex1];
			row[columnIndex1] = row[columnIndex2];
			row[columnIndex2] = value;
		}
	}
	
	public List<String[]> readExcelSheet(ReadExcelSheetArguments arguments) throws Exception{
		Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(arguments.getWorkbookBytes()));
        Sheet sheet = workbook.getSheetAt(arguments.getSheetIndex());
        FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
        List<String[]> list = new ArrayList<>();
        Integer fromRowIndex = arguments.getFromRowIndex(),fromColumnIndex = arguments.getFromColumnIndex();
        Integer rowCount = arguments.getRowCount(),columnCount = arguments.getColumnCount();
        if(rowCount==null)
        	rowCount = sheet.getLastRowNum();
        if(columnCount==null)
        	columnCount = new Integer(sheet.getRow(0).getLastCellNum());
        
        for (int i=0; i<rowCount; i++) {
        	String[] array = null;
            Row row = sheet.getRow(i + fromRowIndex);
            if(row==null){
            	
            }else{
            	array = new String[columnCount];
            	for (int j=0; j<columnCount; j++) {
                    Cell cell = row.getCell(j+fromColumnIndex);
                    if(cell==null)
                    	array[j] = Constant.EMPTY_STRING;
                    else{
                    	CellValue cellValue = formulaEvaluator.evaluate(cell);
                    	String stringValue;
                    	if(cellValue==null)
                    		stringValue = Constant.EMPTY_STRING;
                    	else switch(cellValue.getCellType()){
	    	                case Cell.CELL_TYPE_FORMULA : 
	    	                	throw new RuntimeException("Must never happen! Cannot process a formula. Please change field to result of formula.("+i+","+j+")");
	    	                case Cell.CELL_TYPE_BLANK: stringValue = Constant.EMPTY_STRING; break;
	    	                case Cell.CELL_TYPE_NUMERIC: 
	    	                	if(DateUtil.isCellDateFormatted(cell))
	                        		stringValue = Constant.DATE_TIME_FORMATTER.format(cell.getDateCellValue());
	                        	else
	                        		stringValue = String.valueOf(cellValue.getNumberValue()); 
	    	                	break;
	    	                case Cell.CELL_TYPE_STRING: stringValue = cellValue.getStringValue(); break;
	    	                default:
	    	                	stringValue = StringUtils.trim(cellValue.getStringValue());
	    	                	break;
	    	                }
                    	array[j] = String.valueOf(StringUtils.isBlank(stringValue)?Constant.EMPTY_STRING:stringValue);
                    }
                }
            }
            if(array==null)
            	;
            else{
            	Boolean isEmpty = Boolean.TRUE;
            	for(int k = 0; k < array.length; k++)
            		if(StringUtils.isNotBlank(array[k])){
            			isEmpty = Boolean.FALSE;
            			break;
            		}
            	if(!Boolean.TRUE.equals(arguments.getIgnoreEmptyRow()) || Boolean.FALSE.equals(isEmpty))
            		list.add(array);
            }
        }
        workbook.close();
        return list;
	}
	
	@Getter @Setter
	public static class ReadExcelSheetArguments{
		private byte[] workbookBytes;
		private Integer sheetIndex,fromRowIndex=0,fromColumnIndex=0,rowCount,columnCount;
		private Boolean ignoreEmptyRow = Boolean.TRUE;
	}
	
	/**/
	
	/**/
	
	private CommonUtils() {}
	
	private static final CommonUtils INSTANCE = new CommonUtils();
	
	public static CommonUtils getInstance() {
		return INSTANCE;
	}

	/**/
	
	public static final String CMD_C_FORMAT = "cmd /c %s";
}
