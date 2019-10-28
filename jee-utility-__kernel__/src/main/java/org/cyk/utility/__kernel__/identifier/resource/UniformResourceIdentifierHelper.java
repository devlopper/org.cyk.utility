package org.cyk.utility.__kernel__.identifier.resource;

import static org.cyk.utility.__kernel__.map.MapHelper.getKeys;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.number.NumberHelper;
import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.__kernel__.protocol.Protocol;
import org.cyk.utility.__kernel__.string.Case;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.__kernel__.system.action.SystemActionCreate;
import org.cyk.utility.__kernel__.system.action.SystemActionDelete;
import org.cyk.utility.__kernel__.system.action.SystemActionUpdate;
import org.cyk.utility.__kernel__.value.ValueHelper;

public interface UniformResourceIdentifierHelper {

	/* Parameter value */

	static String buildParameterValue(Object value) {
		if(value == null)
			return null;
		if(value instanceof Class) {
			Class<?> klass = (Class<?>)value;
			if(ClassHelper.isInstanceOf(klass, SystemAction.class))
				if(klass.equals(SystemAction.class))
					return "action";
				else
					if(klass.getSimpleName().endsWith("Impl"))
						return StringUtils.substringBetween(klass.getSimpleName(), SystemAction.class.getSimpleName(), "Impl").toLowerCase();
					else
						return StringUtils.substringAfter(klass.getSimpleName(), SystemAction.class.getSimpleName()).toLowerCase();
			else
				if(klass.getSimpleName().endsWith("Impl"))
					return StringUtils.substringBeforeLast(klass.getSimpleName(), "Impl").toLowerCase();
				else
					return klass.getSimpleName().toLowerCase();
		}else if(value instanceof Objectable) {
			Object identifier = ((Objectable)value).getIdentifier();
			if(identifier == null)
				return null;
			else {
				if(value instanceof SystemAction)
					return identifier.toString().toLowerCase();
				else
					return identifier.toString();	
			}
		}else if(value instanceof String) {
			return (String) value;
		}else
			return value.toString();
		//throw new RuntimeException("Parameter value cannot be found for <<"+value+">>");
	}
	
	static Object mapParameterValue(String parameterName,QueryParameterValueGetter parameterValueGetter) {
		if(parameterName == null)
			return null;
		String parameterValue;
		if(parameterValueGetter == null)
			parameterValueGetter = QueryParameterValueGetter.INSTANCE;		
		parameterValue = parameterValueGetter.get(parameterName);
		if(parameterValue == null || parameterValue.isBlank())
			return null;
		Object value = null;	
		if(ParameterName.ACTION_CLASS.getValue().equals(parameterName)) {
			value = getSystemAction(parameterValue,ParameterName.ACTION_IDENTIFIER,parameterValueGetter);					
			if(value!=null) {
				SystemAction systemAction = (SystemAction) value;
				Class<?> aClass = (Class<?>) mapParameterValue(ParameterName.ENTITY_CLASS.getValue(),parameterValueGetter);
				if(aClass!=null) {
					((SystemAction)value).getEntities(Boolean.TRUE).setElementClass(aClass);
					String entityIdentifier = (String) mapParameterValue(ParameterName.ENTITY_IDENTIFIER.getValue(),parameterValueGetter);
					if(entityIdentifier!=null) {
						//TODO Is it a Number ? a String ? ... How to find out the target identifier field type ?
						//For now we will use string
						systemAction.getEntitiesIdentifiers(Boolean.TRUE).add(entityIdentifier);
					}
				}				
				systemAction.setNextAction((SystemAction) mapParameterValue(ParameterName.NEXT_ACTION_CLASS.getValue(), parameterValueGetter));
			}
		}else if(ParameterName.ACTION_IDENTIFIER.getValue().equals(parameterName)) {
			value = parameterValue;
		}else if(ParameterName.NEXT_ACTION_CLASS.getValue().equals(parameterName)) {
			value = getSystemAction(parameterValue,ParameterName.NEXT_ACTION_IDENTIFIER,parameterValueGetter);
		}else if(ParameterName.NEXT_ACTION_IDENTIFIER.getValue().equals(parameterName)) {
			value = parameterValue;
		}else if(ParameterName.ENTITY_IDENTIFIER.getValue().equals(parameterName)) {
			//TODO Is it a Number ? a String ? ... How to find out the target identifier field type ?
			//value = __inject__(NumberHelper.class).getLong(parameterValue);
			//For now we will use string
			value = parameterValue;
		}else if(ParameterName.ENTITY_CLASS.getValue().equals(parameterName)) {
			value = CollectionHelper.getFirst(getKeys(ParameterName.MAP,null,List.of(ParameterName.ENTITY_CLASS.getType()),List.of(parameterValue)));
		}else if(ParameterName.WINDOW_RENDER_TYPE_CLASS.getValue().equals(parameterName)) {
			value = getKeys(ParameterName.MAP,null,List.of(ParameterName.WINDOW_RENDER_TYPE_CLASS.getType()), List.of(parameterValue));
		}
		
		if(value == null)
			throw new RuntimeException("uniform resource identifier query parameter <<"+parameterName+":"+parameterValue+">> cannot be mapped.");
		return value;
	}
	
