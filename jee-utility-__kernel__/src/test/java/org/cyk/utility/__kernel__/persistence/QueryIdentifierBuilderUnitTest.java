package org.cyk.utility.__kernel__.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.__kernel__.persistence.PersistenceHelperUnitTest.Product;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class QueryIdentifierBuilderUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void build(){
		assertThat(QueryIdentifierBuilder.getInstance().build(Product.class, "read")).isEqualTo("Product.read");
	}
	
}
