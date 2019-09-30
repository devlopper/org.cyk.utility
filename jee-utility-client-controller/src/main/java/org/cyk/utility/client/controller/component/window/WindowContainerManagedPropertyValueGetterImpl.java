package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.__kernel__.system.action.SystemActionRead;
import org.cyk.utility.client.controller.data.Data;
import org.cyk.utility.client.controller.data.DataHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.request.RequestParameterValueMapper;

public class WindowContainerManagedPropertyValueGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Object> implements WindowContainerManagedPropertyValueGetter,Serializable {
	private static final long serialVersionUID = 1L;

	private WindowContainerManaged containerManaged;
	private WindowContainerManagedProperty property;
	private WindowContainerManagedWindowBuilder container;
	
	@Override
	protected Object __execute__() throws Exception {
		Object value = null;
		//WindowContainerManaged containerManaged = getContainerManaged();
		WindowContainerManagedWindowBuilder container = getContainer();
		WindowContainerManagedProperty property = getProperty();
		if(property == null)
			throw new RuntimeException(getClass()+" : property is required");
		switch(property) {
		case WINDOW:
			value = __inject__(WindowBuilder.class);
			break;
		case SYSTEM_ACTION:
			SystemAction systemAction = __inject__(RequestParameterValueMapper.class).setParameterNameAsActionClass().execute().getOutputAs(SystemAction.class);
			if(systemAction instanceof SystemActionRead) {
				if(CollectionHelper.isEmpty(systemAction.getEntitiesIdentifiers())) {
					Data data = (Data) __inject__(systemAction.getEntityClass());
					systemAction.getEntities(Boolean.TRUE).add(data);
				}
			}
			value = systemAction;
			break;
		case FORM_CLASS:
			if(container != null)
				value = __inject__(DataHelper.class).getFormClass(container.getSystemAction());
			break;
		case ROW_CLASS:
			if(container != null)
				value = __inject__(DataHelper.class).getRowClass(container.getSystemAction());
			break;
		}
		return value;
	}
	
	@Override
	public WindowContainerManaged getContainerManaged() {
		return containerManaged;
	}
	
	@Override
	public WindowContainerManagedPropertyValueGetter setContainerManaged(WindowContainerManaged containerManaged) {
		this.containerManaged = containerManaged;
		return this;
	}
	
	@Override
	public WindowContainerManagedPropertyValueGetter setContainer(WindowContainerManagedWindowBuilder container) {
		this.container = container;
		return this;
	}

	@Override
	public WindowContainerManagedWindowBuilder getContainer() {
		return container;
	}

	@Override
	public WindowContainerManagedPropertyValueGetter setProperty(WindowContainerManagedProperty property) {
		this.property = property;
		return this;
	}

	@Override
	public WindowContainerManagedProperty getProperty() {
		return property;
	}

}
