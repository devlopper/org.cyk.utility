package org.cyk.utility.system.layer;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.constant.ConstantCharacter;
import org.cyk.utility.__kernel__.object.dynamic.AbstractSingleton;
import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.string.StringHelper;

public abstract class AbstractSystemLayerImpl extends AbstractSingleton implements SystemLayer, Serializable {
	private static final long serialVersionUID = 1L;

	private SystemSubLayerEntity entityLayer;
	private SystemSubLayerInterface interfaceLayer;
	private SystemSubLayerImplementation implementationLayer;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();

		setEntityLayer(__inject__(SystemSubLayerEntity.class).setParent(this));
		getEntityLayer().setPackageNameRegularExpression(StringUtils.replace(getEntityLayer().getPackageNameRegularExpression(Boolean.TRUE).getExpression()
				, SystemSubLayerEntity.PACKAGE_NAME_REGULAR_EXPRESSION_TOKEN_ENTITIES, getIdentifier().toString().toLowerCase()+"."
						+SystemSubLayerEntity.PACKAGE_NAME_REGULAR_EXPRESSION_TOKEN_ENTITIES));
		
		setInterfaceLayer(__inject__(SystemSubLayerInterface.class).setParent(this));
		getInterfaceLayer().setPackageNameRegularExpression(StringUtils.replace(getInterfaceLayer().getPackageNameRegularExpression(Boolean.TRUE).getExpression()
				, SystemSubLayerInterface.PACKAGE_NAME_REGULAR_EXPRESSION_TOKEN_API, getIdentifier().toString().toLowerCase()+"."
						+SystemSubLayerInterface.PACKAGE_NAME_REGULAR_EXPRESSION_TOKEN_API));
		getInterfaceLayer().setInterfaceNameRegularExpression(getIdentifier()+ConstantCharacter.DOLLAR.toString());
		getInterfaceLayer().getPackageNameRegularExpression().getMiddleTokens(Boolean.TRUE).add(getIdentifier().toString().toLowerCase());
		getInterfaceLayer().getInterfaceNameRegularExpression().getEndTokens(Boolean.TRUE).add(getIdentifier().toString());
		
		setImplementationLayer(__inject__(SystemSubLayerImplementation.class).setParent(this));
		getImplementationLayer().setPackageNameRegularExpression(StringUtils.replace(getImplementationLayer().getPackageNameRegularExpression(Boolean.TRUE).getExpression()
				, SystemSubLayerImplementation.PACKAGE_NAME_REGULAR_EXPRESSION_TOKEN_IMPL, getIdentifier().toString().toLowerCase()+"."
						+SystemSubLayerImplementation.PACKAGE_NAME_REGULAR_EXPRESSION_TOKEN_IMPL));
		getImplementationLayer().setClassNameRegularExpression(getIdentifier()+"Impl"+ConstantCharacter.DOLLAR.toString());
		getImplementationLayer().getClassNameRegularExpression().getEndTokens(Boolean.TRUE).add(getIdentifier().toString()+"Impl");
	}
	
	@Override
	protected String __getIdentifier__() {
		return StringUtils.substringAfter(super.__getIdentifier__(), SystemLayer.class.getSimpleName());
	}
	
	@Override
	public SystemSubLayerEntity getEntityLayer() {
		return entityLayer;
	}
	
	@Override
	public SystemLayer setEntityLayer(SystemSubLayerEntity systemLayerSubLayerEntity) {
		this.entityLayer = systemLayerSubLayerEntity;
		return this;
	}
	
	@Override
	public SystemSubLayerInterface getInterfaceLayer() {
		return interfaceLayer;
	}
	
	@Override
	public SystemLayer setInterfaceLayer(SystemSubLayerInterface systemLayerSubLayerInterface) {
		this.interfaceLayer = systemLayerSubLayerInterface;
		return this;
	}
	
	@Override
	public SystemSubLayerImplementation getImplementationLayer() {
		return implementationLayer;
	}
	
	@Override
	public SystemLayer setImplementationLayer(SystemSubLayerImplementation systemLayerSubLayerImplementation) {
		this.implementationLayer = systemLayerSubLayerImplementation;
		return this;
	}
	
	@Override
	public String getInterfaceNameFrom(String className, SystemSubLayer systemSubLayer) {
		String name = getInterfaceLayer().getInterfaceNameFromClassName(className, systemSubLayer);
		String subString = systemSubLayer!=null && systemSubLayer.getParent()!=null && systemSubLayer.getParent().getIdentifier()!=null 
				? systemSubLayer.getParent().getIdentifier().toString().toLowerCase() : null;
		
		String replacement = getInterfaceLayer()!=null && getInterfaceLayer().getParent()!=null && getInterfaceLayer().getParent().getIdentifier()!=null 
				? getInterfaceLayer().getParent().getIdentifier().toString().toLowerCase() : null;
		
		subString = __inject__(StringHelper.class).addToBeginIfDoesNotStartWith(subString, ConstantCharacter.DOT);		
		subString = __inject__(StringHelper.class).addToEndIfDoesNotEndWith(subString, ConstantCharacter.DOT);
		
		replacement = __inject__(StringHelper.class).addToBeginIfDoesNotStartWith(replacement, ConstantCharacter.DOT);		
		replacement = __inject__(StringHelper.class).addToEndIfDoesNotEndWith(replacement, ConstantCharacter.DOT);
		
		if(StringUtils.contains(name, subString))
			name = StringUtils.replace(name, subString,replacement);
		else {
			subString = subString.substring(1);
			replacement = replacement.substring(1);
			if(StringUtils.contains(name, subString))
				name = StringUtils.replace(name, subString,replacement);
			else {
				//TODO log warning
			}
		}
				
		return name;
	}
	
	@Override
	public String getInterfaceNameFromEntityClassName(String entityClassName) {
		String string = null;
		//if(getEntityLayer().isPackage(entityClassName)) {
			string = StringUtils.substringBeforeLast(StringUtils.replace(entityClassName, ".entities.", ".api."),"Impl")+__inject__(CollectionHelper.class)
			.getFirst(getInterfaceLayer().getInterfaceNameRegularExpression().getEndTokens().get());
		//}
		return string;
	}
	
	@Override
	public Class<?> getInterfaceClassFromEntityClassName(String entityClassName) {
		return __inject__(ClassHelper.class).getByName(getInterfaceNameFromEntityClassName(entityClassName));
	}
	
	
	@Override
	public Class<?> getInterfaceClassFromEntityClassName(Class<?> entityClass) {
		return entityClass == null ? null : getInterfaceClassFromEntityClassName(entityClass.getName());
	}
	
	@Override
	public <T> T injectInterfaceClassFromEntityClassName(Class<?> entityClass, Class<T> type) {
		T object = null;
		Class<?> interfaceClass = getInterfaceClassFromEntityClassName(entityClass);
		if(interfaceClass == null){
			
		}else{
			object = (T) __inject__(interfaceClass);
		}
		
		if(object == null){
			System.err.println("Interface not found for entity class name "+entityClass);
		}
		
		return object;
	}
	
	@Override
	public Object injectInterfaceClassFromEntityClassName(Class<?> entityClass) {
		return injectInterfaceClassFromEntityClassName(entityClass, Object.class);
	}
}
