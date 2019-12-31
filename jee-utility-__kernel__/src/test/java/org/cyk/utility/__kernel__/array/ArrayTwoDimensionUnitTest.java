package org.cyk.utility.__kernel__.array;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class ArrayTwoDimensionUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void buildAssertionComparison_literal_null_equal_literal_null() {
		ArrayTwoDimensionString array = new ArrayTwoDimensionString();
		array.setFirstDimensionElementCount(3).setSecondDimensionElementCount(2);
		array.setValue(new String[][] {
			new String[] {"1","2","3"}
			,new String[] {"a","b","c"}
		});
		assertThat(array.get(0, 0)).isEqualTo("1");
		assertThat(array.get(0, 1)).isEqualTo("2");
		assertThat(array.get(0, 2)).isEqualTo("3");
		
		assertThat(array.get(1, 0)).isEqualTo("a");
		assertThat(array.get(1, 1)).isEqualTo("b");
		assertThat(array.get(1, 2)).isEqualTo("c");
	}
	
	
}
