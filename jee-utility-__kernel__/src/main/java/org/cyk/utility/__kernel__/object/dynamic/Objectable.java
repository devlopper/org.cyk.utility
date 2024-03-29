package org.cyk.utility.__kernel__.object.dynamic;

import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;

public interface Objectable extends org.cyk.utility.__kernel__.object.Objectable {

	Properties getProperties();
	Objectable setProperties(Properties properties);
	Object getProperty(Object key);
	Objectable setProperty(Object key,Object value);
	Objectable setPropertyIfNull(Object key,Object value);
	Objectable copyProperty(Object key,Properties properties);
	Collection<Object> getPropertiesWhereKeyIsInstanceOf(Class<?> clazz);
	
	Object getParent();
	<I> I getParentAs(Class<I> aClass);
	Objectable setParent(Object parent,Boolean executeAddChild);
	Objectable setParent(Object parent);
	
	Object getIdentifier();
	Objectable setIdentifier(Object identifier);
	/*
	Object getExternalIdentifier();
	Objectable setExternalIdentifier(Object identifier);
	*/
	Object getOrderNumber();
	Objectable setOrderNumber(Object orderNumber);
	
	Objectable setChildren(Collection<Object> children);
	Collection<Object> getChildren();
	//Objectable addChildren(Boolean executeSetParent,Collection<Object> children);
	Objectable addChildren(Collection<Object> children);
	Objectable addChild(Object...child);
	Boolean isHasChildren();
	Boolean isHasChildrenInstanceOf(Class<?> aClass);
	<T> Collection<T> getChildrenInstanceOf(Class<T> aClass);
	
	Object getChildAt(Integer index);
	Object getLastChild();
	
	String getRepresentationAsString();
	
}
