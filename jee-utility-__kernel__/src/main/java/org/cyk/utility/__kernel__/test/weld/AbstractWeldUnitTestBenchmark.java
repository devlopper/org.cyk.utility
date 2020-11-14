package org.cyk.utility.__kernel__.test.weld;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public abstract class AbstractWeldUnitTestBenchmark extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	protected void execute(Job job) {
		if(Boolean.TRUE.equals(job.isSystemOutable))
			System.out.print(String.format("\t%s - Using %s : ", job.orderNumber,job.name));
		job.start();
		__start__(job.startNumberOfMillisecondIndex);
		
		for(Integer index = 0 ; index < job.jobs.numberOfRound ; index = index + 1)
			job.runnable.run();
		
		job.end();
		__end__(job.endNumberOfMillisecondIndex,job.numberOfMillisecond);
		if(Boolean.TRUE.equals(job.isSystemOutable))
			System.out.println(String.format("duration=%s , rate=%s", __formatDuration__(job.numberOfMillisecond),job.rate));
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
		jobs.sort();
		System.out.println("          ----    ORDER    ----");
		for(Integer index = 0 ; index < jobs.collection.size() ; index = index + 1) {
			Job job = jobs.collection.get(index);
			System.out.println(String.format("          ----    %s - %s - %s - %s    ----",index+1,job.name,job.numberOfMillisecond,job.rate));
		}
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
		private List<Job> collection = new ArrayList<>();
		private Job jobHavingLowestNumberOfMillisecond;
		private Job jobHavingHighestNumberOfMillisecond;
		
		public List<Job> getCollection(Boolean injectIfNull){
			if(collection == null && Boolean.TRUE.equals(injectIfNull))
				collection = new ArrayList<>();
			return collection;
		}
		
		public Jobs add(Job job) {
			if(job == null)
				return this;
			job.setJobs(this).setOrderNumber(collection.size()+1);
			getCollection(Boolean.TRUE).add(job);
			return this;
		}
		
		public Jobs add(String name,Runnable runnable) {
			add(new Job().setName(name).setRunnable(runnable));
			return this;
		}
		
		public Jobs sort() {
			Collections.sort(collection, new Comparator<Job>() {
				@Override
				public int compare(Job o1, Job o2) {
					return o1.numberOfMillisecond.compareTo(o2.numberOfMillisecond);
				}
			});
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
		private Boolean isSystemOutable=Boolean.FALSE;
		private Long startNumberOfMillisecondIndex,endNumberOfMillisecondIndex;
		private Long numberOfMillisecond;
		private BigDecimal rate;
		
		public Job start() {
			startNumberOfMillisecondIndex = System.currentTimeMillis();
			return this;
		}
		
		public Job end() {
			endNumberOfMillisecondIndex = System.currentTimeMillis();
			numberOfMillisecond =endNumberOfMillisecondIndex - startNumberOfMillisecondIndex;
			rate = new BigDecimal(numberOfMillisecond).divide(new BigDecimal(jobs.numberOfRound),2,RoundingMode.DOWN);
			return this;
		}
	}
}