package org.cyk.utility.server.persistence.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;
import org.cyk.utility.server.persistence.api.ParentChildPersistence;
import org.cyk.utility.server.persistence.api.ParentPersistence;
import org.cyk.utility.server.persistence.entities.Parent;
import org.cyk.utility.server.persistence.entities.ParentChild;

@ApplicationScoped
public class ParentPersistenceImpl extends AbstractPersistenceEntityImpl<Parent> implements ParentPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenExecuteReadAfterSetFieldValue__(Parent parent, Field field, Properties properties) {
		if(Parent.FIELD_CHIDLREN.equals(field.getName())) {
			Collection<ParentChild> parentChilds = __inject__(ParentChildPersistence.class).readByParents(parent);
			if(CollectionHelper.isNotEmpty(parentChilds))
				parent.addChildren(parentChilds.stream().map(ParentChild::getChild).collect(Collectors.toList()));
		}
	}
	
}
