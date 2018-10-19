package org.cyk.utility.client.controller.component;

import java.io.Serializable;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public abstract class AbstractComponentBuilderImpl<COMPONENT extends Component> extends AbstractFunctionWithPropertiesAsInputImpl<COMPONENT> implements ComponentBuilder<COMPONENT> , Serializable {
	private static final long serialVersionUID = 1L;

	private Class<COMPONENT> componentClass;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setComponentClass(__getComponentClass__());
		__inject__(ComponentBuilderPostConstructListener.class).setObject(this).execute();
	}
	
	protected Class<COMPONENT> __getComponentClass__(){
		return (Class<COMPONENT>) __injectClassHelper__().getParameterAt(getClass(), 0, Object.class);
	}
	
	@Override
	protected COMPONENT __execute__() throws Exception {
		COMPONENT component = __inject__(componentClass);
		__inject__(ComponentBuilderExecuteListenerBefore.class).setObject(this).setComponent(component).execute();
		__execute__(component);
		__inject__(ComponentBuilderExecuteListenerAfter.class).setObject(this).setComponent(component).execute();
		return component;
	}
	
	protected void __execute__(COMPONENT component) {
		component.getProperties().copyNonNullKeysFrom(getOutputProperties());		
	}
	
	@Override
	public Class<COMPONENT> getComponentClass() {
		return componentClass;
	}
	
	@Override
	public ComponentBuilder<COMPONENT> setComponentClass(Class<COMPONENT> componentClass) {
		this.componentClass = componentClass;
		return this;
	}
	
	@Override
	public ComponentBuilder<COMPONENT> setOutputProperties(Properties outputProperties) {
		return (ComponentBuilder<COMPONENT>) super.setOutputProperties(outputProperties);
	}
	
	@Override
	public ComponentBuilder<COMPONENT> setOutputProperty(Object key, Object value) {
		return (ComponentBuilder<COMPONENT>) super.setOutputProperty(key, value);
	}
}
