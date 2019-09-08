package org.cyk.utility.css;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.collection.CollectionHelperImpl;
import org.cyk.utility.helper.AbstractHelper;

@ApplicationScoped
public class CascadeStyleSheetHelperImpl extends AbstractHelper implements CascadeStyleSheetHelper, Serializable {
	private static final long serialVersionUID = 1L;

	private static CascadeStyleSheetHelper INSTANCE;
	public static CascadeStyleSheetHelper getInstance(Boolean isNew) {
		//if(INSTANCE == null || Boolean.TRUE.equals(isNew))
			INSTANCE =  DependencyInjection.inject(CascadeStyleSheetHelper.class);
		return INSTANCE;
	}
	public static CascadeStyleSheetHelper getInstance() {
		return getInstance(null);
	}
	
	@Override
	public Collection<String> getStyleClassesFromRoles(Collection<?> roles) {
		return __getStyleClassesFromRoles__(roles);
	}
	
	@Override
	public Collection<String> getStyleClassesFromRoles(Object... roles) {
		return __getStyleClassesFromRoles__(CollectionHelperImpl.getInstance().instanciate(roles));
	}
	
	public static Collection<String> __getStyleClassesFromRoles__(Collection<?> roles) {
		if(roles != null && !roles.isEmpty())
			return roles.stream().map(x -> PREFIX+x.toString().toLowerCase()).collect(Collectors.toSet());
		return null;
	}
	
}
