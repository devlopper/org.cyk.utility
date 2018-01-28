package org.cyk.utility.common.helper;

import static org.cyk.utility.common.helper.UniformResourceLocatorHelper.TokenName.HOST;
import static org.cyk.utility.common.helper.UniformResourceLocatorHelper.TokenName.PORT;
import static org.cyk.utility.common.helper.UniformResourceLocatorHelper.TokenName.SCHEME;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.Constant.Action;
import org.cyk.utility.common.helper.MapHelper.Stringifier.Entry.OutputStrategy;
import org.cyk.utility.common.helper.StringHelper.CaseType;
import org.cyk.utility.common.userinterface.RequestHelper;

import lombok.Getter;

@Singleton @Named
public class UniformResourceLocatorHelper extends AbstractHelper implements Serializable {
	private static final long serialVersionUID = 1L;
	
	static {
		ClassHelper.getInstance().map(UniformResourceLocatorHelper.Listener.class, UniformResourceLocatorHelper.Listener.Adapter.Default.class,Boolean.FALSE);
	}
	
	private static UniformResourceLocatorHelper INSTANCE;
	
	private static final Map<Object,String> PATH_IDENTIFIER_LINK_MAP = new HashMap<>();
	
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
	
	public String getParameterNameInputValueIsNotRequired(){
		return QueryParameter.Name.INPUT_VALUE_IS_NOT_REQUIRED;
	}
	
