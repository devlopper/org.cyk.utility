package org.cyk.utility.client.controller.web.jsf.compositecomponent;

import java.io.Serializable;

import javax.faces.component.FacesComponent;

import org.cyk.utility.client.controller.component.command.CommandImpl;

@FacesComponent(value="org.cyk.utility.client.controller.web.jsf.compositecomponent.Command")
public class Command extends AbstractCompositeComponent<CommandImpl> implements Serializable {
	private static final long serialVersionUID = 1L;

}
