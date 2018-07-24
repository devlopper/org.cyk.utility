package org.cyk.utility.server.persistence.jpa;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.cyk.utility.__kernel__.object.__static__.identifiable.AbstractIdentifiedPersistableByLong;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.field.FieldGetter;
import org.cyk.utility.field.FieldTypeGetter;
import org.cyk.utility.field.FieldValueSetter;
import org.cyk.utility.value.ValueUsageType;

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
		return identifier == null ? null : __inject__(Persistence.class).readOne(aClass,identifier,new Properties().setValueUsageType(ValueUsageType.BUSINESS));
	}
	
	public AbstractIdentifiedByLong setFromBusinessIdentifier(Field field,Object identifier){
		Class<?> type = __inject__(FieldTypeGetter.class).execute(field).getOutput();
		__inject__(FieldValueSetter.class).setObject(this).setField(field).setValue(__getFromBusinessIdentifier__(type, identifier)).execute();
		return this;
	}
	
	public AbstractIdentifiedByLong setFromBusinessIdentifier(String fieldName,Object identifier){
		return setFromBusinessIdentifier(__inject__(CollectionHelper.class).getFirst(__inject__(FieldGetter.class).execute(getClass(), fieldName).getOutput()), identifier);
	}
}