package org.cyk.utility.map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.collection.CollectionHelper;

public abstract class AbstractKeyValueImpl<KEY,VALUE> extends AbstractObject implements KeyValue<KEY, VALUE>,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Collection<VALUE> getMany(Collection<Properties> properties) {
		Collection<VALUE> values = null;
		if(__inject__(CollectionHelper.class).isNotEmpty(values)){
			values = new ArrayList<>();
			for(Properties index : properties)
				values.add(getOne(index));
		}
		return values;
	}
	
	@Override
	public Collection<VALUE> getMany(Properties... properties) {
		return getMany(__inject__(CollectionHelper.class).instanciate(properties));
	}
	
	@Override
	public VALUE getOne(String key) {
		return getOne(getGetOneProperties(key));
	}
	
	protected Properties getGetOneProperties(String key){
		return new Properties().setKey(key);
	}
}
