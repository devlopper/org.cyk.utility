package org.cyk.utility.client.controller.web.jsf.primefaces.page;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.__kernel__.configuration.ConfigurationHelper;
import org.cyk.utility.__kernel__.session.SessionHelper;
import org.cyk.utility.__kernel__.variable.VariableName;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class LogoutPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private String url;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		url = ConfigurationHelper.getValueAsString(VariableName.SYSTEM_WEB_HOME_URL);
	}
	
	public void logout() {
		SessionHelper.destroy(SessionHelper.getUserName());
	}
	
	@Override
	protected String __getWindowTitleValue__() {
		return "Déconnexion";
	}
}