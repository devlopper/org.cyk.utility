package org.cyk.utility.client.controller.web.jsf.primefaces.model.message;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractObject;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.input.AbstractInput;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Message extends AbstractObject implements Serializable {

	private String for_;
	
	/**/
	
	public static final String FIELD_FOR = "for_";
	
	/**/
	
	public static class ConfiguratorImpl extends org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractObject.AbstractConfiguratorImpl<Message> implements Serializable {

		@Override
		public void configure(Message message, Map<Object, Object> arguments) {
			super.configure(message, arguments);
			if(StringHelper.isBlank(message.for_)) {
				AbstractInput<?> input = (AbstractInput<?>) MapHelper.readByKey(arguments, FIELD_INPUT);
				if(input != null)
					message.for_ = input.getIdentifier();
			}
		}
		
		@Override
		protected Class<Message> __getClass__() {
			return Message.class;
		}
		
		@Override
		protected String __getTemplate__(Message object, Map<Object, Object> arguments) {
			return "/message/default.xhtml";
		}
		
		public static final String FIELD_INPUT = "input";
	}
	
	public static Message build(Map<Object, Object> arguments) {
		return Builder.build(Message.class,arguments);
	}
	
	static {
		Configurator.set(Message.class, new ConfiguratorImpl());
	}
}
