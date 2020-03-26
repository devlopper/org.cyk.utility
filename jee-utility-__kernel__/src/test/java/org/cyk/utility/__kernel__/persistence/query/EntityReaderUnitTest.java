package org.cyk.utility.__kernel__.persistence.query;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Persistence;

import org.cyk.utility.__kernel__.__entities__.TestedEntityParent;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.persistence.EntityManagerFactoryGetterImpl;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class EntityReaderUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("pu");
		QueryHelper.getQueries().setIsRegisterableToEntityManager(Boolean.TRUE);
		QueryHelper.scan(List.of(getClass().getPackage()));	
		QueryHelper.addQueries(Query.buildSelectBySystemIdentifiers(TestedEntityParent.class, "SELECT t FROM TestedEntityParent t WHERE t.identifier IN :identifiers"));
	}
	
	@Override
	protected void __listenAfter__() {
		super.__listenAfter__();
		QueryHelper.clear();
	}
	
	/* read all*/
	
	@Test
	public void testedEntityParent_withoutTuple_read_all(){		
		__assertRead__(EntityReader.getInstance().read(TestedEntityParent.class));
	}
	
	@Test
	public void testedEntityParent_withTuple_one_read_all(){		
		EntityCreator.getInstance().createOneInTransaction(new TestedEntityParent("1","1","1"));		
		__assertRead__(EntityReader.getInstance().read(TestedEntityParent.class), "1");
	}
	
	@Test
	public void testedEntityParent_withTuple_many_read_all(){		
		EntityCreator.getInstance().createManyInTransaction(new TestedEntityParent("1","1","1"),new TestedEntityParent("2","2","1"));		
		__assertRead__(EntityReader.getInstance().read(TestedEntityParent.class), "1","2");
	}
	
	/* read by system identifier */
	
	@Test
	public void testedEntityParent_withoutTuple_read_bySystemIdentifier(){		
		EntityCreator.getInstance().createManyInTransaction(new TestedEntityParent("1","1","1"),new TestedEntityParent("2","2","1"));		
		__assertRead__(EntityReader.getInstance().read(TestedEntityParent.class,new QueryExecutorArguments()
				.setQuery(new Query().setIdentifier("TestedEntityParent.readBySystemIdentifiers"))
				//.addParameters("identifiers",List.of("1"))
				.addFilterField("identifiers",List.of("1"))
				)
				, "1");
	}
	
	/* read by business identifier */
	
	/* read by filter */
	
	/**/
	
	private <T> void __assertRead__(Collection<T> entities,Object...expectedSystemIdentifiers) {
		if(ArrayHelper.isEmpty(expectedSystemIdentifiers)) {
			assertThat(entities).isNull();
			return;
		}
		assertThat(entities).isNotNull();
		assertThat(entities.stream().map(entity -> FieldHelper.readSystemIdentifier(entity)).collect(Collectors.toList())).containsExactly(expectedSystemIdentifiers);
	}
	
	/**/
	
}
