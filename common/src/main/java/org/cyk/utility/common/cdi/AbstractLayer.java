package org.cyk.utility.common.cdi;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.inject.spi.Bean;

public abstract class AbstractLayer<META_CLASS> extends AbstractStartupBean implements Serializable {

	private static final long serialVersionUID = 3493129449617672056L;
	
	protected String id;
	protected Set<Class<META_CLASS>> classes = new HashSet<>();
	
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
