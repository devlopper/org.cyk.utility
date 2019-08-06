package org.cyk.utility.server.representation.hierarchy;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.collection.CollectionInstance;
import org.cyk.utility.field.FieldHelper;
import org.cyk.utility.instance.InstanceHelper;
import org.cyk.utility.mapping.AbstractMapperSourceDestinationImpl;
import org.cyk.utility.server.representation.AbstractEntityCollection;

public abstract class AbstractNodeMapperImpl<SOURCE,DESTINATION,SOURCE_COLLECTION,DESTINATION_COLLECTION> extends AbstractMapperSourceDestinationImpl<SOURCE, DESTINATION> {
	private static final long serialVersionUID = 1L;
	
    @SuppressWarnings("unchecked")
	public SOURCE_COLLECTION getNodesSources(DESTINATION_COLLECTION destinationCollection) {
    	SOURCE_COLLECTION sourceCollection = null;
    	if(destinationCollection != null) {
    		Class<DESTINATION_COLLECTION> destinationCollectionClass = __getDestinationCollectionClass__();
        	if(DependencyInjection.inject(ClassHelper.class).isInstanceOf(destinationCollectionClass, CollectionInstance.class)) {
        		CollectionInstance<DESTINATION> destinationCollectionInstance = (CollectionInstance<DESTINATION>) destinationCollection;
        		if(DependencyInjection.inject(CollectionHelper.class).isNotEmpty(destinationCollectionInstance)) {
        			sourceCollection = DependencyInjection.inject(__getSourceCollectionClass__());
            		for(DESTINATION index : destinationCollectionInstance.get())
            			if(sourceCollection instanceof AbstractEntityCollection)
            				((AbstractEntityCollection<SOURCE>)sourceCollection).add(getSource(index));
            	}
        	}
    	}
    	return sourceCollection;
    }
    
    @SuppressWarnings("unchecked")
	public DESTINATION_COLLECTION getNodesDestinations(SOURCE_COLLECTION sourceCollection) {
    	DESTINATION_COLLECTION destinationCollection = null;
    	if(sourceCollection != null) {
    		Class<SOURCE_COLLECTION> sourceCollectionClass = __getSourceCollectionClass__();
        	if(DependencyInjection.inject(ClassHelper.class).isInstanceOf(sourceCollectionClass, AbstractEntityCollection.class)) {
        		AbstractEntityCollection<SOURCE> sourceCollectionInstance = (AbstractEntityCollection<SOURCE>) sourceCollection;
        		if(DependencyInjection.inject(CollectionHelper.class).isNotEmpty(sourceCollectionInstance.getCollection())) {
        			destinationCollection = DependencyInjection.inject(__getDestinationCollectionClass__());
        			Class<DESTINATION> destinationClass = __getDestinationClass__();
            		for(SOURCE index : sourceCollectionInstance.getCollection())
            			if(destinationCollection instanceof CollectionInstance) {
            				DESTINATION destination = null;
            				Object identifier = DependencyInjection.inject(FieldHelper.class).getFieldValueSystemIdentifier(index);
            				if(identifier == null) {
            					identifier = DependencyInjection.inject(FieldHelper.class).getFieldValueBusinessIdentifier(index);
            					if(identifier != null)
            						destination = DependencyInjection.inject(InstanceHelper.class).getByIdentifierBusiness(destinationClass, identifier);
            				}else
            					destination = DependencyInjection.inject(InstanceHelper.class).getByIdentifierSystem(destinationClass, identifier);
            				//if(destination == null)
            				//	destination = getDestination(index);
            				if(destination != null)
            					((CollectionInstance<DESTINATION>)destinationCollection).add(destination);
            			}
            	}
        	}
    	}
    	return destinationCollection;
    }
    
    protected abstract Class<SOURCE_COLLECTION> __getSourceCollectionClass__();
    protected abstract Class<DESTINATION_COLLECTION> __getDestinationCollectionClass__();
    protected abstract Class<DESTINATION> __getDestinationClass__();
}