package org.cyk.utility.assertion;

import java.io.Serializable;

import org.cyk.utility.__kernel__.assertion.Assertion;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

@Deprecated
public abstract class AbstractAssertionBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<Assertion> implements AssertionBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private Boolean IsThrownWhenValueIsNotTrue;
	
	@Override
	protected Assertion __execute__() throws Exception {
		Boolean isAffirmation = ValueHelper.defaultToIfNull(getIsAffirmation(),Boolean.TRUE);
		
		Assertion assertion = __inject__(Assertion.class);
		assertion.setValue(__computeValue__(assertion,isAffirmation));
		if(assertion.getValue() == null)
			throw new RuntimeException("assertion value is required");
		if(!isAffirmation)
			assertion.setValue(!assertion.getValue());
		if(Boolean.TRUE.equals(assertion.getValue())){
			
		}else{
			assertion.setMessageWhenValueIsNotTrue(__computeMessageWhenValueIsNotTrue__(assertion,isAffirmation));
			assertion.setIdentifier(__computeIdentifier__(assertion));	
		}
		
		if(!Boolean.TRUE.equals(assertion.getValue()) && Boolean.TRUE.equals(getIsThrownWhenValueIsNotTrue())){
			throw new RuntimeException(assertion.getMessageWhenValueIsNotTrue());
		}
		
		return assertion;
	}
	
	protected Object __computeIdentifier__(Assertion assertion){
		return getIdentifier();
	}
	
	protected Boolean __computeValue__(Assertion assertion,Boolean isAffirmation){
		Boolean value = getValue();
		return value;
	}
	
	protected String __computeMessageWhenValueIsNotTrue__(Assertion assertion,Boolean isAffirmation){
		return getMessageWhenValueIsNotTrue();
	}
	
	@Override
	public Boolean getIsAffirmation() {
		return (Boolean) getProperties().getFromPath(Properties.AFFIRMATION);
	}
	
	@Override
	public AssertionBuilder setIsAffirmation(Boolean value) {
		getProperties().setFromPath(new Object[]{Properties.AFFIRMATION}, value);
		return this;
	}
	
	@Override
	public Boolean getValue() {
		return (Boolean) getProperties().getFromPath(Properties.VALUE);
	}
	
	@Override
	public AssertionBuilder setValue(Boolean value) {
		getProperties().setFromPath(new Object[]{Properties.VALUE}, value);
		return this;
	}
	
	@Override
	public String getMessageWhenValueIsNotTrue() {
		return (String) getProperties().getFromPath(Properties.MESSAGE);
	}
	
	@Override
	public AssertionBuilder setMessageWhenValueIsNotTrue(String message) {
		getProperties().setFromPath(new Object[]{Properties.MESSAGE}, message);
		return this;
	}
	
	@Override
	public Boolean getIsThrownWhenValueIsNotTrue() {
		return IsThrownWhenValueIsNotTrue;
	}
	
	@Override
	public AssertionBuilder setIsThrownWhenValueIsNotTrue(Boolean IsThrownWhenValueIsNotTrue) {
		this.IsThrownWhenValueIsNotTrue = IsThrownWhenValueIsNotTrue;
		return this;
	}
	
	@Override
	public AssertionBuilder setIdentifier(Object identifier) {
		return (AssertionBuilder) super.setIdentifier(identifier);
	}
}
