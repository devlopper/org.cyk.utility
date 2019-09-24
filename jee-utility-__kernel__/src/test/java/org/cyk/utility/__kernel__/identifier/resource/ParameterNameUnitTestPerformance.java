package org.cyk.utility.__kernel__.identifier.resource;

import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTestPerformance;
import org.junit.jupiter.api.Test;

public class ParameterNameUnitTestPerformance extends AbstractWeldUnitTestPerformance {
	private static final long serialVersionUID = 1L;

	@Test
	public void stringify_1000000(){
		execute("build parameter name from string",1000000,500,new Runnable() {
			@Override
			public void run() {
				ParameterName.stringify("p01");
			}
		});
		
		execute("build parameter name from enum",1000000,500,new Runnable() {
			@Override
			public void run() {
				ParameterName.stringify(ParameterName.ACTION_CLASS);
			}
		});
		
		execute("build parameter name from class",1000000,500,new Runnable() {
			@Override
			public void run() {
				ParameterName.stringify(Class.class);
			}
		});
		
		execute("build parameter name from system action",1000000,500,new Runnable() {
			@Override
			public void run() {
				ParameterName.stringify(MyClass.class);
			}
		});
	}
	
	/**/
	
	public static enum MyEnum {
		V01
		;
	}
	
	public static class MyClass {
		
	}
}
