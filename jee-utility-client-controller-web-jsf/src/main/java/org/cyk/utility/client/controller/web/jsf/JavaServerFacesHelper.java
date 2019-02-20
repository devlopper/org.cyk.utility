package org.cyk.utility.client.controller.web.jsf;

import java.io.Serializable;

import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.inject.Singleton;

import org.cyk.utility.client.controller.AbstractObject;

@Singleton @Named
public class JavaServerFacesHelper extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final String EXPRESSION_FORMAT = "#{%s}";
	
	public Boolean isMessageMaximumSeverityInfo() {
		return FacesMessage.SEVERITY_INFO.equals(FacesContext.getCurrentInstance().getMaximumSeverity());
	}
	
	public String getScriptInstructionGoToUrl(String url) {
		return "window.location.href='"+url+"'";
	}
	
	public String getScriptInstructionGoToUrlIfMessageMaximumSeverityIsInfo(String url) {
		return "if(#{javaServerFacesHelper.isMessageMaximumSeverityInfo()}) "+getScriptInstructionGoToUrl(url);
	}
	
	public String formatExpression(String expression) {
		return String.format(EXPRESSION_FORMAT, expression);
	}
	
	public ValueExpression buildValueExpression(String expression,Class<?> returnType) {
		return FacesContext.getCurrentInstance().getApplication().getExpressionFactory().createValueExpression(
				FacesContext.getCurrentInstance().getELContext(), expression, returnType);
	}
	
	public void setValueExpression(UIComponent uiComponent,String propertyName,ValueExpression valueExpression) {
		uiComponent.setValueExpression(propertyName,valueExpression);
	}
	
	public void setValueExpression(UIComponent uiComponent,String propertyName,String expression,Class<?> returnType) {
		setValueExpression(uiComponent, propertyName, buildValueExpression(expression, returnType));
	}
	
	/**/
	
	public MethodExpression buildMethodExpression(String expression,Class<?> returnType,Class<?>[] parameterTypes) {
		return FacesContext.getCurrentInstance().getApplication().getExpressionFactory().createMethodExpression(
				FacesContext.getCurrentInstance().getELContext(), formatExpression(expression), returnType,parameterTypes);
	}
	/*
	public void setMethodExpression(UIComponent uiComponent,String propertyName,MethodExpression valueExpression) {
		uiComponent.setMethodExpression(propertyName,valueExpression);
	}
	
	public void setMethodExpression(UIComponent uiComponent,String propertyName,String expression,Class<?> returnType,Class<?>[] parameterTypes) {
		setMethodExpression(uiComponent, propertyName, buildValueExpression(expression, returnType));
	}*/
	
	public Boolean hasMessages(String clientId) {
		return FacesContext.getCurrentInstance().getMessages(clientId).hasNext();
	}
}
