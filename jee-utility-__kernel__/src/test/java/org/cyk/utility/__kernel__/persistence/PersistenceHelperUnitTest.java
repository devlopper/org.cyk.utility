package org.cyk.utility.__kernel__.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.persistence.ManyToOne;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class PersistenceHelperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		PersistenceHelper.clear();
	}
	
	@Test
	public void areRelated_Product_Shop_ManyToOne_isFalse(){
		assertThat(PersistenceHelper.areRelated(Shop.class, Product.class, ManyToOne.class)).isFalse();
	}
	
	@Test
	public void areRelated_Sale_Shop_ManyToOne_isFalse(){
		assertThat(PersistenceHelper.areRelated(Sale.class, Shop.class, ManyToOne.class)).isFalse();
	}
	
	@Test
	public void areRelated_Sale_Product_ManyToOne_isTrue(){
		assertThat(PersistenceHelper.areRelated(Sale.class, Product.class, ManyToOne.class)).isTrue();
	}
	
	@Test
	public void sort_Product_Shop_Sale(){
		List<Class<?>> classes = CollectionHelper.listOf(Product.class,Shop.class,Sale.class);
		PersistenceHelper.sort(classes);
		assertThat(classes).containsExactly(Product.class,Shop.class,Sale.class);
	}
	
	@Test
	public void sort_Shop_Product_Sale(){
		List<Class<?>> classes = CollectionHelper.listOf(Shop.class,Product.class,Sale.class);
		PersistenceHelper.sort(classes);
		assertThat(classes).containsExactly(Shop.class,Product.class,Sale.class);
	}
	
	@Test
	public void sort_Sale_Product_Shop(){
		List<Class<?>> classes = CollectionHelper.listOf(Sale.class,Shop.class,Product.class);
		PersistenceHelper.sort(classes);
		assertThat(classes).containsExactly(Product.class,Shop.class,Sale.class);
	}
	
	@Test
	public void sort_Sale_Product_Shop_descending(){
		List<Class<?>> classes = CollectionHelper.listOf(Sale.class,Shop.class,Product.class);
		PersistenceHelper.sort(classes,Boolean.FALSE);
		assertThat(classes).containsExactly(Sale.class,Shop.class,Product.class);
	}
	
	@Test
	public void getQueryIdentifier(){
		assertThat(PersistenceHelper.QUERY_IDENTIFIERS.size()).isEqualTo(0);
		assertThat(PersistenceHelper.getQueryIdentifier(Sale.class,"read")).isEqualTo("Sale.read");
		assertThat(PersistenceHelper.QUERY_IDENTIFIERS.size()).isEqualTo(1);
		assertThat(PersistenceHelper.getQueryIdentifier(Sale.class,"read")).isEqualTo("Sale.read");
		assertThat(PersistenceHelper.QUERY_IDENTIFIERS.size()).isEqualTo(1);
		assertThat(PersistenceHelper.getQueryIdentifier(Product.class,"read")).isEqualTo("Product.read");
		assertThat(PersistenceHelper.QUERY_IDENTIFIERS.size()).isEqualTo(2);
		assertThat(PersistenceHelper.getQueryIdentifier(Product.class,"read")).isEqualTo("Product.read");
		assertThat(PersistenceHelper.QUERY_IDENTIFIERS.size()).isEqualTo(2);
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
}
