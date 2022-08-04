package org.cyk.utility.client.controller.web.jsf.primefaces.controlpanel;
import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.menu.Menu;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class ControlPanelIndexPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Object __getMenuBuilderMapKey__() {
		return Menu.CONTROL_PANEL;
	}
	
	@Override
	protected String __getWindowTitleValue__() {
		return "Panneau de contrôle";
	}
}
