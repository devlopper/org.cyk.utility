package org.cyk.utility.server.representation;

import java.io.Serializable;

public class RepresentationFunctionModifierImpl extends AbstractRepresentationFunctionModifierImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __executeBusiness__() {
		if(getEntities()!=null)
			;
		else if(getEntity()!=null) {
			Object entity = __injectBusiness__().findOne(getPersistenceEntityClass(), __injectNumberHelper__().getLong(
					__injectFieldHelper__().getFieldValueSystemIdentifier(getEntity())));
			__injectBusiness__().update(entity);
		}else {
			
		}
	}
	
}
