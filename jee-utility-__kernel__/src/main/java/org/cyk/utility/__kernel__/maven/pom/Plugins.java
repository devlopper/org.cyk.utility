package org.cyk.utility.__kernel__.maven.pom;

import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Plugins {

	private Collection<Plugin> collection;
	
	@XmlElement(name="plugin")
	public Collection<Plugin> getCollection(){
		return collection;
	}
	
	/**/
	
	public Plugin get(String groupdId,String artifactId){
		Plugin plugin = null;
		if(collection != null)
			for(Plugin index : collection)
				if(index.getGroupId().equals(groupdId) && index.getArtifactId().equals(artifactId)){
					plugin = index;
					break;
				}
		return plugin;
	}
}
