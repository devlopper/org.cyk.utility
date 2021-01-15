package org.cyk.utility.report.jasper.server;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.cyk.utility.report.ReportBuilder;
import org.cyk.utility.report.Template;
import org.junit.jupiter.api.Test;

import net.sf.jasperreports.engine.export.JRPdfExporter;

public class ReportBuilderUnitTestSigobeDev extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;
	
	@Test
	public void build_demande_postes_budgetaires_identification(){
		
		ByteArrayOutputStream outputStream = (ByteArrayOutputStream) ReportBuilder.getInstance().build(new Template().setInputStream(this.getClass()
				.getResourceAsStream("demande_postes_budgetaires_identification.jrxml")), null,null, JRPdfExporter.class);
		try {
			FileUtils.writeByteArrayToFile(new File("target/demande_postes_budgetaires_identification.pdf"), outputStream.toByteArray(),Boolean.FALSE);
			System.out.println("Done!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
