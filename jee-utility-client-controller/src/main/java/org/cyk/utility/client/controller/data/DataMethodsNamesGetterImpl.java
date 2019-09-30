package org.cyk.utility.client.controller.data;

import java.io.Serializable;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.__kernel__.system.action.SystemActionRead;
import org.cyk.utility.string.AbstractStringsFunctionImpl;

public class DataMethodsNamesGetterImpl extends AbstractStringsFunctionImpl implements DataMethodsNamesGetter,Serializable {
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
				if(dataIdentifier!=null && Boolean.TRUE.equals(ClassHelper.isInstanceOfOne(systemAction.getClass(), SystemActionRead.class))) {
					
				}else {
					names = __inject__(Strings.class).add(Form.METHOD_SUBMIT);	
				}
			}
		}
		return names;
	}
	
	@Override
	public SystemAction getSystemAction() {
		return systemAction;
	}

	@Override
	public DataMethodsNamesGetter setSystemAction(SystemAction systemAction) {
		this.systemAction = systemAction;
		return this;
	}

}
