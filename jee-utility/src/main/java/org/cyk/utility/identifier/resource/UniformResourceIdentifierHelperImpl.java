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
import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.clazz.ClassHelperImpl;
import org.cyk.utility.collection.CollectionHelperImpl;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.network.protocol.ProtocolHelperImpl;
import org.cyk.utility.number.NumberHelperImpl;
import org.cyk.utility.string.Case;
import org.cyk.utility.string.StringHelperImpl;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionDelete;
import org.cyk.utility.system.action.SystemActionUpdate;
import org.cyk.utility.throwable.ThrowableHelperImpl;
import org.cyk.utility.value.ValueHelperImpl;

@ApplicationScoped
public class UniformResourceIdentifierHelperImpl extends AbstractHelper implements UniformResourceIdentifierHelper,Serializable {
	private static final long serialVersionUID = 1L;

	/* Parameter name */
	
	public static String __buildParameterName__(Object value) {
		String parameterName = null;
		if(value!=null) {
			if(value instanceof Class) {
				if(Class.class.equals(value))
					parameterName = "class";
				else if(SystemAction.class.equals(value))
					parameterName = "action";
			}else if(value instanceof FieldName) {
				FieldName fieldName = (FieldName) value;
				parameterName = fieldName.name().toLowerCase();
			}else if(value instanceof Enum) {
				Enum<?> enumeration = (Enum<?>) value;
				parameterName = enumeration.name().toLowerCase();
			}else if(value instanceof String) {
				parameterName = (String) value;
			}
			
			if(StringHelperImpl.__isNotBlank__(parameterName)) {
				parameterName = StringUtils.remove(parameterName, "_");
			}
			
			if(StringHelperImpl.__isBlank__(parameterName))
				ThrowableHelperImpl.__throwRuntimeException__("Parameter name cannot be found for <<"+value+">>");
		}
		return parameterName;
	}
	
	/* Parameter value */

