package org.cyk.utility.persistence.server;

import java.io.Serializable;
import java.util.List;

public interface DefaultProjectionsGetter {

	List<String> get(Class<?> klass);
	
	public static abstract class AbstractImpl implements DefaultProjectionsGetter,Serializable {
		
		@Override
		public List<String> get(Class<?> klass) {
			if(klass == null)
				return null;
			return __get__(klass);
		}

		protected abstract List<String> __get__(Class<?> klass);	
	}
}