package org.cyk.utility.client.controller.web.jsf.primefaces.page;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.enumeration.Action;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.__static__.controller.annotation.Input;
import org.cyk.utility.client.controller.web.WebController;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.data.Form;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractEntityEditPageContainerManagedImpl<ENTITY> extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	protected Class<ENTITY> entityClass;
	protected Action action;
	protected Form form;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		entityClass = (Class<ENTITY>) ClassHelper.getParameterAt(getClass(), 0);
		action = WebController.getInstance().getRequestParameterAction();
		form = __buildForm__();
	}
	
	protected Form __buildForm__() {
		Map<Object,Object> arguments = __getFormArguments__();
		if(MapHelper.isEmpty(arguments))
			return null;
		return Form.build(arguments);
	}
	
	protected Map<Object,Object> __getFormArguments__() {
		Collection<String> arguments = __getFormArgumentsFieldInputsFieldsNames__();
		if(CollectionHelper.isEmpty(arguments))
			return null;
		return MapHelper.instantiate(Form.FIELD_ENTITY_CLASS,entityClass,Form.FIELD_ENTITY,__getFormEntity__(),Form.ConfiguratorImpl.FIELD_INPUTS_FIELDS_NAMES,arguments);
	}
	
	protected ENTITY __getFormEntity__() {
		return null;
	}
	
	protected Collection<String> __getFormArgumentsFieldInputsFieldsNames__() {
		Collection<Field> fields = FieldHelper.getByAnnotationClass(entityClass, Input.class);
		if(CollectionHelper.isEmpty(fields))
			return null;
		return FieldHelper.getNames(fields);
	}
	
	@Override
	protected String __getWindowTitleValue__() {
		return form.getTitle();
	}
}
