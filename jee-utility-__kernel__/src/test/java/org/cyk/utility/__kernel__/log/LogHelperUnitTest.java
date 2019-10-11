package org.cyk.utility.__kernel__.log;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class LogHelperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void concatenateMessageTemplateArgument_null_null(){
		assertThat(LogHelper.concatenateMessageTemplateWithArgument((String)null, null)).isNull();
	}
	
	@Test
	public void concatenateMessageTemplateArgument_empty_null(){
		assertThat(LogHelper.concatenateMessageTemplateWithArgument("", null)).isEqualTo("");
	}
	
	@Test
	public void concatenateMessageTemplateArgument_blank_null(){
		assertThat(LogHelper.concatenateMessageTemplateWithArgument(" ", null)).isEqualTo(" ");
	}
	
	@Test
	public void concatenateMessageTemplateArgument_null_p1(){
		assertThat(LogHelper.concatenateMessageTemplateWithArgument((String)null, "p1")).isEqualTo("p1=%s");
	}
	
	@Test
	public void concatenateMessageTemplateArgument_notBlank_p1(){
		assertThat(LogHelper.concatenateMessageTemplateWithArgument("function", "p1")).isEqualTo("function p1=%s");
	}
	
	@Test
	public void addArgumentToMessage_notBlank_p1(){
		LogMessage message = new LogMessage();
		message.addArgument("p1", "v1");
		assertThat(message.getTemplate()).isEqualTo("p1=%s");
	}
	
	@Test
	public void buildMessage_null_null(){
		assertThat(LogHelper.buildMessage(null, (Collection<Object>)null)).isNull();
	}
	
	@Test
	public void buildMessage_empty_null(){
		assertThat(LogHelper.buildMessage("", (Collection<Object>)null)).isNull();
	}
	
	@Test
	public void buildMessage_empty_notNull(){
		assertThat(LogHelper.buildMessage("", "1")).isNull();
	}
	
	@Test
	public void buildMessage_blank_null(){
		assertThat(LogHelper.buildMessage(" ", (Collection<Object>)null)).isNull();
	}
	
	@Test
	public void buildMessage_blank_notNull(){
		assertThat(LogHelper.buildMessage(" ", "1")).isNull();
	}
	
	@Test
	public void buildMessage_notBlankNoParameter_null(){
		assertThat(LogHelper.buildMessage("this my message p1=v1", (Collection<Object>)null)).isEqualTo("this my message p1=v1");
	}
	
	@Test
	public void buildMessage_notBlankWithParameter_null(){
		assertThat(LogHelper.buildMessage("this my message p1=%s", (Collection<Object>)null)).isEqualTo("this my message p1=%s");
	}
	
	@Test
	public void buildMessage_notBlankWithParameter_notNull(){
		assertThat(LogHelper.buildMessage("this my message p1=%s", "v1")).isEqualTo("this my message p1=v1");
	}
}
