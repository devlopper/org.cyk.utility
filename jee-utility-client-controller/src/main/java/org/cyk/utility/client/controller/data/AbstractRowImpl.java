package org.cyk.utility.client.controller.data;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.client.controller.AbstractObject;
import org.cyk.utility.client.controller.component.command.Commandable;
import org.cyk.utility.object.ObjectByClassMap;

public abstract class AbstractRowImpl extends AbstractObject implements Row,Serializable {
	private static final long serialVersionUID = 1L;

	private RowListeners listeners;
	private ObjectByClassMap parametersMap;
	
	@Override
	public RowListeners getListeners() {
		return listeners;
	}
	
	@Override
	public RowListeners getListeners(Boolean injectIfNull) {
		if(listeners == null && Boolean.TRUE.equals(injectIfNull))
			listeners = __inject__(RowListeners.class);
		return listeners;
	}
	
	@Override
	public Row setListeners(RowListeners listeners) {
		this.listeners = listeners;
		return this;
	}
	
	@Override
	public Row addListeners(Collection<RowListener> listeners) {
		getListeners(Boolean.TRUE).add(listeners);
		return this;
	}
	
	@Override
	public Row addListeners(RowListener... listeners) {
		getListeners(Boolean.TRUE).add(listeners);
		return this;
	}
	
	@Override
	public ObjectByClassMap getNavigationParametersMap() {
		return parametersMap;
	}
	
	@Override
	public Row setNavigationParametersMap(ObjectByClassMap parametersMap) {
		this.parametersMap = parametersMap;
		return this;
	}
	
	@Override
	public ObjectByClassMap getNavigationParametersMap(Boolean injectIfNull) {
		if(parametersMap == null && Boolean.TRUE.equals(injectIfNull))
			parametersMap = __inject__(ObjectByClassMap.class);
		return parametersMap;
	}
	
	@Override
	public Row setNavigationParameters(Object... parameters) {
		getNavigationParametersMap(Boolean.TRUE).set(parameters);
		return this;
	}
	
	@Override
	public Commandable getCommandableByIdentifier(Object identifier) {
		System.out.println("AbstractRowImpl.getCommandableByIdentifier() : "+identifier);
		return null;
	}
	
	public static final String FIELD_PARAMETERS_MAP = "parametersMap";
	public static final String FIELD_LISTENERS = "listeners";
	
}
