package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.cyk.utility.__kernel__.identifier.resource.UniformResourceIdentifierHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.session.SessionManager;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.user.interface_.message.MessageRenderer;
import org.cyk.utility.__kernel__.user.interface_.message.RenderType;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.window.WindowBuilder;
import org.cyk.utility.client.controller.event.EventBuilder;
import org.cyk.utility.client.controller.event.EventName;
import org.cyk.utility.client.controller.web.WebController;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractFilterController;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.DataTable;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.Button;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.menu.MenuItem;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.panel.OutputPanel;
import org.cyk.utility.client.controller.web.jsf.primefaces.tag.BlockUI;

import lombok.Getter;
import lombok.Setter;

public abstract class AbstractPageContainerManagedImpl extends org.cyk.utility.client.controller.web.jsf.page.AbstractPageContainerManagedImpl implements PageContainerManaged,Serializable {
	private static final long serialVersionUID = 1L;
	
	@Getter @Setter protected BlockUI blockUI;
	@Getter @Setter protected OutputPanel contentOutputPanel = OutputPanel.build(OutputPanel.FIELD_DEFERRED,Boolean.FALSE);
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		blockUI = new BlockUI();
		blockUI.getProperties().setBlock("outputPanel");
		blockUI.getProperties().setTrigger("initialisePageContent");
		blockUI.getProperties().setBlocked(Boolean.TRUE);
		
		showOnShowNotificationMessage();
	}
	
	protected static Object buildOnShowNotificationMessageIdentifier(Class<?> pageClass) {
		if(pageClass == null)
			return null;
		return pageClass+".onShowMessage";
	}
	
	protected static void writeOnShowNotificationMessage(String message,Class<?> pageClass) {
		SessionManager.getInstance().writeAttribute(buildOnShowNotificationMessageIdentifier(pageClass),message);
	}
	
	protected void showOnShowNotificationMessage() {
		Object identifier = buildOnShowNotificationMessageIdentifier(getClass());
		String message = StringHelper.get(SessionManager.getInstance().readAttribute(identifier));
		if(message != null) {
			MessageRenderer.getInstance().render(message, RenderType.GROWL);
			SessionManager.getInstance().removeAttribute(identifier);
		}
	}
	
	@Override
	protected WindowBuilder __getWindowBuilder__(List<String> subDurations) {
		WindowBuilder window =  super.__getWindowBuilder__(subDurations);
		CommandableBuilder commandable = window.getDialog(Boolean.TRUE).getOkCommandable(Boolean.TRUE);
		__processWindowDialogOkCommandable__(window, commandable);
		return window;
	}
	
	protected void __processWindowDialogOkCommandable__(WindowBuilder window,CommandableBuilder commandable) {
		String widgetVar = (String) window.getDialog(Boolean.TRUE).getOutputProperties().getWidgetVar();		
		String script = __injectPrimefacesHelper__().getScriptInstructionHide(widgetVar);		
		String url = __processWindowDialogOkCommandableGetUrl__(window, commandable);
		if(StringHelper.isBlank(url) && commandable.getUniformResourceIdentifier()!=null)
			url = UniformResourceIdentifierHelper.build(commandable.getUniformResourceIdentifier());			
		if(StringHelper.isNotBlank(url))
			script = script + __injectJavaServerFacesHelper__().getScriptInstructionGoToUrlIfMessageMaximumSeverityIsInfo(url);				
		EventBuilder event = __inject__(EventBuilder.class).setName(EventName.CLICK).addScriptInstructions(script);
		commandable.addEvents(event);
	}
	
	protected String __processWindowDialogOkCommandableGetUrl__(WindowBuilder window,CommandableBuilder commandable) {
		String url = null;
		Object request = __getRequest__();
		if(request instanceof HttpServletRequest)
			url = ((HttpServletRequest)request).getRequestURL().toString();
		return url;
	}
	
	/**/
	
	protected static final PrimefacesHelper __injectPrimefacesHelper__() {
		return __inject__(PrimefacesHelper.class);
	}
	
	public static void addDataTableRecordMenuItemByArgumentsNavigateToViewWithFilterController(Class<?> pageClass,DataTable dataTable,AbstractFilterController filterController,String menuItemOutcome,String menuItemValue,String menuItemIcon
			,Map<String,List<String>> parameters) {
		String filterControllerSessionIdentifier = SessionManager.getInstance().generateIdentifier(pageClass);
		SessionManager.getInstance().writeAttribute(filterControllerSessionIdentifier, filterController);
		if(parameters == null)
			parameters = new HashMap<String, List<String>>();
		parameters.put(AbstractFilterController.SESSION_IDENTIFIER_REQUEST_PARAMETER_NAME, List.of(filterControllerSessionIdentifier));
		dataTable.addRecordMenuItemByArgumentsNavigateToView(null,menuItemOutcome,MenuItem.FIELD_PARAMETERS,parameters, MenuItem.FIELD_VALUE,menuItemValue,MenuItem.FIELD_ICON,menuItemIcon);
	}
	
	public static void addDataTableRecordMenuItemDetailsByArgumentsNavigateToViewWithFilterController(Class<?> pageClass,DataTable dataTable,AbstractFilterController filterController,String menuItemOutcome,Map<String,List<String>> parameters) {
		addDataTableRecordMenuItemByArgumentsNavigateToViewWithFilterController(pageClass, dataTable, filterController, menuItemOutcome, "Détails", "fa fa-eye",parameters);
	}
	
	public static void addDataTableRecordMenuItemDetailsByArgumentsNavigateToViewWithFilterController(Class<?> pageClass,DataTable dataTable,AbstractFilterController filterController,String menuItemOutcome) {
		addDataTableRecordMenuItemDetailsByArgumentsNavigateToViewWithFilterController(pageClass, dataTable, filterController, menuItemOutcome, null);
	}
	
	public static Button buildBackButtonWithFilterController(String buttonOutcome,String buttonValue,String buttonIcon) {
		return Button.build(Button.FIELD_VALUE,buttonValue,Button.FIELD_ICON,buttonIcon,Button.FIELD_OUTCOME,buttonOutcome
				,Button.FIELD_PARAMETERS,Map.of(AbstractFilterController.SESSION_IDENTIFIER_REQUEST_PARAMETER_NAME,WebController.getInstance().getRequestParameter(AbstractFilterController.SESSION_IDENTIFIER_REQUEST_PARAMETER_NAME)));
	}
	
	public static Button buildBackButtonWithFilterController(String buttonOutcome) {
		return buildBackButtonWithFilterController(buttonOutcome, BACK_BUTTON_VALUE, BACK_BUTTON_ICON);
	}
	
	public static <FILTER_CONTROLLER extends AbstractFilterController> FILTER_CONTROLLER getFilterControllerFromSessionOrInstantiateIfNull(Class<FILTER_CONTROLLER> filterControllerClass,FILTER_CONTROLLER filterController) {
		return AbstractFilterController.getFromSessionOrInstantiateIfNull(filterControllerClass, filterController);
	}
	
	public static <FILTER_CONTROLLER extends AbstractFilterController> FILTER_CONTROLLER getFilterControllerFromSessionOrInstantiateIfNull(Class<FILTER_CONTROLLER> filterControllerClass) {
		return getFilterControllerFromSessionOrInstantiateIfNull(filterControllerClass, null);
	}
	
	public static String BACK_BUTTON_VALUE = "Retour à la liste";
	public static String BACK_BUTTON_ICON = "fa fa-arrow-left";
	//private static final String FILTER_CONTROLLER_SESSION_IDENTIFIER_REQUEST_PARAMETER_NAME = "fc_sid";
}