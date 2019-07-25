package org.cyk.utility.server.persistence.jpa;

import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManagerFactory;
import javax.persistence.metamodel.EntityType;

import org.cyk.utility.clazz.Classes;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.server.persistence.PersistableClassesGetter;

@Dependent
public class PersistableClassesGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Classes> implements PersistableClassesGetter, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Classes __execute__() throws Exception {
		Classes classes = __inject__(Classes.class);
		EntityManagerFactory entityManagerFactory = __inject__(EntityManagerFactory.class);
		for(EntityType<?> index : entityManagerFactory.getMetamodel().getEntities()) {
			classes.add(index.getJavaType());
		}
		return classes;
	}
	
}