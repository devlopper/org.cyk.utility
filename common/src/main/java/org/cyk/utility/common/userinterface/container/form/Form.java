package org.cyk.utility.common.userinterface.container.form;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.Constant.Action;
import org.cyk.utility.common.Properties;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.helper.UniformResourceLocatorHelper;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.ContentType;
import org.cyk.utility.common.userinterface.Layout;
import org.cyk.utility.common.userinterface.RequestHelper;
import org.cyk.utility.common.userinterface.collection.Cell;
import org.cyk.utility.common.userinterface.collection.DataTable;
import org.cyk.utility.common.userinterface.command.Command;
import org.cyk.utility.common.userinterface.command.Menu;
import org.cyk.utility.common.userinterface.container.window.Window;
import org.cyk.utility.common.userinterface.input.Input;
import org.cyk.utility.common.userinterface.input.InputFile;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Form extends FormContainer implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//private Constant.Action action;
	private Boolean editable;
	private Object object;
	private FormDetail detail;
	
	private Menu menu = new Menu();
	private Command submitCommand = new Command();
	
	/**/

	public Form(Component parent,Object object,Constant.Action action,Class<? extends SubmitCommandActionAdapter> submitCommandActionAdapterClass) {
		setParent(parent);
		this.object = object;
		menu.addOneChild(submitCommand);
		submitCommand.setLabelFromIdentifier("userinterface.command.submit");
		_setPropertyAction(action);
		setSubmitCommandActionAdapterClass(submitCommandActionAdapterClass);
		instanciateDetail();
	}
	
	public Form(Component parent,Object object,Constant.Action action) {
		this(parent,object,action,ClassHelper.getInstance().getMapping(SubmitCommandActionAdapter.class));
	}
	
	public Form() {
		this(null,null,null);
	}
			
	@Override
	protected Class<?>[] getPropertyStyleClassClasses() {
		return ArrayUtils.addAll(new Class<?>[]{FormContainer.class}, super.getPropertyStyleClassClasses());
	}
	
	public Form addComponentOnSubmit(Component component){
		String identifier = (String)component.getPropertiesMap().getIdentifier();
		getSubmitCommand().getPropertiesMap().addString(Properties.PROCESS, identifier);
		getSubmitCommand().getPropertiesMap().addString(Properties.UPDATE,identifier);
		return this;
	}
	
	public Form _setPropertyAction(Object value){
		super._setPropertyAction(value);
		Constant.Action action = (Action) value;
		if(editable==null && action!=null)
			setEditable(!Constant.Action.READ.equals(action) && !Constant.Action.DELETE.equals(action));
		submitCommand.getPropertiesMap().setRendered(!Constant.Action.READ.equals(action));
		if(Boolean.TRUE.equals(submitCommand.getPropertiesMap().getRendered()))
			submitCommand.setIsConfirmable(Constant.Action.DELETE.equals(action));
		
		FormDetail detail = getDetail();
		if(detail!=null){
			detail.getPropertiesMap().setAction(action);
		}
		return this;
	}
	
	@Override
	public Constant.Action _getPropertyAction() {
		return (Action) super._getPropertyAction();
	}
	
	public Form setSubmitCommandActionAdapterClass(Class<? extends SubmitCommandActionAdapter> submitCommandActionAdapterClass){
		if(submitCommandActionAdapterClass==null){
			
		}else{
			submitCommand.setActionFromClass(submitCommandActionAdapterClass).setIsConfirmable(Constant.Action.DELETE.equals(_getPropertyAction()));
			((SubmitCommandActionAdapter)submitCommand.getAction()).setForm(this);	
		}				
		return this;
	}
	
	/*protected Class<? extends SubmitCommandActionAdapter> getSubmitCommandActionAdapterClass(){
		return SubmitCommandActionAdapter.class;
	}*/
	
	@Override
	@SuppressWarnings("unchecked")
	public Component prepare() {
		__prepare__();
		getPropertiesMap().setIfNull(Properties.HEADER_RENDERED, Boolean.TRUE);
		getPropertiesMap().setIfNull(Properties.FOOTER_RENDERED, Constant.Action.isNotReadAndNotConsult((Constant.Action)getPropertiesMap().getAction()));
		if(getLabel()!=null && StringHelper.getInstance().isBlank((String)getLabel().getPropertiesMap().getValue()))
			setLabelFromIdentifier("userinterface.form.master.label");
		new CollectionHelper.Iterator.Adapter.Default<String>((Set<String>) getPropertiesMap().getFieldNamesSetFromRequestParameters()){
			private static final long serialVersionUID = 1L;

			protected void __executeForEach__(String fieldName) {
				Input<?> input = Form.this.getDetail().getInputByFieldName(fieldName);
				if(input==null)
					logWarning("getFieldNamesSetFromRequestParameters : input of field named <<{}>> is null",fieldName);
				else
					input.getPropertiesMap().setDisabled(Boolean.TRUE);
			};
		}.execute();
		return this;
	}
	
	protected void __prepare__(){
		__setFromRequestParameter__();
		__computeChanges__();
		getDetail().getLayout().setType(Layout.Type.ADAPTIVE);
		____add____();
	}
	
	protected void __computeChanges__(){
		//if(CollectionHelper.getInstance().isNotEmpty((Collection<?>)getPropertiesMap().getFieldNamesSetFromRequestParameters()))
		//	InstanceHelper.getInstance().computeChanges(object);
	}
	
	protected void __setFromRequestParameter__(){
		new CollectionHelper.Iterator.Adapter.Default<Class<?>>(getRequestParameterClasses()){
			private static final long serialVersionUID = 1L;
			@Override
			protected void __executeForEach__(Class<?> aClass) {
				setFromRequestParameter(aClass);
			}
		}.execute();
	}
	
	protected Collection<Class<?>> getRequestParameterClasses(){
		return ClassHelper.getInstance().getByFieldTypeIdentified(object.getClass());
	}
	
	public Form read(){
		if(detail!=null)
			detail.read();
		return this;
	}
	
	public Form write(){
		if(detail!=null)
			detail.write();
		return this;
	}
	
	public FormDetail instanciateDetail(Layout.Type layoutType){
		FormDetail detail = new FormDetail(this,layoutType);
		
		detail.getPropertiesMap().setAction(_getPropertyAction());
		
		addOneChild(this.detail = detail);
		addComponentOnSubmit(detail);
		return detail;
	}
	
	public FormDetail instanciateDetail(){
		return instanciateDetail(Layout.Type.DEFAULT);
	}
	
	public DataTable instanciateDataTable(Class<?> actionOnClass,Class<?> choiceValueClass,Cell.Listener cellListener,Boolean bottom,String...fieldNames){
		DataTable dataTable = new DataTable();
		dataTable.getPropertiesMap().setActionOnClass(actionOnClass);
		dataTable.getPropertiesMap().setChoiceValueClass(choiceValueClass);
		dataTable.getPropertiesMap().setCellListener(cellListener);
		dataTable.getPropertiesMap().setOnPrepareAddColumnAction(Boolean.TRUE);
		
		getDetail().addDataTable(dataTable,bottom);
		
		if(Constant.Action.isCreateOrUpdate( (Constant.Action)dataTable.getPropertiesMap().getAction() )){
			
		}else{
			dataTable.getPropertiesMap().setOnPrepareAddMenu(Boolean.TRUE);
			dataTable.getPropertiesMap().setOnPrepareAddMenuAddCommand(Boolean.TRUE);	
		}
		
		dataTable.addColumnsByFieldNames(fieldNames);
		return dataTable;
	}
	
	public DataTable instanciateDataTable(Class<?> actionOnClass,Class<?> choiceValueClass,Boolean bottom,String...fieldNames){
		return instanciateDataTable(null, null, (Cell.Listener)null,bottom);
	}
	
	public DataTable instanciateDataTable(){
		return instanciateDataTable(null, null,Boolean.TRUE);
	}
	
	/*public DataTable instanciateReadOnlyDataTable(Class<?> actionOnClass,Cell.Listener cellListener,Boolean bottom,String...fieldNames){
		DataTable dataTable = new DataTable();
		dataTable.getPropertiesMap().setActionOnClass(actionOnClass);
		dataTable.getPropertiesMap().setChoiceValueClass(choiceValueClass);
		dataTable.getPropertiesMap().setCellListener(cellListener);
		getDetail().addDataTable(dataTable,bottom);
		dataTable.addColumnsByFieldNames(fieldNames);
		return dataTable;
	}*/
	
	@Override
	public Form setLabelFromIdentifier(String identifier) {
		return (Form) super.setLabelFromIdentifier(identifier);
	}
			
	@Override
	public Form build() {
		Form master = (Form) super.build();
		if(CollectionHelper.getInstance().isNotEmpty(getDetail().getChildren()))
			for(Component component : getDetail().getChildren().getElements())
				if(component instanceof InputFile){
					submitCommand.getPropertiesMap().setAjax(Boolean.FALSE);//because of file upload
					break;
				}			
		return master;
	}
	
	public Form setFromRequestParameter(Class<?> aClass,String fieldName){
		FormDetail detail = getDetail();
		Field field = FieldHelper.getInstance().get(detail.getMaster().getObject().getClass(), fieldName);
		if(field!=null){
			Object value = RequestHelper.getInstance().getParameterAsInstance(aClass);
			@SuppressWarnings("unchecked")
			Set<String> set = (Set<String>) getPropertiesMap().getFieldNamesSetFromRequestParameters();
			if(set == null){
				set = new LinkedHashSet<>();
				getPropertiesMap().setFieldNamesSetFromRequestParameters(set);
			}
			if(value!=null){
				FieldHelper.getInstance().set(detail.getMaster().getObject(), value, field);
				set.add(fieldName);
			}
		}
		return this;
	}
	
	public Form setFromRequestParameter(Class<?> aClass){
		setFromRequestParameter(aClass, ClassHelper.getInstance().getVariableName(aClass));
		return this;
	} 
	
	/**/
	
	protected void ____add____(){
		____addCode____();
		____addName____();
		____addHierarchy____();
		____addType____();
	}
	
	protected void ____addCode____(){
		if(ClassHelper.getInstance().isIdentified(getObject().getClass()))
			____add____(ClassHelper.getInstance().getIdentifierFieldName(getObject().getClass()));
	}
	
	protected void ____addName____(){
		if(ClassHelper.getInstance().isNamedInBusiness(getObject().getClass()))
			____add____(ClassHelper.getInstance().getNameFieldName(getObject().getClass()));
	}
	
	protected void ____addHierarchy____(){
		if(ClassHelper.getInstance().isHierarchy(getObject().getClass()))
			____add____(ClassHelper.getInstance().getHierarchyFieldName(getObject().getClass()));
	}
	
	protected void ____addType____(){
		if(ClassHelper.getInstance().isTyped(getObject().getClass()))
			____add____(ClassHelper.getInstance().getTypeFieldName(getObject().getClass()));
	}
	
	/**/
	
	protected void ____add____(String fieldName){
		if(Boolean.TRUE.equals(Input.isinputable(getObject().getClass(), fieldName))){
			getDetail().setFieldsObjectFromMaster(FieldHelper.getInstance().getIsContainSeparator(fieldName) ? ____getFieldsObjectFromMaster____(fieldName) : null);
			getDetail().add(FieldHelper.getInstance().getLast(fieldName)).addBreak();
		}
	}

	protected String[] ____getFieldsObjectFromMaster____(String fieldName) {
		return FieldHelper.getInstance().getFieldNames(FieldHelper.getInstance().getBeforeLast(fieldName)).toArray(new String[]{});
	}
	
	/**/
	
	public static interface BuilderBase<OUTPUT extends Form> extends FormContainer.BuilderBase<OUTPUT> {

		public static class Adapter<OUTPUT extends Form> extends FormContainer.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}

			/**/

			public static class Default<OUTPUT extends Form> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
			}
		}
	}
	
	public static interface Builder extends BuilderBase<Form> {

		public static class Adapter extends BuilderBase.Adapter.Default<Form> implements Builder, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter() {
				super(Form.class);
			}

			/**/

			public static class Default extends Builder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

			}
		}
	}

	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class SubmitCommandActionAdapter extends Command.ActionAdapter implements Serializable{
		private static final long serialVersionUID = 1L;

		protected Form form;
		
		@Override
		protected Object __execute__() {
			try {
				form.write();
				act();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			return null;
		}
		
		protected void act(){
			switch(form._getPropertyAction()){
			case CREATE: create(); break;
			case READ: read(); break;
			case UPDATE: update(); break;
			case DELETE: delete(); break;
			case SELECT: select();break;
			case SEARCH: search();break;
			case CONSULT: consult();break;
			case LIST: list();break;
			case PRINT: print();break;
			case LOGIN: login();break;
			case LOGOUT: logout();break;
			}
		}
		
		protected void create(){
			InstanceHelper.getInstance().act(form._getPropertyAction(),form.getObject());
		}
		
		protected void read(){
			InstanceHelper.getInstance().act(form._getPropertyAction(),form.getObject());
		}
		
		protected void update(){
			InstanceHelper.getInstance().act(form._getPropertyAction(),form.getObject());
		}
		
		protected void delete(){
			InstanceHelper.getInstance().act(form._getPropertyAction(),form.getObject());
		}
		
		protected void select(){
			InstanceHelper.getInstance().act(form._getPropertyAction(),form.getObject());
		}
		
		protected void search(){
			InstanceHelper.getInstance().act(form._getPropertyAction(),form.getObject());
		}
		
		protected void list(){
			InstanceHelper.getInstance().act(form._getPropertyAction(),form.getObject());
		}
		
		protected void print(){
			InstanceHelper.getInstance().act(form._getPropertyAction(),form.getObject());
		}
		
		protected void consult(){
			InstanceHelper.getInstance().act(form._getPropertyAction(),form.getObject());
		}
		
		protected void login(){
			InstanceHelper.getInstance().act(form._getPropertyAction(),form.getObject());
		}
		
		protected void logout(){
			InstanceHelper.getInstance().act(form._getPropertyAction(),form.getObject());
		}
		
		@Override
		public Boolean getIsNotifiableOnStatusSuccess() {
			return Boolean.TRUE;
		}
		
		@Override
		protected void processOnSuccess() {
			super.processOnSuccess();
			if(ContentType.HTML.equals(form.getSubmitCommand().getRenderAsContentType())){
				if(Boolean.TRUE.equals(form.getSubmitCommand().getPropertiesMap().getAjax()))
					((Window)form.getParent()).getNotificationDialog().getOkCommand().addJavaScriptGoToUniformResourceLocatorOnEvent(Properties.ON_CLICK);
				else {
					Constant.Action action;
					Object object;
					if(Constant.Action.CREATE.equals(form._getPropertyAction()) || Constant.Action.UPDATE.equals(form._getPropertyAction())){
						action = Action.READ;
						object = form.getObject();
					}else {
						action = Action.LIST;
						object = form.getObject().getClass();	
					}
					((Window)form.getParent()).getNotificationDialog().getOkCommand().addJavaScriptGoToUniformResourceLocatorOnEvent(Properties.ON_CLICK
							,UniformResourceLocatorHelper.getInstance().stringify(action, object));
				}	
			}
		}
	}

	/**/

	/**/
	
	public static class ClassLocator extends Component.ClassLocator implements Serializable {

		private static final long serialVersionUID = -3187769614985951029L;

		public ClassLocator(String action) {
			super(Form.class,action,"FormMaster");
		}
		
		public ClassLocator(Constant.Action action) {
			super(Form.class,action,"FormMaster");
		}
		
	}
}