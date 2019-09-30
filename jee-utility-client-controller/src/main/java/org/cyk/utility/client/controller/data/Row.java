package org.cyk.utility.client.controller.data;

import java.util.Collection;

import org.cyk.utility.client.controller.Objectable;
import org.cyk.utility.client.controller.component.command.Commandable;
import org.cyk.utility.object.ObjectByClassMap;
import org.cyk.utility.__kernel__.system.action.SystemAction;

public interface Row extends Objectable {

	RowListeners getListeners();
	RowListeners getListeners(Boolean injectIfNull);
	Row setListeners(RowListeners listeners);
	Row addListeners(Collection<RowListener> listeners);
	Row addListeners(RowListener...listeners);
	
	String getUrlBySystemActionClass(Object request,Class<? extends SystemAction> aClass);
	/*
	ObjectByClassMap getSystemActionIdentifierMap();
	ObjectByClassMap getSystemActionIdentifierMap(Boolean injectIfNull);
	Row setSystemActionIdentifierMap(ObjectByClassMap systemActionIdentifierMap);
	Row setSystemActionIdentifiers(Object...systemActionIdentifiers);
	*/
	ObjectByClassMap getNavigationParametersMap();
	ObjectByClassMap getNavigationParametersMap(Boolean injectIfNull);
	Row setNavigationParametersMap(ObjectByClassMap navigationParametersMap);
	Row setNavigationParameters(Object...navigationParameters);
	
	Commandable getCommandableByIdentifier(Object identifier);
}
