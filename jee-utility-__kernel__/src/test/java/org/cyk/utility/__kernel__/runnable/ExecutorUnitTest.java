package org.cyk.utility.__kernel__.runnable;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class ExecutorUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void executor_noThread_noExecutorService(){
		Executor<Runnable> executor = new Executor<Runnable>().setName("MyExecutor").setNumberOfRunnablesToBeExecuted(3).setListener(new ExecutorListenerImpl());
		executor.addRunnables(new Runnable("Worker1",2000l),new Runnable("Worker2",3000l),new Runnable("Worker3",1000l));
		executor.run();
		assertThat(CollectionHelper.getSize(executor.getRunnables())).isEqualTo(0);
	}
	
	@Test
	public void executor_thread_noExecutorService(){
		Executor<Runnable> executor = new Executor<Runnable>().setName("MyExecutor").setNumberOfRunnablesToBeExecuted(3).setListener(new ExecutorListenerImpl());
		executor.start();
		executor.addRunnables(new Runnable("Worker1",2000l));
		executor.addRunnables(new Runnable("Worker2",3000l));
		executor.addRunnables(new Runnable("Worker3",1000l));
		executor.join();
		assertThat(CollectionHelper.getSize(executor.getRunnables())).isEqualTo(0);
	}
	
	@Test
	public void executor_noThread_executorService(){
		Executor<Runnable> executor = new Executor<Runnable>().setName("MyExecutor").setNumberOfRunnablesToBeExecuted(3)
				.setExecutorService(RunnableHelper.instantiateExecutorService(2)).setListener(new ExecutorListenerImpl());
		executor.addRunnables(new Runnable("Worker1",2000l),new Runnable("Worker2",3000l),new Runnable("Worker3",1000l));
		executor.run();
		assertThat(CollectionHelper.getSize(executor.getRunnables())).isEqualTo(0);
	}
	
	@Test
	public void executor_thread_executorService(){
		Executor<Runnable> executor = new Executor<Runnable>().setName("MyExecutor").setNumberOfRunnablesToBeExecuted(3)
				.setExecutorService(RunnableHelper.instantiateExecutorService(2)).setListener(new ExecutorListenerImpl())
				.setTimeout(30l).setTimeoutUnit(TimeUnit.SECONDS);
		executor.start();
		executor.addRunnables(new Runnable("Worker1",2000l));
		executor.addRunnables(new Runnable("Worker2",6000l));
		executor.addRunnables(new Runnable("Worker3",1000l));
		executor.join();
		assertThat(CollectionHelper.getSize(executor.getRunnables())).isEqualTo(0);
	}
	
	@Test
	public void executor_noThread_executorServiceMany(){
		Executor<Runnable> executor = new Executor<Runnable>().setName("MyExecutor").setNumberOfRunnablesToBeExecuted(3)
				.setExecutorService(RunnableHelper.instantiateExecutorService(2,4,null,null,null,2000,null,null)).setListener(new ExecutorListenerImpl());
		for(Integer index = 0; index < 2000; index++)
			executor.addRunnables(new Runnable("Worker"+index,5l));
		executor.run();
		assertThat(CollectionHelper.getSize(executor.getRunnables())).isEqualTo(0);
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true) @AllArgsConstructor
	public static class Runnable implements java.lang.Runnable {
		
		private String name;
		private Long duration;
		
		@Override
		public void run() {
			if(duration != null && duration > 0)
				try {
					Thread.sleep(duration);
				} catch (InterruptedException e) {
					if(StringHelper.isNotBlank(name))
						System.out.println("Work "+name+" interrupted.");
					else
						e.printStackTrace();
				}
		}		
	}
	
	public static class ExecutorListenerImpl extends Executor.Listener.AbstractImpl<Runnable> {
		@Override
		public void listenStart(Executor<Runnable> executor, Runnable runnable) {
			System.out.println("START From Listener!!! "+runnable.name);
		}
		@Override
		public void listenCompletion(Executor<Runnable> executor, Runnable runnable) {
			System.out.println("DONE From Listener!!! "+runnable.name);
		}		
	}
}