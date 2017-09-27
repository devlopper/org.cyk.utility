package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import javax.inject.Singleton;
import javax.validation.constraints.NotNull;

import org.cyk.utility.common.Action;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
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
	
	public Grid.Column<?> getColumn(){
		return ClassHelper.getInstance().instanciateOne(InstanceHelper.getInstance().getIfNotNullElseDefault(Grid.Column.DEFAULT_CLASS, Grid.Column.class));
	}
	
	@Getter @Setter
	public static class Grid<T,SELECT_ITEM> extends AbstractBean implements Serializable {
		private static final long serialVersionUID = -3543754685060813767L;

		protected String identifier = "grid_identifier_"+RandomHelper.getInstance().getNumeric(10);
		
		protected CollectionHelper.Instance<T> collection;
		
		protected CommandHelper.Command addCommand,deleteCommand;
		protected MapHelper.Map<String,CommandHelper.Command> commandMap = new MapHelper.Map<String, CommandHelper.Command>(String.class, CommandHelper.Command.class);
		protected Column<SELECT_ITEM> indexColumn,nameColumn,commandsColumn;
		protected Boolean isAddCommandShowableAtIndexColumnFooter;
		@SuppressWarnings("unchecked")
		protected MapHelper.Map<String,Column<SELECT_ITEM>> columnMap = new MapHelper.Map<String, Column<SELECT_ITEM>>(String.class
				, (Class<Column<SELECT_ITEM>>) ClassHelper.getInstance().getByName(Column.class));
		
		protected Collection<Listener<T>> listeners;
		
		/**/
		
		@SuppressWarnings("unchecked")
		public Grid(Class<T> elementClass,Class<?> elementObjectClass,Class<?> sourceClass,Class<?> sourceObjectClass) {
			collection = CollectionHelper.getInstance().getCollectionInstance(elementClass,elementObjectClass,sourceClass,sourceObjectClass);
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
			
			indexColumn = (Column<SELECT_ITEM>) getInstance().getColumn().setName(StringHelper.getInstance().get("grid.column.index", (Object[])null));
			nameColumn = (Column<SELECT_ITEM>) getInstance().getColumn().setName(StringHelper.getInstance().get("grid.column.name", (Object[])null));
			commandsColumn = (Column<SELECT_ITEM>) getInstance().getColumn().setName(StringHelper.getInstance().get("grid.column.commands", (Object[])null));
		}
		
		public Grid(Class<T> elementClass) {
			this(elementClass,(Class<?>)null,(Class<?>)null,(Class<?>)null);
		}
		
		protected void add(){
			collection.addOne();
		}
		
		protected void delete(){
			collection.removeOne(deleteCommand.getInput());
		}
		
		public GridHelper.Grid.Column<SELECT_ITEM> addFieldAsColumn(String fieldName,Boolean selectable,Boolean nullable){
			@SuppressWarnings("unchecked")
			GridHelper.Grid.Column<SELECT_ITEM> column = (Column<SELECT_ITEM>) GridHelper.getInstance().getColumn();
			getColumnMap().set(fieldName, column);
			column.setName(StringHelper.getInstance().getField(fieldName));
			if(Boolean.TRUE.equals(selectable)){
				Class<?> elementObjectClass = getCollection().getElementObjectClass();
				column.setField(FieldHelper.getInstance().get(elementObjectClass, fieldName));
				Class<?> aClass = FieldHelper.getInstance().getType(elementObjectClass, column.getField());
				column.setSelectItems(aClass, nullable == null ? column.getNullable() : null);
			}
			return column;
		}
		
		public GridHelper.Grid.Column<SELECT_ITEM> addFieldAsColumn(String fieldName,Boolean selectable){
			return addFieldAsColumn(fieldName, selectable, null);
		}
		
		public GridHelper.Grid.Column<SELECT_ITEM> addFieldAsColumn(String fieldName){
			return addFieldAsColumn(fieldName, Boolean.FALSE);
		}
		
		public GridHelper.Grid.Column<SELECT_ITEM> getColumn(String key){
			return columnMap.get(key);
		}
		
		public Grid<T,SELECT_ITEM> addListener(Listener<T> listener){
			if(this.listeners == null)
				this.listeners = new ArrayList<>();
			this.listeners.add(listener);
			return this;
		}
		
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
			protected Object identifier;
			
			public Dimension() {
				this.identifier = RandomHelper.getInstance().getAlphabetic(20);
			}
			
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
			
			@Override
			public String toString() {
				return name;
			}
		}
		
		@Getter @Setter @Accessors(chain=true)
		public static class Column<SELECT_ITEM> extends Dimension implements Serializable{
			private static final long serialVersionUID = 1L;
			
			@SuppressWarnings("unchecked")
			public static Class<Column<?>> DEFAULT_CLASS = (Class<Column<?>>) ClassHelper.getInstance().getByName(Column.class);
			
			private java.lang.reflect.Field field;
			private List<SELECT_ITEM> selectItems;
			private Class<?> inputClass;
			private MarkupLanguageHelper.Attributes inputAttributes;
			private List<CommandHelper.Command> footerCommands;
			
			public Boolean getIsInputText(){
				return inputClass!=null && InputText.class.equals(inputClass);
			}
			
			public Boolean getIsInputOneCombo(){
				return inputClass!=null && InputOneCombo.class.equals(inputClass);
			}
			
			public MarkupLanguageHelper.Attributes getInputAttributes(){
				if(inputAttributes==null)
					inputAttributes = new MarkupLanguageHelper.Attributes();
				return inputAttributes;
			}
			
			public Boolean getNullable(){
				return field == null ? Boolean.TRUE : field.getAnnotation(NotNull.class)==null;
			}
			
			@SuppressWarnings("unchecked")
			public Column<SELECT_ITEM> setSelectItems(Class<?> aClass,Boolean nullable){
				selectItems = (List<SELECT_ITEM>) SelectItemHelper.getInstance().get(aClass, nullable);
				return this;
			}
			
			public Column<SELECT_ITEM> addFooterCommand(CommandHelper.Command command){
				if(this.footerCommands == null)
					this.footerCommands = new ArrayList<CommandHelper.Command>();
				this.footerCommands.add(command);
				return this;
			}
			
			@SuppressWarnings("unchecked")
			@Override
			public Column<SELECT_ITEM> setName(String name) {
				return (Column<SELECT_ITEM>) super.setName(name);
			}
			
			public Column<SELECT_ITEM> setField(java.lang.reflect.Field field) {
				this.field = field;
				setIdentifier(field.getName()+Constant.CHARACTER_UNDESCORE+getIdentifier());
				return this;
			} 
			
			@SuppressWarnings("unchecked")
			@Override
			public Column<SELECT_ITEM> setProperty(Object name, Object value) {
				return (Column<SELECT_ITEM>) super.setProperty(name, value);
			}
			
			public Column<SELECT_ITEM> setWidth(Object value) {
				return setProperty(PROPERTY_NAME_WIDTH, value);
			}
			
			public Object getWidth(){
				return getProperty(PROPERTY_NAME_WIDTH);
			}
			
		}
	}
	
	public static final String COMMAND_ADD = "COMMAND_ADD";
	public static final String COMMAND_DELETE = "COMMAND_DELETE";
	
	public static final String COLUMN_INDEX = "COLUMN_INDEX";
	public static final String COLUMN_NAME = "COLUMN_NAME";
	public static final String COLUMN_COMMANDS = "COLUMN_COMMANDS";
	
}