	public String getToken(TokenName name,String url){
		if(name == null || StringHelper.getInstance().isBlank(url))
			return null;
		URI uri = URI.create(url);
		java.net.URL u = null;
		try {
			u = new java.net.URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		switch(name){
		case SCHEME: return uri.getScheme();
		case HOST: return u.getHost();
		case PORT: return String.valueOf(uri.getPort());
		case PATH: return uri.getPath();
		case QUERY: return uri.getQuery();
		case FRAGMENT: return uri.getFragment();
		}
		return null;
	}
	
	public String getToken(TokenName name){
		UniformResourceLocatorHelper.Listener listener = ClassHelper.getInstance().instanciateOne(UniformResourceLocatorHelper.Listener.class);
		Object request = RequestHelper.getInstance().get();
		return getToken(name,request == null ? listener.getDefault() :  (String)RequestHelper.getInstance().getUniformResourceLocator(request));
	}
	
	public String getDefault(){
		return ClassHelper.getInstance().instanciateOne(UniformResourceLocatorHelper.Listener.class).getDefault();
	}
	
	public Boolean isRelative(String url){
		return StringUtils.startsWith(url, "/");
	}
	
	public void linkPathIdentifier(String pathIdentifierSource,String pathIdentifierDestination){
		PATH_IDENTIFIER_LINK_MAP.put(pathIdentifierSource, pathIdentifierDestination);
	}
	
	public String getPathIdentifierDestination(String pathIdentifierSource){
		return PATH_IDENTIFIER_LINK_MAP.get(pathIdentifierSource);
	}
	
	public String getPathIdentifier(Constant.Action action,Class<?> aClass){
		return ClassHelper.getInstance().instanciateOne(Listener.class).getPathIdentifier(action, aClass);
	}
	
	public String stringify(String pathIdentifier,Object...queryKeyValue){
		return new UniformResourceLocatorHelper.Stringifier.Adapter.Default().setPathIdentifier(pathIdentifier).addQueryKeyValue(queryKeyValue).execute();
	}
	
	public UniformResourceLocatorHelper.Stringifier getStringifier(Constant.Action action,Object object,Object...queryKeyValue){
		Class<?> aClass = object instanceof Class ? (Class<?>)object : object.getClass();
		//String pathIdentifier = getPathIdentifier(action, aClass);
		UniformResourceLocatorHelper.Stringifier stringifier = new UniformResourceLocatorHelper.Stringifier.Adapter.Default();
		//stringifier.setPathIdentifier(pathIdentifier);
		stringifier.setProperty(Stringifier.PROPERTY_NAME_ACTION, action).setProperty(Stringifier.PROPERTY_NAME_CLASS, aClass);
		stringifier.addQueryParameterAction(action);
		stringifier.addQueryParameterClass(aClass);
		if(object instanceof Class)
			;//stringifier.addQueryParameterClass((Class<?>)object);
		else
			stringifier.addQueryParameterIdentifiable(object);
		stringifier.addQueryKeyValue(queryKeyValue);
		return stringifier;
	}
	
	public String stringify(Constant.Action action,Object object,Object...queryKeyValue){
		return getStringifier(action, object, queryKeyValue).execute();
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
		UniformResourceLocatorHelper.Stringifier setPathIdentifier(String identifier);
		
		QueryStringifier getQueryStringifier();
		QueryStringifier getQueryStringifier(Boolean createIfNull);
		UniformResourceLocatorHelper.Stringifier setQueryStringifier(QueryStringifier queryStringifier);
		UniformResourceLocatorHelper.Stringifier addQueryKeyValue(Object...objects);
		//UniformResourceLocatorHelper.Stringifier addQueryParameter(Object key,Object value);
		
		UniformResourceLocatorHelper.Stringifier addQueryParameterAction(Constant.Action action);
		UniformResourceLocatorHelper.Stringifier addQueryParameterClass(Class<?> aClass);
		//UniformResourceLocatorHelper.Stringifier addQueryParameterInstances(java.util.Collection<Object> objects);
		UniformResourceLocatorHelper.Stringifier addQueryParameterInstances(Object...objects);
		
		UniformResourceLocatorHelper.Stringifier addQueryParameterIdentifiable(Object object);
		UniformResourceLocatorHelper.Stringifier addQueryParameterIdentifier(Object object);
		
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
			public UniformResourceLocatorHelper.Stringifier addQueryParameterAction(Action action) {
				return null;
			}
			
			@Override
			public UniformResourceLocatorHelper.Stringifier addQueryParameterClass(Class<?> aClass) {
				return null;
			}
			
			@Override
			public UniformResourceLocatorHelper.Stringifier addQueryParameterIdentifiable(Object identifiable) {
				return null;
			}
			
			@Override
			public UniformResourceLocatorHelper.Stringifier addQueryParameterIdentifier(Object identifiable) {
				return null;
			}
			
			@Override
			public UniformResourceLocatorHelper.Stringifier addQueryParameterInstances(Object...objects) {
				return null;
			}
			
			@Override
			public UniformResourceLocatorHelper.Stringifier setPathContext(String context) {
				return null;
			}
			
			@Override
			public UniformResourceLocatorHelper.Stringifier setPathIdentifier(String identifier) {
				return null;
			}
			
			@Override
			public UniformResourceLocatorHelper.Stringifier addPathTokens(String...tokens) {
				return null;
			}
			
			@Override
			public UniformResourceLocatorHelper.Stringifier addQueryKeyValue(Object...objects) {
				return null;
			}
			
			@Override
			public UniformResourceLocatorHelper.Stringifier setQuerySeparator(String querySeparator) {
				return null;
			}
			
			@Override
			public UniformResourceLocatorHelper.Stringifier setScheme(String scheme) {
				return null;
			}

			@Override
			public UniformResourceLocatorHelper.Stringifier setHost(String host) {
				return null;
			}

			@Override
			public UniformResourceLocatorHelper.Stringifier setPort(Integer port) {
				return null;
			}

			@Override
			public UniformResourceLocatorHelper.Stringifier setPathStringifier(PathStringifier pathStringifier) {
				return null;
			}

			@Override
			public UniformResourceLocatorHelper.Stringifier setQueryStringifier(QueryStringifier queryStringifier) {
				return null;
			}

			@Override
			public UniformResourceLocatorHelper.Stringifier setRelative(Boolean relative) {
				return null;
			}

			@Override
			public UniformResourceLocatorHelper.Stringifier setPretty(Boolean pretty) {
				return null;
			}

			@Override
			public UniformResourceLocatorHelper.Stringifier setRequest(Object request) {
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

				public static String DEFAULT_QUERY_SEPARATOR=Constant.CHARACTER_QUESTION_MARK.toString();
				public static  Boolean DEFAULT_RELATIVE,DEFAULT_PRETTY;
				
				public Default() {
					setIsInputRequired(Boolean.FALSE);
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
						pathStringifier.setProperty(PROPERTY_NAME_ACTION, queryStringifier.getInput().get(MapHelper.EntryKey.ACTION));
						pathStringifier.setProperty(PROPERTY_NAME_CLASS, queryStringifier.getInput().get(MapHelper.EntryKey.CLAZZ));
						pathStringifier.setProperty(PROPERTY_NAME_INSTANCE, queryStringifier.getInput().get(MapHelper.EntryKey.IDENTIFIABLE));
					}
					String path = pathStringifier == null ? Constant.EMPTY_STRING : pathStringifier.execute();
					if(StringHelper.getInstance().isNotBlank(path))
						tokens.add(0,path);
					
					if(Boolean.TRUE.equals(relative)){
						
					}else{
						String scheme = getScheme();
						if(scheme==null) 
							scheme = getInstance().getToken(SCHEME);
						String host = getHost();
						if(host==null) 
							host = getInstance().getToken(HOST);
						Integer port = getPort();
						Integer defaultPort = UniformResourceLocatorHelper.SCHEME_DEFAULT_PORT_MAP.get(scheme);
						if(port==null)
							port = NumberHelper.getInstance().getInteger(getInstance().getToken(PORT),defaultPort);
						Boolean isDefaultPort = port == null || defaultPort!=null && defaultPort == port;
						tokens.add(0,String.format(SCHEME_HOST_PORT_FORMAT, scheme,host,isDefaultPort ? Constant.EMPTY_STRING : Constant.CHARACTER_COLON.toString()
								,isDefaultPort ? Constant.EMPTY_STRING : port));
					}
					return StringHelper.getInstance().concatenate(tokens, null);
				}
				
				@Override 
				public UniformResourceLocatorHelper.Stringifier addQueryParameterAction(Action action) {
					addQueryKeyValue(MapHelper.EntryKey.ACTION,action);
					return this;
				}
				
				@Override
				public UniformResourceLocatorHelper.Stringifier addQueryParameterClass(Class<?> aClass) {
					addQueryKeyValue(MapHelper.EntryKey.CLAZZ,aClass);
					return this;
				}
				
				@Override
				public UniformResourceLocatorHelper.Stringifier addQueryParameterIdentifiable(Object identifiable) {
					addQueryKeyValue(MapHelper.EntryKey.IDENTIFIABLE,identifiable);
					return this;
				}
				
				@Override
				public UniformResourceLocatorHelper.Stringifier addQueryParameterIdentifier(Object identifiable) {
					addQueryKeyValue(MapHelper.EntryKey.IDENTIFIER,identifiable);
					return this;
				}
				
				@Override
				public UniformResourceLocatorHelper.Stringifier addQueryParameterInstances(Object...instances) {
					if(ArrayHelper.getInstance().isNotEmpty(instances))
						for(Object instance : instances)
							if(instance!=null)
								addQueryKeyValue(ClassHelper.getInstance().getIdentifier(instance.getClass()),instance);
					return this;
				}
				
				@Override
				public UniformResourceLocatorHelper.Stringifier setPathIdentifier(String identifier) {
					getPathStringifier(Boolean.TRUE).setIdentifier(identifier);
					return this;
				}
				
				@Override
				public UniformResourceLocatorHelper.Stringifier setPathContext(String context) {
					getPathStringifier(Boolean.TRUE).setContext(context);
					return this;
				}
				
				@Override
				public UniformResourceLocatorHelper.Stringifier addPathTokens(String...tokens) {
					getPathStringifier(Boolean.TRUE).addTokens(tokens);
					return this;
				}
				
				@Override
				public UniformResourceLocatorHelper.Stringifier addQueryKeyValue(Object...objects) {
					getQueryStringifier(Boolean.TRUE).addKeyValue(objects);
					return this;
				}
				
				@Override
				public UniformResourceLocatorHelper.Stringifier setQuerySeparator(String querySeparator) {
					this.querySeparator = querySeparator;
					return this;
				}
				
				@Override
				public UniformResourceLocatorHelper.Stringifier setScheme(String scheme) {
					this.scheme = scheme;
					return this;
				}

				@Override
				public UniformResourceLocatorHelper.Stringifier setHost(String host) {
					this.host = host;
					return this;
				}

				@Override
				public UniformResourceLocatorHelper.Stringifier setPort(Integer port) {
					this.port = port;
					return this;
				}

				@Override
				public UniformResourceLocatorHelper.Stringifier setPathStringifier(PathStringifier pathStringifier) {
					this.pathStringifier = pathStringifier;
					return this;
				}

				@Override
				public UniformResourceLocatorHelper.Stringifier setQueryStringifier(QueryStringifier queryStringifier) {
					this.queryStringifier = queryStringifier;
					return this;
				}

				@Override
				public UniformResourceLocatorHelper.Stringifier setRelative(Boolean relative) {
					this.relative = relative;
					return this;
				}

				@Override
				public UniformResourceLocatorHelper.Stringifier setPretty(Boolean pretty) {
					this.pretty = pretty;
					return this;
				}

				@Override
				public UniformResourceLocatorHelper.Stringifier setRequest(Object request) {
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
		
		UniformResourceLocatorHelper.Listener getListener();
		PathStringifier setListener(UniformResourceLocatorHelper.Listener listener);
		
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
			protected UniformResourceLocatorHelper.Listener listener;
			
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
			public UniformResourceLocatorHelper.Stringifier getUniformResourceLocatorStringifier() {
				return null;
			}
			
			@Override
			public PathStringifier setListener(org.cyk.utility.common.helper.UniformResourceLocatorHelper.Listener listener) {
				return null;
			}
			
			/**/
			
			public static class Default extends PathStringifier.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

				public static Map<String,String> DEFAULT_SEQUENCE_REPLACEMENT_MAP = null;
				public static Class<? extends UniformResourceLocatorHelper.Listener> DEFAULT_UNIFORM_RESOURCE_LOCATOR_LISTENER_CLASS = UniformResourceLocatorHelper.Listener.Adapter.Default.class;
				public static String IDENTIFIER_UNKNOWN = "unknown";
				public static String IDENTIFIER_HOME = "home";
				public static String DEFAULT_CONTEXT;
				
				public Default() {
					setSeparator(Constant.CHARACTER_SLASH.toString());
					setSequenceReplacementMap(DEFAULT_SEQUENCE_REPLACEMENT_MAP);
					addSequenceReplacement(StringUtils.repeat(getSeparator(), 2), getSeparator());
					setContext(DEFAULT_CONTEXT);
				}
				
				@Override
				protected String __execute__() {
					UniformResourceLocatorHelper.Listener listener = getListener();
					if(listener==null)
						listener = ClassHelper.getInstance().instanciateOne(DEFAULT_UNIFORM_RESOURCE_LOCATOR_LISTENER_CLASS);
					String identifier = getIdentifier();
					if(identifier==null){
						Constant.Action action = (Action) getProperty(PROPERTY_NAME_ACTION);
						Class<?> aClass = (Class<?>) getProperty(PROPERTY_NAME_CLASS);
						if(aClass==null){
							Object instance = getProperty(PROPERTY_NAME_INSTANCE);
							if(instance!=null)
								aClass = instance.getClass();
						}
						identifier = listener.getPathIdentifier(action, aClass);
						if(!Boolean.TRUE.equals(listener.getIsPathIdentifierMappingExist(identifier))){
							identifier = listener.getPathIdentifierWhenMappingDoesNotExist(action, aClass);
						}
					}
					String string;
					String context = getContext();
					Boolean addSeparatorAfterContext = getAddSeparatorAfterContext();
					java.util.Collection<String> tokens = getTokens();
					String separator = getSeparator();
					//if(addSeparatorAfterContext==null)
					//	addSeparatorAfterContext = StringHelper.getInstance().isNotBlank(context) && !CollectionHelper.getInstance().isEmpty(tokens);
					if(Boolean.TRUE.equals(addSeparatorAfterContext) && CollectionHelper.getInstance().isEmpty(tokens) && StringHelper.getInstance().isBlank(identifier))
						addTokenAt(separator,0);
					addTokenAt(context,0);
					if(StringHelper.getInstance().isBlank(identifier)){
						
					}else{
						String mapping = listener.getPathIdentifierMapping(identifier);
						if(StringHelper.getInstance().isNotBlank(mapping)){
							mapping = StringHelper.getInstance().removeAtBeginingIfDoesNotStartWith(mapping, separator);//TODO make it in super
							addTokens(mapping);
						}
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
				public UniformResourceLocatorHelper.Stringifier getUniformResourceLocatorStringifier() {
					return (org.cyk.utility.common.helper.UniformResourceLocatorHelper.Stringifier) getProperty(PROPERTY_NAME_PARENT);
				}
			
				@Override
				public PathStringifier setListener(org.cyk.utility.common.helper.UniformResourceLocatorHelper.Listener listener) {
					this.listener = listener;
					return this;
				}
			}	
		}
		
		public static interface Listener {
			
			public static class Adapter implements Listener,Serializable {
				private static final long serialVersionUID = 1L;
				
				public static class Default extends Listener.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;
					
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
			public UniformResourceLocatorHelper.Stringifier getUniformResourceLocatorStringifier() {
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
				public UniformResourceLocatorHelper.Stringifier getUniformResourceLocatorStringifier() {
					return (org.cyk.utility.common.helper.UniformResourceLocatorHelper.Stringifier) getProperty(PROPERTY_NAME_PARENT);
				}
				
			}	
		}
	
		public static interface Listener {
			
			public static class Adapter implements Listener,Serializable {
				private static final long serialVersionUID = 1L;
				
				public static class Default extends Listener.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;
					
				}
				
			}
			
		}
	}
	
	/**/
	
	public static interface Listener {
		
		String getDefault();
		
		String getPathIdentifier(Constant.Action action,Class<?> aClass);
		String getPathIdentifierMapping(String identifier);
		Boolean getIsPathIdentifierMappingExist(String identifier);		
		String getPathIdentifierWhenMappingDoesNotExist(Constant.Action action,Class<?> aClass);
		
		public static class Adapter implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
			
			@Override
			public String getDefault() {
				return null;
			}
						
			@Override
			public String getPathIdentifier(Action action, Class<?> aClass) {
				return null;
			}
			
			@Override
			public String getPathIdentifierMapping(String identifier) {
				return null;
			}
			
			@Override
			public Boolean getIsPathIdentifierMappingExist(String identifier) {
				return null;
			}
			
			@Override
			public String getPathIdentifierWhenMappingDoesNotExist(Action action, Class<?> aClass) {
				return null;
			}
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				private static final String EDIT = "Edit";
				
				@Override
				public String getDefault() {
					Object port = TOKEN_DEFAULT_VALUE_MAP.get(TokenName.PORT);
					return String.format(Stringifier.SCHEME_HOST_PORT_FORMAT, TOKEN_DEFAULT_VALUE_MAP.get(TokenName.SCHEME), TOKEN_DEFAULT_VALUE_MAP.get(TokenName.HOST)
							,port == null ? Constant.EMPTY_STRING : Constant.CHARACTER_COLON, port == null ? Constant.EMPTY_STRING : port);
				}
				
				@Override
				public String getPathIdentifier(Action action, Class<?> aClass) {
					if(action==null || aClass==null)
						return null;
					return ClassHelper.getInstance().getVariableName(aClass)+getActionAsString(action);
				}
				
				@Override
				public String getPathIdentifierWhenMappingDoesNotExist(Action action, Class<?> aClass) {
					if(action==null)
						return null;
					return StringHelper.getInstance().applyCaseType(getActionAsString(action), CaseType.FL);
				}
				
				@Override
				public String getPathIdentifierMapping(String identifier) {
					return identifier;
				}
				
				protected String getActionAsString(Action action){
					return Action.isCreateOrUpdateOrDelete(action) ? EDIT : StringHelper.getInstance().applyCaseType(action.name(), CaseType.FURL);
				}
			}
		}
	}

