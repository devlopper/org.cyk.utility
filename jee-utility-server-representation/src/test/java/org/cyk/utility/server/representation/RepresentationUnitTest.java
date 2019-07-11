package org.cyk.utility.server.representation;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.instance.InstanceHelper;
import org.cyk.utility.server.persistence.entities.MyEntity;
import org.cyk.utility.server.representation.entities.MyEntityDto;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class RepresentationUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void buildMany_1000_MyEntityDto_to_MyEntity(){
		Long t = System.currentTimeMillis();
		Collection<MyEntityDto> dtos = new ArrayList<>();
		for(Integer index = 0 ; index < 1000 ; index = index + 1) {
			dtos.add(new MyEntityDto().setIdentifier(index.toString()).setCode(index.toString()).setName(index.toString()));
		}
		Collection<MyEntity> entities = __inject__(InstanceHelper.class).buildMany(MyEntity.class, dtos);
		
		Integer count = 0;
		for(MyEntity index  : entities) {
			assertThat(index.getIdentifier()).isEqualTo(count.toString());
			assertThat(index.getCode()).isEqualTo(count.toString());
			assertThat(index.getName()).isEqualTo(count.toString());	
			count++;
		}
		System.out.println("RepresentationUnitTest.buildMany_1000_MyEntityDto_to_MyEntity() : "+(System.currentTimeMillis() - t));
	}
}
