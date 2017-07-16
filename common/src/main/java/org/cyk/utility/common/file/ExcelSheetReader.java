package org.cyk.utility.common.file;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.cyk.utility.common.Constant;

import lombok.Getter;

@Deprecated
public interface ExcelSheetReader extends ArrayReader.TwoDimension<String> {
	
	byte[] getWorkbookBytes();
	ExcelSheetReader setWorkbookBytes(byte[] workbookBytes);
	ExcelSheetReader setWorkbookBytes(InputStream workbookInputStream) throws IOException;
	
	Integer getIndex();
	ExcelSheetReader setIndex(Integer index);
	
	String getSheetName();
	ExcelSheetReader setSheetName(String sheetName);
	ExcelSheetReader setSheetName(Class<?> aClass);
	
	Integer getFromRowIndex();
	ExcelSheetReader setFromRowIndex(Integer fromRowIndex);
	
	Integer getRowCount();
	ExcelSheetReader setRowCount(Integer rowCount);
	
	Integer getFromColumnIndex();
	ExcelSheetReader setFromColumnIndex(Integer fromColumnIndex);
	
	Integer getColumnCount();
	ExcelSheetReader setColumnCount(Integer columnCount);
	
	//String processAfterCellRead(Integer rowIndex,Integer columnIndex,String value);
	
	Boolean isRowAddable(Dimension.Row<String> row);
	
	//Collection<String[]> getValues();
	
	@Getter @Deprecated
	public static class Adapter extends TwoDimension.Adapter.Default<String> implements ExcelSheetReader,Serializable {
		private static final long serialVersionUID = 1L;

		private byte[] workbookBytes;
		private String sheetName;
		private Integer index,fromRowIndex,rowCount,fromColumnIndex,columnCount;
		
		public Adapter(File file) {
			super(file);
		}
		
		public static List<String[]> getValues(Collection<Dimension.Row<String>> rows) {
			List<String[]> collection = new ArrayList<>();
			for(Dimension.Row<String> row : rows)
				collection.add(row.getValues());
			return collection;
		}
		
		@Override
		public Boolean isRowAddable(Dimension.Row<String> row) {
			return null;
		}
		
		@Override
		public ExcelSheetReader setWorkbookBytes(byte[] workbookBytes) {
			this.workbookBytes = workbookBytes;
			return this;
		}
		
		@Override
		public ExcelSheetReader setWorkbookBytes(InputStream workbookInputStream) throws IOException {
			return setWorkbookBytes(IOUtils.toByteArray(workbookInputStream));
		}
		
		@Override
		public ExcelSheetReader setColumnCount(Integer columnCount) {
			this.columnCount = columnCount;
			return this;
		}
		
		@Override
		public ExcelSheetReader setFromColumnIndex(Integer fromColumnIndex) {
			this.fromColumnIndex = fromColumnIndex;
			return this;
		}
		
		@Override
		public ExcelSheetReader setFromRowIndex(Integer fromRowIndex) {
			this.fromRowIndex = fromRowIndex;
			return this;
		}
		
		@Override
		public ExcelSheetReader setIndex(Integer index) {
			this.index = index;
			return this;
		}
		
		@Override
		public ExcelSheetReader setSheetName(String sheetName) {
			this.sheetName = sheetName;
			return this;
		}
		
		@Override
		public ExcelSheetReader setSheetName(Class<?> aClass) {
			return setSheetName(aClass.getSimpleName());
		}
		
		@Override
		public ExcelSheetReader setRowCount(Integer rowCount) {
			this.rowCount = rowCount;
			return this;
		}
		
		/**/
		@Deprecated
		public static class Default extends ExcelSheetReader.Adapter implements Serializable {
			private static final long serialVersionUID = 1L;
			
			protected Integer numberOfNotAdded=0;
			
