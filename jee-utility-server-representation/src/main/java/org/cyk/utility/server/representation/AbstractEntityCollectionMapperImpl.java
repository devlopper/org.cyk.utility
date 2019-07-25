package org.cyk.utility.server.representation;
import java.io.Serializable;

import static org.cyk.utility.__kernel__.DependencyInjection.inject;
import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.collection.CollectionInstance;
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
		sourceClass = (Class<SOURCE>) inject(ClassHelper.class).getParameterAt(getClass(), 0, Object.class);
		sourceItemClass = (Class<SOURCE_ITEM>) inject(ClassHelper.class).getParameterAt(getClass(), 1, Object.class);
		destinationClass = (Class<DESTINATION>) inject(ClassHelper.class).getParameterAt(getClass(), 2, Object.class);
		destinationItemClass = (Class<DESTINATION_ITEM>) inject(ClassHelper.class).getParameterAt(getClass(), 3, Object.class);
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
    			if(Boolean.TRUE.equals(inject(CollectionHelper.class).isNotEmpty(sourceCollection.getCollection()))) {
    				destination = inject(destinationClass);
    				if(destination instanceof CollectionInstance) {
    					CollectionInstance<DESTINATION_ITEM> destinationCollection = (CollectionInstance<DESTINATION_ITEM>) destination;
    		    		for(SOURCE_ITEM index : sourceCollection.getCollection()) {
    		    			DESTINATION_ITEM destinationItem = null;
    		    			Object identifier = null;
    		    			if(destinationItem == null && (identifier = inject(FieldHelper.class).getFieldValueSystemIdentifier(index)) != null)
    		    				destinationItem = inject(InstanceHelper.class).getByIdentifierSystem(destinationItemClass, identifier);
    		    			
    		    			if(destinationItem == null && (identifier = inject(FieldHelper.class).getFieldValueBusinessIdentifier(index)) != null)
    		    				destinationItem = inject(InstanceHelper.class).getByIdentifierBusiness(destinationItemClass, identifier);
    		    			
    		    			if(destinationItem == null)
    		    				destinationItem = inject(MappingHelper.class).getDestination(index,destinationItemClass);
    		    			destinationCollection.add(destinationItem);
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