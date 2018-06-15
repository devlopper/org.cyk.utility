package org.cyk.utility.test.__kernel__.properties;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.test.AbstractArquillianUnitTest;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;

public class PropertiesUnitTest extends AbstractArquillianUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void isMapNullWhenInstanciated(){
		assertionHelper.assertNull(new Properties().__getMap__());
	}
	
	@Test
	public void isMapNullWhenInstanciatedAndSetPropertyWithKeyNullAndValueNull(){
		assertionHelper.assertNotNull(new Properties().set(null, null).__getMap__());
	}
	
	@Test
	public void isMapNullWhenInstanciatedAndSetPropertyWithKeyNullAndValueNotNull(){
		assertionHelper.assertNotNull(new Properties().set(null, "789").__getMap__());
	}
	
	@Test
	public void isMapNotNullWhenInstanciatedAndSetPropertyWithKeyNotNullAndValueNull(){
		assertionHelper.assertNotNull(new Properties().set("myprop", null).__getMap__());
	}
	
	@Test
	public void isMapNotNullWhenInstanciatedAndSetPropertyWithKeyNotNullAndValueNotNull(){
		assertionHelper.assertNotNull(new Properties().set("myprop", "123").__getMap__());
	}
	
	@Test
	public void is123WhenPropertyValueIs123(){
		assertionHelper.assertEquals("123",new Properties().setValue("123").getValue());
	}
	
	@Test
	public void is123WhenPropertyValuePropertiesValueIs123(){
		Properties valueProperties = new Properties().setValue("123");
		assertionHelper.assertEquals("123",new Properties().setValue(valueProperties).getRecursively(Properties.VALUE));
	}
	
	/* Deployment*/
	
	@Deployment
	public static JavaArchive createDeployment() {
		return AbstractArquillianUnitTest.createJavaArchiveDeployment()
				.addPackage(PropertiesUnitTest.class.getPackage());
	}
	
}
