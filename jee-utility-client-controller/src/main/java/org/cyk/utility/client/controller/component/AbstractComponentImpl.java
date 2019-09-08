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
		if(roles == null && Boolean.TRUE.equals(injectIfNull))
			roles = __inject__(ComponentRoles.class);
		return roles;
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
		if(targetModel == null && Boolean.TRUE.equals(injectIfNull))
			targetModel = __inject__(Property.class);
		return targetModel;
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
		if(targetBinding == null && Boolean.TRUE.equals(injectIfNull))
			targetBinding = __inject__(Property.class);
		return targetBinding;
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
		if(updatables == null && Boolean.TRUE.equals(injectIfNull))
			updatables = __inject__(Objects.class);
		return updatables;
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
		if(events == null && Boolean.TRUE.equals(injectIfNull))
			events = __inject__(Events.class);
		return events;
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
	
}