			public Default(File file) {
				super(file);
				try {
					if(file.exists() && file.isFile())
						setWorkbookBytes(IOUtils.toByteArray(new FileInputStream(file)));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			@SuppressWarnings("unchecked")
			@Override
			public Class<List<Dimension.Row<String>>> getOutputClass() {
				@SuppressWarnings("rawtypes")
				Class clazz = List.class;
				return clazz;
			}
			
			@Override
			public Boolean isRowAddable(Dimension.Row<String> row) {
				Boolean isAddable = !row.isBlank();
            	if(!Boolean.TRUE.equals(isAddable))
            		numberOfNotAdded ++;
				return isAddable;
			}
			
			@Override
			public Dimension.Row<String> getRow(Integer index, Integer size) {
				return getRow(index, new String[size]);
			}
			
			@Override
			protected List<Dimension.Row<String>> __execute__() {
				Workbook workbook;
				try {
					workbook = WorkbookFactory.create(new ByteArrayInputStream(getWorkbookBytes()));
				} catch (Exception e1) {
					e1.printStackTrace();
					return null;
				}
					
		        Sheet sheet = StringUtils.isBlank(getSheetName()) ? workbook.getSheetAt(getIndex()) : workbook.getSheet(getSheetName());
		        setOutput(new ArrayList<Dimension.Row<String>>());
		        
		        if(sheet==null){
		        	System.out.println("No sheet named <<"+getSheetName()+">> or at index <<"+getIndex()+">> found");
		        }else{
		        	addLogMessageBuilderParameters(getLogMessageBuilder(),"sheet", sheet.getSheetName());
		        	if(getPrimaryKeyColumnIndexes()!=null)
		        		addLogMessageBuilderParameters(getLogMessageBuilder(), "primary key column indexes",getPrimaryKeyColumnIndexes());
		        	if(getPrimaryKeys()!=null)
		        		addLogMessageBuilderParameters(getLogMessageBuilder(), "#primary keys",getPrimaryKeys().size());
		        	FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
		        	Integer fromRowIndex = getFromRowIndex(),fromColumnIndex = getFromColumnIndex();
		            if(fromRowIndex==null)
		            	fromRowIndex = 0;
		            if(fromColumnIndex==null)
		            	fromColumnIndex = 0;
		        	Integer rowCount = getRowCount(),columnCount = getColumnCount();
		            if(rowCount==null)
		            	rowCount = sheet.getPhysicalNumberOfRows();
		            if(columnCount==null)
		            	columnCount = new Integer(sheet.getRow(0).getLastCellNum());
		          
		            for (int i=0; i<rowCount; i++) {
		            	Row row = sheet.getRow(i + fromRowIndex);
		            	Dimension.Row<String> _row = null;
		                if(row==null){
		                	
		                }else{
		                	_row = getRow(i, columnCount);
		                	for (int j=0; j<columnCount; j++) {
		                        Cell cell = row.getCell(j+fromColumnIndex);
		                        if(cell==null)
		                        	_row.set(j, Constant.EMPTY_STRING);
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
		                        	_row.set(j,StringUtils.defaultIfBlank(stringValue,Constant.EMPTY_STRING));
		                        }
		                    }
		                	
		                	listenAfterRowCreated(_row);
		                }
		                if(_row==null)
		                	;
		                else{
		                	if(Boolean.TRUE.equals(isRowAddable(_row))){
		                		_row.setPrimaryKey(getPrimaryKey(i, _row.getValues()));
		                		getOutput().add(_row);		 
		                		listenAfterRowAdded(_row);
		                	}
		                }
		                //System.out.println("ExcelSheetReader : "+StringUtils.join(array,"|"));
		            }	
		            addLogMessageBuilderParameters(getLogMessageBuilder(),"#row",rowCount);   
		        }
		        
		        addLogMessageBuilderParameters(getLogMessageBuilder(),"#selected",getOutput().size(), "#ignored",numberOfNotAdded);
		        if(Boolean.TRUE.equals(getHasPrimaryKey())) 
		        	addLogMessageBuilderParameters(getLogMessageBuilder(),"#has primary keys",getRowsWhereHasPrimaryKey(String.class,getOutput()).size(),"#has no primary keys"
		        			,getRowsWhereHasNotPrimaryKey(String.class,getOutput()).size());
		        try {
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}				
				return getOutput();
			}
			
			@Override
			protected Boolean isShowOutputLogMessage(List<Dimension.Row<String>> output) {
				return Boolean.FALSE;
			}
		}
		
	}
	
}