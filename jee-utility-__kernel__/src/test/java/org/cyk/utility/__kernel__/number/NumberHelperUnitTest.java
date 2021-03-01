package org.cyk.utility.__kernel__.number;

import static org.assertj.core.api.Assertions.assertThat;
import static org.cyk.utility.__kernel__.number.NumberHelper.compare;
import static org.cyk.utility.__kernel__.number.NumberHelper.get;
import static org.cyk.utility.__kernel__.number.NumberHelper.isEqualToZero;
import static org.cyk.utility.__kernel__.number.NumberHelper.isGreaterThanZero;
import static org.cyk.utility.__kernel__.number.NumberHelper.isLessThanZero;
import static org.cyk.utility.__kernel__.number.NumberHelper.operate;

import java.math.BigDecimal;

import org.cyk.utility.__kernel__.computation.ComparisonOperator;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NumberHelperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	/**/
	
	@Test
	public void percentage_6_by_103_is_6(){
		assertThat(NumberHelper.computePercentageAsInteger(6, 103).intValue()).isEqualTo(6);
	}
	
	@Test
	public void percentage_97_by_103_is_94(){
		assertThat(NumberHelper.computePercentageAsInteger(97, 103).intValue()).isEqualTo(94);
	}
	
	/* get */
	
	@Test
	public void get_null(){
		assertThat(get(Integer.class,null)).isEqualTo(null);
	}
	
	@Test
	public void get_string_empty(){
		assertThat(get(Integer.class,"")).isEqualTo(null);
	}
	
	@Test
	public void get_string_blank(){
		assertThat(get(Integer.class," ")).isEqualTo(null);
	}
	
	@Test
	public void get_string_notBlank(){
		assertThat(get(Integer.class,"12")).isEqualTo(12);
	}
	
	@Test
	public void get_string_notBlank_exception(){
		Assertions.assertThrows(NumberFormatException.class, () -> {get(Integer.class,"a",null,Boolean.TRUE);} );
	}
	
	@Test
	public void get_string_default(){
		assertThat(get(Integer.class,null,15)).isEqualTo(15);
	}
	
	@Test
	public void get_string_exception_default(){
		assertThat(get(Integer.class,"a",15)).isEqualTo(15);
	}
	
	@Test
	public void get_string_byte(){
		assertThat(get(Byte.class,"12")).isEqualTo(Byte.valueOf("12"));
	}
	
	@Test
	public void get_string_short(){
		assertThat(get(Short.class,"12")).isEqualTo(Short.valueOf("12"));
	}
	
	@Test
	public void get_string_integer(){
		assertThat(get(Integer.class,"12")).isEqualTo(Integer.valueOf("12"));
	}
	
	@Test
	public void get_string_long(){
		assertThat(get(Long.class,"12")).isEqualTo(Long.valueOf("12"));
	}
	
	@Test
	public void get_string_float(){
		assertThat(get(Float.class,"12")).isEqualTo(Float.valueOf("12"));
	}
	
	@Test
	public void get_string_double(){
		assertThat(get(Double.class,"12")).isEqualTo(Double.valueOf("12"));
	}
	
	@Test
	public void get_string_bigdecimal(){
		assertThat(get(BigDecimal.class,"12")).isEqualTo(new BigDecimal("12"));
	}
	
	@Test
	public void get_integer_bigdecimal(){
		assertThat(NumberHelper.getInteger(new BigDecimal("12"))).isEqualTo(12);
	}
	
	/* compare */
	
	@Test
	public void compare_null_null_null(){
		assertThat(compare(null,null,null)).isFalse();
	}
	
	@Test
	public void compare_null_null_eq(){
		assertThat(compare(null,null,ComparisonOperator.EQ)).isTrue();
	}
	
	@Test
	public void compare_null_1_null(){
		assertThat(compare(null,1,null)).isFalse();
	}
	
	@Test
	public void compare_null_1_eq(){
		assertThat(compare(null,1,ComparisonOperator.EQ)).isFalse();
	}
	
	@Test
	public void compare_1_null_null(){
		assertThat(compare(1,null,null)).isFalse();
	}
	
	@Test
	public void compare_1_null_eq(){
		assertThat(compare(1,null,ComparisonOperator.EQ)).isFalse();
	}
	
	@Test
	public void compare_1_1_null(){
		assertThat(compare(1,1,null)).isFalse();
	}
	
	@Test
	public void isEqualToZero_true(){
		assertThat(isEqualToZero(0)).isTrue();
	}
	
	@Test
	public void isEqualToZero_false(){
		assertThat(isEqualToZero(1)).isFalse();
	}
	
	@Test
	public void isGreaterThanZero_true(){
		assertThat(isGreaterThanZero(1)).isTrue();
	}
	
	@Test
	public void isGreaterThanZero_false(){
		assertThat(isGreaterThanZero(0)).isFalse();
	}
	
	@Test
	public void isLessThanZero_true(){
		assertThat(isLessThanZero(-1)).isTrue();
	}
	
	@Test
	public void isLessThanZero_false(){
		assertThat(isLessThanZero(0)).isFalse();
	}
	
	/* operate */
	
	@Test
	public void operate_empty(){
		assertThat(operate(Operation.ADD)).isNull();
	}
	
	@Test
	public void operate_one(){
		assertThat(operate(Operation.ADD,1).intValue()).isEqualTo(1);
	}
	
	@Test
	public void operate_two(){
		assertThat(operate(Operation.ADD,1,2).intValue()).isEqualTo(3);
	}
	
	@Test
	public void operate_many(){
		assertThat(operate(Operation.ADD,1,2,4).intValue()).isEqualTo(7);
	}
	
	@Test
	public void operate_add(){
		assertThat(operate(Operation.ADD,2,4).intValue()).isEqualTo(6);
	}
	
	@Test
	public void operate_subtract(){
		assertThat(operate(Operation.SUBTRACT,2,4).intValue()).isEqualTo(-2);
	}
	
	@Test
	public void operate_multiply(){
		assertThat(NumberHelper.multiply(2,3).intValue()).isEqualTo(6);
	}
	
	@Test
	public void format_null(){
		assertThat(NumberHelper.format(null)).isEqualTo("0");
	}
	
	@Test
	public void format_0(){
		assertThat(NumberHelper.format(0)).isEqualTo("0");
	}
	
	@Test
	public void format_1(){
		assertThat(NumberHelper.format(1)).isEqualTo("1");
	}
	
	//@Test
	public void format_1000(){
		String string = NumberHelper.format(1000);
		assertThat(string).startsWith("1 000");
	}
	
	//@Test
	public void formatThousandSeparator(){
		//DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.FRENCH);		
		//System.out.println(NumberFormat.getInstance(Locale.FRENCH).format(18000000l));
		//assertThat(NumberHelper.multiply(2,3).intValue()).isEqualTo(6);
	}
}
