package org.cyk.utility.common.userinterface.container.window;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.Properties;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.ListenerHelper;
import org.cyk.utility.common.helper.MethodHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.helper.UniformResourceLocatorHelper;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.DeviceType;
import org.cyk.utility.common.userinterface.Request;
import org.cyk.utility.common.userinterface.RequestHelper;
import org.cyk.utility.common.userinterface.collection.DataTable;
import org.cyk.utility.common.userinterface.command.Menu;
import org.cyk.utility.common.userinterface.container.Container;
import org.cyk.utility.common.userinterface.container.form.Form;
import org.cyk.utility.common.userinterface.hierarchy.Hierarchy;
import org.cyk.utility.common.userinterface.panel.ConfirmationDialog;
import org.cyk.utility.common.userinterface.panel.NotificationDialog;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Window extends Container implements Serializable {
	private static final long serialVersionUID = 1L;

	static {
		ClassHelper.getInstance().map(Listener.class, Listener.Adapter.Default.class, Boolean.FALSE);
	}
	
	public static String FOOTER = "CYK";
	
	protected DeviceType deviceType;
	protected ConfirmationDialog confirmationDialog = new ConfirmationDialog();
	protected Request requestComponent = new Request();
	protected NotificationDialog notificationDialog = new NotificationDialog();
	/*
	protected Constant.Action action;
	protected Class<?> actionOnClass;
	*/
	protected Collection<Object> actionOnClassInstanceIdentifiers=new ArrayList<>();
	protected Collection<Object> actionOnClassInstances;
	protected Object actionKey;
	
	protected Form form;
	protected DataTable dataTable;
	protected Hierarchy hierarchy;
	
	/**/
	
	@Override
	protected void initialisation() {
		notificationDialog.getPropertiesMap();
		super.initialisation();
		getPropertiesMap().setFullPage(Boolean.TRUE);
		
		getPropertiesMap().setAction(getParameterAsEnum(Constant.Action.class,UniformResourceLocatorHelper.QueryParameter.Name.ACTION));
		getPropertiesMap().setActionOnClass(getParameterAsClass(UniformResourceLocatorHelper.QueryParameter.Name.CLASS));
		Object identifiable = getParameter(UniformResourceLocatorHelper.QueryParameter.Name.IDENTIFIABLE);
		if(identifiable!=null)
			actionOnClassInstanceIdentifiers.add(identifiable);
		getPropertiesMap().setInstance(identifiable);
		
		if(Boolean.TRUE.equals(getIsAutomaticallySetForm()))
			__setForm__();
		
		if(Boolean.TRUE.equals(getIsAutomaticallySetDataTable()))
			__setDataTable__();
		
		if(Boolean.TRUE.equals(getIsAutomaticallySetHierarchy()))
			__setHierarchy__();
		
		_setPropertyTitle(computePropertyTitle());
		_setPropertyUsingOutputText(Properties.FOOTER, FOOTER);
		
		getPropertiesMap().setLayout(createLayout());
		getPropertiesMap().setMainMenu(createMainMenu());
		getPropertiesMap().setContextMenu(createContextMenu());
		//System.out.println("Window.initialisation() : "+getPropertiesMap());
		//System.out.println("Window.initialisation() : "+getActionOnClassInstances());
		logTrace("Properties={} , Parameters : ActionOnClassInstanceIdentifiers={}",getPropertiesMap(),actionOnClassInstanceIdentifiers);
	}
	
	protected String getPropertyTitleIdentifier(){
		return null;
	}
	
	protected String computePropertyTitle(){
		String title = null;
		String identifier = getPropertyTitleIdentifier();
		if(StringHelper.getInstance().isBlank(identifier)){
			if(getPropertiesMap().getAction()!=null && getPropertiesMap().getActionOnClass()!=null)
				title =  StringHelper.getInstance().getPhrase((Constant.Action)getPropertiesMap().getAction(),Boolean.FALSE, (Class<?>)getPropertiesMap().getActionOnClass());
		}else
			title =  StringHelper.getInstance().get(identifier, new Object[]{});
		
		if(StringHelper.getInstance().isBlank(title))
			title = "UNKNOWN TITLE";
		else {
			if(CollectionHelper.getInstance().isNotEmpty(getActionOnClassInstances())){
				Object id = InstanceHelper.getInstance().getIdentifier(getActionOnClassInstances().iterator().next());
				if(id != null)
					title  = title + Constant.CHARACTER_VERTICAL_BAR + id.toString();
			}
		}
		return title;
	}
	
	public Collection<Object> getActionOnClassInstances(){
		if(actionOnClassInstances==null){
			actionOnClassInstances = new ArrayList<>();
			if(getPropertiesMap().getActionOnClass()!=null){
				if(ArrayUtils.contains(new Constant.Action[]{Constant.Action.CREATE,Constant.Action.LOGIN,Constant.Action.LOGOUT}, getPropertiesMap().getAction())){
					actionOnClassInstances.add(ClassHelper.getInstance().instanciateOne((Class<?>)getPropertiesMap().getActionOnClass()));
				}else{
					if(CollectionHelper.getInstance().isNotEmpty(actionOnClassInstanceIdentifiers))
						for(Object identifier : actionOnClassInstanceIdentifiers){
							Object instance = InstanceHelper.getInstance().getByIdentifier((Class<?>)getPropertiesMap().getActionOnClass(), identifier,ClassHelper.Listener.IdentifierType.SYSTEM);
							if(instance!=null)
								actionOnClassInstances.add(instance);
						}	
				}
			}
		}
		return actionOnClassInstances;
	}
	
	protected Boolean getIsAutomaticallySetForm(){
		return getPropertiesMap().getAction()!=null && getPropertiesMap().getActionOnClass()!=null && getActionOnClassInstances().size() > 0;
	}
	
	protected Boolean getIsAutomaticallySetDataTable(){
		return Boolean.FALSE;
	}
	
	protected Boolean getIsAutomaticallySetHierarchy(){
		return Boolean.FALSE;
	}
	
	protected void __setForm__(){
		form = buildForm();
	}
	
	protected void __setDataTable__(){
		dataTable = buildDataTable();
	}
	
	protected void __setHierarchy__(){
		hierarchy = buildHierarchy();
	}
	
	protected Object getParameter(String name) {
		return ClassHelper.getInstance().instanciateOne(Listener.class).getParameter(name);
	}
	
	protected <T extends Enum<?>> T getParameterAsEnum(Class<T> enumClass, String name) {
		return ClassHelper.getInstance().instanciateOne(Listener.class).getParameterAsEnum(enumClass, name);
	}
	
	protected Class<?> getParameterAsClass(String name) {
		return ClassHelper.getInstance().instanciateOne(Listener.class).getParameterAsClass(name);
	}
	
	protected Object getParameterAsString(String name) {
		return ClassHelper.getInstance().instanciateOne(Listener.class).getParameterAsString(name);
	}
	
	public void listenBeforeRender(){}
	
	protected Object createLayout(){
		Object layout = ListenerHelper.getInstance().listenObject(Listener.MAP.get(getClass()), Listener.METHOD_NAME_CREATE_LAYOUT
				,MethodHelper.Method.Parameter.buildArray(Window.class,this));
		if(layout == null)
			layout = ListenerHelper.getInstance().listenObject(Listener.COLLECTION, Listener.METHOD_NAME_CREATE_LAYOUT
					,MethodHelper.Method.Parameter.buildArray(Window.class,this));
		return layout;
	}
	
	protected Menu createMainMenu(){
		return Menu.build(this, Menu.Type.MAIN,Menu.RenderType.BAR);
	}
	
	protected Menu createContextMenu(){
		return Menu.build(this, Menu.Type.CONTEXT,Menu.RenderType.PANEL);
	}
	
	@Override
	protected Properties instanciateProperties() {
		return super.instanciateProperties().setLayoutCardinalPointCenterSouthRendered(Boolean.FALSE);
	}
	
	/* builders */
	
	protected Form buildForm(){
		@SuppressWarnings("unchecked")
		Class<Form> formMasterClass = (Class<Form>) InstanceHelper.getInstance().getIfNotNullElseDefault(getFormMasterClass(),Form.class);
		Form form = Component.get(this,formMasterClass,Form.class);
		form.build();
		return form;
	}
	
	protected Class<? extends Form> getFormMasterClass(){
		return Form.class;
	}
	
	protected DataTable buildDataTable(){
		@SuppressWarnings("unchecked")
		Class<DataTable> dataTableClass = (Class<DataTable>) InstanceHelper.getInstance().getIfNotNullElseDefault(getDataTableClass(),DataTable.class);
		DataTable dataTable = Component.get(this,dataTableClass,DataTable.class);
		dataTable.build();
		return dataTable;
	}
	
	protected Class<? extends DataTable> getDataTableClass(){
		return DataTable.class;
	}
	
	protected Hierarchy buildHierarchy(){
		@SuppressWarnings("unchecked")
		Class<Hierarchy> hierarchyClass = (Class<Hierarchy>) InstanceHelper.getInstance().getIfNotNullElseDefault(getHierarchyClass(),Hierarchy.class);
		Hierarchy hierarchy = Component.get(this,hierarchyClass,Hierarchy.class);
		hierarchy.build();
		return hierarchy;
	}
	
	protected Class<? extends Hierarchy> getHierarchyClass(){
		return Hierarchy.class;
	}
	
	/**/
	
	public static interface BuilderBase<OUTPUT extends Window> extends Component.BuilderBase<OUTPUT> {

		public static class Adapter<OUTPUT extends Window> extends Component.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}

			/**/

			public static class Default<OUTPUT extends Window> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
			}
		}
	}
	
	public static interface Builder extends BuilderBase<Window> {

		public static class Adapter extends BuilderBase.Adapter.Default<Window> implements Builder, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter() {
				super(Window.class);
			}

			/**/

			public static class Default extends Builder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

			}
		}
	}
	
	/**/
	
	public static interface Listener {
		
		Map<Class<? extends Window>,Collection<Listener>> MAP = new HashMap<Class<? extends Window>, Collection<Listener>>();
		Collection<Listener> COLLECTION = new ArrayList<Window.Listener>();
		
		String METHOD_NAME_CREATE_LAYOUT = "createLayout";
		Object createLayout(Window window);
		
		Object getParameter(String name);
		Object getParameterAsString(String name);
		<T extends Enum<?>> T getParameterAsEnum(Class<T> enumClass,String name);
		Class<?> getParameterAsClass(String name);
		
		/**/
		
		public static class Adapter extends AbstractBean implements Listener , Serializable {
			private static final long serialVersionUID = 1L;

			/**/
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

				@Override
				public Object getParameter(String name) {
					return RequestHelper.getInstance().getParameter(name);
				}
				
				@Override
				public <T extends Enum<?>> T getParameterAsEnum(Class<T> enumClass, String name) {
					return RequestHelper.getInstance().getParameterAsEnum(enumClass, name);
				}
				
				@Override
				public Object getParameterAsString(String name) {
					return RequestHelper.getInstance().getParameterAsString(name);
				}
				
				@Override
				public Class<?> getParameterAsClass(String name) {
					return RequestHelper.getInstance().getParameterAsClass(name);
				}
				
			}
			
			/**/
			
			@Override
			public Object createLayout(Window window) {
				return null;
			}
			
			@Override
			public Object getParameter(String name) {
				return null;
			}
			
			@Override
			public <T extends Enum<?>> T getParameterAsEnum(Class<T> enumClass, String name) {
				return null;
			}
			
			@Override
			public Object getParameterAsString(String name) {
				return null;
			}
			
			@Override
			public Class<?> getParameterAsClass(String name) {
				return null;
			}
		}
	}
}
