package org.cyk.jee.utility.client.controller;

public interface ProxyGetterRestEasy extends ProxyGetter {

	ProxyGetterRestEasy setClazz(Class<?> aClass);
	
	ProxyGetterRestEasy execute(Class<?> aClass);
	
	ProxyGetterRestEasy execute();
}