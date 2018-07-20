package org.cyk.utility.__kernel__.maven.pom;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@XmlRootElement(name="project",namespace="http://maven.apache.org/POM/4.0.0") @Getter @Setter @ToString
public class Pom implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Parent parent;
	
	private String groupId;
	private String artifactId;
	private String version;
	
}
