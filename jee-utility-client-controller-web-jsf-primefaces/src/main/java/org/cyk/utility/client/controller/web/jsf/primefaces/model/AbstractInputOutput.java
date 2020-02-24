package org.cyk.utility.client.controller.web.jsf.primefaces.model;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractObjectAjaxable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractInputOutput<VALUE> extends AbstractObjectAjaxable implements Serializable {

	protected VALUE value;
	
	/**/
	
	/**/
	
	public static final String FIELD_VALUE = "value";
	
	/**/
	
	/**/
	
	public static abstract class AbstractConfiguratorImpl<IO extends AbstractInputOutput<?>> extends AbstractObjectAjaxable.AbstractConfiguratorImpl<IO> implements Serializable {

		@Override
		public void configure(IO io, Map<Object, Object> arguments) {
			super.configure(io, arguments);
			
		}
		
	}
}
