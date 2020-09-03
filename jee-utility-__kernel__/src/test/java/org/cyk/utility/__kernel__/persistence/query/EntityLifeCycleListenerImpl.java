package org.cyk.utility.__kernel__.persistence.query;

import java.io.Serializable;

import org.cyk.utility.__kernel__.annotation.Test;
import org.cyk.utility.__kernel__.object.__static__.persistence.EntityLifeCycleListener;

@Test
public class EntityLifeCycleListenerImpl extends EntityLifeCycleListener.AbstractImpl implements Serializable {

	@Override
	public void listen(Object object, Event event, When when) {
		super.listen(object, event, when);
		System.out.println("EntityLifeCycleListenerImpl.listen() : "+object+" - "+event+" - "+when);
	}
	
}
