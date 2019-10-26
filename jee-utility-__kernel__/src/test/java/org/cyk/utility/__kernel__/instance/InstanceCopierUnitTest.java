package org.cyk.utility.__kernel__.instance;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.cyk.utility.__kernel__.configuration.ConfigurationHelper;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.cyk.utility.__kernel__.variable.VariableHelper;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class InstanceCopierUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		InstanceGetterImpl.clear();
		ConfigurationHelper.clear();
	}
	
	@Test
	public void copy_functionType_representation_to_persistence(){
		FunctionTypeDto functionTypeDto = new FunctionTypeDto().setIdentifier("i01").setCode("c01");
		FunctionType functionType = new FunctionType();
		InstanceHelper.copy(functionTypeDto, functionType, List.of("identifier","code"));
		assertThat(functionType.getIdentifier()).isEqualTo("i01");
		assertThat(functionType.getCode()).isEqualTo("c01");
	}
	
	@Test
	public void copy_function_representation_to_persistence(){
		InstanceGetterImpl.add(new FunctionType().setIdentifier("0123").setCode("t01"));
		FunctionDto functionDto = new FunctionDto().setCode("c01").setType(new FunctionTypeDto().setCode("t01"));
		Function function = new Function();
		InstanceHelper.copy(functionDto, function, List.of("code","type"));
		assertThat(function.getCode()).isEqualTo("c01");
		assertThat(function.getType()).isNotNull();
		assertThat(function.getType().getIdentifier()).isEqualTo("0123");
		assertThat(function.getType().getCode()).isEqualTo("t01");
	}
	
	@Test
	public void copy_user_representation_to_persistence(){
		InstanceGetterImpl.add(new Function().setIdentifier("0123").setCode("c01"));
		UserDto userDto = new UserDto().setIdentifier("i01").setFunctions(List.of(new FunctionDto().setCode("c01")));
		User user = new User();
		InstanceHelper.copy(userDto, user, List.of("identifier","functions"));
		assertThat(user.getIdentifier()).isEqualTo("i01");
		assertThat(user.getFunctions()).isNotNull();
		assertThat(user.getFunctions()).isNotEmpty();
		assertThat(user.getFunctions().stream().map(x -> x == null ? null : x.getCode()).collect(Collectors.toList())).containsExactlyInAnyOrder("c01");
	}
	
	@Test
	public void copy_userAccount_representation_to_persistence(){
		InstanceGetterImpl.add(new User().setIdentifier("u01").setName("Zadi"));
		InstanceGetterImpl.add(new Function().setIdentifier("0123").setCode("f01"));
		UserAccountDto userAccountDto = new UserAccountDto().setIdentifier("ua01");
		userAccountDto.setUser(new UserDto().setIdentifier("u01").setName("Konan").setFunctions(List.of(new FunctionDto().setCode("f01"))));
		UserAccount userAccount = new UserAccount();
		InstanceHelper.copy(userAccountDto, userAccount, List.of("identifier","user.name","user.functions"));
		assertThat(userAccount.getIdentifier()).isEqualTo("ua01");
		assertThat(userAccount.getUser()).isNotNull();
		assertThat(userAccount.getUser().getIdentifier()).isEqualTo("u01");
		assertThat(userAccount.getUser().getName()).isEqualTo("Konan");
		assertThat(userAccount.getUser().getFunctions()).isNotNull();
		assertThat(userAccount.getUser().getFunctions()).isNotEmpty();
		assertThat(userAccount.getUser().getFunctions().stream().map(x -> x == null ? null : x.getCode()).collect(Collectors.toList())).containsExactlyInAnyOrder("f01");
	}
	
	@Test
	public void copy_fromMap_string(){
		CopyFromMap object = new CopyFromMap();
		assertThat(object.getString()).isNull();
		assertThat(object.getInteger()).isNull();
		assertThat(object.getLong_()).isNull();
		InstanceHelper.copy(Map.of("string","my string","integer","12","long_","159"), object,Map.of("string","string"));
		assertThat(object.getString()).isEqualTo("my string");
		assertThat(object.getInteger()).isNull();
		assertThat(object.getLong_()).isNull();
	}
	
	@Test
	public void copy_fromMap_keyDifferentToFieldName(){
		VariableHelper.writeFieldName(CopyFromMap.class, "the_string", "string");
		CopyFromMap object = new CopyFromMap();
		assertThat(object.getString()).isNull();
		assertThat(object.getInteger()).isNull();
		assertThat(object.getLong_()).isNull();
		Map<String,String> map = Map.of("the_string","my string","integer","12","long_","159");
		InstanceHelper.copy(map, object,map.keySet());
		assertThat(object.getString()).isEqualTo("my string");
		assertThat(object.getInteger()).isEqualTo(12);
		assertThat(object.getLong_()).isEqualTo(159l);
	}
	
	@Test
	public void copyFromJson_keyDifferentToFieldName(){
		VariableHelper.writeFieldName(CopyFromMap.class, "the_string", "string");
		CopyFromMap object = new CopyFromMap();
		assertThat(object.getString()).isNull();
		assertThat(object.getInteger()).isNull();
		assertThat(object.getLong_()).isNull();
		InstanceCopier.getInstance().copyFromJson("{\"the_string\":\"my string\",\"integer\":\"12\",\"long_\":\"159\"}", object);
		assertThat(object.getString()).isEqualTo("my string");
		assertThat(object.getInteger()).isEqualTo(12);
		assertThat(object.getLong_()).isEqualTo(159l);
	}
	
	/**/
	
	/* Persistence */
	
	@Getter @Setter @Accessors(chain=true) @Entity
	public static class UserAccount {		
		private String identifier;
		@ManyToOne private User user;
	}
	
	@Getter @Setter @Accessors(chain=true) @Entity
	public static class User {
		private String identifier;
		private String name;
		@Transient private Collection<Function> functions;
	}
	
	@Getter @Setter @Accessors(chain=true) @Entity
	public static class Function {
		private String identifier,code;
		private FunctionType type;
	}
	
	@Getter @Setter @Accessors(chain=true) @Entity
	public static class FunctionType {
		private String identifier,code;
	}
	
	/* Representation */
	
	@Getter @Setter @Accessors(chain=true) @XmlRootElement
	public static class UserAccountDto {		
		private String identifier;
		private UserDto user;
	}
	
	@Getter @Setter @Accessors(chain=true) @XmlRootElement
	public static class UserDto {
		private String identifier;
		private String name;
		private Collection<FunctionDto> functions;
	}
	
	@Getter @Setter @Accessors(chain=true) @XmlRootElement
	public static class FunctionDto {
		private String identifier,code;
		private FunctionTypeDto type;
	}
	
	@Getter @Setter @Accessors(chain=true) @XmlRootElement
	public static class FunctionTypeDto {
		private String identifier,code;
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class CopyFromMap {
		private String string;
		private Integer integer;
		private Long long_;
	}
}
