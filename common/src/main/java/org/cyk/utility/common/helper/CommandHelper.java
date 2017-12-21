package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.cyk.utility.common.Action;
import org.cyk.utility.common.cdi.AbstractBean;

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
	
	public Command getCommand(Object identifier,String nameIdentifier,IconHelper.Icon icon,Action.ActionListener actionListener,Boolean isImplemented){
		Command command = getCommand().setName(StringHelper.getInstance().get(nameIdentifier, (Object[])null))
				.setIcon(icon).setIdentifier(identifier);
		command.addActionListener(actionListener).setIsImplemented(isImplemented);
		return command;
	}
	
	public Commands getCommands(){
		return ClassHelper.getInstance().instanciateOne(InstanceHelper.getInstance()
				.getIfNotNullElseDefault(Commands.DEFAULT_CLASS , Commands.class)); 
	}
	
	public static interface Command extends Action<Object,Object> {
		
		Collection<Listener> getListeners();
		Command setListener(Collection<Listener> listeners);
		Command addListener(Collection<Listener> listeners);
		Command addListener(Listener...listeners);
		
		@Override Command setName(String name);
		Boolean getNameRendered();
		Command setNameRendered(Boolean nameRendered);
		
		String getTitle();
		Command setTitle(String title);
		
		Command setIcon(IconHelper.Icon icon);
		IconHelper.Icon getIcon();
		
		Command setMappedIcon(Object mappedIcon);
		Object getMappedIcon();
		
		@Override Command setIdentifier(Object identifier);
		
		@Getter @Setter
		public static class Adapter extends Action.Adapter.Default<Object,Object> implements Command,Serializable {
			private static final long serialVersionUID = 1L;
			
			//TODO those attributes should be deleted
			protected IconHelper.Icon icon;
			protected Object mappedIcon;
			protected Boolean nameRendered;
			protected String title;
			protected Collection<Listener> listeners;
			
			public Adapter() {
				super("command", Object.class, null, Object.class);
			}
			
			@Override
			public Command setIdentifier(Object identifier) {
				return (Command) super.setIdentifier(identifier);
			}
			
			@Override
			public Command setNameRendered(Boolean nameRendered) {
				return null;
			}
			
			@Override
			public Command setTitle(String title) {
				return null;
			}
			
			@Override
			public Command setMappedIcon(Object mappedIcon) {
				return null;
			}
			
			@Override
			public Command setIcon(IconHelper.Icon icon) {
				return null;
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
			
			@Override
			public Command setName(String name) {
				return (Command) super.setName(name);
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
					setIsNotifiable(Boolean.TRUE);
					setIsNotifiableOnStatusFailure(Boolean.TRUE);
				}
				
				@Override
				public Command setName(String name) {
					super.setName(name);
					setTitle(getName());
					return this;
				}
				
				@Override
				public Command setNameRendered(Boolean nameRendered) {
					this.nameRendered = nameRendered;
					return this;
				}
				
				@Override
				public Command setTitle(String title) {
					this.title = title;
					return this;
				}
				
				@Override
				public Command setMappedIcon(Object mappedIcon) {
					this.mappedIcon = mappedIcon;
					return this;
				}
				
				@Override
				public Command setIcon(IconHelper.Icon icon) {
					this.icon = icon;
					setMappedIcon(IconHelper.getInstance().map(getIcon()));
					return this;
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
				
				/**/
				
				public static class NoExecution extends Default implements Serializable {
					private static final long serialVersionUID = 1L;
					
					@Override
					protected Object __execute__() {
						return null;
					}
					
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
	
	/**/
	
	public static class Commands extends AbstractBean implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public static Class<? extends Commands> DEFAULT_CLASS = Commands.class;
		
		private MapHelper.Map<String,Command> map = new MapHelper.Map<String, Command>(String.class, Command.class);
		
		public Command create(Object identifier,String nameIdentifier,IconHelper.Icon icon,Action.ActionListener actionListener,Boolean isImplemented){
			Command command = getInstance().getCommand(identifier, nameIdentifier, icon, actionListener, isImplemented);
			if(command.getIdentifier()!=null)
				map.set(command.getIdentifier().toString(), command);
			return command;
		}
		
		public Command create(Object identifier,String nameIdentifier,IconHelper.Icon icon,Action.ActionListener actionListener){
			return create(identifier, nameIdentifier, icon, actionListener, Boolean.TRUE);
		}
		
		public Command create(String nameIdentifier,IconHelper.Icon icon,Action.ActionListener actionListener){
			return create(RandomHelper.getInstance().getAlphabetic(10), nameIdentifier, icon, actionListener, Boolean.TRUE);
		}
		
		public Commands add(Command command){
			if(command.getIdentifier()==null)
				command.setIdentifier(RandomHelper.getInstance().getAlphabetic(10));
			map.set(command.getIdentifier().toString(), command);
			return this;
		}
		
		public Commands remove(String identifier){
			map.set(identifier, null);
			return this;
		}
		
		public List<Command> getList(){
			return CollectionHelper.getInstance().createList(map.getMap().values());
		}
	}
}
