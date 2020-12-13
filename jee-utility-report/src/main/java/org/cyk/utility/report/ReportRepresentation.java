package org.cyk.utility.report;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.constant.ConstantString;
import org.cyk.utility.__kernel__.enumeration.EnumerationHelper;
import org.cyk.utility.__kernel__.file.FileHelper;
import org.cyk.utility.__kernel__.file.FileType;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.ValueHelper;

@Path(ReportRepresentation.PATH)
//@Tag(name = "Report")
public interface ReportRepresentation extends org.cyk.utility.__kernel__.representation.Representation {

	@GET
	@Path(PATH_GET)
	@Produces({MediaType.APPLICATION_OCTET_STREAM})
	//@Operation(description = "Get report")
	Response get(@QueryParam(PARAMETER_IDENTIFIER) String identifier,@QueryParam(PARAMETER_PARAMETERS_NAMES) String parametersNames
			,@QueryParam(PARAMETER_FILE_TYPE) String fileType,@QueryParam(PARAMETER_IS_INLINE) String isInline);

	public static abstract class AbstractImpl extends AbstractObject implements ReportRepresentation,Serializable{
		
		@Override
		public Response get(String identifier,String parametersNames,String fileType, String isInline) {
			if(StringHelper.isBlank(identifier))
				return Response.status(Status.BAD_REQUEST).entity("Report identifier is required").build();
			Map<Object,Object> parameters = null;
			if(StringHelper.isNotBlank(parametersNames)) {
				String[] parametersNamesAsArray = parametersNames.split(",");
				if(ArrayHelper.isNotEmpty(parametersNamesAsArray)) {
					HttpServletRequest request = DependencyInjection.inject(HttpServletRequest.class);
					for(String index : parametersNamesAsArray) {
						String[] values = request.getParameterValues(index);
						if(ArrayHelper.isEmpty(values))
							continue;
						if(parameters == null)
							parameters = new HashMap<>();
						parameters.put(index, values[0]);
					}
				}
			}			
			FileType __fileType__ = ValueHelper.defaultToIfNull(EnumerationHelper.getByName(FileType.class, fileType, Boolean.FALSE),FileType.PDF);
			ByteArrayOutputStream byteArrayOutputStream = ReportGetter.getInstance().get(identifier,parameters,__fileType__);
			if(byteArrayOutputStream == null)
				return Response.status(Status.NOT_FOUND).build();
			byte[] bytes = byteArrayOutputStream.toByteArray();
			ResponseBuilder response = Response.ok(bytes);
			String extension = __fileType__.name().toLowerCase();
		    response.header(HttpHeaders.CONTENT_TYPE, FileHelper.getMimeTypeByExtension(extension));
		    if(StringHelper.isBlank(isInline))
		    	isInline = Boolean.TRUE.toString();
		    response.header(HttpHeaders.CONTENT_DISPOSITION, (Boolean.parseBoolean(isInline) ? ConstantString.INLINE : ConstantString.ATTACHMENT)+"; "+ConstantString.FILENAME
		    		+"=file_"+System.currentTimeMillis()+"."+extension);
		    response.header(HttpHeaders.CONTENT_LENGTH, bytes.length);
		    return response.build();
		}	
	}
	
	String PATH = "/report";
	String PATH_GET = __SLASH__+"get";
}