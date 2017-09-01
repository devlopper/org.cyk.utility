package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.helper.MapHelper.Stringifier.Entry.OutputStrategy;

import lombok.Getter;

@Singleton
public class UniformResourceLocatorHelper extends AbstractHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	private static UniformResourceLocatorHelper INSTANCE;
	
	public static UniformResourceLocatorHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new UniformResourceLocatorHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public static interface Stringifier extends org.cyk.utility.common.Builder.Stringifier<Object> {
		
		String SCHEME_HOST_PORT_FORMAT = "%s://%s%s%s";
		
		String getScheme();
		UniformResourceLocatorHelper.Stringifier setScheme(String scheme);
		
		String getHost();
		UniformResourceLocatorHelper.Stringifier setHost(String host);
		
		Integer getPort();
		UniformResourceLocatorHelper.Stringifier setPort(Integer port);
		
		PathStringifier getPathStringifier();
		PathStringifier getPathStringifier(Boolean createIfNull);
		UniformResourceLocatorHelper.Stringifier setPathStringifier(PathStringifier pathStringifier);
		UniformResourceLocatorHelper.Stringifier addPathTokens(String...tokens);
		UniformResourceLocatorHelper.Stringifier setPathContext(String context);
		
		QueryStringifier getQueryStringifier();
		QueryStringifier getQueryStringifier(Boolean createIfNull);
		UniformResourceLocatorHelper.Stringifier setQueryStringifier(QueryStringifier queryStringifier);
		UniformResourceLocatorHelper.Stringifier addQueryKeyValue(Object...objects);
		
		Boolean getRelative();
		UniformResourceLocatorHelper.Stringifier setRelative(Boolean relative);
		
		Boolean getPretty();
		UniformResourceLocatorHelper.Stringifier setPretty(Boolean pretty);
		
		Object getRequest();
		UniformResourceLocatorHelper.Stringifier setRequest(Object request);
		
		String getQuerySeparator();
		UniformResourceLocatorHelper.Stringifier setQuerySeparator(String querySeparator);
		
		@Getter
		public static class Adapter extends org.cyk.utility.common.Builder.Stringifier.Adapter.Default<Object> implements UniformResourceLocatorHelper.Stringifier,Serializable {
			private static final long serialVersionUID = 1L;

			protected String scheme,host,querySeparator;
			protected Integer port;
			protected PathStringifier pathStringifier;
			protected QueryStringifier queryStringifier;
			protected Boolean relative,pretty;
			protected Object request;
			
			public Adapter() {
				super(Object.class,null);
			}
			
			@Override
			public org.cyk.utility.common.helper.UniformResourceLocatorHelper.Stringifier setPathContext(String context) {
				return null;
			}
			
			@Override
			public org.cyk.utility.common.helper.UniformResourceLocatorHelper.Stringifier addPathTokens(String...tokens) {
				return null;
			}
			
			@Override
			public org.cyk.utility.common.helper.UniformResourceLocatorHelper.Stringifier addQueryKeyValue(Object...objects) {
				return null;
			}
			
			@Override
			public org.cyk.utility.common.helper.UniformResourceLocatorHelper.Stringifier setQuerySeparator(String querySeparator) {
				return null;
			}
			
			@Override
			public org.cyk.utility.common.helper.UniformResourceLocatorHelper.Stringifier setScheme(String scheme) {
				return null;
			}

			@Override
			public org.cyk.utility.common.helper.UniformResourceLocatorHelper.Stringifier setHost(String host) {
				return null;
			}

			@Override
			public org.cyk.utility.common.helper.UniformResourceLocatorHelper.Stringifier setPort(Integer port) {
				return null;
			}

			@Override
			public org.cyk.utility.common.helper.UniformResourceLocatorHelper.Stringifier setPathStringifier(PathStringifier pathStringifier) {
				return null;
			}

			@Override
			public org.cyk.utility.common.helper.UniformResourceLocatorHelper.Stringifier setQueryStringifier(QueryStringifier queryStringifier) {
				return null;
			}

			@Override
			public org.cyk.utility.common.helper.UniformResourceLocatorHelper.Stringifier setRelative(Boolean relative) {
				return null;
			}

			@Override
			public org.cyk.utility.common.helper.UniformResourceLocatorHelper.Stringifier setPretty(Boolean pretty) {
				return null;
			}

			@Override
			public org.cyk.utility.common.helper.UniformResourceLocatorHelper.Stringifier setRequest(Object request) {
				return null;
			}
			
			@Override
			public PathStringifier getPathStringifier(Boolean createIfNull) {
				return null;
			}
			
			@Override
			public QueryStringifier getQueryStringifier(Boolean createIfNull) {
				return null;
			}
			
			/**/
			
			public static class Default extends UniformResourceLocatorHelper.Stringifier.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

				public static final String SCHEME_HOST_PORT_FORMAT = "%s://%s%s%s";
				
				public static String DEFAULT_SCHEME,DEFAULT_HOST,DEFAULT_QUERY_SEPARATOR=Constant.CHARACTER_QUESTION_MARK.toString();
				public static  Integer DEFAULT_PORT;
				public static  Boolean DEFAULT_RELATIVE,DEFAULT_PRETTY;
				
				public Default() {
					setIsInputRequired(Boolean.FALSE);
					setPort(DEFAULT_PORT);
				}
				
				@Override
				protected String __execute__() {
					List<String> tokens = new ArrayList<>();
					Boolean relative = InstanceHelper.getInstance().getIfNotNullElseDefault(getRelative(),DEFAULT_RELATIVE);
					QueryStringifier queryStringifier = getQueryStringifier();
					String query = queryStringifier == null ? Constant.EMPTY_STRING : queryStringifier.execute();
					if(StringHelper.getInstance().isNotBlank(query)){
						tokens.add(InstanceHelper.getInstance().getIfNotNullElseDefault(getQuerySeparator(),DEFAULT_QUERY_SEPARATOR));
						tokens.add(query);
					}
					PathStringifier pathStringifier = getPathStringifier();
					if(pathStringifier==null && StringHelper.getInstance().isNotBlank(query)){
						pathStringifier = getPathStringifier(Boolean.TRUE);
					}
					if(pathStringifier!=null && StringHelper.getInstance().isNotBlank(query)){
						pathStringifier.setAddSeparatorAfterContext(Boolean.TRUE);
					}
					String path = pathStringifier == null ? Constant.EMPTY_STRING : pathStringifier.execute();
					if(StringHelper.getInstance().isNotBlank(path))
						tokens.add(0,path);
					
					if(Boolean.TRUE.equals(relative)){
						
					}else{
						String scheme = InstanceHelper.getInstance().getIfNotNullElseDefault(getScheme(),DEFAULT_SCHEME);
						String host = InstanceHelper.getInstance().getIfNotNullElseDefault(getHost(),DEFAULT_HOST);
						Integer port = getPort();
						Integer defaultPort = UniformResourceLocatorHelper.SCHEME_DEFAULT_PORT_MAP.get(scheme);
						Boolean isDefaultPort = port == null || defaultPort!=null && defaultPort == port;
						tokens.add(0,String.format(SCHEME_HOST_PORT_FORMAT, scheme,host,isDefaultPort ? Constant.EMPTY_STRING : Constant.CHARACTER_COLON.toString()
								,isDefaultPort ? Constant.EMPTY_STRING : port));
					}
					return StringHelper.getInstance().concatenate(tokens, null);
				}
				
				@Override
				public org.cyk.utility.common.helper.UniformResourceLocatorHelper.Stringifier setPathContext(String context) {
					getPathStringifier(Boolean.TRUE).setContext(context);
					return this;
				}
				
				@Override
				public org.cyk.utility.common.helper.UniformResourceLocatorHelper.Stringifier addPathTokens(String...tokens) {
					getPathStringifier(Boolean.TRUE).addTokens(tokens);
					return this;
				}
				
				@Override
				public org.cyk.utility.common.helper.UniformResourceLocatorHelper.Stringifier addQueryKeyValue(Object...objects) {
					getQueryStringifier(Boolean.TRUE).addKeyValue(objects);
					return this;
				}
				
				@Override
				public org.cyk.utility.common.helper.UniformResourceLocatorHelper.Stringifier setQuerySeparator(String querySeparator) {
					this.querySeparator = querySeparator;
					return this;
				}
				
				@Override
				public org.cyk.utility.common.helper.UniformResourceLocatorHelper.Stringifier setScheme(String scheme) {
					this.scheme = scheme;
					return this;
				}

				@Override
				public org.cyk.utility.common.helper.UniformResourceLocatorHelper.Stringifier setHost(String host) {
					this.host = host;
					return this;
				}

				@Override
				public org.cyk.utility.common.helper.UniformResourceLocatorHelper.Stringifier setPort(Integer port) {
					this.port = port;
					return this;
				}

				@Override
				public org.cyk.utility.common.helper.UniformResourceLocatorHelper.Stringifier setPathStringifier(PathStringifier pathStringifier) {
					this.pathStringifier = pathStringifier;
					return this;
				}

				@Override
				public org.cyk.utility.common.helper.UniformResourceLocatorHelper.Stringifier setQueryStringifier(QueryStringifier queryStringifier) {
					this.queryStringifier = queryStringifier;
					return this;
				}

				@Override
				public org.cyk.utility.common.helper.UniformResourceLocatorHelper.Stringifier setRelative(Boolean relative) {
					this.relative = relative;
					return this;
				}

				@Override
				public org.cyk.utility.common.helper.UniformResourceLocatorHelper.Stringifier setPretty(Boolean pretty) {
					this.pretty = pretty;
					return this;
				}

				@Override
				public org.cyk.utility.common.helper.UniformResourceLocatorHelper.Stringifier setRequest(Object request) {
					this.request = request;
					return this;
				}	
				
				@Override
				public PathStringifier getPathStringifier(Boolean createIfNull) {
					if(this.pathStringifier == null && Boolean.TRUE.equals(createIfNull))
						setPathStringifier(new PathStringifier.Adapter.Default());
					getPathStringifier().setProperty(PROPERTY_NAME_PARENT, this);
					return getPathStringifier();
				}
				
				@Override
				public QueryStringifier getQueryStringifier(Boolean createIfNull) {
					if(this.queryStringifier == null && Boolean.TRUE.equals(createIfNull))
						setQueryStringifier(new QueryStringifier.Adapter.Default());
					getQueryStringifier().setProperty(PROPERTY_NAME_PARENT, this);
					return getQueryStringifier();
				}
				
			}
		}
	}
	
	public static interface PathStringifier extends org.cyk.utility.common.helper.StringHelper.Builder.Collection {
		
		Boolean getAddSeparatorAtBeginning();
		PathStringifier setAddSeparatorAtBeginning(Boolean addSeparatorAtBeginning);
		
		Boolean getAddSeparatorAfterContext();
		PathStringifier setAddSeparatorAfterContext(Boolean addSeparatorAfterContext);
		
		String getContext();
		PathStringifier setContext(String context);
		
		String getIdentifier();
		PathStringifier setIdentifier(String identifier);
		
		UniformResourceLocatorHelper.Stringifier getUniformResourceLocatorStringifier();
		
		@Override PathStringifier addTokens(String... tokens);
		
		@Getter
		public static class Adapter extends org.cyk.utility.common.helper.StringHelper.Builder.Collection.Adapter.Default implements PathStringifier,Serializable {
			private static final long serialVersionUID = 1L;

			protected Boolean addSeparatorAtBeginning=Boolean.TRUE,addSeparatorAfterContext;
			protected String context,identifier;
			
			@Override
			public PathStringifier addTokens(String... tokens) {
				return (PathStringifier) super.addTokens(tokens);
			}
			
			@Override
			public PathStringifier setAddSeparatorAtBeginning(Boolean addSeparatorAtBeginning) {
				return null;
			}
			
			@Override
			public PathStringifier setAddSeparatorAfterContext(Boolean addSeparatorAfterContext) {
				return null;
			}
			
			@Override
			public PathStringifier setContext(String context) {
				return null;
			}
			
			@Override
			public PathStringifier setIdentifier(String identifier) {
				return null;
			}
			
			@Override
			public org.cyk.utility.common.helper.UniformResourceLocatorHelper.Stringifier getUniformResourceLocatorStringifier() {
				return null;
			}
			
			/**/
			
			public static class Default extends PathStringifier.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

				public static Map<String,String> DEFAULT_SEQUENCE_REPLACEMENT_MAP = null;
				public static Class<? extends StringHelper.Mapping> DEFAULT_IDENTIFIER_MAPPING_CLASS = StringHelper.Mapping.Adapter.Default.class;
				public static String IDENTIFIER_UNKNOWN = "unknown";
				public static String DEFAULT_CONTEXT;
				
				public Default() {
					setSeparator(Constant.CHARACTER_SLASH.toString());
					setSequenceReplacementMap(DEFAULT_SEQUENCE_REPLACEMENT_MAP);
					addSequenceReplacement(StringUtils.repeat(getSeparator(), 2), getSeparator());
					setContext(DEFAULT_CONTEXT);
				}
				
				@Override
				protected String __execute__() {
					String identifier = getIdentifier();
					String string;
					String context = getContext();
					Boolean addSeparatorAfterContext = getAddSeparatorAfterContext();
					java.util.Collection<String> tokens = getTokens();
					//if(addSeparatorAfterContext==null)
					//	addSeparatorAfterContext = StringHelper.getInstance().isNotBlank(context) && !CollectionHelper.getInstance().isEmpty(tokens);
					if(Boolean.TRUE.equals(addSeparatorAfterContext) && CollectionHelper.getInstance().isEmpty(tokens))
						addTokenAt(getSeparator(),0);
					addTokenAt(context,0);
					if(StringHelper.getInstance().isBlank(identifier)){
						
					}else{
						addTokens(ClassHelper.getInstance().instanciateOne(DEFAULT_IDENTIFIER_MAPPING_CLASS).setInput(identifier).execute());
					}
					string = super.__execute__();	
					if(Boolean.TRUE.equals(getAddSeparatorAtBeginning()))
						string = StringHelper.getInstance().addAtBeginingIfDoesNotStartWith(string, getSeparator());
					return string;
				}
				
				@Override
				public PathStringifier setAddSeparatorAfterContext(Boolean addSeparatorAfterContext) {
					this.addSeparatorAfterContext = addSeparatorAfterContext;
					return this;
				}
				
				@Override
				public PathStringifier setContext(String context) {
					this.context = context;
					return this;
				}
				
				@Override
				public PathStringifier setAddSeparatorAtBeginning(Boolean addSeparatorAtBeginning) {
					this.addSeparatorAtBeginning = addSeparatorAtBeginning;
					return this;
				}
				
				@Override
				public PathStringifier setIdentifier(String identifier) {
					this.identifier = identifier;
					return this;
				}
				
				@Override
				public org.cyk.utility.common.helper.UniformResourceLocatorHelper.Stringifier getUniformResourceLocatorStringifier() {
					return (org.cyk.utility.common.helper.UniformResourceLocatorHelper.Stringifier) getProperty(PROPERTY_NAME_PARENT);
				}
			}	
		}
	}
	
	public static interface QueryStringifier extends org.cyk.utility.common.helper.MapHelper.Stringifier {
		
		UniformResourceLocatorHelper.Stringifier getUniformResourceLocatorStringifier();
		
		@Override QueryStringifier addKeyValue(Object... objects);
		
		public static class Adapter extends org.cyk.utility.common.helper.MapHelper.Stringifier.Adapter.Default implements QueryStringifier,Serializable {
			private static final long serialVersionUID = 1L;

			@Override
			public org.cyk.utility.common.helper.UniformResourceLocatorHelper.Stringifier getUniformResourceLocatorStringifier() {
				return null;
			}
			
			@Override
			public QueryStringifier addKeyValue(Object... objects) {
				return (QueryStringifier) super.addKeyValue(objects);
			}
			
			/**/
			
			public static class Default extends QueryStringifier.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default() {
					setSeparator(Constant.CHARACTER_AMPERSTAMP.toString());
				}
				
				@Override
				protected void configure(Entry entryStringifier) {
					super.configure(entryStringifier);
					entryStringifier.setOutputStrategy(OutputStrategy.KEY_MANY_VALUES);
					entryStringifier.setKeyValuesSeparator(Constant.CHARACTER_AMPERSTAMP.toString());
				}
				
				@Override
				public org.cyk.utility.common.helper.UniformResourceLocatorHelper.Stringifier getUniformResourceLocatorStringifier() {
					return (org.cyk.utility.common.helper.UniformResourceLocatorHelper.Stringifier) getProperty(PROPERTY_NAME_PARENT);
				}
				
			}	
		}
	}
	
	/**/
	
	public static interface Listener {
		
		
		
		public static class Adapter implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
			}
			
		}
		
	}

	public static final Map<String,Integer> SCHEME_DEFAULT_PORT_MAP = new HashMap<>();
	static {
		SCHEME_DEFAULT_PORT_MAP.put("http", 80);
	}
}
