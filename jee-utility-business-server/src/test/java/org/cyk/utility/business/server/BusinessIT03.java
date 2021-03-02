package org.cyk.utility.business.server;
import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.business.server.EntityCreator;
import org.cyk.utility.persistence.query.EntityCounter;
import org.cyk.utility.persistence.query.EntityFinder;
import org.cyk.utility.persistence.server.hibernate.Initializer;
import org.cyk.utility.persistence.server.hibernate.PhysicalNamingStrategyStandardImpl;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class BusinessIT03 extends AbstractObject {

    @Deployment
    public static Archive<?> buildArchive() {
        WebArchive archive = ShrinkWrap.create(WebArchive.class, "test-business.war");
        archive.addAsResource("META-INF/beans.xml");
        archive.addAsResource("META-INF/MANIFEST.MF");
        archive.addAsResource("memory/persistence.xml","META-INF/persistence.xml");
        archive.addAsResource("memory/data.sql","META-INF/data.sql");
        archive.addPackages(Boolean.TRUE, BusinessIT03.class.getPackage());
        archive.addPackages(Boolean.TRUE, PhysicalNamingStrategyStandardImpl.class.getPackage());
        archive.addAsLibraries(Maven.resolver().loadPomFromFile("pom.xml").importCompileAndRuntimeDependencies().resolve().withTransitivity().asFile());
        archive.addAsLibraries(Maven.resolver().loadPomFromFile("pom.xml").importTestDependencies().resolve().withTransitivity().asFile());
        return archive;
    }

    @Test
	public void test() {
    	System.out.println("BusinessIT03.test()");
    	System.out.println(" *************************** "+__inject__(EntityManagerFactory.class));
    	Initializer.initialize();
    	assertThat(EntityCounter.getInstance().count(DataType.class)).isEqualTo(0l);
    	DataType dataType = new DataType();
    	dataType.setCode("P01");
		EntityCreator.getInstance().createMany(dataType);
		assertThat(EntityCounter.getInstance().count(DataType.class)).isEqualTo(1l);
		dataType = EntityFinder.getInstance().find(DataType.class, "P01");
		assertThat(dataType).isNotNull();
		assertThat(dataType.getIdentifier()).isEqualTo("P01");
		assertThat(dataType.getCode()).isEqualTo("P01");
		assertThat(dataType.getName()).isEqualTo("P01");
	}
}