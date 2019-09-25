package org.cyk.utility.identifier.resource;

import org.cyk.utility.__kernel__.system.action.SystemActionCreate;
import org.cyk.utility.test.weld.AbstractWeldUnitTestPerformance;
import org.junit.jupiter.api.Test;

public class UniformResourceIdentifierParameterValueStringBuilderUnitTestPerformance extends AbstractWeldUnitTestPerformance {
	private static final long serialVersionUID = 1L;

	@Test
	public void execute_10(){
		execute("uri parameter value string builder",10,10,new Runnable() {
			@Override
			public void run() {
				__inject__(UniformResourceIdentifierParameterValueStringBuilder.class).setValue(SystemActionCreate.class).execute();
			}
		});
	}
	
	@Test
	public void execute_100(){
		execute("uri parameter value string builder",100,20,new Runnable() {
			@Override
			public void run() {
				__inject__(UniformResourceIdentifierParameterValueStringBuilder.class).setValue(SystemActionCreate.class).execute();
			}
		});
	}
	
	@Test
	public void execute_1000(){
		execute("uri parameter value string builder",1000,80,new Runnable() {
			@Override
			public void run() {
				__inject__(UniformResourceIdentifierParameterValueStringBuilder.class).setValue(SystemActionCreate.class).execute();
			}
		});
	}
	
	@Test
	public void execute_10000(){
		execute("uri parameter value string builder",10000,160,new Runnable() {
			@Override
			public void run() {
				__inject__(UniformResourceIdentifierParameterValueStringBuilder.class).setValue(SystemActionCreate.class).execute();
			}
		});
	}
	
	@Test
	public void execute_100000(){
		execute("uri parameter value string builder",100000,1100,new Runnable() {
			@Override
			public void run() {
				__inject__(UniformResourceIdentifierParameterValueStringBuilder.class).setValue(SystemActionCreate.class).execute();
			}
		});
	}

}
