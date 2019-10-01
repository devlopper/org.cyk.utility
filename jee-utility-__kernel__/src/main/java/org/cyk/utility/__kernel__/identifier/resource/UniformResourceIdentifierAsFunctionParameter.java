package org.cyk.utility.__kernel__.identifier.resource;

import org.cyk.utility.__kernel__.AbstractAsFunctionParameter;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.__kernel__.system.action.SystemActionAsFunctionParameter;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class UniformResourceIdentifierAsFunctionParameter extends AbstractAsFunctionParameter<String> {

	private String scheme;
	private String user;
	private String password;
	private String host;
	private Integer port;
	private PathAsFunctionParameter path;
	private QueryAsFunctionParameter query;
	private String fragment;
	
	private SystemActionAsFunctionParameter systemAction;
	private SystemActionAsFunctionParameter nextSystemAction;
	private Object request;
	private String model;
	
	public String getScheme() {
		if(StringHelper.isBlank(scheme) && request!=null)
			scheme = RequestHelper.getPropertyScheme(request);
		return scheme;
	}
	
	public String getHost() {
		if(StringHelper.isBlank(host) && request!=null)
			host = RequestHelper.getPropertyHost(request);
		return host;
	}
	
	public Integer getPort() {
		if(port == null && request!=null)
			port = RequestHelper.getPropertyPort(request);
		return port;
	}
	
	public PathAsFunctionParameter getPath(Boolean injectIfNull) {
		if(path == null && Boolean.TRUE.equals(injectIfNull))
			setPath(path = new PathAsFunctionParameter());
		return path;
	}
	
	public QueryAsFunctionParameter getQuery(Boolean injectIfNull) {
		if(query == null && Boolean.TRUE.equals(injectIfNull))
			query = new QueryAsFunctionParameter();
		return query;
	}
	
	public UniformResourceIdentifierAsFunctionParameter setSystemActionClass(Class<? extends SystemAction> systemActionClass) {
		getPath(Boolean.TRUE).getSystemAction(Boolean.TRUE).setKlass(systemActionClass);
		getQuery(Boolean.TRUE).getSystemAction(Boolean.TRUE).setKlass(systemActionClass);
		return this;
	}
	
	public UniformResourceIdentifierAsFunctionParameter setSystemActionEntityClass(Class<?> entityClass) {
		getPath(Boolean.TRUE).getSystemAction(Boolean.TRUE).getEntities(Boolean.TRUE).setElementClass(entityClass);
		getQuery(Boolean.TRUE).getSystemAction(Boolean.TRUE).getEntities(Boolean.TRUE).setElementClass(entityClass);
		return this;
	}
	
	public UniformResourceIdentifierAsFunctionParameter setSystemAction(SystemAction systemAction) {
		if(systemAction == null)
			return this;
		setSystemActionClass(systemAction.getClass());
		setSystemActionEntityClass(systemAction.getEntityClass());
		return this;
	}
}
