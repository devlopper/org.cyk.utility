package org.cyk.utility.playground.server;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.clazz.Classes;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.playground.server.persistence.entities.MyEntity;
import org.cyk.utility.playground.server.persistence.entities.Node;
import org.cyk.utility.playground.server.persistence.entities.NodeHierarchy;
import org.cyk.utility.server.persistence.PersistableClassesGetter;

@Dependent @System
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