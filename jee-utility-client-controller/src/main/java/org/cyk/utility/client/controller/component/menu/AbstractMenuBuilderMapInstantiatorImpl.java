package org.cyk.utility.client.controller.component.menu;

import java.io.Serializable;
import java.security.Principal;
import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.icon.Icon;
import org.cyk.utility.__kernel__.klass.PersistableClassesGetter;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.request.RequestPrincipalGetter;
import org.cyk.utility.scope.ScopeSession;

public abstract class AbstractMenuBuilderMapInstantiatorImpl extends AbstractObject implements MenuBuilderMapInstantiator,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public MenuBuilderMap instantiate(Object key, Object request, Object window) {
		Principal principal = __inject__(RequestPrincipalGetter.class).setRequest(request).execute().getOutput();
		MenuBuilder sessionMenuBuilder = __instantiateSessionMenuBuilder__(key,request, principal);
		if(Menu.CONTROL_PANEL.equals(key))
			__instantiateSessionMenuBuilderItemsControlPanel__(sessionMenuBuilder, request, principal);
		else
			__instantiateSessionMenuBuilderItems__(key,sessionMenuBuilder,request,principal);
		return __inject__(MenuBuilderMap.class).set(ScopeSession.class,sessionMenuBuilder);
	}
	
	protected MenuBuilder __instantiateSessionMenuBuilder__(Object key,Object request,Principal principal) {
		return __inject__(MenuBuilder.class).setRenderType(__inject__(MenuRenderTypeRowBar.class));
	}
	
	protected void __instantiateSessionMenuBuilderItems__(Object key,MenuBuilder sessionMenuBuilder,Object request,Principal principal) {
		if(principal == null)
			__instantiateSessionMenuBuilderItemsPrincipalIsNull__(key,sessionMenuBuilder,request);
		else
			__instantiateSessionMenuBuilderItemsPrincipalIsNotNull__(key,sessionMenuBuilder,request, principal);
	}
	
	protected void __instantiateSessionMenuBuilderItemsPrincipalIsNull__(Object key,MenuBuilder sessionMenuBuilder,Object request) {
		
	}
	
	protected void __instantiateSessionMenuBuilderItemsPrincipalIsNotNull__(Object key,MenuBuilder sessionMenuBuilder,Object request,Principal principal) {
		
	}
	
	/**/
	
	protected void __instantiateSessionMenuBuilderItemsControlPanel__(MenuBuilder sessionMenuBuilder, Object request,Principal principal) {
		Collection<Class<?>> persistanbleClasses = PersistableClassesGetter.getInstance().get();
		if(CollectionHelper.isEmpty(persistanbleClasses))
			return;
		sessionMenuBuilder.addItems(__inject__(MenuItemBuilder.class).setCommandableName("Panneau de contrôle").setCommandableIcon(Icon.EDIT).addEntitiesList(persistanbleClasses));
	}
	
	/**/
	
	protected static MenuItemBuilder __instantiateMenuItemControlPanel__() {
		return __inject__(MenuItemBuilder.class).setCommandableName("Panneau de contrôle").setCommandableIcon(Icon.SUITCASE)
				.setCommandableNavigationIdentifier("controlPanelIndexView");
	}
	
	protected static MenuItemBuilder __instantiateMenuItemTools__() {
		return __inject__(MenuItemBuilder.class).setCommandableName("Outils").setCommandableIcon(Icon.GEARS)
		.addChild(__instantiateMenuItemControlPanel__());
	}
}
