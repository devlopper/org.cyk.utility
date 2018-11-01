package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.collection;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.column.ColumnBuilder;
import org.cyk.utility.client.controller.component.datatable.DataTable;
import org.cyk.utility.client.controller.component.datatable.DataTableBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;

import lombok.Getter;
import lombok.Setter;

@Named @RequestScoped @Getter @Setter
public class DataTablePage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private DataTable dataTable01;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		dataTable01 = __inject__(DataTableBuilder.class)
				.addColumns(__inject__(ColumnBuilder.class).setHeaderValue("C1")
						,__inject__(ColumnBuilder.class).setHeaderValue("C2")
						,__inject__(ColumnBuilder.class).setHeaderValue("C3"))
				.execute().getOutput();
	}
	
}
