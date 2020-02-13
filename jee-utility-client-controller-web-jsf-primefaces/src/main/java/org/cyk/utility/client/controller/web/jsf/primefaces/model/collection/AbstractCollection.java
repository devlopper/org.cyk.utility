package org.cyk.utility.client.controller.web.jsf.primefaces.model.collection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractObject;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.panel.Dialog;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.panel.OutputPanel;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractCollection extends AbstractObject implements Serializable {

	protected Object value;
	protected String emptyMessage,rowsPerPageTemplate,rowStyleClass,paginatorTemplate,currentPageReportTemplate;
	protected Boolean lazy,paginator,paginatorAlwaysVisible;
	protected Integer rows=0,filterDelay=300;
	protected List<?> selection;
	protected Map<String,Object> map = new HashMap<>();
	protected OutputPanel dialogOutputPanel;
	protected Dialog dialog;
	
	/**/
	
	
	
	/**/
	
	public static final String FIELD_VALUE = "value";
	public static final String FIELD_LAZY = "lazy";
	
	/**/
	
	public static abstract class AbstractConfiguratorImpl<COLLECTION extends AbstractCollection> extends AbstractObject.AbstractConfiguratorImpl<COLLECTION> implements Serializable {

		@Override
		public void configure(COLLECTION collection, Map<Object, Object> arguments) {
			super.configure(collection, arguments);
			@SuppressWarnings("unchecked")
			Class<Object> entityClass = (Class<Object>) MapHelper.readByKey(arguments, FIELD_ENTIY_CLASS);
			collection.rows = 0;
			collection.filterDelay = 2000;
			collection.emptyMessage = "-- Aucun élément trouvé --";
			
			if(Boolean.TRUE.equals(collection.lazy)) {
				collection.rows = 20;
				collection.rowsPerPageTemplate = "20,50,100,500,1000";
				collection.paginatorTemplate = "{CurrentPageReport} {FirstPageLink} {PreviousPageLink} {PageLinks} {NextPageLink} {LastPageLink} {RowsPerPageDropdown}";
				collection.currentPageReportTemplate = "{totalRecords} | Page {currentPage}/{totalPages}";
				collection.paginator = Boolean.TRUE;
				if(entityClass == null) {
					
				}else {
					LazyDataModel<Object> value = new LazyDataModel<Object>(entityClass);
					collection.value = value;	
				}				
			}
			
			Boolean selectable = (Boolean) MapHelper.readByKey(arguments, FIELD_SELECTABLE);
			if(Boolean.TRUE.equals(selectable)) {
				collection.selection = new ArrayList<>();
				collection.dialogOutputPanel = Builder.build(OutputPanel.class);
				collection.dialog = Builder.build(Dialog.class,Map.of(Dialog.FIELD_STYLE_CLASS,"cyk-min-width-90-percent cyk-min-height-90-percent"));
			}
		}
		
		public static final String FIELD_ENTIY_CLASS = "entityClass";
		public static final String FIELD_SELECTABLE = "selectable";
		
	}	
}