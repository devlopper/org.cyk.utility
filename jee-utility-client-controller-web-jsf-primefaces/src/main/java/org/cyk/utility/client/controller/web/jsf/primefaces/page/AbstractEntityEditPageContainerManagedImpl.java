package org.cyk.utility.client.controller.web.jsf.primefaces.page;

import java.io.Serializable;

import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.data.Form;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractEntityEditPageContainerManagedImpl<ENTITY> extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	protected Form form;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		form = Form.build(Form.FIELD_ENTITY_CLASS,ClassHelper.getParameterAt(getClass(), 0));
	}
	
	@Override
	protected String __getWindowTitleValue__() {
		return form.getTitle();
	}
}
