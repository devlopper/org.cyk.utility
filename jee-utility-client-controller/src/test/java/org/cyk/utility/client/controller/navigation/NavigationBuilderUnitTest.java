package org.cyk.utility.client.controller.navigation;

import java.io.Serializable;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.__kernel__.function.FunctionRunnableMap;
import org.cyk.utility.client.controller.ApplicationScopeLifeCycleListener;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionList;
import org.cyk.utility.system.action.SystemActionProcess;
import org.cyk.utility.system.action.SystemActionSelect;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class NavigationBuilderUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	static {
		__inject__(FunctionRunnableMap.class).set(NavigationIdentifierToUrlStringMapperImpl.class, NavigationIdentifierToUrlStringMapperFunctionRunnableImpl.class,Boolean.TRUE);
	}
	
	@Override
	protected void __listenBeforeCallCountIsZero__() throws Exception {
		super.__listenBeforeCallCountIsZero__();
		__inject__(ApplicationScopeLifeCycleListener.class).initialize(null);
	}
	
	@Test
	public void identifier_list_class_myentity_action_list_asString() {
		NavigationBuilder builder = __inject__(NavigationBuilder.class).setIdentifier("listView").setParameters("class","myentity","action","list");
		Navigation navigation = builder.execute().getOutput();
		assertionHelper.assertEquals("http://localhost:8080/list.jsf?class=myentity&action=list",navigation.getUniformResourceLocator().toString());
	}
	
	@Test
	public void myentity_identifier_list_asObject() {
		SystemAction list = __inject__(SystemActionList.class);
		list.getEntities(Boolean.TRUE).setElementClass(MyEntity.class);
		NavigationBuilder builder = __inject__(NavigationBuilder.class).setIdentifierBuilderSystemAction(list);
		Navigation navigation = builder.execute().getOutput();
		assertionHelper.assertEquals("http://localhost:8080/myentity/list.jsf?entityclass=myentity&actionclass=list&actionidentifier=list",navigation.getUniformResourceLocator().toString());
	}
	
	@Test
	public void myentity02_identifier_list_asObject() {
		SystemAction list = __inject__(SystemActionList.class);
		list.getEntities(Boolean.TRUE).setElementClass(MyEntity02.class);
		NavigationBuilder builder = __inject__(NavigationBuilder.class).setIdentifierBuilderSystemAction(list);
		Navigation navigation = builder.execute().getOutput();
		assertionHelper.assertEquals("http://localhost:8080/list.jsf?entityclass=myentity02&actionclass=list&actionidentifier=list",navigation.getUniformResourceLocator().toString());
	}
	
	@Test
	public void myentity_select() {
		SystemAction select = __inject__(SystemActionSelect.class);
		select.getEntities(Boolean.TRUE).setElementClass(MyEntity.class);
		NavigationBuilder builder = __inject__(NavigationBuilder.class).setIdentifierBuilderSystemAction(select);
		Navigation navigation = builder.execute().getOutput();
		assertionHelper.assertEquals("http://localhost:8080/select.jsf?entityclass=myentity&actionclass=select&actionidentifier=select",navigation.getUniformResourceLocator().toString());
	}
	
	@Test
	public void myentity_select_process() {
		SystemAction select = __inject__(SystemActionSelect.class);
		select.getEntities(Boolean.TRUE).setElementClass(MyEntity.class);
		select.setNextAction(__inject__(SystemActionProcess.class));
		NavigationBuilder builder = __inject__(NavigationBuilder.class).setIdentifierBuilderSystemAction(select);
		Navigation navigation = builder.execute().getOutput();
		assertionHelper.assertEquals("http://localhost:8080/select.jsf?entityclass=myentity&actionclass=select&actionidentifier=select&nextactionclass=process&nextactionidentifier=process",navigation.getUniformResourceLocator().toString());
	}
	
	@Test
	public void myentity_select_process_validate() {
		SystemAction select = __inject__(SystemActionSelect.class);
		select.getEntities(Boolean.TRUE).setElementClass(MyEntity.class);
		select.setNextAction(__inject__(SystemActionProcess.class).setIdentifier("validate"));
		NavigationBuilder builder = __inject__(NavigationBuilder.class).setIdentifierBuilderSystemAction(select);
		Navigation navigation = builder.execute().getOutput();
		assertionHelper.assertEquals("http://localhost:8080/select.jsf?entityclass=myentity&actionclass=select&actionidentifier=select&nextactionclass=process&nextactionidentifier=validate",navigation.getUniformResourceLocator().toString());
	}
	
	/*
	@Test
	public void identifier_list_asSystemAction_class_myentity_action_list() {
		SystemAction list = __inject__(SystemActionList.class);
		list.getEntities(Boolean.TRUE).setElementClass(MyEntity.class);
		NavigationBuilder builder = __inject__(NavigationBuilder.class).setIdentifierBuilderSystemAction(list).setParameters("class","myentity","action","list");
		Navigation navigation = builder.execute().getOutput();
		assertionHelper.assertEquals("http://localhost:8080/myentity/list.jsf?class=myentity&action=list",navigation.getUniformResourceLocator().toString());
	}
	
	@Test
	public void identifier_list_asSystemAction() {
		SystemAction list = __inject__(SystemActionList.class);
		list.getEntities(Boolean.TRUE).setElementClass(MyEntity.class);
		NavigationBuilder builder = __inject__(NavigationBuilder.class).setIdentifierBuilderSystemAction(list).setParameters("class","myentity","action","list");
		Navigation navigation = builder.execute().getOutput();
		assertionHelper.assertEquals("http://localhost:8080/myentity/list.jsf?class=myentity&action=list",navigation.getUniformResourceLocator().toString());
	}
	*/
	
	/**/
	
	private static interface MyEntity {
		
	}
	
	private static interface MyEntity02 {
		
	}
	
	public static class NavigationIdentifierToUrlStringMapperFunctionRunnableImpl extends AbstractFunctionRunnableImpl<NavigationIdentifierToUrlStringMapper> implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public NavigationIdentifierToUrlStringMapperFunctionRunnableImpl() {
			setRunnable(new Runnable() {
				@Override
				public void run() {
					Object identifier = getFunction().getIdentifier();
					if("__entity__EditView".equals(identifier))
						setOutput("http://localhost:8080/edit.jsf");
					else if("__entity__ListView".equals(identifier))
						setOutput("http://localhost:8080/list.jsf");
					else if("__entity__SelectView".equals(identifier))
						setOutput("http://localhost:8080/select.jsf");
					else if("__entity__ProcessView".equals(identifier))
						setOutput("http://localhost:8080/process.jsf");
					else if("listView".equals(identifier))
						setOutput("http://localhost:8080/list.jsf");
					else if("myEntityListView".equals(identifier))
						setOutput("http://localhost:8080/myentity/list.jsf");
					
				}
			});
		}
		
	}
	
}
