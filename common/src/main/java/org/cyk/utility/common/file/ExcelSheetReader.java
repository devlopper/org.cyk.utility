package org.cyk.utility.common.file;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
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
	
	String processAfterRowRead(Integer rowIndex,String[] values);
	Boolean isRowAddable(Integer rowIndex,String[] values);
	void processAfterRowAdded(Integer rowIndex,String[] values);
	
	@Getter
	public static class Adapter extends TwoDimension.Adapter.Default<String> implements ExcelSheetReader,Serializable {
		private static final long serialVersionUID = 1L;

		private byte[] workbookBytes;
		private String sheetName;
		private Integer index,fromRowIndex,rowCount,fromColumnIndex,columnCount;
		
		public Adapter(File file) {
			super(file);
		}
		
		@Override
		public String processAfterRowRead(Integer rowIndex, String[] values) {
			return null;
		}
		
		@Override
		public Boolean isRowAddable(Integer rowIndex, String[] values) {
			return null;
		}
		
		@Override
		public void processAfterRowAdded(Integer rowIndex, String[] values) {}
		
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
		
		public static class Default extends ExcelSheetReader.Adapter implements Serializable {
			private static final long serialVersionUID = 1L;
			
			protected Integer numberOfNotAdded=0,numberOfExistingPrimaryKeys = 0;
			
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
			public Class<List<String[]>> getOutputClass() {
				@SuppressWarnings("rawtypes")
				Class clazz = List.class;
				return clazz;
			}
			
			@Override
			public Boolean isRowAddable(Integer rowIndex, String[] values) {
				Boolean isAddable = Boolean.TRUE;
				Boolean isEmpty = Boolean.TRUE;
            	for(int k = 0; k < values.length; k++)
            		if(StringUtils.isNotBlank(values[k])){
            			isEmpty = Boolean.FALSE;
            			break;
            		}
            	isAddable = !isEmpty;
            	/*if(Boolean.TRUE.equals(isAddable)){
            		Collection<String> primaryKeys = getPrimaryKeys();
            		Integer primaryKeyColumnIndex = getPrimaryKeyColumnIndex();
            		if(primaryKeys!=null && primaryKeyColumnIndex!=null)
            			isAddable = !primaryKeys.contains(values[primaryKeyColumnIndex]);
            	}*/
            	if(!Boolean.TRUE.equals(isAddable))
            		numberOfNotAdded ++;
				return isAddable;
			}
			
			@Override
			protected List<String[]> __execute__() {
				Workbook workbook;
				try {
					workbook = WorkbookFactory.create(new ByteArrayInputStream(getWorkbookBytes()));
				} catch (Exception e1) {
					e1.printStackTrace();
					return null;
				}
					
		        Sheet sheet = StringUtils.isBlank(getSheetName()) ? workbook.getSheetAt(getIndex()) : workbook.getSheet(getSheetName());
		        List<String[]> list = new ArrayList<>();
		        
		        if(sheet==null){
		        	System.out.println("No sheet named <<"+getSheetName()+">> or at index <<"+getIndex()+">> found");
		        }else{
		        	addLogMessageBuilderParameters(getLogMessageBuilder(),"sheet", sheet.getSheetName());
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
		            	String[] array = null;
		            	Row row = sheet.getRow(i + fromRowIndex);
		                if(row==null){
		                	
		                }else{
		                	array = new String[columnCount+( getPrimaryKeyColumnIndex() == null ? 0 : 1 )];
		                	if(getPrimaryKeyColumnIndex()!=null)
		                		setPrimaryKeyExistColumnIndex(array.length-1);
		                	for (int j=0; j<columnCount; j++) {
		                        Cell cell = row.getCell(j+fromColumnIndex);
		                        if(cell==null)
		                        	array[j] = Constant.EMPTY_STRING;
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
		                        	array[j] = StringUtils.defaultIfBlank(stringValue,Constant.EMPTY_STRING);
		                        }
		                    }
		                	processAfterRowRead(i, array);
		                }
		                if(array==null)
		                	;
		                else{
		                	if(Boolean.TRUE.equals(isRowAddable(i, array))){
		                		list.add(array);
		                		Boolean primaryKeyExist = Boolean.TRUE.equals(isPrimaryKeyExist(i, array));
		                		if(primaryKeyExist)
		                			numberOfExistingPrimaryKeys++;
		                		if(getPrimaryKeyExistColumnIndex()!=null && getPrimaryKeyExistColumnIndex() < array.length){
		                			array[getPrimaryKeyExistColumnIndex()] = String.valueOf(primaryKeyExist);
		                		}
		                		processAfterRowAdded(i, array);
		                	}
		                }
		                //System.out.println("ExcelSheetReader : "+StringUtils.join(array,"|"));
		            }	
		        }
		        
		        addLogMessageBuilderParameters(getLogMessageBuilder(),"#count",list.size(), "#ignored",numberOfNotAdded,"#existing",numberOfExistingPrimaryKeys);
		        try {
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}				
				return list;
			}
			
		}
		
	}
	
}