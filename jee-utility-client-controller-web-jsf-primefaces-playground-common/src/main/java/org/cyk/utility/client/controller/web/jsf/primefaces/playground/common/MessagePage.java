package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.message.MessageRender;
import org.cyk.utility.client.controller.message.MessageRenderTypeDialog;
import org.cyk.utility.client.controller.message.MessageRenderTypeGrowl;
import org.cyk.utility.client.controller.message.MessageRenderTypeInline;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.random.RandomHelper;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class MessagePage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected String __getWindowTitleValue__() {
		return "Message";
	}
	
	//One
	
	public void showOneInformationInline() {
		__inject__(MessageRender.class).addMessages(__instanciateMessage__(FacesMessage.SEVERITY_INFO)).setType(__inject__(MessageRenderTypeInline.class)).execute();
	}
	
	public void showOneWarningInline() {
		__inject__(MessageRender.class).addMessages(__instanciateMessage__(FacesMessage.SEVERITY_WARN)).setType(__inject__(MessageRenderTypeInline.class)).execute();
	}
	
	public void showOneErrorInline() {
		__inject__(MessageRender.class).addMessages(__instanciateMessage__(FacesMessage.SEVERITY_ERROR)).setType(__inject__(MessageRenderTypeInline.class)).execute();
	}
	
	public void showOneFatalInline() {
		__inject__(MessageRender.class).addMessages(__instanciateMessage__(FacesMessage.SEVERITY_FATAL)).setType(__inject__(MessageRenderTypeInline.class)).execute();
	}
	
	public void showOneInformationDialog() {
		__inject__(MessageRender.class).addMessages(__instanciateMessage__(FacesMessage.SEVERITY_INFO)).setType(__inject__(MessageRenderTypeDialog.class)).execute();
	}
	
	public void showOneWarningDialog() {
		__inject__(MessageRender.class).addMessages(__instanciateMessage__(FacesMessage.SEVERITY_WARN)).setType(__inject__(MessageRenderTypeDialog.class)).execute();
	}
	
	public void showOneErrorDialog() {
		__inject__(MessageRender.class).addMessages(__instanciateMessage__(FacesMessage.SEVERITY_ERROR)).setType(__inject__(MessageRenderTypeDialog.class)).execute();
	}
	
	public void showOneFatalDialog() {
		__inject__(MessageRender.class).addMessages(__instanciateMessage__(FacesMessage.SEVERITY_FATAL)).setType(__inject__(MessageRenderTypeDialog.class)).execute();
	}
	
	public void showOneInformationGrowl() {
		__inject__(MessageRender.class).addMessages(__instanciateMessage__(FacesMessage.SEVERITY_INFO)).setType(__inject__(MessageRenderTypeGrowl.class)).execute();
	}
	
	public void showOneWarningGrowl() {
		__inject__(MessageRender.class).addMessages(__instanciateMessage__(FacesMessage.SEVERITY_WARN)).setType(__inject__(MessageRenderTypeGrowl.class)).execute();
	}
	
	public void showOneErrorGrowl() {
		__inject__(MessageRender.class).addMessages(__instanciateMessage__(FacesMessage.SEVERITY_ERROR)).setType(__inject__(MessageRenderTypeGrowl.class)).execute();
	}
	
	public void showOneFatalGrowl() {
		__inject__(MessageRender.class).addMessages(__instanciateMessage__(FacesMessage.SEVERITY_FATAL)).setType(__inject__(MessageRenderTypeGrowl.class)).execute();
	}
	
	//Few
	
	public void showFewInformationInline() {
		__inject__(MessageRender.class).addMessages(__instanciateMessages__(FacesMessage.SEVERITY_INFO,5).toArray()).setType(__inject__(MessageRenderTypeInline.class)).execute();
	}
	
	public void showFewWarningInline() {
		__inject__(MessageRender.class).addMessages(__instanciateMessages__(FacesMessage.SEVERITY_WARN,5).toArray()).setType(__inject__(MessageRenderTypeInline.class)).execute();
	}
	
	public void showFewErrorInline() {
		__inject__(MessageRender.class).addMessages(__instanciateMessages__(FacesMessage.SEVERITY_ERROR,5).toArray()).setType(__inject__(MessageRenderTypeInline.class)).execute();
	}
	
	public void showFewFatalInline() {
		__inject__(MessageRender.class).addMessages(__instanciateMessages__(FacesMessage.SEVERITY_FATAL,5).toArray()).setType(__inject__(MessageRenderTypeInline.class)).execute();
	}
	
	public void showFewInformationDialog() {
		__inject__(MessageRender.class).addMessages(__instanciateMessages__(FacesMessage.SEVERITY_INFO,5).toArray()).setType(__inject__(MessageRenderTypeDialog.class)).execute();
	}
	
	public void showFewWarningDialog() {
		__inject__(MessageRender.class).addMessages(__instanciateMessages__(FacesMessage.SEVERITY_WARN,5).toArray()).setType(__inject__(MessageRenderTypeDialog.class)).execute();
	}
	
	public void showFewErrorDialog() {
		__inject__(MessageRender.class).addMessages(__instanciateMessages__(FacesMessage.SEVERITY_ERROR,5).toArray()).setType(__inject__(MessageRenderTypeDialog.class)).execute();
	}
	
	public void showFewFatalDialog() {
		__inject__(MessageRender.class).addMessages(__instanciateMessages__(FacesMessage.SEVERITY_FATAL,5).toArray()).setType(__inject__(MessageRenderTypeDialog.class)).execute();
	}
	
	public void showFewInformationGrowl() {
		__inject__(MessageRender.class).addMessages(__instanciateMessages__(FacesMessage.SEVERITY_INFO,5).toArray()).setType(__inject__(MessageRenderTypeGrowl.class)).execute();
	}
	
	public void showFewWarningGrowl() {
		__inject__(MessageRender.class).addMessages(__instanciateMessages__(FacesMessage.SEVERITY_WARN,5).toArray()).setType(__inject__(MessageRenderTypeGrowl.class)).execute();
	}
	
	public void showFewErrorGrowl() {
		__inject__(MessageRender.class).addMessages(__instanciateMessages__(FacesMessage.SEVERITY_ERROR,5).toArray()).setType(__inject__(MessageRenderTypeGrowl.class)).execute();
	}
	
	public void showFewFatalGrowl() {
		__inject__(MessageRender.class).addMessages(__instanciateMessages__(FacesMessage.SEVERITY_FATAL,5).toArray()).setType(__inject__(MessageRenderTypeGrowl.class)).execute();
	}
	
	public void showFewInline() {
		__inject__(MessageRender.class).addMessages(__instanciateMessages__(null,5).toArray()).setType(__inject__(MessageRenderTypeInline.class)).execute();
	}
	
	public void showFewDialog() {
		__inject__(MessageRender.class).addMessages(__instanciateMessages__(null,5).toArray()).setType(__inject__(MessageRenderTypeDialog.class)).execute();
	}
	
	public void showFewGrowl() {
		__inject__(MessageRender.class).addMessages(__instanciateMessages__(null,5).toArray()).setType(__inject__(MessageRenderTypeGrowl.class)).execute();
	}
	
	//Many
	
	public void showManyInformationInline() {
		__inject__(MessageRender.class).addMessages(__instanciateMessages__(FacesMessage.SEVERITY_INFO,50).toArray()).setType(__inject__(MessageRenderTypeInline.class)).execute();
	}
	
	public void showManyWarningInline() {
		__inject__(MessageRender.class).addMessages(__instanciateMessages__(FacesMessage.SEVERITY_WARN,50).toArray()).setType(__inject__(MessageRenderTypeInline.class)).execute();
	}
	
	public void showManyErrorInline() {
		__inject__(MessageRender.class).addMessages(__instanciateMessages__(FacesMessage.SEVERITY_ERROR,50).toArray()).setType(__inject__(MessageRenderTypeInline.class)).execute();
	}
	
	public void showManyFatalInline() {
		__inject__(MessageRender.class).addMessages(__instanciateMessages__(FacesMessage.SEVERITY_FATAL,50).toArray()).setType(__inject__(MessageRenderTypeInline.class)).execute();
	}
	
	public void showManyInformationDialog() {
		__inject__(MessageRender.class).addMessages(__instanciateMessages__(FacesMessage.SEVERITY_INFO,50).toArray()).setType(__inject__(MessageRenderTypeDialog.class)).execute();
	}
	
	public void showManyWarningDialog() {
		__inject__(MessageRender.class).addMessages(__instanciateMessages__(FacesMessage.SEVERITY_WARN,50).toArray()).setType(__inject__(MessageRenderTypeDialog.class)).execute();
	}
	
	public void showManyErrorDialog() {
		__inject__(MessageRender.class).addMessages(__instanciateMessages__(FacesMessage.SEVERITY_ERROR,50).toArray()).setType(__inject__(MessageRenderTypeDialog.class)).execute();
	}
	
	public void showManyFatalDialog() {
		__inject__(MessageRender.class).addMessages(__instanciateMessages__(FacesMessage.SEVERITY_FATAL,50).toArray()).setType(__inject__(MessageRenderTypeDialog.class)).execute();
	}
	
	public void showManyInformationGrowl() {
		__inject__(MessageRender.class).addMessages(__instanciateMessages__(FacesMessage.SEVERITY_INFO,50).toArray()).setType(__inject__(MessageRenderTypeGrowl.class)).execute();
	}
	
	public void showManyWarningGrowl() {
		__inject__(MessageRender.class).addMessages(__instanciateMessages__(FacesMessage.SEVERITY_WARN,50).toArray()).setType(__inject__(MessageRenderTypeGrowl.class)).execute();
	}
	
	public void showManyErrorGrowl() {
		__inject__(MessageRender.class).addMessages(__instanciateMessages__(FacesMessage.SEVERITY_ERROR,50).toArray()).setType(__inject__(MessageRenderTypeGrowl.class)).execute();
	}
	
	public void showManyFatalGrowl() {
		__inject__(MessageRender.class).addMessages(__instanciateMessages__(FacesMessage.SEVERITY_FATAL,50).toArray()).setType(__inject__(MessageRenderTypeGrowl.class)).execute();
	}
	
	public void showManyInline() {
		__inject__(MessageRender.class).addMessages(__instanciateMessages__(null,50).toArray()).setType(__inject__(MessageRenderTypeInline.class)).execute();
	}
	
	public void showManyDialog() {
		__inject__(MessageRender.class).addMessages(__instanciateMessages__(null,50).toArray()).setType(__inject__(MessageRenderTypeDialog.class)).execute();
	}
	
	public void showManyGrowl() {
		__inject__(MessageRender.class).addMessages(__instanciateMessages__(null,50).toArray()).setType(__inject__(MessageRenderTypeGrowl.class)).execute();
	}
	
	/**/
	
	private FacesMessage __instanciateMessage__(Severity severity) {
		return new FacesMessage(severity, severity.toString()+" summary", "You have details "+severity+" message");
	}
	
	private Collection<FacesMessage> __instanciateMessages__(Severity severity,Integer count) {
		Collection<FacesMessage> facesMessages = new ArrayList<>();
		Severity localSeverity = null;
		for(Integer index = 0 ; index < count ; index++) {
			if(severity == null) {
				Integer integer = __inject__(RandomHelper.class).getNumeric(1).intValue();
				if(integer == 0 || integer == 2|| integer == 8)
					localSeverity = FacesMessage.SEVERITY_INFO;
				else if(integer == 1 || integer == 3)
					localSeverity = FacesMessage.SEVERITY_WARN;
				else if(integer == 4 || integer == 6 || integer == 9)
					localSeverity = FacesMessage.SEVERITY_ERROR;
				else if(integer == 5 || integer == 7)
					localSeverity = FacesMessage.SEVERITY_FATAL;	
			}else
				localSeverity = severity;
			facesMessages.add(new FacesMessage(localSeverity, __inject__(RandomHelper.class).getAlphabetic(150), __inject__(RandomHelper.class).getAlphabetic(50)));
		}
		return facesMessages;
	}
}
