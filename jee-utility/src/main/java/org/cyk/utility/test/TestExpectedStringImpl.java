package org.cyk.utility.test;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.assertion.AssertionHelper;
import org.cyk.utility.collection.CollectionInstanceString;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.string.StringLocation;

import lombok.ToString;

@ToString
public class TestExpectedStringImpl extends AbstractObject implements TestExpectedString, Serializable {
	private static final long serialVersionUID = 1L;

	private Map<StringLocation,CollectionInstanceString> locationStringsMap;
	
	@Override
	public void evaluate(String string) {
		Map<StringLocation,CollectionInstanceString> locationStringsMap = getLocationStringsMap();
		if(string != null && locationStringsMap!=null) {
			for(StringLocation indexLocation : StringLocation.values()) {
				CollectionInstanceString strings = locationStringsMap.get(indexLocation);
				if(strings!=null) {
					for(String indexString : strings.get()) {
						__inject__(AssertionHelper.class).assertTrue(indexString+"  NOT "+indexLocation+" "+string,__inject__(StringHelper.class).isAtLocation(string, indexString, indexLocation));
					}
				}
			}
		}
	}
	
	@Override
	public Map<StringLocation, CollectionInstanceString> getLocationStringsMap() {
		return locationStringsMap;
	}
	
	@Override
	public Map<StringLocation, CollectionInstanceString> getLocationStringsMap(Boolean instanciateIfNull) {
		Map<StringLocation,CollectionInstanceString> locationStringsMap = getLocationStringsMap();
		if(locationStringsMap == null && Boolean.TRUE.equals(instanciateIfNull))
			setLocationStringsMap(locationStringsMap = new LinkedHashMap<>());
		return locationStringsMap;
	}
	
	@Override
	public TestExpectedString setLocationStringsMap(Map<StringLocation, CollectionInstanceString> locationStringsMap) {
		this.locationStringsMap = locationStringsMap;
		return this;
	}
	
	@Override
	public CollectionInstanceString getLocationStrings(StringLocation location) {
		Map<StringLocation,CollectionInstanceString> locationStringsMap = getLocationStringsMap();
		return locationStringsMap == null ? null : locationStringsMap.get(location);
	}
	
	@Override
	public CollectionInstanceString getLocationStrings(StringLocation location, Boolean injectIfNull) {
		CollectionInstanceString instance = getLocationStrings(location);
		if(instance == null && Boolean.TRUE.equals(injectIfNull)) {
			
			setLocationStrings(location, instance = __inject__(CollectionInstanceString.class));
		}
		return instance;
	}
	
	@Override
	public TestExpectedString setLocationStrings(StringLocation location,CollectionInstanceString strings) {
		if(location != null) {
			Map<StringLocation,CollectionInstanceString> locationStringsMap = getLocationStringsMap();
			if(locationStringsMap == null)
				setLocationStringsMap(locationStringsMap = new HashMap<>());
			locationStringsMap.put(location, strings);
		}
		return this;
	}
	
}