	public static String __buildParameterValue__(Object value) {
		String _value = null;
		if(value!=null) {
			if(value instanceof Class) {
				Class<?> klass = (Class<?>)value;
				if(VALUES_MAP.containsKey(klass.getName()))
					return VALUES_MAP.get(klass.getName());
				_value = StringUtils.substringBetween(klass.getSimpleName(), SystemAction.class.getSimpleName(), "Impl");
				if(_value == null)
					_value = StringUtils.substringAfter(klass.getSimpleName(), SystemAction.class.getSimpleName());
				if(_value != null)
					_value = _value.toLowerCase();
				if(StringUtils.isBlank(_value))
					_value = klass.getSimpleName().toLowerCase();				
				VALUES_MAP.put(klass.getName(), _value);
			}else if(value instanceof Objectable) {
				_value = ((Objectable)value).getIdentifier() == null ? null : ((Objectable)value).getIdentifier().toString();
				String key = _value;
				if(VALUES_MAP.containsKey(key))
					return VALUES_MAP.get(_value);
				if(value instanceof SystemAction)
					_value = _value.toLowerCase();
				
				if(StringUtils.isNotBlank(key))
					VALUES_MAP.put(key, _value);
			}else if(value instanceof String) {
				_value = (String) value;
			}
			if(StringHelperImpl.__isBlank__(_value))
				ThrowableHelperImpl.__throwRuntimeException__("Parameter value cannot be found for <<"+value+">>");
		}
		return _value;
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
			if(StringHelperImpl.__isNotBlank__(entry.getKey()) && CollectionHelperImpl.__isNotEmpty__(entry.getValue())) {
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
					if(CollectionHelperImpl.__isNotEmpty__(values))
						__parameters__.put(name,values);	
				}
			}
		}
		return ____buildQuery____(__parameters__);
	}
	
	public static String __buildQuery__(SystemAction systemAction) {
		Map<Object,List<Object>> parameters = new LinkedHashMap<>();
		if(systemAction.getEntities()!=null) {
			if(systemAction.getEntities().getElementClass()!=null)
				parameters.put(ParameterName.ENTITY_CLASS,Arrays.asList(systemAction.getEntities().getElementClass()));						
			if(CollectionHelperImpl.__isNotEmpty__(systemAction.getEntities()))
				parameters.put(ParameterName.ENTITY_IDENTIFIER,Arrays.asList(systemAction.getEntities().getFirst()));
			if(CollectionHelperImpl.__isNotEmpty__(systemAction.getEntitiesIdentifiers()))
				parameters.put(ParameterName.ENTITY_IDENTIFIER,Arrays.asList(systemAction.getEntitiesIdentifiers().getFirst()));				
		}
		parameters.put(ParameterName.ACTION_CLASS,Arrays.asList(systemAction.getClass()));	
		parameters.put(ParameterName.ACTION_IDENTIFIER,Arrays.asList(systemAction));		
		if(systemAction.getNextAction()!=null) {
			parameters.put(ParameterName.NEXT_ACTION_CLASS,Arrays.asList(systemAction.getNextAction().getClass()));	
			parameters.put(ParameterName.NEXT_ACTION_IDENTIFIER,Arrays.asList(systemAction.getNextAction()));
		}
		return __buildQuery__(parameters);
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
		
		/*
		if(StringHelperImpl.__isNotBlank__(context))
			path = context + ConstantCharacter.SLASH + path;
		
		while(StringUtils.contains(path, "//"))
			path = StringUtils.replace((String)path, "//", "/");
		path = __injectStringHelper__().removeToBeginIfDoesStartWith(path, ConstantCharacter.SLASH);
		
		ObjectByObjectMap parameterMap = getParameterMap();
		if(Boolean.TRUE.equals(__inject__(MapHelper.class).isNotEmpty(parameterMap))) {
			StringByStringMap finalParameterMap = __inject__(StringByStringMap.class).setIsSequential(Boolean.TRUE).setKeyValueSeparator(ConstantCharacter.EQUAL)
					.setEntrySeparator(ConstantCharacter.AMPERSTAMP);
			for(Map.Entry<Object, Object> index : parameterMap.getEntries()) {
				String name = null;
				if(index.getKey()!=null) {
					if(index.getKey() instanceof UniformResourceIdentifierParameterNameStringBuilder)
						name = ((UniformResourceIdentifierParameterNameStringBuilder)index.getKey()).execute().getOutput();
					else
						name = index.getKey().toString();
				}
				if(StringHelperImpl.__isNotBlank__(name)) {
					String value = null;
					if(index.getValue()!=null) {
						if(index.getValue() instanceof UniformResourceIdentifierParameterValueStringBuilder)
							value = ((UniformResourceIdentifierParameterValueStringBuilder)index.getValue()).execute().getOutput();
						else
							value = index.getValue().toString();
					}
					
					if(StringHelperImpl.__isNotBlank__(value)) {
						finalParameterMap.set(name,value);	
					}
				}
			}
			path = path + ConstantCharacter.QUESTION_MARK.toString() + finalParameterMap.getRepresentationAsString();
		}
		
		setPath(path);
		
		String query = __injectStringHelper__().getString(getQuery());
		if(StringHelperImpl.__isBlank__(query))
			setQuery(query = __injectValueHelper__().defaultToIfNull(__getRequestProperty__(RequestProperty.QUERY,string),ConstantEmpty.STRING));
		
		setFormatArguments(FORMAT_ARGUMENT_PATH_QUERY_SEPARATOR,Boolean.TRUE.equals(StringHelperImpl.__isBlank__(query)) ? ConstantEmpty.STRING : ConstantCharacter.QUESTION_MARK);
		
		return super.__getFormat__(format);
		*/
		return uniformResourceIdentifier;
	}
	
	public static String __build__(String scheme,String host,Integer port,String path,String query,String fragment,String model) {
		return __build__(scheme, null, null, host, port, path, query, fragment, model);
	}
	
	public static String __build__(String scheme,String host,Integer port,SystemAction systemAction) {
		return __build__(scheme, null, null, host, port, __buildPath__(systemAction), __buildQuery__(systemAction), null, null);
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
		/*
		if(identifier!=null) {
			UrlBuilder url = getUrl();
			if(url==null) {
				String urlString = identifierToUrlStringMapper.setIdentifier(identifier).execute().getOutput();
				if(StringHelperImpl.__isBlank__(urlString) && systemAction!=null) {
					Class<?> aClass = systemAction.getEntities() == null ? null : systemAction.getEntities().getElementClass();
					//Class<?> aClass = systemAction.getEntities(Boolean.TRUE).getElementClass();
					if(systemAction.getEntities()!=null)
						systemAction.getEntities().setElementClass(null);
					String identifier02 = __inject__(NavigationIdentifierStringBuilder.class).setSystemAction(systemAction).execute().getOutput();
					if(systemAction.getEntities()!=null)
						systemAction.getEntities().setElementClass(aClass);
					
					if(StringHelperImpl.__isNotBlank__(identifier02)) {
						NavigationIdentifierToUrlStringMapper identifierToUrlStringMapper02 = __inject__(NavigationIdentifierToUrlStringMapper.class);
						__process__(identifierToUrlStringMapper02, context, uniformResourceLocatorMap);
						urlString = identifierToUrlStringMapper02.setIdentifier(identifier02).execute().getOutput();
						if(StringHelperImpl.__isNotBlank__(urlString)) {
							identifier = identifier02;
						}
					}
				}
				
				if(StringHelperImpl.__isBlank__(urlString))
					ThrowableHelperImpl.__throwRuntimeException__("url not found for identifier "+identifier);
				url.getString(Boolean.TRUE).getUniformResourceIdentifierString(Boolean.TRUE).setString(urlString);
				
				if((isDeriveParametersFromSystemAction == null || Boolean.TRUE.equals(isDeriveParametersFromSystemAction)) && systemAction!=null) {
					//NavigationIdentifierStringBuilder identifierBuilder = getIdentifierBuilder();
					//if(identifierBuilder!=null) {
						if(parameterMap == null) {
							parameterMap = __inject__(ObjectByObjectMap.class);
							parameterMap.setIsSequential(Boolean.TRUE);	
						}
						
					//}
				}
				url.getString(Boolean.TRUE).getUniformResourceIdentifierString(Boolean.TRUE).setParameterMap(parameterMap);
			}
			
			navigation.setIdentifier(identifier);
			if(url!=null)
				navigation.setUniformResourceLocator(url.execute().getOutput());
		}
		
		if(CollectionHelperImpl.__isNotEmpty__(dynamicParameterNames)) {
			navigation.setDynamicParameterNames(__inject__(Strings.class));
			for(Object index : dynamicParameterNames.get()) {
				if(index!=null)
					if(index instanceof String) {
						navigation.getDynamicParameterNames().add((String)index);
					}else {
						navigation.getDynamicParameterNames().add(index.toString());
					}
			}
		}
		return navigation;
		*/
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
	
	public static String __buildPath__(SystemAction systemAction,String root,Boolean isRecursive) {
		String path = null;
		String identifier = __buildPathIdentifier__(systemAction);
		if(StringHelperImpl.__isNotBlank__(identifier)) {
			//Find specific for entity class
			path = __getPathByIdentifier__(identifier);
			if(path == null && Boolean.TRUE.equals(isRecursive)) {
				//Find generic for any entity class
				Class<?> klass = systemAction.getEntityClass();
				systemAction.setEntityClass(null);
				identifier = __buildPathIdentifier__(systemAction);
				systemAction.setEntityClass(klass);
				if(StringHelperImpl.__isNotBlank__(identifier)) {
					path = __getPathByIdentifier__(identifier);	
				}
			}
		}
		if(path != null) {
			path = __concatenatePathRoot__(path, root);
		}
		return path;
	}
	
	public static String __buildPath__(SystemAction systemAction) {
		return __buildPath__(systemAction, null, Boolean.TRUE);
	}
	
	public static String __buildPathIdentifier__(Class<? extends SystemAction> systemActionClass,Class<?> entityClass,Boolean isUseGeneric) {
		Object[] arguments = new Object[2];		
		arguments[0] = entityClass==null ? IDENTIFIER_ENTITY_TOKEN : StringHelperImpl.__applyCase__(entityClass.getSimpleName(), Case.FIRST_CHARACTER_LOWER);
		if(Boolean.TRUE.equals(isUseGeneric) && ClassHelperImpl.__isInstanceOfOne__(systemActionClass,SystemActionCreate.class,SystemActionUpdate.class,SystemActionDelete.class))
			arguments[1] = IDENTIFIER_EDIT_TOKEN;
		else
			arguments[1] = StringUtils.substringBetween(systemActionClass.getSimpleName(), SystemAction.class.getSimpleName(), "Impl");
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
	
	/**/
	
	public static String SCHEME;
	public static String HOST;
	public static Integer PORT;
	public static String PATH_ROOT;
	
	private static final Map<String,String> VALUES_MAP = new HashMap<>();
	public static final Map<String,String> PATHS_MAP = new HashMap<>();
	
	private static final String IDENTIFIER_FORMAT = "%s%sView";
	private static final String IDENTIFIER_EDIT_TOKEN = "Edit";
	private static final String IDENTIFIER_ENTITY_TOKEN = "__entity__";
}
