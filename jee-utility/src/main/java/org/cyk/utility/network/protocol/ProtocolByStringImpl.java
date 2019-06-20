package org.cyk.utility.network.protocol;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.map.AbstractMapInstanceImpl;

@Dependent
public class ProtocolByStringImpl extends AbstractMapInstanceImpl<String, Protocol> implements ProtocolByString,Serializable {
	private static final long serialVersionUID = 1L;

}
