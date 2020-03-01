package org.cyk.utility.__kernel__.persistence.query;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.value.Value;

public interface TestedEntityChildByParents extends QueryByReferences<TestedEntityParent, TestedEntityChild, String> {

	/**/
	
	static TestedEntityChildByParents getInstance() {
		return Helper.getInstance(TestedEntityChildByParents.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
	
}
