package org.cyk.utility.__kernel__.persistence.query;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.cyk.utility.__kernel__.__entities__.TestedEntityParent;
import org.cyk.utility.__kernel__.persistence.EntityManagerFactoryGetterImpl;
import org.cyk.utility.__kernel__.persistence.EntityManagerGetter;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class EntityManagerUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("pu_show_sql");
	}
	
	@Override
	protected void __listenAfter__() {
		super.__listenAfter__();
		QueryHelper.clear();
	}
	
	@Test
	public void persist_find(){		
		EntityManager entityManager = EntityManagerGetter.getInstance().get();
		entityManager.getTransaction().begin();
		entityManager.persist(new TestedEntityParent("1","1","1").setLazy("lazy").setEager("eager"));
		entityManager.getTransaction().commit();
		
		entityManager = EntityManagerGetter.getInstance().get();
		TestedEntityParent testedEntityParent = entityManager.find(TestedEntityParent.class, "1");
		assertThat(testedEntityParent).isNotNull();
		assertThat(testedEntityParent.getIdentifier()).isEqualTo("1");
		assertThat(testedEntityParent.getCode()).isEqualTo("1");
		assertThat(testedEntityParent.getName()).isEqualTo("1");
	}
	
	@Test
	public void persist_find_entityGraph(){		
		EntityManager entityManager = EntityManagerGetter.getInstance().get();
		entityManager.getTransaction().begin();
		entityManager.persist(new TestedEntityParent("1","1","1").setLazy("lazy").setEager("eager"));
		entityManager.getTransaction().commit();
		
		entityManager = EntityManagerGetter.getInstance().get();
		EntityGraph<TestedEntityParent> testedEntityParentGraph = entityManager.createEntityGraph(TestedEntityParent.class);
		testedEntityParentGraph.addAttributeNodes("identifier");
		TestedEntityParent testedEntityParent = entityManager.find(TestedEntityParent.class, "1",Map.of("javax.persistence.fetchgraph",testedEntityParentGraph));
		assertThat(testedEntityParent).isNotNull();
		assertThat(testedEntityParent.getIdentifier()).isEqualTo("1");
		assertThat(testedEntityParent.getCode()).isEqualTo("1");
		assertThat(testedEntityParent.getName()).isEqualTo("1");
		assertThat(testedEntityParent.getLazy()).isEqualTo("lazy");
		assertThat(testedEntityParent.getEager()).isEqualTo("eager");
		
		entityManager = EntityManagerGetter.getInstance().get();
		testedEntityParentGraph = entityManager.createEntityGraph(TestedEntityParent.class);
		testedEntityParentGraph.addAttributeNodes("identifier");
		testedEntityParent = entityManager.find(TestedEntityParent.class, "1",Map.of("javax.persistence.loadgraph",testedEntityParentGraph));
		assertThat(testedEntityParent).isNotNull();
		assertThat(testedEntityParent.getIdentifier()).isEqualTo("1");
		assertThat(testedEntityParent.getCode()).isEqualTo("1");
		assertThat(testedEntityParent.getName()).isEqualTo("1");
		assertThat(testedEntityParent.getLazy()).isEqualTo("lazy");
		assertThat(testedEntityParent.getEager()).isEqualTo("eager");
	}
	
	@Test
	public void persist_find_query_constructor(){		
		EntityManager entityManager = EntityManagerGetter.getInstance().get();
		entityManager.getTransaction().begin();
		entityManager.persist(new TestedEntityParent("1","1","1").setLazy("lazy").setEager("eager"));
		entityManager.getTransaction().commit();
		
		entityManager = EntityManagerGetter.getInstance().get();
		TestedEntityParent testedEntityParent = entityManager.createQuery("SELECT t FROM TestedEntityParent t WHERE t.identifier = :identifier", TestedEntityParent.class)
				.setParameter("identifier", "1").getSingleResult();
		assertThat(testedEntityParent).isNotNull();
		assertThat(testedEntityParent.getIdentifier()).isEqualTo("1");
		assertThat(testedEntityParent.getCode()).isEqualTo("1");
		assertThat(testedEntityParent.getName()).isEqualTo("1");
		assertThat(testedEntityParent.getLazy()).isEqualTo("lazy");
		assertThat(testedEntityParent.getEager()).isEqualTo("eager");
		
		entityManager = EntityManagerGetter.getInstance().get();
		testedEntityParent = entityManager.createQuery("SELECT new org.cyk.utility.__kernel__.__entities__.TestedEntityParent(t.identifier,t.code,t.name) FROM TestedEntityParent t WHERE t.identifier = :identifier", TestedEntityParent.class)
				.setParameter("identifier", "1").getSingleResult();
		assertThat(testedEntityParent).isNotNull();
		assertThat(testedEntityParent.getIdentifier()).isEqualTo("1");
		assertThat(testedEntityParent.getCode()).isEqualTo("1");
		assertThat(testedEntityParent.getName()).isEqualTo("1");
		assertThat(testedEntityParent.getLazy()).isEqualTo(null);
		assertThat(testedEntityParent.getEager()).isEqualTo(null);
	}
	
}
