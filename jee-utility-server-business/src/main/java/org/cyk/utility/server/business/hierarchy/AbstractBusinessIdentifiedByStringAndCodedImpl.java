package org.cyk.utility.server.business.hierarchy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.business.BusinessFunctionCreator;
import org.cyk.utility.server.business.BusinessFunctionRemover;
import org.cyk.utility.server.persistence.jpa.hierarchy.AbstractHierarchy;
import org.cyk.utility.server.persistence.jpa.hierarchy.AbstractIdentifiedByString;
import org.cyk.utility.server.persistence.jpa.hierarchy.Hierarchies;
import org.cyk.utility.server.persistence.jpa.hierarchy.HierarchyPersistence;
import org.cyk.utility.server.persistence.jpa.hierarchy.PersistenceIdentifiedByString;

public abstract class AbstractBusinessIdentifiedByStringAndCodedImpl<ENTITY extends AbstractIdentifiedByString<ENTITY>,PERSISTENCE extends PersistenceIdentifiedByString<ENTITY>,HIERARCHY extends AbstractHierarchy<ENTITY>,HIERARCHIES extends Hierarchies<HIERARCHY,ENTITY>,HIERARCHY_PERSISTENCE extends HierarchyPersistence<HIERARCHY,ENTITY, HIERARCHIES>,HIERARCHY_BUSINESS extends HierarchyBusiness<HIERARCHY, ENTITY, HIERARCHIES>> extends AbstractBusinessIdentifiedByStringImpl<ENTITY,PERSISTENCE,HIERARCHY,HIERARCHIES,HIERARCHY_PERSISTENCE,HIERARCHY_BUSINESS> implements BusinessIdentifiedByString<ENTITY>,Serializable {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected void __listenExecuteCreateAfter__(ENTITY entity, Properties properties, BusinessFunctionCreator function) {
		super.__listenExecuteCreateAfter__(entity, properties, function);
		Collection<HIERARCHY> hierarchies = null;
		if(CollectionHelper.isNotEmpty(entity.getParents())) {
			if(hierarchies == null)
				hierarchies = new ArrayList<>();
			for(ENTITY index : entity.getParents())
				hierarchies.add((HIERARCHY) __inject__(__hierarchyClass__).setParent(index).setChild(entity));
		}
		if(CollectionHelper.isNotEmpty(entity.getChildren())) {
			if(hierarchies == null)
				hierarchies = new ArrayList<>();
			for(ENTITY index : entity.getChildren())
				hierarchies.add((HIERARCHY) __inject__(__hierarchyClass__).setParent(entity).setChild(index));
		}
		if(CollectionHelper.isNotEmpty(hierarchies))
			__inject__(__hierarchyBusinessClass__).createMany(hierarchies);
	}
	
	/*@Override
	protected void __listenExecuteUpdateAfter__(ENTITY entity, Properties properties,BusinessFunctionModifier function) {
		super.__listenExecuteUpdateAfter__(entity, properties, function);
		Strings fields = __getFieldsFromProperties__(properties);
		if(CollectionHelper.isNotEmpty(fields)) {
			for(String index : fields.get()) {
				if(AbstractIdentifiedByString.FIELD_PARENTS.equals(index)) {
					CollectionInstance<ENTITY> parents = entity.getParents();
					Class<HIERARCHY_PERSISTENCE> hierarchyPersistenceClass = __getHierarchyPersistenceClass__();
					HIERARCHIES hierarchies = __inject__(hierarchyPersistenceClass).readByParents(parents.get());
					Collection<ENTITY> databaseNodes = CollectionHelper.isEmpty(databaseHierarchies) ? null : databaseHierarchies.getHierarchyParents();
					
					if(databaseHierarchies != null)
						__delete__(collectionInstance, databaseHierarchies.get(),AbstractHierarchy.FIELD_PARENT);
					
					__save__(AbstractHierarchy.class,collectionInstance, databaseNodes, AbstractHierarchy.FIELD_PARENT, entity, UserAccountProfile.FIELD_USER_ACCOUNT);
				}else if(AbstractIdentifiedByString.FIELD_CHILDREN.equals(index)) {
					
				}
			}
		}
	}*/
	
	@Override
	protected void __listenExecuteDeleteBefore__(ENTITY entity, Properties properties,BusinessFunctionRemover function) {
		super.__listenExecuteDeleteBefore__(entity, properties, function);
		@SuppressWarnings("unchecked")
		HIERARCHIES hierarchies = __inject__(__hierarchyPersistenceClass__).readWhereIsParentOrChild(entity);
		if(CollectionHelper.isNotEmpty(hierarchies))
			__inject__(__hierarchyBusinessClass__).deleteMany(hierarchies.get());
	}
	
}
