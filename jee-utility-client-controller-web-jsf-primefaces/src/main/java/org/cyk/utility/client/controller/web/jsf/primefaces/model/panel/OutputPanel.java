package org.cyk.utility.client.controller.web.jsf.primefaces.model.panel;

import java.io.Serializable;

import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractObject;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class OutputPanel extends AbstractObject implements Serializable {

	
	/**/
	
	public static abstract class AbstractConfiguratorImpl<OBJECT extends OutputPanel> extends org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractObject.AbstractConfiguratorImpl<OBJECT> implements Serializable {

	}
	
	public static class ConfiguratorImpl extends AbstractConfiguratorImpl<OutputPanel> implements Serializable {

	}
}
