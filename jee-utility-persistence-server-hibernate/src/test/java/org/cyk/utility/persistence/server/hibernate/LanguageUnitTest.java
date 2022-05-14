package org.cyk.utility.persistence.server.hibernate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class LanguageUnitTest extends org.cyk.utility.test.weld.AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;
		
	@Test
	public void coalesce_empty(){
		assertEquals("COALESCE(NULL)", Language.coalesce());
	}
	
	@Test
	public void coalesce_1(){
		assertEquals("COALESCE(1)", Language.coalesce(1));
	}
	
	@Test
	public void coalesce_null_0(){
		assertEquals("COALESCE(0)", Language.coalesce(null,0));
	}
	
	@Test
	public void coalesce_f_0(){
		assertEquals("COALESCE(f,0)", Language.coalesce("f",0));
	}
}