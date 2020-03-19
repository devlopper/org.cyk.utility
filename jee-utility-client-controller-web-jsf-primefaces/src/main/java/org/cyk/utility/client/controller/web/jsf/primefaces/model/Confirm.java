package org.cyk.utility.client.controller.web.jsf.primefaces.model;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Confirm extends AbstractObject implements Serializable {

	private String header;
	private String message;
	private String icon;
	private Boolean disabled;
	private Boolean escape;
	
	public static class ConfiguratorImpl extends AbstractConfiguratorImpl<Confirm> implements Serializable {
		
		@Override
		public void configure(Confirm confirm, Map<Object, Object> arguments) {
			super.configure(confirm, arguments);
			if(StringHelper.isBlank(confirm.header))
				confirm.header = "Confirmation";
			if(StringHelper.isBlank(confirm.message))
				confirm.message = "Êtes-vous sûr de vouloir exécuter cette action ?";
			if(StringHelper.isBlank(confirm.icon))
				confirm.icon = "fa fa-warning";
			if(confirm.disabled == null)
				confirm.disabled = Boolean.FALSE;
			if(confirm.escape == null)
				confirm.escape = Boolean.FALSE;
		}
		
		@Override
		protected String __getTemplate__(Confirm confirm, Map<Object, Object> arguments) {
			return "/confirm/default.xhtml";
		}
		
		@Override
		protected Class<Confirm> __getClass__() {
			return Confirm.class;
		}
	}

	public static Confirm build(Map<Object,Object> arguments) {
		return Builder.build(Confirm.class,arguments);
	}
	
	public static Confirm build(Object...objects) {
		return build(MapHelper.instantiate(objects));
	}
	
	static {
		Configurator.set(Confirm.class, new ConfiguratorImpl());
	}
	
}
