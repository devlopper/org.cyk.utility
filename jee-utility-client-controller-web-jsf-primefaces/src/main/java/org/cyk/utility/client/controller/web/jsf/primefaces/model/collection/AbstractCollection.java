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
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractObject;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.BlockUI;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.ajax.Ajax;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.CommandButton;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.panel.Dialog;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.panel.OutputPanel;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter
public abstract class AbstractCollection extends AbstractObject implements Serializable {

	protected Object value;
	protected String emptyMessage,rowsPerPageTemplate,paginatorTemplate,currentPageReportTemplate,selectionMode,fileName;
	protected String rowStyleClass;
	protected Boolean lazy,paginator,paginatorAlwaysVisible,isExportable,isSelectionShowableInDialog;
	protected Integer rows,filterDelay;
	protected List<?> selection;
	protected Map<String,Object> map = new HashMap<>();
	protected OutputPanel dialogOutputPanel;
	protected Dialog dialog;
	protected Collection<CommandButton> headerToolbarLeftCommandButtons;
	protected BlockUI blockUI;
	protected Ajax pageAjax,sortAjax,filterAjax,rowSelectAjax,rowUnselectAjax,rowSelectCheckBoxAjax,rowUnselectCheckBoxAjax;
	
	/**/
	
	public Collection<CommandButton> getHeaderToolbarLeftCommandButtons(Boolean injectIfNull) {
		if(headerToolbarLeftCommandButtons == null && Boolean.TRUE.equals(injectIfNull))
			headerToolbarLeftCommandButtons = new ArrayList<>();
		return headerToolbarLeftCommandButtons;
	}
	
	public AbstractCollection addHeaderToolbarLeftCommandButtons(Collection<CommandButton> headerToolbarLeftCommandButtons) {
		if(CollectionHelper.isEmpty(headerToolbarLeftCommandButtons))
			return this;
		getHeaderToolbarLeftCommandButtons(Boolean.TRUE).addAll(headerToolbarLeftCommandButtons);
		return this;
	}
	
