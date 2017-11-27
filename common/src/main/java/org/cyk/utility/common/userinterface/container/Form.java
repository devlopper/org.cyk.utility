package org.cyk.utility.common.userinterface.container;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.Constant.Action;
import org.cyk.utility.common.Properties;
import org.cyk.utility.common.helper.ArrayHelper;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.UniformResourceLocatorHelper;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.Control;
import org.cyk.utility.common.userinterface.Layout;
import org.cyk.utility.common.userinterface.command.Command;
import org.cyk.utility.common.userinterface.command.Menu;
import org.cyk.utility.common.userinterface.container.window.Window;
import org.cyk.utility.common.userinterface.input.Input;
import org.cyk.utility.common.userinterface.input.InputFile;
import org.cyk.utility.common.userinterface.output.Output;
import org.cyk.utility.common.userinterface.output.OutputText;

@Getter @Setter @Accessors(chain=true)
public class Form extends Container implements Serializable {
	private static final long serialVersionUID = 1L;

	/**/
	
	public Form() {
		
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
		
		private Constant.Action action;
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
			submitCommand.setLabelFromIdentifier("command.submit");
			setAction(action);
			setSubmitCommandActionAdapterClass(submitCommandActionAdapterClass);
			instanciateDetail();
		}
		
		public Master(Component parent,Object object,Constant.Action action) {
			this(parent,object,action,SubmitCommandActionAdapter.class);
		}
		
		public Master() {
			this(null,null,null);
		}
		
		public Master setAction(Constant.Action action){
			this.action = action;
			if(editable==null && this.action!=null)
				setEditable(!Constant.Action.READ.equals(this.action) && !Constant.Action.DELETE.equals(this.action));
			submitCommand.getPropertiesMap().setRendered(!Constant.Action.READ.equals(this.action));
			if(Boolean.TRUE.equals(submitCommand.getPropertiesMap().getRendered()))
				submitCommand.setIsConfirmable(Constant.Action.DELETE.equals(this.action));
			return this;
		}
		
		public Master setSubmitCommandActionAdapterClass(Class<? extends SubmitCommandActionAdapter> submitCommandActionAdapterClass){
			if(submitCommandActionAdapterClass==null){
				
			}else{
				submitCommand.setActionFromClass(submitCommandActionAdapterClass).setIsConfirmable(Constant.Action.DELETE.equals(action));
				((SubmitCommandActionAdapter)submitCommand.getAction()).setForm(this);	
			}				
			return this;
		}
		
