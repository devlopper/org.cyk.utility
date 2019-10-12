package org.cyk.utility.client.controller.component.theme;

import java.io.Serializable;

import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.system.SystemHelper;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

@SuppressWarnings("rawtypes")
public abstract class AbstractThemeClassGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Class> implements ThemeClassGetter,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Class __execute__() throws Exception {
		Class<?> klass = null;
		String name = SystemHelper.getProperty(CLASS_NAME_IDENTIFIER, Boolean.TRUE);
		if(StringHelper.isBlank(name)) {
			klass = CLASS;
			if(klass == null)
				name = ValueHelper.returnOrThrowIfBlank("user interface theme class", CLASS_NAME);
		}
		
		if(klass!=null || StringHelper.isNotBlank(name))
			__logConfig__("user interface theme class name is : "+(klass == null ? name : klass.getName()));
		
		if(klass == null)
			klass =  ClassHelper.getByName(name, Boolean.FALSE);
		
		return klass;
	}
	
	/**/
	
	public static Class<?> CLASS;
	public static String CLASS_NAME;
}
