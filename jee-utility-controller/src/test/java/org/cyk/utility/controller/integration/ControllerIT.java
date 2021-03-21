package org.cyk.utility.controller.integration;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.cyk.utility.__kernel__.identifier.resource.ProxyUniformResourceIdentifierGetter;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.controller.DataType;
import org.cyk.utility.controller.EntityReader;
import org.cyk.utility.controller.RepresentationEntityClassGetter;
import org.cyk.utility.controller.server.api.DataTypeDto;
import org.cyk.utility.test.arquillian.bootablejar.ArchiveBuilder;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ControllerIT extends AbstractObject {

	@Deployment(name = "server",order = 1)
    public static Archive<?> buildServerArchive() {
		return new ArchiveBuilder().setPersistenceEnabled(Boolean.TRUE).setPomXmlName("pom_arquillian_server.xml").setWebXmlName("memory/web_server.xml")
				.setArchiveName("test-server.war").setRootPackagesNames(List.of("org.cyk.utility.controller")).build();
    }
	
    @Deployment(name = "client",order = 2)
    public static Archive<?> buildClientArchive() {
    	return new ArchiveBuilder().setRootPackagesNames(List.of("org.cyk.utility.controller")).setArchiveName("test-client.war").build();
    }
    
    @Test @OperateOnDeployment("client")
	public void test() {
    	RepresentationEntityClassGetter.AbstractImpl.MAP.put(DataType.class,DataTypeDto.class);
    	ProxyUniformResourceIdentifierGetter.UNIFORM_RESOURCE_IDENTIFIER_STRING.set("http://127.0.0.1:8080/test-server/api");
		Collection<DataType> collection = null;
		collection = EntityReader.getInstance().readMany(DataType.class);		
		assertThat(collection).isNotEmpty();
		assertThat(collection.stream().map(x -> x.getCode()).collect(Collectors.toList())).containsExactlyInAnyOrder("1","2","3","4","5");
	}
}