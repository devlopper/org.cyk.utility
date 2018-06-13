package org.cyk.utility.number;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

public interface NumberConstant {

	BigDecimal LOWEST_NON_ZERO_POSITIVE_VALUE = new BigDecimal("0."+StringUtils.repeat('0', 10)+"1");
	
}
