package org.cyk.utility.field;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

@Dependent
public class FieldInstanceImpl extends AbstractObject implements FieldInstance,Serializable {
	private static final long serialVersionUID = 1L;
	
	private Class<?> clazz;
	private String path;
	private Field field;
	private Class<?> type;
	private Boolean isGeneratable;
	private Map<Action,Class<?>> actionsClassesMap;
	
	@Override
	public Class<?> getClazz() {
		return clazz;
	}

	@Override
	public FieldInstance setClazz(Class<?> clazz) {
		this.clazz = clazz;
		return this;
	}
	
	@Override
	public String getPath() {
		return path;
	}

	@Override
	public FieldInstance setPath(String path) {
		this.path = path;
		return this;
	}
	
	@Override
	public Field getField() {
		return field;
	}

	@Override
	public FieldInstance setField(Field field) {
		this.field = field;
		return this;
	}

	@Override
	public Class<?> getType() {
		return type;
	}

	@Override
	public FieldInstance setType(Class<?> type) {
		this.type = type;
		return this;
	}

	@Override
	public Boolean getIsGeneratable() {
		return isGeneratable;
	}
	
	@Override
	public FieldInstance setIsGeneratable(Boolean isValueGeneratable) {
		this.isGeneratable = isValueGeneratable;
		return this;
	}
	
	@Override
	public Map<Action, Class<?>> getActionsClassesMap() {
		return actionsClassesMap;
	}
	
	@Override
	public Map<Action, Class<?>> getActionsClassesMap(Boolean instantiateIfNull) {
		actionsClassesMap = getActionsClassesMap();
		if(actionsClassesMap == null && Boolean.TRUE.equals(instantiateIfNull))
			actionsClassesMap = new HashMap<>();
		return actionsClassesMap;
	}
	
	@Override
	public FieldInstance setActionsClassesMap(Map<Action, Class<?>> actionsClassesMap) {
		this.actionsClassesMap = actionsClassesMap;
		return this;
	}
	
	@Override
	public FieldInstance setActionClass(Action action, Class<?> klass) {
		getActionsClassesMap(Boolean.TRUE).put(action, klass);
		return this;
	}
	
	@Override
	public Class<?> getActionClass(Action action) {
		Map<Action, Class<?>> actionsClassesMap = getActionsClassesMap();
		return actionsClassesMap == null ? null : actionsClassesMap.get(action);
	}
}
