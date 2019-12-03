package org.cyk.utility.playground.client.controller.component;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.window.WindowBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class WindowThrowablePage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected WindowBuilder __getWindowBuilder__(List<String> subDurations) {
		throw new RuntimeException("Oups! Une erreur est survenue : Details....");
	}
	
	@Override
	protected String __getWindowTitleValue__() {
		return "Throwable";
	}
	
}
