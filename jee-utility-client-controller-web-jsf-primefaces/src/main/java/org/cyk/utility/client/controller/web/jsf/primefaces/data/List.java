package org.cyk.utility.client.controller.web.jsf.primefaces.data;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.enumeration.Action;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.DataTable;
import org.primefaces.PrimeFaces;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class List extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private Class<?> entityClass;
	private DataTable dataTable;
	private Form editionForm;
	/**/
	
	public static final String FIELD_ENTITY_CLASS = "entityClass";
	public static final String FIELD_DATA_TABLE = "dataTable";
	public static final String FIELD_EDITION_FORM = "editionForm";
	
	/**/
	
	public static class ConfiguratorImpl extends Configurator.AbstractImpl<List> implements Serializable {

		@Override
		public void configure(List list, Map<Object, Object> arguments) {
			super.configure(list, arguments);
			if(list.entityClass != null) {
				list.dataTable = DataTable.build(DataTable.FIELD_ELEMENT_CLASS,list.entityClass,DataTable.FIELD_LAZY,Boolean.TRUE);
				
				list.editionForm = Form.build(Form.FIELD_ENTITY_CLASS,list.entityClass,Form.FIELD_ACTION,Action.CREATE,Form.FIELD_CONTAINER,list.dataTable.getDialog(),Form.FIELD_LISTENER
						,new Form.Listener.AbstractImpl() {				
							@Override
							public void listenAfterExecute(Form form) {
								PrimeFaces.current().ajax().update(":form:"+list.dataTable.getIdentifier());
							}
						});
				
				list.dataTable.addHeaderToolbarLeftCommandsByArgumentsOpenViewInDialogCreate();
				//list.dataTable.addRecordMenuItemByArgumentsOpenViewInDialogRead();
				list.dataTable.addRecordMenuItemByArgumentsOpenViewInDialogUpdate();
				list.dataTable.addRecordMenuItemByArgumentsExecuteFunctionDelete();
				
				/*
				list.dataTable.addHeaderToolbarLeftCommands(
						CommandButton.build(CommandButton.FIELD_VALUE,"Créer",CommandButton.FIELD_ICON,"fa fa-plus",CommandButton.ConfiguratorImpl.FIELD_COLLECTION,list.dataTable
								,CommandButton.FIELD_LISTENER,new AbstractAction.Listener.AbstractImpl() {
							protected void __showDialog__(Object argument) {
								super.__showDialog__(argument);
								list.editionForm.setAction(org.cyk.utility.__kernel__.enumeration.Action.CREATE);
								list.dataTable.getDialog().setHeader(org.cyk.utility.__kernel__.enumeration.Action.CREATE+" : "+list.editionForm.getTitle());
							};
						})
					);
				
				list.dataTable.addRecordMenuItemByArguments(MenuItem.FIELD_VALUE,"Modifier",MenuItem.FIELD_ICON,"fa fa-pencil",CommandButton.ConfiguratorImpl.FIELD_COLLECTION,list.dataTable
						,MenuItem.FIELD_LISTENER,new AbstractAction.Listener.AbstractImpl() {
							protected void __showDialog__(Object argument) {
								super.__showDialog__(argument);
								list.editionForm.setAction(org.cyk.utility.__kernel__.enumeration.Action.UPDATE);
								if(CollectionHelper.isNotEmpty(list.editionForm.getUpdatableEntityFieldsNames()))
									InstanceCopier.getInstance().copy(argument, list.editionForm.getEntity(),list.editionForm.getUpdatableEntityFieldsNames());
								for(Cell cell : list.editionForm.getLayout().getCells())
									if(cell.getControl() instanceof AbstractInput<?>)
										((AbstractInput<?>)cell.getControl()).readValueFromField();
				
								list.dataTable.getDialog().setHeader(org.cyk.utility.__kernel__.enumeration.Action.UPDATE+" : "+list.editionForm.getTitle()+" : "+argument);
							};
						});
				
				ControllerEntity<Object> controllerEntity = (ControllerEntity<Object>) list.editionForm.getControllerEntity();
				list.dataTable.addRecordMenuItemByArguments(MenuItem.FIELD_VALUE,"Supprimer",MenuItem.FIELD_ICON,"fa fa-remove"
						,MenuItem.FIELD_LISTENER,new AbstractAction.Listener.AbstractImpl() {			
					@Override public void listenAction(Object argument) {controllerEntity.delete(argument);}
				},MenuItem.ConfiguratorImpl.FIELD_CONFIRMABLE,Boolean.TRUE
								,MenuItem.ConfiguratorImpl.FIELD_RUNNER_ARGUMENTS_SUCCESS_MESSAGE_ARGUMENTS_RENDER_TYPES,CollectionHelper.listOf(RenderType.GROWL));
				*/
			}
		}
		
		@Override
		protected Class<List> __getClass__() {
			return List.class;
		}
		
		/**/
		
		public static final String FIELD_REQUEST = "request";
		
	}
	
	public static List build(Map<Object,Object> arguments) {
		return Builder.build(List.class,arguments);
	}
	
	public static List build(Object...objects) {
		return build(MapHelper.instantiate(objects));
	}
	
	static {
		Configurator.set(List.class, new ConfiguratorImpl());
	}
	
	/**/
	
	public static interface Listener {
		
		/**/
		
		public static abstract class AbstractImpl extends AbstractObject implements Listener,Serializable {
			
		}
	}
}
