package org.cyk.utility.clazz;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

public class ClassInstanceImpl extends AbstractObject implements ClassInstance,Serializable {
	private static final long serialVersionUID = 1L;

	private Class<?> clazz;
	private Boolean isPersistable;
	private Boolean isTransferable;
	
	@Override
	public Class<?> getClazz() {
		return clazz;
	}
	
	@Override
	public ClassInstance setClazz(Class<?> clazz) {
		this.clazz = clazz;
		return this;
	}
	
	@Override
	public Boolean getIsPersistable() {
		return isPersistable;
	}
	
	@Override
	public ClassInstance setIsPersistable(Boolean isPersistable) {
		this.isPersistable = isPersistable;
		return this;
	}
	
	@Override
	public Boolean getIsTransferable() {
		return isTransferable;
	}
	
	@Override
	public ClassInstance setIsTransferable(Boolean isTransferable) {
		this.isTransferable = isTransferable;
		return this;
	}
	
	
}
