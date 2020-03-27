package org.cyk.utility.__kernel__.representation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;

import javax.persistence.Persistence;
import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.__entities__.TestedEntityParent;
import org.cyk.utility.__kernel__.__entities__.TestedEntityParentDto;
import org.cyk.utility.__kernel__.__entities__.TestedEntityParentDtoMapper;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.mapping.MapperClassGetterImpl;
import org.cyk.utility.__kernel__.persistence.EntityManagerFactoryGetterImpl;
import org.cyk.utility.__kernel__.persistence.query.EntityCreator;
import org.cyk.utility.__kernel__.persistence.query.Query;
import org.cyk.utility.__kernel__.persistence.query.QueryExecutorArgumentsDto;
import org.cyk.utility.__kernel__.persistence.query.QueryHelper;
import org.cyk.utility.__kernel__.rest.ResponseHelper;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class RepresentationEntityReaderUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("pu");
		MapperClassGetterImpl.MAP.put(TestedEntityParentDto.class, TestedEntityParentDtoMapper.class);
		QueryHelper.getQueries().setIsRegisterableToEntityManager(Boolean.TRUE);
		QueryHelper.scan(List.of(getClass().getPackage()));
		QueryHelper.addQueries(Query.buildSelectBySystemIdentifiers(TestedEntityParent.class, "SELECT t FROM TestedEntityParent t WHERE t.identifier IN :identifiers"));
	}
	
	@Override
	protected void __listenAfter__() {
		super.__listenAfter__();
		QueryHelper.clear();
		EntityReader.INSTANCE.set(null);
	}
	
	/* read all */
	
	@Test
	public void testedEntityParent_withoutTuple_read_all(){		
		__assertReadMany__(EntityReader.getInstance().read(new Arguments().setRepresentationEntityClass(TestedEntityParentDto.class)));
	}
	
	@Test
	public void testedEntityParent_withTuple_one_read_all(){		
		EntityCreator.getInstance().createOneInTransaction(new TestedEntityParent("1","1","1"));		
		__assertReadMany__(EntityReader.getInstance().read(new Arguments().setRepresentationEntityClass(TestedEntityParentDto.class)), "1");
	}
	
	@Test
	public void testedEntityParent_withTuple_many_read_all(){		
		EntityCreator.getInstance().createManyInTransaction(new TestedEntityParent("1","1","1"),new TestedEntityParent("2","2","1"));		
		__assertReadMany__(EntityReader.getInstance().read(new Arguments().setRepresentationEntityClass(TestedEntityParentDto.class)), "1","2");
	}
	
	/* read by system identifier */
	
	@Test
	public void testedEntityParent_withoutTuple_read_bySystemIdentifier(){		
		EntityCreator.getInstance().createManyInTransaction(new TestedEntityParent("1","1","1"),new TestedEntityParent("2","2","1"));
		__assertReadMany__(EntityReader.getInstance().read(new Arguments().setRepresentationEntityClass(TestedEntityParentDto.class)
				.setQueryExecutorArguments(
						new QueryExecutorArgumentsDto().setQueryIdentifier("TestedEntityParent.readBySystemIdentifiers")
						.addFilterField("identifiers",List.of("1"))
						)), "1");
	}
	
	/* read by business identifier */
	
	/* read by filter */
	
	/* exceptions */
	
	@Test
	public void notYetRegistered(){
		Response response = EntityReader.getInstance().read(new Arguments().setRepresentationEntityClass(TestedEntityParentDto.class)
				.setPersistenceEntityClass(TestedEntityParent.class).setQueryExecutorArguments(
						new QueryExecutorArgumentsDto().setQueryIdentifier("TestedEntityParent.xxx").addFilterField("identifiers",List.of("1"))));
		assertThat(response).isNotNull();
		assertThat(ResponseHelper.isFamilyClientError(response)).isTrue();
	}
	
	/**/
	
	private <T> void __assertReadMany__(Response response,Object...expectedSystemIdentifiers) {
		assertThat(response).as("response is not null").isNotNull();
		Collection<?> entities = (Collection<?>) response.getEntity();
		if(ArrayHelper.isEmpty(expectedSystemIdentifiers)) {
			assertThat(entities).isNull();
			return;
		}
		assertThat(entities).isNotNull();
		assertThat(FieldHelper.readSystemIdentifiers(entities)).containsExactly(expectedSystemIdentifiers);
	}
	
	/**/
	
}