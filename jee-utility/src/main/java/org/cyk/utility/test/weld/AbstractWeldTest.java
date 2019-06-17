package org.cyk.utility.test.weld;

import java.io.Serializable;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldSetup;

@org.jboss.weld.junit5.EnableWeld
public abstract class AbstractWeldTest extends org.cyk.utility.test.AbstractTest implements Serializable {
	private static final long serialVersionUID = 1L;

	@WeldSetup
    protected WeldInitiator weldInitiator = __getWeldInitiator__();
	
	protected WeldInitiator __getWeldInitiator__() {
		Weld weld = __getWeld__();
		if(weld == null)
			weld = new Weld();
		return WeldInitiator.of(weld);
	}
	
	protected Weld __getWeld__() {
		return new Weld();
	}
	
}
