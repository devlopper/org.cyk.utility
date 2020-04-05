package org.cyk.utility.__kernel__.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;
import java.util.Collection;

import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class PersistenceUnitHelperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void getTransientFields(){
		Collection<Field> fields = PersistenceUnitHelper.getTransientFields(Shop.class);
		assertThat(fields).isNotEmpty();
		assertThat(FieldHelper.getNames(fields)).containsExactly("productsTransient");
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Product {
		
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class Shop {
		private Collection<Product> productsNotTranstrient;
		@Transient private Collection<Product> productsTransient;
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class Sale {
		
		@ManyToOne
		private Product product;
		
	}
}
