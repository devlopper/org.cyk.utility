package org.cyk.utility.service;

import org.cyk.utility.__kernel__.constant.ConstantString;

/*
 * All method must be annotated with HTTP method and return a Response object
 */
/**
 * Base interface to expose service using REST
 * @author CYK
 *
 */
public interface Service {

	String __SLASH__ = "/";
	String __MANY__ = ConstantString.MANY;
	String __IDENTIFIERS__ = ConstantString.IDENTIFIERS;
	String __FILE__ = ConstantString.FILE;
	String __FROM__ = ConstantString.FROM;
	String __EXCEL__ = ConstantString.EXCEL;
	String __SHEET__ = ConstantString.SHEET;
	String __COLLECTION__ = ConstantString.COLLECTION;
	String __ALL__ = ConstantString.ALL;
	String __CREATE__ = ConstantString.CREATE;
	String __UPDATE__ = ConstantString.UPDATE;
	String __DELETE__ = ConstantString.DELETE;
	String __GET__ = ConstantString.GET;
	String __SAVE__ = ConstantString.SAVE;
	String __COUNT__ = ConstantString.COUNT;
	
	/* Parameter */
	
	String PARAMETER_IDENTIFIER = "identifier";
	String DESCRIPTION_IDENTIFIER = "Identifier";
	String EXAMPLE_IDENTIFIER = "1";
	
	String PARAMETER_IDENTIFIERS = "identifiers";
	String DESCRIPTION_IDENTIFIERS = "Identifiers";
	String EXAMPLE_IDENTIFIERS = "1,2,3";
	
	String PARAMETER_PARAMETERS_NAMES = "parametersNames";
	String PARAMETER_PARAMETERS_NAMES_VALUES_AS_JSON = "parametersNamesValuesAsJson";
	String PARAMETER_FILE_TYPE = "fileType";
	String PARAMETER_TYPE = ConstantString.TYPE;
	String PARAMETER_FILTER = ConstantString.FILTER;
	String PARAMETER_FILTERS = ConstantString.FILTERS;
	String PARAMETER_FIELDS = ConstantString.FIELDS;
	String PARAMETER_FROM = ConstantString.FROM;
	String PARAMETER_IS_BATCHABLE = ConstantString.IS_BATCHABLE;
	String PARAMETER_COUNT = ConstantString.COUNT;
	String PARAMETER_CODE = ConstantString.CODE;
	String PARAMETER_DIRECTORY = ConstantString.DIRECTORY;
	String PARAMETER_EXTENSION = ConstantString.EXTENSION;
	String PARAMETER_BATCH = ConstantString.BATCH;
	String PARAMETER_SIZE = ConstantString.SIZE;
	String PARAMETER_BATCH_SIZE = ConstantString.BATCH_SIZE;
	String PARAMETER_DIRECTORIES = ConstantString.DIRECTORIES;
	String PARAMETER_IS_INLINE = ConstantString.IS_INLINE;
	String PARAMETER_IS_PAGEABLE = ConstantString.IS_PAGEABLE;
	String PARAMETER_QUERY_IDENTIFIER = "queryIdentifier";
	String PARAMETER_WORKBOOK_NAME = ConstantString.WORKBOOK_NAME;
	String PARAMETER_SHEET_NAME = ConstantString.SHEET_NAME;
	String PARAMETER_COLUMN_INDEX_FIELD_NAME = ConstantString.COLUMN_INDEX_FIELD_NAME;
	String PARAMETER_UNIFORM_RESOURCE_IDENTIFIER = "uniformResourceIdentifier";
	String PARAMETER_PROPERTIES = ConstantString.PROPERTIES;
	String PARAMETER_PAGE_INDEX = "pageIndex";
	String PARAMETER_PAGE_SIZE = "pageSize";
	String PARAMETER_COUNTABLE = "countable";
	
	/* Format */
	
	String FORMAT_PARAMETER_IDENTIFIER = "{"+PARAMETER_IDENTIFIER+"}";
	String FORMAT_PARAMETER_DIRECTORY = "{"+PARAMETER_DIRECTORY+"}";
	
	/* Paths */
	
	String PATH_ROOT = __SLASH__;
	String PATH_MANY = __MANY__;// PATH_ROOT+__MANY__+__SLASH__;
	String PATH_MANY_COLLECTION = PATH_MANY+__COLLECTION__+__SLASH__;
	
	String PATH_IDENTIFIER = PATH_ROOT+FORMAT_PARAMETER_IDENTIFIER;
	String PATH_DIRECTORY = PATH_ROOT+FORMAT_PARAMETER_DIRECTORY;
	
	String PATH_CREATE_ONE = __CREATE__;
	String PATH_CREATE_MANY = __CREATE__+__SLASH__+__MANY__;
	String PATH_CREATE_MANY_COLLECTION = __CREATE__+__SLASH__+__MANY__+__SLASH__+__COLLECTION__;
	
	String PATH_GET = PATH_ROOT+__GET__+__SLASH__;
	String PATH_GET_ONE = PATH_IDENTIFIER;
	String PATH_GET_MANY = __SLASH__;
	//String PATH_GET_MANY_BY_FIELDS = __SLASH__+ConstantString.FIELDS+__SLASH__;
	String PATH_GET_COUNT = PATH_GET+__COUNT__+__SLASH__;
	
