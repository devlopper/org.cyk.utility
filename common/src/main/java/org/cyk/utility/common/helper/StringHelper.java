package org.cyk.utility.common.helper;

import java.beans.Introspector;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.cyk.utility.common.Action;
import org.cyk.utility.common.CommonUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.StringHelper.ToStringMapping.Datasource.Cache;
import org.cyk.utility.common.userinterface.ContentType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Singleton @Named
public class StringHelper extends AbstractHelper implements Serializable {
	private static final long serialVersionUID = 2366347884051000495L;
	
	static {
		ClassHelper.getInstance().map(Listener.class, Listener.Adapter.Default.class,Boolean.FALSE);
	}

	public static final String[] END_LINE = {"\r\n","\n"};
	public static final Character[] VOYELS = {'a','e','i','o','u','y'};
	public static final Collection<String> WORDS_FROM_CAMEL_CASE_EXCLUDED = Arrays.asList(Constant.CHARACTER_DOT.toString(),Constant.CHARACTER_DOLLAR.toString());
	
	public enum CaseType{
		NONE,L,U,FL,FU,FURL
		;
		
		public static CaseType DEFAULT = NONE;
		}
	public enum Location{START,INSIDE,END,EXAT}
	
	private static final String VARIABLE_NAME_MANY_FORMAT = "%ss";
	//@Deprecated private static final String RESOURCE_BUNDLE_NAME = "org.cyk.utility.common.i18n";
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
	
	public String removeEmptyLine(String string){
		String result;
		if(isBlank(string))
			result = string;
		else {
			String[] array = StringUtils.split(string, '\n');
			Collection<String> strings = new ArrayList<String>();
			for(String index : array)
				if(isNotBlank(index))
					strings.add(index);
			result = concatenate(strings, ContentType.TEXT.getNewLineMarker());
		}
		return result;
	}
	
	public String trim(String string){
		return StringUtils.trimToEmpty(string);
	}
	
	public Collection<String> getSubString(Integer from,Integer to,Collection<String> strings){
		Collection<String> subStrings = null;
		if(strings!=null){
			subStrings = new ArrayList<String>();
			for(String string : strings)
				subStrings.add(StringUtils.substring(string, from, to));
		}
		return subStrings;
	}
	
	public Collection<String> getSubString(Integer from,Integer to,String...strings){
		if(strings!=null)
			return getSubString(from, to, Arrays.asList(strings));
		return null;
	}
	
	public Collection<String> getSubStringOfEach(Integer from,Integer to,String string,String separator){
		return getSubString(from, to, StringUtils.split(string,separator));
	}
	
	public Collection<String> getSubStringOfEach(Integer from,Integer to,String string){
		return getSubStringOfEach(from, to, string, Constant.CHARACTER_SPACE.toString());
	}
	
	public Collection<String> getFirstLetterOfEach(String string){
		return getSubStringOfEach(0, 1, string);
	}
	
	public String concatenateFirstLetterOfEach(String string,Object separator){
		return concatenate(getFirstLetterOfEach(string), separator);
	}
	
	public String concatenateFirstLetterOfEach(String string){
		return concatenateFirstLetterOfEach(string, Constant.EMPTY_STRING);
	}
	
	public String getHtml(String string){
		string = StringEscapeUtils.escapeHtml4(string);
		string = StringUtils.replaceAll(string, ContentType.TEXT.getNewLineMarker(), ContentType.HTML.getNewLineMarker());
		string = StringUtils.replaceAll(string, "\n", ContentType.HTML.getNewLineMarker());
		return string;
	}
	
	public Boolean isContainMarkupLanguageTag(String string){
		Boolean contains = Boolean.FALSE;
		
		/*Pattern pattern = Pattern.compile("'(.*?)'");
		Matcher matcher = pattern.matcher(mydata);
		if (matcher.find())
		{
		    System.out.println(matcher.group(1));
		}*/
		
		Integer endStartIndex = StringUtils.indexOf(string, "</");
		if(endStartIndex>-1){
			Integer endEndIndex = StringUtils.indexOf(string, ">",endStartIndex+2);
			if(endEndIndex>-1){
				/*String tagName = StringUtils.substring(string, endStartIndex+2, endEndIndex);
				if(StringUtils.isNotBlank(tagName)){
					string = StringUtils.substring(string, 0, endStartIndex);
					Integer tagStartIndex = StringUtils.lastIndexOf(string, "<"+tagName);
					if(string.length() > ("<"+tagName).length()){
						String end = StringUtils.substringAfterLast(string, "<"+tagName);
						contains = tagStartIndex > -1 && StringUtils.startsWithAny(end, ">"," ");	
					}
					
				}*/
				contains = Boolean.TRUE;
			}
		}
		return contains;
	}
	
	public Boolean isVoyel(Character character){
		return ArrayUtils.contains(VOYELS, Character.toLowerCase(character));
	}
	/*
	public String generate(Long identifier){
		return identifier+System.currentTimeMillis()+Constant.CHARACTER_UNDESCORE+RandomStringUtils.randomAlphanumeric(10);
	}*/
	
	public String applyCaseType(String string,CaseType caseType){
		if(string==null)
			return null;
		if(caseType==null)
			caseType = CaseType.DEFAULT;
		switch(caseType){
		case NONE:return string;
		case L:return StringUtils.lowerCase(string);
		case U:return StringUtils.upperCase(string);
		case FU:return StringUtils.upperCase(StringUtils.substring(string, 0,1))+StringUtils.substring(string, 1);
		case FL:return StringUtils.lowerCase(StringUtils.substring(string, 0,1))+StringUtils.substring(string, 1);
		case FURL:return StringUtils.capitalize(string.toLowerCase());
		}
		return string;	
	}
	
