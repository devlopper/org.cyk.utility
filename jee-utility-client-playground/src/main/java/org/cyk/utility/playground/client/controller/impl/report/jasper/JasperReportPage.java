package org.cyk.utility.playground.client.controller.impl.report.jasper;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class JasperReportPage extends AbstractPageContainerManagedImpl implements Serializable {

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		
		/*VariableHelper.write(VariableName.JASPER_ENABLED, Boolean.TRUE);
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
		}*/
	}
	
	@Override
	protected String __getWindowTitleValue__() {
		return "Jasper Report Demo";
	}	
}