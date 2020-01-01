package ${system_package}.${system_identifier}.server.representation.api;
import javax.ws.rs.Path;

import ${system_package}.${system_identifier}.server.representation.entities.${entity_class_name}Dto;
import org.cyk.utility.server.representation.RepresentationEntity;

@Path(${entity_class_name}Representation.PATH)
public interface ${entity_class_name}Representation extends RepresentationEntity<${entity_class_name}Dto> {
	
	String PATH = "${path}";
	
}
