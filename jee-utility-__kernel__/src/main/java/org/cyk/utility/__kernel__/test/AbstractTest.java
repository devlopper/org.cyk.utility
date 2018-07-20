package org.cyk.utility.__kernel__.test;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.junit.Before;

public abstract class AbstractTest extends AbstractObject implements Serializable {
	private static final long serialVersionUID = -4375668358714913342L;
	
	@Before
	public void listenBefore() {
		__listenBefore__();
	}
	
	protected void __listenBefore__(){}
	
}
