package org.cyk.utility.client.controller.web.jsf.primefaces.error;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class __403__Page extends AbstractPageContainerManagedImpl implements Serializable {

	@Override
	protected String __getWindowTitleValue__() {
		return "Sécurité";
	}	
}