package org.cyk.utility.client.controller.web.jsf.converter;
import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.number.NumberHelper;
import org.cyk.utility.string.StringConstant;

public class NumberConverter extends AbstractObject implements Converter,Serializable {
	private static final long serialVersionUID = -1615078449226502960L;
	
	private Class<? extends Number> clazz;
	
	public NumberConverter(Class<? extends Number> clazz) {
		this.clazz = clazz;
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String number) {
		return __inject__(NumberHelper.class).get(clazz,number);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent c, Object number) {
		//return __inject__(NumberHelper.class). getInstance().stringify((Number)number);
		return number == null ? StringConstant.EMPTY : number.toString();
	}
}