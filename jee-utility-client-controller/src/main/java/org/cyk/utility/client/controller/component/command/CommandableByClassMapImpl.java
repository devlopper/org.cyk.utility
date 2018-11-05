package org.cyk.utility.client.controller.component.command;

import java.io.Serializable;

import org.cyk.utility.map.AbstractMapInstanceImpl;

@SuppressWarnings("rawtypes")
public class CommandableByClassMapImpl extends AbstractMapInstanceImpl<Class, Commandable> implements CommandableByClassMap,Serializable {
	private static final long serialVersionUID = 1L;

}
