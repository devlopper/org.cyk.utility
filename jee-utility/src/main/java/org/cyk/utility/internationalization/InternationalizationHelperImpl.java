package org.cyk.utility.internationalization;

import java.io.Serializable;
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

import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.constant.ConstantCharacter;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.array.ArrayHelperImpl;
import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.locale.LocaleHelper;
import org.cyk.utility.string.Case;
import org.cyk.utility.string.StringHelperImpl;
import org.cyk.utility.string.Strings;
import org.cyk.utility.string.repository.ResourceBundle;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.exception.EntityNotFoundException;
import org.cyk.utility.system.exception.ServiceNotFoundException;
import org.cyk.utility.value.ValueHelperImpl;

@ApplicationScoped
public class InternationalizationHelperImpl extends AbstractHelper implements InternationalizationHelper,Serializable {
	private static final long serialVersionUID = 1L;

	//private static final Map<String,InternalizationKey> INTERNALIZATION_KEYS_MAP = new HashMap<>();
	private static final Map<String,String> INTERNALIZATION_STRINGS_MAP = new HashMap<>();
	/*
	 * String-hi-NOUN
	 * Class-xxx-xxx-xxx-xxx-xxx-VERB
	 */
	private static final String INTERNALIZATION_KEYS_MAP_KEY_FORMAT = "%s-%s-%s";
	
