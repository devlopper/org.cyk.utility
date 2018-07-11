package org.cyk.utility.instance;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Singleton;

import org.cyk.utility.field.FieldName;
import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.value.ValueUsageType;

@Singleton
public class InstanceHelperImpl extends AbstractHelper implements InstanceHelper,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public <INSTANCE> Collection<INSTANCE> getByFieldNameByValueUsageType(Class<INSTANCE> aClass, FieldName fieldName,ValueUsageType valueUsageType, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <INSTANCE> INSTANCE getByIdentifierBusiness(Class<INSTANCE> aClass, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
