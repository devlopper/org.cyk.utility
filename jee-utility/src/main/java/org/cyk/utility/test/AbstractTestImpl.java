package org.cyk.utility.test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;

import org.assertj.core.util.Arrays;
import org.cyk.utility.__kernel__.AbstractRunnableImpl;
import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.assertion.AssertionHelper;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl;
import org.cyk.utility.throwable.ThrowableHelper;

public abstract class AbstractTestImpl extends AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl implements Test,Serializable {
	private static final long serialVersionUID = 1L;

	@Inject protected AssertionHelper assertionHelper;
	private String name;
	private Class<? extends Throwable> expectedThrowableCauseClass;
	private Collection<Object> objectsToBeCreated;
	private Map<Class<?>,Collection<Object>> objectBusinessIdentifiersToBeDeletedOnClean;
	private Collection<Object> garbages;
	private Collection<Object> notGarbagable;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setIsCatchThrowable(Boolean.TRUE);
		try_().begin().addRunnables(new AbstractRunnableImpl() {
			private static final long serialVersionUID = 1L;

			@Override
			public void __run__() {
				System.out.println("Setting up test named <"+__getName__(getName())+">");
				try {
					__setup__();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		finally_().begin().addRunnables(new Runnable() {
			@Override
			public void run() {
				System.out.println("Cleaning up test named <"+__getName__(getName())+">");
				try {
					__clean__();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				/* This following is needed */
				
				Class<? extends Throwable> expectedThrowableCauseClass = getExpectedThrowableCauseClass();
				if(expectedThrowableCauseClass == null)
					assertThrowableIsNull();
				else
					assertThrowableCauseIsInstanceOf(expectedThrowableCauseClass);
			}
		});
	}
	
	@Override
	protected void ____execute____() throws Exception {
		______execute______();
		__assert__();
	}
	
	protected void ______execute______() throws Exception {
		__injectThrowableHelper__().throwRuntimeExceptionNotYetImplemented();
	}
	
	protected void __assert__() {
		Class<? extends Throwable> expectedThrowableCauseClass = getExpectedThrowableCauseClass();
		if(expectedThrowableCauseClass == null)
			assertThrowableIsNull();
		else
			assertThrowableCauseIsInstanceOf(expectedThrowableCauseClass);
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public Test setName(String name) {
		this.name = name;
		return this;
	}
	
	protected String __getName__(String name) {
		if(__injectStringHelper__().isBlank(name)) {
			name = getClass().getSimpleName();
		}
		return name;
	}
	
	@Override
	public Collection<Object> getObjectsToBeCreated(){
		return objectsToBeCreated;
	}
	
	@Override
	public Test setObjectsToBeCreated(Collection<Object> objects){
		this.objectsToBeCreated = objects;
		return this;
	}
	
	@Override
	public Test addObjectsToBeCreatedCollection(Collection<Object> objects){
		setObjectsToBeCreated(__inject__(CollectionHelper.class).add(getObjectsToBeCreated(), Boolean.TRUE, objects));
		return this;
	}
	
	@Override
	public Test addObjectsToBeCreatedArray(Object...objects){
		addObjectsToBeCreatedCollection(__inject__(CollectionHelper.class).instanciate(objects));
		return this;
	}
	
	@Override
	public Map<Class<?>,Collection<Object>> getObjectBusinessIdentifiersToBeDeletedOnClean(){
		return objectBusinessIdentifiersToBeDeletedOnClean;
	}
	
	@Override
	public Test setObjectBusinessIdentifiersToBeDeletedOnClean(Map<Class<?>,Collection<Object>> objectBusinessIdentifiersToBeDeletedOnClean){
		this.objectBusinessIdentifiersToBeDeletedOnClean = objectBusinessIdentifiersToBeDeletedOnClean;
		return this;
	}
	
	@Override
	public Test addObjectBusinessIdentifiersToBeDeletedOnCleanCollection(Class<?> aClass,Collection<Object> identifiers){
		if(aClass!=null) {
			Map<Class<?>,Collection<Object>> map = getObjectBusinessIdentifiersToBeDeletedOnClean();
			if(map == null)
				setObjectBusinessIdentifiersToBeDeletedOnClean(map = new LinkedHashMap<>());
			Collection<Object> collection = map.get(aClass);
			if(collection == null)
				map.put(aClass, collection = new ArrayList<>());
			collection.addAll(identifiers);
		}
		return this;
	}
	
	@Override
	public Test addObjectBusinessIdentifiersToBeDeletedOnCleanArray(Class<?> aClass,Object...identifiers){
		addObjectBusinessIdentifiersToBeDeletedOnCleanCollection(aClass,__inject__(CollectionHelper.class).instanciate(identifiers));
		return this;
	}
	
	@Override
	public Collection<Object> getGarbages(){
		return garbages;
	}
	
	@Override
	public Test setGarbages(Collection<Object> objects){
		this.garbages = objects;
		return this;
	}
	
	@Override
	public Test addGarbagesCollection(Collection<Object> objects){
		setGarbages(__inject__(CollectionHelper.class).add(getGarbages(), Boolean.TRUE, objects));
		return this;
	}
	
	@Override
	public Test addGarbagesArray(Object...objects){
		addGarbagesCollection(__inject__(CollectionHelper.class).instanciate(objects));
		return this;
	}
	
	@Override
	public Collection<Object> getNotGarbagable(){
		return notGarbagable;
	}
	
	@Override
	public Test setNotGarbagable(Collection<Object> objects){
		this.notGarbagable = objects;
		return this;
	}
	
	@Override
	public Test addNotGarbagableCollection(Collection<Object> objects){
		setNotGarbagable(__inject__(CollectionHelper.class).add(getNotGarbagable(), Boolean.TRUE, objects));
		return this;
	}
	
	@Override
	public Test addNotGarbagableArray(Object...objects){
		addNotGarbagableCollection(__inject__(CollectionHelper.class).instanciate(objects));
		return this;
	}
		
	protected void __setup__() throws Exception {
		Collection<Object> objectsToBeCreated = getObjectsToBeCreated();
		if(objectsToBeCreated!=null) {
			__beginTransaction__();
			__createMany__(objectsToBeCreated);
			__endTransaction__();
			
			Collection<Object> garbages = new ArrayList<>(objectsToBeCreated);
			Collection<Object> notGarbagable = getNotGarbagable();
			if(notGarbagable!=null)
				garbages.removeAll(notGarbagable);
				
			addGarbagesCollection(garbages);
		}
	}
	
	protected void __beginTransaction__() throws Exception {}
	protected void __endTransaction__() throws Exception {}
	
	protected void __createOne__(Object object) throws Exception {
		__injectThrowableHelper__().throwRuntimeExceptionNotYetImplemented();
	}
	
	protected void __createMany__(Collection<Object> objects) throws Exception {
		if(__injectCollectionHelper__().isNotEmpty(objects))
			for(Object index : objects)
				__createOne__(index);
	}
	
	protected void __createManyByArray__(Object...objects) throws Exception {
		if(__inject__(ArrayHelper.class).isNotEmpty(objects))
			__createMany__(Arrays.asList(objects));
	}
	
	protected Object __readOneByBusinessIdentifier__(Class<?> aClass,Object identifier) {
		return null;
	}
	
	protected void __deleteOne__(Object object) throws Exception {
		__injectThrowableHelper__().throwRuntimeExceptionNotYetImplemented();
	}
	
	protected void __deleteMany__(Collection<Object> objects) throws Exception {
		if(__injectCollectionHelper__().isNotEmpty(objects))
			for(Object index : objects)
				__deleteOne__(index);
	}
	
	protected void __deleteManyByArray__(Object...objects) throws Exception {
		if(__inject__(ArrayHelper.class).isNotEmpty(objects))
			__deleteMany__(Arrays.asList(objects));
	}
	
	@Override
	public Test setExpectedThrowableCauseClass(Class<? extends Throwable> aClass) {
		this.expectedThrowableCauseClass = aClass;
		return this;
	}
	
	@Override
	public Class<? extends Throwable> getExpectedThrowableCauseClass() {
		return expectedThrowableCauseClass;
	}
	
	@Override
	public Test setExpectedThrowableCauseClassIsConstraintViolationException() {
		return setExpectedThrowableCauseClass(ConstraintViolationException.class);
	}

	protected void __clean__() throws Exception {
		Collection<Object> garbages = getGarbages();
		Map<Class<?>,Collection<Object>> objectBusinessIdentifiersToBeDeletedOnClean = getObjectBusinessIdentifiersToBeDeletedOnClean();
		if(objectBusinessIdentifiersToBeDeletedOnClean != null && !objectBusinessIdentifiersToBeDeletedOnClean.isEmpty()) {
			if(garbages == null)
				garbages = new ArrayList<>();
			for(Map.Entry<Class<?>,Collection<Object>> indexEntry : objectBusinessIdentifiersToBeDeletedOnClean.entrySet())
				for(Object index : indexEntry.getValue())
					garbages.add(__readOneByBusinessIdentifier__(indexEntry.getKey(),index));
		}
		if(__injectCollectionHelper__().isNotEmpty(garbages)) {
			__beginTransaction__();
			__deleteMany__(garbages);
			__endTransaction__();
		}
	}
	
	@Override
	public Throwable getThrowable() {
		return (Throwable) getProperties().getThrowable();
	}
	
	@Override
	public Test assertThrowableCauseIsInstanceOf(Class<?> aClass) {
		assertionHelper.assertTrue(__inject__(ThrowableHelper.class).getInstanceOf(getThrowable(), aClass) != null);
		return this;
	}
	
	@Override
	public Test assertThrowableIsNull() {
		Throwable throwable = getThrowable();
		//if(throwable!=null)
		//	throwable.printStackTrace();
		assertionHelper.assertTrue(throwable == null ? null : __inject__(ThrowableHelper.class).getFirstCause(throwable).getMessage(),throwable == null);
		return this;
	}
	
	@Override
	public Test assertThrowableCauseIsInstanceOfConstraintViolationException() {
		return assertThrowableCauseIsInstanceOf(ConstraintViolationException.class);
	}
	
	@Override
	public Test execute() {
		return (Test) super.execute();
	}
	
}
