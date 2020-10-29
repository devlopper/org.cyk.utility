package org.cyk.utility.__kernel__.business;

import java.io.Serializable;

import javax.transaction.Transactional;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.persistence.query.NativeQueryStringExecutor.Arguments;
import org.cyk.utility.__kernel__.value.Value;

public interface NativeQueryStringExecutor {

	@Transactional
	void execute(Arguments arguments);
	
	/**/
	
	public abstract class AbstractImpl extends AbstractObject implements NativeQueryStringExecutor,Serializable {
		private static final long serialVersionUID = 1L;
	
		@Override @Transactional
		public void execute(Arguments arguments) {
			if(arguments == null)
				return;
			org.cyk.utility.__kernel__.persistence.query.NativeQueryStringExecutor.getInstance().execute(arguments);
		}
	}
	
	/**/
	
	static NativeQueryStringExecutor getInstance() {
		return Helper.getInstance(NativeQueryStringExecutor.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();	
}