package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;

import org.cyk.utility.client.controller.web.jsf.Redirector;
import org.cyk.utility.client.controller.web.jsf.primefaces.annotation.Primefaces;
import org.primefaces.PrimeFaces;

@Primefaces
public class RedirectorImpl extends Redirector.AbstractImpl implements Serializable {

	@Override
	protected void showProcessing() {
		PrimeFaces.current().executeScript(PrimefacesHelper.formatScriptShowDialogStatus());
	}
	
}