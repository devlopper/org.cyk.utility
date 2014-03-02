package org.cyk.utility.common.cdi;

import java.io.Serializable;

import javax.annotation.PostConstruct;

public class AbstractBean implements Serializable {

	private static final long serialVersionUID = -2448439169984218703L;

	@PostConstruct
	private void postConstruct(){
		beforeInitialisation();
		initialisation();
		afterInitialisation();
	}
	
	protected void beforeInitialisation(){}
	
	protected void initialisation(){}

	protected void afterInitialisation(){}
}
