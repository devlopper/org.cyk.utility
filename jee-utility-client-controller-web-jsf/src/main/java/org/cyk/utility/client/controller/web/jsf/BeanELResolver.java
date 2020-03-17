package org.cyk.utility.client.controller.web.jsf;

import java.io.Serializable;

import javax.el.ELContext;

import org.cyk.utility.__kernel__.field.FieldHelper;

/**
 * To resolve fluent setter
 * @author CYK
 *
 */
public class BeanELResolver extends javax.el.BeanELResolver implements Serializable {
	private static final long serialVersionUID = 1L;

	public void setValue(ELContext context, Object base, Object property, Object value) {
		if (base == null || !(property instanceof String))
			return;
		System.out.println("BeanELResolver.setValue() "+base.getClass()+"."+property+" : "+value);
		FieldHelper.write(base, property.toString(), value, Boolean.TRUE);
		context.setPropertyResolved(Boolean.TRUE);
	}
}