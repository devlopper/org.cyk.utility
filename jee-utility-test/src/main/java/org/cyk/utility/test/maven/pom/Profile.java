package org.cyk.utility.test.maven.pom;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Profile {

	private String id;
	private Activation activation;	
	private Dependencies dependencies;
	private Map<String, String> properties = new HashMap<>();

	public Boolean getActivationActiveByDefault(){
		return activation!=null && activation.getActiveByDefault();
	}
	
	@XmlElement(name="properties")
    @XmlJavaTypeAdapter(MapAdapter.class)
    public Map<String, String> getProperties(){
        return properties;
    }
	
	public String getProperty(String name){
		return properties.get(name);
	}
	
	public Collection<Dependency> getDependenciesCollection(){
		return dependencies == null ? null : dependencies.getCollection();
	}
}