	@SuppressWarnings("unchecked")
	static SystemAction getSystemAction(String parameterValue,Object identifierName,QueryParameterValueGetter parameterValueGetter) {
		Class<? extends SystemAction> systemActionClass = null;
		SystemAction systemAction = null;
		//Collection<?> keys = getKeysWhereKeysAreClasses(ParameterName.MAP,List.of(SystemAction.class),parameterValue);
		Collection<?> keys = getKeys(ParameterName.MAP,null,List.of(SystemAction.class),List.of(parameterValue));
		systemActionClass = (Class<? extends SystemAction>) CollectionHelper.getFirst(keys);
		if(systemActionClass!=null) {
			systemAction = DependencyInjection.inject(systemActionClass).setIdentifier(parameterValueGetter.get(ParameterName.stringify(identifierName)));
		}	
		return systemAction;
	}
	
	/* URI */
	
	static String getComponent(Component component,String string) {
		if(component == null || StringHelper.isBlank(string))
			return null;
		String value = null;	
		URI uri = URI.create(string);
		if(uri!=null) {
			switch(component) {
			case SCHEME: return uri.getScheme();
			case USER : return StringUtils.substringBefore(uri.getUserInfo(), ":");
			case PASSWORD : return StringUtils.substringAfter(uri.getUserInfo(), ":");
			case HOST: return uri.getHost();
			case PORT: return uri.getPort() == -1 ? ConstantEmpty.STRING : String.valueOf(uri.getPort());
			case PATH: return StringUtils.defaultIfBlank(uri.getPath(),null);
			case QUERY: return uri.getQuery();
			case FRAGMENT : return uri.getFragment();
			}		
		}
		return value;
	}
	
	static String buildQueryFromStringsMap(Map<String,List<String>> parameters) {
		if(parameters == null || parameters.isEmpty())
			return null;
		Collection<String> keysValues = null;
		for(Map.Entry<String,List<String>> entry : parameters.entrySet()) {
			if(StringHelper.isNotBlank(entry.getKey()) && CollectionHelper.isNotEmpty(entry.getValue())) {
				if(keysValues == null)
					keysValues = new ArrayList<>();
				//FIXME , this is not a standard way to do
				keysValues.add(entry.getKey()+"="+StringHelper.concatenate(entry.getValue(), ","));
			}
		}
		return keysValues == null || keysValues.isEmpty() ? null : StringUtils.join(keysValues,"&");
	}
	
	static String buildQuery(Map<Object,List<Object>> parameters) {
		if(parameters == null || parameters.isEmpty())
			return null;
		Map<String,List<String>> __parameters__ = new LinkedHashMap<>();
		for(Map.Entry<Object,List<Object>> index : parameters.entrySet()) {
			String name = null;
			if(index.getKey()!=null)
				name = ParameterName.stringify(index.getKey());
			if(StringHelper.isNotBlank(name)) {
				List<String> values = new ArrayList<>();
				String value = null;
				if(index.getValue()!=null) {
					for(Object object : index.getValue()) {
						value = buildParameterValue(object);
						if(StringHelper.isNotBlank(value)) {
							if(values == null)
								values = new ArrayList<>();
							values.add(value);
						}
					}
					if(CollectionHelper.isNotEmpty(values))
						__parameters__.put(name,values);	
				}
			}
		}
		return buildQueryFromStringsMap(__parameters__);
	}
	
