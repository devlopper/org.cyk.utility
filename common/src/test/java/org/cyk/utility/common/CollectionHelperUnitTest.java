package org.cyk.utility.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.CollectionHelper.Instance;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

public class CollectionHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@Test
	public void assertSort(){
		CollectionHelper.Instance<AbstractBean> sortables = new CollectionHelper.Instance<>();
		sortables.addOne(new Sortable().setString1("a"));
		sortables.addOne(new Sortable().setString1("z").setOrderNumber(7l));
		sortables.addOne(new Sortable().setString1("e"));
		sortables.addOne(new Sortable().setString1("r"));
		sortables.addOne(new Sortable().setString1("t"));
		sortables.addOne(new Sortable().setString1("y"));
		//System.out.println(sortables.getElements());
		sortables.sort();
		//System.out.println(sortables.getElements());
		
	}
	
	@Test
	public void assertElementObjet(){
		CollectionHelper.Instance<ChildElement> collection = CollectionHelper.getInstance().getCollectionInstance(ChildElement.class,Child.class,SelectItem.class,Master.class);
		collection.setIsElementObjectCreatable(Boolean.TRUE);
		collection.mapElementFieldsAndElementObjectFields("ce1","c1","ce2","c2").mapElementObjectFields("cc1","cc2");
		collection.addOne();
		Child child = collection.getElements().iterator().next().getObject();
		assertEquals(Boolean.TRUE,child!=null);
		
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void assertSourceDisjoint(){
		final Master m9=new Master(),m10=new Master();
		final Child c1=new Child(),c2=new Child(),c3=new Child(),c4=new Child(),c5=new Child(),c6=new Child(),c7=new Child(),c8=new Child(),c9=new Child(),c10=new Child();
		final Master m11=new Master(),m12=new Master(),m13=new Master();
		final Child c11=new Child(),c12=new Child(),c13=new Child();
		final SelectItem s1 = new SelectItem(m11, "M11"),s2 = new SelectItem(m12, "M12"),s3 = new SelectItem(m13, "M13");
		CollectionHelper.Instance<Child> collection = CollectionHelper.getInstance().getCollectionInstance(Child.class);
		collection.addListener(new CollectionHelper.Instance.Listener.Adapter<Child>(){
			private static final long serialVersionUID = 1L;
			
			@Override
			public Boolean isInstanciatable(Instance<Child> instance, Object object) {
				return object instanceof Child || object instanceof Master;
			}
			
			@Override
			public Child instanciate(Instance<Child> instance, Object object) {
				if(object instanceof Master){
					if(object==m9)
						return c9;
					if(object==m10)
						return c10;
					if(object==m11)
						return c11;
					if(object==m12)
						return c12;
					if(object==m13)
						return c13;
				}
				return super.instanciate(instance, object);
			}
			
			@Override
			public Boolean isHasSource(Instance<Child> instance, Child element) {
				return element == c1 || element == c2 || element == c3 || element == c4 || element == c5 || element == c6 || element == c9 || element == c10;
			}
			
			@Override
			public Object getSource(Instance<Child> instance, Object object) {
				if(object==c9)
					return m9;
				if(object==c10)
					return m10;
				if(object==m11)
					return s1;
				if(object==m12)
					return s2;
				if(object==m13)
					return s3;
				return super.getSource(instance, object);
			}
		});
		collection.setSources(new ArrayList<Child>(Arrays.asList(c1,c2,c3,c4,c5,c6)));
		collection.getSources().addAll(Arrays.asList(s1));
		collection.addOne(c7);
		assertList((List<?>) collection.getElements(), Arrays.asList(c7));
		assertList((List<?>) collection.getSources(), Arrays.asList(c1,c2,c3,c4,c5,c6,s1));
		collection.addOne(c8);
		assertList((List<?>) collection.getElements(), Arrays.asList(c7,c8));
		assertList((List<?>) collection.getSources(), Arrays.asList(c1,c2,c3,c4,c5,c6,s1));
		collection.addOne(c2);
		assertList((List<?>) collection.getElements(), Arrays.asList(c7,c8,c2));
		assertList((List<?>) collection.getSources(), Arrays.asList(c1,c3,c4,c5,c6,s1));
		collection.addOne(c6);
		assertList((List<?>) collection.getElements(), Arrays.asList(c7,c8,c2,c6));
		assertList((List<?>) collection.getSources(), Arrays.asList(c1,c3,c4,c5,s1));
		collection.addOne(c3);
		assertList((List<?>) collection.getElements(), Arrays.asList(c7,c8,c2,c6,c3));
		assertList((List<?>) collection.getSources(), Arrays.asList(c1,c4,c5,s1));
		collection.removeOne(c2);
		assertList((List<?>) collection.getElements(), Arrays.asList(c7,c8,c6,c3));
		assertList((List<?>) collection.getSources(), Arrays.asList(c1,c4,c5,s1,c2));
		collection.removeOne(c7);
		assertList((List<?>) collection.getElements(), Arrays.asList(c8,c6,c3));
		assertList((List<?>) collection.getSources(), Arrays.asList(c1,c4,c5,s1,c2));
		collection.addOne(m10);
		assertList((List<?>) collection.getElements(), Arrays.asList(c8,c6,c3,c10));
		assertList((List<?>) collection.getSources(), Arrays.asList(c1,c4,c5,s1,c2));
		collection.removeOne(c10);
		assertList((List<?>) collection.getElements(), Arrays.asList(c8,c6,c3));
		assertList((List<?>) collection.getSources(), Arrays.asList(c1,c4,c5,s1,c2,m10));
		collection.addOne(m11);
		assertList((List<?>) collection.getElements(), Arrays.asList(c8,c6,c3,c11));
		assertList((List<?>) collection.getSources(), Arrays.asList(c1,c4,c5,c2,m10));
	}
	
	@Test
	public void assertSourceObject(){
		final Master ov1=new Master(),ov2=new Master(),ov3=new Master(),ov4=new Master(),ov5=new Master(),ov6=new Master(),ov7=new Master();
		final ChildElement c1=new ChildElement(),c2=new ChildElement(),c3=new ChildElement(),c4=new ChildElement(),c5=new ChildElement(),c6=new ChildElement(),c7=new ChildElement();
		final SelectItem s1 = new SelectItem(ov1, "OV1"),s2 = new SelectItem(ov2, "OV2"),s3 = new SelectItem(ov3, "OV3"),s4 = new SelectItem(ov4, "OV4")
				,s5 = new SelectItem(ov5, "OV5"),s6 = new SelectItem(ov6, "OV6"),s7 = new SelectItem(ov7, "OV7");
		CollectionHelper.Instance<ChildElement> collection = CollectionHelper.getInstance().getCollectionInstance(ChildElement.class,Child.class,SelectItem.class,Master.class);
		collection.addListener(new CollectionHelper.Instance.Listener.Adapter<ChildElement>(){
			private static final long serialVersionUID = 1L;
			
			@Override
			public ChildElement instanciate(Instance<ChildElement> instance, Object object) {
				if(object instanceof Master){
					if(object==ov1)
						return c1;
					if(object==ov2)
						return c2;
					if(object==ov3)
						return c3;
					if(object==ov4)
						return c4;
					if(object==ov5)
						return c5;
					if(object==ov6)
						return c6;
					if(object==ov7)
						return c7;
				}
				return super.instanciate(instance, object);
			}
		});
		collection.setSources(new ArrayList<SelectItem>(Arrays.asList(s1,s2,s3,s4,s5,s6,s7))).setIsEachElementHasSource(Boolean.TRUE);
		collection.setGetSourceObjectMethodName("getValue");
		collection.addOne(ov1);
		assertList((List<?>) collection.getElements(), Arrays.asList(c1));
		assertList((List<?>) collection.getSources(), Arrays.asList(s2,s3,s4,s5,s6,s7));
		collection.addOne(ov7);
		assertList((List<?>) collection.getElements(), Arrays.asList(c1,c7));
		assertList((List<?>) collection.getSources(), Arrays.asList(s2,s3,s4,s5,s6));
		collection.addOne(ov4);
		assertList((List<?>) collection.getElements(), Arrays.asList(c1,c7,c4));
		assertList((List<?>) collection.getSources(), Arrays.asList(s2,s3,s5,s6));
		collection.removeOne(c7);
		assertList((List<?>) collection.getElements(), Arrays.asList(c1,c4));
		assertList((List<?>) collection.getSources(), Arrays.asList(s2,s3,s5,s6,s7));
		collection.removeOne(c1);
		assertList((List<?>) collection.getElements(), Arrays.asList(c4));
		assertList((List<?>) collection.getSources(), Arrays.asList(s2,s3,s5,s6,s7,s1));
		collection.addOne(ov5);
		assertList((List<?>) collection.getElements(), Arrays.asList(c4,c5));
		assertList((List<?>) collection.getSources(), Arrays.asList(s2,s3,s6,s7,s1));	
	}
	
	@Test
	public void assertElementObjetAndObjectFieldsCopy(){
		final Master master1=new Master(),master2=new Master(),master3=new Master(),master4=new Master(),master5=new Master(),master6=new Master(),master7=new Master();
		final Child child1=new Child().setC1("IEC1").setC2(951).setCc1("vcc1").setCc2(789),child2=new Child(),child3=new Child(),child4=new Child(),child5=new Child(),child6=new Child(),child7=new Child();
		final ChildElement element1=new ChildElement(child1),element2=new ChildElement(child2),element3=new ChildElement(child3),element4=new ChildElement(child4)
				,element5=new ChildElement(child5),element6=new ChildElement(child6),element7=new ChildElement(child7);
		final SelectItem selectItem1 = new SelectItem(master1, "OV1"),selectItem2 = new SelectItem(master2, "OV2"),selectItem3 = new SelectItem(master3, "OV3")
				,selectItem4 = new SelectItem(master4, "OV4"),selectItem5 = new SelectItem(master5, "OV5"),selectItem6 = new SelectItem(master6, "OV6")
				,selectItem7 = new SelectItem(master7, "OV7");
		CollectionHelper.Instance<ChildElement> collection = CollectionHelper.getInstance().getCollectionInstance(ChildElement.class,Child.class,SelectItem.class,Master.class);
		collection.addListener(new CollectionHelper.Instance.Listener.Adapter<ChildElement>(){
			private static final long serialVersionUID = 1L;
			
			@Override
			public ChildElement instanciate(Instance<ChildElement> instance, Object object) {
				if(object instanceof Master){
					if(object==master1)
						return element1;
					if(object==master2)
						return element2;
					if(object==master3)
						return element3;
					if(object==master4)
						return element4;
					if(object==master5)
						return element5;
					if(object==master6)
						return element6;
					if(object==master7)
						return element7;
				}
				return super.instanciate(instance, object);
			}
		});
		collection.setSources(new ArrayList<SelectItem>(Arrays.asList(selectItem1,selectItem2,selectItem3,selectItem4,selectItem5,selectItem6,selectItem7)))
			.setIsEachElementHasSource(Boolean.TRUE);
		collection.setGetSourceObjectMethodName("getValue");
		collection.mapElementFieldsAndElementObjectFields("ce1","c1","ce2","c2").mapElementObjectFields("cc1","cc2");
		collection.addOne(master1);
		assertList((List<?>) collection.getElements(), Arrays.asList(element1));
		assertList((List<?>) collection.getSources(), Arrays.asList(selectItem2,selectItem3,selectItem4,selectItem5,selectItem6,selectItem7));	
		assertEquals(child1,element1.getObject());
		
		collection.read();
		assertEquals("IEC1", element1.getCe1());
		assertEquals(951, element1.getCe2());
		assertEquals("vcc1", element1.getCc1());
		assertEquals(789, element1.getCc2());
		
		element1.setCe1("eC1").setCe2(12).setCc1("nvCC1").setCc2(456);
		
		collection.write();
		assertEquals("eC1", child1.getC1());
		assertEquals(12, child1.getC2());
		assertEquals("nvCC1", child1.getCc1());
		assertEquals(456, child1.getCc2());
	}
	
	@Test
	public void listen(){
		CollectionHelper.Instance<Child> collection = new CollectionHelper.Instance<Child>().setElementClass(Child.class);
		collection.addListener(new CollectionHelper.Instance.Listener.Adapter<Child>(){
			private static final long serialVersionUID = 1L;
			
			@Override
			public Boolean isInstanciatable(Instance<Child> instance, Object object) {
				return object instanceof Child || object instanceof Master;
			}
			
			@Override
			public Child instanciate(Instance<Child> instance, Object object) {
				if(object instanceof Master)
					return new Child();
				return super.instanciate(instance, object);
			}
		});
		
		Child c1=new Child(),c2=new Child(),c3=new Child();
		c1.setC1("m159");
		Master m1 = new Master(),m2 = new Master(),m3 = new Master();
		
		collection.addOne(c1);
		assertEquals(1, collection.getElements().size());
		collection.addOne(m1);
		assertEquals(2, collection.getElements().size());
		collection.removeOne(c1);
		assertEquals(1, collection.getElements().size());
		collection.addMany(Arrays.asList(c1,c2,c3));
		assertEquals(4, collection.getElements().size());
		collection.addMany(Arrays.asList(m2,m3));
		assertEquals(6, collection.getElements().size());
	}
	
	@Test
	public void filter(){
		List<A> data = Arrays.asList(
				new A("A",1,"A1")
				,new A("B",2,"A2")
				,new A("C",3,"C3")
				,new A("D",4,"D4")
				,new A("D1",4,"D14")
				,new A("E",5,"E5")
				,new A(null,5,"E5")
				,new A("Z",null,"E58888")
				,new A(null,545,"E5df")
				,new A("456123",545,null)
		);
		assertFilter(data, "f1", "0", null);
		
		assertFilter(data, "f1", "A", Arrays.asList(data.get(0)));
		
		assertFilter(data, "f2", 4, Arrays.asList(data.get(3),data.get(4)));
		
		assertFilter(data, "f1", null, Arrays.asList(data.get(6),data.get(8)));
		
		assertFilter(data, "f2", null, Arrays.asList(data.get(7)));
		
		assertFilter(data, "f3", null, Arrays.asList(data.get(9)));
		
		List<Object> data2 = Arrays.asList(
				new A()
				,new A1()
				,new A2()
				,new A3()
				,new A4()
				,new A1()
				,new A2()
				,new A3()
				,new A2()
				,new A2()
		);
		assertFilter(data2, A2.class, Arrays.asList(data2.get(2),data2.get(6),data2.get(8),data2.get(9)));
		
		assertEquals(CollectionHelper.getInstance().getElementAt(data2, 0), data2.get(0));
		assertEquals(CollectionHelper.getInstance().getElementAt(data2, 3), data2.get(3));
		assertEquals(CollectionHelper.getInstance().getElementAt(data2, 9), data2.get(9));
		
		assertList((List<?>) CollectionHelper.getInstance().removeElementAt(new ArrayList<>(data2), 0), Arrays.asList(data2.get(1),data2.get(2),data2.get(3),data2.get(4),data2.get(5),data2.get(6),data2.get(7),data2.get(8),data2.get(9)));
		assertList((List<?>) CollectionHelper.getInstance().removeElementAt(new ArrayList<>(data2), 1), Arrays.asList(data2.get(0),data2.get(2),data2.get(3),data2.get(4),data2.get(5),data2.get(6),data2.get(7),data2.get(8),data2.get(9)));
	
		assertList((List<?>) CollectionHelper.getInstance().removeElement(new ArrayList<>(data2), data2.get(0)), Arrays.asList(data2.get(1),data2.get(2),data2.get(3),data2.get(4),data2.get(5),data2.get(6),data2.get(7),data2.get(8),data2.get(9)));
		assertList((List<?>) CollectionHelper.getInstance().removeElement(new ArrayList<>(data2), data2.get(1)), Arrays.asList(data2.get(0),data2.get(2),data2.get(3),data2.get(4),data2.get(5),data2.get(6),data2.get(7),data2.get(8),data2.get(9)));
	}
	
	private <T> void assertFilter(Collection<T> collection,Class<?> aClass,String fieldName,Object fieldValue,Collection<T> expected){
		Collection<T> result = new CollectionHelper.Filter.Adapter.Default<T>(collection).setProperty(CollectionHelper.Filter.PROPERTY_NAME_FIELD_NAME, fieldName)
				.setProperty(CollectionHelper.Filter.PROPERTY_NAME_FIELD_VALUE, fieldValue)
				.setProperty(CollectionHelper.Filter.PROPERTY_NAME_CLASS, aClass)
				.execute();
		assertList((List<?>)result, (List<?>)expected);
	}
	
	private <T> void assertFilter(Collection<T> collection,String fieldName,Object fieldValue,Collection<T> expected){
		assertFilter(collection, null, fieldName, fieldValue, expected);
	}
	
	private <T> void assertFilter(Collection<T> collection,Class<?> aClass,Collection<T> expected){
		assertFilter(collection, aClass, null, null, expected);
	}
	
	/**/
	
	@AllArgsConstructor @NoArgsConstructor @Getter @Setter
	public static class Master {
		
		private String m1;

		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
		}
	}
	
	@AllArgsConstructor @NoArgsConstructor @Getter @Setter
	public static class SelectItem {
		
		private Object value;
		private String name;

		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
		}
	}
	
	@AllArgsConstructor @NoArgsConstructor @Getter @Setter @Accessors(chain=true)
	public static class Child implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private String c1;
		private Integer c2;
		private String cc1;
		private Integer cc2;

		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
		}
	}
	
	@NoArgsConstructor @Getter @Setter @Accessors(chain=true)
	public static class ChildElement extends CollectionHelper.Element<Child> {
		private static final long serialVersionUID = 1L;
		
		private String ce1;
		private Integer ce2;
		private String cc1;
		private Integer cc2;
		
		public ChildElement(Child child){
			setObject(child);
		}
				
	}
	
	@AllArgsConstructor @NoArgsConstructor @Getter @Setter
	public static class A {
		
		private String f1;
		private Integer f2;
		private String f3;
		
		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
		}
	}
	
	@AllArgsConstructor @Getter @Setter public static class A1 {}
	@AllArgsConstructor @Getter @Setter public static class A2 {}
	@AllArgsConstructor @Getter @Setter public static class A3 {}
	@AllArgsConstructor @Getter @Setter public static class A4 {}
	
	@Getter @Setter @Accessors(chain=true) public static class Sortable extends AbstractBean {
		private static final long serialVersionUID = 1L;
		
		private String string1,string2;
		
		@Override
		public String toString() {
			return string1+","+string2+","+orderNumber;
		}
	}
}
