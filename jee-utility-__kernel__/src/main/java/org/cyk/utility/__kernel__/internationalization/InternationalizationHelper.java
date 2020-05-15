package org.cyk.utility.__kernel__.internationalization;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.constant.ConstantCharacter;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.locale.LocaleHelper;
import org.cyk.utility.__kernel__.string.Case;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.__kernel__.throwable.EntityNotFoundException;
import org.cyk.utility.__kernel__.throwable.ServiceNotFoundException;

public interface InternationalizationHelper {
	
	Map<String,String> INTERNALIZATION_STRINGS_MAP = new HashMap<>();
	/*
	 * String-hi-NOUN
	 * Class-xxx-xxx-xxx-xxx-xxx-VERB
	 */
	String INTERNALIZATION_KEYS_MAP_KEY_FORMAT = "%s-%s-%s";
	
	static String buildKeyCacheEntryIdentifier(Object value, InternationalizationKeyStringType type) {
		if(value == null)
			return null;
		String group = null,valueAsString=null,identifier=null;
		if(value instanceof SystemAction && ((SystemAction)value).getIdentifier() == null)
			value = value.getClass();
			
		if(value instanceof Class) {
			group = "Class";
			valueAsString = ((Class<?>)value).getName();
		}else if(value instanceof String) {
			group = "String";
			valueAsString = (String) value;
		}else if(value instanceof SystemAction) {
			group = "SystemAction";
			valueAsString = ((SystemAction) value).getIdentifier().toString();
		}
		if(valueAsString != null)
			identifier = String.format(INTERNALIZATION_KEYS_MAP_KEY_FORMAT, group,valueAsString,type == null ? ConstantEmpty.STRING : type.name());
		return identifier;
	}
	
	static String buildKeyCacheEntryIdentifier(Object value) {
		return buildKeyCacheEntryIdentifier(value,null);
	}
	
	static InternationalizationKey buildKey(Object value, InternationalizationKeyStringType type) {
		if(value == null)
			return null;
		InternationalizationKey internalizationKey = null;
		//String cacheEntryIdentifier = buildInternationalizationKeyCacheEntryIdentifier(value, type);
		//if(cacheEntryIdentifier != null && (internalizationKey = INTERNALIZATION_KEYS_MAP.get(cacheEntryIdentifier))!=null)
		//	return internalizationKey;
		//Value
		Object key = value;		
		if(key instanceof Throwable) {
			key = key.getClass().getName();
			if(StringUtils.endsWith((String)key, "Impl"))
				key = StringUtils.substringBeforeLast((String)key, "Impl");
		}
		if(key instanceof SystemAction) {
			if(((SystemAction)key).getIdentifier() == null)
				key = ((SystemAction)key).getClass();
			else
				key = ((SystemAction)key).getIdentifier();
		}
		if(key instanceof String) {
			if(StringHelper.isBlank((String) key))
				return null;
		}
		if(key instanceof Class) {
			Class<?> clazz = (Class<?>) key;
			if(ClassHelper.isInstanceOf(clazz, SystemAction.class))
				key = StringUtils.substringAfter(clazz.getSimpleName(), SystemAction.class.getSimpleName());
			else
				key = StringHelper.applyCase(clazz.getSimpleName(),Case.FIRST_CHARACTER_LOWER);
			key = StringUtils.substringBeforeLast(key.toString(), "Impl");
		}
		
		String[] strings = StringUtils.split(key.toString(),DOT);
		Collection<String> tokens = null;
		
		if(strings != null && strings.length > 0) {
			tokens = new ArrayList<>();
			for(String index : strings) {
				tokens.addAll(StringHelper.splitByCharacterTypeCamelCase(index));
			}
			internalizationKey = new InternationalizationKey().setValue(StringHelper.concatenate(tokens,DOT).toLowerCase());
		}
				
		if(internalizationKey != null) {
			//if(cacheEntryIdentifier != null)
			//	INTERNALIZATION_KEYS_MAP.put(cacheEntryIdentifier, internalizationKey);
			if(type!=null)
				internalizationKey = internalizationKey.setValue(String.format(type.getFormat(), internalizationKey.getValue()));
			//Arguments
			if(value instanceof UnknownHostException) {
				internalizationKey.setArguments(new Object[] {((UnknownHostException)value).getMessage().trim()});
			}else if(value instanceof ConnectException) {
				internalizationKey.setArguments(new Object[] {((ConnectException) value).getMessage().trim()});
			}else if(value instanceof ServiceNotFoundException) {
				ServiceNotFoundException serviceNotFoundException = (ServiceNotFoundException) value;
				internalizationKey.setArguments(new Object[] {
					buildString(buildKey(serviceNotFoundException.getSystemAction(),InternationalizationKeyStringType.NOUN))
					,buildString(buildKey(serviceNotFoundException.getSystemAction().getEntityClass()))
				});
			}else if(value instanceof EntityNotFoundException) {
				EntityNotFoundException entityNotFoundException = (EntityNotFoundException) value;
				internalizationKey.setArguments(new Object[] {
						buildString(buildKey(entityNotFoundException.getSystemAction().getEntityClass()))
						,entityNotFoundException.getSystemAction().getEntitiesIdentifiers().getFirst()
				});
			}else if(value instanceof RuntimeException) {
				RuntimeException runtimeException = (RuntimeException) value;
				internalizationKey.setArguments(new Object[] {runtimeException.getMessage()});
			}				
		}
		
		return internalizationKey;
	}
	
