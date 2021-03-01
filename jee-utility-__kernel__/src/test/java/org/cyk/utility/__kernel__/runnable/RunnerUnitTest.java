package org.cyk.utility.__kernel__.runnable;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.cyk.utility.__kernel__.user.interface_.message.Message;
import org.cyk.utility.__kernel__.user.interface_.message.MessageRenderer;
import org.cyk.utility.__kernel__.user.interface_.message.RenderType;
import org.cyk.utility.__kernel__.user.interface_.message.Severity;
import org.junit.jupiter.api.Test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class RunnerUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	private MessageRendererImpl messageRenderer;
	
	@Override
	protected void __listenBefore__() {
		//Runner.INSTANCE.set(null);
		//MessageRenderer.INSTANCE.set(null);
		messageRenderer = new MessageRendererImpl();
		MessageRenderer.INSTANCE.set(messageRenderer);
	}
	
	@Test
	public void run_throwableIsNull(){
		Runner.Arguments runnerArguments = new Runner.Arguments().assignDefaultMessageArguments();
		runnerArguments.addRunnables(new Runnable("MyJob 01",1000l * 1,null));
		Runner.getInstance().run(runnerArguments);		
		assertThat(messageRenderer.getMessages()).hasSize(1);
		assertThat(messageRenderer.getMessages().stream().map(x->x.getSeverity()).collect(Collectors.toList())).containsExactly(Severity.INFORMATION);
		assertThat(messageRenderer.getMessages().stream().map(x->x.getSummary()).collect(Collectors.toList())).containsExactly("Opération bien éffectuée");
	}
	
	@Test
	public void run_throwableIsNotNull_runtimeException_java(){
		Runner.Arguments runnerArguments = new Runner.Arguments().assignDefaultMessageArguments();
		runnerArguments.addRunnables(new Runnable("MyJob 01",1000l * 1,new RuntimeException("oops!")));
		Runner.getInstance().run(runnerArguments);		
		assertThat(messageRenderer.getMessages()).hasSize(1);
		assertThat(messageRenderer.getMessages().stream().map(x->x.getSeverity()).collect(Collectors.toList())).containsExactly(Severity.ERROR);
		assertThat(messageRenderer.getMessages().stream().map(x->x.getSummary()).collect(Collectors.toList())).containsExactly("oops!");
	}
	
	@Test
	public void run_throwableIsNotNull_runtimeException_cyk_noMessages(){
		Runner.Arguments runnerArguments = new Runner.Arguments().assignDefaultMessageArguments();
		runnerArguments.addRunnables(new Runnable("MyJob 01",1000l * 1,new org.cyk.utility.__kernel__.throwable.RuntimeException("oops!")));
		Runner.getInstance().run(runnerArguments);		
		assertThat(messageRenderer.getMessages()).hasSize(1);
		assertThat(messageRenderer.getMessages().stream().map(x->x.getSeverity()).collect(Collectors.toList())).containsExactly(Severity.ERROR);
		assertThat(messageRenderer.getMessages().stream().map(x->x.getSummary()).collect(Collectors.toList())).containsExactly("oops!");
	}
	
	@Test
	public void run_throwableIsNotNull_runtimeException_cyk_messages_one(){
		Runner.Arguments runnerArguments = new Runner.Arguments().assignDefaultMessageArguments();
		runnerArguments.addRunnables(new Runnable("MyJob 01",1000l * 1,new org.cyk.utility.__kernel__.throwable.RuntimeException().addMessages(new org.cyk.utility.__kernel__.throwable.Message().setSummary("oops!"))));
		Runner.getInstance().run(runnerArguments);		
		assertThat(messageRenderer.getMessages()).hasSize(1);
		assertThat(messageRenderer.getMessages().stream().map(x->x.getSeverity()).collect(Collectors.toList())).containsExactly(Severity.ERROR);
		assertThat(messageRenderer.getMessages().stream().map(x->x.getSummary()).collect(Collectors.toList())).containsExactly("oops!");
	}
	
	@Test
	public void run_throwableIsNotNull_runtimeException_cyk_messages_many(){
		Runner.Arguments runnerArguments = new Runner.Arguments().assignDefaultMessageArguments();
		runnerArguments.addRunnables(new Runnable("MyJob 01",1000l * 1,new org.cyk.utility.__kernel__.throwable.RuntimeException()
				.addMessages(new org.cyk.utility.__kernel__.throwable.Message().setSummary("oops 01!")
						,new org.cyk.utility.__kernel__.throwable.Message().setSummary("oops 02!")
						,new org.cyk.utility.__kernel__.throwable.Message().setSummary("oops 03!"))));
		Runner.getInstance().run(runnerArguments);		
		assertThat(messageRenderer.getMessages()).hasSize(1);
		assertThat(messageRenderer.getMessages().stream().map(x->x.getSeverity()).collect(Collectors.toList())).containsExactly(Severity.ERROR);
		assertThat(messageRenderer.getMessages().stream().map(x->x.getSummary()).collect(Collectors.toList())).containsExactly("1 - oops 01!\r\n2 - oops 02!\r\n3 - oops 03!");
	}

	/**/
	
	@Getter @Setter @Accessors(chain=true) @AllArgsConstructor
	public static class Runnable implements java.lang.Runnable {
		
		private String name;
		private Long duration;
		private Throwable throwable;
		
		@Override
		public void run() {
			if(StringHelper.isNotBlank(name))
				System.out.println("Work "+name+" started.");
			if(throwable != null)
				throw new java.lang.RuntimeException(throwable);
			if(duration != null && duration > 0)
				try {
					Thread.sleep(duration);
					if(StringHelper.isNotBlank(name))
						System.out.println("Work "+name+" done.");
				} catch (InterruptedException e) {
					if(StringHelper.isNotBlank(name))
						System.out.println("Work "+name+" interrupted.");
					else
						e.printStackTrace();
				}
		}		
	}
	
	/**/
	
	@Getter
	public class MessageRendererImpl extends MessageRenderer.AbstractImpl implements Serializable {

		private Collection<Message> messages;
		
		@Override
		public MessageRenderer clear() {
			return this;
		}
		
		@Override
		protected void __render__(Collection<Message> messages, Collection<RenderType> renderTypes) {
			this.messages = messages;
			for(Message message : messages)
				System.out.println(message.getSummary());
		}

	}
}