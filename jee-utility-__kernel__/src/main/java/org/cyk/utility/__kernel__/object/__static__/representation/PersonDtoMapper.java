package org.cyk.utility.__kernel__.object.__static__.representation;

import org.cyk.utility.__kernel__.mapping.AbstractMapperSourceDestinationImpl;
import org.cyk.utility.__kernel__.object.__static__.persistence.embeddedable.Person;
import org.mapstruct.Mapper;

@Mapper
public abstract class PersonDtoMapper extends AbstractMapperSourceDestinationImpl<PersonDto, Person> {
	private static final long serialVersionUID = 1L;

}
