package org.cyk.utility.common.computation;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.cdi.AbstractBean;
import org.joda.time.DateTimeConstants;

@Getter @Setter
public class ExecutionProgress extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 3068508905146910931L;

	private String name;
	
	/**
	 * Total amount of work
	 */
	private Long totalAmountOfWork = 0l;
	
	/**
	 * Current amount of work done
	 */
	private Long currentAmountOfWorkDone = 0l;
	
	private Long step;
	
	/**
	 * Number of millisecond between updates
	 */
	private Long numberOfMillisecondBetweenUpdates = DateTimeConstants.MILLIS_PER_MINUTE * 1l;
	
}
