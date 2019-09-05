package org.cyk.utility.client.controller.web.page;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.cyk.utility.client.controller.component.window.AbstractWindowContainerManagedImpl;
import org.cyk.utility.type.BooleanHelper;

public abstract class AbstractPageContainerManagedImpl extends AbstractWindowContainerManagedImpl implements PageContainerManaged,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBeforePostConstruct__() {
		super.__listenBeforePostConstruct__();
		__isInternalLoggable__ = __inject__(BooleanHelper.class).get(__inject__(HttpServletRequest.class).getParameter("__isInternalLoggable__"));
	}
	
	
}
