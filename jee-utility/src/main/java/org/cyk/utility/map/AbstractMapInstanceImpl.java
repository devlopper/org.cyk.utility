package org.cyk.utility.map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.constant.ConstantCharacter;
import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.string.StringHelper;

public abstract class AbstractMapInstanceImpl<KEY,VALUE> extends AbstractObject implements MapInstance<KEY,VALUE>,Serializable {
	private static final long serialVersionUID = 1L;

	private Map<KEY,VALUE> __map__;
	private Class<VALUE> __valueClass__;
	private Boolean isOrdered,isSequential;
	private Object keyValueSeparator,entrySeparator;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		__valueClass__ = (Class<VALUE>) org.cyk.utility.__kernel__.klass.ClassHelper.getParameterAt(getClass(), 1);
		setKeyValueSeparator(ConstantCharacter.EQUAL);
		setEntrySeparator(ConstantCharacter.COMA);
	}
	
	@Override
	public Map<KEY, VALUE> getMap() {
		return __map__;
	}
	
	@Override
	public MapInstance<KEY,VALUE> set(Object... keyValues) {
		if(__inject__(ArrayHelper.class).isNotEmpty(keyValues)) {
			if(__map__ == null) {
				if(Boolean.TRUE.equals(getIsOrdered()))
					__map__ = new TreeMap<>();
				else if(Boolean.TRUE.equals(getIsSequential()))
					__map__ = new LinkedHashMap<>();
				else
					__map__ = new HashMap<>();
				
			}
			for(Integer index = 0 ; index < keyValues.length ; index = index + 2) {
				__map__.put((KEY)keyValues[index], (VALUE)keyValues[index+1]);
			}
		}
		return this;
	}

	@Override
	public VALUE get(KEY key) {
		return __map__ == null ? null : __map__.get(key);
	}
	
	@Override
	public VALUE get(KEY key, Boolean injectOrCreateIfNull) {
		VALUE value = get(key);
		if(value == null && Boolean.TRUE.equals(injectOrCreateIfNull)) {
			value = __inject__(__valueClass__);
			set(key,value);
		}
		return value;
	}
	
	@Override
	public Collection<Entry<KEY, VALUE>> getEntries() {
		return __map__ == null ? null : __map__.entrySet();
	}
	
	@Override
	public Collection<KEY> getKeys(Collection<VALUE> values) {
		Collection<KEY> keys = null;
		if(CollectionHelper.isNotEmpty(values) && __inject__(MapHelper.class).isNotEmpty(this)) {
			for(Map.Entry<KEY, VALUE> index : getEntries()) {
				if(CollectionHelper.contains(values, index.getValue())){
					if(keys == null)
						keys = new ArrayList<>();
					keys.add(index.getKey());
				}
			}
		}
		return keys;
	}
	
	@Override
	public Collection<KEY> getKeys(VALUE... values) {
		return getKeys(List.of(values));
	}
	
	@Override
	public Boolean getIsOrdered() {
		return isOrdered;
	}
	
	@Override
	public MapInstance<KEY, VALUE> setIsOrdered(Boolean isOrdered) {
		this.isOrdered = isOrdered;
		return this;
	}
	
	@Override
	public Boolean getIsSequential() {
		return isSequential;
	}
	
	@Override
	public MapInstance<KEY, VALUE> setIsSequential(Boolean isSequential) {
		this.isSequential = isSequential;
		return this;
	}
	
	@Override
	public Object getKeyValueSeparator() {
		return keyValueSeparator;
	}

	@Override
	public MapInstance<KEY, VALUE> setKeyValueSeparator(Object keyValueSeparator) {
		this.keyValueSeparator = keyValueSeparator;
		return this;
	}

	@Override
	public Object getEntrySeparator() {
		return entrySeparator;
	}

	@Override
	public MapInstance<KEY, VALUE> setEntrySeparator(Object entrySeparator) {
		this.entrySeparator = entrySeparator;
		return this;
	}
	
	@Override
	public String toString() {
		return __map__ == null ? super.toString() : __map__.toString();
	}

	@Override
	public String getRepresentationAsString() {
		Collection<String> strings = new ArrayList<>();
		Collection<Map.Entry<KEY, VALUE>> entries = getEntries();
		String entrySeparator = __inject__(StringHelper.class).getString(getEntrySeparator());
		if(entries!=null) {
			String keyValueSeparator = __inject__(StringHelper.class).getString(getKeyValueSeparator());
			for(Map.Entry<KEY, VALUE> index : entries) {
				strings.add(index.getKey()+keyValueSeparator+index.getValue());
			}
		}
		return __inject__(StringHelper.class).concatenate(strings, entrySeparator);
	}

	
}
