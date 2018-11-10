package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.message.MessageRender;
import org.cyk.utility.client.controller.message.MessageRenderTypeDialog;
import org.cyk.utility.client.controller.message.MessageRenderTypeGrowl;
import org.cyk.utility.client.controller.message.MessageRenderTypeInline;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class MessagePage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected String __getWindowTitleValue__() {
		return "Message";
	}
	
	public void showInformationInline() {
		__inject__(MessageRender.class).addOneMessage(__instanciateMessage__(FacesMessage.SEVERITY_INFO)).setType(__inject__(MessageRenderTypeInline.class)).execute();
	}
	
	public void showWarningInline() {
		__inject__(MessageRender.class).addOneMessage(__instanciateMessage__(FacesMessage.SEVERITY_WARN)).setType(__inject__(MessageRenderTypeInline.class)).execute();
	}
	
	public void showErrorInline() {
		__inject__(MessageRender.class).addOneMessage(__instanciateMessage__(FacesMessage.SEVERITY_ERROR)).setType(__inject__(MessageRenderTypeInline.class)).execute();
	}
	
	public void showFatalInline() {
		__inject__(MessageRender.class).addOneMessage(__instanciateMessage__(FacesMessage.SEVERITY_FATAL)).setType(__inject__(MessageRenderTypeInline.class)).execute();
	}
	
	public void showInformationDialog() {
		__inject__(MessageRender.class).addOneMessage(__instanciateMessage__(FacesMessage.SEVERITY_INFO)).setType(__inject__(MessageRenderTypeDialog.class)).execute();
	}
	
	public void showWarningDialog() {
		__inject__(MessageRender.class).addOneMessage(__instanciateMessage__(FacesMessage.SEVERITY_WARN)).setType(__inject__(MessageRenderTypeDialog.class)).execute();
	}
	
	public void showErrorDialog() {
		__inject__(MessageRender.class).addOneMessage(__instanciateMessage__(FacesMessage.SEVERITY_ERROR)).setType(__inject__(MessageRenderTypeDialog.class)).execute();
	}
	
	public void showFatalDialog() {
		__inject__(MessageRender.class).addOneMessage(__instanciateMessage__(FacesMessage.SEVERITY_FATAL)).setType(__inject__(MessageRenderTypeDialog.class)).execute();
	}
	
	public void showInformationGrowl() {
		__inject__(MessageRender.class).addOneMessage(__instanciateMessage__(FacesMessage.SEVERITY_INFO)).setType(__inject__(MessageRenderTypeGrowl.class)).execute();
	}
	
	public void showWarningGrowl() {
		__inject__(MessageRender.class).addOneMessage(__instanciateMessage__(FacesMessage.SEVERITY_WARN)).setType(__inject__(MessageRenderTypeGrowl.class)).execute();
	}
	
	public void showErrorGrowl() {
		__inject__(MessageRender.class).addOneMessage(__instanciateMessage__(FacesMessage.SEVERITY_ERROR)).setType(__inject__(MessageRenderTypeGrowl.class)).execute();
	}
	
	public void showFatalGrowl() {
		__inject__(MessageRender.class).addOneMessage(__instanciateMessage__(FacesMessage.SEVERITY_FATAL)).setType(__inject__(MessageRenderTypeGrowl.class)).execute();
	}
	
	/**/
	
	public FacesMessage __instanciateMessage__(Severity severity) {
		return new FacesMessage(severity, severity.toString()+" summary", "You have details "+severity+" message");
	}
}
