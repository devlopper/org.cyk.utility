package org.cyk.utility.server.business;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;

import org.cyk.utility.report.ReportBuilder;
import org.cyk.utility.report.Template;
import org.cyk.utility.__kernel__.test.arquillian.archive.builder.WebArchiveBuilder;
import org.cyk.utility.server.business.test.arquillian.AbstractBusinessArquillianIntegrationTest;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;

import net.sf.jasperreports.engine.export.JRTextExporter;

public class BusinessIntegrationTestReport extends AbstractBusinessArquillianIntegrationTest {
	private static final long serialVersionUID = 1L;
	
	@Test
	public void report() throws Exception{
		ByteArrayOutputStream outputStream = (ByteArrayOutputStream) ReportBuilder.getInstance().build(new Template().setInputStream(this.getClass()
				.getResourceAsStream("report/jasper/text_without_datasource.jrxml")), null,null, JRTextExporter.class);
		String string = new String(outputStream.toByteArray());
		assertThat(string).contains("This is a text");
	}
	
	@org.jboss.arquillian.container.test.api.Deployment
	public static WebArchive createArchive(){
		String f = "org/cyk/utility/server/business/report/jasper/text_without_datasource.jrxml";
		return new WebArchiveBuilder().execute().addAsResource(f, f); 
	}
	
}
