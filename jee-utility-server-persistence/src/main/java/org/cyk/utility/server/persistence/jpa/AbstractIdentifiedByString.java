package org.cyk.utility.server.persistence.jpa;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.object.__static__.identifiable.AbstractIdentifiedPersistableByString;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.value.ValueUsageType;

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
	
	@JsonbTransient
	public AbstractIdentifiedByString __setFromIdentifier__(Field field,Object identifier,ValueUsageType valueUsageType){
		FieldHelper.write(this, field, __getFromIdentifier__((Class<?>) FieldHelper.getType(field, getClass()), identifier,valueUsageType));
		return this;
	}
	
	@JsonbTransient
	public AbstractIdentifiedByString __setFromIdentifier__(String fieldName,Object identifier,ValueUsageType valueUsageType){
		return __setFromIdentifier__(FieldHelper.getByName(getClass(), fieldName), identifier,valueUsageType);
	}
	
	protected <T> T __getFromBusinessIdentifier__(Class<T> aClass,Object identifier){
		return __getFromIdentifier__(aClass,identifier,ValueUsageType.BUSINESS);
	}
	
	@JsonbTransient
	public AbstractIdentifiedByString __setFromBusinessIdentifier__(Field field,Object identifier){
		__setFromIdentifier__(field, identifier, ValueUsageType.BUSINESS);
		return this;
	}
	
	@JsonbTransient
	public AbstractIdentifiedByString __setFromBusinessIdentifier__(String fieldName,Object identifier){
		return __setFromIdentifier__(fieldName, identifier, ValueUsageType.BUSINESS);
	}
	
	protected <T> T __getFromSystemIdentifier__(Class<T> aClass,Object identifier){
		return __getFromIdentifier__(aClass,identifier,ValueUsageType.SYSTEM);
	}
	
	@JsonbTransient
	public AbstractIdentifiedByString __setFromSystemIdentifier__(Field field,Object identifier){
		__setFromIdentifier__(field, identifier, ValueUsageType.SYSTEM);
		return this;
	}
	
	@JsonbTransient
	public AbstractIdentifiedByString __setFromSystemIdentifier__(String fieldName,Object identifier){
		return __setFromIdentifier__(fieldName, identifier, ValueUsageType.SYSTEM);
	}
	
	/**/
	
	public static final String COLUMN_IDENTIFIER = FIELD_IDENTIFIER;
}