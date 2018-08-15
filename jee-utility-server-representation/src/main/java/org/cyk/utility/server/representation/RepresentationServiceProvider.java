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
	
	/* Parameter */
	
	String PARAMETER_IDENTIFIER = "identifier";
	String PARAMETER_TYPE = "type";
	
	/* Paths */
	
	String PATH_ROOT = "/";
	String PATH_MANY = PATH_ROOT+"many"+"/";
	
	String PATH_IDENTIFIER = PATH_ROOT+"{"+PARAMETER_IDENTIFIER+"}";
	
	String PATH_DELETE = PATH_ROOT+"delete"+"/";
	String PATH_DELETE_MANY = PATH_DELETE+"many"+"/";
	String PATH_DELETE_ALL = PATH_DELETE+"all"+"/";
	
	String PATH_GET = PATH_ROOT+"get/";
	String PATH_GET_COUNT = PATH_GET+"count";

	
}
