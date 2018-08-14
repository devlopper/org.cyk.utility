package org.cyk.utility.server.representation;

/*
 * All method must be annotated with HTTP method
 */
public interface RepresentationServiceProvider<OBJECT,DTO> /*extends Singleton*/ {

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

}
