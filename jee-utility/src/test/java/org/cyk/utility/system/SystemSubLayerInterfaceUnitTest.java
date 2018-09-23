package org.cyk.utility.system;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.cyk.utility.system.layer.SystemSubLayer;
import org.cyk.utility.system.layer.SystemSubLayerEntity;
import org.cyk.utility.system.layer.SystemSubLayerInterface;

public class SystemSubLayerInterfaceUnitTest extends AbstractSystemSubLayerUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Override
	protected SystemSubLayer __injectSystemSubLayer__() {
		return __inject__(SystemSubLayerInterface.class);
	}

	@Override
	protected String __getExpectedPackageNameRegularExpression__() {
		return "[.]{0,1}api[.]{0,1}";
	}

	@Override
	protected String __getExpectedClassNameRegularExpression__() {
		return null;
	}

	@Override
	protected String __getExpectedInterfaceNameRegularExpression__() {
		return null;
	}
	
	@Override
	protected Collection<String> __getTruePackages__() {
		return Arrays.asList("a.api.b","a.api","api.b","api");
	}
	
	@Override
	protected Collection<String> __getFalsePackages__() {
		return Arrays.asList("a.ap.b","a.ap","ap.b","ap");
	}
	
	@Override
	protected Map<SystemSubLayer, Map<String, String>> __getExpectedInterfaceNameFromClassName__() {
		Map<SystemSubLayer, Map<String, String>> map = new HashMap<>();
		Map<String, String> systemSubLayerMap = new LinkedHashMap<>();
		map.put(__inject__(SystemSubLayerEntity.class), systemSubLayerMap);
		systemSubLayerMap.put(null, null);
		systemSubLayerMap.put("", "");
		systemSubLayerMap.put(" ", " ");
		systemSubLayerMap.put("  ", "  ");
		systemSubLayerMap.put("a", "a");
		systemSubLayerMap.put("entities", "entities");
		systemSubLayerMap.put("b.entities", "b.entities");
		systemSubLayerMap.put("b.entities.A", "b.api.A");
		systemSubLayerMap.put("entities.A", "api.A");
		return map;
	}
	
}
