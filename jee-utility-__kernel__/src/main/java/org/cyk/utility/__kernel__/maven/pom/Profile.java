package org.cyk.utility.__kernel__.maven.pom;

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
}
