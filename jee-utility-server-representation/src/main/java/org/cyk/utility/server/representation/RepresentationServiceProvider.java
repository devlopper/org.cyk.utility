package org.cyk.utility.server.representation;

import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.system.SystemServiceProvider;

public interface RepresentationServiceProvider<OBJECT> extends SystemServiceProvider {

	/* Create */
	RepresentationServiceProvider<OBJECT> create(OBJECT object,Properties properties);
	RepresentationServiceProvider<OBJECT> create(OBJECT object);
	
	RepresentationServiceProvider<OBJECT> createMany(Collection<OBJECT> objects,Properties properties);
	RepresentationServiceProvider<OBJECT> createMany(Collection<OBJECT> objects);
	
	/* Read */ 
	
	/* Update */
	RepresentationServiceProvider<OBJECT> update(OBJECT object,Properties properties);
	RepresentationServiceProvider<OBJECT> update(OBJECT object);
	
	RepresentationServiceProvider<OBJECT> updateMany(Collection<OBJECT> objects,Properties properties);
	RepresentationServiceProvider<OBJECT> updateMany(Collection<OBJECT> objects);
	
	/* Delete */
	RepresentationServiceProvider<OBJECT> delete(OBJECT object,Properties properties);
	RepresentationServiceProvider<OBJECT> delete(OBJECT object);
	
	RepresentationServiceProvider<OBJECT> deleteMany(Collection<OBJECT> objects,Properties properties);
	RepresentationServiceProvider<OBJECT> deleteMany(Collection<OBJECT> objects);
	
	/* Count */
	
	/**/

}
