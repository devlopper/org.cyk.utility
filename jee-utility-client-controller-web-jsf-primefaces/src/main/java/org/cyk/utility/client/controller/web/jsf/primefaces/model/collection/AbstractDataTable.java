package org.cyk.utility.client.controller.web.jsf.primefaces.model.collection;

import java.io.Serializable;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractDataTable extends AbstractCollection implements Serializable {

	/**/
	
	public static abstract class AbstractConfiguratorImpl<DATATABLE extends AbstractDataTable> extends AbstractCollection.AbstractConfiguratorImpl<DATATABLE> implements Serializable {

		@Override
		public void configure(DATATABLE datatable, Map<Object, Object> arguments) {
			super.configure(datatable, arguments);
			
		}
	}	
}
