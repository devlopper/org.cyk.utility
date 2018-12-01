package org.cyk.utility.client.controller.data;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.client.controller.AbstractObject;
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
		return (RowListeners) __getInjectIfNull__(FIELD_LISTENERS, injectIfNull);
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
		return (ObjectByClassMap) __getInjectIfNull__(FIELD_PARAMETERS_MAP, injectIfNull);
	}
	
	@Override
	public Row setNavigationParameters(Object... parameters) {
		getNavigationParametersMap(Boolean.TRUE).set(parameters);
		return this;
	}
	
	public static final String FIELD_PARAMETERS_MAP = "parametersMap";
	public static final String FIELD_LISTENERS = "listeners";
	
}
