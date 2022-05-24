package org.cyk.utility.rest;

public interface RequestExecutor {

	javax.ws.rs.core.Response execute(Request request,Boolean isThrowableHandlable);
	javax.ws.rs.core.Response execute(Request request);
	
	/**/
	
	public static interface Request {
		
		ResponseBuilder.Arguments execute();
		
		@lombok.Getter @lombok.Setter @lombok.experimental.Accessors(chain=true)
		public static abstract class AbstractImpl extends org.cyk.utility.__kernel__.object.AbstractObject implements Request,java.io.Serializable {
			
			protected org.cyk.utility.rest.ResponseBuilder.Arguments responseBuilderArguments = new org.cyk.utility.rest.ResponseBuilder.Arguments();
			
			protected void prepare() {}
			protected void validatePreConditions() {}
			protected abstract void __execute__();
			
			@Override
			public org.cyk.utility.rest.ResponseBuilder.Arguments execute() {
				Long startTime = System.currentTimeMillis();
				prepare();
				validatePreConditions();
				__execute__();
				Long endTime = System.currentTimeMillis();
				responseBuilderArguments.setProcessingStartTime(startTime);
				responseBuilderArguments.setProcessingEndTime(endTime);
				responseBuilderArguments.setProcessingDuration(endTime - startTime);
				return responseBuilderArguments;
			}
		}
	}
}