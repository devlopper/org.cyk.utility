package ${system_package}.${system_identifier}.server.representation.entities;

import ${system_package}.${system_identifier}.server.persistence.entities.${entity_class_name};
import org.cyk.utility.server.representation.AbstractMapperSourceDestinationImpl;
import org.mapstruct.Mapper;

@Mapper
public abstract class ${entity_class_name}DtoMapper extends AbstractMapperSourceDestinationImpl<${entity_class_name}Dto, ${entity_class_name}> {
	private static final long serialVersionUID = 1L;
     
}