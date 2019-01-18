package org.cyk.utility.field;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false,of= {"sourceClass","destinationClass"})
public class FieldValueCopyDefinitionKeyImpl extends AbstractObject implements FieldValueCopyDefinitionKey,Serializable{
	private static final long serialVersionUID = 1L;

	private Class<?> sourceClass;
	private Class<?> destinationClass;

	@Override
	public Class<?> getSourceClass() {
		return sourceClass;
	}
	
	@Override
	public FieldValueCopyDefinitionKey setSourceClass(Class<?> sourceClass) {
		this.sourceClass = sourceClass;
		return this;
	}
	
	@Override
	public Class<?> getDestinationClass() {
		return destinationClass;
	}
	
	@Override
	public FieldValueCopyDefinitionKey setDestinationClass(Class<?> destinationClass) {
		this.destinationClass = destinationClass;
		return this;
	}
	
	/**/
	
	public static final String FIELD_SOURCE_CLASS = "sourceClass";
	public static final String FIELD_DESTINATION_CLASS = "destinationClass";
	
	
}
