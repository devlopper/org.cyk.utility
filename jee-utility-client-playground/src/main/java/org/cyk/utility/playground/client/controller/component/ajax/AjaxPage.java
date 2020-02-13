package org.cyk.utility.playground.client.controller.component.ajax;

import java.io.Serializable;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.user.interface_.message.MessageRenderer;
import org.cyk.utility.__kernel__.user.interface_.message.RenderType;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.ajax.Ajax;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class AjaxPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private Ajax ajaxServerOnClick,ajaxServerOnDoubleClick,ajaxServerOnChange,ajaxServerOnBlur;
	
	@Override
	protected String __getWindowTitleValue__() {
		return "Ajax Page";
	}
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		ajaxServerOnClick = Builder.build(Ajax.class,Map.of(Ajax.FIELD_LISTENER,new Ajax.Listener() {
			@Override
			public void listenAction(Object argument) {
				MessageRenderer.getInstance().render("You have click", RenderType.GROWL);
			}
		}));
		ajaxServerOnClick.getRunnerArguments().setSuccessMessageArguments(null);
		ajaxServerOnClick.setDisabled(Boolean.FALSE);
		
		ajaxServerOnDoubleClick = Builder.build(Ajax.class,Map.of(Ajax.FIELD_EVENT,"dblclick",Ajax.FIELD_LISTENER,new Ajax.Listener() {
			@Override
			public void listenAction(Object argument) {
				MessageRenderer.getInstance().render("You have double click", RenderType.GROWL);
			}
		}));
		ajaxServerOnDoubleClick.getRunnerArguments().setSuccessMessageArguments(null);
		ajaxServerOnDoubleClick.setDisabled(Boolean.FALSE);
		
		ajaxServerOnChange = Builder.build(Ajax.class,Map.of(Ajax.FIELD_EVENT,"change",Ajax.FIELD_LISTENER,new Ajax.Listener() {
			@Override
			public void listenAction(Object argument) {
				MessageRenderer.getInstance().render("You have change", RenderType.GROWL);
			}
		}));
		ajaxServerOnChange.getRunnerArguments().setSuccessMessageArguments(null);
		ajaxServerOnChange.setDisabled(Boolean.FALSE);
		
		ajaxServerOnBlur = Builder.build(Ajax.class,Map.of(Ajax.FIELD_EVENT,"blur",Ajax.FIELD_LISTENER,new Ajax.Listener() {
			@Override
			public void listenAction(Object argument) {
				MessageRenderer.getInstance().render("You have blur", RenderType.GROWL);
			}
		}));
		ajaxServerOnBlur.getRunnerArguments().setSuccessMessageArguments(null);
		ajaxServerOnBlur.setDisabled(Boolean.FALSE);
	}
	
}
