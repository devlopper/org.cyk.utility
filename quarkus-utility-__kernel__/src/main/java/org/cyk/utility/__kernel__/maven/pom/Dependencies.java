package org.cyk.utility.__kernel__.maven.pom;

import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Dependencies {

	private Collection<Dependency> collection;
	
	@XmlElement(name="dependency")
	public Collection<Dependency> getCollection(){
		return collection;
	}
	
	/**/
	
	public Dependency get(String groupdId,String artifactId){
		Dependency dependency = null;
		if(collection != null)
			for(Dependency index : collection)
				if(index.getGroupId().equals(groupdId) && index.getArtifactId().equals(artifactId)){
					dependency = index;
					break;
				}
		return dependency;
	}
}
