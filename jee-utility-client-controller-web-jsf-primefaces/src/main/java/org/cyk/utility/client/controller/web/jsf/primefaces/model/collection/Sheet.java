package org.cyk.utility.client.controller.web.jsf.primefaces.model.collection;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Sheet extends AbstractDataTable implements Serializable {

	/**/
	
	public static class ConfiguratorImpl extends AbstractDataTable.AbstractConfiguratorImpl<Sheet> implements Serializable {

		@Override
		protected String __getTemplate__(Sheet sheet, Map<Object, Object> arguments) {
			return "/collection/datatable/sheet.xhtml";
		}
		
		@Override
		protected Class<Sheet> __getClass__() {
			return Sheet.class;
		}
	}
	
	public static Sheet build(Map<Object,Object> arguments) {
		return Builder.build(Sheet.class,arguments);
	}
	
	public static Sheet build(Object...objects) {
		return build(MapHelper.instantiate(objects));
	}
	
	static {
		Configurator.set(Sheet.class, new ConfiguratorImpl());
	}
}
