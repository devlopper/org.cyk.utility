package org.cyk.utility.common.userinterface.panel;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.cyk.utility.common.Properties;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.Notifications;
import org.cyk.utility.common.userinterface.command.Command;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class NotificationDialog extends Dialog implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final Map<Object,String> OK_GO_TO_ON_EVENT_UNIFORM_RESOURCE_LOCATOR_MAP = new HashMap<>();
	
	private Notifications notifications = new Notifications();
	private Component notificationList = new Component();
	private Command okCommand=new Command();
	
	public NotificationDialog() {
		okCommand.setLabelFromIdentifier("ok");
	}
	
	@Override
	protected void listenPropertiesInstanciated(Properties propertiesMap) {
		super.listenPropertiesInstanciated(propertiesMap);
		propertiesMap.setEscape(Boolean.TRUE);
	}
	
	/**/
	
	public static void registerWhenOkGotoUniformResourceLocatorOnEvent(String event,String uniformResourceLocatorSource,String uniformResourceLocatorDestination){
		OK_GO_TO_ON_EVENT_UNIFORM_RESOURCE_LOCATOR_MAP.put(uniformResourceLocatorSource, uniformResourceLocatorDestination);
	}
	
	public static String getGotoUniformResourceLocatorOnEvent(String event,String uniformResourceLocatorSource){
		return OK_GO_TO_ON_EVENT_UNIFORM_RESOURCE_LOCATOR_MAP.get(uniformResourceLocatorSource);
	}
}
