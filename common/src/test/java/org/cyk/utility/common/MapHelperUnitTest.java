package org.cyk.utility.common;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.cyk.utility.common.helper.MapHelper;
import org.cyk.utility.common.helper.MapHelper.EntryComponent;
import org.cyk.utility.common.helper.MapHelper.EntryKey;
import org.cyk.utility.common.helper.MapHelper.Stringifier.Entry.InputStrategy;
import org.cyk.utility.common.helper.MapHelper.Stringifier.Entry.OutputStrategy;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

import lombok.Getter;
import lombok.Setter;

public class MapHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;

	static {
		MapHelper.Stringifier.Adapter.Default.DEFAULT_MAP_LISTENER_CLASS = MapListener.class;
		MapHelper.Stringifier.Entry.Adapter.Default.DEFAULT_MAP_LISTENER_CLASS = MapListener.class;
	}
	
	@Test
	public void getAs(){
		assertEquals(null, new MapListener().getAs(EntryComponent.KEY, null));
		assertEquals("", new MapListener().getAs(EntryComponent.KEY, ""));
		assertEquals(" ", new MapListener().getAs(EntryComponent.KEY, " "));
		assertEquals("a", new MapListener().getAs(EntryComponent.KEY, "a"));
		assertEquals("encoded", new MapListener().getAs(EntryComponent.KEY, EntryKey.ENCODED));
		assertEquals("action", new MapListener().getAs(EntryComponent.KEY, EntryKey.ACTION));
		assertEquals("clazz", new MapListener().getAs(EntryComponent.KEY, EntryKey.CLAZZ));
		assertEquals("classa", new MapListener().getAs(EntryComponent.KEY, ClassA.class));
		assertEquals(153l, new MapListener().getAs(EntryComponent.KEY, new ClassA(153l)));
		assertEquals("create", new MapListener().getAs(EntryComponent.KEY, Constant.Action.CREATE));
		
		assertEquals(null, new MapListener().getAs(EntryComponent.VALUE, null));
		assertEquals("", new MapListener().getAs(EntryComponent.VALUE, ""));
		assertEquals(" ", new MapListener().getAs(EntryComponent.VALUE, " "));
		assertEquals("a", new MapListener().getAs(EntryComponent.VALUE, "a"));
		assertEquals("encoded", new MapListener().getAs(EntryComponent.VALUE, EntryKey.ENCODED));
		assertEquals(153l, new MapListener().getAs(EntryComponent.VALUE, new ClassA(153l)));
		assertEquals("classa", new MapListener().getAs(EntryComponent.VALUE, ClassA.class));
		assertEquals("create", new MapListener().getAs(EntryComponent.VALUE, Constant.Action.CREATE));
		
	}
	
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
	
	@Test
	public void stringifyEntryOne(){
		assertEquals(null, new MapHelper.Stringifier.Entry.Adapter.Default().set(null,null).execute());
		assertEquals(null, new MapHelper.Stringifier.Entry.Adapter.Default().set(null,"v1").execute());
		assertEquals(null, new MapHelper.Stringifier.Entry.Adapter.Default().set("a",null).execute());
		assertEquals("a=12", new MapHelper.Stringifier.Entry.Adapter.Default().set("a","12").execute());
		assertEquals(null, new MapHelper.Stringifier.Entry.Adapter.Default().set("","").execute());
		assertEquals(null, new MapHelper.Stringifier.Entry.Adapter.Default().set("","v1").execute());
		assertEquals(null, new MapHelper.Stringifier.Entry.Adapter.Default().set("a","").execute());
		assertEquals(null, new MapHelper.Stringifier.Entry.Adapter.Default().set(" "," ").execute());
		assertEquals(null, new MapHelper.Stringifier.Entry.Adapter.Default().set(" ", "v1").execute());
		assertEquals(null, new MapHelper.Stringifier.Entry.Adapter.Default().set("a", " ").execute());
		
		assertEquals("a=15", new MapHelper.Stringifier.Entry.Adapter.Default().set("a", 15).execute());
		assertEquals("a=15", new MapHelper.Stringifier.Entry.Adapter.Default().set("a", new ClassA(15l)).execute());
		assertEquals("a=15", new MapHelper.Stringifier.Entry.Adapter.Default().set("a", Arrays.asList(new ClassA(15l))).execute());
		assertEquals("a=15", new MapHelper.Stringifier.Entry.Adapter.Default().set("a", new Object[]{new ClassA(15l)}).execute());
		
		assertEquals("a=<<15>>", new MapHelper.Stringifier.Entry.Adapter.Default().set("a", new ClassA(15l)).setListener(new MapHelper.Listener.Adapter.Default(){
			private static final long serialVersionUID = 1L;
			@Override
			public Object getAs(EntryComponent entryComponent, Object object) {
				if(object instanceof ClassA)
					return "<<"+((ClassA)object).getIdentifier()+">>";
				return super.getAs(entryComponent, object);
			}
		}).execute());
		
		assertEquals("a=2_f_0", new MapHelper.Stringifier.Entry.Adapter.Default().set("a", 15).setIsEncoded(Boolean.TRUE).execute());
		assertEquals("a=2_f_0", new MapHelper.Stringifier.Entry.Adapter.Default().set("a", new ClassA(15l)).setIsEncoded(Boolean.TRUE).execute());
		assertEquals("a=2_f_0", new MapHelper.Stringifier.Entry.Adapter.Default().set("a", Arrays.asList(new ClassA(15l))).setIsEncoded(Boolean.TRUE).execute());
		assertEquals("a=2_f_0", new MapHelper.Stringifier.Entry.Adapter.Default().set("a", new Object[]{new ClassA(15l)}).setIsEncoded(Boolean.TRUE).execute());
	}
	
	@Test
	public void stringifyEntryMany(){
		assertEquals("a=v1,v2", new MapHelper.Stringifier.Entry.Adapter.Default().set("a", "v1,v2").execute());
		assertEquals("a=v1&a=v2", new MapHelper.Stringifier.Entry.Adapter.Default().setOutputStrategy(OutputStrategy.KEY_MANY_VALUES)
				.setInputStrategy(InputStrategy.ONE_MANY).set("a","v1,v2").execute());
		
		assertEquals("a=17&5", new MapHelper.Stringifier.Entry.Adapter.Default().setInputStrategy(InputStrategy.MANY).set("a", new Object[]{17,5}).execute());
		assertEquals("a=17&a=5", new MapHelper.Stringifier.Entry.Adapter.Default("a", new Object[]{17,5}).setOutputStrategy(OutputStrategy.KEY_MANY_VALUES).execute());
		
		assertEquals("a=9&4&13", new MapHelper.Stringifier.Entry.Adapter.Default().setInputStrategy(InputStrategy.MANY).set("a", Arrays.asList("9","4","13")).execute());
		assertEquals("a=9&a=4&a=13", new MapHelper.Stringifier.Entry.Adapter.Default("a", Arrays.asList("9","4","13")).setOutputStrategy(OutputStrategy.KEY_MANY_VALUES).execute());
		
		assertEquals(null,new MapHelper.Stringifier.Entry.Adapter.Default().execute());
		assertEquals(null,new MapHelper.Stringifier.Entry.Adapter.Default(Constant.EMPTY_STRING, null).execute());
		assertEquals(null,new MapHelper.Stringifier.Entry.Adapter.Default(" ", null).execute());
		assertEquals(null,new MapHelper.Stringifier.Entry.Adapter.Default("p1", null).execute());
		assertEquals(null,new MapHelper.Stringifier.Entry.Adapter.Default(null, "a").execute());
		assertEquals("p1=a",new MapHelper.Stringifier.Entry.Adapter.Default("p1","a").execute());
		
		assertEquals("p1=a",new MapHelper.Stringifier.Entry.Adapter.Default("p1",new Object[]{"a",null}).execute());
		assertEquals("p1=b",new MapHelper.Stringifier.Entry.Adapter.Default("p1",new Object[]{null,"b"}).execute());
		
		assertEquals("p1=a&p1=b&p1=v3",new MapHelper.Stringifier.Entry.Adapter.Default("p1",new Object[]{"a","b","v3"}).setKeyValuesSeparator("&").setOutputStrategy(OutputStrategy.KEY_MANY_VALUES).execute());
		
		assertEquals("p1=15",new MapHelper.Stringifier.Entry.Adapter.Default("p1","15").execute());
		assertEquals("p1=2_f_0",new MapHelper.Stringifier.Entry.Adapter.Default("p1","15").setIsEncoded(Boolean.TRUE).execute());
		
		assertEquals("p1=15",new MapHelper.Stringifier.Entry.Adapter.Default("p1",new ClassA(15l)).execute());
		assertEquals("p1=2_f_0",new MapHelper.Stringifier.Entry.Adapter.Default("p1",new ClassA(15l)).setIsEncoded(Boolean.TRUE).execute());
		
		assertEquals("a=15&3", new MapHelper.Stringifier.Entry.Adapter.Default().set("a", Arrays.asList(new ClassA(15l),3)).execute());
		assertEquals("a=15&3", new MapHelper.Stringifier.Entry.Adapter.Default().set("a", new Object[]{new ClassA(15l),3}).execute());
		
	}
	
	@Test
	public void stringify(){
		assertEquals("p1=v1&p2=v2&p4=17&5&p3=v3&p5=9&4&13&p6=v6", new MapHelper.Stringifier.Adapter.Default().addKeyValue(
				"p1", "v1","p2", "v2","p4", new Object[]{17,5},"p3", "v3","p5", Arrays.asList("9","4","13"),"p6", "v6").execute());
		
		assertEquals("p1=a&p2=b",new MapHelper.Stringifier.Adapter.Default().addKeyValue("p1","a","p2","b").execute());
		assertEquals("p1=a&p2=b",new MapHelper.Stringifier.Adapter.Default().addKeyValue("p1","a","p2","b","","b").execute());
		assertEquals("p1=a&p2=b",new MapHelper.Stringifier.Adapter.Default().addKeyValue("p1","a","p2","b"," ","b").execute());
		assertEquals("p1=a&p2=b&p3=15",new MapHelper.Stringifier.Adapter.Default().addKeyValue("p1","a","p2","b","p3",15).execute());		
		assertEquals("p1=a&p2=b&p3=2_f_0&encoded=p3",new MapHelper.Stringifier.Adapter.Default().addKeyValue("p1","a","p2","b","p3",15)
				.addEncodedKeys("p3").execute());
		assertEquals("p1=a&p2=b&p3=2_f_0&p4=2_a_0&encoded=p3&encoded=p4",new MapHelper.Stringifier.Adapter.Default(MapHelper.getInstance()
				.getByKeyValue("p1","a","p2","b","p3",15,"p4",10)).addEncodedKeys("p3","p4").execute());
		assertEquals("p1=a&p2=b&p3=2_f_0&p4=2_a_0&encoded=p3&encoded=p4",new MapHelper.Stringifier.Adapter.Default(MapHelper.getInstance()
				.getByKeyValue("p1","a","p2","b","p3",Arrays.asList(new ClassA(15l)),"p4",Arrays.asList(new ClassA(10l)))).addEncodedKeys("p3","p4").execute());
		
	}
	
	@Test
	public void assertMap(){
		MapHelper.Map<Object, Object> map = new MapHelper.Map<Object, Object>(Object.class,Object.class);
		assertEquals(map.getString("k1"), null);
		map.addString("k1", " ", "v1");
		assertEquals(map.getString("k1"), "v1");
		map.addString("k1", " ", "v2");
		assertEquals(map.getString("k1"), "v1 v2");
		map.addString("k1", " ", "v3");
		assertEquals(map.getString("k1"), "v1 v2 v3");
		map.removeString("k1"," ","v2");
		assertEquals(map.getString("k1"), "v1 v3");
	}
	
	@Getter @Setter
	public static class ClassA implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private Long identifier;
		
		public ClassA(Long identifier) {
			this.identifier = identifier;
		}
		
	}
	
	public static class MapListener extends MapHelper.Listener.Adapter.Default {
		
		private static final long serialVersionUID = 1L;

		@Override
		public Object getAs(EntryComponent entryComponent, Object object) {
			if(object instanceof ClassA)
				return ((ClassA)object).getIdentifier();
			return super.getAs(entryComponent, object);
		}
		
		@Override
		public String getSeparatorOfValue() {
			return "&";
		}
		
		@Override
		public String getSeparatorOfKeyValue() {
			return "&";
		}
			
	}
	
	public static class EntryListener extends MapHelper.Stringifier.Entry.Listener.Adapter.Default {
		
		private static final long serialVersionUID = 1L;

		
			
	}
	
}
