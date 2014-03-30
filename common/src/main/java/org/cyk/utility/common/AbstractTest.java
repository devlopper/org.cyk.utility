package org.cyk.utility.common;

import org.junit.After;
import org.junit.Before;

public abstract class AbstractTest {
	
	@Before
	public final void before() throws Exception {
		_before_();
	}
	
	protected void _before_() throws Exception{
		
	}
	
	@After
	public final void after() throws Exception {
		_after_();
	}
	
	protected void _after_() throws Exception {
		
	}
	
	
}
