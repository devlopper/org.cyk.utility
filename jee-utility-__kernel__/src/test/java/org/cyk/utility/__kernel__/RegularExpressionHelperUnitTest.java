package org.cyk.utility.__kernel__;

import static org.assertj.core.api.Assertions.assertThat;
import static org.cyk.utility.__kernel__.string.RegularExpressionHelper.buildIsExactly;
import static org.cyk.utility.__kernel__.string.RegularExpressionHelper.buildIsNotExactly;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class RegularExpressionHelperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void buildIsExactly_one() {
		assertThat(buildIsExactly("identifier")).isEqualTo("^(identifier)$");
	}
	
	@Test
	public void buildIsExactly_many() {
		assertThat(buildIsExactly("identifier","code")).isEqualTo("^(identifier|code)$");
	}
	
	@Test
	public void buildIsNotExactly_one() {
		assertThat(buildIsNotExactly("identifier")).isEqualTo("(^(?!identifier)|(?<!identifier)$)");
	}
	
	@Test
	public void buildIsNotExactly_many() {
		assertThat(buildIsNotExactly("identifier","code")).isEqualTo("(^(?!identifier)|(?<!identifier)$)(^(?!code)|(?<!code)$)");
	}
}

