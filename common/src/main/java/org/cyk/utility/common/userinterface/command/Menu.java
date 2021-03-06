package org.cyk.utility.common.userinterface.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.cyk.utility.common.Constant;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.security.SecurityHelper;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.container.Container;
import org.cyk.utility.common.userinterface.container.window.Window;
import org.cyk.utility.common.userinterface.hierarchy.MenuNodesContainer;

@Getter @Setter @Accessors(chain=true)
public class Menu extends MenuNodesContainer implements Serializable {
	private static final long serialVersionUID = 1L;

	public static enum Set{APPLICATION,SESSION,MODULE,WINDOW;public static Set DEFAULT = SESSION;}
	public static enum Type{MAIN,CONTEXT;public static Type DEFAULT = MAIN;}
	public static enum RenderType{PLAIN,SLIDE,PANEL,TAB,BREAD_CRUMB,BAR;public static RenderType DEFAULT = BAR;}
	
	private Set set = Set.DEFAULT;
	private Type type = Type.DEFAULT;
	private RenderType renderType = RenderType.DEFAULT;
	
	/**/
	
	/**/
	
	public static Menu build(Component parent,Type type,RenderType renderType,Boolean buildTarget){
		Menu menu = ClassHelper.getInstance().instanciateOne(Builder.class).setComponentParent(parent).setType(type).setRenderType(renderType).execute();
		if(Boolean.TRUE.equals(buildTarget))
			menu.build();
		return menu;
	}
	
	public static Menu build(Component parent,Type type,RenderType renderType){
		return build(parent, type,renderType, Boolean.TRUE);
	}
	
	public static Menu build(Component parent,Type type){
		return build(parent, type,RenderType.BAR);
	}
	
	/**/
	
 	public static interface BuilderBase<OUTPUT extends Menu> extends Container.BuilderBase<OUTPUT> {
 		
 		BuilderBase<OUTPUT> setSet(Set set);
 		BuilderBase<OUTPUT> setType(Type type);
 		BuilderBase<OUTPUT> setRenderType(RenderType renderType);
 		String getHomeViewIdentifier();
 		String getControlPanelIdentifiablesManageViewIdentifier();
 		String getUserLogoutViewIdentifier();
 		
 		@Getter
		public static class Adapter<OUTPUT extends Menu> extends Container.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
			private static final long serialVersionUID = 1L;

			protected String homeViewIdentifier,controlPanelIdentifiablesManageViewIdentifier,userLogoutViewIdentifier;
			
			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}

			/**/

			public static class Default<OUTPUT extends Menu> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
				
				@Override
				public BuilderBase<OUTPUT> setSet(Set set){
					setProperty(PROPERTY_NAME_SET, set);
					return this;
				}
				
				@Override
				public BuilderBase<OUTPUT> setType(Type type){
					setProperty(PROPERTY_NAME_TYPE, type);
					return this;
				}
				
