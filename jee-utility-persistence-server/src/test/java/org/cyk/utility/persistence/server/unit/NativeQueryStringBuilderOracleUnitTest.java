package org.cyk.utility.persistence.server.unit;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.cyk.utility.__kernel__.annotation.Oracle;
import org.cyk.utility.persistence.query.NativeQueryStringBuilder;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class NativeQueryStringBuilderOracleUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Inject @Oracle
	private NativeQueryStringBuilder nativeQueryStringBuilder;
	
	@Test
	public void insertOne(){
		assertThat(nativeQueryStringBuilder.buildInsertOne(MyClass01.class, new MyClass01().setId("i01").setCode("abc")))
			.isEqualToIgnoringCase("INSERT INTO MyClass01 (id,code,yes) VALUES ('i01','abc',NULL)");
	}
	
	@Test
	public void insertMany(){
		assertThat(nativeQueryStringBuilder.buildInsertMany(MyClass01.class, List.of(new MyClass01().setId("i01").setCode("abc").setYes(true)
				,new MyClass01().setId("i02").setCode("Z").setYes(false))))
			.isEqualToIgnoringCase("INSERT /*+ PARALLEL(MyClass01,4) */ ALL INTO MyClass01 (id,code,yes) VALUES ('i01','abc',1) INTO MyClass01 (id,code,yes) VALUES ('i02','Z',0) SELECT * FROM dual");
	}
	
	@Test
	public void insertManyFromMaps(){
		Map<String,String> map1 = new LinkedHashMap<>();
		map1.put("id","'i01'");
		map1.put("code","'abc'");
		Map<String,String> map2 = new LinkedHashMap<>();
		map2.put("id","'i02'");
		map2.put("code","'Z'");
		assertThat(nativeQueryStringBuilder.buildInsertManyFromMaps(MyClass01.class, List.of(map1,map2)))
			.isEqualToIgnoringCase("INSERT /*+ PARALLEL(MyClass01,4) */ ALL INTO MyClass01 (id,code) VALUES ('i01','abc') INTO MyClass01 (id,code) VALUES ('i02','Z') SELECT * FROM dual");
	}
	
	@Test
	public void updateManyFromMaps(){
		String sql = nativeQueryStringBuilder.buildUpdateManyFromMaps(MyClass01.class, List.of(Map.of("id","i01","code","abc"),Map.of("id","i02","code","Z")));
		assertThat(sql).isEqualToIgnoringCase("UPDATE MyClass01 SET code = CASE WHEN id = i02 THEN Z WHEN id = i01 THEN abc END WHERE id IN (i01,i02)");
	}
	
	@Test
	public void deleteManyFromMaps(){
		String sql = nativeQueryStringBuilder.buildDeleteManyByIdentifiers(MyClass01.class, List.of("i01","i02"));
		assertThat(sql).isEqualToIgnoringCase("DELETE FROM MyClass01 WHERE id IN ('i01','i02')");
	}
	
	/**/
	
	@Table(name = "MyClass01") @Getter @Setter @Accessors(chain=true)
	public static class MyClass01 {
		@Column(name = "id") @Id
		private String id;
		@Column(name = "code")
		private String code;
		@Column(name = "yes")
		private Boolean yes;
	}
}