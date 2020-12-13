package org.cyk.utility.report.jasper;

import java.io.InputStream;
import java.io.Serializable;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.__kernel__.value.ValueHelper;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.util.ReportOutputFormat;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface OperationExecutor {

	<T> T execute(Class<T> resultClass,Arguments arguments);
	InputStream execute(Arguments arguments);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements OperationExecutor,Serializable {
		
		@SuppressWarnings("unchecked")
		@Override
		public <T> T execute(Class<T> resultClass, Arguments arguments) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("result class", resultClass);
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("arguments", arguments);
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("file path", arguments.getFilePath());
			T entity = null;
			if(InputStream.class.equals(resultClass)) {
				OperationResult<InputStream> result = (OperationResult<InputStream>) SessionGetter.getInstance().get()
						.reportingService()
				        .report(arguments.getFilePath())
				        .prepareForRun(ValueHelper.defaultToIfNull(arguments.getReportOutputFormat(), ReportOutputFormat.PDF))
				        .run();
				entity = (T) result.getEntity();
			}else
				throw new RuntimeException(String.format("Result class %s not yet handled", resultClass));
			return entity;
		}
		
		@Override
		public InputStream execute(Arguments arguments) {
			return execute(InputStream.class, arguments);
		}
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments implements Serializable {
		private String filePath;
		private ReportOutputFormat reportOutputFormat;
	}
	
	/**/
	
	static OperationExecutor getInstance() {
		return Helper.getInstance(OperationExecutor.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}