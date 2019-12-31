package org.cyk.utility.__kernel__.string;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.Map;

import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class StringGeneratorUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void generateFromString(){
		assertThat(StringGenerator.getInstance().generate("Hello ${user}", Map.of("user","komenan"))).isEqualTo("Hello komenan");
	}

	@Test
	public void generateFromFile(){
		assertThat(StringGenerator.getInstance().generate(new File(System.getProperty("user.dir")+"/src/test/resources/org/cyk/utility/__kernel__/string/template.ftl")
				, Map.of("user","komenan"))).isEqualTo("Hello! Welcome komenan");
	}
	
	@Test
	public void generateServerEntity(){
		String string = StringGenerator.getInstance().generate(new File(System.getProperty("user.dir")+"/src/main/resources/org/cyk/utility/__kernel__/file/template/server/persistence/entity.ftl")
				, Map.of("systemIdentifier","contact","entity","Person","tableName","person"));
		System.out.println(string);
	}
}
