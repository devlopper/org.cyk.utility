package org.cyk.utility.client.controller.navigation;

import org.cyk.utility.client.controller.ApplicationScopeLifeCycleListener;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.__kernel__.system.action.SystemActionCreate;
import org.cyk.utility.__kernel__.system.action.SystemActionDelete;
import org.cyk.utility.__kernel__.system.action.SystemActionList;
import org.cyk.utility.__kernel__.system.action.SystemActionTree;
import org.cyk.utility.__kernel__.system.action.SystemActionUpdate;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class NavigationIdentifierStringBuilderUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBeforeCallCountIsZero__() throws Exception {
		super.__listenBeforeCallCountIsZero__();
		__inject__(ApplicationScopeLifeCycleListener.class).initialize(null);
	}
	
	@Test
	public void is_myEntityCreateView() {
		SystemAction systemAction = __inject__(SystemActionCreate.class);
		systemAction.getEntities(Boolean.TRUE).setElementClass(MyEntity.class);
		String identifier = __inject__(NavigationIdentifierStringBuilder.class).setSystemAction(systemAction).execute().getOutput();
		assertionHelper.assertEquals("myEntityEditView",identifier);
	}
	
	@Test
	public void is_createView() {
		SystemAction systemAction = __inject__(SystemActionCreate.class);
		String identifier = __inject__(NavigationIdentifierStringBuilder.class).setSystemAction(systemAction).execute().getOutput();
		assertionHelper.assertEquals("__entity__EditView",identifier);
	}
	
	@Test
	public void is_myEntityUpdateView() {
		SystemAction systemAction = __inject__(SystemActionUpdate.class);
		systemAction.getEntities(Boolean.TRUE).setElementClass(MyEntity.class);
		String identifier = __inject__(NavigationIdentifierStringBuilder.class).setSystemAction(systemAction).execute().getOutput();
		assertionHelper.assertEquals("myEntityEditView",identifier);
	}
	
	@Test
	public void is_updateView() {
		SystemAction systemAction = __inject__(SystemActionUpdate.class);
		String identifier = __inject__(NavigationIdentifierStringBuilder.class).setSystemAction(systemAction).execute().getOutput();
		assertionHelper.assertEquals("__entity__EditView",identifier);
	}
	
	@Test
	public void is_myEntityDeleteView() {
		SystemAction systemAction = __inject__(SystemActionDelete.class);
		systemAction.getEntities(Boolean.TRUE).setElementClass(MyEntity.class);
		String identifier = __inject__(NavigationIdentifierStringBuilder.class).setSystemAction(systemAction).execute().getOutput();
		assertionHelper.assertEquals("myEntityEditView",identifier);
	}
	
	@Test
	public void is_deleteView() {
		SystemAction systemAction = __inject__(SystemActionDelete.class);
		String identifier = __inject__(NavigationIdentifierStringBuilder.class).setSystemAction(systemAction).execute().getOutput();
		assertionHelper.assertEquals("__entity__EditView",identifier);
	}
	
	@Test
	public void is_myEntityListView() {
		SystemAction systemAction = __inject__(SystemActionList.class);
		systemAction.getEntities(Boolean.TRUE).setElementClass(MyEntity.class);
		String identifier = __inject__(NavigationIdentifierStringBuilder.class).setSystemAction(systemAction).execute().getOutput();
		assertionHelper.assertEquals("myEntityListView",identifier);
	}
	
	@Test
	public void is_listView() {
		SystemAction systemAction = __inject__(SystemActionList.class);
		String identifier = __inject__(NavigationIdentifierStringBuilder.class).setSystemAction(systemAction).execute().getOutput();
		assertionHelper.assertEquals("__entity__ListView",identifier);
	}
	
	@Test
	public void is_treeView() {
		SystemAction systemAction = __inject__(SystemActionTree.class);
		String identifier = __inject__(NavigationIdentifierStringBuilder.class).setSystemAction(systemAction).execute().getOutput();
		assertionHelper.assertEquals("__entity__TreeView",identifier);
	}
	
	/**/
	
	private static interface MyEntity {
		
	}
	
}
