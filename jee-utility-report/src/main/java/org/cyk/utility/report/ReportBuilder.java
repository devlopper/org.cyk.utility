package org.cyk.utility.report;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.Value;

public interface ReportBuilder {

	ByteArrayOutputStream build(Template template,Object connection,Object dataSource,Object exporter);
	
	/**/
	
	public abstract class AbstractImpl extends AbstractObject implements ReportBuilder, Serializable {

		@Override
		public ByteArrayOutputStream build(Template template, Object connection, Object dataSource, Object exporter) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("template", template);
			if(connection == null && dataSource == null)
				throw new RuntimeException("Connection or data source is required");
			return __build__(template, connection, dataSource, exporter);
		}
		
		protected ByteArrayOutputStream __build__(Template template, Object connection, Object dataSource, Object exporter) {
			if(connection == null)
				return __buildFromDataSource__(template, dataSource, exporter);
			if(dataSource == null)
				return __buildFromConnection__(template, connection, exporter);
			else {
				LogHelper.logWarning("Connection will be used instead of data source", getClass());
				return __buildFromConnection__(template, connection, exporter);
			}
		}
		
		protected abstract ByteArrayOutputStream __buildFromConnection__(Template template, Object connection, Object exporter);
		
		protected abstract ByteArrayOutputStream __buildFromDataSource__(Template template, Object dataSource, Object exporter);
	}
	
	/**/
	
	static ReportBuilder getInstance() {
		return Helper.getInstance(ReportBuilder.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}
