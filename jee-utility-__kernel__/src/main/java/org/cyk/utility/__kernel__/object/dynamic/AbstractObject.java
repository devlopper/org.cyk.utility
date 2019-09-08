package org.cyk.utility.__kernel__.object.dynamic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.annotation.PostConstruct;

import org.cyk.utility.__kernel__.KernelHelper;
import org.cyk.utility.__kernel__.properties.Properties;

public abstract class AbstractObject extends org.cyk.utility.__kernel__.object.AbstractObject implements Objectable, Serializable {
	private static final long serialVersionUID = 1L;

	protected Object identifier;
	protected Properties properties;
	protected Boolean __initialised__;
	protected Boolean __isCallInitialiseMethodOnPostConstruct__;
	
	@PostConstruct
	private void listenPostConstruct(){
		__listenBeforePostConstruct__();
		__listenPostConstruct__();
		__listenAfterPostConstruct__();
		if(Boolean.TRUE.equals(__isCallInitialiseMethodOnPostConstruct__))
			initialise();
	}
	
	public void initialise() {
		if(Boolean.TRUE.equals(__initialised__))
			return;
		__initialised__ = Boolean.TRUE;
		__initialise__();
	}
	
	protected void __initialise__(){}
	
	protected void __listenBeforePostConstruct__(){}
	protected void __listenPostConstruct__(){}
	protected void __listenAfterPostConstruct__(){}
	
	@Override
	public Properties getProperties() {
		if(properties == null)
			properties = new Properties();
		return properties;
	}
	
	@Override
	public Objectable setProperties(Properties properties) {
		this.properties = properties;
		return this;
	}
	
	@Override
	public Object getProperty(Object key) {
		return getProperties().get(key);
	}
	
	@Override
	public Objectable setProperty(Object key, Object value) {
		getProperties().set(key, value);
		return this;
	}
	
	@Override
	public Objectable setPropertyIfNull(Object key, Object value) {
		Object currentValue = getProperty(key);
		if(currentValue == null)
			setProperty(key, value);
		return this;
	}
	
	@Override
	public Objectable copyProperty(Object key, Properties properties) {
		setProperty(key, Properties.getFromPath(properties, key));
		return this;
	}
	
	@Override
	public Collection<Object> getPropertiesWhereKeyIsInstanceOf(Class<?> clazz) {
		return getProperties().getWhereKeyIsInstanceOf(clazz);
	}
	
	@Override
	public Object getParent() {
		return getProperties().getParent();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <I> I getParentAs(Class<I> aClass) {
		return (I) getParent();
	}
	
	@Override
	public Objectable setParent(Object parent, Boolean executeAddChild) {
		getProperties().setParent(parent);
		if(Boolean.TRUE.equals(executeAddChild) && parent instanceof Objectable)
			((Objectable)parent).addChild(this);
		return this;
	}
	
	@Override
	public Objectable setParent(Object parent) {
		setParent(parent, Boolean.FALSE);
		return this;
	}
	
	@Override
	public Object getIdentifier() {
		return identifier;
	}
	
	@Override
	public Objectable setIdentifier(Object identifier) {
		this.identifier = identifier;
		return this;
	}
	
	@Override
	public Object getOrderNumber() {
		return getProperties().get("orderNumber");
	}
	
	@Override
	public Objectable setOrderNumber(Object orderNumber) {
		getProperties().set("orderNumber", orderNumber);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Object> getChildren() {
		return (Collection<Object>) getProperties().getChildren();
	}
	
	@Override
	public Boolean isHasChildren() {
		Collection<Object> children = getChildren();
		return children == null ? null : !children.isEmpty();
	}
	
	@Override
	public Boolean isHasChildrenInstanceOf(Class<?> aClass) {
		if(Boolean.TRUE.equals(isHasChildren()))
			for(Object index : getChildren())
				if(Boolean.TRUE.equals(____inject____(KernelHelper.class).isInstanceOf(index.getClass(), aClass)))
					return Boolean.TRUE;
		return null;
	}
	
	@Override
	public Objectable setChildren(Collection<Object> children) {
		getProperties().setChildren(children);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> Collection<T> getChildrenInstanceOf(Class<T> aClass) {
		Collection<T> collection = null;
		if(Boolean.TRUE.equals(isHasChildrenInstanceOf(aClass))) {
			for(Object index : getChildren())
				if(Boolean.TRUE.equals(____inject____(KernelHelper.class).isInstanceOf(index.getClass(), aClass))) {
					if(collection == null)
						collection = new ArrayList<T>();
					collection.add((T) index);
				}
		}
		return collection;
	}
	
	@Override
	public Objectable addChild(Object... children) {
		if(children!=null && children.length>0)
			addChildren(Arrays.asList(children));
		return this;
	}
	
	@Override
	public Objectable addChildren(Collection<Object> children) {
		if(children!=null && !children.isEmpty()){
			Collection<Object> collection = getChildren();
			if(collection == null)
				setChildren(collection = new ArrayList<Object>());
			for(Object index : children)
				if(index instanceof Objectable)
					((Objectable)index).setParent(this);
			collection.addAll(children);
		}
		return this;
	}
	
	@Override
	public Object getChildAt(Integer index) {
		return __inject__(KernelHelper.class).getElementAt(getChildren(), index);
	}
	
	@Override
	public Object getLastChild() {
		return __inject__(KernelHelper.class).getElementAtEnd(getChildren());
	}
	
	@Override
	public String getRepresentationAsString() {
		return null;
	}
	
	@Override
	public boolean equals(Object object) {
		if(object instanceof Objectable && object.getClass().equals(getClass()) ) {
			Object identifier01 = getIdentifier();
			Object identifier02 = ((Objectable)object).getIdentifier();
			return identifier01!=null && identifier02!=null && identifier01.equals(identifier02);
		}
		return super.equals(object);
	}
	
	@Override
	public int hashCode() {
		Object identifier = getIdentifier();
		if(identifier!=null)
			return identifier.hashCode();
		return super.hashCode();
	}
}
