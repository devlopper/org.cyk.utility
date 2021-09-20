package org.cyk.utility.mapping.mapstruct;

import static org.assertj.core.api.Assertions.assertThat;
import javax.inject.Inject;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class UnitTest extends AbstractWeldUnitTest {

	@Inject
	private SourceToDestinationMapper sourceToDestinationMapper;
	
	@Test
	public void mapSourceToDestination() {
		Source source = new Source();
		source.setIdentifier("1").setCode("c");
		source.setName("n");
		Destination destination = sourceToDestinationMapper.mapSourceToDestination(source);
		assertThat(destination.getIdentifier()).isEqualTo("1");
		assertThat(destination.getCode()).isEqualTo("c");
		assertThat(destination.getName()).isEqualTo("n");
	}
	
}