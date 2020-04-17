package org.cyk.utility.__kernel__.persistence.query;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringImpl;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
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
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Entity extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringImpl {
		private String registrationNumber;
	}
}
