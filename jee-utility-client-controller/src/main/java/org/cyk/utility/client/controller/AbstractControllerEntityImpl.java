package org.cyk.utility.client.controller;

import java.io.Serializable;

public abstract class AbstractControllerEntityImpl<ENTITY> extends AbstractControllerServiceProviderImpl<ENTITY> implements ControllerEntity<ENTITY>,Serializable {
	private static final long serialVersionUID = 1L;

}
