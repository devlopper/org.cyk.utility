package org.cyk.utility.client.controller.component;

import org.cyk.utility.bean.Property;
import org.cyk.utility.client.controller.Objectable;
import org.cyk.utility.client.controller.component.layout.LayoutItem;
import org.cyk.utility.client.controller.event.Events;
import org.cyk.utility.object.Objects;

public interface Component extends Objectable {

	ComponentBuilder<?> getBuilder();
	Component setBuilder(ComponentBuilder<?> builder);
	
	LayoutItem getLayoutItem();
	Component setLayoutItem(LayoutItem layoutItem);
	
	ComponentRoles getRoles();
	ComponentRoles getRoles(Boolean injectIfNull);
	Component setRoles(ComponentRoles roles);
	
	Objects getUpdatables();
	Objects getUpdatables(Boolean injectIfNull);
	Component setUpdatables(Objects updatables);
	
	Events getEvents();
	Events getEvents(Boolean injectIfNull);
	Component setEvents(Events events);
	
	Property getTargetModel();
	Property getTargetModel(Boolean injectIfNull);
	Component setTargetModel(Property targetModel);
	Object getTargetModelValue();
	
	Property getTargetBinding();
	Property getTargetBinding(Boolean injectIfNull);
	Component setTargetBinding(Property targetBinding);
	Object getTargetBindingValue();
	
	Throwable getThrowable();
	Component setThrowable(Throwable throwable);
	
	String getThrowableInternalizationMessage();
	Component setThrowableInternalizationMessage(String throwableInternalizationMessage);
	
	String getGetByIdentifierExpressionLanguageFormat();
	Component setGetByIdentifierExpressionLanguageFormat(String getByIdentifierExpressionLanguageFormat);
	
	Object getLinkedTo();
	Component setLinkedTo(Object linkedTo);
}