	static <T> String buildQuery(Class<? extends SystemAction> systemActionClass,Object systemActionIdentifier,Class<T> entityClass,Collection<T> entities,Collection<?> identifiers,Class<? extends SystemAction> nextSystemActionClass,Object nextSystemActionIdentifier) {
		Map<Object,List<Object>> parameters = new LinkedHashMap<>();
		if(entityClass != null)
			parameters.put(ParameterName.ENTITY_CLASS,Arrays.asList(entityClass));
		List<Object> list = null;
		if(entities!=null) {
			list = (List<Object>) FieldHelper.readSystemIdentifiers(entities);
		}
		if(identifiers != null) {
			if(list == null)
				list = new ArrayList<Object>();
			list.addAll(identifiers);
		}
		if(list!=null && !list.isEmpty())
			parameters.put(ParameterName.ENTITY_IDENTIFIER,new ArrayList<Object>(list));	
		
		if(systemActionClass != null) {
			parameters.put(ParameterName.ACTION_CLASS,Arrays.asList(systemActionClass));	
			if(systemActionIdentifier == null) 
				systemActionIdentifier = buildParameterValue(systemActionClass);
			if(systemActionIdentifier != null)
				parameters.put(ParameterName.ACTION_IDENTIFIER,Arrays.asList(systemActionIdentifier));		
		}
		if(nextSystemActionClass != null) {
			parameters.put(ParameterName.NEXT_ACTION_CLASS,Arrays.asList(nextSystemActionClass));	
			if(nextSystemActionIdentifier == null)
				nextSystemActionIdentifier = buildParameterValue(nextSystemActionClass);
			if(nextSystemActionIdentifier != null)
				parameters.put(ParameterName.NEXT_ACTION_IDENTIFIER,Arrays.asList(nextSystemActionIdentifier));
		}
		return buildQuery(parameters);
	}
	
	@SuppressWarnings("unchecked")
	static String buildQuery(SystemAction systemAction) {
		return systemAction == null ? null : buildQuery(systemAction.getClass(), systemAction.getIdentifier() == null ? null : systemAction.getIdentifier().toString().toLowerCase()
				, (Class<Object>)systemAction.getEntityClass(), systemAction.getEntities() == null ? null : systemAction.getEntities().get()
				, systemAction.getEntitiesIdentifiers() == null ? null : systemAction.getEntitiesIdentifiers().get()
				, systemAction.getNextAction() == null ? null : systemAction.getNextAction().getClass()
				, systemAction.getNextAction() == null ? null : systemAction.getNextAction().getIdentifier());
	}
	
	@SuppressWarnings("unchecked")
	static String buildQuery(QueryAsFunctionParameter parameter) {
		if(parameter == null)
			return null;
		if(StringHelper.isNotBlank(parameter.getValue()))
			return parameter.getValue();
		if(parameter.getSystemAction() != null) {
			return buildQuery(parameter.getSystemAction().getKlass(), parameter.getSystemAction().getIdentifier(), (Class<Object>) parameter.getSystemAction().getEntityClass()
					, (Collection<Object>) parameter.getSystemAction().getEntitiesCollection(), parameter.getSystemAction().getEntitiesIdentifiersCollection()
					, parameter.getSystemAction().getNext() == null ? null : parameter.getSystemAction().getNext().getKlass()
					, parameter.getSystemAction().getNext() == null ? null : parameter.getSystemAction().getNext().getEntityClass());
		}
		throw new RuntimeException("building query from parameter <<"+parameter+">> not yet handled");
	}

	/**/
	
	static String build(String scheme,String user,String password,String host,Integer port,String path,String query,String fragment,String model) {	
		String uniformResourceIdentifier = ConstantEmpty.STRING;
		//scheme
		if(StringHelper.isBlank(scheme))
			scheme = getComponent(Component.SCHEME, model);
		if(StringHelper.isNotBlank(scheme))
			uniformResourceIdentifier = scheme+"://";
		//user
		if(StringHelper.isBlank(user))
			user = getComponent(Component.USER, model);		
		if(StringHelper.isNotBlank(user)) {
			uniformResourceIdentifier += user;
			if(StringHelper.isBlank(password))
				password = getComponent(Component.PASSWORD, model);		
			if(StringHelper.isNotBlank(password))
				uniformResourceIdentifier += ":"+password;
			uniformResourceIdentifier += "@";
		}
		//host	
		if(StringHelper.isBlank(host))
			host = getComponent(Component.HOST, model);	
		if(StringHelper.isNotBlank(host)) {
			uniformResourceIdentifier += host;
			if(port == null)
				port = NumberHelper.getInteger(getComponent(Component.PORT, model));
			if(port!= null && port > 0 && scheme!=null && port != Helper.getEnumByName(Protocol.class, scheme).getDefaultPort())
				uniformResourceIdentifier += ":"+port;
		}
		//path
		if(StringHelper.isBlank(path))
			path = getComponent(Component.PATH, model);	
		if(StringHelper.isNotBlank(path))
			uniformResourceIdentifier += formatPath(path);

		//query
		if(StringHelper.isBlank(query))
			query = getComponent(Component.QUERY, model);
		if(StringHelper.isNotBlank(query)) {
			uniformResourceIdentifier += "?"+query;
		}
		//fragment
		if(StringHelper.isBlank(fragment))
			fragment = getComponent(Component.FRAGMENT, model);	
		if(StringHelper.isNotBlank(fragment)) {
			uniformResourceIdentifier += "#"+fragment;
		}
		return uniformResourceIdentifier;
	}
	
