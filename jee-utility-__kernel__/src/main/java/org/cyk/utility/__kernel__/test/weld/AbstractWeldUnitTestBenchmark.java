package org.cyk.utility.__kernel__.test.weld;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public abstract class AbstractWeldUnitTestBenchmark extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	protected void execute(Job job) {
		System.out.print(String.format("\t%s - Using %s : ", job.orderNumber,job.name));
		Long t1 = System.currentTimeMillis();
		__start__(t1);
		
		for(Integer index = 0 ; index < job.jobs.numberOfRound ; index = index + 1)
			job.runnable.run();
		
		Long t2 = System.currentTimeMillis();
		Long duration = t2 - t1;
		__end__(t2,duration);
		job.numberOfMillisecond = duration;
		System.out.println(String.format("duration=%s", __formatDuration__(duration)));
	}
	
	protected void execute(Jobs jobs) {
		System.out.println(String.format("****    Field benchmark <<%s | #round=%s>> has started.    ****",jobs.name,jobs.numberOfRound));
		for(Job index : jobs.collection) {
			execute(index);
			if(jobs.jobHavingLowestNumberOfMillisecond == null || index.numberOfMillisecond < jobs.jobHavingLowestNumberOfMillisecond.numberOfMillisecond)
				jobs.jobHavingLowestNumberOfMillisecond = index;
			if(jobs.jobHavingHighestNumberOfMillisecond == null || index.numberOfMillisecond > jobs.jobHavingHighestNumberOfMillisecond.numberOfMillisecond)
				jobs.jobHavingHighestNumberOfMillisecond = index;
		}
		System.out.println(String.format("----    BEST CHOICE IS : %s    ----",jobs.jobHavingLowestNumberOfMillisecond.name));
		System.out.println(String.format("****    Field benchmark <<%s>> is done.    ****",jobs.name));
	}
	
	/**/
	
	protected void __start__(Long timestamp) {}
	protected void __end__(Long timestamp,Long duration) {}
	
	protected String __formatDuration__(Long duration) {
		if(duration == null)
			return null;
		return String.valueOf(duration);
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Jobs implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private String name;
		private Integer numberOfRound;
		private Collection<Job> collection = new ArrayList<>();
		private Job jobHavingLowestNumberOfMillisecond;
		private Job jobHavingHighestNumberOfMillisecond;
		
		public Jobs add(String name,Runnable runnable) {
			collection.add(new Job().setJobs(this).setOrderNumber(collection.size()+1).setName(name).setRunnable(runnable));
			return this;
		}
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class Job implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private Jobs jobs;
		private Integer orderNumber;
		private String name;
		private Runnable runnable;
		private Long numberOfMillisecond;
	}
}
