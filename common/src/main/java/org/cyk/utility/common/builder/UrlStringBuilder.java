package org.cyk.utility.common.builder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.AbstractBuilder;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.ListenerUtils;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class UrlStringBuilder extends AbstractStringBuilder implements Serializable {
	private static final long serialVersionUID = 4504478526075696024L;

	public static final String SCHEME_HOST_PORT_STRING_FORMAT = "%s://%s%s%s";
	public static final String PATH_STRING_FORMAT = "%s%s%s";
	public static final String STRING_FORMAT = SCHEME_HOST_PORT_STRING_FORMAT+PATH_STRING_FORMAT;
	
	public static String SCHEME = null;
	public static String HOST = null;
	public static Integer PORT = null;
	
	private String scheme=SCHEME,host=HOST;
	private Integer port=PORT;
	private PathStringBuilder pathStringBuilder;
	private QueryStringBuilder queryStringBuilder;
	private Boolean relative,pretty;
	
	public PathStringBuilder getPathStringBuilder(){
		if(pathStringBuilder==null)
			pathStringBuilder = new PathStringBuilder().setUrlStringBuilder(this);
		return pathStringBuilder;
	}
	
	public QueryStringBuilder getQueryStringBuilder(){
		if(queryStringBuilder==null)
			queryStringBuilder = new QueryStringBuilder().setUrlStringBuilder(this);
		return queryStringBuilder;
	}
	
	@Override
	public String build() {
		//buildContext();
		//getPathStringBuilder().setAddSeparatorAtBeginning(getRelative());
		String pathString = getPathStringBuilder().build();
		String queryString = getQueryStringBuilder().build();
		StringBuilder stringBuilder = new StringBuilder(String.format(PATH_STRING_FORMAT,StringUtils.defaultIfBlank(pathString, Constant.EMPTY_STRING)
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
		
		public static class Adapter extends AbstractBuilder.Listener.Adapter.Default<String> implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
			
			}
		}
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class PathStringBuilder extends AbstractStringBuilder implements Serializable {
		private static final long serialVersionUID = -872728112292086623L;
		
		public static final String TOKEN_SEPARATOR = "/";
		
		public static String CONTEXT = null;
		public static String PATH_NOT_FOUND_IDENTIFIER = null;
		
		static {
			UrlStringBuilder.PathStringBuilder.Listener.COLLECTION.add(new UrlStringBuilder.PathStringBuilder.Listener.Adapter.Default());
		}
		
		private UrlStringBuilder urlStringBuilder;
		private List<String> tokens;
		private Boolean addSeparatorAtBeginning=Boolean.TRUE;
		private String context=CONTEXT;
		
		@Override
		public String build() {
			addToken(context,0);
			StringBuilder stringBuilder = new StringBuilder();
			if(StringUtils.isBlank(instance)){
				String separator = listenerUtils.getString(Listener.COLLECTION, new ListenerUtils.StringMethod<Listener>() {
					@Override
					public String execute(Listener listener) {
						return listener.getTokenSeparator();
					}
					@Override
					public String getNullValue() {
						return TOKEN_SEPARATOR;
					}
				});
				final String identifier = getIdentifier();
				if(StringUtils.isBlank(identifier)){
					stringBuilder.append(StringUtils.join(getTokens(),separator));	
				}else{
					String fromIdentifier = listenerUtils.getString(Listener.COLLECTION, new ListenerUtils.StringMethod<Listener>() {
						@Override
						public String execute(Listener listener) {
							return listener.getIdentifierMapping(identifier);
						}
					});	
					
					if(StringUtils.isBlank(fromIdentifier)){
						logWarning("path with identifier <<{}>> not found", identifier);
						fromIdentifier = listenerUtils.getString(Listener.COLLECTION, new ListenerUtils.StringMethod<Listener>() {
							@Override
							public String execute(Listener listener) {
								return listener.getIdentifierMapping(PATH_NOT_FOUND_IDENTIFIER);
							}
							
							@Override
							public String getNullValue() {
								return PATH_NOT_FOUND_IDENTIFIER;
							}
						});
				
					}
					
					stringBuilder.append(StringUtils.defaultIfBlank(fromIdentifier, Constant.EMPTY_STRING));
				}
				
				if(Boolean.TRUE.equals(getAddSeparatorAtBeginning()) && !StringUtils.startsWith(stringBuilder, separator))
					stringBuilder.insert(0,separator);
				
				for(Entry<String, String> entry : getTokenReplacementMap().entrySet())
					stringBuilder = new StringBuilder(StringUtils.replace(stringBuilder.toString(), entry.getKey(), entry.getValue()));
			}else{
				stringBuilder.append(instance);
			}
			
			return stringBuilder.toString();
		}
		
		public List<String> getTokens(){
			if(tokens == null)
				tokens = new ArrayList<>();
			return tokens;
		}
		
		public PathStringBuilder addToken(final Object token,Integer index){
			if(Boolean.TRUE.equals(listenerUtils.getBoolean(Listener.COLLECTION, new ListenerUtils.BooleanMethod<Listener>() {
				@Override
				public Boolean execute(Listener listener) {
					return listener.isToken(token);
				}
			}))){
				listenerUtils.execute(Listener.COLLECTION, new ListenerUtils.VoidMethod<Listener>() {
					@Override
					public void execute(Listener listener) {
						listener.listenBeforeAdd(PathStringBuilder.this, token);
					}
				});
				String string = listenerUtils.getString(Listener.COLLECTION, new ListenerUtils.StringMethod<Listener>() {
					@Override
					public String execute(Listener listener) {
						return listener.getTokenAsString(token);
					}
				});
				if(index==null)
					getTokens().add(string);
				else
					getTokens().add(index, string);
				listenerUtils.execute(Listener.COLLECTION, new ListenerUtils.VoidMethod<Listener>() {
					@Override
					public void execute(Listener listener) {
						listener.listenAfterAdded(PathStringBuilder.this, token);
					}
				});
			}
			
			return this;
		}
		
		public PathStringBuilder addToken(final Object token){
			return addToken(token, null);
		}
		
		public PathStringBuilder addTokens(Object...tokens){
			for(Object token : tokens)
				addToken(token);
			return this;
		}
				
		@Override
		public PathStringBuilder setIdentifier(String identifier) {
			return (PathStringBuilder) super.setIdentifier(identifier);
		}
		
		/**/
		
		/**/
		
		public static interface Listener extends AbstractStringBuilder.Listener {
			
			Collection<Listener> COLLECTION = new ArrayList<>();
			
			Boolean isToken(Object token);
			String getTokenAsString(Object token);
			String getTokenSeparator();
			void listenBeforeAdd(PathStringBuilder builder,Object token);
			void listenAfterAdded(PathStringBuilder builder,Object token);
			/**/
			
			public static class Adapter extends AbstractStringBuilder.Listener.Adapter.Default implements Listener,Serializable {
				private static final long serialVersionUID = 1L;
				
				@Override
				public void listenBeforeAdd(PathStringBuilder builder,Object token) {}
				
				@Override
				public void listenAfterAdded(PathStringBuilder builder,Object token) {}
								
				@Override
				public String getTokenAsString(Object token) {
					return null;
				}
				
				@Override
				public String getTokenSeparator() {
					return null;
				}
				
				@Override
				public Boolean isToken(Object token) {
					return null;
				}
								
				/**/
				
				public static class Default extends Listener.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;
					
					@Override
					public Boolean isToken(Object key) {
						return key == null 
							? Boolean.FALSE 
							: (key instanceof String ? StringUtils.isNotBlank((String)key) : Boolean.FALSE);
					}
															
					@Override
					public String getTokenSeparator() {
						return Constant.CHARACTER_SLASH.toString();
					}
					
					@Override
					public String getTokenAsString(Object key) {
						return key.toString();
					}
										
				}
				
			}
			
		}
		
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class QueryStringBuilder extends AbstractStringBuilder implements Serializable {
		private static final long serialVersionUID = -872728112292086623L;
		
		public static final String NAME_VALUE_STRING_FORMAT = "%s%s%s";
		
		static {
			UrlStringBuilder.QueryStringBuilder.Listener.COLLECTION.add(new UrlStringBuilder.QueryStringBuilder.Listener.Adapter.Default());
		}
		
		private UrlStringBuilder urlStringBuilder;
		//private Map<Object, List<Object>> parameters;
		private Collection<Parameter> parameters;
		
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
			for(Parameter parameter : getParameters()){
				final Parameter finalParameter = parameter;
				if(Boolean.TRUE.equals(listenerUtils.getBoolean(Listener.COLLECTION, new ListenerUtils.BooleanMethod<Listener>() {
					@Override
					public Boolean execute(Listener listener) {
						return listener.isParameterName(finalParameter.getName());
					}
				}))){
					final String name = listenerUtils.getString(Listener.COLLECTION, new ListenerUtils.StringMethod<Listener>() {
						@Override
						public String execute(Listener listener) {
							return listener.getParameterNameAsString(finalParameter.getName());
						}
					});
					
					final List<String> values = new ArrayList<>();
					for(Object value : finalParameter.getValues()){
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
		/*
		public Map<Object, List<Object>> getParameters(){
			if(parameters == null)
				parameters = new LinkedHashMap<>();
			return parameters;
		}
		*/
		public Collection<Parameter> getParameters(){
			if(parameters == null)
				parameters = new LinkedHashSet<>();
			return parameters;
		}
		
		private Parameter getParameter(Object key){
			for(Parameter parameter : getParameters())
				if(parameter.getName().equals(key))
					return parameter;
			return null;
		}
		
		public QueryStringBuilder addParameter(final Parameter parameter){
			listenerUtils.execute(Listener.COLLECTION, new ListenerUtils.VoidMethod<Listener>() {
				@Override
				public void execute(Listener listener) {
					listener.processBeforeAdd(QueryStringBuilder.this, parameter);
				}
			});
			
			Parameter existingParameter = getParameter(parameter.getName());
			if(existingParameter==null){
				getParameters().add(existingParameter = parameter);
			}else
				existingParameter.merge(parameter);
			
			listenerUtils.execute(Listener.COLLECTION, new ListenerUtils.VoidMethod<Listener>() {
				@Override
				public void execute(Listener listener) {
					listener.processAfterAdded(QueryStringBuilder.this, parameter);
				}
			});
			
			return this;
		}
		
		public QueryStringBuilder addParameter(final Object key,final Object value){
			Parameter parameter = new Parameter(key, value);
			return addParameter(parameter);
		}
		
		/**/
		
		@Getter @Setter @Accessors(chain=true) @EqualsAndHashCode(of={"name"})
		@AllArgsConstructor
		public static class Parameter implements Serializable {
			private static final long serialVersionUID = 1L;
			
			private Object name;
			private Collection<Object> values;
			private Boolean encoded;
			
			public Parameter(Object name,Object value,Boolean encoded) {
				this(name,null,encoded);
				getValues().add(value);
			}
			
			public Parameter(Object name,Object value) {
				this(name,value,Boolean.FALSE);
			}
			
			public Parameter(Object name) {
				this(name,null);
			}
			
			public Parameter addValue(Object value){
				getValues().add(value);
				return this;
			}
			
			public Collection<Object> getValues(){
				if(values==null)
					values = new ArrayList<>();
				return values;
			}
			
			public Parameter merge(Parameter parameter){
				if(!getName().equals(parameter.getName()))
					throw new RuntimeException("cannot merge different name");
				getValues().addAll(parameter.getValues());
				setEncoded(getEncoded()||parameter.getEncoded());
				return this;
			}
		}
		
		/**/
		
		public static interface Listener extends AbstractStringBuilder.Listener {
			
			Collection<Listener> COLLECTION = new ArrayList<>();
			
			Boolean isParameterName(Object key);
			Boolean isParameterValue(Object value);
			String getParameterNameAsString(Object key);
			String getParameterValueAsString(Object value);
			String getParameterNameAndValueSeparator();
			String getParameterAsString(String key,List<String> values,String nameValueSeparator,String parameterSeparator);
			String getParametersSeparator();
			void processBeforeAdd(QueryStringBuilder queryStringBuilder,Parameter parameter);
			void processAfterAdded(QueryStringBuilder queryStringBuilder,Parameter parameter);
			/**/
			
			public static class Adapter extends AbstractStringBuilder.Listener.Adapter.Default implements Listener,Serializable {
				private static final long serialVersionUID = 1L;
				
				@Override
				public void processBeforeAdd(QueryStringBuilder queryStringBuilder, Parameter parameter) {}
				
				@Override
				public void processAfterAdded(QueryStringBuilder queryStringBuilder, Parameter parameter) {}
				
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
		
		/**/
		
		@Getter @Setter @Accessors(chain=true)
		public static class ParameterStringBuilder extends NameValueStringBuilder implements Serializable {
			private static final long serialVersionUID = -872728112292086623L;
			
			static {
				QueryStringBuilder.ParameterStringBuilder.Listener.COLLECTION.add(new QueryStringBuilder.ParameterStringBuilder.Listener.Adapter.Default());
			}
			
			private QueryStringBuilder queryStringBuilder;
				
			/**/
			
			/**/
			
			public static interface Listener extends NameValueStringBuilder.Listener {
				
				Collection<Listener> COLLECTION = new ArrayList<>();
				
				Boolean isName(Object key);
				Boolean isValue(Object value);
				String getNameAsString(Object key);
				String getValueAsString(Object value);
				String getNameAndValueSeparator();
				String getAsString(String key,List<String> values,String nameValueSeparator,String parameterSeparator);
				
				public static class Adapter extends NameValueStringBuilder.Listener.Adapter.Default implements Listener,Serializable {
					private static final long serialVersionUID = 1L;
								
					/**/
					
					public static class Default extends Listener.Adapter implements Serializable {
						private static final long serialVersionUID = 1L;
						
					}

				}
				
			}
			
		}
		
	}

}
