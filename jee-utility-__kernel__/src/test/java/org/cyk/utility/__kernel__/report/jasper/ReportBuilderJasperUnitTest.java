package org.cyk.utility.__kernel__.report.jasper;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.report.ReportBuilder;
import org.cyk.utility.__kernel__.report.Template;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRTextExporter;

public class ReportBuilderJasperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void text_without_datasource(){
		ByteArrayOutputStream outputStream = (ByteArrayOutputStream) ReportBuilder.getInstance().build(new Template().setInputStream(this.getClass()
				.getResourceAsStream("text_without_datasource.jrxml")), null, JRTextExporter.class);
		String string = new String(outputStream.toByteArray());
		assertThat(string).contains("This is a text");
	}
	
	@Test
	public void text_with_datasource_bean_collection(){
		Collection<Bean> beans = new ArrayList<>();
		Bean bean = new Bean();
		bean.setString("hello");
		bean.setIndex(1);
		beans.add(bean);
		bean = new Bean();
		bean.setString("world");
		bean.setIndex(2);
		beans.add(bean);
		JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(beans);
		ByteArrayOutputStream outputStream = (ByteArrayOutputStream) ReportBuilder.getInstance().build(new Template().setInputStream(this.getClass()
				.getResourceAsStream("text_with_datasource_bean_collection.jrxml")), jrBeanCollectionDataSource, JRTextExporter.class);
		String string = new String(outputStream.toByteArray());
		assertThat(string).contains("hello","1","world","2");
	}
	
	
}
