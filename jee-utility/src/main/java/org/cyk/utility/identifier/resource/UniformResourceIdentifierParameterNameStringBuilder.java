package org.cyk.utility.identifier.resource;

import org.cyk.utility.string.StringFunction;

public interface UniformResourceIdentifierParameterNameStringBuilder extends StringFunction {

	Object getName();
	UniformResourceIdentifierParameterNameStringBuilder setName(Object name);
	
	UniformResourceIdentifierParameterNameStringBuilder setNameAsIdentifier();
	UniformResourceIdentifierParameterNameStringBuilder setNameAsEntityClass();
	UniformResourceIdentifierParameterNameStringBuilder setNameAsEntityIdentifier();
	UniformResourceIdentifierParameterNameStringBuilder setNameAsActionClass();
	UniformResourceIdentifierParameterNameStringBuilder setNameAsActionIdentifier();
	
	UniformResourceIdentifierParameterNameStringBuilder setNameAsNextActionClass();
	UniformResourceIdentifierParameterNameStringBuilder setNameAsNextActionIdentifier();
	
	UniformResourceIdentifierParameterNameStringBuilder setNameAsWindowRenderTypeClass();
	
	/**/
	
	public static enum Name {
		ENTITY_CLASS
		,ENTITY_IDENTIFIER
		,ACTION_CLASS
		,ACTION_IDENTIFIER
		
		,NEXT_ACTION_CLASS
		,NEXT_ACTION_IDENTIFIER
		
		,WINDOW_RENDER_TYPE_CLASS
	}
}
