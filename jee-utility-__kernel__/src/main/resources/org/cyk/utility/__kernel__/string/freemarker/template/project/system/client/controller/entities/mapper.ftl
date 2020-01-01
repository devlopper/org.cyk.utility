package ${system_package}.${system_identifier}.client.controller.entities;
import ${system_package}.${system_identifier}.server.representation.entities.${entity_class_name}Dto;
import org.cyk.utility.__kernel__.mapping.AbstractMapperSourceDestinationImpl;
import org.mapstruct.Mapper;

@Mapper
public abstract class ${entity_class_name}Mapper extends AbstractMapperSourceDestinationImpl<${entity_class_name}, ${entity_class_name}Dto> {
	private static final long serialVersionUID = 1L;
    	
}