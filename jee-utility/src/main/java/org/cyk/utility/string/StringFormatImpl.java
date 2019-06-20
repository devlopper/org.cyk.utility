package org.cyk.utility.string;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.map.MapHelper;
import org.cyk.utility.object.ObjectByIntegerMap;

@Dependent
public class StringFormatImpl extends AbstractObject implements StringFormat,Serializable {
	private static final long serialVersionUID = 1L;

	private String value;
	@Inject private ObjectByIntegerMap argumentMap;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		argumentMap.setIsOrdered(Boolean.TRUE);
	}
	
	@Override
	public String getValue() {
		return value;
	}

	@Override
	public StringFormat setValue(String format) {
		this.value = format;
		return this;
	}

	@Override
	public ObjectByIntegerMap getArgumentMap() {
		return argumentMap;
	}

	@Override
	public StringFormat setArgumentMap(ObjectByIntegerMap argumentMap) {
		this.argumentMap = argumentMap;
		return this;
	}
	
	@Override
	public StringFormat setArguments(Object... arguments) {
		getArgumentMap().set(arguments);
		return this;
	}
	
	@Override
	public Object getArgument(Integer index) {
		ObjectByIntegerMap map = getArgumentMap();
		return map == null ? null : map.get(index);
	}

	@Override
	public String evaluate() {
		String format = getValue();
		if(__inject__(StringHelper.class).isBlank(format))
			throw new RuntimeException("Format value is required");
		ObjectByIntegerMap argumentMap = getArgumentMap();
		if(__inject__(MapHelper.class).isEmpty(argumentMap))
			throw new RuntimeException("Format argument are required");
		Collection<Object> objects = new ArrayList<>();
		Collection<Map.Entry<Integer, Object>> entries = getArgumentMap().getEntries();
		for(Map.Entry<Integer, Object> index : entries)
			objects.add(index.getValue());
		return String.format(format, objects.toArray());
	}
}
