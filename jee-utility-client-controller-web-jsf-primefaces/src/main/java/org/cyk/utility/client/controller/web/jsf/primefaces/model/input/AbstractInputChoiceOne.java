package org.cyk.utility.client.controller.web.jsf.primefaces.model.input;

import java.io.Serializable;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AbstractInputChoiceOne extends AbstractInputChoice<Object> implements Serializable {

	/**/
	
	/**/
	
	
	/**/
	
	public static interface Listener<VALUE> extends AbstractInputChoice.Listener<VALUE>  {
		
		public static abstract class AbstractImpl<VALUE> extends AbstractInputChoice.Listener.AbstractImpl<VALUE> implements Listener<VALUE>,Serializable {
			
		}
	}

	/**/
	
	public static abstract class AbstractConfiguratorImpl<INPUT extends AbstractInputChoiceOne> extends AbstractInputChoice.AbstractConfiguratorImpl<INPUT,Object> implements Serializable {

		@Override
		public void configure(INPUT input, Map<Object, Object> arguments) {
			super.configure(input, arguments);
				
		}
	}
}