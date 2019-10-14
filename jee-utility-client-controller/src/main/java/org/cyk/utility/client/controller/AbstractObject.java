package org.cyk.utility.client.controller;

import java.io.Serializable;
import java.util.Collection;

import javax.ws.rs.core.Response;

import org.cyk.utility.log.Log;
import org.cyk.utility.type.TypeHelper;

public abstract class AbstractObject extends org.cyk.utility.__kernel__.object.dynamic.AbstractObject implements Objectable,Serializable {
	private static final long serialVersionUID = 1L;

	/**/
	
	/* Response */
	
	protected static <T> Collection<T> __readEntityAsCollection__(Response response,Class<T> aClass) {
		return (Collection<T>) response.readEntity(__inject__(TypeHelper.class).instanciateGenericCollectionParameterizedTypeForJaxrs(Collection.class,aClass));
	}
	
	/**/
	
	protected static ControllerLayer __injectControllerLayer__() {
		return __inject__(ControllerLayer.class);
	}
	
	protected static Log __injectLog__() {
		return __inject__(Log.class);
	}
}
