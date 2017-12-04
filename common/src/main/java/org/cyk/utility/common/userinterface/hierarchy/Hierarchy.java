package org.cyk.utility.common.userinterface.hierarchy;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.userinterface.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Hierarchy extends HierarchyNodesContainer implements Serializable {
	private static final long serialVersionUID = 1L;

	public static enum RenderType{TREE,TABLE;public static RenderType DEFAULT = TREE;}
	
	/**/
	
	private RenderType renderType = RenderType.DEFAULT;
	
	/**/
	
	public Hierarchy loadNodes(Collection<?> collection){
		loadNodes(null,null, collection);
		return this;
	}
	
	public Hierarchy loadNodes(CollectionHelper.Instance<?> collection){
		if(CollectionHelper.getInstance().isNotEmpty(collection))
			loadNodes(collection.getElements());
		return this;
	}
	
	protected Hierarchy loadNodes(HierarchyNode currentNode,Object parent,Collection<?> collection){
		Collection<?> children = InstanceHelper.getInstance().getByParent(collection, parent);
		if(CollectionHelper.getInstance().isNotEmpty(children))
			for(Object child : children){
				//Row row = new Row()._setObject(object);
				//row.getPropertiesMap().setValue(object);
				//rows.addOne(row);
				//row.set__orderNumber__(NumberHelper.getInstance().get(Long.class,CollectionHelper.getInstance().getSize(rows.getElements()),0l));
				//addOneChild(row);
				
				HierarchyNode node = instanciateNode();
				node.getPropertiesMap().setValue(child);
				node.setLabelFromIdentifier((String)InstanceHelper.getInstance().getIdentifier(child));
				if(currentNode==null)
					addNode(node);
				else
					currentNode.addNode(node);
				
				loadNodes(node,child, collection);
				
				//addNode((String)InstanceHelper.getInstance().getIdentifier(object));
			}
		/*if(getPropertiesMap().getValue()==null && CollectionHelper.getInstance().isNotEmpty(rows))
			getPropertiesMap().setValue(rows.getElements());
		*/
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
					
					@Override
					protected OUTPUT __execute__() {
						final OUTPUT instance = ClassHelper.getInstance().instanciateOne(getOutputClass());
						initializeRoot(instance);
						Hierarchy hierarchy = getInput();
						if(CollectionHelper.getInstance().isNotEmpty(hierarchy.getChildren()))
							new CollectionHelper.Iterator.Adapter.Default<Component>(hierarchy.getChildren().getElements()){
								private static final long serialVersionUID = 1L;
								protected void __executeForEach__(Component component) {
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
									addHierarchyNode(hierarchy,(HierarchyNode) component,node);
								}
								
							}.execute();
					}
				}
			}
		}
	}
}