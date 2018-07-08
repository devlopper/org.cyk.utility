package org.cyk.utility.test.arquillian;

import java.io.Serializable;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.cyk.utility.maven.pom.Pom;
import org.cyk.utility.stream.StreamHelperImpl;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class ArchiveBuilder<ARCHIVE extends Archive<?>> implements Serializable {
	private static final long serialVersionUID = 1L;

	private Class<ARCHIVE> clazz;
	private ARCHIVE archive;
	
	private String beanXml,projectDefaultsYml,persistenceXml,log4j2Xml,pomXml="pom.xml"
			,jbossDeploymentStructureXml;
	
	public ArchiveBuilder(Class<ARCHIVE> clazz) {
		this.clazz = clazz;
		//archive = ShrinkWrap.create(this.clazz);
		//if(archive instanceof JavaArchive)
		//	((JavaArchive)archive).addPackages(Boolean.TRUE,"org.cyk.utility");
	}
	
	public ARCHIVE execute(){
		archive = ShrinkWrap.create(this.clazz);
		addBeanXml(beanXml);
		if(projectDefaultsYml!=null)
			addProjectDefaultsYml(projectDefaultsYml);
		if(persistenceXml!=null)
			addPersistenceXml(persistenceXml);
		if(log4j2Xml!=null)
			addlog4j2Xml(log4j2Xml);
		if(pomXml!=null)
			addPomXml(pomXml);
		if(jbossDeploymentStructureXml!=null)
			addJbossDeploymentStructureXml(jbossDeploymentStructureXml);
		return archive;
	}
	
	private ArchiveBuilder<ARCHIVE> addBeanXml(Object beansXml){
		if(beansXml == null){
			if(archive instanceof JavaArchive)
				((JavaArchive)archive).addAsManifestResource(EmptyAsset.INSTANCE,"beans.xml");
		}else{
			if(beansXml instanceof String)
				if(archive instanceof JavaArchive)
					((JavaArchive)archive).addAsManifestResource((String) beansXml,"beans.xml");
		}
		return this;
	}
	
	private ArchiveBuilder<ARCHIVE> addJbossDeploymentStructureXml(String path){
		((WebArchive)archive).addAsWebInfResource(path,"jboss-deployment-structure.xml");
		return this;
	}
	
	private ArchiveBuilder<ARCHIVE> addPersistenceXml(String path){
		((WebArchive)archive).addAsResource(path,"META-INF/persistence.xml");
		return this;
	}
	
	private ArchiveBuilder<ARCHIVE> addProjectDefaultsYml(String path){
		((WebArchive)archive).addAsResource(path,"project-defaults.yml");
		return this;
	}
	
	private ArchiveBuilder<ARCHIVE> addlog4j2Xml(String path){
		((WebArchive)archive).addAsResource(path,"log4j2.xml");
		return this;
	}
	
	private ArchiveBuilder<ARCHIVE> addPomXml(String path){
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Pom.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			String xml = StreamHelperImpl.__getStringFromFile__(System.getProperty("user.dir")+"/"+path);
			Pom pom = xml == null ? null : (Pom) unmarshaller.unmarshal(new StringReader(xml));
			if(pom != null){
				String version = pom.getVersion() == null ? pom.getParent().getVersion() : pom.getVersion();
				((WebArchive)archive).addAsLibraries(Maven.resolver().resolve(pom.getGroupId()+":"+pom.getArtifactId()+":"+version).withTransitivity().asFile());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}
	
}
