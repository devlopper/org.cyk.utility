package org.cyk.utility.common;

import org.cyk.utility.common.helper.ArrayHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class ArrayHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@Test
	public void buildKey(){
		assertNull(new ArrayHelper.Dimension.Key.Builder.Adapter.Default(new Object[]{}).execute().getValue());
		assertNull(new ArrayHelper.Dimension.Key.Builder.Adapter.Default(new Object[]{"A","1","B","2","C"}).execute().getValue());
		assertNull(new ArrayHelper.Dimension.Key.Builder.Adapter.Default(new Object[]{}).addManyParameters(0).execute().getValue());
		assertEquals("A", new ArrayHelper.Dimension.Key.Builder.Adapter.Default(new Object[]{"A","1","B","2","C"}).addManyParameters(0).execute().getValue());
		assertEquals("1", new ArrayHelper.Dimension.Key.Builder.Adapter.Default(new Object[]{"A","1","B","2","C"}).addManyParameters(1).execute().getValue());
		assertEquals("B", new ArrayHelper.Dimension.Key.Builder.Adapter.Default(new Object[]{"A","1","B","2","C"}).addManyParameters(2).execute().getValue());
		assertEquals("2", new ArrayHelper.Dimension.Key.Builder.Adapter.Default(new Object[]{"A","1","B","2","C"}).addManyParameters(3).execute().getValue());
		assertEquals("C", new ArrayHelper.Dimension.Key.Builder.Adapter.Default(new Object[]{"A","1","B","2","C"}).addManyParameters(4).execute().getValue());
		
		assertEquals("A1", new ArrayHelper.Dimension.Key.Builder.Adapter.Default(new Object[]{"A","1","B","2","C"}).addManyParameters(0,1).execute().getValue());
		assertEquals("AB", new ArrayHelper.Dimension.Key.Builder.Adapter.Default(new Object[]{"A","1","B","2","C"}).addManyParameters(0,2).execute().getValue());
		assertEquals("12", new ArrayHelper.Dimension.Key.Builder.Adapter.Default(new Object[]{"A","1","B","2","C"}).addManyParameters(1,3).execute().getValue());
		
		assertEquals("CB", new ArrayHelper.Dimension.Key.Builder.Adapter.Default(new Object[]{"A","1","B","2","C"}).addManyParameters(4,2).execute().getValue());
	}
	
	@Test
	public void filter(){
		Object[][] data = new Object[][] {
				{"A","1","A1"}
				,{"B","2","A2"}
				,{"C","3","C3"}
				,{"D","4","D4"}
				,{"D1","4","D14"}
				,{"E","5","E5"}
		};
		assertFilter(data, 0, "0", null);
		
		assertFilter(data, 0, "A", new Object[][] {
				{"A","1","A1"}
		});
		
		assertFilter(data, 1, "4", new Object[][] {
				{"D","4","D4"}
				,{"D1","4","D14"}
		});
	}
	
	private void assertFilter(Object[][] array,Integer index,String value,Object[][] expected){
		Object[][] result = ArrayHelper.getInstance().filter(array, index, value);
		assertArray(result, expected);
	}
}
