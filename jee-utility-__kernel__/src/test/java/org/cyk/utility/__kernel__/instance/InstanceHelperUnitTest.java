package org.cyk.utility.__kernel__.instance;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class InstanceHelperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void build_null_null_null(){
		Object instance = InstanceHelper.build(null, null, (Collection<String>)null);
		assertThat(instance).isNull();
	}
	
	@Test
	public void build_notNull_null_null(){
		Object instance = InstanceHelper.build(Klass01.class, null, (Collection<String>)null);
		assertThat(instance).isNull();
	}
	
	@Test
	public void build_null_notNull_null(){
		Object instance = InstanceHelper.build(null, new Klass01(), (Collection<String>)null);
		assertThat(instance).isNull();
	}
	
	@Test
	public void build_klass01_klass01(){
		Klass01 instance = InstanceHelper.build(Klass01.class, new Klass01().setCode("c01"),List.of("code","name","age"));
		assertThat(instance).isNotNull();
		assertThat(instance.getCode()).isEqualTo("c01");
		assertThat(instance.getName()).isNull();
	}
	
	@Test
	public void build_klass01_klass02(){
		Klass01 instance = InstanceHelper.build(Klass01.class, new Klass02().setCode("c01").setMyName("yao").setAge("15"), Map.of("code","code","myName","name","age","age"));
		assertThat(instance).isNotNull();
		assertThat(instance.getCode()).isEqualTo("c01");
		assertThat(instance.getName()).isEqualTo("yao");
		assertThat(instance.getAge()).isEqualTo(15);
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Klass01 {		
		private String code;
		private String name;
		private Integer age;
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class Klass02 {
		private String code;
		private String myName;
		private String age;
	}
}
