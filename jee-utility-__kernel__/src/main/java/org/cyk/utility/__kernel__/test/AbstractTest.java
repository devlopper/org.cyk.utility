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
			try {
				__listenBeforeCallCountIsZero__();
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		LISTEN_BEFORE_CALL_COUNT++;
	}
	
	protected void __listenBefore__(){}
	
	protected void __listenBeforeCallCountIsZero__() throws Exception{}
	
	protected static Boolean __isSkippable__(Package aPackage){
		Boolean isSkippable = null;
		if(aPackage!=null && "true".equalsIgnoreCase(System.getProperty(aPackage.getName()+".test.skip"))) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			System.out.println(stackTraceElements[3].getClassName()+"."+stackTraceElements[3].getMethodName()+" Skipped ");//TODO this can vary if not called first
			isSkippable = Boolean.TRUE;
		}
		return isSkippable;
	}
	
	protected static Boolean __isSkippable__(Class<?> aClass){
		return aClass == null ? Boolean.TRUE : __isSkippable__(aClass.getPackage());
	}
}
