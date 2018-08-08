package org.cyk.utility.__kernel__.maven.pom;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@XmlRootElement(name="project"/*,namespace="http://maven.apache.org/POM/4.0.0"*/) 
@Getter @Setter @ToString
public class Pom implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Parent parent;
	
	private String groupId;
	private String artifactId;
	private String version;
	private String name;
	private Build build;
	
	private Profiles profiles;
	
	/**/
	
	public String getBuildPluginConfigurationSystemProperty(String property,String groupdId,String artifactId){
		String value = null;
		if(build!=null){
			Plugin plugin = build.getPlugin(groupdId, artifactId);
			if(plugin != null && plugin.getConfiguration()!=null){
				value = plugin.getConfiguration().getSystemPropertyVariables().get(property);
			}
		}
		return value;
	}
	
	public String getMavenSurefirePluginConfigurationSystemProperty(String property){
		return getBuildPluginConfigurationSystemProperty(property, "org.apache.maven.plugins", "maven-surefire-plugin");
	}
	
	public Profile getProfileWhereActivationActivateByDefaultValueIsTrue(){
		return profiles == null ? null : profiles.getWhereActivationActiveByDefaultIsTrue();
	}
	
	public String getProfilePropertyWhereActivationActiveByDefaultIsTrue(String name){
		Profile profile = getProfileWhereActivationActivateByDefaultValueIsTrue();
		return profile == null ? null : profile.getProperty(name);
	}
	
	public Profile getProfile(String id){
		return profiles == null ? null : profiles.get(id);
	}
	
	/**/
	
	public static final Pom INSTANCE = PomBuilderImpl.__execute__();
}
