package org.cyk.utility.condition;

import java.io.Serializable;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.throwable.ThrowableHelper;

public abstract class AbstractConditionBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<Condition> implements ConditionBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Condition __execute__() throws Exception {
		Condition condition = new Condition();
		condition.setValue(__computeConditionValue__(condition));
		if(condition.getValue() == null)
			__inject__(ThrowableHelper.class).throwRuntimeException("condition value is required");
		condition.setMessage(__computeConditionMessage__(condition));
		condition.setIdentifier(__computeConditionIdentifier__(condition));
		return condition;
	}
	
	protected Object __computeConditionIdentifier__(Condition condition){
		return getIdentifier();
	}
	
	protected Boolean __computeConditionValue__(Condition condition){
		return getValue();
	}
	
	protected String __computeConditionMessage__(Condition condition){
		return getMessage();
	}
	
	@Override
	public Boolean getValue() {
		return (Boolean) getProperties().getFromPath(Properties.VALUE);
	}
	
	@Override
	public ConditionBuilder setValue(Boolean value) {
		getProperties().setFromPath(new Object[]{Properties.VALUE}, value);
		return this;
	}
	
	@Override
	public String getMessage() {
		return (String) getProperties().getFromPath(Properties.MESSAGE);
	}
	
	@Override
	public ConditionBuilder setMessage(String message) {
		getProperties().setFromPath(new Object[]{Properties.MESSAGE}, message);
		return this;
	}
	
	@Override
	public ConditionBuilder setIdentifier(Object identifier) {
		return (ConditionBuilder) super.setIdentifier(identifier);
	}
}
