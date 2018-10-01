package org.cyk.utility.server.representation.deployment.web;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.cyk.utility.server.representation.AbstractEntityFromPersistenceEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@XmlRootElement @Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class MyEntityDto extends AbstractEntityFromPersistenceEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String name;

}
