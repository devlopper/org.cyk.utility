package org.cyk.utility.report;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
import org.cyk.utility.__kernel__.constant.ConstantString;
import org.cyk.utility.__kernel__.enumeration.EnumerationHelper;
import org.cyk.utility.__kernel__.file.FileHelper;
import org.cyk.utility.__kernel__.file.FileType;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Path(ReportRepresentation.PATH)
@Tag(name = "Report")
public interface ReportRepresentation extends org.cyk.utility.__kernel__.representation.Representation {

	@GET
	@Path(PATH_GET)
	@Produces({MediaType.APPLICATION_OCTET_STREAM})
	@Operation(description = "Get report")
	@APIResponses(value = {
		@APIResponse(responseCode = "200")	
	})
	Response get(@QueryParam(PARAMETER_IDENTIFIER) String identifier,@QueryParam(PARAMETER_PARAMETERS_NAMES_VALUES_AS_JSON) String parametersNamesValuesAsJson
			,@QueryParam(PARAMETER_FILE_TYPE) String fileType,@QueryParam(PARAMETER_IS_INLINE) String isInline);

	public static abstract class AbstractImpl extends AbstractObject implements ReportRepresentation,Serializable{
		
		@Override
		public Response get(String identifier,String parametersNamesValuesAsJson,String fileType, String isInline) {
			if(StringHelper.isBlank(identifier))
				return Response.status(Status.BAD_REQUEST).entity("Report identifier is required").build();
			Map<Object, Object> parameters = StringHelper.isBlank(parametersNamesValuesAsJson) ? null : new Gson().fromJson(parametersNamesValuesAsJson, new TypeToken<Map<String, String>>() {}.getType());
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
	
	String PATH_GET_URL_FORMAT = "%s://%s:%s/api"+PATH+PATH_GET+"?"+PARAMETER_IDENTIFIER+"=%s";
	static String formatPathGetUrl(String identifier) {
		HttpServletRequest httpServletRequest = DependencyInjection.inject(HttpServletRequest.class);
		URI uri = URI.create(httpServletRequest.getRequestURI());
		return String.format(PATH_GET_URL_FORMAT,uri.getScheme(),uri.getHost(),uri.getPort(),identifier);
	}
	
	static String buildURIQuery(String identifier,Map<String,String> parameters,String fileType,String isInline) {
		ThrowableHelper.throwIllegalArgumentExceptionIfBlank("report identifier", identifier);
		Map<String,String> queryParameters = new HashMap<>();
		queryParameters.put(PARAMETER_IDENTIFIER, identifier);	
		if(MapHelper.isNotEmpty(parameters)) {
			String parametersAsJson = null;
			try {
				parametersAsJson = new Gson().toJson(parameters);
			} catch (Exception exception) {
				exception.printStackTrace();
			}
			if(StringHelper.isNotBlank(parametersAsJson)) {
				parametersAsJson = URLEncoder.encode(parametersAsJson, Charset.defaultCharset());
				queryParameters.put(PARAMETER_PARAMETERS_NAMES_VALUES_AS_JSON, parametersAsJson);
			}
		}
		if(StringHelper.isNotBlank(fileType))
			queryParameters.put(PARAMETER_FILE_TYPE, fileType);
		if(StringHelper.isNotBlank(isInline))
			queryParameters.put(PARAMETER_IS_INLINE, isInline);
		return queryParameters.entrySet().stream().map(entry -> entry.getKey()+"="+entry.getValue()).collect(Collectors.joining("&"));
	}
}