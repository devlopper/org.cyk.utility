package org.cyk.utility.__kernel__.object.__static__.persistence;

import java.io.Serializable;

public class EntityLifeCycleListenerImpl extends EntityLifeCycleListener.AbstractImpl implements Serializable {

	public static void useFrenchValues() {
		EntityLifeCycleListenerImpl.DEFAULT_USER_NAME = "ANONYME";
		EntityLifeCycleListener.Event.CREATE.setValue("CREATION");
		EntityLifeCycleListener.Event.READ.setValue("LECTURE");
		EntityLifeCycleListener.Event.UPDATE.setValue("MODIFICATION");
		EntityLifeCycleListener.Event.DELETE.setValue("SUPRESSION");
	}
	
}
