package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.FacesContext;

import org.cyk.utility.client.controller.navigation.NavigationRedirector;
import org.cyk.utility.client.controller.web.jsf.AbstractFunctionRunnableImpl;

public class NavigationRedirectorFunctionRunnableImpl extends AbstractFunctionRunnableImpl<NavigationRedirector> implements Serializable {
	private static final long serialVersionUID = 1L;

	public NavigationRedirectorFunctionRunnableImpl() {
		setRunnable(new Runnable() { @Override public void run() {
			String url = getFunction().getNavigation().getUniformResourceLocator().toString();
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}});
	}
	
}
