package org.cyk.utility.client.controller.web.jsf.primefaces.model.input;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AbstractInputChoiceMany extends AbstractInputChoice<Collection<Object>> implements Serializable {

	
	
	/**/
	
	/**/
	
	/**/
	
	public static interface Listener {
		
		public static abstract class AbstractImpl extends org.cyk.utility.__kernel__.object.AbstractObject implements Listener,Serializable {
			
		}
	}

	/**/
	
	public static abstract class AbstractConfiguratorImpl<INPUT extends AbstractInputChoiceMany> extends AbstractInputChoice.AbstractConfiguratorImpl<INPUT,Collection<Object>> implements Serializable {

		@Override
		public void configure(INPUT input, Map<Object, Object> arguments) {
			super.configure(input, arguments);
			
		}
	}
}