	public static String __buildKeyCacheEntryIdentifier__(Object value, InternationalizationKeyStringType type) {
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
	
	public static String __buildKeyCacheEntryIdentifier__(Object value) {
		return __buildKeyCacheEntryIdentifier__(value,null);
	}
	
	public static InternationalizationKey __buildKey__(Object value, InternationalizationKeyStringType type) {
		if(value == null)
			return null;
		InternationalizationKey internalizationKey = null;
		//String cacheEntryIdentifier = __buildInternationalizationKeyCacheEntryIdentifier__(value, type);
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
			if(StringHelperImpl.__isBlank__((String) key))
				return null;
		}
		if(key instanceof Class) {
			Class<?> clazz = (Class<?>) key;
			if(ClassHelper.isInstanceOf(clazz, SystemAction.class))
				key = StringUtils.substringAfter(clazz.getSimpleName(), SystemAction.class.getSimpleName());
			else
				key = StringHelperImpl.__applyCase__(clazz.getSimpleName(),Case.FIRST_CHARACTER_LOWER);
			key = StringUtils.substringBeforeLast(key.toString(), "Impl");
		}
		
		String[] strings = StringUtils.split(key.toString(),DOT);
		Collection<String> tokens = null;
		
		if(ArrayHelperImpl.__isNotEmpty__(strings)) {
			tokens = new ArrayList<>();
			for(String index : strings) {
				tokens.addAll(StringHelperImpl.__splitByCharacterTypeCamelCase__(index));
			}
			internalizationKey = new InternationalizationKey().setValue(StringHelperImpl.__concatenate__(tokens,DOT).toLowerCase());
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
					__buildString__(__buildKey__(serviceNotFoundException.getSystemAction(),InternationalizationKeyStringType.NOUN))
					,__buildString__(__buildKey__(serviceNotFoundException.getSystemAction().getEntityClass()))
				});
			}else if(value instanceof EntityNotFoundException) {
				EntityNotFoundException entityNotFoundException = (EntityNotFoundException) value;
				internalizationKey.setArguments(new Object[] {
						__buildString__(__buildKey__(entityNotFoundException.getSystemAction().getEntityClass()))
						,entityNotFoundException.getSystemAction().getEntitiesIdentifiers().getFirst()
				});
			}else if(value instanceof RuntimeException) {
				RuntimeException runtimeException = (RuntimeException) value;
				internalizationKey.setArguments(new Object[] {runtimeException.getMessage()});
			}				
		}
		
		return internalizationKey;
	}
	
	public static InternationalizationKey __buildKey__(Object value) {
		return __buildKey__(value,null);
	}
	
	public static String __buildString__(InternationalizationKey key,Object[] arguments,Locale locale,Case kase) {
		ValueHelperImpl.__throwIfBlank__("internalization key", key);
		String cacheEntityIdentifier = key.buildCacheEntryIdentifier(locale,kase);
		//get it from cache
		String result = INTERNALIZATION_STRINGS_MAP.get(cacheEntityIdentifier);
		if(StringHelperImpl.__isNotBlank__(result))
			return result;
		//get it from resource bundles
		result = __getFromResourceBundles__(key.getValue(), arguments == null ? key.getArguments() : arguments, locale, kase, null);
		if(StringHelperImpl.__isBlank__(result)) {
			//derive it from related
			Collection<Strings> related = __deriveKeys__(key.getValue());
			if(CollectionHelper.isNotEmpty(related)) {
				for(Strings index : related) {
					result = __buildPhraseFromKeysValues__(index.get(),ValueHelperImpl.__defaultToIfBlank__(kase, Case.NONE));
					if(StringHelperImpl.__isNotBlank__(result))
						break;
				}
			}
		}
		
		if(StringHelperImpl.__isBlank__(result)) {
			//mark it as not found
			result = UNKNOWN_MARKER_START+key.getValue()+UNKNOWN_MARKER_END;
		}
		INTERNALIZATION_STRINGS_MAP.put(cacheEntityIdentifier, result);
		return result;
	}
	
	public static List<Strings> __deriveKeys__(String key) {
		if(StringHelperImpl.__isBlank__(key))
			return null;
		List<Strings> collection = new ArrayList<Strings>();
		if(key != null) {
			String keyAsString = __buildKey__(key).getValue();
			if(StringHelperImpl.__isNotBlank__(keyAsString)) {
				if(StringUtils.contains(keyAsString, ConstantCharacter.DOT)) {
					//y.x => x of y
					for(String index : X_OF_Y) {
						String dotIndex = ConstantCharacter.DOT + index;
						if(StringUtils.endsWith(keyAsString, dotIndex)) {		
							collection.add(__inject__(Strings.class).add(index,OF,StringUtils.substringBeforeLast(keyAsString, dotIndex)));
							break;
						}
					}	
				}
			}
		}
		return collection;
	}
	
	public static String __buildString__(InternationalizationKey key) {
		return __buildString__(key, null, null, null);
	}
	
	public static String __buildString__(String key,Object[] parameters,Locale locale,Case kase) {
		return __buildString__(new InternationalizationKey().setValue(key),parameters,locale,kase);
	}
	
	public static String __buildString__(String key) {
		return __buildString__(key, null, null, null);
	}
	
	public static String __buildPhrase__(Collection<InternationalizationKey> keys,Locale locale,Case kase) {
		if(CollectionHelper.isEmpty(keys))
			return null;
		locale = ValueHelperImpl.__defaultToIfNull__(locale, __inject__(LocaleHelper.class).getLocaleDefaultIfNull());
		kase = ValueHelperImpl.__defaultToIfNull__(kase,Case.FIRST_CHARACTER_UPPER_REMAINDER_LOWER);
		//String cacheEntityIdentifier = InternationalizationKey.__buildCacheEntryIdentifier__(keys,locale,kase);
		//String result = INTERNALIZATION_STRINGS_MAP.get(cacheEntityIdentifier);
		//if(StringHelperImpl.__isNotBlank__(result))
		//	return result;
		Collection<String> phraseStrings = new ArrayList<>();
		for(InternationalizationKey index : keys)
			phraseStrings.add(__buildString__(index, null, locale, Case.NONE));
		String phrase = ____buildPhrase____(phraseStrings, kase);
		//INTERNALIZATION_STRINGS_MAP.put(cacheEntityIdentifier, string);
		return phrase;
	}
	
	private static String ____buildPhrase____(Collection<String> strings,Case kase) {
		return CollectionHelper.isEmpty(strings) ? null : StringHelperImpl.__applyCase__(StringHelperImpl.__concatenate__(strings, SEPARATOR), kase);
	}
	
	public static String __buildPhrase__(Collection<InternationalizationKey> keys) {
		return __buildPhrase__(keys, null, null);
	}
	
	public static String __buildPhraseFromKeysValues__(Collection<String> keysValues,Locale locale,Case kase) {
		return keysValues == null ? null : __buildPhrase__(keysValues.stream().map(x -> new InternationalizationKey().setValue(x))
				.collect(Collectors.toList()), locale, kase);
	}
	
	public static String __buildPhraseFromKeysValues__(Collection<String> keysValues,Case kase) {
		return __buildPhraseFromKeysValues__(keysValues, null, kase);
	}
	
	public static String __buildPhraseFromKeysValues__(Collection<String> keysValues) {
		return __buildPhraseFromKeysValues__(keysValues, null, null);
	}
	
	public static String __buildPhraseFromKeysValues__(Locale locale,Case kase,String...keysValues) {
		return keysValues == null ? null : __buildPhraseFromKeysValues__(List.of(keysValues),locale,kase);
	}
	
	public static String __buildPhraseFromKeysValues__(Case kase,String...keysValues) {
		return __buildPhraseFromKeysValues__(null, kase, keysValues);
	}
	
	public static String __buildPhraseFromKeysValues__(String...keysValues) {
		return __buildPhraseFromKeysValues__(null, null, keysValues);
	}
	
	public static String __buildPhrase__(Collection<InternationalizationString> strings,Case kase) {
		if(CollectionHelper.isEmpty(strings))
			return null;
		Collection<String> phraseStrings = new ArrayList<>();
		for(InternationalizationString index : strings) {
			if(!Boolean.TRUE.equals(index.getIsHasBeenProcessed()))
				__processStrings__(index);
			phraseStrings.add(index.getValue());
		}
		return ____buildPhrase____(phraseStrings, kase);
	}
	
	public static void __processStrings__(Collection<InternationalizationString> internalizationStrings) {
		if(CollectionHelper.isNotEmpty(internalizationStrings))
			for(InternationalizationString index : internalizationStrings)
				if(!Boolean.TRUE.equals(index.getIsHasBeenProcessed()))
					index.setValue(__buildString__(index.getKey(), null, index.getLocale(), index.getKase()));
	}
	
	public static void __processStrings__(InternationalizationString...internalizationStrings) {
		__processStrings__(List.of(internalizationStrings));
	}
	
	public static void __processPhrases__(Collection<InternationalizationPhrase> internalizationPhrases) {
		if(CollectionHelper.isNotEmpty(internalizationPhrases))
			for(InternationalizationPhrase index : internalizationPhrases)
				if(CollectionHelper.isNotEmpty(index.getStrings())) {
					__processStrings__(index.getStrings().get());
					index.setValue(____buildPhrase____(index.getStrings().get().stream().map(InternationalizationString::getValue).collect(Collectors.toList()), index.getKase()));
				}
	}
	
	public static void __processPhrases__(InternationalizationPhrase...internalizationPhrases) {
		__processPhrases__(List.of(internalizationPhrases));
	}
	
	public static void __addResourceBundleAt__(String baseName, ClassLoader classLoader,Integer index) {
		if(classLoader == null)
			classLoader = InternationalizationHelperImpl.class.getClassLoader();
		Boolean found = Boolean.FALSE;
		for(ResourceBundle indexResourceBundle : RESOURCE_BUNDLES)
			if(indexResourceBundle.getName().equals(baseName) && indexResourceBundle.getClassLoader().equals(classLoader)) {
				found = Boolean.TRUE;
				break;
			}
		if(Boolean.FALSE.equals(found)) {
			ResourceBundle bundle = new ResourceBundle(baseName, classLoader);
			if(index == null || index < 0)
				RESOURCE_BUNDLES.add(bundle);
			else
				RESOURCE_BUNDLES.add(index, bundle);
		}
	}
	
	public static void __addResourceBundleAt__(String baseName,Integer index) {
		__addResourceBundleAt__(baseName,null,index);
	}
	
	public void __addResourceBundle__(String baseName, ClassLoader classLoader) {
		__addResourceBundleAt__(baseName, classLoader, null);
	}
	
	public static void __addResourceBundle__(String baseName,Integer index) {
		__addResourceBundleAt__(baseName, null,index);
	}
	
	public static void __addResourceBundle__(String... baseNames) {
		if(baseNames!=null)
			for(String index : baseNames)
				__addResourceBundleAt__(index, null,null);
	}
	
	public static String __getFromResourceBundles__(String identifier,Object[] arguments,Locale locale,Case aCase,Collection<ResourceBundle> resourceBundles) {
		resourceBundles = resourceBundles == null ? RESOURCE_BUNDLES : CollectionHelper.concatenate(resourceBundles,RESOURCE_BUNDLES);
		if(CollectionHelper.isEmpty(resourceBundles)){
			//TODO log a warning
		}else{
			locale = ValueHelperImpl.__defaultToIfNull__(locale, __inject__(LocaleHelper.class).getLocaleDefaultIfNull());
			aCase = ValueHelperImpl.__defaultToIfNull__(aCase,Case.DEFAULT);
			for(ResourceBundle index : resourceBundles){
				try {
					java.util.ResourceBundle resourceBundle = java.util.ResourceBundle.getBundle(index.getName(), locale
							, ValueHelperImpl.__defaultToIfNull__(index.getClassLoader(), InternationalizationHelperImpl.class.getClassLoader()) );
					String value = resourceBundle.getString(identifier);
					if(ArrayHelperImpl.__isNotEmpty__(arguments))
						value = MessageFormat.format(value,arguments);
					String substituteCode = null;
					while((substituteCode = StringUtils.substringBetween(value, SUBSTITUTE_TAG_START, SUBSTITUTE_TAG_END)) != null){
						String substituteValue = __getFromResourceBundles__(substituteCode,null,locale,Case.NONE,null);
						if(substituteValue == null){
							value = __formatUnknownIdentifier__(substituteCode);
							break;
						}else
							value = StringUtils.replace(value, SUBSTITUTE_TAG_START+substituteCode+SUBSTITUTE_TAG_END, substituteValue);										
					}
					
					if(StringUtils.startsWith(value,DO_NOT_PROCESS_TAG_START) && StringUtils.endsWith(value,DO_NOT_PROCESS_TAG_END)){
						value = StringUtils.substringBetween(value, DO_NOT_PROCESS_TAG_START, DO_NOT_PROCESS_TAG_END);
					}else{
						value = StringHelperImpl.__applyCase__(value, aCase);
					}
					return value;
				} catch (Exception e) {
					//It is not in that bundle. Let try the next one
				}
			}	
		}
		return null;		
	}
	
	private static String __formatUnknownIdentifier__(String identifier){
		return String.format(UNKNOWN_FORMAT, UNKNOWN_MARKER_START,identifier,UNKNOWN_MARKER_END);
	}
	
	private static final String DOT = ConstantCharacter.DOT.toString();
	private static final String UNKNOWN_MARKER_START = "##??";
	private static final String UNKNOWN_MARKER_END = "??##";
	private static final String UNKNOWN_FORMAT = "%s%s%s";
	
	private static final String DO_NOT_PROCESS_TAG = "cyk_donotprocess";
	private static final String DO_NOT_PROCESS_TAG_START = "<"+DO_NOT_PROCESS_TAG+">";
	private static final String DO_NOT_PROCESS_TAG_END = "</"+DO_NOT_PROCESS_TAG+">";
	
	private static final String SUBSTITUTE_TAG = "cyk_code";
	private static final String SUBSTITUTE_TAG_START = "<"+SUBSTITUTE_TAG+">";
	private static final String SUBSTITUTE_TAG_END = "</"+SUBSTITUTE_TAG+">";
	
	private static final String OF = "of";
	private static final String[] X_OF_Y = {"type","category"};
	
	private static final String SEPARATOR = ConstantCharacter.SPACE.toString();
	
	private static final List<ResourceBundle> RESOURCE_BUNDLES;
	static {
		RESOURCE_BUNDLES = new ArrayList<>();
		for(String index : new String[] {"word","phrase","throwable","assertion"})
			__addResourceBundle__(InternationalizationHelperImpl.class.getPackage().getName()+"."+index);
	}

}
