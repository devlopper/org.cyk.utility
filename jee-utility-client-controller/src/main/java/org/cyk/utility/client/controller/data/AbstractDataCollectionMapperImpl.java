package org.cyk.utility.client.controller.data;
import static org.cyk.utility.__kernel__.DependencyInjection.inject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.mapping.MappingHelper;
import org.cyk.utility.server.representation.AbstractEntityCollection;
import org.cyk.utility.throwable.ThrowableHelper;
import org.mapstruct.TargetType;

public abstract class AbstractDataCollectionMapperImpl<SOURCE,SOURCE_ITEM,DESTINATION,DESTINATION_ITEM> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Class<SOURCE> sourceClass;
	private Class<SOURCE_ITEM> sourceItemClass;
	private Class<DESTINATION> destinationClass;
	private Class<DESTINATION_ITEM> destinationItemClass;
	
	public AbstractDataCollectionMapperImpl() {
		sourceClass = __getSourceClass__();
		sourceItemClass = (Class<SOURCE_ITEM>) inject(ClassHelper.class).getParameterAt(getClass(), 1, Object.class);
		destinationClass = (Class<DESTINATION>) inject(ClassHelper.class).getParameterAt(getClass(), 2, Object.class);
		destinationItemClass = (Class<DESTINATION_ITEM>) inject(ClassHelper.class).getParameterAt(getClass(), 3, Object.class);
	}
	
	protected Class<SOURCE> __getSourceClass__() {
		return (Class<SOURCE>) inject(ClassHelper.class).getParameterAt(getClass(), 0, Object.class);
	}
	
	public SOURCE getSource(DESTINATION destination) {
    	SOURCE source = null;
    	if(destination != null) {
    		if(destination instanceof AbstractEntityCollection) {
    			AbstractEntityCollection<DESTINATION_ITEM> destinationCollection = (AbstractEntityCollection<DESTINATION_ITEM>) destination;
    			if(Boolean.TRUE.equals(inject(ClassHelper.class).isInstanceOf(sourceClass, Collection.class))) {
    				if(Boolean.TRUE.equals(inject(CollectionHelper.class).isNotEmpty(destinationCollection.getCollection()))) {
    					source = (SOURCE) new ArrayList<SOURCE_ITEM>();
        				Collection<SOURCE_ITEM> sourceCollection = (Collection<SOURCE_ITEM>) source;
        				//inject(MappingHelper.class).getSources(destinationCollection.getCollection(), sourceItemClass);
        				sourceCollection.addAll(inject(MappingHelper.class).getSources(destinationCollection.getCollection(), sourceItemClass));	
    				}
        		}else
        			inject(ThrowableHelper.class).throwRuntimeExceptionNotYetImplemented("Source of type "+sourceClass);
        	}else
    			inject(ThrowableHelper.class).throwRuntimeExceptionNotYetImplemented("Destination of type "+destinationClass);	
    	}
        return source;
    }
	
    public DESTINATION getDestination(SOURCE source, @TargetType Class<?> klass) {
    	DESTINATION destination = null;
    	if(source != null) {
    		if(source instanceof Collection) {
    			Collection<SOURCE_ITEM> sourceCollection = (Collection<SOURCE_ITEM>) source;
    			if(Boolean.TRUE.equals(inject(CollectionHelper.class).isNotEmpty(sourceCollection))) {
    				destination = inject(ClassHelper.class).instanciateOne(destinationClass);
    				if(destination instanceof AbstractEntityCollection) {
    					AbstractEntityCollection<DESTINATION_ITEM> destinationCollection = (AbstractEntityCollection<DESTINATION_ITEM>) destination;
    					destinationCollection.add(inject(MappingHelper.class).getDestinations(sourceCollection,destinationItemClass));
    				}else
    					inject(ThrowableHelper.class).throwRuntimeExceptionNotYetImplemented("Destination of type "+destinationClass);
    			}
        	}else
    			inject(ThrowableHelper.class).throwRuntimeExceptionNotYetImplemented("Source of type "+sourceClass);	
    	}
    	return destination;
    }

}