package org.cyk.utility.__kernel__.string;

import static org.assertj.core.api.Assertions.assertThat;
import static org.cyk.utility.__kernel__.string.StringHelper.addToBeginIfDoesNotStartWith;
import static org.cyk.utility.__kernel__.string.StringHelper.addToEndIfDoesNotEndWith;
import static org.cyk.utility.__kernel__.string.StringHelper.applyCase;
import static org.cyk.utility.__kernel__.string.StringHelper.concatenate;
import static org.cyk.utility.__kernel__.string.StringHelper.defaultIfBlank;
import static org.cyk.utility.__kernel__.string.StringHelper.filter;
import static org.cyk.utility.__kernel__.string.StringHelper.get;
import static org.cyk.utility.__kernel__.string.StringHelper.getInvalidLines;
import static org.cyk.utility.__kernel__.string.StringHelper.getLines;
import static org.cyk.utility.__kernel__.string.StringHelper.getVariableNameFrom;
import static org.cyk.utility.__kernel__.string.StringHelper.removeInvalidLines;
import static org.cyk.utility.__kernel__.string.StringHelper.removeToBeginIfDoesStartWith;
import static org.cyk.utility.__kernel__.string.StringHelper.splitByCharacterTypeCamelCase;
import static org.cyk.utility.__kernel__.string.StringHelper.removeFromString;

import java.util.List;

import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class StringHelperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void splitByCharacterTypeCamelCase_(){
		assertThat(splitByCharacterTypeCamelCase("iAmAUser")).containsExactly("i","Am","A","User");
	}
	
	@Test
	public void defaultIfBlank_(){
		assertThat(defaultIfBlank("","Ho")).isEqualTo("Ho");
	}
	
	@Test
	public void getVariableNameFrom_(){
		assertThat(getVariableNameFrom("Person")).isEqualTo("person");
	}
	
	@Test
	public void concatenate_(){
		assertThat(concatenate(List.of("s1","s2"),"-")).isEqualTo("s1-s2");
	}
	
	@Test
	public void applyCase_(){
		assertThat(applyCase("abc",Case.FIRST_CHARACTER_UPPER)).isEqualTo("Abc");
	}
	
	@Test
	public void addToBeginIfDoesNotStartWith_(){
		assertThat(addToBeginIfDoesNotStartWith("abc","p")).isEqualTo("pabc");
	}
	
	@Test
	public void addToEndIfDoesNotEndWith_(){
		assertThat(addToEndIfDoesNotEndWith("abc","p")).isEqualTo("abcp");
	}
	
	@Test
	public void removeToBeginIfDoesStartWith_(){
		assertThat(removeToBeginIfDoesStartWith("abc","a")).isEqualTo("bc");
	}
	
	@Test
	public void get_(){
		assertThat(get(2)).isEqualTo("2");
	}
	
	@Test
	public void getLines_(){
		assertThat(getLines("l1\nl2")).containsExactly("l1\n","l2");
	}
	
	@Test
	public void getInvalidLines_(){
		assertThat(getInvalidLines("l1\nl2\n   \nl3",null)).containsExactly("   \n");
	}
	
	@Test
	public void removeInvalidLines_(){
		assertThat(removeInvalidLines("l1\nl2\n   \nl3",null)).isEqualTo("l1\nl2\nl3");
	}
	
	/* filter */
	
	@Test
	public void filter_null_null(){
		assertThat(filter(null,null)).isNull();
	}
	
	@Test
	public void filter_null_notNull(){
		assertThat(filter(null,"a")).isNull();
	}
	
	@Test
	public void filter_notNull_null(){
		assertThat(filter(List.of("s1"),null)).isNull();
	}
	
	@Test
	public void filter_notNull_notNull(){
		assertThat(filter(List.of("user","user.name","user.f1.f2","account","account.user","username"),"^user\\.")).containsExactlyInAnyOrder("user.name","user.f1.f2");
	}
	
	@Test
	public void filter_notNull_notNull_isRemoveTrue(){
		assertThat(filter(List.of("user","user.name","user.f1.f2","account","account.user","username"),"^user\\.",Boolean.TRUE)).containsExactlyInAnyOrder("name","f1.f2");
	}
	
	@Test
	public void removeFromString_notNull_notNull(){
		assertThat(removeFromString(List.of("user.name","user.f1.f2"),"^user\\.")).containsExactlyInAnyOrder("name","f1.f2");
	}
	
	@Test
	public void getWords(){
		assertThat(StringHelper.getWords(null, 3,"a")).containsExactly("a","a","a");
	}
}
