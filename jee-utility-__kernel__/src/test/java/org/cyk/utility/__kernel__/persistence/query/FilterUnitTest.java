package org.cyk.utility.__kernel__.persistence.query;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import org.cyk.utility.__kernel__.persistence.query.filter.Filter;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;

public class FilterUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;
	
	@Test
	public void normalize_withoutFields(){		
		Filter filter = new Filter();
		filter.normalize(Entity.class);
		assertThat(filter.getFields()).isNull();
	}
	
	@Test
	public void normalize_withField_one_declared(){		
		Filter filter = new Filter();
		filter.addField("identifier", "1");
		filter.normalize(Entity.class);
		assertThat(filter.getFields().get().size()).isEqualTo(1);
	}
	
	@Test
	public void normalize_withField_one_notDeclared(){		
		Filter filter = new Filter();
		filter.addField("identifierAndCode", "1");
		filter.normalize(Entity.class);
		assertThat(filter.getFields().get().size()).isEqualTo(1);
	}
	
	@Test
	public void generateMap_oneField_string(){		
		Filter filter = new Filter();
		filter.addField("identifier", "1");
		filter.normalize(Entity.class);
		Map<Object,Object> map = filter.generateMap();
		assertThat(map).containsEntry("identifier", "1");
	}
	
	@Test
	public void generateMap_oneField_collection(){		
		Filter filter = new Filter();
		filter.addField("identifier", List.of("1"));
		filter.normalize(Entity.class);
		Map<Object,Object> map = filter.generateMap();
		assertThat(map).containsEntry("identifier", List.of("1"));
	}
	
	/**/
	
	@Getter @Setter
	public static class Entity {
		private String identifier,code,name;
	}
	
}
