package org.cyk.utility.test.benchmark;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain = true)
public class Job implements Serializable {
	private static final long serialVersionUID = 1L;

	private Jobs jobs;
	private Integer orderNumber;
	private String name;
	private Runnable runnable;
	private Long numberOfMillisecond;
}