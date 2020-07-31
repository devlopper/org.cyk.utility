package org.cyk.utility.__kernel__.rest;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.JsonHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.protocol.http.HttpHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.Value;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface ResourceGetter {

	<T> List<T> get(Arguments<T> arguments);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements ResourceGetter,Serializable {
		
		@Override
		public <T> List<T> get(Arguments<T> arguments) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("resource getter arguments", arguments);
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("resource getter klass", arguments.klass);
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("resource getter uri", arguments.uri);			
			return __get__(arguments);
		}
		
		protected <T> List<T> __get__(Arguments<T> arguments) {
			//1 - get json string
			HttpResponse<String> response = HttpHelper.get(arguments.uri);
			String responseBody = response.body();
			if(StringHelper.isBlank(responseBody))
				return null;
			//2 - build collection
			if(MapHelper.isEmpty(arguments.fieldsNamesMap))
				return JsonHelper.getList(arguments.klass, responseBody,arguments.type);
			return null;
			/*
			//3 - build collection of map from json
			Collection<Map<String,String>> maps = JsonbBuilder.create().fromJson(responseBody, new ArrayList<Map<String,String>>(){
				private static final long serialVersionUID = 1L;}.getClass().getGenericSuperclass());
			if(CollectionHelper.isEmpty(maps))
				return null;
			
			//4 - copy map to resource instance
			Collection<T> resources = new ArrayList<>();
			for(Map<String, String> map : maps) {
				T resource = ClassHelper.instanciate(arguments.klass);
				InstanceHelper.copy(map, resource, arguments.fieldsNamesMap);
				resources.add(resource);
			}
			return resources;
			*/
		}
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments<T> {
		private Class<T> klass;
		private Type type;
		private String uri;
		private Integer firstElementIndex;
		private Integer numberOfElements;
		private Map<String,String> fieldsNamesMap;
	}
	
	static ResourceGetter getInstance() {
		return Helper.getInstance(ResourceGetter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();	
}