package org.cyk.utility.common.helper;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.common.Constant;
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
			return get(ONCLICK);
		}
		
		public Attributes setOnClick(String onClick){
			set(ONCLICK, onClick);
			return this;
		}
		
		public String getOnChange(){
			return get(ONCHANGE);
		}
		
		public Attributes setOnChange(String onChange){
			set(ONCHANGE, onChange);
			return this;
		}
		
		public String getOnComplete(){
			return get(ONCOMPLETE);
		}
		
		public Attributes setOnComplete(String onComplete){
			set(ONCOMPLETE, onComplete);
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
			set(FILTER_MATCH_MODE, label);
			return this;
		}
		
		public String getPlaceholder(){
			return get(PLACE_HOLDER);
		}
		
		public Attributes setPlaceholder(String placeHolder){
			set(PLACE_HOLDER, placeHolder);
			return this;
		}
		
		public static final String CLASS = "CLASS";
		public static final String IDENTIFIER = "IDENTIFIER";
		public static final String ONCLICK = "ONCLICK";
		public static final String ONCHANGE = "ONCHANGE";
		public static final String ONCOMPLETE = "ONCOMPLETE";
		public static final String STYLE = "STYLE";
		public static final String STYLE_CLASS = "STYLE_CLASS";
		public static final String TITLE = "TITLE";
		public static final String TYPE = "TYPE";
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
		
	}
}
