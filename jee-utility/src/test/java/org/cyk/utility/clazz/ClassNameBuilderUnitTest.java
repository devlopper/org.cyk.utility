package org.cyk.utility.clazz;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class ClassNameBuilderUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	/* Persistence to Others*/
	
	@Test
	public void build_server_persistence_entities_To_server_persistence_api() {
		__assert__("server", "persistence", "entities",null, "server", "persistence", "api","Persistence");
	}
	
	@Test
	public void build_server_persistence_entities_To_server_persistence_impl() {
		__assert__("server", "persistence", "entities",null, "server", "persistence", "impl","PersistenceImpl");
	}
	
	@Test
	public void build_server_persistence_entities_To_server_business_api() {
		__assert__("server", "persistence", "entities",null, "server", "business", "api","Business");
	}
	
	@Test
	public void build_server_persistence_entities_To_server_business_impl() {
		__assert__("server", "persistence", "entities",null, "server", "business", "impl","BusinessImpl");
	}
	
	@Test
	public void build_server_persistence_entities_To_server_representation_entities() {
		__assert__("server", "persistence", "entities",null, "server", "representation", "entities","Dto");
	}
	
	@Test
	public void build_server_persistence_entities_To_server_representation_api() {
		__assert__("server", "persistence", "entities",null, "server", "representation", "api","Representation");
	}
	
	@Test
	public void build_server_persistence_entities_To_server_representation_impl() {
		__assert__("server", "persistence", "entities",null, "server", "representation", "impl","RepresentationImpl");
	}
	
	@Test
	public void build_server_persistence_entities_To_client_controller_entities() {
		__assert__("server", "persistence", "entities",null, "client", "controller", "entities","");
	}
	
	@Test
	public void build_server_persistence_entities_To_client_controller_api() {
		__assert__("server", "persistence", "entities",null, "client", "controller", "api","Controller");
	}
	
	@Test
	public void build_server_persistence_entities_To_client_controller_impl() {
		__assert__("server", "persistence", "entities",null, "client", "controller", "impl","ControllerImpl");
	}
	
	/* Representation to Others*/
	
	@Test
	public void build_server_representation_entities_To_server_persistence_entities() {
		__assert__("server", "representation", "entities","Dto", "server", "persistence", "entities","");
	}
	
	@Test
	public void build_server_representation_entities_To_server_persistence_api() {
		__assert__("server", "representation", "entities","Dto", "server", "persistence", "api","Persistence");
	}
	
	@Test
	public void build_server_representation_entities_To_server_persistence_impl() {
		__assert__("server", "representation", "entities","Dto", "server", "persistence", "impl","PersistenceImpl");
	}
	
	@Test
	public void build_server_representation_entities_To_server_business_api() {
		__assert__("server", "representation", "entities","Dto", "server", "business", "api","Business");
	}
	
	@Test
	public void build_server_representation_entities_To_server_business_impl() {
		__assert__("server", "representation", "entities","Dto", "server", "business", "impl","BusinessImpl");
	}
	
	@Test
	public void build_server_representation_entities_To_client_controller_entities() {
		__assert__("server", "representation", "entities","Dto", "client", "controller", "entities","");
	}
	
	@Test
	public void build_server_representation_entities_To_client_controller_api() {
		__assert__("server", "representation", "entities","Dto", "client", "controller", "api","Controller");
	}
	
	@Test
	public void build_server_representation_entities_To_client_controller_impl() {
		__assert__("server", "representation", "entities","Dto", "client", "controller", "impl","ControllerImpl");
	}
	
	/* Controller to Others*/
	
	@Test
	public void build_client_controller_entities_To_server_persistence_entities() {
		__assert__("client", "controller", "entities","", "server", "persistence", "entities","");
	}
	
	@Test
	public void build_client_controller_entities_To_server_persistence_api() {
		__assert__("client", "controller", "entities","", "server", "persistence", "api","Persistence");
	}
	
	@Test
	public void build_client_controller_entities_To_server_persistence_impl() {
		__assert__("client", "controller", "entities","", "server", "persistence", "impl","PersistenceImpl");
	}
	
	@Test
	public void build_client_controller_entities_To_server_business_api() {
		__assert__("client", "controller", "entities","", "server", "business", "api","Business");
	}
	
	@Test
	public void build_client_controller_entities_To_server_business_impl() {
		__assert__("client", "controller", "entities","", "server", "business", "impl","BusinessImpl");
	}
	
	@Test
	public void build_client_controller_entities_To_server_representation_entities() {
		__assert__("client", "controller", "entities","", "server", "representation", "entities","Dto");
	}
	
	@Test
	public void build_client_controller_entities_To_server_representation_api() {
		__assert__("client", "controller", "entities","", "server", "representation", "api","Representation");
	}
	
	@Test
	public void build_client_controller_entities_To_server_representation_impl() {
		__assert__("client", "controller", "entities","", "server", "representation", "impl","RepresentationImpl");
	}
	
	/**/
	
	private void __assert__(String sourceNode,String sourceLayer,String sourceSubLayer,String sourceSuffix,String destinationNode,String destinationLayer,String destinationSubLayer,String expectedSuffix) {
		ClassNameBuilder classNameBuilder = __inject__(ClassNameBuilder.class);
		classNameBuilder.setPackageName("p1.p2."+sourceNode+"."+sourceLayer+"."+sourceSubLayer).setSimpleName("Entity"+StringUtils.defaultIfBlank(sourceSuffix, ""));
		classNameBuilder.getSourceNamingModel(Boolean.TRUE).setNode(sourceNode).setLayer(sourceLayer).setSubLayer(sourceSubLayer).setSuffix(sourceSuffix);
		classNameBuilder.getDestinationNamingModel(Boolean.TRUE).setNode(destinationNode).setLayer(destinationLayer).setSubLayer(destinationSubLayer);
		assertionHelper.assertEquals("p1.p2."+destinationNode+"."+destinationLayer+"."+destinationSubLayer+".Entity"+expectedSuffix, classNameBuilder.execute().getOutput());
	}
}
