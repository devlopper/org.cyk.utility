package org.cyk.utility.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.ThreadHelper;
import org.cyk.utility.common.helper.ThreadHelper.Duration;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

import lombok.Getter;
import lombok.Setter;

public class ThreadHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
		
	@Test
	public void noException(){
		ThreadHelper.Executor executor = new ThreadHelper.Executor.Adapter.Default();
		executor.setCorePoolSize(4).setMaximumPoolSize(10).setWorkQueueInitialSize(5).setKeepAliveDuration(new Duration(5l, TimeUnit.SECONDS))
			.setMaximumDuration(new Duration(35l, TimeUnit.SECONDS)).addThrowableClasses(RuntimeException.class)
			.addExecutables(new Work("Work 1"),new Work("Work 2"),new Work("Work 3"));
		executor.execute();
		/*
		Collection<Class<?>> classes = new ArrayList<>();
		classes.add(RuntimeException.class); 
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 10, 5l, TimeUnit.SECONDS, 5,35l,TimeUnit.SECONDS,classes);
		threadPoolExecutor.execute(new Work("Work 1"));
		threadPoolExecutor.execute(new Work("Work 2"));
		threadPoolExecutor.execute(new Work("Work 3"));
		
		threadPoolExecutor.waitTermination();
		*/
		assertEquals(3, CollectionHelper.getInstance().getSize(executor.getInput()));
		assertEquals(0, CollectionHelper.getInstance().getSize(executor.getWhereThrowableInstanceOf()));
		assertEquals(3, CollectionHelper.getInstance().getSize(executor.getWhereThrowableIsNull()));
		assertEquals(0, CollectionHelper.getInstance().getSize(executor.getWhereThrowableIsNotNull()));
	}
	
	@Test
	public void withException(){
		Collection<Class<?>> classes = new ArrayList<>();
		classes.add(RuntimeException.class);
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 10, 5l, TimeUnit.SECONDS, 5,35l,TimeUnit.SECONDS,classes);
		threadPoolExecutor.execute(new Work("Work 1"));
		threadPoolExecutor.execute(new Work("Work 2",Boolean.TRUE));
		threadPoolExecutor.execute(new Work("Work 3"));
		
		threadPoolExecutor.waitTermination();
		
		assertEquals(3l, threadPoolExecutor.getNumberOfPlannedTask());
		assertEquals(3l, threadPoolExecutor.getNumberOfExecutedTask());
		assertEquals(1l, threadPoolExecutor.getNumberOfExecutedWhereThrowableIsNotNull());
	}
	
	@Test
	public void withExceptionRunAgain(){
		Collection<Class<?>> classes = new ArrayList<>();
		classes.add(RuntimeException.class);
		Work w2=null;
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 10, 5l, TimeUnit.SECONDS, 5,35l,TimeUnit.SECONDS,classes);
		threadPoolExecutor.execute(new Work("Work 1"));
		threadPoolExecutor.execute(w2=new Work("Work 2",Boolean.TRUE));
		threadPoolExecutor.execute(new Work("Work 3"));
		threadPoolExecutor.waitTermination();
		
		assertEquals(3l, threadPoolExecutor.getNumberOfPlannedTask());
		assertEquals(3l, threadPoolExecutor.getNumberOfExecutedTask());
		assertEquals(1l, threadPoolExecutor.getNumberOfExecutedWhereThrowableIsNotNull());
		
		threadPoolExecutor = ThreadPoolExecutor.createExecutorWhereThrowableInstanceOfOne(threadPoolExecutor,RuntimeException.class);
		threadPoolExecutor.waitTermination();
		
		assertEquals(1l, threadPoolExecutor.getNumberOfPlannedTask());
		assertEquals(1l, threadPoolExecutor.getNumberOfExecutedTask());
		assertEquals(1l, threadPoolExecutor.getNumberOfExecutedWhereThrowableIsNotNull());
		
		((Work)w2).setFail(Boolean.FALSE);
		threadPoolExecutor = ThreadPoolExecutor.createExecutorWhereThrowableInstanceOfOne(threadPoolExecutor,RuntimeException.class);
		threadPoolExecutor.waitTermination();
		
		assertEquals(1l, threadPoolExecutor.getNumberOfPlannedTask());
		assertEquals(1l, threadPoolExecutor.getNumberOfExecutedTask());
		assertEquals(0l, threadPoolExecutor.getNumberOfExecutedWhereThrowableIsNotNull());
	}
	
	@Test
	public void withExceptionRunTillNoThrowable(){
		Collection<Class<?>> classes = new ArrayList<>();
		classes.add(RuntimeException.class);
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 10, 5l, TimeUnit.SECONDS, 5,35l,TimeUnit.SECONDS,classes);
		threadPoolExecutor.execute(new Work("Work 1"));
		threadPoolExecutor.execute(new Work("Work 2",Boolean.TRUE));
		threadPoolExecutor.execute(new Work("Work 3"));
		
		ThreadPoolExecutor.execute(threadPoolExecutor,8l,1000l * 3);
		
	}
	
	/**/
	@Setter @Getter
	public class Work extends ThreadHelper.Executable.Adapter.Default implements Serializable {
		private static final long serialVersionUID = 1L;

		private Boolean fail;
		private int count = 0;
		
		public Work(String name,Boolean fail) {
			setName(name);
			this.fail = fail;
		}
		
		public Work(String name) {
			this(name,Boolean.FALSE);
		}
		
		@Override
		public void __execute__() {
			try {
				Thread.sleep(1000 * 1);
				if(fail == null || fail)
					fail = count++ < 3;
				if(Boolean.TRUE.equals(fail))
					throw new RuntimeException("Fail to run "+getName());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}		
	}
	
}
