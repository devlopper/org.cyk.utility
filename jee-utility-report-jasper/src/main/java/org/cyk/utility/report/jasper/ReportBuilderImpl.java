package org.cyk.utility.report.jasper;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import org.cyk.utility.report.AbstractReportBuilderImpl;
import org.cyk.utility.report.Template;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleTextReportConfiguration;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.TextReportConfiguration;

public class ReportBuilderImpl extends AbstractReportBuilderImpl implements Serializable {

	@Override
	public ByteArrayOutputStream build(Template template,Object dataSource,Object exporter) {
		if(template == null)
			return null;
		if(dataSource == null)
			dataSource = new JREmptyDataSource();
		if(dataSource!=null && !(dataSource instanceof JRDataSource))
			return null;			
		JRDataSource jrDataSource = (JRDataSource) dataSource;
		try {
             JasperDesign jasperDesign = JRXmlLoader.load(template.getInputStream());
             JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);            
             JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, template.getArguments(),jrDataSource);
             
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             if(exporter == null) {
            	 exporter = new JRPdfExporter(); 
             }
                          
        	 if((exporter instanceof JRPdfExporter) || (exporter instanceof Class && JRPdfExporter.class.equals(exporter))) {
        		 JRPdfExporter jrPdfExporter = null;
        		 if(exporter instanceof Class)
        			 jrPdfExporter = new JRPdfExporter();
        		 else
        			 jrPdfExporter = (JRPdfExporter) exporter;
        		 jrPdfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));             
        		 jrPdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
        		 jrPdfExporter.exportReport();
        	 }else if((exporter instanceof JRTextExporter) || (exporter instanceof Class && JRTextExporter.class.equals(exporter))) {
        		 JRTextExporter jrTextExporter = null;
        		 if(exporter instanceof Class)
        			 jrTextExporter = new JRTextExporter();
        		 else
        			 jrTextExporter = (JRTextExporter) exporter;
        		 TextReportConfiguration textReportConfiguration = new SimpleTextReportConfiguration() {
        			 @Override
        			public Float getCharHeight() {
        				return 13.9f;
        			}
        			 
        			 @Override
        			public Float getCharWidth() {
        				return 7f;
        			}
        		 };
        		 jrTextExporter.setConfiguration(textReportConfiguration);       		 
        		 jrTextExporter.setExporterInput(new SimpleExporterInput(jasperPrint));             
        		 jrTextExporter.setExporterOutput(new SimpleWriterExporterOutput(byteArrayOutputStream));
        		 jrTextExporter.exportReport();
        	 }            	                       
             return byteArrayOutputStream;
         } catch (Exception exception) {
             throw new RuntimeException(exception);
         }
	}

}
