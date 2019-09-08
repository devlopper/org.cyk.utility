package org.cyk.utility.client.controller.component;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.device.Device;
import org.cyk.utility.device.DeviceDesktop;
import org.cyk.utility.device.DevicePhone;
import org.cyk.utility.device.DeviceTablet;
import org.cyk.utility.device.DeviceTelevision;

public class VisibleComponentAreaDimensionImpl extends AbstractObject implements VisibleComponentAreaDimension,Serializable {
	private static final long serialVersionUID = 1L;
	
	private Map<Class<? extends Device>, Integer> proportionMap;
	
	@Override
	public Map<Class<? extends Device>, Integer> getProportionMap() {
		return proportionMap;
	}

	@Override
	public Map<Class<? extends Device>, Integer> getProportionMap(Boolean injectIfNull) {
		if(proportionMap == null && Boolean.TRUE.equals(injectIfNull))
			proportionMap = new LinkedHashMap<>();
		return proportionMap;
	}

	@Override
	public VisibleComponentAreaDimension setProportionMap(Map<Class<? extends Device>, Integer> proportionMap) {
		this.proportionMap = proportionMap;
		return this;
	}

	@Override
	public VisibleComponentAreaDimension setProportions(Integer proportion,Collection<Class<? extends Device>> classes) {
		if(__inject__(CollectionHelper.class).isNotEmpty(classes)) {
			Map<Class<? extends Device>, Integer> proportionMap = getProportionMap(Boolean.TRUE);
			for(Class<? extends Device> index : classes)
				proportionMap.put(index, proportion);
		}
		return this;
	}

	@Override
	public VisibleComponentAreaDimension setProportions(Integer proportion, Class<? extends Device>... classes) {
		return setProportions(proportion,__inject__(CollectionHelper.class).instanciate(classes));
	}

	@Override
	public VisibleComponentAreaDimension setProportions(Integer _default, Integer television, Integer desktop,Integer tablet, Integer phone) {
		setProportions(_default, (Class<? extends Device>)null);
		setProportions(television, DeviceTelevision.class);
		setProportions(desktop, DeviceDesktop.class);
		setProportions(tablet, DeviceTablet.class);
		setProportions(phone, DevicePhone.class);
		return this;
	}
	
	public static final String FIELD_PROPORTION_MAP = "proportionMap";

}
