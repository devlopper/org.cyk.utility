package org.cyk.utility.server.business;

import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.system.SystemServiceProvider;

public interface BusinessServiceProvider<OBJECT> extends SystemServiceProvider {

	/* Create */
	BusinessServiceProvider<OBJECT> create(OBJECT object,Properties properties);
	BusinessServiceProvider<OBJECT> create(OBJECT object);
	
	BusinessServiceProvider<OBJECT> createMany(Collection<OBJECT> objects,Properties properties);
	BusinessServiceProvider<OBJECT> createMany(Collection<OBJECT> objects);
	
	/* Read */ 
	
	/* Update */
	BusinessServiceProvider<OBJECT> update(OBJECT object,Properties properties);
	BusinessServiceProvider<OBJECT> update(OBJECT object);
	
	BusinessServiceProvider<OBJECT> updateMany(Collection<OBJECT> objects,Properties properties);
	BusinessServiceProvider<OBJECT> updateMany(Collection<OBJECT> objects);
	
	/* Delete */
	BusinessServiceProvider<OBJECT> delete(OBJECT object,Properties properties);
	BusinessServiceProvider<OBJECT> delete(OBJECT object);
	
	BusinessServiceProvider<OBJECT> deleteMany(Collection<OBJECT> objects,Properties properties);
	BusinessServiceProvider<OBJECT> deleteMany(Collection<OBJECT> objects);
	
	/* Count */
	
	/**/

}
