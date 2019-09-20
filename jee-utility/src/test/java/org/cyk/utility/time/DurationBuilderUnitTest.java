package org.cyk.utility.time;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.number.NumberHelper;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class DurationBuilderUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void build() {
		assertDuration( __inject__(DurationBuilder.class).execute().getOutput(), null, null, null);
		assertDuration( __inject__(DurationBuilder.class).setNumberOfMillisecond(0l).execute().getOutput(), new Short("0"), new Byte("0"), new Byte("0"));
		assertDuration( __inject__(DurationBuilder.class).setNumberOfMillisecond(1l).execute().getOutput(), new Short("1"), new Byte("0"), new Byte("0"));
		assertDuration( __inject__(DurationBuilder.class).setNumberOfMillisecond(999l).execute().getOutput(), new Short("999"), new Byte("0"), new Byte("0"));
		assertDuration( __inject__(DurationBuilder.class).setNumberOfMillisecond(1000l).execute().getOutput(), new Short("0"), new Byte("1"), new Byte("0"));
		assertDuration( __inject__(DurationBuilder.class).setNumberOfMillisecond(1001l).execute().getOutput(), new Short("1"), new Byte("1"), new Byte("0"));
		assertDuration( __inject__(DurationBuilder.class).setNumberOfMillisecond(10000l).execute().getOutput(), new Short("0"), new Byte("10"), new Byte("0"));
		assertDuration( __inject__(DurationBuilder.class).setNumberOfMillisecond(10001l).execute().getOutput(), new Short("1"), new Byte("10"), new Byte("0"));
		assertDuration( __inject__(DurationBuilder.class).setNumberOfMillisecond(11001l).execute().getOutput(), new Short("1"), new Byte("11"), new Byte("0"));
		assertDuration( __inject__(DurationBuilder.class).setNumberOfMillisecond(60000l).execute().getOutput(), new Short("0"), new Byte("0"), new Byte("1"));
		assertDuration( __inject__(DurationBuilder.class).setNumberOfMillisecond(71001l).execute().getOutput(), new Short("1"), new Byte("11"), new Byte("1"));
	}
	
	/**/
	
	private void assertDuration(Duration duration,Short numberOfMillisecond,Byte numberOfSecond,Byte numberOfMinute) {
		assertThat(duration.getNumberOfMillisecond()).isEqualTo(NumberHelper.get(Short.class, numberOfMillisecond));
		assertThat(duration.getNumberOfSecond()).isEqualTo(NumberHelper.get(Byte.class, numberOfSecond));
		assertThat(duration.getNumberOfMinute()).isEqualTo(NumberHelper.get(Byte.class, numberOfMinute));
	}
}
