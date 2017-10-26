package org.cyk.utility.common.computation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.cdi.BeanListener;
import org.joda.time.DateTimeConstants;

import lombok.Getter;
import lombok.Setter;

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

	private long __milliseconds__;
	
	private ExecutionStep currentExecutionStep;
	
	private List<ExecutionStep> executionSteps;
	
	private Collection<Listener> executionProgressListeners = new ArrayList<>();
	
	public ExecutionProgress(String name, Double totalAmountOfWork) {
		super();
		this.name = name;
		setTotalAmountOfWork(totalAmountOfWork);
		clear();
	}
	
	public void clear(){
		currentAmountOfWorkDone = 0d;
		__milliseconds__ = System.currentTimeMillis();
	}
	
	public void setTotalAmountOfWork(Double totalAmountOfWork){
		this.totalAmountOfWork = totalAmountOfWork;
		if(this.totalAmountOfWork!=null){
			this.step = this.totalAmountOfWork / 100;
		}
	}
	
	public void setTotalAmountOfWorkUsing100AsBase(Double totalAmountOfWork){
		this.totalAmountOfWork = new Double(100);
		this.step = 100 / totalAmountOfWork;
		
	}
	
	public void setCurrentExecutionStep(String message){
		currentExecutionStep = new ExecutionStep(message);
	}
	
	public void addWorkDoneByStep(Integer numberOfStep,Throwable throwable){
		Object temp = this.currentAmountOfWorkDone;
		this.currentAmountOfWorkDone += this.step * numberOfStep;
		for(Listener listener : executionProgressListeners)
			listener.valueChanged(this, FIELD_CURRENT_AMOUNT_OF_WORK_DONE, temp);
		currentExecutionStep.done(throwable == null ? "SUCCESS" : "ERROR");//TODO make it as parameters
		addExecutionStep(currentExecutionStep);
	}
	public void addWorkDoneByStep(Integer numberOfStep){
		addWorkDoneByStep(numberOfStep, null);
	}
	
	public void addExecutionStep(ExecutionStep executionStep){
		if(this.executionSteps == null)
			this.executionSteps = new ArrayList<>();
		this.executionSteps.add(executionStep);
	}
	
	/**/
	
	public static final String FIELD_CURRENT_AMOUNT_OF_WORK_DONE = "currentAmountOfWorkDone";
	
	/**/
	
	@Getter @Setter
	public static class ExecutionStep implements Serializable{
		private static final long serialVersionUID = -5613429174384586480L;
		private long t1=System.currentTimeMillis(),t2;
		private String message,status,duration;
		
		public ExecutionStep(String message) {
			this.message = message;
		}
		
		public void done(String status){
			setStatus(status);
			setDuration(String.valueOf(( (t2 = System.currentTimeMillis()) - t1 ) / 1000)+" sec");
		}
	}
	
	/**/
	
	public interface Listener {

		void valueChanged(ExecutionProgress executionProgress,String fieldName,Object oldValue);
		
		/**/
		
		public static class Adapter extends BeanListener.Adapter implements Listener,Serializable{

			private static final long serialVersionUID = -2821329924279855678L;

			@Override
			public void valueChanged(ExecutionProgress executionProgress,String fieldName, Object oldValue) {}
			
			/**/
			
			public static class Default extends Listener.Adapter implements Serializable{
				private static final long serialVersionUID = -4170929744491382130L;
				
			}
			
		}
		
	}

	/**/
}
