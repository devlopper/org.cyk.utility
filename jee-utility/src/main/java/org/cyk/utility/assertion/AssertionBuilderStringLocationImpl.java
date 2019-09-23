package org.cyk.utility.assertion;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.assertion.Assertion;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.field.FieldValueGetter;
import org.cyk.utility.string.StringLocation;
import org.cyk.utility.string.Strings;

@Dependent @Deprecated
public class AssertionBuilderStringLocationImpl extends AbstractAssertionBuilderImpl implements AssertionBuilderStringLocation, Serializable {
	private static final long serialVersionUID = 1L;
	
	private Map<StringLocation,Strings> locationStringsMap;
	
	@Override
	protected Boolean __computeValue__(Assertion assertion, Boolean isAffirmation) {	
		Boolean value = super.__computeValue__(assertion, isAffirmation);
		if(value == null){
			AssertionValue assertionValue = getAssertedValue();
			if(assertionValue!=null){
				FieldValueGetter fieldValueGetter = assertionValue.getFieldValueGetter();
				if(fieldValueGetter != null){
					//String string = (String) fieldValueGetter.execute().getOutput();
					//Map<StringLocation,Strings> locationStringsMap = getLocationStringsMap();
					//if(locationStringsMap)
					//value =  == null;
				}
			}
		}
		return value;
	}
	
	@Override
	protected String __computeMessageWhenValueIsNotTrue__(Assertion assertion,Boolean isAffirmation) {
		String message = Boolean.TRUE.equals(isAffirmation) ? "la valeur doit être nulle" : "la valeur ne doit pas être nulle";
		return message;
	}
	
	@Override
	public FieldValueGetter getFieldValueGetter() {
		//return (FieldValueGetter) getProperties().getFromPath(Properties.FIELD,Properties.VALUE,Properties.GETTER);
		return getAssertedValue(Boolean.TRUE).getFieldValueGetter();
	}
	
	@Override
	public AssertionBuilderStringLocation setFieldValueGetter(FieldValueGetter fieldValueGetter) {
		getProperties().setFromPath(new Object[]{Properties.FIELD,Properties.VALUE,Properties.GETTER}, fieldValueGetter);
		getAssertedValue(Boolean.TRUE).setFieldValueGetter(fieldValueGetter);
		return this;
	}
	
	@Override
	public AssertionBuilderStringLocation setFieldValueGetter(Object object, String... names) {
		setFieldValueGetter(org.cyk.utility.__kernel__.field.FieldHelper.read(object,names));
		getAssertedValue(Boolean.TRUE).setFieldValueGetter(object, names);
		return this;
	}
	
	@Override
	public String getValueName() {
		return getAssertedValue(Boolean.TRUE).getName();
		//return (String) getProperties().getFromPath(Properties.VALUE,Properties.NAME);
	}

	@Override
	public AssertionBuilderStringLocation setValueName(String identifier) {
		getProperties().setFromPath(new Object[]{Properties.VALUE,Properties.NAME}, identifier);
		getAssertedValue(Boolean.TRUE).setName(identifier);
		return this;
	}
	
	@Override
	public AssertionValue getAssertedValue() {
		return (AssertionValue) getProperties().getFromPath(Properties.ASSERTION,Properties.VALUE);
	}
	
	@Override
	public AssertionValue getAssertedValue(Boolean instanciateIfNull) {
		AssertionValue assertionValue = getAssertedValue();
		if(assertionValue == null && Boolean.TRUE.equals(instanciateIfNull)){
			setAssertedValue(assertionValue = __inject__(AssertionValue.class).setParent(this));
		}
		return assertionValue;
	}
	
	@Override
	public AssertionBuilderStringLocation setAssertedValue(AssertionValue assertionValue) {
		getProperties().setFromPath(new Object[]{Properties.ASSERTION,Properties.VALUE}, assertionValue);
		return this;
	}
	
	@Override
	public Map<StringLocation, Strings> getLocationStringsMap() {
		return locationStringsMap;
	}
	
	@Override
	public Map<StringLocation, Strings> getLocationStringsMap(Boolean instanciateIfNull) {
		if(locationStringsMap == null && Boolean.TRUE.equals(instanciateIfNull))
			locationStringsMap = new LinkedHashMap<>();
		return locationStringsMap;
	}
	
	@Override
	public AssertionBuilderStringLocation setLocationStringsMap(Map<StringLocation, Strings> locationStringsMap) {
		this.locationStringsMap = locationStringsMap;
		return this;
	}
	
	@Override
	public Strings getLocationStrings(StringLocation location) {
		return locationStringsMap == null ? null : locationStringsMap.get(location);
	}
	
	@Override
	public Strings getLocationStrings(StringLocation location, Boolean injectIfNull) {
		Strings instance = getLocationStrings(location);
		if(instance == null && Boolean.TRUE.equals(injectIfNull))
			setLocationStrings(location, instance = __inject__(Strings.class));
		return instance;
	}
	
	@Override
	public AssertionBuilderStringLocation setLocationStrings(StringLocation location,Strings strings) {
		if(location != null && locationStringsMap!=null)
			locationStringsMap.put(location, strings);
		return this;
	}
	
	@Override
	public AssertionBuilderStringLocation setIsAffirmation(Boolean value) {
		return (AssertionBuilderStringLocation) super.setIsAffirmation(value);
	}

}
