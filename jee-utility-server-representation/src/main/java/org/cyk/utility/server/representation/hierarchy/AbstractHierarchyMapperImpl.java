package org.cyk.utility.server.representation.hierarchy;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.mapping.MapperSourceDestination;
import org.cyk.utility.server.representation.AbstractMapperSourceDestinationImpl;

public abstract class AbstractHierarchyMapperImpl<SOURCE,DESTINATION,NODE_SOURCE,NODE_DESTINATION,NODE_MAPPER extends MapperSourceDestination<NODE_SOURCE, NODE_DESTINATION>> extends AbstractMapperSourceDestinationImpl<SOURCE, DESTINATION> {
	private static final long serialVersionUID = 1L;
    
	private Class<NODE_MAPPER> nodeMapperClass;
	
	public NODE_SOURCE getNodeSource(NODE_DESTINATION destination) {
    	return destination == null ? null : __getNodeMapper__().getSource(destination);
    }
    
    public NODE_DESTINATION getNodeDestination(NODE_SOURCE source) {
    	return source == null ? null : __getNodeMapper__().getDestination(source);
    }

    @SuppressWarnings("unchecked")
	protected Class<NODE_MAPPER> __getNodeMapperClass__() {
		if(nodeMapperClass == null)
			nodeMapperClass = (Class<NODE_MAPPER>) ClassHelper.getByName(ClassHelper.getParameterAt(getClass(), 4).getName());
    	return nodeMapperClass;
    }
    
    protected NODE_MAPPER __getNodeMapper__() {
    	return DependencyInjection.inject(__getNodeMapperClass__());
    }
}