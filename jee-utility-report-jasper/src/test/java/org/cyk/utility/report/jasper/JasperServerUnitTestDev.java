package org.cyk.utility.report.jasper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.cyk.utility.__kernel__.file.FileType;
import org.cyk.utility.report.ReportGetter;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.cyk.utility.__kernel__.variable.VariableHelper;
import org.cyk.utility.__kernel__.variable.VariableName;
import org.junit.jupiter.api.Test;

public class JasperServerUnitTestDev extends AbstractWeldUnitTest {

	@Test
	public void t(){
		VariableHelper.write(VariableName.JASPER_ENABLED, Boolean.TRUE);
		VariableHelper.write(VariableName.JASPER_SERVER_CREDENTIAL_USERNAME, "siibc");
		VariableHelper.write(VariableName.JASPER_SERVER_CREDENTIAL_PASSWORD, "siibc");
		String uri = "reports/SIIBC/ressources_section_specifie_usb_activite";
		Map<Object,Object> parameters = Map.of("sectionCode","100");
		try {
			FileUtils.writeByteArrayToFile(new File("target/report100.pdf"), ReportGetter.getInstance().get(uri,parameters,FileType.PDF).toByteArray(), Boolean.FALSE);
			FileUtils.writeByteArrayToFile(new File("target/report100.html"), ReportGetter.getInstance().get(uri,parameters,FileType.HTML).toByteArray(), Boolean.FALSE);
			FileUtils.writeByteArrayToFile(new File("target/report100.docx"), ReportGetter.getInstance().get(uri,parameters,FileType.DOCX).toByteArray(), Boolean.FALSE);
			FileUtils.writeByteArrayToFile(new File("target/report100.xls"), ReportGetter.getInstance().get(uri,parameters,FileType.XLS).toByteArray(), Boolean.FALSE);
			FileUtils.writeByteArrayToFile(new File("target/report100.xlsx"), ReportGetter.getInstance().get(uri,parameters,FileType.XLSX).toByteArray(), Boolean.FALSE);
			System.out.println("Done!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		parameters = Map.of("sectionCode","322");
		try {
			FileUtils.writeByteArrayToFile(new File("target/report322.pdf"), ReportGetter.getInstance().get(uri,parameters,FileType.PDF).toByteArray(), Boolean.FALSE);
			FileUtils.writeByteArrayToFile(new File("target/report322.html"), ReportGetter.getInstance().get(uri,parameters,FileType.HTML).toByteArray(), Boolean.FALSE);
			FileUtils.writeByteArrayToFile(new File("target/report322.docx"), ReportGetter.getInstance().get(uri,parameters,FileType.DOCX).toByteArray(), Boolean.FALSE);
			FileUtils.writeByteArrayToFile(new File("target/report322.xls"), ReportGetter.getInstance().get(uri,parameters,FileType.XLS).toByteArray(), Boolean.FALSE);
			FileUtils.writeByteArrayToFile(new File("target/report322.xlsx"), ReportGetter.getInstance().get(uri,parameters,FileType.XLSX).toByteArray(), Boolean.FALSE);
			System.out.println("Done!");
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	//@Test
	public void tEnv(){
		VariableHelper.write(VariableName.JASPER_ENABLED, Boolean.TRUE);
		VariableHelper.write(VariableName.JASPER_SERVER_CREDENTIAL_USERNAME, "jasperadmin");
		VariableHelper.write(VariableName.JASPER_SERVER_CREDENTIAL_PASSWORD, "jasperadmin");
		String uri = "/reports/enveloppe/cadrage/etat_de_cadrage";
		Map<Object,Object> parameters = Map.of("SECTION_CODE","101");
		try {
			FileUtils.writeByteArrayToFile(new File("target/cad_env.pdf"), ReportGetter.getInstance().get(uri,parameters,FileType.PDF).toByteArray(), Boolean.FALSE);
			System.out.println("Done!");
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
}