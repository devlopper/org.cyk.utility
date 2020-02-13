package org.cyk.utility.client.controller.web.jsf.primefaces.model.collection;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.Configurator;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DataTable extends AbstractDataTable implements Serializable {

	/**/
	
	public static class ConfiguratorImpl extends AbstractDataTable.AbstractConfiguratorImpl<DataTable> implements Serializable {

		@Override
		protected Class<DataTable> __getClass__() {
			return DataTable.class;
		}
	}
	
	static {
		Configurator.set(DataTable.class, new ConfiguratorImpl());
	}
}
