package org.cyk.utility.common.file;

import java.io.Serializable;
import java.util.List;

public interface ArrayReader<RESULT> extends FileReader<RESULT> {

	public static class Adapter<RESULT> extends FileReader.Adapter.Default<RESULT> implements ArrayReader<RESULT>,Serializable {
		private static final long serialVersionUID = 1L;
	
		/**/
		
		public static class Default<RESULT> extends ArrayReader.Adapter<RESULT> implements Serializable {
			private static final long serialVersionUID = 1L;
			
		}
		
	}
	
	/**/
	
	public static interface TwoDimension<CELL> extends ArrayReader<List<CELL[]>> {
	
		public static class Adapter<CELL> extends ArrayReader.Adapter.Default<List<CELL[]>> implements TwoDimension<CELL>,Serializable {
			private static final long serialVersionUID = 1L;
		
			/**/
			
			public static class Default<CELL> extends TwoDimension.Adapter<CELL> implements Serializable {
				private static final long serialVersionUID = 1L;
				
			}
			
		}
		
	}
	
}
