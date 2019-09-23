package org.cyk.utility.sql.builder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.array.ArrayHelper;


public class Tuple extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	public Attribute getAttributeByName(String name,Boolean instanciateIfNull){
		Collection<Attribute> attributes = getAttributes();
		Attribute attribute = null;
		if(CollectionHelper.isNotEmpty(attributes)){
			for(Attribute index : attributes)
				if(index.getName().equals(name)){
					attribute = index;
					break;
				}
		}
		if(attribute == null && Boolean.TRUE.equals(instanciateIfNull)){
			attribute = new Attribute().setName(name).setTuple(this);
			addAttributes(attribute);
		}
		return attribute;
	}
	
	public Collection<Attribute> getAttributesByNames(String[] names,Boolean instanciateIfNull){
		Collection<Attribute> result = null;
		if(__inject__(ArrayHelper.class).isNotEmpty(names)){
			for(String index : names){
				Attribute attribute = getAttributeByName(index, instanciateIfNull);
				if(attribute!=null){
					if(result == null)
						result = new ArrayList<>();
					result.add(attribute);	
				}
			}
		}
		return result;
	}
	
	public String getName(){
		return (String) getProperties().getName();
	}
	
	public Tuple setName(String name){
		getProperties().setName(name);
		if(StringHelper.isBlank(getAlias()))
			setAlias(StringHelper.getVariableNameFrom(getName()));
		return this;
	}
	
	public String getAlias(){
		return (String) getProperties().getAlias();
	}
	
	public Tuple setAlias(String alias){
		getProperties().setAlias(alias);
		return this;
	}
	
	public Collection<Attribute> getAttributes(){
		return (Collection<Attribute>) getProperties().getAttributes();
	}
	
	public Tuple setAttributes(Collection<Attribute> attributes){
		getProperties().setAttributes(attributes);
		return this;
	}
	
	public Tuple addAttributes(Collection<Attribute> attributes){
		if(CollectionHelper.isNotEmpty(attributes)){
			Collection<Attribute> collection = getAttributes();
			if(collection == null)
				setAttributes(collection = new LinkedHashSet<Attribute>());
			for(Attribute index : attributes)
				if(index.getTuple() == null)
					index.setTuple(this);
			collection.addAll(attributes);
		}
		return this;
	}
	
	public Tuple addAttributes(Attribute...attributes){
		addAttributes(List.of(attributes));
		return this;
	}
	
	public Tuple addAttributesByNames(Collection<String> attributeNames){
		if(CollectionHelper.isNotEmpty(attributeNames)){
			for(String index : attributeNames)
				addAttributes(new Attribute().setName(index));
		}
		return this;
	}
	
	public Tuple addAttributesByNames(String...attributeNames){
		addAttributesByNames(List.of(attributeNames));
		return this;
	}

	@Override
	public String toString() {
		Collection<Attribute> attributes = getAttributes();
		return getName()+(attributes == null ? ConstantEmpty.STRING : attributes);
	}
}
