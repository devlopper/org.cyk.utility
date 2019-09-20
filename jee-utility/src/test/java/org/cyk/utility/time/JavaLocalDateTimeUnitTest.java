package org.cyk.utility.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.cyk.utility.number.NumberHelper;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class JavaLocalDateTimeUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void parse_date_01_02_2001() {
		assertLocalDate("01/02/2001", 1, 2, 2001);
	}
	
	@Test
	public void parse_date_1_2_2001() {
		assertLocalDate("1/2/2001", 1, 2, 2001);
	}
	
	@Test
	public void parse_date_1_2_01() {
		assertLocalDate("1/2/2001", 1, 2, 2001);
	}
	
	@Test
	public void parse_dateTime_01_02_2001_12_30_0_0() {
		assertLocalDateTime("01/02/2001 12:30", 1, 2, 2001,12,30,0,0);
	}
	
	/**/

	private void assertLocalDate(LocalDate date,Object day,Object month,Object year) {
		org.assertj.core.api.Assertions.assertThat(date.getDayOfMonth()).isEqualTo(NumberHelper.getInteger(day));
		org.assertj.core.api.Assertions.assertThat(date.getMonthValue()).isEqualTo(NumberHelper.getInteger(month));
		org.assertj.core.api.Assertions.assertThat(date.getYear()).isEqualTo(NumberHelper.getInteger(year));
	}
	
	protected void assertLocalDate(String string,Object day,Object month,Object year) {
		LocalDate date = LocalDate.parse(string, DateTimeFormatter.ofPattern("d/M/y"));
		assertLocalDate(date, day, month, year);
	}

	private void assertLocalTime(LocalTime time,Object hour,Object minute,Object second,Object nanosecond) {
		org.assertj.core.api.Assertions.assertThat(time.getHour()).isEqualTo(NumberHelper.getInteger(hour));
		org.assertj.core.api.Assertions.assertThat(time.getMinute()).isEqualTo(NumberHelper.getInteger(minute));
		//org.assertj.core.api.Assertions.assertThat(time.getSecond()).isEqualTo(NumberHelper.getInteger(second));
		//org.assertj.core.api.Assertions.assertThat(time.getNano()).isEqualTo(NumberHelper.getInteger(nanosecond));
	}
	
	protected void assertLocalTime(String string,Object hour,Object minute,Object second,Object nanosecond) {
		LocalTime time = LocalTime.parse(string, DateTimeFormatter.ofPattern("H:m"));
		assertLocalTime(time, hour, minute, second, nanosecond);
	}
	
	private void assertLocalDateTime(LocalDateTime dateTime,Object day,Object month,Object year,Object hour,Object minute,Object second,Object nanosecond) {
		assertLocalDate(dateTime.toLocalDate(), day, month, year);
		assertLocalTime(dateTime.toLocalTime(), hour, minute, second, nanosecond);
	}
	
	protected void assertLocalDateTime(String string,Object day,Object month,Object year,Object hour,Object minute,Object second,Object nanosecond) {
		assertLocalDateTime(LocalDateTime.parse(string, DateTimeFormatter.ofPattern("d/M/y H:m")), day, month, year, hour, minute, second, nanosecond);
	}
}
