package org.cyk.utility.common;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.helper.ThreadHelper;
import org.cyk.utility.common.helper.ThreadHelper.Duration;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

import lombok.Getter;
import lombok.Setter;

public class ThreadHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	static{
		StringHelper.ToStringMapping.Datasource.Adapter.Default.initialize();
	}
	
	@Test
	public void noException(){
		ThreadHelper.Executor executor = new ThreadHelper.Executor.Adapter.Default();
		executor.setCorePoolSize(4).setMaximumPoolSize(10).setWorkQueueInitialSize(5).setKeepAliveDuration(new Duration(10l, TimeUnit.SECONDS))
			.setMaximumDuration(new Duration(35l, TimeUnit.SECONDS)).addThrowableClasses(RuntimeException.class)
			.addExecutables(new Work("Work 1"),new Work("Work 2"),new Work("Work 3"));
		executor.execute();
		assertEquals(3, CollectionHelper.getInstance().getSize(executor.getInput()));
		assertEquals(0, CollectionHelper.getInstance().getSize(executor.getWhereThrowableInstanceOf()));
		assertEquals(3, CollectionHelper.getInstance().getSize(executor.getWhereThrowableIsNull()));
		assertEquals(0, CollectionHelper.getInstance().getSize(executor.getWhereThrowableIsNotNull()));
	}
	
	@Test
	public void withException(){
		ThreadHelper.Executor executor = new ThreadHelper.Executor.Adapter.Default();
		executor.setCorePoolSize(4).setMaximumPoolSize(10).setWorkQueueInitialSize(5).setKeepAliveDuration(new Duration(10l, TimeUnit.SECONDS))
			.setMaximumDuration(new Duration(35l, TimeUnit.SECONDS)).addThrowableClasses(RuntimeException.class)
			.addExecutables(new Work("Work 1"),new Work("Work 2",1),new Work("Work 3"));
		executor.execute();
		assertEquals(3, CollectionHelper.getInstance().getSize(executor.getInput()));
		assertEquals(1, CollectionHelper.getInstance().getSize(executor.getWhereThrowableInstanceOf()));
		assertEquals(2, CollectionHelper.getInstance().getSize(executor.getWhereThrowableIsNull()));
		assertEquals(1, CollectionHelper.getInstance().getSize(executor.getWhereThrowableIsNotNull()));
	}
	
	@Test
	public void withExceptionRunTwice(){
		final ThreadHelper.Executor executor = new ThreadHelper.Executor.Adapter.Default();
		executor.setCorePoolSize(4).setMaximumPoolSize(10).setWorkQueueInitialSize(5).setKeepAliveDuration(new Duration(10l, TimeUnit.SECONDS))
			.setMaximumDuration(new Duration(35l, TimeUnit.SECONDS)).addThrowableClasses(RuntimeException.class)
			.addExecutables(new Work("Work 1"),new Work("Work 2",1),new Work("Work 3"));
		executor.setMaximumNumberOfExecution(2l).execute();
		/*assertEquals(3, CollectionHelper.getInstance().getSize(executor.getInput()));
		assertEquals(1, CollectionHelper.getInstance().getSize(executor.getWhereThrowableInstanceOf()));
		assertEquals(2, CollectionHelper.getInstance().getSize(executor.getWhereThrowableIsNull()));
		assertEquals(1, CollectionHelper.getInstance().getSize(executor.getWhereThrowableIsNotNull()));
		*/
		//executor.setMaximumNumberOfExecution(2l).execute();

		assertEquals(3, CollectionHelper.getInstance().getSize(executor.getInput()));
		assertEquals(0, CollectionHelper.getInstance().getSize(executor.getWhereThrowableInstanceOf()));
		assertEquals(3, CollectionHelper.getInstance().getSize(executor.getWhereThrowableIsNull()));
		assertEquals(0, CollectionHelper.getInstance().getSize(executor.getWhereThrowableIsNotNull()));
		
	}
	
	@Test
	public void withExceptionRunTillNoThrowable(){
		ThreadHelper.Executor executor = new ThreadHelper.Executor.Adapter.Default();
		executor.setCorePoolSize(4).setMaximumPoolSize(10).setWorkQueueInitialSize(5).setKeepAliveDuration(new Duration(10l, TimeUnit.SECONDS))
			.setMaximumDuration(new Duration(35l, TimeUnit.SECONDS)).addThrowableClasses(RuntimeException.class)
			.addExecutables(new Work("Work 1"),new Work("Work 2",4),new Work("Work 3"));
		
		executor.setMaximumNumberOfExecution(7l).setNumberOfMillisecondBeforeReexecuting(3000l).execute();
		
		assertEquals(3, CollectionHelper.getInstance().getSize(executor.getInput()));
		assertEquals(0, CollectionHelper.getInstance().getSize(executor.getWhereThrowableInstanceOf()));
		assertEquals(3, CollectionHelper.getInstance().getSize(executor.getWhereThrowableIsNull()));
		assertEquals(0, CollectionHelper.getInstance().getSize(executor.getWhereThrowableIsNotNull()));
	
	}
	
	/**/
	@Setter @Getter
	public class Work extends ThreadHelper.Executable.Adapter.Default implements Serializable {
		private static final long serialVersionUID = 1L;

		private Integer count = 0;
		private Integer succedAfterCount;
		
		public Work(String name,Integer succedAfterCount) {
			setName(name);
			this.succedAfterCount = succedAfterCount;
		}
		
		public Work(String name) {
			this(name,null);
		}
		
		@Override
		public void ____execute____() {
			try {
				Thread.sleep(1000 * 2);
				if(succedAfterCount != null && count++<succedAfterCount)
					throw new RuntimeException("Fail to run "+getName());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}		
	}
	
}
