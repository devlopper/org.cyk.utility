package org.cyk.utility.file.excel;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class ExcelSheetReaderUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Inject private SheetReader sheetReader;
	
	@Test
	public void read() throws Exception {
		String[][] array = sheetReader.read(new SheetReader.Arguments().setSheetGetterArguments(new SheetGetter.Arguments().setWorkBookGetterArguments(new WorkBookGetter.Arguments().setInputStream(getClass().getResourceAsStream("01.xlsx")))));
		assertThat(array[0][0]).isEqualTo("Column01");
		assertThat(array[0][1]).isEqualTo("Column02");
		assertThat(array[0][2]).isEqualTo("Column03");
		assertThat(array[0][3]).isBlank();
		assertThat(array[0][4]).isEqualTo("Column04");
	}
	
	@Test
	public void read_mergedCells() throws Exception {
		String[][] array = sheetReader.read(new SheetReader.Arguments().setSheetGetterArguments(new SheetGetter.Arguments().setWorkBookGetterArguments(new WorkBookGetter.Arguments().setInputStream(getClass().getResourceAsStream("merged_01.xlsx")))));
		assertThat(array[0][0]).isEqualTo("Column01");
		assertThat(array[0][1]).isEqualTo("Column02");
		assertThat(array[0][2]).isEqualTo("Column03");
		
		assertThat(array[1][0]).isEqualTo("A");
		assertThat(array[1][1]).isEqualTo("A1");
		assertThat(array[1][2]).isEqualTo("A11");
		
		assertThat(array[2][0]).isBlank();
		assertThat(array[2][1]).isBlank();
		assertThat(array[2][2]).isEqualTo("A12");
	}
	
	@Test
	public void read_section_105_cesec_ok() throws Exception {
		String[][] array = sheetReader.read(new SheetReader.Arguments().setNumberOfColumns(5).setSheetGetterArguments(new SheetGetter.Arguments()
				.setWorkBookGetterArguments(new WorkBookGetter.Arguments().setInputStream(getClass().getResourceAsStream("Section 105 CESEC OK.xls")))));
		assertThat(array[3][0]).isEqualTo("BUDGET 2020 - RATTACHEMENT DES ACTIVITES AUX UNITES ADMINISTRATIVES");
	}
}
