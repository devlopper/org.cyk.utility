package org.cyk.utility.mapping;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class MapperSourceDestinationClassNameGetterUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void get_name() {
		assertThat(__inject__(MapperSourceDestinationClassNameGetter.class).setKlass(Class.class).execute().getOutput()).isEqualTo(ClassMapper.class.getName());
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Class {
		
	}
 
	public static interface ClassMapper {
		
	}
}
