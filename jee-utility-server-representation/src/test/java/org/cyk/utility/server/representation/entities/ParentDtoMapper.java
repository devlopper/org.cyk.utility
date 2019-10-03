package org.cyk.utility.server.representation.entities;

import java.util.Collection;

import org.cyk.utility.__kernel__.instance.InstanceHelper;
import org.cyk.utility.server.persistence.entities.Child;
import org.cyk.utility.server.persistence.entities.Parent;
import org.cyk.utility.server.representation.AbstractMapperSourceDestinationImpl;
import org.mapstruct.Mapper;

@Mapper
public abstract class ParentDtoMapper extends AbstractMapperSourceDestinationImpl<ParentDto, Parent> {
    private static final long serialVersionUID = 1L;

    public Collection<Child> getChildren(Collection<ChildDto> childrenDtos) {
    	return InstanceHelper.getIdentifiedFromIdentifiers(Child.class, childrenDtos);
    }
    
}