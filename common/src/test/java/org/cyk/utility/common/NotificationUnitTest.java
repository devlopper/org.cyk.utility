package org.cyk.utility.common;

import org.cyk.utility.common.helper.NotificationHelper;
import org.cyk.utility.common.helper.NotificationHelper.Notification;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class NotificationUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;

	static {
		StringHelper.ToStringMapping.Datasource.Adapter.Default.initialize();
	}
	
	@Test
	public void operationSuccess(){
		Notification notification = NotificationHelper.getInstance().getNotification("notification.operation.executed.success");
		assertEquals("Opération exécutée avec succès", notification.getSummary());
		assertEquals("Opération exécutée avec succès", notification.getDetails());
	}
	
	@Test
	public void viewOperationSuccess(){
		Notification notification = NotificationHelper.getInstance().getNotification("notification.operation.executed.success");
		NotificationHelper.getInstance().getViewer().setInput(notification).setType(NotificationHelper.Notification.Viewer.Type.INLINE).execute();
	}
	
}
