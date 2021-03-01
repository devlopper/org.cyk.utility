package org.cyk.utility.template.freemarker;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.__kernel__.string.StringTemplateGetter;
import org.cyk.utility.__kernel__.string.StringTemplateIdentifierGetter;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

import freemarker.template.Template;

public class StringTemplateGetterUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void system_server_persistence_entity_systemIdentifiable(){
		Template template = (Template) StringTemplateGetter.getInstance().get(StringTemplateIdentifierGetter.getInstance().getServerPersistenceEntitySystemIdentifiable());
		assertThat(template).isNotNull();
		assertThat(template.toString()).contains("extends AbstractIdentifiableSystemScalarStringImpl");
	}

	@Test
	public void system_server_persistence_entity_businessIdentifiable(){
		Template template = (Template) StringTemplateGetter.getInstance().get(StringTemplateIdentifierGetter.getInstance().getServerPersistenceEntityBusinessIdentifiable());
		assertThat(template).isNotNull();
		assertThat(template.toString()).contains("extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringImpl");
	}
	
	@Test
	public void system_server_persistence_entity_namable(){
		Template template = (Template) StringTemplateGetter.getInstance().get(StringTemplateIdentifierGetter.getInstance().getServerPersistenceEntityNamable());
		assertThat(template).isNotNull();
		assertThat(template.toString()).contains("extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl");
	}
	
	@Test
	public void system_server_persistence_api(){
		Template template = (Template) StringTemplateGetter.getInstance().get(StringTemplateIdentifierGetter.getInstance().getServerPersistenceApi());
		assertThat(template).isNotNull();
		assertThat(template.toString()).contains("${entity_class_name}Persistence extends PersistenceEntity<${entity_class_name}>");
	}
	
	@Test
	public void system_server_persistence_impl(){
		Template template = (Template) StringTemplateGetter.getInstance().get(StringTemplateIdentifierGetter.getInstance().getServerPersistenceImpl());
		assertThat(template).isNotNull();
		assertThat(template.toString()).contains("PersistenceImpl extends AbstractPersistenceEntityImpl");
	}
	
	@Test
	public void system_server_business_api(){
		Template template = (Template) StringTemplateGetter.getInstance().get(StringTemplateIdentifierGetter.getInstance().getServerBusinessApi());
		assertThat(template).isNotNull();
		assertThat(template.toString()).contains("${entity_class_name}Business extends BusinessEntity<${entity_class_name}>");
	}
	
	@Test
	public void system_server_business_impl(){
		Template template = (Template) StringTemplateGetter.getInstance().get(StringTemplateIdentifierGetter.getInstance().getServerBusinessImpl());
		assertThat(template).isNotNull();
		assertThat(template.toString()).contains("BusinessImpl extends AbstractBusinessEntityImpl");
	}
	
	@Test
	public void system_server_representation_entity_systemIdentifiable(){
		Template template = (Template) StringTemplateGetter.getInstance().get(StringTemplateIdentifierGetter.getInstance().getServerRepresentationEntitySystemIdentifiable());
		assertThat(template).isNotNull();
		assertThat(template.toString()).contains("extends AbstractIdentifiableSystemScalarStringImpl");
	}

	@Test
	public void system_server_representation_entity_businessIdentifiable(){
		Template template = (Template) StringTemplateGetter.getInstance().get(StringTemplateIdentifierGetter.getInstance().getServerRepresentationEntityBusinessIdentifiable());
		assertThat(template).isNotNull();
		assertThat(template.toString()).contains("extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringImpl");
	}
	
	@Test
	public void system_server_representation_entity_namable(){
		Template template = (Template) StringTemplateGetter.getInstance().get(StringTemplateIdentifierGetter.getInstance().getServerRepresentationEntityNamable());
		assertThat(template).isNotNull();
		assertThat(template.toString()).contains("extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl");
	}
	
	@Test
	public void system_server_representation_entity_mapper(){
		Template template = (Template) StringTemplateGetter.getInstance().get(StringTemplateIdentifierGetter.getInstance().getServerRepresentationEntityMapper());
		assertThat(template).isNotNull();
		assertThat(template.toString()).contains("public abstract class ${entity_class_name}DtoMapper extends AbstractMapperSourceDestinationImpl<${entity_class_name}Dto, ${entity_class_name}>");
	}
	
	@Test
	public void system_server_representation_api(){
		Template template = (Template) StringTemplateGetter.getInstance().get(StringTemplateIdentifierGetter.getInstance().getServerRepresentationApi());
		assertThat(template).isNotNull();
		assertThat(template.toString()).contains("${entity_class_name}Representation extends RepresentationEntity<${entity_class_name}Dto>");
	}
	
	@Test
	public void system_server_representation_impl(){
		Template template = (Template) StringTemplateGetter.getInstance().get(StringTemplateIdentifierGetter.getInstance().getServerRepresentationImpl());
		assertThat(template).isNotNull();
		assertThat(template.toString()).contains("RepresentationImpl extends AbstractRepresentationEntityImpl");
	}
	
	@Test
	public void system_client_controller_entity_systemIdentifiable(){
		Template template = (Template) StringTemplateGetter.getInstance().get(StringTemplateIdentifierGetter.getInstance().getClientControllerEntitySystemIdentifiable());
		assertThat(template).isNotNull();
		assertThat(template.toString()).contains("extends AbstractDataIdentifiableSystemStringImpl");
	}

	@Test
	public void system_client_controller_entity_businessIdentifiable(){
		Template template = (Template) StringTemplateGetter.getInstance().get(StringTemplateIdentifierGetter.getInstance().getClientControllerEntityBusinessIdentifiable());
		assertThat(template).isNotNull();
		assertThat(template.toString()).contains("extends AbstractDataIdentifiableSystemStringIdentifiableBusinessStringImpl");
	}
	
	@Test
	public void system_client_controller_entity_namable(){
		Template template = (Template) StringTemplateGetter.getInstance().get(StringTemplateIdentifierGetter.getInstance().getClientControllerEntityNamable());
		assertThat(template).isNotNull();
		assertThat(template.toString()).contains("extends AbstractDataIdentifiableSystemStringIdentifiableBusinessStringNamableImpl");
	}
	
	@Test
	public void system_client_controller_entity_mapper(){
		Template template = (Template) StringTemplateGetter.getInstance().get(StringTemplateIdentifierGetter.getInstance().getClientControllerEntityMapper());
		assertThat(template).isNotNull();
		assertThat(template.toString()).contains("public abstract class ${entity_class_name}Mapper extends AbstractMapperSourceDestinationImpl<${entity_class_name}, ${entity_class_name}Dto>");
	}
	
	@Test
	public void system_client_controller_api(){
		Template template = (Template) StringTemplateGetter.getInstance().get(StringTemplateIdentifierGetter.getInstance().getClientControllerApi());
		assertThat(template).isNotNull();
		assertThat(template.toString()).contains("${entity_class_name}Controller extends ControllerEntity<${entity_class_name}>");
	}
	
	@Test
	public void system_client_controller_impl(){
		Template template = (Template) StringTemplateGetter.getInstance().get(StringTemplateIdentifierGetter.getInstance().getClientControllerImpl());
		assertThat(template).isNotNull();
		assertThat(template.toString()).contains("ControllerImpl extends AbstractControllerEntityImpl");
	}
}
