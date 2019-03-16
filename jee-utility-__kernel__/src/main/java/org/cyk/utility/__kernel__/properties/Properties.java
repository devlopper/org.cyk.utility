package org.cyk.utility.__kernel__.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.KernelHelper;

public class Properties implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * layer , class group , class name , property name
	 */
	public static final String STRING_VALUE_IDENTIFIER_FORMAT  = "%s.%s.%s.%s";
	private static final String STRING_VALUE_SEPARATOR  = " ";
	
	private java.util.Map<Object,Object> map;
	private Object stringValueSeparator = STRING_VALUE_SEPARATOR;
	private Map<Object,Getter> getterMap;
	//private Object doubleStringValueSeparator = StringUtils.repeat(stringValueSeparator.toString(), 2);
	
	public static Properties instanciate(Map<Object,Object> map){
		Properties properties = null; 
		if(map != null && !map.isEmpty()){
			properties = new Properties();
			properties.map = new HashMap<>();
			properties.map.putAll(map);
		}
		return properties;
	}
	
	public static Properties instanciate(Properties properties){
		return instanciate(properties.map);
	}
	
	public java.util.Map<Object,Object> __getMap__(){
		return map;
	}
	
	/**/
		
	public static void setDefaultValues(Class<?> aClass,Properties properties){
		//Inherited first
		for(Entry<Class<?>,Map<Object,Object>> entry : DEFAULT_VALUES.entrySet()){
			if(!entry.getClass().equals(aClass) && DependencyInjection.inject(KernelHelper.class).isInstanceOf(entry.getKey(), aClass) && isDefaultValuesInherited(entry.getKey())){
				setDefaultValues(aClass, properties, DEFAULT_VALUES.get(entry.getKey()));
			}
		}
		//own second
		setDefaultValues(aClass, properties, DEFAULT_VALUES.get(aClass));
	}
	
	private static void setDefaultValues(Class<?> aClass,Properties properties,Map<Object,Object> map){
		 if(map != null){
			for(Entry<Object, Object> entry : map.entrySet())
				properties.set(entry.getKey(), entry.getValue());
		}
	}
	
	public static Properties setFromPath(Properties properties,Object[] keys,Object value){
		if(properties == null)
			properties = new Properties();
		properties.setFromPath(keys, value);
		return properties;
	}
	
	public static Object getFromPath(Properties properties,Object...keys){
		if(properties == null)
			return null;
		return properties.getFromPath(keys);
	}
	
	public static <T> T getFromPathInstanciateIfNull(Properties properties,Class<T> aClass,Object...keys){
		@SuppressWarnings("unchecked")
		T result = (T) getFromPath(properties, keys);
		if(result == null)
			result = DependencyInjection.inject(KernelHelper.class).instanciate(aClass);
		return result;
	}
	
	/**/
	
	public Boolean has(Object property){
		return map!=null && map.containsKey(property);
	}
	
	/* getters */
	
	public Properties setGetter(Object key,Getter getter){
		if(getterMap == null)
			getterMap = new HashMap<Object, Properties.Getter>();
		getterMap.put(key, getter);
		return this;
	}
	
	public Getter getGetter(Object key){
		return getterMap == null ? null : getterMap.get(key);
	}
	
	/* set */
	
	protected Map<Object,Object> __instanciateMap__(){
		return new java.util.LinkedHashMap<Object, Object>();
	}
	
	public Properties set(Object key,Object value){
		if(this.map == null)
			this.map = __instanciateMap__();
		this.map.put(key, value);
		return this;
	}
	
	public Properties setIfNull(Object key,Object value){
		if(get(key) == null)
			set(key, value);
		return this;
	}
	
	public Properties setManyIfNull(Object[] keys,Object value){
		if(keys!=null && keys.length>0) {
			for(Object index : keys)
				setIfNull(index, value);
		}
		return this;
	}
	
	public Properties setFromPath(Object[] keys,Object value){
		if(keys == null || keys.length == 0){
			//TODO log warning
		}else{
			if(keys.length == 1){
				set(keys[0], value);
			}else {
				Object properties = get(keys[0]);
				if(properties == null){
					set(keys[0], properties = new Properties());
				}
				if(properties instanceof Properties)
					((Properties)properties).setFromPath(ArrayUtils.subarray(keys, 1, keys.length),value);
				else {
					//TODO log warning
				}
			}
		}
		return this;
	}
	
	/* get */
	
	public <T> T getInstanciateIfNull(Object key,Class<T> aClass){
		T value = get(aClass, key, null);
		if(value == null){
			value = DependencyInjection.inject(KernelHelper.class).instanciateOne(aClass);
			set(key, value);
		}
		return value;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> aClass,Object key,T nullValue){
		Getter getter = getGetter(key);
		if(getter!=null)
			return (T) getter.execute(this, key,this.map.get(key),nullValue);
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
	
	public Object getRecursively(Object key){
		Object value = get(key);
		if(value instanceof Properties)
			return ((Properties)value).get(key);
		return value;
	}
	
	public Object getFromPath(Object...keys){
		if(keys == null || keys.length == 0)
			return null;
		Object value = get(keys[0]);
		if(keys.length == 1)
			return value;
		if(value instanceof Properties)
			return ((Properties)value).getFromPath(ArrayUtils.subarray(keys, 1, keys.length));	
		//TODO log warning
		return null;
	}
	
	public Collection<Object> getWhereKeyIsInstanceOf(Class<?> clazz) {
		Collection<Object> collection = null;
		if(map != null && clazz!=null) {
			for(Map.Entry<Object, Object> entry : map.entrySet()) {
				if(entry.getKey() != null) {
					if(clazz.isAssignableFrom(entry.getKey().getClass())){
						if(collection == null)
							collection = new ArrayList<Object>();
						collection.add(entry.getValue());
					}
				}
			}
		}
		return collection;
	}
	
	/* add */
	
	@SuppressWarnings("unchecked")
	private Properties __add__(Object key,Object value,Class<?> valueClass,Boolean add){
		KernelHelper kernelHelper = DependencyInjection.inject(KernelHelper.class);
		Object keyValue = get(key);
		if(valueClass == null && keyValue!=null)
			valueClass = keyValue.getClass();
		if(kernelHelper.isInstanceOf(valueClass,Collection.class)){
			if(keyValue==null){
				if(kernelHelper.isInstanceOf(valueClass,List.class))
					keyValue = new ArrayList<>();
				else if(kernelHelper.isInstanceOf(valueClass,Set.class))
					keyValue = new LinkedHashSet<>();
				else
					keyValue = new ArrayList<>();
			}
			if(Boolean.TRUE.equals(add))
				((Collection<Object>)keyValue).add(value);
			else
				((Collection<Object>)keyValue).remove(value);
		}else if(String.class.equals(valueClass)){
			if(Boolean.TRUE.equals(add))
				keyValue = (String)(keyValue == null ? kernelHelper.getEmptyString() : keyValue) + value;
			else
				keyValue = StringUtils.replace((String)keyValue, (String) value, kernelHelper.getEmptyString());
		}else if(kernelHelper.isNumber(valueClass)){
			if(Boolean.TRUE.equals(add))
				keyValue = kernelHelper.addNumbers((Number)keyValue , (Number)value);
			else
				keyValue = kernelHelper.subtractNumbers((Number)keyValue , (Number)value);
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
		KernelHelper kernelHelper = DependencyInjection.inject(KernelHelper.class);
		if(strings!=null && strings.length>0){
			for(String string : strings)
				if(StringUtils.isNotBlank(string)) {
					add(key, (get(key) == null ? kernelHelper.getEmptyString() : separator ) + string, String.class);
				}
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
		if(strings!=null && strings.length>0){
			for(String string : strings)
				if(StringUtils.isNotBlank(string)){
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
	
	/**/
	
	public Properties copyTo(Properties destination,Object...keys){
		if(destination!=null && keys!=null && keys.length>0)
			DependencyInjection.inject(KernelHelper.class).copyMap(map, destination.map, keys);
		return this;
	}
	
	public Properties copyFrom(Properties source,Object...keys){
		if(source!=null && source.map!=null && !source.map.isEmpty() && keys!=null && keys.length>0) {
			if(map == null)
				map = __instanciateMap__();
			DependencyInjection.inject(KernelHelper.class).copyMap(source.map, map, keys);
		}
		//TODO check to remove key from keys having null value
		return this;
	}
	
	public Properties copyNonNullKeysFrom(Properties source){
		if(source!=null && source.map != null && !source.map.isEmpty()) {
			if(map == null)
				map = __instanciateMap__();
			DependencyInjection.inject(KernelHelper.class).copyMapNonNullKeys(source.map, map);
		}
		return this;
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
	
	public Object getIconPos(){
		return get(ICON_POS);
	}
	
	public Properties setIconPos(Object iconPos){
		set(ICON_POS, iconPos);
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
	
	public Object getMaxHeight() {
		return get(MAX_HEIGHT);
	}

	public Properties setMaxHeight(Object value) {
		set(MAX_HEIGHT, value);
		return this;
	}
	
	public Object getMinWidth() {
		return get(MIN_WIDTH);
	}

	public Properties setMinWidth(Object value) {
		set(MIN_WIDTH, value);
		return this;
	}
	
	public Object getMaxWidth() {
		return get(MAX_WIDTH);
	}

	public Properties setMaxWidth(Object value) {
		set(MAX_WIDTH, value);
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
	
	public Object getAutoUpdate() {
		return get(AUTO_UPDATE);
	}

	public Properties setAutoUpdate(Object value) {
		set(AUTO_UPDATE, value);
		return this;
	}
	
	public Object getGlobalOnly() {
		return get(GLOBAL_ONLY);
	}

	public Properties setGlobalOnly(Object value) {
		set(GLOBAL_ONLY, value);
		return this;
	}
	
	public Object getRedisplay() {
		return get(REDISPLAY);
	}

	public Properties setRedisplay(Object value) {
		set(REDISPLAY, value);
		return this;
	}
	
	public Object getShowDetail() {
		return get(SHOW_DETAIL);
	}

	public Properties setShowDetail(Object value) {
		set(SHOW_DETAIL, value);
		return this;
	}
	
	public Object getShowIcon() {
		return get(SHOW_ICON);
	}

	public Properties setShowIcon(Object value) {
		set(SHOW_ICON, value);
		return this;
	}
	
	public Object getShowSummary() {
		return get(SHOW_SUMMARY);
	}

	public Properties setShowSummary(Object value) {
		set(SHOW_SUMMARY, value);
		return this;
	}
	
	public Object getOnError() {
		return get(ON_ERROR);
	}

	public Properties setOnError(Object value) {
		set(ON_ERROR, value);
		return this;
	}
	
	public Object getOnStart() {
		return get(ON_START);
	}

	public Properties setOnStart(Object value) {
		set(ON_START, value);
		return this;
	}
	
	public Object getOnSuccess() {
		return get(ON_SUCCESS);
	}

	public Properties setOnSuccess(Object value) {
		set(ON_SUCCESS, value);
		return this;
	}
	
	public Object getRenderType() {
		return get(RENDER_TYPE);
	}

	public Properties setRenderType(Object value) {
		set(RENDER_TYPE, value);
		return this;
	}
	
	public Object getBuiltTarget() {
		return get(BUILT_TARGET);
	}

	public Properties setBuiltTarget(Object value) {
		set(BUILT_TARGET, value);
		return this;
	}
	
	public Object getIdentifierAsStyleClass() {
		return get(IDENTIFIER_AS_STYLE_CLASS);
	}

	public Properties setIdentifierAsStyleClass(Object value) {
		set(IDENTIFIER_AS_STYLE_CLASS, value);
		return this;
	}
	
	public Object getStructureTemplate() {
		return get(STRUCTURE_TEMPLATE);
	}

	public Properties setStructureTemplate(Object value) {
		set(STRUCTURE_TEMPLATE, value);
		return this;
	}
	
	public Object getPartialSubmit() {
		return get(PARTIAL_SUBMIT);
	}

	public Properties setPartialSubmit(Object value) {
		set(PARTIAL_SUBMIT, value);
		return this;
	}
	
	public Object getOnDoubleClick() {
		return get(ON_DOUBLE_CLICK);
	}

	public Properties setOnDoubleClick(Object value) {
		set(ON_DOUBLE_CLICK, value);
		return this;
	}
	
	public Object getOnMouseOver() {
		return get(ON_MOUSE_OVER);
	}

	public Properties setOnMouseOver(Object value) {
		set(ON_MOUSE_OVER, value);
		return this;
	}
	
	public Object getOnMouseUp() {
		return get(ON_MOUSE_UP);
	}

	public Properties setOnMouseUp(Object value) {
		set(ON_MOUSE_UP, value);
		return this;
	}
	
	public Object getOnMouseDown() {
		return get(ON_MOUSE_DOWN);
	}

	public Properties setOnMouseDown(Object value) {
		set(ON_MOUSE_DOWN, value);
		return this;
	}
	
	public Object getOnMouseOut() {
		return get(ON_MOUSE_OUT);
	}

	public Properties setOnMouseOut(Object value) {
		set(ON_MOUSE_OUT, value);
		return this;
	}
	
	public Object getOnMouseMove() {
		return get(ON_MOUSE_MOVE);
	}

	public Properties setOnMouseMove(Object value) {
		set(ON_MOUSE_MOVE, value);
		return this;
	}
	
	public Object getOnKeyDown() {
		return get(ON_KEY_DOWN);
	}

	public Properties setOnKeyDown(Object value) {
		set(ON_KEY_DOWN, value);
		return this;
	}
	
	public Object getOnKeyUp() {
		return get(ON_KEY_UP);
	}

	public Properties setOnKeyUp(Object value) {
		set(ON_KEY_UP, value);
		return this;
	}
	
	public Object getOnKeyPress() {
		return get(ON_KEY_PRESS);
	}

	public Properties setOnKeyPress(Object value) {
		set(ON_KEY_PRESS, value);
		return this;
	}
	
	public Object getOnFocus() {
		return get(ON_FOCUS);
	}

	public Properties setOnFocus(Object value) {
		set(ON_FOCUS, value);
		return this;
	}
	
	public Object getOnSelect() {
		return get(ON_SELECT);
	}

	public Properties setOnSelect(Object value) {
		set(ON_SELECT, value);
		return this;
	}
	
	public Object getOnBlur() {
		return get(ON_BLUR);
	}

	public Properties setOnBlur(Object value) {
		set(ON_BLUR, value);
		return this;
	}
	
	public Object getInteractivityBlocker() {
		return get(INTERACTIVITY_BLOCKER);
	}

	public Properties setInteractivityBlocker(Object value) {
		set(INTERACTIVITY_BLOCKER, value);
		return this;
	}
	
	public Object getAutoShow() {
		return get(AUTO_SHOW);
	}

	public Properties setAutoShow(Object value) {
		set(AUTO_SHOW, value);
		return this;
	}
	
	public Object getCenterX() {
		return get(CENTER_X);
	}

	public Properties setCenterX(Object value) {
		set(CENTER_X, value);
		return this;
	}
	
	public Object getCenterY() {
		return get(CENTER_Y);
	}

	public Properties setCenterY(Object value) {
		set(CENTER_Y, value);
		return this;
	}
	
	public Object getContent() {
		return get(CONTENT);
	}

	public Properties setContent(Object value) {
		set(CONTENT, value);
		return this;
	}
	
	public Object getCss() {
		return get(CSS);
	}

	public Properties setCss(Object value) {
		set(CSS, value);
		return this;
	}
	
	public Object getCssOverlay() {
		return get(CSS_OVERLAY);
	}

	public Properties setCssOverlay(Object value) {
		set(CSS_OVERLAY, value);
		return this;
	}
	
	public Object getEvent() {
		return get(EVENT);
	}

	public Properties setEvent(Object value) {
		set(EVENT, value);
		return this;
	}
	
	public Object getSource() {
		return get(SOURCE);
	}

	public Properties setSource(Object value) {
		set(SOURCE, value);
		return this;
	}
	
	public Object getTarget() {
		return get(TARGET);
	}

	public Properties setTarget(Object value) {
		set(TARGET, value);
		return this;
	}
	
	public Object getTimeOut() {
		return get(TIME_OUT);
	}

	public Properties setTimeOut(Object value) {
		set(TIME_OUT, value);
		return this;
	}
	
	public Object getAsync() {
		return get(ASYNC);
	}

	public Properties setAsync(Object value) {
		set(ASYNC, value);
		return this;
	}
	
	public Object getWatermark() {
		return get(WATERMARK);
	}

	public Properties setWatermark(Object value) {
		set(WATERMARK, value);
		return this;
	}
	
	public Object getOnLabel() {
		return get(ON_LABEL);
	}

	public Properties setOnLabel(Object value) {
		set(ON_LABEL, value);
		return this;
	}
	
	public Object getOnIcon() {
		return get(ON_ICON);
	}

	public Properties setOnIcon(Object value) {
		set(ON_ICON, value);
		return this;
	}
	
	public Object getOffLabel() {
		return get(OFF_LABEL);
	}

	public Properties setOffLabel(Object value) {
		set(OFF_LABEL, value);
		return this;
	}
	
	public Object getOffIcon() {
		return get(OFF_ICON);
	}

	public Properties setOffIcon(Object value) {
		set(OFF_ICON, value);
		return this;
	}
	
	public Object getDualListModel() {
		return get(DUAL_LIST_MODEL);
	}

	public Properties setDualListModel(Object value) {
		set(DUAL_LIST_MODEL, value);
		return this;
	}
	
	public Object getSelectItemWrappable() {
		return get(SELECT_ITEM_WRAPPABLE);
	}

	public Properties setSelectItemWrappable(Object value) {
		set(SELECT_ITEM_WRAPPABLE, value);
		return this;
	}
	
	public Object getVar() {
		return get(VAR);
	}

	public Properties setVar(Object value) {
		set(VAR, value);
		return this;
	}
	
	public Object getItemValue() {
		return get(ITEM_VALUE);
	}

	public Properties setItemValue(Object value) {
		set(ITEM_VALUE, value);
		return this;
	}
	
	public Object getItemLabel() {
		return get(ITEM_LABEL);
	}

	public Properties setItemLabel(Object value) {
		set(ITEM_LABEL, value);
		return this;
	}
	
	public Object getItemDescription() {
		return get(ITEM_DESCRIPTION);
	}

	public Properties setItemDescription(Object value) {
		set(ITEM_DESCRIPTION, value);
		return this;
	}
	
	public Object getItemDisabled() {
		return get(ITEM_DISABLED);
	}

	public Properties setItemDisabled(Object value) {
		set(ITEM_DISABLED, value);
		return this;
	}
	
	public Object getItemLabelEscaped() {
		return get(ITEM_LABEL_ESCAPED);
	}

	public Properties setItemLabelEscaped(Object value) {
		set(ITEM_LABEL_ESCAPED, value);
		return this;
	}
	
	public Object getSelectItems() {
		return get(SELECT_ITEMS);
	}

	public Properties setSelectItems(Object value) {
		set(SELECT_ITEMS, value);
		return this;
	}
	
	public Object getPreviewable() {
		return get(PREVIEWABLE);
	}

	public Properties setPreviewable(Object value) {
		set(PREVIEWABLE, value);
		return this;
	}
	
	public Object getClearable() {
		return get(CLEARABLE);
	}

	public Properties setClearable(Object value) {
		set(CLEARABLE, value);
		return this;
	}
	
	public Object getMinimumSize() {
		return get(MINIMUM_SIZE);
	}

	public Properties setMinimumSize(Object value) {
		set(MINIMUM_SIZE, value);
		return this;
	}
	
	public Object getMaximumSize() {
		return get(MAXIMUM_SIZE);
	}

	public Properties setMaximumSize(Object value) {
		set(MAXIMUM_SIZE, value);
		return this;
	}
	
	public Object getExtensions() {
		return get(EXTENSIONS);
	}

	public Properties setExtensions(Object value) {
		set(EXTENSIONS, value);
		return this;
	}
	
	public Object getAllowTypes() {
		return get(ALLOW_TYPES);
	}

	public Properties setAllowTypes(Object value) {
		set(ALLOW_TYPES, value);
		return this;
	}
	
	public Object getExtensionsAsString() {
		return get(EXTENSIONS_AS_STRING);
	}

	public Properties setExtensionsAsString(Object value) {
		set(EXTENSIONS_AS_STRING, value);
		return this;
	}
	
	public Object getMode() {
		return get(MODE);
	}

	public Properties setMode(Object value) {
		set(MODE, value);
		return this;
	}
	
	public Object getPreviewWidth() {
		return get(PREVIEW_WIDTH);
	}

	public Properties setPreviewWidth(Object value) {
		set(PREVIEW_WIDTH, value);
		return this;
	}
	
	public Object getPreviewHeight() {
		return get(PREVIEW_HEIGHT);
	}

	public Properties setPreviewHeight(Object value) {
		set(PREVIEW_HEIGHT, value);
		return this;
	}
	
	public Object getClearCommand() {
		return get(CLEAR_COMMAND);
	}

	public Properties setClearCommand(Object value) {
		set(CLEAR_COMMAND, value);
		return this;
	}
	
	public Object getSkinSimple() {
		return get(SKIN_SIMPLE);
	}

	public Properties setSkinSimple(Object value) {
		set(SKIN_SIMPLE, value);
		return this;
	}
	
	public Object getMultiple() {
		return get(MULTIPLE);
	}

	public Properties setMultiple(Object value) {
		set(MULTIPLE, value);
		return this;
	}
	
	public Object getImageComponent() {
		return get(IMAGE_COMPONENT);
	}

	public Properties setImageComponent(Object value) {
		set(IMAGE_COMPONENT, value);
		return this;
	}
	
	public Object getPreviewImageComponent() {
		return get(PREVIEW_IMAGE_COMPONENT);
	}

	public Properties setPreviewImageComponent(Object value) {
		set(PREVIEW_IMAGE_COMPONENT, value);
		return this;
	}
	
	public Object getSrc() {
		return get(SRC);
	}

	public Properties setSrc(Object value) {
		set(SRC, value);
		return this;
	}
	
	public Object getUrl() {
		return get(URL);
	}

	public Properties setUrl(Object value) {
		set(URL, value);
		return this;
	}
	
	public Object getLibrary() {
		return get(LIBRARY);
	}

	public Properties setLibrary(Object value) {
		set(LIBRARY, value);
		return this;
	}
	
	public Object getStream() {
		return get(STREAM);
	}

	public Properties setStream(Object value) {
		set(STREAM, value);
		return this;
	}
	
	public Object getCache() {
		return get(CACHE);
	}

	public Properties setCache(Object value) {
		set(CACHE, value);
		return this;
	}
	
	public Object getIsMap() {
		return get(IS_MAP);
	}

	public Properties setIsMap(Object value) {
		set(IS_MAP, value);
		return this;
	}
	
	public Object getUseMap() {
		return get(USE_MAP);
	}

	public Properties setUseMap(Object value) {
		set(USE_MAP, value);
		return this;
	}
	
	public Object getDir() {
		return get(DIR);
	}

	public Properties setDir(Object value) {
		set(DIR, value);
		return this;
	}
	
	public Object getAlt() {
		return get(ALT);
	}

	public Properties setAlt(Object value) {
		set(ALT, value);
		return this;
	}
	
	public Object getStreamedContent() {
		return get(STREAMED_CONTENT);
	}

	public Properties setStreamedContent(Object value) {
		set(STREAMED_CONTENT, value);
		return this;
	}
	
	public Object getRemoveCommand() {
		return get(REMOVE_COMMAND);
	}

	public Properties setRemoveCommand(Object value) {
		set(REMOVE_COMMAND, value);
		return this;
	}
	
	public Object getMainMenu() {
		return get(MAIN_MENU);
	}

	public Properties setMainMenu(Object value) {
		set(MAIN_MENU, value);
		return this;
	}
	
	public Object getContextMenu() {
		return get(CONTEXT_MENU);
	}

	public Properties setContextMenu(Object value) {
		set(CONTEXT_MENU, value);
		return this;
	}
	
	public Object getToolBarMenu() {
		return get(TOOL_BAR_MENU);
	}

	public Properties setToolBarMenu(Object value) {
		set(TOOL_BAR_MENU, value);
		return this;
	}
	
	public Object getAutoDisplay() {
		return get(AUTO_DISPLAY);
	}

	public Properties setAutoDisplay(Object value) {
		set(AUTO_DISPLAY, value);
		return this;
	}
	
	public Object getModel() {
		return get(MODEL);
	}

	public Properties setModel(Object value) {
		set(MODEL, value);
		return this;
	}
	
	public Object getTabIndex() {
		return get(TAB_INDEX);
	}

	public Properties setTabIndex(Object value) {
		set(TAB_INDEX, value);
		return this;
	}
	
	public Object getToggleEvent() {
		return get(TOGGLE_EVENT);
	}

	public Properties setToggleEvent(Object value) {
		set(TOGGLE_EVENT, value);
		return this;
	}
	
	public Object getStateful() {
		return get(STATEFUL);
	}

	public Properties setStateful(Object value) {
		set(STATEFUL, value);
		return this;
	}
	
	public Object getActiveIndex() {
		return get(ACTIVE_INDEX);
	}

	public Properties setActiveIndex(Object value) {
		set(ACTIVE_INDEX, value);
		return this;
	}
	
	public Object getDelay() {
		return get(DELAY);
	}

	public Properties setDelay(Object value) {
		set(DELAY, value);
		return this;
	}
	
	public Object getPartialSubmitFilter() {
		return get(PARTIAL_SUBMIT_FILTER);
	}

	public Properties setPartialSubmitFilter(Object value) {
		set(PARTIAL_SUBMIT_FILTER, value);
		return this;
	}
	
	public Object getIgnoreAutoUpdte() {
		return get(IGNORE_AUTO_UPDATE);
	}

	public Properties setIgnoreAutoUpdte(Object value) {
		set(IGNORE_AUTO_UPDATE, value);
		return this;
	}
	
	public Object getResetValues() {
		return get(RESET_VALUES);
	}

	public Properties setResetValues(Object value) {
		set(RESET_VALUES, value);
		return this;
	}
	
	public Object getOutcome() {
		return get(OUTCOME);
	}

	public Properties setOutcome(Object value) {
		set(OUTCOME, value);
		return this;
	}
	
	public Object getIgnoreAutoUpdate() {
		return get(IGNORE_AUTO_UPDATE);
	}

	public Properties setIgnoreAutoUpdate(Object value) {
		set(IGNORE_AUTO_UPDATE, value);
		return this;
	}
	
	public Object getIncludeViewParams() {
		return get(INCLUDE_VIEW_PARAMS);
	}

	public Properties setIncludeViewParams(Object value) {
		set(INCLUDE_VIEW_PARAMS, value);
		return this;
	}
	
	public Object getRel() {
		return get(REL);
	}

	public Properties setRel(Object value) {
		set(REL, value);
		return this;
	}
	
	public Object getForm() {
		return get(FORM);
	}

	public Properties setForm(Object value) {
		set(FORM, value);
		return this;
	}
	
	public Object getContainerStyleClass() {
		return get(CONTAINER_STYLE_CLASS);
	}

	public Properties setContainerStyleClass(Object value) {
		set(CONTAINER_STYLE_CLASS, value);
		return this;
	}
	
	public Object getContainerStyle() {
		return get(CONTAINER_STYLE);
	}

	public Properties setContainerStyle(Object value) {
		set(CONTAINER_STYLE, value);
		return this;
	}
	
	public Object getDisableClientWindow() {
		return get(DISABLE_CLIENT_WINDOW);
	}

	public Properties setDisableClientWindow(Object value) {
		set(DISABLE_CLIENT_WINDOW, value);
		return this;
	}
	
	public Object getFragment() {
		return get(FRAGMENT);
	}

	public Properties setFragment(Object value) {
		set(FRAGMENT, value);
		return this;
	}
	
	public Object getUniformResourceLocator() {
		return get(UNIFORM_RESOURCE_LOCATOR);
	}

	public Properties setUniformResourceLocator(Object value) {
		set(UNIFORM_RESOURCE_LOCATOR, value);
		return this;
	}
	
	public Object getColumns() {
		return get(COLUMNS);
	}

	public Properties setColumns(Object value) {
		set(COLUMNS, value);
		return this;
	}
	
	public Object getHeaderText() {
		return get(HEADER_TEXT);
	}

	public Properties setHeaderText(Object value) {
		set(HEADER_TEXT, value);
		return this;
	}
	
	public Object getFieldName() {
		return get(FIELD_NAME);
	}

	public Properties setFieldName(Object value) {
		set(FIELD_NAME, value);
		return this;
	}
	
	public Object getMenuColumn() {
		return get(MENU_COLUMN);
	}

	public Properties setMenuColumn(Object value) {
		set(MENU_COLUMN, value);
		return this;
	}
	
	public Object getRenderAsInput() {
		return get(RENDER_AS_INPUT);
	}

	public Properties setRenderAsInput(Object value) {
		set(RENDER_AS_INPUT, value);
		return this;
	}
	
	public Object getThumbnail() {
		return get(THUMBNAIL);
	}

	public Properties setThumbnail(Object value) {
		set(THUMBNAIL, value);
		return this;
	}
	
	public Object getAt() {
		return get(AT);
	}

	public Properties setAt(Object value) {
		set(AT, value);
		return this;
	}
	
	public Object getBackLabel() {
		return get(BACK_LABEL);
	}

	public Properties setBackLabel(Object value) {
		set(BACK_LABEL, value);
		return this;
	}
	
	public Object getMy() {
		return get(MY);
	}

	public Properties setMy(Object value) {
		set(MY, value);
		return this;
	}
	
	public Object getOverlay() {
		return get(OVERLAY);
	}

	public Properties setOverlay(Object value) {
		set(OVERLAY, value);
		return this;
	}
	
	public Object getTrigger() {
		return get(TRIGGER);
	}

	public Properties setTrigger(Object value) {
		set(TRIGGER, value);
		return this;
	}
	
	public Object getTriggerEvent() {
		return get(TRIGGER_EVENT);
	}

	public Properties setTriggerEvent(Object value) {
		set(TRIGGER_EVENT, value);
		return this;
	}
	
	public Object getLayoutCardinalPointNorthRendered() {
		return get(LAYOUT_CARDINAL_POINT_NORTH_RENDERED);
	}

	public Properties setLayoutCardinalPointNorthRendered(Object value) {
		set(LAYOUT_CARDINAL_POINT_NORTH_RENDERED, value);
		return this;
	}
	
	public Object getLayoutCardinalPointEastRendered() {
		return get(LAYOUT_CARDINAL_POINT_EAST_RENDERED);
	}

	public Properties setLayoutCardinalPointEastRendered(Object value) {
		set(LAYOUT_CARDINAL_POINT_EAST_RENDERED, value);
		return this;
	}
	
	public Object getLayoutCardinalPointSouthRendered() {
		return get(LAYOUT_CARDINAL_POINT_SOUTH_RENDERED);
	}

	public Properties setLayoutCardinalPointSouthRendered(Object value) {
		set(LAYOUT_CARDINAL_POINT_SOUTH_RENDERED, value);
		return this;
	}
	
	public Object getLayoutCardinalPointCenterSouthRendered() {
		return get(LAYOUT_CARDINAL_POINT_CENTER_SOUTH_RENDERED);
	}

	public Properties setLayoutCardinalPointCenterSouthRendered(Object value) {
		set(LAYOUT_CARDINAL_POINT_CENTER_SOUTH_RENDERED, value);
		return this;
	}
	
	public Object getLayoutCardinalPointWestRendered() {
		return get(LAYOUT_CARDINAL_POINT_WEST_RENDERED);
	}

	public Properties setLayoutCardinalPointWestRendered(Object value) {
		set(LAYOUT_CARDINAL_POINT_WEST_RENDERED, value);
		return this;
	}
	
	public Object getLayoutCardinalPointCenterRendered() {
		return get(LAYOUT_CARDINAL_POINT_CENTER_RENDERED);
	}

	public Properties setLayoutCardinalPointCenterRendered(Object value) {
		set(LAYOUT_CARDINAL_POINT_CENTER_RENDERED, value);
		return this;
	}
	
	public Object getFilterBy() {
		return get(FILTER_BY);
	}

	public Properties setFilterBy(Object value) {
		set(FILTER_BY, value);
		return this;
	}
	
	public Object getShowUnselectableCheckbox() {
		return get(SHOW_UNSELECTABLE_CHECKBOX);
	}

	public Properties setShowUnselectableCheckbox(Object value) {
		set(SHOW_UNSELECTABLE_CHECKBOX, value);
		return this;
	}
	
	public Object getSkipChildren() {
		return get(SKIP_CHILDREN);
	}

	public Properties setSkipChildren(Object value) {
		set(SKIP_CHILDREN, value);
		return this;
	}
	
	public Object getSkipLeafHandling() {
		return get(SKIP_LEAF_HANDLING);
	}

	public Properties setSkipLeafHandling(Object value) {
		set(SKIP_LEAF_HANDLING, value);
		return this;
	}
	
	public Object getDropRestrict() {
		return get(DROP_RESTRICT);
	}

	public Properties setDropRestrict(Object value) {
		set(DROP_RESTRICT, value);
		return this;
	}
	
	public Object getDragMode() {
		return get(DRAG_MODE);
	}

	public Properties setDragMode(Object value) {
		set(DRAG_MODE, value);
		return this;
	}
	
	public Object getDragDropScope() {
		return get(DRAG_DROP_SCOPE);
	}

	public Properties setDragDropScope(Object value) {
		set(DRAG_DROP_SCOPE, value);
		return this;
	}
	
	public Object getDroppable() {
		return get(DROPPABLE);
	}

	public Properties setDroppable(Object value) {
		set(DROPPABLE, value);
		return this;
	}
	
	public Object getPropagateSelectionUp() {
		return get(PROPAGATE_SELECTION_UP);
	}

	public Properties setPropagateSelectionUp(Object value) {
		set(PROPAGATE_SELECTION_UP, value);
		return this;
	}
	
	public Object getPropagateSelectionDown() {
		return get(PROPAGATE_SELECTION_DOWN);
	}

	public Properties setPropagateSelectionDown(Object value) {
		set(PROPAGATE_SELECTION_DOWN, value);
		return this;
	}
	
	public Object getOrientation() {
		return get(ORIENTATION);
	}

	public Properties setOrientation(Object value) {
		set(ORIENTATION, value);
		return this;
	}
	
	public Object getAnimate() {
		return get(ANIMATE);
	}

	public Properties setAnimate(Object value) {
		set(ANIMATE, value);
		return this;
	}
	
	public Object getDataKey() {
		return get(DATA_KEY);
	}

	public Properties setDataKey(Object value) {
		set(DATA_KEY, value);
		return this;
	}
	
	public Object getHighlight() {
		return get(HIGHLIGHT);
	}

	public Properties setHighlight(Object value) {
		set(HIGHLIGHT, value);
		return this;
	}
	
	public Object getSelectionMode() {
		return get(SELECTION_MODE);
	}

	public Properties setSelectionMode(Object value) {
		set(SELECTION_MODE, value);
		return this;
	}
	
	public Object getSelection() {
		return get(SELECTION);
	}

	public Properties setSelection(Object value) {
		set(SELECTION, value);
		return this;
	}
	
	public Object getOnNodeClick() {
		return get(ON_NODE_CLICK);
	}

	public Properties setOnNodeClick(Object value) {
		set(ON_NODE_CLICK, value);
		return this;
	}
	
	public Object getArialLabel() {
		return get(ARIA_LABEL);
	}

	public Properties setArialLabel(Object value) {
		set(ARIA_LABEL, value);
		return this;
	}
	
	public Object getCollapsedIcon() {
		return get(COLLAPSED_ICON);
	}

	public Properties setCollapsedIcon(Object value) {
		set(COLLAPSED_ICON, value);
		return this;
	}
	
	public Object getExpandedIcon() {
		return get(EXPANDED_ICON);
	}

	public Properties setExpandedIcon(Object value) {
		set(EXPANDED_ICON, value);
		return this;
	}
	
	public Object getData() {
		return get(DATA);
	}

	public Properties setData(Object value) {
		set(DATA, value);
		return this;
	}
	
	public Object getRow() {
		return get(ROW);
	}

	public Properties setRow(Object value) {
		set(ROW, value);
		return this;
	}
	
	public Object getColumn() {
		return get(COLUMN);
	}

	public Properties setColumn(Object value) {
		set(COLUMN, value);
		return this;
	}
	
	public Object getRowsPerPageLabel() {
		return get(ROWS_PER_PAGE_LABEL);
	}

	public Properties setRowsPerPageLabel(Object value) {
		set(ROWS_PER_PAGE_LABEL, value);
		return this;
	}
	
	public Object getRowsPerPageTemplate() {
		return get(ROWS_PER_PAGE_TEMPLATE);
	}

	public Properties setRowsPerPageTemplate(Object value) {
		set(ROWS_PER_PAGE_TEMPLATE, value);
		return this;
	}
	
	public Object getFirst() {
		return get(FIRST);
	}

	public Properties setFirst(Object value) {
		set(FIRST, value);
		return this;
	}
	
	public Object getCells() {
		return get(CELLS);
	}

	public Properties setCells(Object value) {
		set(CELLS, value);
		return this;
	}
	
	public Object getUrlRead() {
		return get(URL_READ);
	}

	public Properties setUrlRead(Object value) {
		set(URL_READ, value);
		return this;
	}
	
	public Object getOutputTextComponent() {
		return get(OUTPUT_TEXT_COMPONENT);
	}

	public Properties setOutputTextComponent(Object value) {
		set(OUTPUT_TEXT_COMPONENT, value);
		return this;
	}
	
	public Object getOutputLinkComponent() {
		return get(OUTPUT_LINK_COMPONENT);
	}

	public Properties setOutputLinkComponent(Object value) {
		set(OUTPUT_LINK_COMPONENT, value);
		return this;
	}
	
	public Object getLinked() {
		return get(LINKED);
	}

	public Properties setLinked(Object value) {
		set(LINKED, value);
		return this;
	}
	
	public Object getOnPageEventComponent() {
		return get(ON_PAGE_EVENT_COMPONENT);
	}

	public Properties setOnPageEventComponent(Object value) {
		set(ON_PAGE_EVENT_COMPONENT, value);
		return this;
	}
	
	public Object getOnFilterEventComponent() {
		return get(ON_FILTER_EVENT_COMPONENT);
	}

	public Properties setOnFilterEventComponent(Object value) {
		set(ON_FILTER_EVENT_COMPONENT, value);
		return this;
	}
	
	public Object getOnSortEventComponent() {
		return get(ON_SORT_EVENT_COMPONENT);
	}

	public Properties setOnSortEventComponent(Object value) {
		set(ON_SORT_EVENT_COMPONENT, value);
		return this;
	}
	
	public Object getSortable() {
		return get(SORTABLE);
	}

	public Properties setSortable(Object value) {
		set(SORTABLE, value);
		return this;
	}
	
	public Object getSortBy() {
		return get(SORT_BY);
	}

	public Properties setSortBy(Object value) {
		set(SORT_BY, value);
		return this;
	}
	
	public Object getSortFunction() {
		return get(SORT_FUNCTION);
	}

	public Properties setSortFunction(Object value) {
		set(SORT_FUNCTION, value);
		return this;
	}
	
	public Object getFilterable() {
		return get(FILTERABLE);
	}

	public Properties setFilterable(Object value) {
		set(FILTERABLE, value);
		return this;
	}
	
	public Object getFilterFunction() {
		return get(FILTER_FUNCTION);
	}

	public Properties setFilterFunction(Object value) {
		set(FILTER_FUNCTION, value);
		return this;
	}
	
	public Object getFilterCommandComponent() {
		return get(FILTER_COMMAND_COMPONENT);
	}

	public Properties setFilterCommandComponent(Object value) {
		set(FILTER_COMMAND_COMPONENT, value);
		return this;
	}
	
	public Object getFilterInputComponent() {
		return get(FILTER_INPUT_COMPONENT);
	}

	public Properties setFilterInputComponent(Object value) {
		set(FILTER_INPUT_COMPONENT, value);
		return this;
	}
	
	public Object getActionOnClass() {
		return get(ACTION_ON_CLASS);
	}

	public Properties setActionOnClass(Object value) {
		set(ACTION_ON_CLASS, value);
		return this;
	}
	
	public Object getLazyModel() {
		return get(LAZY_MODEL);
	}

	public Properties setLazyModel(Object value) {
		set(LAZY_MODEL, value);
		return this;
	}
	
	public Object getPackageBaseNameSet() {
		return get(PACKAGE_BASE_NAME_SET);
	}

	public Properties setPackageBaseNameSet(Object value) {
		set(PACKAGE_BASE_NAME_SET, value);
		return this;
	}
	
	public Object getPrefixSet() {
		return get(PREFIX_SET);
	}

	public Properties setPrefixSet(Object value) {
		set(PREFIX_SET, value);
		return this;
	}
	
	public Object getSuffixSet() {
		return get(SUFFIX_SET);
	}

	public Properties setSuffixSet(Object value) {
		set(SUFFIX_SET, value);
		return this;
	}
	
	public Object getClassSimpleNameSet() {
		return get(CLASS_SIMPLE_NAME_SET);
	}

	public Properties setClassSimpleNameSet(Object value) {
		set(CLASS_SIMPLE_NAME_SET, value);
		return this;
	}
	
	public Object getAddCommandComponent() {
		return get(ADD_COMMAND_COMPONENT);
	}

	public Properties setAddCommandComponent(Object value) {
		set(ADD_COMMAND_COMPONENT, value);
		return this;
	}
	
	public Object getAddInputComponent() {
		return get(ADD_INPUT_COMPONENT);
	}

	public Properties setAddInputComponent(Object value) {
		set(ADD_INPUT_COMPONENT, value);
		return this;
	}
	
	public Object getParent() {
		return get(PARENT);
	}

	public Properties setParent(Object value) {
		set(PARENT, value);
		return this;
	}
	
	public Object getTemplateInForm() {
		return get(TEMPLATE_IN_FORM);
	}

	public Properties setTemplateInForm(Object value) {
		set(TEMPLATE_IN_FORM, value);
		return this;
	}
	
	public Object getTemplateWithoutIdentifier() {
		return get(TEMPLATE_WITHOUT_IDENTIFIER);
	}

	public Properties setTemplateWithoutIdentifier(Object value) {
		set(TEMPLATE_WITHOUT_IDENTIFIER, value);
		return this;
	}
	
	public Object getChoiceValueClass() {
		return get(CHOICE_VALUE_CLASS);
	}

	public Properties setChoiceValueClass(Object value) {
		set(CHOICE_VALUE_CLASS, value);
		return this;
	}
	
	public Object getAddCommandComponentActionAdapterClass() {
		return get(ADD_COMMAND_COMPONENT_ACTION_ADAPTER_CLASS);
	}

	public Properties setAddCommandComponentActionAdapterClass(Object value) {
		set(ADD_COMMAND_COMPONENT_ACTION_ADAPTER_CLASS, value);
		return this;
	}
	
	public Object getAddInputComponentValueClass() {
		return get(ADD_INPUT_COMPONENT_VALUE_CLASS);
	}

	public Properties setAddInputComponentValueClass(Object value) {
		set(ADD_INPUT_COMPONENT_VALUE_CLASS, value);
		return this;
	}
	
	public Object getAddTextComponent() {
		return get(ADD_TEXT_COMPONENT);
	}

	public Properties setAddTextComponent(Object value) {
		set(ADD_TEXT_COMPONENT, value);
		return this;
	}
	
	public Object getRowsCollectionInstance() {
		return get(ROWS_COLLECTION_INSTANCE);
	}

	public Properties setRowsCollectionInstance(Object value) {
		set(ROWS_COLLECTION_INSTANCE, value);
		return this;
	}
	
	public Object getRemoveCommandComponent() {
		return get(REMOVE_COMMAND_COMPONENT);
	}

	public Properties setRemoveCommandComponent(Object value) {
		set(REMOVE_COMMAND_COMPONENT, value);
		return this;
	}
	
	public Object getRowsCollectionInstanceListenerClass() {
		return get(ROWS_COLLECTION_INSTANCE_LISTENER_CLASS);
	}

	public Properties setRowsCollectionInstanceListenerClass(Object value) {
		set(ROWS_COLLECTION_INSTANCE_LISTENER_CLASS, value);
		return this;
	}
	
	public Object getColumnsCollectionInstanceListenerClass() {
		return get(COLUMNS_COLLECTION_INSTANCE_LISTENER_CLASS);
	}

	public Properties setColumnsCollectionInstanceListenerClass(Object value) {
		set(COLUMNS_COLLECTION_INSTANCE_LISTENER_CLASS, value);
		return this;
	}
	
	public Object getRemoveCommandComponentActionAdapterClass() {
		return get(REMOVE_COMMAND_COMPONENT_ACTION_ADAPTER_CLASS);
	}

	public Properties setRemoveCommandComponentActionAdapterClass(Object value) {
		set(REMOVE_COMMAND_COMPONENT_ACTION_ADAPTER_CLASS, value);
		return this;
	}
	
	public Object getAutoRun() {
		return get(AUTO_RUN);
	}

	public Properties setAutoRun(Object value) {
		set(AUTO_RUN, value);
		return this;
	}
	
	public Object getRemoteCommand() {
		return get(REMOTE_COMMAND);
	}

	public Properties setRemoteCommand(Object value) {
		set(REMOTE_COMMAND, value);
		return this;
	}
	
	public Object getEventOnBlur() {
		return get(EVENT_ON_BLUR);
	}

	public Properties setEventOnBlur(Object value) {
		set(EVENT_ON_BLUR, value);
		return this;
	}
	
	public Object getCellListener() {
		return get(CELL_LISTENER);
	}

	public Properties setCellListener(Object value) {
		set(CELL_LISTENER, value);
		return this;
	}
	
	public Object getOutputComponent() {
		return get(OUTPUT_COMPONENT);
	}

	public Properties setOutputComponent(Object value) {
		set(OUTPUT_COMPONENT, value);
		return this;
	}
	
	public Object getMethod() {
		return get(METHOD);
	}

	public Properties setMethod(Object value) {
		set(METHOD, value);
		return this;
	}
	
	public Object getCommand() {
		return get(COMMAND);
	}

	public Properties setCommand(Object value) {
		set(COMMAND, value);
		return this;
	}
	
	public Object getListener() {
		return get(LISTENER);
	}

	public Properties setListener(Object value) {
		set(LISTENER, value);
		return this;
	}
	
	public Object getComponent() {
		return get(COMPONENT);
	}

	public Properties setComponent(Object value) {
		set(COMPONENT, value);
		return this;
	}
	
	public Object getDetail() {
		return get(DETAIL);
	}

	public Properties setDetail(Object value) {
		set(DETAIL, value);
		return this;
	}
	
	public Object getProcessedFieldNames() {
		return get(PROCESSED_FIELD_NAMES);
	}

	public Properties setProcessedFieldNames(Object value) {
		set(PROCESSED_FIELD_NAMES, value);
		return this;
	}
	
	public Object getUpdatedFieldNames() {
		return get(UPDATED_FIELD_NAMES);
	}

	public Properties setUpdatedFieldNames(Object value) {
		set(UPDATED_FIELD_NAMES, value);
		return this;
	}
	
	public Object getFormDetail() {
		return get(FORM_DETAIL);
	}

	public Properties setFormDetail(Object value) {
		set(FORM_DETAIL, value);
		return this;
	}
	
	public Object getDecimalPlaces() {
		return get(DECIMAL_PLACES);
	}

	public Properties setDecimalPlaces(Object value) {
		set(DECIMAL_PLACES, value);
		return this;
	}
	
	public Object getDecimalSeparator() {
		return get(DECIMAL_SEPARATOR);
	}

	public Properties setDecimalSeparator(Object value) {
		set(DECIMAL_SEPARATOR, value);
		return this;
	}
	
	public Object getThousandSeparator() {
		return get(THOUSAND_SEPARATOR);
	}

	public Properties setThousandSeparator(Object value) {
		set(THOUSAND_SEPARATOR, value);
		return this;
	}
	
	public Object getDataTableCell() {
		return get(DATA_TABLE_CELL);
	}

	public Properties setDataTableCell(Object value) {
		set(DATA_TABLE_CELL, value);
		return this;
	}
	
	public Object getProcessedColumnFieldNames() {
		return get(PROCESSED_COLUMN_FIELD_NAMES);
	}

	public Properties setProcessedColumnFieldNames(Object value) {
		set(PROCESSED_COLUMN_FIELD_NAMES, value);
		return this;
	}
	
	public Object getUpdatedColumnFieldNames() {
		return get(UPDATED_COLUMN_FIELD_NAMES);
	}

	public Properties setUpdatedColumnFieldNames(Object value) {
		set(UPDATED_COLUMN_FIELD_NAMES, value);
		return this;
	}
	
	public Object getCell() {
		return get(CELL);
	}

	public Properties setCell(Object value) {
		set(CELL, value);
		return this;
	}
	
	public Object getDataTable() {
		return get(DATA_TABLE);
	}

	public Properties setDataTable(Object value) {
		set(DATA_TABLE, value);
		return this;
	}
	
	public Object getFooterRendered() {    
		return get(FOOTER_RENDERED);
	}

	public Properties setFooterRendered(Object value) {
		set(FOOTER_RENDERED, value);
		return this;
	}
	
	public Object getTimeOnly() {
		return get(TIME_ONLY);
	}

	public Properties setTimeOnly(Object value) {
		set(TIME_ONLY, value);
		return this;
	}
	
	public Object getPattern() {
		return get(PATTERN);
	}

	public Properties setPattern(Object value) {
		set(PATTERN, value);
		return this;
	}
	
	public Object getConstraints() {
		return get(CONSTRAINTS);
	}

	public Properties setConstraints(Object value) {
		set(CONSTRAINTS, value);
		return this;
	}
	
	public Object getMaster() {
		return get(MASTER);
	}

	public Properties setMaster(Object value) {
		set(MASTER, value);
		return this;
	}
	
	public Object getInstance() {
		return get(INSTANCE);
	}

	public Properties setInstance(Object value) {
		set(INSTANCE, value);
		return this;
	}
	
	public Object getRequestParameterClasses() {
		return get(REQUEST_PARAMETER_CLASSES);
	}

	public Properties setRequestParameterClasses(Object value) {
		set(REQUEST_PARAMETER_CLASSES, value);
		return this;
	}
	
	public Object getFieldNamesSetFromRequestParameters() {
		return get(FIELD_NAMES_SET_FROM_REQUEST_PARAMETERS);
	}

	public Properties setFieldNamesSetFromRequestParameters(Object value) {
		set(FIELD_NAMES_SET_FROM_REQUEST_PARAMETERS, value);
		return this;
	}
	
	public Object getOnPrepareAddMenu() {
		return get(ON_PREPARE_ADD_MENU);
	}

	public Properties setOnPrepareAddMenu(Object value) {
		set(ON_PREPARE_ADD_MENU, value);
		return this;
	}
	
	public Object getOnPrepareAddMenuAddCommand() {
		return get(ON_PREPARE_ADD_MENU_ADD_COMMAND);
	}

	public Properties setOnPrepareAddMenuAddCommand(Object value) {
		set(ON_PREPARE_ADD_MENU_ADD_COMMAND, value);
		return this;
	}
	
	public Object getOnPrepareAddColumnAction() {
		return get(ON_PREPARE_ADD_COLUMN_ACTION);
	}

	public Properties setOnPrepareAddColumnAction(Object value) {
		set(ON_PREPARE_ADD_COLUMN_ACTION, value);
		return this;
	}
	
	public Object getOnPrepareAddColumnOrderNumber() {
		return get(ON_PREPARE_ADD_COLUMN_ORDER_NUMBER);
	}

	public Properties setOnPrepareAddColumnOrderNumber(Object value) {
		set(ON_PREPARE_ADD_COLUMN_ORDER_NUMBER, value);
		return this;
	}
	
	public Object getContainer() {
		return get(CONTAINER);
	}

	public Properties setContainer(Object value) {
		set(CONTAINER, value);
		return this;
	}
	
	public Object getTopLevelContainer() {
		return get(TOP_LEVEL_CONTAINER);
	}

	public Properties setTopLevelContainer(Object value) {
		set(TOP_LEVEL_CONTAINER, value);
		return this;
	}
	
	public Object getContentTitle() {
		return get(CONTENT_TITLE);
	}

	public Properties setContentTitle(Object value) {
		set(CONTENT_TITLE, value);
		return this;
	}
	
	public Object getHeaderRendered() {
		return get(HEADER_RENDERED);
	}

	public Properties setHeaderRendered(Object value) {
		set(HEADER_RENDERED, value);
		return this;
	}
	
	public Object getExpanded() {
		return get(EXPANDED);
	}

	public Properties setExpanded(Object value) {
		set(EXPANDED, value);
		return this;
	}
	
	public Object getCollapsed() {
		return get(COLLAPSED);
	}

	public Properties setCollapsed(Object value) {
		set(COLLAPSED, value);
		return this;
	}
	
	public Object getRowProperties() {
		return get(ROW_PROPERTIES);
	}

	public Properties setRowProperties(Object value) {
		set(ROW_PROPERTIES, value);
		return this;
	}
	
	public Object getRemoveCommandProperties() {
		return get(REMOVE_COMMAND_PROPERTIES);
	}

	public Properties setRemoveCommandProperties(Object value) {
		set(REMOVE_COMMAND_PROPERTIES, value);
		return this;
	}
	
	public Object getAddCommandQueryKeyValues() {
		return get(ADD_COMMAND_QUERY_KEY_VALUES);
	}

	public Properties setAddCommandQueryKeyValues(Object value) {
		set(ADD_COMMAND_QUERY_KEY_VALUES, value);
		return this;
	}
	
	public Object getIsFooterShowable() {
		return get(IS_FOOTER_SHOWABLE);
	}

	public Properties setIsFooterShowable(Object value) {
		set(IS_FOOTER_SHOWABLE, value);
		return this;
	}
	
	public Object getIsInstancesLoadableFromCollection() {
		return get(IS_INSTANCES_LOADABLE_FROM_COLLECTION);
	}

	public Properties setIsInstancesLoadableFromCollection(Object value) {
		set(IS_INSTANCES_LOADABLE_FROM_COLLECTION, value);
		return this;
	}
	
	public Object getMasterFieldName() {
		return get(MASTER_FIELD_NAME);
	}

	public Properties setMasterFieldName(Object value) {
		set(MASTER_FIELD_NAME, value);
		return this;
	}
	
	public Object getChoicesIsSourceDisjoint() {
		return get(CHOICES_IS_SOURCE_DISJOINT);
	}

	public Properties setChoicesIsSourceDisjoint(Object value) {
		set(CHOICES_IS_SOURCE_DISJOINT, value);
		return this;
	}
	
	public Object getFormMasterObjectActionOnClassCollectionInstanceFieldName() {
		return get(FORM_MASTER_OBJECT_ACTION_ON_CLASS_COLLECTION_INSTANCE_FIELD_NAME);
	}

	public Properties setFormMasterObjectActionOnClassCollectionInstanceFieldName(Object value) {
		set(FORM_MASTER_OBJECT_ACTION_ON_CLASS_COLLECTION_INSTANCE_FIELD_NAME, value);
		return this;
	}
	
	public Object getFormMasterObjectActionOnClassCollectionInstance() {
		return get(FORM_MASTER_OBJECT_ACTION_ON_CLASS_COLLECTION_INSTANCE);
	}

	public Properties setFormMasterObjectActionOnClassCollectionInstance(Object value) {
		set(FORM_MASTER_OBJECT_ACTION_ON_CLASS_COLLECTION_INSTANCE, value);
		return this;
	}
	
	public Object getChoiceValueClassMasterFieldName() {
		return get(CHOICE_VALUE_CLASS_MASTER_FIELD_NAME);
	}

	public Properties setChoiceValueClassMasterFieldName(Object value) {
		set(CHOICE_VALUE_CLASS_MASTER_FIELD_NAME, value);
		return this;
	}
	
	public Object getIsAutomaticallyAddChoiceValues() {
		return get(IS_AUTOMATICALLY_ADD_CHOICE_VALUES);
	}

	public Properties setIsAutomaticallyAddChoiceValues(Object value) {
		set(IS_AUTOMATICALLY_ADD_CHOICE_VALUES, value);
		return this;
	}
	
	public Object getField() {
		return get(FIELD);
	}

	public Properties setField(Object value) {
		set(FIELD, value);
		return this;
	}
	
	public Object getFields() {
		return get(FIELDS);
	}

	public Properties setFields(Object value) {
		set(FIELDS, value);
		return this;
	}
	
	public Object getKey() {
		return get(KEY);
	}

	public Properties setKey(Object value) {
		set(KEY, value);
		return this;
	}
	
	public Object getLocale() {
		return get(LOCALE);
	}

	public Properties setLocale(Object value) {
		set(LOCALE, value);
		return this;
	}
	
	public Object getCase() {
		return get(CASE);
	}

	public Properties setCase(Object value) {
		set(CASE, value);
		return this;
	}
	
	public Object getCachable() {
		return get(CACHABLE);
	}

	public Properties setCachable(Object value) {
		set(CACHABLE, value);
		return this;
	}
	
	public Object getParameters() {
		return get(PARAMETERS);
	}

	public Properties setParameters(Object value) {
		set(PARAMETERS, value);
		return this;
	}
	
	public Object getBeforeExecutionListener() {
		return get(BEFORE_EXECUTION_LISTENER);
	}

	public Properties setBeforeExecutionListener(Object value) {
		set(BEFORE_EXECUTION_LISTENER, value);
		return this;
	}
	
	public Object getAfterExecutionListener() {
		return get(AFTER_EXECUTION_LISTENER);
	}

	public Properties setAfterExecutionListener(Object value) {
		set(AFTER_EXECUTION_LISTENER, value);
		return this;
	}
	
	public Object getResourceBundleKeyValueFoundListener() {
		return get(RESOURCE_BUNDLE_KEY_VALUE_FOUND_LISTENER);
	}

	public Properties setResourceBundleKeyValueFoundListener(Object value) {
		set(RESOURCE_BUNDLE_KEY_VALUE_FOUND_LISTENER, value);
		return this;
	}
	
	public Object getResourceBundles() {
		return get(RESOURCE_BUNDLES);
	}

	public Properties setResourceBundles(Object value) {
		set(RESOURCE_BUNDLES, value);
		return this;
	}
	
	public Object getInput() {
		return get(INPUT);
	}

	public Properties setInput(Object value) {
		set(INPUT, value);
		return this;
	}
	
	public Object getOutput() {
		return get(OUTPUT);
	}

	public Properties setOutput(Object value) {
		set(OUTPUT, value);
		return this;
	}
	
	public Object getParameterSeparator() {
		return get(PARAMETER_SEPARATOR);
	}

	public Properties setParameterSeparator(Object value) {
		set(PARAMETER_SEPARATOR, value);
		return this;
	}
	
	public Object getParameterFormat() {
		return get(PARAMETER_FORMAT);
	}

	public Properties setParameterFormat(Object value) {
		set(PARAMETER_FORMAT, value);
		return this;
	}
	
	public Object getFormat() {
		return get(FORMAT);
	}

	public Properties setFormat(Object value) {
		set(FORMAT, value);
		return this;
	}
	
	public Object getTemplateFormat() {
		return get(TEMPLATE_FORMAT);
	}

	public Properties setTemplateFormat(Object value) {
		set(TEMPLATE_FORMAT, value);
		return this;
	}
	
	public Object getLevel() {
		return get(LEVEL);
	}

	public Properties setLevel(Object value) {
		set(LEVEL, value);
		return this;
	}
	
	public Object getLogLevel() {
		return get(LOG_LEVEL);
	}

	public Properties setLogLevel(Object value) {
		set(LOG_LEVEL, value);
		return this;
	}
	
	public Object getBuilder() {
		return get(BUILDER);
	}

	public Properties setBuilder(Object value) {
		set(BUILDER, value);
		return this;
	}
	
	public Object getMarker() {
		return get(MARKER);
	}

	public Properties setMarker(Object value) {
		set(MARKER, value);
		return this;
	}
	
	public Object getMarkers() {
		return get(MARKERS);
	}

	public Properties setMarkers(Object value) {
		set(MARKERS, value);
		return this;
	}
	
	public Object getIsRecursive() {
		return get(IS_RECURSIVE);
	}

	public Properties setIsRecursive(Object value) {
		set(IS_RECURSIVE, value);
		return this;
	}
	
	public Object getModifiers() {
		return get(MODIFIERS);
	}

	public Properties setModifiers(Object value) {
		set(MODIFIERS, value);
		return this;
	}
	
	public Object getAnnotationClasses() {
		return get(ANNOTATION_CLASSES);
	}

	public Properties setAnnotationClasses(Object value) {
		set(ANNOTATION_CLASSES, value);
		return this;
	}
	
	public Object getToken() {
		return get(TOKEN);
	}

	public Properties setToken(Object value) {
		set(TOKEN, value);
		return this;
	}
	
	public Object getTokenLocation() {
		return get(TOKEN_LOCATION);
	}

	public Properties setTokenLocation(Object value) {
		set(TOKEN_LOCATION, value);
		return this;
	}
	
	public Object getValueUsageType() {
		return get(VALUE_USAGE_TYPE);
	}

	public Properties setValueUsageType(Object value) {
		set(VALUE_USAGE_TYPE, value);
		return this;
	}
	
	public Object getObject() {
		return get(OBJECT);
	}

	public Properties setObject(Object value) {
		set(OBJECT, value);
		return this;
	}
	
	public Object getThrowable() {
		return get(THROWABLE);
	}

	public Properties setThrowable(Object value) {
		set(THROWABLE, value);
		return this;
	}
	
	public Object getEntityManager() {
		return get(ENTITY_MANAGER);
	}

	public Properties setEntityManager(Object value) {
		set(ENTITY_MANAGER, value);
		return this;
	}
	
	public Object getSystemAction() {
		return get(SYSTEM_ACTION);
	}

	public Properties setSystemAction(Object value) {
		set(SYSTEM_ACTION, value);
		return this;
	}
	
	public Object getEntity() {
		return get(ENTITY);
	}

	public Properties setEntity(Object value) {
		set(ENTITY, value);
		return this;
	}
	
	public Object getLog() {
		return get(LOG);
	}

	public Properties setLog(Object value) {
		set(LOG, value);
		return this;
	}
	
	public Object getLoggable() {
		return get(LOGGABLE);
	}

	public Properties setLoggable(Object value) {
		set(LOGGABLE, value);
		return this;
	}
	
	public Object getMonitorable() {
		return get(MONITORABLE);
	}

	public Properties setMonitorable(Object value) {
		set(MONITORABLE, value);
		return this;
	}
	
	public Object getFunction() {
		return get(FUNCTION);
	}

	public Properties setFunction(Object value) {
		set(FUNCTION, value);
		return this;
	}
	
	public Object getExecution() {
		return get(EXECUTION);
	}

	public Properties setExecution(Object value) {
		set(EXECUTION, value);
		return this;
	}
	
	public Object getStart() {
		return get(START);
	}

	public Properties setStart(Object value) {
		set(START, value);
		return this;
	}
	
	public Object getEnd() {
		return get(END);
	}

	public Properties setEnd(Object value) {
		set(END, value);
		return this;
	}
	
	public Object getDuration() {
		return get(DURATION);
	}

	public Properties setDuration(Object value) {
		set(DURATION, value);
		return this;
	}
	
	public Object getEntityClass() {
		return get(ENTITY_CLASS);
	}

	public Properties setEntityClass(Object value) {
		set(ENTITY_CLASS, value);
		return this;
	}
	
	public Object getEntityIdentifier() {
		return get(ENTITY_IDENTIFIER);
	}

	public Properties setEntityIdentifier(Object value) {
		set(ENTITY_IDENTIFIER, value);
		return this;
	}
	
	public Object getBean() {
		return get(BEAN);
	}

	public Properties setBean(Object value) {
		set(BEAN, value);
		return this;
	}
	
	public Object getEntities() {
		return get(ENTITIES);
	}

	public Properties setEntities(Object value) {
		set(ENTITIES, value);
		return this;
	}
	
	public Object getPackage() {
		return get(PACKAGE);
	}

	public Properties setPackage(Object value) {
		set(PACKAGE, value);
		return this;
	}
	
	public Object getInterface() {
		return get(INTERFACE);
	}

	public Properties setInterface(Object value) {
		set(INTERFACE, value);
		return this;
	}
	
	public Object getSuffix() {
		return get(SUFFIX);
	}

	public Properties setSuffix(Object value) {
		set(SUFFIX, value);
		return this;
	}
	
	public Object getUser() {
		return get(USER);
	}

	public Properties setUser(Object value) {
		set(USER, value);
		return this;
	}
	
	public Object getTransaction() {
		return get(TRANSACTION);
	}

	public Properties setTransaction(Object value) {
		set(TRANSACTION, value);
		return this;
	}
	
	public Object getIs() {
		return get(IS);
	}

	public Properties setIs(Object value) {
		set(IS, value);
		return this;
	}
	
	public Object getCatch() {
		return get(CATCH);
	}

	public Properties setCatch(Object value) {
		set(CATCH, value);
		return this;
	}
	
	public Object getRunnable() {
		return get(RUNNABLE);
	}

	public Properties setRunnable(Object value) {
		set(RUNNABLE, value);
		return this;
	}
	
	public Object getCount() {
		return get(COUNT);
	}

	public Properties setCount(Object value) {
		set(COUNT, value);
		return this;
	}
	
	public Object getObjects() {
		return get(OBJECTS);
	}

	public Properties setObjects(Object value) {
		set(OBJECTS, value);
		return this;
	}
	
	public Object getBusiness() {
		return get(BUSINESS);
	}

	public Properties setBusiness(Object value) {
		set(BUSINESS, value);
		return this;
	}
	
	public Object getKeyword() {
		return get(KEYWORD);
	}

	public Properties setKeyword(Object value) {
		set(KEYWORD, value);
		return this;
	}
	
	public Object getArguments() {
		return get(ARGUMENTS);
	}

	public Properties setArguments(Object value) {
		set(ARGUMENTS, value);
		return this;
	}
	
	public Object getAlias() {
		return get(ALIAS);
	}

	public Properties setAlias(Object value) {
		set(ALIAS, value);
		return this;
	}
	
	public Object getTuple() {
		return get(TUPLE);
	}

	public Properties setTuple(Object value) {
		set(TUPLE, value);
		return this;
	}
	
	public Object getTuples() {
		return get(TUPLE);
	}

	public Properties setTuples(Object value) {
		set(TUPLE, value);
		return this;
	}
	
	public Object getPrefix() {
		return get(PREFIX);
	}

	public Properties setPrefix(Object value) {
		set(PREFIX, value);
		return this;
	}
	
	public Object getParameter() {
		return get(PARAMETER);
	}

	public Properties setParameter(Object value) {
		set(PARAMETER, value);
		return this;
	}
	
	public Object getOperator() {
		return get(OPERATOR);
	}

	public Properties setOperator(Object value) {
		set(OPERATOR, value);
		return this;
	}
	
	public Object getLeft() {
		return get(LEFT);
	}

	public Properties setLeft(Object value) {
		set(LEFT, value);
		return this;
	}
	
	public Object getRight() {
		return get(RIGHT);
	}

	public Properties setRight(Object value) {
		set(RIGHT, value);
		return this;
	}
	
	public Object getOperand() {
		return get(OPERAND);
	}

	public Properties setOperand(Object value) {
		set(OPERAND, value);
		return this;
	}
	
	public Object getAttributes() {
		return get(ATTRIBUTES);
	}

	public Properties setAttributes(Object value) {
		set(ATTRIBUTES, value);
		return this;
	}
	
	public Object getAttribute() {
		return get(ATTRIBUTE);
	}

	public Properties setAttribute(Object value) {
		set(ATTRIBUTE, value);
		return this;
	}
	
	public Object getLiteral() {
		return get(LITERAL);
	}

	public Properties setLiteral(Object value) {
		set(LITERAL, value);
		return this;
	}
	
	public Object getChildren() {
		return get(CHILDREN);
	}

	public Properties setChildren(Object value) {
		set(CHILDREN, value);
		return this;
	}
	
	public Object getConjunction() {
		return get(CONJUNCTION);
	}

	public Properties setConjunction(Object value) {
		set(CONJUNCTION, value);
		return this;
	}
	
	public Object getPredicate() {
		return get(PREDICATE);
	}

	public Properties setPredicate(Object value) {
		set(PREDICATE, value);
		return this;
	}
	
	public Object getWher() {
		return get(WHERE);
	}

	public Properties setWher(Object value) {
		set(WHERE, value);
		return this;
	}
	
	public Object getFrom() {
		return get(FROM);
	}

	public Properties setFrom(Object value) {
		set(FROM, value);
		return this;
	}
	
	public Object getNull() {
		return get(NULL);
	}

	public Properties setNull(Object value) {
		set(NULL, value);
		return this;
	}
	
	public Object getAscending() {
		return get(ASCENDING);
	}

	public Properties setAscending(Object value) {
		set(ASCENDING, value);
		return this;
	}
	
	public Object getMatched() {
		return get(MATCHED);
	}

	public Properties setMatched(Object value) {
		set(MATCHED, value);
		return this;
	}
	
	public Object getExcluded() {
		return get(EXCLUDED);
	}

	public Properties setExcluded(Object value) {
		set(EXCLUDED, value);
		return this;
	}
	
	public Object getMatch() {
		return get(MATCH);
	}

	public Properties setMatch(Object value) {
		set(MATCH, value);
		return this;
	}
	
	public Object getCriterias() {
		return get(CRITERIAS);
	}

	public Properties setCriterias(Object value) {
		set(CRITERIAS, value);
		return this;
	}
	
	public Object getCriteria() {
		return get(CRITERIA);
	}

	public Properties setCriteria(Object value) {
		set(CRITERIA, value);
		return this;
	}
	
	public Object getString() {
		return get(STRING);
	}

	public Properties setString(Object value) {
		set(STRING, value);
		return this;
	}
	
	public Object getParenthesis() {
		return get(PARENTHESIS);
	}

	public Properties setParenthesis(Object value) {
		set(PARENTHESIS, value);
		return this;
	}
	
	public Object getQuery() {
		return get(QUERY);
	}

	public Properties setQuery(Object value) {
		set(QUERY, value);
		return this;
	}
	
	public Object getIsExecutable() {
		return get(EXECUTABLE);
	}

	public Properties setIsExecutable(Object value) {
		set(EXECUTABLE, value);
		return this;
	}
	
	public Object getPath() {
		return get(PATH);
	}

	public Properties setPath(Object value) {
		set(PATH, value);
		return this;
	}
	
	public Object getLayer() {
		return get(LAYER);
	}

	public Properties setLayer(Object value) {
		set(LAYER, value);
		return this;
	}
	
	public Object getQueries() {
		return get(QUERIES);
	}

	public Properties setQueries(Object value) {
		set(QUERIES, value);
		return this;
	}
	
	public Object getGetter() {
		return get(GETTER);
	}

	public Properties setGetter(Object value) {
		set(GETTER, value);
		return this;
	}
	
	public Object getAffirmation() {
		return get(AFFIRMATION);
	}

	public Properties setAffirmation(Object value) {
		set(AFFIRMATION, value);
		return this;
	}
	
	public Object getAssertions() {
		return get(ASSERTIONS);
	}

	public Properties setAssertions(Object value) {
		set(ASSERTIONS, value);
		return this;
	}
	
	public Object getPost() {
		return get(POST);
	}

	public Properties setPost(Object value) {
		set(POST, value);
		return this;
	}
	
	public Object getPre() {
		return get(PRE);
	}

	public Properties setPre(Object value) {
		set(PRE, value);
		return this;
	}
	
	public Object getRunnables() {
		return get(RUNNABLES);
	}

	public Properties setRunnables(Object value) {
		set(RUNNABLES, value);
		return this;
	}
	
	public Object getAssertion() {
		return get(ASSERTION);
	}

	public Properties setAssertion(Object value) {
		set(ASSERTION, value);
		return this;
	}
	
	public Object getCore() {
		return get(CORE);
	}

	public Properties setCore(Object value) {
		set(CORE, value);
		return this;
	}
	
	public Object getResult() {
		return get(RESULT);
	}

	public Properties setResult(Object value) {
		set(RESULT, value);
		return this;
	}
	
	public Object getAll() {
		return get(ALL);
	}

	public Properties setAll(Object value) {
		set(ALL, value);
		return this;
	}
	
	public Object getResponse() {
		return get(RESPONSE);
	}

	public Properties setResponse(Object value) {
		set(RESPONSE, value);
		return this;
	}
	
	public Object getSetter() {
		return get(SETTER);
	}

	public Properties setSetter(Object value) {
		set(SETTER, value);
		return this;
	}
	
	public Object getCopy() {
		return get(COPY);
	}

	public Properties setCopy(Object copy) {
		set(COPY, copy);
		return this;
	}
	
	public Object getMap() {
		return get(MAP);
	}

	public Properties setMap(Object map) {
		set(MAP, map);
		return this;
	}
	
	public Object getUri() {
		return get(URI);
	}

	public Properties setUri(Object uri) {
		set(URI, uri);
		return this;
	}
	
	public Object getCaller() {
		return get(CALLER);
	}

	public Properties setCaller(Object caller) {
		set(CALLER, caller);
		return this;
	}
	
	public Object getIdentifiers() {
		return get(IDENTIFIERS);
	}

	public Properties setIdentifiers(Object identifiers) {
		set(IDENTIFIERS, identifiers);
		return this;
	}
	
	public Object getHost() {
		return get(HOST);
	}

	public Properties setHost(Object host) {
		set(HOST, host);
		return this;
	}
	
	public Object getPort() {
		return get(PORT);
	}

	public Properties setPort(Object port) {
		set(PORT, port);
		return this;
	}
	
	public Object getContext() {
		return get(CONTEXT);
	}

	public Properties setContext(Object context) {
		set(CONTEXT, context);
		return this;
	}
	
	public Object getScheme() {
		return get(SCHEME);
	}

	public Properties setScheme(Object scheme) {
		set(SCHEME, scheme);
		return this;
	}
	
	public Object getRequest() {
		return get(REQUEST);
	}

	public Properties setRequest(Object request) {
		set(REQUEST, request);
		return this;
	}
	
	public Object getDisplay() {
		return get(DISPLAY);
	}

	public Properties setDisplay(Object display) {
		set(DISPLAY, display);
		return this;
	}
	
	public Object getDeferred() {
		return get(DEFERRED);
	}

	public Properties setDeferred(Object deferred) {
		set(DEFERRED, deferred);
		return this;
	}
	
	public Object getDeferredMode() {
		return get(DEFERRED_MODE);
	}

	public Properties setDeferredMode(Object deferredMode) {
		set(DEFERRED_MODE, deferredMode);
		return this;
	}
	
	public Object getLang() {
		return get(LANG);
	}

	public Properties setLang(Object lang) {
		set(LANG, lang);
		return this;
	}
	
	public Object getNotificationBuilders() {
		return get(NOTIFICATION_BUILDERS);
	}

	public Properties setNotificationBuilders(Object notificationBuilders) {
		set(NOTIFICATION_BUILDERS, notificationBuilders);
		return this;
	}
	
	public Object getNotifications() {
		return get(NOTIFICATIONS);
	}

	public Properties setNotifications(Object notifications) {
		set(NOTIFICATIONS, notifications);
		return this;
	}
	
	public Object getFacesContext() {
		return get(FACES_CONTEXT);
	}

	public Properties setFacesContext(Object context) {
		set(FACES_CONTEXT, context);
		return this;
	}
	
	public Object getRenderMessages() {
		return get(RENDER_MESSAGES);
	}

	public Properties setRenderMessages(Object renderMessages) {
		set(RENDER_MESSAGES, renderMessages);
		return this;
	}
	
	public Object getThrowThrowable() {
		return get(THROW_THROWABLE);
	}

	public Properties setThrowThrowable(Object throwThrowable) {
		set(THROW_THROWABLE, throwThrowable);
		return this;
	}
	
	public Object getServiceNotFound() {
		return get(SERVICE_NOT_FOUND);
	}

	public Properties setServiceNotFound(Object serviceNotFound) {
		set(SERVICE_NOT_FOUND, serviceNotFound);
		return this;
	}
	
	public Object getMenu() {
		return get(MENU);
	}

	public Properties setMenu(Object menu) {
		set(MENU, menu);
		return this;
	}
	
	public Object getUniformResourceLocatorMap() {
		return get(UNIFORM_RESOURCE_LOCATOR_MAP);
	}

	public Properties setUniformResourceLocatorMap(Object uniformResourceLocatorMap) {
		set(UNIFORM_RESOURCE_LOCATOR_MAP, uniformResourceLocatorMap);
		return this;
	}
	
	public Object getTooltip() {
		return get(TOOLTIP);
	}

	public Properties setTooltip(Object tooltip) {
		set(TOOLTIP, tooltip);
		return this;
	}
	
	public Object getNavigationIdentifier() {
		return get(NAVIGATION_IDENTIFIER);
	}

	public Properties setNavigationIdentifier(Object navigationIdentifier) {
		set(NAVIGATION_IDENTIFIER, navigationIdentifier);
		return this;
	}
	
	public Object getFieldsNames() {
		return get(FIELDS_NAMES);
	}

	public Properties setFieldsNames(Object fieldsNames) {
		set(FIELDS_NAMES, fieldsNames);
		return this;
	}
	
	public Object getRoot() {
		return get(ROOT);
	}

	public Properties setRoot(Object root) {
		set(ROOT, root);
		return this;
	}
	
	//public static final String THROWABLE = "SERVICE_NOT_FOUND";
	public static final String ROOT = "ROOT";
	public static final String FIELDS_NAMES = "FIELDS_NAMES";
	public static final String NAVIGATION_IDENTIFIER = "NAVIGATION_IDENTIFIER";
	public static final String TOOLTIP = "TOOLTIP";
	public static final String UNIFORM_RESOURCE_LOCATOR_MAP = "UNIFORM_RESOURCE_LOCATOR_MAP";
	public static final String MENU = "MENU";
	public static final String SERVICE_NOT_FOUND = "SERVICE_NOT_FOUND";
	public static final String RENDER_MESSAGES = "RENDER_MESSAGES";
	public static final String THROW_THROWABLE = "THROW_THROWABLE";
	public static final String FACES_CONTEXT = "FACES_CONTEXT";
	public static final String DEFERRED = "DEFERRED";
	public static final String DEFERRED_MODE = "DEFERRED_MODE";
	public static final String DISPLAY = "DISPLAY";
	public static final String REQUEST = "REQUEST";
	public static final String SCHEME = "SCHEME";
	public static final String CONTEXT = "CONTEXT";
	public static final String HOST = "HOST";
	public static final String PORT = "PORT";
	public static final String TRY_CATCH_FINALLY = "TRY_CATCH_FINALLY";
	public static final String FINALLY = "FINALLY";
	public static final String IDENTIFIERS = "IDENTIFIERS";
	public static final String CALLER = "CALLER";
	public static final String URI = "URI";
	public static final String PERSISTENCE = "PERSISTENCE";
	public static final String REPRESENTATION = "REPRESENTATION";
	public static final String MAP = "MAP";
	public static final String COPY = "COPY";
	public static final String RESPONSE = "RESPONSE";
	public static final String ALL = "ALL";
	public static final String RESULT = "RESULT";
	public static final String CORE = "CORE";
	public static final String _1 = "_1";
	public static final String _2 = "_2";
	public static final String RUNNABLES = "RUNNABLES";
	public static final String POST = "POST";
	public static final String PRE = "PRE";
	public static final String ASSERTIONS = "ASSERTIONS";
	public static final String ASSERTION = "ASSERTION";
	public static final String AFFIRMATION = "AFFIRMATION";
	public static final String GETTER = "GETTER";
	public static final String SETTER = "SETTER";
	public static final String QUERIES = "QUERIES";
	public static final String LAYER = "LAYER";
	public static final String PATH = "PATH";
	public static final String EXECUTABLE = "EXECUTABLE";
	public static final String QUERY = "QUERY";
	public static final String PARENTHESIS = "PARENTHESIS";
	public static final String STRING = "STRING";
	public static final String CRITERIA = "CRITERIA";
	public static final String CRITERIAS = "CRITERIAS";
	public static final String MATCH = "MATCH";
	public static final String MATCHED = "MATCHED";
	public static final String EXCLUDED = "EXCLUDED";
	public static final String ASCENDING = "ASCENDING";
	public static final String NULL = "NULL";
	public static final String WHERE = "WHERE";
	public static final String FROM = "FROM";
	public static final String SELECT = "SELECT";
	public static final String PREDICATE = "PREDICATE";
	public static final String CONJUNCTION = "CONJUNCTION";
	public static final String CHILDREN = "CHILDREN";
	public static final String LITERAL = "LITERAL";
	public static final String ATTRIBUTE = "ATTRIBUTE";
	public static final String ATTRIBUTES = "ATTRIBUTES";
	public static final String LEFT = "LEFT";
	public static final String RIGHT = "RIGHT";
	public static final String OPERAND = "OPERAND";
	public static final String OPERATOR = "OPERATOR";
	public static final String PREFIX = "PREFIX";
	public static final String TUPLES = "TUPLES";
	public static final String TUPLE = "TUPLE";
	public static final String ALIAS = "ALIAS";
	public static final String ARGUMENTS = "ARGUMENTS";
	public static final String KEYWORD = "KEYWORD";
	public static final String BUSINESS = "BUSINESS";
	public static final String OBJECTS = "OBJECTS";
	public static final String COUNT = "COUNT";
	public static final String RUNNABLE = "RUNNABLE";
	public static final String IS = "IS";
	public static final String CATCH = "CATCH";
	public static final String USER = "USER";
	public static final String TRANSACTION = "TRANSACTION";
	public static final String SUFFIX = "SUFFIX";
	public static final String INTERFACE = "INTERFACE";
	public static final String PACKAGE = "PACKAGE";
	public static final String ENTITIES = "ENTITIES";
	public static final String BEAN = "BEAN";
	public static final String ENTITY_CLASS = "ENTITY_CLASS";
	public static final String ENTITY_IDENTIFIER = "ENTITY_IDENTIFIER";
	public static final String FUNCTION = "FUNCTION";
	public static final String EXECUTION = "EXECUTION";
	public static final String START = "START";
	public static final String END = "END";
	public static final String DURATION = "DURATION";
	public static final String MONITORABLE = "MONITORABLE";
	public static final String LOGGABLE = "LOGGABLE";
	public static final String LOG = "LOG";
	public static final String ENTITY = "ENTITY";
	public static final String ENTITY_MANAGER = "ENTITY_MANAGER";
	public static final String SYSTEM_ACTION = "SYSTEM_ACTION";
	public static final String THROWABLE = "THROWABLE";
	public static final String OBJECT = "OBJECT";
	public static final String VALUE_USAGE_TYPE = "VALUE_USAGE_TYPE";
	public static final String TOKEN = "TOKEN";
	public static final String TOKEN_LOCATION = "TOKEN_LOCATION";
	public static final String ANNOTATION_CLASSES = "ANNOTATION_CLASSES";
	public static final String MODIFIERS = "MODIFIERS";
	public static final String IS_RECURSIVE = "IS_RECURSIVE";
	public static final String MARKERS = "MARKERS";
	public static final String MARKER = "MARKER";
	public static final String BUILDER = "BUILDER";
	public static final String LOG_LEVEL = "LOG_LEVEL";
	public static final String LEVEL = "LEVEL";
	public static final String TEMPLATE_FORMAT = "TEMPLATE_FORMAT";
	public static final String FORMAT = "FORMAT";
	public static final String PARAMETER_SEPARATOR = "PARAMETER_SEPARATOR";
	public static final String PARAMETER_FORMAT = "PARAMETER_FORMAT";
	public static final String INPUT = "INPUT";
	public static final String OUTPUT = "OUTPUT";
	public static final String RESOURCE_BUNDLES = "RESOURCE_BUNDLES";
	public static final String RESOURCE_BUNDLE_KEY_VALUE_FOUND_LISTENER = "RESOURCE_BUNDLE_KEY_VALUE_FOUND_LISTENER";
	public static final String BEFORE_EXECUTION_LISTENER = "BEFORE_EXECUTION_LISTENER";
	public static final String AFTER_EXECUTION_LISTENER = "AFTER_EXECUTION_LISTENER";
	public static final String PARAMETER = "PARAMETER";
	public static final String PARAMETERS = "PARAMETERS";
	public static final String CACHABLE = "CACHABLE";
	public static final String LOCALE = "LOCALE";
	public static final String CASE = "CASE";
	public static final String KEY = "KEY";
	public static final String IS_AUTOMATICALLY_ADD_CHOICE_VALUES = "IS_AUTOMATICALLY_ADD_CHOICE_VALUES";
	public static final String CHOICE_VALUE_CLASS_MASTER_FIELD_NAME = "ChoiceValueClassMasterFieldName";
	public static final String FORM_MASTER_OBJECT_ACTION_ON_CLASS_COLLECTION_INSTANCE = "FormMasterObjectActionOnClassCollectionInstance";
	public static final String FORM_MASTER_OBJECT_ACTION_ON_CLASS_COLLECTION_INSTANCE_FIELD_NAME = "FORM_MASTER_OBJECT_ACTION_ON_CLASS_COLLECTION_INSTANCE_FIELD_NAME";
	public static final String CHOICES_IS_SOURCE_DISJOINT = "CHOICES_IS_SOURCE_DISJOINT";
	public static final String MASTER_FIELD_NAME = "MASTER_FIELD_NAME";
	public static final String IS_INSTANCES_LOADABLE_FROM_COLLECTION = "IS_INSTANCES_LOADABLE_FROM_COLLECTION";
	public static final String IS_FOOTER_SHOWABLE = "IS_FOOTER_SHOWABLE";
	public static final String ADD_COMMAND_QUERY_KEY_VALUES = "ADD_COMMAND_QUERY_KEY_VALUES";
	public static final String ROW_PROPERTIES = "ROW_PROPERTIES";
	public static final String REMOVE_COMMAND_PROPERTIES = "REMOVE_COMMAND_PROPERTIES";
	public static final String COLLAPSED = "COLLAPSED";
	public static final String EXPANDED = "EXPANDED";
	public static final String HEADER_RENDERED = "HEADER_RENDERED";
	public static final String CONTENT_TITLE = "CONTENT_TITLE";
	public static final String TOP_LEVEL_CONTAINER = "TOP_LEVEL_CONTAINER";
	public static final String CONTAINER = "CONTAINER";
	public static final String ON_PREPARE_ADD_MENU = "ON_PREPARE_ADD_MENU";
	public static final String ON_PREPARE_ADD_MENU_ADD_COMMAND = "ON_PREPARE_ADD_MENU_ADD_COMMAND";
	public static final String ON_PREPARE_ADD_COLUMN_ORDER_NUMBER = "ON_PREPARE_ADD_COLUMN_ORDER_NUMBER";
	public static final String ON_PREPARE_ADD_COLUMN_ACTION = "ON_PREPARE_ADD_COLUMN_ACTION";
	public static final String FIELD_NAMES_SET_FROM_REQUEST_PARAMETERS = "FIELD_NAMES_SET_FROM_REQUEST_PARAMETERS";
	public static final String REQUEST_PARAMETER_CLASSES = "REQUEST_PARAMETER_CLASSES";
	public static final String INSTANCE = "INSTANCE";
	public static final String MASTER = "MASTER";
	public static final String CONSTRAINTS = "CONSTRAINTS";
	public static final String TIME_ONLY = "TIME_ONLY";
	public static final String PATTERN = "PATTERN";
	public static final String FOOTER_RENDERED = "FOOTER_RENDERED";
	public static final String CELL = "CELL";
	public static final String DATA_TABLE_CELL = "DATA_TABLE_CELL";
	public static final String DATA_TABLE = "DATA_TABLE";
	public static final String DECIMAL_PLACES = "DECIMAL_PLACES";
	public static final String DECIMAL_SEPARATOR = "DECIMAL_SEPARATOR";
	public static final String THOUSAND_SEPARATOR = "THOUSAND_SEPARATOR";
	public static final String PROCESSED_FIELD_NAMES = "PROCESSED_FIELD_NAMES";
	public static final String UPDATED_FIELD_NAMES = "UPDATED_FIELD_NAMES";
	public static final String PROCESSED_COLUMN_FIELD_NAMES = "PROCESSED_COLUMN_FIELD_NAMES";
	public static final String UPDATED_COLUMN_FIELD_NAMES = "UPDATED_COLUMN_FIELD_NAMES";
	public static final String DETAIL = "DETAIL";
	public static final String FORM_DETAIL = "FORM_DETAIL";
	public static final String COMPONENT = "COMPONENT";
	public static final String LISTENER = "LISTENER";
	public static final String METHOD = "METHOD";
	public static final String COMMAND = "COMMAND";
	public static final String OUTPUT_COMPONENT = "OUTPUT_COMPONENT";
	public static final String CELL_LISTENER = "CELL_LISTENER";
	public static final String EVENT_ON_BLUR = "EVENT_ON_BLUR";
	public static final String REMOTE_COMMAND = "REMOTE_COMMAND";
	public static final String AUTO_RUN = "AUTO_RUN";
	public static final String COLUMNS_COLLECTION_INSTANCE_LISTENER_CLASS = "COLUMNS_COLLECTION_INSTANCE_LISTENER_CLASS";
	public static final String ROWS_COLLECTION_INSTANCE_LISTENER_CLASS = "ROWS_COLLECTION_INSTANCE_LISTENER_CLASS";
	public static final String ROWS_COLLECTION_INSTANCE = "ROWS_COLLECTION_INSTANCE";
	public static final String PARENT = "PARENT";
	public static final String CHOICE_VALUE_CLASS = "CHOICE_CLASS";
	
	public static final String PACKAGE_BASE_NAME_SET = "PACKAGE_BASE_NAME_SET";
	public static final String PREFIX_SET = "PREFIX_SET";
	public static final String SUFFIX_SET = "SUFFIX_SET";
	public static final String CLASS_SIMPLE_NAME_SET = "CLASS_SIMPLE_NAME_SET";
	
	public static final String LAZY_MODEL = "LAZY_MODEL";
	public static final String ACTION_ON_CLASS = "ACTION_ON_CLASS";
	
	public static final String ADD_COMMAND_COMPONENT = "ADD_COMMAND_COMPONENT";
	public static final String ADD_COMMAND_COMPONENT_ACTION_ADAPTER_CLASS = "ADD_COMMAND_COMPONENT_ACTION_ADAPTER_CLASS";
	public static final String ADD_INPUT_COMPONENT = "ADD_INPUT_COMPONENT";
	public static final String ADD_INPUT_COMPONENT_VALUE_CLASS = "ADD_INPUT_COMPONENT_VALUE_CLASS";
	public static final String ADD_TEXT_COMPONENT = "ADD_TEXT_COMPONENT";
	
	public static final String REMOVE_COMMAND_COMPONENT = "REMOVE_COMMAND_COMPONENT";
	public static final String REMOVE_COMMAND_COMPONENT_ACTION_ADAPTER_CLASS = "REMOVE_COMMAND_COMPONENT_ACTION_ADAPTER_CLASS";
	
	public static final String FILTER_COMMAND_COMPONENT = "FILTER_COMMAND_COMPONENT";
	public static final String FILTER_INPUT_COMPONENT = "FILTER_INPUT_COMPONENT";
	
	public static final String FILTERABLE = "FILTERABLE";
	public static final String FILTER_FUNCTION = "FILTER_FUNCTION";
	
	public static final String SORTABLE = "SORTABLE";
	public static final String SORT_BY = "SORT_BY";
	public static final String SORT_FUNCTION = "SORT_FUNCTION";
	
	public static final String ON_SORT_EVENT_COMPONENT = "ON_SORT_EVENT_COMPONENT";
	public static final String ON_FILTER_EVENT_COMPONENT = "ON_FILTER_EVENT_COMPONENT";
	public static final String ON_PAGE_EVENT_COMPONENT = "ON_PAGE_EVENT_COMPONENT";
	public static final String LINKED = "LINKED";
	public static final String OUTPUT_LINK_COMPONENT = "OUTPUT_LINK_COMPONENT";
	public static final String OUTPUT_TEXT_COMPONENT = "OUTPUT_TEXT_COMPONENT";
	public static final String URL_READ = "URL_READ";
	public static final String CELLS = "CELLS";
	public static final String FIRST = "FIRST";
	public static final String ROWS_PER_PAGE_LABEL = "ROWS_PER_PAGE_LABEL";
	public static final String ROWS_PER_PAGE_TEMPLATE = "ROWS_PER_PAGE_TEMPLATE";
	
	public static final String DATA = "DATA";
	public static final String ROW = "ROW";
	public static final String COLUMN = "COLUMN";
	
	public static final String ARIA_LABEL = "ARIA_LABEL";
	public static final String COLLAPSED_ICON = "COLLAPSED_ICON";
	public static final String EXPANDED_ICON = "EXPANDED_ICON";
	
	public static final String ON_NODE_CLICK = "ON_NODE_CLICK";
	public static final String SELECTION = "SELECTION";
	public static final String SELECTION_MODE = "SELECTION_MODE";
	public static final String HIGHLIGHT = "HIGHLIGHT";
	public static final String DATA_KEY = "DATA_KEY";
	public static final String ANIMATE = "ANIMATE";
	public static final String ORIENTATION = "ORIENTATION";
	public static final String PROPAGATE_SELECTION_DOWN = "PROPAGATE_SELECTION_DOWN";
	public static final String PROPAGATE_SELECTION_UP = "PROPAGATE_SELECTION_UP";
	public static final String DROPPABLE = "DROPPABLE";
	public static final String DRAG_DROP_SCOPE = "DRAG_DROP_SCOPE";
	public static final String DRAG_MODE = "DRAG_MODE";
	public static final String DROP_RESTRICT = "DROP_RESTRICT";
	public static final String SKIP_CHILDREN = "SKIP_CHILDREN";
	public static final String SKIP_LEAF_HANDLING = "SKIP_LEAF_HANDLING";
	public static final String SHOW_UNSELECTABLE_CHECKBOX = "SHOW_UNSELECTABLE_CHECKBOX";
	public static final String FILTER_BY = "FILTER_BY";
	
	public static final String LAYOUT_CARDINAL_POINT_NORTH_RENDERED = "LAYOUT_CARDINAL_POINT_NORTH_RENDERED";
	public static final String LAYOUT_CARDINAL_POINT_EAST_RENDERED = "LAYOUT_CARDINAL_POINT_EAST_RENDERED";
	public static final String LAYOUT_CARDINAL_POINT_SOUTH_RENDERED = "LAYOUT_CARDINAL_POINT_SOUTH_RENDERED";
	public static final String LAYOUT_CARDINAL_POINT_WEST_RENDERED = "LAYOUT_CARDINAL_POINT_WEST_RENDERED";
	public static final String LAYOUT_CARDINAL_POINT_CENTER_RENDERED = "LAYOUT_CARDINAL_POINT_CENTER_RENDERED";
	public static final String LAYOUT_CARDINAL_POINT_CENTER_SOUTH_RENDERED = "LAYOUT_CARDINAL_POINT_CENTER_SOUTH_RENDERED";
	
	public static final String AT = "AT";
	public static final String BACK_LABEL = "BACK_LABEL";
	public static final String MY = "MY";
	public static final String OVERLAY = "OVERLAY";
	public static final String TRIGGER = "TRIGGER";
	public static final String TRIGGER_EVENT = "TRIGGER_EVENT";
	
	public static final String THUMBNAIL = "THUMBNAIL";
	public static final String RENDER_AS_INPUT = "RENDER_AS_INPUT";
	public static final String MENU_COLUMN = "MENU_COLUMN";
	public static final String FIELD_NAME = "FIELD_NAME";
	public static final String COLUMNS = "COLUMNS";
	public static final String HEADER_TEXT = "HEADER_TEXT";
	public static final String UNIFORM_RESOURCE_LOCATOR = "UNIFORM_RESOURCE_LOCATOR";
	
	public static final String FRAGMENT = "FRAGMENT";
	public static final String DISABLE_CLIENT_WINDOW = "DISABLE_CLIENT_WINDOW";
	public static final String CONTAINER_STYLE = "CONTAINER_STYLE";
	public static final String CONTAINER_STYLE_CLASS = "CONTAINER_STYLE_CLASS";
	public static final String FORM = "FORM";
	public static final String REL = "REL";
	public static final String INCLUDE_VIEW_PARAMS = "INCLUDE_VIEW_PARAMS";
	public static final String OUTCOME = "OUTCOME";
	public static final String RESET_VALUES = "RESET_VALUES";
	public static final String IGNORE_AUTO_UPDATE = "IGNORE_AUTO_UPDATE";
	
	public static final String PARTIAL_SUBMIT_FILTER = "PARTIAL_SUBMIT_FILTER";
	public static final String DELAY = "DELAY";
	public static final String AUTO_DISPLAY = "AUTO_DISPLAY";
	public static final String MODEL = "MODEL";
	public static final String TAB_INDEX = "TAB_INDEX";
	public static final String TOGGLE_EVENT = "TOGGLE_EVENT";
	public static final String STATEFUL = "STATEFUL";
	public static final String ACTIVE_INDEX = "ACTIVE_INDEX";
	
	public static final String MAIN_MENU = "MAIN_MENU";
	public static final String CONTEXT_MENU = "CONTEXT_MENU";
	public static final String TOOL_BAR_MENU = "TOOL_BAR_MENU";
	
	public static final String STREAMED_CONTENT = "STREAMED_CONTENT";
	public static final String CACHE = "CACHE";
	public static final String DIR = "DIR";
	public static final String LANG = "LANG";
	public static final String ALT = "ALT";
	public static final String IS_MAP = "IS_MAP";
	public static final String USE_MAP = "USE_MAP";
	public static final String STREAM = "STREAM";
	public static final String URL = "URL";
	public static final String LIBRARY = "LIBRARY";
	public static final String SRC = "SRC";
	public static final String IMAGE_COMPONENT = "IMAGE_COMPONENT";
	public static final String PREVIEW_IMAGE_COMPONENT = "PREVIEW_IMAGE_COMPONENT";
	public static final String MULTIPLE = "MULTIPLE";
	public static final String SKIN_SIMPLE = "SKIN_SIMPLE";
	public static final String CLEAR_COMMAND = "CLEAR_COMMAND";
	public static final String REMOVE_COMMAND = "REMOVE_COMMAND";
	public static final String PREVIEWABLE = "PREVIEWABLE";
	public static final String CLEARABLE = "CLEARABLE"; 
	public static final String MINIMUM_SIZE = "MINIMUM_SIZE";
	public static final String MAXIMUM_SIZE = "MAXIMUM_SIZE";
	public static final String EXTENSIONS = "EXTENSIONS";
	public static final String ALLOW_TYPES = "ALLOW_TYPES";
	public static final String EXTENSIONS_AS_STRING = "EXTENSIONS_AS_STRING";
	public static final String MODE="MODE";
	public static final String PREVIEW_WIDTH="PREVIEW_WIDTH";
	public static final String PREVIEW_HEIGHT="PREVIEW_HEIGHT";
	
	public static final String SELECT_ITEMS="SELECT_ITEMS";
	public static final String VAR="VAR";
	public static final String ITEM_VALUE="ITEM_VALUE";
	public static final String ITEM_LABEL="ITEM_LABEL"; 
	public static final String ITEM_DESCRIPTION="ITEM_DESCRIPTION";
	public static final String ITEM_DISABLED="ITEM_DISABLED";
	public static final String ITEM_LABEL_ESCAPED="ITEM_LABEL_ESCAPED";
	
	public static final String SELECT_ITEM_WRAPPABLE = "SELECT_ITEM_WRAPPABLE";
	public static final String DUAL_LIST_MODEL = "DUAL_LIST_MODEL";
	public static final String WATERMARK = "WATERMARK";
	public static final String ON_LABEL = "ON_LABEL";
	public static final String ON_ICON = "ON_ICON";
	public static final String OFF_LABEL = "OFF_LABEL";
	public static final String OFF_ICON = "OFF_ICON";
	public static final String ASYNC = "ASYNC";
	public static final String AUTO_SHOW="AUTO_SHOW";
	public static final String CENTER_X="CENTER_X";
	public static final String CENTER_Y="CENTER_Y";
	public static final String CONTENT="CONTENT";
	public static final String CSS="CSS";
	public static final String CSS_OVERLAY="CSS_OVERLAY";
	public static final String EVENT="EVENT";
	public static final String SOURCE="SOURCE";
	public static final String TARGET="TARGET";
	public static final String TIME_OUT="TIME_OUT";
	
	public static final String INTERACTIVITY_BLOCKER="INTERACTIVITY_BLOCKER";
	public static final String ON_DOUBLE_CLICK="ON_DOUBLE_CLICK";
	public static final String ON_MOUSE_OVER="ON_MOUSE_OVER";
	public static final String ON_MOUSE_UP="ON_MOUSE_UP";
	public static final String ON_MOUSE_DOWN="ON_MOUSE_DOWN";
	public static final String ON_MOUSE_OUT="ON_MOUSE_OUT";
	public static final String ON_MOUSE_MOVE="ON_MOUSE_MOVE";
	public static final String ON_KEY_DOWN="ON_KEY_DOWN";
	public static final String ON_KEY_PRESS="ON_KEY_PRESS";
	public static final String ON_KEY_UP="ON_KEY_UP";
	public static final String ON_FOCUS="ON_FOCUS";
	public static final String ON_SELECT="ON_SELECT";
	public static final String ON_BLUR="ON_BLUR";
	
	public static final String PARTIAL_SUBMIT = "PARTIAL_SUBMIT";
	public static final String STRUCTURE_TEMPLATE = "STRUCTURE_TEMPLATE";
	public static final String BUILT_TARGET = "BUILT_TARGET";
	public static final String RENDER_TYPE = "RENDER_TYPE";
	public static final String ON_ERROR = "ON_ERROR";
	public static final String ON_START = "ON_START";
	public static final String ON_SUCCESS = "ON_SUCCESS";
	public static final String SHOW_ICON = "SHOW_ICON";
	public static final String SHOW_SUMMARY = "SHOW_SUMMARY";
	public static final String SHOW_DETAIL = "SHOW_DETAIL";
	public static final String REDISPLAY = "REDISPLAY";
	public static final String GLOBAL_ONLY = "GLOBAL_ONLY";
	public static final String AUTO_UPDATE = "AUTO_UPDATE";
	public static final String DRAGGABLE = "DRAGGABLE";
	public static final String DYNAMIC = "DYNAMIC";
	public static final String FIT_VIEWPORT = "FIT_VIEWPORT";
	public static final String FOCUS = "FOCUS";
	public static final String MAXIMIZABLE = "MAXIMIZABLE";
	public static final String MIN_HEIGHT = "MIN_HEIGHT";
	public static final String MAX_HEIGHT = "MAX_HEIGHT";
	public static final String MINIMIZABLE = "MINIMIZABLE";
	public static final String MIN_WIDTH = "MIN_WIDTH";
	public static final String MAX_WIDTH = "MAX_WIDTH";
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
	public static final String ICON_POS = "ICON_POS";
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
	public static final String IDENTIFIER_AS_STYLE_CLASS = "IDENTIFIER_AS_STYLE_CLASS";
	public static final String POSITION = "POSITION";
	public static final String ENCTYPE = "ENCTYPE";
	public static final String RESIZABLE = "RESIZABLE";
	public static final String CLOSABLE = "CLOSABLE";
	public static final String INCLUDE = "INCLUDE";
	public static final String TEMPLATE = "TEMPLATE";
	public static final String TEMPLATE_IN_FORM = "TEMPLATE_IN_FORM";
	public static final String TEMPLATE_WITHOUT_IDENTIFIER = "TEMPLATE_WITHOUT_IDENTIFIER";
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
	public static final String FIELD = "FIELD";
	public static final String FIELDS = "FIELDS";
	public static final String AUTO = "AUTO";
	public static final String DETECT = "DETECT";
	public static final String NOTIFICATION_BUILDERS = "NOTIFICATION_BUILDERS";
	public static final String NOTIFICATIONS = "NOTIFICATIONS";
	
	public static final String __THIS__ = "__THIS__";
	/**/
	
	private static final Map<Class<?>,Map<Object,Object>> DEFAULT_VALUES = new HashMap<Class<?>,Map<Object,Object>>();
	private static final Set<Class<?>> INHERITED_DEFAULT_VALUES_CLASSES = new HashSet<Class<?>>();
	
	public static void setDefaultValue(Class<?> aClass,Object name,Object value){
		if(aClass!=null && name!=null){
			Map<Object,Object> map = DEFAULT_VALUES.get(aClass);
			if(map == null)
				DEFAULT_VALUES.put(aClass, map = new HashMap<Object, Object>());
			map.put(name, value);	
		}
	}
	
	public static void setDefaultValues(Class<?> aClass,Object[] namesValues,Boolean inherited){
		for(Integer index = 0 ; index < namesValues.length ; index = index + 2)
			setDefaultValue(aClass, namesValues[index], namesValues[index+1]);
		setDefaultValuesInherited(aClass, inherited);
	}
	
	public static void setDefaultValues(Class<?> aClass,Object[] namesValues){
		setDefaultValues(aClass, namesValues, Boolean.TRUE);
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
	
	public static void setDefaultValuesInherited(Class<?> aClass,Boolean value){
		if(Boolean.TRUE.equals(value))
			INHERITED_DEFAULT_VALUES_CLASSES.add(aClass);
		else
			INHERITED_DEFAULT_VALUES_CLASSES.remove(aClass);
	}
	
	public static Boolean isDefaultValuesInherited(Class<?> aClass){
		return INHERITED_DEFAULT_VALUES_CLASSES.contains(aClass);
	}
	
	@Override
	public String toString() {
		return map == null ? null : map.toString();
	}
	
	/**/
	
	public static String getStringValueIdentifier(String layer,String classGroup,String className,String name){
		return String.format(STRING_VALUE_IDENTIFIER_FORMAT,layer,classGroup,className,name);
	}
	/*
	public static String getStringValueFromIdentifier(String layer,String classGroup,String className,String name){
		return StringHelper.getInstance().get(getStringValueIdentifier(layer, classGroup, className, name), new Object[]{});
	}*/
	
	/**/
	
	public static interface Getter {
		
		Object execute(Properties properties,Object key,Object value,Object nullValue);
		
	}

	/**/
	
	/* shortcuts method */

	public Properties computeAndSetPropertyFromStringIdentifier(Object key,String identifier,Object[] parameters){
		//set(key,StringHelper.getInstance().get(identifier, parameters));
		return this;
	}
	
	public Properties computeAndSetPropertyFromStringIdentifier(Object key,String identifier){
		return computeAndSetPropertyFromStringIdentifier(key,identifier,new Object[]{});
	}
	
	public Properties computeAndSetPropertyValueFromStringIdentifier(String identifier,Object[] parameters){
		computeAndSetPropertyFromStringIdentifier(Properties.VALUE, identifier, parameters);
		return this;
	}
	
	public Properties computeAndSetPropertyValueFromStringIdentifier(String identifier){
		return computeAndSetPropertyValueFromStringIdentifier(identifier,new Object[]{});
	}
}
