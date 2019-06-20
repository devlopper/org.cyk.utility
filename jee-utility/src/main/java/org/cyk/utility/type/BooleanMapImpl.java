package org.cyk.utility.type;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.map.AbstractMapInstanceImpl;

@Dependent
public class BooleanMapImpl extends AbstractMapInstanceImpl<Object, Boolean> implements BooleanMap,Serializable {
	private static final long serialVersionUID = 1L;

}
