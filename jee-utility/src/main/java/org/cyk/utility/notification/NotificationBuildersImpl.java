package org.cyk.utility.notification;

import java.io.Serializable;

import org.cyk.utility.collection.AbstractCollectionInstanceImpl;

public class NotificationBuildersImpl extends AbstractCollectionInstanceImpl<NotificationBuilder> implements NotificationBuilders,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public NotificationBuilders add(NotificationBuilders notificationBuilders) {
		if(notificationBuilders!=null)
			add(notificationBuilders.get());
		return this;
	}
	
}
