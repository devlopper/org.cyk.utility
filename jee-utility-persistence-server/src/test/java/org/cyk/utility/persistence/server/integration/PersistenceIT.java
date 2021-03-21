package org.cyk.utility.persistence.server.integration;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;

import javax.persistence.EntityManagerFactory;

import org.cyk.utility.persistence.query.EntityCounter;
import org.cyk.utility.persistence.server.DataType;
import org.cyk.utility.persistence.server.Initializer;
import org.cyk.utility.persistence.query.EntityReader;
import org.cyk.utility.test.arquillian.bootablejar.AbstractTest;
import org.cyk.utility.test.arquillian.bootablejar.ArchiveBuilder;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;

public class PersistenceIT extends AbstractTest {

    @Deployment
    public static Archive<?> buildArchive() {
    	return new ArchiveBuilder().setPersistenceEnabled(Boolean.TRUE).setClasses(new Class<?>[] {DataType.class}).build();
    }

    @Test @InSequence(1)
   	public void initialize() {
    	Initializer.initialize();
    }
    
    @Test @InSequence(2)
   	public void entityManagerFactoryIsInjected() {
    	assertThat(__inject__(EntityManagerFactory.class)).isNotNull();
    }
    
    @Test @InSequence(2)
	public void count() { 	
    	assertThat(EntityCounter.getInstance().count(DataType.class)).isEqualTo(4l);
	}
    
    @Test @InSequence(2)
	public void readMany() { 	
    	assertThat(EntityReader.getInstance().readMany(DataType.class).stream().map(x -> x.getCode()).collect(Collectors.toList()))
    	.containsExactlyInAnyOrder("SG","LG","ST","INT");
	}
    
    @Test @InSequence(2)
	public void readOneBySystemIdentifier() { 	
    	assertThat(EntityReader.getInstance().readOneBySystemIdentifier(DataType.class,"dt1").getCode()).isEqualTo("SG");
	}
    
    @Test @InSequence(2)
	public void readOneByBusinessIdentifier() { 	
    	assertThat(EntityReader.getInstance().readOneByBusinessIdentifier(DataType.class,"SG").getName()).isEqualTo("String");
	}
}