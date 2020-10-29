package org.cyk.utility.__kernel__.persistence.query;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.annotation.Oracle;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class NativeQueryStringBuilderOracleUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		DependencyInjection.setQualifierClassTo(Oracle.class, NativeQueryStringBuilder.class);
	}
	
	@Test
	public void insertOne(){
		assertThat(__inject__(NativeQueryStringBuilder.class).buildInsertOne(MyClass01.class, new MyClass01().setId("i01").setCode("abc")))
			.isEqualToIgnoringCase("INSERT INTO MyClass01 (id,code) VALUES ('i01','abc');");
	}
	
	@Test
	public void insertMany(){
		assertThat(__inject__(NativeQueryStringBuilder.class).buildInsertMany(MyClass01.class, List.of(new MyClass01().setId("i01").setCode("abc")
				,new MyClass01().setId("i02").setCode("Z"))))
			.isEqualToIgnoringCase("INSERT ALL INTO MyClass01 (id,code) VALUES ('i01','abc') INTO MyClass01 (id,code) VALUES ('i02','Z') SELECT * FROM dual;");
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class MyClass01 {
		private String id;
		private String code;
	}
}
