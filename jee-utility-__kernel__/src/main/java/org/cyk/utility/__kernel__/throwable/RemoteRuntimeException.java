package org.cyk.utility.__kernel__.throwable;

import java.io.Serializable;

import javax.ws.rs.core.Response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class RemoteRuntimeException extends RuntimeException implements Serializable {

	private Response response;
	
	public RemoteRuntimeException() {
		super();
	}

	public RemoteRuntimeException(Response response,String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.response = response;
	}

	public RemoteRuntimeException(Response response,String message, Throwable cause) {
		super(message, cause);
		this.response = response;
	}

	public RemoteRuntimeException(Response response,String message) {
		super(message);
		this.response = response;
	}

	public RemoteRuntimeException(Response response,Throwable cause) {
		super(cause);
		this.response = response;
	}
	
}
