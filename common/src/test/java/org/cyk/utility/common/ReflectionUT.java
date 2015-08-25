package org.cyk.utility.common;

import java.lang.reflect.InvocationTargetException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;


public class ReflectionUT extends AbstractUnitTest {

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
	
	@Getter @Setter @NoArgsConstructor @AllArgsConstructor
	private static class ClassA{
		private String vA;
	}
	
	@Getter @Setter @NoArgsConstructor @AllArgsConstructor
	private static class ClassA1 extends ClassA {
		private String vA1;
	}
	
	@Getter @Setter @NoArgsConstructor @AllArgsConstructor
	private static class ClassA2 extends ClassA{
		private String vA2;
	}
	
	@Getter @Setter @NoArgsConstructor @AllArgsConstructor
	private static class ClassB{
		private ClassA v;
	}
	
	@Getter @Setter @NoArgsConstructor @AllArgsConstructor
	private static class ClassC{
		private ClassA1 v;
	}
}
