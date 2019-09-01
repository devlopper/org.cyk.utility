package org.cyk.utility.client.controller.navigation;

import org.cyk.utility.client.controller.ApplicationScopeLifeCycleListener;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.test.weld.AbstractWeldUnitTestPerformance;
import org.junit.jupiter.api.Test;

public class NavigationIdentifierStringBuilderUnitTestPerformance extends AbstractWeldUnitTestPerformance {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBeforeCallCountIsZero__() throws Exception {
		super.__listenBeforeCallCountIsZero__();
		__inject__(ApplicationScopeLifeCycleListener.class).initialize(null);
	}
	
	@Test
	public void execute_10(){
		execute(10,100);
	}	
	
	@Test
	public void execute_100(){
		execute(100,200);
	}	
	
	@Test
	public void execute_1000(){
		execute(1000,400);
	}	
	
	@Test
	public void execute_10000(){
		execute(10000,2000);
	}	
	
	@Test
	public void execute_100000(){
		execute(100000,12000);
	}	
	
	
	@Override
	protected void __execute__() {
		SystemAction systemAction = __inject__(SystemActionCreate.class);
		systemAction.getEntities(Boolean.TRUE).setElementClass(MyEntity.class);
		__inject__(NavigationIdentifierStringBuilder.class).setSystemAction(systemAction).execute();
	}
	
	/**/
	
	private static interface MyEntity {
		
	}
	
}
