package org.cyk.utility.server.playground;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.annotation.Test;
import org.cyk.utility.clazz.Classes;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.server.persistence.PersistableClassesGetter;
import org.cyk.utility.server.persistence.entities.MyEntity;
import org.cyk.utility.server.persistence.entities.Node;
import org.cyk.utility.server.persistence.entities.NodeHierarchy;

@Dependent @Test
public class PersistableClassesGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Classes> implements PersistableClassesGetter, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Classes __execute__() throws Exception {
		Classes classes = __inject__(Classes.class);
		classes.add(MyEntity.class);
		classes.add(NodeHierarchy.class);
		classes.add(Node.class);
		return classes;
	}
	
}