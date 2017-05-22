package org.cyk.utility.common;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.builder.NameValueCollectionStringBuilder;
import org.cyk.utility.common.builder.NameValueStringBuilder;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;


public class NameValueStringBuilderUT extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	static {
		NameValueStringBuilder.Listener.COLLECTION.add(new NameValueStringBuilder.Listener.Adapter.Default(){
			private static final long serialVersionUID = 1L;

			@Override
			public Object getValueToProcessed(Object value) {
				if(value instanceof ClassA)
					return ((ClassA)value).getIdentifier();
				return super.getValueToProcessed(value);
			}
		});
	}

	@Test
	public void oneNameValue(){
		assertEquals(Constant.EMPTY_STRING,new NameValueStringBuilder().set(null, null).build());
		assertEquals(Constant.EMPTY_STRING,new NameValueStringBuilder().set(Constant.EMPTY_STRING, null).build());
		assertEquals(Constant.EMPTY_STRING,new NameValueStringBuilder().set(" ", null).build());
		assertEquals(Constant.EMPTY_STRING,new NameValueStringBuilder().set("p1", null).build());
		assertEquals(Constant.EMPTY_STRING,new NameValueStringBuilder().set(null, "a").build());
		assertEquals("p1=a",new NameValueStringBuilder("p1").add("a").build());
		
		assertEquals("p1=a",new NameValueStringBuilder("p1").add("a").add(null).build());
		assertEquals("p1=b",new NameValueStringBuilder("p1").add(null).add("b").build());
		
		assertEquals("p1=a&p1=b&p1=v3",new NameValueStringBuilder("p1").addArray("a","b","v3").build());
		
		assertEquals("p1=15",new NameValueStringBuilder("p1").add("15").build());
		assertEquals("p1=2_f_0",new NameValueStringBuilder("p1").add("15").setEncoded(Boolean.TRUE).build());
		
		assertEquals("p1=15",new NameValueStringBuilder("p1").add(new ClassA(15l)).build());
		assertEquals("p1=2_f_0",new NameValueStringBuilder("p1").add(new ClassA(15l)).setEncoded(Boolean.TRUE).build());
	}
	
	@Test
	public void manyNameValue(){
		assertEquals("p1=a&p2=b",new NameValueCollectionStringBuilder()
		.add(new NameValueStringBuilder("p1","a"),new NameValueStringBuilder("p2","b")).add(new NameValueStringBuilder(null,"b")).build());
		assertEquals("p1=a&p2=b",new NameValueCollectionStringBuilder()
		.add(new NameValueStringBuilder("p1","a"),new NameValueStringBuilder("p2","b")).add(new NameValueStringBuilder("","b")).build());
		assertEquals("p1=a&p2=b",new NameValueCollectionStringBuilder()
		.add(new NameValueStringBuilder("p1","a"),new NameValueStringBuilder("p2","b")).add(new NameValueStringBuilder(" ","b")).build());
		
		assertEquals("p1=a&p2=b",new NameValueCollectionStringBuilder()
			.add(new NameValueStringBuilder("p1","a"),new NameValueStringBuilder("p2","b")).build());
		assertEquals("p1=a&p2=b&p3=15",new NameValueCollectionStringBuilder()
		.add(new NameValueStringBuilder("p1","a"),new NameValueStringBuilder("p2","b"),new NameValueStringBuilder("p3").add(new ClassA(15l)))
		.build());
		assertEquals("p1=a&p2=b&p3=2_f_0&encoded=p3",new NameValueCollectionStringBuilder()
			.add(new NameValueStringBuilder("p1","a"),new NameValueStringBuilder("p2","b"),new NameValueStringBuilder("p3").add(new ClassA(15l)).setEncoded(Boolean.TRUE))
			.build());
		assertEquals("p1=a&p2=b&p3=2_f_0&p4=2_a_0&encoded=p3&encoded=p4",new NameValueCollectionStringBuilder()
			.add(new NameValueStringBuilder("p1","a"),new NameValueStringBuilder("p2","b"),new NameValueStringBuilder("p3").add(new ClassA(15l)).setEncoded(Boolean.TRUE))
			.add(new NameValueStringBuilder("p4").add(new ClassA(10l)).setEncoded(Boolean.TRUE))
			.build());
	}
	
	/**/
	
	@Getter @Setter
	public static class ClassA implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private Long identifier;
		
		public ClassA(Long identifier) {
			this.identifier = identifier;
		}
		
	}
}
