package org.cyk.utility.persistence.server;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.computation.SortOrder;

public interface DefaultSortOrdersGetter {

	Map<String, SortOrder> get(Class<?> klass);
	
	public static abstract class AbstractImpl implements DefaultSortOrdersGetter,Serializable {
		
		@Override
		public Map<String, SortOrder> get(Class<?> klass) {
			if(klass == null)
				return null;
			return __get__(klass);
		}

		protected abstract Map<String, SortOrder> __get__(Class<?> klass);	
	}
}