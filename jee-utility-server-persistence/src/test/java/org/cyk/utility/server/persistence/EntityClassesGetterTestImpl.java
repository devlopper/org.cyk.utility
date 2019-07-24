package org.cyk.utility.server.persistence;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.clazz.Classes;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.server.persistence.entities.MyEntity;

@Dependent @org.cyk.utility.__kernel__.annotation.Test
public class EntityClassesGetterTestImpl extends AbstractFunctionWithPropertiesAsInputImpl<Classes> implements EntityClassesGetter, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Classes __execute__() throws Exception {
		return __inject__(Classes.class).add(MyEntity.class);
	}
	
}