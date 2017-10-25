package org.cyk.utility.common.userinterface;

import java.io.Serializable;

import org.cyk.utility.common.userinterface.panel.Dialog;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Request extends Component.Invisible implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Dialog statusDialog = new Dialog();
	
}
