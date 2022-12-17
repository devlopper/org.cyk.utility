package org.cyk.utility.client.controller.web.jsf.primefaces.model.collection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.faces.component.UIComponent;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.data.structure.grid.Grid;
import org.cyk.utility.__kernel__.enumeration.Action;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.identifier.resource.ParameterName;
import org.cyk.utility.__kernel__.internationalization.InternationalizationHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.klass.NamingModel;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.RuntimeException;
import org.cyk.utility.__kernel__.user.interface_.UserInterfaceAction;
import org.cyk.utility.client.controller.ControllerEntity;
import org.cyk.utility.client.controller.ControllerLayer;
import org.cyk.utility.client.controller.web.jsf.OutcomeGetter;
import org.cyk.utility.client.controller.web.jsf.Redirector;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractAction;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractFilterController;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractObjectAjaxable;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.ajax.Ajax;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.AbstractCommand;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.CommandButton;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.RemoteCommand;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.layout.Layout;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.menu.AbstractMenu;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.menu.ContextMenu;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.menu.MenuButton;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.menu.MenuItem;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.output.OutputText;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.panel.Dialog;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.panel.OutputPanel;
import org.cyk.utility.persistence.query.QueryHelper;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractCollection extends AbstractObjectAjaxable implements Serializable {

	protected OutputPanel contentOutputPanel = OutputPanel.build(OutputPanel.FIELD_DEFERRED,Boolean.FALSE);
	protected RenderType renderType;
	protected Class<?> elementClass;
	protected Object __parentElement__;
	protected OutputText title;
	protected Object value,selectedCommandIdentifier;
	protected String emptyMessage,rowsPerPageTemplate,paginatorTemplate,currentPageReportTemplate,selectionMode,fileName;
	protected String rowStyleClass,editMode,sortMode;
	protected Boolean lazy,paginator,paginatorAlwaysVisible,isExportable,isSelectionShowableInDialog,editable,isSavable;
	protected Integer rows,filterDelay;
	protected Object selection;
	protected List<?> selectionAsCollection;
	protected Map<String,Object> map = new HashMap<>();
	protected OutputPanel dialogOutputPanel;
	protected Dialog dialog;
	protected Layout afterFilterControllerLayout;
	protected Collection<AbstractCommand> headerToolbarLeftCommands,headerToolbarRightCommands;
	protected Collection<AbstractCommand> recordCommands;
	protected AbstractMenu recordMenu;
	protected ControllerEntity<Object> controllerEntity;
	protected CommandButton saveCommandButton;
	protected Map<String,UIComponent> cellsBindings;
	protected AbstractFilterController filterController;
	protected Boolean isFilterControllerGettable=Boolean.TRUE;
	protected String entityIdentifierParameterName;
	protected List<RemoteCommand> remoteCommands;
	
	//protected AbstractFilterController filterController;
	
	/**/
	
	/*@SuppressWarnings("unchecked")
	public <T> List<T> getSelectionAsList(Class<T> clazz) {
		return (List<T>) selection;
	}
	
	public List<?> getSelectionAsList() {
		return (List<?>) selection;
	}*/
	
	public RemoteCommand getRemoteCommandByName(String name) {
		if(CollectionHelper.isEmpty(remoteCommands))
			return null;
		for(RemoteCommand remoteCommand : remoteCommands)
			if(remoteCommand.getName().equals(name))
				return remoteCommand;
		return null;
	}
	
	public RemoteCommand instantiateRemoteCommand(String name,AbstractAction.Listener listener,Collection<org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractObject> updatableComponents,Collection<String> updatableStyleClasses) {
		RemoteCommand remoteCommand = RemoteCommand.build(RemoteCommand.FIELD_NAME,name);
		remoteCommand.setListener(listener);
		if(CollectionHelper.isNotEmpty(updatableComponents) || CollectionHelper.isNotEmpty(updatableStyleClasses)) {
			remoteCommand.setUpdate(null);
			if(CollectionHelper.isNotEmpty(updatableComponents))
				remoteCommand.addUpdatablesUsingStyleClass(updatableComponents);
			if(CollectionHelper.isNotEmpty(updatableStyleClasses))
				remoteCommand.addUpdates(updatableStyleClasses.stream().map(styleClass -> String.format("@(.%s)",styleClass)).collect(Collectors.joining(",")));
		}
		addRemoteCommands(remoteCommand);
		return remoteCommand;
	}
	
	public Collection<RemoteCommand> getRemoteCommands(Boolean instantiateIfNull) {
		if(remoteCommands == null && Boolean.TRUE.equals(instantiateIfNull))
			remoteCommands = new ArrayList<>();
		return remoteCommands;
	}
	
	public AbstractCollection addRemoteCommands(Collection<RemoteCommand> remoteCommands) {
		if(CollectionHelper.isEmpty(remoteCommands))
			return this;
		getRemoteCommands(Boolean.TRUE).addAll(remoteCommands);
		return this;
	}
	
	public AbstractCollection addRemoteCommands(RemoteCommand...remoteCommands) {
		if(ArrayHelper.isEmpty(remoteCommands))
			return this;
		return addRemoteCommands(CollectionHelper.listOf(Boolean.TRUE, remoteCommands));
	}
	
	public RemoteCommand getRemoteCommandAt(Integer index) {
		if(remoteCommands == null || index == null || index >= remoteCommands.size())
			return null;
		return remoteCommands.get(index);
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getList(Class<T> klass) {
		if(klass == null)
			return null;
		if(value instanceof LazyDataModel<?>)
			return ((LazyDataModel<T>)value).getList();
		return (List<T>) value;
	}
	
	public AbstractFilterController getFilterController() {
		if(!Boolean.TRUE.equals(isFilterControllerGettable))
			return null;
		if(filterController != null)
			return filterController;
		if(!(value instanceof LazyDataModel<?>))
			return null;
		LazyDataModel.Listener<?> listener = ((LazyDataModel<?>)value).getListener();
		if(listener == null)
			return null;
		return listener.getFilterController();
	}
	
	public AbstractCollection useQueryIdentifiersFiltersLike() {
		if(value instanceof LazyDataModel<?>) {
			LazyDataModel<?> lazyDataModel = (LazyDataModel<?>) value;
			lazyDataModel.setReadQueryIdentifier(QueryHelper.getIdentifierReadByFiltersLike(elementClass));
			lazyDataModel.setCountQueryIdentifier(QueryHelper.getIdentifierCountByFiltersLike(elementClass));
		}
		return this;
	}
	
	public Object getValueAt(Integer index) {
		if(value == null || index == null || index < 0)
			return null;
		if(value instanceof Collection)
			return CollectionHelper.getElementAt((Collection<?>) value, index);
		if(value instanceof LazyDataModel<?>)
			return CollectionHelper.getElementAt(((LazyDataModel<?>) value).getList(), index);
		throw new RuntimeException("we cannot get value at from value of type "+value.getClass());
	}
	
	public Collection<AbstractCommand> getHeaderToolbarLeftCommands(Boolean injectIfNull) {
		if(headerToolbarLeftCommands == null && Boolean.TRUE.equals(injectIfNull))
			headerToolbarLeftCommands = new ArrayList<>();
		return headerToolbarLeftCommands;
	}
	
	public Collection<AbstractCommand> getHeaderToolbarRightCommands(Boolean injectIfNull) {
		if(headerToolbarRightCommands == null && Boolean.TRUE.equals(injectIfNull))
			headerToolbarRightCommands = new ArrayList<>();
		return headerToolbarRightCommands;
	}
	
	public AbstractCollection addHeaderToolbarLeftCommands(Collection<AbstractCommand> headerToolbarLeftCommands) {
		if(CollectionHelper.isEmpty(headerToolbarLeftCommands))
			return this;
		getHeaderToolbarLeftCommands(Boolean.TRUE).addAll(headerToolbarLeftCommands);
		return this;
	}
	
	public AbstractCollection addHeaderToolbarRightCommands(Collection<AbstractCommand> headerToolbarRightCommands) {
		if(CollectionHelper.isEmpty(headerToolbarRightCommands))
			return this;
		getHeaderToolbarRightCommands(Boolean.TRUE).addAll(headerToolbarRightCommands);
		return this;
	}
	
	public AbstractCollection addHeaderToolbarLeftCommands(AbstractCommand...headerToolbarLeftCommands) {
		if(ArrayHelper.isEmpty(headerToolbarLeftCommands))
			return this;
		return addHeaderToolbarLeftCommands(CollectionHelper.listOf(headerToolbarLeftCommands));
	}
	
	public AbstractCollection addHeaderToolbarRightCommands(AbstractCommand...headerToolbarRightCommands) {
		if(ArrayHelper.isEmpty(headerToolbarRightCommands))
			return this;
		return addHeaderToolbarRightCommands(CollectionHelper.listOf(headerToolbarRightCommands));
	}
	
	public AbstractCollection addHeaderToolbarLeftCommandsByArguments(Collection<Map<Object,Object>> headerToolbarLeftCommandsArguments) {
		if(CollectionHelper.isEmpty(headerToolbarLeftCommandsArguments))
			return this;
		headerToolbarLeftCommandsArguments.forEach(new Consumer<Map<Object,Object>>() {
			@Override
			public void accept(Map<Object, Object> map) {
				map.put(AbstractCommand.FIELD___COLLECTION__, AbstractCollection.this);
			}
		});	
		addHeaderToolbarLeftCommands(headerToolbarLeftCommandsArguments.stream().map(map -> CommandButton.build(map)).collect(Collectors.toList()));
		return this;
	}
	
	public AbstractCollection addHeaderToolbarLeftCommandsByArguments(@SuppressWarnings("unchecked") Map<Object,Object>...headerToolbarLeftCommandsArguments) {
		if(ArrayHelper.isEmpty(headerToolbarLeftCommandsArguments))
			return this;
		return addHeaderToolbarLeftCommandsByArguments(CollectionHelper.listOf(headerToolbarLeftCommandsArguments));
	}
	
	@SuppressWarnings("unchecked")
	public AbstractCollection addHeaderToolbarLeftCommandsByArguments(Object...headerToolbarLeftCommandsArguments) {
		if(ArrayHelper.isEmpty(headerToolbarLeftCommandsArguments))
			return this;
		return addHeaderToolbarLeftCommandsByArguments(MapHelper.instantiate(headerToolbarLeftCommandsArguments));
	}
	
	public AbstractCollection addHeaderToolbarLeftCommandsByArgumentsOpenViewInDialog(String outcome,Object...objects) {
		if(StringHelper.isNotBlank(outcome))
			objects = ArrayUtils.addAll(objects, AbstractCommand.FIELD___OUTCOME__,outcome
					,CommandButton.FIELD_USER_INTERFACE_ACTION,UserInterfaceAction.OPEN_VIEW_IN_DIALOG
					,CommandButton.ConfiguratorImpl.FIELD_COLLECTION_SELECTION_SESSIONABLE,Boolean.TRUE);
		if(__parentElement__ != null) {
			objects = ArrayUtils.addAll(objects, AbstractCommand.FIELD___PARAMETERS__
					,Map.of(__parentElement__.getClass().getSimpleName().toLowerCase(),List.of(StringHelper.get(FieldHelper.readSystemIdentifier(__parentElement__))))
					);
		}
		return addHeaderToolbarLeftCommandsByArguments(objects);
	}
	
	public AbstractCollection addHeaderToolbarLeftCommandsByArgumentsOpenViewInDialog(Action action,Object...objects) {
		/*
		if(action != null)
			objects = ArrayUtils.addAll(objects, AbstractCommand.AbstractConfiguratorImpl.FIELD_OPEN_VIEW_IN_DIALOG_ARGUMENTS_GETTER_PARAMETERS
					,Map.of(ParameterName.ACTION_IDENTIFIER.getValue(),List.of(action.name())));
		return addHeaderToolbarLeftCommandsByArgumentsOpenViewInDialog(OutcomeGetter.getInstance().get(elementClass, action),objects);
		*/
		
		objects = ArrayUtils.addAll(objects, CommandButton.FIELD___ACTION__,action,CommandButton.FIELD___COLLECTION__,this
				,CommandButton.FIELD_USER_INTERFACE_ACTION,UserInterfaceAction.OPEN_VIEW_IN_DIALOG
				,CommandButton.ConfiguratorImpl.FIELD_COLLECTION_SELECTION_SESSIONABLE,Boolean.TRUE);
		if(__parentElement__ != null) {			
			objects = ArrayUtils.addAll(objects, AbstractCommand.FIELD___PARAMETERS__
					,Map.of(__parentElement__.getClass().getSimpleName().toLowerCase(),List.of(StringHelper.get(FieldHelper.readSystemIdentifier(__parentElement__))))
					);
		}
		return addHeaderToolbarLeftCommands(CommandButton.build(objects));
	}
	
	public AbstractCollection addHeaderToolbarLeftCommandsByArgumentsOpenViewInDialogCreate(Object...objects) {
		objects = ArrayUtils.addAll(objects,CommandButton.ConfiguratorImpl.FIELD_COLLECTIONABLE,Boolean.FALSE);
		return addHeaderToolbarLeftCommandsByArgumentsOpenViewInDialog(Action.CREATE,objects);
	}
	
	public AbstractCollection addHeaderToolbarLeftCommandsByArgumentsOpenViewInDialogRead(Object...objects) {
		return addHeaderToolbarLeftCommandsByArgumentsOpenViewInDialog(Action.READ,objects);
	}
	
	public AbstractCollection addHeaderToolbarLeftCommandsByArgumentsOpenViewInDialogUpdate(Object...objects) {
		return addHeaderToolbarLeftCommandsByArgumentsOpenViewInDialog(Action.UPDATE,objects);
	}
	/*
	public AbstractCollection addHeaderToolbarLeftCommandsByArgumentsOpenViewInDialogDelete() {
		return addHeaderToolbarLeftCommandsByArgumentsOpenViewInDialog(Action.DELETE);
	}*/
	
	/**/
	
	public Collection<AbstractCommand> getRecordCommands(Boolean injectIfNull) {
		if(recordCommands == null && Boolean.TRUE.equals(injectIfNull))
			recordCommands = new ArrayList<>();
		return recordCommands;
	}
	
	public AbstractCollection addRecordCommands(Collection<AbstractCommand> recordCommands) {
		if(CollectionHelper.isEmpty(recordCommands))
			return this;
		getRecordCommands(Boolean.TRUE).addAll(recordCommands);
		return this;
	}
	
	public AbstractCollection addRecordCommands(AbstractCommand...recordCommands) {
		if(ArrayHelper.isEmpty(recordCommands))
			return this;
		return addRecordCommands(CollectionHelper.listOf(recordCommands));
	}
	
	public AbstractMenu getRecordMenu(Boolean injectIfNull) {
		if(recordMenu == null && Boolean.TRUE.equals(injectIfNull))			
			recordMenu = ((Listener)(listener == null ? Listener.AbstractImpl.DefaultImpl.INSTANCE : listener)).buildRecordMenu(this);
		return recordMenu;
	}
	
	public AbstractCollection addRecordMenuItems(Collection<MenuItem> menuItems) {
		if(CollectionHelper.isEmpty(menuItems))
			return this;
		getRecordMenu(Boolean.TRUE).addItems(menuItems);
		return this;
	}
	
	public AbstractCollection addRecordMenuItems(MenuItem...menuItems) {
		if(ArrayHelper.isEmpty(menuItems))
			return this;
		return addRecordMenuItems(CollectionHelper.listOf(menuItems));
	}
	
	public AbstractCollection addRecordMenuItemsByArguments(Collection<Map<Object,Object>> menuItemsArguments) {
		if(CollectionHelper.isEmpty(menuItemsArguments))
			return this;
		menuItemsArguments.forEach(new Consumer<Map<Object,Object>>() {
			@Override
			public void accept(Map<Object, Object> map) {
				map.put(MenuItem.FIELD___COLLECTION__, AbstractCollection.this);
			}
		});	
		addRecordMenuItems(menuItemsArguments.stream().map(map -> MenuItem.build(map)).collect(Collectors.toList()));
		return this;
	}
	
	public AbstractCollection addRecordMenuItemsByArguments(@SuppressWarnings("unchecked") Map<Object,Object>...menuItemsArguments) {
		if(ArrayHelper.isEmpty(menuItemsArguments))
			return this;
		return addRecordMenuItemsByArguments(CollectionHelper.listOf(menuItemsArguments));
	}
	
	@SuppressWarnings("unchecked")
	public AbstractCollection addRecordMenuItemByArguments(Object...arguments) {
		if(ArrayHelper.isEmpty(arguments))
			return this;
		return addRecordMenuItemsByArguments(MapHelper.instantiate(arguments));
	}
	
	public AbstractCollection addRecordMenuItemByArgumentsOpenViewInDialog(String outcome,Object...objects) {
		if(StringHelper.isNotBlank(outcome))
			objects = ArrayUtils.addAll(objects, MenuItem.FIELD___OUTCOME__,outcome
					,CommandButton.FIELD_USER_INTERFACE_ACTION,UserInterfaceAction.OPEN_VIEW_IN_DIALOG
					,CommandButton.ConfiguratorImpl.FIELD_COLLECTION_SELECTION_SESSIONABLE,Boolean.FALSE
					,CommandButton.ConfiguratorImpl.FIELD_COLLECTIONABLE,Boolean.FALSE
					);
		return addRecordMenuItemByArguments(objects);
	}
	
	public AbstractCollection addRecordMenuItemByArgumentsOpenViewInDialog(Action action,Object...objects) {
		if(action != null)
			objects = ArrayUtils.addAll(objects, AbstractCommand.FIELD___PARAMETERS__
					,Map.of(ParameterName.ACTION_IDENTIFIER.getValue(),List.of(action.name())));
		return addRecordMenuItemByArgumentsOpenViewInDialog(OutcomeGetter.getInstance().get(elementClass, action),objects);
	}
	
	public AbstractCollection addRecordMenuItemByArgumentsOpenViewInDialogRead() {
		return addRecordMenuItemByArgumentsOpenViewInDialog(Action.READ,AbstractCommand.FIELD_VALUE,"Consulter",AbstractCommand.FIELD_ICON,"fa fa-eye");
	}
	
	public AbstractCollection addRecordMenuItemByArgumentsOpenViewInDialogUpdate() {
		return addRecordMenuItemByArgumentsOpenViewInDialog(Action.UPDATE,AbstractCommand.FIELD_VALUE,"Modifier",AbstractCommand.FIELD_ICON,"fa fa-edit");
	}
	
	public AbstractCollection addRecordMenuItemByArgumentsExecuteFunction(String value,String icon,AbstractAction.Listener listener) {
		return addRecordMenuItemByArguments(MenuItem.FIELD_VALUE,value,MenuItem.FIELD_ICON,icon
				,MenuItem.FIELD_LISTENER,listener,MenuItem.FIELD_USER_INTERFACE_ACTION,UserInterfaceAction.EXECUTE_FUNCTION
				,MenuItem.ConfiguratorImpl.FIELD_CONFIRMABLE,Boolean.TRUE
						,MenuItem.ConfiguratorImpl.FIELD_RUNNER_ARGUMENTS_SUCCESS_MESSAGE_ARGUMENTS_RENDER_TYPES
						,CollectionHelper.listOf(org.cyk.utility.__kernel__.user.interface_.message.RenderType.GROWL));
	}
	
	public AbstractCollection addRecordMenuItemByArgumentsExecuteFunctionDelete() {
		if(elementClass != null) {
			return addRecordMenuItemByArgumentsExecuteFunction("Supprimer", "fa fa-remove", new AbstractAction.Listener.AbstractImpl() {
				@Override
				protected Object __runExecuteFunction__(AbstractAction action) {
					super.__runExecuteFunction__(action);
					if(controllerEntity == null)
						throw new RuntimeException("Controller is required to execute delete function");
					Object argument = action.readArgument();
					if(argument == null)
						throw new RuntimeException("Argument is required to execute delete function");
					controllerEntity.delete(argument);
					return argument.getClass().getSimpleName()+" with system identifier <<"+FieldHelper.readSystemIdentifier(argument)+">>" +" has been deleted.";
				} 
			});
		}
		return this;
	}
	
	/**/
	
	public AbstractCollection addRecordMenuItemByArgumentsNavigateToView(Action action,String outcome,Object...objects) {
		if(StringHelper.isBlank(outcome))
			outcome = OutcomeGetter.getInstance().get(elementClass, action);
		if(StringHelper.isNotBlank(outcome))
			objects = ArrayUtils.addAll(objects, MenuItem.FIELD_OUTCOME,outcome, MenuItem.FIELD___OUTCOME__,outcome
					,MenuItem.FIELD_USER_INTERFACE_ACTION,UserInterfaceAction.NAVIGATE_TO_VIEW
					,MenuItem.FIELD___ACTION__,action);
		if(action != null)
			objects = ArrayUtils.addAll(objects, MenuItem.FIELD_PARAMETERS,Map.of(ParameterName.ACTION_IDENTIFIER.getValue(),List.of(action.name())));
		Class<? extends AbstractMenu> recordMenuClass = recordMenu == null ? ((Listener)listener).getRecordMenuClass(this) : recordMenu.getClass();
		if(ContextMenu.class.equals(recordMenuClass)) {
			//TODO is it the best way ?
			String vOutcome = outcome;
			final Map<Object,Object> map = MapHelper.instantiate(objects);
			final Action actionFinal = action;
			addRecordMenuItemByArgumentsExecuteFunction((String)map.get(MenuItem.FIELD_VALUE), (String)map.get(MenuItem.FIELD_ICON), new MenuItem.Listener.AbstractImpl() {
				@SuppressWarnings("unchecked")
				@Override
				protected Object __runExecuteFunction__(AbstractAction action) {
					if(action == null)
						return null;
					Object argument = action.readArgument();
					if(argument == null)
						return null;
					Map<String,List<String>> parameters = new HashMap<>();
					if(actionFinal != null)
						parameters.put(ParameterName.ACTION_IDENTIFIER.getValue(),List.of(actionFinal.name()));
					String argumentSystemIdentifierFieldName = (String) map.get(MenuItem.ConfiguratorImpl.FIELD_ARGUMENT_SYSTEM_IDENTIFIER_FIELD_NAME);
					Object identifier = StringHelper.isBlank(argumentSystemIdentifierFieldName) ? FieldHelper.readSystemIdentifier(argument): FieldHelper.read(argument, argumentSystemIdentifierFieldName);
					String identifierAsString = identifier == null ? null : identifier.toString();
					if(StringHelper.isBlank(identifierAsString))
						throw new java.lang.RuntimeException("Argument identifier is required to navigate to "+vOutcome);
					parameters.put(StringHelper.isBlank(entityIdentifierParameterName) ? ParameterName.ENTITY_IDENTIFIER.getValue() : entityIdentifierParameterName,List.of(identifierAsString));
					/*if(MapHelper.isEmpty(action.get__parameters__())) {
						if(map.get(MenuItem.FIELD___PARAMETERS__) != null)
							parameters.putAll((Map<? extends String, ? extends List<String>>) map.get(MenuItem.FIELD___PARAMETERS__));
					}else
						parameters.putAll(action.get__parameters__());
					*/
					if(map.get(MenuItem.FIELD_PARAMETERS) != null)
						parameters.putAll((Map<? extends String, ? extends List<String>>) map.get(MenuItem.FIELD_PARAMETERS));
					
					if(MapHelper.isNotEmpty(action.get__parameters__()))
						parameters.putAll(action.get__parameters__());
					Redirector.getInstance().redirect(vOutcome, parameters);			
					return null;
				}
			});
			CollectionHelper.getLast(recordMenu.getItems()).setConfirm(null);
			return this;
		}
		return addRecordMenuItemByArguments(objects);
	}
	
	public AbstractCollection addRecordMenuItemByArgumentsNavigateToViewRead(Object...objects) {
		return addRecordMenuItemByArgumentsNavigateToView(Action.READ,ConstantEmpty.STRING,objects);
	}
	
	public AbstractCollection enableCommandButtonSave() {
		setIsSavable(Boolean.TRUE);
		saveCommandButton = CommandButton.build(CommandButton.FIELD_VALUE,"Enregistrer",CommandButton.FIELD_ICON,"fa fa-floppy-o",CommandButton.FIELD_USER_INTERFACE_ACTION,UserInterfaceAction.EXECUTE_FUNCTION
				,CommandButton.FIELD_LISTENER,new CommandButton.Listener.AbstractImpl() {
			@Override
			protected Object __runExecuteFunction__(AbstractAction action) {
				if(listener == null)
					return null;
				else
					return ((Listener)listener).listenSave(AbstractCollection.this);
			}
		});
		return this;
	}
	
	public Boolean hasRecordMenuItems() {
		return recordMenu != null && CollectionHelper.isNotEmpty(recordMenu.getItems());
	}
	
	/**/
	
	public static final String FIELD_ELEMENT_CLASS = "elementClass";
	public static final String FIELD___PARENT_ELEMENT__ = "__parentElement__";
	public static final String FIELD_TITLE = "title";
	public static final String FIELD_VALUE = "value";
	public static final String FIELD_EMPTY_MESSAGE = "emptyMessage";
	public static final String FIELD_ROWS_PER_PAGE_TEMPLATE = "rowsPerPageTemplate";
	public static final String FIELD_ROWS = "rows";
	public static final String FIELD_ROW_STYLE_CLASS = "rowStyleClass";
	public static final String FIELD_FILTER_DELAY = "filterDelay";
	public static final String FIELD_LAZY = "lazy";
	public static final String FIELD_SELECTION_MODE = "selectionMode";
	public static final String FIELD_EDITABLE = "editable";
	public static final String FIELD_EDIT_MODE = "editMode";
	public static final String FIELD_RENDER_TYPE = "renderType";
	public static final String FIELD_SELECTION = "selection";
	public static final String FIELD_SORT_MODE = "sortMode";
	public static final String FIELD_SELECTION_AS_COLLECTION = "selectionAsCollection";
	//public static final String FIELD_FILTER_CONTROLLER = "filterController";
	
	/**/
	
	public static abstract class AbstractConfiguratorImpl<COLLECTION extends AbstractCollection> extends AbstractObjectAjaxable.AbstractConfiguratorImpl<COLLECTION> implements Serializable {

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public void configure(COLLECTION collection, Map<Object, Object> arguments) {
			super.configure(collection, arguments);
			setRenderType(collection,arguments);
			
			if(StringHelper.isBlank(collection.sortMode))
				collection.sortMode = "single";
			
			Boolean controllerEntityBuildable = (Boolean) MapHelper.readByKey(arguments, FIELD_CONTROLLER_ENTITY_BUILDABLE);
			if(controllerEntityBuildable == null)
				controllerEntityBuildable = Boolean.TRUE;
			if(collection.controllerEntity == null && Boolean.TRUE.equals(controllerEntityBuildable)) {
				if(collection.elementClass != null && !Grid.Row.class.equals(collection.elementClass))
					collection.controllerEntity = (ControllerEntity<Object>) __inject__(ControllerLayer.class).injectInterfaceClassFromEntityClass(collection.elementClass);
			}
			Boolean filterable = (Boolean) MapHelper.readByKey(arguments, FIELD_FILTERABLE);
			if(filterable == null)
				filterable = collection.lazy;
			//if(collection.rows == null)
			//	collection.rows = 0;
			if(collection.filterDelay == null)
				collection.filterDelay = 2000;
			if(StringHelper.isEmpty(collection.emptyMessage))
				collection.emptyMessage = "-- Aucun élément trouvé --";
			
			if(Boolean.TRUE.equals(collection.lazy)) {
				//FIXME context menu right click not working well when when deferred is true : might be prime faces bug. For now we will use false
				//collection.contentOutputPanel.setDeferred(Boolean.TRUE);
				if(collection.rows == null)
					collection.rows = 20;
				if(StringHelper.isBlank(collection.rowsPerPageTemplate))
					collection.rowsPerPageTemplate = "20,50,100,500,1000,2000,5000,10000,20000,50000";
				if(StringHelper.isBlank(collection.paginatorTemplate))
					collection.paginatorTemplate = "{CurrentPageReport} {FirstPageLink} {PreviousPageLink} {PageLinks} {NextPageLink} {LastPageLink} {RowsPerPageDropdown}";
				if(StringHelper.isBlank(collection.currentPageReportTemplate))
					collection.currentPageReportTemplate = "Total {totalRecords} | Page {currentPage}/{totalPages}";
				if(collection.paginator == null)
					collection.paginator = Boolean.TRUE;
				if(collection.elementClass == null) {
					
				}else {
					if(MapHelper.readByKey(arguments, FIELD_LAZY_DATA_MODEL) == null) {
						LazyDataModel<Object> value = (LazyDataModel<Object>) MapHelper.readByKey(arguments, FIELD_LAZY_DATA_MODEL);
						if(value == null) {
							/*Class<?> lazyzDataModelClass = (Class<?>) MapHelper.readByKey(arguments, FIELD_LAZY_DATA_MODEL_CLASS);
							if(lazyzDataModelClass == null)
								lazyzDataModelClass = LazyDataModel.class;
							*/
							value = new LazyDataModel<Object>((Class<Object>) collection.elementClass);	
						}	
						
						String persistenceEntityClassName = ClassHelper.buildName(collection.elementClass.getPackageName(), collection.elementClass.getSimpleName()
								, new NamingModel().client().controller().entities(), new NamingModel().server().persistence().entities());						
						Class<?> persistenceEntityClass = ClassHelper.getByName(persistenceEntityClassName);
						if(Boolean.TRUE.equals(filterable)) {											
							if(StringHelper.isBlank(value.getReadQueryIdentifier()))
								value.setReadQueryIdentifier(QueryHelper.getIdentifierReadByFiltersLike(persistenceEntityClass));
							if(StringHelper.isBlank(value.getCountQueryIdentifier()))
								value.setCountQueryIdentifier(QueryHelper.getIdentifierCountByFiltersLike(persistenceEntityClass));																	
						}else {
							if(StringHelper.isBlank(value.getReadQueryIdentifier()))
								value.setReadQueryIdentifier(QueryHelper.getIdentifierReadAll(persistenceEntityClass));
							if(StringHelper.isBlank(value.getCountQueryIdentifier()))
								value.setCountQueryIdentifier(QueryHelper.getIdentifierCountAll(persistenceEntityClass));												
						}
						if(value.getEntityFieldsNames() == null)
							value.setEntityFieldsNames((Collection<String>) MapHelper.readByKey(arguments, FIELD_ENTITY_FIELDS_NAMES));	
						collection.value = value;	
					}else
						collection.value = MapHelper.readByKey(arguments, FIELD_LAZY_DATA_MODEL);
				}
			}else {
				
			}
			
			if(collection.value instanceof LazyDataModel<?>) {
				if(((LazyDataModel<?>)collection.value).getListener() == null)
					((LazyDataModel<?>)collection.value).setListener((LazyDataModel.Listener) MapHelper.readByKey(arguments, FIELD_LAZY_DATA_MODEL_LISTENER));
			}
			
			if(StringHelper.isBlank(collection.selectionMode)) {
				
			}else {
				collection.selection = new ArrayList<>();				
			}
			
			collection.dialogOutputPanel = Builder.build(OutputPanel.class);
			collection.dialog = Builder.build(Dialog.class,Map.of(Dialog.FIELD_STYLE_CLASS,"cyk-min-width-90-percent cyk-min-height-90-percent",Dialog.FIELD_MODAL,Boolean.TRUE));
			
			/* Listeners */
			
			collection.addAjaxes(Map.of(Ajax.FIELD_EVENT,"page",Ajax.FIELD_DISABLED,Boolean.FALSE,Ajax.FIELD___RUNNABLE__,Boolean.FALSE)
					,Map.of(Ajax.FIELD_EVENT,"filter",Ajax.FIELD_DISABLED,Boolean.FALSE,Ajax.FIELD___RUNNABLE__,Boolean.FALSE)
					,Map.of(Ajax.FIELD_EVENT,"sort",Ajax.FIELD_DISABLED,Boolean.FALSE,Ajax.FIELD___RUNNABLE__,Boolean.FALSE)
					,Map.of(Ajax.FIELD_EVENT,"rowSelect",Ajax.FIELD___RUNNABLE__,Boolean.FALSE)
					,Map.of(Ajax.FIELD_EVENT,"rowUnselect",Ajax.FIELD___RUNNABLE__,Boolean.FALSE)
					,Map.of(Ajax.FIELD_EVENT,"rowSelectCheckbox",Ajax.FIELD___RUNNABLE__,Boolean.FALSE)
					,Map.of(Ajax.FIELD_EVENT,"rowUnselectCheckbox",Ajax.FIELD___RUNNABLE__,Boolean.FALSE)
					,Map.of(Ajax.FIELD_EVENT,"cellEdit",Ajax.FIELD___RUNNABLE__,Boolean.FALSE)
					);		
			
			/*
			Class<?> recordMenuClass = (Class<?>) MapHelper.readByKey(arguments, FIELD_RECORD_MENU_CLASS);
			if(recordMenuClass == null)
				recordMenuClass = MenuButton.class;
			if(MenuButton.class.equals(recordMenuClass))
				collection.recordMenu = MenuButton.build();
			else if(ContextMenu.class.equals(recordMenuClass))
				collection.recordMenu = ContextMenu.build();
			*/
			if(!Boolean.TRUE.equals(MapHelper.readByKey(arguments, FIELD_RECORD_MENU_NULLABLE))) {
				Collection<Map<Object,Object>> recordMenuItemsByArguments = (Collection<Map<Object, Object>>) MapHelper.readByKey(arguments, FIELD_RECORD_MENU_ITEMS_BY_ARGUMENTS);
				if(CollectionHelper.isNotEmpty(recordMenuItemsByArguments)) {
					for(Map<Object,Object> map : recordMenuItemsByArguments)
						MapHelper.writeByKey(map, MenuItem.ConfiguratorImpl.FIELD_UPDATABLES, CollectionHelper.listOf(collection), Boolean.FALSE);
				}
				if(CollectionHelper.isNotEmpty(recordMenuItemsByArguments))
					collection.getRecordMenu(Boolean.TRUE).addItemsByArguments(recordMenuItemsByArguments);
			}		
			if(collection.title == null) {
				if(StringHelper.isNotBlank((String) MapHelper.readByKey(arguments, FIELD_TITLE_VALUE)))
					collection.title = OutputText.build(OutputText.FIELD_VALUE,MapHelper.readByKey(arguments, FIELD_TITLE_VALUE));
				if(collection.title == null && collection.elementClass!=null)
					collection.title = OutputText.build(OutputText.FIELD_VALUE,InternationalizationHelper.buildPhraseFromKeysValues("list","of"
							,InternationalizationHelper.buildKey(collection.elementClass).getValue()),OutputText.FIELD_RENDERED,Boolean.FALSE);
			}
			
			if(collection.editable == null)
				collection.editable = Boolean.FALSE;
			
			if(StringHelper.isBlank(collection.editMode))
				collection.editMode = "cell";
			
			if(Boolean.TRUE.equals(collection.editable)) {
				collection.isExportable = Boolean.FALSE;
				collection.isSavable = Boolean.TRUE;
			}
		}
		
		protected void setRenderType(COLLECTION collection, Map<Object, Object> arguments) {
			if(collection.renderType == null)
				collection.renderType = RenderType.OUTPUT;
		}
		
		public static final String FIELD_ENTITY_FIELDS_NAMES = "entityFieldsNames";
		public static final String FIELD_FILTERABLE = "filterable";
		public static final String FIELD_EDITABLE_CELL = "editableCell";
		public static final String FIELD_RECORD_MENU_ITEMS_BY_ARGUMENTS = "recordMenuItemsByArguments";
		public static final String FIELD_LAZY_DATA_MODEL_CLASS = "lazyDataModelClass";
		public static final String FIELD_LAZY_DATA_MODEL = "lazyDataModel";
		public static final String FIELD_LAZY_DATA_MODEL_LISTENER = "lazyDataModelListener";
		public static final String FIELD_TITLE_VALUE = "titleValue";
		public static final String FIELD_RECORD_MENU_CLASS = "recordMenuClass";
		public static final String FIELD_RECORD_ACTIONS = "recordActions";
		public static final String FIELD_ACTIONS = "actions";
		public static final String FIELD_RECORD_MENU_NULLABLE = "recordMenuNullable";
		public static final String FIELD_CONTROLLER_ENTITY_BUILDABLE = "controllerEntityBuildable";
	}

	/**/
	
	public static interface Listener {
		Class<? extends AbstractMenu> getRecordMenuClass(AbstractCollection collection);
		AbstractMenu buildRecordMenu(AbstractCollection collection);
		
		Object listenSave(AbstractCollection collection);
		
		/**/
		public static abstract class AbstractImpl extends AbstractObject implements Listener,Serializable {
			
			@Override
			public Object listenSave(AbstractCollection collection) {
				return null;
			}
			
			@Override
			public Class<? extends AbstractMenu> getRecordMenuClass(AbstractCollection collection) {
				return MenuButton.class;
			}
			
			@Override
			public AbstractMenu buildRecordMenu(AbstractCollection collection) {
				Class<? extends AbstractMenu> clazz = getRecordMenuClass(collection);
				if(clazz == null)
					clazz = ContextMenu.class;
				if(MenuButton.class.equals(clazz))
					return MenuButton.build();
				if(ContextMenu.class.equals(clazz))
					return ContextMenu.build(ContextMenu.FIELD_FOR_,collection.identifier);
				throw new java.lang.RuntimeException("Build record menu using class <<"+clazz+">> not yet handled");
			}
			
			public static class DefaultImpl extends AbstractImpl implements Serializable {
				public static final DefaultImpl INSTANCE = new DefaultImpl();
				
			}
		}
	}
	
	/**/
	
	public static enum RenderType {
		OUTPUT,OUTPUT_UNSELECTABLE,INPUT,SELECTION
	}
}