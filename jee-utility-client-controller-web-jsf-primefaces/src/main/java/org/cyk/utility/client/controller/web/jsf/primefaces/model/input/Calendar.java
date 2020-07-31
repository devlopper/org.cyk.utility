package org.cyk.utility.client.controller.web.jsf.primefaces.model.input;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Calendar extends AbstractInput<Date> implements Serializable {

	
	/**/
	
	/**/
	
	/**/
	
	public static class ConfiguratorImpl extends AbstractConfiguratorImpl<Calendar> implements Serializable {

		@Override
		public void configure(Calendar inputDate, Map<Object, Object> arguments) {
			super.configure(inputDate, arguments);
			
		}
		
		@Override
		protected String __getTemplate__(Calendar inputDate, Map<Object, Object> arguments) {
			return "/input/calendar/default.xhtml";
		}
		
		@Override
		protected Class<Calendar> __getClass__() {
			return Calendar.class;
		}
		
		/**/
		
	}
	
	public static Calendar build(Map<Object, Object> arguments) {
		return Builder.build(Calendar.class,arguments);
	}
	
	public static Calendar build(Object...objects) {
		return build(MapHelper.instantiate(objects));
	}
	
	public static Calendar buildFromArray(Object...objects) {
		return build(objects);
	}

	static {
		Configurator.set(Calendar.class, new ConfiguratorImpl());
	}
}