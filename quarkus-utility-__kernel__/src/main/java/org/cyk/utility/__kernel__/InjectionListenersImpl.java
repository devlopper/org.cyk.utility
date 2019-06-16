package org.cyk.utility.__kernel__;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.inject.Singleton;

@Singleton
public class InjectionListenersImpl implements InjectionListeners,Serializable {
	private static final long serialVersionUID = 1L;

	private Collection<InjectionListener> listeners;
	
	@Override
	public InjectionListeners addListener(Collection<InjectionListener> listeners) {
		if(listeners!=null) {
			if(this.listeners == null)
				this.listeners = new ArrayList<>();
			this.listeners.addAll(listeners);
		}
		return this;
	}
	
	@Override
	public InjectionListeners addListener(InjectionListener... listeners) {
		if(listeners!=null)
			addListener(Arrays.asList(listeners));
		return this;
	}
	
	@Override
	public void execute(Object object) {
		if(listeners!=null)
			for(InjectionListener index : listeners)
				index.execute(object);
		
	}

}
