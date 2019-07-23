package org.cyk.utility.server.representation;
import java.io.Serializable;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.collection.CollectionInstance;
import org.cyk.utility.field.FieldHelper;
import org.cyk.utility.instance.InstanceHelper;
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
		sourceClass = (Class<SOURCE>) DependencyInjection.inject(ClassHelper.class).getParameterAt(getClass(), 0, Object.class);
		sourceItemClass = (Class<SOURCE_ITEM>) DependencyInjection.inject(ClassHelper.class).getParameterAt(getClass(), 1, Object.class);
		destinationClass = (Class<DESTINATION>) DependencyInjection.inject(ClassHelper.class).getParameterAt(getClass(), 2, Object.class);
		destinationItemClass = (Class<DESTINATION_ITEM>) DependencyInjection.inject(ClassHelper.class).getParameterAt(getClass(), 3, Object.class);
	}
	
	public SOURCE getSource(DESTINATION destination) {
    	SOURCE source = null;
    	if(destination != null) {
    		if(destination instanceof CollectionInstance) {
        		@SuppressWarnings("unchecked")
    			CollectionInstance<DESTINATION_ITEM> destinationCollection = (CollectionInstance<DESTINATION_ITEM>) destination;
        		source = DependencyInjection.inject(sourceClass);
        		if(source instanceof AbstractEntityCollection) {
        			@SuppressWarnings("unchecked")
    				AbstractEntityCollection<SOURCE_ITEM> sourceCollection = (AbstractEntityCollection<SOURCE_ITEM>) source;
    				sourceCollection.add(DependencyInjection.inject(InstanceHelper.class).buildMany(sourceItemClass, destinationCollection.get()));
        		}else
        			DependencyInjection.inject(ThrowableHelper.class).throwRuntimeExceptionNotYetImplemented("Source of type "+sourceClass);
        	}else
    			DependencyInjection.inject(ThrowableHelper.class).throwRuntimeExceptionNotYetImplemented("Destination of type "+destinationClass);	
    	}
        return source;
    }
	
    @SuppressWarnings("unchecked")
	public DESTINATION getDestination(SOURCE source, @TargetType Class<?> klass) {
    	DESTINATION destination = null;
    	if(source != null) {
    		if(source instanceof AbstractEntityCollection) {
        		AbstractEntityCollection<SOURCE_ITEM> sourceCollection = (AbstractEntityCollection<SOURCE_ITEM>) source;
    			if(Boolean.TRUE.equals(DependencyInjection.inject(CollectionHelper.class).isNotEmpty(sourceCollection.getCollection()))) {
    				destination = DependencyInjection.inject(destinationClass);
    				if(destination instanceof CollectionInstance) {
    					CollectionInstance<DESTINATION_ITEM> destinationCollection = (CollectionInstance<DESTINATION_ITEM>) destination;
    		    		for(SOURCE_ITEM index : sourceCollection.getCollection()) {
    		    			DESTINATION_ITEM destinationItem = null;
    		    			Object identifier = null;
    		    			if(destinationItem == null && (identifier = DependencyInjection.inject(FieldHelper.class).getFieldValueSystemIdentifier(index)) != null)
    		    				destinationItem = DependencyInjection.inject(InstanceHelper.class).getByIdentifierSystem(destinationItemClass, identifier);
    		    			
    		    			if(destinationItem == null && (identifier = DependencyInjection.inject(FieldHelper.class).getFieldValueBusinessIdentifier(index)) != null)
    		    				destinationItem = DependencyInjection.inject(InstanceHelper.class).getByIdentifierBusiness(destinationItemClass, identifier);
    		    			
    		    			if(destinationItem == null)
    		    				destinationItem = DependencyInjection.inject(InstanceHelper.class).buildOne(destinationItemClass, index);
    		    			destinationCollection.add(destinationItem);
    		    		}	
    				}else
    					DependencyInjection.inject(ThrowableHelper.class).throwRuntimeExceptionNotYetImplemented("Destination of type "+destinationClass);
    			}
        	}else
    			DependencyInjection.inject(ThrowableHelper.class).throwRuntimeExceptionNotYetImplemented("Source of type "+sourceClass);	
    	}
    	return destination;
    }

}