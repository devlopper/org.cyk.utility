package org.cyk.utility.client.controller.web.jsf.primefaces.page;

import java.io.Serializable;

import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.data.List;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.DataTable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractEntityListPageContainerManagedImpl<ENTITY> extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	protected List list;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		@SuppressWarnings("unchecked")
		Class<ENTITY> entityClass = (Class<ENTITY>) ClassHelper.getParameterAt(getClass(), 0);
		
		list = List.build(List.FIELD_ENTITY_CLASS,entityClass,DataTable.FIELD_LAZY,Boolean.TRUE);
	}
	
}
