package org.cyk.utility.client.controller.component.theme;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.system.SystemHelper;

@SuppressWarnings("rawtypes")
public abstract class AbstractThemeClassGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Class> implements ThemeClassGetter,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Class __execute__() throws Exception {
		Class<?> klass = null;
		String name = __inject__(SystemHelper.class).getProperty(CLASS_NAME_IDENTIFIER, Boolean.TRUE);
		if(__injectStringHelper__().isBlank(name)) {
			klass = CLASS;
			if(klass == null)
				name = __injectValueHelper__().returnOrThrowIfBlank("user interface theme class", CLASS_NAME);
		}
		
		if(klass!=null || __injectStringHelper__().isNotBlank(name))
			__logConfig__("user interface theme class name is : "+(klass == null ? name : klass.getName()));
		
		if(klass == null)
			klass =  __injectClassHelper__().getByName(name, Boolean.FALSE);
		
		return klass;
	}
	
	public static Class<?> CLASS;
	public static String CLASS_NAME;
}
