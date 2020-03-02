package org.cyk.utility.__kernel__.persistence.query;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Persistence;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.persistence.EntityCreator;
import org.cyk.utility.__kernel__.persistence.EntityManagerFactoryGetterImpl;
import org.cyk.utility.__kernel__.persistence.query.QueryExecutor.Arguments;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class QueryExecutorUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("pu");
		QueryHelper.getQueries().setIsRegisterableToEntityManager(Boolean.TRUE);
		QueryHelper.scan(List.of(getClass().getPackage()));	
		
		QueryHelper.addQueries(Query.buildSelect(TestedEntityParent.class, "SELECT t FROM TestedEntityParent t",Boolean.TRUE));
		
		QueryHelper.addQueries(Query.buildSelectBySystemIdentifiers(TestedEntityParent.class, "SELECT t FROM TestedEntityParent t WHERE t.identifier IN :identifiers"));
		QueryHelper.addQueries(Query.buildSelectByBusinessIdentifiers(TestedEntityParent.class, "SELECT t FROM TestedEntityParent t WHERE t.code IN :identifiers"));
	}
	
	@Override
	protected void __listenAfter__() {
		super.__listenAfter__();
		QueryHelper.clear();
	}
	
	/* read many*/
	
	@Test
	public void executeReadMany_testedEntityParent_withoutTuple_read_all(){		
		__assertReadMany__(QueryExecutor.getInstance().executeReadMany(TestedEntityParent.class));
	}
	
	@Test
	public void executeCountMany_testedEntityParent_withoutTuple_count_all(){
		assertThat(QueryExecutor.getInstance().executeCount(new Arguments().setQuery(QueryGetter.getInstance().getByCount(TestedEntityParent.class)))).isEqualTo(0l);
	}
	
	@Test
	public void testedEntityParent_withTuple_one_read_all(){		
		EntityCreator.getInstance().createOneInTransaction(new TestedEntityParent("1","1","1"));		
		__assertReadMany__(QueryExecutor.getInstance().executeReadMany(TestedEntityParent.class), "1");
	}
	
	@Test
	public void testedEntityParent_withTuple_one_count_all(){		
		EntityCreator.getInstance().createOneInTransaction(new TestedEntityParent("1","1","1"));		
		assertThat(QueryExecutor.getInstance().executeCount(new Arguments().setQuery(QueryGetter.getInstance().getByCount(TestedEntityParent.class)))).isEqualTo(1l);
	}
	
	@Test
	public void testedEntityParent_withTuple_many_read_all(){		
		EntityCreator.getInstance().createManyInTransaction(new TestedEntityParent("1","1","1"),new TestedEntityParent("2","2","1"));		
		__assertReadMany__(QueryExecutor.getInstance().executeReadMany(TestedEntityParent.class), "1","2");
	}
	
	@Test
	public void testedEntityParent_withTuple_many_count_all(){		
		EntityCreator.getInstance().createManyInTransaction(new TestedEntityParent("1","1","1"),new TestedEntityParent("2","2","1"));		
		assertThat(QueryExecutor.getInstance().executeCount(new Arguments().setQuery(QueryGetter.getInstance().getByCount(TestedEntityParent.class)))).isEqualTo(2l);
	}
	
	@Test
	public void testedEntityParent_withTuple_many_read_1_3(){		
		EntityCreator.getInstance().createManyInTransaction(new TestedEntityParent("1","1","1"),new TestedEntityParent("2","2","1"),new TestedEntityParent("3","3","1"));		
		__assertReadMany__(QueryExecutor.getInstance().executeReadMany(TestedEntityParent.class,new Arguments()
				.setQuery(QueryGetter.getInstance().getBySelectBySystemIdentifiers(TestedEntityParent.class))
				.addFilterField("identifiers",List.of("1","3"))
				),"1","3");
	}
	
	@Test
	public void testedEntityChild_withTuple_many_readByParentsCodes_1(){		
		EntityCreator.getInstance().createManyInTransaction(new TestedEntityParent("1","1","1"),new TestedEntityParent("2","2","1"),new TestedEntityParent("3","3","1"));
		EntityCreator.getInstance().createManyInTransaction(new TestedEntityChild("1.1","1.1","1","1"),new TestedEntityChild("1.2","1.2","1","1"),new TestedEntityChild("2.1","2.1","1","2"));
		__assertReadMany__(QueryExecutor.getInstance().executeReadMany(TestedEntityChild.class,new Arguments()
				.setQuery(QueryGetter.getInstance().getBySelect(TestedEntityChild.class,"readByParentsCodes"))
				.addFilterField("parentsCodes",List.of("1"))
				),"1.1","1.2");
	}
	
	@Test
	public void testedEntityChild_withTuple_many_readByParentsCodes_1_usingCustomObject(){
		TestedEntityParent p1 = new TestedEntityParent("1","1","1");
		EntityCreator.getInstance().createManyInTransaction(p1,new TestedEntityParent("2","2","1"),new TestedEntityParent("3","3","1"));
		EntityCreator.getInstance().createManyInTransaction(new TestedEntityChild("1.1","1.1","1","1"),new TestedEntityChild("1.2","1.2","1","1"),new TestedEntityChild("2.1","2.1","1","2"));
		__assertReadMany__(TestedEntityChildByParentsQuerier.getInstance().read(p1),"1.1","1.2");
	}
	
	@Test
	public void testedEntityChild_withTuple_many_countByParentsCodes_1_usingCustomObject(){
		TestedEntityParent p1 = new TestedEntityParent("1","1","1");
		EntityCreator.getInstance().createManyInTransaction(p1,new TestedEntityParent("2","2","1"),new TestedEntityParent("3","3","1"));
		EntityCreator.getInstance().createManyInTransaction(new TestedEntityChild("1.1","1.1","1","1"),new TestedEntityChild("1.2","1.2","1","1"),new TestedEntityChild("2.1","2.1","1","2"));
		assertThat(TestedEntityChildByParentsQuerier.getInstance().count(p1)).isEqualTo(2l);
	}
	
	@Test
	public void testedEntityChild_withTuple_many_readByParentsCodes_2_usingCustomObject(){		
		EntityCreator.getInstance().createManyInTransaction(new TestedEntityParent("1","1","1"),new TestedEntityParent("2","2","1"),new TestedEntityParent("3","3","1"));
		EntityCreator.getInstance().createManyInTransaction(new TestedEntityChild("1.1","1.1","1","1"),new TestedEntityChild("1.2","1.2","1","1"),new TestedEntityChild("2.1","2.1","1","2"));
		__assertReadMany__(TestedEntityChildByParentsQuerier.getInstance().readByBusinessIdentifiers("2"),"2.1");
	}
	
	@Test
	public void testedEntityChild_withTuple_many_readByParentsCodes_3_usingCustomObject(){		
		EntityCreator.getInstance().createManyInTransaction(new TestedEntityParent("1","1","1"),new TestedEntityParent("2","2","1"),new TestedEntityParent("3","3","1"));
		EntityCreator.getInstance().createManyInTransaction(new TestedEntityChild("1.1","1.1","1","1"),new TestedEntityChild("1.2","1.2","1","1"),new TestedEntityChild("2.1","2.1","1","2"));
		__assertReadMany__(TestedEntityChildByParentsQuerier.getInstance().readByBusinessIdentifiers("3"));
	}
	
	/* read one */
	
	@Test
	public void executeReadOne_testedEntityParent_withoutTuple_read_1(){		
		__assertReadOne__(QueryExecutor.getInstance().executeReadOne(TestedEntityParent.class,new Arguments()
				.setQuery(QueryGetter.getInstance().getBySelectBySystemIdentifiers(TestedEntityParent.class))
				.addFilterField("identifiers","1")
				),null);
	}
	
	@Test
	public void testedEntityOne_withTuple_one_read_1(){		
		EntityCreator.getInstance().createOneInTransaction(new TestedEntityParent("1","1","1"));		
		__assertReadOne__(QueryExecutor.getInstance().executeReadOne(TestedEntityParent.class,new Arguments()
				.setQuery(QueryGetter.getInstance().getBySelectBySystemIdentifiers(TestedEntityParent.class))
				.addFilterField("identifiers","1")
				), "1");
	}
	
	@Test
	public void testedEntityOne_withTuple_many_read_2(){		
		EntityCreator.getInstance().createManyInTransaction(new TestedEntityParent("1","1","1"),new TestedEntityParent("2","2","1"));		
		__assertReadOne__(QueryExecutor.getInstance().executeReadOne(TestedEntityParent.class,new Arguments()
				.setQuery(QueryGetter.getInstance().getBySelectBySystemIdentifiers(TestedEntityParent.class))
				.addFilterField("identifiers","2")
				),"2");
	}
	
	/**/
	
	private void __assertReadMany__(Collection<?> entities,Object...expectedSystemIdentifiers) {
		if(ArrayHelper.isEmpty(expectedSystemIdentifiers)) {
			assertThat(entities).isNull();
			return;
		}
		assertThat(entities).isNotNull();
		assertThat(entities.stream().map(entity -> FieldHelper.readSystemIdentifier(entity)).collect(Collectors.toList())).containsExactly(expectedSystemIdentifiers);
	}
	
	private void __assertReadOne__(Object entity,Object expectedSystemIdentifier) {
		if(expectedSystemIdentifier == null) {
			assertThat(entity).isNull();
			return;
		}
		assertThat(entity).isNotNull();
		assertThat(FieldHelper.readSystemIdentifier(entity)).isEqualTo(expectedSystemIdentifier);
	}
	
	/**/
	
}
