package org.cyk.utility.__kernel__.internationalization;

import java.io.Serializable;
import java.util.List;

import org.cyk.utility.__kernel__.collection.AbstractCollectionInstanceImpl;

@Deprecated
public class ResourceBundlesImpl extends AbstractCollectionInstanceImpl<ResourceBundle> implements ResourceBundles,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setIsDoNotAddNull(Boolean.TRUE).setIsDistinct(Boolean.TRUE).setCollectionClass(List.class);
	}
	
}
