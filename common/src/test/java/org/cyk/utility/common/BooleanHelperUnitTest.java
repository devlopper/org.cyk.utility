package org.cyk.utility.common;

import org.cyk.utility.common.helper.BooleanHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class BooleanHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@Test
	public void build(){
		assertEquals(Boolean.TRUE, new BooleanHelper.Builder.String.Adapter.Default("true").execute());
		assertEquals(Boolean.TRUE, new BooleanHelper.Builder.String.Adapter.Default("True").execute());
		assertEquals(Boolean.TRUE, new BooleanHelper.Builder.String.Adapter.Default("truE").execute());
		assertEquals(Boolean.TRUE, new BooleanHelper.Builder.String.Adapter.Default("TRUE").execute());
		assertEquals(Boolean.TRUE, new BooleanHelper.Builder.String.Adapter.Default("tRuE").execute());
		assertEquals(Boolean.TRUE, new BooleanHelper.Builder.String.Adapter.Default("1").execute());
		assertEquals(Boolean.TRUE, new BooleanHelper.Builder.String.Adapter.Default("1.5").execute());
		assertEquals(Boolean.TRUE, new BooleanHelper.Builder.String.Adapter.Default("25").execute());
		assertEquals(Boolean.TRUE, new BooleanHelper.Builder.String.Adapter.Default("-1").execute());
		assertEquals(Boolean.TRUE, new BooleanHelper.Builder.String.Adapter.Default("0.2").execute());
		assertEquals(Boolean.TRUE, new BooleanHelper.Builder.String.Adapter.Default("0.000000001").execute());
		
		assertEquals(Boolean.FALSE, new BooleanHelper.Builder.String.Adapter.Default("false").execute());
		assertEquals(Boolean.FALSE, new BooleanHelper.Builder.String.Adapter.Default("FALSE").execute());
		assertEquals(Boolean.FALSE, new BooleanHelper.Builder.String.Adapter.Default("False").execute());
		assertEquals(Boolean.FALSE, new BooleanHelper.Builder.String.Adapter.Default("falsE").execute());
		assertEquals(Boolean.FALSE, new BooleanHelper.Builder.String.Adapter.Default("fAlSe").execute());
		assertEquals(Boolean.FALSE, new BooleanHelper.Builder.String.Adapter.Default("0").execute());
		assertEquals(Boolean.FALSE, new BooleanHelper.Builder.String.Adapter.Default("0.0").execute());
		assertEquals(Boolean.FALSE, new BooleanHelper.Builder.String.Adapter.Default("0.00").execute());
		assertEquals(Boolean.FALSE, new BooleanHelper.Builder.String.Adapter.Default("0.000").execute());
	}
	
	
}
