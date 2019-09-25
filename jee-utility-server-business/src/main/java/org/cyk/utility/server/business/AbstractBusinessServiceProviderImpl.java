package org.cyk.utility.server.business;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.assertion.AssertionBuilderNull;
import org.cyk.utility.server.persistence.PersistenceEntity;
import org.cyk.utility.server.persistence.PersistenceLayer;
import org.cyk.utility.system.AbstractSystemServiceProviderImpl;
import org.cyk.utility.__kernel__.system.action.SystemAction;

public abstract class AbstractBusinessServiceProviderImpl<OBJECT> extends AbstractSystemServiceProviderImpl implements BusinessServiceProvider<OBJECT>,Serializable {
	private static final long serialVersionUID = 1L;
	
	/* Create */
	
	@Override  @Transactional
	public BusinessServiceProvider<OBJECT> createMany(Collection<OBJECT> objects, Properties properties) {
		if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(objects))) {
			BusinessFunctionCreator function = ____inject____(BusinessFunctionCreator.class);
			__copyCommonProperties__(function, properties);
			function.setEntities(objects);
			__listenExecuteCreateBefore__(objects, properties, function);
			function.execute();
			__listenExecuteCreateAfter__(objects, properties, function);	
		}
		return this;
	}
	
	@Override @Transactional
	public BusinessServiceProvider<OBJECT> createMany(Collection<OBJECT> objects) {
		return createMany(objects, null);
	}
	
	@Override @Transactional
	public BusinessServiceProvider<OBJECT> create(OBJECT object, Properties properties) {
		if(object != null)
			createMany(Arrays.asList(object),properties);
		return this;
	}
		

	
	@Override @Transactional
	public BusinessServiceProvider<OBJECT> create(OBJECT object) {
		return create(object, null);
	}
	
	@Override
	public BusinessServiceProvider<OBJECT> createByBatch(Collection<OBJECT> objects, Object batchSize,Properties properties) {
		if(CollectionHelper.isNotEmpty(objects)) {
			List<List<OBJECT>> lists = CollectionHelper.getBatches((List<OBJECT>) objects, batchSize);	
			if(CollectionHelper.isNotEmpty(lists)) {
				for(List<OBJECT> index : lists) {
					createMany(index,properties);
				}
			}
		}
		return this;
	}
	
	@Override
	public BusinessServiceProvider<OBJECT> createByBatch(Collection<OBJECT> objects, Object batchSize) {
		createByBatch(objects, batchSize, null);
		return this;
	}

	protected void __listenExecuteCreateBefore__(Collection<OBJECT> objects, Properties properties,BusinessFunctionCreator function){
		if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(objects)))
			for(OBJECT index : objects)
				__listenExecuteCreateBefore__(index, properties, function);
	}
	
	protected void __listenExecuteCreateAfter__(Collection<OBJECT> objects, Properties properties,BusinessFunctionCreator function){
		if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(objects)))
			for(OBJECT index : objects)
				__listenExecuteCreateAfter__(index, properties, function);
	}
	
	protected void __listenExecuteCreateBefore__(OBJECT object, Properties properties,BusinessFunctionCreator function){}
	
	protected void __listenExecuteCreateAfter__(OBJECT object, Properties properties,BusinessFunctionCreator function){}
	
	/* Update */
	
	@Override @Transactional
	public BusinessServiceProvider<OBJECT> updateMany(Collection<OBJECT> objects, Properties properties) {
		if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(objects))) {
			BusinessFunctionModifier function = ____inject____(BusinessFunctionModifier.class);
			__copyCommonProperties__(function, properties);
			function.setEntities(objects);
			__listenExecuteUpdateBefore__(objects, properties, function);
			function.execute();
			__listenExecuteUpdateAfter__(objects, properties, function);
		}
		return this;
	}

	@Override @Transactional
	public BusinessServiceProvider<OBJECT> updateMany(Collection<OBJECT> objects) {
		return updateMany(objects, null);
	}
	
	@Override @Transactional
	public BusinessServiceProvider<OBJECT> update(OBJECT object, Properties properties) {
		if(object != null)
			updateMany(Arrays.asList(object),properties);
		return this;
	}
	
	@Override @Transactional
	public BusinessServiceProvider<OBJECT> update(OBJECT object) {
		return update(object, null);
	}
	
	@Override
	public BusinessServiceProvider<OBJECT> updateByBatch(Collection<OBJECT> objects, Object batchSize,Properties properties) {
		if(CollectionHelper.isNotEmpty(objects)) {
			List<List<OBJECT>> lists = CollectionHelper.getBatches((List<OBJECT>) objects, batchSize);	
			if(CollectionHelper.isNotEmpty(lists)) {
				for(List<OBJECT> index : lists) {
					updateMany(index,properties);
				}
			}
		}
		return this;
	}
	
	@Override
	public BusinessServiceProvider<OBJECT> updateByBatch(Collection<OBJECT> objects, Object batchSize) {
		updateByBatch(objects, batchSize, null);
		return this;
	}
	
	protected void __listenExecuteUpdateBefore__(Collection<OBJECT> objects, Properties properties,BusinessFunctionModifier function){
		if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(objects)))
			for(OBJECT index : objects)
				__listenExecuteUpdateBefore__(index, properties, function);
	}
	
	protected void __listenExecuteUpdateAfter__(Collection<OBJECT> objects, Properties properties,BusinessFunctionModifier function){
		if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(objects)))
			for(OBJECT index : objects)
				__listenExecuteUpdateAfter__(index, properties, function);
	}
	
	protected void __listenExecuteUpdateBefore__(OBJECT object, Properties properties,BusinessFunctionModifier function){}
	
	protected void __listenExecuteUpdateAfter__(OBJECT object, Properties properties,BusinessFunctionModifier function){}
	
	/* Delete */
	
	@Override @Transactional
	public BusinessServiceProvider<OBJECT> deleteMany(Collection<OBJECT> objects, Properties properties) {
		if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(objects))) {
			BusinessFunctionRemover function =  ____inject____(BusinessFunctionRemover.class);
			__copyCommonProperties__(function, properties);
			function.setEntities(objects);
			__listenExecuteDeleteBefore__(objects, properties, function);
			function.execute();
			__listenExecuteDeleteAfter__(objects, properties, function);	
		}
		return this;
	}

	@Override @Transactional
	public BusinessServiceProvider<OBJECT> deleteMany(Collection<OBJECT> objects) {
		return deleteMany(objects, null);
	}
	
	@Override @Transactional
	public BusinessServiceProvider<OBJECT> delete(OBJECT object, Properties properties) {
		if(object != null)
			deleteMany(Arrays.asList(object), properties);
		return this;
	}
	
	@Override @Transactional
	public BusinessServiceProvider<OBJECT> delete(OBJECT object) {
		return delete(object, null);
	}
	
	@Override
	public BusinessServiceProvider<OBJECT> deleteByBatch(Collection<OBJECT> objects, Object batchSize,Properties properties) {
		if(CollectionHelper.isNotEmpty(objects)) {
			List<List<OBJECT>> lists = CollectionHelper.getBatches((List<OBJECT>) objects, batchSize);	
			if(CollectionHelper.isNotEmpty(lists)) {
				for(List<OBJECT> index : lists) {
					deleteMany(index,properties);
				}
			}
		}
		return this;
	}
	
	@Override
	public BusinessServiceProvider<OBJECT> deleteByBatch(Collection<OBJECT> objects, Object batchSize) {
		deleteByBatch(objects, batchSize, null);
		return this;
	}
	
	protected void __listenExecuteDeleteBefore__(Collection<OBJECT> objects, Properties properties,BusinessFunctionRemover function){
		if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(objects)))
			for(OBJECT index : objects)
				__listenExecuteDeleteBefore__(index, properties, function);
	}
	
	protected void __listenExecuteDeleteAfter__(Collection<OBJECT> objects, Properties properties,BusinessFunctionRemover function){
		if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(objects)))
			for(OBJECT index : objects)
				__listenExecuteDeleteAfter__(index, properties, function);
	}
	
	protected void __listenExecuteDeleteBefore__(OBJECT object, Properties properties,BusinessFunctionRemover function){}
	
	protected void __listenExecuteDeleteAfter__(OBJECT object, Properties properties,BusinessFunctionRemover function){}
	
	/* Save */
	
	@Override @Transactional
	public BusinessServiceProvider<OBJECT> save(OBJECT object, Properties properties) {
		if(Boolean.TRUE.equals(isPersisted(object)))
			update(object,properties);
		else
			create(object,properties);
		return this;
	}
	
	@Override @Transactional
	public BusinessServiceProvider<OBJECT> save(OBJECT object) {
		return save(object, null);
	}

	@Override @Transactional
	public BusinessServiceProvider<OBJECT> saveMany(Collection<OBJECT> objects, Properties properties) {
		if(CollectionHelper.isNotEmpty(objects))
			for(OBJECT index : objects)
				save(index,properties);
		return this;
	}
	
	@Override @Transactional
	public BusinessServiceProvider<OBJECT> saveMany(Collection<OBJECT> objects) {
		return saveMany(objects, null);
	}

	@Override
	public BusinessServiceProvider<OBJECT> saveByBatch(Collection<OBJECT> objects, Object batchSize,Properties properties) {
		if(CollectionHelper.isNotEmpty(objects)) {
			List<List<OBJECT>> lists = CollectionHelper.getBatches((List<OBJECT>) objects, batchSize);	
			if(CollectionHelper.isNotEmpty(lists)) {
				for(List<OBJECT> index : lists) {
					saveMany(index,properties);
				}
			}
		}
		return this;
	}
	
	@Override
	public BusinessServiceProvider<OBJECT> saveByBatch(Collection<OBJECT> objects, Object batchSize) {
		saveByBatch(objects, batchSize, null);
		return this;
	}
	
	/**/
	
	protected PersistenceEntity<?> __injectPersistenceFromClass__(Class<?> entityClass){
		return (PersistenceEntity<?>) __inject__(PersistenceLayer.class).injectInterfaceClassFromEntityClass(entityClass);
	}
	
	/**/
	
	protected AssertionBuilderNull __injectAssertionBuilderNull__(Boolean isAffirmation,Object object,String...names){
		return __inject__(AssertionBuilderNull.class).setIsAffirmation(isAffirmation).setFieldValueGetter(object,names);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void __validateOne__(Object object, SystemAction action) {
		____validateOne____((OBJECT) object, action);
	}
	
	protected void ____validateOne____(OBJECT object, SystemAction action) {
		
	}

	/**/
	
	protected static BusinessLayer __injectBusinessLayer__() {
		return __inject__(BusinessLayer.class);
	}
	
}
