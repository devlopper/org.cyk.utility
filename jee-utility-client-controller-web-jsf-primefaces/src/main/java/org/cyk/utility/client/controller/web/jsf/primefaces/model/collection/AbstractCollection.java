package org.cyk.utility.client.controller.web.jsf.primefaces.model.collection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.enumeration.Action;
import org.cyk.utility.__kernel__.identifier.resource.ParameterName;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.klass.NamingModel;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.persistence.query.QueryHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
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
	protected OutputText title;
	protected Object value,selectedCommandIdentifier;
	protected String emptyMessage,rowsPerPageTemplate,paginatorTemplate,currentPageReportTemplate,selectionMode,fileName;
	protected String rowStyleClass;
	protected Boolean lazy,paginator,paginatorAlwaysVisible,isExportable,isSelectionShowableInDialog;
	protected Integer rows,filterDelay;
	protected List<?> selection;
	protected Map<String,Object> map = new HashMap<>();
	protected OutputPanel dialogOutputPanel;
	protected Dialog dialog;
	protected Collection<AbstractCommand> headerToolbarLeftCommands;
	protected Collection<AbstractCommand> recordCommands;
	protected AbstractMenu recordMenu;
	
	/**/
	
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
			objects = ArrayUtils.addAll(objects, AbstractCommand.AbstractConfiguratorImpl.FIELD_OPEN_VIEW_IN_DIALOG_ARGUMENTS_GETTER_OUTCOME,outcome);
		return addHeaderToolbarLeftCommandsByArguments(objects);
	}
	
	public AbstractCollection addHeaderToolbarLeftCommandsByArgumentsOpenViewInDialog(Action action,Object...objects) {
		if(action != null)
			objects = ArrayUtils.addAll(objects, AbstractCommand.AbstractConfiguratorImpl.FIELD_OPEN_VIEW_IN_DIALOG_ARGUMENTS_GETTER_PARAMETERS
					,Map.of(ParameterName.ACTION_IDENTIFIER.getValue(),List.of(action.name())));
		return addHeaderToolbarLeftCommandsByArgumentsOpenViewInDialog(OutcomeGetter.getInstance().get(elementClass, action),objects);
	}
	
	public AbstractCollection addHeaderToolbarLeftCommandsByArgumentsOpenViewInDialogCreate() {
		return addHeaderToolbarLeftCommandsByArgumentsOpenViewInDialog(Action.CREATE,AbstractCommand.FIELD_VALUE,"Créer",AbstractCommand.FIELD_ICON,"fa fa-plus");
	}
	
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
			objects = ArrayUtils.addAll(objects, MenuItem.ConfiguratorImpl.FIELD_OPEN_VIEW_IN_DIALOG_ARGUMENTS_GETTER_OUTCOME,outcome);
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
		return addRecordMenuItemByArgumentsOpenViewInDialog(Action.EDIT,AbstractCommand.FIELD_VALUE,"Modifier",AbstractCommand.FIELD_ICON,"fa fa-edit");
	}
	
	public AbstractCollection addRecordMenuItemByArgumentsExecuteFunctionDelete() {
		if(elementClass != null) {
			@SuppressWarnings("unchecked")
			ControllerEntity<Object> controllerEntity = (ControllerEntity<Object>) __inject__(ControllerLayer.class).injectInterfaceClassFromEntityClass(elementClass);
			return addRecordMenuItemByArguments(MenuItem.FIELD_VALUE,"Supprimer",MenuItem.FIELD_ICON,"fa fa-remove"
					,MenuItem.FIELD_LISTENER,new AbstractAction.Listener.AbstractImpl() {
				@Override
				protected void __executeFunction__(Object argument) {
					super.__executeFunction__(argument);
					controllerEntity.delete(argument);
				}
			}.setAction(AbstractAction.Listener.Action.EXECUTE_FUNCTION),MenuItem.ConfiguratorImpl.FIELD_CONFIRMABLE,Boolean.TRUE
							,MenuItem.ConfiguratorImpl.FIELD_RUNNER_ARGUMENTS_SUCCESS_MESSAGE_ARGUMENTS_RENDER_TYPES,CollectionHelper.listOf(RenderType.GROWL));
		}
		return this;
	}
	
	/**/
	
	public static final String FIELD_ELEMENT_CLASS = "elementClass";
	public static final String FIELD_TITLE = "title";
	public static final String FIELD_VALUE = "value";
	public static final String FIELD_EMPTY_MESSAGE = "emptyMessage";
	public static final String FIELD_ROWS_PER_PAGE_TEMPLATE = "rowsPerPageTemplate";
	public static final String FIELD_ROWS = "rows";
	public static final String FIELD_ROW_STYLE_CLASS = "rowStyleClass";
	public static final String FIELD_FILTER_DELAY = "filterDelay";
	public static final String FIELD_LAZY = "lazy";
	public static final String FIELD_SELECTION_MODE = "selectionMode";
	
	/**/
	
	public static abstract class AbstractConfiguratorImpl<COLLECTION extends AbstractCollection> extends AbstractObjectAjaxable.AbstractConfiguratorImpl<COLLECTION> implements Serializable {

		@SuppressWarnings("unchecked")
		@Override
		public void configure(COLLECTION collection, Map<Object, Object> arguments) {
			super.configure(collection, arguments);
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
				collection.rows = 20;
				collection.rowsPerPageTemplate = "20,50,100,500,1000";
				collection.paginatorTemplate = "{CurrentPageReport} {FirstPageLink} {PreviousPageLink} {PageLinks} {NextPageLink} {LastPageLink} {RowsPerPageDropdown}";
				collection.currentPageReportTemplate = "Total {totalRecords} | Page {currentPage}/{totalPages}";
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
					if(Boolean.TRUE.equals(filterable)) {						
						String persistenceEntityClassName = ClassHelper.buildName(collection.elementClass.getPackageName(), collection.elementClass.getSimpleName()
								, new NamingModel().client().controller().entities(), new NamingModel().server().persistence().entities());						
						Class<?> persistenceEntityClass = ClassHelper.getByName(persistenceEntityClassName);
						if(StringHelper.isBlank(value.getReadQueryIdentifier()))
							value.setReadQueryIdentifier(QueryHelper.getIdentifierReadByFiltersLike(persistenceEntityClass));
						if(StringHelper.isBlank(value.getCountQueryIdentifier()))
							value.setCountQueryIdentifier(QueryHelper.getIdentifierCountByFiltersLike(persistenceEntityClass));						
						if(value.getEntityFieldsNames() == null)
							value.setEntityFieldsNames((Collection<String>) MapHelper.readByKey(arguments, FIELD_ENTITY_FIELDS_NAMES));													
					}					
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
					);		
			
			collection.recordMenu = MenuButton.build();
			Collection<Map<Object,Object>> recordMenuItemsByArguments = (Collection<Map<Object, Object>>) MapHelper.readByKey(arguments, FIELD_RECORD_MENU_ITEMS_BY_ARGUMENTS);
			if(CollectionHelper.isNotEmpty(recordMenuItemsByArguments)) {
				for(Map<Object,Object> map : recordMenuItemsByArguments)
					MapHelper.writeByKey(map, MenuItem.ConfiguratorImpl.FIELD_UPDATABLES, CollectionHelper.listOf(collection), Boolean.FALSE);
			}			
			collection.recordMenu.addItemsByArguments(recordMenuItemsByArguments);
			
			if(collection.title == null && StringHelper.isNotBlank((String) MapHelper.readByKey(arguments, FIELD_TITLE_VALUE))) {
				collection.title = OutputText.build(OutputText.FIELD_VALUE,MapHelper.readByKey(arguments, FIELD_TITLE_VALUE));
			}
		}
		
		public static final String FIELD_ENTITY_FIELDS_NAMES = "entityFieldsNames";
		public static final String FIELD_FILTERABLE = "filterable";
		public static final String FIELD_RECORD_MENU_ITEMS_BY_ARGUMENTS = "recordMenuItemsByArguments";
		public static final String FIELD_LAZY_DATA_MODEL_CLASS = "lazyDataModelClass";
		public static final String FIELD_LAZY_DATA_MODEL = "lazyDataModel";
		public static final String FIELD_TITLE_VALUE = "titleValue";
	}
}