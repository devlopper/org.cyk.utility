package org.cyk.utility.common.helper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.cyk.utility.common.Action;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.helper.InstanceHelper.Setter.ProcessValue;
import org.cyk.utility.common.model.table.Table;

import lombok.Getter;
import lombok.Setter;

@Singleton
public class MicrosoftExcelHelper extends AbstractHelper implements Serializable  {

	private static final long serialVersionUID = 1L;

	private static MicrosoftExcelHelper INSTANCE;
	
	public static MicrosoftExcelHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new MicrosoftExcelHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	/**/
	
	@Getter @Setter
	public static class Workbook implements Serializable {
		private static final long serialVersionUID = 1L;
	
		private org.apache.poi.ss.usermodel.Workbook model;
		
		public static interface Builder<INPUT> extends org.cyk.utility.common.Builder<INPUT,Workbook>{
			
			@Getter @Setter
			public static class Adapter<INPUT> extends org.cyk.utility.common.Builder.Adapter.Default<INPUT,Workbook> implements Builder<INPUT>,Serializable {
				private static final long serialVersionUID = 1L;
				
				public Adapter(Class<INPUT> inputClass,INPUT input) {
					super(inputClass,input,Workbook.class);
				}
				
				public static class Default<INPUT> extends Builder.Adapter<INPUT> implements Serializable {
					private static final long serialVersionUID = 1L;
					
					public Default(Class<INPUT> inputClass,INPUT input) {
						super(inputClass,input);
					}
					
				}
			}
		
			public static interface InputStream extends Builder<java.io.InputStream>{
				
				InputStream setInput(java.io.File file);
				
				public static class Adapter extends Builder.Adapter.Default<java.io.InputStream> implements InputStream,Serializable {
					private static final long serialVersionUID = 1L;
					
					public Adapter(java.io.InputStream stream) {
						super(java.io.InputStream.class,stream);
					}
					
					@Override
					public InputStream setInput(java.io.File file){
						return null;
					}
					
					public static class Default extends InputStream.Adapter implements Serializable {
						private static final long serialVersionUID = 1L;
						
						public Default(java.io.InputStream stream) {
							super(stream);
						}
						
						@Override
						public InputStream setInput(java.io.File file){
							setInput(new ByteArrayInputStream(FileHelper.getInstance().getBytes(file)));
							return this;
						}
						
						@Override
						protected Workbook __execute__() {
							Workbook workbook = null;
							try {
								org.apache.poi.ss.usermodel.Workbook model = WorkbookFactory.create(getInput());
								workbook = new Workbook();
								workbook.setModel(model);
							} catch (Exception e) {
								throw new RuntimeException(e);
							}
							return workbook;
						}
					}
				}
				
			}
			
		}
		
		@Getter @Setter
		public static class Sheet implements Serializable {
			private static final long serialVersionUID = 1L;
			
			private Workbook workbook;
			private org.apache.poi.ss.usermodel.Sheet model;
			private Object[][] values;
			private Table.Default<String> table = new Table.Default<>(String.class);
			
			public static interface Builder extends org.cyk.utility.common.Builder<Workbook, Sheet> {
			
				String getSheetName();
				Builder setSheetName(String name);
				Builder setSheetName(Class<?> aClass);
				
				Integer getSheetIndex();
				Builder setSheetIndex(Integer index);
				
				Integer getFromRowIndex();
				Builder setFromRowIndex(Integer fromRowIndex);
				
				Integer getRowCount();
				Builder setRowCount(Integer rowCount);
				
				Integer getFromColumnIndex();
				Builder setFromColumnIndex(Integer fromColumnIndex);
				
				Integer getColumnCount();
				Builder setColumnCount(Integer columnCount);
				
				ListenerHelper.Executor.Function.Adapter.Default.Boolean<RowSelectable> getRowSelectableExecutor();
				Builder setRowSelectableExecutor(ListenerHelper.Executor.Function.Adapter.Default.Boolean<RowSelectable> rowSelectableExecutor);
				
				
				@Getter @Setter
				public static class Adapter extends org.cyk.utility.common.Builder.Adapter.Default<Workbook, Sheet> implements Builder,Serializable {
					private static final long serialVersionUID = 1L;
					
					protected String sheetName;
					protected Integer sheetIndex,fromRowIndex,rowCount,fromColumnIndex,columnCount;
					protected ListenerHelper.Executor.Function.Adapter.Default.Boolean<RowSelectable> rowSelectableExecutor;
					
