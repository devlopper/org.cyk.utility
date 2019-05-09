package org.cyk.utility.number;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class IntervalsUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void is_c_0_2_c_contains_0(){
		assertThat(__inject__(Intervals.class).add(0, 2).contains(0)).as("[0,2] does not contain 0").isTrue();
	}
	
	@Test
	public void is_c_0_2_c_contains_1(){
		assertThat(__inject__(Intervals.class).add(0, 2).contains(1)).as("[0,2] does not contain 1").isTrue();
	}
	
	@Test
	public void is_c_0_2_c_contains_2(){
		assertThat(__inject__(Intervals.class).add(0, 2).contains(2)).as("[0,2] does not contain 2").isTrue();
	}
	
	@Test
	public void is_c_0_2_c_and_c_10_15_c_contains_0(){
		assertThat(__inject__(Intervals.class).add(0, 2).add(10, 15).contains(0)).as("[0,2],[10,15] does not contain 0").isTrue();
	}
	
	@Test
	public void is_c_0_2_c_and_c_10_15_c_contains_10(){
		assertThat(__inject__(Intervals.class).add(0, 2).add(10, 15).contains(10)).as("[0,2],[10,15] does not contain 10").isTrue();
	}
	
	@Test
	public void is_c_0_2_c_and_c_10_15_c_contains_12(){
		assertThat(__inject__(Intervals.class).add(0, 2).add(10, 15).contains(12)).as("[0,2],[10,15] does not contain 12").isTrue();
	}
	
	/**/
	
	/*private static void contains(Object ){
		assertThat(__inject__(Interval.class).setLow(0).setHigh(2).contains(2)).as("[0,2] does not contain 1").isTrue();
	}*/
}
