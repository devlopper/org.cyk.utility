package org.cyk.utility.field;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.field.FieldName;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.value.ValueUsageType;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.regularexpression.RegularExpressionInstance;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.string.StringLocatable;
import org.cyk.utility.string.StringLocatables;
import org.cyk.utility.string.StringLocation;

@Dependent @Deprecated
public class FieldsGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Fields> implements FieldsGetter, Serializable {
	private static final long serialVersionUID = 1L;

	private static final Map<Class<?>,List<Field>> CLASS_FIELDS = new HashMap<>();
	public static Boolean IS_CLASS_FIELDS_CACHABLE = Boolean.TRUE;
	
	private StringLocation tokenLocation;
	private Boolean isInheritanceFirst;
	private RegularExpressionInstance nameRegularExpression;
	private StringLocatables nameTokens;
	
	@Override
	protected Fields __execute__() {
		Fields fields = null;
		Class<?> aClass = getClazz();
		
		List<Field> collection = __getClassFields__(aClass);
		
	    StringLocatables nameTokens = getNameTokens();
	    if(__injectCollectionHelper__().isEmpty(nameTokens)) {
	    	RegularExpressionInstance nameRegularExpression = getNameRegularExpression();
		    if(nameRegularExpression == null) {
		    	String token = getToken();
				if(__inject__(StringHelper.class).isBlank(token)){
					FieldName fieldName = getFieldName();
					if(fieldName!=null){
						ValueUsageType valueUsageType = getValueUsageType();
						token = __inject__(FieldNameGetter.class).setClazz(aClass).setFieldName(fieldName).setValueUsageType(valueUsageType).execute().getOutput();
					}	
				}
				
				StringLocation location = getTokenLocation();
				if(location == null)
					location = StringLocation.EXAT;
				for(Field index : collection){
					Boolean add = Boolean.TRUE;
					if(__inject__(StringHelper.class).isNotBlank(token)){
						add = __inject__(StringHelper.class).isAtLocation(index.getName(), token, location);				
					}
					if(Boolean.TRUE.equals(add))
						fields = __addField__(fields, index);
				}
		    }else {
		    	for(Field index : collection){
					if(Boolean.TRUE.equals(nameRegularExpression.match(index.getName())))
						fields = __addField__(fields, index);
				}
		    }
	    }else {
	    	for(StringLocatable indexNameToken : nameTokens.get()) {
	    		String string = indexNameToken.getString();
	    		if(__inject__(StringHelper.class).isNotBlank(string)){
	    			StringLocation location = indexNameToken.getLocation();
					if(location == null)
						location = StringLocation.EXAT;
		    		for(Field indexField : collection)
		    			if(__inject__(StringHelper.class).isAtLocation(indexField.getName(), string, location))
		    				fields = __addField__(fields, indexField);	
	    		}
	    	}
	    }
		return fields;
	}
	
	protected List<Field> __getClassFields__(Class<?> klass) {
		List<Field> fields = null;
		if(klass != null) {
			if(Boolean.TRUE.equals(IS_CLASS_FIELDS_CACHABLE))
				fields = CLASS_FIELDS.get(klass);
			
			if(fields == null) {
				fields = new ArrayList<>();
				//Process extends
			    Class<?> indexClass = klass;
			    while (indexClass != null && indexClass != Object.class) {
			    	__addFields__(fields, indexClass.getDeclaredFields());
			        indexClass = indexClass.getSuperclass();
			    }
				//Process implements
			    __addFieldsFromInterface__(fields, klass);	
			    
			    if(Boolean.TRUE.equals(IS_CLASS_FIELDS_CACHABLE))
			    	CLASS_FIELDS.put(klass, fields);
			}
		}
	    return fields;
	}
	
	private void __addFieldsFromInterface__(List<Field> fields,Class<?> aClass) {
		if(aClass!=null && !aClass.equals(Object.class) && Boolean.TRUE.equals(aClass.isInterface())) {
			__addFields__(fields, aClass.getDeclaredFields());
			Class<?>[] interfaces = aClass.getInterfaces();
		    for(Class<?> index : interfaces)
		    	__addFieldsFromInterface__(fields, index);
		}
	}
	
