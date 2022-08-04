package org.cyk.utility.client.controller.web.jsf.primefaces.model.collection;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractObject;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CellEditor extends AbstractObject implements Serializable {

	private Boolean disabled;
	
	/**/
	
	public static final String FIELD_DISABLED = "disabled";
	
	/**/
	
	public static class ConfiguratorImpl extends AbstractObject.AbstractConfiguratorImpl<CellEditor> implements Serializable {

		@Override
		public void configure(CellEditor cellEditor, Map<Object, Object> arguments) {
			super.configure(cellEditor, arguments);
			if(cellEditor.disabled == null)
				cellEditor.disabled = Boolean.FALSE;
		}
		
		@Override
		protected Class<CellEditor> __getClass__() {
			return CellEditor.class;
		}
		
		public static final String FIELD_FILTERABLE = "filterable";
	}
	
	public static CellEditor build(Map<Object,Object> arguments) {
		return Builder.build(CellEditor.class,arguments);
	}
	
	public static CellEditor build(Object...objects) {
		return build(MapHelper.instantiate(objects));
	}
	
	static {
		Configurator.set(CellEditor.class, new ConfiguratorImpl());
	}
}
