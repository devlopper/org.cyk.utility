package org.cyk.utility.client.controller.web.jsf.primefaces.error;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;

import lombok.Getter;
import lombok.Setter;

@Named @RequestScoped @Getter @Setter
public class __ViewExpired__Page extends AbstractPageContainerManagedImpl implements Serializable {

	@Override
	protected String __getWindowTitleValue__() {
		return "Vue expirée";
	}	
}