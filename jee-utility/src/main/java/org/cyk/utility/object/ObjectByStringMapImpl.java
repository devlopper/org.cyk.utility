package org.cyk.utility.object;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.map.AbstractMapInstanceImpl;

@Dependent
public class ObjectByStringMapImpl extends AbstractMapInstanceImpl<String, Object> implements ObjectByStringMap,Serializable {
	private static final long serialVersionUID = 1L;

}
