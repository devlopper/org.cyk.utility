package org.cyk.utility.string.repository;

import java.io.Serializable;

import org.cyk.utility.map.AbstractKeyValueImpl;

public abstract class AbstractStringRepository extends AbstractKeyValueImpl<String,String> implements StringRepository,Serializable {
	private static final long serialVersionUID = 1L;

}
