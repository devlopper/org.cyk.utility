package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.Action;
import org.cyk.utility.common.Constant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Singleton
public class StringHelper extends AbstractHelper implements Serializable {

	private static final long serialVersionUID = 2366347884051000495L;

	public static final String[] END_LINE = {"\r\n","\n"};
	
	public enum CaseType{
		NONE,FURL,FU,L,U
		;
		
		public static CaseType DEFAULT = NONE;
		}
	public enum Location{START,INSIDE,END,EXAT}
	
	private static final String RESOURCE_BUNDLE_NAME = "org.cyk.utility.common.i18n";
	private static final String KEY_ORDINAL_NUMBER_FORMAT = "ordinal.number.%s";
	private static final String KEY_ORDINAL_NUMBER_SUFFIX_FORMAT = KEY_ORDINAL_NUMBER_FORMAT+".suffix";
			
	public String applyCaseType(String string,CaseType caseType){
		if(string==null)
			return null;
		if(caseType==null)
			caseType = CaseType.DEFAULT;
		switch(caseType){
		case NONE:return string;
		case L:return StringUtils.lowerCase(string);
		case U:return StringUtils.upperCase(string);
		case FURL:return StringUtils.capitalize(string.toLowerCase());
		case FU:return StringUtils.upperCase(StringUtils.substring(string, 0,1))+StringUtils.substring(string, 1);
		}
		return string;	
	}
	
	public String removeEndLineMarker(String line){
		for(String endLine : END_LINE)
			if(line.endsWith(endLine))
				return line.substring(0, line.length()-endLine.length());
		return line;
	}
	
