package org.cyk.utility.__kernel__.klass;

import static org.assertj.core.api.Assertions.assertThat;
import static org.cyk.utility.__kernel__.klass.ClassHelper.filter;
import static org.cyk.utility.__kernel__.klass.ClassHelper.getByName;
import static org.cyk.utility.__kernel__.klass.ClassHelper.getImplementationClass;
import static org.cyk.utility.__kernel__.klass.ClassHelper.getImplementationClassSimpleNameFromInterfaceClass;
import static org.cyk.utility.__kernel__.klass.ClassHelper.getInterfaceSimpleNameFromImplementationClass;
import static org.cyk.utility.__kernel__.klass.ClassHelper.getInterfaces;
import static org.cyk.utility.__kernel__.klass.ClassHelper.getParameterAt;
import static org.cyk.utility.__kernel__.klass.ClassHelper.instanciate;
import static org.cyk.utility.__kernel__.klass.ClassHelper.instanciateMany;
import static org.cyk.utility.__kernel__.klass.ClassHelper.isInstanceOf;
import static org.cyk.utility.__kernel__.klass.ClassHelper.isInstanceOfNumber;
import static org.cyk.utility.__kernel__.klass.ClassHelper.isInstanceOfOne;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.cyk.utility.__kernel__.string.RegularExpressionHelper;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class ClassHelperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	/* get name*/
	
	@Test
	public void getImplementationClassSimpleNameFromInterfaceClass_interface() {
		assertThat(getImplementationClassSimpleNameFromInterfaceClass(Interface.class)).isEqualTo("InterfaceImpl");
	}
	
	@Test
	public void getInterfaceSimpleNameFromImplementationClass_class() {
		assertThat(getInterfaceSimpleNameFromImplementationClass(ImplementationImpl.class)).isEqualTo("Implementation");
	}
	
	/* instantiate */

	@Test
	public void instanciate_class_defaultConstructor() {
		ImplementationImpl instance = instanciate(ImplementationImpl.class);
		assertThat(instance).isNotNull();
	}
	
	@Test
	public void instanciate_class_specificConstructor() {
		ImplementationImpl instance = instanciate(ImplementationImpl.class,new Object[] {String.class,"hello"});
		assertThat(instance).isNotNull();
		assertThat(instance.getValue()).isEqualTo("hello");
	}
	
	@Test
	public void instanciate_interface_implemented() {
		Interface instance = instanciate(Interface.class);
		assertThat(instance).isNotNull();
	}
	
	@Test
	public void instanciate_interface_notImplemented() {
		Assertions.assertThrows(RuntimeException.class, () -> {instanciate(InterfaceNotImplemented.class);});
	}
	
	@Test
	public void instanciateMany_() {
		Collection<ImplementationImpl> instances = instanciateMany(ImplementationImpl.class,3);
		assertThat(instances).hasSize(3);
	}
	
	/* is instance of*/
	
	@Test
	public void isInstanceOf_true() {
		assertThat(isInstanceOf(ImplementationImpl.class, Interface.class)).isTrue();
	}
	
	@Test
	public void isInstanceOf_false() {
		assertThat(isInstanceOf(ImplementationImpl.class, InterfaceNotImplemented.class)).isFalse();
	}
	
	@Test
	public void isInstanceOfOne_true() {
		assertThat(isInstanceOfOne(ImplementationImpl.class, InterfaceNotImplemented.class,Interface.class)).isTrue();
	}
	
	@Test
	public void isInstanceOfOne_false() {
		assertThat(isInstanceOfOne(ImplementationImpl.class, InterfaceNotImplemented.class,AnotherInterfaceNotImplemented.class)).isFalse();
	}
	
	@Test
	public void isInstanceOfNumber_true() {
		assertThat(isInstanceOfNumber(Long.class)).isTrue();
	}
	
	@Test
	public void isInstanceOf_interface_interface() {
		assertThat(isInstanceOf(Interface02Sub.class, Interface02.class)).isTrue();
	}
	
	@Test
	public void isNumber_false() {
		assertThat(isInstanceOfNumber(Object.class)).isFalse();
	}
	
	/* get */
	
	@Test
	public void getByName_existing() {
		assertThat(getByName(Interface.class.getName())).isEqualTo(Interface.class);
	}
	
	@Test
	public void getByName_notExisting_notThrowException() {
		assertThat(getByName("xxx")).isNull();
	}
	
	@Test
	public void getByName_notExisting_throwException() {
		Assertions.assertThrows(RuntimeException.class, () -> {getByName("xxx",Boolean.FALSE);});
	}
	
	@Test
	public void getInterfaces_found() {
		assertThat(getInterfaces(MultipleInterfaces.class)).containsExactlyInAnyOrder(Interface01.class,Interface02.class,Serializable.class);
	}
	
	@Test
	public void getInterfaces_notFound() {
		assertThat(getInterfaces(Class.class)).isNull();
	}
	
	@Test
	public void getImplementationClass_interface() {
		assertThat(getImplementationClass(Interface.class)).isEqualTo(ImplementationImpl.class);
	}
	
	@Test
	public void getImplementationClass_interface_notImplemented() {
		Assertions.assertThrows(RuntimeException.class, () -> {getImplementationClass(InterfaceNotImplemented.class);});
	}
	
	@Test
	public void getImplementationClass_class() {
		assertThat(getImplementationClass(Class.class)).isEqualTo(Class.class);
	}
	
	/* get parameter */
	
	@Test
	public void getParameter_class_Object() {
		assertThat(getParameterAt(Object.class,0)).isNull();
	}
	
	@Test
	public void getParameter_class_ParameterizedInteger() {
		assertThat(getParameterAt(ParameterizedInteger.class,0)).isEqualTo(Integer.class);
	}
	
	/* get parameter */
	
	/* filter */
	
	@Test
	public void filter_packageIsNull_basesClassesIsNull() {
		assertThat(filter(null, null) ).isNull();
	}
	
	@Test
	public void filter_packageIsNull_basesClassesIsObject() {
		assertThat(filter(null, List.of(Object.class)) ).isNull();
	}
	
	@Test
	public void filter_packageIsCurrent_basesClassesIsInterface() {
		Collection<java.lang.Class<?>> classes = filter(List.of(ClassHelperUnitTest.class.getPackage()), List.of(Interface.class));
		assertThat(classes).containsExactlyInAnyOrder(ImplementationImpl.class);
	}
	
	//@Test
	public void filter_packageIsCurrent_basesClassesIsObject() {
		Collection<java.lang.Class<?>> classes = filter(List.of(SystemAction.class.getPackage()),  RegularExpressionHelper.buildIsDoNotEndWith("Impl"),List.of(SystemAction.class),null,null);
		assertThat(classes).containsExactlyInAnyOrder(ImplementationImpl.class);
	}
	
	/**/
	
	public static interface Interface {
		
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class ImplementationImpl implements Interface {
		private String value;
		public ImplementationImpl() {}
		
		public ImplementationImpl(String value) {
			this.value = value;
		}
	}
	
	public static interface InterfaceNotImplemented {
		
	}
	
	public static interface AnotherInterfaceNotImplemented {
		
	}
	
	public static interface Interface01 {
		
	}
	
	public static interface Interface02 {
		
	}
	
	public static interface Interface02Sub extends Interface02 {
		
	}
	
	public static class MultipleInterfaces implements Interface01,Interface02,Serializable {
		private static final long serialVersionUID = 1L;
		
	}
	
	public static class Class {
		
	}
	
	public static class Parameterized<T> {
		
	}
	
	public static class ParameterizedInteger extends Parameterized<Integer> {
		
	}
}
