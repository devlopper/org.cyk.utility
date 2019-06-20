package org.cyk.utility.enumeration;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.helper.AbstractHelper;

@ApplicationScoped
public class EnumerationHelperImpl extends AbstractHelper implements EnumerationHelper, Serializable {
	private static final long serialVersionUID = 1L;

	/*@Override
	public <T extends Enum<T>> T getByName(Class<Enum<T>> enumerationClass, Object value) {
		return enumerationClass.getEnumConstants()[];
	}*/

}
