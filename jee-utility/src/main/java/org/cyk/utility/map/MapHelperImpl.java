package org.cyk.utility.map;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.helper.AbstractHelper;

@Singleton
public class MapHelperImpl extends AbstractHelper implements MapHelper,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Boolean isEmpty(Map<?, ?> map){
		return map == null || map.isEmpty();
	}
	
	@Override
	public Boolean isNotEmpty(Map<?, ?> map){
		return Boolean.FALSE.equals(isEmpty(map));
	}
	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void copy(Map source,Map destination,Object[] keys,Boolean removeNullValue){
		if(__inject__(ArrayHelper.class).isNotEmpty(keys))
			for(Object key : keys){
				Object value = source.get(key);
				if(value == null && Boolean.TRUE.equals(removeNullValue))
					destination.remove(key);
				else
					destination.put(key, value);
			}
	}
	
	@Override
	@SuppressWarnings({ "rawtypes" })
	public void copy(Map source,Map destination,Object[] keys){
		copy(source, destination, keys, Boolean.TRUE);
	}
	
	@Override
	public Map<Object, Object> instanciate(Object... objects) {
		Map<Object, Object> map = null;
		if(__inject__(ArrayHelper.class).isNotEmpty(objects)){
			map = new HashMap<Object, Object>();
			for(Integer index = 0 ; index < objects.length - 1 ; index = index + 1){
				map.put(objects[index], objects[index+1]);
			}
		}
		return map;
	}
}
