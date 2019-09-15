package org.cyk.utility.regularexpression;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

public class RegularExpressionHelperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void isFileName_name_dot_extension_3_true() {
		assertThat(RegularExpressionHelperImpl.__isFileName__("f.ext")).isTrue();
	}
	
	@Test
	public void isFileName_name_dot_extension_2_true() {
		assertThat(RegularExpressionHelperImpl.__isFileName__("f.ex")).isTrue();
	}
	
	@Test
	public void isFileName_name_dot_extension_1_true() {
		assertThat(RegularExpressionHelperImpl.__isFileName__("f.e")).isTrue();
	}
	
	@Test
	public void isFileName_name_dot_false() {
		assertThat(RegularExpressionHelperImpl.__isFileName__("f.")).isFalse();
	}
	
	@Test
	public void isFileName_dot_extension_false() {
		assertThat(RegularExpressionHelperImpl.__isFileName__(".ext")).isFalse();
	}
	
	@Test
	public void isFileNameHavingExtensions() {
		assertThat(RegularExpressionHelperImpl.__isFileNameHavingExtensions__("f.txt", Arrays.asList("txt"))).isTrue();
	}
	
	@Test
	public void isFileNameHavingExtensions_case() {
		assertThat(RegularExpressionHelperImpl.__isFileNameHavingExtensions__("f.txt", Arrays.asList("TxT"))).isTrue();
	}
	
	@Test
	public void matchPackageNameSubStringTokenLocation() {
		String expression = "[.]{0,1}mypack[.]{0,1}";
		assertionHelper.assertTrue(__inject__(RegularExpressionHelper.class).match("a.mypack.b", expression));
		assertionHelper.assertTrue(__inject__(RegularExpressionHelper.class).match("a.mypack", expression));
		assertionHelper.assertTrue(__inject__(RegularExpressionHelper.class).match("mypack.b", expression));
		assertionHelper.assertTrue(__inject__(RegularExpressionHelper.class).match("mypack", expression));
	}
	
	@Test
	public void matchSystemServerRepresentationEntity() {
		String expression = "representation.entities..+Dto$";
		assertionHelper.assertTrue(__inject__(RegularExpressionHelper.class).match("representation.entities.MyClassDto", expression));
		assertionHelper.assertTrue(__inject__(RegularExpressionHelper.class).match("representation.entities.p1.MyClassDto", expression));
		assertionHelper.assertTrue(__inject__(RegularExpressionHelper.class).match("p.representation.entities.p1.MyClassDto", expression));
		assertionHelper.assertTrue(__inject__(RegularExpressionHelper.class).match("p.representation.entities.MyClassDto", expression));
	}
	
	@Test
	public void matchStartLineEndLine() {
		assertThat(__inject__(RegularExpressionHelper.class).match("°\nHello", "[°]\n")).isTrue();
	}
	
	@Test
	public void isContainAlphabeticCharacter_null() {
		assertThat(RegularExpressionHelperImpl.__isContainAlphabeticCharacter__(null)).isFalse();
	}
	
	@Test
	public void isContainAlphabeticCharacter_empty() {
		assertThat(RegularExpressionHelperImpl.__isContainAlphabeticCharacter__("")).isFalse();
	}
	
	@Test
	public void isContainAlphabeticCharacter_blank() {
		assertThat(RegularExpressionHelperImpl.__isContainAlphabeticCharacter__(" ")).isFalse();
	}
	
	@Test
	public void isContainAlphabeticCharacter_1() {
		assertThat(RegularExpressionHelperImpl.__isContainAlphabeticCharacter__("1")).isFalse();
	}
	
	@Test
	public void isContainAlphabeticCharacter_a() {
		assertThat(RegularExpressionHelperImpl.__isContainAlphabeticCharacter__("a")).isTrue();
	}
	
	@Test
	public void isContainAlphabeticCharacter_1a() {
		assertThat(RegularExpressionHelperImpl.__isContainAlphabeticCharacter__("1a")).isTrue();
	}
	
	@Test
	public void isContainAlphabeticCharacter_a1() {
		assertThat(RegularExpressionHelperImpl.__isContainAlphabeticCharacter__("a1")).isTrue();
	}
	
	@Test
	public void isContainAlphabeticCharacter_1a1() {
		assertThat(RegularExpressionHelperImpl.__isContainAlphabeticCharacter__("1a1")).isTrue();
	}
	
	@Test
	public void isContainAlphabeticCharacter_a1a() {
		assertThat(RegularExpressionHelperImpl.__isContainAlphabeticCharacter__("a1a")).isTrue();
	}
	
	@Test
	public void isContainAlphabeticCharacter_false() {
		assertThat(RegularExpressionHelperImpl.__isContainAlphabeticCharacter__("°")).isFalse();
	}
}
