package org.cyk.utility.common.generator;

import org.cyk.utility.common.helper.RandomHelper;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@EqualsAndHashCode(callSuper=false)
public class StringGenerator extends AbstractValueGenerator<String>{

	private static final long serialVersionUID = 2263135572597940699L;

	public static int LENGTH = 5;
	
	@Override
	public String generate() {
		return RandomHelper.getInstance().getAlphabetic(LENGTH);
	}

}
