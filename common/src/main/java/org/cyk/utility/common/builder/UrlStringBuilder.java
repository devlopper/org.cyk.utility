package org.cyk.utility.common.builder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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
public class UrlStringBuilder extends AbstractBuilder<String> implements Serializable {
	private static final long serialVersionUID = 4504478526075696024L;

	public static final String SCHEME_HOST_PORT_STRING_FORMAT = "%s://%s%s%s/";
	public static final String PATH_STRING_FORMAT = "%s%s%s";
	public static final String STRING_FORMAT = SCHEME_HOST_PORT_STRING_FORMAT+PATH_STRING_FORMAT;
	
	public static String SCHEME = null;
	public static String HOST = null;
	public static Integer PORT = null;
	public static String CONTEXT = null;
	public static String PATH_NOT_FOUND_IDENTIFIER = null;
	
	private String scheme=SCHEME,host=HOST,context=CONTEXT,path,pathIdentifier;
	private Integer port=PORT;
	private QueryStringBuilder queryStringBuilder;
	private Map<String,String> pathTokenReplacementMap;
	private Boolean relative,pretty;
	
	public UrlStringBuilder setScheme(String scheme){
		this.scheme = scheme;
		return this;
	}
	
	public UrlStringBuilder setHost(String host){
		this.host = host;
		return this;
	}
	
	public UrlStringBuilder setPath(String path){
		this.path = path;
		return this;
	}
	
	public UrlStringBuilder setPathIdentifier(String pathIdentifier){
		this.pathIdentifier = pathIdentifier;
		return this;
	}
	
	public UrlStringBuilder setContext(String context){
		this.context = context;
		return this;
	}
	
	public UrlStringBuilder setPort(Integer port){
		this.port = port;
		return this;
	}
	
	public UrlStringBuilder setRelative(Boolean relative){
		this.relative = relative;
		return this;
	}
	
	public UrlStringBuilder addPathTokenReplacement(String token,String replacement){
		getPathTokenReplacementMap().put(token, replacement);
		return this;
	}
	
	public QueryStringBuilder getQueryStringBuilder(){
		if(queryStringBuilder==null)
			queryStringBuilder = new QueryStringBuilder();
		return queryStringBuilder;
	}
	
	public Map<String,String> getPathTokenReplacementMap(){
		if(pathTokenReplacementMap==null)
			pathTokenReplacementMap = new HashMap<>();
		return pathTokenReplacementMap;
	}
	
	@Override
	public String build() {
		if(StringUtils.isBlank(path)){
			if(StringUtils.isBlank(pathIdentifier)){
				
			}else{
				path = listenerUtils.getString(Listener.COLLECTION, new ListenerUtils.StringMethod<Listener>() {
					@Override
					public String execute(Listener listener) {
						return listener.getPathFromIdentifier(pathIdentifier);
					}
				});	
				
				if(StringUtils.isBlank(path))
					path = listenerUtils.getString(Listener.COLLECTION, new ListenerUtils.StringMethod<Listener>() {
						@Override
						public String execute(Listener listener) {
							return listener.getPathFromIdentifier(PATH_NOT_FOUND_IDENTIFIER);
						}
						
						@Override
						public String getNullValue() {
							return PATH_NOT_FOUND_IDENTIFIER;
						}
					});
			}
			
		}
		
		context = StringUtils.replace(context,Constant.CHARACTER_SLASH.toString(),Constant.EMPTY_STRING);
		path = StringUtils.replaceOnce(path,Constant.CHARACTER_SLASH.toString(),Constant.EMPTY_STRING);
		for(Entry<String, String> entry : getPathTokenReplacementMap().entrySet())
			path = StringUtils.replace(path, entry.getKey(), entry.getValue());
		
		String queryString = getQueryStringBuilder().build();
		StringBuilder stringBuilder = new StringBuilder(String.format(PATH_STRING_FORMAT,(StringUtils.isBlank(context) ? Constant.EMPTY_STRING : context+Constant.CHARACTER_SLASH)+StringUtils.defaultIfBlank(path, Constant.EMPTY_STRING)
				,StringUtils.isBlank(queryString) ? Constant.EMPTY_STRING : Constant.CHARACTER_QUESTION_MARK,StringUtils.defaultIfBlank(queryString, Constant.EMPTY_STRING)));
		if(Boolean.TRUE.equals(relative)){
			if(!Constant.CHARACTER_SLASH.equals(stringBuilder.charAt(0)))
				stringBuilder.insert(0, Constant.CHARACTER_SLASH.toString());
		}else
			stringBuilder.insert(0, String.format(SCHEME_HOST_PORT_STRING_FORMAT, scheme,host
					,port == null ? Constant.EMPTY_STRING : Constant.CHARACTER_COLON,port == null ? Constant.EMPTY_STRING : port));
		
		return stringBuilder.toString();
	}
	
	/**/
	
	public static interface Listener extends AbstractBuilder.Listener<String> {
		
		Collection<Listener> COLLECTION = new ArrayList<>();
		
		String getPathFromIdentifier(String identifier);
		
		public static class Adapter extends AbstractBuilder.Listener.Adapter.Default<String> implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
			
			@Override
			public String getPathFromIdentifier(String identifier) {
				return null;
			}
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
			
			}
		}
	}
	
	/**/
	
	public static class QueryStringBuilder extends AbstractBuilder<String> implements Serializable {
		private static final long serialVersionUID = -872728112292086623L;
		
		public static final String NAME_VALUE_STRING_FORMAT = "%s%s%s";
		
		static {
			UrlStringBuilder.QueryStringBuilder.Listener.COLLECTION.add(new UrlStringBuilder.QueryStringBuilder.Listener.Adapter.Default());
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
		
		public QueryStringBuilder addParameter(final Object key,final Object value){
			List<Object> values = getParameters().get(key);
			if(values==null){
				values = new ArrayList<>();
				getParameters().put(key, values);
			}
			listenerUtils.execute(Listener.COLLECTION, new ListenerUtils.VoidMethod<Listener>() {
				@Override
				public void execute(Listener listener) {
					listener.processBeforeAdd(QueryStringBuilder.this, key, value);
				}
			});
			values.add(value);
			listenerUtils.execute(Listener.COLLECTION, new ListenerUtils.VoidMethod<Listener>() {
				@Override
				public void execute(Listener listener) {
					listener.processAfterAdded(QueryStringBuilder.this, key, value);
				}
			});
			return this;
		}
		
		/**/
		
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
			void processBeforeAdd(QueryStringBuilder queryStringBuilder,Object key,Object value);
			void processAfterAdded(QueryStringBuilder queryStringBuilder,Object key,Object value);
			/**/
			
			public static class Adapter extends AbstractBuilder.Listener.Adapter.Default<String> implements Listener,Serializable {
				private static final long serialVersionUID = 1L;
				
				@Override
				public void processBeforeAdd(QueryStringBuilder queryStringBuilder, Object key, Object value) {}
				
				@Override
				public void processAfterAdded(QueryStringBuilder queryStringBuilder, Object key, Object value) {}
				
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
						return Constant.CHARACTER_AMPERSTAMP.toString();
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
