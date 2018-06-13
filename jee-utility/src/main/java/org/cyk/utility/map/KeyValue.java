package org.cyk.utility.map;

import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;

public interface KeyValue<KEY,VALUE> {

	Collection<VALUE> getMany(Collection<Properties> properties);
	Collection<VALUE> getMany(Properties...properties);
	VALUE getOne(Properties properties);
	VALUE getOne(String key);
	
}
