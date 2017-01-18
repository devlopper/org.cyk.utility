package org.cyk.utility.common.builder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.AbstractBuilder;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.ListenerUtils;

import lombok.Getter;

@Getter
public class UrlBuilder extends AbstractBuilder<String> implements Serializable {
	private static final long serialVersionUID = 4504478526075696024L;

	public static final String PATH_STRING_FORMAT = "%s%s%s";
	public static final String STRING_FORMAT = "%s://%s%s%s/"+PATH_STRING_FORMAT;
	
	private String scheme,host,path;
	private Integer port;
	private QueryStringBuilder queryStringBuilder;
	
	public UrlBuilder setScheme(String scheme){
		this.scheme = scheme;
		return this;
	}
	
	public UrlBuilder setHost(String host){
		this.host = host;
		return this;
	}
	
	public UrlBuilder setPath(String path){
		this.path = path;
		return this;
	}
	
	public UrlBuilder setPort(Integer port){
		this.port = port;
		return this;
	}
	
	@Override
	public String build() {
		String queryString = getQueryStringBuilder().build();
		return String.format(STRING_FORMAT, scheme,host,port == null ? Constant.EMPTY_STRING : Constant.CHARACTER_COLON,port == null ? Constant.EMPTY_STRING : port
				,StringUtils.defaultIfBlank(path, Constant.EMPTY_STRING),StringUtils.isBlank(queryString) ? Constant.EMPTY_STRING : Constant.CHARACTER_QUESTION_MARK
						,StringUtils.defaultIfBlank(queryString, Constant.EMPTY_STRING));
	}
	
	public QueryStringBuilder getQueryStringBuilder(){
		if(queryStringBuilder==null)
			queryStringBuilder = new QueryStringBuilder();
		return queryStringBuilder;
	}

	/**/
	
	public static class QueryStringBuilder extends AbstractBuilder<String> implements Serializable {
		private static final long serialVersionUID = -872728112292086623L;
		
		public static final String NAME_VALUE_STRING_FORMAT = "%s%s%s";
		
		static {
			UrlBuilder.QueryStringBuilder.Listener.COLLECTION.add(new UrlBuilder.QueryStringBuilder.Listener.Adapter.Default());
		}
		
		private Map<Object, List<Object>> parameters;
		
		@Override
		public String build() {
			Collection<String> tokens = new ArrayList<>();
			final String nameValueSeparator = listenerUtils.getString(Listener.COLLECTION, new ListenerUtils.StringMethod<Listener>() {
				@Override
				public String execute(Listener listener) {
					return listener.getParameterNameAndValueSeparator();
				}
			});
			final String parametersSeparator = listenerUtils.getString(Listener.COLLECTION, new ListenerUtils.StringMethod<Listener>() {
				@Override
				public String execute(Listener listener) {
					return listener.getParametersSeparator();
				}
			});
			for(Entry<Object, List<Object>> entry : getParameters().entrySet()){
				final Entry<Object, List<Object>> finalEntry = entry;
				if(Boolean.TRUE.equals(listenerUtils.getBoolean(Listener.COLLECTION, new ListenerUtils.BooleanMethod<Listener>() {
					@Override
					public Boolean execute(Listener listener) {
						return listener.isParameterName(finalEntry.getKey());
					}
				}))){
					final String name = listenerUtils.getString(Listener.COLLECTION, new ListenerUtils.StringMethod<Listener>() {
						@Override
						public String execute(Listener listener) {
							return listener.getParameterNameAsString(finalEntry.getKey());
						}
					});
					
					final List<String> values = new ArrayList<>();
					for(Object value : finalEntry.getValue()){
						final Object finalValue = value;
						if(Boolean.TRUE.equals(listenerUtils.getBoolean(Listener.COLLECTION, new ListenerUtils.BooleanMethod<Listener>() {
							@Override
							public Boolean execute(Listener listener) {
								return listener.isParameterValue(finalValue);
							}
						}))){
							values.add(listenerUtils.getString(Listener.COLLECTION, new ListenerUtils.StringMethod<Listener>() {
								@Override
								public String execute(Listener listener) {
									return listener.getParameterValueAsString(finalValue);
								}
							}));	
						}	
					}
					if(!values.isEmpty())
						tokens.add(listenerUtils.getString(Listener.COLLECTION, new ListenerUtils.StringMethod<Listener>() {
							@Override
							public String execute(Listener listener) {
								return listener.getParameterAsString(name, values, nameValueSeparator, parametersSeparator);
							}
						}));
				}
			}
			return StringUtils.join(tokens,Constant.CHARACTER_AMPERSTAMP);
		}
		
		public Map<Object, List<Object>> getParameters(){
			if(parameters == null)
				parameters = new LinkedHashMap<>();
			return parameters;
		}
		
		public QueryStringBuilder addParameter(Object key,Object value){
			
			List<Object> values = getParameters().get(key);
			if(values==null){
				values = new ArrayList<>();
				getParameters().put(key, values);
			}
			values.add(value);
			return this;
		}
		
		/**/
		
		public static interface Listener extends AbstractBuilder.Listener<String> {
			
			Collection<Listener> COLLECTION = new ArrayList<>();
			
			Boolean isParameterName(Object key);
			Boolean isParameterValue(Object value);
			String getParameterNameAsString(Object key);
			String getParameterValueAsString(Object value);
			String getParameterNameAndValueSeparator();
			String getParameterAsString(String key,List<String> values,String nameValueSeparator,String parameterSeparator);
			String getParametersSeparator();
			
			/**/
			
			public static class Adapter extends AbstractBuilder.Listener.Adapter.Default<String> implements Listener,Serializable {
				private static final long serialVersionUID = 1L;
				
				@Override
				public String getParameterNameAndValueSeparator() {
					return null;
				}
				
				@Override
				public String getParameterNameAsString(Object key) {
					return null;
				}
				
				@Override
				public String getParametersSeparator() {
					return null;
				}
				
				@Override
				public String getParameterValueAsString(Object value) {
					return null;
				}
				
				@Override
				public String getParameterAsString(String key, List<String> values,String nameValueSeparator,String parameterSeparator) {
					return null;
				}
				
				@Override
				public Boolean isParameterName(Object key) {
					return null;
				}
				
				@Override
				public Boolean isParameterValue(Object value) {
					return null;
				}
				
				/**/
				
				public static class Default extends Listener.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;
					
					@Override
					public Boolean isParameterName(Object key) {
						return key == null 
							? Boolean.FALSE 
							: (key instanceof String ? StringUtils.isNotBlank((String)key) : Boolean.FALSE);
					}
					
					@Override
					public Boolean isParameterValue(Object value) {
						return value!=null || (value instanceof String && StringUtils.isNotBlank((String)value));
					}
					
					@Override
					public String getParameterNameAndValueSeparator() {
						return Constant.CHARACTER_EQUAL.toString();
					}
					
					@Override
					public String getParametersSeparator() {
						return Constant.CHARACTER_QUESTION_MARK.toString();
					}
					
					@Override
					public String getParameterNameAsString(Object key) {
						return key.toString();
					}
					
					@Override
					public String getParameterValueAsString(Object value) {
						return value.toString();
					}
					
					@Override
					public String getParameterAsString(String key, List<String> values,String nameValueSeparator,String parameterSeparator) {
						Collection<String> parameterTokens = new ArrayList<>();
						for(String value : values)
							parameterTokens.add(String.format(NAME_VALUE_STRING_FORMAT, key,nameValueSeparator,value));
						return StringUtils.join(parameterTokens,parameterSeparator);
					}
				}
				
			}
			
		}
		
	}

}
