package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.inject.Singleton;
import javax.validation.constraints.NotNull;

import org.cyk.utility.common.Action;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.CollectionHelper.Instance;
import org.cyk.utility.common.helper.GridHelper.Grid.Column.FieldDescriptor;
import org.cyk.utility.common.helper.MarkupLanguageHelper.Attributes;

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
	
	@lombok.Getter @lombok.Setter
	public static class Grid<T,SELECT_ITEM> extends AbstractBean implements Serializable {
		private static final long serialVersionUID = -3543754685060813767L;

		protected String identifier = "grid_identifier_"+RandomHelper.getInstance().getNumeric(10),name;
		
		protected CollectionHelper.Instance<T> collection;
		
		protected CommandHelper.Command addCommand,removeCommand,filterCommand;
		protected CommandHelper.Commands allCommands = CommandHelper.getInstance().getCommands();
		protected CommandHelper.Commands menuCommands = CommandHelper.getInstance().getCommands();
		
		protected Column<SELECT_ITEM> __indexColumn__,__nameColumn__,__commandsColumn__;
		protected Boolean isAddCommandShowableAtIndexColumnFooter;
		@SuppressWarnings("unchecked")
		protected MapHelper.Map<String,Column<SELECT_ITEM>> columnMap = new MapHelper.Map<String, Column<SELECT_ITEM>>(String.class
				, (Class<Column<SELECT_ITEM>>) ClassHelper.getInstance().getByName(Column.class));
		
		protected MarkupLanguageHelper.Attributes filterInputAttributes = new MarkupLanguageHelper.Attributes();
		protected Collection<Listener<T>> listeners;
		
		/**/
		
		@SuppressWarnings("unchecked")
		public Grid(Class<T> elementClass,Class<?> elementObjectClass,Class<?> sourceClass,Class<?> sourceObjectClass) {
			collection = CollectionHelper.getInstance().getCollectionInstance(elementClass,elementObjectClass,sourceClass,sourceObjectClass);
			addCommand = allCommands.create(COMMAND_ADD,"grid.command.add", IconHelper.Icon.PLUS_CIRCLE, new Action.ActionListener.Adapter(){
				private static final long serialVersionUID = 1L;
				@Override
				public void __execute__(Action<?, ?> action) {
					add();
				}
			});
			
			removeCommand = allCommands.create(COMMAND_REMOVE,"grid.command.remove", IconHelper.Icon.MINUS_CIRCLE, new Action.ActionListener.Adapter(){
				private static final long serialVersionUID = 1L;
				@Override
				public void __execute__(Action<?, ?> action) {
					remove();
				}
			}); 
			
			filterCommand = allCommands.create(COMMAND_SEARCH,"grid.command.filter", IconHelper.Icon.FILTER, new Action.ActionListener.Adapter(){
				private static final long serialVersionUID = 1L;
				@Override
				public void __execute__(Action<?, ?> action) {
					filter();
				}
			}); 
			
			menuCommands.add(addCommand);
			
			__indexColumn__ = (Column<SELECT_ITEM>) getInstance().getColumn().setName(StringHelper.getInstance().get("grid.column.index", (Object[])null));
			__nameColumn__ = (Column<SELECT_ITEM>) getInstance().getColumn().setName(StringHelper.getInstance().get("grid.column.name", (Object[])null));
			__commandsColumn__ = (Column<SELECT_ITEM>) getInstance().getColumn().setName(StringHelper.getInstance().get("grid.column.commands", (Object[])null));
		}
		
		public Grid(Class<T> elementClass) {
			this(elementClass,(Class<?>)null,(Class<?>)null,(Class<?>)null);
		}
		
		protected void add(){
			collection.addOne();
		}
		
		protected void remove(){
			collection.removeOne(removeCommand.getInput());
		}
		
		public String getName(){
			return getCollection().getName();
		}
		
		protected void filter(){
			
		}
		
		public GridHelper.Grid.Column<SELECT_ITEM> addFieldAsColumn(String fieldName,Class<?> fieldContainerClass){
			Class<?> elementClass = getCollection().getElementClass();
			Boolean isElementField = ClassHelper.getInstance().isInstanceOf(elementClass, fieldContainerClass);
			if(isElementField)
				getCollection().mapElementObjectFields(fieldName);
			@SuppressWarnings("unchecked")
			GridHelper.Grid.Column<SELECT_ITEM> column = (Column<SELECT_ITEM>) GridHelper.getInstance().getColumn();
			getColumnMap().set(fieldName, column);
			column.setName(StringHelper.getInstance().getField(fieldName));
			column.setFieldDescriptor(new FieldDescriptor(column,FieldHelper.getInstance().get(fieldContainerClass, fieldName),getCollection().getIsUpdatable()));
			column.setIdentifier(column.getFieldDescriptor().getField().getName()+Constant.CHARACTER_UNDESCORE+RandomHelper.getInstance().getAlphabetic(5));
			if(isElementField){
				if(Boolean.TRUE.equals(column.getFieldDescriptor().getIsValueSelectable())){				
					Class<?> aClass = FieldHelper.getInstance().getType(fieldContainerClass, column.getFieldDescriptor().getField());
					column.setSelectItems(aClass, column.getFieldDescriptor().getIsNullValueSelectable());
				}	
			}
			ListenerHelper.getInstance().listen(listeners, Listener.METHOD_NAME_ADD_COLUMN
					,MethodHelper.Method.Parameter.buildArray(Grid.class, this,Column.class,column));
			return column;
		}
		
		public GridHelper.Grid.Column<SELECT_ITEM> addFieldAsColumn(String fieldName){
			return addFieldAsColumn(fieldName,getCollection().getElementClass());
		}
		
		public Grid<T,SELECT_ITEM> addFieldsAsColumns(String...fieldNames){
			for(String fieldName : fieldNames)
				addFieldAsColumn(fieldName);
			return this;
		}
		
		public GridHelper.Grid.Column<SELECT_ITEM> addElementObjectFieldAsColumn(String fieldName){
			return addFieldAsColumn(fieldName,getCollection().getElementObjectClass());
		}
		
		public Grid<T,SELECT_ITEM> addElementObjectFieldsAsColumns(String...fieldNames){
			for(String fieldName : fieldNames)
				addElementObjectFieldAsColumn(fieldName);
			return this;
		}
		
		public GridHelper.Grid.Column<SELECT_ITEM> getColumn(String key){
			return columnMap.get(key);
		}
		
		@SuppressWarnings("unchecked")
		public GridHelper.Grid.Column<SELECT_ITEM> getColumnAt(Integer index){
			return MapHelper.getInstance().getAt(columnMap.getMap(), index, Column.class);
		}
		
		public List<Column<SELECT_ITEM>> getColumns(){
			return new ArrayList<GridHelper.Grid.Column<SELECT_ITEM>>(columnMap.getMap().values());
		}
		
		public Grid<T,SELECT_ITEM> addListener(Listener<T> listener){
			if(this.listeners == null)
				this.listeners = new ArrayList<>();
			this.listeners.add(listener);
			return this;
		}
		
		public Grid<T,SELECT_ITEM> addListeners(Collection<Listener<T>> listeners){
			if(CollectionHelper.getInstance().isNotEmpty(listeners))
				for(Listener<T> listener : listeners)
					addListener(listener);
			return this;
		}
		
		/**/
		
		public static interface Builder<T> extends org.cyk.utility.common.Builder.NullableInput<Grid<T,?>> {
			
			Instance<?> getMasterElementObjectCollection();
			Builder<T> setMasterElementObjectCollection(Instance<?> masterElementObjectCollection);
			
			Object getMasterObject();
			Builder<T> setMasterObject(Object masterObject);
			
			Class<T> getElementClass();
			Builder<T> setElementClass(Class<T> elementClass);
			
			Class<?> getElementObjectClass();
			Builder<T> setElementObjectClass(Class<?> elementObjectClass);
			
			Class<?> getElementClassContainerClass();
			Builder<T> setElementClassContainerClass(Class<?> elementClassContainerClass);
			
			Set<String> getColumnFieldNames();
			GridHelper.Grid.Builder<T> setColumnFieldNames(Set<String> columnFieldNames);
			GridHelper.Grid.Builder<T> addColumnFieldNames(String...columnFieldNames);
			
			Class<?> getSourceClass();
			Builder<T> setSourceClass(Class<?> sourceClass);
			
			Class<?> getSourceObjectClass();
			Builder<T> setSourceObjectClass(Class<?> sourceObjectClass);
			
			Collection<?> getElementObjects();
			Builder<T> setElementObjects(Collection<?> elementObjects);
			
			Class<?> getColumnFieldContainerClass();
			Builder<T> setColumnFieldContainerClass(Class<?> columnFieldContainerClass);
			
			Collection<Grid<T,?>> getGridCollection();
			Builder<T> setGridCollection(Collection<Grid<T,?>> gridCollection);
			
			Collection<Grid.Listener<T>> getListeners();
			Builder<T> setListeners(Collection<Grid.Listener<T>> listeners);
			Builder<T> addListener(Grid.Listener<T> listener);
			
			Collection<CollectionHelper.Instance.Listener<T>> getCollectionListeners();
			Builder<T> setCollectionListeners(Collection<CollectionHelper.Instance.Listener<T>> collectionListeners);
			Builder<T> addCollectionListener(CollectionHelper.Instance.Listener<T> collectionListener);
			
			@lombok.Getter
			public static class Adapter<T> extends org.cyk.utility.common.Builder.NullableInput.Adapter.Default<Grid<T,?>> implements Builder<T>,Serializable {
				private static final long serialVersionUID = 1L;
				
				protected Object masterObject;
				protected Class<T> elementClass;
				protected Class<?> elementObjectClass,elementClassContainerClass,sourceClass,sourceObjectClass,columnFieldContainerClass;
				protected Set<String> columnFieldNames;
				protected Collection<?> elementObjects;
				protected Collection<Grid<T,?>> gridCollection;
				protected Collection<Listener<T>> listeners;
				protected Collection<CollectionHelper.Instance.Listener<T>> collectionListeners;
				protected Instance<?> masterElementObjectCollection;
				
				@SuppressWarnings("unchecked")
				public Adapter() {
					super((Class<Grid<T, ?>>) ClassHelper.getInstance().getByName(Grid.class));
				}
				
				public static class Default<T> extends GridHelper.Grid.Builder.Adapter<T> implements Serializable {
					private static final long serialVersionUID = 1L; 
					
					@SuppressWarnings("unchecked")
					@Override
					protected Grid<T, ?> __execute__() {
						Class<T> elementClass = getElementClass();
						Class<?> elementObjectClass = getElementObjectClass();
						Class<?> sourceClass = getSourceClass();
						Class<?> sourceObjectClass = getSourceObjectClass();
						if(elementClass==null)
							elementClass = (Class<T>) ClassHelper.getInstance().getByName(getElementClassContainerClass().getName()+"$"+elementObjectClass.getSimpleName()+"Item");
						Class<?> columnFieldContainerClass = InstanceHelper.getInstance().getIfNotNullElseDefault(getColumnFieldContainerClass(),elementClass);
						Set<String> columnFieldNames = getColumnFieldNames();
						String[] fieldNames = columnFieldNames == null ? null : columnFieldNames.toArray(new String[]{});
						if(ArrayHelper.getInstance().isEmpty(fieldNames))
							fieldNames = FieldHelper.getInstance().getNamesWhereReferencedByStaticField(columnFieldContainerClass).toArray(new String[]{});
						Grid<T,?> grid = output == null ? new Grid<>(elementClass,elementObjectClass,sourceClass,sourceObjectClass) : output;
						grid.addListeners(getListeners());
						grid.getCollection().addListeners(getCollectionListeners());
						if(grid.getCollection().getElementClass()==null)
							grid.getCollection().setElementClass(elementClass);
						grid.getCollection().setName(StringHelper.getInstance().getClazz(elementObjectClass));
						grid.addFieldsAsColumns(fieldNames);
						grid.get__nameColumn__().setIsShowable(sourceObjectClass!=null);
						grid.getCollection().setIsElementObjectCreatable(Boolean.TRUE);
						Collection<?> elementObjects = getElementObjects();
						CollectionHelper.Instance<?> masterElementObjectCollection = getMasterElementObjectCollection();
						if(grid.getCollection().getMasterElementObjectCollection()==null)
							grid.getCollection().setMasterElementObjectCollection((Instance<Object>) masterElementObjectCollection);
						if(elementObjects==null){
							if(grid.getCollection().getMasterElementObjectCollection()==null){
								Object masterObject = getMasterObject();
								if(masterObject!=null)
									elementObjects = InstanceHelper.getInstance().get(elementObjectClass,masterObject);	
							}else {
								elementObjects = grid.getCollection().getMasterElementObjectCollection().filter(elementObjectClass);
							}
						}
						grid.getCollection().addMany(elementObjects);
						Collection<Grid<T,?>> gridCollection = getGridCollection();
						if(gridCollection!=null)
							gridCollection.add(grid);
						return grid;
					}
					
					@Override
					public Builder<T> setMasterElementObjectCollection(Instance<?> masterElementObjectCollection) {
						this.masterElementObjectCollection = masterElementObjectCollection;
						return this;
					}
					
					@Override
					public Builder<T> setListeners(Collection<Listener<T>> listeners) {
						this.listeners = listeners;
						return this;
					}
					
					@Override
					public Builder<T> setMasterObject(Object masterObject) {
						this.masterObject = masterObject;
						return this;
					}
					
					@Override
					public Builder<T> setElementClass(Class<T> elementClass) {
						this.elementClass = elementClass;
						return this;
					}
					
					@Override
					public Builder<T> setElementObjectClass(Class<?> elementObjectClass) {
						this.elementObjectClass = elementObjectClass;
						return this;
					}
					
					@Override
					public Builder<T> setElementClassContainerClass(Class<?> elementClassContainerClass) {
						this.elementClassContainerClass = elementClassContainerClass;
						return this;
					}
					
					@Override
					public Builder<T> setColumnFieldNames(Set<String> columnFieldNames) {
						this.columnFieldNames = columnFieldNames;
						return this;
					}
					
					@Override
					public Builder<T> addColumnFieldNames(String... columnFieldNames) {
						if(this.columnFieldNames==null)
							this.columnFieldNames = new LinkedHashSet<String>();
						CollectionHelper.getInstance().add(this.columnFieldNames, columnFieldNames);
						return this;
					}
					
					@Override
					public Builder<T> setSourceClass(Class<?> sourceClass) {
						this.sourceClass = sourceClass;
						return this;
					}
					
					@Override
					public Builder<T> setSourceObjectClass(Class<?> sourceObjectClass) {
						this.sourceObjectClass = sourceObjectClass;
						return this;
					}
					
					@Override
					public Builder<T> setElementObjects(Collection<?> elementObjects) {
						this.elementObjects = elementObjects;
						return this;
					}
					
					@Override
					public Builder<T> setGridCollection(Collection<Grid<T, ?>> gridCollection) {
						this.gridCollection = gridCollection;
						return this;
					}
					
					@Override
					public Builder<T> addListener(Listener<T> listener) {
						if(this.listeners == null)
							this.listeners = new ArrayList<GridHelper.Grid.Listener<T>>();
						this.listeners.add(listener);
						return this;
					}
					
					@Override
					public Builder<T> setCollectionListeners(Collection<CollectionHelper.Instance.Listener<T>> collectionListeners) {
						this.collectionListeners = collectionListeners;
						return this;
					}
					
					@Override
					public Builder<T> addCollectionListener(CollectionHelper.Instance.Listener<T> collectionListener) {
						if(this.collectionListeners == null)
							this.collectionListeners = new ArrayList<CollectionHelper.Instance.Listener<T>>();
						this.collectionListeners.add(collectionListener);
						return this;
					}
					
					@Override
					public Builder<T> setColumnFieldContainerClass(Class<?> columnFieldContainerClass) {
						this.columnFieldContainerClass = columnFieldContainerClass;
						return this;
					}
					
				}
				
				@Override
				public Builder<T> addColumnFieldNames(String... columnFieldNames) {
					return null;
				}
				
				@Override
				public Builder<T> setColumnFieldContainerClass(Class<?> columnFieldContainerClass) {
					return null;
				}
						
				@Override
				public Builder<T> setMasterElementObjectCollection(Instance<?> masterElementObjectCollection) {
					return null;
				}
				
				@Override
				public Builder<T> addListener(Listener<T> listener) {
					return null;
				}
				
				@Override
				public Builder<T> setCollectionListeners(Collection<CollectionHelper.Instance.Listener<T>> collectionListeners) {
					return null;
				}
				
				@Override
				public Builder<T> addCollectionListener(CollectionHelper.Instance.Listener<T> collectionListener) {
					return null;
				}
				
				@Override
				public Builder<T> setListeners(Collection<Listener<T>> listeners) {
					return null;
				}
				
				@Override
				public Builder<T> setMasterObject(Object masterObject) {
					return null;
				}
				
				@Override
				public Builder<T> setElementClass(Class<T> elementClass) {
					return null;
				}
				
				@Override
				public Builder<T> setElementObjectClass(Class<?> elementObjectClass) {
					return null;
				}
				
				@Override
				public Builder<T> setElementClassContainerClass(Class<?> elementClassContainerClass) {
					return null;
				}
				
				@Override
				public Builder<T> setColumnFieldNames(Set<String> columnFieldNames) {
					return null;
				}
				
				@Override
				public Builder<T> setSourceClass(Class<?> sourceClass) {
					return null;
				}
				
				@Override
				public Builder<T> setSourceObjectClass(Class<?> sourceObjectClass) {
					return null;
				}
				
				@Override
				public Builder<T> setElementObjects(Collection<?> elementObjects) {
					return null;
				}
				
				@Override
				public Builder<T> setGridCollection(Collection<Grid<T, ?>> gridCollection) {
					return null;
				}
			}
			
		}
		
		public static interface Listener<T> {
			
			String METHOD_NAME_ADD_COLUMN = "addColumn";
			void addColumn(Grid<T,?> grid,Column<?> column);
			
			public static class Adapter<T> extends AbstractBean implements Listener<T>,Serializable {
				private static final long serialVersionUID = 1L;

				@Override
				public void addColumn(Grid<T,?> grid,Column<?> column) {}
				
			}
			
		}
		
		/**/
		
		@lombok.Getter @lombok.Setter @lombok.experimental.Accessors(chain=true)
		public static class Dimension extends AbstractBean implements Serializable{
			private static final long serialVersionUID = 1L;
			
			protected Properties properties;
			protected String name;
			protected Boolean isShowable=Boolean.TRUE;
			protected Object identifier;
			
			public Dimension() {
				this.identifier = RandomHelper.getInstance().getAlphabetic(5);
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
		
		@lombok.Getter @lombok.Setter @lombok.experimental.Accessors(chain=true)
		public static class Column<SELECT_ITEM> extends Dimension implements Serializable{
			private static final long serialVersionUID = 1L;
			
			@SuppressWarnings("unchecked")
			public static Class<Column<?>> DEFAULT_CLASS = (Class<Column<?>>) ClassHelper.getInstance().getByName(Column.class);
			
			private List<SELECT_ITEM> selectItems;
			private List<CommandHelper.Command> footerCommands;
			protected FieldDescriptor fieldDescriptor;
			
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
			
			public java.lang.reflect.Field getField(){
				return fieldDescriptor.getField();
			}
			
			@lombok.Getter @lombok.Setter @lombok.experimental.Accessors(chain=true)
			public static class FieldDescriptor extends AbstractBean implements Serializable {
				private static final long serialVersionUID = 1L;
				
				private Column<?> column;
				private java.lang.reflect.Field field;
				private Boolean isUpdatable;
				
				public FieldDescriptor(Column<?> column,java.lang.reflect.Field field,Boolean isUpdatable) {
					this.column = column;
					this.field = field;
					this.isUpdatable = isUpdatable;
					MarkupLanguageHelper.Attributes attributes = (Attributes) MethodHelper.getInstance().callGet(this, MapHelper.Map.class, "propertiesMap");
					attributes.setLabel(column.getName());
					if(Boolean.TRUE.equals(isUpdatable))
						attributes.setRequired(String.valueOf(getIsValueNotNullable()));
				}
								
				public Boolean getIsValueTextable(){
					return field.isAnnotationPresent(InputText.class);
				}
				
				public Boolean getIsValueSelectable(){
					return field.isAnnotationPresent(InputChoice.class);
				}
				
				public Boolean getIsValueSelectableOne(){
					return field.isAnnotationPresent(InputOneChoice.class);
				}
				
				public Boolean getIsValueSelectableOneCombo(){
					return field.isAnnotationPresent(InputOneCombo.class);
				}
				
				public Boolean getIsNullValueSelectable(){
					return Boolean.TRUE.equals(getIsValueSelectable()) && field.getAnnotation(InputChoice.class).nullable();
				}
				
				public Boolean getIsValueNullable(){
					return !field.isAnnotationPresent(NotNull.class);
				}
				
				public Boolean getIsValueNotNullable(){
					return !Boolean.TRUE.equals(getIsValueNullable());
				}
				
			}
			
		}
	
		@lombok.Getter @lombok.Setter @lombok.experimental.Accessors(chain=true)
		public static class Row extends Dimension implements Serializable{
			private static final long serialVersionUID = 1L;
			
		}
	
		/**/
	}
	
	public static final String COMMAND_ADD = "COMMAND_ADD";
	public static final String COMMAND_REMOVE = "COMMAND_DELETE";
	public static final String COMMAND_SEARCH = "COMMAND_SEARCH";
	
	public static final String COLUMN_INDEX = "COLUMN_INDEX";
	public static final String COLUMN_NAME = "COLUMN_NAME";
	public static final String COLUMN_COMMANDS = "COLUMN_COMMANDS";
	
}
