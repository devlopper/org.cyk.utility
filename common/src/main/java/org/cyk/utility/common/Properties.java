package org.cyk.utility.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.cyk.utility.common.helper.ArrayHelper;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.NumberHelper;
import org.cyk.utility.common.helper.StringHelper;

public class Properties implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private java.util.Map<Object,Object> map;
	private Object stringValueSeparator = Constant.CHARACTER_SPACE;
	//private Object doubleStringValueSeparator = StringUtils.repeat(stringValueSeparator.toString(), 2);
	
	/**/
	
	/* set */
	
	public Properties set(Object key,Object value){
		if(this.map == null)
			this.map = new java.util.LinkedHashMap<Object, Object>();
		this.map.put(key, value);
		return this;
	}
	
	/* get */
	
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> aClass,Object key,T nullValue){
		return (T) (this.map == null ? nullValue : this.map.get(key) == null ? nullValue : this.map.get(key));
	}
	
	public <T> T get(Object key,Class<T> aClass){
		return get(aClass,key,null);
	}
	
	public Object get(Object key,Object nullValue){
		return get(Object.class, key, nullValue);
	}
	
	public Object get(Object key){
		return get(key,(Object)null);
	}
	
	public Object getString(Object key){
		return get(key,String.class);
	}
	
	public Long getLong(Object key){
		return get(key,Long.class);
	}
	
	public Integer getInteger(Object key){
		return get(key,Integer.class);
	}
	
	/* add */
	
	@SuppressWarnings("unchecked")
	private Properties __add__(Object key,Object value,Class<?> valueClass,Boolean add){
		Object keyValue = get(key);
		if(valueClass == null && keyValue!=null)
			valueClass = keyValue.getClass();
		if(ClassHelper.getInstance().isInstanceOf(Collection.class, valueClass)){
			if(keyValue==null){
				if(ClassHelper.getInstance().isInstanceOf(List.class, valueClass))
					keyValue = new ArrayList<>();
				else if(ClassHelper.getInstance().isInstanceOf(Set.class, valueClass))
					keyValue = new LinkedHashSet<>();
				else
					keyValue = new ArrayList<>();
			}
			if(Boolean.TRUE.equals(add))
				((Collection<Object>)keyValue).add(value);
			else
				((Collection<Object>)keyValue).remove(value);
		}else if(ClassHelper.getInstance().isEqual(String.class, valueClass)){
			if(Boolean.TRUE.equals(add))
				keyValue = (String)keyValue + value;
			else
				keyValue = StringHelper.getInstance().replace((String)keyValue, (String) value, Constant.EMPTY_STRING);
		}else if(ClassHelper.getInstance().isNumber(valueClass)){
			if(Boolean.TRUE.equals(add))
				keyValue = NumberHelper.getInstance().add((Number)keyValue , (Number)value);
			else
				keyValue = NumberHelper.getInstance().subtract((Number)keyValue , (Number)value);
		}
		set(key,keyValue);
		return this;
	}
	
	public Properties add(Object key,Object value,Class<?> valueClass){
		__add__(key, value, valueClass, Boolean.TRUE);
		return this;
	}
	
	public Properties add(Object key,Object value){
		return add(key,value,value == null ? getValueClass(key) : value.getClass());
	}
	
	public Properties addString(Object key,Object separator,String...strings){
		if(ArrayHelper.getInstance().isNotEmpty(strings)){
			for(String string : strings)
				if(StringHelper.getInstance().isNotBlank(string))
					add(key, (get(key) == null ? Constant.EMPTY_STRING : separator ) + string, String.class);
		}
		return this;
	}
	
	public Properties addString(Object key,String...strings){
		return addString(key, stringValueSeparator, strings);
	}
	
	public Properties addCollectionElement(Object key,Object...elements){
		for(Object element : elements)
			add(key, element, Collection.class);
		return this;
	}
	
	/* subtract */
	
	public Properties subtract(Object key,Object value,Class<?> valueClass){
		__add__(key, value, valueClass, Boolean.FALSE);
		return this;
	}
	
	public Properties subtract(Object key,Object value){
		subtract(key, value, value == null ? getValueClass(key) : value.getClass());
		return this;
	}
	
	public Properties subtractString(Object key,Object separator,String...strings){
		if(ArrayHelper.getInstance().isNotEmpty(strings)){
			for(String string : strings)
				if(StringHelper.getInstance().isNotBlank(string)){
					subtract(key, stringValueSeparator+string, String.class);
					subtract(key, stringValueSeparator+string+stringValueSeparator, String.class);
					subtract(key, string+stringValueSeparator, String.class);
				}
		}
		//set(key,StringUtils.replace(StringUtils.trim(getString(key)), doubleStringValueSeparator.toString(), stringValueSeparator.toString()) );
		/*String string = (java.lang.String) get(key);
		if(ArrayHelper.getInstance().isNotEmpty(strings))
			for(String s : strings){
				if(string.startsWith(s+separator))
					string = StringHelper.getInstance().replace(string, s+separator, Constant.EMPTY_STRING);
				else if(string.endsWith(separator+s))
					string = StringHelper.getInstance().replace(string, separator+s, Constant.EMPTY_STRING);
				else if(string.contains(separator+s+separator))
					string = StringHelper.getInstance().replace(string, separator+s+separator, separator.toString());
			}
		set(key, string);
		*/
		return this;
	}
	
	public Properties subtractCollectionElement(Object key,Object...elements){
		for(Object element : elements)
			subtract(key, element, Collection.class);
		return this;
	}

	/**/
	
	public Class<?> getValueClass(Object key){
		Object value = get(key);
		return value == null ? Object.class : value.getClass();
	}
	
	/* specific getters and setters*/
	
	public Object getIdentifier(){
		return get(IDENTIFIER);
	}
	
	public Properties setIdentifier(Object identifier){
		set(IDENTIFIER, identifier);
		return this;
	}
	
	public Object getClazz(){
		return get(CLASS);
	}
	
	public Properties setClass(Object aClass){
		set(CLASS, aClass);
		return this;
	}
	
	public Object getStyle(){
		return get(STYLE);
	}
	
	public Properties setStyle(Object style){
		set(STYLE, style);
		return this;
	}
	
	public Object getStyleClass(){
		return get(STYLE_CLASS);
	}
	
	public Properties setStyleClass(Object styleClass){
		set(STYLE_CLASS, styleClass);
		return this;
	}
	
	public Object getTitle(){
		return get(TITLE);
	}
	
	public Properties setTitle(Object title){
		set(TITLE, title);
		return this;
	}
	
	public Object getOnClick(){
		return get(ON_CLICK);
	}
	
	public Properties setOnClick(Object onClick){
		set(ON_CLICK, onClick);
		return this;
	}
	
	public Object getOnChange(){
		return get(ON_CHANGE);
	}
	
	public Properties setOnChange(Object onChange){
		set(ON_CHANGE, onChange);
		return this;
	}
	
	public Object getOnComplete(){
		return get(ON_COMPLETE);
	}
	
	public Properties setOnComplete(Object onComplete){
		set(ON_COMPLETE, onComplete);
		return this;
	}
	
	public Object getAction(){
		return get(ACTION);
	}
	
	public Properties setAction(Object action){
		set(ACTION, action);
		return this;
	}
	
	public Object getAjax(){
		return get(AJAX);
	}
	
	public Properties setAjax(Object ajax){
		set(AJAX, ajax);
		return this;
	}
	
	public Object getDisabled(){
		return get(DISABLED);
	}
	
	public Properties setDisabled(Object disabled){
		set(DISABLED, disabled);
		return this;
	}
	
	public Object getType(){
		return get(TYPE);
	}
	
	public Properties setType(Object type){
		set(TYPE, type);
		return this;
	}
	
	public Object getIcon(){
		return get(ICON);
	}
	
	public Properties setIcon(Object icon){
		set(ICON, icon);
		return this;
	}
	
	public Object getImmediate(){
		return get(IMMEDIATE);
	}
	
	public Properties setImmediate(Object immediate){
		set(IMMEDIATE, immediate);
		return this;
	}
	
	public Object getProcess(){
		return get(PROCESS);
	}
	
	public Properties setProcess(Object process){
		set(PROCESS, process);
		return this;
	}
	
	public static final String FIELD_RENDERED = "rendered";
	public Object getRendered(){
		return get(RENDERED);
	}
	public Properties setRendered(Object rendered){
		set(RENDERED, rendered);
		return this;
	}
	
	public Object getRequired(){
		return get(REQUIRED);
	}
	
	public Properties setRequired(Object required){
		set(REQUIRED, required);
		return this;
	}
	
	public Object getRequiredMessage(){
		return get(REQUIRED_MESSAGE);
	}
	
	public Properties setRequiredMessage(Object requiredMessage){
		set(REQUIRED_MESSAGE, requiredMessage);
		return this;
	}
	
	public Object getUpdate(){
		return get(UPDATE);
	}
	
	public Properties setUpdate(Object update){
		set(UPDATE, update);
		return this;
	}
	
	public Object getWidgetVar(){
		return get(WIDGET_VAR);
	}
	
	public Properties setWidgetVar(Object widgetVar){
		set(WIDGET_VAR, widgetVar);
		return this;
	}
	
	public Object getValidatable(){
		return get(VALIDATABLE);
	}
	
	public Properties setValidatable(Object validatable){
		set(VALIDATABLE, validatable);
		return this;
	}
	
	public Object getFilter(){
		return get(FILTER);
	}
	
	public Properties setFilter(Object filter){
		set(FILTER, filter);
		return this;
	}
	
	public Object getFilterMatchMode(){
		return get(FILTER_MATCH_MODE);
	}
	
	public Properties setFilterMatchMode(Object filterMatchMode){
		set(FILTER_MATCH_MODE, filterMatchMode);
		return this;
	}
	
	public Object getLabel(){
		return get(LABEL);
	}
	
	public Properties setLabel(Object label){
		set(LABEL, label);
		return this;
	}
	
	public Object getDescription(){
		return get(DESCRIPTION);
	}
	
	public Properties setDescription(Object description){
		set(DESCRIPTION, description);
		return this;
	}
	
	public Object getPlaceholder(){
		return get(PLACE_HOLDER);
	}
	
	public Properties setPlaceholder(Object placeHolder){
		set(PLACE_HOLDER, placeHolder);
		return this;
	}
	
	public Object getValue(){
		return get(VALUE);
	}
	
	public Properties setValue(Object value){
		set(VALUE, value);
		return this;
	}
	
	public Object getEscape(){
		return get(ESCAPE);
	}
	
	public Properties setEscape(Object escape){
		set(ESCAPE, escape);
		return this;
	}
	
	public Object getEmptyMessage(){
		return get(EMPTY_MESSAGE);
	}
	
	public Properties setEmptyMessage(Object emptyMessage){
		set(EMPTY_MESSAGE, emptyMessage);
		return this;
	}
	
	public Object getSortMode(){
		return get(SORT_MODE);
	}
	
	public Properties setSortMode(Object sortMode){
		set(SORT_MODE, sortMode);
		return this;
	}
	
	public Object getReflow(){
		return get(REFLOW);
	}
	
	public Properties setReflow(Object reflow){
		set(REFLOW, reflow);
		return this;
	}
	
	public Object getLazy(){
		return get(LAZY);
	}
	
	public Properties setLazy(Object lazy){
		set(LAZY, lazy);
		return this;
	}
	
	public Object getPaginator(){
		return get(PAGINATOR);
	}
	
	public Properties setPaginator(Object paginator){
		set(PAGINATOR, paginator);
		return this;
	}
	
	public Object getPaginatorPosition(){
		return get(PAGINATOR_POSITION);
	}
	
	public Properties setPaginatorPosition(Object paginatorPosition){
		set(PAGINATOR_POSITION, paginatorPosition);
		return this;
	}
	
	public Object getPaginatorTemplate(){
		return get(PAGINATOR_TEMPLATE);
	}
	
	public Properties setPaginatorTemplate(Object paginatorTemplate){
		set(PAGINATOR_TEMPLATE, paginatorTemplate);
		return this;
	}
	
	public Object getCurrentPageReportTemplate(){
		return get(CURRENT_PAGE_REPORT_TEMPLATE);
	}
	
	public Properties setCurrentPageReportTemplate(Object currentPageReportTemplate){
		set(CURRENT_PAGE_REPORT_TEMPLATE, currentPageReportTemplate);
		return this;
	}
	
	public Object getTableStyleClass(){
		return get(TABLE_STYLE_CLASS);
	}
	
	public Properties setTableStyleClass(Object tableStyleClass){
		set(TABLE_STYLE_CLASS, tableStyleClass);
		return this;
	}
	
	public Object getRowStyleClass(){
		return get(ROW_STYLE_CLASS);
	}
	
	public Properties setRowStyleClass(Object rowStyleClass){
		set(ROW_STYLE_CLASS, rowStyleClass);
		return this;
	}
	
	public Object getPaginatorAlwaysVisible(){
		return get(PAGINATOR_ALWAYS_VISIBLE);
	}
	
	public Properties setPaginatorAlwaysVisible(Object paginatorAlwaysVisible){
		set(PAGINATOR_ALWAYS_VISIBLE, paginatorAlwaysVisible);
		return this;
	}
	
	public Object getPageLinks(){
		return get(PAGE_LINKS);
	}
	
	public Properties setPageLinks(Object pageLinks){
		set(PAGE_LINKS, pageLinks);
		return this;
	}
	
	public Object getRows(){
		return get(ROWS);
	}
	
	public Properties setRows(Object rows){
		set(ROWS, rows);
		return this;
	}
	
	public Object getGlobal(){
		return get(GLOBAL);
	}
	
	public Properties setGlobal(Object global){
		set(GLOBAL, global);
		return this;
	}
	
	public Object getReadableOnly(){
		return get(READABLE_ONLY);
	}
	
	public Properties setReadableOnly(Object readableOnly){
		set(READABLE_ONLY, readableOnly);
		return this;
	}
	
	public Object getReadableOnlyValue(){
		return get(READABLE_ONLY_VALUE);
	}
	
	public Properties setReadableOnlyValue(Object readableOnlyValue){
		set(READABLE_ONLY_VALUE, readableOnlyValue);
		return this;
	}
	
	public Object getKeepShowingAsReadableOnly(){
		return get(KEEP_SHOWING_AS_READABLE_ONLY);
	}
	
	public Properties setKeepShowingAsReadableOnly(Object keepShowingAsReadableOnly){
		set(KEEP_SHOWING_AS_READABLE_ONLY, keepShowingAsReadableOnly);
		return this;
	}
	
	public Object getName(){
		return get(NAME);
	}
	
	public Properties setName(Object name){
		set(NAME, name);
		return this;
	}
	
	public Object getInputValueIsNotRequired(){
		return get(INPUT_VALUE_IS_NOT_REQUIRED);
	}
	
	public Properties setInputValueIsNotRequired(Object inputValueIsNotRequired){
		set(INPUT_VALUE_IS_NOT_REQUIRED, inputValueIsNotRequired);
		return this;
	}
	
	public Object getConfirmable(){
		return get(CONFIRMABLE);
	}
	
	public Properties setConfirmable(Object confirmable){
		set(CONFIRMABLE, confirmable);
		return this;
	}
	
	public Object getOptions(){
		return get(OPTIONS);
	}
	
	public Properties setOptions(Object options){
		set(OPTIONS, options);
		return this;
	}
	
	public Object getFullPage(){
		return get(FULL_PAGE);
	}
	
	public Properties setFullPage(Object fullPage){
		set(FULL_PAGE, fullPage);
		return this;
	}
	
	public Object getPosition() {
		return get(POSITION);
	}

	public Properties setPosition(Object value) {
		set(POSITION, value);
		return this;
	}
	
	public Object getStyleClassContent() {
		return get(STYLE_CLASS_CONTENT);
	}

	public Properties setStyleClassContent(Object value) {
		set(STYLE_CLASS_CONTENT, value);
		return this;
	}
	
	public Object getEnctype() {
		return get(ENCTYPE);
	}

	public Properties setEnctype(Object value) {
		set(ENCTYPE, value);
		return this;
	}
	
	public Object getResizable() {
		return get(RESIZABLE);
	}

	public Properties setResizable(Object value) {
		set(RESIZABLE, value);
		return this;
	}
	
	public Object getClosable() {
		return get(CLOSABLE);
	}

	public Properties setClosable(Object value) {
		set(CLOSABLE, value);
		return this;
	}
	
	public Object getInclude() {
		return get(INCLUDE);
	}

	public Properties setInclude(Object value) {
		set(INCLUDE, value);
		return this;
	}
	
	public static final String NAME = "NAME";
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
	public static final String READABLE_ONLY = "READ_ONLY";
	public static final String READABLE_ONLY_VALUE = "READ_ONLY_VALUE";
	public static final String UPDATE = "UPDATE";
	public static final String WIDGET_VAR = "WIDGET_VAR";
	public static final String FILTER = "FILTER";
	public static final String LABEL = "LABEL";
	public static final String DESCRIPTION = "DESCRIPTION";
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
	public static final String KEEP_SHOWING_AS_READABLE_ONLY = "KEEP_SHOWING_AS_READABLE_ONLY";
	public static final String INPUT_VALUE_IS_NOT_REQUIRED = "INPUT_VALUE_IS_NOT_REQUIRED";
	public static final String CONFIRMABLE = "CONFIRMABLE";
	public static final String OPTIONS = "OPTIONS";
	public static final String FULL_PAGE = "FULL_PAGE";
	public static final String STYLE_CLASS_CONTENT = "STYLE_CLASS_CONTENT";
	public static final String POSITION = "POSITION";
	public static final String ENCTYPE = "ENCTYPE";
	public static final String RESIZABLE = "RESIZABLE";
	public static final String CLOSABLE = "CLOSABLE";
	public static final String INCLUDE = "INCLUDE";
}
