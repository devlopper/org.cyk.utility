package ${system_package}.${system_identifier}.server.business.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import ${system_package}.${system_identifier}.server.business.api.${entity_class_name}Business;
import ${system_package}.${system_identifier}.server.persistence.api.${entity_class_name}Persistence;
import ${system_package}.${system_identifier}.server.persistence.entities.${entity_class_name};
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;

@ApplicationScoped
public class ${entity_class_name}BusinessImpl extends AbstractBusinessEntityImpl<${entity_class_name}, ${entity_class_name}Persistence> implements ${entity_class_name}Business,Serializable {
	private static final long serialVersionUID = 1L;
		
}
