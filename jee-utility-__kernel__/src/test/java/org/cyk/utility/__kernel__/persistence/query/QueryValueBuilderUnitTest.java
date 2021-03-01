package org.cyk.utility.__kernel__.persistence.query;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.__kernel__.computation.LogicalOperator;
import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringImpl;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
public class QueryValueBuilderUnitTest extends AbstractWeldUnitTest {

	@Test
	public void buildSelect(){
		assertThat(QueryValueBuilder.getInstance().buildSelect(Entity.class)).isEqualTo("SELECT entity FROM Entity entity");
	}
	
	@Test
	public void buildSelectBySystemIdentifiers(){
		assertThat(QueryValueBuilder.getInstance().buildSelectBySystemIdentifiers(Entity.class)).isEqualTo("SELECT entity FROM Entity entity WHERE entity.identifier IN :identifiers");
	}
	
	@Test
	public void buildSelectByBusinessIdentifiers(){
		assertThat(QueryValueBuilder.getInstance().buildSelectByBusinessIdentifiers(Entity.class)).isEqualTo("SELECT entity FROM Entity entity WHERE entity.code IN :identifiers");
	}
	
	@Test
	public void buildSelectByRegistrationNumber(){
		assertThat(QueryValueBuilder.getInstance().buildSelectWhereFieldIn(Entity.class,"registrationNumber")).isEqualTo("SELECT entity FROM Entity entity WHERE entity.registrationNumber IN :registrationNumbers");
	}
	
	@Test
	public void buildCountFromSelect(){
		assertThat(QueryValueBuilder.buildCountFromSelect("SELECT t FROM T t ORDER BY")).isEqualTo("SELECT COUNT(t) FROM T t");
	}
	
	@Test
	public void buildCountFromSelect02(){
		assertThat(QueryValueBuilder.buildCountFromSelect("SELECT t.identifier,t.code FROM T t ORDER BY")).isEqualTo("SELECT COUNT(t.identifier) FROM T t");
	}
	
	@Test
	public void deriveLeftJoinsFromFieldsNames(){
		assertThat(QueryValueBuilder.deriveLeftJoinsFromFieldsNames("t","a1","a2","a2.b1")).isEqualTo("LEFT JOIN t.a1 a1 LEFT JOIN t.a2 a2 LEFT JOIN t.a2.b1 b1");
	}
	
	@Test
	public void deriveConcatsCodeAndNameFromTuplesNames(){
		assertThat(QueryValueBuilder.deriveConcatsCodeAndNameFromTuplesNames("t","s","f")).isEqualTo("CONCAT(t.code,' ',t.name),CONCAT(s.code,' ',s.name),CONCAT(f.code,' ',f.name)");
	}
	
	@Test
	public void formatTupleFieldLike(){
		assertThat(QueryValueBuilder.deriveLike("t", "name", "query", null, null)).isEqualTo("LOWER(t.name) LIKE LOWER(:query)");
	}
	
	@Test
	public void formatTupleFieldLike_many_and(){
		assertThat(QueryValueBuilder.deriveLike("t", "name", "query", 2, LogicalOperator.AND)).isEqualTo("LOWER(t.name) LIKE LOWER(:query1) AND LOWER(t.name) LIKE LOWER(:query2)");
	}
	
	@Test
	public void formatTupleFieldLike_many_or(){
		assertThat(QueryValueBuilder.deriveLike("t", "name", "query", 2, LogicalOperator.OR)).isEqualTo("LOWER(t.name) LIKE LOWER(:query1) OR LOWER(t.name) LIKE LOWER(:query2)");
	}
	
	@Test
	public void deriveCaseZeroIfNull_one(){
		assertThat(QueryValueBuilder.deriveCaseZeroIfNull("t", "count1")).isEqualTo("CASE WHEN t.count1 IS NULL THEN 0l ELSE t.count1 END");
	}
	
	@Test
	public void deriveCaseZeroIfNull_many(){
		assertThat(QueryValueBuilder.deriveCaseZeroIfNull("t", "count1","count2")).isEqualTo("CASE WHEN t.count1 IS NULL THEN 0l ELSE t.count1 END,CASE WHEN t.count2 IS NULL THEN 0l ELSE t.count2 END");
	}
	
	@Test
	public void deriveCaseZeroIfNull_many_plus(){
		assertThat(QueryValueBuilder.deriveCaseZeroIfNullWithSeparator("t","+", "count1","count2")).isEqualTo("CASE WHEN t.count1 IS NULL THEN 0l ELSE t.count1 END+CASE WHEN t.count2 IS NULL THEN 0l ELSE t.count2 END");
	}
	
	@Test
	public void deriveSum_one(){
		assertThat(QueryValueBuilder.deriveSum("t", "count1")).isEqualTo("SUM(CASE WHEN t.count1 IS NULL THEN 0l ELSE t.count1 END)");
	}
	
	@Test
	public void deriveSum_many(){
		assertThat(QueryValueBuilder.deriveSum("t", "count1","count2")).isEqualTo("SUM(CASE WHEN t.count1 IS NULL THEN 0l ELSE t.count1 END),SUM(CASE WHEN t.count2 IS NULL THEN 0l ELSE t.count2 END)");
	}
	
	@Test
	public void deriveSumAsOne_one(){
		assertThat(QueryValueBuilder.deriveSumAsOne("t", "count1")).isEqualTo("SUM(CASE WHEN t.count1 IS NULL THEN 0l ELSE t.count1 END)");
	}
	
	@Test
	public void deriveSumAsOne_many(){
		assertThat(QueryValueBuilder.deriveSumAsOne("t", "count1","count2")).isEqualTo("SUM(CASE WHEN t.count1 IS NULL THEN 0l ELSE t.count1 END+CASE WHEN t.count2 IS NULL THEN 0l ELSE t.count2 END)");
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Entity extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringImpl {
		private String registrationNumber;
	}
}
