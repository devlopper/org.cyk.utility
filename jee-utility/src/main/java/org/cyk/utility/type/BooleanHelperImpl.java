package org.cyk.utility.type;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.helper.AbstractHelper;

@ApplicationScoped
public class BooleanHelperImpl extends AbstractHelper implements BooleanHelper,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Boolean get(Object object) {
		if(object instanceof Boolean)
			return (Boolean) object;
		if(object instanceof String) {
			String string = (String) object;
			if("yes".equalsIgnoreCase(string))
				string = "true";
			return Boolean.parseBoolean(string);
		}
		if(object instanceof Number) {
			return ((Number)object).doubleValue() > 0;
		}
		return null;
	}

}
