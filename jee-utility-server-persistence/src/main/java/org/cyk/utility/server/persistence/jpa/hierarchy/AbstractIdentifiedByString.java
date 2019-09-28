package org.cyk.utility.server.persistence.jpa.hierarchy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@SuppressWarnings("rawtypes")
@Getter @Setter @Accessors(chain=true) @Access(AccessType.FIELD) @ToString
@MappedSuperclass
public abstract class AbstractIdentifiedByString<ENTITY> extends org.cyk.utility.server.persistence.jpa.AbstractIdentifiedByString implements Serializable {
	private static final long serialVersionUID = 1L;

	@Transient protected Collection<ENTITY> parents;
	@Transient protected Long numberOfParents;
	
	@Transient protected Collection<ENTITY> children;
	@Transient protected Long numberOfChildren;
	
	public Collection<ENTITY> getParents(Boolean injectIfNull) {
		if(parents == null && Boolean.TRUE.equals(injectIfNull))
			setParents(parents = new ArrayList<>());
		return parents;
	}
	
	public AbstractIdentifiedByString addParents(Collection<ENTITY> parents) {
		if(parents == null || parents.isEmpty())
			return this;
		getParents(Boolean.TRUE).addAll(parents);
		return this;
	}
	
	public AbstractIdentifiedByString addParents(@SuppressWarnings("unchecked") ENTITY...parents) {
		if(parents == null || parents.length == 0)
			return this;
		addParents(List.of(parents));
		return this;
	}
	
	public Collection<ENTITY> getChildren(Boolean injectIfNull) {
		if(children == null && Boolean.TRUE.equals(injectIfNull))
			setChildren(children = new ArrayList<>());
		return children;
	}
	
	public AbstractIdentifiedByString addChildren(Collection<ENTITY> children) {
		if(children == null || children.isEmpty())
			return this;
		getChildren(Boolean.TRUE).addAll(children);
		return this;
	}
	
	public AbstractIdentifiedByString addChildren(@SuppressWarnings("unchecked") ENTITY...children) {
		if(children == null || children.length == 0)
			return this;
		addChildren(List.of(children));
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