package org.cyk.utility.server.representation;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.annotation.Test;
import org.cyk.utility.clazz.Classes;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.server.persistence.PersistableClassesGetter;
import org.cyk.utility.server.persistence.entities.Child;
import org.cyk.utility.server.persistence.entities.MyEntity;
import org.cyk.utility.server.persistence.entities.Node;
import org.cyk.utility.server.persistence.entities.NodeHierarchy;
import org.cyk.utility.server.persistence.entities.Parent;
import org.cyk.utility.server.persistence.entities.ParentChild;
import org.cyk.utility.server.persistence.entities.ParentType;

@Dependent @Test
public class PersistableClassesGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Classes> implements PersistableClassesGetter, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Classes __execute__() throws Exception {
		Classes classes = __inject__(Classes.class);
		classes.add(MyEntity.class);
		classes.add(NodeHierarchy.class);
		classes.add(Node.class);
		classes.add(ParentChild.class);
		classes.add(Child.class);
		classes.add(Parent.class);
		classes.add(ParentType.class);
		return classes;
	}
	
}