	static InternationalizationKey buildKey(Object value) {
		return buildKey(value,null);
	}
	
	static String buildString(InternationalizationKey key,Object[] arguments,Locale locale,Case kase) {
		if(key == null)
			return null;
		String cacheEntityIdentifier = key.buildCacheEntryIdentifier(locale,kase);
		//get it from cache
		String result = INTERNALIZATION_STRINGS_MAP.get(cacheEntityIdentifier);
		if(StringHelper.isNotBlank(result))
			return result;
		//get it from resource bundles
		result = getFromResourceBundles(key.getValue(), arguments == null ? key.getArguments() : arguments, locale, kase, null);
		if(StringHelper.isBlank(result)) {
			//derive it from related
			Collection<Strings> related = deriveKeys(key.getValue());
			if(CollectionHelper.isNotEmpty(related)) {
				for(Strings index : related) {
					result = buildPhraseFromKeysValues(index.get(),kase == null ? Case.NONE : kase);
					if(StringHelper.isNotBlank(result))
						break;
				}
			}
		}
		
		if(StringHelper.isBlank(result)) {
			//mark it as not found
			result = UNKNOWN_MARKER_START+key.getValue()+UNKNOWN_MARKER_END;
		}
		INTERNALIZATION_STRINGS_MAP.put(cacheEntityIdentifier, result);
		return result;
	}
	
	static List<Strings> deriveKeys(String key) {
		if(StringHelper.isBlank(key))
			return null;
		List<Strings> collection = new ArrayList<Strings>();
		if(key != null) {
			String keyAsString = buildKey(key).getValue();
			if(StringHelper.isNotBlank(keyAsString)) {
				if(StringUtils.contains(keyAsString, ConstantCharacter.DOT)) {
					//y.x => x of y
					for(String index : X_OF_Y) {
						String dotIndex = ConstantCharacter.DOT + index;
						if(StringUtils.endsWith(keyAsString, dotIndex)) {		
							collection.add(DependencyInjection.inject(Strings.class).add(index,OF,StringUtils.substringBeforeLast(keyAsString, dotIndex)));
							break;
						}
					}	
				}
			}
		}
		return collection;
	}
	
	static String buildString(InternationalizationKey key) {
		return buildString(key, null, null, null);
	}
	
	static String buildString(String key,Object[] parameters,Locale locale,Case kase) {
		return buildString(new InternationalizationKey().setValue(key),parameters,locale,kase);
	}
	
	static String buildString(String key) {
		return buildString(key, null, null, null);
	}
	
	static String buildPhrase(Collection<InternationalizationKey> keys,Locale locale,Case kase) {
		if(CollectionHelper.isEmpty(keys))
			return null;
		if(locale == null)
			locale = LocaleHelper.getDefault();
		if(kase == null)
			kase = Case.FIRST_CHARACTER_UPPER_REMAINDER_LOWER;
		//String cacheEntityIdentifier = InternationalizationKey.buildCacheEntryIdentifier(keys,locale,kase);
		//String result = INTERNALIZATION_STRINGS_MAP.get(cacheEntityIdentifier);
		//if(StringHelper.isNotBlank(result))
		//	return result;
		Collection<String> phraseStrings = new ArrayList<>();
		for(InternationalizationKey index : keys)
			phraseStrings.add(buildString(index, null, locale, Case.NONE));
		String phrase = __buildPhrase__(phraseStrings, kase);
		//INTERNALIZATION_STRINGS_MAP.put(cacheEntityIdentifier, string);
		return phrase;
	}
	
	private static String __buildPhrase__(Collection<String> strings,Case kase) {
		return CollectionHelper.isEmpty(strings) ? null : StringHelper.applyCase(StringHelper.concatenate(strings, SEPARATOR), kase);
	}
	
