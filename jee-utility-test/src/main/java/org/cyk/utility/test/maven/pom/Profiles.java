package org.cyk.utility.test.maven.pom;

import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Profiles {

	private Collection<Profile> collection;
	
	@XmlElement(name="profile")
	public Collection<Profile> getCollection(){
		return collection;
	}
	
	/**/
	
	public Profile get(String id){
		Profile profile = null;
		if(collection != null)
			for(Profile index : collection)
				if(index.getId().equals(id)){
					profile = index;
					break;
				}
		return profile;
	}
	
	public Profile getWhereActivationActiveByDefaultIsTrue(){
		Profile profile = null;
		if(collection != null)
			for(Profile index : collection)
				if(Boolean.TRUE.equals(index.getActivationActiveByDefault())){
					profile = index;
					break;
				}
		return profile;
	}
	
	public String getPropertyWhereActivationActiveByDefaultIsTrue(String name){
		Profile profile = getWhereActivationActiveByDefaultIsTrue();
		return profile == null ? null : profile.getProperty(name);
	}
}
