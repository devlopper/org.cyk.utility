package org.cyk.utility.system.layer;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.lang3.StringUtils;

@ApplicationScoped
public class SystemLayerControllerImpl extends AbstractSystemLayerImpl implements SystemLayerController, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		getEntityLayer().setClassNameRegularExpression("controller.entities..+Impl$");
	}
	
	@Override
	public String getDataTransferClassNameFromEntityClassName(String entityClassName) {
		String name = null;
		if(entityClassName!=null) {
			if(StringUtils.endsWith(entityClassName, "Impl"))
				entityClassName = StringUtils.substringBeforeLast(entityClassName, "Impl");
			name = StringUtils.replaceOnce(entityClassName, ".client.controller.", ".server.representation.")+"Dto";
		}
		return name;
	}

	@Override
	public String getDataTransferClassNameFromEntityClass(Class<?> entityClass) {
		String name = null;
		if(entityClass!=null)
			name = getDataTransferClassNameFromEntityClassName(entityClass.getName());
		return name;
	}

	@Override
	public Class<?> getDataTransferClassFromEntityClass(Class<?> entityClass) {
		return entityClass == null ? null : org.cyk.utility.__kernel__.klass.ClassHelper.getByName(getDataTransferClassNameFromEntityClass(entityClass));
	}

	@Override
	public String getDataTransferClassNameFromEntity(Object entity) {
		return entity == null ? null : getDataTransferClassNameFromEntityClass(entity.getClass());
	}

	@Override
	public Class<?> getDataTransferClassFromEntity(Object entity) {
		return entity == null ? null : getDataTransferClassFromEntityClass(entity.getClass());
	}
	
	@Override
	public String getRepresentationClassNameFromEntityClassName(String entityClassName) {
		String name = null;
		if(entityClassName!=null) {
			if(StringUtils.endsWith(entityClassName, "Impl"))
				entityClassName = StringUtils.substringBeforeLast(entityClassName, "Impl");
			name = StringUtils.replaceOnce(entityClassName, ".client.controller.entities.", ".server.representation.api.")+"Representation";
		}
		return name;
	}

	@Override
	public String getRepresentationClassNameFromEntityClass(Class<?> entityClass) {
		String name = null;
		if(entityClass!=null)
			name = getRepresentationClassNameFromEntityClassName(entityClass.getName());
		return name;
	}

	@Override
	public Class<?> getRepresentationClassFromEntityClass(Class<?> entityClass) {
		return entityClass == null ? null : org.cyk.utility.__kernel__.klass.ClassHelper.getByName(getRepresentationClassNameFromEntityClass(entityClass));
	}

	@Override
	public String getRepresentationClassNameFromEntity(Object entity) {
		return entity == null ? null : getRepresentationClassNameFromEntityClass(entity.getClass());
	}

	@Override
	public Class<?> getRepresentationClassFromEntity(Object entity) {
		return entity == null ? null : getRepresentationClassFromEntityClass(entity.getClass());
	}

}
