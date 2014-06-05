package org.cyk.utility.common.cdi;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.inject.spi.Bean;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(of="id",callSuper=false)
public abstract class AbstractLayer<META_CLASS> extends AbstractStartupBean implements Serializable {

	private static final long serialVersionUID = 3493129449617672056L;
	
	@Getter protected String id ,name;
	@Getter protected Set<Class<META_CLASS>> classes = new HashSet<>();
	
	public AbstractLayer() {
		id = getClass().getName();
		
	}
	
	protected Collection<Bean<?>> layerBeans(Class<? extends Annotation> aClass){
		return startupBeanExtension.beans(aClass, getClass().getPackage().getName());
	}
	
	@Override
	public String toString() {
		return id;
	}

}
