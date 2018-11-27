package org.cyk.utility.system.layer;

public interface SystemLayerController extends SystemLayer {

	String getDataTransferClassNameFromEntityClassName(String entityClassName);
	
	String getDataTransferClassNameFromEntityClass(Class<?> entityClass);
	Class<?> getDataTransferClassFromEntityClass(Class<?> entityClass);
	
	String getDataTransferClassNameFromEntity(Object entity);
	Class<?> getDataTransferClassFromEntity(Object entity);
	
	String getRepresentationClassNameFromEntityClassName(String entityClassName);
	
	String getRepresentationClassNameFromEntityClass(Class<?> entityClass);
	Class<?> getRepresentationClassFromEntityClass(Class<?> entityClass);
	
	String getRepresentationClassNameFromEntity(Object entity);
	Class<?> getRepresentationClassFromEntity(Object entity);
	
}
