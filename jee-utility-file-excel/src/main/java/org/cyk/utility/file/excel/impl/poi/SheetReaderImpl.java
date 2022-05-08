package org.cyk.utility.file.excel.impl.poi;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.file.excel.AbstractSheetReaderImpl;

@ApplicationScoped
public class SheetReaderImpl extends AbstractSheetReaderImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String[][] __read__(org.cyk.utility.file.excel.Sheet sheet,Integer fromColumnIndex,Integer numberOfColumns,Integer fromRowIndex,Integer numberOfRows) {
		if(sheet == null || sheet.getWorkBook() == null || sheet.getWorkBook().getValue() == null || sheet.getValue() == null)
			return null;
		Workbook __workbook__ = (Workbook) sheet.getWorkBook().getValue();
        Sheet __sheet__ = (Sheet) sheet.getValue();
    	FormulaEvaluator formulaEvaluator = __workbook__.getCreationHelper().createFormulaEvaluator();
    	
    	String[][] array = new String[numberOfRows][numberOfColumns];
        for (int i=0; i<numberOfRows; i++) {
        	Row row = __sheet__.getRow(i + fromRowIndex);
            if(row==null){
            	
            }else{
            	for (int j=0; j<numberOfColumns; j++) {
                    Cell cell = row.getCell(j+fromColumnIndex);
                    if(cell==null)
                    	array[i][j] = ConstantEmpty.STRING;
                    else{
                    	CellValue cellValue;
                    	try {
							cellValue = formulaEvaluator.evaluate(cell);
						} catch (Exception exception) {
							cellValue = null;
							sheet.setCellEvaluationException(i, j, exception);
						}
                    	String stringValue;
                    	if(cellValue==null)
                    		stringValue = ConstantEmpty.STRING;
                    	else switch(cellValue.getCellTypeEnum()){ 
                    	case BLANK : 
                    		stringValue = ConstantEmpty.STRING;
                    		break;
                    	case NUMERIC : 
                    		if(DateUtil.isCellDateFormatted(cell))
                        		stringValue = cell.getDateCellValue().toString();
                        	else
                        		stringValue = new BigDecimal(cellValue.getNumberValue()).toString();
    	                	break;
                    	case STRING:
                    		stringValue = cellValue.getStringValue(); 
                    		break;
                    	case FORMULA:
                    		throw new RuntimeException("Must never happen! Cannot process a formula. Please change field to result of formula.("+i+","+j+")");
                    	default:
	    	                stringValue = StringUtils.trim(cellValue.getStringValue());
	    	                break;
	    	            }
                    	array[i][j] = StringUtils.defaultIfBlank(stringValue,ConstantEmpty.STRING);
                    }
                }
            }
        }    
        try {
        	__workbook__.close();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
        return array;
	}

	@Override
	protected Integer getMaximalNumberOfColumns(org.cyk.utility.file.excel.Sheet sheet) {
		return Integer.valueOf(((Sheet) sheet.getValue()).getRow(0).getLastCellNum());
	}
	
	@Override
	protected Integer getMaximalNumberOfRows(org.cyk.utility.file.excel.Sheet sheet) {
		return ((Sheet) sheet.getValue()).getPhysicalNumberOfRows();
	}
}
