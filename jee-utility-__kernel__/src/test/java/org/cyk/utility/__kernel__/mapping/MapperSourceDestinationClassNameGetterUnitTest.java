package org.cyk.utility.__kernel__.mapping;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class MapperSourceDestinationClassNameGetterUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void get_name_byClass() {
		assertThat(MapperClassNameGetter.getInstance().get(Class.class)).isEqualTo(ClassMapper.class.getName());
	}
	
	@Test
	public void get_name_byClassName() {
		assertThat(MapperClassNameGetter.getInstance().get(Class.class.getName())).isEqualTo(ClassMapper.class.getName());
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Class {
		
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class ClassImpl {
		
	}
 
	public static interface ClassMapper {
		
	}
	
}
