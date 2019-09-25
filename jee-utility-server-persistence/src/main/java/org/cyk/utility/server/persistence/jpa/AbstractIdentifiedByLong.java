package org.cyk.utility.server.persistence.jpa;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.object.__static__.identifiable.AbstractIdentifiedPersistableByLong;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.value.ValueUsageType;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @MappedSuperclass @Access(AccessType.FIELD)
public abstract class AbstractIdentifiedByLong extends AbstractIdentifiedPersistableByLong implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Access(AccessType.PROPERTY) @Id @GeneratedValue
	@Override
	public Long getIdentifier() {
		return super.getIdentifier();
	}
	
	@Override
	public AbstractIdentifiedByLong setIdentifier(Long identifier) {
		return (AbstractIdentifiedByLong) super.setIdentifier(identifier);
	}
	
	/**/
	
	protected <T> T __getFromBusinessIdentifier__(Class<T> aClass,Object identifier){
		return identifier == null ? null : __inject__(org.cyk.utility.server.persistence.Persistence.class).readByIdentifier(aClass,identifier,new Properties().setValueUsageType(ValueUsageType.BUSINESS));
	}
	
	public AbstractIdentifiedByLong setFromBusinessIdentifier(Field field,Object identifier){
		FieldHelper.write(this, field, __getFromBusinessIdentifier__((Class<?>) FieldHelper.getType(field, getClass()), identifier));
		return this;
	}
	
	public AbstractIdentifiedByLong setFromBusinessIdentifier(String fieldName,Object identifier){
		return setFromBusinessIdentifier(FieldHelper.getByName(getClass(), fieldName), identifier);
	}
	
	/**/
	
	 @PrePersist 
	 private void listenPrePersist() {
		 __listenPrePersist__();
	 }
	 
	 protected void __listenPrePersist__() {}
	 
	 /*   
	 @PostPersist void onPostPersist() {}
	    @PostLoad void onPostLoad() {}
	    @PreUpdate void onPreUpdate() {}
	    @PostUpdate void onPostUpdate() {}
	    @PreRemove void onPreRemove() {}
	    @PostRemove void onPostRemove() {}
	    */
}