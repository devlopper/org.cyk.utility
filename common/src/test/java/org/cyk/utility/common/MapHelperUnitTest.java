package org.cyk.utility.common;

import java.util.HashMap;
import java.util.Map;

import org.cyk.utility.common.helper.MapHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class MapHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;

	@Test
	public void containsKey(){
		Map<String,String> map = new HashMap<>();
		assertFalse(new MapHelper.ContainsKey.String.Adapter.Default("a").setProperty(MapHelper.ContainsKey.PROPERTY_NAME_MAP, map).execute());
		map.put("b", "bval");
		assertFalse(new MapHelper.ContainsKey.String.Adapter.Default("a").setProperty(MapHelper.ContainsKey.PROPERTY_NAME_MAP, map).execute());
		map.put("a", "aval");
		assertTrue(new MapHelper.ContainsKey.String.Adapter.Default("a").setProperty(MapHelper.ContainsKey.PROPERTY_NAME_MAP, map).execute());
		assertFalse(new MapHelper.ContainsKey.String.Adapter.Default("A").setProperty(MapHelper.ContainsKey.PROPERTY_NAME_MAP, map).execute());
		assertTrue(new MapHelper.ContainsKey.String.Adapter.Default("A").setProperty(MapHelper.ContainsKey.PROPERTY_NAME_MAP, map)
				.setProperty(MapHelper.ContainsKey.PROPERTY_NAME_CASE_SENSITIVE, Boolean.FALSE).execute());
	}
	
	@Test
	public void getValue(){
		Map<String,String> map = new HashMap<>();
		assertNull(new MapHelper.GetValue.String.Adapter.Default<java.lang.String>(map,java.lang.String.class).setProperty(MapHelper.GetValue.PROPERTY_NAME_KEY, "a").execute());
		map.put("b", "bval");
		assertNull(new MapHelper.GetValue.String.Adapter.Default<java.lang.String>(map,java.lang.String.class).setProperty(MapHelper.GetValue.PROPERTY_NAME_KEY, "a").execute());
		map.put("a", "aval");
		assertNotNull(new MapHelper.GetValue.String.Adapter.Default<java.lang.String>(map,java.lang.String.class).setProperty(MapHelper.GetValue.PROPERTY_NAME_KEY, "a").execute());
		assertNull(new MapHelper.GetValue.String.Adapter.Default<java.lang.String>(map,java.lang.String.class).setProperty(MapHelper.GetValue.PROPERTY_NAME_KEY, "A").execute());
		assertNotNull(new MapHelper.GetValue.String.Adapter.Default<java.lang.String>(map,java.lang.String.class).setProperty(MapHelper.GetValue.PROPERTY_NAME_KEY, "A")
				.setProperty(MapHelper.ContainsKey.PROPERTY_NAME_CASE_SENSITIVE, Boolean.FALSE).execute());
	}
	
	@Test
	public void getStringValueAs(){
		Map<String,String> map = new HashMap<>();
		assertEquals(83, MapHelper.getInstance().getStringValueAs(Integer.class, map, "a", "83"));
		map.put("a", "12");
		assertEquals(12, MapHelper.getInstance().getStringValueAs(Integer.class, map, "a", "52"));
	}
}
