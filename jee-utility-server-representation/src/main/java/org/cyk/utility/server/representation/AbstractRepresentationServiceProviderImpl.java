package org.cyk.utility.server.representation;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.system.AbstractSystemServiceProviderImpl;

public abstract class AbstractRepresentationServiceProviderImpl<OBJECT,DTO> extends AbstractSystemServiceProviderImpl implements RepresentationServiceProvider<OBJECT,DTO>,Serializable {

	private static final long serialVersionUID = 1L;
	
	/*
	@Override //@Transactional
	public RepresentationServiceProvider<OBJECT> create(OBJECT object, Properties properties) {
		RepresentationFunctionCreator function = __inject__(RepresentationFunctionCreator.class);
		__configure__(function, properties);
		function.setEntity(object);
		validateOne(object, function.getAction());
		function.execute();
		validateOne(object);
		return this;
	}

	@Override //@Transactional
	public RepresentationServiceProvider<OBJECT> create(OBJECT object) {
		return create(object, null);
	}

	@Override //@Transactional
	public RepresentationServiceProvider<OBJECT> createMany(Collection<OBJECT> objects, Properties properties) {
		for(OBJECT index : objects)
			create(index, properties);
		return this;
	}

	@Override //@Transactional
	public RepresentationServiceProvider<OBJECT> createMany(Collection<OBJECT> objects) {
		return createMany(objects, null);
	}

	@Override //@Transactional
	public RepresentationServiceProvider<OBJECT> update(OBJECT object, Properties properties) {
		RepresentationFunctionModifier function = __inject__(RepresentationFunctionModifier.class);
		__configure__(function, properties);
		function.setEntity(object);
		validateOne(object, function.getAction());
		function.execute();
		validateOne(object);
		return this;
	}

	@Override //@Transactional
	public RepresentationServiceProvider<OBJECT> update(OBJECT object) {
		return update(object, null);
	}

	@Override //@Transactional
	public RepresentationServiceProvider<OBJECT> updateMany(Collection<OBJECT> objects, Properties properties) {
		for(OBJECT index : objects)
			update(index, properties);
		return this;
	}

	@Override //@Transactional
	public RepresentationServiceProvider<OBJECT> updateMany(Collection<OBJECT> objects) {
		return updateMany(objects, null);
	}

	@Override //@Transactional
	public RepresentationServiceProvider<OBJECT> delete(OBJECT object, Properties properties) {
		RepresentationFunctionRemover function =  __inject__(RepresentationFunctionRemover.class);
		__configure__(function, properties);
		function.setEntity(object);
		validateOne(object, function.getAction());
		function.execute();
		//object has been remove so it is not more persisted. no validation
		return this;
	}

	@Override //@Transactional
	public RepresentationServiceProvider<OBJECT> delete(OBJECT object) {
		return delete(object, null);
	}

	@Override //@Transactional
	public RepresentationServiceProvider<OBJECT> deleteMany(Collection<OBJECT> objects, Properties properties) {
		for(OBJECT index : objects)
			delete(index, properties);
		return this;
	}

	@Override //@Transactional
	public RepresentationServiceProvider<OBJECT> deleteMany(Collection<OBJECT> objects) {
		return deleteMany(objects, null);
	}

	
	protected PersistenceEntity<?> __injectPersistenceFromClass__(Class<?> entityClass){
		return (PersistenceEntity<?>) __inject__(PersistenceLayer.class).injectInterfaceClassFromEntityClass(entityClass);
	}
	
	
	protected AssertionBuilderNull __injectAssertionBuilderNull__(Boolean isAffirmation,Object object,String...names){
		return __inject__(AssertionBuilderNull.class).setIsAffirmation(isAffirmation).setFieldValueGetter(object,names);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void __validateOne__(Object object, SystemAction action) {
		____validateOne____((OBJECT) object, action);
	}
	
	protected void ____validateOne____(OBJECT object, SystemAction action) {}
	
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
	
	protected void __configure__(RepresentationFunction function, Properties properties) {
		if(properties != null){
			function.setPreExecutionPhase((ExecutionPhase)properties.getFromPath(Properties.EXECUTION,Properties.PRE));
			//TODO use getter setter from properties
			function.getProperties().setFromPath(new Object[]{Properties.IS,Properties.CORE,Properties.EXECUTABLE}, properties.getFromPath(Properties.IS,Properties.CORE,Properties.EXECUTABLE));	
		}
	}
	*/
}
