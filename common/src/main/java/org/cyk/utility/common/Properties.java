package org.cyk.utility.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
		
	public static void setDefaultValues(Class<?> aClass,Properties properties){
		Map<Object,Object> map = DEFAULT_VALUES.get(aClass);
		if(map != null){
			for(Entry<Object, Object> entry : map.entrySet())
				properties.set(entry.getKey(), entry.getValue());
		}
	}
	
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
	
	public Object getTemplate() {
		return get(TEMPLATE);
	}

	public Properties setTemplate(Object value) {
		set(TEMPLATE, value);
		return this;
	}
	
	public Object getContracts() {
		return get(CONTRACTS);
	}

	public Properties setContracts(Object value) {
		set(CONTRACTS, value);
		return this;
	}
	
	public Object getNorth() {
		return get(NORTH);
	}

	public Properties setNorth(Object value) {
		set(NORTH, value);
		return this;
	}
	
	public Object getEast() {
		return get(EAST);
	}

	public Properties setEast(Object value) {
		set(EAST, value);
		return this;
	}
	
	public Object getSouth() {
		return get(SOUTH);
	}

	public Properties setSouth(Object value) {
		set(SOUTH, value);
		return this;
	}
	
	public Object getWest() {
		return get(WEST);
	}

	public Properties setWest(Object value) {
		set(WEST, value);
		return this;
	}
	
	public Object getCenter() {
		return get(CENTER);
	}

	public Properties setCenter(Object value) {
		set(CENTER, value);
		return this;
	}
	
	public Object getLayout() {
		return get(LAYOUT);
	}

	public Properties setLayout(Object value) {
		set(LAYOUT, value);
		return this;
	}
	
	public Object getHeader() {
		return get(HEADER);
	}

	public Properties setHeader(Object value) {
		set(HEADER, value);
		return this;
	}
	
	public Object getBody() {
		return get(BODY);
	}

	public Properties setBody(Object value) {
		set(BODY, value);
		return this;
	}
	
	public Object getFooter() {
		return get(FOOTER);
	}

	public Properties setFooter(Object value) {
		set(FOOTER, value);
		return this;
	}
	
	public Object getFor() {
		return get(FOR);
	}

	public Properties setFor(Object value) {
		set(FOR, value);
		return this;
	}
	
	public Object getValidator() {
		return get(VALIDATOR);
	}

	public Properties setValidator(Object value) {
		set(VALIDATOR, value);
		return this;
	}
	
	public Object getConverter() {
		return get(CONVERTER);
	}

	public Properties setConverter(Object value) {
		set(CONVERTER, value);
		return this;
	}
	
	public Object getMessage() {
		return get(MESSAGE);
	}

	public Properties setMessage(Object value) {
		set(MESSAGE, value);
		return this;
	}
	
	public Object getAppendTo() {
		return get(APPEND_TO);
	}

	public Properties setAppendTo(Object value) {
		set(APPEND_TO, value);
		return this;
	}
	
	public Object getCloseOnEscape() {
		return get(CLOSE_ON_ESCAPE);
	}

	public Properties setCloseOnEscape(Object value) {
		set(CLOSE_ON_ESCAPE, value);
		return this;
	}
	
	public Object getResponsive() {
		return get(RESPONSIVE);
	}

	public Properties setResponsive(Object value) {
		set(RESPONSIVE, value);
		return this;
	}
	
	public Object getSeverity() {
		return get(SEVERITY);
	}

	public Properties setSeverity(Object value) {
		set(SEVERITY, value);
		return this;
	}
	
	public Object getShowEffect() {
		return get(SHOW_EFFECT);
	}

	public Properties setShowEffect(Object value) {
		set(SHOW_EFFECT, value);
		return this;
	}
	
	public Object getVisible() {
		return get(VISIBLE);
	}

	public Properties setVisible(Object value) {
		set(VISIBLE, value);
		return this;
	}
	
	public Object getHeight() {
		return get(HEIGHT);
	}

	public Properties setHeight(Object value) {
		set(HEIGHT, value);
		return this;
	}
	
	public Object getWidth() {
		return get(WIDTH);
	}

	public Properties setWidth(Object value) {
		set(WIDTH, value);
		return this;
	}
	
	public Object getHideEffect() {
		return get(HIDE_EFFECT);
	}

	public Properties setHideEffect(Object value) {
		set(HIDE_EFFECT, value);
		return this;
	}
	
	public Object getClose() {
		return get(CLOSE);
	}

	public Properties setClose(Object value) {
		set(CLOSE, value);
		return this;
	}
	
	public Object getDraggable() {
		return get(DRAGGABLE);
	}

	public Properties setDraggable(Object value) {
		set(DRAGGABLE, value);
		return this;
	}
	
	public Object getDynamic() {
		return get(DYNAMIC);
	}

	public Properties setDynamic(Object value) {
		set(DYNAMIC, value);
		return this;
	}
	
	public Object getFitViewport() {
		return get(FIT_VIEWPORT);
	}

	public Properties setFitViewport(Object value) {
		set(FIT_VIEWPORT, value);
		return this;
	}
	
	public Object getFocus() {
		return get(FOCUS);
	}

	public Properties setFocus(Object value) {
		set(FOCUS, value);
		return this;
	}
	
	public Object getMaximizable() {
		return get(MAXIMIZABLE);
	}

	public Properties setMaximizable(Object value) {
		set(MAXIMIZABLE, value);
		return this;
	}
	
	public Object getMinHeight() {
		return get(MIN_HEIGHT);
	}

	public Properties setMinHeight(Object value) {
		set(MIN_HEIGHT, value);
		return this;
	}
	
	public Object getMinWidth() {
		return get(MIN_WIDTH);
	}

	public Properties setMinWidth(Object value) {
		set(MIN_WIDTH, value);
		return this;
	}
	
	public Object getModal() {
		return get(MODAL);
	}

	public Properties setModal(Object value) {
		set(MODAL, value);
		return this;
	}
	
	public Object getOnHide() {
		return get(ON_HIDE);
	}

	public Properties setOnHide(Object value) {
		set(ON_HIDE, value);
		return this;
	}
	
	public Object getOnShow() {
		return get(ON_SHOW);
	}

	public Properties setOnShow(Object value) {
		set(ON_SHOW, value);
		return this;
	}
	
	public Object getPositionType() {
		return get(POSITION_TYPE);
	}

	public Properties setPositionType(Object value) {
		set(POSITION_TYPE, value);
		return this;
	}
	
	public Object getShowHeader() {
		return get(SHOW_HEADER);
	}

	public Properties setShowHeader(Object value) {
		set(SHOW_HEADER, value);
		return this;
	}
	
	public Object getMinimizable() {
		return get(MINIMIZABLE);
	}

	public Properties setMinimizable(Object value) {
		set(MINIMIZABLE, value);
		return this;
	}
	
	public static final String DRAGGABLE = "DRAGGABLE";
	public static final String DYNAMIC = "DYNAMIC";
	public static final String FIT_VIEWPORT = "FIT_VIEWPORT";
	public static final String FOCUS = "FOCUS";
	public static final String MAXIMIZABLE = "MAXIMIZABLE";
	public static final String MIN_HEIGHT = "MIN_HEIGHT";
	public static final String MINIMIZABLE = "MINIMIZABLE";
	public static final String MIN_WIDTH = "MIN_WIDTH";
	public static final String MODAL = "MODAL";
	public static final String ON_HIDE = "ON_HIDE";
	public static final String ON_SHOW = "ON_SHOW";
	public static final String POSITION_TYPE = "POSITION_TYPE";
	public static final String SHOW_HEADER = "SHOW_HEADER";
	public static final String RESPONSIVE = "RESPONSIVE";
	public static final String SEVERITY = "SEVERITY";
	public static final String SHOW_EFFECT = "SHOW_EFFECT";
	public static final String VISIBLE = "VISIBLE";
	public static final String HEIGHT = "HEIGHT";
	public static final String WIDTH = "WIDTH";
	public static final String APPEND_TO = "APPEND_TO";
	public static final String CLOSE = "CLOSE";
	public static final String CLOSE_ON_ESCAPE = "CLOSE_ON_ESCAPE";
	public static final String NAME = "NAME";
	public static final String HIDE_EFFECT = "HIDE_EFFECT";
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
	public static final String TEMPLATE = "TEMPLATE";
	public static final String CONTRACTS = "CONTRACTS";
	public static final String NORTH = "NORTH";
	public static final String EAST = "EAST";
	public static final String SOUTH = "SOUTH";
	public static final String WEST = "WEST";
	public static final String CENTER = "CENTER";
	public static final String LAYOUT = "LAYOUT";
	public static final String HEADER = "HEADER";
	public static final String BODY = "BODY";
	public static final String FOOTER = "FOOTER";
	public static final String FOR = "FOR";
	public static final String VALIDATOR = "VALIDATOR";
	public static final String CONVERTER = "CONVERTER";
	public static final String MESSAGE = "MESSAGE";
	
	/**/
	
	private static final Map<Class<?>,Map<Object,Object>> DEFAULT_VALUES = new HashMap<Class<?>,Map<Object,Object>>();
	
	public static void setDefaultValue(Class<?> aClass,Object name,Object value){
		if(aClass!=null && name!=null){
			Map<Object,Object> map = DEFAULT_VALUES.get(aClass);
			if(map == null)
				DEFAULT_VALUES.put(aClass, map = new HashMap<Object, Object>());
			map.put(name, value);	
		}
	}
	
	public static Object getDefaultValue(Class<?> aClass,Object name){
		Object value = null;
		if(aClass!=null && name!=null){
			Map<Object,Object> map = DEFAULT_VALUES.get(aClass);
			if(map != null)
				value = map.get(name);	
		}
		return value;
	}
	
	@Override
	public String toString() {
		return map.toString();
	}
}
