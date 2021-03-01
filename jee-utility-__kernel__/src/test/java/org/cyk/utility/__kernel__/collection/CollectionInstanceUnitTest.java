package org.cyk.utility.__kernel__.collection;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class CollectionInstanceUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void getDefaultIndex(){
		assertThat(__inject__(Strings.class).getDefaultIndex()).isNull();
	}
	
	@Test
	public void getDefault(){
		assertThat(__inject__(Strings.class).add("b","a","c").getDefault()).isEqualTo("b");
	}
	
	@Test
	public void getDefault_defaultindexIs1(){
		assertThat(__inject__(Strings.class).add("b","a","c").setDefaultIndex(1).getDefault()).isEqualTo("a");
	}
	
	/**/
	
	public static class Strings extends AbstractCollectionInstanceImpl<String> implements CollectionInstance<String> {
		private static final long serialVersionUID = 1L;
		
	}
}
