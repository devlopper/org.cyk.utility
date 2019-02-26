package org.cyk.utility.test;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.assertion.AssertionHelper;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.string.StringLocation;
import org.cyk.utility.string.Strings;

import lombok.ToString;

@ToString
public class TestExpectedStringImpl extends AbstractObject implements TestExpectedString, Serializable {
	private static final long serialVersionUID = 1L;

	private Map<StringLocation,Strings> locationStringsMap;
	
	@Override
	public void evaluate(String string) {
		Map<StringLocation,Strings> locationStringsMap = getLocationStringsMap();
		if(string != null && locationStringsMap!=null) {
			for(StringLocation indexLocation : StringLocation.values()) {
				Strings strings = locationStringsMap.get(indexLocation);
				if(strings!=null) {
					for(String indexString : strings.get()) {
						__inject__(AssertionHelper.class).assertTrue(indexString+"  NOT "+indexLocation+" "+string,__inject__(StringHelper.class).isAtLocation(string, indexString, indexLocation));
					}
				}
			}
		}
	}
	
	@Override
	public Map<StringLocation, Strings> getLocationStringsMap() {
		return locationStringsMap;
	}
	
	@Override
	public Map<StringLocation, Strings> getLocationStringsMap(Boolean instanciateIfNull) {
		Map<StringLocation,Strings> locationStringsMap = getLocationStringsMap();
		if(locationStringsMap == null && Boolean.TRUE.equals(instanciateIfNull))
			setLocationStringsMap(locationStringsMap = new LinkedHashMap<>());
		return locationStringsMap;
	}
	
	@Override
	public TestExpectedString setLocationStringsMap(Map<StringLocation, Strings> locationStringsMap) {
		this.locationStringsMap = locationStringsMap;
		return this;
	}
	
	@Override
	public Strings getLocationStrings(StringLocation location) {
		Map<StringLocation,Strings> locationStringsMap = getLocationStringsMap();
		return locationStringsMap == null ? null : locationStringsMap.get(location);
	}
	
	@Override
	public Strings getLocationStrings(StringLocation location, Boolean injectIfNull) {
		Strings instance = getLocationStrings(location);
		if(instance == null && Boolean.TRUE.equals(injectIfNull)) {
			
			setLocationStrings(location, instance = __inject__(Strings.class));
		}
		return instance;
	}
	
	@Override
	public TestExpectedString setLocationStrings(StringLocation location,Strings strings) {
		if(location != null) {
			Map<StringLocation,Strings> locationStringsMap = getLocationStringsMap();
			if(locationStringsMap == null)
				setLocationStringsMap(locationStringsMap = new HashMap<>());
			locationStringsMap.put(location, strings);
		}
		return this;
	}
	
}
