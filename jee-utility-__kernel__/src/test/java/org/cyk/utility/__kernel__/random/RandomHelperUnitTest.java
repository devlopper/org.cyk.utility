package org.cyk.utility.__kernel__.random;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class RandomHelperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void getFirstName(){
		assertThat(RandomHelper.HUMAN_FIRST_NAMES.contains(RandomHelper.getFirstName()));
	}
	
	@Test
	public void getMaleLastName(){
		assertThat(RandomHelper.HUMAN_MALE_LAST_NAMES.contains(RandomHelper.getMaleLastName()));
	}
}
