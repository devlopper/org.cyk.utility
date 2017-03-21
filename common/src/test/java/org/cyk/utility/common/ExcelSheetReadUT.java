package org.cyk.utility.common;

import java.io.File;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.file.ExcelSheetReader;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class ExcelSheetReadUT extends AbstractUnitTest {

	private static final long serialVersionUID = 1L;

	@Test
	public void read(){
		File directory = new File(System.getProperty("user.dir")+"\\src\\test\\resources\\files\\excel");
		ExcelSheetReader reader = new ExcelSheetReader.Adapter.Default(new File(directory, "1xls.xls"));
		try {
			reader.setIndex(0);
			reader.setRowCount(5);
			reader.setColumnCount(3);
			List<String[]> list = reader.execute();
			for(String[] line : list)
				System.out.println(StringUtils.join(line," ; "));
			
			reader = new ExcelSheetReader.Adapter.Default(new File(directory, "1xlsx.xlsx"));
			reader.setIndex(0);
			list = reader.execute();
			for(String[] line : list)
				System.out.println(StringUtils.join(line," ; "));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void person(){
		
	}
	
}
