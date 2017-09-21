package org.cyk.utility.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.CollectionHelper.Instance;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CollectionHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
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
	
	@AllArgsConstructor @NoArgsConstructor @Getter @Setter
	public static class Child {
		
		private String c1;

		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
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
}
