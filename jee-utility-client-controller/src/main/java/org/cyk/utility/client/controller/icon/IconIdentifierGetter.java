package org.cyk.utility.client.controller.icon;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface IconIdentifierGetter extends FunctionWithPropertiesAsInput<Object> {

	Icon getIcon();
	IconIdentifierGetter setIcon(Icon icon);
	
}
