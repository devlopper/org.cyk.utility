package org.cyk.utility.string;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.map.AbstractMapInstanceImpl;

@Dependent @SuppressWarnings("rawtypes")
public class StringByClassImpl extends AbstractMapInstanceImpl<Class, String> implements StringByClass,Serializable {
	private static final long serialVersionUID = 1L;

}
