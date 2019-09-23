package org.cyk.utility.server.persistence.jpa;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.object.__static__.identifiable.AbstractIdentifiedPersistableByString;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.value.ValueUsageType;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.field.FieldsGetter;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @MappedSuperclass @Access(AccessType.FIELD)
public abstract class AbstractIdentifiedByString extends AbstractIdentifiedPersistableByString implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Access(AccessType.PROPERTY) @Id @Column(name=COLUMN_IDENTIFIER)
	@Override
	public String getIdentifier() {
		return super.getIdentifier();
	}
	
	@Override
	public AbstractIdentifiedByString setIdentifier(String identifier) {
		return (AbstractIdentifiedByString) super.setIdentifier(identifier);
	}
	
	/**/
	
	protected <T> T __getFromIdentifier__(Class<T> aClass,Object identifier,ValueUsageType valueUsageType){
		return identifier == null ? null : __inject__(org.cyk.utility.server.persistence.Persistence.class).readByIdentifier(aClass,identifier,new Properties().setValueUsageType(valueUsageType));
	}
	
	public AbstractIdentifiedByString setFromIdentifier(Field field,Object identifier,ValueUsageType valueUsageType){
		FieldHelper.write(this, field, __getFromIdentifier__((Class<?>) FieldHelper.getType(field, getClass()), identifier,valueUsageType));
		return this;
	}
	
	public AbstractIdentifiedByString setFromIdentifier(String fieldName,Object identifier,ValueUsageType valueUsageType){
		return setFromIdentifier(CollectionHelper.getFirst(__inject__(FieldsGetter.class).execute(getClass(), fieldName).getOutput()), identifier,valueUsageType);
	}
	
	protected <T> T __getFromBusinessIdentifier__(Class<T> aClass,Object identifier){
		return __getFromIdentifier__(aClass,identifier,ValueUsageType.BUSINESS);
	}
	
	public AbstractIdentifiedByString setFromBusinessIdentifier(Field field,Object identifier){
		setFromIdentifier(field, identifier, ValueUsageType.BUSINESS);
		return this;
	}
	
	public AbstractIdentifiedByString setFromBusinessIdentifier(String fieldName,Object identifier){
		return setFromIdentifier(fieldName, identifier, ValueUsageType.BUSINESS);
	}
	
	protected <T> T __getFromSystemIdentifier__(Class<T> aClass,Object identifier){
		return __getFromIdentifier__(aClass,identifier,ValueUsageType.SYSTEM);
	}
	
	public AbstractIdentifiedByString setFromSystemIdentifier(Field field,Object identifier){
		setFromIdentifier(field, identifier, ValueUsageType.SYSTEM);
		return this;
	}
	
	public AbstractIdentifiedByString setFromSystemIdentifier(String fieldName,Object identifier){
		return setFromIdentifier(fieldName, identifier, ValueUsageType.SYSTEM);
	}
	
	/**/
	
	public static final String COLUMN_IDENTIFIER = FIELD_IDENTIFIER;
}