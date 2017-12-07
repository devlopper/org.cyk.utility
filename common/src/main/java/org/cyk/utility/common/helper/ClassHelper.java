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
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Singleton;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.cyk.utility.common.Action;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.FieldOverride;
import org.cyk.utility.common.cdi.AbstractBean;
import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;

import lombok.Getter;
import lombok.Setter;

@Singleton
public class ClassHelper extends AbstractReflectionHelper<Class<?>> implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final Map<Class<?>,Class<?>> MAP = new HashMap<>();
	static {
		ClassHelper.getInstance().map(Listener.class, Listener.Adapter.Default.class,Boolean.FALSE);
	}
	
	public static Class<? extends Annotation> ENTITY_ANNOTATION_CLASS = javax.persistence.Entity.class;
	private static Collection<Class<?>> CLASSES_WITH_ENTITY_ANNOTATION;
	
	private static ClassHelper INSTANCE;
	public static ClassHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new ClassHelper();
		return INSTANCE;
	}
	
	
	private static final Map<Class<?>,String> IDENTIFIER_MAP = new HashMap<>();
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public Boolean isIdentified(Class<?> aClass){
		return instanciateOne(Listener.class).isIdentified(aClass);
	}
	
	public Boolean isHierarchy(Class<?> aClass){
		return instanciateOne(Listener.class).isHierarchy(aClass);
	}
	
	public Boolean isTyped(Class<?> aClass){
		return instanciateOne(Listener.class).isTyped(aClass);
	}
	
	public String getHierarchyFieldName(Class<?> aClass) {
		return instanciateOne(Listener.class).getHierarchyFieldName(aClass);
	}

	public String getTypeFieldName(Class<?> aClass) {
		return instanciateOne(Listener.class).getTypeFieldName(aClass);
	}
	
	public Collection<Class<?>> getAnnotatedWithEntity(){
		if(CLASSES_WITH_ENTITY_ANNOTATION==null)
			CLASSES_WITH_ENTITY_ANNOTATION = ClassHelper.getInstance().getByAnnotation("org.cyk", ENTITY_ANNOTATION_CLASS);
		return CLASSES_WITH_ENTITY_ANNOTATION;
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
	
	@SuppressWarnings("unchecked")
	public Collection<Class<?>> get(String packageName,Class<?> baseClass){
		Reflections reflections = new Reflections(packageName);
		@SuppressWarnings("rawtypes")
		Collection classes = reflections.getSubTypesOf(baseClass);
		logTrace("sub types of {} in package {} are : {}", baseClass,packageName,classes);
	    return classes;
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
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public <T> T instanciateOne(Class<T> aClass){
		return instanciateOne(aClass,Boolean.TRUE);
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
		if(MAP.get(aClass1)==null || Boolean.TRUE.equals(overwrite))
			MAP.put(aClass1, aClass2);
	}
	
	public void map(Class<?> aClass1,Class<?> aClass2){
		map(aClass1, aClass2, Boolean.TRUE);
	}
	
	public <T> Class<? extends T> getMapping(Class<T> aClass,Boolean returnClassIfNull){
		@SuppressWarnings("unchecked")
		Class<T> result = (Class<T>) MAP.get(aClass);
		return result == null ? Boolean.TRUE.equals(returnClassIfNull) ? aClass : null : result;
	}
	
	public <T> Class<? extends T> getMapping(Class<T> aClass){
		return getMapping(aClass, Boolean.TRUE);
	}
	
	public void registerIdentifier(Collection<Class<?>> classes){
		new CollectionHelper.Iterator.Adapter.Default<Class<?>>(classes){
			private static final long serialVersionUID = 1L;

			protected void __executeForEach__(java.lang.Class<?> aClass) {
				String identifier = IDENTIFIER_MAP.get(aClass);
				if(StringHelper.getInstance().isBlank(identifier)){
					identifier = aClass.getSimpleName().toLowerCase();
					IDENTIFIER_MAP.put(aClass, identifier);
				}	
			}
		}.execute();
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
		for(Entry<Class<?>,String> entry : IDENTIFIER_MAP.entrySet())
			if(entry.getValue().equals(identifier))
				return entry.getKey();
		return null;
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

		String getIdentifierFieldName(Class<?> aClass);
		Boolean isIdentified(Class<?> aClass);
		
		String getHierarchyFieldName(Class<?> aClass);
		Boolean isHierarchy(Class<?> aClass);
		
		String getTypeFieldName(Class<?> aClass);
		Boolean isTyped(Class<?> aClass);
		
		public static class Adapter extends AbstractBean implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				@Override
				public String getIdentifierFieldName(Class<?> aClass) {
					return "identifier";
				}
				
				@Override
				public Boolean isIdentified(Class<?> aClass) {
					return FieldHelper.getInstance().get(aClass, getIdentifierFieldName(aClass))!=null;
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
				
				/**/
				
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
			public String getIdentifierFieldName(Class<?> aClass) {
				return null;
			}
			
			@Override
			public Boolean isIdentified(Class<?> aClass) {
				return null;
			}
		}
		
	}
}