	private void __addFields__(List<Field> fields,Field...values) {
		fields.addAll(0, __injectCollectionHelper__().instanciate(values));
	}
	
	private Fields __addField__(Fields fields,Field field) {
		if(fields == null) {
			fields = __inject__(Fields.class);
			fields.setCollectionClass(Set.class);
		}
		fields.add(field);
		return fields;
	}
	
	@Override
	public FieldsGetter execute(Class<?> aClass) {
		return (FieldsGetter) setClazz(aClass).execute();
	}
	
	@Override
	public FieldsGetter execute(Class<?> aClass, String name) {
		return (FieldsGetter) setClazz(aClass).setToken(name).setTokenLocation(StringLocation.EXAT).execute();
	}
	
	/*protected Collection<Class<?>> get(Class<?> source) {
		//super class fields first
		
		if(!Boolean.FALSE.equals(getIsRecursive())){
			Class<?> parent = source.getSuperclass();
			if (parent != null) {
				get(parent);
			}
		}
		//declared class fields second
		int searchMods = 0x0;
		for (Integer modifier : getModifiers())
			searchMods |= 0x0 | modifier;
		
		Collection<Class<?>> annotationClasses = getAnnotationClasses();
		for (Field type : source.getDeclaredFields()) {
			if(!CollectionHelper.getInstance().isEmpty(annotationClasses) && !CollectionHelper.getInstance().contains(getAnnotationClasses(type), annotationClasses)){
				continue;
			}
					
			//if(((getModifiers(type) & searchMods) == searchMods) &&  StringHelper.getInstance().isAtLocation(getName(type), getToken(), getTokenLocation())){
			//	getOutput().add(type);
			//}
		}
		
		return null;
	}*/
	
	@Override
	public StringLocatables getNameTokens() {
		return nameTokens;
	}
	
	@Override
	public FieldsGetter setNameTokens(StringLocatables nameTokens) {
		this.nameTokens = nameTokens;
		return this;
	}
	
	@Override
	public StringLocatables getNameTokens(Boolean injectIfNull) {
		return (StringLocatables) __getInjectIfNull__(FIELD_NAME_TOKENS, injectIfNull);
	}
	
	@Override
	public FieldsGetter addNameTokens(Collection<StringLocatable> nameTokens) {
		getNameTokens(Boolean.TRUE).add(nameTokens);
		return this;
	}
	
	@Override
	public FieldsGetter addNameTokens(StringLocatable... names) {
		getNameTokens(Boolean.TRUE).add(names);
		return this;
	}
	
	@Override
	public FieldsGetter addNameToken(String string, StringLocation location) {
		if(__injectStringHelper__().isNotBlank(string))
			addNameTokens(__inject__(StringLocatable.class).setString(string).setLocation(location));
		return this;
	}
	
	@Override
	public FieldsGetter addNameToken(String string) {
		return addNameToken(string, StringLocation.EXAT);
	}
	
	@Override
	public FieldsGetter addNameToken(FieldName fieldName, ValueUsageType valueUsageType) {
		if(fieldName!=null)
			addNameToken(__inject__(FieldNameGetter.class).setFieldName(fieldName).setValueUsageType(valueUsageType).execute().getOutput());
		return this;
	}
	
	public Integer getModifiers(java.lang.reflect.Field field) {
		return field.getModifiers();
	}
	
	public String getName(java.lang.reflect.Field field) {
		return field.getName();
	}
	
	protected Class<?> getParent(Class<?> clazz) {
		return clazz.getSuperclass();
	}
	
	protected java.util.Collection<java.lang.reflect.Field> getTypes(Class<?> clazz) {
		return Arrays.asList(clazz.getDeclaredFields());
	}
	
	@Override
	public FieldsGetter setInput(Properties input) {
		return (FieldsGetter) super.setInput(input);
	}

	@Override
	public FieldsGetter setProperties(Properties properties) {
		return (FieldsGetter) super.setProperties(properties);
	}
	
