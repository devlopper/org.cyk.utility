package org.cyk.utility.common.helper;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.MapHelper.Map;

@Singleton
public class MarkupLanguageHelper extends AbstractHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	private static MarkupLanguageHelper INSTANCE;
	
	public static MarkupLanguageHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new MarkupLanguageHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	/**/
	
	public static class Attributes extends MapHelper.Map<String, String> implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public static final String VALUES_SEPARATOR = Constant.CHARACTER_SPACE.toString();
		
		public Attributes() {
			super(String.class, String.class);
		}
		
		public Map<String, String> add(String key, String... strings) {
			return super.addString(key, VALUES_SEPARATOR, strings);
		}
		
		public Map<String, String> remove(String key, String... strings) {
			return super.removeString(key, VALUES_SEPARATOR, strings);
		}
		
		/**/
		
		/**
		 * get object's properties map and get the value from it
		 * @param object
		 * @param propertyName
		 * @return
		 */
		public static String get(Object object,String propertyName){
			return (String) FieldHelper.getInstance().read( ((AbstractBean)object).getPropertiesMap(), propertyName);
		}
		
		/**
		 * 
		 * @param object
		 * @param propertyName
		 * @param value
		 */
		public static void set(Object object,String propertyName,Object value){
			if(object!=null){
				Attributes attributes = (Attributes)(Object)((AbstractBean)object).getPropertiesMap();
				MethodHelper.getInstance().callSet(attributes, propertyName, String.class, value == null ? null : value.toString());	
			}
		}
		
		public String getIdentifier(){
			return get(IDENTIFIER);
		}
		
		public Attributes setIdentifier(String identifier){
			set(IDENTIFIER, identifier);
			return this;
		}
		
		public String getClazz(){
			return get(CLASS);
		}
		
		public Attributes setClass(String aClass){
			set(CLASS, aClass);
			return this;
		}
		
		public String getStyle(){
			return get(STYLE);
		}
		
		public Attributes setStyle(String style){
			set(STYLE, style);
			return this;
		}
		
		public String getStyleClass(){
			return get(STYLE_CLASS);
		}
		
		public Attributes setStyleClass(String styleClass){
			set(STYLE_CLASS, styleClass);
			return this;
		}
		
		public String getTitle(){
			return get(TITLE);
		}
		
		public Attributes setTitle(String title){
			set(TITLE, title);
			return this;
		}
		
		public String getOnClick(){
			return get(ON_CLICK);
		}
		
		public Attributes setOnClick(String onClick){
			set(ON_CLICK, onClick);
			return this;
		}
		
		public String getOnChange(){
			return get(ON_CHANGE);
		}
		
		public Attributes setOnChange(String onChange){
			set(ON_CHANGE, onChange);
			return this;
		}
		
		public String getOnComplete(){
			return get(ON_COMPLETE);
		}
		
		public Attributes setOnComplete(String onComplete){
			set(ON_COMPLETE, onComplete);
			return this;
		}
		
		public String getAction(){
			return get(ACTION);
		}
		
		public Attributes setAction(String action){
			set(ACTION, action);
			return this;
		}
		
		public String getAjax(){
			return get(AJAX);
		}
		
		public Attributes setAjax(String ajax){
			set(AJAX, ajax);
			return this;
		}
		
		public String getDisabled(){
			return get(DISABLED);
		}
		
		public Attributes setDisabled(String disabled){
			set(DISABLED, disabled);
			return this;
		}
		
		public String getType(){
			return get(TYPE);
		}
		
		public Attributes setType(String type){
			set(TYPE, type);
			return this;
		}
		
		public String getIcon(){
			return get(ICON);
		}
		
		public Attributes setIcon(String icon){
			set(ICON, icon);
			return this;
		}
		
		public String getImmediate(){
			return get(IMMEDIATE);
		}
		
		public Attributes setImmediate(String immediate){
			set(IMMEDIATE, immediate);
			return this;
		}
		
		public String getProcess(){
			return get(PROCESS);
		}
		
		public Attributes setProcess(String process){
			set(PROCESS, process);
			return this;
		}
		
		public static final String FIELD_RENDERED = "rendered";
		public String getRendered(){
			return get(RENDERED);
		}
		public Attributes setRendered(String rendered){
			set(RENDERED, rendered);
			return this;
		}
		
		public String getRequired(){
			return get(REQUIRED);
		}
		
		public Attributes setRequired(String required){
			set(REQUIRED, required);
			return this;
		}
		
		public String getRequiredMessage(){
			return get(REQUIRED_MESSAGE);
		}
		
		public Attributes setRequiredMessage(String requiredMessage){
			set(REQUIRED_MESSAGE, requiredMessage);
			return this;
		}
		
		public String getUpdate(){
			return get(UPDATE);
		}
		
		public Attributes setUpdate(String update){
			set(UPDATE, update);
			return this;
		}
		
		public String getWidgetVar(){
			return get(WIDGET_VAR);
		}
		
		public Attributes setWidgetVar(String widgetVar){
			set(WIDGET_VAR, widgetVar);
			return this;
		}
		
		public String getValidatable(){
			return get(VALIDATABLE);
		}
		
		public Attributes setValidatable(String validatable){
			set(VALIDATABLE, validatable);
			return this;
		}
		
		public String getFilter(){
			return get(FILTER);
		}
		
		public Attributes setFilter(String filter){
			set(FILTER, filter);
			return this;
		}
		
		public String getFilterMatchMode(){
			return get(FILTER_MATCH_MODE);
		}
		
		public Attributes setFilterMatchMode(String filterMatchMode){
			set(FILTER_MATCH_MODE, filterMatchMode);
			return this;
		}
		
		public String getLabel(){
			return get(LABEL);
		}
		
		public Attributes setLabel(String label){
			set(LABEL, label);
			return this;
		}
		
		public String getPlaceholder(){
			return get(PLACE_HOLDER);
		}
		
		public Attributes setPlaceholder(String placeHolder){
			set(PLACE_HOLDER, placeHolder);
			return this;
		}
		
		public String getValue(){
			return get(VALUE);
		}
		
		public Attributes setValue(String value){
			set(VALUE, value);
			return this;
		}
		
		public String getEscape(){
			return get(ESCAPE);
		}
		
		public Attributes setEscape(String escape){
			set(ESCAPE, escape);
			return this;
		}
		
		public String getEmptyMessage(){
			return get(EMPTY_MESSAGE);
		}
		
		public Attributes setEmptyMessage(String emptyMessage){
			set(EMPTY_MESSAGE, emptyMessage);
			return this;
		}
		
		public String getSortMode(){
			return get(SORT_MODE);
		}
		
		public Attributes setSortMode(String sortMode){
			set(SORT_MODE, sortMode);
			return this;
		}
		
		public String getReflow(){
			return get(REFLOW);
		}
		
		public Attributes setReflow(String reflow){
			set(REFLOW, reflow);
			return this;
		}
		
		public String getLazy(){
			return get(LAZY);
		}
		
		public Attributes setLazy(String lazy){
			set(LAZY, lazy);
			return this;
		}
		
		public String getPaginator(){
			return get(PAGINATOR);
		}
		
		public Attributes setPaginator(String paginator){
			set(PAGINATOR, paginator);
			return this;
		}
		
		public String getPaginatorPosition(){
			return get(PAGINATOR_POSITION);
		}
		
		public Attributes setPaginatorPosition(String paginatorPosition){
			set(PAGINATOR_POSITION, paginatorPosition);
			return this;
		}
		
		public String getPaginatorTemplate(){
			return get(PAGINATOR_TEMPLATE);
		}
		
		public Attributes setPaginatorTemplate(String paginatorTemplate){
			set(PAGINATOR_TEMPLATE, paginatorTemplate);
			return this;
		}
		
		public String getCurrentPageReportTemplate(){
			return get(CURRENT_PAGE_REPORT_TEMPLATE);
		}
		
		public Attributes setCurrentPageReportTemplate(String currentPageReportTemplate){
			set(CURRENT_PAGE_REPORT_TEMPLATE, currentPageReportTemplate);
			return this;
		}
		
		public String getTableStyleClass(){
			return get(TABLE_STYLE_CLASS);
		}
		
		public Attributes setTableStyleClass(String tableStyleClass){
			set(TABLE_STYLE_CLASS, tableStyleClass);
			return this;
		}
		
		public String getRowStyleClass(){
			return get(ROW_STYLE_CLASS);
		}
		
		public Attributes setRowStyleClass(String rowStyleClass){
			set(ROW_STYLE_CLASS, rowStyleClass);
			return this;
		}
		
		public String getPaginatorAlwaysVisible(){
			return get(PAGINATOR_ALWAYS_VISIBLE);
		}
		
		public Attributes setPaginatorAlwaysVisible(String paginatorAlwaysVisible){
			set(PAGINATOR_ALWAYS_VISIBLE, paginatorAlwaysVisible);
			return this;
		}
		
		public String getPageLinks(){
			return get(PAGE_LINKS);
		}
		
		public Attributes setPageLinks(String pageLinks){
			set(PAGE_LINKS, pageLinks);
			return this;
		}
		
		public String getRows(){
			return get(ROWS);
		}
		
		public Attributes setRows(String rows){
			set(ROWS, rows);
			return this;
		}
		
		public String getGlobal(){
			return get(GLOBAL);
		}
		
		public Attributes setGlobal(String global){
			set(GLOBAL, global);
			return this;
		}
		
		public static final String GLOBAL = "GLOBAL";
		public static final String ROWS = "ROWS";
		public static final String CLASS = "CLASS";
		public static final String IDENTIFIER = "IDENTIFIER";
		public static final String ON_CLICK = "ONCLICK";
		public static final String ON_CHANGE = "ONCHANGE";
		public static final String ON_COMPLETE = "ONCOMPLETE";
		public static final String STYLE = "STYLE";
		public static final String STYLE_CLASS = "STYLE_CLASS";
		public static final String TITLE = "TITLE";
		public static final String TYPE = "TYPE";
		public static final String VALUE = "VALUE";
		public static final String ESCAPE = "ESCAPE";
		public static final String ACTION = "ACTION";			
		public static final String AJAX = "AJAX";
		public static final String DISABLED = "DISABLED";
		public static final String ICON = "ICON";
		public static final String IMMEDIATE = "IMMEDIATE";
		public static final String VALIDATABLE = "VALIDATABLE";
		public static final String PROCESS = "PROCESS";
		public static final String RENDERED = "RENDERED";
		public static final String REQUIRED = "REQUIRED";
		public static final String REQUIRED_MESSAGE = "REQUIRED_MESSAGE";
		public static final String UPDATE = "UPDATE";
		public static final String WIDGET_VAR = "WIDGET_VAR";
		public static final String FILTER = "FILTER";
		public static final String LABEL = "LABEL";
		public static final String PLACE_HOLDER = "PLACE_HOLDER";
		public static final String FILTER_MATCH_MODE = "FILTER_MATCH_MODE";
		public static final String EMPTY_MESSAGE = "EMPTY_MESSAGE";
		public static final String SORT_MODE = "SORT_MODE";
		public static final String REFLOW = "REFLOW";
		public static final String LAZY = "LAZY";	
		public static final String PAGINATOR = "PAGINATOR";
		public static final String PAGINATOR_POSITION = "PAGINATOR_POSITION";
		public static final String PAGINATOR_TEMPLATE = "PAGINATOR_TEMPLATE";
		public static final String CURRENT_PAGE_REPORT_TEMPLATE = "CURRENT_PAGE_REPORT_TEMPLATE";		
		public static final String TABLE_STYLE_CLASS = "TABLE_STYLE_CLASS";
		public static final String ROW_STYLE_CLASS = "ROW_STYLE_CLASS";
		public static final String PAGE_LINKS = "PAGE_LINKS";
		public static final String PAGINATOR_ALWAYS_VISIBLE = "PAGINATOR_ALWAYS_VISIBLE";
		
	}
}
