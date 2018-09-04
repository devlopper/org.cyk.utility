package org.cyk.utility.__kernel__.test.arquillian;

import java.io.File;
import java.io.Serializable;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.KernelHelperImpl;
import org.cyk.utility.__kernel__.maven.pom.Pom;
import org.cyk.utility.__kernel__.maven.pom.Profile;
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
		System.out.println("Building archive starts");
		String testProfileIdentifier = "org.cyk.test";
		Profile profile = Pom.INSTANCE.getProfile(testProfileIdentifier);
		if(profile == null){
			System.out.println("No test profile found under id "+testProfileIdentifier);
		}
		
		if(StringUtils.isBlank(beanXml) && profile!=null)
			beanXml = profile.getProperty("org.cyk.test.cdi.beans.file");
		
		if(StringUtils.isBlank(projectDefaultsYml) && profile!=null)
			projectDefaultsYml = profile.getProperty("org.cyk.test.swarm.project.defaults.file");
		
		if(StringUtils.isBlank(persistenceXml) && profile!=null)
			persistenceXml = profile.getProperty("org.cyk.test.jpa.persistence.file");
		
		String _package = profile == null ? null : profile.getProperty("org.cyk.test.package");
		String[] classesArray = profile == null ? null : StringUtils.isBlank(profile.getProperty("org.cyk.test.classes")) ? null : profile.getProperty("org.cyk.test.classes").split(",");
		String[] resourcesFoldersArray = profile == null ? null : StringUtils.isBlank(profile.getProperty("org.cyk.test.resources.folders")) ? null : profile.getProperty("org.cyk.test.resources.folders").split(",");
		
		//System.out.println("Swarm project defaults file : "+projectDefaultsYml);
		//System.out.println("JPA persistence file : "+persistenceXml);
		
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
		
		if(StringUtils.isNotBlank(_package)){
			if(archive instanceof WebArchive)
				((WebArchive)archive).addPackages(Boolean.TRUE, _package);
		}
		
		String[] suffixes = {"Persistence","PersistenceImpl","Business","BusinessImpl","Representation","RepresentationImpl","Impl","Dto","DtoCollection"
				,"AssertionsProvider","AssertionsProviderImpl"};
		Set<Class<?>> classes = new HashSet<>();
		if(classesArray!=null)
			for(String index : classesArray){
				try {
					Class<?> aClass = Class.forName(index);
					classes.add(aClass);
					if(!StringUtils.endsWithAny(aClass.getName(), suffixes)) {
						for(String suffix : suffixes)
							try {
								Class<?> relatedClass = Class.forName(aClass.getName()+suffix);
								classes.add(relatedClass);
							} catch (Exception e) {}
					}
					//if(archive instanceof WebArchive)
					//	((WebArchive)archive).addClass(aClass);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		
		if(classes!=null && !classes.isEmpty()) {
			System.out.println("#classes derived : "+classes.size());
			if(archive instanceof WebArchive)
				((WebArchive)archive).addClasses(classes.toArray(new Class<?>[] {}));
		}
		
		/* Resources */
		
		Set<String> resources = new HashSet<>();
		if(resourcesFoldersArray!=null) {
			String javaResourceFolder = System.getProperty("user.dir")+"\\src\\test\\resources\\";
			for(String indexDirectory : resourcesFoldersArray){
				for(File indexFile : FileUtils.listFiles(new File(javaResourceFolder+indexDirectory), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE))
					resources.add(StringUtils.substringAfter(indexFile.getPath(), javaResourceFolder));
			}
		}
		if(resources!=null && !resources.isEmpty()) {
			System.out.println("#resources derived : "+resources.size());
			for(String index : resources) {
				index = StringUtils.replace(index, "\\", "/");
				((WebArchive) archive).addAsResource(index,index);
			}
		}
		
		System.out.println("Building archive done.");
		return archive;
	}
	
	private ArchiveBuilder<ARCHIVE> addBeanXml(Object beansXml){
		if(beansXml == null){
			if(archive instanceof JavaArchive)
				((JavaArchive)archive).addAsManifestResource(EmptyAsset.INSTANCE,"beans.xml");
			else if(archive instanceof WebArchive){
				//((WebArchive)archive).addAsManifestResource(EmptyAsset.INSTANCE,"beans.xml");
				((WebArchive)archive).addAsWebInfResource(EmptyAsset.INSTANCE,"beans.xml");
			}
		}else{
			if(beansXml instanceof String)
				if(archive instanceof JavaArchive)
					((JavaArchive)archive).addAsManifestResource((String) beansXml,"beans.xml");
				else if(archive instanceof WebArchive){
					((WebArchive)archive).addAsManifestResource((String) beansXml,"beans.xml");
					((WebArchive)archive).addAsWebInfResource((String) beansXml,"beans.xml");
					//((WebArchive)archive).addAsResource((String) beansXml,"META-INF/beans.xml");
				}
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
			String xml = KernelHelperImpl.__getStringFromFile__(System.getProperty("user.dir")+"/"+path);
			Pom pom = xml == null ? null : (Pom) unmarshaller.unmarshal(new StringReader(xml));
			if(pom == null){
				System.out.println("Pom no found : "+(System.getProperty("user.dir")+"/"+path));
			}else {
				String version = pom.getVersion() == null ? pom.getParent().getVersion() : pom.getVersion();
				((WebArchive)archive).addAsLibraries(Maven.resolver().resolve(pom.getGroupId()+":"+pom.getArtifactId()+":"+version).withTransitivity().asFile());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}
	
}
