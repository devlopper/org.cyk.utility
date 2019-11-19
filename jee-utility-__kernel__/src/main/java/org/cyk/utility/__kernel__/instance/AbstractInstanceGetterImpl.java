package org.cyk.utility.__kernel__.instance;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.ValueUsageType;

public abstract class AbstractInstanceGetterImpl extends AbstractObject implements InstanceGetter,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public <INSTANCE> INSTANCE getByIdentifier(Class<INSTANCE> klass, Object identifier,ValueUsageType valueUsageType) {
		if(klass == null || identifier == null)
			return null;
		if(valueUsageType == null)
			valueUsageType = ValueUsageType.SYSTEM;
		return __getByIdentifier__(klass, identifier, valueUsageType);
	}

	protected abstract <INSTANCE> INSTANCE __getByIdentifier__(Class<INSTANCE> klass, Object identifier,ValueUsageType valueUsageType);
	
	@Override
	public <INSTANCE> Collection<INSTANCE> get(Class<INSTANCE> klass, Properties properties) {
		if(klass == null)
			return null;
		return __get__(klass, properties);
	}
	
	protected <INSTANCE> Collection<INSTANCE> __get__(Class<INSTANCE> klass,Properties properties) {
		throw ThrowableHelper.NOT_YET_IMPLEMENTED;
	}
	
	@Override
	public <INSTANCE> Collection<INSTANCE> getAll(Class<INSTANCE> klass, Properties properties) {
		if(klass == null)
			return null;
		return __getAll__(klass, properties);
	}
	
	protected <INSTANCE> Collection<INSTANCE> __getAll__(Class<INSTANCE> klass,Properties properties) {
		throw ThrowableHelper.NOT_YET_IMPLEMENTED;
	}
}