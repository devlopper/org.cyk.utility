package org.cyk.utility.client.controller.web.jsf.primefaces.page;

import java.io.Serializable;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.primefaces.model.menu.MenuModel;

public interface PageMenuModelGetter {

	MenuModel get(AbstractPageContainerManagedImpl page);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements PageMenuModelGetter,Serializable {
		
		@Override
		public MenuModel get(AbstractPageContainerManagedImpl page) {
			return null;
		}
		
	}
	
	static PageMenuModelGetter getInstance() {
		return Helper.getInstance(PageMenuModelGetter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();	
}