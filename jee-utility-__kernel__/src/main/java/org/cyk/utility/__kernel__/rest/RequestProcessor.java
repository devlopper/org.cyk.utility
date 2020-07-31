package org.cyk.utility.__kernel__.rest;

import java.io.Serializable;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.cyk.utility.__kernel__.Helper;
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
			Runnable runnable = request.getRunnable();
			if(runnable == null)
				return Response.status(Status.BAD_REQUEST).entity("Request runnable is required").build();
			Runner.Arguments runnerArguments = new Runner.Arguments().addRunnables(runnable);
			Runner.getInstance().run(runnerArguments);
			if(runnerArguments.getThrowable() == null)
				return request.getResponseWhenThrowableIsNull(runnerArguments);
			return ResponseBuilder.getInstance().build(runnerArguments.getThrowable());
		}
	}
	
	public static interface Request {
		Runnable getRunnable();
		Response getResponseWhenThrowableIsNull(Runner.Arguments runnerArguments);
		
		public static abstract class AbstractImpl implements Request {
			@Override
			public Response getResponseWhenThrowableIsNull(Arguments runnerArguments) {
				return Response.ok(getResponseWhenThrowableIsNullAsString(runnerArguments)).build();
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