package org.cyk.utility.test.maven.pom;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Build {

	private Plugins plugins;
	
	public Plugin getPlugin(String groupdId,String artifactId){
		return plugins == null ? null : plugins.get(groupdId, artifactId);
	}
}
