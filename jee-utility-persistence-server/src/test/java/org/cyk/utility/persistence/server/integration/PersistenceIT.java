package org.cyk.utility.persistence.server.integration;
import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.persistence.query.EntityCounter;
import org.cyk.utility.persistence.server.DataType;
import org.cyk.utility.persistence.server.Initializer;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class PersistenceIT extends AbstractObject {

    @Deployment
    public static Archive<?> buildArchive() {
        WebArchive archive = ShrinkWrap.create(WebArchive.class, "test-persistence.war");
        archive.addAsResource("META-INF/beans.xml");
        archive.addAsResource("META-INF/MANIFEST.MF");
        archive.addAsResource("memory/persistence.xml","META-INF/persistence.xml");
        archive.addAsResource("memory/data.sql","META-INF/data.sql");
        archive.addClasses(DataType.class);
        archive.addAsLibraries(Maven.resolver().loadPomFromFile("pom_arquillian.xml").importCompileAndRuntimeDependencies().resolve().withTransitivity().asFile());
        return archive;
    }

    @Test
   	public void entityManagerFactoryIsInjected() {
    	assertThat(__inject__(EntityManagerFactory.class)).isNotNull();
    }
    
    @Test @Transactional
	public void test() {
    	Initializer.initialize();
    	assertThat(EntityCounter.getInstance().count(DataType.class)).isEqualTo(4l);
	}
}