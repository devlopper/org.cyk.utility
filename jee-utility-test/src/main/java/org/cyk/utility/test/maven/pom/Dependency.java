package org.cyk.utility.test.maven.pom;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Dependency {

	private String groupId;
	private String artifactId;
	private String version;
	
}
