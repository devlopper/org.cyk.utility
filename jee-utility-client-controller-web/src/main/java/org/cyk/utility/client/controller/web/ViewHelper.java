package org.cyk.utility.client.controller.web;

import java.io.Serializable;

import javax.inject.Named;
import javax.inject.Singleton;

import org.cyk.utility.__kernel__.object.dynamic.AbstractSingleton;
import org.cyk.utility.random.RandomHelper;

@Singleton @Named
public class ViewHelper extends AbstractSingleton implements Serializable {
	private static final long serialVersionUID = 1L;

	public RandomHelper getRandomHelper() {
		return __inject__(RandomHelper.class);
	}
	
}
