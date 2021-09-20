package org.cyk.utility.test;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class InjectionUnitTest extends AbstractWeldUnitTest {

	@Override
	protected void __listenBefore__() {
		// TODO Auto-generated method stub
		super.__listenBefore__();
		System.out.println("InjectionUnitTest.__listenBefore__() : "+weldInitiator);
	}
	
	@Override
	protected void __listenAfter__() {
		// TODO Auto-generated method stub
		super.__listenAfter__();
		System.out.println("InjectionUnitTest.__listenAfter__() : "+weldInitiator);
	}
	
	@Test
	public void first() {
		
	}
	
	@Test
	public void second() {
		
	}
	
}