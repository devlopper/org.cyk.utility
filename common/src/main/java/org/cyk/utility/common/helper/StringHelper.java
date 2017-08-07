package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javax.inject.Singleton;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.Action;
import org.cyk.utility.common.CommonUtils;
import org.cyk.utility.common.Constant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Singleton
public class StringHelper extends AbstractHelper implements Serializable {

	private static final long serialVersionUID = 2366347884051000495L;

	public static final String[] END_LINE = {"\r\n","\n"};
	public static final Character[] VOYELS = {'a','e','i','o','u','y'};
	
	public enum CaseType{
		NONE,FURL,FU,L,U
		;
		
		public static CaseType DEFAULT = NONE;
		}
	public enum Location{START,INSIDE,END,EXAT}
	
	private static final String RESOURCE_BUNDLE_NAME = "org.cyk.utility.common.i18n";
	private static final String KEY_ORDINAL_NUMBER_FORMAT = "ordinal.number.%s";
	private static final String KEY_ORDINAL_NUMBER_SUFFIX_FORMAT = KEY_ORDINAL_NUMBER_FORMAT+".suffix";
	
	private static StringHelper INSTANCE;
	
	public static StringHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new StringHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public Boolean isVoyel(Character character){
		return ArrayUtils.contains(VOYELS, Character.toLowerCase(character));
	}
	
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
	
