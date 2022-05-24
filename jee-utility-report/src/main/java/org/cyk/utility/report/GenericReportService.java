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
	Response get(@QueryParam("identifier") String identifier,@QueryParam("file-type") String fileType,@QueryParam("is-content-inline") Boolean isContentInline,@QueryParam("parameters-as-json") String parametersAsJson);
}