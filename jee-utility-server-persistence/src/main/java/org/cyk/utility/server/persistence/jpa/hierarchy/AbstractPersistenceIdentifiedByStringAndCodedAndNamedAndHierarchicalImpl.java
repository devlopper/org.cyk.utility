package org.cyk.utility.server.persistence.jpa.hierarchy;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;
import org.cyk.utility.__kernel__.string.Strings;

public abstract class AbstractPersistenceIdentifiedByStringAndCodedAndNamedAndHierarchicalImpl<ENTITY extends AbstractIdentifiedByString<ENTITY,?>,HIERARCHY extends AbstractHierarchy<ENTITY>,HIERARCHIES extends Hierarchies<HIERARCHY,ENTITY>,HIERARCHY_PERSISTENCE extends HierarchyPersistence<HIERARCHY,ENTITY, HIERARCHIES>> extends AbstractPersistenceEntityImpl<ENTITY> implements PersistenceIdentifiedByStringAndCodedAndNamedAndHierarchical<ENTITY>,Serializable {
	private static final long serialVersionUID = 1L;

	private Class<HIERARCHY_PERSISTENCE> hierarchyPersistenceClass;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		hierarchyPersistenceClass = (Class<HIERARCHY_PERSISTENCE>) ClassHelper.getParameterAt(getClass(), 3);
	}
	
	@Override
	public Collection<ENTITY> readByParentsIdentifiers(Collection<String> parentsIdentifiers) {
		HIERARCHIES hierarchies = __inject__(hierarchyPersistenceClass).readByParentsIdentifiers(parentsIdentifiers);
		return hierarchies == null ? null : hierarchies.getHierarchyChildren();	
	}
	
	@Override
	public Collection<ENTITY> readByParentsIdentifiers(String... parentsIdentifiers) {
		return readByParentsIdentifiers(List.of(parentsIdentifiers));
	}
	
	@Override
	public Collection<ENTITY> readByParents(Collection<ENTITY> parents) {
		if(CollectionHelper.isNotEmpty(parents))
			return readByParentsIdentifiers(parents.stream().map(AbstractIdentifiedByString::getIdentifier).collect(Collectors.toList()));
		return null;
	}
	
	@Override
	public Collection<ENTITY> readByParents(@SuppressWarnings("unchecked") ENTITY... parents) {
		return readByParents(List.of(parents));
	}
	
	@Override
	public Collection<ENTITY> readByChildrenIdentifiers(Collection<String> childrenIdentifiers) {
		HIERARCHIES hierarchies = __inject__(hierarchyPersistenceClass).readByChildrenIdentifiers(childrenIdentifiers);
		return hierarchies == null ? null : hierarchies.getHierarchyParents();	
	}
	
	@Override
	public Collection<ENTITY> readByChildrenIdentifiers(String... childrenIdentifiers) {
		return readByChildrenIdentifiers(List.of(childrenIdentifiers));
	}
	
	@Override
	public Collection<ENTITY> readByChildren(Collection<ENTITY> children) {
		if(CollectionHelper.isNotEmpty(children))
			return readByChildrenIdentifiers(children.stream().map(AbstractIdentifiedByString::getIdentifier).collect(Collectors.toList()));
		return null;
	}
	
	@Override
	public Collection<ENTITY> readByChildren(@SuppressWarnings("unchecked") ENTITY... children) {
		return readByChildren(List.of(children));
	}
	
	@Override
	protected void __listenExecuteReadAfter__(ENTITY entity,Properties properties) {
		super.__listenExecuteReadAfter__(entity,properties);
		Strings fields = __getFieldsFromProperties__(properties);
		if(CollectionHelper.isNotEmpty(fields))
			fields.get().forEach(new Consumer<String>() {
				@SuppressWarnings("unchecked")
				@Override
				public void accept(String field) {
					if(AbstractIdentifiedByString.FIELD_PARENTS.equals(field)) {
						Collection<ENTITY> parents = readByChildren(entity);
						if(CollectionHelper.isNotEmpty(parents))
							entity.getParents(Boolean.TRUE).add(parents);
					}else if(AbstractIdentifiedByString.FIELD_CHILDREN.equals(field)) {
						Collection<ENTITY> children = readByParents(entity);
						if(CollectionHelper.isNotEmpty(children))
							entity.getChildren(Boolean.TRUE).add(children);
					}
				}
			});
	}

}
