package org.cyk.utility.__kernel__.uri;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.map.MapHelper;
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
			if(MapHelper.isNotEmpty(arguments.queries)) {
				String suffix = null;
				/*String suffix = StringHelper.concatenate(arguments.queries.keySet().stream()
					.filter(x -> StringHelper.isNotBlank(x) && CollectionHelper.isNotEmpty(arguments.queries.get(x)))
					.map(x -> x+"="+URLEncoder.encode(arguments.queries.get(x).get(0),Charset.forName("UTF-8")))
					.collect(Collectors.toList()),"&");
				*/
				Collection<String> strings = new ArrayList<>();
				for(Map.Entry<String, List<String>> entry : arguments.queries.entrySet()) {
					if(StringHelper.isBlank(entry.getKey()) || CollectionHelper.isEmpty(entry.getValue()))
						continue;
					for(String value : entry.getValue()) {
						if(StringHelper.isBlank(value))
							continue;
						strings.add(entry.getKey()+"="+value);
					}						
				}
				suffix = StringHelper.concatenate(strings, "&");
				if(StringHelper.isNotBlank(suffix))
					query = query + (StringHelper.isBlank(query) ? "?" : "&") + suffix;
			}
			return query;
		}
	}
	
	/**/
	
	String FORMAT = "%s://%s:%s%s%s%s";
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments {
		private String scheme,host,context,path,query;
		private Map<String,List<String>> queries;
		private Integer port;
		private Object request;
		
		public Map<String,List<String>> getQueries(Boolean instantiateIfNull) {
			if(queries == null && Boolean.TRUE.equals(instantiateIfNull))
				queries = new LinkedHashMap<>();
			return queries;
		}
		
		public Arguments addQuery(Object name,Object value) {
			if(name == null || value == null)
				return null;
			String nameAsString = StringHelper.get(name);
			if(StringHelper.isBlank(nameAsString))
				return this;
			List<String> values = convertValueToStrings(value);
			if(CollectionHelper.isEmpty(values))
				return this;
			Map<String,List<String>> queries = getQueries(Boolean.TRUE);
			List<String> list = queries.get(nameAsString);
			if(list == null)
				queries.put(nameAsString, list = new ArrayList<>());			
			list.addAll(values);
			return this;
		}
		
		private List<String> convertValueToStrings(Object value) {
			if(value == null)
				return null;
			if(value instanceof String)
				return List.of((String)value);
			if(value instanceof Collection)
				return ((Collection<?>)value).stream().filter(x -> x != null).map(x -> x.toString()).filter(x -> StringHelper.isNotBlank(x)).collect(Collectors.toList());
			throw new RuntimeException(String.format("cannot convert value of class <<%s>> to strings",value.getClass()));
		}
		/*
		private static String encoreQueryValue(String value) {
			if(StringHelper.isBlank(value))
				return null;
			String encoded = URLEncoder.encode((String)value,Charset.forName("UTF-8"));
			System.out.println("UniformResourceIdentifierBuilder.Arguments.encoreQueryValue() : "+encoded);
			return encoded;
		}*/
		
		public Arguments addQueries(Map<String,List<String>> queries) {
			if(MapHelper.isEmpty(queries))
				return this;
			getQueries(Boolean.TRUE).putAll(queries);
			return this;
		}
	}
	
	static UniformResourceIdentifierBuilder getInstance() {
		return Helper.getInstance(UniformResourceIdentifierBuilder.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();	
}