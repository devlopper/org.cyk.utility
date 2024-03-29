package org.cyk.utility.client.controller.web.jsf.primefaces.deployment.web;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.cyk.utility.client.controller.component.command.Commandable;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.menu.Menu;
import org.cyk.utility.client.controller.component.menu.MenuItemBuilder;
import org.cyk.utility.client.controller.component.tree.Tree;
import org.cyk.utility.client.controller.component.tree.TreeBuilder;
import org.cyk.utility.client.controller.event.EventName;
import org.cyk.utility.client.controller.icon.Icon;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.component.OrganigramNodeBuilder;
import org.cyk.utility.hierarchy.HierarchyNode;
import org.primefaces.component.organigram.OrganigramHelper;
import org.primefaces.model.OrganigramNode;

import lombok.Getter;
import lombok.Setter;

@ManagedBean
//@ViewScoped
@SessionScoped
@Getter @Setter
public class OrganigramView3 extends AbstractPageContainerManagedImpl implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Tree tree;
    private String name;
    private Menu menu;
    private Commandable commandable;
	
    @PostConstruct
    public void init() {
    	TreeBuilder treeBuilder = __inject__(TreeBuilder.class);
    	
    	treeBuilder.getRoot(Boolean.TRUE).getHierarchyNode(Boolean.TRUE).setData("CommerceBay GmbH");
    	
    	treeBuilder.getRoot(Boolean.TRUE).getHierarchyNode(Boolean.TRUE)
        	.addNode("Software Development").getLastChild().setIcon("fa fa-briefcase")
        		.addNode("Ridvan Agar")
        		.addNode("Team JavaEE").getLastChild()
        			.addNode("JSF").getLastChild()
        				.addNode("Thomas Andraschko")
        				.getParent()
        			.addNode("Backend").getLastChild()
        				.addNode("Marie Louise")
        				.getParent()
        			.getParent()
        		.addNode("Team Mobile").getLastChild()
	        		.addNode("Android").getLastChild()
						.addNode("Andy Ruby")
						.getParent()
					.addNode("iOS").getLastChild()
						.addNode("Stevan Jobs")
						.getParent()
					.getParent()					
        		.getParent()
        	.addNode("Managed Services").getLastChild()
        		.addNode("Thorsten Schultze")
        		.addNode("Sandra Becker")
        		.getParent()
	        .addNode("Marketing").getLastChild()
    			.addNode("Social Media").getLastChild()
    				.addNode("Ali Mente")
    				.addNode("Lisa Boehm")
    				.getParent()
    			.addNode("Press").getLastChild()
    				.addNode("Michael Gmeiner")
    				.addNode("Hans Peter")
    				.getParent()
				.getParent()	
        	.addNode("Management").getLastChild()
        		.addNode("Hassan El Manfalouty")
        		.getParent()
        	;
        
        treeBuilder.addEvent(EventName.COLLAPSE, new Runnable() {
				@Override
				public void run() {
					System.out.println("OrganigramView.init().new Runnable() {...}.run() : COLLAPSED");
				}
			})
        	.addEvent(EventName.SELECT, new Runnable() {
    			@Override
    			public void run() {
    				System.out.println("OrganigramView.init().new Runnable() {...}.run() : SELECTED");
    			}
    		})
        	.addEvent(EventName.EXPAND, new Runnable() {
    			@Override
    			public void run() {
    				System.out.println("OrganigramView.init().new Runnable() {...}.run() : EXPANDED");
    			}
    		})
        	.addEvent(EventName.DRAG_DROP, new Runnable() {
    			@Override
    			public void run() {
    				System.out.println("OrganigramView.init().new Runnable() {...}.run() : DRAG DROP");
    			}
    		})
        	.addEvent(EventName.CONTEXT_MENU, new Runnable() {
    			@Override
    			public void run() {
    				System.out.println("OrganigramView.init().new Runnable() {...}.run() : CONTEXT MENU");
    			}
    		})
        	;
         
        MenuItemBuilder addMenuItemBuilder = __inject__(MenuItemBuilder.class);
        addMenuItemBuilder.setCommandableName("Ajouter").setCommandableIcon(Icon.PLUS).addCommandableEvent(EventName.CLICK,"PF('addDialog').show(); return false;");
        
        MenuItemBuilder removeMenuItemBuilder = __inject__(MenuItemBuilder.class);
        removeMenuItemBuilder.setCommandableName("Supprimer").setCommandableIcon(Icon.REMOVE);
        removeMenuItemBuilder.getCommandable().getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).addTryRunRunnables(new Runnable() {
			@Override
			public void run() {
				System.out.println("OrganigramView3.init().new Runnable() {...}.run() DELETE");
				// re-evaluate selection - might be a differenct object instance if viewstate serialization is enabled
		    	OrganigramNode selection = (OrganigramNode) tree.getRuntimeSelection();
		    	OrganigramNode currentSelection = OrganigramHelper.findTreeNode((OrganigramNode) tree.getProperties().getRoot(), selection);
		        currentSelection.getParent().getChildren().remove(currentSelection);
			}
		});
        removeMenuItemBuilder.getCommandable().setGetByIdentifierExpressionLanguageFormat("organigramView3.tree.menu.getCommandableByIdentifier('%s')");
        removeMenuItemBuilder.getCommandable().addUpdatables(treeBuilder);
        
        treeBuilder.getMenu(Boolean.TRUE).addItems(addMenuItemBuilder,removeMenuItemBuilder);

        tree = treeBuilder.execute().getOutput();
        
        //tree.getRoot().setTargetModel(__inject__(OrganigramNodeBuilder.class).setHierarchyNode(treeBuilder.getRoot().getHierarchyNode()).execute().getOutput());
        
        commandable = __inject__(CommandableBuilder.class).setName("Ajouter").setIcon(Icon.PLUS)
        		.addCommandFunctionTryRunRunnable(new Runnable() {
					
					@Override
					public void run() {
						// re-evaluate selection - might be a differenct object instance if viewstate serialization is enabled
				    	OrganigramNode selection = (OrganigramNode) tree.getRuntimeSelection();
				        OrganigramNode currentSelection = OrganigramHelper.findTreeNode((OrganigramNode) tree.getProperties().getRoot(), selection);
				 
				        __inject__(OrganigramNodeBuilder.class).setHierarchyNode(__inject__(HierarchyNode.class).setData(name)).setParent(currentSelection).execute().getOutput();
				        name = null;
				        System.out.println("OrganigramView3.add() : "+name+" ADDED");
						
					}
				})
        		.addUpdatables(tree,"@(.addDialog)")
        		.execute().getOutput();
        
        commandable.getProperties().setOnComplete("PF('addDialog').hide();");
        
    }
  
}