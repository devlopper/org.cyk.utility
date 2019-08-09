package org.cyk.utility.__kernel__.constant;

public interface ConstantString {

	String CYK = "CYK";
	
	String WORD_JAVA = "java";
	String WORD_JAVAX = WORD_JAVA+"x";
	String WORD_GLOBAL = "global";
	String WORD_APP = "app";
	String WORD_VALIDATION = "validation";
	String WORD_MAIL = "mail";
	String WORD_NAME = "name";
	String WORD_GET = "get";
	
	String JAVA_STRING_FORMAT_MARKER_S = "%s";
	
	String JNDI_NAMESPACE_GLOBAL = WORD_JAVA+ConstantCharacter.COLON+WORD_GLOBAL;
	String JNDI_NAMESPACE_APP = WORD_JAVA+ConstantCharacter.COLON+WORD_APP;
	
	String PREFIX_PACKAGE_JAVAX = WORD_JAVAX+ConstantCharacter.DOT;	
	String PREFIX_PACKAGE_BEAN_VALIDATION = PREFIX_PACKAGE_JAVAX+WORD_VALIDATION;
	String PREFIX_PACKAGE_ORG_CYK = "org.cyk.";	
	String PREFIX_PACKAGE_ORG_CYK_SYSTEM = PREFIX_PACKAGE_ORG_CYK+"system.";		
	
	String FIELD_ = "FIELD_";	

	String VARIABLE_RESULT = "result";
	
	String ENCODING_UTF8 = "UTF-8";
		
	String IMPL = "Impl";
	
	String SYSTEM = "system";
	String INFINITE = "infini";
	String MANY = "many";
	String COLLECTION = "collection";
	String ALL = "all";
	String CREATE = "create";
	String UPDATE = "update";
	String DELETE = "delete";
	String GET = "get";
	String COUNT = "count";
	String SAVE = "save";
	String IDENTIFIER = "identifier";
	String IDENTIFIERS = "identifiers";
	String TYPE = "type";
	String FILTER = "filter";
	String FILTERS = "filters";
	String FIELDS = "fields";
	String CODE = "code";
	String FROM = "from";
	String FILE = "file";
	String EXCEL = "excel";
	String SHEET = "sheet";
	String UPLOAD = "upload";
	String DOWNLOAD = "download";
	String DIRECTORY = "directory";
	String EXTENSION = "extension";
	String INTERVAL = "interval";
	String BATCH = "batch";
	String SIZE = "size";
	String WORKBOOK = "workbook";
	String NAME = "name";
	String COLUMN = "column";
	String INDEX = "index";
	String FIELD = "field";
	String BATCH_SIZE = BATCH+SIZE;
	String DIRECTORIES = "directories";
	String FILENAME = "filename";
	String IS = "is";
	String INLINE = "inline";
	String ATTACHMENT = "attachment";
	String IS_INLINE = IS+INLINE;
	String PAGEABLE = "pageable";
	String BATCHABLE = "batchable";
	String IS_PAGEABLE = IS+PAGEABLE;
	String IS_BATCHABLE = IS+BATCHABLE;
	String MESSAGE = "message";
	String WORKBOOK_NAME = WORKBOOK+NAME;
	String SHEET_NAME = SHEET+NAME;
	String COLUMN_INDEX_FIELD_NAME =COLUMN+INDEX+FIELD+NAME;
	String PROPERTIES = "properties";

}
