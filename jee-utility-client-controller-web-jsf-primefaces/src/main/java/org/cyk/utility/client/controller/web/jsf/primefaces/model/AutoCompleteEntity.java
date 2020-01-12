package org.cyk.utility.client.controller.web.jsf.primefaces.model;

import java.io.Serializable;
import java.util.Collection;

import javax.faces.event.AjaxBehaviorEvent;

import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.random.RandomHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.client.controller.ControllerEntity;
import org.cyk.utility.client.controller.ControllerLayer;
import org.primefaces.PrimeFaces;

import lombok.Getter;
import lombok.Setter;

public class AutoCompleteEntity<ENTITY> extends AbstractObject implements Serializable {

	@Getter private Class<ENTITY> entityClass;
	private ControllerEntity<ENTITY> controllerEntity;
	private Integer initialNumberOfResults = 5;
	@Getter private Integer numberOfResults = initialNumberOfResults;
	private String query;
	@Getter @Setter private Object converter;
	@Getter private String widgetVar = RandomHelper.getAlphabetic(5);
	
	public AutoCompleteEntity(Class<ENTITY> entityClass,ControllerEntity<ENTITY> controllerEntity) {
		this.entityClass = entityClass;
		this.controllerEntity = controllerEntity;
		if(this.controllerEntity == null) {
			if(this.entityClass != null)
				this.controllerEntity = __inject__(ControllerLayer.class).injectInterfaceClassFromEntityClass(this.entityClass);
		}
	}
	
	public AutoCompleteEntity(Class<ENTITY> entityClass) {
		this(entityClass,null);
	}
	
	public Collection<ENTITY> complete(String query) {
		this.query = query;
		if(controllerEntity == null)
			return null;
		return controllerEntity.readByString(query);
	}
	
	public void loadMore(AjaxBehaviorEvent event) {
	    org.primefaces.component.autocomplete.AutoComplete autoComplete = (org.primefaces.component.autocomplete.AutoComplete) event.getSource();
	    autoComplete.setMaxResults(autoComplete.getMaxResults() + initialNumberOfResults);
	    if(StringHelper.isBlank(autoComplete.getWidgetVar())) {
	    	LogHelper.logWarning("auto complete widget var attribute must be defined in order to execute search script", getClass());
	    	return;
	    }
	    PrimeFaces.current().executeScript(String.format(SCRIPT_SEARCH, autoComplete.getWidgetVar(),query));
	}
	
	public Object readItemValue(ENTITY entity) {
		return entity;
	}
	
	public Object readItemLabel(ENTITY entity) {
		return entity;
	}
	
	/**/
	
	private static final String SCRIPT_SEARCH = "PF('%s').search('%s')";
}
