package org.cyk.utility.common;

import org.cyk.utility.common.helper.FileHelper;
import org.cyk.utility.common.helper.MicrosoftExcelHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class MicrosoftExcelHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = 1L;
	
	@Test
	public void readWorkbook_1_xls_sheet_0(){
		MicrosoftExcelHelper.Workbook workbook = new MicrosoftExcelHelper.Workbook.Builder.InputStream.Adapter
				.Default(new FileHelper().getInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\files\\excel\\1xls.xls")).execute();
		
		MicrosoftExcelHelper.Workbook.Sheet sheet = new MicrosoftExcelHelper.Workbook.Sheet.Builder.Adapter.Default(workbook).setSheetIndex(0).execute();
		
		assertArray(sheet.getValues(), new Object[][]{
			{"Nom","Prenoms","Age","Mail"}
			,{"Yao","Paul","12","mail555"}
			,{"Jack","Mama","52","yahoo523"}
			,{"Pul","Gnab","24","hotmail"}
		});
	}
	
}
