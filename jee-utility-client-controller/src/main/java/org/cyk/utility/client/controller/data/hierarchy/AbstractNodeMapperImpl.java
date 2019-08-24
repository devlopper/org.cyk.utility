package org.cyk.utility.client.controller.data.hierarchy;

import java.util.Collection;

import org.cyk.utility.server.representation.AbstractMapperSourceDestinationImpl;
import org.cyk.utility.server.representation.hierarchy.AbstractNodeCodedAndNamed;

public abstract class AbstractNodeMapperImpl<SOURCE extends DataIdentifiedByString<SOURCE>,DESTINATION extends AbstractNodeCodedAndNamed<DESTINATION,?>,DESTINATION_COLLECTION> extends AbstractMapperSourceDestinationImpl<SOURCE, DESTINATION> {
	private static final long serialVersionUID = 1L;
 
	@Override
	protected void __listenGetDestinationAfter__(SOURCE source, DESTINATION destination) {
		super.__listenGetDestinationAfter__(source, destination);
		Collection<SOURCE> parents = source.get__parents__();
		if(parents != null && !parents.isEmpty()) {
			for(SOURCE index : parents) {
				destination.addParents(getDestination(index));
			}
		}
	}
	
	/*private Class<DESTINATION_COLLECTION>
	
	@SuppressWarnings("unchecked")
	@PostConstruct
    public void __listenPostConstruct__() {
		if(__destinationClass__ == null) {
			ClassNameBuilder classNameBuilder = DependencyInjection.inject(ClassNameBuilder.class).setKlass(getClass());
			classNameBuilder.getSourceNamingModel(Boolean.TRUE).server().representation().entities().setSuffix("DtoMapperImpl");
			classNameBuilder.getDestinationNamingModel(Boolean.TRUE).server().persistence().entities().suffix();
			__destinationClass__ = DependencyInjection.inject(ValueHelper.class).returnOrThrowIfBlank("persistence entity class"
					,(Class<DESTINATION>) DependencyInjection.inject(ClassHelper.class).getByName(classNameBuilder));
		}
		
		ClassInstance classInstance = DependencyInjection.inject(ClassInstancesRuntime.class).get(__destinationClass__);
		__isDestinationPersistable__ = Boolean.TRUE.equals(classInstance.getIsPersistable());
		__isDestinationProjectionable__ = Boolean.TRUE.equals(DependencyInjection.inject(ValueHelper.class).defaultToIfNull(classInstance.getIsProjectionable(),Boolean.TRUE));
		__isDestinationActionable__ = Boolean.TRUE.equals(DependencyInjection.inject(ValueHelper.class).defaultToIfNull(classInstance.getIsActionable(),Boolean.TRUE));
		if(Boolean.TRUE.equals(__isDestinationActionable__)) {			
			if(__isDestinationPersistable__) {
				__actionsIdentifiers__ = DependencyInjection.inject(Strings.class);
				__actionsIdentifiers__.add(Action.IDENTIFIER_READ,Action.IDENTIFIER_UPDATE,Action.IDENTIFIER_DELETE);
			}
			HttpServletRequest request = DependencyInjection.inject(HttpServletRequest.class);
			__resourcePath__ = StringUtils.substringAfter(request.getRequestURI(), request.getContextPath());
			__resourcePath__ = StringUtils.removeStart(__resourcePath__, "/");
			__resourcePath__ = StringUtils.substringBefore(__resourcePath__,"/");	
		}
    }*/
	
	@SuppressWarnings("unchecked")
	public Collection<Object> getNodesSources(DESTINATION_COLLECTION destinationCollection) {
		Collection<Object> sourceCollection = null;
    	/*if(destinationCollection != null) {
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
    	}*/
    	return sourceCollection;
    }
    
    @SuppressWarnings("unchecked")
	public DESTINATION_COLLECTION getNodesDestinations(Collection<Object> sourceCollection) {
    	DESTINATION_COLLECTION destinationCollection = null;
    	/*if(sourceCollection != null) {
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
    	*/
    	return destinationCollection;
    }
    
    //protected abstract Class<DESTINATION_COLLECTION> __getDestinationCollectionClass__();
    //protected abstract Class<DESTINATION> __getDestinationClass__();
    
	/*@Override
	protected Class<DESTINATION_COLLECTION> __getDestinationCollectionClass__() {
		return PrivilegeDtoCollection.class;
	}
	
	@Override
	protected Class<DESTINATION> __getDestinationClass__() {
		return PrivilegeDto.class;
	}*/
	
}