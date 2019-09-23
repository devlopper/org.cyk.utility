package org.cyk.utility.server.persistence.jpa.hierarchy;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.collection.CollectionInstance;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@SuppressWarnings("rawtypes")
@Getter @Setter @Accessors(chain=true) @Access(AccessType.FIELD) @ToString
@MappedSuperclass
public abstract class AbstractIdentifiedByString<ENTITY,COLLECTION extends CollectionInstance<ENTITY>> extends org.cyk.utility.server.persistence.jpa.AbstractIdentifiedByString implements Serializable {
	private static final long serialVersionUID = 1L;

	@Transient protected COLLECTION parents;
	@Transient protected Long numberOfParents;
	
	@Transient protected COLLECTION children;
	@Transient protected Long numberOfChildren;
	
	@SuppressWarnings("unchecked")
	public COLLECTION getParents(Boolean injectIfNull) {
		if(parents == null && Boolean.TRUE.equals(injectIfNull))
			setParents((COLLECTION) __inject__(org.cyk.utility.__kernel__.klass.ClassHelper.getParameterAt(getClass(), 1)));
		return parents;
	}
	
	public AbstractIdentifiedByString addParents(Collection<ENTITY> children) {
		getParents(Boolean.TRUE).add(children);
		return this;
	}
	
	public AbstractIdentifiedByString addParents(@SuppressWarnings("unchecked") ENTITY...parents) {
		if(__inject__(ArrayHelper.class).isNotEmpty(parents))
			addParents(__inject__(CollectionHelper.class).instanciate(parents));
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public COLLECTION getChildren(Boolean injectIfNull) {
		if(children == null && Boolean.TRUE.equals(injectIfNull))
			setChildren((COLLECTION) __inject__(org.cyk.utility.__kernel__.klass.ClassHelper.getParameterAt(getClass(), 1)));
		return children;
	}
	
	public AbstractIdentifiedByString addChildren(Collection<ENTITY> children) {
		getChildren(Boolean.TRUE).add(children);
		return this;
	}
	
	public AbstractIdentifiedByString addChildren(@SuppressWarnings("unchecked") ENTITY...children) {
		if(__inject__(ArrayHelper.class).isNotEmpty(children))
			addChildren(__inject__(CollectionHelper.class).instanciate(children));
		return this;
	}
	
	/**/
	
	/**/
	
	public static final String FIELD_PARENTS = "parents";
	public static final String FIELD_NUMBER_OF_PARENTS = "numberOfParents";
	public static final String FIELD_CHILDREN = "children";
	public static final String FIELD_NUMBER_OF_CHILDREN = "numberOfChildren";
	
	/**/
	
}