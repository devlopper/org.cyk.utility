package org.cyk.utility.notification;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.collection.AbstractCollectionInstanceImpl;

@Dependent
public class NotificationsImpl extends AbstractCollectionInstanceImpl<Notification> implements Notifications,Serializable {
	private static final long serialVersionUID = 1L;

}
