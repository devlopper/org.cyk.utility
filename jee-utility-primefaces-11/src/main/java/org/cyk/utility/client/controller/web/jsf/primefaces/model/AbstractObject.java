package org.cyk.utility.client.controller.web.jsf.primefaces.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.CardinalPoint;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.map.MapInstance;
import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.__kernel__.random.RandomHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.client.controller.web.jsf.primefaces.PrimefacesHelper;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AbstractObject extends org.cyk.utility.__kernel__.object.AbstractObject implements Serializable {

	protected String identifier;
	protected String widgetVar;
	protected String style,styleClass,styleClassAsIdentifier;
	protected MapInstance<Event, String> eventScripts;
	protected Boolean rendered,__isLoggable__;
	protected Object binding;
	protected CardinalPoint cardinalPointFromReference;
	protected Integer tabIndex;
	
	/* Working variables */
	
	/* Template to enable custom file*/
	protected String __template__;
	
	/* Listener to enabled behavior extension */
	protected Object listener;
	
	/**/
	
	public Boolean getIsInput() {
		return null;
	}
	
	public Boolean getIsOutput() {
		return null;
	}
	
	public AbstractObject hideOnComplete() {
		PrimefacesHelper.executeOnComplete(PrimefacesHelper.formatScriptHide(widgetVar));
		return this;
	}
	
	public AbstractObject setBindingByDerivation(String beanPath) {
		Object binding = deriveBinding(beanPath);
		if(binding == null)
			LogHelper.logWarning(String.format("Derivation of binding of %s under bean path %s is null.", getClass(),beanPath), getClass());
		setBinding(binding);
		return this;
	}
	
	public Object deriveBinding(String beanPath) {
		throw new RuntimeException(String.format("Derivation of binding of %s under bean path %s not yet implemented.",getClass(),beanPath));
	}
	
	/**/
	
	public static final String FIELD_IDENTIFIER = "identifier";
	public static final String FIELD_WIDGET_VAR = "widgetVar";
	public static final String FIELD_STYLE = "style";
	public static final String FIELD_STYLE_CLASS = "styleClass";
	public static final String FIELD_STYLE_CLASS_AS_IDENTIFIER = "styleClassAsIdentifier";
	public static final String FIELD_RENDERED = "rendered";
	public static final String FIELD_EVENT_SCRIPTS = "eventScripts";
	public static final String FIELD___TEMPLATE__ = "__template__";
	public static final String FIELD_LISTENER = "listener";
	public static final String FIELD_CARDINAL_POINT_FROM_REFERENCE = "cardinalPointFromReference";
	
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
	
	public AbstractObject setEventScript(Event event,String script) {
		getEventScripts(Boolean.TRUE).write(event, script);
		return this;
	}
	
	public String getOnStart() {
		return getEventScript(Event.START);
	}
	
	public String getOnComplete() {
		return getEventScript(Event.COMPLETE);
	}
	
	public String getOnChange() {
		return getEventScript(Event.CHANGE);
	}
	
	public String getOnClick() {
		return getEventScript(Event.CLICK);
	}
	
	public String getEventScriptByString(String string) {
		if(StringHelper.isBlank(string))
			return null;
		Event event = Event.valueOf(string);
		return getEventScript(event);
	}
	
	public AbstractObject addEventScipts(Event event,Collection<String> scripts) {
		if(event == null || CollectionHelper.isEmpty(scripts))
			return this;
		String string = StringUtils.stripToEmpty(getEventScript(event));
		setEventScript(event, string+StringHelper.concatenate(scripts, ";"));
		return this;
	}
	
	public AbstractObject addEventScipts(Event event,String...scripts) {
		if(event == null || ArrayHelper.isEmpty(scripts))
			return this;
		return addEventScipts(event, CollectionHelper.listOf(Boolean.TRUE,scripts));
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
		
		@Override
		public void configure(OBJECT object, Map<Object, Object> arguments) {
			super.configure(object, arguments);
			if(StringHelper.isBlank(object.identifier) || StringHelper.isBlank(object.widgetVar)) {
				String identifier_prefix = object.getClass().getSimpleName().toLowerCase();
				if(StringHelper.isBlank(object.identifier)) {
					object.identifier = generateIdentifier(identifier_prefix);
				}
				if(StringHelper.isBlank(object.widgetVar)) {
					object.widgetVar = generateIdentifier(identifier_prefix);
				}
				if(StringHelper.isBlank(object.styleClassAsIdentifier)) {
					if(StringHelper.isNotBlank(object.identifier))
						object.styleClassAsIdentifier = object.identifier;
					else
						object.styleClassAsIdentifier = generateIdentifier(identifier_prefix);
				}
				
				if(StringHelper.isNotBlank(object.styleClassAsIdentifier))
					object.addStyleClasses(object.styleClassAsIdentifier);
			}
			if(object.rendered == null)
				object.rendered = Boolean.TRUE;
			
			if(StringHelper.isBlank(object.__template__))
				object.__template__ = __getTemplate__(object,arguments);
			
			if(object.tabIndex == null)
				object.tabIndex = 0;
			
			if(Boolean.TRUE.equals(MapHelper.readByKey(arguments, FIELD_PADDING_0))) {
				object.addStyleClasses("cyk-padding-0");
			}
		}
		
		protected String __getTemplate__(OBJECT object, Map<Object, Object> arguments) {
			return null;
		}
		
		public static String generateIdentifier(Class<?> klass) {
			if(klass == null)
				return null;
			return generateIdentifier(klass.getSimpleName().toLowerCase());			
		}
		
		public static String generateIdentifier(String prefix) {
			if(StringHelper.isBlank(prefix))
				prefix = "";
			else
				prefix = prefix + "_";
			return prefix+RandomHelper.getAlphabetic(5);
		}
	
		/**/
		
		public static final String FIELD_PADDING_0 = "padding0";
	}
	
	/**/

}
