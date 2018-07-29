package org.cyk.utility.server.business;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.assertion.AssertionBuilder;
import org.cyk.utility.assertion.AssertionBuilderNull;
import org.cyk.utility.function.ExecutionPhase;
import org.cyk.utility.server.persistence.PersistenceEntity;
import org.cyk.utility.server.persistence.PersistenceLayer;
import org.cyk.utility.system.AbstractSystemServiceProviderImpl;

public  class AbstractBusinessServiceProviderImpl<OBJECT> extends AbstractSystemServiceProviderImpl implements BusinessServiceProvider<OBJECT>,Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public BusinessServiceProvider<OBJECT> create(OBJECT object, Properties properties) {
		__inject__(BusinessFunctionCreator.class).setEntity(object).setPreExecutionPhase(properties == null ? null : (ExecutionPhase)properties.getFromPath(Properties.EXECUTION,Properties.PRE)).execute();
		return this;
	}

	@Override
	public BusinessServiceProvider<OBJECT> create(OBJECT object) {
		return create(object, null);
	}

	@Override
	public BusinessServiceProvider<OBJECT> createMany(Collection<OBJECT> objects, Properties properties) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public BusinessServiceProvider<OBJECT> createMany(Collection<OBJECT> objects) {
		return createMany(objects, null);
	}

	@Override
	public BusinessServiceProvider<OBJECT> update(OBJECT object, Properties properties) {
		__inject__(BusinessFunctionModifier.class).setEntity(object).execute();
		return this;
	}

	@Override
	public BusinessServiceProvider<OBJECT> update(OBJECT object) {
		return update(object, null);
	}

	@Override
	public BusinessServiceProvider<OBJECT> updateMany(Collection<OBJECT> objects, Properties properties) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public BusinessServiceProvider<OBJECT> updateMany(Collection<OBJECT> objects) {
		return updateMany(objects, null);
	}

	@Override
	public BusinessServiceProvider<OBJECT> delete(OBJECT object, Properties properties) {
		__inject__(BusinessFunctionRemover.class).setEntity(object).execute();
		return this;
	}

	@Override
	public BusinessServiceProvider<OBJECT> delete(OBJECT object) {
		return delete(object, null);
	}

	@Override
	public BusinessServiceProvider<OBJECT> deleteMany(Collection<OBJECT> objects, Properties properties) {
		// TODO Auto-generated method stub
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
	
	protected Properties addExecutionPhaseAssertions(Properties properties,Boolean isPre,AssertionBuilder...assertionBuilders){
		String pre = Boolean.TRUE.equals(isPre) ? Properties.PRE : Properties.POST;
		ExecutionPhase executionPhase = (ExecutionPhase) Properties.getFromPath(properties,Properties.EXECUTION,pre);
		if(executionPhase == null)
			properties = Properties.setFromPath(properties, new Object[]{Properties.EXECUTION,pre}, executionPhase = new ExecutionPhase());
		for(AssertionBuilder index : assertionBuilders)
			executionPhase.addAssertions(index.execute().getOutput());
		return properties;
	}
	
	protected Properties addExecutionPhaseRunnables(Properties properties,Boolean isPre,Runnable...runnables){
		String pre = Boolean.TRUE.equals(isPre) ? Properties.PRE : Properties.POST;
		ExecutionPhase executionPhase = (ExecutionPhase) Properties.getFromPath(properties,Properties.EXECUTION,pre);
		if(executionPhase == null)
			properties = Properties.setFromPath(properties, new Object[]{Properties.EXECUTION,pre}, executionPhase = new ExecutionPhase());
		executionPhase.addRunnables(runnables);
		return properties;
	}
}
