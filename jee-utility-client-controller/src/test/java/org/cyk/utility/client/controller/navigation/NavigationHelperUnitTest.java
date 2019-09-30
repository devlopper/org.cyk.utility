package org.cyk.utility.client.controller.navigation;

import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.__kernel__.system.action.SystemActionCreate;
import org.cyk.utility.__kernel__.system.action.SystemActionDelete;
import org.cyk.utility.__kernel__.system.action.SystemActionList;
import org.cyk.utility.__kernel__.system.action.SystemActionTree;
import org.cyk.utility.__kernel__.system.action.SystemActionUpdate;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.cyk.utility.client.controller.navigation.NavigationHelperImpl.__buildIdentifier__;

public class NavigationHelperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void buildIdentifier_is_myEntityCreateView() {
		SystemAction systemAction = __inject__(SystemActionCreate.class);
		systemAction.getEntities(Boolean.TRUE).setElementClass(MyEntity.class);
		assertThat(__buildIdentifier__(systemAction)).isEqualTo("myEntityEditView");		
	}
	
	@Test
	public void buildIdentifier_is_createView() {
		SystemAction systemAction = __inject__(SystemActionCreate.class);
		assertThat(__buildIdentifier__(systemAction)).isEqualTo("__entity__EditView");
	}
	
	@Test
	public void buildIdentifier_is_myEntityUpdateView() {
		SystemAction systemAction = __inject__(SystemActionUpdate.class);
		systemAction.getEntities(Boolean.TRUE).setElementClass(MyEntity.class);
		assertThat(__buildIdentifier__(systemAction)).isEqualTo("myEntityEditView");
	}
	
	@Test
	public void buildIdentifier_is_updateView() {
		SystemAction systemAction = __inject__(SystemActionUpdate.class);
		assertThat(__buildIdentifier__(systemAction)).isEqualTo("__entity__EditView");
	}
	
	@Test
	public void buildIdentifier_is_myEntityDeleteView() {
		SystemAction systemAction = __inject__(SystemActionDelete.class);
		systemAction.getEntities(Boolean.TRUE).setElementClass(MyEntity.class);
		assertThat(__buildIdentifier__(systemAction)).isEqualTo("myEntityEditView");
	}
	
	@Test
	public void buildIdentifier_is_deleteView() {
		SystemAction systemAction = __inject__(SystemActionDelete.class);
		assertThat(__buildIdentifier__(systemAction)).isEqualTo("__entity__EditView");
	}
	
	@Test
	public void buildIdentifier_is_myEntityListView() {
		SystemAction systemAction = __inject__(SystemActionList.class);
		systemAction.getEntities(Boolean.TRUE).setElementClass(MyEntity.class);
		assertThat(__buildIdentifier__(systemAction)).isEqualTo("myEntityListView");
	}
	
	@Test
	public void buildIdentifier_is_listView() {
		SystemAction systemAction = __inject__(SystemActionList.class);
		assertThat(__buildIdentifier__(systemAction)).isEqualTo("__entity__ListView");
	}
	
	@Test
	public void buildIdentifier_is_treeView() {
		SystemAction systemAction = __inject__(SystemActionTree.class);
		assertThat(__buildIdentifier__(systemAction)).isEqualTo("__entity__TreeView");
	}
	
	/**/
	
	private static interface MyEntity {
		
	}
	
}