	static String build(String scheme,String host,Integer port,String path,String query,String fragment,String model) {
		return build(scheme, null, null, host, port, path, query, fragment, model);
	}
	
	static <T> String build(String scheme,String host,Integer port,Class<? extends SystemAction> systemActionClass,Object systemActionIdentifier
			,Class<T> entityClass,Collection<T> entities,Collection<?> identifiers,Class<? extends SystemAction> nextSystemActionClass,Object nextSystemActionIdentifier) {
		return build(scheme, null, null, host, port, buildPath(systemActionClass, entityClass, null, Boolean.TRUE)
				, buildQuery(systemActionClass, systemActionIdentifier, entityClass, entities, identifiers, nextSystemActionClass, nextSystemActionIdentifier), null, null);
	}
	
	static String build(String scheme,String host,Integer port,SystemAction systemAction) {
		return build(scheme, null, null, host, port, buildPath(systemAction), buildQuery(systemAction), null, null);
	}
	
	static <T> String build(Object request,Class<? extends SystemAction> systemActionClass,Object systemActionIdentifier,Class<T> entityClass
			,Collection<T> entities,Collection<?> identifiers,Class<? extends SystemAction> nextSystemActionClass,Object nextSystemActionIdentifier) {
		return build(RequestHelper.getPropertyScheme(request), RequestHelper.getPropertyHost(request), RequestHelper.getPropertyPort(request)
				, systemActionClass, systemActionIdentifier, entityClass, entities, identifiers, nextSystemActionClass, nextSystemActionIdentifier);
	}
	
	static String build(Object request,SystemAction systemAction) {
		return build(RequestHelper.getPropertyScheme(request), RequestHelper.getPropertyHost(request), RequestHelper.getPropertyPort(request)
				, systemAction);
	}
	
	static String build(UniformResourceIdentifierAsFunctionParameter parameter) {
		if(parameter == null)
			return null;
		if(StringHelper.isNotBlank(parameter.getValue()))
			return parameter.getValue();
		String path = null;
		if(parameter.getPath() != null)
			path = buildPath(parameter.getPath());
		String query = null;
		if(parameter.getQuery() != null)
			query = buildQuery(parameter.getQuery());
		return build(parameter.getScheme(), parameter.getHost(), parameter.getPort(), path, query, parameter.getFragment(), parameter.getModel());
	}
	
	/* Path */
	
	static String formatPath(String path) {
		if(StringHelper.isNotBlank(path)) {
			if(!path.startsWith("/"))
				path = "/"+path;
		}
		path = StringUtils.replace(path, "//", "/");
		return path;
	}
	
	static String buildPath(String identifier,String root) {
		String path = concatenatePathRoot(null, root);
		if(StringHelper.isNotBlank(identifier)) {
			String __path__ = getPathByIdentifier(identifier);
			if(StringHelper.isNotBlank(__path__)) {
				if(StringHelper.isBlank(path))
					path = __path__;
				else
					path = path + formatPath(__path__);	
			}
		}
		return formatPath(path);
	}
	
	private static String concatenatePathRoot(String path,String root) {		
		root = ValueHelper.defaultToIfBlank(root, Component.PATH_ROOT);	
		if(StringHelper.isNotBlank(root)) {
			path = root + (path == null ? ConstantEmpty.STRING : path);
		}
		return path;
	}
	
	static String buildPath(String identifier) {
		return buildPath(identifier, null);
	}
	
	static String buildPath(Class<? extends SystemAction> systemActionClass,Class<?> entityClass,String root,Boolean isRecursive) {
		String path = null;
		//Find specific for entity class
		String identifier01 = buildPathIdentifier(systemActionClass,entityClass,null);
		if(identifier01!=null) {
			path = PATHS_CACHE_MAP.get(identifier01);
			if(path != null)
				return path;
			path = getPathByIdentifier(identifier01);
			if(path == null && Boolean.TRUE.equals(isRecursive)) {
				//Find generic for any entity class
				String identifier02 = buildPathIdentifier(systemActionClass,null,Boolean.TRUE);
				path = getPathByIdentifier(identifier02);	
			}
		}
		if(path != null) {
			path = concatenatePathRoot(path, root);
			PATHS_CACHE_MAP.put(identifier01, path);
		}	
		return path;
	}
	
