package org.cyk.utility.service.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.service.Link;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@JsonInclude(Include.NON_NULL) @Getter @Setter @Accessors(chain=true)
public abstract class AbstractObject extends org.cyk.utility.__kernel__.object.AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	protected ArrayList<Link> __links__;
	
	public ArrayList<Link> get__links__(Boolean injectIfNull) {
		if(__links__ == null && Boolean.TRUE.equals(injectIfNull))
			__links__ = new ArrayList<>();
		return __links__;
	}
	
	public AbstractObject add__links__(Collection<Link> links) {
		if(CollectionHelper.isEmpty(links))
			return this;
		get__links__(Boolean.TRUE).addAll(links);
		return this;
	}
	
	public AbstractObject add__links__(Link...links) {
		if(ArrayHelper.isEmpty(links))
			return this;
		add__links__(CollectionHelper.listOf(Boolean.TRUE, links));
		return this;
	}
	
	public AbstractObject add__link__(String identifier,String value) {
		if(StringHelper.isBlank(identifier) || StringHelper.isBlank(value))
			return this;
		get__links__(Boolean.TRUE).add(new Link().setIdentifier(identifier).setValue(value));
		return this;
	}
}