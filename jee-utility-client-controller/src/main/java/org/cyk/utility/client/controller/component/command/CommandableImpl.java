package org.cyk.utility.client.controller.component.command;

import java.io.Serializable;

import org.cyk.utility.__kernel__.icon.Icon;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.client.controller.component.AbstractVisibleComponentImpl;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class CommandableImpl extends AbstractVisibleComponentImpl implements Commandable,Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private CommandableRenderType renderType;
	private Icon icon;
	private SystemAction systemAction;
	private String uniformResourceIdentifier;
	
}
