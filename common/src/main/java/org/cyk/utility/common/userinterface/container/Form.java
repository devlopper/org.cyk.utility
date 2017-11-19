package org.cyk.utility.common.userinterface.container;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.helper.ArrayHelper;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.LoggingHelper;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.Control;
import org.cyk.utility.common.userinterface.Layout;
import org.cyk.utility.common.userinterface.command.Command;
import org.cyk.utility.common.userinterface.command.Menu;
import org.cyk.utility.common.userinterface.input.Input;
import org.cyk.utility.common.userinterface.output.Output;
import org.cyk.utility.common.userinterface.output.OutputText;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

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
		
		private static final Map<Class<?>,Map<Constant.Action,Map<Object,Class<? extends Master>>>> MAP = new HashMap<Class<?>, Map<Constant.Action,Map<Object,Class<? extends Master>>>>();
		
		private Constant.Action action;
		private Boolean editable;
		private Object object;
		private Detail detail;
		
		private Menu menu = new Menu();
		private Command submitCommand = new Command();
		
		/**/

		public Master(Component parent,Object object,Class<? extends SubmitCommandActionAdapter> submitCommandActionAdapterClass) {
			setParent(parent);
			this.object = object;
			menu.addOneChild(submitCommand);
			submitCommand.setLabelFromIdentifier("command.submit");
			setSubmitCommandActionAdapterClass(submitCommandActionAdapterClass);
			instanciateDetail();
		}
		
		public Master(Component parent,Object object) {
			this(parent,object,SubmitCommandActionAdapter.class);
		}
		
		public Master() {
			this(null,null);
		}
		
		public Master setSubmitCommandActionAdapterClass(Class<? extends SubmitCommandActionAdapter> submitCommandActionAdapterClass){
			if(submitCommandActionAdapterClass==null){
				
			}else{
				submitCommand.setActionFromClass(submitCommandActionAdapterClass);
				((SubmitCommandActionAdapter)submitCommand.getAction()).setForm(this);
				//submitCommand.getAction().setIsNotifiableOnStatusSuccess(Boolean.TRUE);	
			}				
			return this;
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
			return (Master) super.build();
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
				return null;
			}
			
			@Override
			public Boolean getIsNotifiableOnStatusSuccess() {
				return Boolean.TRUE;
			}
		}
	
		/**/
		
		public static void setClass(Class<?> aClass,Constant.Action action,Object key,Class<? extends Master> formClass){
			Map<Constant.Action,Map<Object,Class<? extends Master>>> actions = MAP.get(aClass);
			if(actions==null)
				MAP.put(aClass, actions = new HashMap<Constant.Action, Map<Object,Class<? extends Master>>>());
			Map<Object,Class<? extends Master>> objects = actions.get(action);
			if(objects==null)
				actions.put(action, objects = new HashMap<Object, Class<? extends Master>>());
			objects.put(key,formClass);
		}
		public static void setClass(Class<?> aClass,Constant.Action action,Class<? extends Master> formClass){
			setClass(aClass, action, null, formClass);
		}
		
		public static Class<? extends Master> getClass(Class<?> aClass,Constant.Action action,Object key){
			Class<? extends Master> formClass = null;
			Map<Constant.Action,Map<Object,Class<? extends Master>>> actions = MAP.get(aClass);
			if(actions!=null){
				Map<Object,Class<? extends Master>> objects = actions.get(action);
				if(objects!=null){
					formClass = objects.get(key);
				}
			}
			if(formClass == null){
				LoggingHelper.Logger.Log4j.Adapter.Default.log("no registered form class found for "+aClass,Form.class,LoggingHelper.Logger.Level.TRACE,null);
				formClass = Form.Master.class;
			}
			return formClass;
		}
		public static Class<? extends Master> getClass(Class<?> aClass,Constant.Action action){
			return getClass(aClass, action, null);
		}
		
		public static Master get(Object object,Constant.Action action,Object key){
			Class<? extends Master> aClass = getClass(object.getClass(), action, key);
			//Master master = ClassHelper.getInstance().instanciate(aClass,new Object[]{Object.class,object});
			Master master = ClassHelper.getInstance().instanciateOne(aClass);
			if(master.getEditable()==null)
				master.setEditable(!Constant.Action.READ.equals(action) && !Constant.Action.DELETE.equals(action));
			if(master.getEditable()==null)
				master.setEditable(Boolean.TRUE);
			Detail detail = master.getDetail();
			if(detail == null)
				detail = master.instanciateDetail();
			
			master.setObject(object);
			master.setAction(action);
			
			if(Master.class.equals(aClass)){
				if(Boolean.TRUE.equals(master.getEditable())){
					for(Input<?> input : Input.get(detail, object))
						detail.add(input).addBreak();
				}else {
					for(Output output : Output.get(detail, object))
						detail.add(output).addBreak();	
				}
					
			}else{
				
			}
			return master;
		}
		public static Master get(Object object,Constant.Action action){
			return get(object, action,null);
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
			Control control = Boolean.TRUE.equals(getMaster().getEditable()) 
					? Input.get(this,object, FieldHelper.getInstance().get(object.getClass(), fieldName))
					: Output.get(this, object, FieldHelper.getInstance().get(object.getClass(), fieldName));
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
									if(component instanceof Input<?> && ((Input<?>)component).getLabel()!=null)
										label = addLabel(row, ((Input<?>)component).getLabel());
									CONTROL control = addControl(row, (Control) component, getType((Control) component));
									if(label!=null)
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
