package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import javax.inject.Singleton;

@Singleton
public class MapHelper extends AbstractHelper implements Serializable  {

	private static final long serialVersionUID = 1L;

	private static MapHelper INSTANCE;
	
	public static MapHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new MapHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	@SuppressWarnings("unchecked")
	public <KEY,VALUE> void addKeyValue(Map<KEY, VALUE> map,Object...objects){
		if(map == null || objects == null)
			return;
		for(int i = 0 ; i < objects.length ; i = i + 2){
			map.put((KEY)objects[i], (VALUE)objects[i+1]);
		}
	}
	
	public <KEY,VALUE> void add(Map<KEY, VALUE> map,KEY key,Collection<VALUE> values){
		if(map == null || key == null || values == null)
			return;
		for(VALUE value : values)
			map.put(key, value);
	}
		
	public <KEY,VALUE> void add(Map<KEY, VALUE> map,KEY key,@SuppressWarnings("unchecked") VALUE...values){
		if(map == null || key == null || values == null)
			return;
		add(map,key,Arrays.asList(values));
	}
	
	public <VALUE> VALUE get(Map<?, VALUE> map,Object key,Class<VALUE> valueClass){
		if(map == null || key == null || valueClass == null)
			return null;
		return map.get(key);
	}
}
