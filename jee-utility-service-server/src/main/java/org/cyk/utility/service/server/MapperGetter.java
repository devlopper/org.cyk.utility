package org.cyk.utility.service.server;

import org.cyk.utility.mapping.Mapper;

public interface MapperGetter {

	<PERSISTENCE_ENTITY,SERVICE_ENTITY> Mapper<PERSISTENCE_ENTITY,SERVICE_ENTITY> get(Class<PERSISTENCE_ENTITY> persistenceEntityClass,Class<SERVICE_ENTITY> serviceEntityClass);

	public static abstract class AbstractImpl implements MapperGetter {
		
		@Override
		public <PERSISTENCE_ENTITY,SERVICE_ENTITY> Mapper<PERSISTENCE_ENTITY,SERVICE_ENTITY> get(Class<PERSISTENCE_ENTITY> persistenceEntityClass,Class<SERVICE_ENTITY> serviceEntityClass) {
			if(persistenceEntityClass == null || serviceEntityClass == null)
				return null;
			return __get__(persistenceEntityClass,serviceEntityClass);
		}

		protected abstract <PERSISTENCE_ENTITY,SERVICE_ENTITY> Mapper<PERSISTENCE_ENTITY,SERVICE_ENTITY> __get__(Class<PERSISTENCE_ENTITY> persistenceEntityClass,Class<SERVICE_ENTITY> serviceEntityClass);
	}
}