package org.cyk.utility.common.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

import javax.inject.Singleton;

import org.apache.commons.io.FileUtils;
import org.cyk.utility.common.Action;

@Singleton
public class FileHelper extends AbstractHelper implements Serializable  {

	private static final long serialVersionUID = 1L;

	private static FileHelper INSTANCE;
	
	public static FileHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new FileHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public byte[] getBytes(java.io.File file){
		try {
			return FileUtils.readFileToByteArray(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public java.io.InputStream getInputStream(String fileName){
		try {
			return new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**/
	
	public interface Read<RESULT> extends Action<File, RESULT> {
		
		public static class Adapter<RESULT> extends Action.Adapter.Default<File, RESULT> implements Read<RESULT>,Serializable {
			private static final long serialVersionUID = 1L;
		
			public Adapter(File file) {
				super("read", File.class, file, null);
			}
			
			/**/
			
			public static class Default<RESULT> extends Read.Adapter<RESULT> implements Serializable {
				private static final long serialVersionUID = 1L;
				
				public Default(File file) {
					super(file);
				}

			}
			
		}
		
	}
}
