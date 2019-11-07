package org.cyk.utility.client.controller;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.annotation.Controller;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.system.action.AbstractSystemActionFieldsGetterImpl;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.client.controller.component.annotation.Input;
import org.cyk.utility.client.controller.component.annotation.Output;
import org.cyk.utility.client.controller.data.DataIdentifiedByStringAndLinkedByStringAndNamed;

@Controller
public class SystemActionFieldsGetterImpl extends AbstractSystemActionFieldsGetterImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Collection<Field> __get__(Class<? extends SystemAction> systemActionClass, Object systemActionIdentifier,Class<?> entityClass) {
		if(entityClass.isInterface()) {
			LogHelper.logWarning("it would be better to use class instead of interface("+entityClass+"). for data class.implementation will be used.", SystemActionFieldsGetterImpl.class);
			entityClass = DependencyInjection.inject(entityClass).getClass();
		}	
		Collection<Field> fields = FieldHelper.getByAnnotationsClasses(entityClass, Input.class,Output.class);	
		if(CollectionHelper.isEmpty(fields))
			return null;
		if(fields.size() > 1) {
			Field field = FieldHelper.getByName(fields, FieldHelper.NAME_CODE);
			if(field != null) {
				field = FieldHelper.getByName(fields, FieldHelper.NAME_IDENTIFIER);
				if(field != null)
					fields.remove(field);
			}
		}
		
		if(ClassHelper.isInstanceOf(entityClass, org.cyk.utility.client.controller.data.hierarchy.DataIdentifiedByString.class)) {
			FieldHelper.removeByNames(fields, org.cyk.utility.client.controller.data.hierarchy.DataIdentifiedByString.PROPERTY_PARENTS
					,org.cyk.utility.client.controller.data.hierarchy.DataIdentifiedByString.PROPERTY_NUMBER_OF_PARENTS
					,org.cyk.utility.client.controller.data.hierarchy.DataIdentifiedByString.PROPERTY_NUMBER_OF_CHILDREN);
		}else if(ClassHelper.isInstanceOf(entityClass, DataIdentifiedByStringAndLinkedByStringAndNamed.class)) {
			
		}
		
		return fields;
	}
	
}
