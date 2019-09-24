package org.cyk.utility.server.representation.hierarchy;

import java.io.Serializable;

import org.cyk.utility.__kernel__.klass.ClassHelper;

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
	protected COLLECTION __getParents__(Boolean injectIfNull) {
		if(parents == null && Boolean.TRUE.equals(injectIfNull))
			parents = (COLLECTION) ClassHelper.instanciate(ClassHelper.getParameterAt(getClass(), 1));
		return parents;			
	}
	
	public AbstractNodeCodedAndNamed<ENTITY,COLLECTION> addParents(@SuppressWarnings("unchecked") ENTITY...parents) {
		__getParents__(Boolean.TRUE).add(parents);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	protected COLLECTION __getChildren__(Boolean injectIfNull) {
		if(children == null && Boolean.TRUE.equals(injectIfNull))
			children = (COLLECTION) ClassHelper.instanciate(ClassHelper.getParameterAt(getClass(), 1));
		return children;			
	}
	
	public AbstractNodeCodedAndNamed<ENTITY,COLLECTION> addChildren(@SuppressWarnings("unchecked") ENTITY...children) {
		__getChildren__(Boolean.TRUE).add(children);
		return this;
	}
}
