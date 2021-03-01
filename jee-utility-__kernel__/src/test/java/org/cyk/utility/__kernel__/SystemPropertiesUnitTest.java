package org.cyk.utility.__kernel__;
import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.__kernel__.maven.pom.Pom;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class SystemPropertiesUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	static {
		assertThat(Pom.INSTANCE.getMavenSurefirePluginConfigurationSystemProperty("unittest.property.01")).isEqualTo("value01");
	}
	
	@Test
	public void getUserDir(){
		assertThat(Pom.INSTANCE.getMavenSurefirePluginConfigurationSystemProperty("unittest.property.01")).isEqualTo("value01");
	}
}
