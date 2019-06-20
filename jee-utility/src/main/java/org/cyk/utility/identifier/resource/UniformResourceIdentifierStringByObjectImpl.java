package org.cyk.utility.identifier.resource;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.map.AbstractMapInstanceImpl;

@ApplicationScoped
public class UniformResourceIdentifierStringByObjectImpl extends AbstractMapInstanceImpl<Object, String>implements UniformResourceIdentifierStringByObject,Serializable {
	private static final long serialVersionUID = 1L;

}
