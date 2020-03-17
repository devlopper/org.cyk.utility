package org.cyk.utility.client.controller.web.jsf.primefaces.page;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.enumeration.Action;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.object.__static__.controller.annotation.Input;
import org.cyk.utility.client.controller.web.WebController;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.data.Form;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractEntityEditPageContainerManagedImpl<ENTITY> extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	protected Action action;
	protected Form form;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		action = WebController.getInstance().getRequestParameterAction();
		form = __buildForm__();
	}
	
	protected Form __buildForm__() {
		Object[] arguments = __getFormArguments__();
		if(ArrayHelper.isEmpty(arguments))
			return null;
		return Form.build(arguments);
	}
	
	protected Object[] __getFormArguments__() {
		Class<?> klass = ClassHelper.getParameterAt(getClass(), 0);
		if(klass == null)
			return null;
		Collection<String> arguments = __getFormArgumentsFieldInputsFieldsNames__(klass);
		if(CollectionHelper.isEmpty(arguments))
			return null;
		return new Object[] {Form.FIELD_ENTITY_CLASS,klass,Form.FIELD_ENTITY,__getFormEntity__(),Form.ConfiguratorImpl.FIELD_INPUTS_FIELDS_NAMES,arguments};
	}
	
	protected ENTITY __getFormEntity__() {
		return null;
	}
	
	protected Collection<String> __getFormArgumentsFieldInputsFieldsNames__(Class<?> klass) {
		Collection<Field> fields = FieldHelper.getByAnnotationClass(klass, Input.class);
		if(CollectionHelper.isEmpty(fields))
			return null;
		return FieldHelper.getNames(fields);
	}
	
	@Override
	protected String __getWindowTitleValue__() {
		return form.getTitle();
	}
}
