package org.cyk.utility.string;

import org.cyk.utility.character.CharacterConstant;

public interface StringConstant {

	String EMPTY = "";
	
	String CYK = "CYK";
	
	String WORD_JAVA = "java";
	String WORD_JAVAX = WORD_JAVA+"x";
	String WORD_GLOBAL = "global";
	String WORD_APP = "app";
	String WORD_VALIDATION = "validation";
	String WORD_MAIL = "mail";
	String WORD_NAME = "name";
	
	String JAVA_STRING_FORMAT_MARKER_S = "%s";
	
	String JNDI_NAMESPACE_GLOBAL = WORD_JAVA+CharacterConstant.COLON+WORD_GLOBAL;
	String JNDI_NAMESPACE_APP = WORD_JAVA+CharacterConstant.COLON+WORD_APP;
	
	String PREFIX_PACKAGE_JAVAX = WORD_JAVAX+CharacterConstant.DOT;	
	String PREFIX_PACKAGE_BEAN_VALIDATION = PREFIX_PACKAGE_JAVAX+WORD_VALIDATION;
	String PREFIX_PACKAGE_ORG_CYK = "org.cyk.";	
	String PREFIX_PACKAGE_ORG_CYK_SYSTEM = PREFIX_PACKAGE_ORG_CYK+"system.";		
	
	String FIELD_ = "FIELD_";	

	String VARIABLE_RESULT = "result";
	
	String ENCODING_UTF8 = "UTF-8";
	
	String LINE_DELIMITER = "\r\n";
		
	String IMPL = "Impl";

}
