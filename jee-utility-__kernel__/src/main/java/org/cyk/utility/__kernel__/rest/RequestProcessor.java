package org.cyk.utility.__kernel__.rest;

import java.io.Serializable;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.runnable.Runner;
import org.cyk.utility.__kernel__.runnable.Runner.Arguments;
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
			
			request.execute(runnerArguments);
			
			if(runnerArguments.getThrowable() == null)
				return request.getResponseWhenThrowableIsNull(runnerArguments);
			
			return ResponseBuilder.getInstance().build(runnerArguments.getThrowable());
		}
	}
	
	public static interface Request {
		Runner.Arguments getRunnerArguments();
		Runnable getRunnable();
		void execute(Runner.Arguments arguments);
		Response getResponseWhenThrowableIsNull(Runner.Arguments runnerArguments);
		
		public static abstract class AbstractImpl implements Request {
			@Override
			public Arguments getRunnerArguments() {
				return null;
			}
			
			@Override
			public Response getResponseWhenThrowableIsNull(Arguments runnerArguments) {
				return Response.ok(getResponseWhenThrowableIsNullAsString(runnerArguments)).build();
			}
			
			@Override
			public void execute(Runner.Arguments arguments) {
				Runner.getInstance().run(arguments);
			}
			
			protected String getResponseWhenThrowableIsNullAsString(Arguments runnerArguments) {
				return "success";
			}
		}
	}
	
	static RequestProcessor getInstance() {
		return Helper.getInstance(RequestProcessor.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();	
}