	static String buildPath(SystemAction systemAction,String root,Boolean isRecursive) {
		/*String path = null;
		String identifier01 = buildPathIdentifier(systemAction);
		if(identifier01!=null) {
			//Find specific for entity class
			path = PATHS_CACHE_MAP.get(identifier01);
			if(path != null)
				return path;
			path = getPathByIdentifier(identifier01);
			if(path == null && Boolean.TRUE.equals(isRecursive)) {
				//Find generic for any entity class
				Class<?> klass = systemAction.getEntityClass();
				systemAction.setEntityClass(null);
				String identifier02 = buildPathIdentifier(systemAction);
				systemAction.setEntityClass(klass);
				path = getPathByIdentifier(identifier02);	
			}
		}
		if(path != null) {
			path = concatenatePathRoot(path, root);
			PATHS_CACHE_MAP.put(identifier01, path);
		}*/	
		return systemAction == null ? null : buildPath(systemAction.getClass(), systemAction.getEntityClass(), root, isRecursive);
	}
	
	static String buildPath(SystemAction systemAction) {
		return buildPath(systemAction, null, Boolean.TRUE);
	}
	
	static String buildPath(PathAsFunctionParameter parameter) {
		if(parameter == null)
			return null;
		if(StringHelper.isNotBlank(parameter.getValue()))
			return parameter.getValue();
		if(StringHelper.isNotBlank(parameter.getIdentifier()))
			return buildPath(parameter.getIdentifier());
		if(parameter.getSystemAction()!=null) {
			if(parameter.getSystemAction().getKlass()!=null && parameter.getSystemAction().getEntityClass()!=null)
				return buildPath(parameter.getSystemAction().getKlass(), parameter.getSystemAction().getEntityClass(), null, Boolean.TRUE);
		}
		throw new RuntimeException("building path from parameter <<"+parameter+">> not yet handled");
	}
	
	static String buildPathIdentifier(Class<? extends SystemAction> systemActionClass,Class<?> entityClass,Boolean isUseGeneric) {
		if(systemActionClass == null)
			return null;
		Object[] arguments = new Object[2];		
		arguments[0] = entityClass==null ? IDENTIFIER_ENTITY_TOKEN : StringHelper.applyCase(entityClass.getSimpleName().endsWith("Impl") 
				? StringUtils.substringBefore(entityClass.getSimpleName(), "Impl") : entityClass.getSimpleName(), Case.FIRST_CHARACTER_LOWER);
		if(Boolean.TRUE.equals(isUseGeneric) && ClassHelper.isInstanceOfOne(systemActionClass,SystemActionCreate.class,SystemActionUpdate.class,SystemActionDelete.class))
			arguments[1] = IDENTIFIER_EDIT_TOKEN;
		else
			arguments[1] = systemActionClass.getSimpleName().endsWith("Impl") 
					? StringUtils.substringBetween(systemActionClass.getSimpleName(), SystemAction.class.getSimpleName(), "Impl")
					: StringUtils.substringAfter(systemActionClass.getSimpleName(), SystemAction.class.getSimpleName());
		return String.format(IDENTIFIER_FORMAT, arguments);
	}
	
	static String buildPathIdentifier(SystemAction systemAction) {
		return buildPathIdentifier(systemAction.getClass(), systemAction.getEntityClass(), systemAction.getEntityClass() == null);
	}
	
	static String getPathByIdentifier(String identifier) {
		return PATHS_MAP.get(identifier);
	}
	
	static void setPathByIdentifier(String identifier,String path) {
		PATHS_MAP.put(identifier, path);
	}
	
	static Collection<URI> getURIs(Collection<String> uniformResourceIdentifiers) {
		if(CollectionHelper.isEmpty(uniformResourceIdentifiers))
			return null;
		Collection<URI> uris = new ArrayList<>();
		for(String uniformResourceIdentifier : uniformResourceIdentifiers)
			uris.add(URI.create(uniformResourceIdentifier));
		return uris;
	}
	
	/**/
	
	public static final Map<String,String> PATHS_MAP = new HashMap<>();
	public static final Map<String,String> PATHS_CACHE_MAP = new HashMap<>();
	
	String IDENTIFIER_FORMAT = "%s%sView";
	String IDENTIFIER_EDIT_TOKEN = "Edit";
	String IDENTIFIER_ENTITY_TOKEN = "__entity__";

}
