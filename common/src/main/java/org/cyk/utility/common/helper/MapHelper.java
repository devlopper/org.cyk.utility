package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Singleton;

import org.cyk.utility.common.Action;

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
	
	/**/
	
	public static interface ContainsKey<KEY> extends Action<KEY, Boolean> {
		
		public static class Adapter<KEY> extends Action.Adapter.Default<KEY, Boolean> implements ContainsKey<KEY>,Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<KEY> inputClass, KEY input) {
				super("contains key", inputClass, input, Boolean.class);
			}
			
			public static class Default<KEY> extends ContainsKey.Adapter<KEY> implements Serializable {
				private static final long serialVersionUID = 1L;
				
				public Default(Class<KEY> inputClass, KEY input) {
					super(inputClass, input);
				}
				
				@Override
				protected Boolean __execute__() {
					@SuppressWarnings("unchecked")
					Map<KEY,?> map = (Map<KEY, ?>) getProperty(PROPERTY_NAME_MAP);
					if(contains(map,getInput())){
						
					}
					map.containsKey(getInput());
					return super.__execute__();
				}
				
				protected Boolean contains(Map<KEY,?> map,KEY key){
					return map.containsKey(key);
				}
				
				protected void found(Map<KEY,?> map,Entry<KEY, ?> entry){
					setProperty(PROPERTY_NAME_KEY, entry.getKey());
				}
			}
			
		}
		
		/**/
		
		public static interface String extends ContainsKey<java.lang.String> {
			
			public static class Adapter extends ContainsKey.Adapter.Default<java.lang.String> implements String,Serializable {
				private static final long serialVersionUID = 1L;

				public Adapter(java.lang.String input) {
					super(java.lang.String.class, input);
				}
				
				public static class Default extends String.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;
					
					public Default(java.lang.String input) {
						super(input);
					}
					
					@Override
					protected Boolean __execute__() {
						@SuppressWarnings("unchecked")
						Map<java.lang.String,Object> map = (Map<java.lang.String, Object>) getProperty(PROPERTY_NAME_MAP);
						Boolean caseSensitive = (Boolean) getProperty(PROPERTY_NAME_CASE_SENSITIVE);
						if(caseSensitive==null || Boolean.TRUE.equals(caseSensitive))
							return map.containsKey(getInput());
						else for(Entry<java.lang.String, Object> entry : map.entrySet()){
							if(entry.getKey().equalsIgnoreCase(getInput())){
								found(map, entry);
								return Boolean.TRUE;
							}
						}
						return Boolean.FALSE;
					}
					
				}
				
			}
			
		}
		
	}

}
