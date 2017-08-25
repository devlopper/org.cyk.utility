package org.cyk.utility.common.utility.loghelper;

import org.cyk.utility.common.helper.LoggingHelper;
import org.cyk.utility.common.helper.StackTraceHelper;

public  class BaseLogClass {
		
	public void m1(){
		LoggingHelper.Logger.Log4j.Adapter.Default.log("another message from m1",getClass(),LoggingHelper.Logger.Level.TRACE,"ML3"
				,StackTraceHelper.getInstance().getAt(3));
	}
	
	public void myoperation001(){		
		new LoggingHelper.Run.Adapter.Default(StackTraceHelper.getInstance().getAt(2),getClass()){
			private static final long serialVersionUID = 1L;
			
			public Object __execute__() {
				return null;
			}
			
		}.execute();
	}
	
	public void myoperation002(){		
		new LoggingHelper.Run.Adapter.Default(StackTraceHelper.getInstance().getAt(2),getClass()){
			private static final long serialVersionUID = 1L;
			
			public Object __execute__() {
				return null;
			}
			
		}.execute();
	}
	
	public void myoperation003(){		
		new LoggingHelper.Run.Adapter.Default(StackTraceHelper.getInstance().getAt(2),getClass()){
			private static final long serialVersionUID = 1L;
			
			public Object __execute__() {
				return null;
			}
			
		}.execute();
	}
	
}