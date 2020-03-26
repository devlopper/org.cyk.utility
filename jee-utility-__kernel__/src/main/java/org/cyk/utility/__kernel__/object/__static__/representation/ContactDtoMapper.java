package org.cyk.utility.__kernel__.object.__static__.representation;

import org.cyk.utility.__kernel__.mapping.MapperSourceDestination;
import org.cyk.utility.__kernel__.object.__static__.persistence.embeddedable.Contact;
import org.mapstruct.Mapper;

@Mapper
public abstract class ContactDtoMapper extends MapperSourceDestination.AbstractImpl<ContactDto, Contact> {
	private static final long serialVersionUID = 1L;

}
