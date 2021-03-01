package org.cyk.utility.__kernel__.time;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class TimeHelperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void formatLocalDateTimeFromString(){
		assertThat(TimeHelper.formatLocalDateTimeFromString("2019-12-07T09:18:41.585586", "dd/MM/yyyy")).isEqualTo("07/12/2019");
	}
	
}
