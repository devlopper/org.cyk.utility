package org.cyk.utility.playground.client.controller.api;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.AbstractControllerEntityImpl;
import org.cyk.utility.playground.client.controller.entities.Namable;

@ApplicationScoped @Named(value = "namableController")
public class NamableControllerImpl extends AbstractControllerEntityImpl<Namable> implements NamableController,Serializable {
	private static final long serialVersionUID = 1L;
	
}
