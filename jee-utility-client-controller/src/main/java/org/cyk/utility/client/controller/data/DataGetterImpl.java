package org.cyk.utility.client.controller.data;

import java.io.Serializable;

import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public class DataGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Data> implements DataGetter,Serializable {
	private static final long serialVersionUID = 1L;

	private SystemAction systemAction;
	private Boolean isInjectIfNull;
	private Class<?> klass;
	
	@Override
	protected Data __execute__() throws Exception {
		Data data = null;
		SystemAction systemAction = getSystemAction();
		Boolean isInjectIfNull = ValueHelper.defaultToIfNull(getIsInjectIfNull(),Boolean.FALSE);
		if(data == null) {
			if(systemAction!=null)
				data = (Data) systemAction.getEntities().getFirst();
		}
		if(data == null) {
			if(Boolean.TRUE.equals(isInjectIfNull)) {
				Class<?> klass = getKlass();
				if(klass == null)
					klass = __inject__(DataHelper.class).getDataClass(systemAction);
				if(klass != null) {
					data = (Data) __inject__(klass);
				}
			}
		}
		return data;
	}
	
	@Override
	public SystemAction getSystemAction() {
		return systemAction;
	}

	@Override
	public DataGetter setSystemAction(SystemAction systemAction) {
		this.systemAction = systemAction;
		return this;
	}

	@Override
	public Boolean getIsInjectIfNull() {
		return isInjectIfNull;
	}
	
	@Override
	public DataGetter setIsInjectIfNull(Boolean isInjectIfNull) {
		this.isInjectIfNull = isInjectIfNull;
		return this;
	}
	
	@Override
	public Class<?> getKlass() {
		return klass;
	}
	
	@Override
	public DataGetter setKlass(Class<?> klass) {
		this.klass = klass;
		return this;
	}
}
