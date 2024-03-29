package org.cyk.utility.__kernel__.variable;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.cyk.utility.__kernel__.variable.VariableName;
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
		assertThat(VariableName.build("v1","v2")).isEqualTo("cyk.variable.v1-v2");
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
		assertThat(VariableName.SYSTEM_UNIFORM_RESOURCE_IDENTIFIER_HOST).isEqualTo("cyk.variable.system.host");
	}
	
	@Test
	public void systemPort(){
		assertThat(VariableName.SYSTEM_UNIFORM_RESOURCE_IDENTIFIER_PORT).isEqualTo("cyk.variable.system.port");
	}
	 
	@Test
	public void systemWebContext(){
		assertThat(VariableName.SYSTEM_WEB_CONTEXT).isEqualTo("cyk.variable.system.web.context");
	}
	
	@Test
	public void protocolSimpleMailTransferEnable(){
		assertThat(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_ENABLE).isEqualTo("cyk.variable.protocol.simple.mail.transfer.enabled");
	}
	
	@Test
	public void buildClassUniformResourceIdentifier(){
		assertThat(VariableName.buildClassUniformResourceIdentifier(Integer.class, null)).isEqualTo("cyk.variable.java.lang.Integer-uri");
	}
	
	@Test
	public void buildClassUniformResourceIdentifier_classifierIs1(){
		assertThat(VariableName.buildClassUniformResourceIdentifier(Integer.class, 1)).isEqualTo("cyk.variable.java.lang.Integer-1-uri");
	}
	
	@Test
	public void buildFieldName(){
		assertThat(VariableName.buildFieldName(Integer.class, "value")).isEqualTo("cyk.variable.java.lang.Integer-value");
	}
	
	@Test
	public void buildFieldName_classifierIs1(){
		assertThat(VariableName.buildFieldName(Integer.class,(Object)1, "value")).isEqualTo("cyk.variable.java.lang.Integer-1-value");
	}
	
	@Test
	public void buildFieldName_nested(){
		assertThat(VariableName.buildFieldName(Nested.class, "f1")).isEqualTo("cyk.variable.org.cyk.utility.__kernel__.variable.VariableNameUnitTest$Nested-f1");
	}
	
	/**/
	
	public static class Nested {}
}
