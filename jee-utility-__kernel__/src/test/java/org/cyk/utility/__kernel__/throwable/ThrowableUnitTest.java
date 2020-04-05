package org.cyk.utility.__kernel__.throwable;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.__kernel__.mapping.MappingHelper;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class ThrowableUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void mapMessageToDto(){
		Message message = new Message().setIdentifier("id01").setSummary("sum01").setDetails("d01");
		Message.Dto dto = MappingHelper.getSource(message, Message.Dto.class);
		assertThat(dto.getIdentifier()).isEqualTo("id01");
		assertThat(dto.getSummary()).isEqualTo("sum01");
		assertThat(dto.getDetails()).isEqualTo("d01");
	}
	
	@Test
	public void mapDtoToMessage(){
		Message.Dto dto = new Message.Dto().setIdentifier("id01").setSummary("sum01").setDetails("d01");
		Message message = MappingHelper.getDestination(dto, Message.class);
		assertThat(message.getIdentifier()).isEqualTo("id01");
		assertThat(message.getSummary()).isEqualTo("sum01");
		assertThat(message.getDetails()).isEqualTo("d01");
	}
	
	@Test
	public void mapRuntimeExceptionToDto(){
		RuntimeException runtimeException = new RuntimeException("something happens");
		RuntimeException.Dto dto = MappingHelper.getSource(runtimeException, RuntimeException.Dto.class);
		assertThat(dto).isNotNull();
	}
	
	@Test
	public void mapDtoToRuntimeException(){
		RuntimeException.Dto dto = new RuntimeException.Dto();
		RuntimeException runtimeException = MappingHelper.getDestination(dto, RuntimeException.class);
		assertThat(runtimeException).isNotNull();
	}
}
