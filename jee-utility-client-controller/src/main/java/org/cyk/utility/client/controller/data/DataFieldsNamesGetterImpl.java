package org.cyk.utility.client.controller.data;

import java.io.Serializable;

import org.cyk.utility.string.AbstractStringsFunctionImpl;
import org.cyk.utility.string.Strings;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionDelete;
import org.cyk.utility.system.action.SystemActionRead;
import org.cyk.utility.system.action.SystemActionUpdate;

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
				if(__injectCollectionHelper__().isNotEmpty(systemAction.getEntities()))
					data = (Data) systemAction.getEntities().getFirst();
				Object dataIdentifier = __injectFieldHelper__().getFieldValueSystemOrBusinessIdentifier(data);
				if(dataIdentifier == null && Boolean.TRUE.equals(__injectClassHelper__().isInstanceOfOne(systemAction.getClass(), SystemActionRead.class,SystemActionUpdate.class,SystemActionDelete.class))) {
					names = __inject__(Strings.class).add("code");
				}else {
					names = __inject__(DataHelper.class).getPropertiesFieldsNames(systemAction.getEntityClass());	
				}
				
				if(data instanceof DataIdentifiedByStringAndCoded && names != null && names.getSize() > 1)
					names.remove(DataIdentifiedByStringAndCoded.PROPERTY_IDENTIFIER);
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