	public static final Map<String,Integer> SCHEME_DEFAULT_PORT_MAP = new HashMap<>();
	static {
		SCHEME_DEFAULT_PORT_MAP.put("http", 80);
	}
	
	public static enum TokenName{SCHEME,HOST,PORT,PATH,QUERY,FRAGMENT}
	public static final Map<TokenName,String> TOKEN_DEFAULT_VALUE_MAP = new HashMap<>();
	static {
		TOKEN_DEFAULT_VALUE_MAP.put(TokenName.SCHEME, "http");
		TOKEN_DEFAULT_VALUE_MAP.put(TokenName.HOST, "localhost");
	}
	
	/**/
	
	public static interface QueryParameter {
		
		public static interface Name {
			
			String FILE_EXTENSION = "fileextension";
			String ATTACHMENT = "attachement";
			String IDENTIFIER = "identifier";
			String IDENTIFIABLE = "identifiable";
			String ENCODED = "encoded";
			String DATA_SOURCE = "datasource";
			String URL_PREVIOUS = "urlprevious";
			String URL_NEXT = "urlnext";
			String ACTION = "action";
			String CLASS = "clazz";
			String CLASS_IDENTIFIABLE_GLOBAL_IDENTIFIER = "clazzidentifiableglobalidentifier";
			String INPUT_VALUE_IS_NOT_REQUIRED = "INPUT_VALUE_IS_NOT_REQUIRED";
		}
		
		public static interface Value {
			
			String DATA_SOURCE_USER_SESSION = "usersession";
			String DATA_SOURCE_DATABASE = "database";
		}
	}

	/**/
	
	public static enum Identifier{
		HOME
	}
}
