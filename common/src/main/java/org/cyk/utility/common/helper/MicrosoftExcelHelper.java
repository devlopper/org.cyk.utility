package org.cyk.utility.common.helper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
import org.cyk.utility.common.helper.ArrayHelper.Dimension.Key;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

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
						
						public Default(java.lang.String filePath) {
							this(FileHelper.getInstance().getInputStream(filePath));
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
			private Object[][] ignoreds;
			//private Table.Default<String> table = new Table.Default<>(String.class);
			
			public static interface Builder extends org.cyk.utility.common.Builder<Workbook, Sheet> {
			
				java.lang.String getSheetName();
				Builder setSheetName(java.lang.String name);
				Builder setSheetName(Class<?> aClass);
				
				Integer getSheetIndex();
				Builder setSheetIndex(Integer index);
				
				Matrix getMatrix();
				Builder setMatrix(Matrix matrix);
				Builder createMatrix();
				
				@Getter @Setter
				public static class Adapter extends org.cyk.utility.common.Builder.Adapter.Default<Workbook, Sheet> implements Builder,Serializable {
					private static final long serialVersionUID = 1L;
					
					protected java.lang.String sheetName;
					protected Integer sheetIndex;
					protected Matrix matrix;
					
					public Adapter(Workbook workbook) {
						super(Workbook.class, workbook, Sheet.class);
					}
					
					@Override
					public Builder setMatrix(Matrix matrix){
						return null;
					}
					
					@Override
					public Builder createMatrix() {
						return null;
					}
					
					@Override
					public Builder setSheetName(java.lang.String sheetName){
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
					
					public static class Default extends Builder.Adapter implements Serializable {
						private static final long serialVersionUID = 1L;
						
						public Default(Workbook workbook) {
							super(workbook);
						}
						
						public Default(java.lang.String filePath,Integer sheetIndex) {
							this(new MicrosoftExcelHelper.Workbook.Builder.InputStream.Adapter.Default(filePath).execute());
							setSheetIndex(sheetIndex);
						}
						
						public Default(java.lang.String filePath,Class<?> aClass) {
							this(new MicrosoftExcelHelper.Workbook.Builder.InputStream.Adapter.Default(filePath).execute());
							setSheetName(aClass);
						}
						
						public Default(java.io.InputStream fileInputStream,Class<?> aClass) {
							this(new MicrosoftExcelHelper.Workbook.Builder.InputStream.Adapter.Default(fileInputStream).execute());
							setSheetName(aClass);
						}
						
						@Override
						public Builder setMatrix(Matrix matrix) {
							this.matrix = matrix;
							return this;
						}
						
						@Override
						public Builder createMatrix() {
							Matrix matrix = new Matrix();
							setMatrix(matrix);
							return this;
						}
						
						@Override
						public Builder setSheetName(java.lang.String sheetName){
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
						protected Sheet __execute__() {
							Sheet sheet = new Sheet();
							sheet.setWorkbook(getInput());
							Integer index = getSheetIndex();
							java.lang.String name = getSheetName();
							sheet.setModel(StringUtils.isBlank(name) ? getInput().getModel().getSheetAt(index) : getInput().getModel().getSheet(name));
							
							if(sheet.getModel()==null){
								logWarning("No sheet named <<{}>> or at index <<{}>> found", name,index);
					        }else{
					        	Matrix matrix = getMatrix();
					        	if(matrix==null)
					        		matrix = new Matrix();
					        	
					        	addLoggingMessageBuilderNamedParameters("sheet", sheet.getModel().getSheetName());
					        	FormulaEvaluator formulaEvaluator = sheet.getWorkbook().getModel().getCreationHelper().createFormulaEvaluator();
					        	Integer fromRowIndex = matrix.getRow().getFromIndex(),fromColumnIndex = matrix.getColumn().getFromIndex();
					            if(fromRowIndex==null)
					            	fromRowIndex = 0;
					            if(fromColumnIndex==null)
					            	fromColumnIndex = 0;
					        	Integer rowCount = matrix.getRow().getNumberOfIndexes(),columnCount = matrix.getColumn().getNumberOfIndexes();
					            if(rowCount==null)
					            	rowCount = sheet.getModel().getPhysicalNumberOfRows();
					            if(columnCount==null)
					            	columnCount = new Integer(sheet.getModel().getRow(0).getLastCellNum());
					          
					            if(matrix.getRow().getIgnoredKeys()!=null){
					            	if(matrix.getRow().getIgnoreds()==null)
					            		matrix.getRow().setIgnoreds(new ArrayList<Object[]>());
					            }
								
								ListenerHelper.Executor.Function.Adapter.Default.Boolean<DimensionSelectable> rowSelectableExecutor = matrix.getRow().getSelectableExecutor();
								if(rowSelectableExecutor==null){
									rowSelectableExecutor = new ListenerHelper.Executor.Function.Adapter.Default.Boolean<DimensionSelectable>();
									rowSelectableExecutor.setResultMethod(ClassHelper.getInstance().instanciateOne(DimensionSelectable.Adapter.Default.RESULT_METHOD_CLASS));
									rowSelectableExecutor.setInput((Collection<DimensionSelectable>) ClassHelper.getInstance().instanciateMany(DimensionSelectable.class
											,CollectionHelper.getInstance().isEmpty(DimensionSelectable.CLASSES) ? Arrays.asList(DimensionSelectable.Adapter.Default.class) : DimensionSelectable.CLASSES));
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
					                        	java.lang.String stringValue;
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
					                }
					                if(_row==null)
					                	;
					                else{
					                	Boolean select = Boolean.TRUE;
					                	if(matrix.getRow().getKeyBuilder()!=null && matrix.getRow().getIgnoredKeys()!=null){ 
					                		Key key = matrix.getRow().getKeyBuilder().setInput(_row).execute();
					                		select = !CollectionHelper.getInstance().contains(matrix.getRow().getIgnoredKeys(), key);
					                	}
					                	if(Boolean.TRUE.equals(select)){
					                		rowSelectableExecutor.getResultMethod().setParameters(CollectionHelper.getInstance().get(_row));
						                	if(Boolean.TRUE.equals(rowSelectableExecutor.execute())){
						                		rowCollection.add(_row);
						                	}
					                	}
					                	
					                	if(!Boolean.TRUE.equals(select) && matrix.getRow().getIgnoredKeys()!=null){
					                		CollectionHelper.getInstance().add(matrix.getRow().getIgnoreds(), _row);
					                	}
					                }
					                
					            	if(!CollectionHelper.getInstance().isEmpty(rowCollection)){
					            		sheet.values = new Object[rowCollection.size()][columnCount];
					            		for(int g = 0 ; g < rowCollection.size() ; g++)
					            			sheet.values[g] = rowCollection.get(g);
					            	}
					            	
					            	if(!CollectionHelper.getInstance().isEmpty(matrix.getRow().getIgnoreds())){
					            		sheet.ignoreds = new Object[matrix.getRow().getIgnoreds().size()][columnCount];
					            		for(int g = 0 ; g < matrix.getRow().getIgnoreds().size() ; g++)
					            			sheet.ignoreds[g] = matrix.getRow().getIgnoreds().get(g);
					            	}
					            }	
					            addLoggingMessageBuilderNamedParameters("#row",rowCount);   
					        }
							addLoggingMessageBuilderNamedParameters("#selected",ArrayHelper.getInstance().size(sheet.getValues()), "#ignored",ArrayHelper.getInstance().size(sheet.getIgnoreds()));					        
					        try {
								sheet.getWorkbook().getModel().close();
							} catch (IOException e) {
								e.printStackTrace();
							}	
							return sheet;
						}
					}
				}
				
				public static interface DimensionSelectable extends Action<Object, java.lang.Boolean> {
					
					Collection<Class<? extends DimensionSelectable>> CLASSES = new ArrayList<>();
					
					public static class Adapter extends Action.Adapter.Default<Object, java.lang.Boolean> implements DimensionSelectable,Serializable {
						private static final long serialVersionUID = 1L;
						
						public Adapter(Object value) {
							super("select", Object.class, value, java.lang.Boolean.class);
						}
						
						public static class Default extends DimensionSelectable.Adapter implements Serializable {
							private static final long serialVersionUID = 1L;
							
							@SuppressWarnings("unchecked")
							public static Class<ListenerHelper.Executor.ResultMethod<java.lang.Boolean, DimensionSelectable>> RESULT_METHOD_CLASS = (Class<org.cyk.utility.common.helper.ListenerHelper.Executor.ResultMethod<Boolean, DimensionSelectable>>) ClassHelper.getInstance().getByName(ResultMethod.class);
							
							public Default(Object value) {
								super(value);
							}
							
							public Default() {
								this(null);
							}
							
							
						}
					}
					
					public static class ResultMethod extends ListenerHelper.Executor.ResultMethod.Adapter.Default.Boolean<DimensionSelectable> {
						private static final long serialVersionUID = 1L;

						@Override
						protected java.lang.Boolean __execute__() {
							return !CollectionHelper.getInstance().isBlank(getParameters());
						}
					}
				}
				
				@Getter @Setter @Accessors(chain=true)
				public static class Dimension implements Serializable {
					private static final long serialVersionUID = 1L;
					
					private Integer fromIndex,toIndex,numberOfIndexes;
					private ArrayHelper.Dimension.Key.Builder keyBuilder;
					private Collection<ArrayHelper.Dimension.Key> ignoredKeys;
					private List<Object[]> ignoreds;
					private ListenerHelper.Executor.Function.Adapter.Default.Boolean<DimensionSelectable> selectableExecutor;
					
					public Dimension createKeyBuilder(Collection<Object> indexes,Collection<java.lang.String> ignoredKeyValues){
						keyBuilder = new ArrayHelper.Dimension.Key.Builder.Adapter.Default();
						keyBuilder.addParameters(indexes);
						ignoredKeys = new ArrayList<>();
						if(ignoredKeys!=null)
							for(java.lang.String keyValue : ignoredKeyValues)
								ignoredKeys.add(new ArrayHelper.Dimension.Key(keyValue));
						return this;
					}
					
					public Dimension createKeyBuilder(Object[] indexes,java.lang.String[] ignoredKeyValues){
						return createKeyBuilder(Arrays.asList(indexes), Arrays.asList(ignoredKeyValues));
					}
					
					public Dimension createKeyBuilder(Object[] indexes,Collection<java.lang.String> ignoredKeyValues){
						return createKeyBuilder(Arrays.asList(indexes), ignoredKeyValues);
					}
					
					public Dimension addIgnoredKeyValues(Collection<?> values){
						if(!CollectionHelper.getInstance().isEmpty(values)){
							if(ignoredKeys==null)
								ignoredKeys = new ArrayList<>();
							for(Object value : values)
								ignoredKeys.add(new ArrayHelper.Dimension.Key(value.toString()));
						}
						return this;
					}
					
					@Override
					public java.lang.String toString() {
						Collection<java.lang.String> collection = new ArrayList<>();
						if(fromIndex!=null)
							collection.add("from:"+fromIndex);
						if(numberOfIndexes!=null)
							collection.add("numberOfIndexes:"+numberOfIndexes);
						return StringHelper.getInstance().get(collection, " ");
					}
				}
				
				@Getter @Setter @Accessors(chain=true)
				public static class Matrix implements Serializable {
					private static final long serialVersionUID = 1L;
					
					private Dimension row = new Dimension(),column = new Dimension();
					
					@Override
					public java.lang.String toString() {
						Collection<java.lang.String> collection = new ArrayList<>();
						if(row!=null)
							collection.add("row:"+row);
						if(column!=null)
							collection.add("column:"+column);
						return StringHelper.getInstance().get(collection, " | ");
					}
				}
			}
		
			/**/
			
			
		}
		
		
	}
}
