package org.cyk.utility.representation.server.integration;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.business.server.EntityCreator;
import org.cyk.utility.persistence.query.EntityCounter;
import org.cyk.utility.persistence.query.EntityFinder;
import org.cyk.utility.persistence.server.hibernate.Initializer;
import org.cyk.utility.representation.server.DataType;
import org.cyk.utility.test.arquillian.bootablejar.ArchiveBuilder;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class RepresentationIT extends AbstractObject {

    @Deployment
    public static Archive<?> buildArchive() {
    	return new ArchiveBuilder().setPersistenceEnabled(Boolean.TRUE).setRootPackagesNames(List.of("org.cyk.utility.representation.server")).build();
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