package org.cyk.utility.server.persistence.jpa.hierarchy;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.collection.CollectionInstance;
import org.cyk.utility.server.persistence.jpa.AbstractIdentifiedByStringAndCodedAndNamed;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@SuppressWarnings("rawtypes")
@Getter @Setter @Accessors(chain=true) @Access(AccessType.FIELD) @ToString
@MappedSuperclass
public abstract class AbstractIdentifiedByStringAndCodedAndNamedAndHierarchical<ENTITY,COLLECTION extends CollectionInstance<ENTITY>> extends AbstractIdentifiedByStringAndCodedAndNamed implements Serializable {
	private static final long serialVersionUID = 1L;

	@Transient protected COLLECTION parents;
	
	@Transient protected COLLECTION children;
	
	@SuppressWarnings("unchecked")
	public COLLECTION getParents(Boolean injectIfNull) {
		if(parents == null && Boolean.TRUE.equals(injectIfNull))
			setParents((COLLECTION) __inject__(__inject__(ClassHelper.class).getParameterAt(getClass(), 1, CollectionInstance.class)));
		return parents;
	}
	
	public AbstractIdentifiedByStringAndCodedAndNamedAndHierarchical addParents(Collection<ENTITY> children) {
		getParents(Boolean.TRUE).add(children);
		return this;
	}
	
	public AbstractIdentifiedByStringAndCodedAndNamedAndHierarchical addParents(@SuppressWarnings("unchecked") ENTITY...parents) {
		if(__inject__(ArrayHelper.class).isNotEmpty(parents))
			addParents(__inject__(CollectionHelper.class).instanciate(parents));
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public COLLECTION getChildren(Boolean injectIfNull) {
		if(children == null && Boolean.TRUE.equals(injectIfNull))
			setChildren((COLLECTION) __inject__(__inject__(ClassHelper.class).getParameterAt(getClass(), 1, CollectionInstance.class)));
		return children;
	}
	
	public AbstractIdentifiedByStringAndCodedAndNamedAndHierarchical addChildren(Collection<ENTITY> children) {
		getChildren(Boolean.TRUE).add(children);
		return this;
	}
	
	public AbstractIdentifiedByStringAndCodedAndNamedAndHierarchical addChildren(@SuppressWarnings("unchecked") ENTITY...children) {
		if(__inject__(ArrayHelper.class).isNotEmpty(children))
			addChildren(__inject__(CollectionHelper.class).instanciate(children));
		return this;
	}
	
	/**/
	
	/**/
	
	public static final String FIELD_PARENTS = "parents";
	public static final String FIELD_CHILDREN = "children";
	
	/**/
	
}