package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Singleton;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.cyk.utility.common.Action;
import org.cyk.utility.common.Builder;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.FieldOverride;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.cdi.BeanListener;
import org.cyk.utility.common.helper.ClassHelper.Listener.FieldName;
import org.cyk.utility.common.helper.ClassHelper.Listener.FieldName.ValueUsageType;
import org.cyk.utility.common.helper.ClassHelper.Listener.IdentifierType;
import org.cyk.utility.common.model.Identifiable;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import lombok.Getter;
import lombok.Setter;

@Singleton
public class ClassHelper extends AbstractReflectionHelper<Class<?>> implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final Map<Class<?>,Class<?>> MAP = new HashMap<>();
	static {
		ClassHelper.getInstance().map(Listener.class, Listener.Adapter.Default.class,Boolean.FALSE);
		ClassHelper.getInstance().map(NameBuilder.class, NameBuilder.Adapter.Default.class,Boolean.FALSE);
	}
	
	public static String IDENTIFIABLES_PACKAGE = "org.cyk";
	public static final Collection<Class<?>> IDENTIFIABLE_BASE_CLASSES = new HashSet<>();
	private static Collection<Class<?>> IDENTIFIABLES;
	public static Class<? extends Annotation> ENTITY_ANNOTATION_CLASS = javax.persistence.Entity.class;
	private static Collection<Class<?>> CLASSES_WITH_ENTITY_ANNOTATION;
	
	private static ClassHelper INSTANCE;
	public static ClassHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new ClassHelper();
		return INSTANCE;
	}
	
	private static Collection<Class<?>> CLASSES;
	private static final Map<Class<?>,String> IDENTIFIER_MAP = new HashMap<>();
	
	static {
		IDENTIFIABLE_BASE_CLASSES.add(Identifiable.class);
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public String getIdentifiablePeriodFieldName(Class<?> aClass){
		return getFieldName(aClass, FieldName.IDENTIFIABLE_PERIOD);
	}
	
	public String getFieldName(Class<?> aClass,FieldName fieldName,FieldName.ValueUsageType valueUsageType){
		return instanciateOne(Listener.class).getFieldName(aClass, fieldName, valueUsageType);
	}
	
	public String getFieldName(Class<?> aClass,FieldName fieldName){
		return instanciateOne(Listener.class).getFieldName(aClass, fieldName);
	}
	
	public String getIdentifierFieldName(Class<?> aClass,IdentifierType type){
		return instanciateOne(Listener.class).getIdentifierFieldName(aClass,type);
	}
	
	public String getIdentifierFieldName(Class<?> aClass){
		return instanciateOne(Listener.class).getIdentifierFieldName(aClass);
	}
	
	public Boolean isIdentified(Class<?> aClass,IdentifierType type){
		return instanciateOne(Listener.class).isIdentified(aClass,type);
	}
	
	public Boolean isIdentified(Class<?> aClass){
		return instanciateOne(Listener.class).isIdentified(aClass);
	}
	
	public IdentifierType getIdentifierTypeByFirstMatch(Class<?> aClass,IdentifierType...types){
		return instanciateOne(Listener.class).getIdentifierTypeByFirstMatch(aClass,types);
	}
	
	public String getNameFieldName(Class<?> aClass){
		return instanciateOne(Listener.class).getNameFieldName(aClass);
	}
	
	public Boolean isNamed(Class<?> aClass){
		return instanciateOne(Listener.class).isNamed(aClass);
	}
	
	public Boolean isNamedInBusiness(Class<?> aClass){
		return instanciateOne(Listener.class).isNamedInBusiness(aClass);
	}
	
	public Boolean isHierarchy(Class<?> aClass){
		return instanciateOne(Listener.class).isHierarchy(aClass);
	}
	
	public Boolean isTyped(Class<?> aClass){
		return instanciateOne(Listener.class).isTyped(aClass);
	}
	
	public Boolean isEnumerated(Class<?> aClass){
		return instanciateOne(Listener.class).isEnumerated(aClass);
	}
	
	public String getHierarchyFieldName(Class<?> aClass) {
		return instanciateOne(Listener.class).getHierarchyFieldName(aClass);
	}

	public String getTypeFieldName(Class<?> aClass) {
		return instanciateOne(Listener.class).getTypeFieldName(aClass);
	}
	
	public Boolean isLazy(Class<?> aClass) {
		return instanciateOne(Listener.class).isLazy(aClass);
	}
	
	public Boolean isFilterable(Class<?> aClass) {
		return instanciateOne(Listener.class).isFilterable(aClass);
	}
	
	public Boolean isPaginated(Class<?> aClass) {
		return instanciateOne(Listener.class).isPaginated(aClass);
	}
	
	public Integer getPageSize(Class<?> aClass) {
		return instanciateOne(Listener.class).getPageSize(aClass);
	}
	
	public Boolean isPersisted(Class<?> aClass) {
		return instanciateOne(Listener.class).isPersisted(aClass);
	}
	
	public Boolean isModel(Class<?> aClass) {
		return instanciateOne(Listener.class).isModel(aClass);
	}
	
	public Collection<Class<?>> getAnnotatedWithEntity(){
		if(CLASSES_WITH_ENTITY_ANNOTATION==null)
			CLASSES_WITH_ENTITY_ANNOTATION = ClassHelper.getInstance().getByAnnotation("org.cyk", ENTITY_ANNOTATION_CLASS);
		return CLASSES_WITH_ENTITY_ANNOTATION;
	}
	
	public Collection<Class<?>> getClasses(){
		if(CLASSES==null)
			CLASSES = ClassHelper.getInstance().get("org.cyk", Object.class);
		return CLASSES;
	}
	
	public Collection<Class<?>> getAnnotatedWithEntityAndPackageNameStartsWith(String string){
		return filterByPackageName(getAnnotatedWithEntity(),string, StringHelper.Location.START);
	}
	
	public Collection<Class<?>> getAnnotatedWithEntityAndPackageNameStartsWith(Package aPackage){
		return getAnnotatedWithEntityAndPackageNameStartsWith(aPackage.getName());
	}
	
	public Collection<Class<?>> getAnnotatedWithEntityAndPackageNameStartsWith(Class<?> aClass){
		return getAnnotatedWithEntityAndPackageNameStartsWith(aClass.getPackage());
	}
	
	public Collection<Class<?>> filterByPackageName(Collection<Class<?>> classes,String string,StringHelper.Location location){
		Collection<Class<?>> result = new ArrayList<Class<?>>();
		for(Class<?> index : classes)
			if(StringHelper.getInstance().isAtLocation(index.getPackage().getName(), string, location))
				result.add(index);
		return result;
	}
	
	public Class<?> getWrapper(Class<?> aClass){
		return ClassUtils.primitiveToWrapper(aClass);
	}
	
	public Boolean isNumber(Class<?> aClass){
		return isInstanceOf(Number.class, getWrapper(aClass));
	}
	
	public Boolean isString(Class<?> aClass){
		return isEqual(java.lang.String.class, aClass);
	}
	
	public Boolean isDate(Class<?> aClass){
		return isEqual(java.util.Date.class, aClass);
	}
	
	public Boolean isBoolean(Class<?> aClass){
		return isEqual(Boolean.class, getWrapper(aClass));
	}
	
	public Class<?> get(Class<?> aClass, String fieldName,Class<?> fieldType) {
		FieldOverride fieldOverride = inject(FieldHelper.class).getOverride(aClass,fieldName);
		Class<?> clazz;
		if(fieldOverride==null)
			clazz = fieldType;
		else
			clazz = fieldOverride.type();
		return clazz;
	}
	
	public Class<?> get(Class<?> aClass, String fieldName) {
		Field field = inject(FieldHelper.class).get(aClass, fieldName);
		return get(aClass, field);
	}
	
	public Class<?> get(Class<?> aClass, Field field) {
		return get(aClass, field.getName(), field.getType());
	}
	
	public Collection<Class<?>> get(Class<?> aClass,Collection<Field> fields){
		Collection<Class<?>> classes = new ArrayList<Class<?>>();
		for(Field field : fields)
			classes.add(FieldHelper.getInstance().getType(aClass, field));
		return classes;
	}
	
	public Collection<Class<?>> getByFieldTypeIdentified(Class<?> aClass){
		return ClassHelper.getInstance().get(aClass, FieldHelper.getInstance().getByTypeIdentified(aClass));
	}
	
	/*@SuppressWarnings("unchecked")
	public Collection<Class<?>> get(String packageName,Class<?> baseClass){
		Reflections reflections = new Reflections(packageName);
		@SuppressWarnings("rawtypes")
		Collection classes = reflections.getSubTypesOf(baseClass);
		logTrace("sub types of {} in package {} are : {}", baseClass,packageName,classes);
	    return classes;
	}*/
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Collection<Class<?>> get(String packageName,Class<?> baseClass){
		SubTypesScanner subTypesScanner = new SubTypesScanner(false);
		
		ConfigurationBuilder configurationBuilder = new ConfigurationBuilder()
		    	.filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(packageName)))
		    	.setUrls(ClasspathHelper.forPackage(packageName))
		    	.setScanners(subTypesScanner);
		
		Collection classes = new Reflections(configurationBuilder).getSubTypesOf(baseClass);
		logTrace("sub types of {} in package {} are : {}", baseClass,packageName,classes);
		return classes;
	}
	
	/*public Collection<Class<?>> getIdentifiables(String packageName){
		return get(packageName, Identifiable.class);
	}*/
	
	public Collection<Class<?>> getIdentifiables(){
		if(IDENTIFIABLES == null){
			IDENTIFIABLES = new HashSet<>(); 
			for(Class<?> aClass : IDENTIFIABLE_BASE_CLASSES){
				if(aClass.getAnnotation(ENTITY_ANNOTATION_CLASS)!=null)
					IDENTIFIABLES.add(aClass);
				IDENTIFIABLES.addAll(get(IDENTIFIABLES_PACKAGE, aClass));
			}
			IDENTIFIABLES.removeAll(Arrays.asList(Identifiable.Class.class,Identifiable.Long.class,Identifiable.Long.Class.class));
			logTrace("base packages {} , identifiables {}", IDENTIFIABLE_BASE_CLASSES,IDENTIFIABLES);
		}
		return IDENTIFIABLES;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Class<?>> getByAnnotation(String packageName,Class<? extends Annotation> annotationClass){
		Reflections reflections = new Reflections(packageName,new TypeAnnotationsScanner());
		@SuppressWarnings("rawtypes")
		Collection classes = reflections.getTypesAnnotatedWith(annotationClass);
		return classes;
	}
	
	@SuppressWarnings("unchecked")
	public <TYPE> Class<TYPE> getParameterAt(Class<?> aClass,Integer index,Class<TYPE> typeClass){
		return (Class<TYPE>) ((ParameterizedType) aClass.getGenericSuperclass()).getActualTypeArguments()[index];
	}
	
	@SuppressWarnings("unchecked")
	public <TYPE> Class<TYPE> getParameterAt(Field field,Integer index,Class<TYPE> typeClass){
		return (Class<TYPE>) ((ParameterizedType)field.getGenericType()).getActualTypeArguments()[index];
	}
	
	public Class<?> getByName(Class<?> aClass){
		return getByName(aClass.getName());
	}
	
	public Class<?> getByName(String name,Boolean returnNullIfNotExist){
		try {
			if(Boolean.TRUE.equals(returnNullIfNotExist))
				return Boolean.TRUE.equals(getIsExist(name)) ? __getByName__(name) : null;
			return __getByName__(name);
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	public Class<?> getByName(String name){
		return getByName(name, Boolean.FALSE);
	}
	
	private Class<?> __getByName__(String name) throws ClassNotFoundException{
		return Class.forName(name);
	}
	
	public Boolean getIsExist(String name){
		try {
			__getByName__(name);
			return Boolean.TRUE;
		} catch (Exception exception) {
			if(exception.getClass().equals(ClassNotFoundException.class))
				return Boolean.FALSE;
			throw new RuntimeException(exception);
		}
	}
	
	public <T> Constructor<T> getConstructor(Class<T> aClass,Class<?>[] parameters){
		try {
			//return aClass.getConstructor(parameters);
			return ConstructorUtils.getMatchingAccessibleConstructor(aClass, parameters);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public <T> T instanciate(Class<T> aClass,Object[] constructorParameters){
		Class<?>[] classes = new Class[constructorParameters.length / 2];
		Object[] arguments = new Object[constructorParameters.length / 2];
		int j = 0;
		for(int i = 0 ; i < constructorParameters.length ; i = i + 2){
			classes[j] = (Class<?>) constructorParameters[i];
			arguments[j++] = constructorParameters[i+1];
		}
		try {
			Constructor<T> constructor = getConstructor(aClass, classes);
			if(constructor == null){
				logError("no constructor found in class % with parameters %", aClass,StringUtils.join(classes,","));
				return null;
			}
			return constructor.newInstance(arguments);
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
	}
	
	private <T> T __instanciate__(Class<T> aClass){
		try {
			return aClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public <T> T instanciateOne(Class<T> aClass,Boolean getMapping){
		try {
			return new Instanciation.Get.Adapter.Default<>(Boolean.TRUE.equals(getMapping) ? getMapping(aClass, Boolean.TRUE) : aClass).execute();
			//return getListener().instanciateOne(Boolean.TRUE.equals(getMapping) ? getMapping(aClass, Boolean.TRUE) : aClass);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public <T> T instanciateOne(Class<T> aClass){
		return instanciateOne(aClass,Boolean.TRUE);
	}
	
	public <T> T instanciateOne(Class<T> aClass,Object identifier,Listener.IdentifierType identifierType){
		T instance = instanciateOne(aClass);
		FieldHelper.getInstance().set(instance, identifier, getIdentifierFieldName(aClass,identifierType));
		return instance;
	}
	
	public <T> T instanciateOne(Class<T> aClass,Object identifier){
		return instanciateOne(aClass, identifier,Listener.IdentifierType.BUSINESS);
	}
	
	public Collection<?> instanciateMany(@SuppressWarnings("rawtypes") Collection classes){
		Collection<Object> collection = new ArrayList<>();
		if(classes!=null)
			for(Object aClass : classes)
				collection.add(instanciateOne((Class<?>) aClass));
		return collection;
	}
	
	@SuppressWarnings("unchecked")
	public <T> Collection<T> instanciateMany(Class<T> aClass,@SuppressWarnings("rawtypes") Collection classes){
		return (Collection<T>) instanciateMany(classes);
	}
	
	public Boolean isInstanceOf(Class<?> parentClass,Class<?> childClass){
		if(parentClass==null || childClass==null)
			return Boolean.FALSE;
		return parentClass.isAssignableFrom(childClass);
	}
	
	public Boolean isInstanceOfOneAtLeast(Class<?> aClass,Collection<Class<?>> classes){
		if(CollectionHelper.getInstance().isNotEmpty(classes)){
			for(Class<?> indexClass : classes)
				if((aClass.equals(indexClass) || isInstanceOf(indexClass, aClass)))
					return Boolean.TRUE;	
		}
		
		return Boolean.FALSE;
	}
	
	public Boolean isEqual(Class<?> aClass1,Class<?> aClass2){
		if(aClass1==null || aClass2==null)
			return Boolean.FALSE;
		return aClass1.equals(aClass2);
	}
	
	public String getVariableName(Class<?> aClass,Boolean many){
		if(aClass==null)
			return null;
		String name = StringHelper.getInstance().normalizeToVariableName(aClass.getSimpleName());
		if(Boolean.TRUE.equals(many))
			name = StringHelper.getInstance().getVariableNameMany(name);
		return name;
	}
	
	public String getVariableName(Class<?> aClass){
		return getVariableName(aClass, Boolean.FALSE);
	}
	
	public List<String> getContainerNames(Class<?> aClass){
		List<String> names = new ArrayList<>();
		String packageName = aClass.getPackage().getName()+Constant.CHARACTER_DOT;
		String[] classeNames = StringUtils.split(StringUtils.substringAfter(aClass.getName(),packageName), Constant.CHARACTER_DOLLAR.toString());
		for(String className : classeNames)
			if(!aClass.getSimpleName().equals(className))
				names.add(className);
		Collections.reverse(names);
		return names;
	}

	public void map(Class<?> aClass1,Class<?> aClass2,Boolean overwrite){
		Class<?> previous = MAP.get(aClass1);
		if(previous==null || Boolean.TRUE.equals(overwrite)){
			MAP.put(aClass1, aClass2);
		}
		logTrace("map class , old={} , overwrite={} , new={}",previous,overwrite,MAP.get(aClass1));
	}
	
	public void map(Class<?> aClass1,Class<?> aClass2){
		map(aClass1, aClass2, Boolean.TRUE);
	}
	
	public <T> Class<? extends T> getMapping(Class<T> aClass,Boolean returnClassIfNull){
		@SuppressWarnings("unchecked")
		Class<T> result = (Class<T>) MAP.get(aClass);
		result = result == null ? Boolean.TRUE.equals(returnClassIfNull) ? aClass : null : result;
		logTrace("mapped class of {} is {}",aClass,result);
		return result;
	}
	
	public <T> Class<? extends T> getMapping(Class<T> aClass){
		return getMapping(aClass, Boolean.TRUE);
	}
	
	public String computeIdentifier(Class<?> aClass){
		String identifier = aClass.getSimpleName().toLowerCase();
		return identifier;
	}
	
	public void registerIdentifier(Collection<Class<?>> classes){
		final LoggingHelper.Message.Builder loggingMessageBuiler = new LoggingHelper.Message.Builder.Adapter.Default();
		loggingMessageBuiler.addNamedParameters("register classes",classes);
		new CollectionHelper.Iterator.Adapter.Default<Class<?>>(classes){
			private static final long serialVersionUID = 1L;

			protected void __executeForEach__(java.lang.Class<?> aClass) {
				String identifier = IDENTIFIER_MAP.get(aClass);
				if(StringHelper.getInstance().isBlank(identifier)){
					IDENTIFIER_MAP.put(aClass, computeIdentifier(aClass));
					loggingMessageBuiler.addNamedParameters("class",aClass,"identifier",IDENTIFIER_MAP.get(aClass));
				}
			}
		}.execute();
		logTrace(loggingMessageBuiler);
	}
	
	public void registerIdentifier(Class<?>...classes){
		if(ArrayHelper.getInstance().isNotEmpty(classes))
			registerIdentifier(Arrays.asList(classes));
	}
	
	public String getIdentifier(Class<?> aClass){
		String identifier = IDENTIFIER_MAP.get(aClass);
		if(StringHelper.getInstance().isBlank(identifier)){
			registerIdentifier(aClass);
			identifier = IDENTIFIER_MAP.get(aClass);
		}
		return identifier;
	}
	
	public Class<?> getClassByIdentifier(String identifier){ 
		Class<?> aClass = null;
		for(Entry<Class<?>,String> entry : IDENTIFIER_MAP.entrySet())
			if(entry.getValue().equals(identifier)){
				aClass = entry.getKey();
				break;
			}
		for(Class<?> index : getIdentifiables())
			if(identifier.equals(computeIdentifier(index))){
				registerIdentifier(index);
				aClass = index;
				break;
			}
		logTrace("class with identifier {} is {}", identifier,aClass);
		return aClass;
	}
	
	/**/
	
	public static interface Get extends AbstractReflectionHelper.Get<Package, Class<?>> {
		
		Class<?> getBaseClass();
		Get setBaseClass(Class<?> aClass);
		
		@Getter @Setter 
		public static class Adapter extends AbstractReflectionHelper.Get.Adapter.Default<Package, Class<?>> implements Get,Serializable {
			private static final long serialVersionUID = 1L;

			protected Class<?> baseClass;
			
			@SuppressWarnings("unchecked")
			public Adapter(Package input) {
				super(input);
				setInputClass((Class<Package>) ClassHelper.getInstance().getByName(Class.class.getName())); 
				setOutputClass((Class<Collection<Class<?>>>) ClassHelper.getInstance().getByName(Class.class.getName())); 
			}
			
			public Get setBaseClass(Class<?> baseClass){
				this.baseClass = baseClass;
				return this;
			}
			
			/**/
			
			public static class Default extends Get.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Package input) {
					super(input);
				}
				
				@Override
				public Integer getModifiers(Class<?> clazz) {
					return clazz.getModifiers();
				}
				
				@Override
				public String getName(Class<?> clazz) {
					return clazz.getName();
				}
				
				@Override
				protected Package getParent(Package aPackage) {
					return null;
				}
				
				@Override
				protected Collection<Class<?>> getTypes(Package aPackage) {
					return ClassHelper.getInstance().get(aPackage.getName(), getBaseClass());
				}
				
				@Override
				public Set<Class<?>> getAnnotationClasses(Class<?> aClass) {
					Set<Class<?>> classes = new HashSet<>();
					for(Annotation annotation : aClass.getAnnotations()){
						classes.add(annotation.annotationType());
					}
					return classes;
				}
				
			}
		}
		
	}

	public static interface Instanciation<INSTANCE> extends org.cyk.utility.common.Builder<INSTANCE,INSTANCE> {
		
		ListenerHelper.Executor.Function.Adapter.Default.Object<Get<?>> getGetExecutor();
		Instanciation<INSTANCE> setGetExecutor(ListenerHelper.Executor.Function.Adapter.Default.Object<Get<?>> getExecutor);
		
		@Getter
		public static class Adapter<INSTANCE> extends org.cyk.utility.common.Builder.Adapter.Default<INSTANCE,INSTANCE> implements Instanciation<INSTANCE>,Serializable {
			private static final long serialVersionUID = 1L;
			
			protected ListenerHelper.Executor.Function.Adapter.Default.Object<Get<?>> getExecutor;
			
			@SuppressWarnings("unchecked")
			public Adapter(Class<INSTANCE> outputClass) {
				super(outputClass, (INSTANCE) outputClass, outputClass);
			}
			
			@Override
			public Instanciation<INSTANCE> setGetExecutor(ListenerHelper.Executor.Function.Adapter.Default.Object<Get<?>> getExecutor) {
				return null;
			}
			
			public static class Default<INSTANCE> extends Instanciation.Adapter<INSTANCE> implements Serializable {
				private static final long serialVersionUID = 1L;
				
				public Default(Class<INSTANCE> outputClass) {
					super(outputClass);
				}
				
				@Override
				public Instanciation<INSTANCE> setGetExecutor(ListenerHelper.Executor.Function.Adapter.Default.Object<Get<?>> getExecutor) {
					this.getExecutor = getExecutor;
					return this;
				}
				
				@SuppressWarnings({ "rawtypes", "unchecked" })
				@Override
				protected INSTANCE __execute__() {
					ListenerHelper.Executor.Function.Adapter.Default.Object<Get<?>> getExecutor = getGetExecutor();
					if(getExecutor==null){
						getExecutor = new ListenerHelper.Executor.Function.Adapter.Default.Object<Get<?>>();
						getExecutor.setResultMethod(ClassHelper.getInstance().instanciateOne(Get.Adapter.Default.RESULT_METHOD_CLASS));
						getExecutor.setInput((Collection) ClassHelper.getInstance().instanciateMany(Get.class
								,CollectionHelper.getInstance().isEmpty(Get.CLASSES) ? Arrays.asList(Get.Adapter.Default.class) : Get.CLASSES));
					}
					getExecutor.getResultMethod().setInputClass((Class<Object>) getOutputClass());
					getExecutor.getResultMethod().setInput(getOutputClass());
					getExecutor.getResultMethod().setOutputClass((Class<Object>) getOutputClass());
					INSTANCE instance = (INSTANCE) getExecutor.execute();
					return instance;
				}
			}
		}
		
		/**/
		
		public static interface Get<INSTANCE> extends Action<INSTANCE, INSTANCE> {
			
			Collection<Class<? extends Get<?>>> CLASSES = new ArrayList<>();
			
			public static class Adapter<INSTANCE> extends Action.Adapter.Default<INSTANCE, INSTANCE> implements Get<INSTANCE>,Serializable {
				private static final long serialVersionUID = 1L;
				
				@SuppressWarnings("unchecked")
				public Adapter(Class<INSTANCE> outputClass) {
					super("get", outputClass, (INSTANCE) outputClass, outputClass);
				}
				
				public static class Default<INSTANCE> extends Get.Adapter<INSTANCE> implements Serializable {
					private static final long serialVersionUID = 1L;
					
					@SuppressWarnings("unchecked")
					public static Class<ListenerHelper.Executor.ResultMethod<Object, Get<?>>> RESULT_METHOD_CLASS = (Class<org.cyk.utility.common.helper.ListenerHelper.Executor.ResultMethod<Object, Get<?>>>) ClassHelper.getInstance().getByName(ResultMethod.class);
					
					public Default(Class<INSTANCE> outputClass) {
						super(outputClass);
					}
					
					public Default() {
						this(null);
					}
					
					@SuppressWarnings("unchecked")
					@Override
					protected INSTANCE __execute__() {
						return (INSTANCE) ClassHelper.getInstance().__instanciate__(RESULT_METHOD_CLASS).setInput(getInput()).execute();
					}
				}
			}
			
			public static class ResultMethod extends ListenerHelper.Executor.ResultMethod.Adapter.Default.Object<Get<?>> {
				private static final long serialVersionUID = 1L;

				@Override
				protected java.lang.Object __execute__() {
					return ClassHelper.getInstance().__instanciate__((Class<?>)getInput());
				}
			}
		}
	}
	
	public static interface Listener {
		@lombok.Getter
		public static enum FieldName {
			IDENTIFIER("code","identifier"),TYPE,NAME,BIRTH_DATE("birthDate"),DEATH_DATE("deathDate"),IDENTIFIABLE_PERIOD("__identifiablePeriod__")
			,ORDER_NUMBER("orderNumber","creationOrderNumber");
			private java.util.Map<ValueUsageType,String> valueMap = new java.util.HashMap<>();
			
			private FieldName(String business,String system) {
				if(StringHelper.getInstance().isBlank(business))
					business = name().toLowerCase();
				if(StringHelper.getInstance().isBlank(system))
					system = name().toLowerCase();
				valueMap.put(ValueUsageType.BUSINESS, business);
				valueMap.put(ValueUsageType.SYSTEM, system);
			}
			
			private FieldName(String business) {
				this(business,business);
			}
			
			private FieldName() {
				this(null,null);
			}
			
			public String getByValueUsageType(ValueUsageType valueUsageType){
				return valueMap.get(valueUsageType);
			}
			
			/**/
			
			public static enum ValueUsageType {
				BUSINESS,SYSTEM
				;
				public static ValueUsageType DEFAULT = BUSINESS;
			}
		}
		
		public static enum IdentifierType {
			BUSINESS,SYSTEM
			;
			public static IdentifierType DEFAULT = BUSINESS;
		}
		
		<T> T instanciateOne(Class<T> aClass);
		
		Boolean isModel(Class<?> aClass);
		Boolean isPersisted(Class<?> aClass);
		
		String getFieldName(Class<?> aClass,FieldName fieldName,FieldName.ValueUsageType valueUsageType);
		String getFieldName(Class<?> aClass,FieldName fieldName);
		
		Boolean hasFieldNamed(Class<?> aClass,FieldName fieldName,FieldName.ValueUsageType valueUsageType);
		Boolean hasFieldNamed(Class<?> aClass,FieldName fieldName);
		
		
		String getIdentifierFieldName(Class<?> aClass,IdentifierType type);
		String getIdentifierFieldName(Class<?> aClass);
		Boolean isIdentified(Class<?> aClass,IdentifierType type);
		Boolean isIdentified(Class<?> aClass);
		IdentifierType getIdentifierTypeByFirstMatch(Class<?> aClass,IdentifierType...types);
		
		Boolean isEnumerated(Class<?> aClass);
		
		String getNameFieldName(Class<?> aClass);
		Boolean isNamed(Class<?> aClass);
		Boolean isNamedInBusiness(Class<?> aClass);
		
		String getHierarchyFieldName(Class<?> aClass);
		Boolean isHierarchy(Class<?> aClass);
		
		String getTypeFieldName(Class<?> aClass);
		Boolean isTyped(Class<?> aClass);
		
		Boolean isFilterable(Class<?> aClass);
		Boolean isLazy(Class<?> aClass);
		Boolean isPaginated(Class<?> aClass);
		Integer getPageSize(Class<?> aClass);
		
		public static class Adapter extends AbstractBean implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				public static Integer PAGE_SIZE = 10;
				
				@Override
				public <T> T instanciateOne(Class<T> aClass) {
					try {
						return aClass.newInstance();
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
				
				@Override
				public String getFieldName(Class<?> aClass, FieldName fieldName, FieldName.ValueUsageType valueUsageType) {
					return fieldName.getByValueUsageType(valueUsageType);
				}
				
				@Override
				public String getFieldName(Class<?> aClass, FieldName fieldName) {
					return getFieldName(aClass, fieldName, FieldName.ValueUsageType.DEFAULT);
				}
				
				@Override
				public String getIdentifierFieldName(Class<?> aClass,IdentifierType type) {
					return "identifier";
				}
				
				@Override
				public String getIdentifierFieldName(Class<?> aClass) {
					return getIdentifierFieldName(aClass, IdentifierType.DEFAULT);
				}
				
				@Override
				public IdentifierType getIdentifierTypeByFirstMatch(Class<?> aClass,IdentifierType...types) {
					for(IdentifierType type : types)
						if(Boolean.TRUE.equals(isIdentified(aClass, type)))
							return type;
					return null;
				}
				
				@Override
				public Boolean hasFieldNamed(Class<?> aClass,FieldName fieldName,FieldName.ValueUsageType valueUsageType) {
					return FieldHelper.getInstance().get(aClass, getFieldName(aClass, fieldName, valueUsageType))!=null;
				}
				
				@Override
				public Boolean hasFieldNamed(Class<?> aClass,FieldName fieldName) {
					return hasFieldNamed(aClass, fieldName,FieldName.ValueUsageType.DEFAULT);
				}
				
				@Override
				public Boolean isIdentified(Class<?> aClass,IdentifierType type) {
					return FieldHelper.getInstance().get(aClass, getIdentifierFieldName(aClass,type))!=null;
				}
				
				@Override
				public Boolean isIdentified(Class<?> aClass) {
					return isIdentified(aClass, IdentifierType.DEFAULT);
				}
								
				@Override
				public String getNameFieldName(Class<?> aClass) {
					return "name";
				}
				
				@Override
				public Boolean isNamed(Class<?> aClass) {
					return FieldHelper.getInstance().get(aClass, getNameFieldName(aClass))!=null;
				}
				
				@Override
				public Boolean isNamedInBusiness(Class<?> aClass) {
					return isNamed(aClass);
				}
				
				@Override
				public String getTypeFieldName(Class<?> aClass) {
					return "type";
				}
				
				@Override
				public Boolean isTyped(Class<?> aClass) {
					return FieldHelper.getInstance().get(aClass, getTypeFieldName(aClass))!=null;
				}
				
				@Override
				public String getHierarchyFieldName(Class<?> aClass) {
					return "parent";
				}
				
				@Override
				public Boolean isHierarchy(Class<?> aClass) {
					return FieldHelper.getInstance().get(aClass, getHierarchyFieldName(aClass))!=null;
				}
				
				@Override
				public Boolean isFilterable(Class<?> aClass) {
					return isIdentified(aClass,getIdentifierTypeByFirstMatch(aClass, IdentifierType.values()));
				}
				
				@Override
				public Boolean isLazy(Class<?> aClass) {
					return isIdentified(aClass,getIdentifierTypeByFirstMatch(aClass, IdentifierType.values()));
				}
				
				@Override
				public Boolean isPaginated(Class<?> aClass) {
					return isLazy(aClass);
				}
				
				@Override
				public Integer getPageSize(Class<?> aClass) {
					return PAGE_SIZE;
				}
				
				@Override
				public Boolean isModel(Class<?> aClass) {
					return isIdentified(aClass,getIdentifierTypeByFirstMatch(aClass, IdentifierType.values()));
				}
				
				@Override
				public Boolean isPersisted(Class<?> aClass) {
					return isIdentified(aClass,getIdentifierTypeByFirstMatch(aClass, IdentifierType.values()));
				}
				
				/**/
				
			}
			
			@Override
			public Boolean hasFieldNamed(Class<?> aClass, FieldName fieldName, ValueUsageType valueUsageType) {
				return null;
			}
			
			@Override
			public Boolean hasFieldNamed(Class<?> aClass, FieldName fieldName) {
				return null;
			}
			
			@Override
			public String getFieldName(Class<?> aClass, FieldName fieldName, FieldName.ValueUsageType valueUsageType) {
				return null;
			}
			
			@Override
			public String getFieldName(Class<?> aClass, FieldName fieldName) {
				return null;
			}
			
			@Override
			public <T> T instanciateOne(Class<T> aClass) {
				return null;
			}
			
			@Override
			public Boolean isModel(Class<?> aClass) {
				return null;
			}
			
			@Override
			public Boolean isPersisted(Class<?> aClass) {
				return null;
			}
		
			@Override
			public Boolean isFilterable(Class<?> aClass) {
				return null;
			}
			
			@Override
			public Boolean isLazy(Class<?> aClass) {
				return null;
			}
			
			@Override
			public Boolean isPaginated(Class<?> aClass) {
				return null;
			}
			
			@Override
			public Integer getPageSize(Class<?> aClass) {
				return null;
			}
			
			@Override
			public Boolean isHierarchy(Class<?> aClass) {
				return null;
			}
			
			@Override
			public Boolean isTyped(Class<?> aClass) {
				return null;
			}
		
			@Override
			public String getHierarchyFieldName(Class<?> aClass) {
				return null;
			}
		
			@Override
			public String getTypeFieldName(Class<?> aClass) {
				return null;
			}
			
			@Override
			public String getIdentifierFieldName(Class<?> aClass,IdentifierType type) {
				return null;
			}
			
			@Override
			public String getIdentifierFieldName(Class<?> aClass) {
				return null;
			}
			
			@Override
			public Boolean isIdentified(Class<?> aClass,IdentifierType type) {
				return null;
			}
			
			@Override
			public Boolean isIdentified(Class<?> aClass) {
				return null;
			}
			
			@Override
			public IdentifierType getIdentifierTypeByFirstMatch(Class<?> aClass, IdentifierType...types) {
				return null;
			}
		
			@Override
			public Boolean isNamed(Class<?> aClass) {
				return null;
			}
			
			@Override
			public Boolean isNamedInBusiness(Class<?> aClass) {
				return null;
			}
			
			@Override
			public String getNameFieldName(Class<?> aClass) {
				return null;
			}
		
			@Override
			public Boolean isEnumerated(Class<?> aClass) {
				return null;
			}

		}
		
	}

	/*
	 * mypack.MyClass -> {mypack01.MyClass,mypack01.MyPrefixMyClass,mypack01.MyClassMySuffix,mypack01.MyPrefixMyClassMySuffix}
	 * this can be resume as : {package base name set}{prefix set}{class simple name set}{suffix set}
	 */
	public static interface NameBuilder extends Builder.NullableInput<Set<String>> {
		
		public static class Adapter extends Builder.NullableInput.Adapter.Default<Set<String>> implements NameBuilder,Serializable {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			public Adapter() {
				super((Class<Set<String>>) getInstance().getByName(Set.class));
			}
			
			public static class Default extends NameBuilder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				@SuppressWarnings("unchecked")
				@Override
				protected Set<String> __execute__() {
					Set<String> set = new LinkedHashSet<String>();
					Set<String> packageBaseNameSet = (Set<String>) getPropertiesMap().getPackageBaseNameSet();
					Set<String> prefixSet = (Set<String>) getPropertiesMap().getPrefixSet();
					Set<String> suffixSet = (Set<String>) getPropertiesMap().getSuffixSet();
					Set<String> classSimpleNameSet = (Set<String>) getPropertiesMap().getClassSimpleNameSet();
					
					Class<?> aClass = (Class<?>) getPropertiesMap().getClazz();
					
					if(packageBaseNameSet == null && aClass!=null){
						packageBaseNameSet = new LinkedHashSet<String>(Arrays.asList(aClass.getPackage().getName()));
					}
					
					if(classSimpleNameSet == null && aClass!=null){
						classSimpleNameSet = new LinkedHashSet<String>(Arrays.asList(aClass.getSimpleName()));
					}
					
					if(prefixSet == null){
						prefixSet = new HashSet<String>(Arrays.asList(Constant.EMPTY_STRING));
					}
					
					if(suffixSet == null){
						suffixSet = new HashSet<String>(Arrays.asList(Constant.EMPTY_STRING));
					}
					
					for(String packageBaseName : packageBaseNameSet)
						for(String prefix : prefixSet)
							for(String classSimpleName : classSimpleNameSet)
								for(String suffix : suffixSet)
									set.add(StringHelper.getInstance().appendIfDoesNotEndWith(packageBaseName, Constant.CHARACTER_DOT.toString())+prefix+classSimpleName+suffix);
					
					return set;
				}	
			}
		}
	}
	
	/**/
	
	@Getter @Setter
	public static class Locator extends AbstractBean implements Serializable {
		private static final long serialVersionUID = -5858728987562936549L;

		protected String classType;
		protected NameBuilder nameBuilder;
		protected Map<Class<?>,Class<?>> cache = new HashMap<>();
		protected Boolean logClassNotFoundException = Boolean.TRUE;
		
		public Class<?> locate(final Class<?> basedClass){
			Class<?> clazz = null;
			if(Boolean.TRUE.equals(isLocatable(basedClass))){
				clazz = cache.get(basedClass);
				if(clazz==null){
					/*if(nameBuilder==null){
						nameBuilder = getInstance().instanciateOne(NameBuilder.class);
					}*/
					
					Set<String> names = getNames(basedClass);
					logTrace("names : {}", StringHelper.getInstance().concatenate(names, " , "));
					if(CollectionHelper.getInstance().isNotEmpty(names))
						for(String name : names){
							if(StringUtils.isNotBlank(name)){
								try {
									clazz = Class.forName(name);
								} catch (ClassNotFoundException e) {
									clazz = listenClassNotFound(name);
									//if(clazz == null && Boolean.TRUE.equals(getLogClassNotFoundException()))
									//	logThrowable(e);
								}
								if(clazz!=null)
									break;
							}
						}
				}
			}else
				logTrace("{} is not locatable", basedClass);
			
			if(clazz==null)
				clazz = getDefault(basedClass);
			
			if(clazz==null)
				logClassIsNull(basedClass);
			else
				cache.put(basedClass, clazz);
			return clazz;
		}
		
		protected Boolean isLocatable(Class<?> aClass){
			return Boolean.TRUE;
		}
		
		protected Set<String> getNames(Class<?> basedClass){
			nameBuilder.getPropertiesMap().setClass(basedClass);
			return nameBuilder.execute();
		}
		
		protected Class<?> listenClassNotFound(String name){
			return null;
		}
		
		protected Class<?> getDefault(Class<?> aClass){
			return null;
		}
		
		protected void logClassIsNull(Class<?> basedClass){
			logWarning(getLogClassIsNullMessageFormat(), basedClass);
		}
		
		protected String getLogClassIsNullMessageFormat(){
			return (StringUtils.isBlank(classType) ? Constant.EMPTY_STRING : (classType+Constant.CHARACTER_SPACE))
					+"class cannot be found based on {}";
		}
		
		@SuppressWarnings("unchecked")
		public <T> T injectLocated(Class<T> aClass) {
			Class<?> clazz = locate(aClass);
			if(clazz==null)
				return null;
			return (T) inject(clazz);
		}
		
		/**/
		
		public static interface Listener {
			
			Class<?> locate(Class<?> basedClass);
			
			Boolean isLocatable(Class<?> basedClass);
			
			/**/
			
			/**/
			
			@Getter @Setter
			public static class Adapter extends BeanListener.Adapter implements Listener,Serializable {
				private static final long serialVersionUID = -4338231956722553859L;

				@Override
				public Class<?> locate(Class<?> basedClass) {
					return null;
				}
				@Override
				public Boolean isLocatable(Class<?> basedClass) {
					return null;
				}
				/**/
				
				@Getter @Setter
				public static class Default extends Listener.Adapter implements Serializable {

					private static final long serialVersionUID = -4338231956722553859L;
					
				}
				
			}
		}

	}

	
}
