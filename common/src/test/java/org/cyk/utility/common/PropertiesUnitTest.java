package org.cyk.utility.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class PropertiesUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@Test
	public void set(){
		Properties properties = new Properties();
		List<String> listString = new ArrayList<String>();
		List<Integer> listInteger = new ArrayList<Integer>();
		properties.set(1, "mystring");
		properties.set(2, 10);
		properties.set(3, listString);
		properties.set(4, listInteger);
		
		assertEquals("mystring", properties.get(1));
		assertEquals(10, properties.get(2));
		assertList(listString, Arrays.asList());
		assertList(listInteger, Arrays.asList());
	}
	
	@Test
	public void add(){
		Properties properties = new Properties();
		List<String> listString = new ArrayList<String>();
		List<Integer> listInteger = new ArrayList<Integer>();
		properties.set(1, "mystring");
		properties.set(2, 10);
		properties.set(3, listString);
		properties.set(4, listInteger);
		
		assertEquals("mystring", properties.get(1));
		assertEquals(10, properties.get(2));
		assertList(listString, Arrays.asList());
		assertList(listInteger, Arrays.asList());
		
		assertEquals("mystring mystring2", properties.addString(1, "mystring2").get(1));
		assertEquals(13, ((Number)properties.add(2, 3).get(2)).intValue());
		assertList((List<?>) properties.addCollectionElement(3, "one").get(3),Arrays.asList("one"));
		assertList((List<?>) properties.addCollectionElement(4, 7).get(4),Arrays.asList(7));
		
		assertEquals("mystring mystring2 mystring3", properties.addString(1, null,"mystring3",""," ","  ").get(1));
		assertEquals(16, ((Number)properties.add(2, 3).get(2)).intValue());
		assertList((List<?>) properties.addCollectionElement(3, "two").get(3),Arrays.asList("one","two"));
		assertList((List<?>) properties.addCollectionElement(4, 15).get(4),Arrays.asList(7,15));
	}
	
	@Test
	public void remove(){
		Properties properties = new Properties();
		List<String> listString = new ArrayList<String>();
		List<Integer> listInteger = new ArrayList<Integer>();
		properties.set(1, "mystring");
		properties.set(2, 10);
		properties.set(3, listString);
		properties.set(4, listInteger);
		
		assertEquals("mystring", properties.get(1));
		assertEquals(10, properties.get(2));
		assertList(listString, Arrays.asList());
		assertList(listInteger, Arrays.asList());
		
		assertEquals("mystring mystring2", properties.addString(1, "mystring2").get(1));
		assertEquals(13, ((Number)properties.add(2, 3).get(2)).intValue());
		assertList((List<?>) properties.addCollectionElement(3, "one").get(3),Arrays.asList("one"));
		assertList((List<?>) properties.addCollectionElement(4, 7).get(4),Arrays.asList(7));
		
		assertEquals("mystring mystring2 mystring3 a b", properties.addString(1, null,"mystring3",""," ","  ","a","b").get(1));
		assertEquals(16, ((Number)properties.add(2, 3).get(2)).intValue());
		assertList((List<?>) properties.addCollectionElement(3, "two").get(3),Arrays.asList("one","two"));
		assertList((List<?>) properties.addCollectionElement(4, 15).get(4),Arrays.asList(7,15));
		
		assertEquals("mystring mystring3 a b", properties.subtractString(1, null,"mystring2").get(1));
		assertEquals("mystring mystring3 a", properties.subtractString(1, null,"b").get(1));
		assertEquals("mystring3 a", properties.subtractString(1, null,"mystring").get(1));
		assertEquals(10, ((Number)properties.subtract(2, 6).get(2)).intValue());
		assertList((List<?>) properties.subtractCollectionElement(3, "one").get(3),Arrays.asList("two"));
		assertList((List<?>) properties.subtractCollectionElement(4, 15).get(4),Arrays.asList(7));
	}
	
	@Test
	public void setDefaultValues(){
		//assertEquals("mytempa", new A().getPropertiesMap().getTemplate());
		assertEquals("mytempa", new B().getPropertiesMap().getTemplate());
	}

	/**/
	
	public static class A extends AbstractBean {
		
	}
	
	public static class B extends A {
		
	}
	
	static {
		Properties.setDefaultValues(A.class, new Object[]{
				Properties.TEMPLATE,"mytempa"
		});
	}
}
