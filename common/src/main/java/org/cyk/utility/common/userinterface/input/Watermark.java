package org.cyk.utility.common.userinterface.input;

import java.io.Serializable;

import org.cyk.utility.common.userinterface.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class Watermark extends Component.Visible implements Serializable {
	private static final long serialVersionUID = 1L;

	/*public Watermark(String string){
		getPropertiesMap().setValue(string);
	}*/
	
}
