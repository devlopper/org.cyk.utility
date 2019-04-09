package org.cyk.utility.object;

import org.cyk.utility.__kernel__.annotation.Json;
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
	
	/**/
	
	private static String buildStringFromObjectUsingJson(Object object,String...fieldNamesStrings) {
		ObjectToStringBuilder objectToStringBuilder = __injectByQualifiersClasses__(ObjectToStringBuilder.class,Json.Class.class);
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
	}
}
