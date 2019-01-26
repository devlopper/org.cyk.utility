package org.cyk.utility.client.controller.component;

import java.io.Serializable;

import org.cyk.utility.client.controller.AbstractObject;
import org.cyk.utility.client.controller.component.layout.LayoutItem;
import org.cyk.utility.client.controller.event.Events;
import org.cyk.utility.object.Objects;

public abstract class AbstractComponentImpl extends AbstractObject implements Component,Serializable {
	private static final long serialVersionUID = 1L;
	
	private LayoutItem layoutItem;
	private ComponentRoles roles;
	private Object targetModel;
	private Boolean isTargetModelBuilt;
	private Objects updatables;
	private ComponentBuilder<?> builder;
	private Events events;
	private Throwable throwable;
	private String throwableInternalizationMessage;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		__inject__(ComponentPostConstructListener.class).setObject(this).execute();
	}
	
	@Override
	public ComponentBuilder<?> getBuilder() {
		return builder;
	}
	
	@Override
	public Component setBuilder(ComponentBuilder<?> builder) {
		this.builder = builder;
		return this;
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
	public Events getEvents() {
		return events;
	}
	
	@Override
	public Events getEvents(Boolean injectIfNull) {
		return (Events) __getInjectIfNull__(FIELD_EVENTS, injectIfNull);
	}
	
	@Override
	public Component setEvents(Events events) {
		this.events = events;
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
	
	@Override
	public Throwable getThrowable() {
		return throwable;
	}
	
	@Override
	public Component setThrowable(Throwable throwable) {
		this.throwable = throwable;
		return this;
	}
	
	@Override
	public String getThrowableInternalizationMessage() {
		return throwableInternalizationMessage;
	}
	
	@Override
	public Component setThrowableInternalizationMessage(String throwableInternalizationMessage) {
		this.throwableInternalizationMessage = throwableInternalizationMessage;
		return this;
	}
	
	/**/
	
	public static final String FIELD_ROLES = "roles";
	public static final String FIELD_UPDATABLES = "updatables";
	public static final String FIELD_EVENTS = "events";
}
