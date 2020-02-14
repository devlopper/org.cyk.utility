package org.cyk.utility.client.controller.web.jsf.primefaces.model.collection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractObject;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.panel.Dialog;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.panel.OutputPanel;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractCollection extends AbstractObject implements Serializable {

	protected Object value;
	protected String emptyMessage,rowsPerPageTemplate,rowStyleClass,paginatorTemplate,currentPageReportTemplate,selectionMode;
	protected Boolean lazy,paginator,paginatorAlwaysVisible;
	protected Integer rows,filterDelay;
	protected List<?> selection;
	protected Map<String,Object> map = new HashMap<>();
	protected OutputPanel dialogOutputPanel;
	protected Dialog dialog;
	
	/**/
	
	/**/
	
	public static final String FIELD_VALUE = "value";
	public static final String FIELD_EMPTY_MESSAGE = "emptyMessage";
	public static final String FIELD_ROWS_PER_PAGE_TEMPLATE = "rowsPerPageTemplate";
	public static final String FIELD_ROWS = "rows";
	public static final String FIELD_FILTER_DELAY = "filterDelay";
	public static final String FIELD_LAZY = "lazy";
	public static final String FIELD_SELECTION_MODE = "selectionMode";
	
	/**/
	
	public static abstract class AbstractConfiguratorImpl<COLLECTION extends AbstractCollection> extends AbstractObject.AbstractConfiguratorImpl<COLLECTION> implements Serializable {

		@Override
		public void configure(COLLECTION collection, Map<Object, Object> arguments) {
			super.configure(collection, arguments);
			@SuppressWarnings("unchecked")
			Class<Object> entityClass = (Class<Object>) MapHelper.readByKey(arguments, FIELD_ENTIY_CLASS);
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
					collection.value = value;	
				}				
			}else {
				
			}
			
			if(StringHelper.isBlank(collection.selectionMode)) {
				
			}else {
				collection.selection = new ArrayList<>();
				collection.dialogOutputPanel = Builder.build(OutputPanel.class);
				collection.dialog = Builder.build(Dialog.class,Map.of(Dialog.FIELD_STYLE_CLASS,"cyk-min-width-90-percent cyk-min-height-90-percent"));
			}
		}
		
		public static final String FIELD_ENTIY_CLASS = "entityClass";
	}	
}