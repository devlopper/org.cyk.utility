package org.cyk.utility.controller;
import org.cyk.utility.controller.AbstractMapperSourceDestinationImpl;
import org.cyk.utility.controller.server.api.DataTypeDto;
import org.mapstruct.Mapper;

@Mapper
public abstract class DataTypeMapper extends AbstractMapperSourceDestinationImpl<DataType, DataTypeDto> {
	private static final long serialVersionUID = 1L;
    	
}