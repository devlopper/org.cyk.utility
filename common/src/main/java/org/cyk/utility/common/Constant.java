package org.cyk.utility.common;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.helper.ArrayHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.joda.time.DateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public interface Constant {
	
	String CYK = "CYK";
	
	Character CHARACTER_SPACE = new Character(' ');
	Character CHARACTER_DOT = new Character('.');
	Character CHARACTER_COLON = new Character(':');
	Character CHARACTER_COMA = new Character(',');
	Character CHARACTER_SEMI_COLON = new Character(';');
	Character CHARACTER_CARET_CIRCUMFLEX = new Character('^');
	Character CHARACTER_BACK_SLASH = new Character('\\');
	Character CHARACTER_PERCENTAGE = new Character('%');
	
	/**
	 * \\/
	 */
	Character CHARACTER_ZERO = new Character('0');
	Character CHARACTER_SLASH = new Character('/');
	Character CHARACTER_DOLLAR = new Character('$');
	Character CHARACTER_QUESTION_MARK = new Character('?');
	Character CHARACTER_AMPERSTAMP = new Character('&');
	Character CHARACTER_EQUAL = new Character('=');
	Character CHARACTER_AT = new Character('@');
	Character CHARACTER_UNDESCORE = new Character('_');
	Character CHARACTER_VERTICAL_BAR = new Character('|');
	Character CHARACTER_LEFT_PARENTHESIS = new Character('(');
	Character CHARACTER_RIGHT_PARENTHESIS = new Character(')');
	Character CHARACTER_LEFT_BRACKET = new Character('{');
	Character CHARACTER_RIGHT_BRACKET = new Character('}');
	Character CHARACTER_STAR = new Character('*');
	Character CHARACTER_PLUS = new Character('+');
	Character CHARACTER_PERCENT = new Character('%');
	Character CHARACTER_HYPHEN = new Character('-');
	Character CHARACTER_MINUS = new Character('-');
	Character CHARACTER_GREATER_THAN = new Character('>');
	Character CHARACTER_LOWER_THAN = new Character('<');
	Character CHARACTER_SHARP = new Character('#');
	
	Character CHARACTER_H = new Character('H');
	
	String WORD_JAVA = "java";
	String WORD_JAVAX = WORD_JAVA+"x";
	String WORD_GLOBAL = "global";
	String WORD_APP = "app";
	String WORD_VALIDATION = "validation";
	String WORD_MAIL = "mail";
	String WORD_NAME = "name";
	
	String JAVA_STRING_FORMAT_MARKER_S = "%s";
	
	String JNDI_NAMESPACE_GLOBAL = WORD_JAVA+CHARACTER_COLON+WORD_GLOBAL;
	String JNDI_NAMESPACE_APP = WORD_JAVA+CHARACTER_COLON+WORD_APP;
	
	String PREFIX_PACKAGE_JAVAX = WORD_JAVAX+CHARACTER_DOT;	
	String PREFIX_PACKAGE_BEAN_VALIDATION = PREFIX_PACKAGE_JAVAX+WORD_VALIDATION;
	String PREFIX_PACKAGE_ORG_CYK = "org.cyk.";	
	String PREFIX_PACKAGE_ORG_CYK_SYSTEM = PREFIX_PACKAGE_ORG_CYK+"system.";		
	
	String FIELD_ = "FIELD_";	
	
	String EMPTY_STRING = "";
	String[] EMPTY_STRING_ARRAY = new String[]{};
	
	Collection<Long> COLLECTION_ONE_LONG_MIN_VALUE = Arrays.asList(Long.MIN_VALUE);
	
	String VARIABLE_RESULT = "result";
	
	String ENCODING_UTF8 = "UTF-8";
	
	String LINE_DELIMITER = "\r\n";
		
	java.lang.String HOUR_MINUTE_PATTERN_FORMAT = "%s:%s";
	java.lang.String HOUR_MINUTE_SECOND_PATTERN_FORMAT = HOUR_MINUTE_PATTERN_FORMAT+":%s";
	java.lang.String HOUR_MINUTE_SECOND_MILLISECOND_PATTERN_FORMAT = HOUR_MINUTE_SECOND_PATTERN_FORMAT+":%s";
	java.lang.String DAY_MONTH_YEAR_PATTERN_FORMAT = "%s/%s/%s";
	java.lang.String DATE_TIME_PATTERN_FORMAT = "%s"+CHARACTER_SPACE+"%s";
	
	java.lang.String DAY_MONTH_YEAR_PATTERN = String.format(DAY_MONTH_YEAR_PATTERN_FORMAT, "dd","MM","yyyy");
	java.lang.String HOUR_MINUTE_PATTERN = String.format(HOUR_MINUTE_PATTERN_FORMAT, "HH","mm");
	java.lang.String HOUR_MINUTE_SECOND_PATTERN = String.format(HOUR_MINUTE_SECOND_PATTERN_FORMAT, "HH","mm","ss");
	java.lang.String HOUR_MINUTE_SECOND_MILLISECOND_PATTERN = String.format(HOUR_MINUTE_SECOND_MILLISECOND_PATTERN_FORMAT, "HH","mm","ss","SSS");
	java.lang.String DAY_MONTH_YEAR_HOUR_MINUTE_PATTERN = String.format(DATE_TIME_PATTERN_FORMAT, DAY_MONTH_YEAR_PATTERN,HOUR_MINUTE_PATTERN);
	java.lang.String DAY_MONTH_YEAR_HOUR_MINUTE_SECOND_PATTERN = String.format(DATE_TIME_PATTERN_FORMAT, DAY_MONTH_YEAR_PATTERN,HOUR_MINUTE_SECOND_PATTERN);
	java.lang.String DAY_MONTH_YEAR_HOUR_MINUTE_SECOND_MILLISECOND_PATTERN = String.format(DATE_TIME_PATTERN_FORMAT, DAY_MONTH_YEAR_PATTERN,HOUR_MINUTE_SECOND_MILLISECOND_PATTERN);
	
	@Deprecated String DATE_PATTERN = "dd/MM/yyyy";
	@Deprecated String TIME_PATTERN = "HH:mm";
	@Deprecated String DATE_TIME_PATTERN = DATE_PATTERN+CHARACTER_SPACE+TIME_PATTERN;
	
	@Deprecated SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat(DATE_PATTERN);
	@Deprecated SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(TIME_PATTERN);
	@Deprecated SimpleDateFormat DATE_TIME_FORMATTER = new SimpleDateFormat(DATE_TIME_PATTERN);
	
	java.util.Date DATE_LOWEST_VALUE = new DateTime(0, 1, 1, 0, 0, 0, 0).toDate();
	java.util.Date DATE_HIGHEST_VALUE = new DateTime(9999, 12, 31, 23, 59, 59, 999).toDate();
	
	BigDecimal NUMBER_LOWEST_NEGATIVE=new BigDecimal("-"+StringUtils.repeat('9', 18));
	BigDecimal NUMBER_HIGHEST_NEGATIVE_LOWER_THAN_ZERO=new BigDecimal("-0."+StringUtils.repeat('0', 18)+"1");
	
	BigDecimal NUMBER_LOWEST_POSITIVE_GREATER_THAN_ZERO=NUMBER_HIGHEST_NEGATIVE_LOWER_THAN_ZERO.negate();
	BigDecimal NUMBER_HIGHEST_POSITIVE=NUMBER_LOWEST_NEGATIVE.negate();
	
	BigDecimal BIGDECIMAL_100 = new BigDecimal("100");
	
	java.lang.Void VOID = null;
	
	/**/
	
	@Getter
	public static enum Date {
		
		/**/
		
		;
		
		private static Map<Locale, Collection<Pattern>> PATTERN_MAP = new HashMap<>();
		static{
			putPatterns(Locale.FRENCH, String.format(DAY_MONTH_YEAR_PATTERN_FORMAT, "dd","MM","yyyy"),"EEEE , dd MMMM yyyy", HOUR_MINUTE_PATTERN,HOUR_MINUTE_PATTERN,HOUR_MINUTE_SECOND_PATTERN,HOUR_MINUTE_SECOND_MILLISECOND_PATTERN,"yyyy");
			putPatterns(Locale.ENGLISH, String.format(DAY_MONTH_YEAR_PATTERN_FORMAT, "dd","MM","yyyy"),"EEEE , dd MMMM yyyy", HOUR_MINUTE_PATTERN,HOUR_MINUTE_PATTERN,HOUR_MINUTE_SECOND_PATTERN,HOUR_MINUTE_SECOND_MILLISECOND_PATTERN,"yyyy");
		}
		
		public static Pattern getPattern(Locale locale,Part part,Length length){
			if(locale == null)
				locale = Locale.FRENCH;
			Collection<Pattern> patterns = PATTERN_MAP.get(locale);
			if(patterns==null)
				patterns = PATTERN_MAP.get(Locale.FRENCH);
			for(Pattern pattern : patterns)
				if(pattern.getPart().equals(part) && pattern.getLength().equals(length))
					return pattern;
			return Pattern.DEFAULT;
		}
		
		private static void putPatterns(Locale locale,String shortDateOnlyPattern,String longDateOnlyPattern,String shortTimeOnlyPattern,String shortTimeOnlyHourMinutePattern,String shortTimeOnlyHourMinuteSecondPattern,String shortTimeOnlyHourMinuteSecondMillisecondPattern,String dateYearOnlyPattern){
			String longTimeOnlyPattern = shortTimeOnlyPattern;
			Collection<Pattern> patterns = new ArrayList<>();
			patterns.add(new Pattern(locale, Constant.Date.Part.DATE_ONLY, Constant.Date.Length.SHORT, shortDateOnlyPattern));
			patterns.add(new Pattern(locale, Constant.Date.Part.DATE_ONLY, Constant.Date.Length.LONG, longDateOnlyPattern));
			patterns.add(new Pattern(locale, Constant.Date.Part.TIME_ONLY, Constant.Date.Length.SHORT, shortTimeOnlyPattern));
			patterns.add(new Pattern(locale, Constant.Date.Part.TIME_ONLY, Constant.Date.Length.LONG, longTimeOnlyPattern));
			patterns.add(new Pattern(locale, Constant.Date.Part.DATE_AND_TIME, Constant.Date.Length.SHORT, shortDateOnlyPattern+Constant.CHARACTER_SPACE+shortTimeOnlyPattern));
			patterns.add(new Pattern(locale, Constant.Date.Part.DATE_AND_TIME, Constant.Date.Length.LONG, longDateOnlyPattern+Constant.CHARACTER_SPACE+longTimeOnlyPattern));
			
			patterns.add(new Pattern(locale, Constant.Date.Part.DATE_AND_TIME_YEAR_MONTH_DAY_HOUR_MINUTE, Constant.Date.Length.SHORT, shortDateOnlyPattern+Constant.CHARACTER_SPACE+shortTimeOnlyHourMinutePattern));
			patterns.add(new Pattern(locale, Constant.Date.Part.DATE_AND_TIME_YEAR_MONTH_DAY_HOUR_MINUTE, Constant.Date.Length.LONG, longDateOnlyPattern+Constant.CHARACTER_SPACE+shortTimeOnlyHourMinutePattern));
			
			patterns.add(new Pattern(locale, Constant.Date.Part.DATE_AND_TIME_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, Constant.Date.Length.SHORT, shortDateOnlyPattern+Constant.CHARACTER_SPACE+shortTimeOnlyHourMinuteSecondPattern));
			patterns.add(new Pattern(locale, Constant.Date.Part.DATE_AND_TIME_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, Constant.Date.Length.LONG, longDateOnlyPattern+Constant.CHARACTER_SPACE+shortTimeOnlyHourMinuteSecondPattern));
			
			patterns.add(new Pattern(locale, Constant.Date.Part.DATE_AND_TIME_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_MILLISECOND, Constant.Date.Length.SHORT, shortDateOnlyPattern+Constant.CHARACTER_SPACE+shortTimeOnlyHourMinuteSecondMillisecondPattern));
			patterns.add(new Pattern(locale, Constant.Date.Part.DATE_AND_TIME_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_MILLISECOND, Constant.Date.Length.LONG, longDateOnlyPattern+Constant.CHARACTER_SPACE+shortTimeOnlyHourMinuteSecondMillisecondPattern));
			
			patterns.add(new Pattern(locale, Constant.Date.Part.DATE_YEAR_ONLY, Constant.Date.Length.SHORT, dateYearOnlyPattern));
			patterns.add(new Pattern(locale, Constant.Date.Part.DATE_YEAR_ONLY, Constant.Date.Length.LONG, dateYearOnlyPattern));
			PATTERN_MAP.put(locale, patterns);
		}
		
		/**/
		
		@Getter @Setter @AllArgsConstructor
		public static class Pattern implements Serializable {
			private static final long serialVersionUID = 1L;
			
			private static final Pattern DEFAULT = new Pattern(null, Constant.Date.Part.DATE_ONLY, Constant.Date.Length.SHORT, "dd/MM/yyyy");
			
			private Locale locale;
			private Part part;
			private Length length;
			private String value;
			
		}
		
		@Getter
		public static enum Part {
			DATE_ONLY
			,TIME_ONLY
			,DATE_AND_TIME
			,DATE_AND_TIME_YEAR_MONTH_DAY_HOUR_MINUTE
			,DATE_AND_TIME_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND
			,DATE_AND_TIME_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_MILLISECOND
			,DATE_YEAR_ONLY
			;			
			
		}
		
		@Getter
		public static enum Length {
			SHORT
			,LONG
			;			
			
		}
		
	}
	
	/**/
	
	public static enum Action {
	    LOGIN
	    ,LOGOUT
		,CREATE
	    ,READ
	    ,UPDATE
	    ,DELETE
	    ,SELECT
	    ,SEARCH
	    ,CONSULT
	    ,LIST
	    ,PRINT
	    
	    ;
		
		public static Boolean isCreateOrReadOrUpdateOrDeleteOrConsult(Object action){
			return isCreateOrReadOrUpdateOrDelete(action) || Action.CONSULT.equals(action);
		}
		
		public static Boolean isCreateOrReadOrUpdateOrDelete(Object action){
			return isCreateOrUpdateOrDelete(action) || Action.READ.equals(action);
		}
		
		public static Boolean isCreateOrUpdateOrDelete(Object action){
			return isCreateOrUpdate(action) || Action.DELETE.equals(action);
		}
		
		public static Boolean isCreateOrUpdate(Object action){
			return Action.CREATE.equals(action) || Action.UPDATE.equals(action);
		}
		
		public static Boolean isReadOrConsult(Object action){
			return isOneOf(action, Action.READ, Action.CONSULT);
		}
		
		public static Boolean isNotReadAndNotConsult(Object action){
			return isNotOneOf(action, Action.READ, Action.CONSULT);
		}
		
		public static Boolean isNotOneOf(Object action,Collection<Action> actions){
			return !Boolean.TRUE.equals(isOneOf(action, actions));
		}
		
		public static Boolean isNotOneOf(Object action,Action...actions){
			return !Boolean.TRUE.equals(isOneOf(action, actions));
		}
		
		public static Boolean isOneOf(Object action,Action...actions){
			return ArrayHelper.getInstance().isNotEmpty(actions) && ArrayHelper.getInstance().isNotEmpty(actions) && isOneOf(action, Arrays.asList(actions));
		}
		
		public static Boolean isOneOf(Object action,Collection<Action> actions){
			return CollectionHelper.getInstance().isNotEmpty(actions) && actions.contains(action);
		}
		
		/**/
		
		public static final Action[] SET_CRUD = {CREATE,READ,UPDATE,DELETE};
		public static final Action[] SET_CRUDC = {CREATE,READ,UPDATE,DELETE,CONSULT};
	}

	
	/**/
	
	public static class SimpleMailTransferProtocol implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public static final String ADDRESS_FORMAT = "%s@%s.%s";
		public static final String SMTP = "smtp";
		public static final String PROPERTY_FORMAT = "mail."+SMTP+"%s.%s";
		
		public static String getProperty(String name,Boolean secured){
			return String.format(PROPERTY_FORMAT, Boolean.TRUE.equals(secured) ? "s" : Constant.EMPTY_STRING,name);
		}
		public static String getProperty(String name){
			return getProperty(name, Boolean.FALSE);
		}
		
		public static final String HOST = "host";
		public static final String FROM = "from";
		public static final String USER = "user";
		public static final String PASSWORD = "password";
		public static final String PORT = "port";
		public static final String AUTH = "auth";
		public static final String STARTTLS_ENABLE = "starttls.enable";
		public static final String SSL_ENABLE = "ssl.enable";
		
		public static final String PROPERTY_USERNAME = getProperty(USER);
		public static final String PROPERTY_PASSWORD = getProperty(PASSWORD);
		public static final String PROPERTY_FROM = getProperty(FROM);
		
		
	}

	/**/
	
	public static enum ApplicationType {
		STAND_ALONE
		,CLIENT_SERVER
		;
		
		public static ApplicationType DEFAULT = ApplicationType.CLIENT_SERVER;
	
	}

	/**/
	
	public static class Code implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public static String SEPARATOR = Constant.EMPTY_STRING;
		
		public static String generateFromString(String string){
			return StringUtils.remove(string, Constant.CHARACTER_SPACE);
		}
		
		public static String generate(Object[] objects,Object separator){
			Collection<String> collection = new ArrayList<>();
			for(Object object : objects)
				if(object instanceof Class<?>)
					collection.add(((Class<?>)object).getSimpleName().toUpperCase());
				else
					collection.add(object.toString());
			return StringUtils.join(collection,SEPARATOR);
		}
		
		public static String generate(Object[] objects){
			return generate(objects, SEPARATOR);
		}
		
		public static String generate(String masterCode,String detailCode,Object separator){
			return generate(new Object[]{masterCode,detailCode}, separator);
		}
		
		/*public static String getRelativeCode(AbstractCollection<?> collection,String code){
			return StringUtils.isBlank(collection.getItemCodeSeparator()) ? code : StringUtils.split(code,collection.getItemCodeSeparator())[1];
		}
		
		public static String getRelativeCode(AbstractCollectionItem<?> item){
			return getRelativeCode((AbstractCollection<?>) item.getCollection(), item.getCode());
		}*/
		
		public static String generateFieldNotNull(Class<?> aClass,String...fieldNames){
			return generate(new Object[]{aClass,FieldHelper.getInstance().buildPath(fieldNames),NotNull.class});
		}
		
	}
}
