package org.cyk.utility.__kernel__.icon;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.constant.ConstantCharacter;

public interface IconIdentifierGetter {

	String get(Icon icon);
	
	IconIdentifierGetter FONT_AWSOME = new IconIdentifierGetter() {
		@Override
		public String get(Icon icon) {
			if(icon == null)
				return null;
			return String.format(FORMAT, StringUtils.replace(icon.name().toLowerCase(),UNDESCORE, MINUS));
		}
		
		String FORMAT = "fa fa-%s";
		String UNDESCORE = ConstantCharacter.UNDESCORE.toString();
		String MINUS = ConstantCharacter.MINUS.toString();
	};
	
	
}
