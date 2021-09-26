package org.cyk.utility.persistence.server;

import org.cyk.utility.persistence.SpecificPersistence;

public interface SpecificPersistenceGetter {

	<ENTITY> SpecificPersistence<ENTITY> get(Class<ENTITY> klass);
	
	public static abstract class AbstractImpl implements SpecificPersistenceGetter {
		
		@Override
		public <ENTITY> SpecificPersistence<ENTITY> get(Class<ENTITY> klass) {
			if(klass == null)
				return null;
			return __get__(klass);
		}

		protected abstract <ENTITY> SpecificPersistence<ENTITY> __get__(Class<ENTITY> klass);
	}
}