package org.cyk.utility.common.builder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.AbstractBuilder;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.ListenerUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor @Deprecated
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
	private Object request;
		
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
	
	public UrlStringBuilder addFiles(final Collection<?> files){
		getPathStringBuilder().addFiles(files);
		getQueryStringBuilder().getNameValueCollectionStringBuilder().addFiles(files);
		return this;
	}
	
	@Override
	public String buildWhenBlank() {
		final Object request = listenerUtils.getObject(Listener.COLLECTION, new ListenerUtils.ObjectMethod<Listener>() {
			@Override
			public Object execute(Listener listener) {
				return listener.getRequest();
			}
		});
		if(request!=null){
			scheme = listenerUtils.getString(Listener.COLLECTION, new ListenerUtils.StringMethod<Listener>() {
				@Override
				public String execute(Listener listener) {
					return listener.getScheme(request);
				}
				public String getNullValue() {
					return scheme;
				}
			});
			
			host = listenerUtils.getString(Listener.COLLECTION, new ListenerUtils.StringMethod<Listener>() {
				@Override
				public String execute(Listener listener) {
					return listener.getHost(request);
				}
				public String getNullValue() {
					return host;
				}
			});
			
			port = listenerUtils.getInteger(Listener.COLLECTION, new ListenerUtils.IntegerMethod<Listener>() {
				@Override
				public Integer execute(Listener listener) {
					return listener.getPort(request);
				}
				public Integer getNullValue() {
					return port;
				}
			});
		}
		
		String pathString = getPathStringBuilder().setRequest(request).build();
		String queryString = getQueryStringBuilder().build();
		java.lang.StringBuilder stringBuilder = new java.lang.StringBuilder(String.format(PATH_STRING_FORMAT,StringUtils.defaultIfBlank(pathString, Constant.EMPTY_STRING)
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
	@Deprecated
	public static interface Listener extends AbstractBuilder.Listener<String> {
		
		Collection<Listener> COLLECTION = new ArrayList<>();
		
		Object getRequest();
		
		String getScheme(Object request);
		String getHost(Object request);
		Integer getPort(Object request);
		
		public static class Adapter extends AbstractBuilder.Listener.Adapter.Default<String> implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
			
			@Override
			public Object getRequest() {
				return null;
			}
			
			@Override
			public String getScheme(Object request) {
				return null;
			}
			
			@Override
			public String getHost(Object request) {
				return null;
			}
			
			@Override
			public Integer getPort(Object request) {
				return null;
			}
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
			
			}
		}
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true) @Deprecated
	public static class PathStringBuilder extends AbstractStringBuilder implements Serializable {
		private static final long serialVersionUID = -872728112292086623L;
		
		public static final String TOKEN_SEPARATOR = "/";
		
		public static String CONTEXT = null;
		public static String PATH_NOT_FOUND_IDENTIFIER = null;
		
		private UrlStringBuilder urlStringBuilder;
		private List<String> tokens;
		private Boolean addSeparatorAtBeginning=Boolean.TRUE;
		private String context=CONTEXT;
		private Object request;
		private Object action;
		private Object subject;
		
		public PathStringBuilder addFiles(Collection<?> files){
			setIdentifier(listenerUtils.getString(Listener.COLLECTION, new ListenerUtils.StringMethod<Listener>() {
				@Override
				public String execute(Listener listener) {
					return listener.getConsultFilesIdentifier();
				}
				public String getNullValue() {
					return identifier;
				}
			}));
			return this;
		}
		
		@Override
		public String buildWhenBlank() {
			context = listenerUtils.getString(Listener.COLLECTION, new ListenerUtils.StringMethod<Listener>() {
				@Override
				public String execute(Listener listener) {
					return listener.getContext(request);
				}
				public String getNullValue() {
					return context;
				}
			});
			
			addToken(context,0);
			java.lang.StringBuilder stringBuilder = new java.lang.StringBuilder();
			
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
				
				stringBuilder.append(StringUtils.join(getTokens(),separator)+StringUtils.defaultIfBlank(fromIdentifier, Constant.EMPTY_STRING));
			}
			
			if(Boolean.TRUE.equals(getAddSeparatorAtBeginning()) && !StringUtils.startsWith(stringBuilder, separator))
				stringBuilder.insert(0,separator);
			
			for(Entry<String, String> entry : getTokenReplacementMap().entrySet())
				stringBuilder = new java.lang.StringBuilder(StringUtils.replace(stringBuilder.toString(), entry.getKey(), entry.getValue()));
			
			
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
				@Override
				public Boolean getNullValue() {
					return token == null 
							? Boolean.FALSE 
							: (token instanceof String ? StringUtils.isNotBlank((String)token) : Boolean.FALSE);
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
					@Override
					public String getNullValue() {
						return token.toString();
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
		@Deprecated
		public static interface Listener extends AbstractStringBuilder.Listener {
			
			Collection<Listener> COLLECTION = new ArrayList<>();
			
			Boolean isToken(Object token);
			String getTokenAsString(Object token);
			String getTokenSeparator();
			void listenBeforeAdd(PathStringBuilder builder,Object token);
			void listenAfterAdded(PathStringBuilder builder,Object token);
			
			String getContext(Object request);
			String getConsultFilesIdentifier();
			
			
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
				
				@Override
				public String getContext(Object request) {
					return null;
				}
				
				@Override
				public String getConsultFilesIdentifier() {
					return null;
				}
								
				/**/
				
				public static class Default extends Listener.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;
																									
				}
				
			}
			
		}
		
		/**/
		
		@Getter @Setter @NoArgsConstructor @Accessors(chain=true) @Deprecated
		public static class IdentifierBuilder extends AbstractIdentifierBuilder implements Serializable {
			private static final long serialVersionUID = -872728112292086623L;
					
			@Override
			protected Collection<Listener> getListeners() {
				return Listener.COLLECTION;
			}
				
			/**/
			@Deprecated
			public static interface Listener extends AbstractIdentifierBuilder.Listener {
				
				Collection<Listener> COLLECTION = new ArrayList<>();
				
				public static class Adapter extends AbstractIdentifierBuilder.Listener.Adapter.Default implements Listener,Serializable {
					private static final long serialVersionUID = 1L;
						
					/**/
					
					public static class Default extends Listener.Adapter implements Serializable {
						private static final long serialVersionUID = 1L;
						
					}
				}
			}
		}
	}
	
	/**/
	
	@Deprecated
	@Getter @Setter @Accessors(chain=true)
	public static class QueryStringBuilder extends AbstractStringBuilder implements Serializable {
		private static final long serialVersionUID = -872728112292086623L;
		
		private UrlStringBuilder urlStringBuilder;
		private NameValueCollectionStringBuilder nameValueCollectionStringBuilder = new NameValueCollectionStringBuilder();
		
		public QueryStringBuilder addParameter(Object name,Object value){
			getNameValueCollectionStringBuilder().add(new NameValueStringBuilder(name, value));
			return this;
		}
		
		public QueryStringBuilder addIdentifiableFiles(final Collection<?> files){
			getNameValueCollectionStringBuilder().addFiles(files);
			return this;
		}
		
		@Override
		public String buildWhenBlank() {
			nameValueCollectionStringBuilder.setSeparator(Constant.CHARACTER_AMPERSTAMP.toString());
			return nameValueCollectionStringBuilder.build();
		}
				
		/**/
				
		/**/
		
		public static interface Listener extends AbstractStringBuilder.Listener {
			
			Collection<Listener> COLLECTION = new ArrayList<>();
			
			/**/
			
			public static class Adapter extends AbstractStringBuilder.Listener.Adapter.Default implements Listener,Serializable {
				private static final long serialVersionUID = 1L;
				
				/**/
				
				public static class Default extends Listener.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;
					
				}
				
			}
			
		}
		
		/**/
		
		
		
	}

}