	static String buildPhrase(Collection<InternationalizationKey> keys) {
		return buildPhrase(keys, null, null);
	}
	
	static String buildPhraseFromKeysValues(Collection<String> keysValues,Locale locale,Case kase) {
		return keysValues == null ? null : buildPhrase(keysValues.stream().map(x -> new InternationalizationKey().setValue(x))
				.collect(Collectors.toList()), locale, kase);
	}
	
	static String buildPhraseFromKeysValues(Collection<String> keysValues,Case kase) {
		return buildPhraseFromKeysValues(keysValues, null, kase);
	}
	
	static String buildPhraseFromKeysValues(Collection<String> keysValues) {
		return buildPhraseFromKeysValues(keysValues, null, null);
	}
	
	static String buildPhraseFromKeysValues(Locale locale,Case kase,String...keysValues) {
		return keysValues == null ? null : buildPhraseFromKeysValues(List.of(keysValues),locale,kase);
	}
	
	static String buildPhraseFromKeysValues(Case kase,String...keysValues) {
		return buildPhraseFromKeysValues(null, kase, keysValues);
	}
	
	static String buildPhraseFromKeysValues(String...keysValues) {
		return buildPhraseFromKeysValues(null, null, keysValues);
	}
	
	static String buildPhrase(Collection<InternationalizationString> strings,Case kase) {
		if(CollectionHelper.isEmpty(strings))
			return null;
		Collection<String> phraseStrings = new ArrayList<>();
		for(InternationalizationString index : strings) {
			if(!Boolean.TRUE.equals(index.getIsHasBeenProcessed()))
				processStrings(index);
			phraseStrings.add(index.getValue());
		}
		return __buildPhrase__(phraseStrings, kase);
	}
	
	static void processStrings(Collection<InternationalizationString> internalizationStrings) {
		if(CollectionHelper.isNotEmpty(internalizationStrings))
			for(InternationalizationString index : internalizationStrings)
				if(!Boolean.TRUE.equals(index.getIsHasBeenProcessed()))
					index.setValue(buildString(index.getKey(), null, index.getLocale(), index.getKase()));
	}
	
	static void processStrings(InternationalizationString...internalizationStrings) {
		processStrings(List.of(internalizationStrings));
	}
	
	static void processPhrases(Collection<InternationalizationPhrase> internalizationPhrases) {
		if(CollectionHelper.isNotEmpty(internalizationPhrases))
			for(InternationalizationPhrase index : internalizationPhrases)
				if(CollectionHelper.isNotEmpty(index.getStrings())) {
					processStrings(index.getStrings().get());
					index.setValue(__buildPhrase__(index.getStrings().get().stream().map(InternationalizationString::getValue).collect(Collectors.toList()), index.getKase()));
				}
	}
	
	static void processPhrases(InternationalizationPhrase...internalizationPhrases) {
		processPhrases(List.of(internalizationPhrases));
	}
	
	static void addResourceBundles(Collection<ResourceBundle> resourceBundles,Integer index) {
		if(CollectionHelper.isEmpty(resourceBundles))
			return;
		if(index == null)
			RESOURCE_BUNDLES.addAll(resourceBundles);
		else
			RESOURCE_BUNDLES.addAll(index,resourceBundles);
	}
	
	static void addResourceBundles(Collection<ResourceBundle> resourceBundles) {
		addResourceBundles(resourceBundles,null);
	}
	
	static void addResourceBundles(Integer index,ResourceBundle... resourceBundles) {
		if(ArrayHelper.isEmpty(resourceBundles))
			return;
		addResourceBundles(CollectionHelper.listOf(resourceBundles),index);
	}
	
	static void addResourceBundles(ResourceBundle... resourceBundles) {
		addResourceBundles(null,resourceBundles);
	}
	
	static void addResourceBundles(Collection<String> baseNames, ClassLoader classLoader,Integer index) {
		if(baseNames == null || baseNames.isEmpty())
			return;
		ClassLoader classLoaderFinal = classLoader == null ? InternationalizationHelper.class.getClassLoader() : classLoader;
		if(index != null) {
			if(index < 0)
				index = 0;
			else if(index > RESOURCE_BUNDLES.size())
				index = null;	
		}
		addResourceBundles(baseNames.stream().map(name -> new ResourceBundle(name,classLoaderFinal)).collect(Collectors.toList()),index);
	}
	
