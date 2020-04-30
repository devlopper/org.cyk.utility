package org.cyk.utility.client.controller.web.jsf.primefaces.model.collection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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
import org.cyk.utility.__kernel__.persistence.query.QueryHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.RuntimeException;
import org.cyk.utility.__kernel__.user.interface_.message.RenderType;
import org.cyk.utility.client.controller.ControllerEntity;
import org.cyk.utility.client.controller.ControllerLayer;
import org.cyk.utility.client.controller.web.jsf.OutcomeGetter;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractAction;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractObjectAjaxable;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.ajax.Ajax;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.AbstractCommand;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.CommandButton;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.menu.AbstractMenu;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.menu.ContextMenu;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.menu.MenuButton;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.menu.MenuItem;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.output.OutputText;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.panel.Dialog;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.panel.OutputPanel;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractCollection extends AbstractObjectAjaxable implements Serializable {

	protected Class<?> elementClass;
	protected Object __parentElement__;
	protected OutputText title;
	protected Object value,selectedCommandIdentifier;
	protected String emptyMessage,rowsPerPageTemplate,paginatorTemplate,currentPageReportTemplate,selectionMode,fileName;
	protected String rowStyleClass,editMode;
	protected Boolean lazy,paginator,paginatorAlwaysVisible,isExportable,isSelectionShowableInDialog,editable,isSavable;
	protected Integer rows,filterDelay;
	protected List<?> selection;
	protected Map<String,Object> map = new HashMap<>();
	protected OutputPanel dialogOutputPanel;
	protected Dialog dialog;
	protected Collection<AbstractCommand> headerToolbarLeftCommands;
	protected Collection<AbstractCommand> recordCommands;
	protected AbstractMenu recordMenu;
	protected ControllerEntity<Object> controllerEntity;
	protected CommandButton saveCommandButton;
	
	/**/
	
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
	
	public AbstractCollection addHeaderToolbarLeftCommands(Collection<AbstractCommand> headerToolbarLeftCommands) {
		if(CollectionHelper.isEmpty(headerToolbarLeftCommands))
			return this;
		getHeaderToolbarLeftCommands(Boolean.TRUE).addAll(headerToolbarLeftCommands);
		return this;
	}
	
	public AbstractCollection addHeaderToolbarLeftCommands(AbstractCommand...headerToolbarLeftCommands) {
		if(ArrayHelper.isEmpty(headerToolbarLeftCommands))
			return this;
		return addHeaderToolbarLeftCommands(CollectionHelper.listOf(headerToolbarLeftCommands));
	}
	
	public AbstractCollection addHeaderToolbarLeftCommandsByArguments(Collection<Map<Object,Object>> headerToolbarLeftCommandsArguments) {
		if(CollectionHelper.isEmpty(headerToolbarLeftCommandsArguments))
			return this;
		headerToolbarLeftCommandsArguments.forEach(new Consumer<Map<Object,Object>>() {
			@Override
			public void accept(Map<Object, Object> map) {
				map.put(AbstractCommand.AbstractConfiguratorImpl.FIELD_COLLECTION, AbstractCollection.this);
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
			objects = ArrayUtils.addAll(objects, AbstractCommand.AbstractConfiguratorImpl.FIELD_OPEN_VIEW_IN_DIALOG_ARGUMENTS_GETTER_OUTCOME,outcome
					,CommandButton.ConfiguratorImpl.FIELD_LISTENER_ACTION,AbstractAction.Listener.Action.OPEN_VIEW_IN_DIALOG
					,CommandButton.ConfiguratorImpl.FIELD_COLLECTION_SELECTION_SESSIONABLE,Boolean.TRUE);
		if(__parentElement__ != null) {
			objects = ArrayUtils.addAll(objects, AbstractCommand.AbstractConfiguratorImpl.FIELD_OPEN_VIEW_IN_DIALOG_ARGUMENTS_GETTER_PARAMETERS
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
		
		objects = ArrayUtils.addAll(objects, CommandButton.ConfiguratorImpl.FIELD_ACTION,action,CommandButton.ConfiguratorImpl.FIELD_COLLECTION,this
				,CommandButton.ConfiguratorImpl.FIELD_LISTENER_ACTION,AbstractAction.Listener.Action.OPEN_VIEW_IN_DIALOG
				,CommandButton.ConfiguratorImpl.FIELD_COLLECTION_SELECTION_SESSIONABLE,Boolean.TRUE);
		if(__parentElement__ != null) {			
			objects = ArrayUtils.addAll(objects, AbstractCommand.AbstractConfiguratorImpl.FIELD_OPEN_VIEW_IN_DIALOG_ARGUMENTS_GETTER_PARAMETERS
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
			recordMenu = MenuButton.build();
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
				map.put(MenuItem.ConfiguratorImpl.FIELD_COLLECTION, AbstractCollection.this);
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
			objects = ArrayUtils.addAll(objects, MenuItem.ConfiguratorImpl.FIELD_OPEN_VIEW_IN_DIALOG_ARGUMENTS_GETTER_OUTCOME,outcome
					,CommandButton.ConfiguratorImpl.FIELD_LISTENER_ACTION,AbstractAction.Listener.Action.OPEN_VIEW_IN_DIALOG
					,CommandButton.ConfiguratorImpl.FIELD_COLLECTION_SELECTION_SESSIONABLE,Boolean.FALSE
					,CommandButton.ConfiguratorImpl.FIELD_COLLECTIONABLE,Boolean.FALSE
					);
		return addRecordMenuItemByArguments(objects);
	}
	
	public AbstractCollection addRecordMenuItemByArgumentsOpenViewInDialog(Action action,Object...objects) {
		if(action != null)
			objects = ArrayUtils.addAll(objects, AbstractCommand.AbstractConfiguratorImpl.FIELD_OPEN_VIEW_IN_DIALOG_ARGUMENTS_GETTER_PARAMETERS
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
				,MenuItem.FIELD_LISTENER,listener.setAction(AbstractAction.Listener.Action.EXECUTE_FUNCTION)
				,MenuItem.ConfiguratorImpl.FIELD_CONFIRMABLE,Boolean.TRUE
						,MenuItem.ConfiguratorImpl.FIELD_RUNNER_ARGUMENTS_SUCCESS_MESSAGE_ARGUMENTS_RENDER_TYPES,CollectionHelper.listOf(RenderType.GROWL));
	}
	
	public AbstractCollection addRecordMenuItemByArgumentsExecuteFunctionDelete() {
		if(elementClass != null) {
			return addRecordMenuItemByArgumentsExecuteFunction("Supprimer", "fa fa-remove", new AbstractAction.Listener.AbstractImpl() {
				@Override
				protected Object __executeFunction__(Object argument) {
					super.__executeFunction__(argument);
					if(controllerEntity == null)
						throw new RuntimeException("Controller is required to execute delete function");
					controllerEntity.delete(argument);
					return argument+" has been deleted.";
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
			objects = ArrayUtils.addAll(objects, MenuItem.FIELD_OUTCOME,outcome
					,MenuItem.ConfiguratorImpl.FIELD_LISTENER_ACTION,AbstractAction.Listener.Action.NAVIGATE_TO_VIEW
					,MenuItem.ConfiguratorImpl.FIELD_ACTION,action);
		if(action != null)
			objects = ArrayUtils.addAll(objects, MenuItem.FIELD_PARAMETERS,Map.of(ParameterName.ACTION_IDENTIFIER.getValue(),List.of(action.name())));
		return addRecordMenuItemByArguments(objects);
	}
	
	public AbstractCollection addRecordMenuItemByArgumentsNavigateToViewRead(Object...objects) {
		return addRecordMenuItemByArgumentsNavigateToView(Action.READ,ConstantEmpty.STRING);
	}
	
	public AbstractCollection enableCommandButtonSave() {
		setIsSavable(Boolean.TRUE);
		saveCommandButton = CommandButton.build(CommandButton.FIELD_VALUE,"Enregistrer",CommandButton.FIELD_ICON,"fa fa-floppy-o",CommandButton.FIELD_LISTENER,new CommandButton.Listener.AbstractImpl() {
			protected Object __executeFunction__(Object argument) {
				if(listener == null)
					return null;
				else
					return ((Listener)listener).listenSave(AbstractCollection.this);
			}
		}.setAction(org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractAction.Listener.Action.EXECUTE_FUNCTION));
		return this;
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
	
	/**/
	
	public static abstract class AbstractConfiguratorImpl<COLLECTION extends AbstractCollection> extends AbstractObjectAjaxable.AbstractConfiguratorImpl<COLLECTION> implements Serializable {

		@SuppressWarnings("unchecked")
		@Override
		public void configure(COLLECTION collection, Map<Object, Object> arguments) {
			super.configure(collection, arguments);
			if(collection.controllerEntity == null) {
				if(collection.elementClass != null && !Grid.Row.class.equals(collection.elementClass))
					collection.controllerEntity = (ControllerEntity<Object>) __inject__(ControllerLayer.class).injectInterfaceClassFromEntityClass(collection.elementClass);
			}
			Boolean filterable = (Boolean) MapHelper.readByKey(arguments, FIELD_FILTERABLE);
			if(filterable == null)
				filterable = collection.lazy;
			if(collection.rows == null)
				collection.rows = 0;
			if(collection.filterDelay == null)
				collection.filterDelay = 2000;
			if(StringHelper.isEmpty(collection.emptyMessage))
				collection.emptyMessage = "-- Aucun élément trouvé --";
			
			if(Boolean.TRUE.equals(collection.lazy)) {
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
				}				
			}else {
				
			}
			
			if(StringHelper.isBlank(collection.selectionMode)) {
				
			}else {
				collection.selection = new ArrayList<>();				
			}
			
			collection.dialogOutputPanel = Builder.build(OutputPanel.class);
			collection.dialog = Builder.build(Dialog.class,Map.of(Dialog.FIELD_STYLE_CLASS,"cyk-min-width-90-percent cyk-min-height-90-percent",Dialog.FIELD_MODAL,Boolean.TRUE));
			
			/* Listeners */
			
			collection.addAjaxes(Map.of(Ajax.FIELD_EVENT,"page",Ajax.FIELD_DISABLED,Boolean.FALSE,Ajax.ConfiguratorImpl.FIELD_LISTENER_NULLABLE,Boolean.TRUE)
					,Map.of(Ajax.FIELD_EVENT,"filter",Ajax.FIELD_DISABLED,Boolean.FALSE,Ajax.ConfiguratorImpl.FIELD_LISTENER_NULLABLE,Boolean.TRUE)
					,Map.of(Ajax.FIELD_EVENT,"sort",Ajax.FIELD_DISABLED,Boolean.FALSE,Ajax.ConfiguratorImpl.FIELD_LISTENER_NULLABLE,Boolean.TRUE)
					,Map.of(Ajax.FIELD_EVENT,"rowSelect",Ajax.ConfiguratorImpl.FIELD_LISTENER_NULLABLE,Boolean.TRUE)
					,Map.of(Ajax.FIELD_EVENT,"rowUnselect",Ajax.ConfiguratorImpl.FIELD_LISTENER_NULLABLE,Boolean.TRUE)
					,Map.of(Ajax.FIELD_EVENT,"rowSelectCheckbox",Ajax.ConfiguratorImpl.FIELD_LISTENER_NULLABLE,Boolean.TRUE)
					,Map.of(Ajax.FIELD_EVENT,"rowUnselectCheckbox",Ajax.ConfiguratorImpl.FIELD_LISTENER_NULLABLE,Boolean.TRUE)
					,Map.of(Ajax.FIELD_EVENT,"cellEdit",Ajax.ConfiguratorImpl.FIELD_LISTENER_NULLABLE,Boolean.TRUE)
					);		
			
			Class<?> recordMenuClass = (Class<?>) MapHelper.readByKey(arguments, FIELD_RECORD_MENU_CLASS);
			if(recordMenuClass == null)
				recordMenuClass = MenuButton.class;
			if(MenuButton.class.equals(recordMenuClass))
				collection.recordMenu = MenuButton.build();
			else if(ContextMenu.class.equals(recordMenuClass))
				collection.recordMenu = ContextMenu.build();
			
			Collection<Map<Object,Object>> recordMenuItemsByArguments = (Collection<Map<Object, Object>>) MapHelper.readByKey(arguments, FIELD_RECORD_MENU_ITEMS_BY_ARGUMENTS);
			if(CollectionHelper.isNotEmpty(recordMenuItemsByArguments)) {
				for(Map<Object,Object> map : recordMenuItemsByArguments)
					MapHelper.writeByKey(map, MenuItem.ConfiguratorImpl.FIELD_UPDATABLES, CollectionHelper.listOf(collection), Boolean.FALSE);
			}			
			collection.recordMenu.addItemsByArguments(recordMenuItemsByArguments);
			
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
		
		public static final String FIELD_ENTITY_FIELDS_NAMES = "entityFieldsNames";
		public static final String FIELD_FILTERABLE = "filterable";
		public static final String FIELD_EDITABLE_CELL = "editableCell";
		public static final String FIELD_RECORD_MENU_ITEMS_BY_ARGUMENTS = "recordMenuItemsByArguments";
		public static final String FIELD_LAZY_DATA_MODEL_CLASS = "lazyDataModelClass";
		public static final String FIELD_LAZY_DATA_MODEL = "lazyDataModel";
		public static final String FIELD_TITLE_VALUE = "titleValue";
		public static final String FIELD_RECORD_MENU_CLASS = "recordMenuClass";
		public static final String FIELD_RECORD_ACTIONS = "recordActions";
		public static final String FIELD_ACTIONS = "actions";
	}

	/**/
	
	public static interface Listener {
		Object listenSave(AbstractCollection dataTable);
		/**/
		public static abstract class AbstractImpl extends AbstractObject implements Listener,Serializable {
			@Override
			public Object listenSave(AbstractCollection dataTable) {
				return null;
			}
		}
	}
}