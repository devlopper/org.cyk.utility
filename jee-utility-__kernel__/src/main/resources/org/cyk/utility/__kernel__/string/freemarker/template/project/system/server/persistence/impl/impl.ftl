package ${system_package}.${system_identifier}.server.persistence.impl;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import ${system_package}.${system_identifier}.server.persistence.api.${entity_class_name}Persistence;
import ${system_package}.${system_identifier}.server.persistence.entities.Title;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;

@ApplicationScoped
public class ${entity_class_name}PersistenceImpl extends AbstractPersistenceEntityImpl<${entity_class_name}> implements ${entity_class_name}Persistence,Serializable {
	private static final long serialVersionUID = 1L;

	
}