	static void addResourceBundles(ClassLoader classLoader,Integer index,String...baseNames) {
		if(baseNames == null || baseNames.length == 0)
			return;
		addResourceBundles(List.of(baseNames),classLoader,index);
	}
	
	static void addResourceBundles(Integer index,String...baseNames) {
		if(baseNames == null || baseNames.length == 0)
			return;
		addResourceBundles(List.of(baseNames),null,index);
	}
	
	static void addResourceBundles(String...baseNames) {
		if(baseNames == null || baseNames.length == 0)
			return;
		addResourceBundles(List.of(baseNames),null,null);
	}
	
	static void addResourceBundlesFromNames(Collection<String> names,Class<?> klass,Integer index) {
		if(names == null || names.isEmpty())
			return;
		Class<?> classFinal = klass == null ? InternationalizationHelper.class : klass;
		addResourceBundles(names.stream().map(name -> classFinal.getPackageName()+"."+name).collect(Collectors.toSet()),classFinal.getClassLoader(),index);
	}
	
	static void addResourceBundlesFromNames(Class<?> klass,Integer index,String...names) {
		if(names == null || names.length == 0)
			return;
		addResourceBundlesFromNames(List.of(names), klass,index);
	}
	
	static String getFromResourceBundles(String identifier,Object[] arguments,Locale locale,Case kase,Collection<ResourceBundle> resourceBundles) {
		resourceBundles = resourceBundles == null ? RESOURCE_BUNDLES : CollectionHelper.concatenate(resourceBundles,RESOURCE_BUNDLES);
		if(CollectionHelper.isEmpty(resourceBundles)){
			//TODO log a warning
		}else{
			if(locale == null)
				locale = LocaleHelper.getDefault();
			if(kase == null)
				kase = Case.DEFAULT;
			for(ResourceBundle index : resourceBundles){
				try {
					java.util.ResourceBundle resourceBundle = java.util.ResourceBundle.getBundle(index.getName(), locale
							, index.getClassLoader() == null ? InternationalizationHelper.class.getClassLoader() : index.getClassLoader() );
					String value = resourceBundle.getString(identifier);
					if(arguments!=null && arguments.length > 0)
						value = MessageFormat.format(value,arguments);
					String substituteCode = null;
					while((substituteCode = StringUtils.substringBetween(value, SUBSTITUTE_TAG_START, SUBSTITUTE_TAG_END)) != null){
						String substituteValue = getFromResourceBundles(substituteCode,null,locale,Case.NONE,null);
						if(substituteValue == null){
							value = formatUnknownIdentifier(substituteCode);
							break;
						}else
							value = StringUtils.replace(value, SUBSTITUTE_TAG_START+substituteCode+SUBSTITUTE_TAG_END, substituteValue);										
					}
					
					if(StringUtils.startsWith(value,DO_NOT_PROCESS_TAG_START) && StringUtils.endsWith(value,DO_NOT_PROCESS_TAG_END)){
						value = StringUtils.substringBetween(value, DO_NOT_PROCESS_TAG_START, DO_NOT_PROCESS_TAG_END);
					}else{
						value = StringHelper.applyCase(value, kase);
					}
					return value;
				} catch (Exception e) {
					//It is not in that bundle. Let try the next one
				}
			}	
		}
		return null;		
	}
	
	private static String formatUnknownIdentifier(String identifier){
		return String.format(UNKNOWN_FORMAT, UNKNOWN_MARKER_START,identifier,UNKNOWN_MARKER_END);
	}
	
	String DOT = ConstantCharacter.DOT.toString();
	String UNKNOWN_MARKER_START = "##??";
	String UNKNOWN_MARKER_END = "??##";
	String UNKNOWN_FORMAT = "%s%s%s";
	
	String DO_NOT_PROCESS_TAG = "cyk_donotprocess";
	String DO_NOT_PROCESS_TAG_START = "<"+DO_NOT_PROCESS_TAG+">";
	String DO_NOT_PROCESS_TAG_END = "</"+DO_NOT_PROCESS_TAG+">";
	
	String SUBSTITUTE_TAG = "cyk_code";
	String SUBSTITUTE_TAG_START = "<"+SUBSTITUTE_TAG+">";
	String SUBSTITUTE_TAG_END = "</"+SUBSTITUTE_TAG+">";
	
	String OF = "of";
	String[] X_OF_Y = {"type","category"};
	
	String SEPARATOR = ConstantCharacter.SPACE.toString();
	
	//ResourceBundles RESOURCE_BUNDLES = DependencyInjection.inject(ResourceBundles.class);
	List<ResourceBundle> RESOURCE_BUNDLES = new ArrayList<>();
}
