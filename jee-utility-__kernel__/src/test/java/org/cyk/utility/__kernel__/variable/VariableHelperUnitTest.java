package org.cyk.utility.__kernel__.variable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.cyk.utility.__kernel__.variable.VariableHelper.get;

import java.util.stream.Collectors;

import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class VariableHelperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		VariableHelper.clear();
	}
	
	@Test
	public void get_name_null_os_null_sys_null_ctx_null_req_null_is_null(){
		assertThat(get(null)).isNull();
	}
	
	@Test
	public void get_name_empty_os_null_sys_null_ctx_null_req_null_is_null(){
		assertThat(get("")).isNull();
	}
	
	@Test
	public void get_name_blank_os_null_sys_null_ctx_null_req_null_is_null(){
		assertThat(get(" ")).isNull();
	}
	
	@Test
	public void get_name_notBlankNotSet_os_null_sys_null_ctx_null_req_null_is_null(){
		assertThat(get("xxx")).isNull();
	}
	
	@Test
	public void get_name_unittest_config_var_01_os_env_sys_sys_ctx_null_req_null_is_env(){
		Variable variable = get("unittest.config.var.01");
		assertThat(variable).isNotNull();
		assertThat(variable.getLocation()).isEqualTo(VariableLocation.ENVIRONMENT);
		assertThat(variable.getValue()).isEqualTo("env");
	}
	
	@Test
	public void get_name_unittest_config_var_02_os_env_sys_null_ctx_null_req_null_is_env(){
		Variable variable = get("unittest.config.var.02");
		assertThat(variable).isNotNull();
		assertThat(variable.getLocation()).isEqualTo(VariableLocation.ENVIRONMENT);
		assertThat(variable.getValue()).isEqualTo("env");
	}
	
	@Test
	public void get_name_unittest_config_var_03_os_null_sys_sys_ctx_null_req_null_is_sys(){
		Variable variable = get("unittest.config.var.03");
		assertThat(variable).isNotNull();
		assertThat(variable.getLocation()).isEqualTo(VariableLocation.SYSTEM);
		assertThat(variable.getValue()).isEqualTo("sys");
	}
	
	@Test
	public void get_notdef(){
		Variable variable = get("unittest.config.var.notdef");
		assertThat(variable).isNotNull();
		assertThat(variable.getLocation()).isEqualTo(VariableLocation.ENVIRONMENT);
		assertThat(variable.getValue()).isEqualTo("${value}");
	}
	
	@Test
	public void read_notdef(){
		assertThat(VariableHelper.read("unittest.config.var.notdef")).isEqualTo("${value}");
	}
	
	@Test
	public void getFieldsNames(){
		VariableHelper.writeFieldName(Integer.class, "f1","nf2");
		VariableHelper.writeFieldName(Integer.class, "f2","nf2");
		assertThat(VariableHelper.getFieldsNames(Integer.class,null).stream().map(Variable::getName).collect(Collectors.toList()))
			.containsExactlyInAnyOrder("cyk.variable.java.lang.Integer-f1","cyk.variable.java.lang.Integer-f2");
	}
	
	@Test
	public void read_FieldName_f1_f2_is_f2(){
		String name = VariableName.buildFieldName(Integer.class, "f1");
		VariableHelper.write(name, "f2");
		assertThat(VariableHelper.readFieldName(Integer.class, "f1")).isEqualTo("f2");
	}
	
	@Test
	public void read_FieldName_f1_f2_is_f1(){
		String name = VariableName.buildFieldName(Integer.class, "f1");
		VariableHelper.write(name, "f2");
		assertThat(VariableHelper.readFieldNameByValue(Integer.class,null, "f2")).isEqualTo("f1");
	}
	
	@Test
	public void read_FieldName_f3_f4_is_f3(){
		VariableHelper.read("cyk.variable.java.lang.Integer-f3");
		assertThat(VariableHelper.readFieldNameByValue(Integer.class,null, "f4")).isEqualTo("f3");
	}
}
