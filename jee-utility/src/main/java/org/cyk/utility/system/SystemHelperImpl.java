package org.cyk.utility.system;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.constant.ConstantNull;
import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.value.ValueHelper;

@ApplicationScoped
public class SystemHelperImpl extends AbstractHelper implements SystemHelper, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String getProperty(String name,String defaultToIfBlank) {
		String value = System.getProperty(name);
		if(__inject__(StringHelper.class).isBlank(value))
			value = defaultToIfBlank;
		return value;
	}
	
	@Override
	public String getProperty(String name) {
		return getProperty(name,ConstantNull.STRING);
	}

	@Override
	public String getProperty(String name, Boolean defaultOnOperatingSystemIfBlank) {
		String value = getProperty(name);
		if(__inject__(StringHelper.class).isBlank(value) && Boolean.TRUE.equals(defaultOnOperatingSystemIfBlank)) {
			value = __inject__(OperatingSystemHelper.class).getProperty(name);
		}
		return value;
	}

	@Override
	public String getPropertyThrowIfBlank(String name) {
		String value = getProperty(name);
		return __inject__(ValueHelper.class).returnOrThrowIfBlank(name, value);
	}

	@Override
	public String getPropertyThrowIfBlank(String name, Boolean defaultOnOperatingSystemIfBlank) {
		String value = getProperty(name,defaultOnOperatingSystemIfBlank);
		return __inject__(ValueHelper.class).returnOrThrowIfBlank(name, value);
	}
	
	@Override
	public SystemHelper executeCommand(String command) {
		if(__inject__(StringHelper.class).isNotBlank(command))
			try {
				Runtime.getRuntime().exec(command);
			} catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		return this;
	}
	
}
