package org.cyk.utility.playground.client.controller.component;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractAction;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.DataTable;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.CommandButton;
import org.cyk.utility.playground.client.controller.entities.Namable;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class DataTableLazyPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private DataTable dataTable;
	private CommandButton commandButtonOperation1Mass;
	
	@Override
	protected String __getWindowTitleValue__() {
		return "DataTable Lazy Page";
	}
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		dataTable = Builder.build(DataTable.class,Map.of(DataTable.FIELD_LAZY,Boolean.TRUE,DataTable.ConfiguratorImpl.FIELD_ENTIY_CLASS,Namable.class
				,DataTable.FIELD_SELECTION_MODE,"multiple"));
		
		dataTable.getDialog().getCloseAjax().setListener(new AbstractAction.Listener() {			
			@Override
			public void listenAction(Object argument) {
				System.out.println("DataTableLazyPage.__listenPostConstruct__().new Listener() {...}.listenAction() : "+LocalDateTime.now());
			}
		});
		dataTable.getDialog().getCloseAjax().setDisabled(Boolean.FALSE);
		
		commandButtonOperation1Mass = Builder.build(CommandButton.class,Map.of(CommandButton.FIELD_VALUE,"Opération 1",CommandButton.ConfiguratorImpl.FIELD_DATA_TABLE,dataTable));
		
	}
	
}
