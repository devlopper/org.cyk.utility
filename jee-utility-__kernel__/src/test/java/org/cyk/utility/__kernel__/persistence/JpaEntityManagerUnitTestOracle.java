package org.cyk.utility.__kernel__.persistence;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.cyk.utility.__kernel__.__entities__.CompositeIdClass;
import org.cyk.utility.__kernel__.__entities__.JpaEntity;
import org.cyk.utility.__kernel__.__entities__.JpaEntityCompositeId;
import org.cyk.utility.__kernel__.__entities__.JpaEntityCompositeIdClass;
import org.cyk.utility.__kernel__.__entities__.JpaEntityVersion;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class JpaEntityManagerUnitTestOracle extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("jpa_em_unittest_oracle");
	}
	
	//@Test
	public void persist(){
		System.out.println("---------------------- PERSIST --------------------------------");
		EntityManager entityManager = EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(new JpaEntity().setCode("c01").setName("n01"));
		entityManager.getTransaction().commit();
		/*
		JpaEntity jpaEntity = entityManager.find(JpaEntity.class, "1");
		assertThat(jpaEntity).isNotNull();
		assertThat(jpaEntity.getIdentifier()).isEqualTo("1");
		assertThat(jpaEntity.getCode()).isEqualTo("c01");
		assertThat(jpaEntity.getName()).isEqualTo("n01");
		*/
	}
	
	@Test
	public void merge_as_persist(){
		System.out.println("---------------------- MERGE as persist --------------------------------");
		JpaEntity jpaEntity = null;
		EntityManager entityManager = EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY.createEntityManager();
		entityManager.getTransaction().begin();
		jpaEntity = entityManager.merge(new JpaEntity().setIdentifier(1).setCode("c02").setName("n02"));
		entityManager.getTransaction().commit();
		entityManager.clear();
		System.out.println("DO IT AGAIN");
		entityManager.getTransaction().begin();
		entityManager.merge(new JpaEntity().setIdentifier(1).setCode("c02N").setName("n02"));
		entityManager.getTransaction().commit();
		/*
		JpaEntity jpaEntity = entityManager.find(JpaEntity.class, "2");
		assertThat(jpaEntity).isNotNull();
		assertThat(jpaEntity.getIdentifier()).isEqualTo("2");
		assertThat(jpaEntity.getCode()).isEqualTo("c02");
		assertThat(jpaEntity.getName()).isEqualTo("n02");
		*/
	}
	
	//@Test
	public void persistVersion(){
		System.out.println("---------------------- PERSIST Version --------------------------------");
		EntityManager entityManager = EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(new JpaEntityVersion().setCode("c01").setName("n01"));
		entityManager.getTransaction().commit();
		/*
		JpaEntity jpaEntity = entityManager.find(JpaEntity.class, "1");
		assertThat(jpaEntity).isNotNull();
		assertThat(jpaEntity.getIdentifier()).isEqualTo("1");
		assertThat(jpaEntity.getCode()).isEqualTo("c01");
		assertThat(jpaEntity.getName()).isEqualTo("n01");
		*/
	}
	
	@Test
	public void merge_as_persist_composite(){
		System.out.println("---------------------- MERGE as persist Composiste --------------------------------");
		EntityManager entityManager = EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.merge(new JpaEntityCompositeId().setIdentifier1("1")/*.setIdentifier2("2")*/);
		entityManager.getTransaction().commit();
	}
	
	@Test
	public void merge_as_persist_composite_class(){
		System.out.println("---------------------- MERGE as persist Composiste --------------------------------");
		EntityManager entityManager = EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.merge(new JpaEntityCompositeIdClass().setIdentifier(new CompositeIdClass().setIdentifier1("1").setIdentifier2("2")));
		entityManager.getTransaction().commit();
	}
	
	@Test
	public void merge_as_persistVersion(){
		System.out.println("---------------------- MERGE Version as persist --------------------------------");
		JpaEntityVersion jpaEntityVersion = null;
		EntityManager entityManager = EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY.createEntityManager();
		entityManager.getTransaction().begin();
		jpaEntityVersion = entityManager.merge(new JpaEntityVersion().setIdentifier(1).setCode("c02").setName("n02").setVersion(0));
		entityManager.getTransaction().commit();
		entityManager.clear();
		
		System.out.println("Do IT AGAIN");
		
		jpaEntityVersion.setName("NN");
		entityManager.getTransaction().begin();
		entityManager.merge(jpaEntityVersion);
		entityManager.getTransaction().commit();
		/*
		JpaEntity jpaEntity = entityManager.find(JpaEntity.class, "2");
		assertThat(jpaEntity).isNotNull();
		assertThat(jpaEntity.getIdentifier()).isEqualTo("2");
		assertThat(jpaEntity.getCode()).isEqualTo("c02");
		assertThat(jpaEntity.getName()).isEqualTo("n02");
		*/
	}
}
