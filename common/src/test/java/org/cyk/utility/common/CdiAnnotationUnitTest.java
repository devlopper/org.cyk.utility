package org.cyk.utility.common;

import java.util.Collection;

import org.cyk.utility.common.cdi.annotation.Log;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;
import org.mockito.InjectMocks;


public class CdiAnnotationUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@InjectMocks private LogAnnotation logAnnotation;
	
	@Override
	protected void registerBeans(Collection<Object> collection) {
		super.registerBeans(collection);
		collection.add(logAnnotation);
	}
	
	@Test
	public void log(){
		logAnnotation.f1();
	}
	
	/**/
	
	@Log
	public static class LogAnnotation {
		
		public void f1(){
			System.out.println("CdiAnnotationUT.LogAnnotation.f1()");
		}
		
	}
}
