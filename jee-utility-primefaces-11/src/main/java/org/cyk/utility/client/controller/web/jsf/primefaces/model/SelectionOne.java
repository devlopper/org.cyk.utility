package org.cyk.utility.client.controller.web.jsf.primefaces.model;

import java.io.Serializable;

import org.cyk.utility.__kernel__.properties.Properties;

public class SelectionOne<T> extends org.cyk.utility.__kernel__.instance.SelectionOne<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	public SelectionOne() {
		super();
	}

	public SelectionOne(Class<T> choiceClass, Properties properties) {
		super(choiceClass, properties);
	}

	public SelectionOne(Class<T> choiceClass) {
		super(choiceClass);
	}
	
}
