package org.cyk.utility.client.controller.data;

import java.io.Serializable;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.__kernel__.system.action.SystemActionDelete;
import org.cyk.utility.__kernel__.system.action.SystemActionRead;
import org.cyk.utility.__kernel__.system.action.SystemActionUpdate;
import org.cyk.utility.string.AbstractStringsFunctionImpl;

public class DataFieldsNamesGetterImpl extends AbstractStringsFunctionImpl implements DataFieldsNamesGetter,Serializable {
	private static final long serialVersionUID = 1L;

	private SystemAction systemAction;
	
	@Override
	protected Strings __execute__() throws Exception {
		Strings names = null;
		SystemAction systemAction = getSystemAction();
		if(names == null) {
			if(systemAction!=null) {
				Data data = null;
				if(CollectionHelper.isNotEmpty(systemAction.getEntities()))
					data = (Data) systemAction.getEntities().getFirst();
				Object dataIdentifier = FieldHelper.readSystemIdentifierOrBusinessIdentifier(data);
				if(dataIdentifier == null && Boolean.TRUE.equals(ClassHelper.isInstanceOfOne(systemAction.getClass(), SystemActionRead.class,SystemActionUpdate.class,SystemActionDelete.class))) {
					names = __inject__(Strings.class).add("code");
				}else {
					Class<?> klass = __inject__(DataHelper.class).getDataClass(systemAction);
					names = __inject__(DataHelper.class).getPropertiesFieldsNames(klass);	
				}
				
				if(data instanceof DataIdentifiedByStringAndCoded && names != null && names.getSize() > 1)
					names.remove(DataIdentifiedByStringAndCoded.PROPERTY_IDENTIFIER);
				
				if(data instanceof org.cyk.utility.client.controller.data.hierarchy.DataIdentifiedByString && names != null && names.getSize() > 1)
					names.removeMany(org.cyk.utility.client.controller.data.hierarchy.DataIdentifiedByString.PROPERTY_PARENTS
							,org.cyk.utility.client.controller.data.hierarchy.DataIdentifiedByString.PROPERTY_NUMBER_OF_PARENTS
							,org.cyk.utility.client.controller.data.hierarchy.DataIdentifiedByString.PROPERTY_NUMBER_OF_CHILDREN);
			}
		}
		return names;
	}
	
	@Override
	public SystemAction getSystemAction() {
		return systemAction;
	}

	@Override
	public DataFieldsNamesGetter setSystemAction(SystemAction systemAction) {
		this.systemAction = systemAction;
		return this;
	}

}
