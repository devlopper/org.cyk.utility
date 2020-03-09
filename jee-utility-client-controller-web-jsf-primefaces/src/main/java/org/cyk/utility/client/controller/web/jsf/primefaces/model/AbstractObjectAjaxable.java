package org.cyk.utility.client.controller.web.jsf.primefaces.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.ajax.Ajax;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractObjectAjaxable extends AbstractObject implements Serializable {

	protected Map<String,Ajax> ajaxes;
	protected BlockUI blockUI;
	
	/**/
	
	public static final String FIELD_AJAXES = "ajaxes";
	
	/**/
	
	public Map<String,Ajax> getAjaxes(Boolean injectIfNull) {
		if(ajaxes == null && Boolean.TRUE.equals(injectIfNull))
			ajaxes = new HashMap<>();
		return ajaxes;
	}
	
	public AbstractObjectAjaxable addAjaxes(Collection<Map<Object,Object>> argumentsMaps) {
		if(CollectionHelper.isEmpty(argumentsMaps))
			return this;
		for(Map<Object,Object> argumentsMap : argumentsMaps) {
			Map<Object,Object> __argumentsMap__ = argumentsMap == null ? new HashMap<>() : new HashMap<>(argumentsMap);
			MapHelper.writeByKey(__argumentsMap__, Ajax.ConfiguratorImpl.FIELD_BLOCK_UI,blockUI, Boolean.FALSE);
			MapHelper.writeByKey(__argumentsMap__, Ajax.ConfiguratorImpl.FIELD_RUNNER_ARGUMENTS_SUCCESS_MESSAGE_ARGUMENTS_NULLABLE,Boolean.TRUE, Boolean.FALSE);
			Ajax ajax = Builder.build(Ajax.class,__argumentsMap__);
			if(ajax != null)
				getAjaxes(Boolean.TRUE).put(ajax.getEvent(), ajax);
		}
		return this;
	}
	
	public AbstractObjectAjaxable addAjaxes(@SuppressWarnings("unchecked") Map<Object,Object>...argumentsMaps) {
		if(ArrayHelper.isEmpty(argumentsMaps))
			return this;
		return addAjaxes(CollectionHelper.listOf(argumentsMaps));
	}
	
	/**/
	
	public static final String FIELD_BLOCK_UI = "blockUI";
	
	/**/
	
	public static abstract class AbstractConfiguratorImpl<AJAXABLE extends AbstractObjectAjaxable> extends AbstractObject.AbstractConfiguratorImpl<AJAXABLE> implements Serializable {

		@Override
		public void configure(AJAXABLE ajaxable, Map<Object, Object> arguments) {
			super.configure(ajaxable, arguments);			
			if(ajaxable.blockUI == null)
				ajaxable.blockUI = Builder.build(BlockUI.class, Map.of(BlockUI.FIELD_BLOCK,ajaxable.identifier,BlockUI.FIELD_TRIGGER,ajaxable.identifier));		
			
			@SuppressWarnings("unchecked")
			Collection<Map<Object,Object>> ajaxesArguments = (Collection<Map<Object, Object>>) MapHelper.readByKey(arguments, FIELD_AJAXES_ARGUMENTS);
			if(CollectionHelper.isNotEmpty(ajaxesArguments))
				ajaxable.addAjaxes(ajaxesArguments);
		}
		
		/**/
		
		public static final String FIELD_AJAXES_ARGUMENTS = "ajaxesArguments";
	}	
}