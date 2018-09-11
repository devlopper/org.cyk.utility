package org.cyk.utility.server.business;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.assertion.AssertionBuilderNull;
import org.cyk.utility.server.persistence.PersistenceEntity;
import org.cyk.utility.server.persistence.PersistenceLayer;
import org.cyk.utility.system.AbstractSystemServiceProviderImpl;
import org.cyk.utility.system.action.SystemAction;

public abstract class AbstractBusinessServiceProviderImpl<OBJECT> extends AbstractSystemServiceProviderImpl implements BusinessServiceProvider<OBJECT>,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public BusinessServiceProvider<OBJECT> create(OBJECT object, Properties properties) {
		BusinessFunctionCreator function = __injectCreatorForOne__();
		function.setEntity(object);
		
		__configure__(function, properties);

		validateOne(object, function.getAction());
		__listenExecuteCreateOneBefore__(object, properties, function);
		function.execute();
		__listenExecuteCreateOneAfter__(object, properties, function);
		validateOne(object);
		return this;
	}
	
	protected BusinessFunctionCreator __injectCreatorForOne__(){
		return ____inject____(BusinessFunctionCreator.class);
	}
	
	protected void __listenExecuteCreateOneBefore__(OBJECT object, Properties properties,BusinessFunctionCreator function){}
	protected void __listenExecuteCreateOneAfter__(OBJECT object, Properties properties,BusinessFunctionCreator function){}
	
	@Override 
	public BusinessServiceProvider<OBJECT> create(OBJECT object) {
		return create(object, null);
	}

	@Override 
	public BusinessServiceProvider<OBJECT> createMany(Collection<OBJECT> objects, Properties properties) {
		BusinessFunctionCreator function = __injectCreatorForMany__();
		__configure__(function, properties);
		if(Boolean.TRUE.equals(__isCreateManyOneByOne__())) {
			//Loop execution
			function.try_().setIsCodeFromFunctionExecutable(Boolean.FALSE).run().addRunnables(new Runnable() {
				@Override
				public void run() {
					for(OBJECT index : objects) {
						create(index);
					}
				}
			});
		}else {
			//Batch execution
			function.setEntities(objects);
			validateMany(objects, function.getAction());
		}
		
		function.execute();
		
		if(Boolean.TRUE.equals(__isCreateManyOneByOne__())) {
			//Loop execution
			
		}else {
			//Batch execution
			validateMany(objects);
		}
		return this;
	}
	
	protected BusinessFunctionCreator __injectCreatorForMany__(){
		return ____inject____(BusinessFunctionCreator.class);
	}

	@Override 
	public BusinessServiceProvider<OBJECT> createMany(Collection<OBJECT> objects) {
		return createMany(objects, null);
	}
	
	protected Boolean __isCreateManyOneByOne__() {
		return Boolean.TRUE;
	}

	@Override 
	public BusinessServiceProvider<OBJECT> update(OBJECT object, Properties properties) {
		BusinessFunctionModifier function = ____inject____(BusinessFunctionModifier.class);
		__configure__(function, properties);
		function.setEntity(object);
		validateOne(object, function.getAction());
		__listenExecuteUpdateOneBefore__(object, properties, function);
		function.execute();
		__listenExecuteUpdateOneAfter__(object, properties, function);
		validateOne(object);
		return this;
	}
	
	protected void __listenExecuteUpdateOneBefore__(OBJECT object, Properties properties,BusinessFunctionModifier function){}
	protected void __listenExecuteUpdateOneAfter__(OBJECT object, Properties properties,BusinessFunctionModifier function){}

	@Override 
	public BusinessServiceProvider<OBJECT> update(OBJECT object) {
		return update(object, null);
	}

	@Override 
	public BusinessServiceProvider<OBJECT> updateMany(Collection<OBJECT> objects, Properties properties) {
		for(OBJECT index : objects)
			update(index, properties);
		return this;
	}

	@Override 
	public BusinessServiceProvider<OBJECT> updateMany(Collection<OBJECT> objects) {
		return updateMany(objects, null);
	}

	@Override 
	public BusinessServiceProvider<OBJECT> delete(OBJECT object, Properties properties) {
		BusinessFunctionRemover function =  ____inject____(BusinessFunctionRemover.class);
		__configure__(function, properties);
		function.setEntity(object);
		validateOne(object, function.getAction());
		__listenExecuteDeleteOneBefore__(object, properties, function);
		function.execute();
		__listenExecuteDeleteOneAfter__(object, properties, function);
		//object has been remove so it is not more persisted. no validation
		return this;
	}
	
	protected void __listenExecuteDeleteOneBefore__(OBJECT object, Properties properties,BusinessFunctionRemover function){}
	protected void __listenExecuteDeleteOneAfter__(OBJECT object, Properties properties,BusinessFunctionRemover function){}

	@Override 
	public BusinessServiceProvider<OBJECT> delete(OBJECT object) {
		return delete(object, null);
	}

	@Override 
	public BusinessServiceProvider<OBJECT> deleteMany(Collection<OBJECT> objects, Properties properties) {
		for(OBJECT index : objects)
			delete(index, properties);
		return this;
	}

	@Override 
	public BusinessServiceProvider<OBJECT> deleteMany(Collection<OBJECT> objects) {
		return deleteMany(objects, null);
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
	
	protected void __configure__(BusinessFunction function, Properties properties) {
		if(properties != null){
			
		}
	}
	
}
