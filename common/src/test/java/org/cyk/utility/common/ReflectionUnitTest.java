package org.cyk.utility.common;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Assert;
import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class ReflectionUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@Test
	public void jdkConstructor(){
		try {
			ClassB.class.getConstructor(ClassA.class).newInstance(new ClassA());
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Test(expected=RuntimeException.class)
	public void jdkConstructorSubType(){
		try {
			ClassB.class.getConstructor(ClassA1.class).newInstance(new ClassA1());
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Test
	public void apacheUtilsConstructor(){
		try {
			ConstructorUtils.getMatchingAccessibleConstructor(ClassB.class,ClassA.class).newInstance(new ClassA());
		} catch (IllegalAccessException
				| InvocationTargetException | InstantiationException e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	public void apacheUtilsConstructorSubType(){
		try {
			ConstructorUtils.getMatchingAccessibleConstructor(ClassB.class,ClassA1.class).newInstance(new ClassA());
		} catch (IllegalAccessException
				| InvocationTargetException | InstantiationException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void apacheUtilsPropertyGet(){
		ClassB b = new ClassB();
		b.setV(new ClassA());
		try {
			PropertyUtils.setProperty(b, "v.v_a", "AutoA");
			Assert.assertEquals("AutoA", PropertyUtils.getProperty(b, "v.v_a"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Test
	public void getReflections(){
		Reflections reflections = new Reflections(new ConfigurationBuilder()
	    	.filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix("org.cyk")))
	    	.setUrls(ClasspathHelper.forPackage("org.cyk"))
	    	.setScanners(new SubTypesScanner(){
	    		@Override
	    		public boolean acceptsInput(String file) {
	    			System.out.println(file);
	    			return super.acceptsInput(file);
	    		}
	    	}));
	 
	       Set<Class<? extends ClassA>> modules = reflections.getSubTypesOf(ClassA.class);
	       assertEquals(6, modules.size());
	}
	
	@Getter @Setter @NoArgsConstructor @AllArgsConstructor
	public static class ClassA{
		private String v_a;
	}
	
	@Getter @Setter @NoArgsConstructor @AllArgsConstructor
	public static class ClassA1 extends ClassA {
		private String vA1;
	}
	
	@Getter @Setter
	public static class ClassA11 extends ClassA1 {
		
	}
	
	@Getter @Setter @NoArgsConstructor @AllArgsConstructor
	public static class ClassA2 extends ClassA{
		private String vA2;
	}
	
	@Getter @Setter
	public static class ClassA21 extends ClassA2 {
		
	}
	
	@Getter @Setter
	public static class ClassA22 extends ClassA2 {
		
	}
	
	@Getter @Setter
	public static class ClassA221 extends ClassA22 {
		
	}
	
	@Getter @Setter @NoArgsConstructor @AllArgsConstructor
	public static class ClassB{
		private ClassA v;
	}
	
	@Getter @Setter @NoArgsConstructor @AllArgsConstructor
	public static class ClassC{
		private ClassA1 v;
	}
}
