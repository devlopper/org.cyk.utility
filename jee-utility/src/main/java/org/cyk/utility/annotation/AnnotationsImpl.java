package org.cyk.utility.annotation;

import java.io.Serializable;
import java.lang.annotation.Annotation;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.collection.AbstractCollectionInstanceImpl;

@Dependent
public class AnnotationsImpl extends AbstractCollectionInstanceImpl<Annotation> implements Annotations , Serializable {
	private static final long serialVersionUID = 1L;

}
