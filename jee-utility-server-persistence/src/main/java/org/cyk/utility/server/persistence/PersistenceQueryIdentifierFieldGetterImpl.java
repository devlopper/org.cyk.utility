package org.cyk.utility.server.persistence;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

import javax.enterprise.context.Dependent;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.field.FieldsGetter;
import org.cyk.utility.field.Fields;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

@Dependent
public class PersistenceQueryIdentifierFieldGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Collection<Field>> implements PersistenceQueryIdentifierFieldGetter, Serializable {
	private static final long serialVersionUID = 1L;

	private static final String[] PREFIXES = {"create","read","count","update","delete","execute"};
	
	@Override
	protected Collection<Field> __execute__() throws Exception {
		Fields classFields = __inject__(FieldsGetter.class).execute(getClazz()).getOutput();
		Collection<Field> identifierfields = null;
		if(CollectionHelper.isNotEmpty(classFields)){
			for(Field index : classFields.get()){
				if(StringUtils.startsWithAny(index.getName(), PREFIXES)){
					if(identifierfields == null)
						identifierfields = new ArrayList<Field>();
					identifierfields.add(index);
				}
			}
		}
		return identifierfields;
	}
	
	@Override
	public PersistenceQueryIdentifierFieldGetter setClazz(Class<?> aClass) {
		getProperties().setClass(aClass);
		return this;
	}
	
	@Override
	public Class<?> getClazz() {
		return (Class<?>) getProperties().getClazz();
	}
}
