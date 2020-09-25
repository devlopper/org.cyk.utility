package org.cyk.utility.client.controller.web.jsf.primefaces.model.collection;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractObject;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class RowToggler extends AbstractObject implements Serializable {
	
	private String collapseLabel,expandLabel;
	
	/**/
	
	/**/
	
	public static final String FIELD_COLLAPSE_LABEL = "collapseLabel";
	public static final String FIELD_EXPAND_LABEL = "expandLabel";
	
	/**/
	
	public static abstract class AbstractConfiguratorImpl<ROW_TOGLLER extends RowToggler> extends AbstractObject.AbstractConfiguratorImpl<ROW_TOGLLER> implements Serializable {
		
		@Override
		public void configure(ROW_TOGLLER rowToggler, Map<Object, Object> arguments) {
			super.configure(rowToggler, arguments);
			
		}
		
	}
	
	public static class ConfiguratorImpl extends AbstractConfiguratorImpl<RowToggler> implements Serializable {

		@Override
		protected Class<RowToggler> __getClass__() {
			return RowToggler.class;
		}

		@Override
		protected String __getTemplate__(RowToggler object, Map<Object, Object> arguments) {
			return "/collection/datatable/rowtoggler.xhtml";
		}
	}
	
	public static RowToggler build(Map<Object, Object> arguments) {
		return Builder.build(RowToggler.class,arguments);
	}
	
	public static RowToggler build(Object...objects) {
		return build(MapHelper.instantiate(objects));
	}
	
	static {
		Configurator.set(RowToggler.class, new ConfiguratorImpl());
	}
}
