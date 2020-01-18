package org.cyk.utility.client.controller.web.jsf.primefaces.model;

import java.io.Serializable;

import org.cyk.utility.__kernel__.map.MapInstance;
import org.cyk.utility.__kernel__.random.RandomHelper;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.Setter;

public class AbstractObject extends org.cyk.utility.__kernel__.object.AbstractObject implements Serializable {

	@Getter @Setter protected String identifier = RandomHelper.getAlphabetic(5);
	@Getter @Setter protected MapInstance<Event, String> eventScripts;
	@Getter @Setter protected Boolean rendered = Boolean.TRUE;

	/* Events */
	
	public MapInstance<Event, String> getEventScripts(Boolean injectIfNull) {
		if(eventScripts == null && Boolean.TRUE.equals(injectIfNull))
			eventScripts = new MapInstance<>();
		return eventScripts;
	}
	
	public String getEventScript(Event event) {
		if(eventScripts == null || event == null)
			return null;
		return eventScripts.read(event);
	}
	
	public String getOnComplete() {
		return getEventScript(Event.COMPLETE);
	}
	
	public String getOnChange() {
		return getEventScript(Event.CHANGE);
	}
	
	public String getEventScriptByString(String string) {
		if(StringHelper.isBlank(string))
			return null;
		Event event = Event.valueOf(string);
		return getEventScript(event);
	}
}
