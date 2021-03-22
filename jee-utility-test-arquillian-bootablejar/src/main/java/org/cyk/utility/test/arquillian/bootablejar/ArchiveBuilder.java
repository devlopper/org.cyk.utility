package org.cyk.utility.test.arquillian.bootablejar;

import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

import lombok.Setter;
import lombok.experimental.Accessors;

public class ArchiveBuilder {

	@Setter @Accessors(chain = true) private Class<? extends Archive<?>> archiveClass = WebArchive.class;
	@Setter @Accessors(chain = true) private String archiveName = "test.war";
	@Setter @Accessors(chain = true) private Boolean cdiEnabled = Boolean.TRUE;
	@Setter @Accessors(chain = true) private Boolean persistenceEnabled;
	@Setter @Accessors(chain = true) private String persistenceCreationScriptName;
	@Setter @Accessors(chain = true) private String pomXmlName= "pom_arquillian.xml";
	@Setter @Accessors(chain = true) private String webXmlName;
	@Setter @Accessors(chain = true) private Collection<String> rootPackagesNames;
	@Setter @Accessors(chain = true) private Collection<Class<?>> classes;
	
	/**/
	
	private Archive<?> archive;

	public ArchiveBuilder instantiate() {
		if(archive != null)
			return this;
		archive = ShrinkWrap.create(archiveClass, archiveName);
		return this;
	}
	
	public Archive<?> build() {
        instantiate();
        enableCdi();
        enablePersistence();
        addPackages();
        addClasses();
        addDependencies();
        setWebXml();
        return archive;
    }
	
	private void enableCdi() {
		if(Boolean.TRUE.equals(cdiEnabled)) {
			WebArchive webArchive = (WebArchive) archive;
	        webArchive.addAsResource("META-INF/beans.xml");
	        webArchive.addAsResource("META-INF/MANIFEST.MF");
		}
	}
	
	private void enablePersistence() {
		if(Boolean.TRUE.equals(persistenceEnabled)) {
			WebArchive webArchive = (WebArchive) archive;
			webArchive.addAsResource("memory/persistence.xml","META-INF/persistence.xml");
	        webArchive.addAsResource("memory/data.sql","META-INF/data.sql");
		}
	}
	
	private void addDependencies() {
		if(StringHelper.isBlank(pomXmlName))
			return;
		WebArchive webArchive = (WebArchive) archive;   
        webArchive.addAsLibraries(Maven.resolver().loadPomFromFile(pomXmlName).importCompileAndRuntimeDependencies().resolve().withTransitivity().asFile());
	}
	
	/**/
	
	private Collection<String> getRootPackagesNames(Boolean injectIfNull) {
		if(rootPackagesNames == null && Boolean.TRUE.equals(injectIfNull))
			rootPackagesNames = new ArrayList<>();
		return rootPackagesNames;
	}
	
	public ArchiveBuilder addPackages(Collection<String> rootPackagesNames) {
		if(CollectionHelper.isEmpty(rootPackagesNames))
			return this;
		getRootPackagesNames(Boolean.TRUE).addAll(rootPackagesNames);
		return this;
	}
	
	public ArchiveBuilder addPackages(String...rootPackagesNames) {
		if(ArrayHelper.isEmpty(rootPackagesNames))
			return this;
		return addPackages(CollectionHelper.listOf(rootPackagesNames));
	}
	
	public ArchiveBuilder addPackages(Package...rootPackages) {
		if(ArrayHelper.isEmpty(rootPackages))
			return this;
		for(Package rootPackage : rootPackages)
			addPackages(rootPackage.getName());
		return this;
	}
	
	private void addPackages() {
		if(CollectionHelper.isEmpty(rootPackagesNames))
			return;
		WebArchive webArchive = (WebArchive) archive; 
		webArchive.addPackages(Boolean.TRUE, rootPackagesNames.toArray(new String[] {}));
	}
	
	/**/
	
	private Collection<Class<?>> getClasses(Boolean injectIfNull) {
		if(classes == null && Boolean.TRUE.equals(injectIfNull))
			classes = new ArrayList<>();
		return classes;
	}
	
	public ArchiveBuilder addClasses(Collection<Class<?>> classes) {
		if(CollectionHelper.isEmpty(classes))
			return this;
		getClasses(Boolean.TRUE).addAll(classes);
		return this;
	}
	
	public ArchiveBuilder addClasses(Class<?>...classes) {
		if(ArrayHelper.isEmpty(classes))
			return this;
		return addClasses(CollectionHelper.listOf(classes));
	}
	
	private void addClasses() {
		if(CollectionHelper.isEmpty(classes))
			return;
		WebArchive webArchive = (WebArchive) archive;   
        webArchive.addClasses(classes.toArray(new Class<?>[] {}));
	}
	
	/**/
	
	private void setWebXml() {
		if(StringHelper.isBlank(webXmlName))
			return;
		WebArchive webArchive = (WebArchive) archive;   
        webArchive.setWebXML(webXmlName);
	}
}