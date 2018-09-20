package org.cyk.utility.system.layer;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.object.dynamic.AbstractSingleton;
import org.cyk.utility.character.CharacterConstant;
import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.collection.CollectionHelper;

public abstract class AbstractSystemLayerImpl extends AbstractSingleton implements SystemLayer, Serializable {
	private static final long serialVersionUID = 1L;

	private SystemSubLayerEntity entityLayer;
	private SystemSubLayerInterface interfaceLayer;
	private SystemSubLayerImplementation implementationLayer;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();

		setEntityLayer(__inject__(SystemSubLayerEntity.class));
		getEntityLayer().setPackageNameRegularExpression(StringUtils.replace(getEntityLayer().getPackageNameRegularExpression(Boolean.TRUE).getExpression()
				, SystemSubLayerEntity.PACKAGE_NAME_REGULAR_EXPRESSION_TOKEN_ENTITIES, getIdentifier().toString().toLowerCase()+"."
						+SystemSubLayerEntity.PACKAGE_NAME_REGULAR_EXPRESSION_TOKEN_ENTITIES));
		
		setInterfaceLayer(__inject__(SystemSubLayerInterface.class));
		getInterfaceLayer().setPackageNameRegularExpression(StringUtils.replace(getInterfaceLayer().getPackageNameRegularExpression(Boolean.TRUE).getExpression()
				, SystemSubLayerInterface.PACKAGE_NAME_REGULAR_EXPRESSION_TOKEN_API, getIdentifier().toString().toLowerCase()+"."
						+SystemSubLayerInterface.PACKAGE_NAME_REGULAR_EXPRESSION_TOKEN_API));
		getInterfaceLayer().setInterfaceNameRegularExpression(getIdentifier()+CharacterConstant.DOLLAR.toString());
		getInterfaceLayer().getInterfaceNameRegularExpression().getEndTokens(Boolean.TRUE).add(getIdentifier().toString());
		
		setImplementationLayer(__inject__(SystemSubLayerImplementation.class));
		getImplementationLayer().setPackageNameRegularExpression(StringUtils.replace(getImplementationLayer().getPackageNameRegularExpression(Boolean.TRUE).getExpression()
				, SystemSubLayerImplementation.PACKAGE_NAME_REGULAR_EXPRESSION_TOKEN_IMPL, getIdentifier().toString().toLowerCase()+"."
						+SystemSubLayerImplementation.PACKAGE_NAME_REGULAR_EXPRESSION_TOKEN_IMPL));
		getImplementationLayer().setClassNameRegularExpression(getIdentifier()+"Impl"+CharacterConstant.DOLLAR.toString());
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
	public String getInterfaceNameFromEntityClassName(String entityClassName) {
		String string = null;
		//if(getEntityLayer().isPackage(entityClassName)) {
			string = StringUtils.replace(entityClassName, ".entities.", ".api.")+__inject__(CollectionHelper.class)
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
		return getInterfaceClassFromEntityClassName(entityClass.getName());
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
