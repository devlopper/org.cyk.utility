package org.cyk.utility.__kernel__.persistence.query;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class QueryIdentifierGetterUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void getRead(){
		assertThat(QueryIdentifierGetter.getInstance().get(Product.class, "read")).isEqualTo("Product.read");
	}
	
	@Test
	public void getCount(){
		assertThat(QueryIdentifierGetter.getInstance().getCountFromRead(Product.class,"read.xxx")).isEqualTo("Product.count.xxx");
	}
	
	public static class Product {}
}