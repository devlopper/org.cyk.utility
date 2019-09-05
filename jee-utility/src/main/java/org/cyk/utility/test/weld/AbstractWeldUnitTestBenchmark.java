package org.cyk.utility.test.weld;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.time.Duration;
import org.cyk.utility.time.DurationBuilder;
import org.cyk.utility.time.DurationStringBuilder;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public abstract class AbstractWeldUnitTestBenchmark extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	protected static void execute(Job job) {
		System.out.print(String.format("\t%s - Using %s : ", job.orderNumber,job.name));
		DurationBuilder durationBuilder = __inject__(DurationBuilder.class).setBeginToNow();
		for(Integer index = 0 ; index < job.jobs.numberOfRound ; index = index + 1)
			job.runnable.run();
		durationBuilder.setEndToNow();
		job.numberOfMillisecond = durationBuilder.getEnd() - durationBuilder.getBegin();
		Duration duration = durationBuilder.execute().getOutput();
		System.out.println(String.format("duration=%s", __inject__(DurationStringBuilder.class).setDuration(duration).execute().getOutput()));
	}
	
	protected static void execute(Jobs jobs) {
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
