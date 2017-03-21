package org.cyk.utility.common.file;

import java.io.File;
import java.io.Serializable;

import org.cyk.utility.common.Action;

public interface FileReader<RESULT> extends Action<File, RESULT> {
	
	public static class Adapter<RESULT> extends Action.Adapter.Default<File, RESULT> implements FileReader<RESULT>,Serializable {
		private static final long serialVersionUID = 1L;
	
		public Adapter(File file) {
			super("Read", File.class, file, null, null);
		}
		
		/**/
		
		public static class Default<RESULT> extends FileReader.Adapter<RESULT> implements Serializable {
			private static final long serialVersionUID = 1L;
			
			public Default(File file) {
				super(file);
			}

		}
		
	}
	
}
