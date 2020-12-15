package org.cyk.utility.report.jasper.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.cyk.utility.__kernel__.file.FileType;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.report.ReportGetter;
import org.cyk.utility.__kernel__.throwable.RuntimeException;
import org.cyk.utility.__kernel__.value.ValueHelper;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.RunReportAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.util.ReportOutputFormat;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.ResourceNotFoundException;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

public class ReportGetterImpl extends ReportGetter.AbstractImpl implements Serializable {

	@Override
	protected ByteArrayOutputStream __getFromServer__(String filePath,Map<Object,Object> parameters,FileType fileType) {
		Session session = SessionGetter.getInstance().get();
		if(session == null)
			throw new RuntimeException("Session is null");
		OperationResult<InputStream> result = null;
		try {			
			RunReportAdapter runReportAdapter = session
			        .reportingService()
			        .report(filePath)
			        .prepareForRun(ValueHelper.defaultToIfNull(getReportOutputFormat(fileType), ReportOutputFormat.PDF));
			if(MapHelper.isNotEmpty(parameters))
				parameters.forEach( (key,value) -> {
					if(key != null) {
						@SuppressWarnings("unchecked")
						List<String> strings = (List<String>)(value instanceof List ? value : List.of(value));
						runReportAdapter.parameter(key.toString(), strings);
					}
				} );
			result = (OperationResult<InputStream>) runReportAdapter.run();
		} catch (ResourceNotFoundException resourceNotFoundException) {
			throwNotFound(filePath);
		}
		if(result == null)
			throw new RuntimeException("Result is null");
		InputStream inputStream = result.getEntity();
		ByteArrayOutputStream byteArrayOutputStream;
		try {
			byteArrayOutputStream = new ByteArrayOutputStream();
			byteArrayOutputStream.write(IOUtils.toByteArray(inputStream));
		} catch (IOException exception) {
			LogHelper.log(exception, getClass());
			return null;
		}
		return byteArrayOutputStream;
	}

	private ReportOutputFormat getReportOutputFormat(FileType fileType) {
		if(fileType == null)
			fileType = FileType.PDF;
		ReportOutputFormat reportOutputFormat = ReportOutputFormat.valueOf(fileType.name());
		if(reportOutputFormat != null)
			return reportOutputFormat;
		if(reportOutputFormat == null)
			reportOutputFormat = ReportOutputFormat.PDF;
		return reportOutputFormat;
	}
}