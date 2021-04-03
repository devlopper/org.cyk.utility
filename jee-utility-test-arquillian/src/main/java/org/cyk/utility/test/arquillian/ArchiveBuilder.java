package org.cyk.utility.test.arquillian;

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

	@Setter @Accessors(chain = true) protected Class<? extends Archive<?>> archiveClass = WebArchive.class;
	@Setter @Accessors(chain = true) protected Mode mode;
	@Setter @Accessors(chain = true) protected String archiveName;
	@Setter @Accessors(chain = true) protected Boolean cdiEnabled = Boolean.TRUE;
	@Setter @Accessors(chain = true) protected Boolean persistenceEnabled;
	@Setter @Accessors(chain = true) protected String persistenceCreationScriptName;
	@Setter @Accessors(chain = true) protected String pomXmlName;
	@Setter @Accessors(chain = true) protected Boolean webConfigEnabled;
	@Setter @Accessors(chain = true) protected String webXmlName;
	@Setter @Accessors(chain = true) protected Collection<String> rootPackagesNames;
	@Setter @Accessors(chain = true) protected Collection<Class<?>> classes;
	
	/**/
	
	protected Archive<?> archive;

	protected String buildFileName(String blankValue,String name,String extension) {
		if(StringHelper.isBlank(name))
			name = blankValue;
		if(mode != null)
			name = name + "_" + mode.name().toLowerCase();
		if(StringHelper.isNotBlank(extension)) {
			if(!name.endsWith("."+extension))
				name = name + "."+extension;
		}
		return name;
	}
	
	public ArchiveBuilder instantiate() {
		if(archive != null)
			return this;
		archiveName = buildFileName("test",archiveName,"war");
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
	
	protected void enableCdi() {
		if(Boolean.TRUE.equals(cdiEnabled)) {
			WebArchive webArchive = (WebArchive) archive;
	        webArchive.addAsResource("META-INF/beans.xml");
	        webArchive.addAsResource("META-INF/MANIFEST.MF");
		}
	}
	
	protected void enablePersistence() {
		if(Boolean.TRUE.equals(persistenceEnabled)) {
			WebArchive webArchive = (WebArchive) archive;
			webArchive.addAsResource("memory/persistence.xml","META-INF/persistence.xml");
	        webArchive.addAsResource("memory/data.sql","META-INF/data.sql");
		}
	}
	
	protected void addDependencies() {
		pomXmlName = buildFileName("pom_arquillian",pomXmlName,"xml");
		if(StringHelper.isBlank(pomXmlName))
			return;
		WebArchive webArchive = (WebArchive) archive;   
        webArchive.addAsLibraries(Maven.resolver().loadPomFromFile(pomXmlName).importCompileAndRuntimeDependencies().resolve().withTransitivity().asFile());
	}
	
	/**/
	
	protected Collection<String> getRootPackagesNames(Boolean injectIfNull) {
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
	
	protected void addPackages() {
		if(CollectionHelper.isEmpty(rootPackagesNames))
			return;
		WebArchive webArchive = (WebArchive) archive; 
		webArchive.addPackages(Boolean.TRUE, rootPackagesNames.toArray(new String[] {}));
	}
	
	/**/
	
	protected Collection<Class<?>> getClasses(Boolean injectIfNull) {
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
	
	protected void addClasses() {
		if(CollectionHelper.isEmpty(classes))
			return;
		WebArchive webArchive = (WebArchive) archive;   
        webArchive.addClasses(classes.toArray(new Class<?>[] {}));
	}
	
	/**/
	
	protected void setWebXml() {
		if(!Boolean.TRUE.equals(webConfigEnabled))
			return;
		if(StringHelper.isBlank(webXmlName) && mode != null)
			webXmlName = buildFileName("memory/web",webXmlName,"xml");
		if(StringHelper.isBlank(webXmlName))
			return;
		WebArchive webArchive = (WebArchive) archive;   
        webArchive.setWebXML(webXmlName);
	}
	
	/**/
	
	public static enum Mode{
		SERVER,CLIENT
	}
}