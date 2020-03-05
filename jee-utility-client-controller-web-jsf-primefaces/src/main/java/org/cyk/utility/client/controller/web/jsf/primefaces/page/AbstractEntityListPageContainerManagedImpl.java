package org.cyk.utility.client.controller.web.jsf.primefaces.page;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.enumeration.Action;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.identifier.resource.PathAsFunctionParameter;
import org.cyk.utility.__kernel__.identifier.resource.QueryAsFunctionParameter;
import org.cyk.utility.__kernel__.identifier.resource.UniformResourceIdentifierAsFunctionParameter;
import org.cyk.utility.__kernel__.identifier.resource.UniformResourceIdentifierHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.user.interface_.message.RenderType;
import org.cyk.utility.client.controller.ControllerEntity;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.data.Form;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractAction;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.DataTable;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.CommandButton;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.menu.MenuItem;
import org.primefaces.PrimeFaces;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractEntityListPageContainerManagedImpl<ENTITY> extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	protected DataTable dataTable;
	protected Form editionForm;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		@SuppressWarnings("unchecked")
		Class<ENTITY> entityClass = (Class<ENTITY>) ClassHelper.getParameterAt(getClass(), 0);
		
		dataTable = DataTable.build(DataTable.ConfiguratorImpl.FIELD_ENTIY_CLASS,entityClass,DataTable.FIELD_LAZY,Boolean.TRUE);
		
		dataTable.addRecordMenuItemByArguments(MenuItem.FIELD_VALUE,"Modifier",MenuItem.FIELD_ICON,"fa fa-pencil",MenuItem.FIELD_LISTENER,new AbstractAction.Listener.AbstractImpl() {			
			@Override public void listenAction(Object argument) {
				UniformResourceIdentifierAsFunctionParameter p = new UniformResourceIdentifierAsFunctionParameter();
				p.setRequest(__getRequest__());
				p.setPath(new PathAsFunctionParameter());
				p.getPath().setIdentifier("namableEditView");
				p.setQuery(new QueryAsFunctionParameter());
				p.getQuery().setValue("entityidentifier="+FieldHelper.readSystemIdentifier(argument)+"&actionidentifier=update");
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect(UniformResourceIdentifierHelper.build(p));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		},MenuItem.ConfiguratorImpl.FIELD_RUNNER_ARGUMENTS_SUCCESS_MESSAGE_ARGUMENTS_NULLABLE,Boolean.TRUE);
		
		
		/*
		dataTable.addColumnsAfterRowIndex(
				Column.build(Map.of(Column.FIELD_FIELD_NAME,Namable.FIELD_CODE,Column.FIELD_WIDTH,"200",Column.ConfiguratorImpl.FIELD_FILTERABLE,Boolean.TRUE))
				,Column.build(Map.of(Column.FIELD_FIELD_NAME,Namable.FIELD_NAME,Column.ConfiguratorImpl.FIELD_FILTERABLE,Boolean.TRUE))
				);
		*/
		
		
		/*
		dataTable.getDialog().getExecuteCommandButton().setListener(new AbstractAction.Listener.AbstractImpl() {
					@Override
					protected void __executeFunction__() {
						super.__executeFunction__();
						
						creationLayout.writeInputsValuesToObjectsFields();
						__inject__(NamableController.class).create(namable);
						
						Ajax.oncomplete("PF('"+dataTable.getDialog().getWidgetVar()+"').hide();");
						PrimeFaces.current().ajax().update(":form:"+dataTable.getIdentifier());
					}
				}.setAction(AbstractAction.Listener.Action.EXECUTE_FUNCTION));
		*/
		//dataTable.getDialog().getExecuteCommandButton().getRunnerArguments().getSuccessMessageArguments().setRenderTypes(List.of(RenderType.GROWL,RenderType.INLINE));
		
		editionForm = Form.build(Form.FIELD_ENTITY_CLASS,entityClass,Form.FIELD_ACTION,Action.CREATE,Form.FIELD_CONTAINER,dataTable.getDialog(),Form.FIELD_LISTENER
				,new Form.Listener.AbstractImpl() {				
					@Override
					public void listenExecute(Form form) {
						PrimeFaces.current().ajax().update(":form:"+dataTable.getIdentifier());
					}
				});
		
		dataTable.addHeaderToolbarLeftCommands(
				CommandButton.build(CommandButton.FIELD_VALUE,"Créer",CommandButton.FIELD_ICON,"fa fa-plus",CommandButton.ConfiguratorImpl.FIELD_COLLECTION,dataTable
						,CommandButton.FIELD_LISTENER,new AbstractAction.Listener.AbstractImpl() {
					protected void __showDialog__() {
						super.__showDialog__();
						dataTable.getDialog().setHeader(editionForm.getTitle());
						/*
						namable.setIdentifier(null);
						namable.setCode(null);
						namable.setName(null);
						code.setValue(null);
						name.setValue(null);
						*/
					};
				}.setAction(AbstractAction.Listener.Action.SHOW_DIALOG).setDialog(dataTable.getDialog()).setDialogOutputPanel(dataTable.getDialogOutputPanel()))
			);
		
		@SuppressWarnings("unchecked")
		ControllerEntity<ENTITY> controllerEntity = (ControllerEntity<ENTITY>) editionForm.getControllerEntity();
		dataTable.addRecordMenuItemByArguments(MenuItem.FIELD_VALUE,"Supprimer",MenuItem.FIELD_ICON,"fa fa-remove",MenuItem.FIELD_LISTENER,new AbstractAction.Listener.AbstractImpl() {			
			@SuppressWarnings("unchecked")
			@Override public void listenAction(Object argument) {controllerEntity.delete((ENTITY) argument);}
		},MenuItem.ConfiguratorImpl.FIELD_CONFIRMABLE,Boolean.TRUE
						,MenuItem.ConfiguratorImpl.FIELD_RUNNER_ARGUMENTS_SUCCESS_MESSAGE_ARGUMENTS_RENDER_TYPES,CollectionHelper.listOf(RenderType.GROWL));
	}
	
}
