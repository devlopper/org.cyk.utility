package org.cyk.utility.client.controller.web.jsf.primefaces.builder;

import java.io.Serializable;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;

import org.cyk.utility.client.controller.AbstractObject;
import org.cyk.utility.client.controller.web.jsf.JavaServerFacesHelper;

public abstract class AbstractBuilder extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

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
