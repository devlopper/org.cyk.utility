package org.cyk.utility.common;

import org.cyk.utility.common.helper.FileHelper;
import org.cyk.utility.common.helper.RandomHelper;
import org.cyk.utility.common.helper.StreamHelper;
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
	public void getFilePersonHeadOnly(){
		FileHelper.File file = RandomHelper.getInstance().getFilePersonHeadOnly(Boolean.TRUE);
		StreamHelper.getInstance().write(file.getBytes(), "target/"+file.getName(), file.getExtension());
	}
	
	@Test
	public void getElectronicMailAddress(){
		String value = RandomHelper.getInstance().getElectronicMailAddress();
		assertEquals(Boolean.TRUE, ValidationHelper.getInstance().isElectronicMailAddress(value));	
	}
	
	@Test
	public void getStringCollection(){
		assertNotNull(RandomHelper.getInstance().get(RandomHelper.StringCollection.FIRST_NAME));
		RandomHelper.Person person = RandomHelper.getInstance().getPerson(Boolean.TRUE);
		assertNotNull(person);
		assertNotNull(person.getFirstname());
		assertNotNull(person.getLastname());
		assertNotNull(person.getHeadOnlyPhoto());
	}
	
}
