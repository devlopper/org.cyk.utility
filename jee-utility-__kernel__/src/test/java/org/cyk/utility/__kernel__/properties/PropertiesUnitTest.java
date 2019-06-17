package org.cyk.utility.__kernel__.properties;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class PropertiesUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void isMapNullWhenInstanciated(){
		Assert.assertNull(new Properties().__getMap__());
	}
	
	@Test
	public void isMapNullWhenInstanciatedAndSetPropertyWithKeyNullAndValueNull(){
		Assert.assertNotNull(new Properties().set(null, null).__getMap__());
	}
	
	@Test
	public void isMapNullWhenInstanciatedAndSetPropertyWithKeyNullAndValueNotNull(){
		Assert.assertNotNull(new Properties().set(null, "789").__getMap__());
	}
	
	@Test
	public void isMapNotNullWhenInstanciatedAndSetPropertyWithKeyNotNullAndValueNull(){
		Assert.assertNotNull(new Properties().set("myprop", null).__getMap__());
	}
	
	@Test
	public void isMapNotNullWhenInstanciatedAndSetPropertyWithKeyNotNullAndValueNotNull(){
		Assert.assertNotNull(new Properties().set("myprop", "123").__getMap__());
	}
	
	@Test
	public void is123WhenPropertyValueIs123(){
		Assert.assertEquals("123",new Properties().setValue("123").getValue());
	}
	
	@Test
	public void is123WhenPropertyValuePropertiesValueIs123(){
		Properties valueProperties = new Properties().setValue("123");
		Assert.assertEquals("123",new Properties().setValue(valueProperties).getRecursively(Properties.VALUE));
	}
	
	@Test
	public void setPropertyFromPathL0(){
		Properties properties = new Properties().setFromPath(new Object[]{Properties.ACTION}, "MyClass");
		Assert.assertNotNull(properties.getAction());
		Assert.assertTrue(properties.getAction() instanceof String);
		Assert.assertEquals("MyClass", properties.getAction() );
	}
	
	@Test
	public void setPropertyFromPathL1(){
		Properties properties = new Properties().setFromPath(new Object[]{Properties.ACTION,Properties.ACTION_ON_CLASS}, "MyClass");
		Assert.assertNotNull(properties.getAction());
		Assert.assertTrue(properties.getAction() instanceof Properties);
		
		Properties actionProperties = (Properties) properties.getAction();
		
		Assert.assertNotNull(actionProperties.getActionOnClass());
		Assert.assertTrue(actionProperties.getActionOnClass() instanceof String);
		
		String actionOnClass = (String) actionProperties.getActionOnClass();
		
		Assert.assertEquals("MyClass", actionOnClass );
	}
	
	@Test
	public void setPropertyFromPathL2(){
		Properties properties = new Properties().setFromPath(new Object[]{Properties.ACTION,Properties.ACTION_ON_CLASS,Properties.ACTION}, "MyClass");
		Assert.assertNotNull(properties.getAction());
		Assert.assertTrue(properties.getAction() instanceof Properties);
		
		Properties actionProperties = (Properties) properties.getAction();
		
		Assert.assertNotNull(actionProperties.getActionOnClass());
		Assert.assertTrue(actionProperties.getActionOnClass() instanceof Properties);
		
		Properties actionOnClassProperties = (Properties) actionProperties.getActionOnClass();
		
		Assert.assertNotNull(actionOnClassProperties.getAction());
		Assert.assertTrue(actionOnClassProperties.getAction() instanceof String);
		
		String action = (String) actionOnClassProperties.getAction();
		
		Assert.assertEquals("MyClass", action );
	}
	
	@Test
	public void getPropertyFromPathL0(){
		Properties properties = new Properties().set(Properties.ACTION, "MyClass");
		Assert.assertNotNull(properties.getAction());
		Assert.assertTrue(properties.getAction() instanceof String);
		Assert.assertEquals("MyClass", properties.getFromPath(Properties.ACTION) );
	}
	
	@Test
	public void getPropertyFromPathL1(){
		Properties properties = new Properties().set(Properties.ACTION_ON_CLASS, new Properties());
		Properties actionOnClassProperties = (Properties) properties.getActionOnClass();
		actionOnClassProperties.setAction("MyClass");
		
		Assert.assertNotNull(properties.getActionOnClass());
		Assert.assertTrue(properties.getActionOnClass() instanceof Properties);
		
		Assert.assertNotNull(actionOnClassProperties.getAction());
		Assert.assertTrue(actionOnClassProperties.getAction() instanceof String);
		Assert.assertEquals("MyClass", properties.getFromPath(Properties.ACTION_ON_CLASS,Properties.ACTION) );
	}
	
	@Test
	public void getPropertiesWhereKeyIsInstanceOfString(){
		Properties properties = new Properties();
		properties.set("k01", "v01");
		properties.set(2, "v02");
		properties.set("k03", "v03");
		Collection<Object> collection = properties.getWhereKeyIsInstanceOf(String.class);
		assertThat(collection).containsExactly("v01","v03");
	}
	
	@Test
	public void getPropertiesWhereKeyIsInstanceOfInteger(){
		Properties properties = new Properties();
		properties.set("k01", "v01");
		properties.set(2, "v02");
		properties.set("k03", "v03");
		Collection<Object> collection = properties.getWhereKeyIsInstanceOf(Integer.class);
		assertThat(collection).containsExactly("v02");
	}
}
