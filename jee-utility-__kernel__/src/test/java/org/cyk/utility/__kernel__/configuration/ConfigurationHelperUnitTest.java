package org.cyk.utility.__kernel__.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class ConfigurationHelperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void getValue_notdef(){
		assertThat(ConfigurationHelper.getValue("unittest.config.var.notdef")).isNull();
	}
}
