package org.cyk.utility.client.controller.web.jsf.converter;
import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.number.NumberHelper;
import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

public class NumberConverter extends AbstractObject implements Converter<Number>,Serializable {
	private static final long serialVersionUID = -1615078449226502960L;
	
	private Class<? extends Number> clazz;
	
	public NumberConverter(Class<? extends Number> clazz) {
		this.clazz = clazz;
	}
	
	@Override
	public Number getAsObject(FacesContext context, UIComponent component, String number) {
		return NumberHelper.get(clazz,number);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent c, Number number) {
		//return __inject__(NumberHelper.class). getInstance().stringify((Number)number);
		return number == null ? ConstantEmpty.STRING : number.toString();
	}
}