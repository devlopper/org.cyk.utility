package org.cyk.utility.__kernel__.object.dynamic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;

import org.assertj.core.util.Arrays;
import org.cyk.utility.__kernel__.KernelHelper;
import org.cyk.utility.__kernel__.properties.Properties;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractObject extends org.cyk.utility.__kernel__.object.AbstractObject implements Objectable, Serializable {
	private static final long serialVersionUID = 1L;

	private Properties properties;
	
	@PostConstruct
	private void listenPostConstruct(){
		__listenBeforePostConstruct__();
		__listenPostConstruct__();
		__listenAfterPostConstruct__();
	}
	
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
	public Object getParent() {
		return getProperties().getParent();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <I extends Objectable> I getParentAs(Class<I> aClass) {
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
		return getProperties().getIdentifier();
	}
	
	@Override
	public Objectable setIdentifier(Object identifier) {
		getProperties().setIdentifier(identifier);
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
	public Objectable setChildren(Collection<Object> children) {
		getProperties().setChildren(children);
		return this;
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
}
