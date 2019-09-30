package org.cyk.utility.client.controller.data;
import static org.cyk.utility.__kernel__.DependencyInjection.inject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.mapping.MappingHelper;
import org.cyk.utility.server.representation.AbstractEntityCollection;
import org.mapstruct.TargetType;

@Deprecated
public abstract class AbstractDataCollectionMapperImpl<SOURCE,SOURCE_ITEM,DESTINATION,DESTINATION_ITEM> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Class<SOURCE> sourceClass;
	private Class<SOURCE_ITEM> sourceItemClass;
	private Class<DESTINATION> destinationClass;
	private Class<DESTINATION_ITEM> destinationItemClass;
	
	public AbstractDataCollectionMapperImpl() {
		sourceClass = __getSourceClass__();
		sourceItemClass = (Class<SOURCE_ITEM>) ClassHelper.getParameterAt(getClass(), 1);
		destinationClass = (Class<DESTINATION>) ClassHelper.getParameterAt(getClass(), 2);
		destinationItemClass = (Class<DESTINATION_ITEM>) ClassHelper.getParameterAt(getClass(), 3);
	}
	
	protected Class<SOURCE> __getSourceClass__() {
		return (Class<SOURCE>) ClassHelper.getParameterAt(getClass(), 0);
	}
	
	public SOURCE getSource(DESTINATION destination) {
    	SOURCE source = null;
    	if(destination != null) {
    		if(destination instanceof AbstractEntityCollection) {
    			AbstractEntityCollection<DESTINATION_ITEM> destinationCollection = (AbstractEntityCollection<DESTINATION_ITEM>) destination;
    			if(Boolean.TRUE.equals(ClassHelper.isInstanceOf(sourceClass, Collection.class))) {
    				if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(destinationCollection.getCollection()))) {
    					source = (SOURCE) new ArrayList<SOURCE_ITEM>();
        				Collection<SOURCE_ITEM> sourceCollection = (Collection<SOURCE_ITEM>) source;
        				//inject(MappingHelper.class).getSources(destinationCollection.getCollection(), sourceItemClass);
        				sourceCollection.addAll(inject(MappingHelper.class).getSources(destinationCollection.getCollection(), sourceItemClass));	
    				}
        		}else
        			ThrowableHelper.throwNotYetImplemented("Source of type "+sourceClass);
        	}else
        		ThrowableHelper.throwNotYetImplemented("Destination of type "+destinationClass);	
    	}
        return source;
    }
	
    public DESTINATION getDestination(SOURCE source, @TargetType Class<?> klass) {
    	DESTINATION destination = null;
    	if(source != null) {
    		if(source instanceof Collection) {
    			Collection<SOURCE_ITEM> sourceCollection = (Collection<SOURCE_ITEM>) source;
    			if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(sourceCollection))) {
    				destination = ClassHelper.instanciate(destinationClass);
    				if(destination instanceof AbstractEntityCollection) {
    					AbstractEntityCollection<DESTINATION_ITEM> destinationCollection = (AbstractEntityCollection<DESTINATION_ITEM>) destination;
    					destinationCollection.add(inject(MappingHelper.class).getDestinations(sourceCollection,destinationItemClass));
    				}else
    					ThrowableHelper.throwNotYetImplemented("Destination of type "+destinationClass);
    			}
        	}else
        		ThrowableHelper.throwNotYetImplemented("Source of type "+sourceClass);	
    	}
    	return destination;
    }

}