	@Override
	public Boolean getIsRecursive() {
		return (Boolean) getProperties().getIsRecursive();
	}
	
	@Override
	public FieldsGetter setIsRecursive(Boolean value) {
		getProperties().setIsRecursive(value);
		return this;
	}
	
	@Override
	public RegularExpressionInstance getNameRegularExpression() {
		return nameRegularExpression;
	}
	
	@Override
	public RegularExpressionInstance getNameRegularExpression(Boolean injectIfNull) {
		return (RegularExpressionInstance) __getInjectIfNull__(FIELD_NAME_REGULAR_EXPRESSION, injectIfNull);
	}
	
	@Override
	public FieldsGetter setNameRegularExpression(RegularExpressionInstance nameRegularExpression) {
		this.nameRegularExpression = nameRegularExpression;
		return this;
	}

	@Override
	public String getToken() {
		return (String) getProperties().getToken();
	}

	@Override
	public FieldsGetter setToken(String token) {
		getProperties().setToken(token);
		return this;
	}

	@Override
	public StringLocation getTokenLocation() {
		return tokenLocation;
	}

	@Override
	public FieldsGetter setTokenLocation(StringLocation tokenLocation) {
		this.tokenLocation = tokenLocation;
		return this;
	}

	@Override
	public Set<Integer> getModifiers() {
		return (Set<Integer>) getProperties().getModifiers();
	}

	@Override
	public FieldsGetter setModifiers(Set<Integer> modifiers) {
		getProperties().setModifiers(modifiers);
		return this;
	}

	@Override
	public FieldsGetter addModifiers(Collection<Integer> modifiers) {
		if(modifiers != null){
			Set<Integer> set = getModifiers();
			if(set == null)
				setModifiers(set = new LinkedHashSet<>());
			set.addAll(modifiers);
		}
		return this;
	}

	@Override
	public FieldsGetter addModifiers(Integer... modifiers) {
		addModifiers(__inject__(CollectionHelper.class).instanciate(modifiers));
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Class<?>> getAnnotationClasses() {
		return (Set<Class<?>>) getProperties().getAnnotationClasses();
	}

	@Override
	public FieldsGetter setAnnotationClasses(Set<Class<?>> annotationClasses) {
		getProperties().setAnnotationClasses(annotationClasses);
		return this;
	}

	@Override
	public FieldsGetter addAnnotationClasses(Collection<Class<?>> annotationClasses) {
		if(annotationClasses != null){
			Set<Class<?>> set = getAnnotationClasses();
			if(set == null)
				setAnnotationClasses(set = new LinkedHashSet<>());
			set.addAll(annotationClasses);
		}
		return this;
	}

	@Override
	public FieldsGetter addAnnotationClasses(Class<?>...annotationClasses) {
		addAnnotationClasses(__inject__(CollectionHelper.class).instanciate(annotationClasses));
		return this;
	}

	@Override
	public Class<?> getClazz() {
		return (Class<?>) getProperties().getClazz();
	}

	@Override
	public FieldsGetter setClazz(Class<?> aClass) {
		getProperties().setClass(aClass);
		return this;
	}

	@Override
	public FieldName getFieldName() {
		return (FieldName) getProperties().getFieldName();
	}

	@Override
	public FieldsGetter setFieldName(FieldName fieldName) {
		getProperties().setFieldName(fieldName);
		return this;
	}

	@Override
	public ValueUsageType getValueUsageType() {
		return (ValueUsageType) getProperties().getValueUsageType();
	}

	@Override
	public FieldsGetter setValueUsageType(ValueUsageType valueUsageType) {
		getProperties().setValueUsageType(valueUsageType);
		return this;
	}
	
	@Override
	public Boolean getIsInheritanceFirst() {
		return isInheritanceFirst;
	}
	
	@Override
	public FieldsGetter setIsInheritanceFirst(Boolean isInheritanceFirst) {
		this.isInheritanceFirst = isInheritanceFirst;
		return this;
	}

	private static final String FIELD_NAME_TOKENS = "nameTokens";
	private static final String FIELD_NAME_REGULAR_EXPRESSION = "nameRegularExpression";

}
