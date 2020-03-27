package org.cyk.utility.__kernel__.controller;

import java.io.Serializable;

import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.representation.EntityReader;
import org.cyk.utility.__kernel__.throwable.RuntimeExceptionDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor @ToString(callSuper = false,doNotUseGetters = true)
public class Arguments extends AbstractObject implements Serializable {
	
	public static Boolean IS_REPRESENTATION_PROXYABLE = Boolean.TRUE;
	
	private Class<?> controllerEntityClass;
	private Class<?> representationEntityClass;
	private EntityReader representationEntityReader;
	private Boolean isRepresentationProxyable = IS_REPRESENTATION_PROXYABLE;
	private org.cyk.utility.__kernel__.representation.Arguments representationArguments;
	
	private Response response;
	private RuntimeExceptionDto runtimeException;
}