package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.cyk.utility.common.Action;

import lombok.Getter;
import lombok.Setter;

public class CommandHelper extends AbstractHelper implements Serializable {
	private static final long serialVersionUID = 1L;

	private static CommandHelper INSTANCE;
	
	public static CommandHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new CommandHelper();
		return INSTANCE;
	}
	
	public Command getCommand(){
		return ClassHelper.getInstance().instanciateOne(InstanceHelper.getInstance()
				.getIfNotNullElseDefault(Command.Adapter.Default.DEFAULT_CLASS , Command.Adapter.Default.class)); 
	}
	
	public static interface Command extends Action<Object,Object> {
		
		Collection<Listener> getListeners();
		Command setListener(Collection<Listener> listeners);
		Command addListener(Collection<Listener> listeners);
		Command addListener(Listener...listeners);
		
		@Getter @Setter
		public static class Adapter extends Action.Adapter.Default<Object,Object> implements Command,Serializable {
			private static final long serialVersionUID = 1L;
			
			protected Collection<Listener> listeners;
			
			public Adapter() {
				super("command", Object.class, null, Object.class);
			}
			
			@Override
			public Command setListener(Collection<Listener> listeners) {
				return null;
			}
			
			@Override
			public Command addListener(Collection<Listener> listeners) {
				return null;
			}
			
			@Override
			public Command addListener(Listener...listeners) {
				return null;
			}
			
			/**/
			
			public static class Default extends Command.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				@SuppressWarnings("unchecked")
				public static Class<Command> DEFAULT_CLASS = (Class<Command>) ClassHelper.getInstance().getByName(Default.class);
				
				public Default() {
					setIsInputRequired(Boolean.FALSE);
					setIsInputValidatable(Boolean.TRUE);
					setIsProcessableOnStatus(Boolean.TRUE);
					setIsNotifiable(Boolean.FALSE);
				}
				
				@Override
				public Command addListener(Collection<Listener> listeners) {
					if(CollectionHelper.getInstance().isNotEmpty(listeners)){
						if(this.listeners==null)
							this.listeners = new ArrayList<>();
						this.listeners.addAll(listeners);	
					}
					return this;
				}
				
				@Override
				public Command addListener(Listener...listeners) {
					if(ArrayHelper.getInstance().isNotEmpty(listeners))
						addListener(Arrays.asList(listeners));
					return this;
				}
				
			}
		}
		
		/**/
		
		public static interface Listener {
			
			//Boolean isNotifiable(Command command,ExecutionState state);
			
			//String notifiy(Command command,Object parameter,ExecutionState state);
			
			/**/
		}
		
	}
		
}
