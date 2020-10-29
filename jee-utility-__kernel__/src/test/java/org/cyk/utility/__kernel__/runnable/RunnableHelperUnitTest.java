package org.cyk.utility.__kernel__.runnable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.cyk.utility.__kernel__.runnable.RunnableHelper.instantiateExecutorService;
import static org.cyk.utility.__kernel__.runnable.RunnableHelper.run;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class RunnableHelperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void run_null(){
		ExecutorService executorService = instantiateExecutorService();
		run(null,"MyRunnables01",  executorService);
		ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executorService;
		assertThat(threadPoolExecutor.getCompletedTaskCount()).isEqualTo(0);
	}
	
	@Test
	public void run_empty(){
		ExecutorService executorService = instantiateExecutorService();
		run(Arrays.asList(),"MyRunnables01",  executorService);
		ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executorService;
		assertThat(threadPoolExecutor.getCompletedTaskCount()).isEqualTo(0);
	}
	
	@Test
	public void run_one(){
		ExecutorService executorService = instantiateExecutorService();
		run(Arrays.asList(new Runnable("job 01",1000l * 1)),"MyRunnables01",  executorService);
		ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executorService;
		assertThat(threadPoolExecutor.getCompletedTaskCount()).isEqualTo(1);
	}
	
	@Test
	public void run_two(){
		ExecutorService executorService = instantiateExecutorService();
		run(Arrays.asList(new Runnable("job 01",1000l * 1),new Runnable("job 02",1000l * 1)),"MyRunnables01",  executorService);
		ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executorService;
		assertThat(threadPoolExecutor.getCompletedTaskCount()).isEqualTo(2);
	}
	
	@Test
	public void run_two_timeOut(){
		ExecutorService executorService = instantiateExecutorService();
		try {
			run(Arrays.asList(new Runnable("job 01",1000l * 1),new Runnable("job 02",1000l * 5)),"MyRunnables01",  executorService,3l,TimeUnit.SECONDS);
		} catch (Exception exception) {}
		ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executorService;
		assertThat(threadPoolExecutor.getTaskCount()).isEqualTo(2);
		assertThat(threadPoolExecutor.getCompletedTaskCount()).isEqualTo(1);
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true) @AllArgsConstructor
	public static class Runnable implements java.lang.Runnable {
		
		private String name;
		private Long duration;
		
		@Override
		public void run() {
			if(StringHelper.isNotBlank(name))
				System.out.println("Work "+name+" started.");
			if(duration != null && duration > 0)
				try {
					Thread.sleep(duration);
					if(StringHelper.isNotBlank(name))
						System.out.println("Work "+name+" done.");
				} catch (InterruptedException e) {
					if(StringHelper.isNotBlank(name))
						System.out.println("Work "+name+" interrupted.");
					else
						e.printStackTrace();
				}
		}
		
	}
}
