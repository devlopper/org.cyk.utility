package org.cyk.utility.persistence.server.query.string;

import java.io.Serializable;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.persistence.query.QueryExecutorArguments;

public interface RuntimeQueryStringBuilder {

	String build(QueryExecutorArguments arguments);	
	
	/**/
	
	public abstract class AbstractImpl extends AbstractObject implements RuntimeQueryStringBuilder,Serializable {
		private static final long serialVersionUID = 1L;		
		
		@Override
		public String build(QueryExecutorArguments arguments) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("arguments", arguments);
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("query", arguments.getQuery());
			return __build__(arguments);
		}
		
		protected String __build__(QueryExecutorArguments arguments) {	
			return arguments.getQuery().getValue();
		}
	}
	
	/**/
	
	/**/
	
	static RuntimeQueryStringBuilder getInstance() {
		return Helper.getInstance(RuntimeQueryStringBuilder.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();	
}