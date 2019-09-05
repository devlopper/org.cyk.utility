package org.cyk.utility.client.controller.component;

import java.io.Serializable;

import org.cyk.utility.bean.Property;
import org.cyk.utility.client.controller.AbstractObject;
import org.cyk.utility.client.controller.component.layout.LayoutItem;
import org.cyk.utility.client.controller.event.Events;
import org.cyk.utility.object.Objects;

public abstract class AbstractComponentImpl extends AbstractObject implements Component,Serializable {
	private static final long serialVersionUID = 1L;
	
	private LayoutItem layoutItem;
	private ComponentRoles roles;
	private Objects updatables;
	private ComponentBuilder<?> builder;
	private Events events;
	private Throwable throwable;
	private String throwableInternalizationMessage;
	private String getByIdentifierExpressionLanguageFormat;
	private Object linkedTo;
	private Property targetBinding,targetModel;
	
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
	public Property getTargetModel() {
		return targetModel;
	}
	
	@Override
	public Component setTargetModel(Property targetModel) {
		this.targetModel = targetModel;
		return this;
	}
	
	@Override
	public Property getTargetModel(Boolean injectIfNull) {
		return (Property) __getInjectIfNull__(FIELD_TARGET_MODEL, injectIfNull);
	}
	
	@Override
	public Object getTargetModelValue() {
		Property property = getTargetModel();
		return property == null ? null : property.read();
	}
	
	@Override
	public Property getTargetBinding() {
		return targetBinding;
	}
	
	@Override
	public Component setTargetBinding(Property targetBinding) {
		this.targetBinding = targetBinding;
		return this;
	}
	
	@Override
	public Property getTargetBinding(Boolean injectIfNull) {
		return (Property) __getInjectIfNull__(FIELD_TARGET_BINDING, injectIfNull);
	}
	
	@Override
	public Object getTargetBindingValue() {
		Property property = getTargetBinding();
		return property == null ? null : property.read();
	}
	
	@Override
	public void setTargetBindingValue(Object targetBindingValue) {
		getTargetBinding(Boolean.TRUE).setValue(targetBindingValue);
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
	
	@Override
	public String getGetByIdentifierExpressionLanguageFormat() {
		return getByIdentifierExpressionLanguageFormat;
	}
	
	@Override
	public Component setGetByIdentifierExpressionLanguageFormat(String getByIdentifierExpressionLanguageFormat) {
		this.getByIdentifierExpressionLanguageFormat = getByIdentifierExpressionLanguageFormat;
		return this;
	}
	
	@Override
	public Object getLinkedTo() {
		return linkedTo;
	}
	
	@Override
	public Component setLinkedTo(Object linkedTo) {
		this.linkedTo = linkedTo;
		return this;
	}
	
	/**/
	
	public static final String FIELD_ROLES = "roles";
	public static final String FIELD_UPDATABLES = "updatables";
	public static final String FIELD_EVENTS = "events";
	public static final String FIELD_TARGET_MODEL = "targetModel";
	public static final String FIELD_TARGET_BINDING = "targetBinding";
}
