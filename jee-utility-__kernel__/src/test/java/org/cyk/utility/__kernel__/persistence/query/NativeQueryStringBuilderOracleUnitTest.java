package org.cyk.utility.__kernel__.persistence.query;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

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
			.isEqualToIgnoringCase("INSERT INTO MyClass01 (id,code) VALUES ('i01','abc')");
	}
	
	@Test
	public void insertMany(){
		assertThat(__inject__(NativeQueryStringBuilder.class).buildInsertMany(MyClass01.class, List.of(new MyClass01().setId("i01").setCode("abc")
				,new MyClass01().setId("i02").setCode("Z"))))
			.isEqualToIgnoringCase("INSERT /*+ PARALLEL(MyClass01,4) */ ALL INTO MyClass01 (id,code) VALUES ('i01','abc') INTO MyClass01 (id,code) VALUES ('i02','Z') SELECT * FROM dual");
	}
	
	@Test
	public void insertManyFromMaps(){
		assertThat(__inject__(NativeQueryStringBuilder.class).buildInsertManyFromMaps(MyClass01.class, List.of(Map.of("id","'i01'","code","'abc'"),Map.of("id","'i02'","code","'Z'"))))
			.isEqualToIgnoringCase("INSERT /*+ PARALLEL(MyClass01,4) */ ALL INTO MyClass01 (id,code) VALUES ('i01','abc') INTO MyClass01 (id,code) VALUES ('i02','Z') SELECT * FROM dual");
	}
	
	@Test
	public void updateManyFromMaps(){
		String sql = __inject__(NativeQueryStringBuilder.class).buildUpdateManyFromMaps(MyClass01.class, List.of(Map.of("id","i01","code","abc"),Map.of("id","i02","code","Z")));
		System.out.println(sql);
		assertThat(sql)
			.isEqualToIgnoringCase(" ALL INTO MyClass01 (id,code) VALUES ('i01','abc') INTO MyClass01 (id,code) VALUES ('i02','Z') SELECT * FROM dual");
	}
	
	@Test
	public void deleteManyFromMaps(){
		String sql = __inject__(NativeQueryStringBuilder.class).buildDeleteManyByIdentifiers(MyClass01.class, List.of("i01","i02"));
		System.out.println(sql);
		assertThat(sql)
			.isEqualToIgnoringCase(" ALL INTO MyClass01 (id,code) VALUES ('i01','abc') INTO MyClass01 (id,code) VALUES ('i02','Z') SELECT * FROM dual");
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
