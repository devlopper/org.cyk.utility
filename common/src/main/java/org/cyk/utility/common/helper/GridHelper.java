package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import javax.inject.Singleton;

import org.cyk.utility.common.Action;
import org.cyk.utility.common.cdi.AbstractBean;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Singleton
public class GridHelper extends AbstractHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	private static GridHelper INSTANCE;
	
	public static GridHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new GridHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public Grid.Column getColumn(){
		return ClassHelper.getInstance().instanciateOne(InstanceHelper.getInstance().getIfNotNullElseDefault(Grid.Column.DEFAULT_CLASS, Grid.Column.class));
	}
	
	@Getter @Setter
	public static class Grid<T> extends AbstractBean implements Serializable {
		private static final long serialVersionUID = -3543754685060813767L;

		protected String identifier = "grid_identifier_"+RandomHelper.getInstance().getNumeric(10);
		
		protected CollectionHelper.Instance<T> collection;
		
		protected CommandHelper.Command addCommand,deleteCommand;
		protected Column indexColumn,nameColumn,commandsColumn;
		protected Boolean isAddCommandShowableAtIndexColumnFooter;
		protected Collection<Listener<T>> listeners;
		
		/**/
		
		public Grid(Class<T> elementClass) {
			collection = CollectionHelper.getInstance().getCollectionInstance(elementClass);
			addCommand = CommandHelper.getInstance().getCommand().setName(StringHelper.getInstance().get("grid.command.add", (Object[])null))
					.setIcon(IconHelper.Icon.ACTION_ADD);
			addCommand.addActionListener(new Action.ActionListener.Adapter(){
				private static final long serialVersionUID = 1L;
				@Override
				public void __execute__(Action<?, ?> action) {
					add();
				}
			}).setIsImplemented(Boolean.TRUE);
			
			deleteCommand = CommandHelper.getInstance().getCommand().setName(StringHelper.getInstance().get("grid.command.delete", (Object[])null))
					.setIcon(IconHelper.Icon.ACTION_DELETE);
			deleteCommand.addActionListener(new Action.ActionListener.Adapter(){
				private static final long serialVersionUID = 1L;
				@Override
				public void __execute__(Action<?, ?> action) {
					delete();
				}
			}).setIsImplemented(Boolean.TRUE);
			
			indexColumn = getInstance().getColumn().setName(StringHelper.getInstance().get("grid.column.index", (Object[])null));
			nameColumn = getInstance().getColumn().setName(StringHelper.getInstance().get("grid.column.name", (Object[])null));
			commandsColumn = getInstance().getColumn().setName(StringHelper.getInstance().get("grid.column.commands", (Object[])null));
		}
		
		protected void add(){
			collection.addOne();
		}
		
		protected void delete(){
			collection.removeOne(deleteCommand.getInput());
		}
		
		public Grid<T> addListener(Listener<T> listener){
			if(this.listeners == null)
				this.listeners = new ArrayList<>();
			this.listeners.add(listener);
			return this;
		}
		
		/**/
		
		/*public static class Element<T> extends org.cyk.utility.common.helper.CollectionHelper.Element<T> implements Serializable {
			private static final long serialVersionUID = 1L;
			
		}*/
		
		/**/
		
		public static interface Listener<T> {
			
			public static class Adapter<T> extends AbstractBean implements Listener<T>,Serializable {
				private static final long serialVersionUID = 1L;

			}
			
		}
		
		/**/
		
		@Getter @Setter @Accessors(chain=true)
		public static class Dimension extends AbstractBean implements Serializable{
			private static final long serialVersionUID = 1L;
			
			protected Properties properties;
			protected String name;
			protected Boolean isShowable=Boolean.TRUE;
			
			public Dimension setProperty(Object name,Object value){
				if(this.properties == null)
					this.properties = new Properties();
				this.properties.put(name, value);
				return this;
			}
			
			public Object getProperty(Object name){
				if(this.properties == null)
					return null;
				return this.properties.get(name);
			}
			
			public static final String PROPERTY_NAME_WIDTH = "width";
		}
		
		@Getter @Setter @Accessors(chain=true)
		public static class Column extends Dimension implements Serializable{
			private static final long serialVersionUID = 1L;
			
			public static Class<Column> DEFAULT_CLASS = Column.class;
			
			private List<CommandHelper.Command> footerCommands;
			
			public Column addFooterCommand(CommandHelper.Command command){
				if(this.footerCommands == null)
					this.footerCommands = new ArrayList<CommandHelper.Command>();
				this.footerCommands.add(command);
				return this;
			}
			
			@Override
			public Column setName(String name) {
				return (Column) super.setName(name);
			}
			
			@Override
			public Column setProperty(Object name, Object value) {
				return (Column) super.setProperty(name, value);
			}
			
			public Column setWidth(Object value) {
				return setProperty(PROPERTY_NAME_WIDTH, value);
			}
			
			public Object getWidth(){
				return getProperty(PROPERTY_NAME_WIDTH);
			}
			
		}
	}
	
	
	
}