	public List<String> applyCaseType(List<String> strings,CaseType caseType){
		List<String> list = new ArrayList<>();
		for(String string : strings)
			list.add(applyCaseType(string, caseType));
		return list;
	}
	
	public List<String> getFromCamelCase(String stringInCamelCase,Collection<String> excludedTokens){
		List<String> tokens = new ArrayList<>();
		if(isNotBlank(stringInCamelCase)){
			tokens.addAll(Arrays.asList(StringUtils.splitByCharacterTypeCamelCase(stringInCamelCase)));
		}
		CollectionHelper.getInstance().remove(tokens, excludedTokens);
		return tokens;
	}
	
	public List<String> getWordsFromCamelCase(String stringInCamelCase){
		return getFromCamelCase(stringInCamelCase,WORDS_FROM_CAMEL_CASE_EXCLUDED);
	}
	
	public String concatenateWordsFromCamelCase(String name,Object separator,CaseType caseType){
		if(name == null)
			return null;
		Collection<String> tokens = getWordsFromCamelCase(name);
		return applyCaseType(concatenate(tokens, separator),caseType);
	}
	
	public String removeEndLineMarker(String line){
		for(String endLine : END_LINE)
			if(line.endsWith(endLine))
				return line.substring(0, line.length()-endLine.length());
		return line;
	}
	
	/*@Deprecated
	public String getText(Locale locale,String identifier,Object[] parameters){
		if(locale==null)
			locale = Locale.FRENCH;
		ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME,locale);
		String value = parameters==null?resourceBundle.getString(identifier):MessageFormat.format(resourceBundle.getString(identifier),parameters);
		return value;
    }*/
	
	public String get(String identifier,CaseType caseType,Object[] parameters,Locale locale){
		return new StringHelper.ToStringMapping.Adapter.Default(identifier).setCaseType(caseType).setLocale(locale).addParameters(parameters).execute();
    }
	
	public String get(String identifier,Object[] parameters,Locale locale){
		return get(identifier,CaseType.FURL,parameters,locale);
	}
	
	public String get(String identifier,CaseType caseType,Object[] parameters){
		return get(identifier,caseType,parameters,null);
	}
	
	public String get(String identifier,Object[] parameters){
		return get(identifier, CaseType.FURL, parameters);
	}
	
	public java.lang.String getOrdinalNumberSuffix(Locale locale,Number number) {
		return get(String.format(KEY_ORDINAL_NUMBER_SUFFIX_FORMAT, number),CaseType.NONE,null,locale);
	}
	
	public java.lang.String getOrdinalNumber(Locale locale,Number number) {
		return get(String.format(KEY_ORDINAL_NUMBER_FORMAT, number),CaseType.NONE,null,locale);
	}
	
	public String concatenate(Object[] strings,Object separator){
		if(strings==null)
			return Constant.EMPTY_STRING;
		return StringUtils.join(strings, separator == null ? Constant.EMPTY_STRING : separator.toString());
	}
	
