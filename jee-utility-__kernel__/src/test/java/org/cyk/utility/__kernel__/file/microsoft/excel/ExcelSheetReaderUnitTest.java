package org.cyk.utility.__kernel__.file.microsoft.excel;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.cyk.utility.__kernel__.array.ArrayTwoDimensionString;
import org.cyk.utility.__kernel__.number.Interval;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class ExcelSheetReaderUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void read() throws Exception {
		Workbook workbook = (Workbook) WorkBookGetter.getInstance().get(getClass().getResourceAsStream("01.xlsx"));
		Sheet sheet =  (Sheet) SheetGetter.getInstance().get(workbook, 0);
		ArrayTwoDimensionString array = SheetReader.getInstance().read(workbook, sheet);
		assertThat(array.get(0, 0)).isEqualTo("Column01");
		assertThat(array.get(0, 1)).isEqualTo("Column02");
		assertThat(array.get(0, 2)).isEqualTo("Column03");
		assertThat(array.get(0, 3)).isBlank();
		assertThat(array.get(0, 4)).isEqualTo("Column04");
	}
	
	@Test
	public void read_mergedCells() throws Exception {
		Workbook workbook = (Workbook) WorkBookGetter.getInstance().get(getClass().getResourceAsStream("merged_01.xlsx"));
		Sheet sheet =  (Sheet) SheetGetter.getInstance().get(workbook, 0);
		ArrayTwoDimensionString array = SheetReader.getInstance().read(workbook, sheet);
		//assertThat(array.getFirstDimensionElementCount()).isEqualTo(3);
		//assertThat(array.getSecondDimensionElementCount()).isEqualTo(10);
		assertThat(array.get(0, 0)).isEqualTo("Column01");
		assertThat(array.get(0, 1)).isEqualTo("Column02");
		assertThat(array.get(0, 2)).isEqualTo("Column03");
		
		assertThat(array.get(1, 0)).isEqualTo("A");
		assertThat(array.get(1, 1)).isEqualTo("A1");
		assertThat(array.get(1, 2)).isEqualTo("A11");
		
		assertThat(array.get(2, 0)).isBlank();
		assertThat(array.get(2, 1)).isBlank();
		assertThat(array.get(2, 2)).isEqualTo("A12");
	}
	
	@Test
	public void read_section_105_cesec_ok() throws Exception {
		Workbook workbook = (Workbook) WorkBookGetter.getInstance().get(getClass().getResourceAsStream("Section 105 CESEC OK.xls"));
		Sheet sheet =  (Sheet) SheetGetter.getInstance().get(workbook, 0);
		ArrayTwoDimensionString array = SheetReader.getInstance().read(workbook, sheet,__inject__(Interval.class).setLow(0),__inject__(Interval.class).setHigh(5));
		assertThat(array.get(3, 0)).isEqualTo("BUDGET 2020 - RATTACHEMENT DES ACTIVITES AUX UNITES ADMINISTRATIVES");
	}
}
