package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.character.CharacterConstant;
import org.cyk.utility.client.controller.component.ComponentRole;
import org.cyk.utility.client.controller.component.ComponentRoleStyleClassGetter;

public class ComponentRoleStyleClassGetterFunctionRunnableImpl extends AbstractFunctionRunnableImpl<ComponentRoleStyleClassGetter> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public ComponentRoleStyleClassGetterFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				ComponentRole role = getFunction().getRole();
				/*
				switch(role) {
				case COLUMN:
					setOutput("cyk_layout_row");
					break;
				case EVEN:
					setOutput("");
					break;
				case FOOTER:
					setOutput("");
					break;
				case GRID:
					setOutput("");
					break;
				case HEADER:
					setOutput("");
					break;
				case ODD:
					setOutput("");
					break;
				case ROW:
					setOutput("");
					break;
				case TITLE:
					setOutput("");
					break;
				}
				*/
				setOutput("cyk"+CharacterConstant.UNDESCORE+role.name().toLowerCase());
			}
		});
	}
	
}