package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;

import javax.inject.Named;
import javax.inject.Singleton;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

@Singleton @Named
public class PrimefacesHelper extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	public String getScriptInstructionHide(String widgetVar) {
		return String.format(SCRIPT_INSTRUCTION_COMPONENT_METHOD_CALL_FORMAT, widgetVar,"hide");
	}

	/**/
	
	private static final String SCRIPT_INSTRUCTION_COMPONENT_METHOD_CALL_FORMAT = "PF('%s').%s();";
}
