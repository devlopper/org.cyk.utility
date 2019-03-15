package org.cyk.utility.client.controller.web.jsf.primefaces.component;

import java.io.Serializable;
import java.util.Map;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;

import org.cyk.utility.client.controller.component.Component;
import org.cyk.utility.client.controller.web.ValueExpressionMap;
import org.cyk.utility.map.MapHelper;

public abstract class AbstractUIComponentBuilderImpl<COMPONENT extends UIComponent,MODEL extends Component> extends AbstractComponentBuilderImpl<COMPONENT,MODEL> implements UIComponentBuilder<COMPONENT,MODEL>,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected COMPONENT __execute__() throws Exception {
		MODEL model = getModel();
		ValueExpressionMap valueExpressionMap = getValueExpressionMap();
		if(valueExpressionMap == null)
			valueExpressionMap = __inject__(ValueExpressionMap.class);
		COMPONENT component = __execute__(model,valueExpressionMap);
		if(__inject__(MapHelper.class).isNotEmpty(valueExpressionMap))
			for(Map.Entry<String, ValueExpression> index : valueExpressionMap.getEntries()) {
				ValueExpression valueExpression = index.getValue();
				if(valueExpression!=null)
					__setValueExpression__(component, index.getKey(), valueExpression);	
			}
		return component;
	}
	
}
