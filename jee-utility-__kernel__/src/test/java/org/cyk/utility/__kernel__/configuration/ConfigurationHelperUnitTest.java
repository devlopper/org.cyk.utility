package org.cyk.utility.__kernel__.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.cyk.utility.__kernel__.configuration.ConfigurationHelper.getVariable;

import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class ConfigurationHelperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void getVariable_name_null_os_null_sys_null_ctx_null_req_null_is_null(){
		assertThat(getVariable(null)).isNull();
	}
	
	@Test
	public void getVariable_name_empty_os_null_sys_null_ctx_null_req_null_is_null(){
		assertThat(getVariable("")).isNull();
	}
	
	@Test
	public void getVariable_name_blank_os_null_sys_null_ctx_null_req_null_is_null(){
		assertThat(getVariable(" ")).isNull();
	}
	
	@Test
	public void getVariable_name_notBlankNotSet_os_null_sys_null_ctx_null_req_null_is_null(){
		assertThat(getVariable("xxx")).isNull();
	}
	
	@Test
	public void getVariable_name_unittest_config_var_01_os_env_sys_sys_ctx_null_req_null_is_env(){
		Variable variable = getVariable("unittest.config.var.01");
		assertThat(variable).isNotNull();
		assertThat(variable.getLocation()).isEqualTo(Location.ENVIRONMENT);
		assertThat(variable.getValue()).isEqualTo("env");
	}
	
	@Test
	public void getVariable_name_unittest_config_var_02_os_env_sys_null_ctx_null_req_null_is_env(){
		Variable variable = getVariable("unittest.config.var.02");
		assertThat(variable).isNotNull();
		assertThat(variable.getLocation()).isEqualTo(Location.ENVIRONMENT);
		assertThat(variable.getValue()).isEqualTo("env");
	}
	
	@Test
	public void getVariable_name_unittest_config_var_03_os_null_sys_sys_ctx_null_req_null_is_sys(){
		Variable variable = getVariable("unittest.config.var.03");
		assertThat(variable).isNotNull();
		assertThat(variable.getLocation()).isEqualTo(Location.SYSTEM);
		assertThat(variable.getValue()).isEqualTo("sys");
	}
}
