package org.cyk.utility.client.controller;

import java.io.Serializable;

import org.cyk.utility.__kernel__.annotation.Default;
import org.cyk.utility.client.controller.component.Component;
import org.cyk.utility.client.controller.component.ComponentBuilder;

public abstract class AbstractObjectLifeCycleListenerImpl extends org.cyk.utility.__kernel__.AbstractObjectLifeCycleListenerImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void listen(Object object, Action action, When when) {
		if(Action.CONSTRUCT.equals(action) && When.AFTER.equals(when) && object instanceof ComponentBuilder)
			listenPostConstructComponentBuilder((ComponentBuilder<?>) object);
		else if(Action.EXECUTE.equals(action) && When.BEFORE.equals(when) && object instanceof ComponentBuilder)
			listenExecuteComponentBuilderBefore((ComponentBuilder<?>) object);
		else if(Action.EXECUTE.equals(action) && When.AFTER.equals(when) && object instanceof ComponentBuilder)
			listenExecuteComponentBuilderAfter((ComponentBuilder<?>) object);
	}
	
	protected void listenPostConstructComponentBuilder(ComponentBuilder<?> componentBuilder) {
		
	}
	
	protected void listenExecuteComponentBuilderBefore(ComponentBuilder<?> componentBuilder) {
		
	}
	
	protected void listenExecuteComponentBuilderAfter(ComponentBuilder<?> componentBuilder) {
		
	}
	
	/**/
	
	protected static void setTargetModelIsDerivable(Component component) {
		component.getTargetModel(Boolean.TRUE).setIsDerivable(Boolean.TRUE).addValueGetterClassQualifiers(Default.class).setValue(component);
	}
	
	protected static void setTargetBindingIsDerivable(Component component) {
		component.getTargetBinding(Boolean.TRUE).setIsDerivable(Boolean.TRUE).addValueGetterClassQualifiers(Default.class).setValue(component);
	}
}