					public Adapter(Workbook workbook) {
						super(Workbook.class, workbook, Sheet.class);
					}
					
					@Override
					public Builder setRowSelectableExecutor(ListenerHelper.Executor.Function.Adapter.Default.Boolean<RowSelectable> rowSelectableExecutor){
						return null;
					}
					
					@Override
					public Builder setSheetName(String sheetName){
						return null;
					}
					
					@Override
					public Builder setSheetIndex(Integer sheetIndex){
						return null;
					}
					
					@Override
					public Builder setSheetName(Class<?> aClass) {
						return null;
					}

					@Override
					public Builder setFromRowIndex(Integer fromRowIndex) {
						return null;
					}

					@Override
					public Builder setRowCount(Integer rowCount) {
						return null;
					}

					@Override
					public Builder setFromColumnIndex(Integer fromColumnIndex) {
						return null;
					}

					@Override
					public Builder setColumnCount(Integer columnCount) {
						return null;
					}
				
					public static class Default extends Builder.Adapter implements Serializable {
						private static final long serialVersionUID = 1L;
						
						public Default(Workbook workbook) {
							super(workbook);
						}
						
						@Override
						public Builder setRowSelectableExecutor(ListenerHelper.Executor.Function.Adapter.Default.Boolean<RowSelectable> rowSelectableExecutor) {
							this.rowSelectableExecutor = rowSelectableExecutor;
							return this;
						}
						
						@Override
						public Builder setSheetName(String sheetName){
							this.sheetName = sheetName;
							return this;
						}
						
						@Override
						public Builder setSheetIndex(Integer sheetIndex){
							this.sheetIndex = sheetIndex;
							return this;
						}
						
						@Override
						public Builder setSheetName(Class<?> aClass) {
							return setSheetName(aClass.getSimpleName());
						}

						@Override
						public Builder setFromRowIndex(Integer fromRowIndex) {
							this.fromRowIndex = fromRowIndex;
							return this;
						}

						@Override
						public Builder setRowCount(Integer rowCount) {
							this.rowCount = rowCount;
							return this;
						}

						@Override
						public Builder setFromColumnIndex(Integer fromColumnIndex) {
							this.fromColumnIndex = fromColumnIndex;
							return this;
						}

						@Override
						public Builder setColumnCount(Integer columnCount) {
							this.columnCount = columnCount;
							return this;
						}
						
