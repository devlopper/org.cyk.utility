package org.cyk.utility.client.controller.web.jsf.primefaces.model;

import java.io.Serializable;
import java.util.Collection;

import javax.faces.event.AjaxBehaviorEvent;

import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.object.ReadListener;
import org.cyk.utility.__kernel__.random.RandomHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.client.controller.ControllerEntity;
import org.cyk.utility.client.controller.ControllerLayer;
import org.cyk.utility.client.controller.web.jsf.model.AjaxBehaviorEventListener;
import org.primefaces.PrimeFaces;

import lombok.Getter;
import lombok.Setter;

public class AutoCompleteEntity<ENTITY> extends AbstractObject implements Serializable {

	public static Integer INITIAL_NUMBER_OF_RESULTS = 10;
	public static Integer QUERY_DELAY = 2000;
	
	@Getter private Class<ENTITY> entityClass;
	private ControllerEntity<ENTITY> controllerEntity;
	private Integer initialNumberOfResults = INITIAL_NUMBER_OF_RESULTS;
	@Getter private Integer numberOfResults = initialNumberOfResults;
	private String queryString;
	@Getter @Setter private Object converter;
	@Getter private String widgetVar = RandomHelper.getAlphabetic(5);
	@Getter @Setter private Object value;
	@Getter @Setter private Integer queryDelay = QUERY_DELAY;
	
	@Getter @Setter private ReadListener readItemLabelListener;
	@Getter @Setter private ReadListener readItemValueListener;
	
	@Getter @Setter private AjaxSelectEvent ajaxItemSelect = new AjaxSelectEvent("itemSelect");
	@Getter @Setter private AjaxUnselectEvent ajaxItemUnselect = new AjaxUnselectEvent("itemUnselect");
	@Getter @Setter private Ajax ajaxQuery = new Ajax("query");
	@Getter @Setter private Ajax ajaxMoreText = new Ajax("moreText");
	
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
	
	private static final String SCRIPT_SEARCH = "PF('%s').search('%s')";
	
	/**/
	
	public static interface Listener<ENTITY> {
		
		Collection<ENTITY> listenComplete(AutoCompleteEntity<ENTITY> autoCompleteEntity,String queryString);
		
	}
}
