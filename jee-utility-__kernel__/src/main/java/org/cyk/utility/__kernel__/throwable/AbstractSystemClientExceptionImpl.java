package org.cyk.utility.__kernel__.throwable;

import java.io.Serializable;

import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.system.action.SystemAction;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public abstract class AbstractSystemClientExceptionImpl extends AbstractSystemExceptionImpl implements SystemClientException,Serializable {
	private static final long serialVersionUID = 1L;

	private Response response;
	
	public AbstractSystemClientExceptionImpl(String message,java.lang.Throwable throwable) {
		super(message,throwable);
	}
	
	public AbstractSystemClientExceptionImpl(String message) {
		super(message);
	}
	
	@Override
	public SystemClientException setSystemAction(SystemAction systemAction) {
		return (SystemClientException) super.setSystemAction(systemAction);
	}
	
}