	public String getText(Locale locale,String identifier,Object[] parameters){
		if(locale==null)
			locale = Locale.FRENCH;
		ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME,locale);
		String value = parameters==null?resourceBundle.getString(identifier):MessageFormat.format(resourceBundle.getString(identifier),parameters);
		return value;
    }
	
	public java.lang.String getOrdinalNumberSuffix(Locale locale,Number number) {
		return getText(locale, String.format(KEY_ORDINAL_NUMBER_SUFFIX_FORMAT, number), null);
	}
	
	public java.lang.String getOrdinalNumber(Locale locale,Number number) {
		return getText(locale, String.format(KEY_ORDINAL_NUMBER_FORMAT, number), null);
	}
	
	public String concatenate(Object[] strings,String separator){
		if(strings==null)
			return Constant.EMPTY_STRING;
		return StringUtils.join(strings, separator);
	}
	
	public String concatenate(Collection<String> strings,String separator){
		if(strings==null)
			return Constant.EMPTY_STRING;
		return concatenate(strings.toArray(), separator);
	}
	
	public Boolean isAtLocation(String string,String value,Location location){
		if(StringUtils.isEmpty(value))
			return Boolean.TRUE;
		if(location==null)
			location = Location.EXAT;
		switch(location){
		case START : return StringUtils.startsWith(string, value);
		case INSIDE : return StringUtils.contains(string, value);
		case END : return StringUtils.endsWith(string, value);
		case EXAT : return StringUtils.equals(string, value);
		}
		return Boolean.FALSE;
	}
	
	public Collection<String> removeBlank(Collection<String> collection){
		Collection<String> results = new ArrayList<>();
		for(String string : collection)
			if(StringUtils.isNotBlank(string))
				results.add(string);
		return results;
	}
	
	/**/
	
	private static final StringHelper INSTANCE = new StringHelper();
	public static StringHelper getInstance() {
		return INSTANCE;
	}
	
	//TODO listener has to be added
	
	public static interface Builder extends org.cyk.utility.common.Builder<String> {

		String getSeparator();
		Builder setSeparator(String separator);
		
		@Getter @Setter
		public static class Adapter extends org.cyk.utility.common.Builder.Adapter.Default<String> implements Builder,Serializable {
			private static final long serialVersionUID = 1L;

			protected String separator;
			
			public Adapter() {
				super(String.class);
			}
			
			@Override
			public Builder setSeparator(String separator) {
				return null;
			}
			
			/**/
			
			@Getter @Setter
			public static class Default extends Builder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

				@Override
				public Builder setSeparator(String separator) {
					this.separator = separator;
					return this;
				}
				
			}
			
		}
		
		/**/
		
		public static interface CacheIdentifier extends Builder {
			
			public static class Adapter extends Builder.Adapter.Default implements CacheIdentifier,Serializable {
				private static final long serialVersionUID = 1L;
				
				public static class Default extends CacheIdentifier.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;
					
					@Override
					protected String __execute__() {
						String parameters = new CollectionHelper().concatenate(getParameters(),Constant.CHARACTER_UNDESCORE.toString());
						return commonUtils.getValueIfNotNullElseDefault(getLocale(),Locale.FRENCH)+Constant.CHARACTER_UNDESCORE.toString()+getInput()
							+(StringUtils.isBlank(parameters) ? Constant.EMPTY_STRING : Constant.CHARACTER_UNDESCORE+parameters);
					}
					
				}
				
			}
			
		}
	}

	/**/
	
	public static interface ToStringMapping extends org.cyk.utility.common.Mapping<String,String> {

		Collection<Datasource> DATASOURCES = new ArrayList<>();
		
		CaseType getCaseType();
		ToStringMapping setCaseType(CaseType caseType);
		
		Boolean getCachable();
		ToStringMapping setCachable(Boolean cachable);
		
		@Getter @Setter
		public static class Adapter extends org.cyk.utility.common.Mapping.Adapter.Default<String,String> implements ToStringMapping,Serializable {
			private static final long serialVersionUID = 1L;

			protected CaseType caseType;
			protected Boolean cachable;
			
			public Adapter(String input) {
				super(String.class, input, String.class);
				setName("build string cache identifier");
			}

			public ToStringMapping setCaseType(CaseType caseType){
				this.caseType = caseType;
				return this;
			}
			
			public ToStringMapping setCachable(Boolean cachable){
				this.cachable = cachable;
				return this;
			}
			
			/**/
			
			@Getter @Setter
			public static class Default extends ToStringMapping.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(String input) {
					super(input);
					setCachable(Boolean.TRUE);
				}
				
				@Override
				protected String __execute__() {
					String identifier = getInput();
					return __execute__(identifier, getCaseType(), getLocale(), getCachable());
				}
				
				private String __execute__(final String identifier,final CaseType pCaseType,final Locale pLocale,Boolean cachabled){
					final Locale locale = commonUtils.getValueIfNotNullElseDefault(pLocale, Locale.FRENCH);
					final CaseType caseType = commonUtils.getValueIfNotNullElseDefault(pCaseType, CaseType.DEFAULT);
					final Collection<Object> parameters = getParameters();
					
					String value = new ListenerHelper.Executor.Function.Adapter.Default.String<Datasource>().setReturnFirstNotNull(Boolean.TRUE)
						.setResultMethod(new ListenerHelper.Executor.ResultMethod.Adapter.Default.String<Datasource>() {
							private static final long serialVersionUID = 1L;

							@Override
							protected java.lang.String __execute__() {
								return getListener().setInput(identifier).setLocale(ToStringMapping.Adapter.Default.this.getLocale())
										.setParameters(ToStringMapping.Adapter.Default.this.getParameters())
										.setParent(ToStringMapping.Adapter.Default.this).execute();
							}
						}).setInput(DATASOURCES).execute();
					
					if(value==null){
						value = MAP.get(identifier);
						if(value==null){
							CollectionHelper collectionHelper = new CollectionHelper();
							for(Entry<String, ClassLoader> entry : RESOURCE_BUNDLE_MAP.entrySet()){
								try {
									ResourceBundle resourceBundle = ResourceBundle.getBundle(entry.getKey(), locale, entry.getValue());
									value = collectionHelper.isEmpty(parameters)?resourceBundle.getString(identifier):MessageFormat.format(resourceBundle.getString(identifier),parameters);
									
									addLoggingMessageBuilderNamedParameters("location","resource bundle "+entry.getKey());
									
									String substituteCode = null;
									while((substituteCode = StringUtils.substringBetween(value, SUBSTITUTE_TAG_START, SUBSTITUTE_TAG_END)) != null){
										value = StringUtils.replace(value, SUBSTITUTE_TAG_START+substituteCode+SUBSTITUTE_TAG_END, __execute__(substituteCode,CaseType.NONE,locale,cachabled));
									}
									
									if(StringUtils.startsWith(value,DO_NOT_PROCESS_TAG_START) && StringUtils.endsWith(value,DO_NOT_PROCESS_TAG_END)){
										value = StringUtils.substringBetween(value, DO_NOT_PROCESS_TAG_START, DO_NOT_PROCESS_TAG_END);
									}else{
										value = StringHelper.getInstance().applyCaseType(value, caseType);
									}
									
									if(Boolean.TRUE.equals(cachabled)){
										String cacheIdentifier = new Builder.CacheIdentifier.Adapter.Default().setInput(identifier).setLocale(locale).addParameters(parameters)
												.addParameters(new Object[]{caseType}).execute();
										CACHE.put(cacheIdentifier, value);
										addLoggingMessageBuilderNamedParameters("cache identifier",cacheIdentifier,"cache value",value);
									}
									break;
								} catch (Exception e) {
									//It is not in that bundle. Let try the next one
									e.printStackTrace();
								}
							}	
						}else{
							addLoggingMessageBuilderNamedParameters("location","map");
							//logTrace("identifier {} has been found in user defined map with value {}", identifier,value);
						}
					}
					return value;
				}
				
				
			}
			
		}

		/**/
		
		public static interface Datasource extends Action<String, String>{
			
			public static class Adapter extends Action.Adapter.Default<String, String> implements Datasource,Serializable {
				private static final long serialVersionUID = 1L;

				public Adapter() {
					super("get", String.class, null, String.class);
				}
				
				@Override
				public ToStringMapping getParent() {
					return (ToStringMapping) super.getParent();
				}
				
				/**/
				
				public static class Default extends Datasource.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;
					
					/**/
				}
				
			}
			
			/**/
			
			public static interface UserDefined extends Datasource {
				public static final java.util.Map<String,String> REPOSITORY = new HashMap<>();
				public static class Adapter extends Datasource.Adapter.Default implements UserDefined,Serializable {
					private static final long serialVersionUID = 1L;
				
					public static class Default extends UserDefined.Adapter implements Serializable {
						private static final long serialVersionUID = 1L;
						
						@Override
						protected String __execute__() {
							return REPOSITORY.get(getInput());
						}
						
					}
					
				}
			}
			
			public static interface Cache extends Datasource {
				public static final java.util.Map<String,String> REPOSITORY = new HashMap<>();
				public static class Adapter extends Datasource.Adapter.Default implements Cache,Serializable {
					private static final long serialVersionUID = 1L;
				
					public static class Default extends Cache.Adapter implements Serializable {
						private static final long serialVersionUID = 1L;
						
						@Override
						protected String __execute__() {
							return REPOSITORY.get(getInput());
						}
						
					}
					
				}
			}
			
			public static interface Database extends Datasource {
				public static final java.util.Map<String,String> REPOSITORY = new HashMap<>();
				public static class Adapter extends Datasource.Adapter.Default implements Database,Serializable {
					private static final long serialVersionUID = 1L;
				
					public static class Default extends Database.Adapter implements Serializable {
						private static final long serialVersionUID = 1L;
						
						@Override
						protected String __execute__() {
							return REPOSITORY.get(getInput());
						}
						
					}
					
				}
			}
			
			public static interface ResourceBundle extends Datasource {
				public static final java.util.Map<String,ClassLoader> REPOSITORY = new LinkedHashMap<>();
				public static class Adapter extends Datasource.Adapter.Default implements ResourceBundle,Serializable {
					private static final long serialVersionUID = 1L;
				
					public static class Default extends ResourceBundle.Adapter implements Serializable {
						private static final long serialVersionUID = 1L;
						
						protected String __execute__() {
							String identifier = getParent().getInput();
							final Locale locale = commonUtils.getValueIfNotNullElseDefault(getParent().getLocale(), Locale.FRENCH);
							final CaseType caseType = commonUtils.getValueIfNotNullElseDefault(getParent().getCaseType(), CaseType.DEFAULT);
							final Boolean cachable = commonUtils.getValueIfNotNullElseDefault(getParent().getCachable(), Boolean.TRUE);
							return __execute__(identifier, caseType, locale, cachable);
							
						}
					
						private String __execute__(final String identifier,final CaseType caseType,final Locale locale,Boolean cachable){
							CollectionHelper collectionHelper = new CollectionHelper();
							for(Entry<String, ClassLoader> entry : REPOSITORY.entrySet()){
								try {
									java.util.ResourceBundle resourceBundle = java.util.ResourceBundle.getBundle(entry.getKey(), locale, entry.getValue());
									String value = collectionHelper.isEmpty(parameters)?resourceBundle.getString(identifier):MessageFormat.format(resourceBundle.getString(identifier)
											,collectionHelper.getArray(parameters));
									
									addLoggingMessageBuilderNamedParameters("location","resource bundle "+entry.getKey());
									
									String substituteCode = null;
									while((substituteCode = StringUtils.substringBetween(value, SUBSTITUTE_TAG_START, SUBSTITUTE_TAG_END)) != null){
										value = StringUtils.replace(value, SUBSTITUTE_TAG_START+substituteCode+SUBSTITUTE_TAG_END, __execute__(substituteCode,CaseType.NONE,locale,cachable));
									}
									
									if(StringUtils.startsWith(value,DO_NOT_PROCESS_TAG_START) && StringUtils.endsWith(value,DO_NOT_PROCESS_TAG_END)){
										value = StringUtils.substringBetween(value, DO_NOT_PROCESS_TAG_START, DO_NOT_PROCESS_TAG_END);
									}else{
										value = StringHelper.getInstance().applyCaseType(value, caseType);
									}
									
									if(Boolean.TRUE.equals(cachable)){
										String cacheIdentifier = new Builder.CacheIdentifier.Adapter.Default().setInput(identifier).setLocale(locale)
												.addParameters(parameters).addParameters(new Object[]{caseType}).execute();
										CACHE.put(cacheIdentifier, value);
										addLoggingMessageBuilderNamedParameters("cache identifier",cacheIdentifier,"cache value",value);
									}
									
									return value;
								} catch (Exception e) {
									//It is not in that bundle. Let try the next one
									//e.printStackTrace();
								}
							}
							return null;
						}
					}
				}
			}
		}
		
		/**/
		
		Map<String,String> CACHE = new HashMap<>();
		Map<String,ClassLoader> RESOURCE_BUNDLE_MAP = new LinkedHashMap<>();
		Map<String,String> MAP = new HashMap<>();
		
		String DO_NOT_PROCESS_TAG = "cyk_donotprocess";
		String DO_NOT_PROCESS_TAG_START = "<"+DO_NOT_PROCESS_TAG+">";
		String DO_NOT_PROCESS_TAG_END = "</"+DO_NOT_PROCESS_TAG+">";
		
		String SUBSTITUTE_TAG = "cyk_code";
		String SUBSTITUTE_TAG_START = "<"+SUBSTITUTE_TAG+">";
		String SUBSTITUTE_TAG_END = "</"+SUBSTITUTE_TAG+">";
	}

	/**/
	
	@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
	public static class Parameter extends Action.Parameter implements Serializable {
		private static final long serialVersionUID = 1L;
		
		protected CaseType caseType;

		public Parameter(Object name, Object value, CaseType caseType) {
			super(name, value);
			this.caseType = caseType;
		}
		
		public Parameter(Object name, Object value) {
			this(name, value,null);
		}
		
		public Parameter(Object value) {
			this(null, value);
		}
		
		public Parameter(Object value, CaseType caseType) {
			this(null, value,caseType);
		}
		
		@Override
		public String toString() {
			return super.toString()+(caseType == null ? Constant.EMPTY_STRING : (Constant.CHARACTER_COLON.toString()+caseType));
		}
	}
}
