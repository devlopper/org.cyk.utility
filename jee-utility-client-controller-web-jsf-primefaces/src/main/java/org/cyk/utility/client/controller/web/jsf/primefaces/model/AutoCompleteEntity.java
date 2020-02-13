package org.cyk.utility.client.controller.web.jsf.primefaces.model;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.object.ReadListener;
import org.cyk.utility.client.controller.ControllerEntity;
import org.cyk.utility.client.controller.ControllerLayer;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.ajax.Ajax;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.ajax.AjaxSelectEvent;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.ajax.AjaxUnselectEvent;

import lombok.Getter;
import lombok.Setter;

@Deprecated
public class AutoCompleteEntity<ENTITY> extends AbstractObject implements Serializable {

	public static Integer INITIAL_NUMBER_OF_RESULTS = 10;
	public static Integer QUERY_DELAY = 2000;
	
	@Getter private Class<ENTITY> entityClass;
	private ControllerEntity<ENTITY> controllerEntity;
	private Integer initialNumberOfResults = INITIAL_NUMBER_OF_RESULTS;
	@Getter private Integer numberOfResults = initialNumberOfResults;
	@SuppressWarnings("unused")
	private String queryString;
	@Getter @Setter private String dropdownMode = "current",placeholder,emptyMessage="-- Aucun résultat --";
	@Getter @Setter private Object converter;
	@Getter @Setter private Object value;
	@Getter @Setter private Integer queryDelay = QUERY_DELAY;
	@Getter @Setter private Boolean multiple,dropdown;
	
	@Getter @Setter private ReadListener readItemLabelListener;
	@Getter @Setter private ReadListener readItemValueListener;
	
	@Getter @Setter private AjaxSelectEvent ajaxItemSelect = new AjaxSelectEvent();
	@Getter @Setter private AjaxUnselectEvent ajaxItemUnselect = new AjaxUnselectEvent();
	@Getter @Setter private Ajax ajaxQuery = new Ajax();
	@Getter @Setter private Ajax ajaxMoreText = new Ajax();
	
	@Getter @Setter private Listener<ENTITY> listener;
	
	public AutoCompleteEntity(Class<ENTITY> entityClass,ControllerEntity<ENTITY> controllerEntity) {
		this.entityClass = entityClass;
		this.controllerEntity = controllerEntity;
		if(this.controllerEntity == null) {
			if(this.entityClass != null)
				this.controllerEntity = __inject__(ControllerLayer.class).injectInterfaceClassFromEntityClass(this.entityClass);
		}
		ajaxQuery.setDisabled(Boolean.FALSE);
		ajaxMoreText.setDisabled(Boolean.FALSE);
		/*
		ajaxMoreText.setListener(new AjaxBehaviorEventListener() {
			@Override
			public void listen(AjaxBehaviorEvent event) {
				org.primefaces.component.autocomplete.AutoComplete autoComplete = (org.primefaces.component.autocomplete.AutoComplete) event.getSource();
				autoComplete.setMaxResults(autoComplete.getMaxResults() + initialNumberOfResults);
				if(StringHelper.isBlank(autoComplete.getWidgetVar())) {
					LogHelper.logWarning("auto complete widget var attribute must be defined in order to execute search script", getClass());
					return;
				}
				PrimeFaces.current().executeScript(String.format(SCRIPT_SEARCH, autoComplete.getWidgetVar(),queryString));				
			}
		});
		*/
	}
	
	public AutoCompleteEntity(Class<ENTITY> entityClass) {
		this(entityClass,null);
	}
	
	public Collection<ENTITY> complete(String queryString) {
		this.queryString = queryString;
		if(listener == null) {
			if(controllerEntity == null)
				return null;
			return controllerEntity.readByString(queryString);	
		}else
			return listener.listenComplete(this,queryString);
	}
	
	public Object readItemValue(ENTITY entity) {
		if(readItemValueListener == null)
			return entity;
		return readItemValueListener.read(entity);
	}
	
	public Object readItemLabel(ENTITY entity) {
		if(readItemLabelListener == null)
			return entity;
		return readItemLabelListener.read(entity);
	}
	
	/**/
	
	@SuppressWarnings("unused")
	private static final String SCRIPT_SEARCH = "PF('%s').search('%s')";
	
	/**/
	
	public static interface Listener<ENTITY> {
		
		Collection<ENTITY> listenComplete(AutoCompleteEntity<ENTITY> autoCompleteEntity,String queryString);
		
	}
}
