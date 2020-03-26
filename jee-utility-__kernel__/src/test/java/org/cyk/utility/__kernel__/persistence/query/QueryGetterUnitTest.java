package org.cyk.utility.__kernel__.persistence.query;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;

import javax.persistence.Persistence;

import org.cyk.utility.__kernel__.__entities__.TestedEntityParent;
import org.cyk.utility.__kernel__.persistence.EntityManagerFactoryGetterImpl;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class QueryGetterUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("pu");
		QueryHelper.getQueries().setIsRegisterableToEntityManager(Boolean.TRUE);
	}
	
	@Override
	protected void __listenAfter__() {
		super.__listenAfter__();
		QueryHelper.clear();
	}
	
	@Test
	public void get_null_null_null(){		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {QueryGetter.getInstance().get(null, null, null);});		
		assertThat(QueryHelper.getQueries().getSize()).isEqualTo(0);
		assertThat(QueryHelper.getQueries().get()).isNull();
	}
	
	@Test
	public void get_TestedEntityParent_readAll(){		
		QueryHelper.addQueries(Query.build(TestedEntityParent.class,"readAll","SELECT t FROM TestedEntityParent t"));
		Query query = QueryGetter.getInstance().getBySelect(TestedEntityParent.class, "readAll");
		assertThat(query).isNotNull();
		assertThat(query.getIdentifier()).isEqualTo("TestedEntityParent.readAll");
		assertThat(query.getValue()).isEqualTo("SELECT t FROM TestedEntityParent t");
		assertThat(QueryHelper.getQueries().getSize()).isEqualTo(1);
		assertThat(QueryHelper.getQueries().get().stream().map(Query::getIdentifier).collect(Collectors.toList())).contains("TestedEntityParent.readAll");
	}
	
	@Test
	public void get_identifier_value_oneCall(){		
		Query query = QueryGetter.getInstance().get(TestedEntityParent.class, "r1", "SELECT t FROM TestedEntityParent t");		
		assertThat(query).isNotNull();
		assertThat(query.getIdentifier()).isEqualTo("r1");
		assertThat(query.getValue()).isEqualTo("SELECT t FROM TestedEntityParent t");
		assertThat(QueryHelper.getQueries().getSize()).isEqualTo(1);
		assertThat(QueryHelper.getQueries().get().stream().map(Query::getIdentifier).collect(Collectors.toList())).contains("r1");
	}
	
	@Test
	public void get_identifier_value_twoCall_sameValue(){		
		Query query = QueryGetter.getInstance().get(TestedEntityParent.class, "r1", "SELECT t FROM TestedEntityParent t");
		assertThat(query).isNotNull();
		assertThat(query.getIdentifier()).isEqualTo("r1");
		assertThat(query.getValue()).isEqualTo("SELECT t FROM TestedEntityParent t");
		assertThat(QueryHelper.getQueries().getSize()).isEqualTo(1);
		assertThat(QueryHelper.getQueries().get().stream().map(Query::getIdentifier).collect(Collectors.toList())).contains("r1");
		
		query = QueryGetter.getInstance().get(TestedEntityParent.class, "r1", "SELECT t FROM TestedEntityParent t");
		assertThat(query).isNotNull();
		assertThat(query.getIdentifier()).isEqualTo("r1");
		assertThat(query.getValue()).isEqualTo("SELECT t FROM TestedEntityParent t");
		assertThat(QueryHelper.getQueries().getSize()).isEqualTo(1);
		assertThat(QueryHelper.getQueries().get().stream().map(Query::getIdentifier).collect(Collectors.toList())).contains("r1");
	}
	
	@Test
	public void get_identifier_value_twoCall_differentValue(){		
		QueryGetter.getInstance().get(TestedEntityParent.class, "r1", "SELECT t FROM TestedEntityParent t");
		Assertions.assertThrows(IllegalArgumentException.class, () -> {QueryGetter.getInstance().get(TestedEntityParent.class, "r1", "SELECT t FROM TestedEntityParent t ORDER BY t.identifier");});				
		assertThat(QueryHelper.getQueries().getSize()).isEqualTo(1);
		assertThat(QueryHelper.getQueries().get().stream().map(Query::getIdentifier).collect(Collectors.toList())).contains("r1");
	}
	
	/* count */
	
	@Test
	public void get_TestedEntityParent_countAll(){		
		QueryHelper.addQueries(Query.build(TestedEntityParent.class,"countAll","SELECT COUNT(t) FROM TestedEntityParent t",Long.class));
		Query query = QueryGetter.getInstance().getBySelect(TestedEntityParent.class, "countAll");
		assertThat(query).isNotNull();
		assertThat(query.getIdentifier()).isEqualTo("TestedEntityParent.countAll");
		assertThat(query.getValue()).isEqualTo("SELECT COUNT(t) FROM TestedEntityParent t");
		assertThat(QueryHelper.getQueries().getSize()).isEqualTo(1);
		assertThat(QueryHelper.getQueries().get().stream().map(Query::getIdentifier).collect(Collectors.toList())).contains("TestedEntityParent.countAll");
	}
	
	/**/
	
}
