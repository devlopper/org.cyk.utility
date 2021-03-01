package org.cyk.utility.__kernel__.string;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class RegularExpressionHelperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void isFileName_name_dot_extension_3_true() {
		assertThat(RegularExpressionHelper.isFileName("f.ext")).isTrue();
	}
	
	@Test
	public void isFileName_name_dot_extension_2_true() {
		assertThat(RegularExpressionHelper.isFileName("f.ex")).isTrue();
	}
	
	@Test
	public void isFileName_name_dot_extension_1_true() {
		assertThat(RegularExpressionHelper.isFileName("f.e")).isTrue();
	}
	
	@Test
	public void isFileName_name_dot_false() {
		assertThat(RegularExpressionHelper.isFileName("f.")).isFalse();
	}
	
	@Test
	public void isFileName_dot_extension_false() {
		assertThat(RegularExpressionHelper.isFileName(".ext")).isFalse();
	}
	
	@Test
	public void isFileNameHavingExtensions() {
		assertThat(RegularExpressionHelper.isFileNameHavingExtensions("f.txt", Arrays.asList("txt"))).isTrue();
	}
	
	@Test
	public void isFileNameHavingExtensions_case() {
		assertThat(RegularExpressionHelper.isFileNameHavingExtensions("f.txt", Arrays.asList("TxT"))).isTrue();
	}
	
	@Test
	public void matchPackageNameSubStringTokenLocation() {
		assertThat(RegularExpressionHelper.match("a.mypack.b", "[.]{0,1}mypack[.]{0,1}")).isTrue();
	}
	
	@Test
	public void matchSystemServerRepresentationEntity() {
		assertThat(RegularExpressionHelper.match("representation.entities.MyClassDto", "representation.entities..+Dto$")).isTrue();
	}
	
	@Test
	public void matchStartLineEndLine() {
		assertThat(RegularExpressionHelper.match("°\nHello",  "[°]\n")).isTrue();
	}
	
	@Test
	public void isContainAlphabeticCharacter_null() {
		assertThat(RegularExpressionHelper.isContainAlphabeticCharacter(null)).isFalse();
	}
	
	@Test
	public void isContainAlphabeticCharacter_empty() {
		assertThat(RegularExpressionHelper.isContainAlphabeticCharacter("")).isFalse();
	}
	
	@Test
	public void isContainAlphabeticCharacter_blank() {
		assertThat(RegularExpressionHelper.isContainAlphabeticCharacter(" ")).isFalse();
	}
	
	@Test
	public void isContainAlphabeticCharacter_1() {
		assertThat(RegularExpressionHelper.isContainAlphabeticCharacter("1")).isFalse();
	}
	
	@Test
	public void isContainAlphabeticCharacter_a() {
		assertThat(RegularExpressionHelper.isContainAlphabeticCharacter("a")).isTrue();
	}
	
	@Test
	public void isContainAlphabeticCharacter_1a() {
		assertThat(RegularExpressionHelper.isContainAlphabeticCharacter("1a")).isTrue();
	}
	
	@Test
	public void isContainAlphabeticCharacter_a1() {
		assertThat(RegularExpressionHelper.isContainAlphabeticCharacter("a1")).isTrue();
	}
	
	@Test
	public void isContainAlphabeticCharacter_1a1() {
		assertThat(RegularExpressionHelper.isContainAlphabeticCharacter("1a1")).isTrue();
	}
	
	@Test
	public void isContainAlphabeticCharacter_a1a() {
		assertThat(RegularExpressionHelper.isContainAlphabeticCharacter("a1a")).isTrue();
	}
	
	@Test
	public void isContainAlphabeticCharacter_false() {
		assertThat(RegularExpressionHelper.isContainAlphabeticCharacter("°")).isFalse();
	}
}
