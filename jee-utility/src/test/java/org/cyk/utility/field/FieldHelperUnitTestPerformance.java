package org.cyk.utility.field;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.cyk.utility.time.Duration;
import org.cyk.utility.time.DurationBuilder;
import org.cyk.utility.time.DurationStringBuilder;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class FieldHelperUnitTestPerformance extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void get_100(){
		get(100,100);
	}	
	
	@Test
	public void get_1000(){
		get(1000,100);
	}	
	
	@Test
	public void get_10000(){
		get(10000,1000);
	}	
	
	@Test
	public void get_100000(){
		get(100000,2000);
	}	
	
	private void get(Integer numberOfCalls,Integer expectedMaximumNumberOfMillisecond) {
		DurationBuilder durationBuilder = __inject__(DurationBuilder.class).setBeginToNow();
		Long t = System.currentTimeMillis();
		for(Integer index = 0 ; index < numberOfCalls ; index = index + 1)
			__inject__(FieldHelper.class).getField(MyClass01.class, "intField");
		t = System.currentTimeMillis() - t;
		Duration duration = durationBuilder.setEndToNow().execute().getOutput();
		System.out.println(String.format("#calls=%s,duration=%s",numberOfCalls, __inject__(DurationStringBuilder.class).setDuration(duration).execute().getOutput()));
		assertThat(t).isLessThanOrEqualTo(expectedMaximumNumberOfMillisecond);
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class MyClass01 {
		private int intField;
		private Integer integerField;
		private String stringField;
		private long longValue1;
		private Long longValue2;
		private Date dateField;
		private Object x;
		private MyClass01Sub sub;
		
		public MyClass01 setLongValue1(long value) {
			this.longValue1 = value * 2;
			return this;
		}
		
		public MyClass01 setMyProperty(Object value) {
			this.x = value;
			return this;
		}
		
		public Object getMyProperty() {
			return x;
		}
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class MyClass01Sub {
		
		private int sIntField;
		private Integer sIntegerField;
		private String sStringField;
		private long sLongValue1;
		private Long sLongValue2;
		private Date sDateField;
		private Object sX;
		
	}
}
