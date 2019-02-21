package org.cyk.utility.server.representation;

import org.cyk.utility.random.RandomHelper;
import org.cyk.utility.server.representation.AbstractEntityCollection;
import org.cyk.utility.server.representation.test.TestRepresentationCreate;
import org.cyk.utility.server.representation.test.arquillian.AbstractRepresentationArquillianIntegrationTestWithDefaultDeploymentAsSwram;
import org.junit.Test;

public class MyEntityFunctionIntegrationTest extends AbstractRepresentationArquillianIntegrationTestWithDefaultDeploymentAsSwram {
	private static final long serialVersionUID = 1L;
	
	@Test
	public void createOneMyEntity() throws Exception{
		String code = __inject__(RandomHelper.class).getAlphabetic(3);
		MyEntityDto roleCategory = new MyEntityDto().setCode(code);
		__inject__(TestRepresentationCreate.class).addObjects(roleCategory).execute();
	}
	
	@Test
	public void updateOneMyEntity() throws Exception{
		String code = __inject__(RandomHelper.class).getAlphabetic(3);
		
		MyEntity persistence = new MyEntity().setCode(code).setName("n01ToEdit");
		__inject__(MyEntityBusiness.class).create(persistence);
		
		MyEntityDto dto = (MyEntityDto) __inject__(MyEntityRepresentation.class).getOne(code, "business").getEntity();
		assertionHelper.assertEquals("n01ToEdit", dto.getName());
		
		dto.setName("n01");
		__inject__(MyEntityRepresentation.class).updateOne(dto, null);
		MyEntityDto updatedDto = (MyEntityDto) __inject__(MyEntityRepresentation.class).getOne(code, "business").getEntity();
		assertionHelper.assertEquals("n01ToEdit", updatedDto.getName());
		
		dto.setName("n01");
		__inject__(MyEntityRepresentation.class).updateOne(dto, "name");
		updatedDto = (MyEntityDto) __inject__(MyEntityRepresentation.class).getOne(code, "business").getEntity();
		assertionHelper.assertEquals("n01", updatedDto.getName());
		
		//__inject__(TestRepresentationUpdate.class).setFieldValuesMap(dto, __inject__(MapHelper.class).instanciateKeyAsStringValueAsObject("name","n02"))
		//	.addObjects(dto).execute();
	}
	
	@Test
	public void deleteOneRole() throws Exception{
		String code01 = __inject__(RandomHelper.class).getAlphabetic(3);
		String code02 = __inject__(RandomHelper.class).getAlphabetic(3);
		
		assertionHelper.assertNull(__inject__(MyEntityRepresentation.class).getOne(code01,"business").getEntity());
		assertionHelper.assertNull(__inject__(MyEntityRepresentation.class).getOne(code02,"business").getEntity());
		
		__inject__(MyEntityBusiness.class).create(new MyEntity().setCode(code01));
		__inject__(MyEntityBusiness.class).create(new MyEntity().setCode(code02));
		
		assertionHelper.assertNotNull(__inject__(MyEntityRepresentation.class).getOne(code01,"business").getEntity());
		assertionHelper.assertNotNull(__inject__(MyEntityRepresentation.class).getOne(code02,"business").getEntity());
		
		__inject__(MyEntityRepresentation.class).deleteOne(new MyEntityDto().setCode(code01));
		
		assertionHelper.assertNull(__inject__(MyEntityRepresentation.class).getOne(code01,"business").getEntity());
		assertionHelper.assertNotNull(__inject__(MyEntityRepresentation.class).getOne(code02,"business").getEntity());
		
	}
	
	@Override
	protected <ENTITY> Class<? extends AbstractEntityCollection<ENTITY>> __getEntityCollectionClass__(Class<ENTITY> aClass) {
		return null;
	}
	

}
