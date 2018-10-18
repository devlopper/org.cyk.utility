package org.cyk.utility.client.controller.component;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface VisibleComponentsBuilderListener extends Objectable {

	/**/
	
	public static class Adapter extends AbstractObject implements VisibleComponentsBuilderListener,Serializable {
		private static final long serialVersionUID = 1L;
		
	}
}