	public AbstractCollection addHeaderToolbarLeftCommandButtons(CommandButton...headerToolbarLeftCommandButtons) {
		if(ArrayHelper.isEmpty(headerToolbarLeftCommandButtons))
			return this;
		return addHeaderToolbarLeftCommandButtons(CollectionHelper.listOf(headerToolbarLeftCommandButtons));
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
	
	public static abstract class AbstractConfiguratorImpl<COLLECTION extends AbstractCollection> extends AbstractObject.AbstractConfiguratorImpl<COLLECTION> implements Serializable {

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
				collection.dialogOutputPanel = Builder.build(OutputPanel.class);
				collection.dialog = Builder.build(Dialog.class,Map.of(Dialog.FIELD_STYLE_CLASS,"cyk-min-width-90-percent cyk-min-height-90-percent"
						,Dialog.FIELD_MODAL,Boolean.TRUE));
			}
			
			if(collection.blockUI == null)
				collection.blockUI = Builder.build(BlockUI.class, Map.of(BlockUI.FIELD_BLOCK,collection.identifier,BlockUI.FIELD_TRIGGER,collection.identifier));				
			
			/* Listeners */
			
			if(collection.pageAjax == null)
				collection.pageAjax = Builder.build(Ajax.class, Map.of(Ajax.FIELD_EVENT,"page",Ajax.FIELD_DISABLED,Boolean.FALSE,Ajax.ConfiguratorImpl.FIELD_BLOCK_UI
						,collection.blockUI,Ajax.ConfiguratorImpl.FIELD_RUNNER_ARGUMENTS_SUCCESS_MESSAGE_ARGUMENTS_NULLABLE,Boolean.TRUE
						,Ajax.ConfiguratorImpl.FIELD_LISTENER_NULLABLE,Boolean.TRUE));
			if(collection.filterAjax == null)
				collection.filterAjax = Builder.build(Ajax.class, Map.of(Ajax.FIELD_EVENT,"filter",Ajax.FIELD_DISABLED,Boolean.FALSE,Ajax.ConfiguratorImpl.FIELD_BLOCK_UI
						,collection.blockUI,Ajax.ConfiguratorImpl.FIELD_RUNNER_ARGUMENTS_SUCCESS_MESSAGE_ARGUMENTS_NULLABLE,Boolean.TRUE
						,Ajax.ConfiguratorImpl.FIELD_LISTENER_NULLABLE,Boolean.TRUE));
			if(collection.sortAjax == null)
				collection.sortAjax = Builder.build(Ajax.class, Map.of(Ajax.FIELD_EVENT,"sort",Ajax.FIELD_DISABLED,Boolean.FALSE,Ajax.ConfiguratorImpl.FIELD_BLOCK_UI
						,collection.blockUI,Ajax.ConfiguratorImpl.FIELD_RUNNER_ARGUMENTS_SUCCESS_MESSAGE_ARGUMENTS_NULLABLE,Boolean.TRUE
						,Ajax.ConfiguratorImpl.FIELD_LISTENER_NULLABLE,Boolean.TRUE));
			if(collection.rowSelectAjax == null)
				collection.rowSelectAjax = Builder.build(Ajax.class, Map.of(Ajax.FIELD_EVENT,"rowSelect",Ajax.FIELD_DISABLED,Boolean.FALSE,Ajax.ConfiguratorImpl.FIELD_BLOCK_UI
						,collection.blockUI,Ajax.ConfiguratorImpl.FIELD_RUNNER_ARGUMENTS_SUCCESS_MESSAGE_ARGUMENTS_NULLABLE,Boolean.TRUE
						,Ajax.ConfiguratorImpl.FIELD_LISTENER_NULLABLE,Boolean.TRUE));
			if(collection.rowUnselectAjax == null)
				collection.rowUnselectAjax = Builder.build(Ajax.class, Map.of(Ajax.FIELD_EVENT,"rowUnselect",Ajax.FIELD_DISABLED,Boolean.FALSE,Ajax.ConfiguratorImpl.FIELD_BLOCK_UI
						,collection.blockUI,Ajax.ConfiguratorImpl.FIELD_RUNNER_ARGUMENTS_SUCCESS_MESSAGE_ARGUMENTS_NULLABLE,Boolean.TRUE
						,Ajax.ConfiguratorImpl.FIELD_LISTENER_NULLABLE,Boolean.TRUE));
			if(collection.rowSelectCheckBoxAjax == null)
				collection.rowSelectCheckBoxAjax = Builder.build(Ajax.class, Map.of(Ajax.FIELD_EVENT,"rowSelectCheckbox",Ajax.FIELD_DISABLED,Boolean.FALSE,Ajax.ConfiguratorImpl.FIELD_BLOCK_UI
						,collection.blockUI,Ajax.ConfiguratorImpl.FIELD_RUNNER_ARGUMENTS_SUCCESS_MESSAGE_ARGUMENTS_NULLABLE,Boolean.TRUE
						,Ajax.ConfiguratorImpl.FIELD_LISTENER_NULLABLE,Boolean.TRUE));
			if(collection.rowUnselectCheckBoxAjax == null)
				collection.rowUnselectCheckBoxAjax = Builder.build(Ajax.class, Map.of(Ajax.FIELD_EVENT,"rowUnselectCheckbox",Ajax.FIELD_DISABLED,Boolean.FALSE,Ajax.ConfiguratorImpl.FIELD_BLOCK_UI
						,collection.blockUI,Ajax.ConfiguratorImpl.FIELD_RUNNER_ARGUMENTS_SUCCESS_MESSAGE_ARGUMENTS_NULLABLE,Boolean.TRUE
						,Ajax.ConfiguratorImpl.FIELD_LISTENER_NULLABLE,Boolean.TRUE));
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
		
		public AbstractActionListenerImpl(AbstractCollection collection) {
			this.collection = collection;
		}
		
		@Override
		public void listenAction(Object argument) {
			if(Boolean.TRUE.equals(isDialogShowable())) {
				//if(collection != null && collection.dialog != null && StringHelper.isNotBlank(collection.dialog.getWidgetVar()))
				__showDialog__();
			}else {
				MessageRenderer.getInstance().render(getDialogNotShowableMessageSummary(), Severity.WARNING, RenderType.GROWL);
			}
		}
		
		protected void __showDialog__() {
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