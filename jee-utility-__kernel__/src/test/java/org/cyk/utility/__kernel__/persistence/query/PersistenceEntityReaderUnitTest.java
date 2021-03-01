package org.cyk.utility.__kernel__.persistence.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.cyk.utility.__kernel__.__entities__.CodeAndName;
import org.cyk.utility.__kernel__.__entities__.TestedEntityParent;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.persistence.EntityManagerFactoryGetterImpl;
import org.cyk.utility.__kernel__.persistence.EntityManagerGetter;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class PersistenceEntityReaderUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("pu");
		QueryHelper.getQueries().setIsRegisterableToEntityManager(Boolean.TRUE);
		QueryHelper.scan(List.of(getClass().getPackage()));	
		QueryHelper.addQueries(Query.buildSelectBySystemIdentifiers(TestedEntityParent.class, "SELECT t FROM TestedEntityParent t WHERE t.identifier IN :identifiers"));
		CodeAndNameQuerier.initialize();
	}
	
	@Override
	protected void __listenAfter__() {
		super.__listenAfter__();
		QueryHelper.clear();
	}
	
	@Test
	public void codeNadName_readCodes(){
		assertThat(CodeAndNameQuerier.getInstance().readCodes()).isNull();
		EntityCreator.getInstance().createManyInTransaction(new CodeAndName().setCode("c01"));
		assertThat(CodeAndNameQuerier.getInstance().readCodes()).containsExactly("c01");
		EntityCreator.getInstance().createManyInTransaction(new CodeAndName().setCode("c02"));
		assertThat(CodeAndNameQuerier.getInstance().readCodes()).containsExactly("c01","c02");
		EntityCreator.getInstance().createManyInTransaction(new CodeAndName().setCode("c00"));
		assertThat(CodeAndNameQuerier.getInstance().readCodes()).containsExactly("c00","c01","c02");
	}
	
	/* read all*/
	
	@Test
	public void testedEntityParent_withoutTuple_read_all(){		
		__assertRead__(EntityReader.getInstance().readMany(TestedEntityParent.class));
	}
	
	@Test
	public void testedEntityParent_withTuple_one_read_all(){		
		EntityCreator.getInstance().createOneInTransaction(new TestedEntityParent("1","1","1"));		
		__assertRead__(EntityReader.getInstance().readMany(TestedEntityParent.class), "1");
	}
	
	@Test
	public void testedEntityParent_withTuple_one_read_all_attached(){		
		EntityCreator.getInstance().createOneInTransaction(new TestedEntityParent("1","1","1"));		
		EntityManager entityManager = EntityManagerGetter.getInstance().get();
		__assertAttached__(EntityReader.getInstance().readMany(TestedEntityParent.class,new QueryExecutorArguments().setEntityManager(entityManager)
				), entityManager,Boolean.TRUE);
	}
	
	@Test
	public void testedEntityParent_withTuple_one_read_all_dettached(){		
		EntityCreator.getInstance().createOneInTransaction(new TestedEntityParent("1","1","1"));		
		EntityManager entityManager = EntityManagerGetter.getInstance().get();
		__assertAttached__(EntityReader.getInstance().readMany(TestedEntityParent.class,new QueryExecutorArguments().setEntityManager(entityManager)
				.setIsEntityManagerClearable(Boolean.TRUE)), entityManager,Boolean.FALSE);
	}
	
	@Test
	public void testedEntityParent_withTuple_many_read_all(){		
		EntityCreator.getInstance().createManyInTransaction(new TestedEntityParent("1","1","1"),new TestedEntityParent("2","2","1"));		
		__assertRead__(EntityReader.getInstance().readMany(TestedEntityParent.class), "1","2");
	}
	
	/* read by system identifier */
	
	@Test
	public void testedEntityParent_withoutTuple_read_bySystemIdentifier(){		
		EntityCreator.getInstance().createManyInTransaction(new TestedEntityParent("1","1","1"),new TestedEntityParent("2","2","1"));		
		__assertRead__(EntityReader.getInstance().readMany(TestedEntityParent.class,new QueryExecutorArguments()
				.setQuery(QueryGetter.getInstance().get("TestedEntityParent.readBySystemIdentifiers"))
				//.addParameters("identifiers",List.of("1"))
				.addFilterField("identifiers",List.of("1"))
				)
				, "1");
	}
	
	/* read by business identifier */
	
	/* read by filter */
	
	/* exception */
	
	@Test
	public void notYetRegistered(){		
		assertThrows(IllegalArgumentException.class, () -> {
			EntityReader.getInstance().readMany(TestedEntityParent.class,new QueryExecutorArguments()
					.setQuery(new Query().setIdentifier("TestedEntityParent.xxx")).addFilterField("identifiers",List.of("1")));
		});
	}
	
	/**/
	
	private <T> void __assertRead__(Collection<T> entities,Object...expectedSystemIdentifiers) {
		if(ArrayHelper.isEmpty(expectedSystemIdentifiers)) {
			assertThat(entities).isNull();
			return;
		}
		assertThat(entities).isNotNull();
		assertThat(entities.stream().map(entity -> FieldHelper.readSystemIdentifier(entity)).collect(Collectors.toList())).containsExactly(expectedSystemIdentifiers);
	}
	
	private <T> void __assertAttached__(Collection<T> entities,EntityManager entityManager,Boolean expected) {
		if(CollectionHelper.isEmpty(entities))
			return;
		if(entityManager == null)
			throw new IllegalArgumentException("entity manager is required");
		entities.forEach(entity -> {
			assertThat(entityManager.contains(entity)).as(entity+" is contains in entity manager. expected %s",expected).isEqualTo(expected);
		});
	}
	
	/**/
	
}
