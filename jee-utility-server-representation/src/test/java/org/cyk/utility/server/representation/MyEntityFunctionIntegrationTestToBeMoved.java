package org.cyk.utility.server.representation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.cyk.utility.field.FieldHelper;
import org.cyk.utility.random.RandomHelper;
import org.cyk.utility.server.business.api.MyEntityBusiness;
import org.cyk.utility.server.persistence.entities.MyEntity;
import org.cyk.utility.server.representation.api.MyEntityRepresentation;
import org.cyk.utility.server.representation.entities.MyEntityDto;
import org.cyk.utility.server.representation.test.TestRepresentationCreate;
import org.cyk.utility.server.representation.test.arquillian.AbstractRepresentationArquillianIntegrationTestWithDefaultDeployment;
import org.junit.Test;

public class MyEntityFunctionIntegrationTestToBeMoved extends AbstractRepresentationArquillianIntegrationTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;
	
	@Test
	public void createOneMyEntity() throws Exception{
		String code = __inject__(RandomHelper.class).getAlphabetic(3);
		MyEntityDto roleCategory = new MyEntityDto().setCode(code);
		__inject__(TestRepresentationCreate.class).addObjects(roleCategory).execute();
	}
	
	@Test
	public void updateOneMyEntity() throws Exception{
		String identifier = __getRandomIdentifier__();
		
		MyEntity persistence = new MyEntity().setIdentifier(identifier).setCode(__getRandomCode__()).setName("n01ToEdit");
		__inject__(MyEntityBusiness.class).create(persistence);
		
		MyEntityDto dto = (MyEntityDto) __inject__(MyEntityRepresentation.class).getOne(identifier, "system",null).getEntity();
		assertionHelper.assertNotNull(dto);
		assertionHelper.assertEquals("n01ToEdit", dto.getName());
		
		dto.setName("n01");
		__inject__(MyEntityRepresentation.class).updateOne(dto, null);
		MyEntityDto updatedDto = (MyEntityDto) __inject__(MyEntityRepresentation.class).getOne(identifier, "system",null).getEntity();
		assertionHelper.assertEquals("n01ToEdit", updatedDto.getName());
		
		dto.setName("n01");
		__inject__(MyEntityRepresentation.class).updateOne(dto, "name");
		updatedDto = (MyEntityDto) __inject__(MyEntityRepresentation.class).getOne(identifier, "system",null).getEntity();
		assertionHelper.assertEquals("n01", updatedDto.getName());
		
		__inject__(MyEntityBusiness.class).deleteBySystemIdentifier(identifier);
		//__inject__(TestRepresentationUpdate.class).setFieldValuesMap(dto, __inject__(MapHelper.class).instanciateKeyAsStringValueAsObject("name","n02"))
		//	.addObjects(dto).execute();
	}
	
	@Test
	public void deleteOne() throws Exception{
		String code01 = __inject__(RandomHelper.class).getAlphabetic(3);
		String code02 = __inject__(RandomHelper.class).getAlphabetic(3);
		assertionHelper.assertNull(__inject__(MyEntityRepresentation.class).getOne(code01,"business",null).getEntity());
		assertionHelper.assertNull(__inject__(MyEntityRepresentation.class).getOne(code02,"business",null).getEntity());
		__inject__(MyEntityBusiness.class).create(new MyEntity().setCode(code01));
		__inject__(MyEntityBusiness.class).create(new MyEntity().setCode(code02));
		assertionHelper.assertNotNull(__inject__(MyEntityRepresentation.class).getOne(code01,"business",null).getEntity());
		assertionHelper.assertNotNull(__inject__(MyEntityRepresentation.class).getOne(code02,"business",null).getEntity());
		__inject__(MyEntityRepresentation.class).deleteOne(new MyEntityDto().setCode(code01));
		assertionHelper.assertNull(__inject__(MyEntityRepresentation.class).getOne(code01,"business",null).getEntity());
		assertionHelper.assertNotNull(__inject__(MyEntityRepresentation.class).getOne(code02,"business",null).getEntity());
		__inject__(MyEntityRepresentation.class).deleteOne(new MyEntityDto().setCode(code02));
	}
	
	@Test
	public void getManyByPage() throws Exception{
		for(Integer index = 0 ; index < 10 ; index = index + 1)
			__inject__(MyEntityBusiness.class).create(new MyEntity().setIdentifier(index.toString()).setCode(index.toString()));
		
		assertThat(__inject__(FieldHelper.class).getSystemIdentifiers(String.class, (Collection<?>)__inject__(MyEntityRepresentation.class).getMany(Boolean.FALSE,null,null,null,null).getEntity()))
			.containsExactly("0","1","2","3","4","5","6","7","8","9");
		
		assertThat(__inject__(FieldHelper.class).getSystemIdentifiers(String.class, (Collection<?>)__inject__(MyEntityRepresentation.class).getMany(null,0l,1l,null,null).getEntity()))
			.containsExactly("0");
		
		assertThat(__inject__(FieldHelper.class).getSystemIdentifiers(String.class, (Collection<?>)__inject__(MyEntityRepresentation.class).getMany(null,1l,1l,null,null).getEntity()))
			.containsExactly("1");
		
		assertThat(__inject__(FieldHelper.class).getSystemIdentifiers(String.class, (Collection<?>)__inject__(MyEntityRepresentation.class).getMany(null,0l,3l,null,null).getEntity()))
			.containsExactly("0","1","2");
		
		assertThat(__inject__(FieldHelper.class).getSystemIdentifiers(String.class, (Collection<?>)__inject__(MyEntityRepresentation.class).getMany(null,4l,3l,null,null).getEntity()))
			.containsExactly("4","5","6");
	}
	
	@Override
	protected <ENTITY> Class<? extends AbstractEntityCollection<ENTITY>> __getEntityCollectionClass__(Class<ENTITY> aClass) {
		return null;
	}
	

}
