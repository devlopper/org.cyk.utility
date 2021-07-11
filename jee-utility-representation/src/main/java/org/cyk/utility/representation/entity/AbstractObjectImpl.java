package org.cyk.utility.representation.entity;

import java.io.Serializable;
import java.util.ArrayList;

import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@JsonInclude(Include.NON_NULL) @Getter @Setter @Accessors(chain=true)
public abstract class AbstractObjectImpl extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	protected Boolean __deletable__;
	
	protected ArrayList<Link> __links__;
	
	public ArrayList<Link> get__links__(Boolean injectIfNull) {
		if(__links__ == null && Boolean.TRUE.equals(injectIfNull))
			__links__ = new ArrayList<>();
		return __links__;
	}
	
	public AbstractObjectImpl add__link__(String name,String value) {
		if(StringHelper.isBlank(name) || StringHelper.isBlank(value))
			return this;
		get__links__(Boolean.TRUE).add(new Link().setName(name).setValue(value));
		return this;
	}
	
	/**/
	
	public static final String FIELD___DELETABLE__ = "__deletable__";
}