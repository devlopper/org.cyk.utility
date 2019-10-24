package org.cyk.utility.__kernel__.value;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.annotation.Configuration;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class CheckerUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void isNull_default_null() {
		assertThat(Checker.getInstance().isNull(null)).isTrue();
	}
	
	@Test
	public void isNull_configuration_null() {
		assertThat(DependencyInjection.injectByQualifiersClasses(Checker.class,Configuration.Class.class).isNull(null)).isTrue();
	}
	
	@Test
	public void isNull_configuration_startWithDollarAndBracket() {
		assertThat(DependencyInjection.injectByQualifiersClasses(Checker.class,Configuration.Class.class).isNull("${var}")).isTrue();
	}
	
}
