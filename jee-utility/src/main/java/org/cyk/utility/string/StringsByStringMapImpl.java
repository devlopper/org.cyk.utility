package org.cyk.utility.string;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.map.AbstractMapInstanceImpl;

@Dependent
public class StringsByStringMapImpl extends AbstractMapInstanceImpl<String, Strings> implements StringsByStringMap,Serializable {
	private static final long serialVersionUID = 1L;

}
