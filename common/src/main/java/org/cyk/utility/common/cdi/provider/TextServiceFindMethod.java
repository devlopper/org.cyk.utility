package org.cyk.utility.common.cdi.provider;

import org.cyk.utility.common.AbstractMethod;

public abstract class TextServiceFindMethod extends AbstractMethod<String, Object> {

	private static final long serialVersionUID = -7853031523229753639L;

	@Override
	protected String __execute__(Object parameter) {
		if(!(parameter instanceof Object[]))
			exceptionParameter("Text Service Find Method");
		Object[] parameters = (Object[]) parameter;
		return execute((String)parameters[0], (Object[])parameters[1],(Boolean)parameters[2]);
	}
	
	public abstract String execute(String id,Object[] parameters,Boolean isId);

}
