package org.cyk.utility.client.controller.web.jsf.primefaces.model.collection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.klass.NamingModel;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.persistence.query.QueryHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.user.interface_.message.MessageRenderer;
import org.cyk.utility.__kernel__.user.interface_.message.RenderType;
import org.cyk.utility.__kernel__.user.interface_.message.Severity;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractAction;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractObjectAjaxable;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.ajax.Ajax;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.AbstractCommand;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.menu.AbstractMenu;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.menu.MenuButton;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.panel.Dialog;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.panel.OutputPanel;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter
public abstract class AbstractCollection extends AbstractObjectAjaxable implements Serializable {

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
	
	/**/
	
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
			Class<Object> entityClass = (Class<Object>) MapHelper.readByKey(arguments, FIELD_ENTIY_CLASS);
			Boolean filterable = (Boolean) MapHelper.readByKey(arguments, FIELD_FILTERABLE);
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
				if(entityClass == null) {
					
				}else {
					LazyDataModel<Object> value = new LazyDataModel<Object>(entityClass);
					if(Boolean.TRUE.equals(filterable)) {						
						String persistenceEntityClassName = ClassHelper.buildName(entityClass.getPackageName(), entityClass.getSimpleName()
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
		}
		
		public static final String FIELD_ENTIY_CLASS = "entityClass";
		public static final String FIELD_ENTITY_FIELDS_NAMES = "entityFieldsNames";
		public static final String FIELD_FILTERABLE = "filterable";
		
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static abstract class AbstractActionListenerImpl extends org.cyk.utility.__kernel__.object.AbstractObject implements AbstractAction.Listener,Serializable {
		
		protected AbstractCollection collection;
		protected Integer minimumSelectionSize=1,maximumSelectionSize;
		protected Boolean isSelectionShowable=Boolean.TRUE;
		protected Object commandIdentifier;
		
		public AbstractActionListenerImpl(AbstractCollection collection) {
			this.collection = collection;
		}
		
		@Override
		public void listenAction(Object argument) {
			if(Boolean.TRUE.equals(isDialogShowable())) {
				__showDialog__();
			}else {
				MessageRenderer.getInstance().render(getDialogNotShowableMessageSummary(), Severity.WARNING, RenderType.GROWL);
			}
		}
		
		protected void __showDialog__() {
			collection.setSelectedCommandIdentifier(commandIdentifier);
			collection.setIsSelectionShowableInDialog(isSelectionShowable);			
			org.omnifaces.util.Ajax.oncomplete("PF('"+collection.dialog.getWidgetVar()+"').show();");
		}
		
		protected Boolean isDialogShowable() {
			if(minimumSelectionSize == null && maximumSelectionSize == null)
				return Boolean.TRUE;
			if(minimumSelectionSize != null)
				return CollectionHelper.getSize(collection.selection) >= minimumSelectionSize;
			return Boolean.TRUE;
		}
		
		protected String getDialogNotShowableMessageSummary() {
			if(minimumSelectionSize != null)
				return "Sélectionner au moins "+minimumSelectionSize;
			return "Impossible d'ouvrir la boite de dialogue";
		}
	}
}