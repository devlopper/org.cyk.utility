package org.cyk.utility.object;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.map.AbstractMapInstanceImpl;

@Dependent @SuppressWarnings("rawtypes")
public class ObjectByClassMapImpl extends AbstractMapInstanceImpl<Class, Object> implements ObjectByClassMap,Serializable {
	private static final long serialVersionUID = 1L;

}
