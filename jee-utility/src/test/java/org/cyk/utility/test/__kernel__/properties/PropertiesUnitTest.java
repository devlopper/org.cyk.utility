package org.cyk.utility.test.__kernel__.properties;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class PropertiesUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
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
	
	@Test
	public void setPropertyFromPathL0(){
		Properties properties = new Properties().setFromPath(new Object[]{Properties.ACTION}, "MyClass");
		assertionHelper.assertNotNull(properties.getAction());
		assertionHelper.assertTrue(properties.getAction() instanceof String);
		assertionHelper.assertEquals("MyClass", properties.getAction() );
	}
	
	@Test
	public void setPropertyFromPathL1(){
		Properties properties = new Properties().setFromPath(new Object[]{Properties.ACTION,Properties.ACTION_ON_CLASS}, "MyClass");
		assertionHelper.assertNotNull(properties.getAction());
		assertionHelper.assertTrue(properties.getAction() instanceof Properties);
		
		Properties actionProperties = (Properties) properties.getAction();
		
		assertionHelper.assertNotNull(actionProperties.getActionOnClass());
		assertionHelper.assertTrue(actionProperties.getActionOnClass() instanceof String);
		
		String actionOnClass = (String) actionProperties.getActionOnClass();
		
		assertionHelper.assertEquals("MyClass", actionOnClass );
	}
	
	@Test
	public void setPropertyFromPathL2(){
		Properties properties = new Properties().setFromPath(new Object[]{Properties.ACTION,Properties.ACTION_ON_CLASS,Properties.ACTION}, "MyClass");
		assertionHelper.assertNotNull(properties.getAction());
		assertionHelper.assertTrue(properties.getAction() instanceof Properties);
		
		Properties actionProperties = (Properties) properties.getAction();
		
		assertionHelper.assertNotNull(actionProperties.getActionOnClass());
		assertionHelper.assertTrue(actionProperties.getActionOnClass() instanceof Properties);
		
		Properties actionOnClassProperties = (Properties) actionProperties.getActionOnClass();
		
		assertionHelper.assertNotNull(actionOnClassProperties.getAction());
		assertionHelper.assertTrue(actionOnClassProperties.getAction() instanceof String);
		
		String action = (String) actionOnClassProperties.getAction();
		
		assertionHelper.assertEquals("MyClass", action );
	}
	
	@Test
	public void getPropertyFromPathL0(){
		Properties properties = new Properties().set(Properties.ACTION, "MyClass");
		assertionHelper.assertNotNull(properties.getAction());
		assertionHelper.assertTrue(properties.getAction() instanceof String);
		assertionHelper.assertEquals("MyClass", properties.getFromPath(Properties.ACTION) );
	}
	
	@Test
	public void getPropertyFromPathL1(){
		Properties properties = new Properties().set(Properties.ACTION_ON_CLASS, new Properties());
		Properties actionOnClassProperties = (Properties) properties.getActionOnClass();
		actionOnClassProperties.setAction("MyClass");
		
		assertionHelper.assertNotNull(properties.getActionOnClass());
		assertionHelper.assertTrue(properties.getActionOnClass() instanceof Properties);
		
		assertionHelper.assertNotNull(actionOnClassProperties.getAction());
		assertionHelper.assertTrue(actionOnClassProperties.getAction() instanceof String);
		assertionHelper.assertEquals("MyClass", properties.getFromPath(Properties.ACTION_ON_CLASS,Properties.ACTION) );
	}
	
}
