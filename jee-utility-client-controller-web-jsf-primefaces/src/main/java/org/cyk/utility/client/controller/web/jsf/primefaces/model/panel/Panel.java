package org.cyk.utility.client.controller.web.jsf.primefaces.model.panel;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractObject;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Panel extends AbstractObject implements Serializable {

	protected String header;
	protected Boolean toggleable,collapsed;
	
	/**/
	
	public static final String FIELD_HEADER = "header";
	public static final String FIELD_TOGGLEABLE = "toggleable";
	public static final String FIELD_COLLAPSED = "collapsed";
	
	/**/
	
	public static class ConfiguratorImpl extends org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractObject.AbstractConfiguratorImpl<Panel> implements Serializable {
		
		public void configure(Panel panel, Map<Object,Object> arguments) {
			super.configure(panel, arguments);
			if(panel.toggleable == null)
				panel.toggleable = Boolean.FALSE;
			if(panel.collapsed == null)
				panel.collapsed = Boolean.FALSE;
			if(StringHelper.isBlank(panel.header))
				panel.header = "HEADER HAS NOT BEEN SET";
		}
		
		@Override
		protected Class<Panel> __getClass__() {
			return Panel.class;
		}
		
		@Override
		protected String __getTemplate__(Panel panel, Map<Object, Object> arguments) {
			return "/panel/default.xhtml";
		}
	}
	
	static {
		Configurator.set(Panel.class, new ConfiguratorImpl());
	}
	
	public static Panel build(Map<Object, Object> arguments) {
		return Builder.build(Panel.class,arguments);
	}
	
	public static Panel build(Object...objects) {
		return build(MapHelper.instantiate(objects));
	}
}
