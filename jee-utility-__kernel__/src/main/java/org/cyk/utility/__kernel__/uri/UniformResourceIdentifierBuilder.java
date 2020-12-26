package org.cyk.utility.__kernel__.uri;

import java.io.Serializable;
import java.net.URI;

import javax.servlet.http.HttpServletRequest;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface UniformResourceIdentifierBuilder {

	URI build(Arguments arguments);
	URI build(String scheme,String host,Integer port,String context,String path,String query);
	URI buildFromRequest(Object request,String path,String query);
	URI buildFromCurrentRequest(String path,String query);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements UniformResourceIdentifierBuilder,Serializable {
		@Override
		public URI build(Arguments arguments) {
			String string = String.format(FORMAT, getScheme(arguments),getHost(arguments),getPort(arguments),getContext(arguments),getPath(arguments),getQuery(arguments));
			return URI.create(string);
		}
		
		@Override
		public URI build(String scheme, String host, Integer port, String context, String path, String query) {
			return build(new Arguments().setScheme(scheme).setHost(host).setPort(port).setContext(context).setPath(path).setQuery(query));
		}
		
		@Override
		public URI buildFromRequest(Object request, String path, String query) {
			return build(new Arguments().setRequest(request).setPath(path).setQuery(query));
		}
		
		@Override
		public URI buildFromCurrentRequest(String path, String query) {
			return buildFromRequest(__inject__(HttpServletRequest.class), path, query);
		}
		
		/**/
		
		protected String getScheme(Arguments arguments) {
			if(StringHelper.isNotBlank(arguments.scheme))
				return arguments.scheme;
			if(arguments.request != null)
				return UniformResourceIdentifierPartGetter.getInstance().getSchemeFromRequest(arguments.request);
			return null;
		}
		
		protected String getHost(Arguments arguments) {
			if(StringHelper.isNotBlank(arguments.host))
				return arguments.host;
			if(arguments.request != null)
				return UniformResourceIdentifierPartGetter.getInstance().getHostFromRequest(arguments.request);
			return null;
		}
		
		protected Integer getPort(Arguments arguments) {
			if(arguments.port != null)
				return arguments.port;
			if(arguments.request != null)
				return UniformResourceIdentifierPartGetter.getInstance().getPortFromRequest(arguments.request);
			return null;
		}
		
		protected String getContext(Arguments arguments) {
			String context = arguments.context;
			if(context == null)
				context = UniformResourceIdentifierPartGetter.getInstance().getContextFromRequest(arguments.request);
			if(context == null)
				context = ConstantEmpty.STRING;
			if(!context.startsWith("/"))
				context = "/"+context;			
			return context;
		}
		
		protected String getPath(Arguments arguments) {
			String path = arguments.path;
			if(path == null)
				path = ConstantEmpty.STRING;
			if(!path.startsWith("/"))
				path = "/"+path;
			return path;
		}
		
		protected String getQuery(Arguments arguments) {
			String query = arguments.query;
			if(query == null)
				query = ConstantEmpty.STRING;
			if(!query.isBlank() && !query.startsWith("?"))
				query = "?"+query;
			return query;
		}
	}
	
	/**/
	
	String FORMAT = "%s://%s:%s%s%s%s";
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments {
		private String scheme,host,context,path,query;
		private Integer port;
		private Object request;
	}
	
	static UniformResourceIdentifierBuilder getInstance() {
		return Helper.getInstance(UniformResourceIdentifierBuilder.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();	
}