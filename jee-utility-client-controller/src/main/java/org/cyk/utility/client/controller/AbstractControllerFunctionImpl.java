package org.cyk.utility.client.controller;

import java.io.Serializable;

import org.cyk.utility.system.AbstractSystemFunctionClientImpl;
import org.cyk.utility.system.layer.SystemLayer;
import org.cyk.utility.system.layer.SystemLayerController;

public abstract class AbstractControllerFunctionImpl extends AbstractSystemFunctionClientImpl implements ControllerFunction,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected SystemLayer getSystemLayer() {
		return __inject__(SystemLayerController.class);
	}
	
}
