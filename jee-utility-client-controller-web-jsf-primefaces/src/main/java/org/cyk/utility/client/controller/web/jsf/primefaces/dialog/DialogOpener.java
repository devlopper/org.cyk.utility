package org.cyk.utility.client.controller.web.jsf.primefaces.dialog;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.identifier.resource.ParameterName;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.__kernel__.system.action.SystemActionRead;
import org.cyk.utility.__kernel__.value.Value;
import org.primefaces.PrimeFaces;

public interface DialogOpener {

	default Map<String,Object> getOptions(String outcome,Map<String,List<String>> parameters) {
		Map<String,Object> options = new HashMap<>();
        options.put("modal", true);
        options.put("width", "90%");
        options.put("height", "90%");
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%"); 
        return options;
	}
	
	default void open(String outcome,Map<String,List<String>> parameters,Map<String,Object> options) {
		if(StringHelper.isBlank(outcome))
			return;
		if(options == null)
			options = getOptions(outcome, parameters);
		if(parameters == null)
			parameters = new HashMap<String, List<String>>();
		parameters.put("windowrendertype", List.of("windowrendertypedialog")); 
		PrimeFaces.current().dialog().openDynamic(outcome, options, parameters);
	}
	
	default void openByEntityIdentifier(String outcome,String entityIdentifier,Map<String,Object> options) {
		if(StringHelper.isBlank(outcome) || StringHelper.isBlank(entityIdentifier))
			return;
        Map<String,List<String>> parameters = new HashMap<>();
        parameters.put(ParameterName.ENTITY_IDENTIFIER.getValue(), Arrays.asList(entityIdentifier));
		open(outcome, parameters,options);
	}
	
	default void openByEntityIdentifier(String outcome,String entityIdentifier) {
		if(StringHelper.isBlank(outcome) || StringHelper.isBlank(entityIdentifier))
			return;
		openByEntityIdentifier(outcome, entityIdentifier, null);
	}
	
	default void openByEntity(Object entity,Class<? extends SystemAction> systemActionClass,Map<String,Object> options) {
		if(entity == null)
			return;
		if(systemActionClass == null)
			systemActionClass = SystemActionRead.class;
		Object identifier = FieldHelper.readSystemIdentifier(entity);
		if(identifier == null)
			return;
		openByEntityIdentifier("read_dialog",identifier.toString());
	}
	
	default void openByEntity(Object entity) {
		if(entity == null)
			return;
		openByEntity(entity, SystemActionRead.class, null);
	}
	
	/**/
	
	static DialogOpener getInstance() {
		return Helper.getInstance(DialogOpener.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	
}
