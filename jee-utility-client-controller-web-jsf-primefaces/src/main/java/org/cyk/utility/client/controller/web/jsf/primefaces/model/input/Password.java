package org.cyk.utility.client.controller.web.jsf.primefaces.model.input;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Password extends AbstractInput<String> implements Serializable {

	private Boolean feedback;
	private String promptLabel,match;
	
	/**/
	
	public static final String FIELD_MATCH = "match";
	
	/**/
	
	/**/
	
	public static class ConfiguratorImpl extends AbstractConfiguratorImpl<Password> implements Serializable {

		@Override
		public void configure(Password password, Map<Object, Object> arguments) {
			super.configure(password, arguments);
			if(password.feedback == null)
				password.feedback = Boolean.TRUE;
			if(StringHelper.isBlank(password.promptLabel))
				password.promptLabel = password.placeholder;
		}
		
		@Override
		protected String __getTemplate__(Password password, Map<Object, Object> arguments) {
			return "/input/password/default.xhtml";
		}
		
		@Override
		protected Class<Password> __getClass__() {
			return Password.class;
		}
		
		/**/
		
	}
	
	public static Password build(Map<Object, Object> arguments) {
		return Builder.build(Password.class,arguments);
	}
	
	public static Password build(Object...objects) {
		return build(MapHelper.instantiate(objects));
	}

	static {
		Configurator.set(Password.class, new ConfiguratorImpl());
	}
}