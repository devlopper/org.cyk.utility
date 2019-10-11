package org.cyk.utility.__kernel__.value;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class CheckerUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void isNull_null() {
		assertThat(Checker.INSTANCE.isNull(null)).isTrue();
	}
	
	@Test
	public void isNull_custom_string() {
		assertThat(new CheckerConfigValue(){}.isNull("${var}")).isTrue();
	}
	
	public static interface CheckerConfigValue extends Checker {
		@Override
		default Boolean isNull(Object value) {
			if(Checker.super.isNull(value))
				return Boolean.TRUE;
			if(value instanceof String && ((String)value).trim().startsWith("${"))
				return Boolean.TRUE;			
			return Boolean.FALSE;
		}	
	}
}
