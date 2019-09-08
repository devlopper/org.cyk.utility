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
		execute("Build navigation identifier string",10,10,new Runnable() {
			@Override
			public void run() {
				SystemAction systemAction = __inject__(SystemActionCreate.class);
				systemAction.getEntities(Boolean.TRUE).setElementClass(MyEntity.class);
				__inject__(NavigationIdentifierStringBuilder.class).setSystemAction(systemAction).execute();
			}
		});
	}	
	
	@Test
	public void execute_100(){
		execute("Build navigation identifier string",100,50,new Runnable() {
			@Override
			public void run() {
				SystemAction systemAction = __inject__(SystemActionCreate.class);
				systemAction.getEntities(Boolean.TRUE).setElementClass(MyEntity.class);
				__inject__(NavigationIdentifierStringBuilder.class).setSystemAction(systemAction).execute();
			}
		});
	}	
	
	@Test
	public void execute_1000(){
		execute("Build navigation identifier string",1000,450,new Runnable() {
			@Override
			public void run() {
				SystemAction systemAction = __inject__(SystemActionCreate.class);
				systemAction.getEntities(Boolean.TRUE).setElementClass(MyEntity.class);
				__inject__(NavigationIdentifierStringBuilder.class).setSystemAction(systemAction).execute();
			}
		});
	}	
	
	@Test
	public void execute_10000(){
		execute("Build navigation identifier string",10000,1400,new Runnable() {
			@Override
			public void run() {
				SystemAction systemAction = __inject__(SystemActionCreate.class);
				systemAction.getEntities(Boolean.TRUE).setElementClass(MyEntity.class);
				__inject__(NavigationIdentifierStringBuilder.class).setSystemAction(systemAction).execute();
			}
		});
	}	
	
	@Test
	public void execute_100000(){
		execute("Build navigation identifier string",100000,7600,new Runnable() {
			@Override
			public void run() {
				SystemAction systemAction = __inject__(SystemActionCreate.class);
				systemAction.getEntities(Boolean.TRUE).setElementClass(MyEntity.class);
				__inject__(NavigationIdentifierStringBuilder.class).setSystemAction(systemAction).execute();
			}
		});
	}	
	
	/**/
	
	private static interface MyEntity {
		
	}
	
}
