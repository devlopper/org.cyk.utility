package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.crud;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.ComponentRole;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.component.window.AbstractWindowContainerManagedWindowBuilderImpl;
import org.cyk.utility.client.controller.data.Form;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.instance.InstanceGetter;
import org.cyk.utility.request.RequestParameterValueMapper;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionDelete;
import org.cyk.utility.system.action.SystemActionRead;
import org.cyk.utility.system.action.SystemActionUpdate;
import org.cyk.utility.value.ValueUsageType;

public class MyEntityEditWindowBuilderImpl extends AbstractWindowContainerManagedWindowBuilderImpl implements MyEntityEditWindowBuilder, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		/*
		SystemAction systemAction = __inject__(RequestParameterValueMapper.class).setParameterName(SystemAction.class).execute().getOutputAs(SystemAction.class);
		Class<?> clazz = __inject__(RequestParameterValueMapper.class).setParameterName(Class.class).execute().getOutputAs(Class.class);
		systemAction.getEntities(Boolean.TRUE).setElementClass(clazz);
		
		Object instance = null;
		if(systemAction instanceof SystemActionRead || systemAction instanceof SystemActionUpdate || systemAction instanceof SystemActionDelete) {
			Long identifier = __inject__(RequestParameterValueMapper.class).setParameterName(FieldName.IDENTIFIER).execute().getOutputAs(Long.class);
			instance = __injectCollectionHelper__().getFirst(__inject__(InstanceGetter.class).setClazz(clazz).setFieldName(FieldName.IDENTIFIER)
					.setValueUsageType(ValueUsageType.SYSTEM).setValue(identifier).execute().getOutput());	
		}else if(systemAction instanceof SystemActionCreate) {
			instance = __inject__(clazz);
		}
		
		if(instance!=null && systemAction!=null)
			systemAction.getEntities(Boolean.TRUE).add(instance);
		*/
		//window.setTitleValue("MyEntity CRUD : "+action);
		
		SystemAction systemAction = getSystemAction();
		if(systemAction!=null) {
			MyEntityEditForm form = __inject__(MyEntityEditForm.class);
			form.setData((MyEntity) systemAction.getEntities().getAt(0));
			form.setTitle(systemAction.getIdentifier().toString()+" "+systemAction.getEntities().getElementClass().getSimpleName());
			
			ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
			
			viewBuilder.addComponentBuilderByObjectByFieldNames(form, Form.PROPERTY_TITLE).addRoles(ComponentRole.TITLE);
		
			viewBuilder.addComponentBuilderByObjectByFieldNames(form.getData(), MyEntity.PROPERTY_CODE);
			viewBuilder.addComponentBuilderByObjectByFieldNames(form.getData(), MyEntity.PROPERTY_NAME);
			viewBuilder.addComponentBuilderByObjectByFieldNames(form.getData(), MyEntity.PROPERTY_DESCRIPTION);
			
			CommandableBuilder commandableBuilder = (CommandableBuilder) viewBuilder.addComponentBuilderByObjectByMethodName(form, Form.METHOD_SUBMIT);
			commandableBuilder.setName(systemAction.getIdentifier().toString());	
				
			setView(viewBuilder);
		}
	}
	
}
