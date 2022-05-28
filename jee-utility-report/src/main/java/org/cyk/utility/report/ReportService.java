package org.cyk.utility.report;

import org.cyk.utility.service.Service;

public interface ReportService extends Service {

	String PARAMETER_IDENTIFIER = "identifier";
	String PARAMETER_FILE_TYPE = "file-type";
	String PARAMETER_IS_CONTENT_INLINE = "is-content-inline";
	String PARAMETER_PARAMETERS_AS_JSON = "parameters-as-json";
	
}