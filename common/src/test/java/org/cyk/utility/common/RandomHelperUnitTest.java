package org.cyk.utility.common;

import org.cyk.utility.common.helper.RandomHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.helper.ValidationHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class RandomHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	{
		StringHelper.ToStringMapping.Datasource.Adapter.Default.initialize();
	}
	
	@Test
	public void getString(){
		assertEquals(5, RandomHelper.getInstance().getString(5).length());	
	}
	
	@Test
	public void getAlphabetic(){
		assertEquals(5, RandomHelper.getInstance().getAlphabetic(5).length());	
	}
	
	@Test
	public void getNumeric(){
		Number numeric = RandomHelper.getInstance().getNumeric(5);
		assertEquals(5, numeric.toString().length());	
	}
	
	@Test
	public void getAlphanumeric(){
		String value = RandomHelper.getInstance().getAlphanumeric(5);
		assertEquals(5, value.length());	
	}
	
	@Test
	public void getElectronicMailAddress(){
		String value = RandomHelper.getInstance().getElectronicMailAddress();
		assertEquals(Boolean.TRUE, ValidationHelper.getInstance().isElectronicMailAddress(value));	
	}
	
}
