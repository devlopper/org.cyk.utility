package org.cyk.utility.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.server.persistence.api.MyEntity;
import org.cyk.utility.server.service.api.MyEntityDto;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class ServiceUnitTest extends AbstractWeldUnitTest {

	@Test
	public void test() {
		assertThat(PersistenceEntityClassGetter.getInstance().get(MyEntityDto.class)).isEqualTo(MyEntity.class);
	}
}