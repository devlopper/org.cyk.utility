package org.cyk.utility.business.server;
import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.persistence.query.EntityCounter;
import org.cyk.utility.persistence.query.EntityFinder;
import org.cyk.utility.persistence.server.hibernate.Initializer;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class BusinessIT extends AbstractObject {

    @Deployment
    public static Archive<?> buildArchive() {
        WebArchive archive = ShrinkWrap.create(WebArchive.class, "test-business.war");
        archive.addAsResource("META-INF/beans.xml");
        archive.addAsResource("META-INF/MANIFEST.MF");
        archive.addAsResource("memory/persistence.xml","META-INF/persistence.xml");
        archive.addAsResource("memory/data.sql","META-INF/data.sql");
        archive.addClasses(DataType.class);
        archive.addAsLibraries(Maven.resolver().loadPomFromFile("pom_arquillian.xml").importCompileAndRuntimeDependencies().resolve().withTransitivity().asFile());
        return archive;
    }

    @Test
	public void test() {
    	Initializer.initialize();
    	assertThat(EntityCounter.getInstance().count(DataType.class)).isEqualTo(5l);
    	DataType dataType = new DataType();
    	dataType.setCode("P01");
		EntityCreator.getInstance().createMany(dataType);
		assertThat(EntityCounter.getInstance().count(DataType.class)).isEqualTo(6l);
		dataType = EntityFinder.getInstance().find(DataType.class, "P01");
		assertThat(dataType).isNotNull();
		assertThat(dataType.getIdentifier()).isEqualTo("P01");
		assertThat(dataType.getCode()).isEqualTo("P01");
		assertThat(dataType.getName()).isEqualTo("P01");
	}
}