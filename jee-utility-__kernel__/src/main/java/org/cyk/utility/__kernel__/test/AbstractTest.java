package org.cyk.utility.__kernel__.test;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.junit.Before;

public abstract class AbstractTest extends AbstractObject implements Serializable {
	private static final long serialVersionUID = -4375668358714913342L;
	
	protected static Integer LISTEN_BEFORE_CALL_COUNT = 0;
	
	@Before
	public void listenBefore() {
		__listenBefore__();
		
		if(LISTEN_BEFORE_CALL_COUNT == 0)
			__listenBeforeCallCountIsZero__();
		
		LISTEN_BEFORE_CALL_COUNT++;
	}
	
	protected void __listenBefore__(){}
	
	protected void __listenBeforeCallCountIsZero__(){}
}
