package org.cyk.utility.common;

import org.cyk.utility.common.file.FileNameNormaliser;
import org.cyk.utility.test.unit.AbstractUnitTest;

public class FileNameNormaliserUT extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@Override
	protected void _execute_() {
		super._execute_();
		FileNameNormaliser normaliser = new FileNameNormaliser.Adapter.Default();
		assertEquals("Report sheet PREMIER TERM _ G1 REPORT SHEET1483202759651", normaliser.setInput("Report sheet PREMIER TERM , G1 REPORT SHEET1483202759651").execute());
	}
	
}
