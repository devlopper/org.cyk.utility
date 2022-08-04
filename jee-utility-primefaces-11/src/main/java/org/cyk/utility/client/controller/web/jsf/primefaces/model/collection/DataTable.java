package org.cyk.utility.client.controller.web.jsf.primefaces.model.collection;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
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
	
	public static DataTable build(Map<Object,Object> arguments) {
		return Builder.build(DataTable.class,arguments);
	}
	
	public static DataTable build(Object...objects) {
		return build(MapHelper.instantiate(objects));
	}
	
	static {
		Configurator.set(DataTable.class, new ConfiguratorImpl());
	}
}
