package org.cyk.utility.__kernel__.file.microsoft.excel.poi;

import java.io.IOException;
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
import org.cyk.utility.__kernel__.array.ArrayTwoDimensionString;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.file.microsoft.excel.AbstractSheetReaderImpl;
import org.cyk.utility.__kernel__.number.Interval;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.ValueHelper;

@Dependent
public class SheetReaderImpl extends AbstractSheetReaderImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public ArrayTwoDimensionString read(Object workbook, Object sheet,Interval rowInterval,Interval columnInterval) {
		if(workbook == null)
			return null;
		if(sheet == null)
			return null;
		Workbook __workbook__ = (Workbook) workbook;
        Sheet __sheet__ = (Sheet) sheet;
    	FormulaEvaluator formulaEvaluator = __workbook__.getCreationHelper().createFormulaEvaluator();
    	Integer fromRowIndex = ValueHelper.defaultToIfNull(rowInterval == null ? null : rowInterval.getLowValueAs(Integer.class),0);
    	Integer fromColumnIndex = ValueHelper.defaultToIfNull(columnInterval == null ? null : columnInterval.getLowValueAs(Integer.class),0);    	
    	Integer rowCount = ValueHelper.defaultToIfNull(rowInterval == null ? null : rowInterval.getHighValueAs(Integer.class),__sheet__.getPhysicalNumberOfRows()) - fromRowIndex;
    	Integer columnCount = null;
    	if(columnInterval == null) {
    		columnCount = Integer.valueOf(__sheet__.getRow(0).getLastCellNum());
    	}else {
    		columnCount = columnInterval.getHighValueAs(Integer.class);
    	}
    	ThrowableHelper.throwIllegalArgumentExceptionIfNull("excel sheet column count", columnCount);
    	columnCount = columnCount - fromColumnIndex;
    	ArrayTwoDimensionString arrayInstance = new ArrayTwoDimensionString();
    	arrayInstance.setFirstDimensionElementCount(rowCount).setSecondDimensionElementCount(columnCount);   	
        for (int i=0; i<rowCount; i++) {
        	Row row = __sheet__.getRow(i + fromRowIndex);
            if(row==null){
            	
            }else{
            	for (int j=0; j<columnCount; j++) {
                    Cell cell = row.getCell(j+fromColumnIndex);
                    if(cell==null)
                    	arrayInstance.set(i,j, ConstantEmpty.STRING);
                    else{
                    	CellValue cellValue;
                    	try {
							cellValue = formulaEvaluator.evaluate(cell);
						} catch (Exception exception) {
							cellValue = null;
							//throw new RuntimeException(exception); // TODO what to do ???
						}
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
        	__workbook__.close();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
        return arrayInstance;
	}

}
