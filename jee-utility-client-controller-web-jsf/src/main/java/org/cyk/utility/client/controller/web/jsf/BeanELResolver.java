package org.cyk.utility.client.controller.web.jsf;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.el.ELContext;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.method.MethodGetter;

/**
 * To resolve fluent setter
 * @author CYK
 *
 */
public class BeanELResolver extends javax.el.BeanELResolver implements Serializable {
	private static final long serialVersionUID = 1L;

	public boolean isReadOnly(ELContext context, Object base, Object property) {
		if (base == null || !(property instanceof String)) {
			return Boolean.TRUE;
		}
		Method method = getSetterMethod(context, base, property.toString());
		if (method == null) {
			return Boolean.TRUE;
		}
		context.setPropertyResolved(Boolean.TRUE);
		return Boolean.FALSE;
	}

	public void setValue(ELContext context, Object base, Object property, Object value) {
		if (base == null || !(property instanceof String)) {
			return;
		}
		if(base!=null)
			System.out.println("BeanELResolver.setValue() "+base.getClass()+"."+property+" : "+value);
		Method method = getSetterMethod(context, base, property.toString());
		if (method == null) {
			return;
		}
		try {
			method.invoke(base, value);
		} catch (IllegalAccessException e) {
			throw new IllegalStateException(e);
		} catch (InvocationTargetException e) {
			if (e.getTargetException() instanceof RuntimeException) {
				throw (RuntimeException) e.getTargetException();
			} else {
				throw new IllegalStateException(e.getTargetException());
			}
		}
		context.setPropertyResolved(Boolean.TRUE);
	}

	private static Method getSetterMethod(ELContext context, Object base, String name) {
		return DependencyInjection.inject(CollectionHelper.class).getFirst(DependencyInjection.inject(MethodGetter.class).setClazz(base.getClass()).execute().getOutput());
	}

}
