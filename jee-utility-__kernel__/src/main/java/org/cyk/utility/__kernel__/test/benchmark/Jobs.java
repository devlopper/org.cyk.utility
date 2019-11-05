package org.cyk.utility.__kernel__.test.benchmark;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain = true)
public class Jobs implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private Integer numberOfRound;
	private Collection<Job> collection = new ArrayList<>();
	private Job jobHavingLowestNumberOfMillisecond;
	private Job jobHavingHighestNumberOfMillisecond;

	public Jobs add(String name, Runnable runnable) {
		collection.add(new Job().setJobs(this).setOrderNumber(collection.size() + 1).setName(name).setRunnable(runnable));
		return this;
	}
}
