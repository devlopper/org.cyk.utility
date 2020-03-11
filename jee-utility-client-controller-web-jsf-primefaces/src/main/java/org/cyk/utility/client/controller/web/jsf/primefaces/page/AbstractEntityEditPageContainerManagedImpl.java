package org.cyk.utility.client.controller.web.jsf.primefaces.page;

import java.io.Serializable;

import org.cyk.utility.__kernel__.enumeration.Action;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.client.controller.web.WebController;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.data.Form;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractEntityEditPageContainerManagedImpl<ENTITY> extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	protected Action action;
	protected Form form;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		action = WebController.getInstance().getRequestParameterAction();
		form = Form.build(Form.FIELD_ENTITY_CLASS,ClassHelper.getParameterAt(getClass(), 0));
	}
	
	@Override
	protected String __getWindowTitleValue__() {
		return form.getTitle();
	}
}
