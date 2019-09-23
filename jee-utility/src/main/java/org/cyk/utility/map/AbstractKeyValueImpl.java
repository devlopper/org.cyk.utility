package org.cyk.utility.map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.properties.Properties;

public abstract class AbstractKeyValueImpl<KEY,VALUE> extends AbstractObject implements KeyValue<KEY, VALUE>,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Collection<VALUE> getMany(Collection<Properties> properties) {
		Collection<VALUE> values = null;
		if(CollectionHelper.isNotEmpty(values)){
			values = new ArrayList<>();
			for(Properties index : properties)
				values.add(getOne(index));
		}
		return values;
	}
	
	@Override
	public Collection<VALUE> getMany(Properties... properties) {
		return getMany(List.of(properties));
	}
	
	@Override
	public VALUE getOne(String key) {
		return getOne(getGetOneProperties(key));
	}
	
	protected Properties getGetOneProperties(String key){
		return new Properties().setKey(key);
	}
}
