package org.cyk.utility.common.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.Action;
import org.cyk.utility.common.LogMessage.Builder;

public interface Send<ADDRESS> extends Action<Message, Void> {

	/*
	private Long numberOfRetry = 5l;
	private Long numberOfMillisecondBeforeRetry = 1000l * 6;
	private ThreadPoolExecutor.Listener threadPoolExecutorListener;
	*/
	/*
	Boolean getBlockingEnabled();
	Send<INPUT, OUTPUT> setBlockingEnabled(Boolean blockingEnabled);
	
	Boolean getDebugEnabled();
	Send<INPUT, OUTPUT> setDebugEnabled(Boolean debugEnabled);
	*/
	
	Send<ADDRESS> setHostAndUserProperties(String host, Integer port,Boolean secured, String username, String password);
	Send<ADDRESS> setHostAndUserProperties(String host, Integer port, String username, String password);
	
	void ping();
	
	/**/
	
	@Getter @Setter
	public static class Adapter<ADDRESS> extends Action.Adapter.Default<Message, Void> implements Send<ADDRESS>,Serializable {
		private static final long serialVersionUID = 1L;

		public Adapter(Message message,Builder logMessageBuilder) {
			super("Send", Message.class, message, Void.class, logMessageBuilder);
		}		
		
		protected ADDRESS getAddress(String identifier) throws Exception{
			throw new RuntimeException("get address from identifier "+identifier+" Not yet implemented");
		}
		
		@Override
		public Send<ADDRESS> setHostAndUserProperties(String host, Integer port,Boolean secured, String username, String password) {
			return this;
		}
		
		@Override
		public Send<ADDRESS> setHostAndUserProperties(String host, Integer port,String username, String password) {
			return setHostAndUserProperties(host, port,Boolean.TRUE, username, password);
		}
		
		@Override
		public void ping() {
			Message message = new Message();
			message.setSubject("Hi!");
			message.setContent("This is a test");
			setInput(message);
			execute();
		}
		
		protected Collection<ADDRESS> getAddresses(){
			Collection<ADDRESS> addresses = new ArrayList<>();
			for(String identifier : getInput().getReceiverIdentifiers())
				try {
					addresses.add(getAddress(identifier));
				} catch (Exception e) {
					e.printStackTrace();
				}
			return addresses;
		}
		
		/**/
		@Getter @Setter
		public static class Default<ADDRESS> extends Send.Adapter<ADDRESS> implements Serializable {
			private static final long serialVersionUID = 1L;

			public Default(Message message,Builder logMessageBuilder) {
				super(message, logMessageBuilder);
			}
			
		}
		
	}
	
	/**/
	
	
	
}
