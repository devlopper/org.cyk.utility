package org.cyk.utility.common.helper;

import java.io.Serializable;
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
		
		String getScheme();
		UniformResourceLocatorHelper.Stringifier setScheme(String scheme);
		
		String getHost();
		UniformResourceLocatorHelper.Stringifier setHost(String host);
		
		Integer getPort();
		UniformResourceLocatorHelper.Stringifier setPort(Integer port);
		
		PathStringBuilder getPathStringBuilder();
		UniformResourceLocatorHelper.Stringifier setPathStringBuilder(PathStringBuilder pathStringBuilder);
		
		QueryStringBuilder getQueryStringBuilder();
		UniformResourceLocatorHelper.Stringifier setQueryStringBuilder(QueryStringBuilder queryStringBuilder);
		
		Boolean getRelative();
		UniformResourceLocatorHelper.Stringifier setRelative(Boolean relative);
		
		Boolean getPretty();
		UniformResourceLocatorHelper.Stringifier setPretty(Boolean pretty);
		
		Object getRequest();
		UniformResourceLocatorHelper.Stringifier setRequest(Object request);
		
		@Getter
		public static class Adapter extends org.cyk.utility.common.Builder.Stringifier.Adapter.Default<Object> implements UniformResourceLocatorHelper.Stringifier,Serializable {
			private static final long serialVersionUID = 1L;

			protected String scheme,host;
			protected Integer port;
			protected PathStringBuilder pathStringBuilder;
			protected QueryStringBuilder queryStringBuilder;
			protected Boolean relative,pretty;
			protected Object request;
			
			public Adapter() {
				super(Object.class,null);
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
			public org.cyk.utility.common.helper.UniformResourceLocatorHelper.Stringifier setPathStringBuilder(PathStringBuilder pathStringBuilder) {
				return null;
			}

			@Override
			public org.cyk.utility.common.helper.UniformResourceLocatorHelper.Stringifier setQueryStringBuilder(QueryStringBuilder queryStringBuilder) {
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
			
			/**/
			
			public static class Default extends UniformResourceLocatorHelper.Stringifier.Adapter implements Serializable {

				private static final long serialVersionUID = 1L;

				@Override
				protected String __execute__() {
					// TODO Auto-generated method stub
					return super.__execute__();
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
				public org.cyk.utility.common.helper.UniformResourceLocatorHelper.Stringifier setPathStringBuilder(PathStringBuilder pathStringBuilder) {
					this.pathStringBuilder = pathStringBuilder;
					return this;
				}

				@Override
				public org.cyk.utility.common.helper.UniformResourceLocatorHelper.Stringifier setQueryStringBuilder(QueryStringBuilder queryStringBuilder) {
					this.queryStringBuilder = queryStringBuilder;
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
				
			}
		}
	}
	
	public static interface PathStringBuilder extends org.cyk.utility.common.helper.StringHelper.Builder.Collection {
		
		Boolean getAddSeparatorAtBeginning();
		PathStringBuilder setAddSeparatorAtBeginning(Boolean addSeparatorAtBeginning);
		
		String getContext();
		PathStringBuilder setContext(String context);
		
		String getIdentifier();
		PathStringBuilder setIdentifier(String identifier);
		
		@Getter
		public static class Adapter extends org.cyk.utility.common.helper.StringHelper.Builder.Collection.Adapter.Default implements PathStringBuilder,Serializable {
			private static final long serialVersionUID = 1L;

			protected Boolean addSeparatorAtBeginning=Boolean.TRUE;
			protected String context,identifier;
			
			@Override
			public PathStringBuilder setAddSeparatorAtBeginning(Boolean addSeparatorAtBeginning) {
				return null;
			}
			
			@Override
			public PathStringBuilder setContext(String context) {
				return null;
			}
			
			@Override
			public PathStringBuilder setIdentifier(String identifier) {
				return null;
			}
			
			/**/
			
			public static class Default extends PathStringBuilder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

				public static Map<String,String> DEFAULT_SEQUENCE_REPLACEMENT_MAP = null;
				public static Class<? extends StringHelper.Mapping> DEFAULT_IDENTIFIER_MAPPING_CLASS = StringHelper.Mapping.Adapter.Default.class;
				public static String IDENTIFIER_UNKNOWN = "unknown";
				
				public Default() {
					setSeparator(Constant.CHARACTER_SLASH.toString());
					setSequenceReplacementMap(DEFAULT_SEQUENCE_REPLACEMENT_MAP);
					addSequenceReplacement(StringUtils.repeat(getSeparator(), 2), getSeparator());
				}
				
				@Override
				public PathStringBuilder setContext(String context) {
					this.context = context;
					return this;
				}
				
				@Override
				public PathStringBuilder setAddSeparatorAtBeginning(Boolean addSeparatorAtBeginning) {
					this.addSeparatorAtBeginning = addSeparatorAtBeginning;
					return this;
				}
				
				@Override
				public PathStringBuilder setIdentifier(String identifier) {
					this.identifier = identifier;
					return this;
				}
				
				@Override
				protected String __execute__() {
					String identifier = getIdentifier();
					String string;
					addTokenAt(getContext(),0);
					if(StringHelper.getInstance().isBlank(identifier)){
						
					}else{
						addTokens(ClassHelper.getInstance().instanciateOne(DEFAULT_IDENTIFIER_MAPPING_CLASS).setInput(identifier).execute());
					}
					string = super.__execute__();	
					if(Boolean.TRUE.equals(getAddSeparatorAtBeginning()))
						string = StringHelper.getInstance().addAtBeginingIfDoesNotStartWith(string, getSeparator());
					return string;
				}
			}	
		}
	}
	
	public static interface QueryStringBuilder extends org.cyk.utility.common.helper.MapHelper.Stringifier {
		
		public static class Adapter extends org.cyk.utility.common.helper.MapHelper.Stringifier.Adapter.Default implements QueryStringBuilder,Serializable {
			private static final long serialVersionUID = 1L;

			/**/
			
			public static class Default<OUTPUT> extends QueryStringBuilder.Adapter implements Serializable {
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
					
			}	
		}
	}
	
}
