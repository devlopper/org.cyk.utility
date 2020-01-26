package org.cyk.utility.playground.client.controller.component.command;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.__kernel__.user.interface_.message.Message;
import org.cyk.utility.__kernel__.user.interface_.message.MessageRenderer;
import org.cyk.utility.__kernel__.user.interface_.message.RenderType;
import org.cyk.utility.__kernel__.user.interface_.message.Severity;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class MessageRendererPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String __getWindowTitleValue__() {
		return "Message Renderer Page";
	}
	
	public void renderInformationInline() {
		MessageRenderer.getInstance().render(new Message().setSummary("Info Summary").setDetails("Info Details").setSeverity(Severity.INFORMATION), RenderType.INLINE);
	}
	
	public void renderInformationDialog() {
		MessageRenderer.getInstance().render(new Message().setSummary("Info Summary").setDetails("Info Details").setSeverity(Severity.INFORMATION), RenderType.DIALOG);
	}
	
	public void renderInformationGrowl() {
		MessageRenderer.getInstance().render(new Message().setSummary("Info Summary").setDetails("Info Details").setSeverity(Severity.INFORMATION), RenderType.GROWL);
	}
	
	public void renderWarningInline() {
		MessageRenderer.getInstance().render(new Message().setSummary("Info Summary").setDetails("Info Details").setSeverity(Severity.WARNING), RenderType.INLINE);
	}
	
	public void renderErrorInline() {
		MessageRenderer.getInstance().render(new Message().setSummary("Info Summary").setDetails("Info Details").setSeverity(Severity.ERROR), RenderType.INLINE);
	}
	
	public void renderFatalInline() {
		MessageRenderer.getInstance().render(new Message().setSummary("Info Summary").setDetails("Info Details").setSeverity(Severity.FATAL), RenderType.INLINE);
	}
}
