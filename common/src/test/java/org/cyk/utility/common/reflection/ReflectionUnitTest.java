package org.cyk.utility.common.reflection;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.model.Identifiable;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Assert;
import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;


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
		SubTypesScanner subTypesScanner = new SubTypesScanner(false);
		
		ConfigurationBuilder configurationBuilder = new ConfigurationBuilder()
		    	.filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix("org.cyk")))
		    	.setUrls(ClasspathHelper.forPackage("org.cyk"))
		    	.setScanners(subTypesScanner);
		
		assertEquals(new Reflections(configurationBuilder).getSubTypesOf(Identifiable.class).size(), ClassHelper.getInstance().get("org.cyk",Identifiable.class).size());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
