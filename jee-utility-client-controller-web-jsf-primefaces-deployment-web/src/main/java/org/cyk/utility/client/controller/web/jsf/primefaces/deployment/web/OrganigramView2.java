package org.cyk.utility.client.controller.web.jsf.primefaces.deployment.web;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.random.RandomHelper;
import org.primefaces.component.organigram.OrganigramHelper;
import org.primefaces.event.organigram.OrganigramNodeCollapseEvent;
import org.primefaces.event.organigram.OrganigramNodeDragDropEvent;
import org.primefaces.event.organigram.OrganigramNodeExpandEvent;
import org.primefaces.event.organigram.OrganigramNodeSelectEvent;
import org.primefaces.model.DefaultOrganigramNode;
import org.primefaces.model.OrganigramNode;

@ManagedBean
@SessionScoped
public class OrganigramView2 implements Serializable {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OrganigramNode rootNode;
    private OrganigramNode selection;
 
    private boolean zoom = false;
    private String style = "width: 800px";
    private int leafNodeConnectorHeight = 0;
    private boolean autoScrollToSelection = false;
 
    private String employeeName;
 
    @PostConstruct
    public void init() {
        selection = new DefaultOrganigramNode(null, "Ridvan Agar", null);
 
        rootNode = new DefaultOrganigramNode("CommerceBay GmbH", null);
        rootNode.setCollapsible(false);
        rootNode.setDroppable(true);
        rootNode.setRowKey(DependencyInjection.inject(RandomHelper.class).getAlphabetic(3));
 
 
        OrganigramNode softwareDevelopment = addDivision(rootNode, "Software Development", "Ridvan Agar");
 
        OrganigramNode teamJavaEE = addDivision(softwareDevelopment, "Team JavaEE");
        addDivision(teamJavaEE, "JSF", "Thomas Andraschko");
        addDivision(teamJavaEE, "Backend", "Marie Louise");
        
        OrganigramNode teamMobile = addDivision(softwareDevelopment, "Team Mobile");
        addDivision(teamMobile, "Android", "Andy Ruby");
        addDivision(teamMobile, "iOS", "Stevan Jobs");
 	
        addDivision(rootNode, "Managed Services", "Thorsten Schultze", "Sandra Becker");
 
        OrganigramNode marketing = addDivision(rootNode, "Marketing");
        addDivision(marketing, "Social Media", "Ali Mente", "Lisa Boehm");
        addDivision(marketing, "Press", "Michael Gmeiner", "Hans Peter");
 
        addDivision(rootNode, "Management", "Hassan El Manfalouty");
        
    }
 
    protected OrganigramNode addDivision(OrganigramNode parent, String name, String... employees) {
        OrganigramNode divisionNode = new DefaultOrganigramNode( name, parent);
        divisionNode.setDroppable(true);
        divisionNode.setDraggable(true);
        divisionNode.setSelectable(true);
        divisionNode.setRowKey(DependencyInjection.inject(RandomHelper.class).getAlphabetic(3));
        
        if (employees != null) {
            for (String employee : employees) {
                OrganigramNode employeeNode = new DefaultOrganigramNode(employee, divisionNode);
                employeeNode.setDraggable(true);
                employeeNode.setSelectable(true);
                employeeNode.setRowKey(DependencyInjection.inject(RandomHelper.class).getAlphabetic(3));
            }
        }
 
        return divisionNode;
    }
 
    public void nodeDragDropListener(OrganigramNodeDragDropEvent event) {
    	System.out.println("OrganigramView2.nodeDragDropListener()");
        FacesMessage message = new FacesMessage();
        message.setSummary("Node '" + event.getOrganigramNode().getData() + "' moved from " + event.getSourceOrganigramNode().getData() + " to '" + event.getTargetOrganigramNode().getData() + "'");
        message.setSeverity(FacesMessage.SEVERITY_INFO);
 
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
 
    public void nodeSelectListener(OrganigramNodeSelectEvent event) {
    	System.out.println("OrganigramView2.nodeSelectListener()");
        FacesMessage message = new FacesMessage();
        message.setSummary("Node '" + event.getOrganigramNode().getData() + "' selected.");
        message.setSeverity(FacesMessage.SEVERITY_INFO);
 
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
 
    public void nodeCollapseListener(OrganigramNodeCollapseEvent event) {
    	System.out.println("OrganigramView2.nodeCollapseListener()");
        FacesMessage message = new FacesMessage();
        message.setSummary("Node '" + event.getOrganigramNode().getData() + "' collapsed.");
        message.setSeverity(FacesMessage.SEVERITY_INFO);
 
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
 
    public void nodeExpandListener(OrganigramNodeExpandEvent event) {
    	System.out.println("OrganigramView2.nodeExpandListener()");
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