package org.cyk.utility.common.userinterface.container.form;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.Constant.Action;
import org.cyk.utility.common.helper.ArrayHelper;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.Control;
import org.cyk.utility.common.userinterface.Layout;
import org.cyk.utility.common.userinterface.collection.DataTable;
import org.cyk.utility.common.userinterface.event.Event;
import org.cyk.utility.common.userinterface.input.Input;
import org.cyk.utility.common.userinterface.output.Output;
import org.cyk.utility.common.userinterface.output.OutputText;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class FormDetail extends FormContainer implements Serializable {
	private static final long serialVersionUID = 1L;

	private Form master;
	private String fieldsObjectFieldName;
	private Object fieldsObject;
	private Collection<DataTable> dataTables;
	private Collection<DataTable> dataTablesUp;
	private Collection<DataTable> dataTablesBottom;
	
	public FormDetail(Form master,Layout.Type layoutType) {
		setMaster(master);
		getLayout().setType(layoutType);
	}
	
	public FormDetail(Form master) {
		this(master,Layout.Type.DEFAULT);
	}
	
	public FormDetail() {
		this(null,null);
	}
	
	@Override
	protected Class<?>[] getPropertyStyleClassClasses() {
		return ArrayUtils.addAll(new Class<?>[]{FormContainer.class}, super.getPropertyStyleClassClasses());
	}
	
	public FormDetail setMaster(Form master){
		this.master = master;
		if(fieldsObject == null && this.master!=null)
			fieldsObject = this.master.getObject();
		return this;
	}
	
	public FormDetail setFieldsObjectFromMaster(String...fieldNames){
		this.fieldsObjectFieldName = FieldHelper.getInstance().buildPath(fieldNames);
		setFieldsObject(ArrayHelper.getInstance().isEmpty(fieldNames) ? master.getObject() 
				: FieldHelper.getInstance().read(master.getObject(), fieldNames));
		return this;
	}
	
	public FormDetail addDataTable(DataTable dataTable,Boolean bottom){
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
	
	public FormDetail addDataTable(DataTable dataTable){
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
	
	public FormDetail addEvent(String fieldName,String[] updatedFieldNames){
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
	
	public FormDetail add(Collection<Control> controls) {
		if(CollectionHelper.getInstance().isNotEmpty(controls))
			for(Control control : controls)
				__add__(control);
		return this;
	}
	
	public FormDetail add(Control...controls) {
		if(ArrayHelper.getInstance().isNotEmpty(controls))
			add(Arrays.asList(controls));
		return this;
	}
	
	public FormDetail add(Object object,String fieldName,Number length,Number width,Boolean editable,Control.Listener.Get getControlListener){
		if(object==null)
			object = getMaster().getObject();
		String constraintsFieldName = StringHelper.getInstance().isBlank(fieldsObjectFieldName) ? fieldName : FieldHelper.getInstance().buildPath(fieldsObjectFieldName,fieldName);
		FieldHelper.Constraints constraints = FieldHelper.Field.get(getMaster().getObject().getClass(), constraintsFieldName).getConstraints();
		Field field = FieldHelper.getInstance().get(object.getClass(), fieldName);
		Control control = Boolean.TRUE.equals(editable) 
				? Input.get(this,object, field,constraints,getControlListener) 
				: Output.get(this, object, field,constraints,getControlListener)/*.__setLabelFromField__()*/;
		add(control.setLength(length).setWidth(width));
		return this;
	}
	
	public FormDetail add(Object object,String fieldName,Number length,Number width,Boolean editable){
		return add(object,fieldName,length,width,editable,null);
	}
	
	public FormDetail add(Object object,String fieldName,Number length,Number width){
		return add(object,fieldName,length,width,getMaster().getEditable());
	}
	
	public FormDetail add(String fieldName,Number length,Number width,Boolean editable){
		return add(fieldsObject, fieldName, length, width,editable);
	}
	
	public FormDetail add(String fieldName,Number length,Number width){
		return add(fieldName, length, width,getMaster().getEditable());
	}
	
	public FormDetail add(Object object,String fieldName,Boolean editable){
		return add(object, fieldName, 1, 1,editable);
	}
	
	public FormDetail add(Object object,String fieldName){
		return add(object,fieldName,getMaster().getEditable());
	}
	
	public FormDetail add(String[] fieldNames,Number length,Number width,Boolean editable,Control.Listener.Get getControlListener){
		Object object = fieldNames.length == 1 ? fieldsObject : FieldHelper.getInstance().read(fieldsObject, ArrayUtils.remove(fieldNames, fieldNames.length-1));
		String fieldName = fieldNames[fieldNames.length-1] ;
		add(object, fieldName, length, width,editable,getControlListener);
		//getInputByFieldName(object, fieldName).getPropertiesMap().setIdentifierAsStyleClass(CascadeStyleSheetHelper.getInstance().getClass(fieldsObject, fieldNames));
		return this;
	}
	
	public FormDetail add(String[] fieldNames,Number length,Number width,Boolean editable){
		return add(fieldNames,length,width,editable,null);
	}
	
	public FormDetail add(String[] fieldNames,Number length,Number width){
		return add(fieldNames,length,width,getMaster().getEditable());
	}
	
	public FormDetail addByEditable(Control.Listener.Get getControlListener,Boolean editable,String fieldName,String...fieldNames){
		return add(ArrayUtils.addAll(new String[]{fieldName}, fieldNames), 1, 1,editable,getControlListener);
	}
	
	public FormDetail addByEditable(Boolean editable,String fieldName,String...fieldNames){
		return addByEditable(null,editable,fieldName,fieldNames);
	}
	
	public FormDetail addReadOnly(String fieldName,String...fieldNames){
		return addByEditable(Boolean.FALSE, fieldName, fieldNames);
	}
	
	public FormDetail addByControlGetListener(Control.Listener.Get getControlListener,String fieldName,String...fieldNames){
		return addByEditable(getControlListener,getMaster().getEditable(),fieldName, fieldNames);
	}
	
	public FormDetail add(String fieldName,String...fieldNames){
		return addByControlGetListener(null,fieldName,fieldNames);
	}
	
	public FormDetail add(String fieldName1,String fieldName2,Boolean editable){
		return addByEditable(editable,fieldName1,new String[]{fieldName2});
	}
	
	public FormDetail add(String fieldName1,String fieldName2){
		return add(fieldName1,fieldName2,getMaster().getEditable());
	}
	
	public FormDetail addFieldName(String name,String labelStringIdentifier){
		add(name);
		Control control = getControlByFieldName(fieldsObject,name);
		control.setLabelFromIdentifier(labelStringIdentifier);
		return this;
	}
	
	public FormDetail addBreak(){
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
		return getControlByFieldName(FieldHelper.getInstance().readBeforeLast(master.getObject(), fieldName), FieldHelper.getInstance().getLast(fieldName));
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
	public FormDetail read(){
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
	public FormDetail write(){
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
	public static Object buildTarget(FormDetail detail){
		return ClassHelper.getInstance().instanciateOne(Builder.Target.class).setInput(detail).execute();
	}
	
	/**/
	
	public static interface BuilderBase<OUTPUT extends FormDetail> extends FormContainer.BuilderBase<OUTPUT> {

		public static class Adapter<OUTPUT extends FormDetail> extends FormContainer.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}

			/**/

			public static class Default<OUTPUT extends FormDetail> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
			}
		}
	}
	
	public static interface Builder extends BuilderBase<FormDetail> {

		public static class Adapter extends BuilderBase.Adapter.Default<FormDetail> implements Builder, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter() {
				super(FormDetail.class);
			}

			/**/

			public static class Default extends Builder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

			}
		}
		
		public static interface Target<OUTPUT,CONTROL,ROW,LABEL> extends org.cyk.utility.common.Builder<FormDetail, OUTPUT> {
			
			ROW createRow(OUTPUT model);
			String getType(Control control);
			CONTROL addControl(ROW row,Control control,String type);
			LABEL addLabel(ROW row,OutputText outputText);
			Target<OUTPUT,CONTROL,ROW,LABEL> link(CONTROL control,LABEL label);
			
			public static class Adapter<OUTPUT,ROW,CONTROL,LABEL> extends org.cyk.utility.common.Builder.Adapter.Default<FormDetail, OUTPUT> implements Target<OUTPUT,CONTROL,ROW,LABEL>,Serializable {
				private static final long serialVersionUID = 1L;
				
				public Adapter(FormDetail input, Class<OUTPUT> outputClass) {
					super(FormDetail.class, input, outputClass);
				}
				
				public static class Default<OUTPUT,CONTROL,ROW,LABEL> extends Target.Adapter<OUTPUT,ROW,CONTROL,LABEL> implements Serializable {
					private static final long serialVersionUID = 1L;
					
					public Default(FormDetail input, Class<OUTPUT> outputClass) {
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
								if(component instanceof Component.Visible && ((Component.Visible)component).getLabel()!=null){
									//System.out.println("FormDetail.Builder.Target.Adapter.Default.__execute__()");
									//debug(((Component.Visible)component));
									label = addLabel(row, ((Component.Visible)component).getLabel());
								}
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