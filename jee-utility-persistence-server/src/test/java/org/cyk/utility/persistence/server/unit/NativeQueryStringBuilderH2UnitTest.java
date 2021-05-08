package org.cyk.utility.persistence.server.unit;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.cyk.utility.__kernel__.annotation.H2;
import org.cyk.utility.persistence.query.NativeQueryStringBuilder;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class NativeQueryStringBuilderH2UnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Inject @H2
	private NativeQueryStringBuilder nativeQueryStringBuilder;
	
	@Test
	public void insertOne(){
		assertThat(nativeQueryStringBuilder.buildInsertOne(MyClass01.class, new MyClass01().setId("i01").setCode("abc")))
			.isEqualToIgnoringCase("INSERT INTO MyClass01 (id,code) VALUES ('i01','abc')");
	}
	
	@Test
	public void insertMany(){
		assertThat(nativeQueryStringBuilder.buildInsertMany(MyClass01.class, List.of(new MyClass01().setId("i01").setCode("abc")
				,new MyClass01().setId("i02").setCode("Z"))))
			.isEqualToIgnoringCase("INSERT INTO MyClass01 (id,code) VALUES ('i01','abc'),('i02','Z')");
	}
	
	/**/
	
	@Table(name = "MyClass01") @Getter @Setter @Accessors(chain=true)
	public static class MyClass01 {
		@Column(name = "id") @Id
		private String id;
		@Column(name = "code")
		private String code;
	}
}