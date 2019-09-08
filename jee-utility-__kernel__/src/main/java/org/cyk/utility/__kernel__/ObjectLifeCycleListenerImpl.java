package org.cyk.utility.__kernel__;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ObjectLifeCycleListenerImpl extends AbstractObjectLifeCycleListenerImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void listen(Object object, Action action, When when) {
		
	}

}
