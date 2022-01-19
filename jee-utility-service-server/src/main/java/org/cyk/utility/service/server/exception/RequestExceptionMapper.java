package org.cyk.utility.service.server.exception;
import java.io.Serializable;

import org.cyk.utility.business.RequestException;

@javax.ws.rs.ext.Provider
public class RequestExceptionMapper extends AbstractRuntimeExceptionMapper<RequestException> implements Serializable {

}