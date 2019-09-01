package org.cyk.utility.identifier.resource;

import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.test.weld.AbstractWeldUnitTestPerformance;
import org.junit.jupiter.api.Test;

public class UniformResourceIdentifierParameterValueStringBuilderUnitTestPerformance extends AbstractWeldUnitTestPerformance {
	private static final long serialVersionUID = 1L;

	@Test
	public void execute_10(){
		execute(10,10);
	}
	
	@Test
	public void execute_100(){
		execute(100,10);
	}
	
	@Test
	public void execute_1000(){
		execute(1000,150);
	}
	
	@Test
	public void execute_10000(){
		execute(10000,600);
	}
	
	@Test
	public void execute_100000(){
		execute(100000,2000);
	}
	
	@Override
	protected void __execute__() {
		__inject__(UniformResourceIdentifierParameterValueStringBuilder.class).setValue(SystemActionCreate.class).execute();
	}
	
}
