package org.cyk.utility.field;

import java.io.Serializable;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public abstract class AbstractFieldValueCopyFieldsGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Fields> implements FieldValueCopyFieldsGetter,Serializable {
	private static final long serialVersionUID = 1L;

	private Class<?> sourceClass,destinationClass;
	private FieldsGetter fieldGetter;
	private FieldInstanceValue source,destination;
	
	@Override
	protected Fields __execute__() throws Exception {
		Fields fields = null;
		Class<?> sourceClass = getSourceClass();
		//Class<?> destinationClass = getSourceClass();
		FieldsGetter fieldGetter = getFieldGetter(Boolean.TRUE);
		if(fieldGetter.getClazz() == null)
			fieldGetter.setClazz(sourceClass);		
		fields = fieldGetter.execute().getOutput();
		
		if(CollectionHelper.isNotEmpty(fields))
			fields.removeModifierStatic().removeModifierFinal();
		fields = CollectionHelper.isEmpty(fields) ? null : fields;
		return fields;
	}
	
	@Override
	public Class<?> getSourceClass() {
		return sourceClass;
	}
	
	@Override
	public FieldValueCopyFieldsGetter setSourceClass(Class<?> sourceClass) {
		this.sourceClass = sourceClass;
		return this;
	}
	
	@Override
	public Class<?> getDestinationClass() {
		return destinationClass;
	}
	
	@Override
	public FieldValueCopyFieldsGetter setDestinationClass(Class<?> destinationClass) {
		this.destinationClass = destinationClass;
		return this;
	}
	
	@Override
	public FieldsGetter getFieldGetter() {
		return fieldGetter;
	}
	
	@Override
	public FieldsGetter getFieldGetter(Boolean injectIfNull) {
		return (FieldsGetter) __getInjectIfNull__(FIELD_FIELD_GETTER, injectIfNull);
	}
	
	@Override
	public FieldValueCopyFieldsGetter setFieldGetter(FieldsGetter fieldGetter) {
		this.fieldGetter = fieldGetter;
		return this;
	}
	
	@Override
	public FieldInstanceValue getSource() {
		return source;
	}

	@Override
	public FieldValueCopyFieldsGetter setSource(FieldInstanceValue source) {
		this.source = source;
		return this;
	}

	@Override
	public FieldInstanceValue getDestination() {
		return destination;
	}

	@Override
	public FieldValueCopyFieldsGetter setDestination(FieldInstanceValue destination) {
		this.destination = destination;
		return this;
	}

	/**/
	
	private static final String FIELD_FIELD_GETTER = "fieldGetter";
	
}
