package org.cyk.utility.cache;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.properties.Properties;

@Dependent
public class CacheImpl extends AbstractObject implements Cache,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Boolean isDisabled() {
		return getProperty(Properties.DISABLED) == null || (Boolean) getProperty(Properties.DISABLED);
	}
	
}
