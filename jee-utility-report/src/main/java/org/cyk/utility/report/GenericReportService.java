package org.cyk.utility.report;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path(GenericReportService.PATH)
@Tag(name = "Report")
public interface GenericReportService extends ReportService {
	String PATH = "reports";
	
	@POST
	@Path("/")
	Response get(@QueryParam(PARAMETER_IDENTIFIER) String identifier,@QueryParam(PARAMETER_PARAMETERS_AS_JSON) String parametersAsJson,@QueryParam(PARAMETER_FILE_TYPE) String fileType
			,@QueryParam(PARAMETER_IS_CONTENT_INLINE) Boolean isContentInline);
}