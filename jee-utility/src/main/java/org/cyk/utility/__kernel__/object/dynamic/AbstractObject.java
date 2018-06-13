package org.cyk.utility.__kernel__.object.dynamic;

import java.io.Serializable;

import org.cyk.utility.__kernel__.properties.Properties;

public abstract class AbstractObject extends org.cyk.utility.__kernel__.object.AbstractObject implements Objectable, Serializable {
	private static final long serialVersionUID = 1L;

	private Properties properties;
	
	@Override
	public Properties getProperties() {
		if(properties == null)
			properties = new Properties();
		return properties;
	}
	
	@Override
	public Objectable setProperties(Properties properties) {
		this.properties = properties;
		return this;
	}
	
}
