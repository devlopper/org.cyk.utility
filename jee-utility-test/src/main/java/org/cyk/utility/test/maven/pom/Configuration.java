package org.cyk.utility.test.maven.pom;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Configuration {

	private Map<String, String> systemPropertyVariables = new HashMap<>();

    @XmlElement(name="systemPropertyVariables")
    @XmlJavaTypeAdapter(MapAdapter.class)
    public Map<String, String> getSystemPropertyVariables(){
        return systemPropertyVariables;
    }
}
