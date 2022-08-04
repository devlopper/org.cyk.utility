package org.cyk.utility.client.controller.web.jsf.primefaces.model.collection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.system.action.SystemActionCustom;
import org.cyk.utility.client.controller.component.command.Commandable;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.primefaces.PrimeFaces;

import lombok.Getter;
import lombok.Setter;

@Deprecated
public class DataTableEntity<ENTITY> extends DataTable implements Serializable {

	@Getter @Setter private Class<ENTITY> entityClass;
	@Getter @Setter private Collection<ENTITY> value;
	//@Getter @Setter private Listener<ENTITY> listener;
	@Getter @Setter private String dialogOutputPanelIdentifier = "dialogOutputPanel"+getIdentifier();	
	//@Getter @Setter private Dialog dialog = new Dialog();
	@Getter @Setter private ActionMode addMode = ActionMode.INLINE;
	@Getter @Setter private Commandable addCommandable;
	/**/
	
	public DataTableEntity() {
		CommandableBuilder commandableBuilder = __inject__(CommandableBuilder.class);
		commandableBuilder.setName("Ajouter").setCommandFunctionActionClass(SystemActionCustom.class).addCommandFunctionTryRunRunnable(
			new Runnable() {
				@Override
				public void run() {
					if(ActionMode.INLINE.equals(addMode)) {
						add();
					}else {
						openDialogForAdd();
					}
				}
			}
		);
		addCommandable = commandableBuilder.execute().getOutput();
	}
	
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
	
	public static enum ActionMode {
		INLINE,
		DIALOG
	}
}
