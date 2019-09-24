package org.cyk.utility.server.representation;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import static org.cyk.utility.__kernel__.DependencyInjection.inject;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.collection.CollectionInstance;
import org.cyk.utility.field.FieldHelper;
import org.cyk.utility.instance.InstanceHelper;
import org.cyk.utility.mapping.MappingHelper;
import org.cyk.utility.server.representation.AbstractEntityCollection;
import org.cyk.utility.throwable.ThrowableHelper;
import org.mapstruct.TargetType;

public abstract class AbstractEntityCollectionMapperImpl<SOURCE,SOURCE_ITEM,DESTINATION,DESTINATION_ITEM> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Class<SOURCE> sourceClass;
	private Class<SOURCE_ITEM> sourceItemClass;
	private Class<DESTINATION> destinationClass;
	private Class<DESTINATION_ITEM> destinationItemClass;
	
	@SuppressWarnings("unchecked")
	public AbstractEntityCollectionMapperImpl() {
		sourceClass = (Class<SOURCE>) ClassHelper.getParameterAt(getClass(), 0);
		sourceItemClass = (Class<SOURCE_ITEM>) ClassHelper.getParameterAt(getClass(), 1);
		destinationClass = (Class<DESTINATION>) ClassHelper.getParameterAt(getClass(), 2);
		destinationItemClass = (Class<DESTINATION_ITEM>) ClassHelper.getParameterAt(getClass(), 3);
	}
	
	@SuppressWarnings("unchecked")
	public SOURCE getSource(DESTINATION destination) {
    	SOURCE source = null;
    	if(destination != null) {
    		if(destination instanceof CollectionInstance) {
        		CollectionInstance<DESTINATION_ITEM> destinationCollection = (CollectionInstance<DESTINATION_ITEM>) destination;
        		source = inject(sourceClass);
        		if(source instanceof AbstractEntityCollection) {
        			AbstractEntityCollection<SOURCE_ITEM> sourceCollection = (AbstractEntityCollection<SOURCE_ITEM>) source;
    				sourceCollection.add(inject(MappingHelper.class).getSources(destinationCollection.get(),sourceItemClass));
        		}else
        			inject(ThrowableHelper.class).throwRuntimeExceptionNotYetImplemented("Source of type "+sourceClass);
        	}else
    			inject(ThrowableHelper.class).throwRuntimeExceptionNotYetImplemented("Destination of type "+destinationClass);	
    	}
        return source;
    }
	
    @SuppressWarnings("unchecked")
	public DESTINATION getDestination(SOURCE source, @TargetType Class<?> klass) {
    	DESTINATION destination = null;
    	if(source != null) {
    		if(source instanceof AbstractEntityCollection) {
        		AbstractEntityCollection<SOURCE_ITEM> sourceCollection = (AbstractEntityCollection<SOURCE_ITEM>) source;
    			if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(sourceCollection.getCollection()))) {
    				destination = inject(destinationClass);
    				if(destination instanceof CollectionInstance) {
    					CollectionInstance<DESTINATION_ITEM> destinationCollection = (CollectionInstance<DESTINATION_ITEM>) destination;
    		    		for(SOURCE_ITEM index : sourceCollection.getCollection()) {
    		    			DESTINATION_ITEM destinationItem = null;
    		    			Object identifier = null;
    		    			if(destinationItem == null && (identifier = org.cyk.utility.__kernel__.field.FieldHelper.readSystemIdentifier(index)) != null)
    		    				destinationItem = inject(InstanceHelper.class).getByIdentifierSystem(destinationItemClass, identifier);
    		    			
    		    			if(destinationItem == null && (identifier = org.cyk.utility.__kernel__.field.FieldHelper.readBusinessIdentifier(index)) != null)
    		    				destinationItem = inject(InstanceHelper.class).getByIdentifierBusiness(destinationItemClass, identifier);
    		    			
    		    			if(destinationItem == null)
    		    				destinationItem = inject(MappingHelper.class).getDestination(index,destinationItemClass);
    		    			
    		    			if(destinationItem != null) {
    		    				destinationCollection.add(Arrays.asList(destinationItem));
    		    			}
    		    		}	
    				}else
    					inject(ThrowableHelper.class).throwRuntimeExceptionNotYetImplemented("Destination of type "+destinationClass);
    			}
        	}else
    			inject(ThrowableHelper.class).throwRuntimeExceptionNotYetImplemented("Source of type "+sourceClass);	
    	}
    	return destination;
    }

}