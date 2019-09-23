package org.cyk.utility.identifier.resource;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.network.protocol.ProtocolHelperImpl;
import org.cyk.utility.number.NumberHelperImpl;
import org.cyk.utility.request.RequestHelperImpl;
import org.cyk.utility.string.Case;
import org.cyk.utility.string.StringHelperImpl;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionDelete;
import org.cyk.utility.system.action.SystemActionUpdate;
import org.cyk.utility.value.ValueHelperImpl;

@ApplicationScoped
public class UniformResourceIdentifierHelperImpl extends AbstractHelper implements UniformResourceIdentifierHelper,Serializable {
	private static final long serialVersionUID = 1L;

	/* Parameter name */
	
	public static String __buildParameterName__(Object value) {
		if(value == null)
			return null;
		if(value instanceof Class) {
			if(SystemAction.class.equals(value))
				return "action";
			return __normalizeParameterName__(((Class<?>)value).getSimpleName().toLowerCase());
		}else if(value instanceof Enum)
			return __normalizeParameterName__(((Enum<?>) value).name().toLowerCase());
		else if(value instanceof String)
			return __normalizeParameterName__(StringUtils.defaultIfBlank((String) value,null));		
		throw new RuntimeException("Parameter name cannot be found for <<"+value+">>");
	}
	
	public static String __normalizeParameterName__(String value) {
		if(value == null)
			return null;
		value = StringUtils.remove(value, "_");
		return value;
	}
	
	/* Parameter value */

