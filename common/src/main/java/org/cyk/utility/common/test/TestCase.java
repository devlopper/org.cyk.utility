package org.cyk.utility.common.test;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.ArrayHelper;
import org.cyk.utility.common.helper.AssertionHelper;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.RandomHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.helper.ThrowableHelper;
import org.cyk.utility.common.helper.TimeHelper;
import org.junit.Assert;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class TestCase extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected String name;
	protected List<Object> objects;
	protected Boolean cleaned = Boolean.FALSE;
	protected Set<Class<?>> classes=new LinkedHashSet<>();
	protected Map<Class<?>,Long> countAllMap = new HashMap<>();
	protected Class<? extends java.lang.Throwable> defaultThrowableClass;
	
	public TestCase prepare(){
		addIdentifiableClasses();
		logTrace("Preparing test case {}. #Classes="+classes.size(), name,classes.size());
		countAll(classes);
		return this;
	}
	
	public void clean(){
		if(Boolean.TRUE.equals(cleaned))
			return;
		logTrace(StringUtils.repeat("#", 5)+" CLEAN "+StringUtils.repeat("#", 5));
		if(objects!=null){
			Collections.reverse(objects);
			while(!objects.isEmpty()){
				Object object = objects.iterator().next();
				Object identifier = getIdentifierWhereValueUsageTypeIsBusiness(object);
				if(identifier == null){
					identifier = getIdentifierWhereValueUsageTypeIsSystem(object);
					if(identifier != null)
						object = getByIdentifierWhereValueUsageTypeIsSystem(object);
				}else
					object = getByIdentifierWhereValueUsageTypeIsBusiness(object.getClass(),identifier);
				
				if(object != null)
					delete(object);
				
				//delete(objects.iterator().next());
			}
		}
		cleaned = Boolean.TRUE;
		assertCountAll(classes);
	}
	
	/*  Act */
	
	public <T> T act(final Constant.Action action,final T object,ThrowableHelper.Throwable expectedThrowable){
		T result = object;
		if(expectedThrowable == null) {
			Object identifier = getIdentifierWhereValueUsageTypeIsBusiness(object);
			if(identifier==null){
				
			}else{
				if( StringHelper.getInstance().isNotBlank((CharSequence) identifier) ){
					if(Constant.Action.CREATE.equals(action))
						assertNull("Object to create with code <<"+identifier+">> already exist",getByIdentifierWhereValueUsageTypeIsBusiness(object,identifier));
					else
						;//assertNotNull("Object to "+action+" with code <<"+identifier+">> not found",getByIdentifierWhereValueUsageTypeIsBusiness(object,identifier));
				}
			}
			InstanceHelper.getInstance().act(action, object);
			result = getByIdentifierWhereValueUsageTypeIsSystem(object);
    		if(Constant.Action.DELETE.equals(action)){
    			assertNull(getByIdentifierWhereValueUsageTypeIsBusiness(object,identifier));
    			remove(object);
    		}else{
    			assertNotNull(object);
    			if(Constant.Action.CREATE.equals(action))
	    			add(result);
    			else if(Constant.Action.READ.equals(action))
    				;
    		}
		}else {
			new org.cyk.utility.common.test.Try(new Runnable() {
				private static final long serialVersionUID = 1L;
				@Override
				protected void __run__() throws Throwable {
					InstanceHelper.getInstance().act(action, object);
				}
			}).setExpectedThrowable(expectedThrowable).execute();
		}
		return result;
	}
	
	public <T> T act(final Constant.Action action,final T object,Object expectedThrowableIdentifier,String expectedThrowableMessage){
		return act(action, object, new ThrowableHelper.Throwable().setIdentifier(expectedThrowableIdentifier).setMessages(expectedThrowableMessage));
	}
	
	public <T> T act(final Constant.Action action,final T object,Object expectedThrowableIdentifier){
		return act(action, object, expectedThrowableIdentifier,null);
	}
	
	public <T> T act(final Constant.Action action,final T object){
		return act(action, object,null);
	}
	
	/* Create */
	
	public <T> T create(final T object,ThrowableHelper.Throwable expectedThrowable){
		return act(Constant.Action.CREATE, object, expectedThrowable);
	}
	
	public <T> T create(final T object,Object expectedThrowableIdentifier,String expectedThrowableMessage){
		return act(Constant.Action.CREATE, object, expectedThrowableIdentifier, expectedThrowableMessage);
	}
	
	public <T> T create(final T object,Object expectedThrowableIdentifier){
		return act(Constant.Action.CREATE,object, expectedThrowableIdentifier);
	}
	
	public <T> T create(final T object){
		return act(Constant.Action.CREATE,object);
	}
	
	/* Read */
	
	public <T> T read(Class<T> aClass,Object identifier,ThrowableHelper.Throwable expectedThrowable){
		return act(Constant.Action.READ, getByIdentifierWhereValueUsageTypeIsBusiness(aClass, identifier), expectedThrowable);
	}
	
	public <T> T read(Class<T> aClass,Object identifier,Object expectedThrowableIdentifier,String expectedThrowableMessage){
		return act(Constant.Action.READ, getByIdentifierWhereValueUsageTypeIsBusiness(aClass, identifier), expectedThrowableIdentifier, expectedThrowableMessage);
	}
	
	public <T> T read(Class<T> aClass,Object identifier,Object expectedThrowableIdentifier){
		return act(Constant.Action.READ,getByIdentifierWhereValueUsageTypeIsBusiness(aClass, identifier), expectedThrowableIdentifier);
	}
	
	public <T> T read(Class<T> aClass,Object identifier){
		return act(Constant.Action.READ,getByIdentifierWhereValueUsageTypeIsBusiness(aClass, identifier,Boolean.TRUE));
	}
	
	public <T> T readByIdentifiers(Class<T> aClass,Object parentIdentifier,Object childIdentifier,Object identifiersSeparator){
		return read(aClass, Constant.Code.generate(new Object[]{parentIdentifier,childIdentifier}, identifiersSeparator));
	}
	
	public <T> T readByIdentifiers(Class<T> aClass,Object parentIdentifier,Object childIdentifier){
		return read(aClass, Constant.Code.generate(new Object[]{parentIdentifier,childIdentifier}));
	}
	
	/* Update */
	
	public <T> T update(final T object,ThrowableHelper.Throwable expectedThrowable){
		return act(Constant.Action.UPDATE, object, expectedThrowable);
	}
	
	public <T> T update(final T object,Object expectedThrowableIdentifier,String expectedThrowableMessage){
		return act(Constant.Action.UPDATE, object, expectedThrowableIdentifier, expectedThrowableMessage);
	}
	
	public <T> T update(final T object,Object expectedThrowableIdentifier){
		return act(Constant.Action.UPDATE,object, expectedThrowableIdentifier);
	}
	
	public <T> T update(final T object){
		return act(Constant.Action.UPDATE,object);
	}
	
	/* Delete */
	
	public <T> T delete(final T object,ThrowableHelper.Throwable expectedThrowable){
		return act(Constant.Action.DELETE, object, expectedThrowable);
	}
	
	public <T> T delete(final T object,Object expectedThrowableIdentifier,String expectedThrowableMessage){
		return act(Constant.Action.DELETE, object, expectedThrowableIdentifier, expectedThrowableMessage);
	}
	
	public <T> T delete(final T object,Object expectedThrowableIdentifier){
		return act(Constant.Action.DELETE,object, expectedThrowableIdentifier);
	}
	
	public <T> T delete(final T object){
		return act(Constant.Action.DELETE,object);
	}
	
	public <T> T deleteByCode(final Class<T> aClass,final String code){
		T object = (T) getByIdentifierWhereValueUsageTypeIsBusiness(aClass, code);
		assertNotNull("Object to delete not found", object);
		delete(object);
		object = (T) getByIdentifierWhereValueUsageTypeIsBusiness(aClass, code); 
		assertNull("Object not deleted", object);
		return object;
	}
	
	/**/
	
	public void add(Object object){
		if(objects==null)
			objects = new ArrayList<>();
		objects.add(object);
	}
	
	public void remove(Object object){
		if(objects!=null){
			for(int i = 0 ; i< objects.size() ; )
				if(objects.get(i).equals(object)){
					objects.remove(i);
					break;
				}else
					i++;
		}				
	}
	
	public TestCase addClasses(Class<?>...classes){
		if(classes!=null){
			Collection<Class<?>> collection = new ArrayList<>();
			for(@SuppressWarnings("rawtypes") Class aClass : classes)
				collection.add(aClass);
			addClasses(collection);
		}
		return this;
	}
	
	public TestCase addClasses(Collection<Class<?>> classes){
		this.classes.addAll(classes);
		return this;
	}
	
	public TestCase addIdentifiableClasses(){		
		addClasses(ClassHelper.getInstance().getAnnotatedWithEntity());	
		return this;
	}
	
	/* Count */
	
	protected Long getCountAll(Class<?> aClass){
		return InstanceHelper.getInstance().count(aClass);
	}
	
	public void countAll(Collection<Class<?>> classes){
		for(@SuppressWarnings("rawtypes") Class aClass : classes){
			countAllMap.put((Class<?>) aClass, getCountAll(aClass));	
		}
	}
	
	public TestCase countAll(Class<?>...classes){
		if(classes!=null)
			countAll(Arrays.asList(classes));
		return this;
	}
	
	public TestCase assertCountAll(@SuppressWarnings("rawtypes") Class aClass,Integer increment){
		assertEquals(aClass.getSimpleName()+" count all is not correct", new Long(commonUtils.getValueIfNotNullElseDefault(countAllMap.get(aClass),0l)+increment)
				, getCountAll(aClass));
		return this;
	}
	
	public TestCase assertCountAll(Collection<Class<?>> classes){
		for(Class<?> aClass : classes)
			assertCountAll(aClass,0);
		return this;
	}
	
	public TestCase assertCountAll(Class<?>...classes){
		if(classes!=null)
			assertCountAll(Arrays.asList(classes));
		return this;
	}
	
	/*public void assertBigDecimalEquals(String message, BigDecimal expected, BigDecimal actual) {
		assertEquals(message, formatBigDecimal(expected),formatBigDecimal(actual));
	}*/
	
	public String formatBigDecimal(BigDecimal value) {
		return value == null ? null : value.toString();
	}
	
	public void assertBigDecimalValue(String message,String expected,BigDecimal value){
    	Assert.assertEquals(message,new BigDecimal(expected).doubleValue()+"", value.doubleValue()+"");
    }
	
	public void assertThrowable(final java.lang.Runnable runnable,Class<? extends java.lang.Throwable> expectedClass,Object expectedIdentifier,String expectedMessage){
		new Try(runnable).__set__(expectedIdentifier,expectedClass,expectedMessage).execute();	
	}
	
	public void assertThrowable(final java.lang.Runnable runnable,Object expectedIdentifier,String expectedMessage){
		new Try(runnable).__set__(expectedIdentifier,defaultThrowableClass,expectedMessage).execute();	
	}
	
	/**/
	
	public <T> T instanciateOne(Class<T> aClass){
		return ClassHelper.getInstance().instanciateOne(aClass);
	}
	
	public <T> T instanciateOne(Class<T> aClass,Object identifier){
		return ClassHelper.getInstance().instanciateOne(aClass,identifier);
	}
	
	public <T> T instanciateOneWithRandomIdentifier(Class<T> aClass){
		Class<?> identifierClass = FieldHelper.getInstance().getType(aClass, ClassHelper.getInstance().getIdentifierFieldName(aClass));
		Object identifier = null;
		if(String.class.equals(identifierClass))
			identifier = getRandomHelper().getAlphabetic(5);
		return ClassHelper.getInstance().instanciateOne(aClass,identifier);
	}
	
	/**/
	
	public TestCase computeChanges(Object instance) {
		InstanceHelper.getInstance().computeChanges(instance);
		return this;
	}
	
	/* shortcuts */
	
	public RandomHelper getRandomHelper(){
		return RandomHelper.getInstance();
	}
	
	public String getRandomAlphabetic(Integer length){
		return getRandomHelper().getAlphabetic(length);
	}
	
	public String getRandomAlphabetic(){
		return getRandomAlphabetic(5);
	}

	public Date getTimeAfterNow(Object numberOfMillisecond){
		return TimeHelper.getInstance().getAfterNow(numberOfMillisecond);
	}
	
	public Date getTimeAfterNowByNumberOfMinute(Object numberOfMinutes){
		return TimeHelper.getInstance().getAfterNowByNumberOfMinute(numberOfMinutes);
	}
	
	public Object getIdentifierWhereValueUsageTypeIsSystem(Object instance) {
		return InstanceHelper.getInstance().getIdentifierWhereValueUsageTypeIsSystem(instance);
	}
	
	public Object getIdentifierWhereValueUsageTypeIsBusiness(Object instance) {
		return InstanceHelper.getInstance().getIdentifierWhereValueUsageTypeIsBusiness(instance);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getByIdentifierWhereValueUsageTypeIsSystem(T object){
		Object identifier = getIdentifierWhereValueUsageTypeIsSystem(object);
		return (T) InstanceHelper.getInstance().getByIdentifier(object.getClass(), identifier, ClassHelper.Listener.IdentifierType.SYSTEM);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getByIdentifierWhereValueUsageTypeIsBusiness(T object,Object identifier){
		return (T) InstanceHelper.getInstance().getByIdentifier(object.getClass(), identifier, ClassHelper.Listener.IdentifierType.BUSINESS);
	}
	
	public <T> T getByIdentifierWhereValueUsageTypeIsBusiness(T object){
		return getByIdentifierWhereValueUsageTypeIsBusiness(object,getIdentifierWhereValueUsageTypeIsBusiness(object));
	}
	
	public <T> T getByIdentifierWhereValueUsageTypeIsBusiness(Class<T> aClass,Object identifier,Boolean assertNotNull){
		T instance = (T) InstanceHelper.getInstance().getByIdentifier(aClass, identifier, ClassHelper.Listener.IdentifierType.BUSINESS);
		if(assertNotNull == null)
			assertNotNull = Boolean.FALSE;
		if(assertNotNull)
			assertNotNull("object with business identifier "+identifier+" is null",instance);
		return instance;
	}
	
	public <T> T getByIdentifierWhereValueUsageTypeIsBusiness(Class<T> aClass,Object identifier){
		return getByIdentifierWhereValueUsageTypeIsBusiness(aClass, identifier, Boolean.FALSE);
	}
	
	/**/
	
	public void addObject(Object object){
		if(objects==null)
			objects = new ArrayList<>();
		objects.add(object);
	}
	
	public void removeObject(Object object){
		if(objects!=null){
			for(int i = 0 ; i< objects.size() ; )
				if(objects.get(i).equals(object)){
					objects.remove(i);
					break;
				}else
					i++;
		}			
	}
	
	public TestCase deleteAll(Collection<Class<?>> classes){
		new CollectionHelper.Iterator.Adapter.Default<Class<?>>((Collection<Class<?>>) classes){
			private static final long serialVersionUID = 1L;
			protected void __executeForEach__(java.lang.Class<?> aClass) {
				Collection<?> identifiables = InstanceHelper.getInstance().get(aClass);
				deleteInstances(identifiables);
				//inject(GenericBusiness.class).delete(identifiables);
			}
		}.execute();
		return this;
	}
	
	public void deleteInstances(Collection<?> instances){
		
	}
	
	public TestCase deleteAll(Class<?>...classes){
		if(ArrayHelper.getInstance().isNotEmpty(classes))
			deleteAll(Arrays.asList(classes));
		return this;
	}
	
	/* Assertions */
	
	public AssertionHelper getAssertionHelper(){
		return AssertionHelper.getInstance();
	}

	public TestCase assertNull(String message, Object object) {
		getAssertionHelper().assertNull(message, object);
		return this;
	}
	
	public TestCase assertNull(Object object) {
		assertNull("object is not null", object);
		return this;
	}
	
	public TestCase assertNotNull(String message, Object object) {
		getAssertionHelper().assertNotNull(message, object);
		return this;
	}
	
	public TestCase assertNotNull(Object object) {
		assertNotNull("object is null", object);
		return this;
	}
	
	public TestCase assertTrue(String message, Boolean condition) {
		getAssertionHelper().assertTrue(message, condition);
		return this;
	}
	
	public TestCase assertTrue(Boolean condition) {
		assertTrue("it is false", condition);
		return this;
	}
	
	public TestCase assertFalse(String message, Boolean condition) {
		getAssertionHelper().assertFalse(message, condition);
		return this;
	}
	
	public TestCase assertFalse(Boolean condition) {
		assertTrue("it is true", condition);
		return this;
	}
	
	public TestCase assertEquals(String message, Object expected,Object actual) {
		getAssertionHelper().assertEquals(message, expected,actual);
		return this;
	}
	
	public TestCase assertEquals(Object expected,Object actual) {
		getAssertionHelper().assertEquals(expected,actual);
		return this;
	}
	
	public TestCase assertNotEquals(String message, Object expected,Object actual) {
		getAssertionHelper().assertNotEquals(message, expected,actual);
		return this;
	}
	
	public TestCase assertNotEquals(Object expected,Object actual) {
		getAssertionHelper().assertNotEquals(expected,actual);
		return this;
	}
	
	public TestCase assertEqualsNumber(String message, Object expected,Object actual) {
		getAssertionHelper().assertEqualsNumber(message, expected,actual);
		return this;
	}
	
	public TestCase assertEqualsNumber(Object expected,Object actual) {
		getAssertionHelper().assertEqualsNumber(expected,actual);
		return this;
	}
	
	public TestCase assertEqualsByFieldValue(Object expected,Object actual,String...fieldNames) {
		getAssertionHelper().assertEqualsByFieldValue(expected,actual,fieldNames);
		return this;
	}
	
	public TestCase assertEqualsFieldValues(Object object,FieldHelper.Field.Value.Collection expectedFieldValues){
		getAssertionHelper().assertEqualsFieldValues(object, expectedFieldValues);
		return this;
	}
	
	public TestCase assertEqualsOrderNumberByBusinessIdentifier(Class<?> aClass,Object identifier,Object orderNumber){
		Object instance = getByIdentifierWhereValueUsageTypeIsBusiness(aClass, identifier, Boolean.TRUE);
		assertEqualsNumber("order number are not equal", orderNumber, InstanceHelper.getInstance().getOrderNumberWhereValueUsageTypeIsBusiness(instance));
		return this;
	}
	
	public TestCase assertEqualsCreationOrderNumberByBusinessIdentifier(Class<?> aClass,Object identifier,Object creationOrderNumber){
		Object instance = getByIdentifierWhereValueUsageTypeIsBusiness(aClass, identifier, Boolean.TRUE);
		assertEqualsNumber("creation order number are not equal", creationOrderNumber, InstanceHelper.getInstance().getOrderNumberWhereValueUsageTypeIsSystem(instance));
		return this;
	}
	
	public TestCase assertEqualsOrderNumbersByBusinessIdentifier(Class<?> aClass,Object identifier,Object creationOrderNumber,Object orderNumber){
		assertEqualsCreationOrderNumberByBusinessIdentifier(aClass, identifier, creationOrderNumber);
		assertEqualsOrderNumberByBusinessIdentifier(aClass, identifier, orderNumber);
		return this;
	}
	
	public TestCase assertNullByBusinessIdentifier(Class<?> aClass,Collection<Object> identifiers){
		if(CollectionHelper.getInstance().isNotEmpty(identifiers))
			for(Object identifier : identifiers)
				assertNull(aClass+" with business identifier "+identifier+" is not null", getByIdentifierWhereValueUsageTypeIsBusiness(aClass, identifier));
		return this;
	}
	
	public TestCase assertNullByBusinessIdentifier(Class<?> aClass,Object...identifiers){
		if(ArrayHelper.getInstance().isNotEmpty(identifiers))
			assertNullByBusinessIdentifier(aClass, Arrays.asList(identifiers));
		return this;
	}
	
	public TestCase assertNotNullByBusinessIdentifier(Class<?> aClass,Collection<Object> identifiers){
		if(CollectionHelper.getInstance().isNotEmpty(identifiers))
			for(Object identifier : identifiers)
				assertNotNull(aClass+" with business identifier "+identifier+" is null", getByIdentifierWhereValueUsageTypeIsBusiness(aClass, identifier));
		return this;
	}
	
	public TestCase assertNotNullByBusinessIdentifier(Class<?> aClass,Object...identifiers){
		if(ArrayHelper.getInstance().isNotEmpty(identifiers))
			assertNotNullByBusinessIdentifier(aClass, Arrays.asList(identifiers));
		return this;
	}
	
	public void assertCollection(Collection<?> expected,Collection<?> actual){
		if(expected == null){
			assertNull(actual);
		}if(actual == null){
			assertNull(expected);
		}else {
			assertEquals("collection size not equals", CollectionHelper.getInstance().getSize(expected), CollectionHelper.getInstance().getSize(actual));
			if(actual!=null)
				for(Integer index = 0 ; index < actual.size() ; index++){
					assertEquals("element at position "+index+" not equals", CollectionHelper.getInstance().getElementAt(expected, index), CollectionHelper.getInstance().getElementAt(actual, index));
				}	
		}
	}
	
	public void assertFieldValueEquals(Class<?> aClass,String code,Object...objects){
		assertFieldValueEquals(read(aClass, code), objects);
	}
	
	public void assertFieldValueEquals(Object instance,Object...objects){
		if(ArrayHelper.getInstance().isNotEmpty(objects)){
			for(Integer index = 0 ; index < objects.length - 2; index = index + 2){
				assertEquals("not equal", objects[index+1], FieldHelper.getInstance().read(instance, (String)objects[index]));
			}
		}
	}
}