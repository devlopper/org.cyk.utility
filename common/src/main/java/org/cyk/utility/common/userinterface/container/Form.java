package org.cyk.utility.common.userinterface.container;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.Constant.Action;
import org.cyk.utility.common.Properties;
import org.cyk.utility.common.helper.ArrayHelper;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.helper.UniformResourceLocatorHelper;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.ContentType;
import org.cyk.utility.common.userinterface.Control;
import org.cyk.utility.common.userinterface.Layout;
import org.cyk.utility.common.userinterface.RequestHelper;
import org.cyk.utility.common.userinterface.collection.DataTable;
import org.cyk.utility.common.userinterface.command.Command;
import org.cyk.utility.common.userinterface.command.Menu;
import org.cyk.utility.common.userinterface.container.window.Window;
import org.cyk.utility.common.userinterface.event.Event;
import org.cyk.utility.common.userinterface.input.Input;
import org.cyk.utility.common.userinterface.input.InputFile;
import org.cyk.utility.common.userinterface.output.Output;
import org.cyk.utility.common.userinterface.output.OutputText;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Form extends Container implements Serializable {
	private static final long serialVersionUID = 1L;
	
	static {
		ClassHelper.getInstance().map(Builder.Target.class, Builder.Target.Adapter.Default.class,Boolean.FALSE);
	}
	
	public Form() {
		
	}
	
	@Override
	protected void listenPropertiesInstanciated(Properties propertiesMap) {
		super.listenPropertiesInstanciated(propertiesMap);
		
	}
	
	@Override
	public Visible setLabel(OutputText label) {
		super.setLabel(label);
		getPropertiesMap().setHeader(getLabel());
		return this;
	}
	
	@Override
	protected OutputText instanciateLabel() {
		OutputText instance =  super.instanciateLabel();
		getPropertiesMap().setHeader(instance);
		return instance;
	}
	
	public Form read(){
		return this;
	}
	
	public Form write(){
		return this;
	}
	
	/**/
	
	public static interface BuilderBase<OUTPUT extends Form> extends Component.BuilderBase<OUTPUT> {

		public static class Adapter<OUTPUT extends Form> extends Component.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
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
	public static class Master extends Form implements Serializable {
		private static final long serialVersionUID = 1L;
		
		//private Constant.Action action;
		private Boolean editable;
		private Object object;
		private Detail detail;
		
		private Menu menu = new Menu();
		private Command submitCommand = new Command();
		
		/**/

		public Master(Component parent,Object object,Constant.Action action,Class<? extends SubmitCommandActionAdapter> submitCommandActionAdapterClass) {
			setParent(parent);
			this.object = object;
			menu.addOneChild(submitCommand);
			submitCommand.setLabelFromIdentifier("userinterface.command.submit");
			_setPropertyAction(action);
			setSubmitCommandActionAdapterClass(submitCommandActionAdapterClass);
			instanciateDetail();
		}
		
		public Master(Component parent,Object object,Constant.Action action) {
			this(parent,object,action,ClassHelper.getInstance().getMapping(SubmitCommandActionAdapter.class));
		}
		
		public Master() {
			this(null,null,null);
		}
				
		@Override
		protected Class<?>[] getPropertyStyleClassClasses() {
			return ArrayUtils.addAll(new Class<?>[]{Form.class}, super.getPropertyStyleClassClasses());
		}
		
		public Master addComponentOnSubmit(Component component){
			String identifier = (String)component.getPropertiesMap().getIdentifier();
			getSubmitCommand().getPropertiesMap().addString(Properties.PROCESS, identifier);
			getSubmitCommand().getPropertiesMap().addString(Properties.UPDATE,identifier);
			return this;
		}
		
		public Master _setPropertyAction(Object value){
			super._setPropertyAction(value);
			Constant.Action action = (Action) value;
			if(editable==null && action!=null)
				setEditable(!Constant.Action.READ.equals(action) && !Constant.Action.DELETE.equals(action));
			submitCommand.getPropertiesMap().setRendered(!Constant.Action.READ.equals(action));
			if(Boolean.TRUE.equals(submitCommand.getPropertiesMap().getRendered()))
				submitCommand.setIsConfirmable(Constant.Action.DELETE.equals(action));
			
			Detail detail = getDetail();
			if(detail!=null){
				detail.getPropertiesMap().setAction(action);
			}
			return this;
		}
		
		@Override
		public Constant.Action _getPropertyAction() {
			return (Action) super._getPropertyAction();
		}
		
		public Master setSubmitCommandActionAdapterClass(Class<? extends SubmitCommandActionAdapter> submitCommandActionAdapterClass){
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
					Master.this.getDetail().getInputByFieldName(fieldName).getPropertiesMap().setDisabled(Boolean.TRUE);
				};
			}.execute();
			return this;
		}
		
		protected void __prepare__(){
			new CollectionHelper.Iterator.Adapter.Default<Class<?>>(getRequestParameterClasses()){
				private static final long serialVersionUID = 1L;
				@Override
				protected void __executeForEach__(Class<?> aClass) {
					setFromRequestParameter(aClass);
				}
			}.execute();
			InstanceHelper.getInstance().computeChanges(object);
		}
		
		protected Collection<Class<?>> getRequestParameterClasses(){
			return ClassHelper.getInstance().getByFieldTypeIdentified(object.getClass());
		}
		
		public Master read(){
			if(detail!=null)
				detail.read();
			return this;
		}
		
		public Master write(){
			if(detail!=null)
				detail.write();
			return this;
		}
		
		public Detail instanciateDetail(Layout.Type layoutType){
			Detail detail = new Detail(this,layoutType);
			
			detail.getPropertiesMap().setAction(_getPropertyAction());
			
			addOneChild(this.detail = detail);
			addComponentOnSubmit(detail);
			return detail;
		}
		
		public Detail instanciateDetail(){
			return instanciateDetail(Layout.Type.DEFAULT);
		}
		
		public DataTable instanciateDataTable(Class<?> actionOnClass,Class<?> choiceValueClass,DataTable.Cell.Listener cellListener,Boolean bottom,String...fieldNames){
			DataTable dataTable = new DataTable();
			dataTable.getPropertiesMap().setActionOnClass(actionOnClass);
			dataTable.getPropertiesMap().setChoiceValueClass(choiceValueClass);
			dataTable.getPropertiesMap().setCellListener(cellListener);
			getDetail().addDataTable(dataTable,bottom);
			dataTable.addColumnsByFieldNames(fieldNames);
			return dataTable;
		}
		
		public DataTable instanciateDataTable(Class<?> actionOnClass,Class<?> choiceValueClass,Boolean bottom,String...fieldNames){
			return instanciateDataTable(null, null, (DataTable.Cell.Listener)null,bottom);
		}
		
		public DataTable instanciateDataTable(){
			return instanciateDataTable(null, null,Boolean.TRUE);
		}
		
		/*public DataTable instanciateReadOnlyDataTable(Class<?> actionOnClass,DataTable.Cell.Listener cellListener,Boolean bottom,String...fieldNames){
			DataTable dataTable = new DataTable();
			dataTable.getPropertiesMap().setActionOnClass(actionOnClass);
			dataTable.getPropertiesMap().setChoiceValueClass(choiceValueClass);
			dataTable.getPropertiesMap().setCellListener(cellListener);
			getDetail().addDataTable(dataTable,bottom);
			dataTable.addColumnsByFieldNames(fieldNames);
			return dataTable;
		}*/
		
		@Override
		public Master setLabelFromIdentifier(String identifier) {
			return (Master) super.setLabelFromIdentifier(identifier);
		}
				
		@Override
		public Master build() {
			Master master = (Master) super.build();
			if(CollectionHelper.getInstance().isNotEmpty(getDetail().getChildren()))
				for(Component component : getDetail().getChildren().getElements())
					if(component instanceof InputFile){
						submitCommand.getPropertiesMap().setAjax(Boolean.FALSE);//because of file upload
						break;
					}			
			return master;
		}
		
		public Master setFromRequestParameter(Class<?> aClass,String fieldName){
			Form.Detail detail = getDetail();
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
		
		public Master setFromRequestParameter(Class<?> aClass){
			setFromRequestParameter(aClass, ClassHelper.getInstance().getVariableName(aClass));
			return this;
		} 
		
		public static interface BuilderBase<OUTPUT extends Master> extends Form.BuilderBase<OUTPUT> {

			public static class Adapter<OUTPUT extends Master> extends Form.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
				private static final long serialVersionUID = 1L;

				public Adapter(Class<OUTPUT> outputClass) {
					super(outputClass);
				}

				/**/

				public static class Default<OUTPUT extends Master> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
					private static final long serialVersionUID = 1L;

					public Default(Class<OUTPUT> outputClass) {
						super(outputClass);
					}
				}
			}
		}
		
		public static interface Builder extends BuilderBase<Master> {

			public static class Adapter extends BuilderBase.Adapter.Default<Master> implements Builder, Serializable {
				private static final long serialVersionUID = 1L;

				public Adapter() {
					super(Master.class);
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

			protected Master form;
			
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
				super(Master.class,action,"FormMaster");
			}
			
			public ClassLocator(Constant.Action action) {
				super(Master.class,action,"FormMaster");
			}
			
		}
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class Detail extends Form implements Serializable {
		private static final long serialVersionUID = 1L;

		private Master master;
		private String fieldsObjectFieldName;
		private Object fieldsObject;
		private Collection<DataTable> dataTables;
		private Collection<DataTable> dataTablesUp;
		private Collection<DataTable> dataTablesBottom;
		
		public Detail(Master master,Layout.Type layoutType) {
			setMaster(master);
			getLayout().setType(layoutType);
		}
		
		public Detail(Master master) {
			this(master,Layout.Type.DEFAULT);
		}
		
		public Detail() {
			this(null,null);
		}
		
		@Override
		protected Class<?>[] getPropertyStyleClassClasses() {
			return ArrayUtils.addAll(new Class<?>[]{Form.class}, super.getPropertyStyleClassClasses());
		}
		
		public Detail setMaster(Master master){
			this.master = master;
			if(fieldsObject == null && this.master!=null)
				fieldsObject = this.master.getObject();
			return this;
		}
		
		public Detail setFieldsObjectFromMaster(String...fieldNames){
			this.fieldsObjectFieldName = FieldHelper.getInstance().buildPath(fieldNames);
			setFieldsObject(ArrayHelper.getInstance().isEmpty(fieldNames) ? master.getObject() 
					: FieldHelper.getInstance().read(master.getObject(), fieldNames));
			return this;
		}
		
		public Detail addDataTable(DataTable dataTable,Boolean bottom){
			if(dataTables == null)
				dataTables = new ArrayList<>();
			if(Boolean.TRUE.equals(bottom)){
				if(dataTablesBottom == null)
					dataTablesBottom = new ArrayList<>();	
			}else{
				if(dataTablesUp == null)
					dataTablesUp = new ArrayList<>();	
			}
			
			if(dataTable.getForm() == null)//TODO form attribute sould be deprecated
				dataTable.setForm(this);
			if(dataTable.getPropertiesMap().getFormDetail() == null)
				dataTable.getPropertiesMap().setFormDetail(this);
			if(dataTable.getPropertiesMap().getAction() == null)
				dataTable.getPropertiesMap().setAction(getPropertiesMap().getAction());
			if(dataTable.getPropertiesMap().getMaster() == null)
				dataTable.getPropertiesMap().setMaster(master.getObject());
			
			
			if(Boolean.TRUE.equals(bottom)){
				dataTablesBottom.add(dataTable);
			}else{
				dataTablesUp.add(dataTable);
			}
			dataTables.add(dataTable);
			
			master.addComponentOnSubmit(dataTable);
			
			return this;
		}
		
		public Detail addDataTable(DataTable dataTable){
			return addDataTable(dataTable, Boolean.TRUE);
		}
		
		public DataTable instanciateDataTable(Boolean bottom){
			DataTable dataTable = new DataTable();
			addDataTable(dataTable,bottom);
			return dataTable;
		}
		
		public DataTable instanciateDataTable(){
			return instanciateDataTable(Boolean.TRUE);
		}
		
		public DataTable getDataTableAtIndex(Integer index,Boolean bottom){
			return CollectionHelper.getInstance().getElementAt(Boolean.TRUE.equals(bottom) ? dataTablesBottom : dataTablesUp, index);
		}
		
		public DataTable getDataTableAtIndex(Integer index){
			return getDataTableAtIndex(index, Boolean.TRUE);
		}
		
		public Detail addEvent(String fieldName,String[] updatedFieldNames){
			if(!Constant.Action.DELETE.equals((Action) getPropertiesMap().getAction())){
				Event.instanciateOne(this, fieldName, updatedFieldNames);
			}
			return this;
		}
		
		/**/
		
		private void __add__(Control control) {
			/*if(input.getLabel()!=null){
				layOut(input.getLabel());
			}*/
			layOut(control);
		}
		
		public Detail add(Collection<Control> controls) {
			if(CollectionHelper.getInstance().isNotEmpty(controls))
				for(Control control : controls)
					__add__(control);
			return this;
		}
		
		public Detail add(Control...controls) {
			if(ArrayHelper.getInstance().isNotEmpty(controls))
				add(Arrays.asList(controls));
			return this;
		}
		
		public Detail add(Object object,String fieldName,Number length,Number width,Boolean editable){
			if(object==null)
				object = getMaster().getObject();
			String constraintsFieldName = StringHelper.getInstance().isBlank(fieldsObjectFieldName) ? fieldName : FieldHelper.getInstance().buildPath(fieldsObjectFieldName,fieldName);
			FieldHelper.Constraints constraints = FieldHelper.Field.get(getMaster().getObject().getClass(), constraintsFieldName).getConstraints();
			Field field = FieldHelper.getInstance().get(object.getClass(), fieldName);
			Control control = Boolean.TRUE.equals(editable) ? Input.get(this,object, field,constraints) : Output.get(this, object, field,constraints).__setLabelFromField__();
			add(control.setLength(length).setWidth(width));
			return this;
		}
		
		public Detail add(Object object,String fieldName,Number length,Number width){
			return add(object,fieldName,length,width,getMaster().getEditable());
		}
		
		public Detail add(String fieldName,Number length,Number width,Boolean editable){
			return add(fieldsObject, fieldName, length, width,editable);
		}
		
		public Detail add(String fieldName,Number length,Number width){
			return add(fieldName, length, width,getMaster().getEditable());
		}
		
		public Detail add(Object object,String fieldName,Boolean editable){
			return add(object, fieldName, 1, 1,editable);
		}
		
		public Detail add(Object object,String fieldName){
			return add(object,fieldName,getMaster().getEditable());
		}
		
		public Detail add(String[] fieldNames,Number length,Number width,Boolean editable){
			Object object = fieldNames.length == 1 ? fieldsObject : FieldHelper.getInstance().read(fieldsObject, ArrayUtils.remove(fieldNames, fieldNames.length-1));
			String fieldName = fieldNames[fieldNames.length-1] ;
			add(object, fieldName, length, width,editable);
			//getInputByFieldName(object, fieldName).getPropertiesMap().setIdentifierAsStyleClass(CascadeStyleSheetHelper.getInstance().getClass(fieldsObject, fieldNames));
			return this;
		}
		
		public Detail add(String[] fieldNames,Number length,Number width){
			return add(fieldNames,length,width,getMaster().getEditable());
		}
		
		public Detail addByEditable(Boolean editable,String fieldName,String...fieldNames){
			return add(ArrayUtils.addAll(new String[]{fieldName}, fieldNames), 1, 1,editable);
		}
		
		public Detail addReadOnly(String fieldName,String...fieldNames){
			return addByEditable(Boolean.FALSE, fieldName, fieldNames);
		}
		
		public Detail add(String fieldName,String...fieldNames){
			return addByEditable(getMaster().getEditable(),fieldName, fieldNames);
		}
		
		public Detail add(String fieldName1,String fieldName2,Boolean editable){
			return addByEditable(editable,fieldName1,new String[]{fieldName2});
		}
		
		public Detail add(String fieldName1,String fieldName2){
			return add(fieldName1,fieldName2,getMaster().getEditable());
		}
		
		public Detail addFieldName(String name,String labelStringIdentifier){
			add(name);
			Control control = getControlByFieldName(fieldsObject,name);
			control.setLabelFromIdentifier(labelStringIdentifier);
			return this;
		}
		
		public Detail addBreak(){
			layOutBreak();
			return this;
		}
		
		public Control getControlByFieldName(Object object,String fieldName){
			if(children!=null && CollectionHelper.getInstance().isNotEmpty(children.getElements()))
				for(Component component : children.getElements())
					if(component instanceof Control && ((Control)component).getField()!=null && ((Control)component).getObject() == object && ((Control)component).getField().getName().equals(fieldName))
						return (Control) component;
			return null;
		}
		
		public Control getControlByFieldName(String fieldName){
			return getControlByFieldName(master.getObject(), fieldName);
		}
		
		public Input<?> getInputByFieldName(Object object,String fieldName){
			/*
			if(children!=null && CollectionHelper.getInstance().isNotEmpty(children.getElements()))
				for(Component component : children.getElements())
					if(component instanceof Input<?> && ((Input<?>)component).getField()!=null && ((Input<?>)component).getObject() == object && ((Input<?>)component).getField().getName().equals(fieldName))
						return (Input<?>) component;
			return null;
			*/
			return (Input<?>) getControlByFieldName(object, fieldName);
		}
		
		public Input<?> getInputByFieldName(String fieldName){
			//return getInputByFieldName(master.getObject(), fieldName);
			return (Input<?>) getControlByFieldName(fieldName);
		}
		
		public Output getOutputByFieldName(Object object,String fieldName){
			return (Output) getControlByFieldName(object, fieldName);
		}
		
		public Output getOutputByFieldName(String fieldName){
			return (Output) getControlByFieldName(fieldName);
		}
		
		@Override
		public Detail read(){
			if(children!=null)
				new CollectionHelper.Iterator.Adapter.Default<Component>(children.getElements()){
					private static final long serialVersionUID = 1L;
					@Override
					protected void __executeForEach__(Component component) {
						((Input<?>)component).read();
					}
				}.execute();
			
			if(dataTables!=null)
				for(DataTable dataTable : dataTables)
					dataTable.read();	
			return this;
		}
		
		@Override
		public Detail write(){
			if(children!=null)
				new CollectionHelper.Iterator.Adapter.Default<Component>(children.getElements()){
					private static final long serialVersionUID = 1L;
					@Override
					protected void __executeForEach__(Component component) {
						if(component instanceof Input<?>){
							((Input<?>)component).write();
						}
					}
				}.execute();
			
			if(dataTables!=null)
				for(DataTable dataTable : dataTables)
					dataTable.write();	
				
			return this;
		}
		
		/**/

		@SuppressWarnings("unchecked")
		public static Object buildTarget(Detail detail){
			return ClassHelper.getInstance().instanciateOne(Builder.Target.class).setInput(detail).execute();
		}
		
		/**/
		
		public static interface BuilderBase<OUTPUT extends Detail> extends Form.BuilderBase<OUTPUT> {

			public static class Adapter<OUTPUT extends Detail> extends Form.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
				private static final long serialVersionUID = 1L;

				public Adapter(Class<OUTPUT> outputClass) {
					super(outputClass);
				}

				/**/

				public static class Default<OUTPUT extends Detail> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
					private static final long serialVersionUID = 1L;

					public Default(Class<OUTPUT> outputClass) {
						super(outputClass);
					}
				}
			}
		}
		
		public static interface Builder extends BuilderBase<Detail> {

			public static class Adapter extends BuilderBase.Adapter.Default<Detail> implements Builder, Serializable {
				private static final long serialVersionUID = 1L;

				public Adapter() {
					super(Detail.class);
				}

				/**/

				public static class Default extends Builder.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;

				}
			}
			
			public static interface Target<OUTPUT,CONTROL,ROW,LABEL> extends org.cyk.utility.common.Builder<Detail, OUTPUT> {
				
				ROW createRow(OUTPUT model);
				String getType(Control control);
				CONTROL addControl(ROW row,Control control,String type);
				LABEL addLabel(ROW row,OutputText outputText);
				Target<OUTPUT,CONTROL,ROW,LABEL> link(CONTROL control,LABEL label);
				
				public static class Adapter<OUTPUT,ROW,CONTROL,LABEL> extends org.cyk.utility.common.Builder.Adapter.Default<Detail, OUTPUT> implements Target<OUTPUT,CONTROL,ROW,LABEL>,Serializable {
					private static final long serialVersionUID = 1L;
					
					public Adapter(Detail input, Class<OUTPUT> outputClass) {
						super(Detail.class, input, outputClass);
					}
					
					public static class Default<OUTPUT,CONTROL,ROW,LABEL> extends Target.Adapter<OUTPUT,ROW,CONTROL,LABEL> implements Serializable {
						private static final long serialVersionUID = 1L;
						
						public Default(Detail input, Class<OUTPUT> outputClass) {
							super(input, outputClass);
						}
						
						@SuppressWarnings("unchecked")
						public Default() {
							this(null, null);
							setOutputClass((Class<OUTPUT>) ClassHelper.getInstance().getParameterAt(getClass(), 0, Object.class));
						}
						
						@Override
						protected OUTPUT __execute__() {
							OUTPUT instance = ClassHelper.getInstance().instanciateOne(getOutputClass());
							for (Integer rowIndex = 0; rowIndex < getInput().getLayout().getArea().getWidth().getTo().intValue(); rowIndex++) {
								ROW row = createRow(instance);
								for (Component component : getInput().getLayout().getWhereAreaWidthFromEqual(rowIndex)) {
									LABEL label = null;
									if(component instanceof Component.Visible && ((Component.Visible)component).getLabel()!=null)
										label = addLabel(row, ((Component.Visible)component).getLabel());
									CONTROL control = addControl(row, (Control) component, getType((Control) component));
									if(component instanceof Input<?> && label!=null && !Boolean.TRUE.equals(component.getPropertiesMap().getReadableOnly()))
										link(control, label);
								}
							}
							return instance;
						}
						
					}
					
					@Override
					public ROW createRow(OUTPUT model) {
						return null;
					}
					
					@Override
					public CONTROL addControl(ROW row, Control control, String type) {
						return null;
					}
					
					@Override
					public LABEL addLabel(ROW row, OutputText outputText) {
						return null;
					}
					
					@Override
					public String getType(Control control) {
						return null;
					}
					
					@Override
					public Target<OUTPUT, CONTROL, ROW, LABEL> link(CONTROL control, LABEL label) {
						return null;
					}
				}
				
			}
		}
	
		/**/
		
	}

	/**/
	
	
}
