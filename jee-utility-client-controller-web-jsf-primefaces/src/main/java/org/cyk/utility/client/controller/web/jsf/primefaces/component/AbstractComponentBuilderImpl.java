package org.cyk.utility.client.controller.web.jsf.primefaces.component;

import java.io.Serializable;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;

import org.cyk.utility.client.controller.component.Component;
import org.cyk.utility.client.controller.web.ValueExpressionMap;
import org.cyk.utility.client.controller.web.jsf.JavaServerFacesHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public abstract class AbstractComponentBuilderImpl<COMPONENT,MODEL extends Component> extends AbstractFunctionWithPropertiesAsInputImpl<COMPONENT> implements ComponentBuilder<COMPONENT,MODEL>,Serializable {
	private static final long serialVersionUID = 1L;

	private MODEL model;
	private ValueExpressionMap valueExpressionMap;
	
	@Override
	protected COMPONENT __execute__() throws Exception {
		MODEL model = __executeGetModel__(getModel());
		ValueExpressionMap valueExpressionMap = getValueExpressionMap();
		if(valueExpressionMap == null)
			valueExpressionMap = __inject__(ValueExpressionMap.class);
		COMPONENT component = __execute__(model,valueExpressionMap);
		return component;
	}
	
	protected MODEL __executeGetModel__(MODEL model) {
		return model;
	}
	protected abstract COMPONENT __execute__(MODEL model,ValueExpressionMap valueExpressionMap) throws Exception;
	
	@Override
	public MODEL getModel() {
		return model;
	}
	
	@Override
	public ComponentBuilder<COMPONENT,MODEL> setModel(MODEL model) {
		this.model = model;
		return this;
	}
	
	@Override
	public ValueExpressionMap getValueExpressionMap() {
		return valueExpressionMap;
	}
	
	@Override
	public ComponentBuilder<COMPONENT,MODEL> setValueExpressionMap(ValueExpressionMap valueExpressionMap) {
		this.valueExpressionMap = valueExpressionMap;
		return this;
	}
	
	/**/
	
	protected static JavaServerFacesHelper __injectJavaServerFacesHelper__() {
		return __inject__(JavaServerFacesHelper.class);
	}
	
	protected static String __formatExpression__(String expression) {
		return __injectJavaServerFacesHelper__().formatExpression(expression);
	}
	
	protected static ValueExpression __buildValueExpression__(String expression,Class<?> returnType) {
		return __injectJavaServerFacesHelper__().buildValueExpression(expression, returnType);
	}
	
	protected static ValueExpression __buildValueExpressionString__(String expression) {
		return __buildValueExpression__(expression, String.class);
	}
	
	protected static void __setValueExpression__(UIComponent uiComponent,String propertyName,ValueExpression valueExpression) {
		__injectJavaServerFacesHelper__().setValueExpression(uiComponent, propertyName, valueExpression);
	}
	
}
