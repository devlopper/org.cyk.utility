package org.cyk.utility.__kernel__.persistence.query;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.__kernel__.persistence.query.QueryIdentifierBuilder;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class QueryIdentifierBuilderUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void build_read(){
		assertThat(QueryIdentifierBuilder.getInstance().build(Product.class, "read")).isEqualTo("Product.read");
	}
	
	@Test
	public void buildCountFrom(){
		assertThat(QueryIdentifierBuilder.getInstance().buildCountFrom("Product.read.xxx")).isEqualTo("Product.count.xxx");
	}
	
	public static class Product {}
}