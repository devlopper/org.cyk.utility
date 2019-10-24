package org.cyk.utility.__kernel__.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class VariableNameUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void build_null(){
		assertThat(VariableName.build((Collection<Object>)null)).isNull();
	}
	
	@Test
	public void build_empty(){
		assertThat(VariableName.build()).isNull();
	}
	
	@Test
	public void build_one(){
		assertThat(VariableName.build("v1")).isEqualTo("cyk.variable.v1");
	}
	
	@Test
	public void build_many(){
		assertThat(VariableName.build("v1","v2")).isEqualTo("cyk.variable.v1|v2");
	}
	
	@Test
	public void systemIdentifier(){
		assertThat(VariableName.SYSTEM_IDENTIFIER).isEqualTo("cyk.variable.system.identifier");
	}
	
	@Test
	public void systemVersion(){
		assertThat(VariableName.SYSTEM_VERSION).isEqualTo("cyk.variable.system.version");
	}
	
	@Test
	public void systemName(){
		assertThat(VariableName.SYSTEM_NAME).isEqualTo("cyk.variable.system.name");
	}
	
	@Test
	public void systemHost(){
		assertThat(VariableName.SYSTEM_HOST).isEqualTo("cyk.variable.system.host");
	}
	
	@Test
	public void systemPort(){
		assertThat(VariableName.SYSTEM_PORT).isEqualTo("cyk.variable.system.port");
	}
	
	@Test
	public void systemWebContext(){
		assertThat(VariableName.SYSTEM_WEB_CONTEXT).isEqualTo("cyk.variable.system.web.context");
	}
	
	@Test
	public void buildClassUniformResourceIdentifier(){
		assertThat(VariableName.buildClassUniformResourceIdentifier(Integer.class, null)).isEqualTo("cyk.variable.java.lang.Integer|uri");
	}
	
	@Test
	public void buildClassUniformResourceIdentifier_classifierIs1(){
		assertThat(VariableName.buildClassUniformResourceIdentifier(Integer.class, 1)).isEqualTo("cyk.variable.java.lang.Integer|1|uri");
	}
}
