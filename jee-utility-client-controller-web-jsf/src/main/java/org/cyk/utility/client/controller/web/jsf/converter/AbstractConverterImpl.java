package org.cyk.utility.client.controller.web.jsf.converter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.random.RandomHelper;
import org.cyk.utility.__kernel__.string.StringHelper;

public abstract class AbstractConverterImpl<T> extends AbstractObject implements javax.faces.convert.Converter<T>,Serializable {

	private final Map<String,Object> map = new HashMap<>();
	
	@Override
	public String getAsString(FacesContext context, UIComponent component, T value) {
		String string = null;
		Object identifier = FieldHelper.readSystemIdentifier(value);
		if(identifier == null)
			identifier = RandomHelper.getAlphanumeric(6);
		if(identifier != null)
			string = identifier.toString();
		if(StringHelper.isBlank(string)) {
			LogHelper.logWarning(String.format("we cannot compute key for value <<%s>>", value), getClass());
			return null;
		}
		if(map.containsKey(string))
			return string;
		map.put(string, value);
		return string;
	}
	
	@Override
	public T getAsObject(FacesContext context, UIComponent component, String string) {
		if(StringHelper.isBlank(string))
			return null;
		if(map.containsKey(string))
			return (T) map.get(string);
		LogHelper.logWarning(String.format("key <<%s>> is not mapped", string), getClass());
		return null;
	}
}