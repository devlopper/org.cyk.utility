package org.cyk.utility.__kernel__.object.dynamic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.collection.CollectionHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractObject extends org.cyk.utility.__kernel__.object.AbstractObject implements Objectable, Serializable {
	private static final long serialVersionUID = 1L;

	private Properties properties;
	
	@PostConstruct
	private void listenPostConstruct(){
		__listenPostConstruct__();
	}
	
	protected void __listenPostConstruct__(){}
	
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
	public Object getParent() {
		return getProperties().getParent();
	}
	
	@Override
	public Objectable setParent(Object parent) {
		getProperties().setParent(parent);
		return this;
	}
	
	@Override
	public String getIdentifier() {
		return (String) getProperties().getIdentifier();
	}
	
	@Override
	public Objectable setIdentifier(Object identifier) {
		getProperties().setIdentifier(identifier);
		return this;
	}
	
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
		addChildren(__inject__(CollectionHelper.class).instanciate(children));
		return this;
	}
	
	@Override
	public Objectable addChildren(Collection<Object> children) {
		if(__inject__(CollectionHelper.class).isNotEmpty(children)){
			Collection<Object> collection = getChildren();
			if(collection == null)
				setChildren(collection = new ArrayList<Object>());
			collection.addAll(children);
		}
		return this;
	}
}
