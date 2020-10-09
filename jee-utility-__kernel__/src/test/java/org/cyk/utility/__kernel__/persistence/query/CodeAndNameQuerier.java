package org.cyk.utility.__kernel__.persistence.query;

import java.io.Serializable;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.__entities__.CodeAndName;
import org.cyk.utility.__kernel__.value.Value;

public interface CodeAndNameQuerier extends Querier.CodableAndNamable<CodeAndName> {

	/**/
	
	public static abstract class AbstractImpl extends Querier.CodableAndNamable.AbstractImpl<CodeAndName> implements CodeAndNameQuerier,Serializable {	
		
		@Override
		protected Class<CodeAndName> getKlass() {
			return CodeAndName.class;
		}
		
	}
	
	/**/
	
	static CodeAndNameQuerier getInstance() {
		return Helper.getInstance(CodeAndNameQuerier.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	
	/**/
	
	static void initialize() {
		Querier.CodableAndNamable.initialize(CodeAndName.class);
		//QueryHelper.addQueries(Query.buildSelect(CodeAndName.class, QueryIdentifierGetter.getInstance().get(CodeAndName.class, QueryName.READ_CODES)
		//		, ""));
	}	
}