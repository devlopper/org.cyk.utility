package org.cyk.utility.service.server;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.constant.ConstantCharacter;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.identifier.resource.UniformResourceIdentifierHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.object.__static__.representation.AbstractRepresentationObject;
import org.cyk.utility.__kernel__.object.__static__.representation.Action;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.string.RegularExpressionHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.__kernel__.value.ValueHelper;

public abstract class AbstractMapperSourceDestinationImpl<SOURCE,DESTINATION> extends org.cyk.utility.__kernel__.mapping.MapperSourceDestination.AbstractImpl<SOURCE,DESTINATION> {
	private static final long serialVersionUID = 1L;

	/* working variables : must be accessible from outside*/
	protected Boolean __isDestinationActionable__;
	protected Boolean __isDestinationPersistable__;
	protected Boolean __isDestinationProjectionable__;
	protected Strings __actionsIdentifiers__;
	protected String __resourcePath__;
	
	/*@SuppressWarnings("unchecked")
	@PostConstruct
    public void __listenPostConstruct__() {
		if(__destinationClass__ == null) {
			ClassNameBuilder classNameBuilder = DependencyInjection.inject(ClassNameBuilder.class).setKlass(getClass());
			classNameBuilder.getSourceNamingModel(Boolean.TRUE).server().representation().entities().setSuffix("DtoMapperImpl");
			classNameBuilder.getDestinationNamingModel(Boolean.TRUE).server().persistence().entities().suffix();
			__destinationClass__ = ValueHelper.returnOrThrowIfBlank("persistence entity class"
					,(Class<DESTINATION>) ClassHelper.getByName(classNameBuilder.execute().getOutput()));
		}
		
		__isDestinationPersistable__ = ClassHelper.isPersistable(__destinationClass__);
		__isDestinationProjectionable__ = ValueHelper.defaultToIfNull(ClassHelper.isProjectionable(__destinationClass__),Boolean.TRUE);
		__isDestinationActionable__ = ValueHelper.defaultToIfNull(ClassHelper.isActionable(__destinationClass__),Boolean.TRUE);
		if(Boolean.TRUE.equals(__isDestinationActionable__)) {			
			if(__isDestinationPersistable__) {
				__actionsIdentifiers__ = DependencyInjection.inject(Strings.class);
				__actionsIdentifiers__.add(Action.IDENTIFIER_READ,Action.IDENTIFIER_UPDATE,Action.IDENTIFIER_DELETE);
			}
			HttpServletRequest request = DependencyInjection.inject(HttpServletRequest.class);
			__resourcePath__ = StringUtils.substringAfter(request.getRequestURI(), request.getContextPath());
			__resourcePath__ = StringUtils.removeStart(__resourcePath__, "/");
			__resourcePath__ = StringUtils.substringBefore(__resourcePath__,"/");	
		}
		super.__listenPostConstruct__();
    }*/
	
	@Override
	protected void __listenGetSourceAfter__(DESTINATION destination, SOURCE source,Properties properties) {
		super.__listenGetSourceAfter__(destination, source);
		if(Boolean.TRUE.equals(__isDestinationActionable__)) {
			__addActions__(destination, source);	
		}
		
		if(Boolean.TRUE.equals(__isDestinationProjectionable__)) {
			Strings fieldsNames = (Strings) Properties.getFromPath(properties, Properties.FIELDS);
			__project__(destination, source, fieldsNames);
		}
	}
	
	protected void __addActions__(DESTINATION destination, SOURCE source) {
		if(source instanceof AbstractRepresentationObject) {
			AbstractRepresentationObject representationObject = (AbstractRepresentationObject) source;
			Strings actionsIdentifiers = __getActionsIdentifiers__(destination,source);
			if(CollectionHelper.isNotEmpty(actionsIdentifiers)) {
				for(String actionIdentifier : actionsIdentifiers.get()) {
					String pathFormat = __getPathFormat__(actionIdentifier,destination,source);
					//TODO is it necessary to get the request. required information can be set once in post construct
					HttpServletRequest request = DependencyInjection.inject(HttpServletRequest.class);
					Object[] pathFormatParameters = __getPathFormatParameters__(actionIdentifier,request,destination, source);
					String path = ArrayHelper.isEmpty(pathFormatParameters) ? pathFormat : String.format(pathFormat, pathFormatParameters);
					String uniformResourceLocator = UniformResourceIdentifierHelper.build(null, null, null, path, null, null, null);
					if(StringHelper.isNotBlank(uniformResourceLocator)) {
						String actionMethod = __getActionMethod__(actionIdentifier, destination, source);
						representationObject.add__action__(actionIdentifier, uniformResourceLocator, actionMethod);	
					}
				}
			}
		}			
	}
	
	protected Strings __getActionsIdentifiers__(DESTINATION destination, SOURCE source) {
		return __actionsIdentifiers__;
	}
	
	protected String __getPathFormat__(String actionIdentifier,DESTINATION destination, SOURCE source) {
		/*if(Action.IDENTIFIER_READ.equals(actionIdentifier))
			return PATH_FORMAT_READ;
		if(Action.IDENTIFIER_UPDATE.equals(actionIdentifier))
			return PATH_FORMAT_UPDATE;
		if(Action.IDENTIFIER_DELETE.equals(actionIdentifier))
			return PATH_FORMAT_DELETE;*/
		return null;
	}
	
	protected Object[] __getPathFormatParameters__(String actionIdentifier,HttpServletRequest request,DESTINATION destination, SOURCE source) {
		if(Action.IDENTIFIER_READ.equals(actionIdentifier) || Action.IDENTIFIER_DELETE.equals(actionIdentifier)) {
			Object identifier = FieldHelper.readSystemIdentifier(source);
			if(identifier != null)
				return new Object[] {__resourcePath__,identifier};
		}
		return new Object[] {__resourcePath__};
	}

	protected String __getActionMethod__(String actionIdentifier,DESTINATION destination, SOURCE source) {
		if(Action.IDENTIFIER_READ.equals(actionIdentifier))
			return Action.METHOD_GET;
		if(Action.IDENTIFIER_UPDATE.equals(actionIdentifier))
			return Action.METHOD_PUT;
		if(Action.IDENTIFIER_DELETE.equals(actionIdentifier))
			return Action.METHOD_DELETE;
		return Action.METHOD_GET;
	}
	
	/**/
	
	protected void __project__(DESTINATION destination, SOURCE source,Strings fieldsNames) {
		if(CollectionHelper.isNotEmpty(fieldsNames) )
			org.cyk.utility.__kernel__.field.FieldHelper.nullify(source, RegularExpressionHelper.buildIsNotExactly(fieldsNames.get()),null);
	}
	
	/**/
	/*
	private static final String PATH_FORMAT_READ = "%s/"+StringUtils.replace(RepresentationEntity.PATH_GET_ONE, RepresentationEntity.FORMAT_PARAMETER_IDENTIFIER, "%s");
	private static final String PATH_FORMAT_UPDATE = "%s/"+RepresentationEntity.PATH_UPDATE_ONE;
	private static final String PATH_FORMAT_DELETE = "%s/"+RepresentationEntity.PATH_DELETE_IDENTIFIERS+ConstantCharacter.QUESTION_MARK+RepresentationEntity.PARAMETER_IDENTIFIER
			+ConstantCharacter.EQUAL+ConstantCharacter.PERCENTAGE+"s";
	*/
}