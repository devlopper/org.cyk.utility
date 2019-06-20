package org.cyk.utility.criteria;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.value.ValueUsageType;

@Dependent
public class CriteriaImpl extends AbstractObject implements Criteria,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setIsMulptipleValues(Boolean.FALSE);
	}
	
	@Override
	public Boolean getIsMulptipleValues(){
		return (Boolean) getProperties().getFromPath(new Object[]{Properties.IS,Properties.MULTIPLE,Properties.VALUE});
	}
	
	@Override
	public Criteria setIsMulptipleValues(Boolean value){
		getProperties().setFromPath(new Object[]{Properties.IS,Properties.MULTIPLE,Properties.VALUE},value);
		return this;
	}
	
	@Override
	public Boolean getIsAscendingOrder(){
		return (Boolean) getProperties().getFromPath(new Object[]{Properties.IS,Properties.ASCENDING});
	}
	
	@Override
	public Criteria setAscendingOrder(Boolean value){
		getProperties().setFromPath(new Object[]{Properties.IS,Properties.ASCENDING},value);
		return this;
	}
	
	@Override
	public Collection<Object> getValuesMatch(){
		return (Collection<Object>) getProperties().getFromPath(new Object[]{Properties.VALUE,Properties.MATCH});
	}
	
	@Override
	public Criteria setValuesMatch(Collection<Object> values){
		getProperties().setFromPath(new Object[]{Properties.VALUE,Properties.MATCH},values);
		return this;
	}
	
	@Override
	public Collection<Object> getValuesRequired(){
		return (Collection<Object>) getProperties().getFromPath(new Object[]{Properties.VALUE,Properties.REQUIRED});
	}
	
	@Override
	public Criteria setValuesRequired(Collection<Object> values){
		getProperties().setFromPath(new Object[]{Properties.VALUE,Properties.REQUIRED},values);
		return this;
	}
	
	@Override
	public Collection<Object> getValuesToExclude(){
		return (Collection<Object>) getProperties().getFromPath(new Object[]{Properties.VALUE,Properties.EXCLUDED});
	}
	
	@Override
	public Criteria setValuesToExclude(Collection<Object> values){
		getProperties().setFromPath(new Object[]{Properties.VALUE,Properties.EXCLUDED},values);
		return this;
	}
	
	@Override
	public Object getNullValue(){
		return getProperties().getFromPath(new Object[]{Properties.VALUE,Properties.NULL});
	}
	
	@Override
	public Criteria setNullValue(Object nullValue){
		getProperties().setFromPath(new Object[]{Properties.VALUE,Properties.NULL},nullValue);
		return this;
	}

	@Override
	public Class<?> getClazz() {
		return (Class<?>) getProperties().getFromPath(Properties.CLASS,Properties.__THIS__);
	}

	@Override
	public Criteria setClazz(Class<?> aClass) {
		getProperties().setFromPath(new Object[]{Properties.CLASS,Properties.__THIS__}, aClass);
		return this;
	}
	
	@Override
	public String getClassName() {
		return (String) getProperties().getFromPath(Properties.CLASS,Properties.NAME);
	}

	@Override
	public Criteria setClassName(String name) {
		getProperties().setFromPath(new Object[]{Properties.CLASS,Properties.NAME}, name);
		return this;
	}

	@Override
	public FieldName getFieldName() {
		return (FieldName) getProperties().getFromPath(Properties.FIELD_NAME,Properties.__THIS__);
	}

	@Override
	public Criteria setFieldName(FieldName fieldName) {
		getProperties().setFromPath(new Object[]{Properties.FIELD_NAME,Properties.__THIS__}, fieldName);
		return this;
	}

	@Override
	public ValueUsageType getFieldValueUsageType() {
		return (ValueUsageType) getProperties().getValueUsageType();
	}

	@Override
	public Criteria setFieldValueUsageType(ValueUsageType valueUsageType) {
		getProperties().setValueUsageType(valueUsageType);
		return this;
	}
	
	@Override
	public String getFieldNameAsString() {
		return (String) getProperties().getFromPath(Properties.FIELD_NAME,Properties.NAME);
	}

	@Override
	public Criteria setFieldNameAsString(String fieldName) {
		getProperties().setFromPath(new Object[]{Properties.FIELD_NAME,Properties.NAME}, fieldName);
		return this;
	}
	
	@Override
	public Criteria addChild(Object... children) {
		return (Criteria) super.addChild(children);
	}
	
	/**/
	
	/*public T getPreparedValue(){
		return __inject__(CollectionHelper.class).isEmpty(getValuesMatch()) ? nullValue : value;
	}*/
	/*
	public java.lang.Boolean isNull(){
		return value == null;
	}*/
	/*
	public abstract T get(String string);
	
	public Criteria set(java.lang.String string){
		setValue(get(string));
		return this;
	}
	
	@Override
	public java.lang.String toString() {
		Object o = getPreparedValue();
		if(o==null)
			return Constant.EMPTY_STRING;
		else
			return o.toString();
	}*/
}
