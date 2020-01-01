package ${system_package}.${system_identifier}.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import ${system_package}.${system_identifier}.server.representation.api.${entity_class_name}Representation;
import ${system_package}.${system_identifier}.server.representation.entities.${entity_class_name}Dto;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

@ApplicationScoped
public class ${entity_class_name}RepresentationImpl extends AbstractRepresentationEntityImpl<${entity_class_name}Dto> implements ${entity_class_name}Representation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
