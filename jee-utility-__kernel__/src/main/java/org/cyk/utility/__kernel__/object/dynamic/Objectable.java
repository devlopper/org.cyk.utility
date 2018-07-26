package org.cyk.utility.__kernel__.object.dynamic;

import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;

public interface Objectable extends org.cyk.utility.__kernel__.object.Objectable {

	Properties getProperties();
	Objectable setProperties(Properties properties);
	
	Object getParent();
	<I extends Objectable> I getParentAs(Class<I> aClass);
	Objectable setParent(Object parent,Boolean executeAddChild);
	Objectable setParent(Object parent);
	
	Object getIdentifier();
	Objectable setIdentifier(Object identifier);
	
	Objectable setChildren(Collection<Object> children);
	Collection<Object> getChildren();
	//Objectable addChildren(Boolean executeSetParent,Collection<Object> children);
	Objectable addChildren(Collection<Object> children);
	Objectable addChild(Object...child);
}