	public static String __buildParameterValue__(Object value) {
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
	
	/* URI */
	
	public static String __getComponent__(Component component,String string) {
		if(component == null || StringHelperImpl.__isBlank__(string))
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
	
	public static String ____buildQuery____(Map<String,List<String>> parameters) {
		if(parameters == null || parameters.isEmpty())
			return null;
		Collection<String> keysValues = null;
		for(Map.Entry<String,List<String>> entry : parameters.entrySet()) {
			if(StringHelperImpl.__isNotBlank__(entry.getKey()) && CollectionHelper.isNotEmpty(entry.getValue())) {
				if(keysValues == null)
					keysValues = new ArrayList<>();
				//FIXME , this is not a standard way to do
				keysValues.add(entry.getKey()+"="+StringHelperImpl.__concatenate__(entry.getValue(), ","));
			}
		}
		return keysValues == null || keysValues.isEmpty() ? null : StringUtils.join(keysValues,"&");
	}
	
	public static String __buildQuery__(Map<Object,List<Object>> parameters) {
		if(parameters == null || parameters.isEmpty())
			return null;
		Map<String,List<String>> __parameters__ = new LinkedHashMap<>();
		for(Map.Entry<Object,List<Object>> index : parameters.entrySet()) {
			String name = null;
			if(index.getKey()!=null)
				name = __buildParameterName__(index.getKey());
			if(StringHelperImpl.__isNotBlank__(name)) {
				List<String> values = new ArrayList<>();
				String value = null;
				if(index.getValue()!=null) {
					for(Object object : index.getValue()) {
						value = __buildParameterValue__(object);
						if(StringHelperImpl.__isNotBlank__(value)) {
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
		return ____buildQuery____(__parameters__);
	}
	
	public static <T> String __buildQuery__(Class<? extends SystemAction> systemActionClass,Object systemActionIdentifier,Class<T> entityClass,Collection<T> entities,Collection<?> identifiers,Class<? extends SystemAction> nextSystemActionClass,Object nextSystemActionIdentifier) {
		Map<Object,List<Object>> parameters = new LinkedHashMap<>();
		if(entityClass != null)
			parameters.put(ParameterName.ENTITY_CLASS,Arrays.asList(entityClass));
		List<Object> list = null;
		if(entities!=null) {
			list = new ArrayList<Object>();
			for(Object index : entities)
				if(index instanceof Objectable) {
					Object identifier = ((Objectable)index).getIdentifier();
					if(identifier != null)
						list.add(identifier);
				}		
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
				systemActionIdentifier = __buildParameterValue__(systemActionClass);
			if(systemActionIdentifier != null)
				parameters.put(ParameterName.ACTION_IDENTIFIER,Arrays.asList(systemActionIdentifier));		
		}
		if(nextSystemActionClass != null) {
			parameters.put(ParameterName.NEXT_ACTION_CLASS,Arrays.asList(nextSystemActionClass));	
			if(nextSystemActionIdentifier == null)
				nextSystemActionIdentifier = __buildParameterValue__(nextSystemActionClass);
			if(nextSystemActionIdentifier != null)
				parameters.put(ParameterName.NEXT_ACTION_IDENTIFIER,Arrays.asList(nextSystemActionIdentifier));
		}
		return __buildQuery__(parameters);
	}
	
	public static String __buildQuery__(SystemAction systemAction) {
		/*Map<Object,List<Object>> parameters = new LinkedHashMap<>();
		if(systemAction.getEntities()!=null) {
			if(systemAction.getEntities().getElementClass()!=null)
				parameters.put(ParameterName.ENTITY_CLASS,Arrays.asList(systemAction.getEntities().getElementClass()));						
			if(CollectionHelper.isNotEmpty(systemAction.getEntities()))
				parameters.put(ParameterName.ENTITY_IDENTIFIER,Arrays.asList(systemAction.getEntities().getFirst()));
			if(CollectionHelper.isNotEmpty(systemAction.getEntitiesIdentifiers()))
				parameters.put(ParameterName.ENTITY_IDENTIFIER,Arrays.asList(systemAction.getEntitiesIdentifiers().getFirst()));				
		}
		parameters.put(ParameterName.ACTION_CLASS,Arrays.asList(systemAction.getClass()));	
		parameters.put(ParameterName.ACTION_IDENTIFIER,Arrays.asList(systemAction));		
		if(systemAction.getNextAction()!=null) {
			parameters.put(ParameterName.NEXT_ACTION_CLASS,Arrays.asList(systemAction.getNextAction().getClass()));	
			parameters.put(ParameterName.NEXT_ACTION_IDENTIFIER,Arrays.asList(systemAction.getNextAction()));
		}
		return __buildQuery__(parameters);
		*/
		
		return systemAction == null ? null : __buildQuery__(systemAction.getClass(), systemAction.getIdentifier() == null ? null : systemAction.getIdentifier().toString().toLowerCase()
				, (Class<Object>)systemAction.getEntityClass(), systemAction.getEntities() == null ? null : systemAction.getEntities().get()
				, systemAction.getEntitiesIdentifiers() == null ? null : systemAction.getEntitiesIdentifiers().get()
				, systemAction.getNextAction() == null ? null : systemAction.getNextAction().getClass()
				, systemAction.getNextAction() == null ? null : systemAction.getNextAction().getIdentifier());
		
	}
	
	public static String __build__(String scheme,String user,String password,String host,Integer port,String path,String query,String fragment,String model) {	
		String uniformResourceIdentifier = ConstantEmpty.STRING;
		//scheme
		if(StringHelperImpl.__isBlank__(scheme))
			scheme = __getComponent__(Component.SCHEME, model);
		if(StringHelperImpl.__isNotBlank__(scheme))
			uniformResourceIdentifier = scheme+"://";
		//user
		if(StringHelperImpl.__isBlank__(user))
			user = __getComponent__(Component.USER, model);		
		if(StringHelperImpl.__isNotBlank__(user)) {
			uniformResourceIdentifier += user;
			if(StringHelperImpl.__isBlank__(password))
				password = __getComponent__(Component.PASSWORD, model);		
			if(StringHelperImpl.__isNotBlank__(password))
				uniformResourceIdentifier += ":"+password;
			uniformResourceIdentifier += "@";
		}
		//host	
		if(StringHelperImpl.__isBlank__(host))
			host = __getComponent__(Component.HOST, model);	
		if(StringHelperImpl.__isNotBlank__(host)) {
			uniformResourceIdentifier += host;
			if(port == null)
				port = NumberHelperImpl.__getInteger__(__getComponent__(Component.PORT, model));
			if(port!= null && port > 0 && scheme!=null && port != ProtocolHelperImpl.__getDefaultPort__(scheme))
				uniformResourceIdentifier += ":"+port;
		}
		//path
		if(StringHelperImpl.__isBlank__(path))
			path = __getComponent__(Component.PATH, model);	
		if(StringHelperImpl.__isNotBlank__(path))
			uniformResourceIdentifier += __formatPath__(path);

		//query
		if(StringHelperImpl.__isBlank__(query))
			query = __getComponent__(Component.QUERY, model);
		if(StringHelperImpl.__isNotBlank__(query)) {
			uniformResourceIdentifier += "?"+query;
		}
		//fragment
		if(StringHelperImpl.__isBlank__(fragment))
			fragment = __getComponent__(Component.FRAGMENT, model);	
		if(StringHelperImpl.__isNotBlank__(fragment)) {
			uniformResourceIdentifier += "#"+fragment;
		}
		return uniformResourceIdentifier;
	}
	
	public static String __build__(String scheme,String host,Integer port,String path,String query,String fragment,String model) {
		return __build__(scheme, null, null, host, port, path, query, fragment, model);
	}
	
	public static <T> String __build__(String scheme,String host,Integer port,Class<? extends SystemAction> systemActionClass,Object systemActionIdentifier
			,Class<T> entityClass,Collection<T> entities,Collection<?> identifiers,Class<? extends SystemAction> nextSystemActionClass,Object nextSystemActionIdentifier) {
		return __build__(scheme, null, null, host, port, __buildPath__(systemActionClass, entityClass, null, Boolean.TRUE)
				, __buildQuery__(systemActionClass, systemActionIdentifier, entityClass, entities, identifiers, nextSystemActionClass, nextSystemActionIdentifier), null, null);
	}
	
	public static String __build__(String scheme,String host,Integer port,SystemAction systemAction) {
		return __build__(scheme, null, null, host, port, __buildPath__(systemAction), __buildQuery__(systemAction), null, null);
	}
	
	public static <T> String __build__(Object request,Class<? extends SystemAction> systemActionClass,Object systemActionIdentifier,Class<T> entityClass
			,Collection<T> entities,Collection<?> identifiers,Class<? extends SystemAction> nextSystemActionClass,Object nextSystemActionIdentifier) {
		return __build__(RequestHelperImpl.__getPropertyScheme__(request), RequestHelperImpl.__getPropertyHost__(request), RequestHelperImpl.__getPropertyPort__(request)
				, systemActionClass, systemActionIdentifier, entityClass, entities, identifiers, nextSystemActionClass, nextSystemActionIdentifier);
	}
	
	public static String __build__(Object request,SystemAction systemAction) {
		return __build__(RequestHelperImpl.__getPropertyScheme__(request), RequestHelperImpl.__getPropertyHost__(request), RequestHelperImpl.__getPropertyPort__(request)
				, systemAction);
	}
	
	/* Path */
	
	public static String __formatPath__(String path) {
		if(StringHelperImpl.__isNotBlank__(path)) {
			if(!path.startsWith("/"))
				path = "/"+path;
		}
		path = StringUtils.replace(path, "//", "/");
		return path;
	}
	
	public static String __buildPath__(String identifier,String root) {
		String path = __concatenatePathRoot__(null, root);
		if(StringHelperImpl.__isNotBlank__(identifier)) {
			String __path__ = __getPathByIdentifier__(identifier);
			if(StringHelperImpl.__isNotBlank__(__path__)) {
				if(StringHelperImpl.__isBlank__(path))
					path = __path__;
				else
					path = path + __formatPath__(__path__);	
			}
		}
		return __formatPath__(path);
	}
	
	private static String __concatenatePathRoot__(String path,String root) {		
		root = ValueHelperImpl.__defaultToIfBlank__(root, PATH_ROOT);	
		if(StringHelperImpl.__isNotBlank__(root)) {
			path = root + (path == null ? ConstantEmpty.STRING : path);
		}
		return path;
	}
	
	public static String __buildPath__(String identifier) {
		return __buildPath__(identifier, null);
	}
	
	public static String __buildPath__(Class<? extends SystemAction> systemActionClass,Class<?> entityClass,String root,Boolean isRecursive) {
		String path = null;
		//Find specific for entity class
		String identifier01 = __buildPathIdentifier__(systemActionClass,entityClass,null);
		if(identifier01!=null) {
			path = PATHS_CACHE_MAP.get(identifier01);
			if(path != null)
				return path;
			path = __getPathByIdentifier__(identifier01);
			if(path == null && Boolean.TRUE.equals(isRecursive)) {
				//Find generic for any entity class
				String identifier02 = __buildPathIdentifier__(systemActionClass,null,Boolean.TRUE);
				path = __getPathByIdentifier__(identifier02);	
			}
		}
		if(path != null) {
			path = __concatenatePathRoot__(path, root);
			PATHS_CACHE_MAP.put(identifier01, path);
		}	
		return path;
	}
	
	public static String __buildPath__(SystemAction systemAction,String root,Boolean isRecursive) {
		/*String path = null;
		String identifier01 = __buildPathIdentifier__(systemAction);
		if(identifier01!=null) {
			//Find specific for entity class
			path = PATHS_CACHE_MAP.get(identifier01);
			if(path != null)
				return path;
			path = __getPathByIdentifier__(identifier01);
			if(path == null && Boolean.TRUE.equals(isRecursive)) {
				//Find generic for any entity class
				Class<?> klass = systemAction.getEntityClass();
				systemAction.setEntityClass(null);
				String identifier02 = __buildPathIdentifier__(systemAction);
				systemAction.setEntityClass(klass);
				path = __getPathByIdentifier__(identifier02);	
			}
		}
		if(path != null) {
			path = __concatenatePathRoot__(path, root);
			PATHS_CACHE_MAP.put(identifier01, path);
		}*/	
		return systemAction == null ? null : __buildPath__(systemAction.getClass(), systemAction.getEntityClass(), root, isRecursive);
	}
	
	public static String __buildPath__(SystemAction systemAction) {
		return __buildPath__(systemAction, null, Boolean.TRUE);
	}
	
	public static String __buildPathIdentifier__(Class<? extends SystemAction> systemActionClass,Class<?> entityClass,Boolean isUseGeneric) {
		if(systemActionClass == null)
			return null;
		Object[] arguments = new Object[2];		
		arguments[0] = entityClass==null ? IDENTIFIER_ENTITY_TOKEN : StringHelperImpl.__applyCase__(entityClass.getSimpleName().endsWith("Impl") 
				? StringUtils.substringBefore(entityClass.getSimpleName(), "Impl") : entityClass.getSimpleName(), Case.FIRST_CHARACTER_LOWER);
		if(Boolean.TRUE.equals(isUseGeneric) && ClassHelper.isInstanceOfOne(systemActionClass,SystemActionCreate.class,SystemActionUpdate.class,SystemActionDelete.class))
			arguments[1] = IDENTIFIER_EDIT_TOKEN;
		else
			arguments[1] = systemActionClass.getSimpleName().endsWith("Impl") 
					? StringUtils.substringBetween(systemActionClass.getSimpleName(), SystemAction.class.getSimpleName(), "Impl")
					: StringUtils.substringAfter(systemActionClass.getSimpleName(), SystemAction.class.getSimpleName());
		return String.format(IDENTIFIER_FORMAT, arguments);
	}
	
	public static String __buildPathIdentifier__(SystemAction systemAction) {
		return __buildPathIdentifier__(systemAction.getClass(), systemAction.getEntityClass(), systemAction.getEntityClass() == null);
	}
	
	public static String __getPathByIdentifier__(String identifier) {
		return PATHS_MAP.get(identifier);
	}
	
	public static void __setPathByIdentifier__(String identifier,String path) {
		PATHS_MAP.put(identifier, path);
	}
	
	public static Collection<URI> __getURIs__(Collection<String> uniformResourceIdentifiers) {
		if(CollectionHelper.isEmpty(uniformResourceIdentifiers))
			return null;
		Collection<URI> uris = new ArrayList<>();
		for(String uniformResourceIdentifier : uniformResourceIdentifiers)
			uris.add(URI.create(uniformResourceIdentifier));
		return uris;
	}
	
	/**/
	
	public static String SCHEME;
	public static String HOST;
	public static Integer PORT;
	public static String PATH_ROOT;
	
	public static final Map<String,String> PATHS_MAP = new HashMap<>();
	public static final Map<String,String> PATHS_CACHE_MAP = new HashMap<>();
	
	private static final String IDENTIFIER_FORMAT = "%s%sView";
	private static final String IDENTIFIER_EDIT_TOKEN = "Edit";
	private static final String IDENTIFIER_ENTITY_TOKEN = "__entity__";
}
