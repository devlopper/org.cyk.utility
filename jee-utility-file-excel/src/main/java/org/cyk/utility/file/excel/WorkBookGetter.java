package org.cyk.utility.file.excel;

import java.io.File;
import java.io.InputStream;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.value.Value;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface WorkBookGetter {

	WorkBook get(Arguments arguments);
	
	WorkBook get(InputStream inputStream);
	WorkBook get(byte[] bytes);
	WorkBook get(File file);
	WorkBook get(String fileName);
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments {
		private InputStream inputStream;
		private byte[] bytes;
		private File file;
		private String fileName;
	}
	
	/**/
	
	static WorkBookGetter getInstance() {
		return Helper.getInstance(WorkBookGetter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	
}
