package org.cyk.utility.server.business.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;
import org.cyk.utility.server.business.BusinessFunctionCreator;
import org.cyk.utility.server.business.BusinessFunctionModifier;
import org.cyk.utility.server.business.api.ParentBusiness;
import org.cyk.utility.server.business.api.ParentChildBusiness;
import org.cyk.utility.server.persistence.api.ParentChildPersistence;
import org.cyk.utility.server.persistence.api.ParentPersistence;
import org.cyk.utility.server.persistence.entities.Child;
import org.cyk.utility.server.persistence.entities.Parent;
import org.cyk.utility.server.persistence.entities.ParentChild;

@ApplicationScoped
public class ParentBusinessImpl extends AbstractBusinessEntityImpl<Parent,ParentPersistence> implements ParentBusiness,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenExecuteCreateAfter__(Parent parent, Properties properties,BusinessFunctionCreator function) {
		super.__listenExecuteCreateAfter__(parent, properties, function);
		if(CollectionHelper.isNotEmpty(parent.getChildren())) {
			Collection<ParentChild> parentChildren = parent.getChildren().stream().map(child -> new ParentChild().setParent(parent).setChild(child)).collect(Collectors.toList());
			__inject__(ParentChildBusiness.class).createMany(parentChildren);
		}
	}
	
	@Override
	protected void __listenExecuteUpdateBefore__(Parent parent, Properties properties,BusinessFunctionModifier function) {
		super.__listenExecuteUpdateBefore__(parent, properties, function);
		Strings fields = __getFieldsFromProperties__(properties);
		if(CollectionHelper.isEmpty(fields))
			return;
		for(String index : fields.get()) {
			if(Parent.FIELD_CHIDLREN.equals(index)) {
				Collection<ParentChild> databaseParentChildren = __inject__(ParentChildPersistence.class).readByParents(parent);
				Collection<Child> databaseChildren = CollectionHelper.isEmpty(databaseParentChildren) ? null : databaseParentChildren.stream()
						.map(ParentChild::getChild).collect(Collectors.toList());
				__delete__(parent.getChildren(), databaseParentChildren,ParentChild.FIELD_PARENT);
				__save__(ParentChild.class,parent.getChildren(), databaseChildren, ParentChild.FIELD_CHILD, parent, ParentChild.FIELD_PARENT);
			}
		}			
	}
	
}
