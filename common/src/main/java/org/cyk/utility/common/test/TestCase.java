package org.cyk.utility.common.test;

import java.io.Serializable;
import java.util.Date;

import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.RandomHelper;
import org.cyk.utility.common.helper.TimeHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class TestCase extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected Class<? extends java.lang.Throwable> defaultThrowableClass;
	
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
	
	public TestCase computeChanges(Object instance) {
		InstanceHelper.getInstance().computeChanges(instance);
		return this;
	}
	
	/* shortcuts */
	
	public RandomHelper getRandomHelper(){
		return RandomHelper.getInstance();
	}

	public Date getTimeAfterNow(Object numberOfMillisecond){
		return TimeHelper.getInstance().getAfterNow(numberOfMillisecond);
	}
	
	public Date getTimeAfterNowByNumberOfMinute(Object numberOfMinutes){
		return TimeHelper.getInstance().getAfterNowByNumberOfMinute(numberOfMinutes);
	}
	
	/**/
	

}