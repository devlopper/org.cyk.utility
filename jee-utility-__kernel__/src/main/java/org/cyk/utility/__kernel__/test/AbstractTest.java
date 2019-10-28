package org.cyk.utility.__kernel__.test;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.ApplicationScopeLifeCycleListener;
import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class AbstractTest extends AbstractObject implements Serializable {
	private static final long serialVersionUID = -4375668358714913342L;
	
	protected static Integer LISTEN_BEFORE_CALL_COUNT = 0;
	
	@BeforeEach 
	public void listenBefore() {
		__listenBefore__();
	}
	
	@AfterEach
	public void listenAfter() {
		__listenAfter__();
	}
	
	protected void __listenBefore__(){
		__inject__(ApplicationScopeLifeCycleListener.class).initialize(null);
		try {
			__listenBeforeCallCountIs__(LISTEN_BEFORE_CALL_COUNT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LISTEN_BEFORE_CALL_COUNT++;
	}
	
	protected void __listenBeforeCallCountIs__(Integer count) throws Exception{
		if(count != null && count == 0)
			__listenBeforeCallCountIsZero__();
			
	}
	
	protected void __listenBeforeCallCountIsZero__() throws Exception{}
	
	protected void __listenBeforeInitialiseProperties__(){
		
	}
	
	protected void __listenAfter__(){
		
	}
	
	protected static String __getPackageSystemPropertyName__(Package aPackage,String suffix) {
		return aPackage.getName()+".test"+( StringUtils.isNotBlank(suffix) ? "."+suffix : "" );
	}
	
	protected static Boolean __isSkippable__(Package aPackage,Integer stackTraceElementOffset){
		Boolean isSkippable = null;
		if(aPackage!=null && "true".equalsIgnoreCase(__getPackageSystemPropertyName__(aPackage, "skip"))) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			Integer index = 2 + stackTraceElementOffset;
			logMethodHasBeenSkipped(stackTraceElements[index]);
			isSkippable = Boolean.TRUE;
		}
		return isSkippable;
	}
	
	protected static Boolean __isRunnable__(Package aPackage,Integer stackTraceElementOffset){
		Boolean result = null;
		Integer index = 2 + stackTraceElementOffset;
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		if(aPackage!=null && "true".equalsIgnoreCase(__getPackageSystemPropertyName__(aPackage, "run"))) {
			result = Boolean.TRUE;
		}else {
			logMethodHasBeenSkipped(stackTraceElements[index]);
		}
		return result;
	}
	
	protected static void logMethodHasBeenSkipped(StackTraceElement stackTraceElement) {
		System.out.println(stackTraceElement.getClassName()+"."+stackTraceElement.getMethodName()+" skipped");
	}
	
	protected static Boolean __isSkippable__(Class<?> aClass){
		return aClass == null ? Boolean.TRUE : __isSkippable__(aClass.getPackage(),1);
	}
	
	protected static Boolean __isRunnable__(Class<?> aClass){
		return aClass == null ? Boolean.FALSE : __isRunnable__(aClass.getPackage(),1);
	}

	/**/
	
	
	/**/
}
