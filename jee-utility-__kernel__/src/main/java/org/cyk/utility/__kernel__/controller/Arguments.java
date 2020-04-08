package org.cyk.utility.__kernel__.controller;

import java.io.Serializable;
import java.util.Collection;

import javax.ws.rs.core.Response;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.cyk.utility.__kernel__.TypeHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.identifier.resource.ProxyGetter;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.mapping.MappingHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.rest.ResponseHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.RuntimeException;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class Arguments<T> extends AbstractObject implements Serializable {
	
	public static Boolean IS_REPRESENTATION_PROXYABLE = Boolean.TRUE;
	
	private Class<T> controllerEntityClass;
	private Class<?> representationEntityClass;
	private Boolean isRepresentationProxyable = IS_REPRESENTATION_PROXYABLE;
	private Object representation;
	private Class<?> responseEntityClass;
	private org.cyk.utility.__kernel__.representation.Arguments representationArguments;	
	private Collection<T> providedCollection;
	private Collection<T> creatables;
	private Collection<T> updatables;
	private Collection<T> deletables;	
	private Collection<T> existingCollection;
	private Boolean isNotBelogingToProvidedCollectionDeletable;
	private Boolean countable;
	
	Class<T> __controllerEntityClass__;
	Class<?> __representationEntityClass__;
	Boolean __isRepresentationProxyable__;
	Object __representation__;
	org.cyk.utility.__kernel__.representation.Arguments __representationArguments__;	
	Response __response__;
	Object __responseEntity__;
	RuntimeException __runtimeException__;
	
	public void prepare(Class<T> controllerEntityClass,Class<?> representationClass) {
		if(this.controllerEntityClass == null)
			__controllerEntityClass__ = controllerEntityClass;
		else
			__controllerEntityClass__ = this.controllerEntityClass;
		if(representationEntityClass == null)
			__representationEntityClass__ = RepresentationEntityClassGetter.getInstance().get(__controllerEntityClass__);	
		if(__representationEntityClass__ == null)
			throw new RuntimeException("representation entity class is required");
		
		if(isRepresentationProxyable == null)
			__isRepresentationProxyable__ = Boolean.TRUE;
		else
			__isRepresentationProxyable__ = isRepresentationProxyable;
		
		if(representation == null) {
			if(__isRepresentationProxyable__) {
				if(org.cyk.utility.__kernel__.representation.EntityReader.class.equals(representationClass))
					__representation__ = ProxyGetter.getInstance().get(org.cyk.utility.__kernel__.representation.EntityReader.class);
				else if(org.cyk.utility.__kernel__.representation.EntitySaver.class.equals(representationClass))
					__representation__ = ProxyGetter.getInstance().get(RepresentationClassGetter.getInstance().get(__controllerEntityClass__, RepresentationClassGetter.Function.SAVER));
			}else {
				if(org.cyk.utility.__kernel__.representation.EntityReader.class.equals(representationClass))
					__representation__ = org.cyk.utility.__kernel__.representation.EntityReader.getInstance();
				else if(org.cyk.utility.__kernel__.representation.EntitySaver.class.equals(representationClass))
					try {
						__representation__ = MethodUtils.invokeExactStaticMethod(RepresentationClassGetter.getInstance().get(__controllerEntityClass__, RepresentationClassGetter.Function.SAVER)
								, "getInstance");
					} catch (Exception exception) {
						throw new RuntimeException(exception);
					}
			}
			if(__representation__ == null)
				throw new RuntimeException("service cannot be deduced from "+representationClass);				
		}else
			__representation__ = representation;
		if(__representation__ == null)
			throw new RuntimeException("service."+representationClass+" cannot be acquired");
		if(representationArguments == null)
			__representationArguments__ = new org.cyk.utility.__kernel__.representation.Arguments();
		else
			__representationArguments__ = representationArguments;
		
		if(StringHelper.isBlank(__representationArguments__.getRepresentationEntityClassName()))
			__representationArguments__.setRepresentationEntityClass(__representationEntityClass__);
	}
	
	public <RESPONSE_ENTITY> void finalise(Response response) {
		if(response == null)
			throw new RuntimeException("Response is recquired");
		__response__ = response;
		if(response.hasEntity()) {
			RuntimeException.Dto runtimeExceptionDto = null;
			if(__isRepresentationProxyable__) {
				if(ResponseHelper.isFamilySuccessful(response)) {
					if(ClassHelper.isInstanceOf(responseEntityClass, Collection.class))
						__responseEntity__ = response.readEntity(TypeHelper.instantiateGenericCollectionParameterizedTypeForJaxrs(Collection.class,__representationEntityClass__));
				}else
					runtimeExceptionDto = response.readEntity(RuntimeException.Dto.class);
			}else {
				if(ResponseHelper.isFamilySuccessful(response))
					__responseEntity__ = response.getEntity();
				else
					runtimeExceptionDto = (RuntimeException.Dto) response.getEntity();
			}
			if(runtimeExceptionDto == null) {
				if(ClassHelper.isInstanceOf(responseEntityClass, Collection.class)) {
					if(CollectionHelper.isEmpty((Collection<?>) __responseEntity__))
						;
					else
						__responseEntity__ = (Collection<T>) MappingHelper.getSources((Collection<?>)__responseEntity__, __controllerEntityClass__);
				}
			}else {
				__runtimeException__ = MappingHelper.getDestination(runtimeExceptionDto, RuntimeException.class);
				throw __runtimeException__;
			}
		}
	}
}