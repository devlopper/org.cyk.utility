package org.cyk.utility.object;

import java.util.Arrays;

import org.cyk.utility.__kernel__.annotation.Json;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class ObjectFromStringBuilderUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void objectFieldAreNull_json_stringOnlyClosures(){
		Class object = buildObjectFromStringUsingJson("{}", Class.class,"field01");
		assertionHelper.assertNotNull(object);
		assertionHelper.assertNull(object.getField01());
		assertionHelper.assertNull(object.getField02());
		assertionHelper.assertNull(object.getField03());
	}
	
	@Test
	public void objectField01IsNotNull_json_stringField01Is1(){
		Class object = buildObjectFromStringUsingJson("{\"field01\":\"1\"}", Class.class,"field01");
		assertionHelper.assertNotNull(object);
		assertionHelper.assertEquals("1",object.getField01());
	}
	
	/*
	@Test
	public void stringify_json_field01IsNotNull(){
		assertionHelper.assertEquals("{\"field01\":\"abc\"}",buildObjectFromStringUsingJson(new Class().setField01("abc"), "field01"));
	}
	
	@Test
	public void stringify_json_field01IsNotNull_field02IsNotNull(){
		assertionHelper.assertEquals("{\"field01\":\"abc\",\"field02\":\"159\"}",buildObjectFromStringUsingJson(new Class().setField01("abc").setField02("159")
				, "field01","field02"));
	}
	*/
	/**/
	
	private static <T> T buildObjectFromStringUsingJson(String string,java.lang.Class<T> klass,String...fieldNamesStrings) {
		ObjectFromStringBuilder objectFromStringBuilder = __injectByQualifiersClasses__(ObjectFromStringBuilder.class,Json.Class.class);
		objectFromStringBuilder.setString(string);
		objectFromStringBuilder.setKlass(klass);
		objectFromStringBuilder.addFieldNamesStrings(fieldNamesStrings);
		return (T) objectFromStringBuilder.execute().getOutput();
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Class {
		private String field01;
		private String field02;
		private String field03;
	}
}
