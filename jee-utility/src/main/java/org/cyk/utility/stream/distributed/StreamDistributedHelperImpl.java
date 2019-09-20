package org.cyk.utility.stream.distributed;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.system.SystemHelper;
import org.cyk.utility.type.BooleanHelper;

@ApplicationScoped
public class StreamDistributedHelperImpl extends AbstractHelper implements StreamDistributedHelper,Serializable {
	private static final long serialVersionUID = 1L;

	private Boolean isEnable;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		//TODO use configuration parameter
		setIsEnable(BooleanHelper.get(__inject__(SystemHelper.class).getProperty("org.cyk.utility.stream.distributed.is.enable", Boolean.TRUE)));
		//System.out.println("Stream distributed functionnality is enable : "+getIsEnable());
	}
	
	@Override
	public Boolean getIsEnable() {
		return isEnable;
	}

	@Override
	public StreamDistributedHelper setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
		return this;
	}

}
