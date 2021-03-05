package org.cyk.utility.persistence.server;

import javax.persistence.EntityManagerFactory;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.annotation.Persistence;
import org.cyk.utility.__kernel__.instance.InstanceGetter;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.mapping.MapperClassGetter;
import org.cyk.utility.persistence.EntityManagerFactoryGetterImpl;
import org.cyk.utility.persistence.query.Field;
import org.cyk.utility.persistence.query.Filter;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.persistence.query.QueryHelper;

public interface Initializer {

	static void initialize() {
		if(EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY == null)
			EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY = DependencyInjection.inject(EntityManagerFactory.class);
		QueryHelper.getQueries().setIsRegisterableToEntityManager(Boolean.TRUE);
		DependencyInjection.setQualifierClassTo(Persistence.Class.class, InstanceGetter.class);
		
		MapperClassGetter.AbstractImpl.MAP.put(QueryExecutorArguments.class, QueryExecutorArguments.Dto.Mapper.class);
		MapperClassGetter.AbstractImpl.MAP.put(QueryExecutorArguments.Dto.class, QueryExecutorArguments.Dto.Mapper.class);
		MapperClassGetter.AbstractImpl.MAP.put(Filter.class, Filter.Dto.Mapper.class);
		MapperClassGetter.AbstractImpl.MAP.put(Filter.Dto.class, Filter.Dto.Mapper.class);
		MapperClassGetter.AbstractImpl.MAP.put(Field.class, Field.Dto.Mapper.class);
		MapperClassGetter.AbstractImpl.MAP.put(Field.Dto.class, Field.Dto.Mapper.class);
		
		LogHelper.logInfo(String.format("Persistence server has been initialized. Entity Manager Factory Instance is : %s",EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY)
				, Initializer.class);
	}
}