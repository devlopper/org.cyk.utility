package org.cyk.utility.client.controller.web.jsf.primefaces.model;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.map.MapInstance;
import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.__kernel__.random.RandomHelper;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.Setter;

public class AbstractObject extends org.cyk.utility.__kernel__.object.AbstractObject implements Serializable {

	@Getter @Setter protected String identifier = RandomHelper.getAlphabetic(5);
	@Getter @Setter protected String widgetVar = RandomHelper.getAlphabetic(5);
	@Getter @Setter protected String style,styleClass;
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
	
	/* Style */
	
	public AbstractObject addStyleClasses(Collection<String> classes) {
		if(CollectionHelper.isEmpty(classes))
			return this;
		styleClass = (StringHelper.isBlank(styleClass) ? ConstantEmpty.STRING : styleClass+" ") + StringHelper.concatenate(classes, " ");		
		return this;
	}
	
	public AbstractObject addStyleClasses(String...classes) {
		if(ArrayHelper.isEmpty(classes))
			return this;
		return addStyleClasses(CollectionHelper.listOf(classes));
	}
	
	public AbstractObject addStyles(Collection<String> styles) {
		if(CollectionHelper.isEmpty(styles))
			return this;
		style = (StringHelper.isBlank(style) ? ConstantEmpty.STRING : style+" ") + StringHelper.concatenate(styles, " ");		
		return this;
	}
	
	public AbstractObject addStyle(String...styles) {
		if(ArrayHelper.isEmpty(styles))
			return this;
		return addStyles(CollectionHelper.listOf(styles));
	}
	
	/**/
	
	public static abstract class AbstractConfiguratorImpl<OBJECT extends AbstractObject> extends Configurator.AbstractImpl<OBJECT> implements Serializable {
		
	}
	
	/**/
	
	/**/
	
	public static final String FIELD_IDENTIFIER = "identifier";
	public static final String FIELD_WIDGET_VAR = "widgetVar";
	public static final String FIELD_RENDERED = "rendered";
	public static final String FIELD_EVENT_SCRIPTS = "eventScripts";
	public static final String FIELD_EVENT_STYLE = "style";
	public static final String FIELD_EVENT_STYLE_CLASS = "styleClass";
}
