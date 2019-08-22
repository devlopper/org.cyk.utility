package org.cyk.utility.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.runnable.RunnablesExecutor;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.cyk.utility.time.TimeHelper;
import org.junit.jupiter.api.Test;

public class CollectionHelperUnitTestPerformance extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	private static final Integer GAP = 100;
	
	@Test
	public void process_serial() {
		__assertProcess__(1, null, Worker.DURATION+GAP);
		__assertProcess__(5, null, 5*Worker.DURATION+GAP);
		__assertProcess__(10, null, 10*Worker.DURATION+GAP);
	}
	
	@Test
	public void process_parallel_2() {
		__assertProcess__(1, 2, Worker.DURATION+GAP);
		__assertProcess__(5, 2, 3*Worker.DURATION+GAP);
		__assertProcess__(10, 2, 5*Worker.DURATION+GAP);
	}
	
	@Test
	public void process_parallel_3() {
		__assertProcess__(1, 3, Worker.DURATION+GAP);
		__assertProcess__(5, 3, 2*Worker.DURATION+GAP);
		__assertProcess__(10, 3, 4*Worker.DURATION+GAP);
	}
	
	@Test
	public void process_parallel_4() {
		__assertProcess__(1, 4, Worker.DURATION+GAP);
		__assertProcess__(5, 4, 2*Worker.DURATION+GAP);
		__assertProcess__(10, 4, 3*Worker.DURATION+GAP);
	}
	
	/**/
	
	private void __assertProcess__(Integer numberOfWorker,Integer numberOfSimultanousWorker,Long expectedMaximumDuration) {
		Collection<Worker> workers = null;
		if(numberOfWorker!= null && numberOfWorker>0) {
			workers = new ArrayList<>();
			for(Integer index = 0; index < numberOfWorker;index = index+1) {
				Worker worker = new Worker();
				workers.add(worker);
			}	
		}
		Long t = System.currentTimeMillis();
		System.out.print("Working. #worker : "+numberOfWorker+" : ");
		if(__inject__(CollectionHelper.class).isNotEmpty(workers)) {
			if(numberOfSimultanousWorker!=null && numberOfSimultanousWorker>0) {
				RunnablesExecutor runner = __inject__(RunnablesExecutor.class);
				runner.getExecutorServiceBuilder(Boolean.TRUE).setCorePoolSize(numberOfSimultanousWorker);
				runner.setTimeOut(1l).setTimeOutUnit(TimeUnit.SECONDS);
				for(Worker index : workers) {
					runner.addRunnables(new Runnable() {
						@Override
						public void run() {
							index.work();
						}
					});
				}
				runner.execute();
			}else {
				for(Worker index : workers) {
					index.work();
				}	
			}
		}
		t = System.currentTimeMillis() - t;
		System.out.println(t);
		assertThat(t).isLessThanOrEqualTo(expectedMaximumDuration);
	}
	
	public static class Worker {
		public static final Long DURATION = 200l;
		public void work() {
			__inject__(TimeHelper.class).pause(DURATION);
		}
	}
}
