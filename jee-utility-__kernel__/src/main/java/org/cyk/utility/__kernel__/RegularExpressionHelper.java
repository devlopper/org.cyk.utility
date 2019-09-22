package org.cyk.utility.__kernel__;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;

public interface RegularExpressionHelper {

	static String buildIsExactly(Collection<String> strings) {
		if(strings == null || strings.isEmpty())
			return null;
		return "^("+StringUtils.join(strings,"|")+")$";
	}
	
	static String buildIsExactly(String...strings) {
		if(strings == null || strings.length == 0)
			return null;
		return buildIsExactly(List.of(strings));
	}
	
	static String buildIsNotExactly(Collection<String> strings) {
		if(strings == null || strings.isEmpty())
			return null;
		return StringUtils.join(strings.stream().map(string -> "(^(?!"+string+")|(?<!"+string+")$)").collect(Collectors.toList()),ConstantEmpty.STRING);
	}
	
	static String buildIsNotExactly(String...strings) {
		if(strings == null || strings.length == 0)
			return null;
		return buildIsNotExactly(List.of(strings));
	}
}
