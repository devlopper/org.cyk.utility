package org.cyk.utility.client.controller.web.jsf.converter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public abstract class AbstractBasedMapConverter extends AbstractConverter implements Serializable {
	private static final long serialVersionUID = -1615078449226502960L;
	
	protected final Map<String,Object> map = new HashMap<>();
	 
	protected abstract String getString(Object object);
	
	@Override
	public String getAsString(FacesContext context, UIComponent c, Object object) {
		String string = getString(object);
		map.put(string, object);
		return string;
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String string) {
		return map.get(string);
	}
	
}