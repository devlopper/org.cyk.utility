package org.cyk.utility.instance;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class InstanceRepositoryUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void add(){
		ClassRepository repository = new ClassRepository();
		assertThat(repository.countAll()).isEqualTo(0l);
		assertThat(repository.getBySystemIdentifier(1)).isNull();
		repository.add(new Class().setIdentifier(1));
		assertThat(repository.countAll()).isEqualTo(1l);
		assertThat(repository.getBySystemIdentifier(1)).isNotNull();
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Class {
		Object identifier;
	}
	
	public static class ClassRepository extends AbstractInstanceRepositoryImpl<Class> {
		private static final long serialVersionUID = 1L;
	
	}
}
