package org.cyk.utility.stream.distributed;

import org.cyk.utility.helper.Helper;

public interface StreamDistributedHelper extends Helper {

	//TODO create configuration properties map and use it for helper or other who need to be configurable by caller
	
	Boolean getIsEnable();
	StreamDistributedHelper setIsEnable(Boolean isEnable);
	
}
