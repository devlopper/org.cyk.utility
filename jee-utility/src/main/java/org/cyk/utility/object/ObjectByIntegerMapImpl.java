package org.cyk.utility.object;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.map.AbstractMapInstanceImpl;

@Dependent
public class ObjectByIntegerMapImpl extends AbstractMapInstanceImpl<Integer, Object> implements ObjectByIntegerMap,Serializable {
	private static final long serialVersionUID = 1L;

}
