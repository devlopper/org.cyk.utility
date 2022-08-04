package org.cyk.utility.client.controller.web.jsf.primefaces.model.input;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.primefaces.PrimeFaces;

import lombok.Getter;
import lombok.Setter;

@Deprecated
public class DataTableEntity<ENTITY> extends org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.DataTable implements Serializable {

	@Getter @Setter private Class<ENTITY> entityClass;
	@Getter @Setter private Collection<ENTITY> value;
	//@Getter @Setter private Listener<ENTITY> listener;
	@Getter @Setter private String dialogOutputPanelIdentifier = "dialogOutputPanel"+getIdentifier();	
	//@Getter @Setter private Dialog dialog = new Dialog();
	
	/**/
	
	public void openDialogForAdd() {
		//if(listener != null)
		//	listener.listenOpenDialogForAdd(this);
		PrimeFaces.current().executeScript("PF('"+dialog.getWidgetVar()+"').show();");
	}
	
	public void add() {
		if(entityClass == null)
			return;
		ENTITY entity = ClassHelper.instanciate(entityClass);
		if(entity == null)
			return;
		if(value == null)
			value = new ArrayList<>();
		value.add(entity);
		//if(listener != null)
		//	listener.listenAdd(this,entity);
	}
	
	public void remove(ENTITY entity) {
		if(entity == null)
			return;
		value.remove(entity);
		//if(listener != null)
		//	listener.listenRemove(this,entity);
	}
	
	/**/
	
	public static interface Listener<ENTITY> {
		
		void listenOpenDialogForAdd(DataTableEntity<ENTITY> dataTableEntity);
		
		void listenAdd(DataTableEntity<ENTITY> dataTableEntity,ENTITY entity);
		
		void listenRemove(DataTableEntity<ENTITY> dataTableEntity,ENTITY entity);
		
	}
	
	/**/
}