						@Override
						protected Sheet __execute__() {
							Sheet sheet = new Sheet();
							sheet.setWorkbook(getInput());
							Integer index = getSheetIndex();
							String name = getSheetName();
							sheet.setModel(StringUtils.isBlank(name) ? getInput().getModel().getSheetAt(index) : getInput().getModel().getSheet(name));
							
							if(sheet.getModel()==null){
								logWarning("No sheet named <<{}>> or at index <<{}>> found", name,index);
					        }else{
					        	addLoggingMessageBuilderNamedParameters("sheet", sheet.getModel().getSheetName());
					        	FormulaEvaluator formulaEvaluator = sheet.getWorkbook().getModel().getCreationHelper().createFormulaEvaluator();
					        	Integer fromRowIndex = getFromRowIndex(),fromColumnIndex = getFromColumnIndex();
					            if(fromRowIndex==null)
					            	fromRowIndex = 0;
					            if(fromColumnIndex==null)
					            	fromColumnIndex = 0;
					        	Integer rowCount = getRowCount(),columnCount = getColumnCount();
					            if(rowCount==null)
					            	rowCount = sheet.getModel().getPhysicalNumberOfRows();
					            if(columnCount==null)
					            	columnCount = new Integer(sheet.getModel().getRow(0).getLastCellNum());
					          
					            CollectionHelper collectionHelper = new CollectionHelper();
								
								ListenerHelper.Executor.Function.Adapter.Default.Boolean<RowSelectable> rowSelectableExecutor = getRowSelectableExecutor();
								if(rowSelectableExecutor==null){
									rowSelectableExecutor = new ListenerHelper.Executor.Function.Adapter.Default.Boolean<RowSelectable>();
									rowSelectableExecutor.setResultMethod(RowSelectable.Adapter.Default.RESULT_METHOD);
									rowSelectableExecutor.setInput((Collection<RowSelectable>) new ClassHelper().instanciateMany(RowSelectable.class
											,collectionHelper.isEmpty(RowSelectable.CLASSES) ? Arrays.asList(RowSelectable.Adapter.Default.class) : RowSelectable.CLASSES));
								}
					            
					            List<Object[]> rowCollection = new ArrayList<>(); 
					            for (int i=0; i<rowCount; i++) {
					            	Row row = sheet.getModel().getRow(i + fromRowIndex);
					            	Object[] _row = null;
					            	if(row==null){
					                	
					                }else{
					                	_row = new Object[columnCount];
					                	for (int j=0; j<columnCount; j++) {
					                        Cell cell = row.getCell(j+fromColumnIndex);
					                        if(cell==null)
					                        	_row[j] = Constant.EMPTY_STRING;
					                        else{
					                        	CellValue cellValue = formulaEvaluator.evaluate(cell);
					                        	String stringValue;
					                        	if(cellValue==null)
					                        		stringValue = Constant.EMPTY_STRING;
					                        	else switch(cellValue.getCellType()){
					    	    	                case Cell.CELL_TYPE_FORMULA : 
					    	    	                	throw new RuntimeException("Must never happen! Cannot process a formula. Please change field to result of formula.("+i+","+j+")");
					    	    	                case Cell.CELL_TYPE_BLANK: stringValue = Constant.EMPTY_STRING; break;
					    	    	                case Cell.CELL_TYPE_NUMERIC: 
					    	    	                	if(DateUtil.isCellDateFormatted(cell))
					    	                        		stringValue = Constant.DATE_TIME_FORMATTER.format(cell.getDateCellValue());
					    	                        	else
					    	                        		stringValue = /*cellValue.getStringValue();*/ new BigDecimal(cellValue.getNumberValue()).toString(); //cellValue.getNumberValue()); 
					    	    	                	break;
					    	    	                case Cell.CELL_TYPE_STRING: stringValue = cellValue.getStringValue(); break;
					    	    	                default:
					    	    	                	stringValue = StringUtils.trim(cellValue.getStringValue());
					    	    	                	break;
					    	    	                }
					                        	_row[j] = StringUtils.defaultIfBlank(stringValue,Constant.EMPTY_STRING);
					                        }
					                    }
					                	
					                	//listenAfterRowCreated(_row);
					                }
					                if(_row==null)
					                	;
					                else{
					                	rowSelectableExecutor.getResultMethod().setParameters(CollectionHelper.getInstance().get(_row));
					                	if(Boolean.TRUE.equals(rowSelectableExecutor.execute())){
					                		//_row.setPrimaryKey(getPrimaryKey(i, _row.getValues()));
					                		//getOutput().add(_row);	
					                		rowCollection.add(_row);
					                		//listenAfterRowAdded(_row);
					                	}
					                }
					                
					            	if(!CollectionHelper.getInstance().isEmpty(rowCollection)){
					            		sheet.values = new Object[rowCollection.size()][columnCount];
					            		for(int g = 0 ; g < rowCollection.size() ; g++)
					            			sheet.values[g] = rowCollection.get(g);
					            	}
					            	
					            	
					            }	
					            addLoggingMessageBuilderNamedParameters("#row",rowCount);   
					        }
					        
							addLoggingMessageBuilderNamedParameters("#selected",sheet.getValues().length/*, "#ignored",numberOfNotAdded*/);
					        
					        try {
								sheet.getWorkbook().getModel().close();
							} catch (IOException e) {
								e.printStackTrace();
							}
							
							return sheet;
						}
						
					}

					
				}
				
				public static interface RowSelectable extends Action<Object, java.lang.Boolean> {
					
					Collection<Class<? extends RowSelectable>> CLASSES = new ArrayList<>();
					
					public static class Adapter extends Action.Adapter.Default<Object, java.lang.Boolean> implements RowSelectable,Serializable {
						private static final long serialVersionUID = 1L;
						
						public Adapter(Object value) {
							super("select", Object.class, value, java.lang.Boolean.class);
						}
						
						public static class Default extends RowSelectable.Adapter implements Serializable {
							private static final long serialVersionUID = 1L;
							
							public static ListenerHelper.Executor.ResultMethod<java.lang.Boolean, RowSelectable> RESULT_METHOD = new ResultMethod();
							
							public Default(Object value) {
								super(value);
							}
							
							public Default() {
								this(null);
							}
							
							
						}
					}
					
					public static class ResultMethod extends ListenerHelper.Executor.ResultMethod.Adapter.Default.Boolean<RowSelectable> {
						private static final long serialVersionUID = 1L;

						@Override
						protected java.lang.Boolean __execute__() {
							return !CollectionHelper.getInstance().isBlank(getParameters());
						}
					}
				}
			}
		
			/**/
			
			
		}
		
		
	}
}
