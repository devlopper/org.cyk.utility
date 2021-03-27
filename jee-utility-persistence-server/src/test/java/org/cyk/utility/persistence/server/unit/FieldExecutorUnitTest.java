package org.cyk.utility.persistence.server.unit;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.persistence.server.DataType;
import org.cyk.utility.persistence.server.query.executor.field.CodeExecutor;
import org.cyk.utility.persistence.server.query.executor.field.GenericFieldExecutor;
import org.junit.jupiter.api.Test;

public class FieldExecutorUnitTest extends AbstractUnitTest {
	private static final long serialVersionUID = 1L;
	
	@Test
	public void generic_exists(){
		assertThat(GenericFieldExecutor.getInstance().exists(DataType.class,String.class,DataType.FIELD_CODE, "SG")).isTrue();
		assertThat(GenericFieldExecutor.getInstance().exists(DataType.class,String.class,DataType.FIELD_CODE, "SG","LG")).isTrue();
		assertThat(GenericFieldExecutor.getInstance().exists(DataType.class,String.class,DataType.FIELD_CODE, "SG","LG","X")).isFalse();
	}
	
	@Test
	public void specific_exists(){
		assertThat(CodeExecutor.getInstance().exists(DataType.class, "SG")).isTrue();
		assertThat(CodeExecutor.getInstance().exists(DataType.class, "SG","LG")).isTrue();
		assertThat(CodeExecutor.getInstance().exists(DataType.class, "SG","LG","X")).isFalse();
	}
	
	@Test
	public void specific_unexisting(){
		assertThat(CodeExecutor.getInstance().getUnexisting(DataType.class, "SG")).isNull();
		assertThat(CodeExecutor.getInstance().getUnexisting(DataType.class, "SG","LG")).isNull();
		assertThat(CodeExecutor.getInstance().getUnexisting(DataType.class, "SG","LG","X")).contains("X");
	}
	
	@Test
	public void specific_existing(){
		assertThat(CodeExecutor.getInstance().getExisting(DataType.class, "SG")).contains("SG");
		assertThat(CodeExecutor.getInstance().getExisting(DataType.class, "SG","LG")).contains("SG","LG");
		assertThat(CodeExecutor.getInstance().getExisting(DataType.class, "SG","LG","X")).contains("SG","LG");
	}
}