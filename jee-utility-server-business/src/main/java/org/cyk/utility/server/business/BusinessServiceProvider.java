package org.cyk.utility.server.business;

import java.util.Collection;

import javax.transaction.Transactional;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.system.SystemServiceProvider;

public interface BusinessServiceProvider<OBJECT> extends SystemServiceProvider {

	/* Create */ 
	@Transactional
	BusinessServiceProvider<OBJECT> create(OBJECT object,Properties properties);
	@Transactional
	BusinessServiceProvider<OBJECT> create(OBJECT object);
	@Transactional
	BusinessServiceProvider<OBJECT> createMany(Collection<OBJECT> objects,Properties properties);
	@Transactional
	BusinessServiceProvider<OBJECT> createMany(Collection<OBJECT> objects);
	BusinessServiceProvider<OBJECT> createByBatch(Collection<OBJECT> objects,Object batchSize,Properties properties);
	BusinessServiceProvider<OBJECT> createByBatch(Collection<OBJECT> objects,Object batchSize);
	
	/* Read */ 
	
	/* Update */
	@Transactional
	BusinessServiceProvider<OBJECT> update(OBJECT object,Properties properties);
	@Transactional
	BusinessServiceProvider<OBJECT> update(OBJECT object);
	@Transactional
	BusinessServiceProvider<OBJECT> updateMany(Collection<OBJECT> objects,Properties properties);
	@Transactional
	BusinessServiceProvider<OBJECT> updateMany(Collection<OBJECT> objects);
	BusinessServiceProvider<OBJECT> updateByBatch(Collection<OBJECT> objects,Object batchSize,Properties properties);
	BusinessServiceProvider<OBJECT> updateByBatch(Collection<OBJECT> objects,Object batchSize);
	
	/* Delete */
	@Transactional
	BusinessServiceProvider<OBJECT> delete(OBJECT object,Properties properties);
	@Transactional
	BusinessServiceProvider<OBJECT> delete(OBJECT object);
	@Transactional
	BusinessServiceProvider<OBJECT> deleteMany(Collection<OBJECT> objects,Properties properties);
	@Transactional
	BusinessServiceProvider<OBJECT> deleteMany(Collection<OBJECT> objects);
	BusinessServiceProvider<OBJECT> deleteByBatch(Collection<OBJECT> objects,Object batchSize,Properties properties);
	BusinessServiceProvider<OBJECT> deleteByBatch(Collection<OBJECT> objects,Object batchSize);
	BusinessServiceProvider<OBJECT> deleteAll(Properties properties);
	BusinessServiceProvider<OBJECT> deleteAll();
	
	/* Save */ 
	@Transactional
	BusinessServiceProvider<OBJECT> save(OBJECT object,Properties properties);
	@Transactional
	BusinessServiceProvider<OBJECT> save(OBJECT object);
	@Transactional
	BusinessServiceProvider<OBJECT> saveMany(Collection<OBJECT> objects,Properties properties);
	@Transactional
	BusinessServiceProvider<OBJECT> saveMany(Collection<OBJECT> objects);
	BusinessServiceProvider<OBJECT> saveByBatch(Collection<OBJECT> objects,Object batchSize,Properties properties);
	BusinessServiceProvider<OBJECT> saveByBatch(Collection<OBJECT> objects,Object batchSize);
	
	/* Count */
	
	/**/

}
