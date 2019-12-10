package org.cyk.utility.__kernel__.mapping;

import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;
import org.mapstruct.Mapper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class MapperGetterUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void get_source_destination() {
		//MapperSourceDestination<Source, Destination> mapper = 
		Object mapper =  MapperGetter.getInstance().getBySourceClassByDestinationClass(Source.class,Destination.class);
		System.out.println("MapperGetterUnitTest.get_source_destination() MAPPER : "+mapper);
		//System.out.println("MapperGetterUnitTest.get_source_destination() ::: "+mapper);
		//Destination destination = SourceDestinationMapper.MAPPER.mapFromType1(new Source().setString01("s01").setInteger01(12));
		//assertThat(destination.getString01()).isEqualTo("s01");
		//assertThat(destination.getInteger01()).isEqualTo("12");
	}
	
	/**/
	 
	@Getter @Setter @Accessors(chain=true)
	public static class Source {

		private String string01;
		private Integer integer01;
		
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class Destination {

		private String string01;
		private String integer01;
		
	}
	
	@Mapper
	public static abstract class SourceMapper extends AbstractMapperSourceDestinationImpl<Source, Destination> {
		private static final long serialVersionUID = 1L;

	}
	
}
