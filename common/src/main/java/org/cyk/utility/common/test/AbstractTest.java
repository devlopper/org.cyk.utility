package org.cyk.utility.common.test;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cyk.utility.common.AbstractMethod;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;


public abstract class AbstractTest implements Serializable {
	
	private static final long serialVersionUID = -4375668358714913342L;
	
	//protected Collection<AbstractTestMethod> methods = new ArrayList<>();
	
	protected static AbstractMethod<Object, Object> AFTER_CLASS_METHOD;
	
	@Before
	public final void before() throws Exception {
		_before_();
		_methods_();
	}
	
	protected void _before_() throws Exception{}
	
	protected void _methods_(){}
	
	@After
	public final void after() throws Exception {_after_();}
	protected void _after_() throws Exception {}
	
	@AfterClass
	public static final void afterClass() throws Exception {
		if(AFTER_CLASS_METHOD!=null)
			AFTER_CLASS_METHOD.execute();
	}
	/**/
	
	@Test
	public final void execute(){
		_execute_();
	}
	
	protected void _execute_(){
		/*for(AbstractTestMethod method : methods)
			method.execute();*/
	}
	
	/**/
	
	protected void debug(Object object){
		System.out.println("------------------------------------- Debug -----------------------------");
		System.out.println(ToStringBuilder.reflectionToString(object, ToStringStyle.MULTI_LINE_STYLE));
	}
	
}
