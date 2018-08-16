package org.cyk.utility.server.representation;

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
	String __MANY__ = "many";
	String __COLLECTION__ = "collection";
	String __ALL__ = "all";
	String __DELETE__ = "delete";
	String __GET__ = "get";
	String __GOUNT__ = "count";
	
	/* Parameter */
	
	String PARAMETER_IDENTIFIER = "identifier";
	String PARAMETER_TYPE = "type";
	
	/* Paths */
	
	String PATH_ROOT = __SLASH__;
	String PATH_MANY = PATH_ROOT+__MANY__+__SLASH__;
	String PATH_MANY_COLLECTION = PATH_MANY+__COLLECTION__+__SLASH__;
	
	String PATH_IDENTIFIER = PATH_ROOT+"{"+PARAMETER_IDENTIFIER+"}";
	
	String PATH_DELETE = PATH_ROOT+__DELETE__+__SLASH__;
	String PATH_DELETE_MANY = PATH_DELETE+__MANY__+__SLASH__;
	String PATH_DELETE_ALL = PATH_DELETE+__ALL__+__SLASH__;
	
	String PATH_GET = PATH_ROOT+__GET__+__SLASH__;
	String PATH_GET_COUNT = PATH_GET+__GOUNT__+__SLASH__;

	/**/
	
	
}
