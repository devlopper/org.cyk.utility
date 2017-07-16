package org.cyk.utility.common;

import org.cyk.utility.common.helper.MicrosoftExcelHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class MicrosoftExcelHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = 1L;
	
	@Test
	public void readWorkbook_1_xls_sheet_0(){
		MicrosoftExcelHelper.Workbook.Sheet sheet = new MicrosoftExcelHelper.Workbook.Sheet.Builder.Adapter
				.Default(System.getProperty("user.dir")+"\\src\\test\\resources\\files\\excel\\1xls.xls",0).execute();
		
		assertArray(sheet.getValues(), new Object[][]{
			{"Nom","Prenoms","Age","Mail"}
			,{"Yao","Paul","12","mail555"}
			,{"Jack","Mama","52","yahoo523"}
			,{"Pul","Gnab","24","hotmail"}
		});
		
		assertNull(sheet.getIgnoreds());
	}
	
	@Test
	public void readWorkbook_1_xls_sheet_0_ignored_keys(){
		MicrosoftExcelHelper.Workbook.Sheet.Builder builder = new MicrosoftExcelHelper.Workbook.Sheet.Builder.Adapter
				.Default(System.getProperty("user.dir")+"\\src\\test\\resources\\files\\excel\\1xls.xls",0);
		builder.createMatrix().getMatrix().getRow().createKeyBuilder(new Object[]{0}, new String[]{"Jack"});
		MicrosoftExcelHelper.Workbook.Sheet sheet = builder.execute();
		
		assertArray(sheet.getValues(), new Object[][]{
			{"Nom","Prenoms","Age","Mail"}
			,{"Yao","Paul","12","mail555"}
			,{"Pul","Gnab","24","hotmail"}
		});
		
		assertArray(sheet.getIgnoreds(), new Object[][]{
			{"Jack","Mama","52","yahoo523"}
			
		});
	}
}
