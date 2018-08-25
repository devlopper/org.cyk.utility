package org.cyk.utility.string.repository;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.cyk.utility.__kernel__.properties.Properties;

public abstract class AbstractStringRepositoryMapImpl extends AbstractStringRepositoryImpl implements StringRepositoryMap,Serializable {
	private static final long serialVersionUID = 1L;

	private final Map<String,String> map = new HashMap<>();

	@Override
	public Map<String, String> getMap() {
		return map;
	}
	
	@Override
	public String getOne(Properties properties) {
		return map.get(properties.getKey());
	} 
	
}
