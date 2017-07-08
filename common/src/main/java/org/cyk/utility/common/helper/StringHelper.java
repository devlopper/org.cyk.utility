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
import org.cyk.utility.common.Constant;

import lombok.Getter;
import lombok.Setter;

@Singleton
public class StringHelper extends AbstractHelper implements Serializable {

	private static final long serialVersionUID = 2366347884051000495L;

	public static final String[] END_LINE = {"\r\n","\n"};
	
	public enum CaseType{
		NONE,FURL
		;
		
		public static CaseType DEFAULT = NONE;
		}
	public enum Location{START,INSIDE,END,EXAT}
	
	private static final String RESOURCE_BUNDLE_NAME = "org.cyk.utility.common.i18n";
	private static final String KEY_ORDINAL_NUMBER_FORMAT = "ordinal.number.%s";
	private static final String KEY_ORDINAL_NUMBER_SUFFIX_FORMAT = KEY_ORDINAL_NUMBER_FORMAT+".suffix";
			
	public String applyCaseType(String string,CaseType caseType){
		if(caseType==null)
			caseType = CaseType.DEFAULT;
		switch(caseType){
		case NONE:return string;
		case FURL:return StringUtils.capitalize(string.toLowerCase());
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
		return StringUtils.join(strings, separator);
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
	
	public String buildCacheIdentifier(Locale locale,String code,Object[] parameters,CaseType caseType){
		return locale+Constant.CHARACTER_UNDESCORE.toString()+code+(parameters==null?Constant.EMPTY_STRING:StringUtils.join(parameters))
				+Constant.CHARACTER_UNDESCORE.toString()+caseType;
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
				
				private String __execute__(String identifier,CaseType caseType,Locale locale,Boolean cachabled){
					locale = commonUtils.getValueIfNotNullElseDefault(locale, Locale.FRENCH);
					caseType = commonUtils.getValueIfNotNullElseDefault(caseType, CaseType.DEFAULT);
					String value = MAP.get(identifier);
					if(value==null){
						CollectionHelper collectionHelper = new CollectionHelper();
						Collection<Object> parameters = getParameters();
						for(Entry<String, ClassLoader> entry : RESOURCE_BUNDLE_MAP.entrySet()){
							try {
								ResourceBundle resourceBundle = ResourceBundle.getBundle(entry.getKey(), locale, entry.getValue());
								//logDebug("Bunble={}, Locale={}, Key={}",entry.getKey(),locale, code);
								/*if(!locale.equals(resourceBundle.getLocale()))
									throw new RuntimeException("Locale has changed! No same locale : "+locale+" <> "+resourceBundle.getLocale());*/
								value = collectionHelper.isEmpty(parameters)?resourceBundle.getString(identifier):MessageFormat.format(resourceBundle.getString(identifier),parameters);
								
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
											.addParameters(new Object[]{caseType})/*.setLogMessageBuilder(getLogMessageBuilder())*/.execute();
									CACHE.put(cacheIdentifier, value);
									//logTrace("identifier {} has been put in cache with value {}",cacheIdentifier,value);
									addLogMessageBuilderParameters(getLogMessageBuilder(), "cache identifier",cacheIdentifier,"cache value",value);
								}
								break;
							} catch (Exception e) {
								//It is not in that bundle. Let try the next one
								e.printStackTrace();
							}
						}	
					}else{
						addLogMessageBuilderParameters(getLogMessageBuilder(), "found in user defined map","");
						//logTrace("identifier {} has been found in user defined map with value {}", identifier,value);
					}
					
					return value;
				}
				
			}
			
		}
		
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
}
