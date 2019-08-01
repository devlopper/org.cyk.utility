package org.cyk.utility.server.persistence.jpa.hierarchy;
import java.io.Serializable;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;
import org.cyk.utility.string.Strings;

public abstract class AbstractPersistenceIdentifiedByStringImpl<ENTITY extends AbstractIdentifiedByString<ENTITY,?>,HIERARCHY extends AbstractHierarchy<ENTITY>,HIERARCHIES extends Hierarchies<HIERARCHY,ENTITY>,HIERARCHY_PERSISTENCE extends HierarchyPersistence<HIERARCHY,ENTITY, HIERARCHIES>> extends AbstractPersistenceEntityImpl<ENTITY> implements PersistenceIdentifiedByString<ENTITY>,Serializable {
	private static final long serialVersionUID = 1L;

	protected Class<HIERARCHY_PERSISTENCE> __hierarchyPersistenceClass__;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		__hierarchyPersistenceClass__ = (Class<HIERARCHY_PERSISTENCE>) __injectClassHelper__().getByName(__injectClassHelper__().getParameterAt(getClass(), 3, Object.class).getName());
	}
	
	@Override
	public Collection<ENTITY> readByParentsIdentifiers(Collection<String> parentsIdentifiers) {
		HIERARCHIES hierarchies = __inject__(__hierarchyPersistenceClass__).readByParentsIdentifiers(parentsIdentifiers);
		return hierarchies == null ? null : hierarchies.getHierarchyChildren();	
	}
	
	@Override
	public Collection<ENTITY> readByParentsIdentifiers(String... parentsIdentifiers) {
		return readByParentsIdentifiers(__injectCollectionHelper__().instanciate(parentsIdentifiers));
	}
	
	@Override
	public Collection<ENTITY> readByParents(Collection<ENTITY> parents) {
		if(__injectCollectionHelper__().isNotEmpty(parents))
			return readByParentsIdentifiers(parents.stream().map(AbstractIdentifiedByString::getIdentifier).collect(Collectors.toList()));
		return null;
	}
	
	@Override
	public Collection<ENTITY> readByParents(@SuppressWarnings("unchecked") ENTITY... parents) {
		return readByParents(__injectCollectionHelper__().instanciate(parents));
	}
	
	@Override
	public Collection<ENTITY> readByChildrenIdentifiers(Collection<String> childrenIdentifiers) {
		HIERARCHIES hierarchies = __inject__(__hierarchyPersistenceClass__).readByChildrenIdentifiers(childrenIdentifiers);
		return hierarchies == null ? null : hierarchies.getHierarchyParents();	
	}
	
	@Override
	public Collection<ENTITY> readByChildrenIdentifiers(String... childrenIdentifiers) {
		return readByChildrenIdentifiers(__injectCollectionHelper__().instanciate(childrenIdentifiers));
	}
	
	@Override
	public Collection<ENTITY> readByChildren(Collection<ENTITY> children) {
		if(__injectCollectionHelper__().isNotEmpty(children))
			return readByChildrenIdentifiers(children.stream().map(AbstractIdentifiedByString::getIdentifier).collect(Collectors.toList()));
		return null;
	}
	
	@Override
	public Collection<ENTITY> readByChildren(@SuppressWarnings("unchecked") ENTITY... children) {
		return readByChildren(__injectCollectionHelper__().instanciate(children));
	}
	
	@Override
	protected void __listenExecuteReadAfter__(ENTITY entity,Properties properties) {
		super.__listenExecuteReadAfter__(entity,properties);
		Strings fields = __getFieldsFromProperties__(properties);
		if(__injectCollectionHelper__().isNotEmpty(fields))
			fields.get().forEach(new Consumer<String>() {
				@SuppressWarnings("unchecked")
				@Override
				public void accept(String field) {
					if(AbstractIdentifiedByString.FIELD_PARENTS.equals(field)) {
						Collection<ENTITY> parents = readByChildren(entity);
						if(__injectCollectionHelper__().isNotEmpty(parents))
							entity.getParents(Boolean.TRUE).add(parents);
					}else if(AbstractIdentifiedByString.FIELD_CHILDREN.equals(field)) {
						Collection<ENTITY> children = readByParents(entity);
						if(__injectCollectionHelper__().isNotEmpty(children))
							entity.getChildren(Boolean.TRUE).add(children);
					}
				}
			});
	}

}
