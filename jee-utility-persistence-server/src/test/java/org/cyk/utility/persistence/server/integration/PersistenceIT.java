package org.cyk.utility.persistence.server.integration;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;

import javax.persistence.EntityManagerFactory;

import org.cyk.utility.persistence.query.EntityCounter;
import org.cyk.utility.persistence.query.EntityReader;
import org.cyk.utility.persistence.server.DataType;
import org.cyk.utility.test.arquillian.bootablejar.AbstractTest;
import org.cyk.utility.test.arquillian.bootablejar.ArchiveBuilder;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;

public class PersistenceIT extends AbstractTest {

    @Deployment
    public static Archive<?> buildArchive() {
    	return new ArchiveBuilder().setPersistenceEnabled(Boolean.TRUE).addClasses(DataType.class,ApplicationScopeLifeCycleListener.class).build();
    }
    
    @Test
   	public void entityManagerFactoryIsInjected() {
    	assertThat(__inject__(EntityManagerFactory.class)).isNotNull();
    }
    
    @Test
	public void count() { 	
    	assertThat(EntityCounter.getInstance().count(DataType.class)).isEqualTo(4l);
	}
    
    @Test
	public void readMany() { 	
    	assertThat(EntityReader.getInstance().readMany(DataType.class).stream().map(x -> x.getCode()).collect(Collectors.toList()))
    	.containsExactlyInAnyOrder("SG","LG","ST","INT");
	}
    
    @Test
	public void readOneBySystemIdentifier() { 	
    	assertThat(EntityReader.getInstance().readOneBySystemIdentifier(DataType.class,"dt1").getCode()).isEqualTo("SG");
	}
    
    @Test
	public void readOneByBusinessIdentifier() { 	
    	assertThat(EntityReader.getInstance().readOneByBusinessIdentifier(DataType.class,"SG").getName()).isEqualTo("String");
	}
    
    @Test
	public void specialChars() { 	
    	assertThat(EntityReader.getInstance().readOneBySystemIdentifier(DataType.class,"dt2").getDescription()).isEqualTo("Valeur entière");
	}
}