	String PATH_UPDATE_ONE = __UPDATE__;
	String PATH_UPDATE_MANY = __UPDATE__+__SLASH__+__MANY__;
	
	String PATH_DELETE_ONE = __DELETE__;
	String PATH_DELETE_MANY = __DELETE__+__SLASH__+__MANY__;
	String PATH_DELETE_IDENTIFIERS = __DELETE__+__SLASH__+__IDENTIFIERS__;
	String PATH_DELETE_ALL = __DELETE__+__SLASH__+__ALL__;
	
	String PATH_SAVE_FROM_FILE_EXCEL_SHEET = __SAVE__+__SLASH__+__FROM__+__SLASH__+__FILE__+__SLASH__+__EXCEL__+__SLASH__+__SHEET__;
	
	String PATH_IMPORT = "import";
			
	/**/
	
	String TAG_CREATE = "create";
	String TAG_READ = "read";
	String TAG_UPDATE = "update";
	String TAG_DELETE = "delete";
	String TAG_SAVE = "save";
	String TAG_COUNT = "count";
	String TAG_IMPORT = "import";
	String TAG_LOAD = "load";
	
	String TAG_ONE = "one";
	String TAG_MANY = "many";
	String TAG_ALL = "all";
	String TAG_BY_IDENTIFIERS = "by identifiers";
	String TAG_EXCEL = "excel";

	/**/
	
	String PARAMETER_PAGEABLE = "paginer";
	String DESCRIPTION_PAGEABLE = "Activer la pagination";
	String EXAMPLE_PAGEABLE = "true";
	
	String PARAMETER_FIRST_TUPLE_INDEX = "index";
	String DESCRIPTION_FIRST_TUPLE_INDEX = "index du premier élément";
	String EXAMPLE_FIRST_TUPLE_INDEX = "0";
	
	String PARAMETER_NUMBER_OF_TUPLES = "nombre";
	String DESCRIPTION_NUMBER_OF_TUPLES = "Nombre d'élément";
	String EXAMPLE_NUMBER_OF_TUPLES = "5";
	
	String PARAMETER_NAME_FILTER = "f";
	String PARAMETER_NAME_FILTER_DESCRIPTION = "Filter";
	String PARAMETER_NAME_FILTER_DESCRIPTION_FRENCH = "Filtre";
	
	String PARAMETER_NAME_FILTER_FORMAT = "ff";
	String PARAMETER_NAME_FILTER_FORMAT_DESCRIPTION = "Filter format";
	String PARAMETER_NAME_FILTER_FORMAT_DESCRIPTION_FRENCH = "Format du filtre";
	
	String PARAMETER_NAME_FILTER_AS_JSON = "fjson";
	String PARAMETER_NAME_FILTER_AS_JSON_DESCRIPTION = "Filter as json";
	String PARAMETER_NAME_FILTER_AS_JSON_DESCRIPTION_FRENCH = "Filtre au format JSON";
	
	String PARAMETER_NAME_FILTER_AS_STRING = "f";
	String PARAMETER_NAME_FILTER_AS_STRING_DESCRIPTION = "Filter as string";
	String PARAMETER_NAME_FILTER_AS_STRING_DESCRIPTION_FRENCH = "Filtre";
	
	String PARAMETER_NAME_FIRST_TUPLE_INDEX = "from";
	String PARAMETER_NAME_FIRST_TUPLE_INDEX_DESCRIPTION = "First tuple index";
	String PARAMETER_NAME_FIRST_TUPLE_INDEX_DESCRIPTION_FRENCH = "Index du premier élément";
	
	String PARAMETER_NAME_NUMBER_OF_TUPLES = "count";
	String PARAMETER_NAME_NUMBER_OF_TUPLES_DESCRIPTION = "Number of tuples";
	String PARAMETER_NAME_NUMBER_OF_TUPLES_DESCRIPTION_FRENCH = "Nombre d'éléments";
	
	String PARAMETER_NAME_COLLECTIONABLE = "collectionable";
	String PARAMETER_NAME_COLLECTIONABLE_DESCRIPTION = "Collectionable";
	
	String PARAMETER_NAME_COUNTABLE = "countable";
	String PARAMETER_NAME_COUNTABLE_DESCRIPTION = "Countable";
	String PARAMETER_NAME_COUNTABLE_DESCRIPTION_FRENCH = "Comptable";
	
	String PARAMETER_NAME_PROJECTIONS = "projections";
	String PARAMETER_NAME_PROJECTIONS_DESCRIPTION = "Projections";
	String PARAMETER_NAME_PROJECTIONS_DESCRIPTION_FRENCH = "Projections";
	
	String PARAMETER_NAME_PAGEABLE = "pageable";
	String PARAMETER_NAME_PAGEABLE_DESCRIPTION = "Pageable";
	String PARAMETER_NAME_PAGEABLE_DESCRIPTION_FRENCH = "Paginable";
	
	String PARAMETER_NAME_IDENTIFIER = "identifier";
	String PARAMETER_NAME_IDENTIFIER_DESCRIPTION = "Identifier";
	String PARAMETER_NAME_IDENTIFIER_DESCRIPTION_FRENCH = "Identifiant";
}