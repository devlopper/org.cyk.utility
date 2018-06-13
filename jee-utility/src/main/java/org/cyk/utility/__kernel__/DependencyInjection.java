package org.cyk.utility.__kernel__;

import java.io.Serializable;
import java.util.Set;

import javax.enterprise.inject.spi.CDI;

public class DependencyInjection implements Serializable {
	private static final long serialVersionUID = 1L;

	public static <OBJECT> OBJECT inject(Class<OBJECT> aClass){
		if(aClass == null){
			//TODO log a warning
			return null;
		}
		return CDI.current().select(aClass).get();
	}
	
	@SuppressWarnings("unchecked")
	public static <OBJECT> Set<OBJECT> injectAll(Class<OBJECT> aClass){
		if(aClass == null){
			//TODO log a warning
			return null;
		}
		return (Set<OBJECT>) CDI.current().getBeanManager().getBeans(aClass);
	}
	
}
