package org.cyk.utility.__kernel__.rest;

import java.io.Serializable;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.runnable.Runner;
import org.cyk.utility.__kernel__.value.Value;

public interface RequestProcessor {

	Response process(Request request);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements RequestProcessor,Serializable {
		
		public Response process(Request request) {
			if(request == null)
				return Response.status(Status.BAD_REQUEST).entity("Request is required").build();
			
			Runner.Arguments runnerArguments = request.getRunnerArguments();
			if(runnerArguments == null)
				runnerArguments = new Runner.Arguments();
			
			if(CollectionHelper.isEmpty(runnerArguments.getRunnables())) {
				Runnable runnable = request.getRunnable();
				if(runnable == null)
					return Response.status(Status.BAD_REQUEST).entity("Request runnable is required").build();
				runnerArguments.addRunnables(runnable);
			}
			
			if(runnerArguments.getThrowableCatchableOnly() == null)
				runnerArguments.setThrowableCatchableOnly(Boolean.TRUE);
			
			return request.execute(runnerArguments);
			/*
			if(runnerArguments.getThrowable() == null)
				return request.getResponseWhenThrowableIsNull(runnerArguments);
			return ResponseBuilder.getInstance().build(runnerArguments.getThrowable());
			*/
		}
	}
	
	public static interface Request {
		Runner.Arguments getRunnerArguments();
		Runnable getRunnable();
		Response execute(Runner.Arguments arguments);
		//Response getResponse();
		//Request buildResponse();
		//Response.ResponseBuilder getResponseBuilderWhenThrowableIsNull(Runner.Arguments runnerArguments);
		Response getResponseWhenThrowableIsNull(Runner.Arguments runnerArguments);
		
		public static abstract class AbstractImpl implements Request {
			
			protected ResponseBuilder.Arguments responseBuilderArguments = new ResponseBuilder.Arguments();
			
			@Override
			public Runner.Arguments getRunnerArguments() {
				return null;
			}
			/*
			@Override
			public Response.ResponseBuilder getResponseBuilderWhenThrowableIsNull(Runner.Arguments runnerArguments) {				
				return Response.ok(getResponseWhenThrowableIsNullAsString(runnerArguments));
			}
			*/
			@Override
			public Response getResponseWhenThrowableIsNull(Runner.Arguments runnerArguments) {
				if(responseBuilderArguments.getEntity() == null)
					if(runnerArguments.getResult() == null)
						responseBuilderArguments.setEntity(getResponseWhenThrowableIsNullAsString(runnerArguments));
					else
						responseBuilderArguments.setEntity(runnerArguments.getResult());
				/*
				if(runnerArguments.getResult() == null)
					return getResponseBuilderWhenThrowableIsNull(runnerArguments).build();
				*/
				return ResponseBuilder.getInstance().build(responseBuilderArguments/*new ResponseBuilder.Arguments().setEntity(runnerArguments.getResult())*/);
				//return getResponseBuilderWhenThrowableIsNull(runnerArguments).build();
			}
			
			@Override
			public Response execute(Runner.Arguments arguments) {
				try {
					Runner.getInstance().run(arguments);
				} catch (Exception exception) {
					LogHelper.log(exception, getClass());
					arguments.setThrowable(exception);
				}
				
				if(arguments.getThrowable() == null)
					return getResponseWhenThrowableIsNull(arguments);
				return ResponseBuilder.getInstance().build(arguments.getThrowable());
			}
			
			protected String getResponseWhenThrowableIsNullAsString(Runner.Arguments runnerArguments) {
				if(runnerArguments != null && runnerArguments.getResult() != null)
					return runnerArguments.getResult().toString();
				return "success";
			}
		}
	}
	
	static RequestProcessor getInstance() {
		return Helper.getInstance(RequestProcessor.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();	
}