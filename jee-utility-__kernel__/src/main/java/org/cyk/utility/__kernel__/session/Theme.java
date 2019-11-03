package org.cyk.utility.__kernel__.session;

import java.io.Serializable;

import org.cyk.utility.__kernel__.configuration.ConfigurationHelper;
import org.cyk.utility.__kernel__.variable.VariableName;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Theme implements Serializable {
	private static final long serialVersionUID = 1L;

	private Object color = ConfigurationHelper.getValue(VariableName.USER_INTERFACE_THEME_COLOR);
	
}
