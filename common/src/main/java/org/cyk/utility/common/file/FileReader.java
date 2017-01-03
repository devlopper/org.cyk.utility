package org.cyk.utility.common.file;

import java.io.Serializable;

import org.cyk.utility.common.cdi.BeanAdapter;

public interface FileReader<RESULT> {

	RESULT execute() throws Exception;
	
	public static class Adapter<RESULT> extends BeanAdapter implements FileReader<RESULT>,Serializable {
		private static final long serialVersionUID = 1L;
	
		@Override
		public RESULT execute() throws Exception {
			return null;
		}
		
		/**/
		
		public static class Default<RESULT> extends FileReader.Adapter<RESULT> implements Serializable {
			private static final long serialVersionUID = 1L;
			
		}
		
	}
	
}
