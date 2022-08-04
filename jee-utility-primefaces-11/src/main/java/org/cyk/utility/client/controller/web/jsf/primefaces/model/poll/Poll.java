package org.cyk.utility.client.controller.web.jsf.primefaces.model.poll;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.__kernel__.user.interface_.UserInterfaceAction;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractAction;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Poll extends AbstractAction implements Serializable{

	private Long delay;
	private Integer interval;
	private String intervalType;
	private Boolean async,autoStart,stop;
	
	/**/
	
	public void action() {
		act(null);
	}
	
	/**/

	public static final String FIELD_ASYNC = "async";
	public static final String FIELD_AUTO_START = "autoStart";
	public static final String FIELD_STOP = "stop";
	public static final String FIELD_DELAY = "delay";
	public static final String FIELD_INTERVAL = "interval";
	public static final String FIELD_INTERVAL_TYPE = "intervalType";
	
	/**/
	
	public static class ConfiguratorImpl extends AbstractAction.AbstractConfiguratorImpl<Poll> implements Serializable {

		@Override
		public void configure(Poll poll, Map<Object, Object> arguments) {
			super.configure(poll, arguments);
			if(poll.interval == null)
				poll.interval = 5;
			if(poll.intervalType == null)
				poll.intervalType = "second";
			if(poll.async == null)
				poll.async = Boolean.TRUE;
			if(poll.autoStart == null)
				poll.autoStart = Boolean.FALSE;
			if(poll.userInterfaceAction == null) {
				poll.userInterfaceAction = UserInterfaceAction.EXECUTE_FUNCTION;
				if(poll.runnerArguments != null)
					poll.runnerArguments.setSuccessMessageArguments(null);
			}
		}
		
		@Override
		protected Class<Poll> __getClass__() {
			return Poll.class;
		}
		
		@Override
		protected String __getTemplate__(Poll object, Map<Object, Object> arguments) {
			return "/poll/default.xhtml";
		}
	}
	
	public static Poll build(Map<Object, Object> arguments) {
		return Builder.build(Poll.class,arguments);
	}
	
	public static Poll build(Object...objects) {
		return build(MapHelper.instantiate(objects));
	}
	
	static {
		Configurator.set(Poll.class, new ConfiguratorImpl());
	}	
}