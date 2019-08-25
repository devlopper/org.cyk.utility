package org.cyk.utility.server.representation.hierarchy;

import java.io.Serializable;

import org.cyk.utility.clazz.ClassHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractNodeCodedAndNamed<ENTITY extends AbstractNodeCodedAndNamed<ENTITY,?>,COLLECTION extends AbstractNodeCollection<ENTITY>> extends org.cyk.utility.server.representation.AbstractEntityFromPersistenceEntityCodedAndNamed implements Serializable {	
	private static final long serialVersionUID = 1L;

	private COLLECTION parents;
	private Long numberOfParents;
	private COLLECTION children;
	private Long numberOfChildren;
	
	@SuppressWarnings("unchecked")
	public COLLECTION getParents(Boolean injectIfNull) {
		if(parents == null && Boolean.TRUE.equals(injectIfNull))
			parents = (COLLECTION) __inject__(ClassHelper.class).instanciateOne(__inject__(ClassHelper.class).getParameterAt(getClass(), 1, Object.class));
		return parents;			
	}
	
	public AbstractNodeCodedAndNamed<ENTITY,COLLECTION> addParents(@SuppressWarnings("unchecked") ENTITY...parents) {
		getParents(Boolean.TRUE).add(parents);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public COLLECTION getChildren(Boolean injectIfNull) {
		if(children == null && Boolean.TRUE.equals(injectIfNull))
			children = (COLLECTION) __inject__(ClassHelper.class).instanciateOne(__inject__(ClassHelper.class).getParameterAt(getClass(), 1, Object.class));
		return children;			
	}
	
	public AbstractNodeCodedAndNamed<ENTITY,COLLECTION> addChildren(@SuppressWarnings("unchecked") ENTITY...children) {
		getChildren(Boolean.TRUE).add(children);
		return this;
	}
}
