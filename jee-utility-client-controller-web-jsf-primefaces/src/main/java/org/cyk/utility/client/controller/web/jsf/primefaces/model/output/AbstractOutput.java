package org.cyk.utility.client.controller.web.jsf.primefaces.model.output;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractInputOutput;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractOutput<VALUE> extends AbstractInputOutput<VALUE> implements Serializable {

	/**/
	
	@Override
	public Boolean getIsOutput() {
		return Boolean.TRUE;
	}
	
	/**/
	
	/**/
	
	public static abstract class AbstractConfiguratorImpl<OUTPUT extends AbstractOutput<?>> extends AbstractInputOutput.AbstractConfiguratorImpl<OUTPUT> implements Serializable {

		@Override
		public void configure(OUTPUT output, Map<Object, Object> arguments) {
			super.configure(output, arguments);
			
		}
		
	}
}
