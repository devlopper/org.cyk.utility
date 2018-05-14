package org.cyk.utility.common.userinterface.collection;

import java.io.Serializable;
import java.util.Locale;

import org.cyk.utility.common.Constant;
import org.cyk.utility.common.Constant.Action;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.FileHelper;
import org.cyk.utility.common.helper.LoggingHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.userinterface.CascadeStyleSheetHelper;
import org.cyk.utility.common.userinterface.input.Input;
import org.cyk.utility.common.userinterface.output.OutputText;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Column extends Dimension implements Serializable {
	private static final long serialVersionUID = 1L;

	static {
		ClassHelper.getInstance().map(Builder.class, Builder.Adapter.Default.class,Boolean.FALSE);
	}
	
	public static enum CellValueSource {ROW_PROPERTIES_MAP,ROW_PROPERTY_VALUE,ROW; public static CellValueSource DEFAULT = CellValueSource.ROW_PROPERTY_VALUE;}
	//public static enum CellValueType {TEXT,MENU,FILE,IMAGE; public static CellValueType DEFAULT = CellValueType.TEXT;}

	//private Collection<Listener> listeners = new ArrayList<>();
	private CellValueSource cellValueSource = CellValueSource.DEFAULT;
	private Cell.ValueType cellValueType;
	private Class<Input<?>> inputClass;
	
	/**/
	
	public Cell getCell(Row row){
		if(row==null)
			return null;
		return Cell.getOne(this, row,(Cell.Listener) getPropertiesMap().getCellListener());
	}
		
	public Column computeCellValueType(Class<?> aClass,Boolean sortable){
		if(aClass!=null && cellValueType==null){
			LoggingHelper.Message.Builder loggingMessageBuilder = new LoggingHelper.Message.Builder.Adapter.Default();
			loggingMessageBuilder.addManyParameters("compute cell value type");
			String fieldName = (String) getPropertiesMap().getFieldName();
			loggingMessageBuilder.addNamedParameters("action",getPropertiesMap().getAction(),"field name",fieldName,"cell value source",cellValueSource);
			Class<?> fieldType = null;
			if(CellValueSource.ROW.equals(cellValueSource))
				fieldType = FieldHelper.getInstance().get(Row.class, fieldName).getType();
			else if(CellValueSource.ROW_PROPERTIES_MAP.equals(cellValueSource))
				fieldType = Object.class;
			else{
				java.lang.reflect.Field field = FieldHelper.getInstance().get(aClass, fieldName);
				if(field!=null){
					fieldType = FieldHelper.getInstance().getType(aClass, field);
				}
				//inputClass = (Class<Input<?>>) Input.getListener().getClass(null, object, FieldHelper.getInstance().get(object.getClass(), fieldName));
				if( Constant.Action.isCreateOrUpdate((Action) getPropertiesMap().getAction()) )
					cellValueType = Cell.ValueType.INPUT;	
			}
			loggingMessageBuilder.addNamedParameters("field type",fieldType);
			if( cellValueType==null ){
				if(FileHelper.getListener().getModelClass().equals(fieldType))
					cellValueType = Cell.ValueType.FILE;
				else
					cellValueType = Cell.ValueType.DEFAULT;
			}
			if( Constant.Action.isCreateOrUpdate((Action) getPropertiesMap().getAction()) )
				;
			else if(fieldType!=null)
				getPropertiesMap().setLinked(ClassHelper.getInstance().isIdentified(fieldType));
			
			if(Cell.ValueType.TEXT.equals(cellValueType)){
				if(CellValueSource.ROW_PROPERTY_VALUE.equals(cellValueSource))
					getPropertiesMap().setSortable(sortable);
			}
			loggingMessageBuilder.addNamedParameters("cell value type",cellValueType,"linked",getPropertiesMap().getLinked());
			logTrace(loggingMessageBuilder);
		}
		return this;
	}

	@SuppressWarnings("unchecked")
	public int sort(Object o1,Object o2){
		return ((Comparable<Object>)o1).compareTo(o2);
	}
	
	public boolean filter(Object value,Object filter,Locale locale){
		return ((String)value).startsWith((String) filter);
	}
	
	/**/

	public static Builder instanciateOneBuilder(){
		return ClassHelper.getInstance().instanciateOne(Builder.class);
	}
	
	public static Column instanciateOne(String labelStringIdentifier,String fieldName,Column.CellValueSource cellValueSource){
		Column column = new Column();
		column.setCellValueSource(cellValueSource);
		column.setLabelFromIdentifier(labelStringIdentifier);
		column.getPropertiesMap().setHeaderText(column.getLabel().getPropertiesMap().getValue());
		column.getPropertiesMap().setHeader(column.getLabel());
		column.getPropertiesMap().setSortable(Boolean.FALSE);
		
		OutputText footer = new OutputText();
		column.getPropertiesMap().setFooter(footer);
		
		column.getPropertiesMap().setFieldName(fieldName);
		column.addPropertyStyleClass(CascadeStyleSheetHelper.getInstance().getClass(fieldName.toLowerCase()));
		return column;
	}
	
	public static Column instanciateOne(String labelStringIdentifier,String fieldName){
		return instanciateOne(labelStringIdentifier, fieldName, CellValueSource.DEFAULT);
	}

	/**/
	
	public void __setPropertyFooterPropertyValueBasedOnMaster__(){
		Column column = this;
		LoggingHelper.Message.Builder loggingMessageBuilder = new LoggingHelper.Message.Builder.Adapter.Default();
		loggingMessageBuilder.addManyParameters("set column footer value");
		DataTable dataTable = (DataTable) column.getPropertiesMap().getDataTable();
		loggingMessageBuilder.addNamedParameters("field name",column.getPropertiesMap().getFieldName(),"master exist",dataTable.getPropertiesMap().getMaster()!=null);
		if(dataTable.getPropertiesMap().getMaster()!=null){
			java.lang.reflect.Field field = FieldHelper.getInstance().get(dataTable.getPropertiesMap().getMaster().getClass(), (String)column.getPropertiesMap().getFieldName());
			loggingMessageBuilder.addNamedParameters("field exist",field!=null);
			if(field!=null){
				loggingMessageBuilder.addNamedParameters("before set",((OutputText)column.getPropertiesMap().getFooter()).getPropertiesMap().getValue());
				((OutputText)column.getPropertiesMap().getFooter()).getPropertiesMap().setValue(FieldHelper.getInstance()
						.read(dataTable.getPropertiesMap().getMaster(), (String)column.getPropertiesMap().getFieldName()));
				loggingMessageBuilder.addNamedParameters("after set",((OutputText)column.getPropertiesMap().getFooter()).getPropertiesMap().getValue());
			}
		}
		logTrace(loggingMessageBuilder);
	}
	
	/**/
	
	public static interface BuilderBase<OUTPUT extends Column> extends Dimension.BuilderBase<OUTPUT> {

		CellValueSource getCellValueSource();
		BuilderBase<OUTPUT> setCellValueSource(CellValueSource cellValueSource);
		
		@Getter @Setter
		public static class Adapter<OUTPUT extends Column> extends Dimension.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
			private static final long serialVersionUID = 1L;

			protected CellValueSource cellValueSource;
			
			/**/

			public static class Default<OUTPUT extends Column> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
				
				@Override
				public BuilderBase<OUTPUT> setCellValueSource(CellValueSource cellValueSource) {
					this.cellValueSource = cellValueSource;
					return this;
				}
			}
			
			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}
			
			@Override
			public BuilderBase<OUTPUT> setCellValueSource(CellValueSource cellValueSource) {
				return null;
			}
		}
	}
	
	public static interface Builder extends BuilderBase<Column> {

		public static class Adapter extends BuilderBase.Adapter.Default<Column> implements Builder, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter() {
				super(Column.class);
			}

			/**/

			public static class Default extends Builder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				@Override
				protected Column __execute__() {
					Column column = new Column();
					column.setCellValueSource(getCellValueSource());
					//column.setLabelFromIdentifier(labelStringIdentifier);
					column.getPropertiesMap().setAction(getPropertiesMap().getAction());
					column.getPropertiesMap().setActionOnClass(getPropertiesMap().getActionOnClass());
					column.getPropertiesMap().setCellListener(getPropertiesMap().getCellListener());
					column.getPropertiesMap().setDataTable(getPropertiesMap().getDataTable());
					column.getPropertiesMap().setFieldName(getPropertiesMap().getFieldName());
					column.getPropertiesMap().setField(getPropertiesMap().getField());
					/*
					column.getPropertiesMap().copyFrom(getPropertiesMap(), Properties.ACTION,Properties.ACTION_ON_CLASS,Properties.CELL_LISTENER,Properties.DATA_TABLE
							,Properties.FIELD_NAME);
					*/
					//field name
					column.getPropertiesMap().setFieldName(getPropertiesMap().getFieldName());
					if(column.getPropertiesMap().getFieldName() == null){
						if(column.getPropertiesMap().getField() instanceof java.lang.reflect.Field)
							column.getPropertiesMap().setFieldName( ((java.lang.reflect.Field)column.getPropertiesMap().getField()).getName() );
					}else {
						if(getPropertiesMap().getField() == null)
							if(column.getPropertiesMap().getActionOnClass() instanceof Class<?>)
								column.getPropertiesMap().setField(FieldHelper.getInstance().get((Class<?>)column.getPropertiesMap().getActionOnClass()
										,(String)column.getPropertiesMap().getFieldName()));
					}
					
					//header
					column.getPropertiesMap().setHeader(getPropertiesMap().getHeader());
					if(column.getPropertyHeaderPropertyValue() == null){
						if(column.getPropertiesMap().getField() instanceof java.lang.reflect.Field){
							column.setPropertyHeaderPropertyValue(StringHelper.getInstance().getField((java.lang.reflect.Field)column.getPropertiesMap().getField()));
						}
					}
					//column.getPropertiesMap().setHeader(column.getLabel());
					//column.getPropertiesMap().setHeaderText(column.getLabel().getPropertiesMap().getValue());
					
					if(column.getCellValueSource() == null){
						column.setCellValueSource(Column.CellValueSource.DEFAULT);
					}
					
					column.getPropertiesMap().setSortable(Boolean.FALSE);
					
					OutputText footer = new OutputText();
					column.getPropertiesMap().setFooter(footer);
					
					//cascade style sheet
					if(column.getPropertiesMap().getFieldName()!=null){
						column.addPropertyStyleClass(CascadeStyleSheetHelper.getInstance().getClass(column.getPropertiesMap().getFieldName().toString().toLowerCase()));	
					}
					
					if(column.getPropertiesMap().getActionOnClass()!=null)
						column.computeCellValueType((Class<?>)column.getPropertiesMap().getActionOnClass(), Boolean.TRUE);
					return column;
				}
				
			}
		}
	}
	
	/**/
	
	public static interface Listener {
		
		public static class Adapter extends AbstractBean implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
			
			}				
		}
	}
}