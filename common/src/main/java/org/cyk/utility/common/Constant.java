package org.cyk.utility.common;

public interface Constant {
	
	Character CHARACTER_SPACE = new Character(' ');
	Character CHARACTER_DOT = new Character('.');
	Character CHARACTER_COLON = new Character(':');
	Character CHARACTER_SEMI_COLON = new Character(';');
	Character CHARACTER_BACK_SLASH = new Character('\\');
	Character CHARACTER_SLASH = new Character('/');
	
	Character CHARACTER_H = new Character('H');
	
	String WORD_JAVA = "java";
	String WORD_JAVAX = WORD_JAVA+"x";
	String WORD_GLOBAL = "global";
	String WORD_APP = "app";
	String WORD_VALIDATION = "validation";
	String WORD_MAIL = "mail";
	
	String JNDI_NAMESPACE_GLOBAL = WORD_JAVA+CHARACTER_COLON+WORD_GLOBAL;
	String JNDI_NAMESPACE_APP = WORD_JAVA+CHARACTER_COLON+WORD_APP;
	
	String PREFIX_PACKAGE_JAVAX = WORD_JAVAX+CHARACTER_DOT;	
	String PREFIX_PACKAGE_BEAN_VALIDATION = PREFIX_PACKAGE_JAVAX+WORD_VALIDATION;
	
	String EMPTY_STRING = "";
}
