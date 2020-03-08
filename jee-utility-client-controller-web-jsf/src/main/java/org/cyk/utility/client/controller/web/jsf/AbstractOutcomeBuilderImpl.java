package org.cyk.utility.client.controller.web.jsf;

import java.io.Serializable;

import org.cyk.utility.__kernel__.enumeration.Action;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.Case;
import org.cyk.utility.__kernel__.string.StringHelper;

public abstract class AbstractOutcomeBuilderImpl extends AbstractObject implements OutcomeBuilder,Serializable {
		
	@Override
	public String build(Class<?> klass, Action action) {
		if(klass == null)
			return null;
		if(action == null)
			return null;			
		return String.format(FORMAT, StringHelper.getVariableNameFrom(klass.getSimpleName()),StringHelper.applyCase(action.name(),Case.FIRST_CHARACTER_UPPER_REMAINDER_LOWER));
	}
}