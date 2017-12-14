package org.cyk.utility.common.filter;

import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CriteriaHelper;
import org.cyk.utility.common.helper.FilterHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class FilterHelperUnitTest extends AbstractUnitTest {
	private static final long serialVersionUID = -6691092648665798471L;
	
	static {
		ClassHelper.getInstance().map(ClassHelper.Listener.class, ClassHelperAdapter.class);
	}
	
	@Test
	public void assertNull(){
		assertEquals(Boolean.TRUE, new FilterHelper.Filter<Object>().addCriterias(new CriteriaHelper.Criteria.String()).isNull());
		assertEquals(Boolean.TRUE, new FilterHelper.Filter<Object>().addCriterias(new CriteriaHelper.Criteria.String()
				,new CriteriaHelper.Criteria.String().set("")).isNull());
		assertEquals(Boolean.TRUE, new FilterHelper.Filter<Object>().addCriterias(new CriteriaHelper.Criteria.String()
				,new CriteriaHelper.Criteria.String().set(" ")).isNull());
		assertEquals(Boolean.FALSE, new FilterHelper.Filter<Object>().addCriterias(new CriteriaHelper.Criteria.String()
				,new CriteriaHelper.Criteria.String().set("maval")).isNull());
	}
	
	@Test
	public void getClassUsingLocator(){
		assertEquals(Model1.Filter.class, FilterHelper.Filter.ClassLocator.getInstance().locate(Model1.class));
	}

	/**/
	
	
	
	public static class ClassHelperAdapter extends ClassHelper.Listener.Adapter.Default {
		private static final long serialVersionUID = 1L;
		
		@Override
		public Boolean isModel(Class<?> aClass) {
			return Boolean.TRUE;
		}
		
		@Override
		public Boolean isPersisted(Class<?> aClass) {
			return Boolean.TRUE;
		}
		
	}
}
