package org.cyk.utility.__kernel__.object.__static__.persistence;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;

import org.cyk.utility.__kernel__.object.marker.IdentifiableSystem;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
@MappedSuperclass @Access(AccessType.FIELD)
public abstract class AbstractIdentifiableSystemImpl<IDENTIFIER> extends AbstractObjectImpl implements IdentifiableSystem<IDENTIFIER>,Serializable {
	private static final long serialVersionUID = 1L;
	
	@Transient protected String asString;
	
	protected abstract IDENTIFIER getIdentifier();
	
	protected abstract AbstractIdentifiableSystemImpl<IDENTIFIER> setIdentifier(IDENTIFIER identifier);
	
	@Override
	public IDENTIFIER getSystemIdentifier() {
		return getIdentifier();
	}

	@Override
	public IdentifiableSystem<IDENTIFIER> setSystemIdentifier(IDENTIFIER identifier) {
		setIdentifier(identifier);
		return this;
	}
	
	@Override
	public String toString() {
		IDENTIFIER identifier = getIdentifier();
		if(identifier == null)
			return super.toString();
		return identifier.toString();
	}
	
	/**/
	
	@PrePersist
	public void __listenBeforeCreation__() {
		EntityLifeCycleListener.getInstance().listen(this, EntityLifeCycleListener.Event.CREATE, EntityLifeCycleListener.When.BEFORE);
	}
	
	@PostPersist
	public void __listenAfterCreation__() {
		EntityLifeCycleListener.getInstance().listen(this, EntityLifeCycleListener.Event.CREATE, EntityLifeCycleListener.When.AFTER);
	}
	
	@PostLoad
	public void __listenAfterRead__() {
		EntityLifeCycleListener.getInstance().listen(this, EntityLifeCycleListener.Event.READ, EntityLifeCycleListener.When.AFTER);
	}
	
	@PreUpdate
	public void __listenBeforeUpdate__() {
		EntityLifeCycleListener.getInstance().listen(this, EntityLifeCycleListener.Event.UPDATE, EntityLifeCycleListener.When.BEFORE);
	}
	
	@PostUpdate
	public void __listenAfterUpdate__() {
		EntityLifeCycleListener.getInstance().listen(this, EntityLifeCycleListener.Event.UPDATE, EntityLifeCycleListener.When.AFTER);
	}
	
	@PreRemove
	public void __listenBeforeDeletion__() {
		EntityLifeCycleListener.getInstance().listen(this, EntityLifeCycleListener.Event.DELETE, EntityLifeCycleListener.When.BEFORE);
	}
	
	@PostRemove
	public void __listenAfterDeletion__() {
		EntityLifeCycleListener.getInstance().listen(this, EntityLifeCycleListener.Event.DELETE, EntityLifeCycleListener.When.AFTER);
	}
	
	/**/
	
	public static final String FIELD_IDENTIFIER = "identifier";
	public static final String FIELD_AS_STRING = "asString";
}