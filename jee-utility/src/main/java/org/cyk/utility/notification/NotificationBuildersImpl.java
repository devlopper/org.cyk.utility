package org.cyk.utility.notification;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.collection.AbstractCollectionInstanceImpl;

@Dependent
public class NotificationBuildersImpl extends AbstractCollectionInstanceImpl<NotificationBuilder> implements NotificationBuilders,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public NotificationBuilders add(NotificationBuilders notificationBuilders) {
		if(notificationBuilders!=null)
			add(notificationBuilders.get());
		return this;
	}
	
}
