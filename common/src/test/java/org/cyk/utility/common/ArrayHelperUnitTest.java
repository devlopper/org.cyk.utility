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
	
	
	
}
