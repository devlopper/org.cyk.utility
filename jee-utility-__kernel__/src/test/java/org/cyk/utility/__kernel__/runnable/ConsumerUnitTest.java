package org.cyk.utility.__kernel__.runnable;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class ConsumerUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void consume_noThread_noExecutorService(){
		Consumer consumer = new Consumer().setName("MyConsumer").setNumberOfRunnablesToBeExecuted(3).setListener(new ConsumerListenerImpl());
		consumer.addRunnables(new Runnable("Worker1",2000l),new Runnable("Worker2",3000l),new Runnable("Worker3",1000l));
		consumer.run();
		assertThat(CollectionHelper.getSize(consumer.getRunnables())).isEqualTo(0);
	}
	
	@Test
	public void consume_thread_noExecutorService(){
		Consumer consumer = new Consumer().setName("MyConsumer").setNumberOfRunnablesToBeExecuted(3).setListener(new ConsumerListenerImpl());
		consumer.start();
		consumer.addRunnables(new Runnable("Worker1",2000l));
		consumer.addRunnables(new Runnable("Worker2",3000l));
		consumer.addRunnables(new Runnable("Worker3",1000l));
		consumer.join();
		assertThat(CollectionHelper.getSize(consumer.getRunnables())).isEqualTo(0);
	}
	
	@Test
	public void consume_noThread_executorService(){
		Consumer consumer = new Consumer().setName("MyConsumer").setNumberOfRunnablesToBeExecuted(3)
				.setExecutorService(RunnableHelper.instantiateExecutorService(2)).setListener(new ConsumerListenerImpl());
		consumer.addRunnables(new Runnable("Worker1",2000l),new Runnable("Worker2",3000l),new Runnable("Worker3",1000l));
		consumer.run();
		assertThat(CollectionHelper.getSize(consumer.getRunnables())).isEqualTo(0);
	}
	
	@Test
	public void consume_thread_executorService(){
		Consumer consumer = new Consumer().setName("MyConsumer").setNumberOfRunnablesToBeExecuted(3)
				.setExecutorService(RunnableHelper.instantiateExecutorService(2)).setListener(new ConsumerListenerImpl())
				.setTimeout(30l).setTimeoutUnit(TimeUnit.SECONDS);
		consumer.start();
		consumer.addRunnables(new Runnable("Worker1",2000l));
		consumer.addRunnables(new Runnable("Worker2",6000l));
		consumer.addRunnables(new Runnable("Worker3",1000l));
		consumer.join();
		assertThat(CollectionHelper.getSize(consumer.getRunnables())).isEqualTo(0);
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
	
	public static class ConsumerListenerImpl extends Consumer.Listener.AbstractImpl {
		@Override
		public void listenStart(AbstractProducerConsumer producerConsumer, java.lang.Runnable runnable) {
			Runnable r = (Runnable) runnable;
			System.out.println("START From Listener!!! "+r.name);
		}
		@Override
		public void listenCompletion(AbstractProducerConsumer producerConsumer, java.lang.Runnable runnable) {
			Runnable r = (Runnable) runnable;
			System.out.println("DONE From Listener!!! "+r.name);
		}		
	}
}