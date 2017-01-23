package org.cyk.utility.common;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

public interface Constant {
	
	Character CHARACTER_SPACE = new Character(' ');
	Character CHARACTER_DOT = new Character('.');
	Character CHARACTER_COLON = new Character(':');
	Character CHARACTER_COMA = new Character(',');
	Character CHARACTER_SEMI_COLON = new Character(';');
	Character CHARACTER_CARET_CIRCUMFLEX = new Character('^');
	Character CHARACTER_BACK_SLASH = new Character('\\');
	
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
	
	String JAVA_STRING_FORMAT_MARKER_S = "%s";
	
	String JNDI_NAMESPACE_GLOBAL = WORD_JAVA+CHARACTER_COLON+WORD_GLOBAL;
	String JNDI_NAMESPACE_APP = WORD_JAVA+CHARACTER_COLON+WORD_APP;
	
	String PREFIX_PACKAGE_JAVAX = WORD_JAVAX+CHARACTER_DOT;	
	String PREFIX_PACKAGE_BEAN_VALIDATION = PREFIX_PACKAGE_JAVAX+WORD_VALIDATION;
	String PREFIX_PACKAGE_ORG_CYK = "org.cyk.";	
	String PREFIX_PACKAGE_ORG_CYK_SYSTEM = PREFIX_PACKAGE_ORG_CYK+"system.";		
	
	String EMPTY_STRING = "";
	
	String VARIABLE_RESULT = "result";
	
	String ENCODING_UTF8 = "UTF-8";
	
	String LINE_DELIMITER = "\r\n";
	
	String DATE_PATTERN = "dd/MM/yyyy";
	String TIME_PATTERN = "HH:mm";
	String DATE_TIME_PATTERN = DATE_PATTERN+CHARACTER_SPACE+TIME_PATTERN;
	SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat(DATE_PATTERN);
	SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(TIME_PATTERN);
	SimpleDateFormat DATE_TIME_FORMATTER = new SimpleDateFormat(DATE_TIME_PATTERN);
	
	Date DATE_LOWEST_VALUE = new DateTime(0, 1, 1, 0, 0, 0, 0).toDate();
	Date DATE_HIGHEST_VALUE = new DateTime(9999, 12, 31, 23, 59, 59, 999).toDate();
	
	BigDecimal NUMBER_LOWEST_NEGATIVE=new BigDecimal("-1"+StringUtils.repeat('0', 18));
	BigDecimal NUMBER_HIGHEST_NEGATIVE_LOWER_THAN_ZERO=new BigDecimal("-0."+StringUtils.repeat('0', 18)+"1");
	
	BigDecimal NUMBER_LOWEST_POSITIVE_GREATER_THAN_ZERO=new BigDecimal("0."+StringUtils.repeat('0', 18)+"1");
	BigDecimal NUMBER_HIGHEST_POSITIVE=new BigDecimal("1"+StringUtils.repeat('0', 18));
	
	BigDecimal BIGDECIMAL_100 = new BigDecimal("100");
	
}