	public String concatenate(java.util.Collection<String> strings,Object separator){
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
	
	public Boolean isAtLocation(String string,Collection<String> values,Location location,Boolean caseSensitive){
		for(String value : values)
			if(Boolean.TRUE.equals(isAtLocation(string, value, location, caseSensitive)))
				return Boolean.TRUE;
		return Boolean.FALSE;
	}
	
	public Boolean isAtLocation(String string,Collection<String> values,Location location){
		return isAtLocation(string, values, location, Boolean.TRUE);
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
	
	public Boolean isNotBlank(CharSequence charSequence){
		return StringUtils.isNotBlank(charSequence);
	}
	
	public String get(java.util.Collection<?> collection,Object separator){
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
	
	public String addAtBeginingIfDoesNotStartWith(String string,String start){
		if(!isAtLocation(string, start, Location.START, Boolean.FALSE))
			return start+string;
		return string;
	}
	
	public String removeAtBeginingIfDoesNotStartWith(String string,String start){
		if(isAtLocation(string, start, Location.START, Boolean.FALSE))
			return StringUtils.substring(string, start.length());
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
	
	public String getI18nIdentifier(String name){
		return concatenateWordsFromCamelCase(name,  Constant.CHARACTER_DOT, CaseType.L);
	}
	
	public Collection<String> getFieldIdentifiers(java.lang.reflect.Field field,Collection<String> domains){
		Collection<String> identifiers = new LinkedHashSet<>();
		Collection<String> noDomainIdentifiers = new LinkedHashSet<>();
		//from field declaring class full name
		noDomainIdentifiers.add(FieldHelper.getInstance().buildPath(field.getDeclaringClass().getName(),field.getName()));
		//from field declaring class simple name
		noDomainIdentifiers.add(FieldHelper.getInstance().buildPath(field.getDeclaringClass().getSimpleName(),field.getName()));
		/*
		String domainPrefix = null;
		if(domain instanceof Class<?>)
			domainPrefix = ((Class<?>)domain).getName();
		else if(domain instanceof Package)
			domainPrefix = ((Package)domain).getName();
		else if(domain instanceof String)
			domainPrefix = domain.toString();
		*/
		if(CollectionHelper.getInstance().isNotEmpty(domains))
			for(String domain : domains)
				if(StringHelper.getInstance().isNotBlank(domain))
					for(String index : noDomainIdentifiers)
						identifiers.add(getFieldIdentifier(FieldHelper.getInstance().buildPath(domain,index)));
		for(String index : noDomainIdentifiers)
			identifiers.add(getFieldIdentifier(index));
		
		//identifiers.add(getClassIdentifier(field.getName()));
		
		//from annotations
		org.cyk.utility.common.annotation.user.interfaces.Text text = field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.Text.class);
		if(text == null || isBlank(text.value())){
			identifiers.add(getFieldIdentifier(field.getName()));
			identifiers.add(getClassIdentifier(field.getName()));//field first : we can use comparator to sort field first , class second , and others
			identifiers.add(getI18nIdentifier(field.getName()));
		}else {
			org.cyk.utility.common.annotation.user.interfaces.Text.ValueType valueType = text.valueType();
			if(org.cyk.utility.common.annotation.user.interfaces.Text.ValueType.AUTO.equals(valueType))
				valueType = org.cyk.utility.common.annotation.user.interfaces.Text.ValueType.ID;
			org.cyk.utility.common.annotation.user.interfaces.Text.Type type = text.type();
			if(org.cyk.utility.common.annotation.user.interfaces.Text.Type.AUTO.equals(type))
				type = org.cyk.utility.common.annotation.user.interfaces.Text.Type.LABEL;
			
			if(org.cyk.utility.common.annotation.user.interfaces.Text.ValueType.ID.equals(valueType)){
				identifiers.add(getFieldIdentifier(text.value()));
				identifiers.add(getClassIdentifier(field.getName()));//field first : we can use comparator to sort field first , class second , and others
				identifiers.add(text.value());
			}
		}	
		return identifiers;
	}
	
	public Collection<String> getFieldIdentifiers(java.lang.reflect.Field field){
		return getFieldIdentifiers(field, null);
	}
	
	public Collection<String> getFieldIdentifiers(Object domain,Class<?> aClass,String...fieldNames){
		String fieldName = FieldHelper.getInstance().buildPath(fieldNames);
		java.lang.reflect.Field field = FieldHelper.getInstance().get(aClass, fieldName);
		Collection<String> identifiers = new LinkedHashSet<>();
		Collection<String> domains = new ArrayList<String>();
		String domainName = null;
		if(domain instanceof Package)
			domainName = ((Package)domain).getName();
		else if(domain instanceof Class<?>)
			domainName = ((Class<?>)domain).getName();
		else if(domain instanceof String)
			domainName = (String)domain;
		
		if(aClass!=null){
			if(isNotBlank(domainName))
				domains.add(domainName+Constant.CHARACTER_DOT+aClass.getName());
			domains.add(aClass.getName());
		}
		
		CollectionHelper.getInstance().add(identifiers, Boolean.TRUE, getFieldIdentifiers(field,FieldHelper.getInstance().getLast(fieldName)
				.equals(fieldName) ? null : domains));
		return identifiers;
	}
	
	public Collection<String> getFieldIdentifiers(Class<?> aClass,String...fieldNames){
		return getFieldIdentifiers(null,aClass,fieldNames);
	}
	
	public String getFieldIdentifier(String name){
		name = String.format(ToStringMapping.FIELD_IDENTIFIER_FORMAT,getI18nIdentifier(name));
		return name;
	}

	public String getField(String identifier,CaseType caseType){
		String fieldIdentifier = getFieldIdentifier(identifier);
		String result = new ToStringMapping.Adapter.Default(fieldIdentifier).setCaseType(caseType).execute();
		String temp = result;
		if(ToStringMapping.Adapter.Default.isUnknown(result)){
			result = new ToStringMapping.Adapter.Default(getClassIdentifier(identifier)).setCaseType(caseType).execute();	
			if(ToStringMapping.Adapter.Default.isUnknown(result)){
				result = new ToStringMapping.Adapter.Default(getI18nIdentifier(identifier)).setCaseType(caseType).execute();
				if(ToStringMapping.Adapter.Default.isUnknown(result)){
					result = temp;
				}
			}
		}
		if(!Boolean.TRUE.equals(ToStringMapping.Adapter.Default.isUnknown(result))){
			Cache.Adapter.Default.add(fieldIdentifier, null, caseType, null, result);
		}
		return result;
	}
	
	public String getField(String identifier){
		return getField(identifier, CaseType.FU);
	}
	
	public String getFieldOLD(java.lang.reflect.Field field){
		//FieldHelper.Field fieldHelperField = FieldHelper.Field.get(field.getDeclaringClass(), field.getName());
		String identifier = null;//fieldHelperField.getNameIdentifier();
		/*if(isBlank(identifier)){
			
			org.cyk.utility.common.annotation.user.interfaces.Text text = field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.Text.class);
			/*
			if(text == null || isBlank(text.value()))
				identifier = field.getName();
			else {
				org.cyk.utility.common.annotation.user.interfaces.Text.ValueType valueType = text.valueType();
				if(org.cyk.utility.common.annotation.user.interfaces.Text.ValueType.AUTO.equals(valueType))
					valueType = org.cyk.utility.common.annotation.user.interfaces.Text.ValueType.ID;
				org.cyk.utility.common.annotation.user.interfaces.Text.Type type = text.type();
				if(org.cyk.utility.common.annotation.user.interfaces.Text.Type.AUTO.equals(type))
					type = org.cyk.utility.common.annotation.user.interfaces.Text.Type.LABEL;
				
				if(org.cyk.utility.common.annotation.user.interfaces.Text.ValueType.ID.equals(valueType))
					return get(text.value(), CaseType.FU,new Object[]{});
				else
					return text.value();
			}	
			
			for(String index : getFieldIdentifiers(field)){
				String result = getField(index);
				if(!ToStringMapping.Adapter.Default.isUnknown(result)){
					fieldHelperField.setNameIdentifier(index);
					return result;
				}
			}
		}else {
			
		}
		*/
		org.cyk.utility.common.annotation.user.interfaces.Text text = field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.Text.class);
		if(text == null || isBlank(text.value()))
			identifier = field.getName();
		else {
			org.cyk.utility.common.annotation.user.interfaces.Text.ValueType valueType = text.valueType();
			if(org.cyk.utility.common.annotation.user.interfaces.Text.ValueType.AUTO.equals(valueType))
				valueType = org.cyk.utility.common.annotation.user.interfaces.Text.ValueType.ID;
			org.cyk.utility.common.annotation.user.interfaces.Text.Type type = text.type();
			if(org.cyk.utility.common.annotation.user.interfaces.Text.Type.AUTO.equals(type))
				type = org.cyk.utility.common.annotation.user.interfaces.Text.Type.LABEL;
			
			if(org.cyk.utility.common.annotation.user.interfaces.Text.ValueType.ID.equals(valueType))
				return get(text.value(), CaseType.FU,new Object[]{});
			else
				return text.value();
		}	
		/*fieldHelperField.setNameIdentifier(identifier);
		System.out.println("StringHelper.getField() DANGEEERRR");
		*/
		return getField(identifier);
	}
	
	public String getField(java.lang.reflect.Field field,CaseType caseType){
		FieldHelper.Field fieldHelperField = FieldHelper.Field.get(field.getDeclaringClass(), field.getName());
		String identifier = fieldHelperField.getNameIdentifier();
		if(isBlank(identifier)){
			for(String index : getFieldIdentifiers(field)){
				String result = new ToStringMapping.Adapter.Default(index).setCaseType(caseType).execute();
				if(!ToStringMapping.Adapter.Default.isUnknown(result)){
					fieldHelperField.setNameIdentifier(index);
					Cache.Adapter.Default.add(index, null, caseType, null, result);
					return result;
				}
			}
			
			org.cyk.utility.common.annotation.user.interfaces.Text text = field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.Text.class);
			if(text != null && isNotBlank(text.value())){
				org.cyk.utility.common.annotation.user.interfaces.Text.ValueType valueType = text.valueType();
				if(org.cyk.utility.common.annotation.user.interfaces.Text.ValueType.AUTO.equals(valueType))
					valueType = org.cyk.utility.common.annotation.user.interfaces.Text.ValueType.ID;
				org.cyk.utility.common.annotation.user.interfaces.Text.Type type = text.type();
				if(org.cyk.utility.common.annotation.user.interfaces.Text.Type.AUTO.equals(type))
					type = org.cyk.utility.common.annotation.user.interfaces.Text.Type.LABEL;
				if(!org.cyk.utility.common.annotation.user.interfaces.Text.ValueType.ID.equals(valueType))
					return text.value();
			}	
		}else
			return new ToStringMapping.Adapter.Default(identifier).setCaseType(caseType).execute();
		
		
		
		return new ToStringMapping.Adapter.Default(getFieldIdentifier(field.getName())).setCaseType(caseType).execute();
	}
	
	public String getField(java.lang.reflect.Field field){
		return getField(field, CaseType.FU);
	}
	
	public String getClassIdentifier(String name){
		name = String.format(ToStringMapping.CLASS_IDENTIFIER_FORMAT,getI18nIdentifier(name));
		return name;
	}
	
	public String getClassIdentifier(Class<?> aClass){
		return getClassIdentifier(aClass.getSimpleName());
	}
	
	public String getClazz(String identifier,CaseType caseType){
		String classIdentifier = getClassIdentifier(identifier);
		String result = new ToStringMapping.Adapter.Default(classIdentifier).setCaseType(caseType).execute();
		String temp = result;
		if(ToStringMapping.Adapter.Default.isUnknown(result)){
			result = new ToStringMapping.Adapter.Default(getI18nIdentifier(identifier)).setCaseType(caseType).execute();
			if(ToStringMapping.Adapter.Default.isUnknown(result)){
				result = temp;
			}
		}
		if(!Boolean.TRUE.equals(ToStringMapping.Adapter.Default.isUnknown(result))){
			Cache.Adapter.Default.add(classIdentifier, null, caseType, null, result);
		}
		return result;
	}
	
	public String getClazz(String identifier){
		return getClazz(identifier, CaseType.FU);
	}
	
	public String getClazz(Class<?> aClass,CaseType caseType){
		return getClazz(aClass.getSimpleName(),caseType);
	}
	
	public String getClazz(Class<?> aClass){
		return getClazz(aClass,CaseType.FU);
	}
	
	public String getClassArticle(String identifier,Boolean any,Boolean plural){
		String classIdentifier = getClassIdentifier(identifier);
		Boolean isMasculine = isMasculine(classIdentifier);
		if(isMasculine == null){
			isMasculine = isMasculine(identifier);
		}
		return getWordArticle(isMasculine, any, plural);
	}
	
	public String getClassArticle(Class<?> aClass,Boolean any,Boolean plural){
		return getClassArticle(getI18nIdentifier(aClass.getSimpleName()), any, plural);
	}
	
	public String getVerbIdentifier(String identifier){
		identifier = String.format(ToStringMapping.VERB_FORMAT,getI18nIdentifier(identifier));
		return identifier;
	}
	
	public String getVerb(String identifier,CaseType caseType){
		String verbIdentifier = getVerbIdentifier(identifier);
		String result = new ToStringMapping.Adapter.Default(verbIdentifier).setCaseType(caseType).execute();
		String temp = result;
		if(ToStringMapping.Adapter.Default.isUnknown(result)){
			result = new ToStringMapping.Adapter.Default(getI18nIdentifier(identifier)).setCaseType(caseType).execute();
			if(ToStringMapping.Adapter.Default.isUnknown(result)){
				result = temp;
			}
		}
		if(!Boolean.TRUE.equals(ToStringMapping.Adapter.Default.isUnknown(result))){
			Cache.Adapter.Default.add(verbIdentifier, null, caseType, null, result);
		}
		return result;
	}
	
	public String getVerb(Constant.Action action){
		return getVerb(action.name(),CaseType.FU);
	}
	
	public String getNameIdentifier(String identifier){
		identifier = String.format(ToStringMapping.NAME_FORMAT,getI18nIdentifier(identifier));
		return identifier;
	}
	
	public String getName(Constant.Action action){
		return getName(action.name(),CaseType.FU);
	}
	
	public String getName(String identifier,CaseType caseType){
		String nameIdentifier = getNameIdentifier(identifier);
		String result = new ToStringMapping.Adapter.Default(nameIdentifier).setCaseType(caseType).execute();
		String temp = result;
		if(ToStringMapping.Adapter.Default.isUnknown(result)){
			result = new ToStringMapping.Adapter.Default(getI18nIdentifier(identifier)).setCaseType(caseType).execute();
			if(ToStringMapping.Adapter.Default.isUnknown(result)){
				result = temp;
			}
		}
		if(!Boolean.TRUE.equals(ToStringMapping.Adapter.Default.isUnknown(result))){
			Cache.Adapter.Default.add(nameIdentifier, null, caseType, null, result);
		}
		return result;
	}
	
	public String getResponse(Boolean response,CaseType caseType,Locale locale){
		return StringHelper.getInstance().get(response == null ? "response.undefined" : response ? "response.yes" : "response.no", caseType, null, locale);
	}
	
	public String getResponse(Boolean response,Locale locale){
		return getResponse(response, CaseType.L, locale);
	}
	
	public String getResponse(Boolean response){
		return getResponse(response, null);
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
	
	public String getPhrase(Constant.Action action,Boolean verb,Class<?> aClass){
		Collection<String> strings = new ArrayList<>();
		strings.add(Boolean.TRUE.equals(verb) ? getVerb(action) : getName(action));
		String clazz = getClazz(aClass);
		String article;
		if(Boolean.TRUE.equals(verb)){
			article = getClassArticle(aClass, Boolean.TRUE, Boolean.FALSE);
		}else {
			String ofIdentifier = null;
			if(Locale.FRENCH.equals(LocaleHelper.getInstance().get())){
				if(isVoyel(clazz.charAt(0)))
					ofIdentifier = "of.voyel";
			}
			if(ofIdentifier == null)
				ofIdentifier = "of";
			article = get(ofIdentifier, new Object[]{});
		}
		if(Locale.FRENCH.equals(LocaleHelper.getInstance().get()) && !Boolean.TRUE.equals(verb) && isVoyel(clazz.charAt(0))){
			strings.add(article+clazz);
		}else{
			strings.add(article);
			strings.add(clazz);	
		}
		return applyCaseType(concatenate(strings,Constant.CHARACTER_SPACE), CaseType.FURL);
	}
	
	public String getPhrase(Constant.Action action,Class<?> aClass){
		return getPhrase(action, Boolean.TRUE, aClass);
	}
	
	public Boolean isIdentified(String string){
		return !ToStringMapping.Adapter.Default.isUnknown(string);
	}
	
	public String getComparisonOperator(Boolean greater,Boolean equal,Boolean masculine,Boolean plural){
		if(greater == null){
			if(equal == null)
				;
			else
				return StringHelper.getInstance().getWord(equal ? "equal" : "different", masculine, plural);
		}else{
			String identifier = String.format(ToStringMapping.COMPARISON_OPERATOR_IDENTIFIER_FORMAT, Boolean.TRUE.equals(equal) ? ".or" : Constant.EMPTY_STRING );
			return new ToStringMapping.Adapter.Default(identifier)
					.addManyParameters(
							StringHelper.getInstance().getWord(Boolean.TRUE.equals(greater) ? "superior" : "inferior", masculine, plural)
					,StringHelper.getInstance().getWord("equal", masculine, plural)
					).execute();
		}
		return StringHelper.getInstance().getWord("equal", masculine, plural);
	}
	
	public Boolean isMasculine(String identifier){
		String value = new StringHelper.ToStringMapping.Adapter.Default(String.format(ToStringMapping.GENDER_FORMAT,identifier)).execute();
		return isIdentified(value) ? new BooleanHelper.Builder.String.Adapter.Default(value).execute() : null;
	}
	
	public String normalizeToVariableName(String string){
		return Introspector.decapitalize(string);
	}
	
	public String getVariableNameMany(String variableName){
		return String.format(VARIABLE_NAME_MANY_FORMAT, variableName);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T convert(String value,Class<T> type){
		Object convertedValue = null;
		if(String.class.equals(type))
			convertedValue = value;
		else if(BigDecimal.class.equals(type))
			convertedValue = new BigDecimal(value);
		else if(Long.class.equals(type))
			convertedValue = new Long(value);
		else if(Integer.class.equals(type))
			convertedValue = new Integer(value);
		else if(Byte.class.equals(type))
			convertedValue = new Byte(value);
		else if(Short.class.equals(type))
			convertedValue = new Short(value);
		else if(Double.class.equals(type))
			convertedValue = new Double(value);
		else if(double.class.equals(type))
			convertedValue = new Double(value);
		else if(Boolean.class.equals(type))
			convertedValue = new Boolean(value);
		else if(type.isEnum()){
			for(Object object : type.getEnumConstants()){
				if(object.toString().equals(value)){
					convertedValue = object;
					break;
				}
			}
		}
		else if(Date.class.equals(type))
			convertedValue = new TimeHelper.Builder.String.Adapter.Default(value).execute();
		else
			ThrowableHelper.getInstance().throwNotYetImplemented("convert string to "+type);
		return (T) convertedValue;
	}
	
	public String convert(Object object,Collection<String> fieldNames){
		Collection<String> strings = new ArrayList<>();
		if(object==null){
			
		}else{
			if(CollectionHelper.getInstance().isNotEmpty(fieldNames)){
				for(String index : fieldNames){
					Object value = FieldHelper.getInstance().read(object, index);
					if(value!=null)
						strings.add(index+Constant.CHARACTER_EQUAL+value);
				}
			}
		}
		return CollectionHelper.getInstance().concatenate(strings, Constant.CHARACTER_COMA);
	}
	
	public String convert(Object object,String...fieldNames){
		if(ArrayHelper.getInstance().isNotEmpty(fieldNames))
			return convert(object, Arrays.asList(fieldNames));
		return null;
	}
	
	public String convert(Object object){
		Collection<String> fieldNames = object == null ? null : FieldHelper.getInstance().getNamesWhereReferencedByStaticField(object.getClass());
		return convert(object, fieldNames);
	}
	
	public String replace(String string,String search,String replacement){
		return StringUtils.replace(string, search, replacement);
	}
	
	public String getFieldValue(Object object,Field field,Locale locale){
		return getListener().getFieldValue(object, field,locale);
	}
	
	public String getFieldValue(Object object,Field field){
		return getListener().getFieldValue(object, field);
	}
	
	public String getFieldValue(Object object,String fieldName,Locale locale){
		return getListener().getFieldValue(object, fieldName,locale);
	}
	
	public String getFieldValue(Object object,String fieldName){
		return getListener().getFieldValue(object, fieldName);
	}
	
	/**/
	
	private static Listener getListener(){
		return ClassHelper.getInstance().instanciateOne(Listener.class);
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
			
			Collection addTokenAt(String token,Integer index);
			Collection addTokens(java.util.List<String> tokens);
			Collection addTokens(String...tokens);
			Collection addActions(@SuppressWarnings("rawtypes") java.util.List<Action> actions);
			Collection addActions(@SuppressWarnings({ "rawtypes" }) Action...actions);
			Collection space();
			Collection and();
			Collection or();
			Collection equal();
			Collection leftParathensis();
			Collection rightParathensis();
			
			Collection applyCaseToLastToken(CaseType caseType);
			
			Collection setSequenceReplacementMap(Map<String,String> map);
			Map<String,String> getSequenceReplacementMap();
			Collection addSequenceReplacement(String key,String value);
			
			@Getter
			public static class Adapter extends Builder.Adapter implements Collection,Serializable {
				private static final long serialVersionUID = 1L;
				
				protected Map<String,String> sequenceReplacementMap;
				protected java.util.List<String> tokens;
				
				public Collection addSequenceReplacement(String key,String value){
					return null;
				}
				
				public Collection setSequenceReplacementMap(Map<String,String> map){
					return null;
				}
				
				public Collection addTokenAt(String token,Integer index){
					return null;
				}
				
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
				public Collection equal() {
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
						String string = CollectionHelper.getInstance().concatenate(tokens, getSeparator());
						Map<String,String> sequenceReplacementMap = getSequenceReplacementMap();
						if(sequenceReplacementMap!=null)
							for(Entry<String, String> entry : sequenceReplacementMap.entrySet())
								string = StringUtils.replaceEach(string, new String[]{entry.getKey()}, new String[]{entry.getValue()});
						return string;
					}
					
					public Collection addSequenceReplacement(String key,String value){
						if(sequenceReplacementMap==null)
							sequenceReplacementMap = new LinkedHashMap<>();
						sequenceReplacementMap.put(key, value);
						return this;
					}
					
					public Collection setSequenceReplacementMap(Map<String,String> map){
						this.sequenceReplacementMap = map;
						return this;
					}
					
					@Override
					public Builder setSeparator(String separator) {
						this.separator = separator;
						return this;
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
					public Collection addTokenAt(String token,Integer index) {
						if(token!=null && index!=null){
							if(this.tokens == null)
								this.tokens = new ArrayList<>();
							this.tokens.add(index, token);
						}
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
					public Collection equal() {
						return addTokens(EQUAL);
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
			String EQUAL = "=";
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
		String VERB_FORMAT = "%s.__verb__";
		String NAME_FORMAT = "%s.__name__";
		String WORD_IDENTIFIER_FORMAT = "word.%s.__%sine__";
		String FIELD_IDENTIFIER_PREFIX = "__field__.";
		String FIELD_IDENTIFIER_FORMAT = FIELD_IDENTIFIER_PREFIX+"%s";
		String CLASS_IDENTIFIER_PREFIX = "__class__.";
		String CLASS_IDENTIFIER_FORMAT = CLASS_IDENTIFIER_PREFIX+"%s";
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
		
		java.util.List<String> getIdentifiers();
		StringHelper.ToStringMapping setIdentifiers(java.util.List<String> identifiers);
		StringHelper.ToStringMapping addIdentifier(String identifier);
		
		@Getter @Setter
		public static class Adapter extends org.cyk.utility.common.Mapping.Adapter.Default<String,String> implements ToStringMapping,Serializable {
			private static final long serialVersionUID = 1L;

			protected CaseType caseType;
			protected Boolean cachable;
			protected java.util.List<String> identifiers;
			protected ListenerHelper.Executor.Function.Adapter.Default.String<Datasource> datasourcesExecutor;
			
			public Adapter(String input) {
				super(String.class, input, String.class);
				setName("build string cache identifier");
			}
			
			@Override
			public ToStringMapping addIdentifier(String identifier){
				return null;
			}
			
			@Override
			public ToStringMapping setIdentifiers(java.util.List<String> identifiers){
				return null;
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
				
				public static Boolean isUnknown(String result){
					return StringUtils.startsWith(result, UNKNOWN_MARKER_START) && StringUtils.endsWith(result, UNKNOWN_MARKER_END);
				}
				
				@Override
				public ToStringMapping addIdentifier(String identifier){
					if(identifiers==null)
						identifiers = new ArrayList<String>();
					identifiers.add(identifier);
					return this;
				}
				
				@Override
				public ToStringMapping setIdentifiers(java.util.List<String> identifiers){
					this.identifiers = identifiers;
					return this;
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
					List<String> identifiers = CollectionHelper.getInstance().createList(getIdentifiers());
					if(identifiers==null){
						identifiers = new ArrayList<String>();
						identifiers.add(getInput());
					}else {
						identifiers.add(0, getInput());
					}
					//String identifier = getInput();
					String firstResult = null,gender = null , all = null , result = null;
					for(String identifier : identifiers){
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
						result = __execute__(identifier, getCaseType(), locale, getCachable());
						if(firstResult==null)
							firstResult = result;
	
						if(Locale.FRENCH.equals(locale)){
							if(ArrayUtils.contains(new String[]{"le ","la "}, gender) && StringHelper.getInstance().isVoyel(result.charAt(0)))
								gender = "l'";
						}	
						
						if(isUnknown(result))
							;
						else
							break;
						
					}
					if(isUnknown(result))
						result = firstResult;
					return all+gender+result;
				}
				
				private String __execute__(final String pIdentifier,final CaseType pCaseType,final Locale pLocale,Boolean cachabled){
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
										java.lang.String lIdentifier = pIdentifier;
										if(getListener() instanceof Datasource.Cache)
											lIdentifier = new StringHelper.Builder.CacheIdentifier.Adapter.Default().setInput(pIdentifier)
													.setLocale(ToStringMapping.Adapter.Default.this.getLocale()).setParameters(ToStringMapping.Adapter.Default.this.getParameters())
													.addManyParameters(InstanceHelper.getInstance().getIfNotNullElseDefault(ToStringMapping.Adapter.Default.this.getCaseType(),CaseType.DEFAULT)).execute();
										return getListener().setInput(lIdentifier).setLocale(ToStringMapping.Adapter.Default.this.getLocale())
												.setParameters(ToStringMapping.Adapter.Default.this.getParameters())
												.setParent(ToStringMapping.Adapter.Default.this).execute();
									}
								}).setInput(ClassHelper.getInstance().instanciateMany(Datasource.class,Datasource.CLASSES));
						for(Datasource datasource : datasourcesExecutor.getInput()){
							datasource.setInput(pIdentifier);
						}
					}
					String value = datasourcesExecutor.execute();
					
					if(value==null){
						value = MAP.get(pIdentifier);
						if(value==null){
							for(Entry<String, ClassLoader> entry : RESOURCE_BUNDLE_MAP.entrySet()){
								try {
									ResourceBundle resourceBundle = ResourceBundle.getBundle(entry.getKey(), locale, entry.getValue());
									value = CollectionHelper.getInstance().isEmpty(parameters)?resourceBundle.getString(pIdentifier):MessageFormat.format(resourceBundle.getString(pIdentifier),parameters);
									
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
										String cacheIdentifier = new Builder.CacheIdentifier.Adapter.Default().setInput(pIdentifier).setLocale(locale).addParameters(parameters)
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
						value =formatUnknownIdentifier(pIdentifier);
					
					return value;
				}
			}
			
			public static String formatUnknownIdentifier(String identifier){
				return String.format(UNKNOWN_FORMAT, UNKNOWN_MARKER_START,identifier,UNKNOWN_MARKER_END);
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
						for(String index : new String[]{"core","word","phrase","ordinal","class","field","condition","userinterface"})
							StringHelper.ToStringMapping.Datasource.Adapter.Default.ResourceBundle.REPOSITORY.put("org.cyk.utility.common.i18n."+index
									, CommonUtils.class.getClassLoader());
				        
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
						
						public static void add(String identifier,Locale locale,CaseType caseType,java.util.Collection<Object> parameters,String value){
							String cacheIdentifier = new Builder.CacheIdentifier.Adapter.Default().setInput(identifier).setLocale(locale)
									.addParameters(parameters).addParameters(new Object[]{caseType}).execute();
							REPOSITORY.put(cacheIdentifier, value);
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
										String substituteValue = __execute__(substituteCode,CaseType.NONE,locale,cachable);
										if(substituteValue == null){
											value = ToStringMapping.Adapter.formatUnknownIdentifier(substituteCode);
											break;
										}else
											value = StringUtils.replace(value, SUBSTITUTE_TAG_START+substituteCode+SUBSTITUTE_TAG_END, substituteValue);										
									}
									
									if(StringUtils.startsWith(value,DO_NOT_PROCESS_TAG_START) && StringUtils.endsWith(value,DO_NOT_PROCESS_TAG_END)){
										value = StringUtils.substringBetween(value, DO_NOT_PROCESS_TAG_START, DO_NOT_PROCESS_TAG_END);
									}else{
										value = StringHelper.getInstance().applyCaseType(value, caseType);
									}
									
									if(Boolean.TRUE.equals(cachable)){
										Cache.Adapter.Default.add(identifier, locale, caseType, parameters, value);
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
	
	public static interface Mapping extends Action<String, String> {
		
		public static class Adapter extends Action.Adapter.Default<String, String> implements Mapping,Serializable {
			private static final long serialVersionUID = -4167553207734748200L;

			public Adapter(String input) {
				super("st mapringping", String.class, input, String.class);
			}
			
			public static class Default extends Mapping.Adapter implements Serializable {
				private static final long serialVersionUID = -4167553207734748200L;

				public Default(String input) {
					super(input);
				}
				
				public Default() {
					this(null);
				}
				
				@Override
				protected String __execute__() {
					return getInput();
				}
				
			}
		}
		
	}
	
	public static interface Concatenate extends Action<java.util.Collection<String>, String> {
		
		java.lang.String getSeparator();
		Concatenate setSeparator(java.lang.String separator);
		
		java.lang.Boolean getIsAddCountPrefix();
		Concatenate setIsAddCountPrefix(Boolean isAddCountPrefix);
		
		@Getter
		public static class Adapter extends Action.Adapter.Default<java.util.Collection<String>, String> implements Concatenate,Serializable {
			private static final long serialVersionUID = -4167553207734748200L;

			@SuppressWarnings("unchecked")
			public Adapter(java.util.Collection<String> strings) {
				super("concatenate", (Class<java.util.Collection<String>>) ClassHelper.getInstance().getByName(java.util.Collection.class), strings, String.class);
			}
			
			@Override
			public String getSeparator() {
				return null;
			}
			
			@Override
			public Concatenate setSeparator(String separator) {
				return null;
			}
			
			@Override
			public Boolean getIsAddCountPrefix() {
				return null;
			}
			
			@Override
			public Concatenate setIsAddCountPrefix(Boolean isAddCountPrefix) {
				return null;
			}
			
			public static class Default extends Concatenate.Adapter implements Serializable {
				private static final long serialVersionUID = -4167553207734748200L;

				public Default(java.util.Collection<String> strings) {
					super(strings);
				}
				
				public Default() {
					this(null);
				}
				
				@Override
				public String getSeparator() {
					return getPropertyAsString(PROPERTY_NAME_SEPARATOR);
				}
				
				@Override
				public Concatenate setSeparator(String separator) {
					return (Concatenate) setProperty(PROPERTY_NAME_SEPARATOR, separator);
				}
				
				@Override
				public Boolean getIsAddCountPrefix() {
					return getPropertyAsBoolean(PROPERTY_NAME_IS_ADD_COUNT_PREFIX);
				}
				
				@Override
				public Concatenate setIsAddCountPrefix(Boolean isAddCountPrefix) {
					return (Concatenate) setProperty(PROPERTY_NAME_IS_ADD_COUNT_PREFIX, isAddCountPrefix);
				}
				
				@Override
				protected String __execute__() {
					java.util.Collection<String> collection = new ArrayList<>();
					String separator = InstanceHelper.getInstance().getIfNotNullElseDefault(getSeparator(),Constant.CHARACTER_SPACE.toString());
					Boolean isAddCountPrefix = InstanceHelper.getInstance().getIfNotNullElseDefault(getIsAddCountPrefix(),Boolean.FALSE);
					Integer count = 0;
					for(String string : getInput()){
						StringBuilder stringBuilder = new StringBuilder();
						if(Boolean.TRUE.equals(isAddCountPrefix))
							stringBuilder.append(++count+Constant.CHARACTER_SPACE.toString());
						stringBuilder.append(string);
						collection.add(stringBuilder.toString());
					}
					return StringUtils.join(collection,separator);
				}
				
			}
		}
		
	}

	/**/
	
	public interface Transformer extends org.cyk.utility.common.helper.StringHelper.Builder.Collection {
		
		public static class Adapter extends org.cyk.utility.common.helper.StringHelper.Builder.Collection.Adapter.Default implements Transformer , Serializable {
			private static final long serialVersionUID = 1L;

			/**/
			
			public static class Default extends Transformer.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
			}	
		}
	}
	
	public static interface Listener {
		
		String getFieldValue(Object object,Field field,Locale locale);
		String getFieldValue(Object object,Field field);
		String getFieldValue(Object object,String fieldName,Locale locale);
		String getFieldValue(Object object,String fieldName);
		
		public static class Adapter extends AbstractBean implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
			
			public static class Default extends Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				@Override
				public String getFieldValue(Object object, Field field,Locale locale) {
					Object value = (object == null || field == null) ? null : FieldHelper.getInstance().read(object, field);
					if(value==null)
						value =  Constant.EMPTY_STRING;
					else if(ClassHelper.getInstance().isBoolean(field.getType()))
						value = getInstance().getResponse((Boolean) value,locale) ;
					else if(ClassHelper.getInstance().isDate(field.getType())){
						value = new TimeHelper.Stringifier.Date.Adapter.Default((Date)value).execute(); 
					}else
						value = value.toString();
					return value.toString();
				}
				
				@Override
				public String getFieldValue(Object object, Field field) {
					return getFieldValue(object, field, LocaleHelper.getInstance().get());
				}
				
				@Override
				public String getFieldValue(Object object, String fieldName,Locale locale) {
					return getFieldValue(object, object == null ? null :  FieldHelper.getInstance().get(object.getClass(), fieldName),locale);
				}
				
				@Override
				public String getFieldValue(Object object, String fieldName) {
					return getFieldValue(object, fieldName, LocaleHelper.getInstance().get());
				}
				
			}
			
			@Override
			public String getFieldValue(Object object, Field field) {
				return null;
			}
			
			@Override
			public String getFieldValue(Object object, String fieldName) {
				return null;
			}
			
			@Override
			public String getFieldValue(Object object, Field field, Locale locale) {
				return null;
			}
			
			@Override
			public String getFieldValue(Object object, String fieldName, Locale locale) {
				return null;
			}
		}
	}
	/*
	public static class FieldIdentifierComparator implements Comparator<String>,Serializable {

		@Override
		public int compare(String identifier1, String identifier2) {
			if(StringUtils.startsWith(identifier1, ToStringMapping.FIELD_IDENTIFIER_PREFIX))
				return 0;
			
				if(StringUtils.startsWith(identifier2, ToStringMapping.FIELD_IDENTIFIER_PREFIX))
					return 0;
				else if(StringUtils.startsWith(identifier2, ToStringMapping.CLASS_IDENTIFIER_PREFIX))
					return -1;
			
			return 0;
		}
	}*/
}
