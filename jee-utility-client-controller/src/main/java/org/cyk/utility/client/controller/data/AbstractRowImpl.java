package org.cyk.utility.client.controller.data;

import java.io.Serializable;

import org.cyk.utility.client.controller.AbstractObject;
import org.cyk.utility.system.action.SystemAction;

public abstract class AbstractRowImpl extends AbstractObject implements Row,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String getUrlBySystemActionClass(Class<? extends SystemAction> aClass) {
		String url = null;
		/*Navigation navigation = getNavigation();
		if(navigation == null) {
			
		}
		if(navigation!=null) {
			url = navigation.getUniformResourceLocator().toString()+"&identifier=1";
		}
		System.out.println("AbstractRowImpl.getUrlBySystemActionClass() : "+url);
		*/
		return url;
	}
	
}
