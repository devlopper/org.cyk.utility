package org.cyk.utility.client.controller.web.jsf.primefaces.deployment.web;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.primefaces.component.organigram.OrganigramHelper;
import org.primefaces.event.organigram.OrganigramNodeCollapseEvent;
import org.primefaces.event.organigram.OrganigramNodeDragDropEvent;
import org.primefaces.event.organigram.OrganigramNodeExpandEvent;
import org.primefaces.event.organigram.OrganigramNodeSelectEvent;
import org.primefaces.model.DefaultOrganigramNode;
import org.primefaces.model.OrganigramNode;

@ManagedBean
//@ViewScoped
@SessionScoped
public class OrganigramView extends AbstractObject implements Serializable {
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
 
        rootNode = new DefaultOrganigramNode("root", "CommerceBay GmbH", null);
        rootNode.setCollapsible(false);
        rootNode.setDroppable(true);
 
 
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
        OrganigramNode divisionNode = new DefaultOrganigramNode("division", name, parent);
        divisionNode.setDroppable(true);
        divisionNode.setDraggable(true);
        divisionNode.setSelectable(true);
 
        if (employees != null) {
            for (String employee : employees) {
                OrganigramNode employeeNode = new DefaultOrganigramNode("employee", employee, divisionNode);
                employeeNode.setDraggable(true);
                employeeNode.setSelectable(true);
            }
        }
 
        return divisionNode;
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
        currentSelection.getParent().getChildren().remove(currentSelection);
        //setMessage(currentSelection.getData() + " will entfernt werden.", FacesMessage.SEVERITY_INFO);
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