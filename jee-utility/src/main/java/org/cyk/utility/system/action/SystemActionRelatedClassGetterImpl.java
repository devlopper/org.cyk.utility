package org.cyk.utility.system.action;

import java.io.Serializable;

import org.cyk.utility.clazz.AbstractClassFunctionImpl;
import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.string.Strings;

@SuppressWarnings("rawtypes")
public class SystemActionRelatedClassGetterImpl extends AbstractClassFunctionImpl implements SystemActionRelatedClassGetter,Serializable {
	private static final long serialVersionUID = 1L;

	private SystemActionRelatedClassesNamesGetter namesGetter;
	
	@Override
	protected Class __execute__() throws Exception {
		Class clazz = null;
		Strings names = null;
		SystemActionRelatedClassesNamesGetter namesGetter = getNamesGetter();
		if(namesGetter!=null)
			names = namesGetter.execute().getOutput();
		if(__injectCollectionHelper__().isNotEmpty(names))
			for(String index : names.get()) {
				clazz = (Class<?>) __inject__(ClassHelper.class).getByName(index);
				if(clazz != null)
					break;
			}
		return clazz;
	}
	
	@Override
	public SystemActionRelatedClassesNamesGetter getNamesGetter() {
		return namesGetter;
	}

	@Override
	public SystemActionRelatedClassGetter setNamesGetter(SystemActionRelatedClassesNamesGetter namesGetter) {
		this.namesGetter = namesGetter;
		return this;
	}

}