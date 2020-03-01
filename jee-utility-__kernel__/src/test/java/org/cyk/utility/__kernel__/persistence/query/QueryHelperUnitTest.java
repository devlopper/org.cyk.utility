package org.cyk.utility.__kernel__.persistence.query;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import javax.persistence.ManyToOne;
import javax.persistence.Persistence;

import org.cyk.utility.__kernel__.persistence.EntityCreator;
import org.cyk.utility.__kernel__.persistence.EntityFinder;
import org.cyk.utility.__kernel__.persistence.EntityManagerFactoryGetterImpl;
import org.cyk.utility.__kernel__.persistence.EntityReader;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class QueryHelperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		QueryHelper.clear();
	}
	
	@Test
	public void getQueryIdentifier(){
		assertThat(QueryHelper.IDENTIFIERS.size()).isEqualTo(0);
		assertThat(QueryHelper.getIdentifier(Sale.class,"read")).isEqualTo("Sale.read");
		assertThat(QueryHelper.IDENTIFIERS.size()).isEqualTo(1);
		assertThat(QueryHelper.getIdentifier(Sale.class,"read")).isEqualTo("Sale.read");
		assertThat(QueryHelper.IDENTIFIERS.size()).isEqualTo(1);
		assertThat(QueryHelper.getIdentifier(Product.class,"read")).isEqualTo("Product.read");
		assertThat(QueryHelper.IDENTIFIERS.size()).isEqualTo(2);
		assertThat(QueryHelper.getIdentifier(Product.class,"read")).isEqualTo("Product.read");
		assertThat(QueryHelper.IDENTIFIERS.size()).isEqualTo(2);
	}
	
	@Test
	public void addQuery(){
		assertThat(QueryHelper.QUERIES.getSize()).isEqualTo(0);
		QueryHelper.getQueries().add(new Query());
		assertThat(QueryHelper.QUERIES.getSize()).isEqualTo(1);
	}
	
	@Test
	public void create(){
		EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("pu");
		TestedEntityParent testedEntity = EntityFinder.getInstance().find(TestedEntityParent.class, "1");
		assertThat(testedEntity).isNull();
		EntityCreator.getInstance().createOneInTransaction(new TestedEntityParent().setIdentifier("1"));
		testedEntity = EntityFinder.getInstance().find(TestedEntityParent.class, "1");
		assertThat(testedEntity).isNotNull();
	}
	
	@Test
	public void readAll(){
		QueryHelper.getQueries().setIsRegisterableToEntityManager(Boolean.TRUE);
		EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("pu");
		Collection<TestedEntityParent> testedEntities = EntityReader.getInstance().read(TestedEntityParent.class);
		assertThat(testedEntities).isNull();
		EntityCreator.getInstance().createOneInTransaction(new TestedEntityParent().setIdentifier("1"));
		testedEntities = EntityReader.getInstance().read(TestedEntityParent.class);
		assertThat(testedEntities).isNotNull();
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Product {
		
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class Shop {
		
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class Sale {
		
		@ManyToOne
		private Product product;
		
	}
	
	/**/
	
}
