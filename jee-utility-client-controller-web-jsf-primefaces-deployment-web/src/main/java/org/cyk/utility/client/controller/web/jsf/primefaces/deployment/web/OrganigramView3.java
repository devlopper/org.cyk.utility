package org.cyk.utility.client.controller.web.jsf.primefaces.deployment.web;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.client.controller.component.command.CommandFunction;
import org.cyk.utility.client.controller.component.tree.Tree;
import org.cyk.utility.client.controller.component.tree.TreeBuilder;
import org.cyk.utility.client.controller.event.EventBuilder;
import org.cyk.utility.client.controller.event.EventName;
import org.cyk.utility.client.controller.web.jsf.primefaces.component.OrganigramNodeBuilder;
import org.cyk.utility.hierarchy.HierarchyNode;
import org.primefaces.component.organigram.OrganigramHelper;
import org.primefaces.event.organigram.OrganigramNodeCollapseEvent;
import org.primefaces.event.organigram.OrganigramNodeDragDropEvent;
import org.primefaces.event.organigram.OrganigramNodeExpandEvent;
import org.primefaces.event.organigram.OrganigramNodeSelectEvent;
import org.primefaces.model.DefaultOrganigramNode;
import org.primefaces.model.OrganigramNode;

import lombok.Getter;
import lombok.Setter;

@ManagedBean
//@ViewScoped
@SessionScoped
public class OrganigramView3 extends AbstractObject implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Getter @Setter private Tree tree;
    
	private OrganigramNode rootNode;
    private OrganigramNode selection;
 
    private boolean zoom = false;
    private String style = "width: 1200px";
    private int leafNodeConnectorHeight = 0;
    private boolean autoScrollToSelection = false;
 
    private String employeeName;
 
    @PostConstruct
    public void init() {
        selection = new DefaultOrganigramNode(null, "Ridvan Agar", null);
        
        HierarchyNode hierarchyNode = __inject__(HierarchyNode.class).setData("CommerceBay GmbH");
        hierarchyNode.setIsCollapsible(Boolean.FALSE);
        
        hierarchyNode
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
        
        rootNode = __inject__(OrganigramNodeBuilder.class).setHierarchyNode(hierarchyNode).execute().getOutput();
        
        tree = __inject__(TreeBuilder.class)
        	.addEvent(EventName.COLLAPSE, new Runnable() {
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
        	.execute().getOutput();
         
        tree.getProperties().setRoot(rootNode);
        
    }
 
    public void nodeDragDropListener(OrganigramNodeDragDropEvent event) {
        FacesMessage message = new FacesMessage();
        message.setSummary("Node '" + event.getOrganigramNode().getData() + "' moved from " + event.getSourceOrganigramNode().getData() + " to '" + event.getTargetOrganigramNode().getData() + "'");
        message.setSeverity(FacesMessage.SEVERITY_INFO);
 
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
 
    public void nodeSelectListener(OrganigramNodeSelectEvent event) {
        FacesMessage message = new FacesMessage();
        message.setSummary("Node '" + event.getOrganigramNode().getData() + "' selected.");
        message.setSeverity(FacesMessage.SEVERITY_INFO);
 
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
 
    public void nodeCollapseListener(OrganigramNodeCollapseEvent event) {
        FacesMessage message = new FacesMessage();
        message.setSummary("Node '" + event.getOrganigramNode().getData() + "' collapsed.");
        message.setSeverity(FacesMessage.SEVERITY_INFO);
 
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
 
    public void nodeExpandListener(OrganigramNodeExpandEvent event) {
        FacesMessage message = new FacesMessage();
        message.setSummary("Node '" + event.getOrganigramNode().getData() + "' expanded.");
        message.setSeverity(FacesMessage.SEVERITY_INFO);
 
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
 
    public void removeDivision() {
        // re-evaluate selection - might be a differenct object instance if viewstate serialization is enabled
        OrganigramNode currentSelection = OrganigramHelper.findTreeNode(rootNode, selection);
        setMessage(currentSelection.getData() + " will entfernt werden.", FacesMessage.SEVERITY_INFO);
    }
 
    public void removeEmployee() {
        // re-evaluate selection - might be a differenct object instance if viewstate serialization is enabled
        OrganigramNode currentSelection = OrganigramHelper.findTreeNode(rootNode, selection);
        currentSelection.getParent().getChildren().remove(currentSelection);
    }
 
    public void addEmployee() {
        // re-evaluate selection - might be a differenct object instance if viewstate serialization is enabled
        OrganigramNode currentSelection = OrganigramHelper.findTreeNode(rootNode, selection);
 
        OrganigramNode employee = new DefaultOrganigramNode("employee", employeeName, currentSelection);
        employee.setDraggable(true);
        employee.setSelectable(true);
    }
 
    private void setMessage(String msg, FacesMessage.Severity severity) {
        FacesMessage message = new FacesMessage();
        message.setSummary(msg);
        message.setSeverity(severity);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
 
    public OrganigramNode getRootNode() {
        return rootNode;
    }
 
    public void setRootNode(OrganigramNode rootNode) {
        this.rootNode = rootNode;
    }
 
    public OrganigramNode getSelection() {
        return selection;
    }
 
    public void setSelection(OrganigramNode selection) {
        this.selection = selection;
    }
 
    public boolean isZoom() {
        return zoom;
    }
 
    public void setZoom(boolean zoom) {
        this.zoom = zoom;
    }
 
    public String getEmployeeName() {
        return employeeName;
    }
 
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
 
    public String getStyle() {
        return style;
    }
 
    public void setStyle(String style) {
        this.style = style;
    }
 
    public int getLeafNodeConnectorHeight() {
        return leafNodeConnectorHeight;
    }
 
    public void setLeafNodeConnectorHeight(int leafNodeConnectorHeight) {
        this.leafNodeConnectorHeight = leafNodeConnectorHeight;
    }
 
    public boolean isAutoScrollToSelection() {
        return autoScrollToSelection;
    }
 
    public void setAutoScrollToSelection(boolean autoScrollToSelection) {
        this.autoScrollToSelection = autoScrollToSelection;
    }
}