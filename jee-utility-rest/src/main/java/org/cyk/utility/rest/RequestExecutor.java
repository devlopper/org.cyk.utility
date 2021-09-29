package org.cyk.utility.rest;

public interface RequestExecutor {

	javax.ws.rs.core.Response execute(Request request);
	
	/**/
	
	public static interface Request {
		
		ResponseBuilder.Arguments execute();
		
		public static abstract class AbstractImpl extends org.cyk.utility.__kernel__.object.AbstractObject implements Request,java.io.Serializable {
			
			protected org.cyk.utility.rest.ResponseBuilder.Arguments responseBuilderArguments = new org.cyk.utility.rest.ResponseBuilder.Arguments();
			
			public org.cyk.utility.rest.ResponseBuilder.Arguments getResponseBuilderArguments(Boolean instantiateIfNull) {
				if(responseBuilderArguments == null && Boolean.TRUE.equals(instantiateIfNull))
					responseBuilderArguments = new ResponseBuilder.Arguments();
				return responseBuilderArguments;
			}
			
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