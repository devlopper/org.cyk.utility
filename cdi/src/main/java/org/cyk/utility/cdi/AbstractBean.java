package org.cyk.utility.cdi;

import java.io.Serializable;

import javax.annotation.PostConstruct;

public class AbstractBean implements Serializable {

	private static final long serialVersionUID = -2448439169984218703L;

	@PostConstruct
	private void postConstruct(){
		initialisation();
	}
	
	protected void initialisation(){}

}
