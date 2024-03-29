package org.cyk.utility.time;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.__kernel__.number.NumberHelper;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class DurationBuilderUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void build() {
		assertDuration( __inject__(DurationBuilder.class).execute().getOutput(), null, null, null);
		assertDuration( __inject__(DurationBuilder.class).setNumberOfMillisecond(0l).execute().getOutput(), Short.valueOf("0"), Byte.valueOf("0"), Byte.valueOf("0"));
		assertDuration( __inject__(DurationBuilder.class).setNumberOfMillisecond(1l).execute().getOutput(), Short.valueOf("1"), Byte.valueOf("0"), Byte.valueOf("0"));
		assertDuration( __inject__(DurationBuilder.class).setNumberOfMillisecond(999l).execute().getOutput(), Short.valueOf("999"), Byte.valueOf("0"), Byte.valueOf("0"));
		assertDuration( __inject__(DurationBuilder.class).setNumberOfMillisecond(1000l).execute().getOutput(), Short.valueOf("0"), Byte.valueOf("1"), Byte.valueOf("0"));
		assertDuration( __inject__(DurationBuilder.class).setNumberOfMillisecond(1001l).execute().getOutput(), Short.valueOf("1"), Byte.valueOf("1"), Byte.valueOf("0"));
		assertDuration( __inject__(DurationBuilder.class).setNumberOfMillisecond(10000l).execute().getOutput(), Short.valueOf("0"), Byte.valueOf("10"), Byte.valueOf("0"));
		assertDuration( __inject__(DurationBuilder.class).setNumberOfMillisecond(10001l).execute().getOutput(), Short.valueOf("1"), Byte.valueOf("10"), Byte.valueOf("0"));
		assertDuration( __inject__(DurationBuilder.class).setNumberOfMillisecond(11001l).execute().getOutput(), Short.valueOf("1"), Byte.valueOf("11"), Byte.valueOf("0"));
		assertDuration( __inject__(DurationBuilder.class).setNumberOfMillisecond(60000l).execute().getOutput(), Short.valueOf("0"), Byte.valueOf("0"), Byte.valueOf("1"));
		assertDuration( __inject__(DurationBuilder.class).setNumberOfMillisecond(71001l).execute().getOutput(), Short.valueOf("1"), Byte.valueOf("11"), Byte.valueOf("1"));
	}
	
	/**/
	
	private void assertDuration(Duration duration,Short numberOfMillisecond,Byte numberOfSecond,Byte numberOfMinute) {
		assertThat(duration.getNumberOfMillisecond()).isEqualTo(NumberHelper.get(Short.class, numberOfMillisecond));
		assertThat(duration.getNumberOfSecond()).isEqualTo(NumberHelper.get(Byte.class, numberOfSecond));
		assertThat(duration.getNumberOfMinute()).isEqualTo(NumberHelper.get(Byte.class, numberOfMinute));
	}
}
