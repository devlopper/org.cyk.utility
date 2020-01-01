package org.cyk.utility.__kernel__.string;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.__kernel__.string.freemarker.FreeMarkerHelper;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

import freemarker.template.Template;

public class FreeMarkerUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void system_server_persistence_entity(){
		Template template = FreeMarkerHelper.getTemplate("project/system/server/persistence/entity/namable.ftl");
		assertThat(template).isNotNull();
		//assertThat(template.toString()).contains("${}");
		System.out.println(template.toString());
		System.out.println("-------------------------------------------------------------------");
		//System.out.println(StringGenerator.getInstance().generate(templateFile, arguments));
		//assertThat(template.get).isEqualTo("Hello komenan");
	}

}
