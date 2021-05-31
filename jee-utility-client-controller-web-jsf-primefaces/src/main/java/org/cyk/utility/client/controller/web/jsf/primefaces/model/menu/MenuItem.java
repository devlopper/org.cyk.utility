package org.cyk.utility.client.controller.web.jsf.primefaces.model.menu;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.identifier.resource.ParameterName;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.AbstractCommand;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MenuItem extends AbstractCommand implements Serializable {

	/**/
	
	@Override
	public MenuItem setValue(String value) {
		return (MenuItem) super.setValue(value);
	}
	
	@Override
	public MenuItem setOutcome(String outcome) {
		return (MenuItem) super.setOutcome(outcome);
	}
	
	public MenuItem setIcon(String icon) {
		return (MenuItem) super.setIcon(icon);
	}
	
	@Override
	public MenuItem setParameters(Map<String, List<String>> parameters) {
		return (MenuItem) super.setParameters(parameters);
	}
	
	public MenuItem addParameter(String name, String value) {
		return (MenuItem) super.addParameter(name, value);
	}
	
	public MenuItem addParameterFromInstance(Object instance) {
		if(instance == null)
			return this;
		Object identifier = FieldHelper.readSystemIdentifier(instance);
		if(identifier == null)
			return this;
		return addParameter(ParameterName.stringify(instance.getClass()), StringHelper.get(identifier));
	}
	
	public MenuItem addParameterFromInstanceIfConditionIsTrue(Object instance,Boolean condition) {
		if(Boolean.TRUE.equals(condition))
			addParameterFromInstance(instance);
		return this;
	}
	
	public static class ConfiguratorImpl extends AbstractConfiguratorImpl<MenuItem> implements Serializable {

		@Override
		public void configure(MenuItem menuItem, Map<Object, Object> arguments) {
			super.configure(menuItem, arguments);
			if(StringHelper.isBlank(menuItem.__actionArgumentIdentifierParameterName__))
				menuItem.__actionArgumentIdentifierParameterName__ = "entityidentifier";
		}
		
		@Override
		protected String __getTemplate__(MenuItem menuItem, Map<Object, Object> arguments) {
			return "/menu/item/default.xhtml";
		}
		
		@Override
		protected Class<MenuItem> __getClass__() {
			return MenuItem.class;
		}
		
	}
	
	public static MenuItem build(Map<Object, Object> arguments) {
		return Builder.build(MenuItem.class,arguments);
	}
	
	public static MenuItem build(Object...objects) {
		return build(MapHelper.instantiate(objects));
	}
	
	static {
		Configurator.set(MenuItem.class, new ConfiguratorImpl());
	}
	
}
