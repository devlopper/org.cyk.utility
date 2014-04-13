package org.cyk.utility.common.test;

import java.io.Serializable;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public abstract class AbstractTest implements Serializable {
	
	private static final long serialVersionUID = -4375668358714913342L;
	
	//protected Collection<AbstractTestMethod> methods = new ArrayList<>();
	
	@Before
	public final void before() throws Exception {
		_before_();
		_methods_();
	}
	
	protected void _before_() throws Exception{}
	
	protected void _methods_(){}
	
	@After
	public final void after() throws Exception {
		_after_();
	}
	
	protected void _after_() throws Exception {
		
	}
	
	/**/
	
	@Test
	public final void execute(){
		_execute_();
	}
	
	protected void _execute_(){
		/*for(AbstractTestMethod method : methods)
			method.execute();*/
	}
	
	/**/
	
	
	
}
