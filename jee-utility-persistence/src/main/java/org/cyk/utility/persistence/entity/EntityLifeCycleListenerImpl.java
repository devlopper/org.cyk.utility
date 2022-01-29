package org.cyk.utility.persistence.entity;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EntityLifeCycleListenerImpl extends EntityLifeCycleListener.AbstractImpl implements Serializable {

	public static void useFrenchValues() {
		EntityLifeCycleListenerImpl.SYSTEM_USER_NAME = "SYSTEME";
		EntityLifeCycleListenerImpl.DEFAULT_USER_NAME = "INCONNUE";
		EntityLifeCycleListenerImpl.DEFAULT_ACTION = "INCONNUE";
		EntityLifeCycleListenerImpl.DEFAULT_FUNCTIONALITY = "INCONNUE";
		EntityLifeCycleListener.Event.CREATE.setValue("CREATION");
		EntityLifeCycleListener.Event.READ.setValue("LECTURE");
		EntityLifeCycleListener.Event.UPDATE.setValue("MODIFICATION");
		EntityLifeCycleListener.Event.DELETE.setValue("SUPRESSION");
	}
}