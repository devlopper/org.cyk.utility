package org.cyk.utility.common.userinterface.hierarchy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.common.Constant;
import org.cyk.utility.common.Properties;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.NumberHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.collection.Cell;
import org.cyk.utility.common.userinterface.collection.Column;
import org.cyk.utility.common.userinterface.collection.Column.CellValueSource;
import org.cyk.utility.common.userinterface.collection.Columns;
import org.cyk.utility.common.userinterface.collection.DataTable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Hierarchy extends HierarchyNodesContainer implements Serializable {
	private static final long serialVersionUID = 1L;

	static {
		ClassHelper.getInstance().map(Listener.class, Listener.Adapter.Default.class,Boolean.FALSE);
	}
	
	public static enum RenderType{TREE,TABLE;public static RenderType DEFAULT = TREE;}
	
	/**/
	
	private RenderType renderType;
	
	/**/
	
	@Override
	protected void listenPropertiesInstanciated(Properties propertiesMap) {
		super.listenPropertiesInstanciated(propertiesMap);
		
	}
	
	@Override
	public Hierarchy build() {
		Columns columns = (Columns) getPropertiesMap().getColumns();
		if(columns!=null){
			CollectionHelper.getInstance().sort((Collection<?>) columns.getPropertiesMap().getValue());
		}
		return (Hierarchy) super.build();
	}
	
	@Override
	public Hierarchy prepare() {
		super.prepare();
		if(getRenderType() == null)
			setRenderType(RenderType.DEFAULT);
		if(RenderType.TABLE.equals(renderType)){
			getPropertiesMap().setOnPrepareAddMenu(Boolean.TRUE);
			getPropertiesMap().setOnPrepareAddColumnAction(Boolean.TRUE);
			getPropertiesMap().setOnPrepareAddColumnOrderNumber(Boolean.TRUE);
			getPropertiesMap().setOnPrepareAddMenuAddCommand(Boolean.TRUE);	
		}
		
		addMenu();
		addFirstColumns();
		__prepare__();
		addLastColumns();
		if(Boolean.TRUE.equals(onPrepareCallLoad)){
			load(); //can be trigger by callback to enabled fast rendering of table structure	
		}
		
		return this;
	}
	
	protected void __prepare__(){
		setOnPrepareCallLoad(Boolean.TRUE);
		DataTable.addFilter(this);
	}
	
	protected void addLastColumns(){
		addOtherColumns();
		if(Boolean.TRUE.equals(getPropertiesMap().getOnPrepareAddColumnAction())){
			addColumn("userinterface.column.action", Properties.MAIN_MENU).setCellValueSource(CellValueSource.ROW_PROPERTIES_MAP).setCellValueType(Cell.ValueType.MENU).set__orderNumber__(Long.MAX_VALUE);	
		}
	}
	
	public Column addColumnByFieldName(String fieldName){
		String labelStringIdentifier = StringHelper.getInstance().getI18nIdentifier(FieldHelper.getInstance().getLast(fieldName));
		return addColumn(labelStringIdentifier, fieldName);
	}
	
	public Hierarchy addColumnsByFieldNames(Collection<String> fieldNames){
		if(CollectionHelper.getInstance().isNotEmpty(fieldNames))
			for(String fieldName : fieldNames)
				addColumnByFieldName(fieldName);
		return this;
	}
	
	protected void addOtherColumns(){
		addColumnsByFieldNames(getColumnsFieldNames());
	}
	
	protected Collection<String> getColumnsFieldNames(){
		Collection<String> collection = ClassHelper.getInstance().instanciateOne(Listener.class).getColumnsFieldNames(this);
		if(collection == null)
			collection = new ArrayList<String>();
		ClassHelper.getInstance().instanciateOne(Listener.class).processColumnsFieldNames(this, collection);
		return collection;
	}
	
	protected void addMenu(){
		DataTable.addMenu(this);
	}
	
	protected void addFirstColumns(){
		DataTable.addFirstColumns(this);
	}
	
	@Override
	public Component load() {
		if(getPropertiesMap().getActionOnClass()!=null)
			loadNodes(InstanceHelper.getInstance().getHierarchyRoots((Class<?>)getPropertiesMap().getActionOnClass()));
		return this;
	}
	
	public Hierarchy loadNodes(Collection<?> collection){
		//loadNodes(null,null, collection);
		if(CollectionHelper.getInstance().isNotEmpty(collection))
			for(Object root : collection){
				HierarchyNode node = instanciateNode();
				node.getPropertiesMap().setValue(root);
				node.setLabelFromIdentifier((String)InstanceHelper.getInstance().getIdentifier(root));
				node.set__orderNumber__(NumberHelper.getInstance().get(Long.class,1,0l));
				node.getPropertiesMap().setTopLevelContainer(this);
				node.setNumberOfChildren(InstanceHelper.getInstance().getHierarchyNumberOfChildren(root));
				addNode(node);
			}
		return this;
	}
	
	public Hierarchy loadNodes(CollectionHelper.Instance<?> collection){
		if(CollectionHelper.getInstance().isNotEmpty(collection))
			loadNodes(collection.getElements());
		return this;
	}
	
	protected Hierarchy loadNodes(HierarchyNode currentNode,Object parent,Collection<?> collection){
		Collection<?> children = InstanceHelper.getInstance().getByParent(collection, parent);// HierarchyChildren(parent);
		Integer index = 0;
		if(CollectionHelper.getInstance().isNotEmpty(children))
			for(Object child : children){
				HierarchyNode node = instanciateNode();
				node.getPropertiesMap().setValue(child);
				node.setLabelFromIdentifier((String)InstanceHelper.getInstance().getIdentifier(child));
				node.set__orderNumber__(NumberHelper.getInstance().get(Long.class,++index,0l));
				node.getPropertiesMap().setTopLevelContainer(this);
				
				if(currentNode==null)
					addNode(node);
				else
					currentNode.addNode(node);
				
				loadNodes(node,child, collection);
			}
		return this;
	}
	
	/**/
	
	public static interface BuilderBase<OUTPUT extends Hierarchy> extends Component.Visible.BuilderBase<OUTPUT> {
 		
 		BuilderBase<OUTPUT> setRenderType(RenderType renderType);
 		
		public static class Adapter<OUTPUT extends Hierarchy> extends Component.Visible.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}

			/**/

			public static class Default<OUTPUT extends Hierarchy> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
				
				@Override
				public BuilderBase<OUTPUT> setRenderType(RenderType renderType){
					setProperty(PROPERTY_NAME_RENDER_TYPE, renderType);
					return this;
				}
			}
			
			@Override
			public BuilderBase<OUTPUT> setRenderType(RenderType renderType) {
				return null;
			}
		}
	}
	
	public static interface Builder extends BuilderBase<Hierarchy> {
		
		Builder setComponentParent(Component component);
		
		public static class Adapter extends BuilderBase.Adapter.Default<Hierarchy> implements Builder, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter() {
				super(Hierarchy.class);
			}

			/**/

			public static class Default extends Builder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				@Override
				protected Hierarchy __execute__() {
					Hierarchy hierarchy = new Hierarchy();
					hierarchy.setParent(componentParent);
					hierarchy.setRenderType((RenderType) getProperty(PROPERTY_NAME_RENDER_TYPE));
					return hierarchy;
				}
				
			}
			
			@Override
			public Builder setComponentParent(Component component) {
				return (Builder) super.setComponentParent(component);
			}
		}
	
		public static interface Target<OUTPUT> extends Component.BuilderBase.Target<Hierarchy, OUTPUT> {
			
			public static class Adapter<OUTPUT> extends Component.BuilderBase.Target.Adapter.Default<Hierarchy, OUTPUT> implements Target<OUTPUT>,Serializable {
				private static final long serialVersionUID = 1L;
				
				public Adapter(Hierarchy input, Class<OUTPUT> outputClass) {
					super(Hierarchy.class, input, outputClass);
				}
				
				public static class Default<OUTPUT> extends Target.Adapter<OUTPUT> implements Serializable {
					private static final long serialVersionUID = 1L;
					
					public Default(Hierarchy input, Class<OUTPUT> outputClass) {
						super(input, outputClass);
					}
					
					@SuppressWarnings("unchecked")
					public Default() {
						super(null, null);
						setOutputClass((Class<OUTPUT>) ClassHelper.getInstance().getParameterAt(getClass(), 0, Object.class));
					}
					
					protected OUTPUT instanciateOutput(){
						return ClassHelper.getInstance().instanciateOne(getOutputClass());
					}
					
					@Override
					protected OUTPUT __execute__() {
						final OUTPUT instance = instanciateOutput();
						initializeRoot(instance);
						Hierarchy hierarchy = getInput();
						if(CollectionHelper.getInstance().isNotEmpty(hierarchy.getChildren()))
							new CollectionHelper.Iterator.Adapter.Default<Component>(hierarchy.getChildren().getElements()){
								private static final long serialVersionUID = 1L;
								protected void __executeForEach__(Component component) {
									if(component instanceof HierarchyNode)
										addHierarchyNode(instance,(HierarchyNode) component,instance);
								}
								
							}.execute();
						return instance;
					}
					
					protected void initializeRoot(final OUTPUT hierarchy){}
					
					protected Object createNode(final OUTPUT hierarchy,HierarchyNode hierarchyNode,Object parent){
						Object node= __createNode__(hierarchy, hierarchyNode, parent);
						addNode(hierarchy, node,parent);
						return node;
					}
					
					protected Object __createNode__(final OUTPUT hierarchy,HierarchyNode hierarchyNode,Object parent){
						Object node= null;
						if(CollectionHelper.getInstance().isEmpty(hierarchyNode.getChildren())){
							node = createLeaf(hierarchy,hierarchyNode);
						}else{
							node = createNotLeaf(hierarchy,hierarchyNode);
						}
						return node;
					}
					
					protected void addNode(final OUTPUT hierarchy,Object node,Object parent){
						
					}
					
					protected Object createLeaf(final OUTPUT hierarchy,HierarchyNode hierarchyNode){
						return null;
					}

					protected Object createNotLeaf(final OUTPUT hierarchy,HierarchyNode hierarchyNode){
						return null;
					}
					
					protected void addHierarchyNode(final OUTPUT hierarchy,HierarchyNode hierarchyNode,Object parent){
						final Object node = createNode(hierarchy,hierarchyNode,parent);
						if(CollectionHelper.getInstance().isNotEmpty(hierarchyNode.getChildren()))
							new CollectionHelper.Iterator.Adapter.Default<Component>(hierarchyNode.getChildren().getElements()){
								private static final long serialVersionUID = 1L;
								protected void __executeForEach__(Component component) {
									if(component instanceof HierarchyNode)
										addHierarchyNode(hierarchy,(HierarchyNode) component,node);
								}
								
							}.execute();
					}
				}
			}
		}
	}

	/**/
	
	public static class ClassLocator extends Component.ClassLocator implements Serializable {

		private static final long serialVersionUID = -3187769614985951029L;

		public ClassLocator(String action) {
			super(Hierarchy.class,action);
		}
		
		public ClassLocator(Constant.Action action) {
			super(Hierarchy.class,action);
		}
		
	}

	public static interface Listener {
		
		Collection<String> getColumnsFieldNames(Hierarchy hierarchy);
		void processColumnsFieldNames(Hierarchy hierarchy,Collection<String> fieldNames);
		
		public static class Adapter extends AbstractBean implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				@Override
				public void processColumnsFieldNames(Hierarchy hierarchy, Collection<String> fieldNames) {
					super.processColumnsFieldNames(hierarchy, fieldNames);
					Class<?> actionOnClass = (Class<?>) hierarchy.getPropertiesMap().getActionOnClass();
					if(ClassHelper.getInstance().isIdentified(actionOnClass))
						fieldNames.add(ClassHelper.getInstance().getIdentifierFieldName(actionOnClass));
					if(ClassHelper.getInstance().isNamed(actionOnClass))
						fieldNames.add(ClassHelper.getInstance().getNameFieldName(actionOnClass));
					if(ClassHelper.getInstance().isTyped(actionOnClass))
						fieldNames.add(ClassHelper.getInstance().getTypeFieldName(actionOnClass));
				}
			}
			
			@Override
			public Collection<String> getColumnsFieldNames(Hierarchy hierarchy) {
				return null;
			}
			
			@Override
			public void processColumnsFieldNames(Hierarchy hierarchy, Collection<String> fieldNames) {
				
			}
			
		}
		
	}
}