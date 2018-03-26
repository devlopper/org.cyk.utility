package org.cyk.utility.common.test;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.AssertionHelper;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.RandomHelper;
import org.cyk.utility.common.helper.TimeHelper;
import org.hamcrest.MatcherAssert;
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
	
	/* Assertions */
	
	public void assertThat(String reason,Boolean assertion){
		MatcherAssert.assertThat(reason, assertion);
	}
	
	public void assertEquals(String message, Object expected, Object actual){
		Assert.assertEquals(message, expected, actual);
	}
	
	public void assertEquals(Object expected, Object actual){
		Assert.assertEquals(expected, actual);
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
	
	/*public <T> T act(final Constant.Action action,final T object,Object expectedThrowableIdentifier,String expectedThrowableMessage){
		if(expectedThrowableMessage!=null){
    		new Try(new Runnable() {
				private static final long serialVersionUID = 1L;
				@Override
				protected void __run__() throws Throwable {
					InstanceHelper.getInstance().act(action, object);
				}
			}).setExpectedThrowableIdentifier(expectedThrowableIdentifier).setExpectedThrowableMessage(expectedThrowableMessage).execute();
    	}else{
    		InstanceHelper.getInstance().getByIdentifier(object.getClass(), InstanceHelper.getInstance().getFieldValue(object, ClassHelper.Listener.FieldName.IDENTIFIER
    				, ClassHelper.Listener.FieldName.ValueUsageType.SYSTEM), ClassHelper.Listener.IdentifierType.SYSTEM);
    		//assertThat("Created", inject(PersistenceInterfaceLocator.class).injectTypedByObject(identifiable).read(identifiable.getIdentifier())!=null);
    	}
		return object;
	}
	
	public <T> T create(final T object,Object expectedThrowableIdentifier,String expectedThrowableMessage){
		return act(Constant.Action.CREATE, object,expectedThrowableIdentifier, expectedThrowableMessage);
	}
	public <T> T create(final T identifiable){
		return create(identifiable,null, null);
	}
	
	public <T> T read(final Class<T> aClass,final String code,Object expectedThrowableIdentifier,String expectedThrowableMessage){
		T read = null;
		if(expectedThrowableMessage!=null){
    		new Try(expectedThrowableMessage){ 
    			private static final long serialVersionUID = -8176804174113453706L;
    			@Override protected void code() {inject(PersistenceInterfaceLocator.class).injectTyped(aClass).read(code);}
    		}.execute();
    	}else{
    		read = inject(PersistenceInterfaceLocator.class).injectTyped(aClass).read(code);
    		assertThat("Read code "+code+" is null", read!=null);
    	}
		return read;
		
		return null;
	}
	public <T> T read(final Class<T> aClass,final String code){
		return read(aClass,code,null, null);
	}
	
	public <T> T update(final T identifiable,Object expectedThrowableIdentifier,String expectedThrowableMessage){
		if(expectedThrowableMessage!=null){
    		new Try(expectedThrowableMessage){ 
    			private static final long serialVersionUID = -8176804174113453706L;
    			@Override protected void code() {inject(GenericBusiness.class).update(identifiable);}
    		}.execute();
    	}else{
    		inject(GenericBusiness.class).update(identifiable);
    		assertThat("Updated", inject(PersistenceInterfaceLocator.class).injectTypedByObject(identifiable).read(identifiable.getIdentifier())!=null);
    	}
		return identifiable;
		
		return null;
	}
	public <T> T update(T identifiable){
		return (T) update(identifiable,null,null);
	}
	
	public <T> T delete(final T identifiable,Object expectedThrowableIdentifier,String expectedThrowableMessage){
		if(expectedThrowableMessage!=null){
    		new Try(expectedThrowableMessage){ 
    			private static final long serialVersionUID = -8176804174113453706L;
    			@Override protected void code() {inject(GenericBusiness.class).delete(identifiable);}
    		}.execute();
    	}else{
    		inject(GenericBusiness.class).delete(identifiable);
    		assertThat("Deleted", inject(PersistenceInterfaceLocator.class).injectTypedByObject(identifiable).read(identifiable.getIdentifier())==null);
    	}
		return identifiable;
		
		return null;
	}
	public <T> T delete(T identifiable){
		return delete(identifiable,null,null);
	}
	public <T> T delete(Class<T> aClass,String code,Object expectedThrowableIdentifier,String expectedThrowableMessage){
		T identifiable = inject(PersistenceInterfaceLocator.class).injectTyped(aClass).read(code);
		assertThat("Found "+aClass.getSimpleName()+" with code "+code+" to delete", identifiable!=null);
		return delete(identifiable,expectedThrowableMessage);
		
		return null;
	}
	public <T> T delete(Class<T> aClass,String code){
		return delete(aClass,code,null,null);
	}*/
	
	
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
	
	/* Assertions */
	
	public AssertionHelper getAssertionHelper(){
		return AssertionHelper.getInstance();
	}

	public void assertNull(String message, Object object) {
		getAssertionHelper().assertNull(message, object);
	}
	
	public void assertNull(Object object) {
		assertNull("object is null", object);
	}
	
	public void assertNotNull(String message, Object object) {
		getAssertionHelper().assertNotNull(message, object);
	}
	
	public void assertNotNull(Object object) {
		assertNotNull("object is not null", object);
	}
}