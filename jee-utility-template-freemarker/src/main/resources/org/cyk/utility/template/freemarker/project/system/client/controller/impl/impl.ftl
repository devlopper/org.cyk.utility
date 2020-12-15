package ${system_package}.${system_identifier}.client.controller.api;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import ${system_package}.${system_identifier}.client.controller.entities.${entity_class_name};
import org.cyk.utility.client.controller.AbstractControllerEntityImpl;

@ApplicationScoped
public class ${entity_class_name}ControllerImpl extends AbstractControllerEntityImpl<${entity_class_name}> implements ${entity_class_name}Controller,Serializable {
	private static final long serialVersionUID = 1L;
	
}
