package org.cyk.utility.client.controller.component;

import java.io.Serializable;

import org.cyk.utility.client.controller.AbstractObject;
import org.cyk.utility.client.controller.component.layout.LayoutItem;
import org.cyk.utility.object.Objects;

public abstract class AbstractComponentImpl extends AbstractObject implements Component,Serializable {
	private static final long serialVersionUID = 1L;
	
	private LayoutItem layoutItem;
	private ComponentRoles roles;
	private Object targetModel;
	Boolean isTargetModelBuilt;
	private Objects updatables;
	
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
		if(targetModel == null) {
			if(!Boolean.TRUE.equals(getIsTargetModelBuilt())) {
				setTargetModel(__inject__(ComponentTargetModelBuilder.class).setComponent(this).execute().getOutput());
				setIsTargetModelBuilt(Boolean.TRUE);
			}	
		}
		return targetModel;
	}
	
	@Override
	public void setTargetModel(Object targetModel) {
		this.targetModel = targetModel;
		//return this;
	}
	
	@Override
	public Objects getUpdatables() {
		return updatables;
	}
	
	@Override
	public Objects getUpdatables(Boolean injectIfNull) {
		return (Objects) __getInjectIfNull__(FIELD_UPDATABLES, injectIfNull);
	}
	
	@Override
	public Component setUpdatables(Objects updatables) {
		this.updatables = updatables;
		return this;
	}
	
	@Override
	public Boolean getIsTargetModelBuilt() {
		return isTargetModelBuilt;
	}
	
	@Override
	public Component setIsTargetModelBuilt(Boolean isTargetModelBuilt) {
		this.isTargetModelBuilt = isTargetModelBuilt;
		return this;
	}
	
	/**/
	
	public static final String FIELD_ROLES = "roles";
	public static final String FIELD_UPDATABLES = "updatables";
}
