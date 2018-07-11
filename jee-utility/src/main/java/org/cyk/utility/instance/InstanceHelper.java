package org.cyk.utility.instance;

import java.util.Collection;

import org.cyk.utility.field.FieldName;
import org.cyk.utility.helper.Helper;
import org.cyk.utility.value.ValueUsageType;

public interface InstanceHelper extends Helper {

	<INSTANCE> Collection<INSTANCE> getByFieldNameByValueUsageType(Class<INSTANCE> aClass,FieldName fieldName,ValueUsageType valueUsageType,Object value);
	<INSTANCE> INSTANCE getByIdentifierBusiness(Class<INSTANCE> aClass,Object value);
}
