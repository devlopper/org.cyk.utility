package org.cyk.utility.common;

import org.cyk.utility.common.helper.MicrosoftExcelHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class MicrosoftExcelHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = 1L;
	
	@Test
	public void readWorkbook_1_xls_sheet_0(){
		assertSheet(null, null, new Object[][]{
				{"Nom","Prenoms","Age","Mail"}
				,{"Yao","Paul","12","mail555"}
				,{"Jack","Mama","52","yahoo523"}
				,{"Pul","Gnab","24","hotmail"}
			});
		
		assertSheet(0, null, new Object[][]{
				{"Nom","Prenoms","Age","Mail"}
				,{"Yao","Paul","12","mail555"}
				,{"Jack","Mama","52","yahoo523"}
				,{"Pul","Gnab","24","hotmail"}
			});
		
		assertSheet(1, null, new Object[][]{
				{"Yao","Paul","12","mail555"}
				,{"Jack","Mama","52","yahoo523"}
				,{"Pul","Gnab","24","hotmail"}
			});
		
		assertSheet(2, null, new Object[][]{
				{"Jack","Mama","52","yahoo523"}
				,{"Pul","Gnab","24","hotmail"}
			});
		
		assertSheet(3, null, new Object[][]{
				{"Pul","Gnab","24","hotmail"}
			});
		
		assertSheet(4, null,null);
		assertSheet(5, null,null);
		
		assertSheet(0, 1, new Object[][]{
				{"Nom","Prenoms","Age","Mail"}
			});
		
		assertSheet(1, 1, new Object[][]{
				{"Yao","Paul","12","mail555"}
			});
		
		assertSheet(2, 1, new Object[][]{
				{"Jack","Mama","52","yahoo523"}
			});
		
		assertSheet(3, 1, new Object[][]{
				{"Pul","Gnab","24","hotmail"}
			});
		
		assertSheet(4, 1,null);
		assertSheet(5, 1,null);
		
	}
	
	private void assertSheet(String workbookFilename,Integer index,Integer fromRowIndex,Integer numberOfIndexes,Object[][] expectedValues,Object[][] expectedIgnoreds){
		MicrosoftExcelHelper.Workbook.Sheet.Builder.Adapter.Default builder = new MicrosoftExcelHelper.Workbook.Sheet.Builder.Adapter.Default(workbookFilename,index);
		builder.createMatrix().getMatrix().getRow().setFromIndex(fromRowIndex).setNumberOfIndexes(numberOfIndexes);
		MicrosoftExcelHelper.Workbook.Sheet sheet = builder.execute();
		
		if(expectedValues==null)
			assertNull(sheet.getValues());
		else
			assertArray(sheet.getValues(), expectedValues);
		
		if(expectedIgnoreds==null)
			assertNull(sheet.getIgnoreds());
		else
			assertArray(sheet.getIgnoreds(), expectedIgnoreds);
	}
	
	private void assertSheet(Integer fromRowIndex,Integer numberOfIndexes,Object[][] expectedValues,Object[][] expectedIgnoreds){
		assertSheet(System.getProperty("user.dir")+"\\src\\test\\resources\\files\\excel\\1xls.xls",0, fromRowIndex, numberOfIndexes, expectedValues, expectedIgnoreds);
	}
	
	private void assertSheet(Integer fromRowIndex,Integer numberOfIndexes,Object[][] expectedValues){
		assertSheet(fromRowIndex, numberOfIndexes, expectedValues, null);
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
