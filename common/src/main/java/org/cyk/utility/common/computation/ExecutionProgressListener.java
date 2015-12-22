package org.cyk.utility.common.computation;

import java.io.Serializable;

import org.cyk.utility.common.cdi.BeanAdapter;

public interface ExecutionProgressListener {

	void valueChanged(ExecutionProgress executionProgress,String fieldName,Object oldValue);
	
	/**/
	
	public static class Adapter extends BeanAdapter implements ExecutionProgressListener,Serializable{

		private static final long serialVersionUID = -2821329924279855678L;

		@Override
		public void valueChanged(ExecutionProgress executionProgress,String fieldName, Object oldValue) {}
		
		/**/
		
		public static class Default extends Adapter implements Serializable{
			private static final long serialVersionUID = -4170929744491382130L;
			
		}
		
	}
	
}
