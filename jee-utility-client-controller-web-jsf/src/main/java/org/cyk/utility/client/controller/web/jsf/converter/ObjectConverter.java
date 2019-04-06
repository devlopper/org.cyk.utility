package org.cyk.utility.client.controller.web.jsf.converter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;
import javax.inject.Singleton;

import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.random.RandomHelper;
import org.cyk.utility.string.StringHelper;

/**
 * Converter Using View Map to store and retrieve object.
 * No connection to data store (DB) needed
 * @author christian yao komenan
 *
 */
@Named @Singleton 
public class ObjectConverter extends AbstractObject implements Converter,Serializable {
	private static final long serialVersionUID = -1615078449226502960L;
	
	private static final String OBJECT_MAP_KEY = ObjectConverter.class.getSimpleName();
	
	private static final String NULL_STRING_VALUE = ConstantEmpty.STRING;
	
	private Map<String, Object> getObjectMap(FacesContext context) {
		Map<String, Object> viewMap = context.getViewRoot().getViewMap();
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Map<String, Object> objectMap = (Map) viewMap.get(OBJECT_MAP_KEY);
		if (objectMap == null) {
			objectMap = new HashMap<String, Object>();
			viewMap.put(OBJECT_MAP_KEY, objectMap);
		}
		return objectMap;
	}
	
	private String getIdentifier(Object object){
		if(object == null)
			return null;
		return __inject__(RandomHelper.class).getAlphabetic(6);
	}
	
	
	private String createNewIdentifierFrom(String identifier){
		return identifier+System.currentTimeMillis();
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent c, String identifier) {
		if (__inject__(StringHelper.class).isEmpty(identifier)) {
			return null;
		}
		return getObjectMap(context).get(identifier);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent c, Object object) {
		if (object == null) 
			return NULL_STRING_VALUE;
		String identifier = getIdentifier(object);
		if(identifier==null){
			//log.warning("Identifier value of <"+object+"> is null");
			return NULL_STRING_VALUE;
		}
		// handle duplicate id (two object of different type can have the same id value)
		Map<String, Object> objectMap = getObjectMap(context);
		if(objectMap.containsKey(identifier))
			identifier = createNewIdentifierFrom(identifier);
		objectMap.put(identifier, object);
		return identifier;
	}
}