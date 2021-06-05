package org.cyk.utility.__kernel__.uri;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class UniformResourceIdentifierBuilderUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void build(){
		assertThat(UniformResourceIdentifierBuilder.getInstance().build(new UniformResourceIdentifierBuilder.Arguments()
				.setScheme("http").setHost("localhost").setPort(80).setContext("p")).toString())
			.isEqualTo("http://localhost:80/p/");
		
		assertThat(UniformResourceIdentifierBuilder.getInstance().build(new UniformResourceIdentifierBuilder.Arguments()
				.setScheme("http").setHost("localhost").setPort(80).setContext("p").addQuery("p", "v1")).toString())
			.isEqualTo("http://localhost:80/p/?p=v1");
		
		assertThat(UniformResourceIdentifierBuilder.getInstance().build(new UniformResourceIdentifierBuilder.Arguments()
				.setScheme("http").setHost("localhost").setPort(80).setContext("p").addQuery("p", "v1 v2")).toString())
			.isEqualTo("http://localhost:80/p/?p=v1+v2");
		
		assertThat(UniformResourceIdentifierBuilder.getInstance().build(new UniformResourceIdentifierBuilder.Arguments()
				.setScheme("http").setHost("localhost").setPort(80).setContext("p").addQuery("p", "v1").addQuery("p", "v2")).toString())
			.isEqualTo("http://localhost:80/p/?p=v1&p=v2");
	}
	
}
