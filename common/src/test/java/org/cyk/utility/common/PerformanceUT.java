package org.cyk.utility.common;

import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class PerformanceUT extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;

	@Test
	public void callActionExecute_1_all(){
		for(Integer index = 0 ; index < 1 ; index++)
			new MyAction().execute();
	}
	
	@Test
	public void callActionExecute_10_all(){
		for(Integer index = 0 ; index < 10 ; index++)
			new MyAction().execute();
	}
	
	@Test
	public void callActionExecute_100_all(){
		for(Integer index = 0 ; index < 100 ; index++)
			new MyAction().execute();
	}
	
	@Test
	public void callActionExecute_1000_all(){
		for(Integer index = 0 ; index < 1000 ; index++)
			new MyAction().execute();
	}
	
	@Test
	public void callActionExecute_10000_all(){
		for(Integer index = 0 ; index < 10000 ; index++)
			new MyAction().execute();
	}
	
	@Test
	public void callActionExecute_100000_all(){
		for(Integer index = 0 ; index < 100000 ; index++)
			new MyAction().execute();
	}
	
	@Test
	public void callActionExecute_1000000_all(){
		for(Integer index = 0 ; index < 1000000 ; index++)
			new MyAction().execute();
	}
	
	@Test
	public void callActionExecute_1_not_all(){
		for(Integer index = 0 ; index < 1 ; index++)
			new MyAction().setIsProduceOutputOnly(Boolean.TRUE).execute();
	}
	
	@Test
	public void callActionExecute_10_not_all(){
		for(Integer index = 0 ; index < 10 ; index++)
			new MyAction().setIsProduceOutputOnly(Boolean.TRUE).execute();
	}
	
	@Test
	public void callActionExecute_100_not_all(){
		for(Integer index = 0 ; index < 100 ; index++)
			new MyAction().setIsProduceOutputOnly(Boolean.TRUE).execute();
	}
	
	@Test
	public void callActionExecute_1000_not_all(){
		for(Integer index = 0 ; index < 1000 ; index++)
			new MyAction().setIsProduceOutputOnly(Boolean.TRUE).execute();
	}
	
	@Test
	public void callActionExecute_10000_not_all(){
		for(Integer index = 0 ; index < 10000 ; index++)
			new MyAction().setIsProduceOutputOnly(Boolean.TRUE).execute();
	}
	
	@Test
	public void callActionExecute_100000_not_all(){
		for(Integer index = 0 ; index < 100000 ; index++)
			new MyAction().setIsProduceOutputOnly(Boolean.TRUE).execute();
	}
	
	@Test
	public void callActionExecute_1000000_not_all(){
		for(Integer index = 0 ; index < 1000000 ; index++)
			new MyAction().setIsProduceOutputOnly(Boolean.TRUE).execute();
	}
	
	/**/
	
	public static class MyAction extends Action.Adapter.Default<Object, Object> {
		private static final long serialVersionUID = 1L;

		public MyAction() {
			super("myaction", Object.class, new Object(), Object.class);
		}
		
		@Override
		protected Object __execute__() {
			return null;
		}
		
	}
}
