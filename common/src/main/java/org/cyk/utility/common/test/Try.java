package org.cyk.utility.common.test;

import java.io.Serializable;

import org.cyk.utility.common.helper.AssertionHelper;
import org.cyk.utility.common.helper.FieldHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Try implements Serializable{
	private static final long serialVersionUID = -4483490165697187680L;
	
	private java.lang.Runnable runnable;
	
	private Object expectedThrowableIdentifier;
	private Class<? extends Throwable> expectedThrowableClass;
	private String expectedThrowableMessage;
	
	public Try(java.lang.Runnable runnable) {
		super();
		this.runnable = runnable;
	}
	
	public Try __set__(Object expectedThrowableIdentifier,Class<? extends Throwable> expectedThrowableClass,String expectedThrowableMessage) {
		this.expectedThrowableIdentifier = expectedThrowableIdentifier;
		this.expectedThrowableClass = expectedThrowableClass;
		this.expectedThrowableMessage = expectedThrowableMessage;
		return this;
	}
	
	public void execute(){
		Throwable expectedThrowable = null;
		try { 
			code();
		} catch (Throwable throwable) {
			expectedThrowable = expectedThrowableClass == null ? throwable : getInstanceOf(throwable,expectedThrowableClass);
		}  finally {
			AssertionHelper.getInstance().assertEquals("expected throwable is null", Boolean.TRUE, expectedThrowable!=null);
			if(expectedThrowableClass!=null)
				AssertionHelper.getInstance().assertEquals("Throwable class is not equal",expectedThrowableClass,expectedThrowable.getClass());
			if(expectedThrowableIdentifier!=null)
				AssertionHelper.getInstance().assertEquals("Throwable identifier is not equal",expectedThrowableIdentifier,FieldHelper.getInstance().read(expectedThrowable, "fields.identifier")); 
			if(expectedThrowableMessage!=null)
				AssertionHelper.getInstance().assertEquals("Throwable message is not equal",expectedThrowableMessage,expectedThrowable.getMessage());
		}
	}
	
	protected void code() throws Throwable {
		runnable.run();	
	}
	
	public java.lang.Throwable getInstanceOf(java.lang.Throwable throwable,Class<?> aClass){
		java.lang.Throwable index = throwable;
		while(index!=null){
			if(aClass.isAssignableFrom(index.getClass())){
				return index;
			}else
				index = index.getCause();
		}
		return null;
	}

	/**/

}