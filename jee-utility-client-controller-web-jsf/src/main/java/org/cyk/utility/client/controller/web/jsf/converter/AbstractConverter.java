package org.cyk.utility.client.controller.web.jsf.converter;
import java.io.Serializable;

import javax.faces.convert.Converter;

import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

public abstract class AbstractConverter extends AbstractObject implements Converter,Serializable {
	private static final long serialVersionUID = -1L;
	
	protected static final String NULL_STRING_VALUE = ConstantEmpty.STRING;
	
}