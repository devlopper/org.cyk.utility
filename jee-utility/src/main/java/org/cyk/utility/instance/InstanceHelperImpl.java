package org.cyk.utility.instance;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.number.NumberHelper;

@Singleton
public class InstanceHelperImpl extends AbstractHelper implements InstanceHelper,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public InstanceHelper get() {
		return __inject__(InstanceHelper.class);
	}

	@Override
	public ClassHelper getClassHelper() {
		return __inject__(ClassHelper.class);
	}

	@Override
	public NumberHelper getNumberHelper() {
		return __inject__(NumberHelper.class);
	}

}
