package org.cyk.utility.persistence.server;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.computation.SortOrder;

@ApplicationScoped
public class DefaultSortOrdersGetterImpl extends DefaultSortOrdersGetter.AbstractImpl implements Serializable {

	public static final Map<Class<?>,Map<String, SortOrder>> MAP = new HashMap<>();
	
	@Override
	protected Map<String, SortOrder> __get__(Class<?> klass) {
		return MAP.get(klass);
	}
}