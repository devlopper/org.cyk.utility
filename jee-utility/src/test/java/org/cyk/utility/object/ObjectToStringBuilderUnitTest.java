package org.cyk.utility.object;

import org.cyk.utility.__kernel__.annotation.JavaScriptObjectNotation;
import org.cyk.utility.network.message.Receiver;
import org.cyk.utility.network.message.Receivers;
import org.cyk.utility.string.Strings;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class ObjectToStringBuilderUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void stringify_json_field01IsNull(){
		assertionHelper.assertEquals("{}",buildStringFromObjectUsingJson(new Class(), "field01"));
	}
	
	@Test
	public void stringify_json_field01IsNotNull(){
		assertionHelper.assertEquals("{\"field01\":\"abc\"}",buildStringFromObjectUsingJson(new Class().setField01("abc"), "field01"));
	}
	
	@Test
	public void stringify_json_field01IsNotNull_field02IsNotNull(){
		assertionHelper.assertEquals("{\"field01\":\"abc\",\"field02\":\"159\"}",buildStringFromObjectUsingJson(new Class().setField01("abc").setField02("159")
				, "field01","field02"));
	}
	
	@Test
	public void stringify_json_field04IsNotNull(){
		assertionHelper.assertEquals("{\"field04\":\"abc,2,hello world\"}",buildStringFromObjectUsingJson(new Class()
				.setField04(__inject__(Strings.class).add("abc","2","hello world")), "field04"));
	}
	
	@Test
	public void stringify_json_field05IsNotNull(){
		assertionHelper.assertEquals("{\"field05\":\"r01,k@mail.com\"}",buildStringFromObjectUsingJson(new Class()
				.setField05(__inject__(Receivers.class).add(__inject__(Receiver.class).setIdentifier("r01")
						,__inject__(Receiver.class).setIdentifier("k@mail.com"))), "field05"));
	}
	
	/**/
	
	private static String buildStringFromObjectUsingJson(Object object,String...fieldNamesStrings) {
		ObjectToStringBuilder objectToStringBuilder = __injectByQualifiersClasses__(ObjectToStringBuilder.class,JavaScriptObjectNotation.Class.class);
		objectToStringBuilder.setObject(object);
		objectToStringBuilder.addFieldNamesStrings(fieldNamesStrings);
		return objectToStringBuilder.execute().getOutput();
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Class {
		private String field01;
		private String field02;
		private String field03;
		private Strings field04;
		private Receivers field05;
	}
	
	
}
