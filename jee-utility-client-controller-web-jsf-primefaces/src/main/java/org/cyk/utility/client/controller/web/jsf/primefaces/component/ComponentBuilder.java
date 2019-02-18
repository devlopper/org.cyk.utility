package org.cyk.utility.client.controller.web.jsf.primefaces.component;

import javax.faces.component.UIComponent;

import org.cyk.utility.client.controller.component.Component;
import org.cyk.utility.client.controller.web.ValueExpressionMap;
import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface ComponentBuilder<COMPONENT extends UIComponent,MODEL extends Component> extends FunctionWithPropertiesAsInput<COMPONENT> {
	
	MODEL getModel();
	ComponentBuilder<COMPONENT,MODEL> setModel(MODEL model);
	
	ValueExpressionMap getValueExpressionMap();
	ComponentBuilder<COMPONENT,MODEL> setValueExpressionMap(ValueExpressionMap valueExpressionMap);

}
