package org.cyk.utility.__kernel__;

import java.util.Collection;

public interface InjectionListeners {

	InjectionListeners addListener(Collection<InjectionListener> listeners);
	InjectionListeners addListener(InjectionListener...listeners);
	
	void execute(Object object);
	
}
