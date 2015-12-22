package org.cyk.utility.common.computation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

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
	private Double totalAmountOfWork;
	
	/**
	 * Current amount of work done
	 */
	private Double currentAmountOfWorkDone;
	
	private Double step;
	
	/**
	 * Number of millisecond between updates
	 */
	private Long numberOfMillisecondBetweenUpdates = DateTimeConstants.MILLIS_PER_MINUTE * 1l;

	private Collection<ExecutionProgressListener> executionProgressListeners = new ArrayList<>();
	
	public ExecutionProgress(String name, Double totalAmountOfWork) {
		super();
		this.name = name;
		this.totalAmountOfWork = totalAmountOfWork;
		if(this.totalAmountOfWork!=null){
			this.step = this.totalAmountOfWork / 100;
		}
		clear();
	}
	
	public void clear(){
		currentAmountOfWorkDone = 0d;
	}
	
	public void addWorkDoneByStep(Integer numberOfStep){
		Object temp = this.currentAmountOfWorkDone;
		this.currentAmountOfWorkDone += this.step * numberOfStep;
		for(ExecutionProgressListener listener : executionProgressListeners)
			listener.valueChanged(this, FIELD_CURRENT_AMOUNT_OF_WORK_DONE, temp);
	}
	
	/**/
	
	public static final String FIELD_CURRENT_AMOUNT_OF_WORK_DONE = "currentAmountOfWorkDone";
}
