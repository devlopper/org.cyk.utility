package org.cyk.utility.__kernel__.persistence.query;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.__entities__.TestedEntityChild;
import org.cyk.utility.__kernel__.__entities__.TestedEntityParent;
import org.cyk.utility.__kernel__.persistence.query.annotation.Queries;
import org.cyk.utility.__kernel__.persistence.query.annotation.Query;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;

@Queries(value = {
		@Query(tupleClass = TestedEntityChild.class,name = "readByParentsCodes",value = "SELECT t FROM TestedEntityChild t WHERE t.parent.code IN :"+TestedEntityChildByParentsQuerier.PARAMETER_NAME_PARENTS_CODES)
})
public interface TestedEntityChildByParentsQuerier extends ByDimensionOneBusinessIdentifierQuerier<TestedEntityChild,TestedEntityParent,String> {

	/**/
	
	static TestedEntityChildByParentsQuerier getInstance() {
		return Helper.getInstance(TestedEntityChildByParentsQuerier.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
	
	String QUERY_NAME_READ = "readByParentsCodes";
	String QUERY_NAME_COUNT = StringHelper.get("countByParentsCodes");
	String PARAMETER_NAME_PARENTS_CODES = "parentsCodes";
}