		/*protected Class<? extends SubmitCommandActionAdapter> getSubmitCommandActionAdapterClass(){
			return SubmitCommandActionAdapter.class;
		}*/
		
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
			addOneChild(this.detail = detail);
			return detail;
		}
		
		public Detail instanciateDetail(){
			return instanciateDetail(Layout.Type.DEFAULT);
		}
		
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
				form.write();
				act();
				return null;
			}
			
			protected void act(){
				switch(form.getAction()){
				case CREATE: create(); break;
				case READ: read(); break;
				case UPDATE: update(); break;
				case DELETE: delete(); break;
				case SELECT: select();break;
				case SEARCH: search();break;
				case CONSULT: consult();break;
				case LIST: list();break;
				case PRINT: print();break;
				}
			}
			
			protected void create(){
				InstanceHelper.getInstance().act(form.getAction(),form.getObject());
			}
			
			protected void read(){
				InstanceHelper.getInstance().act(form.getAction(),form.getObject());
			}
			
			protected void update(){
				InstanceHelper.getInstance().act(form.getAction(),form.getObject());
			}
			
			protected void delete(){
				InstanceHelper.getInstance().act(form.getAction(),form.getObject());
			}
			
			protected void select(){
				InstanceHelper.getInstance().act(form.getAction(),form.getObject());
			}
			
			protected void search(){
				InstanceHelper.getInstance().act(form.getAction(),form.getObject());
			}
			
			protected void list(){
				InstanceHelper.getInstance().act(form.getAction(),form.getObject());
			}
			
			protected void print(){
				InstanceHelper.getInstance().act(form.getAction(),form.getObject());
			}
			
			protected void consult(){
				InstanceHelper.getInstance().act(form.getAction(),form.getObject());
			}
			
			@Override
			public Boolean getIsNotifiableOnStatusSuccess() {
				return Boolean.TRUE;
			}
			
			@Getter @Setter @Accessors(chain=true)
			public static class Web extends SubmitCommandActionAdapter implements Serializable{
				private static final long serialVersionUID = 1L;
				
				@Override
				protected void processOnSuccess() {
					super.processOnSuccess();				
					if(Boolean.TRUE.equals(form.getSubmitCommand().getPropertiesMap().getAjax()))
						((Window)form.getParent()).getNotificationDialog().getOkCommand().addJavaScriptGoToUniformResourceLocatorOnEvent(Properties.ON_CLICK);
					else {
						Constant.Action action;
						Object object;
						if(Constant.Action.CREATE.equals(form.getAction()) || Constant.Action.UPDATE.equals(form.getAction())){
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
	
		public static class Web extends Master implements Serializable {
			private static final long serialVersionUID = 1L;

			public Web() {
				setSubmitCommandActionAdapterClass(SubmitCommandActionAdapter.Web.class);
			}
			
			public Web(Component parent, Object object, Action action) {
				super(parent, object, action,SubmitCommandActionAdapter.Web.class);
			}
			
			
			
		}
		
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
		private Object fieldsObject;
		
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
		
		public Detail setMaster(Master master){
			this.master = master;
			if(fieldsObject == null && this.master!=null)
				fieldsObject = this.master.getObject();
			return this;
		}
		
		public Detail setFieldsObjectFromMaster(String...fieldNames){
			setFieldsObject(ArrayHelper.getInstance().isEmpty(fieldNames) ? master.getObject() : FieldHelper.getInstance().read(master.getObject(), fieldNames));
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
		
		public Detail add(Object object,String fieldName,Number length,Number width){
			Field field = FieldHelper.getInstance().get(object.getClass(), fieldName);
			Control control = Boolean.TRUE.equals(getMaster().getEditable()) ? Input.get(this,object, field) : Output.get(this, object, field).__setLabelFromField__();
			add(control.setLength(length).setWidth(width));
			return this;
		}
		
		public Detail add(String fieldName,Number length,Number width){
			return add(fieldsObject, fieldName, length, width);
		}
		
		public Detail add(Object object,String fieldName){
			return add(object, fieldName, 1, 1);
		}
		
		public Detail add(String[] fieldNames,Number length,Number width){
			Object object = fieldNames.length == 1 ? fieldsObject : FieldHelper.getInstance().read(fieldsObject, ArrayUtils.remove(fieldNames, fieldNames.length-1));
			String fieldName = fieldNames[fieldNames.length-1] ;
			add(object, fieldName, length, width);
			//getInputByFieldName(object, fieldName).getPropertiesMap().setIdentifierAsStyleClass(CascadeStyleSheetHelper.getInstance().getClass(fieldsObject, fieldNames));
			return this;
		}
		
		public Detail add(String fieldName,String...fieldNames){
			return add(ArrayUtils.addAll(new String[]{fieldName}, fieldNames), 1, 1);
		}
		
		public Detail add(String fieldName1,String fieldName2){
			return add(fieldName1,new String[]{fieldName2});
		}
		
		public Detail addBreak(){
			layOutBreak();
			return this;
		}
		
		public Input<?> getInputByFieldName(Object object,String fieldName){
			if(children!=null && CollectionHelper.getInstance().isNotEmpty(children.getElements()))
				for(Component component : children.getElements())
					if(component instanceof Input<?> && ((Input<?>)component).getField()!=null && ((Input<?>)component).getObject() == object && ((Input<?>)component).getField().getName().equals(fieldName))
						return (Input<?>) component;
			return null;
		}
		
		public Input<?> getInputByFieldName(String fieldName){
			return getInputByFieldName(master.getObject(), fieldName);
		}
		
		@Override
		public Detail read(){
			new CollectionHelper.Iterator.Adapter.Default<Component>(children.getElements()){
				private static final long serialVersionUID = 1L;
				@Override
				protected void __executeForEach__(Component component) {
					((Input<?>)component).read();
				}
			}.execute();
			return this;
		}
		
		@Override
		public Detail write(){
			new CollectionHelper.Iterator.Adapter.Default<Component>(children.getElements()){
				private static final long serialVersionUID = 1L;
				@Override
				protected void __executeForEach__(Component component) {
					if(component instanceof Input<?>)
						((Input<?>)component).write();
				}
			}.execute();
			return this;
		}
		
		/**/

		@SuppressWarnings("unchecked")
		public static Object buildTarget(Detail detail){
			return ClassHelper.getInstance().instanciateOne(InstanceHelper.getInstance()
					.getIfNotNullElseDefault(Builder.Target.Adapter.Default.DEFAULT_CLASS, Builder.Target.Adapter.Default.class)).setInput(detail).execute();
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
						
						@SuppressWarnings("unchecked")
						public static Class<? extends Target<?,?,?,?>> DEFAULT_CLASS = (Class<? extends Default<?,?,?,?>>) ClassHelper.getInstance().getByName(Default.class);
						
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
									if(component instanceof Input<?> && label!=null)
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
