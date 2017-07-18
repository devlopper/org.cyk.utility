package org.cyk.utility.common;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class CollectionHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@Test
	public void filter(){
		List<A> data = Arrays.asList(
				new A("A",1,"A1")
				,new A("B",2,"A2")
				,new A("C",3,"C3")
				,new A("D",4,"D4")
				,new A("D1",4,"D14")
				,new A("E",5,"E5")
		);
		assertFilter(data, "f1", "0", null);
		
		assertFilter(data, "f1", "A", Arrays.asList(data.get(0)));
		
		assertFilter(data, "f2", 4, Arrays.asList(data.get(3),data.get(4)));
	}
	
	private <T> void assertFilter(Collection<T> collection,String fieldName,Object fieldValue,Collection<T> expected){
		Collection<T> result = new CollectionHelper.Filter.Adapter.Default<T>(collection).setProperty(CollectionHelper.Filter.PROPERTY_NAME_FIELD_NAME, fieldName)
				.setProperty(CollectionHelper.Filter.PROPERTY_NAME_FIELD_VALUE, fieldValue)
				.execute();
		assertList((List<?>)result, (List<?>)expected);
	}
	
	/**/
	
	@AllArgsConstructor @Getter @Setter
	public static class A {
		
		private String f1;
		private Integer f2;
		private String f3;
	}
}
