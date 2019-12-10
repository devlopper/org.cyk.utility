package org.cyk.utility.mapping;

import java.io.Serializable;

import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

@SuppressWarnings("rawtypes") @Deprecated
public abstract class AbstractMapperSourceDestinationGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<MapperSourceDestination> implements MapperSourceDestinationGetter,Serializable {
	private static final long serialVersionUID = 1L;
	
	private Class<?> sourceClass,destinationClass;
	private Object source,destination;
	
	@Override
	protected MapperSourceDestination __execute__() throws Exception {
		Class<?> sourceClass = getSourceClass();
		if(sourceClass == null) {
			Object source = getSource();
			if(source != null)
				sourceClass = source.getClass();
		}
		Class<?> destinationClass = getDestinationClass();
		if(destinationClass == null) {
			Object destination = getDestination();
			if(destination != null)
				destinationClass = destination.getClass();
		}
		sourceClass = ValueHelper.returnOrThrowIfBlank("source class", sourceClass);
		destinationClass = ValueHelper.returnOrThrowIfBlank("destination class", destinationClass);
		return __execute__(sourceClass,destinationClass);
	}
	
	protected MapperSourceDestination __execute__(Class<?> sourceClass,Class<?> destinationClass) {
		Class<?> klass = __inject__(MapperSourceDestinationClassGetter.class).setKlass(sourceClass).execute().getOutput();
		if(klass != null) {
			return (MapperSourceDestination) __inject__(klass);
		}
		throw new RuntimeException(String.format("mapper of %s and %s not found", sourceClass,destinationClass));
	}
	
	@Override
	public Class<?> getSourceClass() {
		return sourceClass;
	}

	@Override
	public MapperSourceDestinationGetter setSourceClass(Class<?> sourceClass) {
		this.sourceClass = sourceClass;
		return this;
	}
	
	@Override
	public Object getSource() {
		return source;
	}
	
	@Override
	public MapperSourceDestinationGetter setSource(Object source) {
		this.source = source;
		return this;
	}

	@Override
	public Class<?> getDestinationClass() {
		return destinationClass;
	}

	@Override
	public MapperSourceDestinationGetter setDestinationClass(Class<?> destinationClass) {
		this.destinationClass = destinationClass;
		return this;
	}
	
	@Override
	public Object getDestination() {
		return destination;
	}
	
	@Override
	public MapperSourceDestinationGetter setDestination(Object destintation) {
		this.destination = destintation;
		return this;
	}
	
}
