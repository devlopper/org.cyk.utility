package org.cyk.utility.test;

import java.util.Collection;
import java.util.Map;

import org.cyk.utility.function.FunctionWithPropertiesAsInputAndVoidAsOutput;

public interface Test extends FunctionWithPropertiesAsInputAndVoidAsOutput {

	String getName();
	Test setName(String name);
	
	/*
	 * Setup
	 */
	
	Test setObjectsToBeCreated(Collection<Object> objects);
	Collection<Object> getObjectsToBeCreated();
	Test addObjectsToBeCreatedCollection(Collection<Object> objects);
	Test addObjectsToBeCreatedArray(Object...objects);
	
	Test setObjectBusinessIdentifiersToBeDeletedOnClean(Map<Class<?>,Collection<Object>> map);
	Map<Class<?>,Collection<Object>> getObjectBusinessIdentifiersToBeDeletedOnClean();
	Test addObjectBusinessIdentifiersToBeDeletedOnCleanCollection(Class<?> aClass,Collection<Object> identifiers);
	Test addObjectBusinessIdentifiersToBeDeletedOnCleanArray(Class<?> aClass,Object...identifiers);
	
	/*
	 * Running
	 */
	

	/* 
	 * Results 
	 */
	
	Throwable getThrowable();
	
	/* 
	 * Assertions 
	 */

	Test setExpectedThrowableCauseClass(Class<? extends Throwable> aClass);
	Class<? extends Throwable> getExpectedThrowableCauseClass();
	Test setExpectedThrowableCauseClassIsConstraintViolationException();
	
	Test assertThrowableCauseIsInstanceOf(Class<?> aClass);
	Test assertThrowableIsNull();
	
	Test assertThrowableCauseIsInstanceOfConstraintViolationException();

	/*
	 * Cleanup
	 */
	Test setGarbages(Collection<Object> objects);
	Collection<Object> getGarbages();
	Test addGarbagesCollection(Collection<Object> objects);
	Test addGarbagesArray(Object...objects);
	
	Test setNotGarbagable(Collection<Object> objects);
	Collection<Object> getNotGarbagable();
	Test addNotGarbagableCollection(Collection<Object> objects);
	Test addNotGarbagableArray(Object...objects);
	
	/**/
	
	@Override Test execute();
	
}