	@Deprecated
	public String getText(Locale locale,String identifier,Object[] parameters){
		if(locale==null)
			locale = Locale.FRENCH;
		ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME,locale);
		String value = parameters==null?resourceBundle.getString(identifier):MessageFormat.format(resourceBundle.getString(identifier),parameters);
		return value;
    }
	
	public String get(String identifier,Object[] parameters){
		return new StringHelper.ToStringMapping.Adapter.Default(identifier).setCaseType(CaseType.FURL).addParameters(parameters).execute();
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
	
	public String concatenate(java.util.Collection<String> strings,String separator){
		if(strings==null)
			return Constant.EMPTY_STRING;
		return concatenate(strings.toArray(), separator);
	}
	
	public Boolean isAtLocation(String string,String value,Location location,Boolean caseSensitive){
		if(StringUtils.isEmpty(value))
			return Boolean.TRUE;
		if(location==null)
			location = Location.EXAT;
		if(caseSensitive==null)
			caseSensitive = Boolean.TRUE;
		switch(location){
		case START : return Boolean.TRUE.equals(caseSensitive) ? StringUtils.startsWith(string, value) : StringUtils.startsWithIgnoreCase(string, value);
		case INSIDE : return Boolean.TRUE.equals(caseSensitive) ? StringUtils.contains(string, value) : StringUtils.containsIgnoreCase(string, value);
		case END : return Boolean.TRUE.equals(caseSensitive) ? StringUtils.endsWith(string, value) : StringUtils.endsWithIgnoreCase(string, value);
		case EXAT : return Boolean.TRUE.equals(caseSensitive) ? StringUtils.equals(string, value) : StringUtils.equalsIgnoreCase(string, value);
		}
		return Boolean.FALSE;
	}
	
	public Boolean isAtLocation(String string,String value,Location location){
		return isAtLocation(string, value, location, Boolean.TRUE);
	}
	
	public java.util.Collection<String> removeBlank(java.util.Collection<String> collection){
		java.util.Collection<String> results = new ArrayList<>();
		for(String string : collection)
			if(StringUtils.isNotBlank(string))
				results.add(string);
		return results;
	}
	
	public Boolean isBlank(CharSequence charSequence){
		return StringUtils.isBlank(charSequence);
	}
	
	public String get(java.util.Collection<?> collection,String separator){
		if(collection==null)
			return Constant.EMPTY_STRING;
		return get(collection.toArray(), separator);
	}
	
	public String get(Object[] array,Object separator){
		if(array==null)
			return Constant.EMPTY_STRING;
		List<String> list = new ArrayList<>();
		for(Object object : array)
			if(object==null)
				list.add(Constant.EMPTY_STRING);
			else if(object instanceof Object[])
				list.add(get((Object[])object, separator));
			else if(object instanceof java.util.Collection<?>) {
				java.util.Collection<String> collection = new ArrayList<>();
				for(Object collectionItem : (java.util.Collection<?>)object)
					collection.add(/*get(collectionItem)*/ collectionItem.toString());
				list.add(StringUtils.join(collection,Constant.CHARACTER_COMA.toString()));
			}else
				list.add(object.toString());
		return StringUtils.join(list,separator.toString());
	}
	
	public String get(Object[][] array,Object firstDimensionElementSeparator,Object secondDimensionElementSeparator){
		if(array==null)
			return Constant.EMPTY_STRING;
		java.util.Collection<String> collection = new ArrayList<>();
		for(Object[] index : array)
			collection.add(get(index, secondDimensionElementSeparator.toString()));
		return StringUtils.join(collection,firstDimensionElementSeparator.toString());
	}
	
	public String appendIfDoesNotEndWith(String string,String end){
		if(!isAtLocation(string, end, Location.END, Boolean.FALSE))
			return string+end;
		return string;
	}
	
	public String getWordIdentifier(String identifier,Boolean masculine,Boolean plural){
		identifier = String.format(ToStringMapping.MASCULINE_FORMAT,identifier, Boolean.TRUE.equals(masculine)?"mascul":"femin");
		identifier = Boolean.TRUE.equals(plural) ? String.format(ToStringMapping.PLURAL_FORMAT, identifier) : identifier;
		return identifier;
	}
	
	public String getWord(String identifier,Boolean masculine,Boolean plural){
		return new ToStringMapping.Adapter.Default(getWordIdentifier(identifier, masculine, plural)).execute();
	}
	
	public String getWordArticle(Boolean masculine,Boolean any,Boolean plural){
		String identifier = String.format(ToStringMapping.WORD_ARTICLE_IDENTIFIER_FORMAT, Boolean.TRUE.equals(masculine)?"mascul":"femin"
			,Boolean.TRUE.equals(any) ? "any" : "specific" );
		identifier = Boolean.TRUE.equals(plural) ? String.format(ToStringMapping.PLURAL_FORMAT, identifier) : identifier;
		return new ToStringMapping.Adapter.Default(identifier).execute();
	}
	
	public String getWordArticleAll(Boolean masculine,Boolean plural){
		String identifier = String.format(ToStringMapping.WORD_ARTICLE_ALL_IDENTIFIER_FORMAT, Boolean.TRUE.equals(masculine)?"mascul":"femin" );
		identifier = Boolean.TRUE.equals(plural) ? String.format(ToStringMapping.PLURAL_FORMAT, identifier) : identifier;
		return new ToStringMapping.Adapter.Default(identifier).execute();
	}
	
	public String getComparisonOperator(Boolean greater,Boolean equal,Boolean masculine,Boolean plural){
		String identifier = String.format(ToStringMapping.COMPARISON_OPERATOR_IDENTIFIER_FORMAT, Boolean.TRUE.equals(equal) ? ".equal" : Constant.EMPTY_STRING );
		return new ToStringMapping.Adapter.Default(identifier)
				.addManyParameters(
						StringHelper.getInstance().getWord(Boolean.TRUE.equals(greater) ? "superior" : "inferior", masculine, plural)
				,StringHelper.getInstance().getWord("equal", masculine, plural)
				).execute();
	}
	
	public Boolean isMasculine(String identifier){
		String value = new StringHelper.ToStringMapping.Adapter.Default(String.format(ToStringMapping.GENDER_FORMAT,identifier)).execute();
		return new BooleanHelper.Builder.String.Adapter.Default(value).execute();
	}
	
	/**/
	
	//TODO listener has to be added
	
	public static interface Builder extends org.cyk.utility.common.Builder.NullableInput<String> {

		String getSeparator();
		Builder setSeparator(String separator);
		
		@Getter @Setter
		public static class Adapter extends org.cyk.utility.common.Builder.NullableInput.Adapter.Default<String> implements Builder,Serializable {
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
						String parameters = CollectionHelper.getInstance().concatenate(getParameters(),Constant.CHARACTER_UNDESCORE.toString());
						return commonUtils.getValueIfNotNullElseDefault(getLocale(),Locale.FRENCH)+Constant.CHARACTER_UNDESCORE.toString()+getInput()
							+(StringUtils.isBlank(parameters) ? Constant.EMPTY_STRING : Constant.CHARACTER_UNDESCORE+parameters);
					}	
				}	
			}	
		}
	
		public static interface ClassIdentifier extends Builder {
			
			@Override
			ClassIdentifier setInput(Object input);
			
			public static class Adapter extends Builder.Adapter.Default implements ClassIdentifier,Serializable {
				private static final long serialVersionUID = 1L;
				
				@Override
				public ClassIdentifier setInput(Object input) {
					return (ClassIdentifier) super.setInput(input);
				}
				
				public static class Default extends ClassIdentifier.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;
					
					@Override
					public ClassIdentifier setInput(Object input) {
						return (ClassIdentifier) super.setInput(input);
					}
					
					@Override
					protected String __execute__() {
						return "model.entity."+StringUtils.uncapitalize(((Class<?>)getInput()).getSimpleName());
					}	
				}	
			}	
		}
	
		public static interface Collection extends Builder {
			
			Collection addTokens(java.util.List<String> tokens);
			Collection addTokens(String...tokens);
			Collection addActions(@SuppressWarnings("rawtypes") java.util.List<Action> actions);
			Collection addActions(@SuppressWarnings({ "rawtypes" }) Action...actions);
			Collection space();
			Collection and();
			Collection or();
			Collection leftParathensis();
			Collection rightParathensis();
			
			Collection applyCaseToLastToken(CaseType caseType);
			
			public static class Adapter extends Builder.Adapter implements Collection,Serializable {
				private static final long serialVersionUID = 1L;
				
				protected java.util.List<String> tokens;
				
				@Override
				public Collection addActions(@SuppressWarnings("rawtypes") List<Action> actions) {
					return null;
				}
				
				@Override
				public Collection addActions(@SuppressWarnings({ "rawtypes" }) Action...actions) {
					return null;
				}
				
				@Override
				public Collection applyCaseToLastToken(CaseType caseType) {
					return null;
				}
				
				@Override
				public Collection addTokens(java.util.List<String> tokens) {
					return null;
				}
				
				@Override
				public Collection addTokens(String...tokens) {
					return null;
				}
				
				@Override
				public Collection space() {
					return null;
				}
				
				@Override
				public Collection and() {
					return null;
				}
				
				@Override
				public Collection or() {
					return null;
				}
				
				@Override
				public Collection leftParathensis() {
					return null;
				}

				@Override
				public Collection rightParathensis() {
					return null;
				}

				public static class Default extends Collection.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;
					
					@Override
					protected String __execute__() {
						return CollectionHelper.getInstance().concatenate(tokens, null);
					}
					
					@Override
					public Collection addActions(@SuppressWarnings("rawtypes") List<Action> actions) {
						if(!CollectionHelper.getInstance().isEmpty(actions))
							for(Action<?,?> action : actions)
								addTokens((java.lang.String)action.execute());
						return this;
					}
					
					@Override
					public Collection addActions(@SuppressWarnings({ "rawtypes" }) Action...actions) {
						if(!ArrayHelper.getInstance().isEmpty(actions))
							addActions(Arrays.asList(actions));
						return this;
					}
					
					@Override
					public Collection applyCaseToLastToken(CaseType caseType) {
						if(!CollectionHelper.getInstance().isEmpty(tokens)){
							tokens.set(tokens.size()-1, StringHelper.getInstance().applyCaseType(tokens.get(tokens.size()-1), caseType));
						}
						return this;
					}
					
					@Override
					public Collection addTokens(java.util.List<String> tokens) {
						if(!CollectionHelper.getInstance().isEmpty(tokens)){
							if(this.tokens == null)
								this.tokens = new ArrayList<>();
							this.tokens.addAll(tokens);
						}
						return this;
					}
					
					@Override
					public Collection addTokens(String...tokens) {
						if(!ArrayHelper.getInstance().isEmpty(tokens))
							addTokens(Arrays.asList(tokens));
						return this;
					}
					
					@Override
					public Collection space() {
						return addTokens(Constant.CHARACTER_SPACE.toString());
					}
					
					@Override
					public Collection and() {
						return addTokens(AND);
					}
					
					@Override
					public Collection or() {
						return addTokens(OR);
					}
					
					@Override
					public Collection leftParathensis() {
						return addTokens(Constant.CHARACTER_LEFT_PARENTHESIS.toString());
					}

					@Override
					public Collection rightParathensis() {
						return addTokens(Constant.CHARACTER_RIGHT_PARENTHESIS.toString());
					}
					
					@Override
					public Action<Object, String> clear() {
						CollectionHelper.getInstance().clear(tokens);
						return super.clear();
					}
					
				}
				
			}
			
			/**/
			
			String AND = "and";
			String OR = "or";
		}
	}

	/**/
	
	public static interface ToStringMapping extends org.cyk.utility.common.Mapping<String,String> {

		@Deprecated java.util.Collection<Datasource> DATASOURCES = new ArrayList<>();
		String UNKNOWN_MARKER_START = "##";
		String UNKNOWN_MARKER_END = "##";
		String UNKNOWN_FORMAT = "%s%s%s";
		String PLURAL_FORMAT = "%s.__plural__";
		String GENDER_FORMAT = "%s.__gender__";
		String MASCULINE_FORMAT = "%s.__%sine__";
		String WORD_IDENTIFIER_FORMAT = "word.%s.__%sine__";
		String WORD_ARTICLE_IDENTIFIER_FORMAT = "word.article.__%sine__.__%s__";
		String WORD_ARTICLE_ALL_IDENTIFIER_FORMAT = "word.article.all.__%sine__";
		String COMPARISON_OPERATOR_IDENTIFIER_FORMAT = "__comparison.operator%s__";
		
		CaseType getCaseType();
		ToStringMapping setCaseType(CaseType caseType);
		
		Boolean getCachable();
		ToStringMapping setCachable(Boolean cachable);
		
		ListenerHelper.Executor.Function.Adapter.Default.String<Datasource> getDatasourcesExecutor();
		ToStringMapping setDatasourcesExecutor(ListenerHelper.Executor.Function.Adapter.Default.String<Datasource> datasourcesExecutor);
		
		Boolean getPlural();
		ToStringMapping setPlural(Boolean value);
		
		Boolean getGender();
		ToStringMapping setGender(Boolean value);
		
		Boolean getGenderAny();
		ToStringMapping setGenderAny(Boolean value);
		
		Boolean getWordArticleAll();
		ToStringMapping setWordArticleAll(Boolean value);
		
		@Getter @Setter
		public static class Adapter extends org.cyk.utility.common.Mapping.Adapter.Default<String,String> implements ToStringMapping,Serializable {
			private static final long serialVersionUID = 1L;

			protected CaseType caseType;
			protected Boolean cachable;
			protected ListenerHelper.Executor.Function.Adapter.Default.String<Datasource> datasourcesExecutor;
			
			public Adapter(String input) {
				super(String.class, input, String.class);
				setName("build string cache identifier");
			}

			@Override
			public ToStringMapping setCaseType(CaseType caseType){
				return null;
			}
			
			@Override
			public ToStringMapping setCachable(Boolean cachable){
				return null;
			}
			
			@Override
			public ToStringMapping setDatasourcesExecutor(ListenerHelper.Executor.Function.Adapter.Default.String<Datasource> datasourcesExecutor){
				return null;
			}
			
			@Override
			public Boolean getPlural() {
				return null;
			}
			
			@Override
			public ToStringMapping setPlural(Boolean value) {
				return null;
			}
			
			@Override
			public Boolean getGender() {
				return null;
			}
			
			@Override
			public ToStringMapping setGender(Boolean value) {
				return null;
			}
			
			@Override
			public Boolean getGenderAny() {
				return null;
			}
			
			@Override
			public ToStringMapping setGenderAny(Boolean value) {
				return null;
			}
			
			@Override
			public Boolean getWordArticleAll() {
				return null;
			}
			
			@Override
			public ToStringMapping setWordArticleAll(Boolean value) {
				return null;
			}
			
			/**/
			
			@Getter @Setter
			public static class Default extends ToStringMapping.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(String input) {
					super(input);
					setCachable(Boolean.TRUE);
				}
				
				public Default() {
					this(null);
				}
				
				@Override
				public ToStringMapping setCaseType(CaseType caseType){
					this.caseType = caseType;
					return this;
				}
				
				@Override
				public ToStringMapping setCachable(Boolean cachable){
					this.cachable = cachable;
					return this;
				}
				
				@Override
				public ToStringMapping setDatasourcesExecutor(ListenerHelper.Executor.Function.Adapter.Default.String<Datasource> datasourcesExecutor){
					this.datasourcesExecutor = datasourcesExecutor;
					return this;
				}
				
				@Override
				public Boolean getPlural() {
					return (Boolean) getProperty(PROPERTY_NAME_PLURAL);
				}
				
				@Override
				public ToStringMapping setPlural(Boolean value) {
					setProperty(PROPERTY_NAME_PLURAL, value);
					return this;
				}
				
				@Override
				public Boolean getGender() {
					return (Boolean) getProperty(PROPERTY_NAME_GENDER);
				}
				
				@Override
				public ToStringMapping setGender(Boolean value) {
					setProperty(PROPERTY_NAME_GENDER, value);
					return this;
				}
				
				@Override
				public Boolean getGenderAny() {
					return (Boolean) getProperty(PROPERTY_NAME_GENDER_ANY);
				}
				
				@Override
				public ToStringMapping setGenderAny(Boolean value) {
					setProperty(PROPERTY_NAME_GENDER_ANY, value);
					return this;
				}
				
				@Override
				public Boolean getWordArticleAll() {
					return (Boolean) getProperty(PROPERTY_NAME_WORD_ARTICLE_ALL);
				}
				
				@Override
				public ToStringMapping setWordArticleAll(Boolean value) {
					setProperty(PROPERTY_NAME_WORD_ARTICLE_ALL, value);
					return this;
				}
				
				@Override
				protected String __execute__() {
					String identifier = getInput();
					String gender = null , all = null;
					Boolean plural = getPlural();
					Locale locale = InstanceHelper.getInstance().getIfNotNullElseDefault(getLocale(), Locale.FRENCH);
					Boolean useGender = getGender();
					
					if(Boolean.TRUE.equals(getWordArticleAll())){
						all = StringHelper.getInstance().getWordArticleAll(StringHelper.getInstance().isMasculine(identifier), plural);
						if(!StringHelper.getInstance().isBlank(all))
							all+=Constant.CHARACTER_SPACE;
					}
					all = StringUtils.defaultString(all, Constant.EMPTY_STRING);
					
					if(Boolean.TRUE.equals(useGender)){
						gender = StringHelper.getInstance().getWordArticle(StringHelper.getInstance().isMasculine(identifier), getGenderAny(), plural);
						if(!StringHelper.getInstance().isBlank(gender))
							gender+=Constant.CHARACTER_SPACE;
					}
					gender = StringUtils.defaultString(gender, Constant.EMPTY_STRING);
					
					if(Boolean.TRUE.equals(plural))
						identifier = String.format(PLURAL_FORMAT,identifier);
					String result = __execute__(identifier, getCaseType(), locale, getCachable());
					if(Locale.FRENCH.equals(locale)){
						if(ArrayUtils.contains(new String[]{"le ","la "}, gender) && StringHelper.getInstance().isVoyel(result.charAt(0)))
							gender = "l'";
					}
					return all+gender+result;
				}
				
				private String __execute__(final String identifier,final CaseType pCaseType,final Locale pLocale,Boolean cachabled){
					final Locale locale = commonUtils.getValueIfNotNullElseDefault(pLocale, Locale.FRENCH);
					final CaseType caseType = commonUtils.getValueIfNotNullElseDefault(pCaseType, CaseType.DEFAULT);
					final java.util.Collection<Object> parameters = getParameters();
					
					ListenerHelper.Executor.Function.Adapter.Default.String<Datasource> datasourcesExecutor = getDatasourcesExecutor();
					if(datasourcesExecutor==null){
						this.datasourcesExecutor = datasourcesExecutor = new ListenerHelper.Executor.Function.Adapter.Default.String<Datasource>();
						datasourcesExecutor.setReturnFirstNotNull(Boolean.TRUE)
								.setResultMethod(new ListenerHelper.Executor.ResultMethod.Adapter.Default.String<Datasource>() {
									private static final long serialVersionUID = 1L;

									@Override
									protected java.lang.String __execute__() {
										java.lang.String lIdentifier = identifier;
										if(getListener() instanceof Datasource.Cache)
											lIdentifier = new StringHelper.Builder.CacheIdentifier.Adapter.Default().setInput(identifier)
													.setLocale(ToStringMapping.Adapter.Default.this.getLocale()).setParameters(ToStringMapping.Adapter.Default.this.getParameters())
													.addManyParameters(InstanceHelper.getInstance().getIfNotNullElseDefault(ToStringMapping.Adapter.Default.this.getCaseType(),CaseType.DEFAULT)).execute();
										return getListener().setInput(lIdentifier).setLocale(ToStringMapping.Adapter.Default.this.getLocale())
												.setParameters(ToStringMapping.Adapter.Default.this.getParameters())
												.setParent(ToStringMapping.Adapter.Default.this).execute();
									}
								}).setInput(ClassHelper.getInstance().instanciateMany(Datasource.class,Datasource.CLASSES));
						for(Datasource datasource : datasourcesExecutor.getInput()){
							datasource.setInput(identifier);
						}
					}
					String value = datasourcesExecutor.execute();
					
					if(value==null){
						value = MAP.get(identifier);
						if(value==null){
							for(Entry<String, ClassLoader> entry : RESOURCE_BUNDLE_MAP.entrySet()){
								try {
									ResourceBundle resourceBundle = ResourceBundle.getBundle(entry.getKey(), locale, entry.getValue());
									value = CollectionHelper.getInstance().isEmpty(parameters)?resourceBundle.getString(identifier):MessageFormat.format(resourceBundle.getString(identifier),parameters);
									
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
					
					if(value==null)
						value = String.format(UNKNOWN_FORMAT, UNKNOWN_MARKER_START,identifier,UNKNOWN_MARKER_END);
					
					return value;
				}
				
				
			}
			
		}

		/**/
		
		public static interface Datasource extends Action<String, String>{
			
			java.util.Collection<Class<? extends Datasource>> CLASSES = new ArrayList<>();
			
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
					
					public static void initialize(){
						StringHelper.ToStringMapping.Datasource.CLASSES.add(StringHelper.ToStringMapping.Datasource.UserDefined.Adapter.Default.class);
						StringHelper.ToStringMapping.Datasource.CLASSES.add(StringHelper.ToStringMapping.Datasource.Cache.Adapter.Default.class);
						StringHelper.ToStringMapping.Datasource.CLASSES.add(StringHelper.ToStringMapping.Datasource.ResourceBundle.Adapter.Default.class);
						/*
						StringHelper.ToStringMapping.DATASOURCES.add(new StringHelper.ToStringMapping.Datasource.UserDefined.Adapter.Default());
						StringHelper.ToStringMapping.DATASOURCES.add(new StringHelper.ToStringMapping.Datasource.Cache.Adapter.Default());
						StringHelper.ToStringMapping.DATASOURCES.add(new StringHelper.ToStringMapping.Datasource.ResourceBundle.Adapter.Default());
						*/
				        StringHelper.ToStringMapping.Datasource.Adapter.Default.ResourceBundle.REPOSITORY.put("org.cyk.utility.common.i18n", CommonUtils.class.getClassLoader());
						StringHelper.ToStringMapping.Datasource.Adapter.Default.ResourceBundle.REPOSITORY.put("org.cyk.utility.common.field", CommonUtils.class.getClassLoader());
						StringHelper.ToStringMapping.Datasource.Adapter.Default.ResourceBundle.REPOSITORY.put("org.cyk.utility.common.condition", CommonUtils.class.getClassLoader());
				        
					}
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
							String identifier = getInput();
							final Locale locale = commonUtils.getValueIfNotNullElseDefault(getParent().getLocale(), Locale.FRENCH);
							final CaseType caseType = commonUtils.getValueIfNotNullElseDefault(getParent().getCaseType(), CaseType.DEFAULT);
							final Boolean cachable = commonUtils.getValueIfNotNullElseDefault(getParent().getCachable(), Boolean.TRUE);
							return __execute__(identifier, caseType, locale, cachable);
							
						}
					
						private String __execute__(final String identifier,final CaseType caseType,final Locale locale,Boolean cachable){
							for(Entry<String, ClassLoader> entry : REPOSITORY.entrySet()){
								try {
									java.util.ResourceBundle resourceBundle = java.util.ResourceBundle.getBundle(entry.getKey(), locale, entry.getValue());
									String value = CollectionHelper.getInstance().isEmpty(parameters)?resourceBundle.getString(identifier):MessageFormat.format(resourceBundle.getString(identifier)
											,CollectionHelper.getInstance().getArray(parameters));
									
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
										Datasource.Cache.REPOSITORY.put(cacheIdentifier, value);
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
