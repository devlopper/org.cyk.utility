package org.cyk.utility.__kernel__;

import static org.assertj.core.api.Assertions.assertThat;
import static org.cyk.utility.__kernel__.Helper.isHaveModifiers;

import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.util.List;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class HelperUniteTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void isHaveModifiers_privateString_private_true() throws NoSuchFieldException, SecurityException {
		assertThat(isHaveModifiers(Class.class.getDeclaredField("privateString").getModifiers(), Modifier.PRIVATE)).isTrue();
	}
	
	@Test
	public void isHaveModifiers_publicString_public_true() throws NoSuchFieldException, SecurityException {
		assertThat(isHaveModifiers(Class.class.getDeclaredField("publicString").getModifiers(), Modifier.PUBLIC)).isTrue();
	}
	
	@Test
	public void isHaveModifiers_publicString_private_true() throws NoSuchFieldException, SecurityException {
		assertThat(isHaveModifiers(Class.class.getDeclaredField("publicString").getModifiers(), Modifier.PRIVATE)).isFalse();
	}
	
	@Test
	public void isHaveModifiers_serialVersionUID_private_true() throws NoSuchFieldException, SecurityException {
		assertThat(isHaveModifiers(Class.class.getDeclaredField("serialVersionUID").getModifiers(), Modifier.PRIVATE)).isTrue();
	}
	
	@Test
	public void isHaveModifiers_serialVersionUID_static_true() throws NoSuchFieldException, SecurityException {
		assertThat(isHaveModifiers(Class.class.getDeclaredField("serialVersionUID").getModifiers(), Modifier.STATIC)).isTrue();
	}
	
	@Test
	public void isHaveModifiers_serialVersionUID_final_true() throws NoSuchFieldException, SecurityException {
		assertThat(isHaveModifiers(Class.class.getDeclaredField("serialVersionUID").getModifiers(), Modifier.FINAL)).isTrue();
	}
	
	@Test
	public void isHaveModifiers_serialVersionUID_private_static_final_true() throws NoSuchFieldException, SecurityException {
		assertThat(isHaveModifiers(Class.class.getDeclaredField("serialVersionUID").getModifiers(), Modifier.PRIVATE,Modifier.STATIC,Modifier.FINAL)).isTrue();
	}
	
	@Test
	public void isHaveModifiers_serialVersionUID_private_static_interface_true() throws NoSuchFieldException, SecurityException {
		assertThat(isHaveModifiers(Class.class.getDeclaredField("serialVersionUID").getModifiers(), Modifier.PRIVATE,Modifier.STATIC,Modifier.INTERFACE)).isTrue();
	}
	
	@Test
	public void isHaveModifiers_serialVersionUID_private_static_interface_1_false() throws NoSuchFieldException, SecurityException {
		assertThat(isHaveModifiers(Class.class.getDeclaredField("serialVersionUID").getModifiers(), List.of(Modifier.PRIVATE,Modifier.STATIC,Modifier.INTERFACE),1)).isTrue();
	}
	
	@Test
	public void isHaveModifiers_serialVersionUID_private_static_interface_2_false() throws NoSuchFieldException, SecurityException {
		assertThat(isHaveModifiers(Class.class.getDeclaredField("serialVersionUID").getModifiers(), List.of(Modifier.PRIVATE,Modifier.STATIC,Modifier.INTERFACE),2)).isTrue();
	}
	
	@Test
	public void isHaveModifiers_serialVersionUID_private_static_interface_3_false() throws NoSuchFieldException, SecurityException {
		assertThat(isHaveModifiers(Class.class.getDeclaredField("serialVersionUID").getModifiers(), List.of(Modifier.PRIVATE,Modifier.STATIC,Modifier.INTERFACE),3)).isFalse();
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class Class implements Serializable {
		private static final long serialVersionUID = 1L;
		private String privateString;
		public String publicString;
		String defaultString;
		
		static String staticString;
		static final String staticFinalString="1";
	}
	
}
