package org.cyk.utility.server.representation;

import org.cyk.utility.__kernel__.constant.ConstantString;

/*
 * All method must be annotated with HTTP method and return a Response object
 */
public interface RepresentationServiceProvider<OBJECT,DTO> {

	/* Create */
	/*
	RepresentationServiceProvider<OBJECT,DTO> create(OBJECT object,Properties properties);
	RepresentationServiceProvider<OBJECT,DTO> create(OBJECT object);
	
	RepresentationServiceProvider<OBJECT,DTO> createMany(Collection<OBJECT> objects,Properties properties);
	RepresentationServiceProvider<OBJECT,DTO> createMany(Collection<OBJECT> objects);
	*/
	/* Read */ 
	
	/* Update */
	/*RepresentationServiceProvider<OBJECT,DTO> update(OBJECT object,Properties properties);
	RepresentationServiceProvider<OBJECT,DTO> update(OBJECT object);
	
	RepresentationServiceProvider<OBJECT,DTO> updateMany(Collection<OBJECT> objects,Properties properties);
	RepresentationServiceProvider<OBJECT,DTO> updateMany(Collection<OBJECT> objects);
	*/
	/* Delete */
	/*RepresentationServiceProvider<OBJECT,DTO> delete(OBJECT object,Properties properties);
	RepresentationServiceProvider<OBJECT,DTO> delete(OBJECT object);
	
	RepresentationServiceProvider<OBJECT,DTO> deleteMany(Collection<OBJECT> objects,Properties properties);
	RepresentationServiceProvider<OBJECT,DTO> deleteMany(Collection<OBJECT> objects);
	*/
	/* Count */
	
	/**/
	
	String __SLASH__ = "/";
	String __MANY__ = ConstantString.MANY;
	String __COLLECTION__ = ConstantString.COLLECTION;
	String __ALL__ = ConstantString.ALL;
	String __CREATE__ = ConstantString.CREATE;
	String __UPDATE__ = ConstantString.UPDATE;
	String __DELETE__ = ConstantString.DELETE;
	String __GET__ = ConstantString.GET;
	String __COUNT__ = ConstantString.COUNT;
	
	/* Parameter */
	
	String PARAMETER_IDENTIFIER = ConstantString.IDENTIFIER;
	String PARAMETER_TYPE = ConstantString.TYPE;
	String PARAMETER_FIELDS = ConstantString.FIELDS;
	String PARAMETER_FROM = ConstantString.FROM;
	String PARAMETER_COUNT = ConstantString.COUNT;
	String PARAMETER_CODE = ConstantString.CODE;
	String PARAMETER_IS_INLINE = ConstantString.IS_INLINE;
	
	/* Paths */
	
	String PATH_ROOT = __SLASH__;
	String PATH_MANY = __MANY__;// PATH_ROOT+__MANY__+__SLASH__;
	String PATH_MANY_COLLECTION = PATH_MANY+__COLLECTION__+__SLASH__;
	
	String PATH_IDENTIFIER = PATH_ROOT+"{"+PARAMETER_IDENTIFIER+"}";
	
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
	String PATH_DELETE_ALL = __DELETE__+__SLASH__+__ALL__;
	
	/**/
	
	
}