				@Override
				public BuilderBase<OUTPUT> setRenderType(RenderType renderType){
					setProperty(PROPERTY_NAME_RENDER_TYPE, renderType);
					return this;
				}
			}
		
			@Override
			public BuilderBase<OUTPUT> setSet(Set set){
				return null;
			}
			
			@Override
			public BuilderBase<OUTPUT> setType(Type type){
				return null;
			}
			
			@Override
			public BuilderBase<OUTPUT> setRenderType(RenderType renderType) {
				return null;
			}
		}
	}
	
	public static interface Builder extends BuilderBase<Menu> {
		
		@Override Builder setSet(Set set);
		@Override Builder setType(Type set);
		Builder setComponentParent(Component component);
		
		public static class Adapter extends BuilderBase.Adapter.Default<Menu> implements Builder, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter() {
				super(Menu.class);
			}

			/**/

			public static class Default extends Builder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				protected MenuNode homeMainMenuNode,controlPanelMainMenuNode;
				
				public Default() {
					homeViewIdentifier = "homeView";
					controlPanelIdentifiablesManageViewIdentifier = "controlPanelIdentifiablesManageView";
					userLogoutViewIdentifier = "userLogoutView";
				}
				
				@Override
				protected Menu __execute__() {
					Object principal = SecurityHelper.getInstance().getPrincipal();
					Menu menu = new Menu();
					menu.setParent(componentParent);
					menu.setSet((Set) getProperty(PROPERTY_NAME_SET));
					menu.setType((Type) getProperty(PROPERTY_NAME_TYPE));
					menu.setRenderType((RenderType) getProperty(PROPERTY_NAME_RENDER_TYPE));
					
					if(Menu.Type.MAIN.equals(menu.getType()))
						addMain(menu,principal);
					else if(Menu.Type.CONTEXT.equals(menu.getType()))
						addContext(menu,principal);
					return menu;
				}
				
				protected void addMain(Menu menu,Object principal){
					homeMainMenuNode = menu.addNode("userinterface.menu.main.home.label",getHomeViewIdentifier());
					
					controlPanelMainMenuNode = menu.addNode("userinterface.menu.main.controlpanel.label");
					controlPanelMainMenuNode.addNode("userinterface.menu.main.controlpanel.identifiables.manage.label",getControlPanelIdentifiablesManageViewIdentifier());
					
					/*
					menu.addNode("ui.menu.tools","toolsView")
						.addNode("ui.menu.tools.data.export")
						.getParentAsNode().addNode("ui.menu.tools.data.import")
						;
					*/
					
					if(principal!=null){
						MenuNode menuNode = menu.addNode((String)null,"userView");
						menuNode.getLabel().getPropertiesMap().setValue(principal);
						menuNode.addNode("userinterface.menu.main.user.account.manage.label");
						menuNode.addNode("userinterface.menu.main.user.logout.label",getUserLogoutViewIdentifier());
					}
				}
				
				protected void addContext(Menu menu,Object principal){
					if(Boolean.TRUE.equals(isIdentifiablesManageWindow())){
						addNodeIdentifiablesManage(menu);
					}else if(Boolean.TRUE.equals(isIdentifiableConsultWindow()) || Boolean.TRUE.equals(isIdentifiableEditWindow())){
						Object instance = CollectionHelper.getInstance().getFirst(((Window)componentParent).getActionOnClassInstances());
						if(instance!=null){
							Constant.Action action = (Constant.Action) componentParent.getPropertiesMap().getAction();
							addNodeInstance(menu,instance,action);
							addNodeInstanceClass(menu, instance.getClass(), action);	
						}
					}
				}
				
				protected Boolean isIdentifiablesManageWindow(){
					return null;
				}
				
				protected Boolean isIdentifiableConsultWindow(){
					return null;
				}
				
				protected Boolean isIdentifiableEditWindow(){
					return null;
				}
				
				protected void addNodeIdentifiablesManage(Menu menu){
					Collection<Class<?>> packageFromClass = new ArrayList<>();
					populateNodeIdentifiablesManagePackageFromClass(packageFromClass);
					if(CollectionHelper.getInstance().isNotEmpty(packageFromClass)){						
						menu.addNodeActionListManyFromPackage(packageFromClass.toArray(new Class<?>[]{}));
						menu.getChildren().setComparator(new Component.LabelPropertyValueComparator()).sort();
					}
				}
				
				protected void populateNodeIdentifiablesManagePackageFromClass(Collection<Class<?>> packageFromClass){}
				
				protected void addNodeInstance(Menu menu,Object instance,Constant.Action action){
					MenuNode node = menu.addNode("");
					node.getPropertiesMap().setExpanded(Boolean.TRUE);
					node._setLabelPropertyValue(InstanceHelper.getInstance().getLabel(instance));
					if(Boolean.TRUE.equals(isIdentifiableConsultWindow())){
						addNodeInstanceUpdate(node, instance, action);
						addNodeInstanceDelete(node, instance, action);
					}else if(Boolean.TRUE.equals(isIdentifiableEditWindow())){
						if(Constant.Action.isOneOf(action, Constant.Action.UPDATE,Constant.Action.DELETE)){
							addNodeInstanceRead(node, instance, action);	
						}else{
								
						}
					} 
				}
				
				protected void addNodeInstanceRead(MenuNode node,Object instance,Constant.Action action){
					node.addNodeActionRead(instance);
				}
				
				protected void addNodeInstanceUpdate(MenuNode node,Object instance,Constant.Action action){
					node.addNodeActionUpdate(instance);
				}
				
				protected void addNodeInstanceDelete(MenuNode node,Object instance,Constant.Action action){
					node.addNodeActionDelete(instance);
				}
				
				protected void addNodeInstanceClass(Menu menu,Class<?> instanceClass,Constant.Action action){
					MenuNode node = menu.addNode("");
					node.getPropertiesMap().setExpanded(Boolean.TRUE);
					node._setLabelPropertyValue(StringHelper.getInstance().getClazz(instanceClass));
					if(Boolean.TRUE.equals(isIdentifiableConsultWindow())){
						addNodeInstanceClassListMany(node, instanceClass);
						addNodeInstanceClassCreateOne(node, instanceClass);
					}else if(Boolean.TRUE.equals(isIdentifiableEditWindow())){
						addNodeInstanceClassListMany(node, instanceClass);
					}
				}
				
				protected void addNodeInstanceClassListMany(MenuNode node,Class<?> instanceClass){
					node.addNodeActionListMany(instanceClass);
					((Component.Visible)CollectionHelper.getInstance().getLast(node.getChildren().getElements())).__setLabelValueBasedOnActionProperty__();
				}
				
				protected void addNodeInstanceClassCreateOne(MenuNode node,Class<?> instanceClass){
					node.addNodeActionCreate(instanceClass);
					((Component.Visible)CollectionHelper.getInstance().getLast(node.getChildren().getElements())).__setLabelValueBasedOnActionProperty__();
				}
			}
			
			@Override
			public Builder setSet(Set set) {
				return (Builder) super.setSet(set);
			}
			
			@Override
			public Builder setType(Type type) {
				return (Builder) super.setType(type);
			}
			
			@Override
			public Builder setComponentParent(Component component) {
				return (Builder) super.setComponentParent(component);
			}
		}
	
		public static interface Target<OUTPUT> extends Component.BuilderBase.Target<Menu, OUTPUT> {
			
			public static class Adapter<OUTPUT> extends Component.BuilderBase.Target.Adapter.Default<Menu, OUTPUT> implements Target<OUTPUT>,Serializable {
				private static final long serialVersionUID = 1L;
				
				public Adapter(Menu input, Class<OUTPUT> outputClass) {
					super(Menu.class, input, outputClass);
				}
				
				public static class Default<OUTPUT> extends Target.Adapter<OUTPUT> implements Serializable {
					private static final long serialVersionUID = 1L;
					
					public Default(Menu input, Class<OUTPUT> outputClass) {
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
						Menu menu = getInput();
						if(CollectionHelper.getInstance().isNotEmpty(menu.getChildren()))
							new CollectionHelper.Iterator.Adapter.Default<Component>(menu.getChildren().getElements()){
								private static final long serialVersionUID = 1L;
								protected void __executeForEach__(Component component) {
									addMenuNode(instance,(MenuNode) component,null);
								}
								
							}.execute();
						return instance;
					}
					
					protected Object createNode(final OUTPUT menu,MenuNode menuNode,Object parent){
						Object node= null;
						if(CollectionHelper.getInstance().isEmpty(menuNode.getChildren())){
							node = createLeaf(menu,menuNode);
						}else{
							node = createNotLeaf(menu,menuNode);
						}
						addNode(menu, node,parent);
						return node;
					}
					
					protected void addNode(final OUTPUT menu,Object node,Object parent){
						
					}
					
					protected Object createLeaf(final OUTPUT menu,MenuNode menuNode){
						return null;
					}

					protected Object createNotLeaf(final OUTPUT menu,MenuNode menuNode){
						return null;
					}
					
					protected void addMenuNode(final OUTPUT menu,MenuNode menuNode,Object parent){
						final Object node = createNode(menu,menuNode,parent);
						if(CollectionHelper.getInstance().isNotEmpty(menuNode.getChildren()))
							new CollectionHelper.Iterator.Adapter.Default<Component>(menuNode.getChildren().getElements()){
								private static final long serialVersionUID = 1L;
								protected void __executeForEach__(Component component) {
									addMenuNode(menu,(MenuNode) component,node);
								}
								
							}.execute();
					}
					
					
					
					/*
					public Object menuItem(UICommandable aCommandable,DefaultSubMenu parent,String managedBeanName,String...fields){
						if(aCommandable.getChildren().isEmpty()){
							DefaultMenuItem	menuItem = new DefaultMenuItem();
							menuItem.setValue(aCommandable.getLabel());
							menuItem.setRendered(Boolean.TRUE.equals(aCommandable.getRendered()));
							if(aCommandable.getIcon()!=null)
								menuItem.setIcon(FontAwesomeIconSet.INSTANCE.get(aCommandable.getIcon()));
							if(aCommandable.getCascadeStyleSheet()!=null){
								menuItem.setStyleClass(aCommandable.getCascadeStyleSheet().getClazz());
								menuItem.setStyle(aCommandable.getCascadeStyleSheet().getInline());
								
							}
							
							if(aCommandable.getIsNavigationCommand()){
								if(aCommandable.getViewType()==null){
									if(aCommandable.getViewId()!=null){
										menuItem.setOutcome(aCommandable.getViewId().toString());
									
									}
								}else{
									if(aCommandable.getBusinessEntityInfos()!=null)
										menuItem.setParam(UniformResourceLocatorParameter.CLASS, UIManager.getInstance().keyFromClass(aCommandable.getBusinessEntityInfos()));
								}
								//TODO navigation mode to be handled
								for(Parameter parameter : aCommandable.getParameters()){
									if(StringUtils.isBlank(parameter.getName()) || parameter.getValue()==null)
										;
									else
										menuItem.setParam(parameter.getName(), parameter.getValue());
								}
								
								menuItem.setOnclick(aCommandable.getOnClick());
							}else{
								
								menuItem.setUpdate(WebManager.getInstance().getFormContentFullId());//TODO make it constant
								menuItem.setOnstart(WebManager.getInstance().getBlockUIDialogWidgetId()+".show();");
								menuItem.setOnsuccess(WebManager.getInstance().getBlockUIDialogWidgetId()+".hide();");
								menuItem.setGlobal(true);
								
								if(aCommandable.getViewType()==null){
									
								}else{
									switch(aCommandable.getViewType()){
									case USERACCOUNT_LOGOUT:
										menuItem.setCommand("#{userSession.logout()}");
										menuItem.setAjax(Boolean.FALSE);
										break;
									default:menuItem.setCommand(String.format(EL_MENU_ITEM_COMMAND_FORMAT, managedBeanName,StringUtils.join(fields,Constant.CHARACTER_DOT),aCommandable.getIdentifier())); 
									}
								}
												
								if(UICommandable.ProcessGroup.THIS.equals(aCommandable.getProcessGroup()))
									menuItem.setProcess("@this");	
							}
							//if(StringUtils.isNotBlank(aCommandable.getOnClick()))
							//	menuItem.setOnclick(aCommandable.getOnClick());
							
							if(parent!=null)
								parent.addElement(menuItem);
							return menuItem;
						}else{
							
							//DefaultSubMenu subMenu = new DefaultSubMenu(aCommandable.getLabel());
							//subMenu.setRendered(Boolean.TRUE.equals(aCommandable.getRendered()));
							//if(aCommandable.getIcon()!=null)
							//	subMenu.setIcon(FontAwesomeIconSet.INSTANCE.get(aCommandable.getIcon()));
							//if(aCommandable.getCascadeStyleSheet()!=null){
							//	subMenu.setStyleClass(aCommandable.getCascadeStyleSheet().getClazz());
							//	subMenu.setStyle(aCommandable.getCascadeStyleSheet().getInline());
							//}
							//for(UICommandable commandable : aCommandable.getChildren())
							//	menuItem(commandable, subMenu, managedBeanName, fields);
							r//eturn subMenu;
							
						}
					}
					*/
					
					
				}
			}
		}
	}
	
	/**/
	
	
}
