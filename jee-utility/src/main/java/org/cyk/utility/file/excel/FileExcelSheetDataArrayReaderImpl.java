package org.cyk.utility.file.excel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.enterprise.context.Dependent;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.array.ArrayInstanceTwoDimensionString;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.number.Interval;

@Dependent
public class FileExcelSheetDataArrayReaderImpl extends AbstractFunctionWithPropertiesAsInputImpl<ArrayInstanceTwoDimensionString> implements FileExcelSheetDataArrayReader,Serializable {
	private static final long serialVersionUID = 1L;

	private String workbookFileName,sheetName;
	private Interval rowInterval,columnInterval;
	private InputStream workbookInputStream;
	
	@Override
	protected ArrayInstanceTwoDimensionString __execute__() throws Exception {
		ArrayInstanceTwoDimensionString arrayInstance = __inject__(ArrayInstanceTwoDimensionString.class);
		Workbook workbook = null;
		
		if(workbook == null) {
			InputStream workbookInputStream = getWorkbookInputStream();
			if(workbookInputStream!=null)
				workbook = WorkbookFactory.create(workbookInputStream);
		}
		
		if(workbook == null) {
			String workbookFileName = getWorkbookFileName();
			if(StringHelper.isNotBlank(workbookFileName))
				workbook = WorkbookFactory.create(new File(workbookFileName));
		}
		
		workbook = __injectValueHelper__().returnOrThrowIfBlank("excel workbook", workbook);
		
        Sheet sheet = null;
        String sheetName = getSheetName();
        if(sheet == null && StringHelper.isNotBlank(sheetName))
        	sheet = workbook.getSheet(sheetName);
        
        sheet = __injectValueHelper__().returnOrThrowIfBlank("excel workbook sheet", sheet);
        
        Interval rowInterval = getRowInterval();
        Interval columnInterval = getColumnInterval();
        
    	FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
    	Integer fromRowIndex = __injectValueHelper__().defaultToIfNull(rowInterval == null ? null : rowInterval.getLowValueAs(Integer.class),0);
    	Integer fromColumnIndex = __injectValueHelper__().defaultToIfNull(columnInterval == null ? null : columnInterval.getLowValueAs(Integer.class),0);
    	
    	Integer rowCount = __injectValueHelper__().defaultToIfNull(rowInterval == null ? null : rowInterval.getHighValueAs(Integer.class),sheet.getPhysicalNumberOfRows()) - fromRowIndex;
    	Integer columnCount = __injectValueHelper__().defaultToIfNull(columnInterval == null ? null : columnInterval.getHighValueAs(Integer.class),new Integer(sheet.getRow(0).getLastCellNum())) - fromColumnIndex;
    	
    	arrayInstance.setFirstDimensionElementCount(rowCount).setSecondDimensionElementCount(columnCount);
    	
        for (int i=0; i<rowCount; i++) {
        	Row row = sheet.getRow(i + fromRowIndex);
            if(row==null){
            	
            }else{
            	for (int j=0; j<columnCount; j++) {
                    Cell cell = row.getCell(j+fromColumnIndex);
                    if(cell==null)
                    	arrayInstance.set(i,j, ConstantEmpty.STRING);
                    else{
                    	CellValue cellValue = formulaEvaluator.evaluate(cell);
                    	String stringValue;
                    	if(cellValue==null)
                    		stringValue = ConstantEmpty.STRING;
                    	else switch(cellValue.getCellType()){
	    	                case Cell.CELL_TYPE_FORMULA : 
	    	                	throw new RuntimeException("Must never happen! Cannot process a formula. Please change field to result of formula.("+i+","+j+")");
	    	                case Cell.CELL_TYPE_BLANK: stringValue = ConstantEmpty.STRING; break;
	    	                case Cell.CELL_TYPE_NUMERIC: 
	    	                	if(DateUtil.isCellDateFormatted(cell))
	                        		stringValue = cell.getDateCellValue().toString(); //Constant.DATE_TIME_FORMATTER.format(cell.getDateCellValue());
	                        	else
	                        		stringValue = /*cellValue.getStringValue();*/ new BigDecimal(cellValue.getNumberValue()).toString(); //cellValue.getNumberValue()); 
	    	                	break;
	    	                case Cell.CELL_TYPE_STRING: stringValue = cellValue.getStringValue(); break;
	    	                default:
	    	                	stringValue = StringUtils.trim(cellValue.getStringValue());
	    	                	break;
	    	                }
                    	arrayInstance.set(i,j,StringUtils.defaultIfBlank(stringValue,ConstantEmpty.STRING));
                    }
                }
            }
        }	
        
        try {
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return arrayInstance;
	}
	
	@Override
	public InputStream getWorkbookInputStream() {
		return workbookInputStream;
	}
	
	@Override
	public FileExcelSheetDataArrayReader setWorkbookInputStream(InputStream workbookInputStream) {
		this.workbookInputStream = workbookInputStream;
		return this;
	}
	
	@Override
	public String getWorkbookFileName() {
		return workbookFileName;
	}
	
	@Override
	public FileExcelSheetDataArrayReader setWorkbookFileName(String workbookFileName) {
		this.workbookFileName = workbookFileName;
		return this;
	}
	
	@Override
	public String getSheetName() {
		return sheetName;
	}
	
	@Override
	public FileExcelSheetDataArrayReader setSheetName(String sheetName) {
		this.sheetName = sheetName;
		return this;
	}
	
	@Override
	public Interval getRowInterval() {
		return rowInterval;
	}
	
	@Override
	public Interval getRowInterval(Boolean injectIfNull) {
		return (Interval) __getInjectIfNull__(FIELD_ROW_INTERVAL, injectIfNull);
	}
	
	@Override
	public FileExcelSheetDataArrayReader setRowInterval(Interval rowInterval) {
		this.rowInterval = rowInterval;
		return this;
	}
	
	@Override
	public Interval getColumnInterval() {
		return columnInterval;
	}
	
	@Override
	public Interval getColumnInterval(Boolean injectIfNull) {
		return (Interval) __getInjectIfNull__(FIELD_COLUMN_INTERVAL, injectIfNull);
	}
	
	@Override
	public FileExcelSheetDataArrayReader setColumnInterval(Interval columnInterval) {
		this.columnInterval = columnInterval;
		return this;
	}
	
	/**/
	
	public static final String FIELD_ROW_INTERVAL = "rowInterval";
	public static final String FIELD_COLUMN_INTERVAL = "columnInterval";

}
