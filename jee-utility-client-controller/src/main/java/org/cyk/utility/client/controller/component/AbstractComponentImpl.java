package org.cyk.utility.client.controller.component;

import java.io.Serializable;

import org.cyk.utility.client.controller.AbstractObject;
import org.cyk.utility.client.controller.component.layout.LayoutItem;

public abstract class AbstractComponentImpl extends AbstractObject implements Component,Serializable {
	private static final long serialVersionUID = 1L;
	
	private LayoutItem layoutItem;
	private ComponentRoles roles;
	private Object targetModel;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		__inject__(ComponentPostConstructListener.class).setObject(this).execute();
	}
	
	@Override
	public LayoutItem getLayoutItem() {
		return layoutItem;
	}
	
	@Override
	public Component setLayoutItem(LayoutItem layoutItem) {
		this.layoutItem = layoutItem;
		return this;
	}
	
	@Override
	public ComponentRoles getRoles() {
		return roles;
	}
	@Override
	public Component setRoles(ComponentRoles roles) {
		this.roles = roles;
		return this;
	}
	
	@Override
	public ComponentRoles getRoles(Boolean injectIfNull) {
		return (ComponentRoles) __getInjectIfNull__(FIELD_ROLES, injectIfNull);
	}
	
	@Override
	public Object getTargetModel() {
		return targetModel;
	}
	
	@Override
	public void setTargetModel(Object targetModel) {
		this.targetModel = targetModel;
		//return this;
	}
	
	/**/
	
	public static final String FIELD_ROLES = "roles";
}
