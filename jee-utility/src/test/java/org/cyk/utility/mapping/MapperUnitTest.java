package org.cyk.utility.mapping;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class MapperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void map_mapStruct() {
		Source source = new Source().setString01("s01").setInteger01(12);
		Destination destination = SourceMapper.INSTANCE.getDestination(source);
		assertThat(destination.getString01()).isEqualTo("s01");
		assertThat(destination.getInteger01()).isEqualTo("12");
	}
	
	@Test
	public void map_mapStruct_inject() {
		Source source = new Source().setString01("s01").setInteger01(12);
		Destination destination = __inject__(SourceMapper.class).getDestination(source);
		assertThat(destination.getString01()).isEqualTo("s01");
		assertThat(destination.getInteger01()).isEqualTo("12");
	}
	
	@Test
	public void map_getter() {
		Source source = new Source().setString01("s01").setInteger01(12);
		MapperSourceDestination<Source,Destination> mapper = __inject__(MapperSourceDestinationGetter.class).setSource(source).setDestinationClass(Destination.class).execute().getOutput();
		Destination destination = mapper.getDestination(source);
		assertThat(destination.getString01()).isEqualTo("s01");
		assertThat(destination.getInteger01()).isEqualTo("12");
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
	public static interface SourceMapper extends MapperSourceDestination<Source, Destination> {
	    SourceMapper INSTANCE = Mappers.getMapper(SourceMapper.class);
	 
	}

	
}
