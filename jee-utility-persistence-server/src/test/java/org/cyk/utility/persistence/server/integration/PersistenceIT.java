package org.cyk.utility.persistence.server.integration;
import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.EntityManagerFactory;

import org.cyk.utility.persistence.query.EntityCounter;
import org.cyk.utility.persistence.server.DataType;
import org.cyk.utility.persistence.server.Initializer;
import org.cyk.utility.test.arquillian.bootablejar.AbstractTest;
import org.cyk.utility.test.arquillian.bootablejar.ArchiveBuilder;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;

public class PersistenceIT extends AbstractTest {

    @Deployment
    public static Archive<?> buildArchive() {
    	return new ArchiveBuilder().setPersistenceEnabled(Boolean.TRUE).setClasses(new Class<?>[] {DataType.class}).build();
    }

    @Test
   	public void entityManagerFactoryIsInjected() {
    	assertThat(__inject__(EntityManagerFactory.class)).isNotNull();
    }
    
    @Test
	public void test() {
    	Initializer.initialize();
    	assertThat(EntityCounter.getInstance().count(DataType.class)).isEqualTo(4l);
	}
}