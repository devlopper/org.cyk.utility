package org.cyk.utility.common.userinterface.collection;

import java.io.Serializable;

import org.cyk.utility.common.Constant;
import org.cyk.utility.common.Constant.Action;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.ContentType;
import org.cyk.utility.common.userinterface.Image;
import org.cyk.utility.common.userinterface.input.Input;
import org.cyk.utility.common.userinterface.output.Output;
import org.cyk.utility.common.userinterface.output.OutputFile;
import org.cyk.utility.common.userinterface.output.OutputText;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @EqualsAndHashCode(callSuper=false,of={Cell.FIELD_COLUMN,Cell.FIELD_ROW})
public class Cell extends Component.Visible implements Serializable {
	private static final long serialVersionUID = 1L;
	
	static {
		ClassHelper.getInstance().map(Cell.Listener.class, Cell.Listener.Adapter.Default.class, Boolean.FALSE);
	}
	
	public static enum ValueType {TEXT,LINK,MENU,FILE,IMAGE,INPUT; public static ValueType DEFAULT = ValueType.TEXT;}
	
	private Column column;
	private Row row;
	private Input<?> input;
	/**/
	
	public static Cell instanciateOne(Column column,Row row,Cell.Listener listener){
		return (listener == null ? ClassHelper.getInstance().instanciateOne(Cell.Listener.class) : listener).instanciateOne(column, row);
	}
	
	public static Cell getOne(Column column,Row row,Cell.Listener listener){
		Cell cell = null;
		if(CollectionHelper.getInstance().isNotEmpty(column.cells))
			for(Cell index : column.cells)
				if(index.column.equals(column) && index.row.equals(row)) {
					cell = index;
					break;
				}
		if(cell==null){
			column.addOneCell(cell = Cell.instanciateOne(column, row,listener));
			row.addOneCell(cell);
		}
		return cell;
	}
	
	/**/
	
	public static final String FIELD_COLUMN = "column";
	public static final String FIELD_ROW = "row";
	
	/**/
	
	public static interface Listener {
		
		Cell instanciateOne(Column column,Row row);
		
		public static class Adapter extends AbstractBean implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
			
			@Override
			public Cell instanciateOne(Column column, Row row) {
				return null;
			}
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				public Cell instanciateOne(Column column,Row row){
					if(row.getPropertiesMap().getValue()!=null)
						column.computeCellValueType(row.getPropertiesMap().getValue().getClass(),Boolean.TRUE);
					Cell cell = new Cell().setColumn(column).setRow(row);
					Output output = null;
					/*if(row==null){
						value = "ROW IS NULL";
					}*/
					if(row!=null){
						String fieldName = (String) column.getPropertiesMap().getFieldName();
						if(StringHelper.getInstance().isBlank(fieldName)){
							output = new OutputText();
							output.getPropertiesMap().setValue("NO FIELD NAME");
						}else {
							if(column.getCellValueSource()==null)
								;
							else
								switch(column.getCellValueSource()){
								case ROW : 
									output = new Output(); 
									output.getPropertiesMap().setValue(FieldHelper.getInstance().read(row, fieldName)); 
									break;
								case ROW_PROPERTIES_MAP : 
									output = new Output(); 
									output.getPropertiesMap().setValue(row.getPropertiesMap().get(fieldName)); 
									break;
								case ROW_PROPERTY_VALUE:
									FieldHelper.Constraints constraints = FieldHelper.Field.get(row.getPropertiesMap().getValue().getClass(), fieldName).getConstraints();
									Object object = FieldHelper.getInstance().readBeforeLast(row.getPropertiesMap().getValue(), fieldName);
									java.lang.reflect.Field field = FieldHelper.getInstance().getLast(object.getClass(),fieldName);
									output = Output.getListener().get(null,object, field,constraints); 
									
									if(column.getPropertiesMap().getAction() instanceof Constant.Action){
										Constant.Action action = (Action) column.getPropertiesMap().getAction();
										if(Constant.Action.isCreateOrUpdate(action) && ValueType.INPUT.equals(column.getCellValueType())){
											cell.input = Input.get(null, object, field,constraints);
											cell.input.getPropertiesMap().setWatermark(null);
										}
									}
									/*
									if(column.inputClass == null){
										column.inputClass = (Class<Input<?>>) Input.getListener().getClass(null, object, FieldHelper.getInstance()
												.get(row.getPropertiesMap().getValue().getClass(), fieldName));
									}
									
									if(column.getInputClass()!=null){
										cell.input = ClassHelper.getInstance().instanciateOne(column.getInputClass());
									}
									*/
									break;
								}	
						}	
					}
					if(output instanceof OutputFile){
						Image thumbnail = (Image) ((OutputFile) output).getPropertiesMap().getThumbnail();
						if(ContentType.HTML.equals(Component.RENDER_AS_CONTENT_TYPE)){
							thumbnail.getPropertiesMap().setWidth("20px").setHeight("20px");
						}
					}
					
					cell.getPropertiesMap().setValue(output);
					if(output instanceof OutputFile){
						
					}else{
						cell.getPropertiesMap().setSortBy(output.getPropertiesMap().getValue());
						cell.getPropertiesMap().setFilterBy(output.getPropertiesMap().getValue());
					}
					
					return cell;
				}
				
			}
			
		}
	}
}
