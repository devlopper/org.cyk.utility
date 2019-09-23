package org.cyk.utility.device;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.collection.AbstractCollectionInstanceImpl;

@Dependent
public class DevicesImpl extends AbstractCollectionInstanceImpl<Device> implements Devices,Serializable {
	private static final long serialVersionUID = 1L;

}
