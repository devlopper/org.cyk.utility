package org.cyk.utility.server.representation;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.constant.ConstantCharacter;
import org.cyk.utility.__kernel__.object.__static__.representation.AbstractRepresentationObject;
import org.cyk.utility.__kernel__.object.__static__.representation.Action;
import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.clazz.ClassInstance;
import org.cyk.utility.clazz.ClassInstancesRuntime;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.identifier.resource.UniformResourceIdentifierStringBuilder;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.string.Strings;

public abstract class AbstractMapperSourceDestinationImpl<SOURCE,DESTINATION> extends org.cyk.utility.mapping.AbstractMapperSourceDestinationImpl<SOURCE,DESTINATION> {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenGetSourceAfter__(DESTINATION destination, SOURCE source) {
		super.__listenGetSourceAfter__(destination, source);
		if(source instanceof AbstractRepresentationObject) {
			AbstractRepresentationObject representationObject = (AbstractRepresentationObject) source;
			Strings actionsIdentifiers = __getActionsIdentifiers__(destination,source);
			if(DependencyInjection.inject(CollectionHelper.class).isNotEmpty(actionsIdentifiers)) {
				for(String actionIdentifier : actionsIdentifiers.get()) {
					String pathFormat = __getPathFormat__(actionIdentifier,destination,source);
					HttpServletRequest request = DependencyInjection.inject(HttpServletRequest.class);
					Object[] pathFormatParameters = __getPathFormatParameters__(actionIdentifier,request,destination, source);
					String path = DependencyInjection.inject(ArrayHelper.class).isEmpty(pathFormatParameters) ? pathFormat : String.format(pathFormat, pathFormatParameters);
					String uniformResourceLocator = DependencyInjection.inject(UniformResourceIdentifierStringBuilder.class)
							.setRequest(request).setPath(path).execute().getOutput();
					if(DependencyInjection.inject(StringHelper.class).isNotBlank(uniformResourceLocator)) {
						String actionMethod = __getActionMethod__(actionIdentifier, destination, source);
						representationObject.add__action__(actionIdentifier, uniformResourceLocator, actionMethod);	
					}
				}
			}
		}
	}
	
	protected Strings __getActionsIdentifiers__(DESTINATION destination, SOURCE source) {
		Strings identifiers = DependencyInjection.inject(Strings.class);
		ClassInstance classInstance = DependencyInjection.inject(ClassInstancesRuntime.class).get(destination.getClass());
		if(Boolean.TRUE.equals(classInstance.getIsPersistable())) {
			identifiers.add(Action.IDENTIFIER_READ,Action.IDENTIFIER_UPDATE,Action.IDENTIFIER_DELETE);
		}
		return identifiers;
	}
	
	protected String __getPathFormat__(String actionIdentifier,DESTINATION destination, SOURCE source) {
		if(Action.IDENTIFIER_READ.equals(actionIdentifier))
			return PATH_FORMAT_READ;
		if(Action.IDENTIFIER_UPDATE.equals(actionIdentifier))
			return PATH_FORMAT_UPDATE;
		if(Action.IDENTIFIER_DELETE.equals(actionIdentifier))
			return PATH_FORMAT_DELETE;
		return null;
	}
	
	protected Object[] __getPathFormatParameters__(String actionIdentifier,HttpServletRequest request,DESTINATION destination, SOURCE source) {
		String context = StringUtils.substringAfter(request.getRequestURI(), request.getContextPath());
		context = StringUtils.removeStart(context, "/");
		context = StringUtils.substringBefore(context,"/");
		if(Action.IDENTIFIER_READ.equals(actionIdentifier) || Action.IDENTIFIER_DELETE.equals(actionIdentifier)) {
			if(source instanceof AbstractEntityFromPersistenceEntity)
				return new Object[] {context,((AbstractEntityFromPersistenceEntity)source).getIdentifier()};
		}
		return new Object[] {context};
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
	
	private static final String PATH_FORMAT_READ = "%s/"+StringUtils.replace(RepresentationEntity.PATH_GET_ONE, RepresentationEntity.FORMAT_PARAMETER_IDENTIFIER, "%s");
	private static final String PATH_FORMAT_UPDATE = "%s/"+RepresentationEntity.PATH_UPDATE_ONE;
	private static final String PATH_FORMAT_DELETE = "%s/"+RepresentationEntity.PATH_DELETE_IDENTIFIERS+ConstantCharacter.QUESTION_MARK+RepresentationEntity.PARAMETER_IDENTIFIER
			+ConstantCharacter.EQUAL+ConstantCharacter.PERCENTAGE+"s";
}