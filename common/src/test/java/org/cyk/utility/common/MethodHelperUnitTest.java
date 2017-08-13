package org.cyk.utility.common;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cyk.utility.common.helper.AssertionHelper;
import org.cyk.utility.common.helper.MethodHelper;
import org.cyk.utility.common.helper.MethodHelper.Method.Parameter;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class MethodHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@Test
	public void doTest1(){
		assertEquals("doTest1",MethodHelper.getInstance().getCurrentNameFromStackTrace());
	}
	
	@Test
	public void callGet(){
		AssertionHelper.getInstance().assertEquals(null, MethodHelper.getInstance().callGet(new A(), String.class, "f1"));
		AssertionHelper.getInstance().assertEquals("hello", MethodHelper.getInstance().callGet(new A().setF1("hello"), String.class, "f1"));
	}
	
	@Test
	public void callSet(){
		AssertionHelper.getInstance().assertEquals("hello", ((A)MethodHelper.getInstance().callSet(new A(),"f1", String.class, "hello")).getF1());
	}
	
	@Test
	public void call(){
		AssertionHelper.getInstance().assertEquals("Hi komenan!", MethodHelper.getInstance().call(new A(), String.class, "hi"
				, MethodHelper.Method.Parameter.buildArray(String.class,"komenan")));
		AssertionHelper.getInstance().assertEquals("Hi null!", MethodHelper.getInstance().call(new A(), String.class, "hi"
				, MethodHelper.Method.Parameter.buildArray(String.class,null)));
	}
	
	@Test
	public void callBack(){
		MethodHelper.Method.CallBack callBack = new MethodHelper.Method.CallBack() {
			
			@Override
			public <T> Boolean isExecutable(Object instance, Class<T> resultClass, String name, Parameter[] parameters,T result) {
				return result == null;
			}
			
			@SuppressWarnings("unchecked")
			@Override
			public <T> T execute(Object instance, Class<T> resultClass, String name, Parameter[] parameters, T result) {
				if("getF1".equals(name))
					return (T) MethodHelper.getInstance().call(instance, String.class, "getF11",this);
				if("getF11".equals(name))
					return (T) MethodHelper.getInstance().call(instance, String.class, "getF111",this);
				return null;
			}
		};

		AssertionHelper.getInstance().assertEquals(null, MethodHelper.getInstance().call(new A(), String.class, "getF1",callBack));
		AssertionHelper.getInstance().assertEquals("C", MethodHelper.getInstance().call(new A().setF111("C"), String.class, "getF1",callBack));
		AssertionHelper.getInstance().assertEquals("B", MethodHelper.getInstance().call(new A().setF11("B"), String.class, "getF1",callBack));
		AssertionHelper.getInstance().assertEquals("A", MethodHelper.getInstance().call(new A().setF1("A"), String.class, "getF1",callBack));
		
		AssertionHelper.getInstance().assertEquals("A", MethodHelper.getInstance().call(new A().setF1("A").setF11("B").setF111("C"), String.class, "getF1",callBack));
		AssertionHelper.getInstance().assertEquals("B", MethodHelper.getInstance().call(new A().setF11("B").setF111("C"), String.class, "getF1",callBack));
		AssertionHelper.getInstance().assertEquals("A", MethodHelper.getInstance().call(new A().setF1("A").setF111("C"), String.class, "getF1",callBack));
		AssertionHelper.getInstance().assertEquals("A", MethodHelper.getInstance().call(new A().setF1("A").setF11("B"), String.class, "getF1",callBack));
		AssertionHelper.getInstance().assertEquals("C", MethodHelper.getInstance().call(new A().setF111("C"), String.class, "getF1",callBack));
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class A implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		@NotNull private String f1,f11,f111;
		@Digits(integer=2,fraction=2) private Integer f2;
		private double f3;
		private Boolean f4;
		private Date f5;
		
		public String hi(String name){
			return "Hi "+name+"!";
		}
		
		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
		}
		
	}
}
