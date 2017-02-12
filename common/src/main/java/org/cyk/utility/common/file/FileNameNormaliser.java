package org.cyk.utility.common.file;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.Action;
import org.cyk.utility.common.Constant;

public interface FileNameNormaliser extends Action<String, String> {
	
	String ILLEGAL_CHARACTERS = "\\/.,;:!?";
	String ILLEGAL_CHARACTERS_REPLACEMENT = StringUtils.repeat(Constant.CHARACTER_UNDESCORE.charValue(), ILLEGAL_CHARACTERS.length());
	
	public static class Adapter extends Action.Adapter<String, String> implements FileNameNormaliser , Serializable {
		private static final long serialVersionUID = 1L;

		public Adapter() {
			super("File name normaliser", String.class, null, String.class, null);
		}
		
		/**/
		
		public static class Default extends FileNameNormaliser.Adapter implements Serializable {
			private static final long serialVersionUID = 1L;

			public Default() {
				super();
			}
			
			@Override
			public String execute() {
				StringBuilder result = new StringBuilder(StringUtils.replaceChars(input, ILLEGAL_CHARACTERS, ILLEGAL_CHARACTERS_REPLACEMENT));
				return result.toString();
			}
		}
		
	}
	
	/**/
	
}
