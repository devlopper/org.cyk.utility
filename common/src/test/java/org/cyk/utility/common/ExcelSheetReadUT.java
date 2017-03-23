package org.cyk.utility.common;

import java.io.File;
import java.util.List;

import org.cyk.utility.common.file.ArrayReader.Dimension;
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
			reader.setFromRowIndex(1);
			List<Dimension.Row<String>> list = reader.execute();
			for(Dimension.Row<String> line : list)
				System.out.println(line);
			
			reader = new ExcelSheetReader.Adapter.Default(new File(directory, "1xlsx.xlsx"));
			reader.setIndex(0);
			list = reader.execute();
			for(Dimension.Row<String> line : list)
				System.out.println(line);
			
			System.out.println("ExcelSheetReadUT.read()");
			System.out.println(CommonUtils.getInstance().convertToString(reader.getValues(), "|", ","));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void person(){
		
	}
	
}
