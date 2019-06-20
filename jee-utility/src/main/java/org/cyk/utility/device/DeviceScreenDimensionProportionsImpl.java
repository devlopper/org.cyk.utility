package org.cyk.utility.device;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.collection.CollectionHelper;

@Dependent
public class DeviceScreenDimensionProportionsImpl extends AbstractObject implements DeviceScreenDimensionProportions,Serializable {
	private static final long serialVersionUID = 1L;

	private Map<Class<? extends Device>, Integer> map;
	
	@Override
	public Map<Class<? extends Device>, Integer> getMap() {
		return map;
	}

	@Override
	public Map<Class<? extends Device>, Integer> getMap(Boolean instanciateIfNull) {
		return (Map<Class<? extends Device>, Integer>) __getInstanciateIfNull__(FIELD_MAP, instanciateIfNull);
	}

	@Override
	public DeviceScreenDimensionProportions setMap(Map<Class<? extends Device>, Integer> map) {
		this.map = map;
		return this;
	}

	@Override
	public DeviceScreenDimensionProportions setByClasses(Integer value,Collection<Class<? extends Device>> classes) {
		if(__inject__(CollectionHelper.class).isNotEmpty(classes)) {
			Map<Class<? extends Device>, Integer> map = getMap(Boolean.TRUE);
			for(Class<? extends Device> index : classes)
				map.put(index, value);
		}
		return this;
	}

	@Override
	public DeviceScreenDimensionProportions setByClasses(Integer value, Class<? extends Device>... classes) {
		return setByClasses(value,__inject__(CollectionHelper.class).instanciate(classes));
	}

	@Override
	public DeviceScreenDimensionProportions setAllClasses(Integer _default, Integer television, Integer desktop,Integer tablet, Integer phone) {
		setByClasses(_default, (Class<? extends Device>)null);
		setByClasses(television, DeviceTelevision.class);
		setByClasses(desktop, DeviceDesktop.class);
		setByClasses(tablet, DeviceTablet.class);
		setByClasses(phone, DevicePhone.class);
		return this;
	}

	@Override
	public DeviceScreenDimensionProportions setPhone(Integer value) {
		return setByClasses(value, DevicePhone.class);
	}

	@Override
	public DeviceScreenDimensionProportions setTablet(Integer value) {
		return setByClasses(value, DeviceTablet.class);
	}

	@Override
	public DeviceScreenDimensionProportions setDesktop(Integer value) {
		return setByClasses(value, DeviceDesktop.class);
	}

	@Override
	public DeviceScreenDimensionProportions setTelevision(Integer value) {
		return setByClasses(value, DeviceTelevision.class);
	}

	@Override
	public DeviceScreenDimensionProportions setDefault(Integer value) {
		return setByClasses(value, (Class<? extends Device>)null);
	}

	@Override
	public DeviceScreenDimensionProportions setAll(Integer value) {
		setPhone(value);
		setTablet(value);
		setDesktop(value);
		setTelevision(value);
		setDefault(value);
		return this;
	}
	
	/**/
	
	public static final String FIELD_MAP = "map";

}
